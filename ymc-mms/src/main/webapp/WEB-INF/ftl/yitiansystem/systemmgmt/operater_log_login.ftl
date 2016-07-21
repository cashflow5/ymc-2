<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,优购网,B网络营销系统,商品管理" />
<meta name="Description" content=" , ,B网络营销系统-商品管理" />
<#include "../orderCss.ftl"/>
<script type="text/javascript">
$(document).ready(
	function() {
	$('#state_time').calendar({ maxDate:'#end_time', format:'yyyy-MM-dd', targetFormat:'yyyy-MM-dd'});
	$('#end_time').calendar({ minDate:'#state_time', format:'yyyy-MM-dd', targetFormat:'yyyy-MM-dd'});
});
</script>
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
					<li class='curr' ><span>${log.user_name!""}登录日志</span></li>
				</ul>
			</div>
		 	<div class="modify">  
				<form id="queryForm" name="queryForm" action="queryOperateLog.sc" method="post">
					<input type="hidden" name="url" id="url" value="login"/>
					<input type="hidden" name="user_id" id="user_id" value="${log.user_id!""}"/>
					<div class="add_detail_box">
						<p>
						<label>登录类型：</label><input type="text" name="operate_type" id="operate_type" value="${log.operate_type?if_exists}" />
						<label>登录时间：</label>
						<input type="text" id="state_time" name="state_time" value="${log.state_time?if_exists}" />
						-
						<input type="text" id="end_time" name="end_time" value="${log.end_time?if_exists}" />
			      		<label>登录IP地址：</label><input type="text" name="operate_ip" id="operate_ip" value="${log.operate_ip?if_exists}" />
			      		<input type="submit" value="查询" class="btn-add-normal" />
						</p>
					</div>
					<div class="blank10"></div>	
				 	<table cellpadding="0" cellspacing="0" class="list_table">
					    <thead>
			            	<tr>
					            <th>登录类型</th>
					            <th>登录时间</th>
					            <th>登录IP地址</th>
				            </tr>              
	            		</thead>
					    <tbody>
						    <#if pageFinder??>
						    <#if pageFinder.data??>				 
						    <#list pageFinder.data as op>
						    <tr class="div-pl-list"> 		
								<td>${op.operate_desc?default("")}</td>
								<td>${op.create_time?default("")}</td>				
								<td>${op.operate_ip?default("")}</td>												
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