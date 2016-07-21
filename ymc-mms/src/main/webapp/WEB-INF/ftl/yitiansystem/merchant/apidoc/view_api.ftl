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
	<!--工具栏start-->
	<div class="toolbar">
		<div class="t-content">
		</div>
	</div>
	<!--工具栏end-->
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr">
				  <span><a href="#" class="btn-onselc">API基本信息</a></span>
				</li>
				<li>
				  <span><a href="<#if api??>${BasePath}/openapimgt/api/view/inputparam.sc?id=${api.id?default('')}<#else>#</#if>" class="btn-onselc">API输入参数</a></span>
				</li>
				<li>
				  <span><a href="<#if api??>${BasePath}/openapimgt/api/view/outputparam.sc?id=${api.id?default('')}<#else>#</#if>" class="btn-onselc">API输出参数</a></span>
				</li>
				<li>
				  <span><a href="<#if api??>${BasePath}/openapimgt/api/view/validator.sc?id=${api.id?default('')}<#else>#</#if>" class="btn-onselc">API验证器链</a></span>
				</li>
				<li>
				  <span><a href="<#if api??>${BasePath}/openapimgt/api/view/interceptor.sc?id=${api.id?default('')}<#else>#</#if>" class="btn-onselc">API拦截器链</a></span>
				</li>
				<li>
				  <span><a href="<#if api??>${BasePath}/openapimgt/api/view/example.sc?id=${api.id?default('')}<#else>#</#if>" class="btn-onselc">API输出示例数据</a></span>
				</li>
				<li>
				  <span><a href="<#if api??>${BasePath}/openapimgt/api/view/error.sc?id=${api.id?default('')}<#else>#</#if>" class="btn-onselc">API错误码</a></span>
				</li>
			</ul>
		</div>
 		<div class="modify"> 
     	<form action="${BasePath}/openapimgt/api/saveorupdate.sc" name="createForm" id="createForm" method="post">
      		<input type="hidden" name="id" id="id" <#if api??>value="${api.id?default('')}"</#if>/>
      		<input type="hidden" name="isEnable" id="isEnable" <#if api??>value="${api.isEnable?default('0')}"</#if>/>
                <table cellpadding="0" cellspacing="0" class="list_table">
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;*</span>API版本：</label>
												</td>
						<td>
							<select name="apiVersion.id" id="apiVersionId">
								<option value="">请选择</option>
								<#list apiVersions as item >
								<option value="${item.id}" <#if api?? && item.id == api.apiVersion.id?default('')>selected="selected"</#if>>${item.versionNo}</option>
								</#list>
							</select>
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;*</span>API分类：</label>
												</td>
						<td>
							<select name="apiCategory.id" id="apiCategoryId">
								<option value="">请选择</option>
								<#list apiCategorys as item >
								<option value="${item.id}" <#if api?? && item.id == api.apiCategory.id?default('')>selected="selected"</#if>>${item.categoryCode}</option>
								</#list>
							</select>
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;*</span>API实现者：</label>
												</td>
						<td>
							<select name="apiImplementor.id" id="apiImplementorId">
								<option value="">请选择</option>
								<#list apiImplementors as item >
								<option value="${item.id}" <#if api?? && item.id == api.apiImplementor.id?default('')>selected="selected"</#if>>${item.implementorClass}</option>
								</#list>
							</select>
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;*</span>API实现方法：</label>
												</td>
						<td>
							<input type="text" name="apiMethod" id="apiMethod" <#if api??>value="${api.apiMethod?default('')}"</#if> />
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;*</span>API实现方法参数类型：</label>
												</td>
						<td>
							<input type="text" name="apiMethodParamTypes" id="apiMethodParamTypes" <#if api??>value="${api.apiMethodParamTypes?default('')}"</#if> />
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;*</span>API代码：</label>
												</td>
						<td>
							<input type="text" name="apiCode" id="apiCode" <#if api??>value="${api.apiCode?default('')}"</#if> />
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;*</span>API名称：</label>
												</td>
						<td>
							<input type="text" name="apiName" id="apiName" <#if api??>value="${api.apiName?default('')}"</#if> />
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;*</span>API权重：</label>
												</td>
						<td>
	    		       		<#list bitWeights as item>
	    		       			<#if item.label??>
	    		       				<input type="checkbox" class="apiWeight" id="apiWeight${item_index}" value="${item.value?c}" <#if api?? && bitwise_and(api.apiWeight?default(0)?c, item.value?c)?c == item.value?c>checked="checked"</#if> /><label for="apiWeight${item_index}">${item.label}</label>
	    		       			</#if>
	    		       		</#list>
							<input type="hidden" name="apiWeight" id="apiWeight" <#if api??>value="${api.apiWeight?default(0)?c}"</#if> />
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;*</span>是否保存结果到NOSQL_DB：</label></td>
						<td>
							<input type="checkbox" name="isSave2Result" id="isSave2Result" value="${(api.isSaveResult)?default('')}"  <#if api?? && (api.isSaveResult)?? && api.isSaveResult == "1">checked="checked"</#if>  />
							<input type="hidden" name="isSaveResult" id="isSaveResult" value="${(api.isSaveResult)?default('')}" />
						</td>
					</tr>
					<tr>
						<td style="text-align:right;"><label> <span style="color:red;">&nbsp;*</span>API描述：</label>
												</td>
						<td>
							<textarea rows="5" cols="60" name="apiDescription" id="apiDescription"><#if api??>${api.apiDescription?default('')}</#if></textarea>
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
	var weight = 0;
	$('.apiWeight').each(function() { if($(this).attr('checked')) weight |= $(this).val() });
	$('#apiWeight').val(weight);
	
	if ($.trim($('#apiVersionId').val()) == '') {
		alert('API版本不能空选!');
		return false;
	}
	if ($.trim($('#apiCategoryId').val()) == '') {
		alert('API分类不能空选!');
		return false;
	}
	if ($.trim($('#apiImplementorId').val()) == '') {
		alert('API实现者不能空选!');
		return false;
	}
	if ($.trim($('#apiMethod').val()) == '') {
		alert('API实现方法不能为空!');
		return false;
	}
	if ($.trim($('#apiMethodParamTypes').val()) == '') {
		alert('API实现方法参数类型不能为空!');
		return false;
	}
	if ($.trim($('#apiCode').val()) == '') {
		alert('API代码不能为空!');
		return false;
	}
	if ($.trim($('#apiName').val()) == '') {
		alert('API代码不能为空!');
		return false;
	}
	if (weight <= 0) {
		alert('API权重不能空选!');
		return false;
	}
	if ($.trim($('#apiDescription').val()) == '') {
		alert('API描述不能为空!');
		return false;
	}
	
	if ($('#isSave2Result').attr('checked')) {
		$('#isSaveResult').val("1");
	} else {
		$('#isSaveResult').val("0");
	}
	
	$.ajax({ type : 'post', url: $('#createForm').attr('action'), dataType: 'json', data: $('#createForm').serialize(),
		beforeSend: function(XMLHttpRequest) {
			$('.yt-seach-btn').attr('disabled', true);
		},
		success: function(data, textStatus) {
			if ((typeof(data) == 'boolean' && data) || $.trim(data) == 'true') {
				alert('保存成功!');
				refreshpage('${BasePath}/openapimgt/api/list.sc');
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