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
				  <span><a href="#" class="btn-onselc"><#if flag??&&flag=='1'>修改商家绑定手机<#else>修改商家绑定邮箱</#if></a></span>
				</li>
				
			</ul>
		</div>
 		<div class="modify"> 
     		<form action="<#if flag??&&flag=='1'>${BasePath}/yitiansystem/merchants/businessorder/updateMobile.sc<#else>${BasePath}/yitiansystem/merchants/businessorder/updateEmail.sc</#if>"
     		 name="queryForm" id="queryForm" method="post"> 
              	<input type="hidden" id="id" name="id" value="<#if merchantUser??>${(merchantUser.id)!''}</#if>">
               	<input type="hidden" id="flag" name="flag" value="${flag!''}">
               	<div style="margin-top:30px;">
                 	<table cellpadding="0" cellspacing="0" class="list_table">
                   		<tr>
                   			<td>
                   			<#if flag??&&flag=='1'>
                          	<label><span style="color:red;margin-left:54px;">&nbsp;*</span>原绑定手机：</label>
                          	<#if merchantUser??>${(merchantUser.mobileCode)!''}</#if>
			             	<input type="hidden" id="originalMobileCode" name="originalMobileCode" value="<#if merchantUser??>${(merchantUser.mobileCode)!''}</#if>">
                            
                           	<#else>
                          	<label><span style="color:red;margin-left:54px;">&nbsp;*</span>原绑定邮箱：</label>
                          	<#if merchantUser??>${(merchantUser.email)!''}</#if>
			             	<input type="hidden" id="originalEmail" name="originalEmail" value="<#if merchantUser??>${(merchantUser.email)!''}</#if>">
                           	 
                           	</#if>
                        	</td>
                        </tr>
                        <tr>
                        	<td>
                   			<#if flag??&&flag=='1'>
                          	<label><span style="color:red;margin-left:54px;">&nbsp;*</span>新绑定手机：</label>
			             	<input  id="mobileCode" name="mobileCode" value="">
                           	&nbsp;&nbsp;<span style="color:red;" id="mobileCodeError"></span>
                           	<#else>
                          	<label><span style="color:red;margin-left:54px;">&nbsp;*</span>新绑定邮箱：</label>
			             	<input  id="email" name="email" value="">
                           	&nbsp;&nbsp;<span style="color:red;" id="emailError"></span>
                           	</#if>
                        	</td>
                       	</tr>
                        <tr>
                        	<td>
                          	<label> <span style="color:red;margin-left:54px;">&nbsp;*</span> 备注：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                           	<textarea style="width:200px;" class="flag_textarea" maxlength="200"  name="remark" id="remark"></textarea>
                        	&nbsp;&nbsp;<span style="color:red;" id="remarkError"></span>
                       		</td>
                       	</tr>
                	</table>
               </div>
               <div style="margin-top:10px;margin-left:144px;">
                 <input type="button" value="提交" onclick="<#if flag??&&flag=='1'>updateMobile('${(merchantUser.id)!''}');<#else>updateEmail('${(merchantUser.id)!''}');</#if>"  class="yt-seach-btn">
               </div>
       	</form>
    </div>
 <div class="blank20"></div>
</div>
</body>
</html>
<script language="javascript">
function updateEmail(id) {
	var _email = $('#email').val();
	if (!verifyAddress(_email)) {
		alert('请输入正确的邮箱地址!');
		return false;
	}
	if ( $('#remark').val()=='' || $.trim( $('#remark').val() )=='' ) {
		alert('请填写修改原因!');
		return false;
	}
	 document.queryForm.method="post";
     document.queryForm.submit();
	
}

function updateMobile(id){
	var mobileCode = $('#mobileCode').val();
	if (!verifyMobile(mobileCode)) {
		alert('请输入正确的手机号码!');
		return false;
	}
	if ( $('#remark').val()=='' || $.trim( $('#remark').val() )=='' ) {
		alert('请填写修改原因!');
		return false;
	}
	 document.queryForm.method="post";
     document.queryForm.submit();
}

function verifyMobile(mobile){
	var reg = /^\d+$/;
	console.info(mobile);
	if(mobile.length==11 && mobile.match(reg)){
		return true;
	}
	return false;
}

function verifyAddress(email){
	var pattern =/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/; 
	var flag = pattern.test(email);
	return flag;
} 
</script>