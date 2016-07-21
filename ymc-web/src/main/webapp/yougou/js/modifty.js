$(function(){
	$('.btn-sms,.btn-sms-sms').click(function(event) {
		var $this=$(this);
		if(!$this.hasClass('disable')){
			if(checkphone($("#tempVar").length)){
				sendMsg(this);
			}
		}
	});
	if(flag){
		if((!mobilePhone) && (!email)){
			ygdg.dialog.alert("请先绑定手机或邮箱!",function(){
				location.href = basePath+"/merchants/security/accountSecurity.sc";
			});
		}else{
			changeway($("#verifywaySelect"));
		}
	}
});

//发送短信中...
function sendMsg(btnObj) {
	$.post(basePath+"/merchants/security/getMobileCode.sc",
			{
				mobileCode:$.trim($("#mobileCode").val())
			},
			function(text){
			if(text=="1"){
				//这里异步发送短信，如果成功做如下操作
			    var t = 60;
			    $('.code-tip').html("验证码已发出，请注意查收短信，如果没有收到，你可以在<font color='red'>"+t + "</font>秒要求系统重新发送");
			    $('.sms-text').removeAttr('disabled');
				//$('.code-tip').removeClass('hide');
				$(btnObj).addClass('disable');
			    function handleTimeout() {
			        t--;
			        $('.code-tip').html("验证码已发出，请注意查收短信，如果没有收到，你可以在<font color='red'>"+t + "</font>秒要求系统重新发送");
			        if (t <= 1) {
			            $(btnObj).removeClass("disable");
			            $('.code-tip').html("重新获取短信验证码");
			            } else {
			            setTimeout(handleTimeout, 1000);
			        }
			    }
			    setTimeout(handleTimeout, 1000);
			}else if(text=="-1" || text=="-2"){
				$('.code-tip').html("<span class='cred'>今日发送短信已达上限，请明日再试！</span>");
			}else if(text=="0"){
				$('.code-tip').html("<span class='cred'>手机号码格式不正确，请修改再试！</span>");
			}else{
				$('.code-tip').html("<span class='cred'>短信验证码发送失败，请稍后重试！</span>");
			}
			$('.code-tip').removeClass('hide');
	});
}

function checkphone(variable){
	$(".code-tip").html("");
	if(variable){
		return true;
	}
	var tel = $.trim($("#mobileCode").val()); //获取手机号
	if(tel==""){
		$(".code-tip").html("<span class='cred'>手机号码不能为空！</span>");
		$('.code-tip').removeClass('hide');
		return false;
	}else{
		var telReg = !!tel.match(/^(0|86|17951)?(13[0-9]|15[012356789]|17[05678]|18[0-9]|14[57])[0-9]{8}$/);
		//如果手机号码不能通过验证
		if(telReg==false){
			$(".code-tip").html("<span class='cred'>手机号码格式错误！</span>");
			$('.code-tip').removeClass('hide');
			return false;
		}
		return true;
	}
}

function bandingMobile(){
	if(checkphone()){
		var verifyCode = $.trim($("#verifyCode").val()); //获取验证码
		if(checkVerifyCode(verifyCode)){
			$.post(basePath+"/merchants/security/bandingMobile.sc",{
				mobileCode:$.trim($("#mobileCode").val()),
				verifyCode:verifyCode
			},function(text){
				$(".code-tip").children(".cred").html("");
				if(text=="1"){
					if($(".step-list").length>0){
						$(".step-list").eq(1).addClass("histoy").removeClass("curr");
						$(".step-list").eq(2).addClass("curr");
					}
					ygdg.dialog.alert("绑定手机成功！",function(){
						//跳转到账号管理页面
						location.href = basePath+"/merchants/security/accountSecurity.sc";
					});
				}else if(text=="-1"){
					ygdg.dialog.alert("验证码已失效，请重新获取短信验证码！");
				}else if(text=="0"){
					ygdg.dialog.alert("验证码不正确，请输入正确的短信验证码！");
//				}else if(text=="-4"){
//					ygdg.dialog.alert("该手机号码已绑定账号，请更换其他号码进行绑定！");
				}else{
					ygdg.dialog.alert("绑定手机失败，请稍后再试！");
				}
			});
		}
	}
}

function changeway(obj){
	if("mobile"==$(obj).val()){
		$(".mobile_code").show();
		$("a.medium").attr("onclick","nextStep('mobile','/merchants/security/sentupdatepwdsms.sc','','/merchants/security/updatepasswordByMobile.sc');");
		$("a.medium").html("提交");
	}else if("email"==$(obj).val()){
		$(".mobile_code").hide();
		$("a.medium").attr("onclick","nextStep('email','/merchants/security/sentupdatepwdemail.sc');");
		$("a.medium").html("发送验证邮件");
	}
}

function changeLevel(){
    var verify=document.getElementById('imgValidateCode');
	verify.setAttribute('src',basePath+'/servlet/imageValidate?dt='+Math.random());
  }

var updatePwd = function(mobile,email){
	if((!mobile) && (!email)){
		ygdg.dialog.alert("请先绑定手机或邮箱!");
	}else{
		location.href = basePath+"/merchants/security/updatePwd.sc";
	}
};

//basePath+'/merchants/security/sentupdatepwdemail.sc?param=' + new Date().getTime()
/**
 * way 选择哪种方式
 * url 提交url
 * email 修改邮箱时用到，发送到邮件email
 * targetUrl 成功之后跳转url
 */
var nextStep = function(way,url,email,targetUrl){
	var code = $.trim($("#code").val());
	if(code!=""){
		//验证图形验证码是否正确
		$.post(basePath+"/merchants/security/checkCode.sc",{
			code:code
		},function(text){
			if(text==1){
				dialog_submit=ygdg.dialog({
		        	id:'submitDialog',
		          content: '提交中...'
			    }).show();
				if("mobile"==way){
					var verifyCode = $.trim($("#verifyCode").val()); //获取验证码
					//手机验证码发送
					sentMsg("mobile",url,verifyCode,targetUrl);
				}else if("email"==way){
					//邮箱验证，发送邮件
					sentMsg("email",url,email);
				}else{
					submitHandler();
				}
			}else{
				$(".code-tip").html("<span class='cred'>图形验证码不正确！</span>");
				$('.code-tip').removeClass('hide');
			}
		});
	}else{
		$(".code-tip").html("<span class='cred'>图形验证码不能为空！</span>");
		$('.code-tip').removeClass('hide');
	}
};

/**
 * @param type 类型
 * @param url 提交url
 * @param target 可变参数
 * @param targetUrl 成功之后跳转url
 */
function sentMsg(type,url,target,targetUrl){
	var code = $.trim($("#code").val());
	if("mobile"==type){
		if(checkVerifyCode(target)){
			$.ajax({
				  type: 'post',
				  url: url,
				  dataType: 'json',
				  data: 'code='+code+'&verifyCode='+target+'&t='+new Date().getTime(),
				  success: function(data, textStatus) {
					  $(".code-tip").html("");
					  dialog_submit.close();
					  if(data){
						  if(data.success){
							  //跳转到下一步
							  //location.href = targetUrl;
							  refreshpage(targetUrl);
						  }else{
							  ygdg.dialog({
						        	id:'submitDialog',
							          content: "<font class='cred'>"+data.message+"</font>",
							          icon:'face-sad',
							          title:'验证码校验失败'
								    });
						  }
					  }else{
						  ygdg.dialog({
					        	id:'submitDialog',
						          content: "<font class='cred'>短信验证码发送失败，请稍后重试！</font>",
						          icon:'face-sad',
						          title:'验证码发送失败'
							    });
					  }
					  changeLevel();
				  },
				  error: function(XMLHttpRequest, textStatus, errorThrown) {
						dialog_submit.close();
					    ygdg.dialog.alert(XMLHttpRequest.responseText);
					    changeLevel();
				  }
			});
		}else{
			dialog_submit.close();
		}
	}else{
		$.ajax({
			  type: 'post',
			  url: url,
			  dataType: 'json',
			  data: 'code='+code+'&email='+target+'&t='+new Date().getTime(),
			  success: function(data, textStatus) {
				  $(".code-tip").html("");
				  dialog_submit.close();
				  if(data){
					  if(data.success){
						  ygdg.dialog({
					        	id:'submitDialog',
						          content: "<font style='color:green;'>"+data.message+"</font>",
						          icon:'succeed',
						          title:'邮件发送成功'
							    });
					  }else{
						  ygdg.dialog({
					        	id:'submitDialog',
						          content: "<font class='cred'>"+data.message+"</font>",
						          icon:'face-sad',
						          title:'邮件发送失败'
							    });
					  }
				  }else{
					  ygdg.dialog({
				        	id:'submitDialog',
					          content: "<font class='cred'>邮件发送失败，请稍后重试！</font>",
					          icon:'face-sad',
					          title:'邮件发送失败'
						    });
				  }
				  changeLevel();
			  },
			  error: function(XMLHttpRequest, textStatus, errorThrown) {
				dialog_submit.close();
			    ygdg.dialog.alert(XMLHttpRequest.responseText);
			    changeLevel();
			  }
		});
	}
} 

var bandingEmail = function(mobile){
	if(mobile){
		location.href = basePath+"/merchants/security/bandingemail.sc";
	}else{
		ygdg.dialog.alert("请先绑定手机!");
	}
};

var checkVerifyCode = function(verifyCode){
	$(".code-tip").html("");
	if(verifyCode==""){
		$(".code-tip").html("<span class='cred'>短信验证码不能为空！</span>");
		$('.code-tip').removeClass('hide');
		return false;
	}
	return true;
};

/**
 * submitUrl:第一步提交到的url
 * successUrl:第一步成功之后，跳转到的url
 */
var nextUpdate = function(submitUrl,successUrl){
	var verifyCode = $.trim($("#verifyCode").val()); //获取验证码
	if(checkVerifyCode(verifyCode)){
		$.post(submitUrl,{
			verifyCode:verifyCode
		},function(text){
			$(".code-tip").html("");
			if(text=="1"){
				//跳转到第二步
				location.href = successUrl;
			}else if(text=="-1"){
				ygdg.dialog.alert("验证码已失效，请重新获取短信验证码！");
			}else if(text=="0"){
				ygdg.dialog.alert("验证码不正确，请输入正确的短信验证码！");
			}else{
				ygdg.dialog.alert("验证身份失败，请稍后再试！");
			}
		});
	}
};

var checkEmail = function(email){
	var reg = /^([a-zA-Z0-9._-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
	console.info(!(reg.test(email)));
	if(email==""){
		$(".code-tip").html("<span class='cred'>邮箱不能为空！</span>");
		$('.code-tip').removeClass('hide');
		return false;
	}else if(!(reg.test(email))){
		$(".code-tip").html("<span class='cred'>邮箱格式不正确！</span>");
		$('.code-tip').removeClass('hide');
		return false;
	}
	$(".code-tip").html("");
	return true;
};

var nextUpdateEmailStep = function(){
	var email = $.trim($("#email").val());
	if(checkEmail(email)){
		nextStep("email",basePath+'/merchants/security/sendUpdateEmail.sc',$.trim($("#email").val()));
	}
};

var checkPassword = function(password1,password2){
	if(password1.length>=6 && password1.length<=20){
		var reg = /^[(\~)(\!)(\@)(\#)(\$)(\%)(\^)(\&)(\*)(\()(\))(\-)(\_)(\+)(\=)(\[)(\])(\{)(\})(\|)(\\)(\;)(\:)(\')(\")(\,)(\.)(\/)(\<)(\>)(\?)(\)]+$/;
		if(/^\d+$/.test(password1)){
			//全数字
			$('.code-tip').html("<span class='cred'>密码不能为纯数字！</span>");
			$('.code-tip').removeClass('hide');
			return false;
		}else if(/^[A-Za-z]+$/.test(password1)){
			//全字母
			$('.code-tip').html("<span class='cred'>密码不能为纯字母！</span>");
			$('.code-tip').removeClass('hide');
			return false;
		}else if(reg.test(password1)){
			//全是字符
			$('.code-tip').html("<span class='cred'>密码不能为纯符号！</span>");
			$('.code-tip').removeClass('hide');
			return false;
		}else if(password1!=password2){
			$('.code-tip').html("<span class='cred'>新密码与确认密码不相同！</span>");
			$('.code-tip').removeClass('hide');
			return false;
		}else if(/[(\ )]+/.test(password1)){
			$(".code-tip").html("<span class='cred'>密码不能含有空格！</span>");
			$('.code-tip').removeClass('hide');
			return false;
		}
		$('.code-tip').html("");
		$('.code-tip').addClass('hide');
		return true;
	}else{
		$('.code-tip').html("<span class='cred'>请输入6-20位密码！</span>");
		$('.code-tip').removeClass('hide');
		return false;
	}
};

var submitHandler = function(){
	var code = $.trim($("#code").val());
	$.post(basePath+"/merchants/security/doUpdatePassword.sc",{
		password1:$.trim($("#password1").val()),
		password2:$.trim($("#password2").val()),
		code:code,
	},function(text){
		dialog_submit.close();
		if(text=="1"){
			if($(".step-list").length>0){
				$(".step-list").eq(1).addClass("histoy").removeClass("curr");
				$(".step-list").eq(2).addClass("curr");
			}
			ygdg.dialog.alert("密码修改成功，请重新登录！",function(){
				//跳转到登录页面
				location.href = basePath+"/merchants/login/to_Back.sc";
			});
		}else if(text=="-1"){
			ygdg.dialog.alert("密码格式错误！");
		}else if(text=="0"){
			ygdg.dialog.alert("图形验证码错误！");
		}else{
			ygdg.dialog.alert("密码修改失败！请稍后再试");
		}
	});
};

var updatePassword = function(){
	//密码是否符合规则
	if(checkPassword($("#password1").val(),$("#password2").val())){
		nextStep("updatePwd");
	}
};
