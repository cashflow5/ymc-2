<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-用户登录</title>
<link rel="stylesheet" href="${BasePath}/yougou/css/base.css?v=${style_v!''}" />
<link rel="stylesheet" href="${BasePath}/yougou/css/login.css?v=${style_v!''}" />
<style>
	.login_form .check-code-error{background:url(${BasePath}/yougou/images/ico-error.gif) 125px 17px no-repeat}
	.login_form .check-code-success{background:url(${BasePath}/yougou/images/icon-right.gif) 125px 17px no-repeat}
</style>
<script type="text/javascript" src="${BasePath}/yougou/js/ymc.common.js?v=${style_v!''}"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
<script language="javascript" type="text/javascript">
	//回车提交表单
	document.onkeydown = function(e){
	    if(!e) e = window.event;//火狐中是 window.event
	    if((e.keyCode || e.which) == 13){
	        document.getElementById("SubmitBtn").click();
	    }
	} 
  function changeLevel(){
    var verify=document.getElementById('imgValidateCode');
	verify.setAttribute('src','${BasePath}/servlet/imageValidate?dt='+Math.random());
  }
</script>
</head>

<body>
<div class="w850" style="padding-top:50px"><img src="${BasePath}/yougou/images/logo.jpg" width="285" height="47" alt="" /></div>
<div class="login mt20">
	<div class="w925 login_bd clearfix">
        <div class="login_box fr" id="login_div">
        	<form method="post" action="${BasePath}/merchants/login/merchantsLogin.sc" id="loginForm">
        	<ul class="login_form jsLoginForm">
            	<li>
                	<div class="form_ipt user_ipt">
 						<input class="ipt_txt" type="text" id="loginName" name="loginName" value="" placeholder="账号"/>
                    </div>
                    <div class="validator_msg"></div>
                </li>
            	<li>
                	<div class="form_ipt pwd_ipt">
						<input class="ipt_txt" type="password" id="pwd" name="pwd" value="" placeholder="密码"/>
                    </div>
                    <div class="validator_msg"></div>
                </li>
                <li class="rel">
                	<div class="form_ipt code_ipt">
						<input class="ipt_txt" type="text" id="code" name="code" value="" placeholder="验证码" maxlength="4"/>
                    </div>
                    <div class="code_img">
                    	<span><img id="imgValidateCode" class="imgValidateCode" src="${BasePath}/servlet/imageValidate" alt="验证码" title="验证码不区分大小写"/></span>
                    	<span class="ml5">看不清?<br /><a class="cblue" href="javascript:;" onclick="changeLevel();return false;">换一张</a></span>
                    </div>
                    <div class="validator_msg"></div>
                </li>
                <li><input class="smt_brwn jsLoginSmt" name="SubmitBtn" id="SubmitBtn" type="button" value="" /></li>
                <li class="clearfix mt15">
                	<div class="rem_box fl rel jsRemBox"><b class="ico_chkbox"></b><input class="ipt_chkbox" id="remAccnt" name="" type="checkbox" value=""><label for="remAccnt">记住账号</label></div>
                    <div class="clr_hstry fr"><a href="${BasePath}/merchants/login/findpassword.sc">忘记密码?</a></div>
                </li>
            </ul>
            </form>
        </div>
    </div>
</div>
<div class="wrap footer" style="padding-top: 8px;">Copyright © 2011-2014 Yougou Technology Co., Ltd. 粤ICP备09070608号-4 增值电信业务经营许可证：粤 B2-20090203</div>
<script language="javascript" type="text/javascript" src="${BasePath}/yougou/js/placeholder.js?v=${style_v!''}"></script>
<script language="javascript" type="text/javascript" src="${BasePath}/yougou/js/login.js?v=${style_v!''}"></script>
<script language="javascript" type="text/javascript">
$(document).ready(function(){
var loginName=getCookie("loginName");
if(loginName!=""){
  input=top.document.getElementsByName("loginName");
  input[0].value=loginName;
}
	// 表单验证
	$("#SubmitBtn").click(function(){
		ymc_common.loadingInWindow("show", "正在登录中，请稍后...","login_div");
		$(this).addClass("disabled");
		$(this).attr("disabled",true);
		var user=$("#loginName");
		var pwd=$("#pwd");
		var code=$("#code");
		var userVal=user.val().replace(/^\s+|\s+$/g,"");
		var pwdVal=pwd.val().replace(/^\s+|\s+$/g,"");
		var codeVal=code.val().replace(/^\s+|\s+$/g,"");
		var userValidator=user.closest(".form_ipt").siblings(".validator_msg");
		var pwdValidator=pwd.closest(".form_ipt").siblings(".validator_msg");
		var codeValidator=code.closest(".form_ipt").siblings(".validator_msg");
		userValidator.removeClass("validator_error").html("");
		pwdValidator.removeClass("validator_error").html("");
		codeValidator.removeClass("validator_error").html("");
		if(userVal==""){
			userValidator.addClass("validator_error").html("请输入的用户名！");
			user.focus();
			$(this).removeClass("disabled");
			$(this).removeAttr("disabled");
			ymc_common.loadingInWindow();
			return false;
		}
		if(pwdVal==""){
			pwdValidator.addClass("validator_error").html("请输入的密码！");
			pwd.focus();
			$(this).removeClass("disabled");
			$(this).removeAttr("disabled");
			ymc_common.loadingInWindow();
			return false;
		}
		if(codeVal==""){
			codeValidator.addClass("validator_error").html("请输入的验证码！");
			code.focus();
			$(this).removeClass("disabled");
			$(this).removeAttr("disabled");
			ymc_common.loadingInWindow();
			return false;
		}
		ajaxSubmit(user,pwd,code,userVal,pwdVal,codeVal,userValidator,pwdValidator,codeValidator);
	});
	setCookie("mobilephone",null,null);
	setCookie("pwdpower",null,null);
});

function ajaxSubmit(user,pwd,code,userVal,pwdVal,codeVal,userValidator,pwdValidator,codeValidator){
	$.post("${BasePath}/merchants/login/merchantsLogin.sc",
				{loginName:userVal,pwd:pwdVal,code:codeVal},function(json){
				var data = json.msg;
				if("sucuess"==data){
					location.href="${BasePath}/merchants/login/to_index.sc";
					if(document.getElementById("remAccnt").checked){
					   var username=$('#loginName').val();
	                   setCookie("loginName",username,15);
	                }
					//登录成功,跳转到首页
					$("#SubmitBtn").removeClass("disabled"); 
					$("#SubmitBtn").removeAttr("disabled");
				}else{
					$("#SubmitBtn").removeClass("disabled"); 
					$("#SubmitBtn").removeAttr("disabled");
					$(this).attr("disabled","true");
					ymc_common.loadingInWindow();
					if("loginNameOrPwdIsNull"==data){
						userValidator.addClass("validator_error").html("用户名或密码为空,请输入值！");
						user.focus();
					}else if("codeIsNull"==data){
						codeValidator.addClass("validator_error").html("验证码为空,请输入验证码！");
						code.focus();
						code.val("");
					}else if("codeIsExpire"==data){
						codeValidator.addClass("validator_error").html("验证码已过期,请刷新页面后输入验证码！");
						code.focus();
						code.val("");
					}else if("codeIsFault"==data) {
						codeValidator.addClass("validator_error").html("验证码校验不通过,请重新输入验证码！");
						code.focus();
						code.val("");
					}else if("loginNameIsNotExist"==data) {
						userValidator.addClass("validator_error").html("该用户名在系统中不存在,请检查后重新输入！");
						user.focus();
					}else if("pwdIsFault"==data){
						pwdValidator.addClass("validator_error").html("密码校验不通过,请重新输入密码！");
						pwd.focus();
					}else if("merchant_codeIsNull"==data){
						userValidator.addClass("validator_error").html("该用户名对应商家编码不存在,请联系管理员！");
						user.focus();
					}else if("is_valid_stop"==data){
						userValidator.addClass("validator_error").html("该用户名对应供应商信息已停用,请确认后登录！");
						user.focus();
					}else if("is_valid_new"==data){
						userValidator.addClass("validator_error").html("该用户名对应该商家资料没有审核,请审核后登录！");
						user.focus();
					}else if("isCongeal"==data){
						userValidator.addClass("validator_error").html("商家账号状态已锁定，请启用后登录！");
						user.focus();
					}else if("isDelete"==data){
						userValidator.addClass("validator_error").html("供应商信息已删除,请确认后登录！");
						user.focus();
					}else{
						userValidator.addClass("validator_error").html("登录异常,请稍后再试,还有问题请联系管理员！");
						user.focus();
					}
				 	var verify=document.getElementById('imgValidateCode');
					verify.setAttribute('src','${BasePath}/servlet/imageValidate?dt='+Math.random());
				}
			},"json");
}

function setCookie(c_name,value,expiredays){
  var exdate=new Date();
  exdate.setDate(exdate.getDate()+expiredays);
  document.cookie=c_name+ "=" +escape(value)+((expiredays==null) ? "" : ";expires="+exdate.toGMTString());
}

function getCookie(c_name){
  if (document.cookie.length>0){
    var c_start=document.cookie.indexOf(c_name + "=");
    if(c_start!=-1){ 
      c_start=c_start + c_name.length+1;
      var c_end=document.cookie.indexOf(";",c_start);
      if (c_end==-1){c_end=document.cookie.length;}
      return unescape(document.cookie.substring(c_start,c_end));
    } 
  }
return "";
}
</script>
</body>
</html>