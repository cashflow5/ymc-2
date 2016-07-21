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
			<div class="btn" onclick="javascript:openwindow('${BasePath}/openapimgt/api/inputparam/view.sc?api.id=${api.id?default('')}',　800,　500,　'添加API输入参数')">
				<span class="btn_l"></span><b class="ico_btn add"></b><span class="btn_txt">添加API输入参数</span><span class="btn_r"></span> </div>
		</div>
	</div>
	<!--工具栏end-->
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li>
				  <span><a href="<#if api??>${BasePath}/openapimgt/api/view.sc?id=${api.id?default('')}<#else>#</#if>" class="btn-onselc">API基本信息</a></span>
				</li>
				<li class="curr">
				  <span><a href="#" class="btn-onselc">API输入参数</a></span>
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
		<div class="tbox">
			<table class="list_table" ellspacing="0" cellpadding="0" border="0">
				<thead>
                    <tr>
                    	<th width="10%">参数名称</th>
                    	<th width="8%">参数类型</th>
                    	<th width="8%">参数是否必须</th>
                    	<th width="32%">参数说明</th>
                    	<th width="16%">参数取值说明</th>
                    	<th width="8%">参数默认值</th>
                    	<th width="8%">参数排序号</th>
                    	<th width="10%">操作</th>
                    </tr>
				</thead>
				<tbody>
                    <#if api.apiInputParams?? && api.apiInputParams?size != 0>
						<#list api.apiInputParams as apiInputParam>
							<tr>
								<td>${apiInputParam.paramName?default('')}</td>
								<td>${apiInputParam.paramDataType?default('')}</td>
								<td>
									<#if apiInputParam.isRequired?default('') == 'Y'>
									是
									<#else>
									否
									</#if>
								</td>
								<td>${apiInputParam.paramDescription?default('')}</td>
								<td>${apiInputParam.paramExampleData?default('')}</td>
								<td>${apiInputParam.paramDefaultData?default('')}</td>
								<td>${apiInputParam.orderNo?default('')}</td>
								<td>
									<a href="javascript:void(0);" onclick="openwindow('${BasePath}/openapimgt/api/inputparam/view.sc?id=${apiInputParam.id?default('')}&api.id=${api.id?default('')}',　800,　500,　'修改API输入参数')">修改</a>
									<a href="javascript:void(0);" onclick="deleteApiInputParam('${apiInputParam.id}')">删除</a>
								</td>
							</tr>
						</#list>
					<#else>
						<tr>
							<td colspan="8" align="center" style="color: red;">暂无数据</td>
						</tr>
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
function deleteApiInputParam(id) {
	if (confirm('确定删除该API输入参数?')) {
		$.ajax({ type : 'post', url: '${BasePath}/openapimgt/api/inputparam/delete.sc', dataType: 'text', data: 'id=' + id,
			success: function(data, textStatus) {
				if ((typeof(data) == 'boolean' && data) || $.trim(data) == 'true') {
					alert('删除成功!');
					window.location.reload();
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