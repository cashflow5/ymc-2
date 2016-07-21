<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<#include "../merchants-include.ftl">
	<script>
		var taobaoYougouBrand = {};
		taobaoYougouBrand.basePath = "${BasePath}";
	</script>
	<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>
	<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script>
	<script type="text/javascript" src="${BasePath}/js/yitiansystem/merchants/taobao/yougou.taobao.brand.js?${version}"></script>
	<title>优购商城--商家后台</title>
</head>
<body>
	<div class="continer">
		<!--工具栏START-->
		<div class="toolbar">
			<div class="t-content">
				<div class="btn" onclick="location.href='goYougouTaobaoBrandBind.sc'">
					<span class="btn_l"></span> <b class="ico_btn add"></b>
					<span class="btn_txt">添加品牌对应</span>
					<span class="btn_r"></span>
				</div>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<div class="btn"  align="right">
					&nbsp;&nbsp;
					<span class="btn_txt" >
					&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;" onclick="viewLog();">查看日志</a>
				    </span>
				</div>
			</div>
		</div>
		<div class="list_content">
			<div class="top clearfix">
				<ul class="tab">
					<li  class="curr">
						<span>
							<a href="#" class="btn-onselc">品牌对应列表</a>
						</span>
					</li>
				</ul>
			</div>
			<div class="modify">
				<form action="goYougouTaobaoItemCat.sc" id="queryVoform" name="queryVoform" method="post">
					<table width="80%" class="bind-table">
						<tr class="form-table-tr" style="height:40px;">
							<td>优购品牌：</td>
							<td>
								<input type="text" name="yougouBrandName">
							</td>
							<td style="padding-left:100px;">
								淘宝品牌：
							</td>
							<td id="taobaoSelt">
								<input type="text" name="taobaoBrandName">
							</td><td style="padding-left:100px;">
								VID：
							</td>
							<td id="taobaoSelt">
								<input type="text" name="vid">
							</td>
							<td>
								<input type="button" value="搜索" onclick="search();" class="yt-seach-btn" />
							</td>
						</tr>
					</table>
				</form>
				<div id="content_list">
					
				</div>
			</div>
		</div>
</body>

<script>
function viewLog(){
	    openwindow("${BasePath}/yitiansystem/taobao/log.sc?type=TAOBAO_B_BIND", 900, 700, "查看日志");
    }
</script>
</html>