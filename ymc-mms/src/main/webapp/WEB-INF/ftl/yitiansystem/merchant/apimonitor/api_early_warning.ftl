<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#setting number_format="#">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>

<script type="text/javascript" src="${BasePath}/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js"></script>
<!-- 日期 -->
<script src="${BasePath}/js/common/form/datepicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/AutoComplete.js"></script>

<style type="text/css">
#queryinput_appkey_div li{padding: 1px;height:20px;}
</style>

<title>招商--商家后台--API预警统计</title>
</head>
<body>
<div class="container">
	<!--工具栏start-->
	<div class="toolbar">
		<div class="t-content"> <!--操作按钮start--> 
			<div class="btn" onclick="to_export_warn_report();">
				<span class="btn_l"></span>
	        	<b class="ico_btn add"></b>
	        	<span class="btn_txt">导出数据</span>
	        	<span class="btn_r"></span>
        	</div> 
		</div>
	</div>
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr">
				  <span>API预警统计</span>
				</li>
			</ul>
		</div>
		
		<div class="modify">
		<form action="${BasePath}/api/monitor/manage/to_earlywarning_list.sc" name="queryForm" id="queryForm" method="post"> 
			<div class="wms-top">
  			  		<label>AppKey持有者：</label>
					<#--<input type="text" id="appKeyHolder" name="appKeyHolder" value="${appKeyHolder!''}" />-->
  			  		<input mytype="sq" onkeyup="AutoSuggest(queryAppKey(this),this,event);" style="width: 300px" autocomplete="off" id="queryinput_appkey">
			  		<div id="queryinput_appkey_div" style="height:300px;position: absolute; display: none; border: 1px solid #817F82; background-color: #FFFFFF;"></div>
  			  		
  			  		<input type="hidden" id="appKey" name="appKey" />
  			  		<label>查询日期从：</label>
  			  		<input type="text" id="startTime" name="startTime" width="50px" readonly="readonly" value="<#if startTime??>${startTime?default('')}</#if>" />
           			&nbsp;至&nbsp;
           			<input type="text" id="endTime" name="endTime" readonly="readonly" value="<#if endTime??>${endTime?default('')}</#if>" /><span style="color:red;">&nbsp;默认查询半月内的数据</span>
           			<br />
                    <input type="button" value="搜索" onclick="queryEarlyWarningList();" class="yt-seach-btn" />&nbsp;&nbsp;&nbsp;
                    <input type="button" value="重置" onclick="reset();" class="yt-seach-btn" />
			</div>
		</form>
		
		<div>
			<table cellpadding="0" cellspacing="0" class="list_table">
				<thead>
					<tr>
						<th style="font-weight:bold;">序号</th>
                        <th style="width:180px;font-weight:bold;">AppKey持有者</th>
                        <th style="font-weight:bold;">AppKey日调用次数预警[次]</th>
                        <th style="font-weight:bold;">单接口日调用次数预警[次]</th>
                        <th style="font-weight:bold;">单接口频率预警[次]</th>
                        <th style="font-weight:bold;">单接口调用成功率预警[次]</th>
                    </tr>
				</thead>
				<tbody>
				<#if pageFinder??&&pageFinder.data??&& pageFinder.data?size != 0>
					<#list pageFinder.data as item>
					<tr>
						<td>${item_index + 1}</td>
		                <td>${item.appKeyHolder!'-'}</td>
		                <td>
		                	<a href="#" onclick="openAppKeyearlyWarnDetail('${item.appKey!''}', '${startTime!''}', '${endTime?default('')}', ${item.warmAppkeyCallCount!0});">${item.warmAppkeyCallCount!0}</a>
		               	</td>
						<td>
							<a href="#" onclick="openApiearlyWarnDetail('${item.appKey!''}', '${startTime!''}', '${endTime?default('')}', ${item.warmDayCallCount!0});">${item.warmDayCallCount!0}</a>
						</td>
						<td>
							<a href="#" onclick="openFrequencyEarlyWarnDetail('${item.appKey!''}', '${startTime!''}', '${endTime?default('')}', ${item.warmRateCount!0});">${item.warmRateCount!0}</a>
						</td>
						<td>
							<a href="#" onclick="openSuccRateearlyWarnDetail('${item.appKey!''}', '${startTime!''}', '${endTime?default('')}', ${item.warmSuccessCount!0});">${item.warmSuccessCount!0}</a>
						</td>
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
		<div>
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

function reset() {
	$('#appKeyHolder').val('');
	$('#startTime').val('');
	$('#endTime').val('');
}

function queryEarlyWarningList() {
	$('#appKey').val($('#queryinput_appkey').attr('mykey'));
	$('#queryForm').submit();
}

$(function($) {
	//设置默认初始时间 半个月的数据
	var startTime = $('#startTime').val();
	var endTime = $('#endTime').val();
  	
  	if (startTime == '' && endTime == '') {
  		$('#startTime').val(dateDiff(new Date(), 15));
  		$('#endTime').val((new Date()).Format("yyyy-MM-dd"));
  	}
});

function queryAppKey(input) {
    var backdata="";
    $.ajax({
        type: "POST",
        url: "${BasePath}/api/monitor/manage/queryAppKey.sc",
        data: {"appKeyText":$(input).val()},
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        async:false,
        dataType: "json",
        success: function (data) {
			backdata=data;
        }
    });
	return backdata;
}

// 在时间date的基础上减去天数
function dateDiff(date, diff)
{
	var a = date.valueOf();
	a = a - diff * 24 * 60 * 60 * 1000
	a = new Date(a)
	return a.Format("yyyy-MM-dd");
}

// 对Date的扩展，将 Date 转化为指定格式的String   
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，   
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)   
// 例子：   
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423   
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18   
Date.prototype.Format = function(fmt)   
{ //author: meizz   
  var o = {   
    "M+" : this.getMonth()+1,                 //月份   
    "d+" : this.getDate(),                    //日   
    "h+" : this.getHours(),                   //小时   
    "m+" : this.getMinutes(),                 //分   
    "s+" : this.getSeconds(),                 //秒   
    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
    "S"  : this.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
}

//appKey日调用次数预警明细
function openAppKeyearlyWarnDetail(appKey, startTime, endTime, count) {
	if (count > 0) {
		ygdgDialog.open('${BasePath}/api/monitor/manage/to_appkey_earlywarn_detail.sc?appKey=' + appKey + '&startTime=' + startTime + '&endTime=' + endTime, {width:600,height:300,title:'AppKey日调用次数预警明细'});	
	}
}
//API日调用次数预警明细
function openApiearlyWarnDetail(appKey, startTime, endTime, count) {
	if (count > 0) {
		ygdgDialog.open('${BasePath}/api/monitor/manage/to_api_earlywarn_detail.sc?appKey=' + appKey + '&startTime=' + startTime + '&endTime=' + endTime, {width:600,height:300,title:'API单接口日调用次数预警'});
	}
}

//API调用频率预警明细
function openFrequencyEarlyWarnDetail(appKey, startTime, endTime, count) {
	if (count > 0) {
		ygdgDialog.open('${BasePath}/api/monitor/manage/to_frequency_rate_earlywarn_detail.sc?appKey=' + appKey + '&startTime=' + startTime + '&endTime=' + endTime, {width:600,height:300,title:'API单接口频率预警明细'});
	}
}
//API调用成功率预警明细
function openSuccRateearlyWarnDetail(appKey, startTime, endTime, count) {
	if (count > 0) {
		ygdgDialog.open('${BasePath}/api/monitor/manage/to_succrate_earlywarn_detail.sc?appKey=' + appKey + '&startTime=' + startTime + '&endTime=' + endTime, {width:600,height:300,title:'单接口调用成功率预警明细'});
	}
}

function to_export_warn_report() {
	$('#queryForm').attr('action', '${BasePath}/api/monitor/manage/to_earlywarning_list.sc?isExport=1');
	$('#queryForm').submit();
}
</script>