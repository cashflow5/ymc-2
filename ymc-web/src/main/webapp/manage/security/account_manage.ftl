<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-账户管理</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css?${style_v}"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<script type="text/javascript">
	var basePath = "${BasePath}";
	var flag = 0;
</script>
</head>
<body>
<div class="main_container">
<div class="normal_box">
		<p class="title site">当前位置：商家中心 &gt; 设置模块 &gt; 帐号管理</p>
		<ul class="account-information">
                        <li>登录账号：<span>${loginName!'' }</span></li>
                        <li>安全等级：<span><#if grade==3>高<#elseif grade==2>中<#else>低</#if></span></li>
                        <li>绑定手机：<span class="bold">${mobilePhone!'<span class="cred">未绑定</span>' }</span></li>
                        <li>绑定邮箱：<span class="bold">${email!'<span class="cred">未绑定</span>' }</span></li>
                    </ul>
                    <div class="account-box clearfix">
                        <div class="account-img">
                            <div class="img-icon img-ok"></div>
                            <div class="red"><#if strength??><#if strength=="3">强度高<#elseif strength=="2">强度中<#else>强度低</#if></#if></div>
                        </div>
                        <div class="account-left">登录密码</div>
                        <div class="account-main">安全性高的密码可以使账户更安全，建议您定期更换密码。</div>
                        <a href="javascript:void(0);" onclick="updatePwd('${mobilePhone!}','${email!}');" class="account-right">修改</a>
                    </div>
                    <div class="account-box clearfix">
                        <div class="account-img">
                            <div class="img-icon ${(email??)?string('img-ok','img-warn')}"></div>
                            <div>${(email??)?string('已绑定','未绑定') }</div>
                        </div>
                        <div class="account-left">绑定邮箱</div>
                        <div class="account-main">验证后，可以用邮箱找回密码，接受订单提醒等，保障您的账户更加安全。</div>
                        <#if email??>
	                        <a href="javascript:void(0);" onclick="bandingEmail('${mobilePhone!}');" class="account-right">修改</a>
                        	<#else>
                        	<a href="javascript:void(0);" onclick="bandingEmail('${mobilePhone!}');" class="btn-blue-1 fr">立即绑定</a>
                        </#if>
                    </div>
                    <div class="account-box clearfix">
                        <div class="account-img">
                            <div class="img-icon ${(mobilePhone??)?string('img-ok','img-warn')}"></div>
                            <div>${(mobilePhone??)?string('已绑定','未绑定') }</div>
                        </div>
                        <div class="account-left">绑定手机</div>
                        <div class="account-main">绑定后，可以用手机找回密码、接收手机动态验证码等，使您的账户更加安全。</div>
                        <#if mobilePhone??>
	                        <a href="${BasePath }/merchants/security/updateMobile.sc" class="account-right">修改</a>
                        	<#else>
                        	<a href="${BasePath }/merchants/security/bandingMobile.sc" class="btn-blue-1 fr">立即绑定</a>
                        </#if>
                    </div>
    </div>
	 </div>
	 <script src="${BasePath}/yougou/js/modifty.js?${style_v}"></script>
</body>
</html>