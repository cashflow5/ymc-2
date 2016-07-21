<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-添加账号</title>
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
		<form id="queryForm" name="queryForm" action="${BasePath}/merchants/login/add_merchants.sc" method="post">
            <dl class="form-warp small clearfix">
                <dt class="form-title"><em class="cred">*</em>账号：</dt>
                <dd class="form-text"><input type="text" id="loginName" name="loginName" /></dd>
                <dd></dd>
            </dl>
            <dl class="form-warp small clearfix">
                <dt class="form-title"><em class="cred">*</em>密码：</dt>
                <dd class="form-text"><input type="password" maxlength="20" id="password" name="password" class="password-text" /></dd>
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
                <dt class="form-title"><em class="cred">*</em>确认密码：</dt>
                <dd class="form-text"><input maxlength="20" id="loginPasswordTwo" name="loginPasswordTwo" type="password"/></dd>
                <dd></dd>
            </dl>
            <dl class="form-warp small clearfix">
                <dt class="form-title"><em class="cred">*</em>手机号码：</dt>
                <dd class="form-text"><input id="mobileCode" maxlength="11" name="mobileCode" type="text"/></dd>
                <dd class="Gray"><p class="prompt-tip">不能与已绑定的号码重复</p></dd>
            </dl>
            <dl class="form-warp small clearfix">
                <dt class="form-title">真实姓名：</dt>
                <dd class="form-text"><input type="text" id="userName" name="userName"/></dd>
                <dd></dd>
            </dl>
            <dl class="form-warp small clearfix">
                <dt class="form-title">备注：</dt>
                <dd class="form-text textarea"><textarea id="remark" name="remark"></textarea></dd>
                <dd></dd>
            </dl>
            <div class="code-tip mt10 hide"></div>
            </form>
        </div>
    </div>
	<script type="text/javascript" src="${BasePath}/yougou/js/subaccount.js"></script>
</body>
</html>

