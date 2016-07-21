var reg = new Reg();
var config={
	form:"form1",
	submit:function(){
		
	},fields:[
		{
			name:'username',
			allownull:false,
			regExp:/^\w{4,20}|\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
			defaultMsg:'请输入用户名或邮箱地址',
			focusMsg:'',
			errorMsg:'用户名为4-20位字符\邮箱格式:admin@yitian.com',
			rightMsg:'',
			msgTip:'username_tip'
		},{
			name:'password',
			allownull:false,
			regExp:/^\w{6,20}$/,
			defaultMsg:'密码可使用字母+数字或符号的组合长度6-20个字符',
			focusMsg:'',
			errorMsg:'密码应为6-20个字符',
			rightMsg:'',
			msgTip:'password_tip'
		}
	]
};
Tool.onReady(function(){
	var f = new Fw(config);
	f.register();
});