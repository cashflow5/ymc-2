function fn_deleteShop(shopId){
	var isWorking = $('#' + shopId).find('td').eq(2).text().indexOf('正在运营') > -1 ? true : false ;
	var isApp = $('#' + shopId).find('td').eq(3).text().indexOf('已审核') > -1 ? true : false ;
	if(isApp){
		alert('该店铺已经审核，不能删除！');
		return;
	}
	if(isWorking){
		alert('该店铺正在运营，不能删除！');
		return; 
	}
	if(confirm("您确定要删除该店铺吗?")){
		$.ajax({
			url:"d_Shop.sc",
			data:{"shopId":shopId},
			type:"POST",
			async:false,
			dataType:"text",
			success:function(d){
				var obj = eval("("+d+")");
				if(obj.flag=="1"){
					alert("删除成功!");
					location.href="toShopList.sc";
				}else{
					alert("此店铺已被引用,不能进行删除!");
				}
			}
		});
	}
	
//	var isApp = checkIsApp(shopId);
//	
//	if(isApp){
//		alert("该店铺已经审核,不能删除!");
//		return;
//	}
//	
//	if(confirm("确定要删除该店铺吗?")){
//		
//		$.ajax({
//			url:"d_Shop.sc",
//			data:{"shopId":shopId},
//			type:"POST",
//			async:false,
//			dataType:"text",
//			success:function(d){
//				//alert(d);
//				var obj = eval("("+d+")");
//				if(obj.flag=="1"){
//					alert("删除成功!");
//					location.href="toShopList.sc";
//				}else{
//					alert("此店铺已被引用,不能进行删除!");
//				}
//			}
//		});
//	}
}

/**
 * 验证店铺是否已经审核
 * @param shopId
 * @return
 */
function checkIsApp(shopId){
	var url = "checkShopIsApp.sc";
	var data = {shopId:shopId};
	var result = false;
	
	$.ajax({
		url:url,
		data:data,
		dataType:'text',
		type:'POST',
		async:false,
		success:function(d){
			if(d=="1"){
				result = true;//表示已经审核
			}else{
				result = false;//表示未审核
			}
		}
	});
	return result;
}

/**
 * 
 * @param shopId
 * @return
 */
function fn_updateShopWorkingState(shopId,option_){
	///yitiansystem/commoditymgmt/bizmgmt/shopInformation/d_Shop.sc?shopId=
	if(option_==1){
		if(confirm("确定要停止运营该店铺吗?")){
			fn_WorkingOrNoWorking(shopId,"停止成功!");
		}
	}
	else if(option_==0){
		if(confirm("确定要启用该店铺吗?")){
			fn_WorkingOrNoWorking(shopId,"启用成功!");
		}
	}
}

/**
 * 停用与启用的操作
 * 抽取的方法
 * @param shopId
 * @return
 */
function fn_WorkingOrNoWorking(shopId,msg){
	$.ajax({
		url:"u_Shop.sc",
		data:{"shopId":shopId},
		type:"POST",
		dataType:"text",
		success:function(d){
			//alert(d);
			var obj = eval("("+d+")");
			alert(msg);
			location.href="toShopList.sc";
		}
	});
}
 
 
/**
 * 查看店内商品...跳转界面
 * @return
 */
function searchShopCommodity(shopId,shopName,brandNo,goodsState){
	$("#shopId").val(shopId);
	$("#shopName").val(shopName);
	$("#brandNo").val(brandNo);
	$("#goodsState").val(goodsState);
	$("#searchShopCommodityForm").submit();
}