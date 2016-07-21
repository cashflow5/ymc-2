<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rev="stylesheet" rel="stylesheet" type="text/css" href="${BasePath}/css/ytsys-base.css" />
<#assign spring=JspTaglibs["/WEB-INF/tlds/spring.tld"]/>
<title>网络营销系统-信息提示</title>
<script type="text/javascript"  src="${BasePath}/yougou/js/jquery-1.4.2.min.js" ></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script> 

</head>

<body onload="onPageLoad()">

<div class="warnFrm" style="margin:50px auto;text-align:center;vertical-align: middle;">
  <div class="warnFrmTop"><span id="tip"></span><a href="${returnUrl?default('mbdif')}" class="close">close</a></div>
  <div class="warnFrmCenter">
      <span>
      	<@spring.message code="${warnMessagekey}" /><br/>
      </span>
  </div>
  <div class="warnFrmBt">
  	<#if returnUrl?? && (returnUrl != "-1") && ( (returnUrl?index_of('?parentSourcePage') > 0) || (returnUrl?index_of('&parentSourcePage') > 0)  )>
  		<a id="linker" href="${returnUrl?default('')}" target="${target?default('mbdif')}">确定</a>
  	<#elseif !returnUrl?? || (returnUrl == "-1") >
  		<a id="linker" href="#" onclick="window.history.go(-1);" target="${target?default('mbdif')}">确定</a>
  	<#else>
  		<a id="linker" href="${returnUrl?default('')?replace("@38@",'&')}" target="${target?default('mbdif')}">确定</a>
  	</#if>
  	
  </div>
 </div>
 
 
<script type="text/javascript">
function onPageLoad() {
	
	var defaultMillisec = 5000;
	
	<#if millisec??>defaultMillisec=${millisec};</#if>
	
	document.getElementById("tip").innerHTML = '提示(' + (defaultMillisec / 1000) + '秒后自动关闭)：';
	
	window.setTimeout(function(){window.parent.mbdif.location.href="${returnUrl?default('')?replace("@38@",'&')}";closewindow();}, defaultMillisec);
	
	var timer = window.setInterval(function(){
		
		defaultMillisec -= 1000;
		
		document.getElementById("tip").innerHTML = '提示(' + (defaultMillisec / 1000) + '秒后自动关闭)：';
		
		if (defaultMillisec <= 0) {
			
			window.clearInterval(timer);
		}
	}, 1000);
}
</script>
</body>
</html>
