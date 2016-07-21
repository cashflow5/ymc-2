<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>

<title>优购商城--商家后台</title>
</head>
<script type="text/javascript">

</script>
<body>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script> 
<div class="container">
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr">
				  <span><a href="#" class="btn-onselc">管理员</a></span>
				</li>
				
			</ul>
		</div>
 		<div class="modify"> 
     		<form action="${BasePath}/yitiansystem/merchants/businessorder/saveMerchantYougouAdmin.sc" name="queryForm" id="queryForm" method="post"> 
              	<input type="hidden" id="id" name="id" value="${(merchantUser.id)!''}">
               	<input type="hidden" id="merchantCode" name="merchantCode" value="">
               	<input type="hidden" id="isAdministrator" name="isAdministrator" value="0">
               	<input type="hidden" id="isYougouAdmin" name="isYougouAdmin" value="1">
               	<input type="hidden" id="yg_admin_type" name="yg_admin_type" value="${(yg_admin_type)!''}">
               	<input type="hidden" id="deleteFlag" name="deleteFlag" value="1">
               	<div style="margin-top:30px;">
                 	<table cellpadding="0" cellspacing="0" class="list_table">
                   		<tr>
                   			<td>
                          	<label><span style="color:red;margin-left:54px;">&nbsp;*</span>管理员名称：</label>
			             	<input type="test" id="userName" name="userName" value="${(merchantUser.userName)!''}">
                           	&nbsp;&nbsp;<span style="color:red;" id="userNameError"></span>
                        	</td>
                        </tr>
                   		<tr>
                   			<td>
                   			<#if isModify??&&isModify=="true">
                          	<label><span style="color:red;margin-left:54px;">&nbsp;*</span>登录账号：</label>
                          	${(merchantUser.loginName)!''}
			             	<input type="hidden" id="loginName" name="loginName" value="${(merchantUser.loginName)!''}">
                           	&nbsp;&nbsp;<span style="color:red;" id="loginNameError"></span>
                           	<#else>
                          	<label><span style="color:red;margin-left:54px;">&nbsp;*</span>登录账号：</label>
			             	<input type="test" id="loginName" name="loginName" value="${(merchantUser.loginName)!''}">
                           	&nbsp;&nbsp;<span style="color:red;" id="loginNameError"></span>
                           	</#if>
                        	</td>
                        </tr>
                        <tr>
                        	<td>
                          	<label> <span style="color:red;margin-left:50px;">&nbsp;*</span> 登录密码：</label>
                           	<input type="password" id="password" name="password" value=""/>
                        	&nbsp;&nbsp;<span style="color:red;" id="passwordError"></span>
                       		</td>
                       	</tr>
                        <tr>
                        	<td>
                          	<label> <span style="color:red;margin-left:50px;">&nbsp;*</span> 确认密码：</label>
                           	<input type="password" name="passwordTwo" id="passwordTwo"  />
                        	&nbsp;&nbsp;<span style="color:red;" id="passwordTwoError"></span>
                       		</td>
                       	</tr>
                        <tr>
                        	<td>
                          	<label> <span style="color:red;margin-left:50px;">&nbsp;*</span> 是否启用：</label>
                          	<select id="status" name="status">
                          		<option value="">请选择状态</option>	
                          		<option value="1" <#if (merchantUser.status)??&&merchantUser.status==1> selected="selected"</#if>>启用</option>	
                          		<option value="0" <#if (merchantUser.status)??&&merchantUser.status==0> selected="selected"</#if>>停用</option>	
                          	</select>
                        	&nbsp;&nbsp;<span style="color:red;" id="statusError"></span>
                       		</td>
                       	</tr>
                	</table>
               </div>
               <div style="margin-top:10px;margin-left:60px;">
                 <input type="button" value="保存" onclick="return addMerchantYougouAdmin();"  class="yt-seach-btn">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <input type="button" value="重置" onclick="javascript:document.queryForm.reset();" class="yt-seach-btn">
               </div>
       	</form>
    </div>
 <div class="blank20"></div>
</div>
</body>
</html>
<script language="javascript">
//保存商家信息
function addMerchantYougouAdmin(){
	var userName = $("#userName").val();//登录账号
	var loginName= $("#loginName").val();//登录密码
	var password= $("#password").val();//登录密码
	var passwordTwo = $("#passwordTwo").val();//确认密码
	var status = $("#status").val();//状态
	if(userName=="" ){
		$("#userNameError").html("管理员账号不能为空!");
		$("#userName").focus();
		return false;
	}else{
	   $("#userNameError").html("");
	}
	if(loginName=="" ){
		$("#loginNameError").html("登录账号不能为空!");
		$("#loginName").focus();
		return false;
	}else{
	   $("#loginNameError").html("");
	}
	if(password=="" ){
		$("#passwordError").html("登录密码不能为空!");
		$("#password").focus();
		return false;
	}else{
	   $("#passwordError").html("");
	}
     //判断密码长度必须是在6-30之间
     var length = password.length;
     if(length >= 6 && length <= 30){
        $("#password").html("");
     }else{
     	$("#password").val("");
     	$("#passwordTwo").val("");
        $("#passwordError").html("密码长度必须大于等于6和小于等于30!");
        return false;
     }
     //判断原秘密是否输入正确
     var reg=new RegExp(/[A-Za-z].*[0-9]|[0-9].*[A-Za-z]/);
     if(!reg.test(password)){
     	$("#password").val("");
     	$("#passwordTwo").val("");
        $("#passwordError").html("密码必须是数字和字符的组合!");
        return false;
     }else{
        $("#password").html("");
     }
	if(passwordTwo=="" ){
		$("#passwordTwoError").html("确认密码不能为空!");
		$("#passwordTwo").focus();
		return false;
	}else{
	   $("#passwordTwoError").html("");
	}
	if(password != passwordTwo ){
		$("#passwordTwoError").html("登录密码和确认密码不一致!");
		$("#password").focus();
		return false;
	}else{
	   $("#passwordTwoError").html("");
	}
	if(status=="" ){
		$("#statusError").html("请选择状态!");
		$("#status").focus();
		return false;
	}else{
	   $("#statusError").html("");
	}
//	var userName=encodeURIComponent(userName);
	<#if isModify??&&isModify=="true">
   	document.queryForm.submit();
	<#else>
	//判断用户名是否存在
	$.ajax({ 
		type: "post", 
		url: "${BasePath}/yitiansystem/merchants/businessorder/exitsLoginAccount.sc?loginAccount=" + loginName, 
		success: function(data){
			if("sucuess"==data){
			   $("#loginNameError").html("登录帐号已经存在,请重新输入!");
			   $("#loginName").focus();
			   return false;
			}else{
			   document.queryForm.submit();
			}
		} 
	});
	</#if>
}
</script>