<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<#include "../merchants-include.ftl">
	<script>
		var taobaoYougouItemCat = {};
		taobaoYougouItemCat.basePath = "${BasePath}";
	</script>
	<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>
	<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script>
	<script type="text/javascript" src="${BasePath}/js/yitiansystem/merchants/taobao/yougou.taobao.itembind.js?${version}"></script>
	<link rel="stylesheet" type="text/css" href="${BasePath}/css/yitiansystem/merchants/taobao/taobao.binditem.css?${version}">
	<title>优购商城--商家后台</title>
</head>
<body>
	<div class="continer">
		<!--工具栏START-->
		<div class="toolbar">
			<div class="t-content">
				<div class="btn" onclick="location.href='goBindCat.sc'">
					<span class="btn_l"></span> <b class="ico_btn add"></b>
					<span class="btn_txt">添加分类对应</span>
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
							<a href="#" class="btn-onselc">分类对应列表</a>
						</span>
					</li>
				</ul>
			</div>
			<div class="modify">
				<form action="goYougouTaobaoItemCat.sc" id="queryVoform" name="queryVoform" method="post">
					<table>
						<tr class="form-table-tr">
							<td>优购分类：</td>
							<td>
								<select id="sel1" style="width:100px;">
									<option value="" selected="selected">一级分类</option>
									<#list yougouTree as item>
										<#if item.pId=='0'>
											<option value="${(item.structName)!""}">${(item.name)!""}</option>
										</#if>
							    	</#list>
								</select>
								<select id="sel2" style="width:100px;">
									<option value="" selected="selected">二级分类</option>
								</select>
								<select id="sel3" name="catId" style="width:100px;">
									<option value="" selected="selected">三级分类</option>
								</select>
							</td>
							<td style="padding-left:100px;">
								淘宝分类：
							</td>
							<td id="taobaoSelt">
								<select id="selt1" style="width:100px;">
									<option value="" selected="selected">一级分类</option>
									<#list taobaoTree as item>
							    		<option value="${(item.id)!""}">${(item.name)!""}</option>
							    	</#list>
								</select>
								<select id="selt2" style="width:100px;">
									<option value="" selected="selected">二级分类</option>
								</select>
							</td>
							<td>
								<input type="hidden" name="taobaoCid" id="taobaoCid">
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
	    openwindow("${BasePath}/yitiansystem/taobao/log.sc?type=TAOBAO_CAT_BIND", 900, 700, "查看日志");
    }
</script>
</html>