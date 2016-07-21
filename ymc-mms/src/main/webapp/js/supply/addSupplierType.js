//验证名称的结构标志
var validateFlag=true;
var config = {
	form : "submitForm",
	submit : submitForms,
	fields : [{
		name : 'typeValue',
		allownull : false,
		regExp : "chinese",
		defaultMsg : '请选择供应商类型',
		focusMsg : '请选择供应商类型',
		errorMsg : '仅中文',
		rightMsg : '供应商类型输入正确',
		msgTip : 'typeValueTip'
	}]
}

Tool.onReady( function() {
	var f = new Fv(config);
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