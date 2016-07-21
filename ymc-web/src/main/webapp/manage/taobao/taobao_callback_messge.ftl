<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-淘宝店铺授权</title>
<script type="text/javascript" src="${BasePath}/yougou/js/manage/common.util.js"></script>
<script type="text/JavaScript">
function delayURL(url) {
		var delay = document.getElementById("time").innerHTML;
		if(delay > 0) {
			delay--;
			document.getElementById("time").innerHTML = delay;
		} else {
			window.top.location.href = url;
		}
		setTimeout("delayURL('" + url + "')", 1000);
}
</script>
</head>

<body>
	<div class="main_container">
		<div class="normal_box">
			<p class="title site">当前位置：商家中心 &gt; 淘宝店铺授权</p>
			<div class="tab_panel">
				<ul class="tab">
					<li class="curr"><span>授权结果</span></li>
				</ul>
				<div class="tab_content">
					<div class="page_detail">
                    	<img style="vertical-align: bottom;" width="64" height="64" src="${BasePath}/yougou/images/<#if success??&&success==true>ok.png<#else>error-b.png</#if>"/>${message}<span id="time" style="color: #FF6600">10</span>秒钟后自动返回，如果不跳转，请点击下面的链接<a href="${BasePath}/merchants/login/to_index.sc">返回</a>
                    	<script type="text/javascript">
							delayURL("${BasePath}/taobao/goTaobaoShop.sc");
						</script>
                    </div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>