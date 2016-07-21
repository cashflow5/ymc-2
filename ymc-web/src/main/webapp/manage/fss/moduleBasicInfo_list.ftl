<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>店铺装修-品牌旗舰店-优购网</title>
</head>
<body>
<div class="main_container">
	<div class="normal_box">
		<p class="title site">当前位置：商家中心 &gt; 我的店铺 &gt; 模块管理</p>
		<div class="tab_panel">
			<ul class="tab">
				<li class="curr">
					<span>模块列表</span>
				</li>
			</ul>
			<div class="tab_content"> 
				<!--列表start-->
				<table class="list_table">
					<thead>
						<tr>
							<th width="5%">NO.</th>
							<th width="15%">模块编号</th>
							<th width="20%">模块名称</th>
							<th width="20">ICON</th>
							<th width="40%">模块描述</th>
						</tr>
					</thead>
					<tbody>
						<#if dataList?? && (dataList?size > 0 )>
				  			<#list dataList as item>
							<tr>
								<td>${item_index+1}</td>
								<td>${(item.code) !}</td>
								<td>${(item.name) !}</td>
								<td><img src="http://kaidian.yougou.com${(item.iconUrl) !}" width="60" height="60"  /></td>
								<td>${(item.description) !}</td>
							</tr>
							</#list>
						<#else>
							<tr>
								<td class="td-no" colspan="5">暂无数据!</td>
							</tr>
						</#if>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
</body>
</html>
