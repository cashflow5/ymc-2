//验证名称的结构标志
var validateFlag=true;
var config = {
	form : "submitForm",
	submit : submitForms,
	fields : [{
		name : 'contact',
		allownull : false,
		regExp : "name",
		defaultMsg : '请输入联系人姓名',
		focusMsg : '请输入联系人姓名',
		errorMsg : '请输入字符',
		rightMsg : '联系人姓名输入正确',
		msgTip : 'contactTip'
	},{
		name : 'type',
		allownull : false,
		regExp : "num",
		defaultMsg : '请选择业务类型',
		focusMsg : '请选择业务类型',
		errorMsg : '请选择业务类型',
		rightMsg : '业务类型输入正确',
		msgTip : 'typeTip'
	},{
		name : 'telePhone',
		allownull : false,
		regExp : "phone",
		defaultMsg : '请输入联系人电话',
		focusMsg : '请输入联系人电话',
		errorMsg : '区号加7到8位号码,中间以"-"间隔',
		rightMsg : '联系人电话输入正确',
		msgTip : 'telePhoneTip'
	},{
		name : 'mobilePhone',
		allownull : false,
		regExp : "phonecall",
		defaultMsg : '请输入联系人手机',
		focusMsg : '请输入联系人手机',
		errorMsg : '请输入11位手机号码',
		rightMsg : '联系人手机输入正确',
		msgTip : 'mobilePhoneTip'
	},{
		name : 'email',
		allownull : false,
		regExp : "email",
		defaultMsg : '请输入Email',
		focusMsg : '请输入Email',
		errorMsg : '格式错误，重新输入',
		rightMsg : 'Email输入正确',
		msgTip : 'emailTip'
	},{
		name : 'fax',
		allownull : false,
		regExp : "phone",
		defaultMsg : '请输入传真',
		focusMsg : '请输入传真',
		errorMsg : '区号加7到8位号码,中间以"-"间隔',
		rightMsg : '传真输入正确',
		msgTip : 'faxTip'
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