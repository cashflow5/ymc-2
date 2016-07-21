<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商城--优购平台</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
</head>
<body>

<div class="container"> 
	<!--工具栏start-->
	<div class="toolbar">
		<div class="t-content">
			<div class="btn" onclick="openwindow('${BasePath}/openapimgt/version/view.sc',　800,　500,　'添加API版本')">
				<span class="btn_l"></span><b class="ico_btn add"></b><span class="btn_txt">添加API版本</span><span class="btn_r"></span> </div>
		</div>
	</div>
	<!--工具栏end-->
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr"><span>API版本管理</span></li>
			</ul>
		</div>
		<!--当前位置end-->
		<div class="modify"> 
			<!--搜索开始-->
			<div class="add_detail_box">
			</div>
			<!--搜索结束--> 
			<!--列表start-->
			<table class="list_table" ellspacing="0" cellpadding="0" border="0">
					<thead>
	                    <tr>
	                    <th style="width:50%;">API版本号</th>
	                    <th style="width:40%;">描述</th>
	                    <th style="width:10%;">操作</th>
	                    </tr>              
	                </thead>
                    <tbody>
                    <#if apiVersions?? && apiVersions?size != 0>
                    	<#list apiVersions as item >
                    		<tr>
                    			<td>${item.versionNo?default('')}</td>
                    			<td>${item.description?default('')}</td>
                    			<td>
                    				<a href="${BasePath}/openapimgt/version/view.sc?id=${item.id}">修改</a>
                    				<a href="javascript:void(0);" onclick="deleteApiVersion('${item.id}')">删除</a>
                    			</td>
                    		</tr>
                    	</#list>
                    <#else>
                		<tr>
                			<td colspan="8" align="center">没有相关记录！</td>
                		</tr>
                    </#if>
                    </tbody>
				</table>
			</div>
			<div class="bottom clearfix">
				<#if pageFinder??>
					<#import "../../../common.ftl" as page>
					<@page.queryForm formId="queryForm"/>
				</#if>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
</body>
<script type="text/javascript">
function deleteApiVersion(id) {
	if (confirm('确定删除该API版本?')) {
		$.ajax({ type : 'post', url: '${BasePath}/openapimgt/version/delete.sc', dataType: 'text', data: 'id=' + id,
			success: function(data, textStatus) {
				if ((typeof(data) == 'boolean' && data) || $.trim(data) == 'true') {
					alert('删除成功!');
					refreshpage();
				} else {
					alert(data);
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert(textStatus.toUpperCase() + ' : ' + XMLHttpRequest.responseText);
			}
		});
	}
}
</script>
</html>
