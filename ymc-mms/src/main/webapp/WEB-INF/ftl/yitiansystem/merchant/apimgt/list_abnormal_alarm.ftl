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
			<div class="btn" onclick="openwindow('${BasePath}/openapimgt/abnormalalarm/view.sc',　800,　500,　'添加API异常报警器')">
				<span class="btn_l"></span><b class="ico_btn add"></b><span class="btn_txt">添加API异常报警器</span><span class="btn_r"></span> </div>
		</div>
	</div>
	<!--工具栏end-->
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr"><span>API异常报警器管理</span></li>
			</ul>
		</div>
		<!--当前位置end-->
		<div class="modify"> 
			<!--搜索开始-->
			<div class="add_detail_box">
				<form id="queryForm" name="queryForm" action="${BasePath}/openapimgt/abnormalalarm/list.sc" method="post">
    		       	<label for="alarmCauserCode">异常报警器代码：</label>
    		       	<input type="text" id="alarmCauserCode" name="alarmCauserCode" value="<#if apiAbnormalAlarm??>${apiAbnormalAlarm.alarmCauserCode!''}</#if>" />
           			<label for="alarmCauserClass">异常报警器类名：</label>
           			<input type="text" id="alarmCauserClass" name="alarmCauserClass" value="<#if apiAbnormalAlarm??>${apiAbnormalAlarm.alarmCauserClass!''}</#if>" />
            		<input type="submit" class="btn-add-normal" value="搜索" />
              	</form>
			</div>
			<!--搜索结束--> 
			<!--列表start-->
			<div class="tbox">
			<table class="list_table" ellspacing="0" cellpadding="0" border="0">
					<thead>
	                    <tr>
	                    <th style="width:10%;">异常报警器代码</th>
	                    <th style="width:15%;">异常报警器类名</th>
	                    <th style="width:15%;">事件权重</th>
	                    <th style="width:10%;">接警人邮件</th>
	                    <th style="width:10%;">接警人手机</th>
	                    <th style="width:10%;">忽略次数</th>
	                    <th style="width:10%;">周期时间轴</th>
	                    <th style="width:10%;">描述</th>
	                    <th style="width:10%;">功能操作</th>
	                    </tr>              
	                </thead>
                    <tbody>
                    <#if pageFinder?? && pageFinder.data?? && pageFinder.data?size != 0>
                    	<#list pageFinder.data as item >
                    		<tr>
                    			<td>${item.alarmCauserCode!""}</td>
                    			<td>${item.alarmCauserClass!""}</td>
                    			<td>
                    			<#if bitwise_and(item.alarmCauserWeight, 1) == 1>
                    			邮件
                    			</#if>
                    			<#if bitwise_and(item.alarmCauserWeight, 2) == 2>
                    			短信
                    			</#if>
                    			</td>
                    			<td>${item.alarmReceiverEmail!""}</td>
                    			<td>${item.alarmReceiverPhone!""}</td>
                    			<td>${item.ignoreNumbers!""}</td>
                    			<td>${item.cycleTimeline?c!""}</td>
                    			<td>${item.description?default('')}</td>
                    			<td id="link_${item.id}" class="td0">
									<a href="javascript:void(0);" onclick="openwindow('${BasePath}/openapimgt/abnormalalarm/view.sc?id=${item.id}',　800,　500,　'修改API异常报警器')">修改</a>
									<a href="javascript:void(0);" onclick="deleteAbnormalAlarm('${item.id}')">删除</a>
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
function deleteAbnormalAlarm(id) {
	if (confirm('确定删除该异常报警器?')) {
		$.ajax({ type : 'post', url: '${BasePath}/openapimgt/abnormalalarm/delete.sc', dataType: 'text', data: 'id=' + id,
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
