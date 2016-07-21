//验证名称的结构标志
var validateFlag=true;
var config = {
	form : "submitForm",
	submit : submitForms,
	fields : [{
		name : 'loginAccount',
		allownull : false,
		regExp : "username",
		defaultMsg : '请输入供应商登录名',
		focusMsg : '请输入供应商登录名',
		errorMsg : '请输入 6-12位数字或字母',
		rightMsg : '供应商登录名输入正确',
		msgTip : 'loginAccountTip'
	},{
		name : 'loginPassword',
		allownull : false,
		regExp : "letter_ln",
		defaultMsg : '请输入供应商登录密码',
		focusMsg : '请输入供应商登录密码',
		errorMsg : '请输入数字和字母组合',
		rightMsg : '供应商登录密码输入正确',
		msgTip : 'loginPasswordTip'
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

function addClass(spanId,cs) {
    var name = $("#"+spanId);
    name.removeClass("oncorrect").removeClass("onshow").removeClass("onerror");
    return name.addClass(cs);
}

function checkHasSupplierLogin(){
	var value = $("#loginAccount").val();	
	var regex = /^\S+$/;
	//alert(value);
	if(""==value){
		addClass("loginAccountTip","onerror").html("登录名不能为空");
		boo=false;
		return false;
	}
	if (!regex.exec(value)) {
		addClass("loginAccountTip","onerror").html("登录名不能为空");
		boo = false;
		return false;
	}
	
	var data__ = {"loginAccount":value};
  	
	$.ajax({
  		type : "POST",
  		url : "checkSupplierLogin.sc",  		
  		data :data__ ,  		
  		success:function(data) {
  			if(data == "fail"){  				
  				addClass("loginAccountTip","onerror").html("该名称已经存在");
   				boo = false;
   				return false;
  			}else{
  				addClass("loginAccountTip","oncorrect").html("该名称可以使用");
   				boo = true;
   				return true;
  			}
  		}
  	});
}