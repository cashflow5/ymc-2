<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商城--优购平台</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
</head>
<body>

<div class="container"> 
	<!--工具栏start-->
	<div class="toolbar">
		<div class="t-content">
				<div class="btn" onclick="javascript:doExport();">
					<span class="btn_l"></span><b class="ico_btn exp"></b><span class="btn_txt">导出</span><span class="btn_r"></span> </div>
		</div>
	</div>
	<!--工具栏end-->
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr"><span>API日志管理</span></li>
			</ul>
		</div>
		<!--当前位置end-->
		<div class="modify"> 
			<!--搜索开始-->
			<div class="add_detail_box">
				<form id="queryForm" name="queryForm" action="${BasePath}/openapimgt/api/log/list.sc" method="post">
    		       	<p>
    		       	<label for="operator">AppKey：</label><input type="text" id="operator" name="operator" style="width: 120px;" value="<#if apiLog??>${apiLog.operator?default('')}</#if>" />
           			<label for="operator">操作时间：</label><input type="text" id="fromOperated" name="fromOperated" style="width: 120px;" readonly="readonly" value="<#if fromOperated??>${fromOperated?default('')?datetime}</#if>" />&nbsp;至&nbsp;<input type="text" id="toOperated" name="toOperated" style="width: 120px;" readonly="readonly" value="<#if toOperated??>${toOperated?default('')?datetime}</#if>" />
           			<#--
           			<label for="clientIp">操作结果：</label><input type="text" id="operationResult" name="operationResult" style="width: 120px;" value="<#if apiLog??>${apiLog.operationResult?default('')}</#if>" />
           			-->
           			<!-- 
           			<label for="clientIp">请求IP：</label><input type="text" id="clientIp" name="clientIp" style="width: 120px;" value="<#if apiLog??>${apiLog.clientIp?default('')}</#if>" />
           			 -->
           			</p>
           			  	<p>
           			<label for="apiMethod">请求API：</label><select id="apiMethod" name="apiMethod"	style="width: 220px;" onchange="javascript:rebuildApiInputParam();rebuildApiInputParamValue();">
    		       		<optgroup label="">
	    		       		<option aips="" value="">请选择API</option>
    		       		</optgroup>
    		       		<#if apiListMap??>
	    		       		<#list apiListMap?keys as key>
		    		       		<optgroup label="${key}">
		    		       			<#list apiListMap[key] as api>
			    		       			<option aips="<#list api.apiInputParams as item>${item.paramName},</#list>" value="${api.apiMethod}" <#if apiLog?? && api.apiMethod == apiLog.apiMethod?default('')>selected="selected"</#if>>${api.apiMethod}</option>
		    		       			</#list>
		    		       		</optgroup>
	    		       		</#list>
    		       		</#if>
    		       	</select>

    		       	<select id="apiInputParam" name="apiInputParam" style="width: 144px;" onchange="javascript:rebuildApiInputParamValue();">
    		       		<option value="">请选择API参数</option>
    		       	</select>
    		       	<input type="text" id="apiInputParamValue" name="apiInputParamValue" style="width: 120px;" value="<#if apiInputParamValue??>${apiInputParamValue?default('')}</#if>"/>
            		
            		<label for="clientIp">请求结果：</label>
           			<select id="isCallSucess" name="isCallSucess">
           				<option value="">请选择</option>
           				<option value="true" <#if apiLog?? && apiLog.isCallSucess?? && apiLog.isCallSucess?string == "true" >selected</#if> >成功</option>
           				<option value="false" <#if apiLog?? && apiLog.isCallSucess?? &&  apiLog.isCallSucess?string == "false" >selected</#if> >异常</option>
           			</select>
           			<!-- 
           			<input type="checkbox" id="showCount" name="showCount" <#if (query.showCount)?? && query.showCount?string == "true" >checked</#if> />显示记录数
            		-->
            		&nbsp;&nbsp;&nbsp;&nbsp;
            		<input type="submit" class="btn-add-normal" value="搜索" />
            		</p>
              	</form>
			</div>
			<!--搜索结束--> 
			<!--列表start-->
			<div class="tbox">
			<table class="list_table" ellspacing="0" cellpadding="0" border="0">
					<thead>
	                    <tr>
	                    <th style="width:10%;">AppKey</th>
	                    <th style="width:10%;">请求IP</th>
	                    <th style="width:10%;">请求时间戳</th>
	                    <th style="width:10%;">API版本</th>
	                    <th style="width:10%;">API</th>
	                    <th style="width:10%;">Format</th>
	                    <th style="width:30%;">记录时间</th>
	                    <th style="width:10%;">请求结果</th>
	                    <th style="width:10%;">操作</th>
	                    </tr>              
	                </thead>
                    <tbody>
                    <#if pageFinder?? && pageFinder.data?? && pageFinder.data?size != 0>
                    	<#list pageFinder.data as item>
                    		<tr>
                    			<td>${item['operationParameters']['app_key']?default('')}</td>
                    			<td>${item['clientIp']?default('')}</td>
                    			<td>${item['operationParameters']['timestamp']?default('')}</td>
                    			<td>${item['operationParameters']['app_version']?default('1.0')}</td>
                    			<td>${item['operationParameters']['method']?default('')}</td>
                    			<td>${item['operationParameters']['format']?default('xml')}</td>
                    			<td>${item['operated']?default('')?datetime}</td>
                    			<td><#if item['isCallSucess']?? >${item['isCallSucess']?string("成功","异常")}</#if></td>
                    			<td id="link_${item['_id']}" class="td0">
									<a href="javascript:void(0);" onclick="openIO('${item['_id']}')">I/O详情查看</a>
									<div id="${item['_id']}" style="display: none;">
										<div style="width: 800px; height: 500px; word-break: break-all; word-wrap: break-word; float: left; overflow: scroll;">
											<div style="color: #ff0000; font-weight: bold;">请求参数</div>
											<div>${item['operationParametersStr']?default('')?html}</div>
											<div style="color: #ff0000; font-weight: bold;">响应结果</div>
											<div>${item['operationResult']?default('')?html}</div>
										</div>
									</div>
 								</td>
                    		</tr>
                    	</#list>
                    <#else>
                		<tr>
                			<td colspan="9" align="center">没有相关记录！</td>
                		</tr>
                    </#if>
                    </tbody>
				</table>
				</div>
			</div>
			<div class="bottom clearfix">
			<#if pageFinder?? >
				<#if query.showCount?string == "true" >
					<#import "../../../common.ftl" as page>
				<#else>
					<#import "../../../pageWithoutCount.ftl" as page>
				</#if>
				<@page.queryForm formId="queryForm"/>
			</#if>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
</body>
<script type="text/javascript">
function deleteApi(id) {
	if (confirm('确定删除该API?')) {
		$.ajax({ type : 'post', url: '${BasePath}/openapimgt/api/delete.sc', dataType: 'text', data: 'id=' + id,
			success: function(data, textStatus) {
				if ((typeof(data) == 'boolean' && data) || $.trim(data) == 'true') {
					alert('删除成功!');
					$('.btn-add-normal').click();
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

function rebuildApiInputParam() {
	var selector = document.getElementById("apiMethod");
	var paramNames = selector.options[selector.selectedIndex].getAttribute('aips').split(',');
	$('#apiInputParam').html('<option value="">请选择API参数</option>');
	for (var i = 0; i < paramNames.length - 1; i++) {
		$('#apiInputParam').append('<option value="' + paramNames[i] + '">' + paramNames[i] + '</option>');
	}
}

function rebuildApiInputParamValue() {
	if ($.trim($('#apiInputParam').val()) == '') {
		$('#apiInputParamValue').val('').hide();
	} else {
		$('#apiInputParamValue').show();
	}
}

var isExporting = false;
function doExport() {
	if (isExporting) {
		alert('导出中请稍候...');
		return false;
	}
	isExporting = true;
	window.location.href = '${BasePath}/openapimgt/api/log/export.sc?' + $('#queryForm').serialize();
}

function openIO(id) {
	openDiv({ title: 'I/O详情', width: 800, height: 500, content: '<pre>' + $('#' + id).html() + '</pre>' });
}

rebuildApiInputParam();
$('#apiInputParam').find('option[value="<#if apiInputParam??>${apiInputParam?default('')}</#if>"]').attr('selected', true).change();

$(function(){ 
	$('#fromOperated').calendar({format:'yyyy-MM-dd HH:mm:ss' }); 
	$('#toOperated').calendar({format:'yyyy-MM-dd HH:mm:ss' });
});
</script>
</html>
