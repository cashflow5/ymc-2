var flag = true;
function submitvalidateform() {
    return flag;
}

var config = {
    form : "form1",
    submit : submitvalidateform,
    fields : [ {
	name : 'staffName',
	allownull : false,
	regExp : /^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]{2,30}$/,
	defaultMsg : '',
	focusMsg : '请输入人员名称',
	errorMsg : '人员名称只能有2-30个汉字、数字、字母、下划线，不能以下划线开头和结尾',
	rightMsg : '人员名称输入合法',
	msgTip : 'staffNametip'
    }, {
	name : 'telPhone',
	allownull : false,
	regExp : /^(\d{3}-?\d{7,8}|\d{4}-?\d{7,8}|\d{7,8})$/,
	defaultMsg : '',
	focusMsg : '请输入电话号码',
	errorMsg : '电话号码输入错误 例：0755-88888888',
	rightMsg : '电话号码输入合法',
	msgTip : 'telPhonetip'
    }, {
	name : 'machinePhone',
	allownull : false,
	regExp : /^1[3|4|5|8][0-9]\d{8}$/,
	defaultMsg : '',
	focusMsg : '请输入手机号码',
	errorMsg : '手机号码输入错误 例：13700000000',
	rightMsg : '手机号码输入合法',
	msgTip : 'machinePhonetip'
    }, {
	name : 'email',
	allownull : false,
	regExp : /^(\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*)$/,
	defaultMsg : '',
	focusMsg : '请输入邮件地址',
	errorMsg : '邮件地址输入错误 例：ss@163.com',
	rightMsg : '邮件地址输入合法',
	msgTip : 'emailtip'
    }, {
	name : 'qq',
	allownull : true,
	regExp : /^[1-9][0-9]{4,}$/,
	defaultMsg : '',
	focusMsg : '选填',
	errorMsg : 'QQ输入错误',
	rightMsg : 'QQ输入合法',
	msgTip : 'qqtip'
    }, {
	name : 'msn',
	allownull : true,
	regExp : /^(\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*)$/,
	defaultMsg : '',
	focusMsg : '选填',
	errorMsg : 'MSN输入错误',
	rightMsg : 'MSN输入合法',
	msgTip : 'msntip'
    } ]
}
Tool.onReady( function() {
    var f = new Fw(config);
    f.register();
});

function addRemarktClass(cs) {
    var name = $("#remarktip");
    name.removeClass("oncorrect").removeClass("onshow").removeClass("onerror");
    return name.addClass(cs);

}
//验证描述内容是否超长
function checkRemark()
{
  	var remarkText=document.getElementById("remark").value;
	if (remarkText.length>250) {
	  	addRemarktClass("onerror").html("备注内容只能输入250个字符");
        flag = false;
	}
	else{
		addRemarktClass("oncorrect").html("备注内容输入正确");
		 flag =true;
		}	
   return false;		
 /*   if (remarkText.length>250) {
	   alert("备注内容共["+remarkText.length+"]个字符只能输入250个字符");
	$("#submitbutton").attr("disabled","disabled");
	}
	else{
		$("#submitbutton").attr("disabled","");
		}*/
}		
