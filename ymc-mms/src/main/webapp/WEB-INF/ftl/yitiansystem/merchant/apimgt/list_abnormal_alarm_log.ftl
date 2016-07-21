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
		</div>
	</div>
	<!--工具栏end-->
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr"><span>API日志管理</span></li>
			</ul>
		</div>
		<!--当前位置end-->
		<div class="modify"> 
			<!--搜索开始-->
			<div class="add_detail_box">
				<form id="queryForm" name="queryForm" action="${BasePath}/openapimgt/abnormalalarm/log/list.sc" method="post">
    		       	<label for="receivers">接警人：</label>
    		       	<input type="text" id="receivers" name="receivers" value="<#if apiAbnormalAlarm??>${apiAbnormalAlarm.receivers?default('')}</#if>" />
					<label for="notifyStyle">通知方式：</label>
    		       	<select id="notifyStyle" name="notifyStyle">
    		       		<#list notifyStyles as item>
    		       		<option value="${item.value}" <#if notifyStyle?? && item.value == notifyStyle?default('')>selected="selected"</#if>>${item.label}</option>
    		       		</#list>
    		       	</select>
           			<label for="fromAbnormalTime">异常时间：</label>
           			<input type="text" id="fromAbnormalTime" name="fromAbnormalTime" readonly="readonly" value="<#if fromAbnormalTime??>${fromAbnormalTime?default('')}</#if>" />
           			&nbsp;至&nbsp;
           			<input type="text" id="toAbnormalTime" name="toAbnormalTime" readonly="readonly" value="<#if toAbnormalTime??>${toAbnormalTime?default('')}</#if>" />
           			<br />
           			<label for="code">异常代码：</label>
           			<input type="text" id="code" name="code" value="<#if apiAbnormalAlarm??>${apiAbnormalAlarm.code?default('')}</#if>" />
           			<label for="content">异常内容：</label>
           			<input type="text" id="content" name="content" value="<#if apiAbnormalAlarm??>${apiAbnormalAlarm.content?default('')}</#if>" />
           			<label for="notifyState">通知状态：</label>
    		       	<select id="notifyState" name="notifyState">
    		       		<option value="">请选择</option>
    		       		<#list notifyStates as item>
    		       		<option value="${item.value}" <#if apiAbnormalAlarm?? && item.value == apiAbnormalAlarm.notifyState?default('')>selected="selected"</#if>>${item.label}</option>
    		       		</#list>
    		       	</select>
            		<input type="submit" class="btn-add-normal" value="搜索" />
              	</form>
			</div>
			<!--搜索结束--> 
			<!--列表start-->
			<div class="tbox">
			<table class="list_table" ellspacing="0" cellpadding="0" border="0">
					<thead>
	                    <tr>
	                    <th style="width:10%;">异常代码</th>
	                    <th style="width:35%;">接警人</th>
	                    <th style="width:10%;">异常时间</th>
	                    <th style="width:10%;">通知时间</th>
	                    <th style="width:10%;">通知状态</th>
	                    <th style="width:15%;">通知方式</th>
	                    <th style="width:10%;">操作</th>
	                    </tr>              
	                </thead>
                    <tbody>
                    <#if pageFinder?? && pageFinder.data?? && pageFinder.data?size != 0>
                    	<#list pageFinder.data as item>
                    		<tr>
                    			<td>${item['code']?default('')}</td>
                    			<td>${item['receivers']?default('')}</td>
                    			<td>${item['abnormalTime']?default('')?datetime}</td>
                    			<td>
                    				<#if item['notified']??>
                    				${item['notified']?default('')?datetime}
                    				</#if>
                    			</td>
                    			<td>
                    				<#if item['notifyState']?default('') == 'WAITING'>
                    				待通知
                    				<#elseif item['notifyState']?default('') == 'NOTIFYING'>
                    				通知中
                    				<#elseif item['notifyState']?default('') == 'COMPLETED'>
                    				已通知
                    				<#else>
                    				${item['notifyState']?default('')}
                    				</#if>
                    			</td>
                    			<td>
                    				<#if item['notifyStyle']?default('') == 'EMAIL'>
                    				邮件
                    				<#elseif item['notifyStyle']?default('') == 'SMS'>
                    				短信
                    				<#else>
                    				${item['notifyStyle']?default('')}
                    				</#if>
                    			</td>
                    			<td id="link_${item['_id']}" class="td0">
									<a href="javascript:void(0);" onclick="openIO('${item['_id']}')">I/O详情查看</a>
									<div id="${item['_id']}" style="display: none;">
										<div style="width: 800px; height: 500px; word-break: break-all; word-wrap: break-word; float: left; overflow: scroll;">${item['content']?default('')?html}</div>
									</div>
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
$('#fromAbnormalTime').calendar({maxDate:'#datepicker_end',format:'yyyy-MM-dd HH:mm:ss' }); 
$('#toAbnormalTime').calendar({minDate:'#datepicker_start',format:'yyyy-MM-dd HH:mm:ss' });

function deleteApi(id) {
	if (confirm('确定删除该API?')) {
		$.ajax({ type : 'post', url: '${BasePath}/openapimgt/api/delete.sc', dataType: 'text', data: 'id=' + id,
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

function openIO(id) {
	openDiv({ title: 'I/O详情', width: 800, height: 500, content: '<pre>' + $('#' + id).html() + '</pre>' });
}
</script>
</html>
