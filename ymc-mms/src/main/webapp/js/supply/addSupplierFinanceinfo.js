//验证名称的结构标志
var validateFlag=true;
var config = {
	form : "submitForm",
	submit : submitForms,
	fields : [{
		name : 'bank',
		allownull : false,
		regExp : "name",
		defaultMsg : '请输入开户行',
		focusMsg : '请输入开户行',
		errorMsg : '请输入字符',
		rightMsg : '开户行输入正确',
		msgTip : 'bankTip'
	},{
		name : 'subBank',
		allownull : false,
		regExp : "name",
		defaultMsg : '请输入支行名称',
		focusMsg : '请输入支行名称',
		errorMsg : '请输入字符',
		rightMsg : '支行名称输入正确',
		msgTip : 'subBankTip'
	},{
		name : 'creator',
		allownull : false,
		regExp :/^\S+$/,
		defaultMsg : '请输入开户人',
		focusMsg : '请输入开户人',
		errorMsg : '请输入字符',
		rightMsg : '开户人输入正确',
		msgTip : 'creatorTip'
	},{
		name : 'account',
		allownull : false,
		regExp : "integezero",
		defaultMsg : '请输入账号',
		focusMsg : '请输入账号',
		errorMsg : '请输入数字',
		rightMsg : '账号输入正确',
		msgTip : 'accountTip'
	},{
		name : 'dutyCode',
		allownull : false,
		regExp : "/^\S+$/",
		defaultMsg : '请输入税号',
		focusMsg : '请输入税号',
		errorMsg : '请输入数字',
		rightMsg : '税号输入正确',
		msgTip : 'dutyCodeTip'
	},{
		name : 'conTime',
		allownull : false,
		regExp : "intege1",
		defaultMsg : '请输入合作期限',
		focusMsg : '请输入合作期限',
		errorMsg : '请输入非负数',
		rightMsg : '合作期限输入正确',
		msgTip : 'conTimeTip'
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