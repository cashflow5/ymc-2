<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,优购网,B网络营销系统,商品管理" />
<meta name="Description" content=" , ,B网络营销系统-商品管理" />
<#include "../orderCss.ftl"/>
<title>B网络营销系统-采购管理-优购网</title>
</script>
</head>
<body>
	<div class="container">
		<div class="toolbar">
			<div class="t-content"> <!--操作按钮start--> 
				<div class="btn">
					<span class="btn_l"></span>
		        	<b class="ico_btn add"></b>
		        	<span class="btn_txt"><a href="#" onclick="history.go(-1);">返回</a></span>
		        	<span class="btn_r"></span>
		    	</div> 
			</div>
		</div>
		<div class="list_content">
			<div class="top clearfix">
				<ul class="tab">
					<li class='curr' ><span>系统用户操作日志列表</span></li>
				</ul>
			</div>
		 	<div class="modify">  
				<form id="queryForm" name="queryForm" action="queryOperaterLogList.sc">
				<input type="hidden" name="userId" id="userId" value="${systemmgtUserOperateLog.userId}"/>
					<div class="blank10"></div>	
				 	<table cellpadding="0" cellspacing="0" class="list_table">
					    <thead>
			            	<tr>
					            <th>操作人账户 </th>
					            <th>操作人姓名</th>
					            <th>操作IP地址 </th>
					            <th>操作时间</th>
					            <th>操作事项</th>
				            </tr>              
	            		</thead>
					    <tbody>
						    <#if pageFinder??>
						    <#if pageFinder.data??>				 
						    <#list pageFinder.data as op>
						    <tr class="div-pl-list"> 		
								<td>${op.operateAccount?default("")}</td>
								<td>${op.operateName?default("")}</td>				
								<td>${op.operateIp?default("")}</td>												
								<td>${op.operateDate?default("")}</td>						
								<td>${op.operateRemark?default("")}</td>
						  	</tr>
						  	</#list>	
						  	</#if>
			 			  	</#if>
		 			  </tbody>				 
				  	</table>
				</form>	
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