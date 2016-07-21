<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css" />
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.8.2.min.js"></script> 
<title>优购商城--商家后台</title>
<style>
pre {
	white-space: pre-wrap; /* css-3 */
	white-space: -moz-pre-wrap; /* Mozilla, since 1999 */
	white-space: -pre-wrap; /* Opera 4-6 */
	white-space: -o-pre-wrap; /* Opera 7 */
}
* html pre {
	word-wrap: break-word; /* Internet Explorer 5.5+ */
	white-space: normal; /* Internet Explorer 5.5+ */
}
</style>
</head>

<body>
	<div class="container">
		<!--工具栏start-->
		<div class="toolbar">
			<div class="t-content"></div>
		</div>
		<div class="list_content">
			<div class="top clearfix">
				<ul class="tab">
					<li class='curr'><span><a
							href="${BasePath}/yitiansystem/merchants/training/training_log.sc?trainingId=<#if trainingId??>${trainingId!''}</#if>">查看日志</a></span></li>
				</ul>
			</div>
			<div class="modify">
				<form id="queryForm" name="queryForm" action="${BasePath}/yitiansystem/merchants/training/training_log.sc" method="post">
				<input type="hidden" name="trainingId" value="<#if trainingId??>${trainingId!''}</#if>"/>
				<table cellpadding="0" cellspacing="0" class="list_table">
					<thead>
						<tr>
							<th>操作类型</th>
							<th>操作时间</th>
							<th>操作人</th>
							<th>操作内容</th>
						</tr>
					</thead>
					<tbody>
						<#if pageFinder?? && pageFinder.data??>
							<#list pageFinder.data as item >
								<tr>
									<td>${item.operation!""}</td>
									<td>${item.operated!""}</td>
									<td>${item.operator!""}</td>
									<td>
										<div style="width: 500px; word-break: break-all;word-wrap:break-word;word-break:normal;float:left; ">
										<pre>${item.operationDesc!""}</pre>
										</div>
									</td>
								</tr>
							</#list>
						<#else>
							<tr>
								<td colSpan="10">抱歉，没有您要找的数据</td>
							</tr>
						</#if>
					</tbody>
				</table>
				</form>
			</div>
			<div class="bottom clearfix">
			<#if pageFinder ??>
				<#import "../../common.ftl" as page>
				<@page.queryForm formId="queryForm"/>
			</#if>
			</div>
			<div class="blank20"></div>
		</div>
	</div>
</body>
</html>

<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
