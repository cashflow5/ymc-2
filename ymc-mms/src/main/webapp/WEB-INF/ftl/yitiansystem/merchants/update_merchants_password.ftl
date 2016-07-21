<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>

<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/jquery-1.3.2.min.js"></script>
<script type="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/js.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/order.js"></script>
<title>优购商城--商家后台</title>
</head>
<body>
<div class="container">
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr">
				  <span><a href="#" class="btn-onselc">修改商家密码</a></span>
				</li>
				
			</ul>
		</div>
 <div class="modify"> 
               <form action="${BasePath}/yitiansystem/merchants/businessorder/updatePassword.sc" name="queryForm" id="queryForm" method="post" style="padding:0px;margin:0px;">
                <input type="hidden" id="id" name="id" value="<#if merchantUser??&&merchantUser.id??>${merchantUser.id!''}</#if>">
		      		<#if merchantUser??>
		      		 <table cellpadding="0" cellspacing="0" class="list_table">
                        <tr><td>
                            <label><span style="color:red;">&nbsp;*</span>登录名称：</label>
                          </td><td>
                          <#if merchantUser??&&merchantUser.loginName??>${merchantUser.loginName!''}</#if>
                        </td></tr>
                         <tr><td>
                            <label><span style="color:red;">&nbsp;*</span>新密码：</label>
                          </td><td>
                            <input type="password" name="password" id="password" />
                            &nbsp;&nbsp;<span style="color:red;" id="passwordError" ></span>
                        </td></tr>
                         <tr><td>
                            <label><span style="color:red;">&nbsp;*</span>确认密码：</label>
                          </td><td>
                            <input type="password" name="passwordTwo" id="passwordTwo" />
                            &nbsp;&nbsp;<span style="color:red;" id="passwordTwoError" ></span>
                        </td></tr>
                        <tr><td>&nbsp;</td>
                        <td> <input id="btn" type="button" value="提交" class="yt-seach-btn" onclick="return updateMerchantPassword();">
                        </td></tr>
                   </table>
                 <#else>
				   <span style="color:red;">商家登录帐号为空,请先添加商家登录帐号在修改密码！</span>
			    </#if> 
       	</form>
    </div>
 <div class="blank20"></div>
</div>
</body>
</html>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
<script type="text/javascript">

//修改商家角色名称
function updateMerchantPassword(){
   //新密码
	var password = $("#password").val();
	 //确认密码
	var passwordTwo = $("#passwordTwo").val();
	
	if(password=="" ){
		$("#passwordError").html("新密码不能为空!");
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
	if(password.length<6){
		$("#passwordTwoError").html("新密码长度必须大于等于6位数!");
		$("#password").focus();
		return false;
	}else{
	   $("#passwordTwoError").html("");
	}
	if(password!=passwordTwo ){
		$("#passwordTwoError").html("登录密码和确认密码不一致!");
		$("#passwordTwo").focus();
		return false;
	}else{
	   $("#passwordTwoError").html("");
	}
	document.queryForm.submit();
}

</script>