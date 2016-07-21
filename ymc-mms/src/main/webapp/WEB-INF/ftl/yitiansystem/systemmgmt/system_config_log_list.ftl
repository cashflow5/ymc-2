<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,优购网,B网络营销系统,商品管理" />
<meta name="Description" content=" , ,B网络营销系统-商品管理" />
<#include "../orderCss.ftl"/>
<title>B网络营销系统-采购管理-优购网</title>
</script>
<script type="text/javascript"  src="${BasePath}/js/yitiansystem/systemmgmt/systemconfig.js"></script>
</head>
<body>
	<div class="container">
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
			<div class="add_detail_box">
				<form id="queryForm" name="queryForm" action="querySystemConfigLogList.sc">
					<p>
						<span>
							<label>配置名称：</label>
							<input name="info_id" class="inputtxt" value="<#if vo ?? && vo.info_id ??>${vo.info_id}</#if>"/>
						</span>
						<span>
							<label>操作人账户：</label>
							<input name="user_id" class="inputtxt" value="<#if vo ?? && vo.user_id ??>${vo.user_id}</#if>"/>
						</span>
						<span>
							<label>操作日期：</label>
								<input id="datepicker_start" class="inputtxt" type="text" style="width:120px;" readonly="readonly" value="<#if vo ?? && vo.state_time ??>${vo.state_time}</#if>" name="state_time" size="12">
						 		&nbsp;至&nbsp;
								<input id="datepicker_end" class="inputtxt" type="text" style="width:120px;" readonly="readonly" value="<#if vo ?? && vo.end_time ??>${vo.end_time}</#if>" name="end_time" size="12">
							</span>
						<span>
							<input type="button" class="btn-add-normal" value="搜索" onclick="mysubmit()" />
						</span>
					</p>
				</form>
			</div>
		 	<div class="modify">  
					<div class="blank10"></div>	
				 	<table cellpadding="0" cellspacing="0" class="list_table">
					    <thead>
			            	<tr>
			            		 <th>操作时间</th>
					            <th>操作人账户 </th>
					            <th>操作人姓名</th>
					            <th>操作IP地址 </th>
					            <th>操作事项</th>
				            </tr>              
	            		</thead>
					    <tbody>
						    <#if pageFinder??>
						    <#if pageFinder.data??>				 
						    <#list pageFinder.data as op>
						    <tr class="div-pl-list"> 
						   	 <td>${op.create_time?default("")}</td>						
								<td>${op.user_id?default("")}</td>
								<td>${op.user_name?default("")}</td>				
								<td>${op.operate_ip?default("")}</td>	
								<td>${op.operate_desc?default("")}</td>
						  	</tr>
						  	</#list>	
						  	</#if>
			 			  	</#if>
		 			  </tbody>				 
				  	</table>
				<div class="bottom clearfix">
				    <#import "../../common.ftl"  as page>
					<@page.queryForm formId="queryForm" />
				</div>
		 	</div>
		 </div>
	</div>
</body>
</html>
<#include "../orderJs.ftl"/>
<script type="text/javascript">
function mysubmit(){
	
	$("#queryForm").submit();
}
$(function(){ 
$('#datepicker_start').calendar({maxDate:'#datepicker_end',format:'yyyy-MM-dd HH:mm:ss' }); 
$('#datepicker_end').calendar({minDate:'#datepicker_start',format:'yyyy-MM-dd HH:mm:ss' });
});
</script>