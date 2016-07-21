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
<title>招商--商家后台--IP监控</title>
</head>
<body>
<div class="container">
	<!--工具栏start-->
	<div class="toolbar">
		<div class="t-content"> <!--操作按钮start--> 
			<div id="batch_toolbar" <#if flag??&&flag==2>style="display:none;"</#if> class="btn" onclick="toBatch_add_backlist();">
				<span class="btn_l"></span>
	        	<b class="ico_btn add"></b>
	        	<span class="btn_txt">批量加入黑名单</span>
	        	<span class="btn_r"></span>
        	</div> 
		</div>
	</div>
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li id="li_invalid_ip" <#if flag??&&flag==1>class="curr"</#if>>
				  <span><a href="#" onclick="onclick_tab(1);" class="btn-onselc">可疑IP</a></span>
				</li>
				<li id="li_blacklist_ip" <#if flag??&&flag==2>class="curr"</#if>>
				  <span><a href="#" onclick="onclick_tab(2);" class="btn-onselc">IP黑名单</a></span>
				</li>
			</ul>
		</div>
		<input type="hidden" id="flag" name="flag" value="${flag?default(1)}" />
		<#if flag??&&flag==1>
 		<div class="modify" id="div_invalid_ip">
     		<form action="${BasePath}/api/monitor/manage/to_invalidIps.sc" name="queryForm" id="queryForm" method="post"> 
				<div class="wms-top">
  			  		<label>查询日期：</label>
  			  		<input type="text" id="startTime" name="startTime" width="50px" readonly="readonly" value="<#if startTime??>${startTime?default('')}</#if>" />
           			&nbsp;至&nbsp;
           			<input type="text" id="endTime" name="endTime" readonly="readonly" value="<#if endTime??>${endTime?default('')}</#if>" />
           			<span>
						<label style="width: 30px;">&nbsp;</label>
						<input type="hidden" id="mark" name="mark" value="${mark}" />
						<a href="javascript:changeTimeMark('1');" <#if mark?default('-1')=='1'>class="msg-active"</#if> >今天</a>
						<a href="javascript:changeTimeMark('2');" <#if mark?default('-1')=='2'>class="msg-active"</#if>>昨天</a>
						<a href="javascript:changeTimeMark('3');" <#if mark?default('-1')=='3'>class="msg-active"</#if>>一周内</a>
						<a href="javascript:changeTimeMark('4');" <#if mark?default('-1')=='4'>class="msg-active"</#if>>半个月内</a>
						<a href="javascript:changeTimeMark('-1');" <#if mark?default('-1')=='-1'>class="msg-active"</#if>>全部</a>
					</span>
           			<br />
                	<label>IP：</label>
                    <input type="text" name="ip" id="ip" value="<#if ip??>${ip!""}</#if>"/>&nbsp;&nbsp;&nbsp;
                    <lable>可疑类型：</label>
                    <select id="doubtType" name="doubtType">
						<option value=-1>全部</option>
                     	<option value=0 <#if doubtType?? && doubtType == 0>selected</#if>>AppKey不存在</option>
                     	<option value=1 <#if doubtType?? && doubtType == 1>selected</#if>>AppKey未开启</option>
                    </select>
                    <input type="button" value="搜索" onclick="queryInvalidIps();" class="yt-seach-btn" />&nbsp;&nbsp;&nbsp;
                    <input type="button" value="重置" onclick="toReset();" class="yt-seach-btn" />
				</div>
			</form>
			<table cellpadding="0" cellspacing="0" class="list_table">
				<thead>
					<tr>
                        <th width="30px;">序号</th>
                        <th width="60px;">
                        	<input type="checkbox" id="all_check" value="-1" onclick="allCheck();" />&nbsp;全选
                        	<!--input type="button" value="加入黑名单" onclick="toBatch_add_backlist();" class=".yt-seach-btn-4ft" /-->
                        </th>
                        <th>IP</th>
                        <th>可疑类型</th>
                        <th>无效调用次数</th>
                        <th>最后请求时间</th>
                        <th>操作</th>
                    </tr>
				</thead>
				<tbody>
				<#if pageFinder??&&pageFinder.data??&& pageFinder.data?size != 0>
					<#list pageFinder.data as item>
					<tr>
						<td>${item_index + 1}</td>
		                <td>
		                	<#if item.isBlackList?? && item.isBlackList==1>
		                		<input type="checkbox" name="ip_check" value="${item.ip}" />
		                	</#if>
		                </td>
		                <td>${item.ip}</td>
						<td>
							<#if item.doubtType == 0>
								AppKey不存在
							<#elseif item.doubtType == 1>
								AppKey未开启
							</#if>
						</td>
						<td>${item.invalidCount}</td>
						<td>${item.lastCallTime?string("yyyy-MM-dd HH:mm:ss")}</td>
						<td>
							<#if item.isBlackList?? && item.isBlackList==1>
							<a href="#" onclick="to_add_backlist('${item.ip!''}');">加入黑名单</a>
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
		<#elseif flag??&&flag==2>
		<!-- 黑名单 -->
		<div class="modify" id="div_blacklist_ip">
			<form action="${BasePath}/api/monitor/manage/to_blacklistIps.sc" name="_queryForm" id="_queryForm" method="post"> 
				<div class="wms-top">
                    <input type="text" name="ip" id="ip" value="<#if ip??>${ip!""}</#if>"/>&nbsp;&nbsp;&nbsp;
                  	<label>加入时间：</label>
  			  		<input type="text" id="startTime" name="startTime" width="50px" readonly="readonly" value="<#if startTime??>${startTime?default('')}</#if>" />
           			&nbsp;至&nbsp;
           			<input type="text" id="endTime" name="endTime" readonly="readonly" value="<#if endTime??>${endTime?default('')}</#if>" />
                	<label>IP：</label>
                    <input type="button" value="搜索" onclick="queryblacklistIps();" class="yt-seach-btn" />&nbsp;&nbsp;&nbsp;
                    <input type="button" value="重置" onclick="_toReset();" class="yt-seach-btn" />
				</div>
			</form>
			<table cellpadding="0" cellspacing="0" class="list_table">
				<thead>
					<tr>
                        <th width="30px;">序号</th>
                        <th>IP</th>
                        <th>加入原因</th>
                        <th>加入黑名单时间</th>
                        <th>操作人</th>
                        <th>操作</th>
                    </tr>
				</thead>
				<tbody>
				<#if pageFinder??&&pageFinder.data??&& pageFinder.data?size != 0>
					<#list pageFinder.data as item>
					<tr>
						<td>${item_index + 1}</td>
		                <td>${item.ip}</td>
						<td>${item.reason}</td>
						<td>${item.updateTime?string("yyyy-MM-dd HH:mm:ss")}</td>
						<td>${item.operator!''}</td>
						<td>
							<a href="#" onclick="remove_black_list('${item.id!''}');">移除</a>
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
		</#if>
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

function changeTimeMark(mark){
	$('#mark').val(mark);
	$('#queryForm').attr('action', '${BasePath}/api/monitor/manage/to_invalidIps.sc?fixed=1');
   	$('#queryForm').submit();
}

//加入IP黑名单
var dialog;
function to_add_backlist(ip){
  	dialog=ygdg.dialog({
	      title: '加入黑名单',
	      //icon: 'question',
	      content: '<div>' + 
	      		   '    <label><span style="color:red;">*</span>加入原因：</label>' +
	      		   '	<input type="hidden" id="hidden_ip" name="hidden_ip" value="' + ip + '"/>' + 
	      		   '    <textarea style="width:300px;height:100px;" id="reason" name="reason" value="" />' +
	      		   '    <span><label id="ygdg_warn" style="display:inline-block;">(0/100字)</label></span>' + 
	      		   '</div>',
	      button: [
	           {
            		name: '提交',
            		callback: function () {
            		var reason = $("#reason").val();
            		var ips = $('#hidden_ip').val();
                    if(reason.length > 0 && reason.length <= 100){
                      add_backlist(ips, reason);
                    }else{
                       this.shake && this.shake();
                       $("#ygdg_warn").html("(" + reason.length + "/100字)");
                       $("#ygdg_warn").css("color", "red");
                       $("#reason").select();
                       $("#reason").focus();
                       return false;
                    }	
            	    },
            	    focus: true
        		},
        		{
            		name: '取消'
        		}
    		   ],
	});
}

function add_backlist(ips, reason) {
	$.ajax({
		type: 'post',
		url: '${BasePath}/api/monitor/manage/add_ip_blacklist.sc',
		dataType: 'json',
		data: {ips:ips,reason:reason},
		success: function(data, textStatus) {
		 	//dialog.close();
		 	location.href="${BasePath}/api/monitor/manage/to_invalidIps.sc";
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			ygdg.dialog.alert(XMLHttpRequest.responseText);
		}
	});
}

//批量加入黑名单
function toBatch_add_backlist() {
	var flag = $('#flag').val();
	if (flag == 2) return false;
	var ips = '';
	$("input[name='ip_check']:checked").each(function(){
		ips += $(this).val() + ',';	
	});
	to_add_backlist(ips);
}

//重置
function toReset() {
    $('#startTime').val('');
    $('#endTime').val('');
	$('#ip').val('');
	$('#doubtType').val(-1);
	queryInvalidIps();
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
function queryInvalidIps(){
   	document.queryForm.method="post";
   	document.queryForm.submit();
}

function queryblacklistIps() {
	document._queryForm.method="post";
   	document._queryForm.submit();
}

//重置
function _toReset() {
    $('#startTime').val('');
    $('#endTime').val('');
	$('#ip').val('');
	queryblacklistIps();
}

//切换tab页
function onclick_tab(flag) {
	$('#flag').val(flag);
	if (1 == flag) {
		$('#batch_toolbar').show();
		location.href="${BasePath}/api/monitor/manage/to_invalidIps.sc?flag=" + 1;
	} else if (2 == flag) {
		$('#batch_toolbar').hide();
		location.href="${BasePath}/api/monitor/manage/to_blacklistIps.sc?flag=" + 2;
	}
}

//移除黑名单
function remove_black_list(id) {
	if (confirm("是否移除!"))  {
		$.ajax({
		type: 'post',
		url: '${BasePath}/api/monitor/manage/remove_ip_blacklist.sc',
		dataType: 'json',
		data: {id:id},
		success: function(data, textStatus) {
		 	//dialog.close();
		 	location.href="${BasePath}/api/monitor/manage/to_blacklistIps.sc";
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			ygdg.dialog.alert(XMLHttpRequest.responseText);
		}
	});	
	} 
}
</script>