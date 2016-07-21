<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-修改密码</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css?${style_v}"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<script type="text/javascript">
	var flag = 0;
	var basePath = "${BasePath}";
	var mobilePhone = "${mobilePhone!''}";
	var email = "${email!''}";
</script>
</head>
<body>
<div class="main_container">
<div class="normal_box">
		<p class="title site">当前位置：商家中心 &gt; 账户管理  &gt; 修改密码</p>
		<h1 class="modify-title">修改登录密码</h1>
                    <ul class="step-list clearfix">
                        <li class="histoy">1 <p>验证身份</p></li>
                        <li class="curr">2 <p>修改登录密码</p></li>
                        <li class="complete"><img src="${BasePath }/yougou/images/modity-step-ok.png" alt="" /><p>完成</p></li>
                    </ul>
                    <div class="form-box">
                        <dl class="form-warp clearfix">
                            <dt class="form-title"><em class="cred">*</em>新的登录密码：</dt>
                            <dd class="form-text"><input type="password" name="password1" id="password1"/></dd>
                            <dd></dd>
                        </dl>
                        <dl class="form-warp clearfix">
                            <dt class="form-title"><em class="cred">*</em>请确认密码：</dt>
                            <dd class="form-text"><input type="password" name="password2" id="password2"/></dd>
                            <dd class="Gray"><p class="prompt-tip">6-20个字符，由字母、数字和符号的两种以上组合</p></dd>
                        </dl>
                        <dl class="form-warp clearfix">
                            <dt class="form-title"><em class="cred">*</em>图形验证码：</dt>
                            <dd class="form-text"><input type="text" id="code" name="code" maxlength="4"/></dd>
                            <dd><img id="imgValidateCode" src="${BasePath}/servlet/imageValidate" alt="验证码" title="验证码不区分大小写" />
                            <a class="next-code Gray" onclick="changeLevel();return false;">换一张</a></dd>
                        </dl>
                        <div class="code-tip hide"></div>
                        <div class="btn-box"><a href="javascript:void(0);" onclick="updatePassword();" class="btn-blue-1 medium">提交</a></div>
                    </div>
    </div>
	 </div>
	 <script src="${BasePath}/yougou/js/modifty.js?${style_v}"></script>
</body>
</html>