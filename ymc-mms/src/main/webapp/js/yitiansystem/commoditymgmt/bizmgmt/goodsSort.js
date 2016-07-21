var path ;
var i = 0;
var subi = 0;
//一级分类的选中的值和文本
var rootVal ;
// 二级分类的选中的值和文本
var secondVal = $('#secondCategory').children('option:selected').val();
 // 三级分类的选中的值和文本
var threeVal;
// 存储选中集合的val
var arr = new Array();
// ajax请求
function get(selValue,selId){
}
//注册加载的事件
$(document).ready(function(){
	path = $("#basepath").val();
	var shopId=(location.href).substring((location.href).lastIndexOf("shopId=")+1);
	//alert(shopId);
	if(shopId==""||shopId==null)
		shopId=$("#shopId").val();
	
	//添加到某个分类
	$("#addToType").change(function(){
		var typeId = this.value;//获得类别编号
		if(typeId=="0"){
			return;
		}
		var shopCommodityIds = $("input:checkbox[name='checkname']:checked");
		
		var ids="";
		if(shopCommodityIds.size()==0){
			alert("请先选中商品再进行操作！");
			$(this).val("0");
			return;
		}
		for(var i =0;i<shopCommodityIds.size();i++){
			//alert(shopCommodityIds[i].value);
			ids=shopCommodityIds[i].value+";"+ids;
		}
		//alert(ids);
		//$("#shopId").val(shopId);/*店铺编号*/
		$("#commodityIds").val(ids);
		$("#shopCommodityTypeId").val(typeId);
		$("#shopCommodityFrm").attr("action","c_addCommodityToType.sc");
		//alert($("#shopCommodityFrm").attr("action"));
		$("#shopCommodityFrm").submit();
		
	});
	
	//移动到某个分类
	$("#moveToType").change(function(){
		var typeId = this.value;//获得类别编号
		//alert(typeId);
		if(typeId=="0"){
			return;
		}
		var shopCommodityIds = $("input:checkbox[name='checkname']:checked");
		
		var ids="";
		if(shopCommodityIds.size()==0){
			alert("请先选中商品再进行操作！");
			$(this).val("0");
			return;
		}
		for(var i =0;i<shopCommodityIds.size();i++){
			//alert(shopCommodityIds[i].value);
			ids=shopCommodityIds[i].value+";"+ids;
		}
		//alert(ids);//
		//$("#shopId").val(shopId);/*店铺编号*/
		$("#commodityIds").val(ids);
		$("#shopCommodityTypeId").val(typeId);
		$("#shopCommodityFrm").attr("action","u_updateShopCommodityType.sc");
		//alert($("#shopCommodityFrm").attr("action"));
		$("#shopCommodityFrm").submit();
	});
	
	
});

/*全选的方法*/
function checkAlls(current){
	//alert(current);
	$("input:checkbox[name='checkname']").attr("checked",current);
}


function fn_deleteThisType(current,typeId){
	var index = current.parentNode.parentNode.rowIndex;
	//alert(index);
	var commodityId = $(current).parent().parent().find("td:eq(0)>input[type=checkbox]").val();
	//alert(commodityId);
	$("#commodityIds").val(commodityId);
	$("#shopCommodityTypeId").val(typeId);
	$("#shopCommodityFrm").attr("action","d_deleteShopCommodityByType.sc");
	alert($("#shopCommodityFrm").attr("action"));
	$("#shopCommodityFrm").submit();
}

function searchCommodity(){
	var rootCattegory = $("#rootCattegory").val();
	var secondCategory = $("#secondCategory").val();
	var threeCategory = $("#threeCategory").val();
	
	var commodityIndex = $("#commodityIndex").val();
	
	var structName = "";
	if(threeCategory!="0"){
		structName=threeCategory;
	}else if(secondCategory!="0"){
		structName=secondCategory;
	}else if(rootCattegory!="0"){
		structName=rootCattegory;
	}else{
		structName="";
	}
	//location.href=path;
	location.href=path+"/yitiansystem/commoditymgmt/bizmgmt/shopcommodity/findCommodityBySearch.sc?structName="+
	structName+"&commodityIndex="+commodityIndex;
}