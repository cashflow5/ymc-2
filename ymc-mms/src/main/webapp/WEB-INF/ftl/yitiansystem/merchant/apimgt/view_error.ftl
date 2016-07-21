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

<body>
<div class="container">
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr">
				  <span><a href="#" class="btn-onselc">添加错误码</a></span>
				</li>
			</ul>
		</div>
 		<div class="modify"> 
     	<form action="${BasePath}/openapimgt/api/error/saveorupdate.sc" name="createForm" id="createForm" method="post">
     		<input type="hidden" name="id" id="id" <#if apiError??>value="${apiError.id?default('')}"</#if>/>
                <table cellpadding="0" cellspacing="0" class="list_table">
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;*</span>错误码：</label>
												</td>
						<td>
							<input type="text" name="errorCode" id="errorCode" <#if apiError??>value="${apiError.errorCode?default('')}"</#if>/>
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;*</span>错误描述：</label>
												</td>
						<td>
							<textarea rows="5" cols="60" name="errorDescription" id="errorDescription"><#if apiError??>${apiError.errorDescription?default('')}</#if></textarea>
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;</span>解决方案：</label>
												</td>
						<td>
							<textarea rows="5" cols="60" name="errorSolution" id="errorSolution"><#if apiError??>${apiError.errorSolution?default('')}</#if></textarea>
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;*</span>排序号：</label>
												</td>
						<td>
							<select name="orderNo" id="orderNo">
								<option value="">请选择</option>
								<#list usableOrderNoList as item >
								<option value="${item}" <#if apiError?? && item == apiError.orderNo?default(0)>selected="selected"</#if>>${item}</option>
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
	if ($.trim($('#errorCode').val()) == '') {
		alert('错误码不能为空!');
		return false;
	}
	if ($.trim($('#errorDescription').val()) == '') {
		alert('错误描述不能为空!');
		return false;
	}
	if ($.trim($('#orderNo').val()) == '') {
		alert('排序号不能为空!');
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