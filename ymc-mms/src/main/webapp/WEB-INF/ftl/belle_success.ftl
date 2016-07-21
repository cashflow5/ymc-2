<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>提示信息 - Powered By systemConfig.systemName</title>
<meta name="Author" content="belle Team" />
<meta name="Copyright" content="belle" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<style type="text/css">
	body{font-size:13px;}
	.messageText{color:red;}
</style>
<#include "yitianwms/yitianwms-include.ftl">
</head>
<body class="message">
	<div class="body">
		<div class="messageBox">
			<div class="boxTop">
				<div class="boxTitle">提示信息：&nbsp;</div>
				<a class="boxClose windowClose" href="#" hidefocus="true"></a>
			</div>
			<br/>
			<div class="boxMiddle">
				<div class="messageContent">
					<span class="icon success">&nbsp;</span>
					<span class="messageText">
						<#if (errorMessages ?? && errorMessages?size > 0)>
							<#list errorMessages as list>${list}<br></#list>
						<#elseif (actionMessages ??)>
							${actionMessages}
						<#elseif (fieldErrors ?? && fieldErrors?size > 0)>
							<#list (fieldErrors?keys) as key>
								${fieldErrors[key]?replace('^\\[', '', 'r')?replace('\\]$', '', 'r')}<br>
							</#list>
						<#else>
							您的操作已成功!
						</#if>
					</span>
				</div><br />
				<input type="button" value="确定." <#if redirectionUrl??>onclick="javascript:parent.document.getElementById('mbdif').src='${redirectionUrl}';closewindow();"
												  <#else>
												  	onclick="javascript:closewindow();"
												  </#if> />
			<!--	
				<input type="button" class="formButton messageButton" <#if redirectionUrl??>onclick="window.location.href='redirection('${redirectionUrl}')'"<#else>onclick="window.history.back(); return false;"</#if> value="确  定" hidefocus="true" />
			-->
			</div>
			<div class="boxBottom"></div>
		</div>
	</div>
</body>
<#include "yitianwms/yitianwms-include-bottom.ftl"> 
</html>
