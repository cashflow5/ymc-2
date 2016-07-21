/*
 * 检验商品名称的可用行
 */
function checkProductName(){
//	var commodityName = $("#commodityName").val();
//	$.ajax({
//        type: "POST",
//        url: path+"/yitiansystem/commoditymgmt/commodityinfo/productpricemanager/checkProductName.sc",
//        data: {
//			"commodityName":commodityName
//		},
//        dataType:"json", 
//        success: function(data){
//        	if(data[0].length == 0){
//        		$('#editProductPriceManagerFrom').attr('action',path + '/yitiansystem/commoditymgmt/commodityinfo/productpricemanager/u_productPriceManager.sc');
//        	}else{
//        		alert("该属性项名称已经存在，请重新输入");
//        	}
//        }
//      });
	$('#editProductPriceManagerFrom').attr('action',path + '/yitiansystem/commoditymgmt/commodityinfo/productpricemanager/u_productPriceManager.sc');
	//alert($('#editProductPriceManagerFrom').attr('action'));
	$('#editProductPriceManagerFrom').submit();
}
 
var path;
$(function(){
	path = $('#basepath').val();
})

var config={
	form:"editProductPriceManagerFrom",submit:submitForm,
 	fields:[
		{name:'costPrice',allownull:true,regExp:"decmal4",rightMsg:'输入格式正确',errorMsg:'格式不正确',msgTip:'costPriceTip'},
		{name:'publicPrice',allownull:true,regExp:"decmal4",rightMsg:'输入格式正确',errorMsg:'格式不正确',msgTip:'publicPriceTip'},
		{name:'salePrice',allownull:true,regExp:"decmal4",rightMsg:'输入格式正确',errorMsg:'格式正整数',msgTip:'salePriceTip'}
	]
}
  
Tool.onReady(function(){
	var f = new Fw(config);
	f.register();
});

 
function submitForm(){
	return true;
}