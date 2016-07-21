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
			<div class="btn" onclick="javascript:openwindow('${BasePath}/openapimgt/version/inputparam/metadata/view.sc?refId=${apiVersion.id?default('')}',　800,　500,　'添加API版本输入参数验证器')">
				<span class="btn_l"></span><b class="ico_btn add"></b><span class="btn_txt">添加版本API输入参数验证器</span><span class="btn_r"></span> </div>
		</div>
	</div>
	<!--工具栏end-->
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li>
				  <span><a href="<#if apiVersion??>${BasePath}/openapimgt/version/view.sc?id=${apiVersion.id?default('')}<#else>#</#if>" class="btn-onselc">API版本基本信息</a></span>
				</li>
				<li>
				  <span><a href="<#if apiVersion??>${BasePath}/openapimgt/version/view/inputparam.sc?id=${apiVersion.id?default('')}<#else>#</#if>" class="btn-onselc">API版本输入参数</a></span>
				</li>
				<li>
				  <span><a href="<#if apiVersion??>${BasePath}/openapimgt/version/view/outputparam.sc?id=${apiVersion.id?default('')}<#else>#</#if>" class="btn-onselc">API版本输出参数</a></span>
				</li>
				<li class="curr">
				  <span><a href="#" class="btn-onselc">API版本验证器链</a></span>
				</li>
				<li>
				  <span><a href="<#if apiVersion??>${BasePath}/openapimgt/version/view/interceptor.sc?id=${apiVersion.id?default('')}<#else>#</#if>" class="btn-onselc">API版本拦截器链</a></span>
				</li>
			</ul>
		</div>
 		<div class="modify"> 
 		<div class="tbox">
			<table class="list_table" ellspacing="0" cellpadding="0" border="0">
				<thead>
                    <tr style="font-weight: bold;">
                    	<th width="20%">验证器标示符</th>
                    	<th width="20%">验证参数名称</th>
                    	<th width="10%">正则表达式</th>
                    	<th width="10%">大小写敏感</th>
                    	<th width="10%">去掉前台空格</th>
                    	<th width="10%">最小值</th>
                    	<th width="10%">最大值</th>
                    	<th width="10%">最小长度</th>
                    	<th width="10%">最大长度</th>
                    	<th width="10%">排序号</th>
                    	<th width="10%">操作</th>
                    </tr>
				</thead>
				<tbody>
                    <#if apiVersion.apiVersionInputParams?? && apiVersion.apiVersionInputParams?size != 0>
						<#list apiVersion.apiVersionInputParams as apiVersionInputParam>
							<#if apiVersionInputParam.apiInputParamMetadatas?? && apiVersionInputParam.apiInputParamMetadatas?size != 0>
								<#list apiVersionInputParam.apiInputParamMetadatas as apiInputParamMetadata>
									<tr>
										<td>${apiInputParamMetadata.apiValidator.validatorClass?default('')}</td>
										<td>${apiVersionInputParam.paramName?default('')}</td>
										<td>${apiInputParamMetadata.expression?default('')}</td>
										<td>${apiInputParamMetadata.caseSensitive?default('')}</td>
										<td>${apiInputParamMetadata.trim?default('')}</td>
										<td>${apiInputParamMetadata.minValue?default('')}</td>
										<td>${apiInputParamMetadata.maxValue?default('')}</td>
										<td>${apiInputParamMetadata.minLength?default('')}</td>
										<td>${apiInputParamMetadata.maxLength?default('')}</td>
										<td>${apiInputParamMetadata.orderNo?default('')}</td>
										<td>
											<a href="javascript:void(0);" onclick="openwindow('${BasePath}/openapimgt/version/inputparam/metadata/view.sc?metadataId=${apiInputParamMetadata.id}&refId=${apiVersion.id?default('')}',　800,　500,　'修改API版本输入参数验证器')">修改</a>
											<a href="javascript:void(0);" onclick="deleteApiInputParamMetadata('${apiInputParamMetadata.id}')">删除</a>
										</td>
									</tr>
								</#list>
							</#if>
						</#list>
					</#if>
                </tbody>
			</table>
			</div>		
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
function deleteApiInputParamMetadata(id) {
	if (confirm('确定删除该API版本验证器链?')) {
		$.ajax({ type : 'post', url: '${BasePath}/openapimgt/version/inputparam/metadata/delete.sc', dataType: 'text', data: 'id=' + id,
			success: function(data, textStatus) {
				if ((typeof(data) == 'boolean' && data) || $.trim(data) == 'true') {
					alert('删除成功!');
					refreshpage();
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