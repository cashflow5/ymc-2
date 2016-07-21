<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-首页</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/iframeMessager.js"></script>
</head>

<body>
	
	
	<div class="main_container">
		<iframe frameborder="0" id="myIframe" src="${help_url}" scrolling="no" style="width:100%;height:100%;"></iframe>
	</div>
	
	<script>
	var messenger = Messenger.initInParent(document.getElementById('myIframe'));
      messenger.onmessage = function (data) {
          $('#myIframe').height(data);
      };
	</script>
</body>
</html>