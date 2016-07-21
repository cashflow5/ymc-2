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
				  <span><a href="#" class="btn-onselc">输入参数</a></span>
				</li>
			</ul>
		</div>
 		<div class="modify"> 
	 		<#if inputParam?? && inputParam.api??>
	     	<form action="${BasePath}/openapimgt/api/inputparam/saveorupdate.sc" name="createForm" id="createForm" method="post">
	 		<input type="hidden" name="api.id" id="apiId" value="${inputParam.api.id?default('')}"/>
	 		<#elseif inputParam?? && inputParam.apiVersion??>
	 		<form action="${BasePath}/openapimgt/version/inputparam/saveorupdate.sc" name="createForm" id="createForm" method="post">
	 		<input type="hidden" name="apiVersion.id" id="apiVersionId" value="${inputParam.apiVersion.id?default('')}"/>
	 		</#if>
      		<input type="hidden" name="id" id="id" <#if inputParam??>value="${inputParam.id?default('')}"</#if>/>
                <table cellpadding="0" cellspacing="0" class="list_table">
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;*</span>参数名称：</label>
						</td>
						<td>
							<input type="text" name="paramName" id="paramName" <#if inputParam??>value="${inputParam.paramName?default('')}"</#if> />
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;*</span>参数类型：</label>
						</td>
						<td>
							<select name="paramDataType" id="paramDataType">
								<option value="">请选择</option>
								<option value="String" <#if inputParam?? && inputParam.paramDataType?default('') == 'String'>selected="selected"</#if>>String</option>
								<option value="Number" <#if inputParam?? && inputParam.paramDataType?default('') == 'Number'>selected="selected"</#if>>Number</option>
								<option value="Date" <#if inputParam?? && inputParam.paramDataType?default('') == 'Date'>selected="selected"</#if>>Date</option>
								<option value="Array" <#if inputParam?? && inputParam.paramDataType?default('') == 'Array'>selected="selected"</#if>>Array</option>
								<option value="Boolean" <#if inputParam?? && inputParam.paramDataType?default('') == 'Boolean'>selected="selected"</#if>>Boolean</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;*</span>参数是否必须：</label>
						</td>
						<td>
							<select name="isRequired" id="isRequired">
								<option value="">请选择</option>
								<option value="Y" <#if inputParam?? && inputParam.isRequired?default('') == 'Y'>selected="selected"</#if>>是</option>
								<option value="N" <#if inputParam?? && inputParam.isRequired?default('') == 'N'>selected="selected"</#if>>否</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;&nbsp;</span>参数说明：</label>
						</td>
						<td>
							<textarea rows="5" cols="60" name="paramDescription" id="paramDescription"><#if inputParam??>${inputParam.paramDescription?default('')}</#if></textarea>
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;&nbsp;</span>参数取值说明：</label>
						</td>
						<td>
							<textarea rows="5" cols="60" name="paramExampleData" id="paramExampleData"><#if inputParam??>${inputParam.paramExampleData?default('')}</#if></textarea>
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;&nbsp;</span>参数默认值：</label>
						</td>
						<td>
							<input type="text" name="paramDefaultData" id="paramDefaultData" <#if inputParam??>value="${inputParam.paramDefaultData?default('')}"</#if> />
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;&nbsp;</span>参数排序号：</label>
						</td>
						<td>
							<select name="orderNo" id="orderNo">
								<option value="">请选择</option>
								<#list usableOrderNoList as item >
								<option value="${item}" <#if inputParam?? && item == inputParam.orderNo?default(0)>selected="selected"</#if>>${item}</option>
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
	if ($.trim($('#paramName').val()) == '') {
		alert('参数名称不能为空!');
		return false;
	}
	if ($.trim($('#paramDataType').val()) == '') {
		alert('参数类型不能空选!');
		return false;
	}
	if ($.trim($('#isRequired').val()) == '') {
		alert('是否必须不能空选!');
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