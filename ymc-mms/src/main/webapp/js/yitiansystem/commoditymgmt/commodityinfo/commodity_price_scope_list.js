
/**
* 跳转到编辑页面
*/
function fn_toEditPriceScope(catId,catName,catStructName){
	$("#catId").val(catId);
	$("#catName").val(catName);
	$("#catStructName").val(catStructName);
	$("#priceScopeFrm").submit();
}


/**
* 跳转到编辑页面
*/
function fn_toEditPriceScopeByEntry(catId,catName,catStructName,id){
	$("#catId").val(catId);
	$("#catName").val(catName);
	$("#catStructName").val(catStructName);
	$("#priceScopeId").val(id);
	$("#priceScopeFrm").submit();
}

