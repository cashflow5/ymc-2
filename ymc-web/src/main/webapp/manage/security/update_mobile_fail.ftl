<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-修改邮箱</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css?${style_v}"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<script type="text/javascript">
	var basePath = "${BasePath}";
	function delayURL(url) {
		var delay = parseInt($("#time").text());
		if(delay > 0) {
			delay--;
			$("#time").text(delay);
		} else {
			location.href = url;
		}
		setTimeout("delayURL('" + url + "')", 1000);
	}
</script>
</head>
<body>
<div class="main_container">
<div class="normal_box">
		<p class="title site">当前位置：商家中心 &gt; 账户管理 &gt; 修改绑定手机</p>
		<h1 class="modify-title">修改绑定手机</h1>
                    <ul class="step-list clearfix">
                        <li class="histoy">1 <p>验证身份</p></li>
                        <li class="histoy">2 <p>修改绑定手机</p></li>
                        <li class="complete curr"><img src="${BasePath }/yougou/images/modity-step-ok.png" alt="" /><p>完成</p></li>
                    </ul>
                    <div class="form-box">
                    <div class="<#if isScuess?? && isScuess==true>fdpwd_suc<#else>fdpwd_fail</#if>">
				    	 <div class="con" style="padding: 100px 0 0 90px">
				       	  <h2>${message}</h2>
				          <p class="mt20">您可立即返回 <a href="${BasePath}/merchants/security/accountSecurity.sc" class="cblue">商家中心账户管理页面</a></p>
				          <p class="c9 mt5"><span id="time">5</span>秒后，页面自动跳转到商家中心账户管理页面</p>
				         </div>
    				</div>
    				</div>
    </div>
	 </div>
	 <script type="text/javascript">
	$(function(){
		delayURL(basePath+"/merchants/security/accountSecurity.sc");
	});
</script>
</body>
</html>