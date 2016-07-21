<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rev="stylesheet" rel="stylesheet" type="text/css" href="${BasePath}/css/anm/ytsys-base.css" />
<#assign spring=JspTaglibs["/WEB-INF/tlds/spring.tld"]/>
<title>网络营销系统-信息提示</title>
</head>

<body>

<div class="warnFrm" style="margin:0px auto;text-align:center;vertical-align: middle;">
  <div class="warnFrmTop"><span>优购温馨提示您：</span><a href="${BasePath}/${returnUrl?default('')}" class="close">close</a></div>
  <div class="warnFrmCenter">
      <span>
      	<@spring.message code="${warnMessagekey}" /><br/>
      </span>
  </div>
  <div class="warnFrmBt"><a href="${BasePath}/${returnUrl?default('')}" target="${target?default('mbdif')}">确定</a></div>
 </div>
		 
</body>
</html>
