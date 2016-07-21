<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>

<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/jquery-1.3.2.min.js"></script>
<script type="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/js.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/order.js"></script>
<title>优购商城--商家后台</title>
<!-- 日期控件 -->
<script src="${BasePath}/js/common/form/datepicker/WdatePicker.js" type="text/javascript"></script>
</head>
<script type="text/javascript">

</script>
<body>
<div class="container">
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr">
				  <span><a href="#" class="btn-onselc">添加API异常报警器</a></span>
				</li>
			</ul>
		</div>
 		<div class="modify"> 
     	<form action="${BasePath}/openapimgt/abnormalalarm/saveorupdate.sc" name="createForm" id="createForm" method="post">
      		<input type="hidden" name="id" id="id" <#if apiAbnormalAlarm??>value="${apiAbnormalAlarm.id?default('')}"</#if>/>
                <table cellpadding="0" cellspacing="0" class="list_table">
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;*</span>异常报警器代码：</label>
												</td>
						<td>
							<input type="text" name="alarmCauserCode" id="alarmCauserCode" <#if apiAbnormalAlarm??>value="${apiAbnormalAlarm.alarmCauserCode?default('')}"</#if> />
							<span style="color:red;">&nbsp;星号表示所有代码</span>
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;*</span>异常报警器类名：</label>
												</td>
						<td>
							<input type="text" name="alarmCauserClass" id="alarmCauserClass" <#if apiAbnormalAlarm??>value="${apiAbnormalAlarm.alarmCauserClass?default('')}"</#if> />
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;&nbsp;</span>事件权重：</label>
												</td>
						<td>
							<input type="checkbox" class="alarmCauserWeight" id="alarmCauserWeight1" value="1" <#if apiAbnormalAlarm?? && bitwise_and(apiAbnormalAlarm.alarmCauserWeight?default(0), 1) == 1>checked="checked"</#if> /><label for="alarmCauserWeight1">邮件</label>
							<input type="checkbox" class="alarmCauserWeight" id="alarmCauserWeight2" value="2" <#if apiAbnormalAlarm?? && bitwise_and(apiAbnormalAlarm.alarmCauserWeight?default(0), 2) == 2>checked="checked"</#if> /><label for="alarmCauserWeight2">短信</label>
							<input type="hidden" name="alarmCauserWeight" id="alarmCauserWeight" <#if apiAbnormalAlarm??>value="${apiAbnormalAlarm.alarmCauserWeight?default('')}"</#if> />
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;&nbsp;</span>接警人邮件：</label>
												</td>
						<td>
							<input type="text" name="alarmReceiverEmail" id="alarmReceiverEmail" <#if apiAbnormalAlarm??>value="${apiAbnormalAlarm.alarmReceiverEmail?default('')}"</#if> />
							<span style="color:red;">&nbsp;如存在多人请使用逗号分隔(如:police1@mail.com,police2@mail.com)</span>
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;&nbsp;</span>接警人手机：</label>
												</td>
						<td>
							<input type="text" name="alarmReceiverPhone" id="alarmReceiverPhone" <#if apiAbnormalAlarm??>value="${apiAbnormalAlarm.alarmReceiverPhone?default('')}"</#if> />
							<span style="color:red;">&nbsp;如存在多人请使用逗号分隔(如:13800138000,13800138001)</span>
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;*</span>忽略次数：</label>
												</td>
						<td>
							<input type="text" name="ignoreNumbers" id="ignoreNumbers" <#if apiAbnormalAlarm??>value="${apiAbnormalAlarm.ignoreNumbers?default('')}"</#if> />
							<span style="color:red;">&nbsp;配合"周期时间轴"使用,指特定时间段允许发生该异常最大次数,超出后系统将触发接警指令</span>
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;*</span>周期时间轴：</label>
												</td>
						<td>
							<input type="text" name="cycleTimeline" id="cycleTimeline" <#if apiAbnormalAlarm??>value="${apiAbnormalAlarm.cycleTimeline?c?default('')}"</#if> />
							<span style="color:red;">&nbsp;周期单位为"毫秒",1000毫秒等于1秒,如需设置周期时间轴为1分钟,正确取值为60000</span>
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;&nbsp;</span>描述：</label>
												</td>
						<td>
							<textarea rows="5" cols="60" name="description" id="description"><#if apiAbnormalAlarm??>${apiAbnormalAlarm.description?default('')}</#if></textarea>
						</td>
					</tr>
					<tr>
						<td>
												</td>
						<td>
					 		<input id="btn" type="button" value="保存" class="yt-seach-btn" onclick="fireCreate();">
						</td>
					</tr>
                </table>
       	</form>
    </div>
 <div class="blank20"></div>
</div>
</body>
</html>
</script>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
<script type="text/javascript">
function fireCreate() {
	var weight = $('#alarmCauserWeight').val() || 0;
	$('.alarmCauserWeight').each(function() { if($(this).attr('checked')) weight |= $(this).val() });
	$('#alarmCauserWeight').val(weight);
	
	if ($.trim($('#alarmCauserCode').val()) == '') {
		alert('异常报警器代码不能为空!');
		return false;
	}
	if ($.trim($('#alarmCauserClass').val()) == '') {
		alert('异常报警器类名不能为空!');
		return false;
	}
	if ($.trim($('#ignoreNumbers').val()) == '') {
		alert('忽略次数不能为空!');
		return false;
	}
	if ($.trim($('#cycleTimeline').val()) == '') {
		alert('周期时间轴不能为空!');
		return false;
	}
	
	$.ajax({ type : 'post', url: $('#createForm').attr('action'), dataType: 'text', data: $('#createForm').serialize(),
		beforeSend: function(XMLHttpRequest) {
			$('.yt-seach-btn').attr('disabled', true);
		},
		success: function(data, textStatus) {
			if ((typeof(data) == 'boolean' && data) || $.trim(data) == 'true') {
				alert('保存成功!');
				refreshpage('${BasePath}/openapimgt/abnormalalarm/list.sc');
				closewindow();
			} else {
				alert(data);
				$('.yt-seach-btn').attr('disabled', false);
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			$('.yt-seach-btn').attr('disabled', false);
			alert(textStatus.toUpperCase() + ' : ' + XMLHttpRequest.responseText);
		}
	});
}
</script>