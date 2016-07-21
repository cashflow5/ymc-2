<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#include "supplier_include.ftl">
<title>无标题文档</title>
</head><script type="text/javascript">
</script>
<body>
<div class="container"> 
	<!--工具栏start-->
	<div class="toolbar">
		<div class="t-content"></div>
	</div>
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr"><span>更换仓库日志</span></li>
			</ul>
		</div>
		<!--当前位置end-->
		
		<div class="modify"> 
			<!--搜索start-->
			<div class="add_detail_box">
			</div>
			<table cellpadding="0" cellspacing="0" class="list_table">
				<thead>
					<tr>
						<th>采购单号</th>
						<th>更换前仓库</th>
						<th>更换后仓库</th>
						<th>操作人</th>
						<th>操作时间</th>
					</tr>
				</thead>
				<tbody id="tbd">
				<#if psChangeLogVoList??>
				<#list psChangeLogVoList as item>
				<tr>
					<td>${item.perchaseCode?if_exists}</td>
					<td>${item.preWarehouseName?if_exists}</td>
					<td>${item.lastWarehouseName?if_exists}</td>
					<td>${item.operator?if_exists}</td>
					<td>${item.operateTime?if_exists}</td>
				</tr>
				</#list>
				<#else>
				<tr >
					<td class="td-no" colspan="5"> 抱歉，没有您要找的数据 </td>
				</tr>
				</#if>
				</tbody>
			</table>
		</div>
	</div>
</div>
</body>
</html>
