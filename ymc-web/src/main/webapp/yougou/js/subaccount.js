
$(function(){
    $('.password-text').keyup(function(event) {
        PwdStrengthValidate(this);
    });
  //限制手机号码只能为输入数字
    $("#mobileCode").keydown(function(event){
    	var code = event.which;
    	if(code==10||code==8){
    	    return true;
    	}else if(code >47 && code < 58) {
    		return true;
    	}else if(code >95 && code < 106){
    		return true;
    	}else{
    	   return false;
    	}
    	return true;
    });
});

//密码强度验证函数
function PwdStrengthValidate(obj) {
    var that = $(obj);
    var val = that.val(),
        li = $(".safetyStrength li");
    //特殊字符
    var reg = /[(\ )(\~)(\!)(\@)(\#)(\$)(\%)(\^)(\&)(\*)(\()(\))(\-)(\_)(\+)(\=)(\[)(\])(\{)(\})(\|)(\\)(\;)(\:)(\')(\")(\,)(\.)(\/)(\<)(\>)(\?)(\)]+/;
    var numberFlag = false;
    var letterFlag = false;
    var otherFlag = false;
    if(/[0-9]+/.test(val)){
    	numberFlag = true;
    }
    if(/[A-Za-z]+/.test(val)){
    	letterFlag = true;
    }
    if(reg.test(val)){
    	otherFlag = true;
    }
    if(val.length >= 6 && val.length<=20){
    	if (numberFlag && letterFlag && otherFlag) {
            li.attr("class", "").eq(2).addClass("pwdTall");
            li.eq(3).addClass('text').html('高');
        }else if ((numberFlag && !letterFlag && otherFlag) || 
    			(numberFlag && letterFlag && !otherFlag) || 
    			(!numberFlag && letterFlag && otherFlag)) {
            li.attr("class", "").eq(1).addClass("pwdMid");
            li.eq(3).addClass('text').html('中');
        }else{
        	li.attr("class", "").eq(0).addClass("pwdLow");
            li.eq(3).addClass('text').html('低');
        }
    }else{
    	li.attr("class", "").eq(0).addClass("pwdLow");
        li.eq(3).addClass('text').html('低');
    }
}


  function validateMerchants(flag){
	  $(".code-tip").html("");
	  $(".code-tip").removeClass("hide");
     var mobileCode = $("#mobileCode").val();//手机号码
     //判断密码长度必须是在6-20之间
     var loginPassword=$("#password").val();//密码
     var loginPasswordTwo=$("#loginPasswordTwo").val();//确认密码
     if(!flag){
    	 var loginAccount=$("#loginName").val();//姓名
    	 if(loginAccount==""){
    	        $(".code-tip").html("<span class='cred'>帐号不能为空!</span>");
    	        $("#loginName").focus();
    	        return false;
    	     }
    	     var reg = /[\W]/g;
    		 if(reg.exec(loginAccount)){
    			$(".code-tip").html("<span class='cred'>登录账号只能包含数字字母!</span>");
    		  	$("#loginName").focus();
    		  	return false;
    		 }
    	 if(loginPassword==""){
    	    	$(".code-tip").html("<span class='cred'>密码不能为空!</span>");
    	    	$("#password").focus();
    	        return false;
    	  }
    	 if(loginPassword.length < 6 || loginPassword.length > 20){
    	    	$(".code-tip").html("<span class='cred'>密码长度必须大于等于6和小于等于20!</span>");
    	    	$("#password").focus();
    	        return false;
    	     }
    	     if(loginPasswordTwo==""){
    	    	$(".code-tip").html("<span class='cred'>确认密码不能为空!</span>");
    	    	$("#loginPasswordTwo").focus();
    	        return false;
    	     }
    	     if(loginPasswordTwo!=loginPassword){
    	    	$(".code-tip").html("<span class='cred'>2次密码输入不一致,请重新输入!!</span>");
    	    	$("#loginPasswordTwo").focus();
    	        return false;
    	     }
     }else{
    	 if(loginPassword!="" || loginPasswordTwo!=""){
    		 if(loginPassword.length < 6 || loginPassword.length > 20){
    	 	    	$(".code-tip").html("<span class='cred'>密码长度必须大于等于6和小于等于20!</span>");
    	 	    	$("#password").focus();
    	 	        return false;
    	 	     }
    		 if(loginPasswordTwo!=loginPassword){
    	 	    	$(".code-tip").html("<span class='cred'>2次密码输入不一致,请重新输入!!</span>");
    	 	    	$("#loginPasswordTwo").focus();
    	 	        return false;
    	 	     }
    	 }
     }
     
     var telReg = !!mobileCode.match(/^(0|86|17951)?(13[0-9]|15[012356789]|17[05678]|18[0-9]|14[57])[0-9]{8}$/);
     //如果手机号码不能通过验证
     if(mobileCode==""){
 		$(".code-tip").html("<span class='cred'>手机号码不能为空！</span>");
 		$("#mobileCode").focus();
 		return false;
 	}else{
		if(telReg==false){
			$(".code-tip").html("<span class='cred'>手机号码格式错误！</span>");
			$("#mobileCode").focus();
			return false;
		}
 	}
     return true;
  }

  //添加帐号信息
  function addMerchants(){
	  var loginAccount=$("#loginName").val();
	  if(validateMerchants(false)){
		  //判断用户名是否存在
			$.ajax({ 
				type: "post", 
				url: basePath+"/merchants/login/exitsLoginAccount.sc?loginAccount=" + loginAccount, 
				success: function(dt){
					if("sucuess"==dt){
						$(".code-tip").html("<span class='cred'>帐号已经存在,请重新输入!</span>");
						$("#loginName").focus();
					   return false;
					}else{
					   //提交表单数据
					   $("#queryForm").submit();
					}
				} 
			});
	  }
  }
  
  
  function updateMerchants(){
	  //修改，密码、确认密码、账号名  为空不需要验证，不为空还是需要判断
	  if(validateMerchants(true)){
		  $("#queryForm").submit();
	  }
  }
	  
