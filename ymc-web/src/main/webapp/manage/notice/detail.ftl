<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-在售商品</title>
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
			<p class="title site">当前位置：商家中心 &gt; 公告详情</p>
			<div class="tab_panel">
				<ul class="tab">
					<li class="curr"><span>公告详情</span></li>
				</ul>
				<div class="tab_content">
					<div class="page_detail">
					    <#if mctNotice??>
	                    	<h1 class="t">【<#if mctNotice.noticeType=="1" >新功能<#else>重要通知</#if>】${mctNotice.title!''}</h1>
	                        <p class="c9 mt15">发布日期：<#if mctNotice.createTime??>${mctNotice.createTime?string("yyyy-MM-dd HH:mm:ss")}</#if></p>
	                        <div class="detail_c mt30">
	                           ${mctNotice.content!''}
	                        </div>
	                        <p class="c9 mt15" style="text-align:right;">优购招商运营</p>
	                    <#else>
	                    	对不起,该条公告不存在,可能已经被删除!<span id="time" style="color: #FF6600">5</span>秒钟后自动返回首页，如果不跳转，请点击下面的链接<a href="${BasePath}/merchants/login/to_index.sc">返回首页</a>
	                    	<script type="text/javascript">
								delayURL("${BasePath}/merchants/login/to_index.sc");
							</script>
	                    </#if>
                    </div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>