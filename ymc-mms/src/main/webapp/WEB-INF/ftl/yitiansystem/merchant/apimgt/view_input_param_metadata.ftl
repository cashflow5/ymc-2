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
				  <span><a href="#" class="btn-onselc">API输入参数验证器</a></span>
				</li>
			</ul>
		</div>
 		<div class="modify">
 			<#if apiInputParamMetadata?? && apiInputParamMetadata.inputParam.refer.apiInputParams??>
			<form action="${BasePath}/openapimgt/api/inputparam/metadata/saveorupdate.sc" name="createForm" id="createForm" method="post"> 			
 			<#elseif apiInputParamMetadata?? && apiInputParamMetadata.inputParam.refer.apiVersionInputParams??>
 			<form action="${BasePath}/openapimgt/version/inputparam/metadata/saveorupdate.sc" name="createForm" id="createForm" method="post">
 			</#if>
      		<input type="hidden" name="id" id="id" <#if apiInputParamMetadata??>value="${apiInputParamMetadata.id?default('')}"</#if>/>
                <table cellpadding="0" cellspacing="0" class="list_table">
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;*</span>API输入参数：</label>
						</td>
						<td>
							<select name="inputParamId" id="inputParamId">
								<option value="">请选择</option>
					      		<#if apiInputParamMetadata?? && apiInputParamMetadata.inputParam.refer.apiInputParams??>
					      			<#list apiInputParamMetadata.inputParam.refer.apiInputParams as item>
					      				<option value="${item.id}" <#if item.id == apiInputParamMetadata.inputParam.id?default('')>selected="selected"</#if>>${item.paramName}</option>
					      			</#list>
					      		<#elseif apiInputParamMetadata?? && apiInputParamMetadata.inputParam.refer.apiVersionInputParams??>
					      			<#list apiInputParamMetadata.inputParam.apiVersion.apiVersionInputParams as item>
					      				<option value="${item.id}" <#if item.id == apiInputParamMetadata.inputParam.id?default('')>selected="selected"</#if>>${item.paramName}</option>
					      			</#list>
					      		</#if>
							</select>
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;*</span>API验证器：</label>
						</td>
						<td>
							<select name="apiValidator.validatorClass" id="apiValidatorClass">
								<option value="">请选择</option>
								<#list classes as item >
								<option value="${item.name}" <#if apiInputParamMetadata?? && apiInputParamMetadata.apiValidator?? && item.name == apiInputParamMetadata.apiValidator.validatorClass?default('')>selected="selected"</#if>>${item.name}</option>
								</#list>
							</select>
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;&nbsp;</span>正则表达式：</label>
						</td>
						<td>
							<input type="text" name="expression" id="expression" <#if apiInputParamMetadata??>value="${apiInputParamMetadata.expression?default('')}"</#if> />
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;&nbsp;</span>大小写敏感：</label>
						</td>
						<td>
							<select name="caseSensitive" id="caseSensitive">
								<option value="">请选择</option>
								<option value="Y" <#if apiInputParamMetadata?? && apiInputParamMetadata.caseSensitive?default('') == 'Y'>selected="selected"</#if>>是</option>
								<option value="N" <#if apiInputParamMetadata?? && apiInputParamMetadata.caseSensitive?default('') == 'N'>selected="selected"</#if>>否</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;&nbsp;</span>去掉前台空格：</label>
						</td>
						<td>
							<select name="trim" id="trim">
								<option value="">请选择</option>
								<option value="Y" <#if apiInputParamMetadata?? && apiInputParamMetadata.trim?default('') == 'Y'>selected="selected"</#if>>是</option>
								<option value="N" <#if apiInputParamMetadata?? && apiInputParamMetadata.trim?default('') == 'N'>selected="selected"</#if>>否</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;&nbsp;</span>最小值：</label>
						</td>
						<td>
							<input type="text" name="minValue" id="minValue" <#if apiInputParamMetadata??>value="${apiInputParamMetadata.minValue?default('')}"</#if> />
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;&nbsp;</span>最大值：</label>
						</td>
						<td>
							<input type="text" name="maxValue" id="maxValue" <#if apiInputParamMetadata??>value="${apiInputParamMetadata.maxValue?default('')}"</#if> />
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;&nbsp;</span>最小长度：</label>
						</td>
						<td>
							<input type="text" name="minLength" id="minLength" <#if apiInputParamMetadata??>value="${apiInputParamMetadata.minLength?default('')}"</#if> />
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;&nbsp;</span>最大长度：</label>
						</td>
						<td>
							<input type="text" name="maxLength" id="maxLength" <#if apiInputParamMetadata??>value="${apiInputParamMetadata.maxLength?default('')}"</#if> />
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;&nbsp;</span>参数排序号：</label>
						</td>
						<td>
							<select name="orderNo" id="orderNo">
								<option value="">请选择</option>
								<#list usableOrderNoList as item >
								<option value="${item}" <#if apiInputParamMetadata?? && item == apiInputParamMetadata.orderNo?default(0)>selected="selected"</#if>>${item}</option>
								</#list>
							</select>
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
	if ($.trim($('#inputParamId').val()) == '') {
		alert('API输入参数不能空选!');
		return false;
	}
	if ($.trim($('#apiValidatorClass').val()) == '') {
		alert('API验证器不能空选!');
		return false;
	}
	if ($.trim($('#orderNo').val()) == '') {
		alert('排序号不能空选!');
		return false;
	}
	
	$.ajax({ type : 'post', url: $('#createForm').attr('action'), dataType: 'text', data: $('#createForm').serialize(),
		beforeSend: function(XMLHttpRequest) {
			$('.yt-seach-btn').attr('disabled', true);
		},
		success: function(data, textStatus) {
			if ((typeof(data) == 'boolean' && data) || $.trim(data) == 'true') {
				alert('保存成功!');
				refreshpage();
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