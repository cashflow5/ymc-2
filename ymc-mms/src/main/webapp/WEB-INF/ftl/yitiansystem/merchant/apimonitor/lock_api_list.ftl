<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>

<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js"></script>
<!-- 日期 -->
<script src="${BasePath}/js/common/form/datepicker/WdatePicker.js" type="text/javascript"></script>
<title>招商--商家后台--锁定API</title>
</head>
<body>
<div class="container">
	<!--工具栏start-->
	<div class="toolbar">
		<div class="t-content"> <!--操作按钮start--> 
			<div class="btn" onclick="toBatch_unlockApi();">
				<span class="btn_l"></span>
	        	<b class="ico_btn add"></b>
	        	<span class="btn_txt">批量解锁</span>
	        	<span class="btn_r"></span>
        	</div> 
		</div>
	</div>
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li id="li_invalid_ip" class="curr">
				  <span><a href="#" class="btn-onselc">锁定API管理</a></span>
				</li>
			</ul>
		</div>
 		<div class="modify" id="div_invalid_ip">
     		<form action="${BasePath}/api/monitor/manage/to_apilocklists.sc" name="queryForm" id="queryForm" method="post"> 
				<div class="wms-top">
					<span width="20%">
						<label>AppKey持有者: </label>
						<input type="text" name="merchantCode" id="merchantCode" value="<#if monitorLock.merchantCode??>${monitorLock.merchantCode!""}</#if>"/>&nbsp;&nbsp;&nbsp;
					</span>
					<span width="20%">
						<label>接口: </label>
						<select id="apiId" name="apiId">
							<option value="">全部</option>
	                    </select>
                    </span>
                    <span width="100px;">
						<label>锁定类型: </label>
						<select id="lockType" name="lockType">
							<option value=-1>全部</option>
	                     	<option value=0 <#if monitorLock.lockType?? && monitorLock.lockType == 0>selected</#if>>频率锁定</option>
	                     	<option value=1 <#if monitorLock.lockType?? && monitorLock.lockType == 1>selected</#if>>流量锁定</option>
	                    </select>
                    </span>
					<br />
					<span width="100px;">
						<label>状态: </label>
						<select id="lockStatus" name="lockStatus">
							<option value=-1>全部</option>
	                     	<option value=0 <#if monitorLock.lockStatus?? && monitorLock.lockStatus == 0>selected</#if>>已解锁</option>
	                     	<option value=1 <#if monitorLock.lockStatus?? && monitorLock.lockStatus == 1>selected</#if>>已锁定</option>
	                    </select>
                    </span>
                    <!--<label>剩余锁定时长小于： </label> 小时-->
                    <span width="100px;">
	  			  		<label>锁定日期：</label>
	  			  		<input type="text" id="startTime" name="startTime" width="50px" readonly="readonly" value="<#if monitorLock.startTime??>${monitorLock.startTime?default('')}</#if>" />
	           			&nbsp;至&nbsp;
	           			<input type="text" id="endTime" name="endTime" readonly="readonly" value="<#if monitorLock.endTime??>${monitorLock.endTime?default('')}</#if>" />
           			</span>
                    <input type="button" value="搜索" onclick="queryApilocklists();" class="yt-seach-btn" />&nbsp;&nbsp;&nbsp;
                    <input type="button" value="重置" onclick="toReset();" class="yt-seach-btn" />
				</div>
			</form>
			<table cellpadding="0" cellspacing="0" class="list_table">
				<thead>
					<tr>
                        <th width="30px;">序号</th>
                        <th width="60px;">
                        	<input type="checkbox" id="all_check" value="-1" onclick="allCheck();" />&nbsp;全选
                        	<!-- input type="button" value="批量解锁" onclick="toBatch_add_backlist();" class=".yt-seach-btn-4ft" /-->
                        </th>
                        <th width="220px;">AppKey持有者</th>
                        <th>锁定对象</th>
                        <th>锁定类型</th>
                        <th>锁定时间</th>
                        <th>已锁时长</th>
                        <th>剩余锁定时长</th>
                        <th>状态</th>
                        <th>解锁时间</th>
                        <th>操作</th>
                    </tr>
				</thead>
				<tbody>
				<#if pageFinder??&&pageFinder.data?? && pageFinder.data?size != 0>
					<#list pageFinder.data as item>
					<tr>
						<td>${item_index + 1}</td>
		                <td>
		                	<#if item.lockStatus?? && item.lockStatus==1>
		                		<input type="checkbox" name="ip_check" value="${item.id}" />
		                	</#if>
		                </td>
		                <td>${item.appKeyHolder!'-'}</td>
						<td>${item.apiName!'-'}</td>
						<td><#if item.lockType == 0>
								频率锁定
							<#elseif item.lockType == 1>
								流量锁定
							</#if>
						</td>
						<td>${item.lockTime?string("yyyy-MM-dd HH:mm:ss")}</td>
						<td>${item.lockTimeStr!'-'}</td>
						<td>${item.remainLockTimeStr!'-'}</td>
						<td>
							<#if item.lockStatus == 0>
								已解锁
							<#elseif item.lockStatus == 1>
								已锁定
							</#if>
						</td>
						<td>${item.unlockTime?string("yyyy-MM-dd HH:mm:ss")}</td>
						<td>
							<#if item.lockStatus?? && item.lockStatus==1>
							<a href="#" onclick="unlockApi('${item.id!''}');">解锁</a>
							</#if>
	                    </tr>
                    </#list>
				<#else>
                	<tr>
                    	<td colSpan="10">
                        	抱歉，没有您要找的数据 
	                  	</td>
	               	</tr>
                </#if>
                </tbody>
			</table>
		</div>
		<div class="bottom clearfix">
			<#if pageFinder ??><#import "../../../common.ftl" as page>
			  		<@page.queryForm formId="queryForm"/></#if>
		</div>
        <div class="blank20"></div>
       	</div>
</div>
</body>
</html>
<script type="text/javascript">
$('#startTime').calendar({maxDate:'#endTime',format:'yyyy-MM-dd' }); 
$('#endTime').calendar({minDate:'#startTime',format:'yyyy-MM-dd' });

//批量加入黑名单
function toBatch_unlockApi() {
	var ips = '';
	$("input[name='ip_check']:checked").each(function(){
		ips += $(this).val() + ',';	
	});
	unlockApi(ips);
}

//重置
function toReset() {
    $('#merchantCode').val('');
    $('#apiId').val('');
    $('#lockType').val(-1);
	$('#lockStatus').val(-1);
	$('#startTime').val('');
	$('#endTime').val('');
	queryApilocklists();
}

//全选
function allCheck() {
	var check_flag = $('#all_check').attr("checked");
	if (check_flag) {
		$("input[name='ip_check']").attr("checked",true);      //设置所有name为'ip_check'对象的checked为true
	} else {
		$("input[name='ip_check']").attr("checked",false);      //设置所有name为'ip_check'对象的checked为true
	}
}

//根据条件查询可疑IP列表
function queryApilocklists(){
   	document.queryForm.method="post";
   	document.queryForm.submit();
}

//移除黑名单
function unlockApi(ids) {
	if (confirm("是否确定解锁!"))  {
		$.ajax({
		type: 'post',
		url: '${BasePath}/api/monitor/manage/unlock_api.sc',
		dataType: 'json',
		data: {id:ids},
		success: function(data, textStatus) {
		 	//dialog.close();
		 	location.href="${BasePath}/api/monitor/manage/to_apilocklists.sc";
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			ygdg.dialog.alert(XMLHttpRequest.responseText);
		}
	});	
	} 
}
</script>