<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-修改密码</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css?${style_v}"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<script type="text/javascript">
	var flag = 1;
	var basePath = "${BasePath}";
	var mobilePhone = "${mobilePhone!''}";
	var email = "${email!''}";
</script>
</head>
<body>
<div class="main_container">
<div class="normal_box">
		<p class="title site">当前位置：商家中心 &gt; 账户管理 &gt; 修改密码</p>
		<h1 class="modify-title">修改登录密码</h1>
                    <ul class="step-list clearfix">
                        <li class="curr">1 <p>验证身份</p></li>
                        <li class="">2 <p>修改登录密码</p></li>
                        <li class="complete"><img src="${BasePath }/yougou/images/modity-step-ok.png" alt="" /><p>完成</p></li>
                    </ul>
                    <div class="form-box">
                        <dl class="form-warp clearfix">
                            <dt class="form-title select"><em class="cred">*</em>验证方式：</dt>
                            <dd class="form-text"><select style="height: 28px;" id="verifywaySelect" onchange="changeway(this);">
                            	<#if mobilePhone??><option value="mobile">${mobilePhone }</option></#if>
                            	<#if email??><option value="email">${email }</option></#if>
                            </select></dd>
                            <dd></dd>
                        </dl>
                        <dl class="form-warp clearfix">
                            <dt class="form-title"><em class="cred">*</em>图形验证码：</dt>
                            <dd class="form-text"><input type="text" id="code" name="code" maxlength="4"/></dd>
                            <dd><img id="imgValidateCode" src="${BasePath}/servlet/imageValidate" alt="验证码" title="验证码不区分大小写" /><a class="next-code Gray" onclick="changeLevel();return false;">换一张</a></dd>
                        </dl>
                        <dl class="form-warp clearfix mobile_code">
                            <dt class="form-title"><em class="cred">*</em>请填写手机验证码：</dt>
                            <dd class="form-text"><input type="text" maxlength="6" name="verifyCode" id="verifyCode" class="sms-text" /></dd>
                            <dd><a class="btn-sms" href="javascript:void(0);">获取短信校验码</a></dd>
                        </dl>
                        <div class="code-tip hide">验证码已发出，请注意查收短信，如果没有收到，你可以在60秒后要求系统重新发送</div>
                        <div class="btn-box"><a href="javascript:void(0);" onclick="nextStep('mobile','${BasePath }/merchants/security/sentupdatepwdsms.sc');" class="btn-blue-1 medium">提交</a></div>
                        <input type="hidden" name="mobileCode" id="mobileCode" value="${mobilePhone!'' }"/>
                        <input name="temp" id="tempVar" type="hidden"/>
                    </div>
    </div>
	 </div>
	 <script src="${BasePath}/yougou/js/modifty.js?${style_v}"></script>
</body>
</html>