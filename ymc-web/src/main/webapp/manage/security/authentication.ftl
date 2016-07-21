<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>优购商家中心-身份验证</title>
    <link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css?${style_v}"/>
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
	<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
	<script type="text/javascript">
		var flag = 0;
		var basePath = "${BasePath}";
	</script>
</head>
<body>
    <div class="box-contain mt30">
        <dl class="c-h-2column-left c-h-2column-l120 padding-normal">
            <dt class="label">手机号码：</dt>
            <dd class="c-h-contain">${mobileCode!'————' }</dd>
            <dt class="label">&nbsp;</dt>
            <dd class="c-h-contain">&nbsp;</dd>
        </dl>
        <dl class="c-h-2column-left c-h-2column-l120 padding-normal">
            <dt class="label"><em class="cred">*</em>图形验证码：</dt>
            <dd class="c-h-contain">
                <input type="text" class="input-default" id="code" maxlength="4" name="code" style="width:185px;">
                <img id="imgValidateCode" src="${BasePath}/servlet/imageValidate" alt="验证码" title="验证码不区分大小写" class="checkcode">
                <a href="javascript:void(0);" onclick="changeLevel();return false;" class="btnn btnn-link gray">换一张</a></dd>
            <dt class="label">&nbsp;</dt>
            <dd class="c-h-contain errormsg f_red">&nbsp;</dd>
        </dl>
        <dl class="c-h-2column-left c-h-2column-l120 padding-normal">
            <dt class="label"><em class="cred">*</em>请填写手机验证码：</dt>
            <dd class="c-h-contain">
                <input type="text" maxlength="6" name="verifyCode" id="verifyCode" class="input-default" style="width:185px;">
                <a href="javascript:void(0);" class="btn-sms-sms btnn btnn-default black">获取短信校验码</a>
            </dd>
            <dt class="label">&nbsp;</dt>
            <dd class="c-h-contain errormsg">&nbsp;</dd>
        </dl>
        <div class="code-tip hide" style="margin-bottom: 18px;">验证码已发出，请注意查收短信，如果没有收到，你可以在60秒后要求系统重新发送</div>
        <dl class="c-h-2column-left c-h-2column-l120">
            <dt class="label">&nbsp;</dt>
            <dd class="c-h-contain"><a href="javascript:void(0);" onclick="nextStep('mobile','${BasePath }/merchants/security/authentication.sc<#if from?? && from=='index'>?from=index</#if>','',<#if from?? && from=='index'>'${BasePath }/merchants/login/to_index.sc'<#else>'${BasePath }/merchants/login/to_merchants.sc'</#if>);" class="btnn btnn-primary"><strong>提交</strong></a></dd>
        </dl>
        <input type="hidden" name="mobileCode" id="mobileCode" value="${mobileCode!'' }"/>
        <input name="temp" id="tempVar" type="hidden"/>
    </div>
    <script src="${BasePath}/yougou/js/modifty.js?${style_v}"></script>
</body>

</html>
