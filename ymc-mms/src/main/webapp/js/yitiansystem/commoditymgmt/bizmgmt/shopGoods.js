/*定义 一个集合,用于子窗体传值*/
var commodityList=[];

/*打开窗体的方法*/
function getChooseCommoditys(){
	var shopId = $('#shopId').val();
	var brandNo = $('#brandNo').val();
	javascript:art.dialog.openwindow(
		basePath+'/yitiansystem/commoditymgmt/bizmgmt/shopcommodity/toCommodityList.sc?shopId=' + shopId + '&brandNo=' + brandNo,
		'','','yes',
		{id:'open',title: '添加商品分类'});
}

/*加载表格的方法,同时还要异步请求添加到类别里面*/
function addCommodityToTable(){
	//$("#commoditys_tbl>tbody").empty();
	var shopId=$("#shopId").val();
	var brandNo = $("#brandNo").val();
	//alert(brandNo);
	var ids = "";
	for(var i = 0;i<commodityList.length;i++){
		/*newRow_ = newRow.clone(true);
		newRow_.find("td:eq(0)").html(commodityList[i].commodityId.html());
		newRow_.find("td:eq(1)").html(commodityList[i].commodityName.html());
		newRow_.find("td:eq(2)").html("");
		newRow_.find("td:eq(3)").html("添加所属分类");
		newRow_.find("td:eq(4)").html(commodityList[i].prodDesc.html());
		*/
		ids=commodityList[i].commodityId.find("input:eq(0)").val()+";"+ids;
		//$("#commoditys_tbl>tbody").append(newRow_);
	}
	
	//选择商品后直接进行向数据库添加数据-----异步添加
	var data_={'commodityIds':ids,'shopCommodityTypeId':'','shopId':shopId};
	$.ajax({
		url:basePath+'/yitiansystem/commoditymgmt/bizmgmt/shopcommodity/c_addCheckedCommodityToType.sc',
		data:data_,
		type:'POST',
		dataType:'text',
		success:function(d){
			//alert(d);
			//$("#addToType").val("0");
			alert("添加成功!");
			//还原为默认值，除了把分类类型置为‘未归类’
			$('#commodityKey').val('');
			$('#commodityCondition').val('0');
			$('#isRecommend').val('-1');
			$('#typeId').val('1');
			$('#goodsState').attr('checked',true);
			$('#shopGoodsForm').submit();
//			location.href = basePath+"/yitiansystem/commoditymgmt/bizmgmt/shopInformation/toShopGoods.sc?shopId="+shopId+"&brandNo="+brandNo+"&goodsState=1";
		}
	});
}

//点击隐藏与显示按钮获取商品
function r_shopGoods(){
	$('#shopGoodsForm').submit();
}


/*
 * 批量处理：推荐、下架(亦就是显示与隐藏)、删除、审核
 */
function batch(action){
	var ids = vhkValues();
	if(ids == ""){
		alert('请选择商品');
		return ;
	}
	var url ;
	if(action == 'commend'){
		//推荐
		url = "batchCommend.sc";
	}else if(action == "cancel"){
		//取消推荐
		url = "batchCancelCommend.sc";
	}else if(action == 'undershelf'){
		//下架
		url = "batchUndershelf.sc";
	}else if(action == 'delete'){
		//删除
		url = "batchDelete.sc";
	}else if(action == 'verify'){
		//审核
		url = "batchVerify.sc";
	}else if(action =="upshelf"){
		//上架
		url = "batchShelve.sc";
	}
	$.ajax({
           type: "POST",
           url: url,
           data: {"ids":ids},
           dataType:"text", 
           success: function(msg){
           	 if(msg == "SUCCESS"){
           	 	if(url == "batchCommend.sc"){
           	 		//推荐
           	 		$(":checkbox[name='chk']").each(function(){
           	 			if($(this).attr("checked")) {
           	 			//将推荐状态改为已推荐
							$(this).parent('td').parent('tr').find('td').eq(6).text("推荐"); 
							$(this).parent('td').parent('tr').find('td').eq(6).addClass("ft-wt-b ft-cl-r");
           	 			}
           	 		});
           	 	}else if(url == "batchUndershelf.sc"){
           	 		//下架(隐藏)
           	 	 $('#shopGoodsForm').submit();
//           	 		$(":checkbox[name='chk']").each(function(){
//           	 			if($(this).attr("checked")) {
//           	 			//上下架状态修改为下架
//							$(this).parent('td').parent('tr').find('td').eq(7).text("隐藏"); 
//							$(this).parent('td').parent('tr').find('td').eq(7).removeClass("ft-wt-b ft-cl-r");
//           	 			}
//           	 		});
           	 	}else if(url == "batchDelete.sc"){
           	 		//删除
           	 		$('#shopGoodsForm').submit();
           	 	}else if(url == "batchShelve.sc"){
           	 		//上架（显示）
           	 	$('#shopGoodsForm').submit();
//           	 		$(":checkbox[name='chk']").each(function(){
//           	 			if($(this).attr("checked")) {
//           	 			//将上下架状态改为已上架
//							$(this).parent('td').parent('tr').find('td').eq(7).text("显示"); 
//							$(this).parent('td').parent('tr').find('td').eq(7).addClass("ft-wt-b ft-cl-r");
//           	 			}
//           	 		});
           	 	}else if(url == "batchCancelCommend.sc"){
           	 		//取消推荐
           	 		$(":checkbox[name='chk']").each(function(){
           	 			if($(this).attr("checked")) {
           	 			//将推荐状态改为普通
							$(this).parent('td').parent('tr').find('td').eq(6).text("普通"); 
           	 				$(this).parent('td').parent('tr').find('td').eq(6).removeClass("ft-wt-b ft-cl-r");
           	 			}
           	 		});
           	 	}else{
           	 		//审核
           	 	}
           	 }
           }
         });

}

var basePath="";
//全选反选
$(document).ready(function(){
	basePath=$("#basepath").val();
	doEnabledOrDisabled($('input[name=goodsState]:checked').val());
	$("#chkAll").click(function(){
		if($(this).attr("checked")) {
			$(" :checkbox").each(function(){   
				$(this).attr("checked",true);   
			});
		}else{
			$(" :checkbox").each(function(){   
				$(this).attr("checked",false);   
			});
		}
	});
});
/**
 * 获取checkbox的值
 */
function vhkValues(){
	var arr = new Array();
	$(" :checkbox[name='chk']").each(function(){   
		if($(this).attr("checked")) {
			arr.push($(this).val()); 
		}
	});
	if(arr.length == 0){
		return "";
	}
	return arr.join(",");
}

//隐藏或显示按钮
function doEnabledOrDisabled(e){
	var tr_length = $('tbody tr').length;
	if(tr_length == 0){
		$('a[id^=action]').each(function(){
			$(this).hide(0);
		})
	}else{
		$('#actionUpshelf' + e).hide(0);
	}
}


/**
* .跳转到编辑店内商品界面
* @return
*/
function editShopCommodity(shopId,shopName,brandNo,goodsState,id){
	$("#id_").val(id);
	$("#shopId_").val(shopId);
	$("#shopName_").val(shopName);
	$("#brandNo_").val(brandNo);
	$("#goodsState_").val(goodsState);
	
	$("#editShopCommodityForm").submit();
}
