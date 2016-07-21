<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购科技有限公司B2C管理系统</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-index.css"/>
</head>
<body class="login_bg">
<table width="100%" align="center">
	<tbody>
		<tr>
			<td>
				<form action="login.sc" method="post"  name="loginForm" id="loginForm" class="loginForm" >
				<input type="hidden" name="PrivateFromSubmitKey"  value="${PrivateFromSubmitKey?default('')}" />
        	
        		<input type="hidden" name="sysMenuId" value="0" />
					<div class="login">
						<div class="img"><img src="${BasePath}/yougou/images/layout/logo.gif" width="251" height="102" /></div>
						<div class="txt">
							<table class="com_modi_table" width="338">
								<tr>
									<th  style="width:80px;">用户名：</th>
									<td colspan="2">
										<input id="username" name="username" type="text" class="g-ipt"/>
										<span id="loginNameTip"></span>
										<div class="tip" id="loginNameFixTip" ></div>
									</td>
								</tr>
								<tr>
									<th style="width:80px;">密码：</th>
									<td colspan="2">
										<input id="password"  name="password" type="password" class="g-ipt" />
										<span id="loginPasswordTip"></span>
										<div class="tip" id="loginPasswordFixTip" ></div>
									</td>
								</tr>
								<tr>
									<th style="width:80px;">验证码：</th>
									<td colspan="2">
										<input type="text" class="g-ipt" id="loginCode"  name="validateCode" style="width:80px;">
										<img id="imgValidateCode" src="${BasePath}/servlet/imageValidate?dt=<%=new Date() %>" width="55" height="20" align="top" border="0" style="cursor:hand"/>
										<div class="tip" id="loginCodeFixTip" ></div>
									</td>
								</tr>
								<tr>
									<th style="width:80px;">&nbsp;</th>
									<td>
										<input type="checkbox" name="rememberMe" id="rememberMe" tabindex="4" />
										<label for="rememberMe">记住登录</label>
									</td>
								</tr>
								<tr>
									<th style="width:80px;">&nbsp;</th>
									<td>
										<input name="" type="submit" class="button_login" value=""/>
									</td>
								</tr>
							</table>
						</div>
					</div>
					<div class="footer_login">©2011 Yougou Technology Co., Ltd.</div>
				</form>
			</td>
		</tr>
	</tbody>
</table>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script> 

<script type="text/javascript">

	  function reloadCode(){
			var verify=document.getElementById('imgValidateCode');
			verify.setAttribute('src','${BasePath}/servlet/imageValidate?rand='+Math.random());
		}
    
    
  	//var config={form:"loginForm",submit:submitForm,
 	//fields:[
	//	{name:'loginName',allownull:false,regExp:"name",defaultMsg:null,focusMsg:null,errorMsg:'',rightMsg:'',msgTip:'loginNameTip'}
	//]}
  
  //	Tool.onReady(function(){
		//var f = new Fv(config);
		//f.register();
	//});
  	
  	/**
  	 * 提交表单
  	 */
  	//function submitForm(){
  	//	return true;
  	//}
  	
  	
  	
</script>

<script type="text/javascript">
$(function(){
    $.formValidator.initConfig({theme:"default",submitOnce:true,formID:"loginForm",
        onError:function(msg){},
        submitAfterAjaxPrompt : '有数据正在异步验证，请稍等...'
    });
    
    $("#loginName").formValidator({onShowFixText:" ",onCorrect:" "}).inputValidator({min:2,onError:"用户名不能为空！"});
    $("#loginPassword").formValidator({onShowFixText:" ",onCorrect:" "}).inputValidator({min:6,onError:"用户密码不能为空！"});
    $("#loginCode").formValidator({onShowFixText:" ",onCorrect:" "}).inputValidator({min:1,onError:"验证码不能为空！"});
});
</script>

    
</body>
</html>
