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
			<div class="btn" onclick="openwindow('${BasePath}/openapimgt/api/view.sc',　800,　500,　'添加API')">
				<span class="btn_l"></span><b class="ico_btn add"></b><span class="btn_txt">添加API</span><span class="btn_r"></span> </div>
		</div>
	</div>
	<!--工具栏end-->
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr"><span>API管理</span></li>
			</ul>
		</div>
		<!--当前位置end-->
		<div class="modify"> 
			<!--搜索开始-->
			<div class="add_detail_box">
				<form id="queryForm" name="queryForm" action="${BasePath}/openapimgt/api/list.sc" method="post">
    		       	<label for="apiCode">API代码：</label>
    		       	<input type="text" id="apiCode" name="apiCode" value="<#if api??>${api.identifier?default('')}</#if>" />
           			<label for="apiMethod">API实现方法：</label>
           			<input type="text" id="apiMethod" name="apiMethod" value="<#if api??>${api.apiMethod?default('')}</#if>" />
           			<label for="apiWeight">API权重：</label>
    		       	<select id="apiWeight" name="apiWeight">
    		       		<option value="">请选择</option>
    		       		<#list bitWeights as item>
    		       			<#if item.label??>
    		       				<option value="${item.value}" <#if api?? && item.value == api.apiWeight?default(0)>selected="selected"</#if>>${item.label}</option>
    		       			</#if>
    		       		</#list>
    		       	</select>
           			<br />
    		       	<label for="apiCategoryId">API分类：</label>
    		       	<select id="apiCategoryId" name="apiCategory.id">
    		       		<option value="">请选择</option>
    		       		<#list apiCategorys as item>
    		       		<option value="${item.id}" <#if api?? && api.apiCategory?? && item.id == api.apiCategory.id?default('')>selected="selected"</#if>>${item.categoryName}（${item.categoryCode}）</option>
    		       		</#list>
    		       	</select>
           			<label for="apiVersionId">API版本：</label>
           			<select id="apiVersionId" name="apiVersion.id">
    		       		<option value="">请选择</option>
    		       		<#list apiVersions as item>
    		       		<option value="${item.id}" <#if api?? && api.apiVersion?? && item.id == api.apiVersion.id?default('')>selected="selected"</#if>>${item.versionNo}</option>
    		       		</#list>
    		       	</select>
           			<label for="apiImplementorId">API实现者：</label>
           			<select id="apiImplementorId" name="apiImplementor.id">
    		       		<option value="">请选择</option>
    		       		<#list apiImplementors as item>
    		       		<option value="${item.id}" <#if api?? && api.apiImplementor?? && item.id == api.apiImplementor.id?default('')>selected="selected"</#if>>${item.identifier}</option>
    		       		</#list>
    		       	</select>
            		<input type="submit" class="btn-add-normal" value="搜索" />
              	</form>
			</div>
			<!--搜索结束--> 
			<!--列表start-->
			<div class="tbox">
			<table class="list_table" ellspacing="0" cellpadding="0" border="0">
					<thead>
	                    <tr>
	                    <th style="width:10%;">API代码</th>
	                    <th style="width:10%;">API名称</th>
	                    <th style="width:10%;">API实现者</th>
	                    <th style="width:10%;">API实现方法</th>
	                    <th style="width:10%;">API实现方法参数类型</th>
	                    <th style="width:10%;">API描述</th>
	                    <th style="width:10%;">API权重</th>
	                    <th style="width:10%;">API分类</th>
	                    <th style="width:10%;">API版本</th>
	                    <th style="width:10%;">操作</th>
	                    </tr>              
	                </thead>
                    <tbody>
                    <#if pageFinder?? && pageFinder.data?? && pageFinder.data?size != 0>
                    	<#list pageFinder.data as item>
                    		<tr>
                    			<td>${item.apiCode?default('')}</td>
                    			<td>${item.apiName?default('')}</td>
                    			<td>${item.apiImplementor.implementorClass?default('')}</td>
                    			<td>${item.apiMethod?default('')}</td>
                    			<td>${item.apiMethodParamTypes?default('')}</td>
                    			<td><div style="width: 160px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; -o-text-overflow: ellipsis;" onmouseover="javascript:this.title=this.innerHTML;">${item.apiDescription?default('')}</div></td>
                    			<td>
                    				<#list statics['com.yougou.api.constant.BitWeight'].values() as bitWeight>
                    					<#if bitWeight.in(item.apiWeight?default(0))>
                    					${bitWeight.label?default('')}<br/>
                    					</#if>
                    				</#list>
                    			</td>
                    			<td>${item.apiCategory.categoryName?default('')}</td>
                    			<td>${item.apiVersion.versionNo?default('')}</td>
                    			<td id="link_${item.id}" class="td0">
                    				<#if item.isEnable == '0'>
                    					<a href="javascript:void(0);" onclick="updateStatus('${item.id}', '1');"><span style="color:red;">禁用</span></a>
                    				<#else>
                    					<a href="javascript:void(0);" style="" onclick="updateStatus('${item.id}', '0');"><span style="color:#228B22;">启用</span></a>
                    				</#if>
									<a href="${BasePath}/openapimgt/api/view.sc?id=${item.id}">修改</a>
									<a href="javascript:void(0);" onclick="deleteApi('${item.id}')">删除</a>
 								</td>
                    		</tr>
                    	</#list>
                    <#else>
                		<tr>
                			<td colspan="10" align="center">没有相关记录！</td>
                		</tr>
                    </#if>
                    </tbody>
				</table>
				</div>
			</div>
			<div class="bottom clearfix">
				<#if pageFinder??>
					<#import "../../../common.ftl" as page>
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

function updateStatus(id, isEnable) {
	$.ajax({ type : 'post', url: '${BasePath}/openapimgt/api/status.sc', data: {id:id,isEnable:isEnable},
			success: function(data, textStatus) {
				if ((typeof(data) == 'boolean' && data) || $.trim(data) == 'true') {
					if (isEnable == '1') {alert('禁用成功!');}
					else {alert('启用成功!');}
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
</script>
</html>
