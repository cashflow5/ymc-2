<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>重置密码</title>
<link href="${BasePath}/yougou/css/base.css" rel="stylesheet" />
<link href="${BasePath}/yougou/css/mold.css?${style_v}" rel="stylesheet" />
<script language="JavaScript" type="text/javascript">
	function delayURL(url) {
		var delay = document.getElementById("time").innerHTML;
		if(delay > 0) {
			delay--;
			document.getElementById("time").innerHTML = delay;
		} else {
			window.top.location.href = url;
		}
		setTimeout("delayURL('" + url + "')", 1000);
	}
</script>
</head>

<body>
<div class="wrap mt20">
    <a href="${BasePath}/merchants/login/to_index.sc" class="logo"><img height="47" width="285" alt="商家中心" src="/yougou/images/logo.jpg"></a>
</div>
<div class="wrap topline mt20">
</div>
<div class="wrap mt25">
    您当前所在位置 &gt; 商家中心 &gt;
    <span class="cblue">找回密码</span>
</div>
<div class="fdpwd_step wrap">
    <ul class="step3 clearfix">
        <li>1.输入账号</li>
        <li>2.密码重置</li>
        <li class="curr">3.修改成功</li>
    </ul>
</div>
<div class="wrap" style="width:550px;">
    <div class="<#if isScuess?? && isScuess==true>fdpwd_suc<#else>fdpwd_fail</#if>">
    	 <div class="con">
       	  <h2>${message}</h2>
          <p class="mt20">您可立即返回 <a href="${BasePath}/merchants/login/to_index.sc" class="cblue">商家中心登录页</a></p>
          <p class="c9 mt5"><span id="time">5</span>秒后，页面自动跳转到商家中心登录页</p>
         </div>
    </div>
</div>
<script src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript">
	delayURL("${BasePath}/merchants/login/to_index.sc");
</script>
</body>
</html>
