//验证名称的结构标志
var validateFlag=true;
var config = {
	form : "submitForm",
	submit : submitForms,
	fields : [{
		name : 'supplier',
		allownull : false,
		regExp :/^\S+$/,
		defaultMsg : '请输入供应商名称',
		focusMsg : '请输入供应商名称',
		errorMsg : '请输入数字 字母  下划线 与中文',
		rightMsg : '供应商名称输入正确',
		msgTip : 'supplierTip'
	},{
		name : 'address',
		allownull : false,
		regExp : "/^\S+$/",
		defaultMsg : '请输入供应商地址',
		focusMsg : '请输入供应商地址',
		errorMsg : '请输入数字 字母  下划线 与中文',
		rightMsg : '供应商地址输入正确',
		msgTip : 'addressTip'
	},{
		name : 'supplierType',
		allownull : false,
		regExp : "/^\S+$/",
		defaultMsg : '请选择供应商类型',
		focusMsg : '请选择供应商类型',
		errorMsg : '请选择供应商类型',
		rightMsg : '供应商类型输入正确',
		msgTip : 'supplierTypeTip'
	},{
		name : 'isValid',
		allownull : false,
		regExp : "/^\S+$/",
		defaultMsg : '请选择供应商状态',
		focusMsg : '请选择供应商状态',
		errorMsg : '请选择供应商状态',
		rightMsg : '供应商状态输入正确',
		msgTip : 'isValidTip'
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