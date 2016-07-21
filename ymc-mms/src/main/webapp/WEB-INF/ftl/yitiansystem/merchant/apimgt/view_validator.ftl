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
				  <span><a href="#" class="btn-onselc">添加API验证器</a></span>
				</li>
				
			</ul>
		</div>
 		<div class="modify"> 
     	<form action="${BasePath}/openapimgt/validator/saveorupdate.sc" name="createForm" id="createForm" method="post">
      		<input type="hidden" name="id" id="id" <#if apiValidator??>value="${apiValidator.id?default('')}"</#if>/>
                <table cellpadding="0" cellspacing="0" class="list_table">
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;*</span>验证器标示符：</label>
												</td>
						<td>
							<input type="text" name="identifier" id="identifier" <#if apiValidator??>value="${apiValidator.identifier?default('')}" readonly="readonly"</#if> />
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;*</span>验证器类名称：</label>
												</td>
						<td>
							<select name="validatorClass" id="validatorClass">
								<option value="">请选择</option>
								<#list classes as item >
								<option value="${item.name}" <#if apiValidator?? && item.name == apiValidator.validatorClass?default('')>selected="selected"</#if>>${item.name}</option>
								</#list>
							</select>
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;*</span>消息表达式：</label>
												</td>
						<td>
							<input type="text" name="messagePattern" id="messagePattern" value="<#if apiValidator??>${apiValidator.messagePattern?default('')}</#if>" />
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;&nbsp;</span>消息资源键：</label>
												</td>
						<td>
							<input type="text" name="messageKey" id="messageKey" value="<#if apiValidator??>${apiValidator.messageKey?default('')}</#if>" />
							<span style="color:red;">&nbsp;优先使用资源键,其次消息表达式</span>
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;&nbsp;</span>描述：</label>
												</td>
						<td>
							<textarea rows="5" cols="60" name="description" id="description"><#if apiValidator??>${apiValidator.description?default('')}</#if></textarea>
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
	if ($.trim($('#identifier').val()) == '') {
		alert('验证器标示符不能为空!');
		return false;
	}
	if ($.trim($('#validatorClass').val()) == '') {
		alert('验证器类名称不能为空!');
		return false;
	}
	if ($.trim($('#messagePattern').val()) == '') {
		alert('消息表达式不能为空!');
		return false;
	}
	
	$.ajax({ type : 'post', url: $('#createForm').attr('action'), dataType: 'text', data: $('#createForm').serialize(),
		beforeSend: function(XMLHttpRequest) {
			$('.yt-seach-btn').attr('disabled', true);
		},
		success: function(data, textStatus) {
			if ((typeof(data) == 'boolean' && data) || $.trim(data) == 'true') {
				alert('保存成功!');
				refreshpage('${BasePath}/openapimgt/validator/list.sc');
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