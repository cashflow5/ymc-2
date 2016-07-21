<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>优购-商家中心-赔付管理列表</title>
    <link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css" />
    <link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css" />
</head>

<body>
		<div class="blank20"></div>
		<div class="blank20"></div>
        <dl class="c-h-2column-left c-h-2column-l120 padding-normal clearfix">
            <dt class="label">未退回数量：</dt>
            <dd class="c-h-contain">${noBackCount!'0'}</dd>
        </dl>
    	<dl class="c-h-2column-left c-h-2column-l120 padding-normal clearfix">
            <dt class="label"><em class="cred">*</em>当前退回数量：</dt>
            <dd class="c-h-contain">
            	<input id="receiveCount" name="receiveCount" value="${noBackCount!'0'}" class="inputtxt" style="width:185px;" type="text">
            </dd>
        </dl>
        <div class="blank20"></div>
</body>

</html>
