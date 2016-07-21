<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-商品-商品预览</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
</head>

<body>
	<form id="previewForm" action="${commodityPreviewUrl!''}" method="post">
		<input type="hidden" name="commodityVo"/>
		<div id="commodityVo" style="display: none;">${commodityVoJsonStr!''}</div>
	</form>
</body>
<script type="text/javascript">
$(document).ready(function(){
	$('[name="commodityVo"]').val(encodeURIComponent($('#commodityVo').html()));
	$('#previewForm').submit();
});
</script>
</html>

