<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-绑定手机</title>
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
		<p class="title site">当前位置：商家中心 &gt;账户管理 &gt; 绑定手机</p>
		<form action="#" method="post" name="form" id="form">
		<div class="form-box">
                        <dl class="form-warp clearfix">
                            <dt class="form-title">登录账号：</dt>
                            <dd class="form-text"><p class="prompt-tip">${loginName }</p></dd>
                            <dd></dd>
                        </dl>
                        <dl class="form-warp clearfix">
                            <dt class="form-title">手机号码：</dt>
                            <dd class="form-text"><input type="text" id="mobileCode" maxlength="11" name="mobileCode"/></dd>
                            <dd></dd>
                        </dl>
                        <dl class="form-warp clearfix">
                            <dt class="form-title">短信验证码：</dt>
                            <dd class="form-text"><input type="text" id="verifyCode" name="verifyCode" maxlength="6" class="sms-text" /></dd>
                            <dd><a href="javascript:void(0);" class="btn-sms">获取短信验证码</a></dd>
                        </dl>
                        <div class="code-tip hide">验证码已发出，请注意查收短信，如果没有收到，你可以在60秒后要求系统重新发送</div>
                        <div class="btn-box"><a class="btn-blue-1 medium" href="javascript:void(0);" onclick="bandingMobile();">保存</a></div>
                    </div>
                    </form>
    </div>
	 </div>
	 <script src="${BasePath}/yougou/js/modifty.js?${style_v}"></script>
</body>
</html>