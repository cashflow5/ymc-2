//验证名称的结构标志
var validateFlag=true;
var config = {
	form : "submitForm",
	submit : submitForms,
	fields : [{
		name : 'purchaseType',
		allownull : false,
		regExp : "intege",
		defaultMsg : '请输入采购类型',
		focusMsg : '请输入采购类型',
		errorMsg : '请输入采购类型',
		rightMsg : '采购类型输入正确',
		msgTip : 'purchaseTypeTip'
	},{
		name : 'basepurchaseQuantity',
		allownull : false,
		regExp : "intege",
		defaultMsg : '请输入起购数量',
		focusMsg : '请输入起购数量',
		errorMsg : '请输入起购数量',
		rightMsg : '起购数量输入正确',
		msgTip : 'basepurchaseQuantityTip'
	},{
		name : 'actualPrice',
		allownull : false,
		regExp : "decmal1",
		defaultMsg : '请输入单价',
		focusMsg : '请输入单价',
		errorMsg : '请输入数字格式',
		rightMsg : '单价输入正确',
		msgTip : 'actualPriceTip'
	},{
		name : 'round',
		allownull : false,
		regExp : "intege",
		defaultMsg : '请输入到货周期',
		focusMsg : '请输入到货周期',
		errorMsg : '请输入数字格式',
		rightMsg : '到货周期输入正确',
		msgTip : 'roundTip'
	}]
}

Tool.onReady( function() {
	var f = new Fw(config);
	f.register();
});

/**
 * 提交表单
 */
function submitForms() {
	result=true;
	if(result){		
		return result;		
	}
	return false;

}