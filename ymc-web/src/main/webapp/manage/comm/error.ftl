<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>优购商家中心-系统出错了</title>
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
	<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
	<style type="text/css">
		.newError-page {
		    color: #535353;
		    padding-bottom: 50px;
		    padding-left: 140px;
		    padding-top: 58px;
		    position: relative;
		    width: 643px;
		}
		.newError-page h3 {
		    color: #FF5000;
		    font-family: '微软雅黑','Microsoft YaHei';
		    font-size: 18px;
		}
		.newError-page .errorImages {
		    left: 80px;
		    position: absolute;
		    top: 46px;
		}
	</style>
</head>

<body>

	<div class="main_container">
		<div class="normal_box">
			<p class="title site">当前位置： 异常</p>
			<div class="newError-page">
				<h3>${errorDesc?default('系统出错了')}</h3>
				<#--<span style="color:red;">${errorDesc?default('系统出错了')}</span> -->
				<img class="errorImages" src="${BasePath}/yougou/images/warn_mid_icon.jpg">
			</div>
		</div>
	</div>
</body>
</html>

