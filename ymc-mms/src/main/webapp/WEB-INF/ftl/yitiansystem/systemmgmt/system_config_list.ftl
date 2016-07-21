<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-index.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" />
<meta name="Description" content="" />
<title>B网络营销系统-系统管理-系统配置</title>
</head>
<script type="text/javascript"  src="${BasePath}/js/yitiansystem/systemmgmt/systemconfig.js"></script>
<body>
<div class="container"  id="main_body">
	<div class="toolbar">
		<div class="t-content">
			<div class="btn" onclick="javascript:toAddConfig();">
				<span class="btn_l"></span>
	        	<b class="ico_btn add"></b>
	        	<span class="btn_txt">增加</span>
	        	<span class="btn_r"></span>
        	</div>
        	<div class="btn" onclick="javascript:updateCache();">
				<span class="btn_l"></span>
	        	<b class="ico_btn add"></b>
	        	<span class="btn_txt">一键更新缓存</span>
	        	<span class="btn_r"></span>
        	</div>  
		</div>
	</div>
	<div class="list_content">
		<div class="top clearfix">
		<#if selectTab?? && selectTab !="-1">
			<ul class="tab">
				<li <#if selectTab?? && selectTab="0">class="curr"<#else>class=</#if>><span><a href="queryUseSystemConfigList.sc" class="btn-onselc" >使用中配置</a></span></li>
				<li <#if selectTab?? && selectTab="1">class="curr"<#else>class=</#if>><span><a href="querySystemConfigList.sc"  class="btn-onselc" >所有配置</a></span></li>
				<li <#if selectTab?? && selectTab="2">class="curr"<#else>class=</#if>><span><a href="toQuerySystemConfigLogList.sc"  class="btn-onselc" >操作日志</a></span></li>
			</ul>
		</#if>
		</div>
		<div class="modify">
			<table cellpadding="0" cellspacing="0" class="list_table">
            <thead>
            <tr>
	            <th>配置名称</th>
	            <th>键</th>
	            <th>值</th>
	            <th>状态</th>
				<th>备注</th>
	            <th colspan="2">操作</th>
            </tr>
            </thead>
            <tbody>
            	<#if configList ?? >
		      		<#list configList as item>
		      		<tr id='Tr${item.id}'>
		                <td>
		                	<#if item.configName?length lt 25 >
								${item.configName!""}
		                 	<#else>
		                 		${item.configName[0..24]!""}...
		                 	</#if>
		                </td>
		                <td title="${item.key!""}">
		                 	<#if item.key?length lt 25 >
								${item.key!""}
		                 	<#else>
		                 		${item.key[0..24]!""}...
		                 	</#if>
		                </td>
		                 <td title="${item.value!""}">
		                 	<#if item.value ??>
			                 	<#if item.value?length lt 25 >
									${item.value!""}
			                 	<#else>
			                 		${item.value[0..24]!""}...
			                 	</#if>
		                 	</#if>
		                </td>
		                <td>
	                		<#if item.deleteFlag?? && item.deleteFlag == "1" >
	                			正在使用
		                 	<#else>
		                 		已停用
		                 	</#if>
		                </td>
		                 <td  title="${item.remark!""}">
		                 	<#if item.remark?? >
			                	<#if item.remark?length lt 25 >
									${item.remark!""}
			                 	<#else>
			                 		${item.remark[0..24]!""}...
			                 	</#if>
		                 	</#if>
		                </td>
		                <td>
		                	<a href="javascript:toUpdateConfig('${item.id}');" target="mbdif">编辑</a>
		                </td>
		                <#if selectTab?? && selectTab="0">
		                	 <td>
		                		<a href="javascript:removeConfig('${item.id}');" >删除</a>
		                	</td>
		                </#if>
	                	<#if !item.deleteFlag?? || item.deleteFlag == "0" >
			                 <td>
		                		<a href="#" onclick="javascript:location.href='u_useConfig.sc?id=${item.id}'" >启用</a>
			                </td>
	                	</#if>
		            </tr>
		      		</#list>
	      		</#if>
            </tbody>
            </table>
		</div>
	</div>
</div>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script> 
</body>
</html>