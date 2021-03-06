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
			<div class="btn" onclick="openwindow('${BasePath}/openapimgt/filter/view.sc',　800,　500,　'添加API过滤器')">
				<span class="btn_l"></span><b class="ico_btn add"></b><span class="btn_txt">添加API过滤器</span><span class="btn_r"></span> </div>
		</div>
	</div>
	<!--工具栏end-->
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr"><span>API过滤器管理</span></li>
			</ul>
		</div>
		<!--当前位置end-->
		<div class="modify"> 
			<!--搜索开始-->
			<div class="add_detail_box">
				<form id="queryForm" name="queryForm" action="${BasePath}/openapimgt/filter/list.sc" method="post">
    		       	<label for="identifier">过滤器标示符：</label>
    		       	<input type="text" id="identifier" name="identifier" value="<#if apiFilter??>${apiFilter.identifier?default('')}</#if>" />
           			<label for="filterClass">过滤器类名称：</label>
           			<input type="text" id="filterClass" name="filterClass" value="<#if apiFilter??>${apiFilter.filterClass?default('')}</#if>" />
            		<input type="submit" class="btn-add-normal" value="搜索" />
              	</form>
			</div>
			<!--搜索结束--> 
			<!--列表start-->
			<div class="tbox">
			<table class="list_table" ellspacing="0" cellpadding="0" border="0">
					<thead>
	                    <tr>
	                    <th style="width:10%;">过滤器标示符</th>
	                    <th style="width:35%;">过滤器类名称</th>
	                    <th style="width:20%;">过滤器规则</th>
	                    <th style="width:10%;">排序号</th>
	                    <th style="width:20%;">描述</th>
	                    <th style="width:10%;">操作</th>
	                    </tr>              
	                </thead>
                    <tbody>
                    <#if pageFinder?? && pageFinder.data?? && pageFinder.data?size != 0>
                    	<#list pageFinder.data as item>
                    		<tr>
                    			<td>${item.identifier?default('')}</td>
                    			<td>${item.filterClass?default('')}</td>
                    			<td>${item.filterRule?default('')}</td>
                    			<td>${item.orderNo?default('')}</td>
                    			<td>${item.description?default('')}</td>
                    			<td id="link_${item.id}" class="td0">
									<a href="javascript:void(0);" onclick="openwindow('${BasePath}/openapimgt/filter/view.sc?identifier=${item.identifier}',　800,　500,　'修改API过滤器')">修改</a>
									<a href="javascript:void(0);" onclick="deleteFilter('${item.identifier}')">删除</a>
 								</td>
                    		</tr>
                    	</#list>
                    <#else>
                		<tr>
                			<td colspan="9" align="center">没有相关记录！</td>
                		</tr>
                    </#if>
                    </tbody>
				</table>
				</div>
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
function deleteFilter(identifier) {
	if (confirm('确定删除该过滤器?')) {
		$.ajax({ type : 'post', url: '${BasePath}/openapimgt/filter/delete.sc', dataType: 'text', data: 'identifier=' + identifier,
			success: function(data, textStatus) {
				if ((typeof(data) == 'boolean' && data) || $.trim(data) == 'true') {
					alert('删除成功!');
					$('.btn-add-normal').click();
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
