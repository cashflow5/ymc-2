<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-修改账号</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
<script type="text/javascript">
	var basePath = "${BasePath}";
</script>
</head>

<body>
<div class="account_box">
        <div class="form-box w400">
	<form id="queryForm" name="queryForm" action="${BasePath}/merchants/login/update_merchants.sc" method="post">
		<input type="hidden" name="id" id="id" value="<#if merchant??&&merchant.id??>${merchant.id!''}</#if>"/>
		<input type="hidden" id="loginName" name="loginName" value="${merchant.loginName!''}"/>
		<dl class="form-warp small clearfix">
                <dt class="form-title"><em class="cred">*</em>账号：</dt>
                <dd class="form-text"><p class="prompt-tip">${merchant.loginName!''}</p></dd>
                <dd></dd>
            </dl>
            <dl class="form-warp small clearfix">
                <dt class="form-title">密码：</dt>
                <dd class="form-text"><input type="password" maxlength="20" class="password-text" id="password" name="password"/></dd>
                <dd>
                    <ul class="safetyStrength">
                        <li class="pwdLow"></li>
                        <li></li>
                        <li></li>
                        <li class="text">低</li>
                    </ul>
                </dd>
            </dl>
            <dl class="form-warp small clearfix">
                <dt class="form-title">确认密码：</dt>
                <dd class="form-text"><input type="password" maxlength="20" id="loginPasswordTwo" name="loginPasswordTwo"/></dd>
                <dd class="Gray"><p class="prompt-tip">不填写表示不修改原密码</p></dd>
            </dl>
            <dl class="form-warp small clearfix">
                <dt class="form-title"><em class="cred">*</em>手机号码：</dt>
                <dd class="form-text"><input type="text" id="mobileCode" value="<#if merchant??&&merchant.mobileCode??>${merchant.mobileCode!''}</#if>" name="mobileCode" maxlength="11"/></dd>
                <dd class="Gray"><p class="prompt-tip">不能与已绑定的号码重复</p></dd>
            </dl>
            <dl class="form-warp small clearfix">
                <dt class="form-title">真实姓名：</dt>
                <dd class="form-text"><input type="text" id="userName" value="<#if merchant??&&merchant.userName??>${merchant.userName!''}</#if>" name="userName"/></dd>
                <dd></dd>
            </dl>
            <dl class="form-warp small clearfix">
                <dt class="form-title">备注：</dt>
                <dd class="form-text textarea"><textarea id="remark" name="remark"><#if merchant??&&merchant.remark??>${merchant.remark!''}</#if></textarea></dd>
                <dd></dd>
            </dl>
            <div class="code-tip mt10 hide"></div>
	</form>
	</div>
</div>
<script type="text/javascript" src="${BasePath}/yougou/js/subaccount.js"></script>
</body>
</html>
