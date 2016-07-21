<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商城--商家后台</title>
<#include "../../../yitianwms/yitianwms-include.ftl">
<script type="text/javascript">	 
var toAddUrl="${BasePath}/supply/manage/returnDefect/c_addReturnDefectUI.sc";
var doQueryUrl="${BasePath}/supply/manage/returnDefect/toQueryReturnDefectConfirm.sc";
var doExportUrl = "${BasePath}/supply/manage/returnDefect/doExpReturnDefectConfirmDetail.sc"
</script>
</head> 
<body>
<div class="container"> 
	<!--工具栏start-->
	<div class="toolbar">
		<div class="t-content">
			<div class="btn" onclick="javascript:toAdd()"> <span class="btn_l" ></span> <b class="ico_btn add"></b> <span class="btn_txt">新增退残确认单</span> <span class="btn_r"></span> </div>
			<div class="btn" onclick="javascript:doExpReturnDefectConfirmDetail()"> <span class="btn_l" ></span> <b class="ico_btn add"></b> <span class="btn_txt">导出详情</span> <span class="btn_r"></span> </div>
		</div>
	</div>
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr"><span>退残确认单 </span></li>
			</ul>
		</div>
		<!--当前位置end-->
		<div class="modify"> 
				<!--普通搜索内容开始-->
				<div class="add_detail_box">
				<form name="queryForm" id="queryForm" method="POST">
				<p>		
				
				<span>供&nbsp;应&nbsp;商：</span>
					<select id="supplierCode" name="supplierCode" <#if returnDefectConfirmQueryVo.supplierCode??></#if>>
						<#if supplierList??>
							<#list supplierList as item>
								<#if returnDefectConfirmQueryVo.supplierCode??>
									<#if item.key == returnDefectConfirmQueryVo.supplierCode?if_exists>
										<option value="${item.key?if_exists}" selected>
									<#else>
										<option value="${item.key?if_exists}">
									</#if>
									${item.value?if_exists}
									</option>
								<#else>
									<option value="${item.key?if_exists}">${item.value?if_exists}
								</#if>
							</#list>
						</#if>
			    </select>
			    
			      <span>状&nbsp;&nbsp;&nbsp;&nbsp;态：</span>
					 <select name="status" <#if returnDefectConfirmQueryVo.status??> </#if>>
						<#if statusList??>
							<#list statusList as item>
								<#if returnDefectConfirmQueryVo.status??>
									<#if item.key == returnDefectConfirmQueryVo.status?if_exists>
										<option value="${item.key?if_exists}" selected>
									<#else>
										<option value="${item.key?if_exists}">
									</#if>
									${item.value?if_exists}
									</option>
								<#else>
									<option value="${item.key?if_exists}">${item.value?if_exists}
								</#if>
							</#list>
						</#if>
					</select>
		
					<span>确&nbsp;认&nbsp;人：</span>
 					<input type="text" name="approver" value="${returnDefectConfirmQueryVo.approver?if_exists}" />
			      </p>
			       
			      <p>
			       	<span>确认单号：</span>
			        <input type="text" name="defectConfirmCode" value="${returnDefectConfirmQueryVo.defectConfirmCode?if_exists}" />
			        <span>确认时间：</span>
			        <input type="text" name="beginApproverDate" id="bTime" value="${returnDefectConfirmQueryVo.beginApproverDate?if_exists}" readonly="readonly" size="20" />
			        -
			        <input type="text" name="endApproverDate" id="eTime" value="${returnDefectConfirmQueryVo.endApproverDate?if_exists}" readonly="readonly" size="20"  />
			        <input type="button" onclick="doQuery();" value="查询" class="btn-add-normal" />
			      </p>
	      	</form>
	      	</div>
				<!--普通搜索内容结束--> 	
			<!--列表start-->
			<table cellpadding="0" cellspacing="0" class="list_table">
	        <thead>
	          <tr>
	            <th>确认单号</th>
	            <th>确认人</th>
	            <th>确认时间</th>
	            <th>供应商</th>
	            <th>状态</th>
	            <th>操作</th>
	          </tr>
	        </thead>
	        <tbody>
	        <#if pageFinder?? && (pageFinder.data)?? > 
	        <#list pageFinder.data as item >
	        <tr>
	          <td>${item.defectConfirmCode?if_exists}</td>
	          <td>${item.approver?if_exists}</td>
	          <td>${item.approverDate?if_exists}</td>
	          <td>${item.supplierName?if_exists}</td>
	          <td>
	          <#if item.status??>
	           		<#if item.status == 1 >
	           		  已确认
	          		<#else>
	          		  待确认
	          		</#if>
	          </#if>
	          </td>
	          <td>
	          	<a href="#" onclick="javascript:doQueryDetail('${item.id}')">详情</a> 
	          </td>
	        </tr>
	        </#list> 
	        <#else>
	        <tr>
	          <td  colspan="8">没有相关记录！</td>
	        <tr> 
	        </#if>
	      </tbody>
	      </table>
	      	 <!--列表end--> 
        </div>
		<!--分页start-->
		<div class="bottom clearfix">
	    	<#import "../../../common.ftl" as page>
	      	<@page.queryForm formId="pageForm" />
		</div>
		<!--分页end--> 
	</div>
</div>
<form name="pageForm" id="pageForm"	action="${BasePath}/supply/manage/returnDefect/toQueryReturnDefectConfirm.sc" method="POST" >
      	<input type="hidden" name="defectConfirmCode" value="${returnDefectConfirmQueryVo.defectConfirmCode?if_exists}" />
        <input type="hidden" name="supplierCode" value="${returnDefectConfirmQueryVo.supplierCode?if_exists}" />
        <input type="hidden" name="status" value="${returnDefectConfirmQueryVo.status?if_exists}" />
        <input type="hidden" name="approver" value="${returnDefectConfirmQueryVo.approver?if_exists}" />
        <input type="hidden" name="beginApproverDate" value="${returnDefectConfirmQueryVo.beginApproverDate?if_exists}" />
        <input type="hidden" name="endApproverDate" value="${returnDefectConfirmQueryVo.endApproverDate?if_exists}" />
</form>
</body>
</html>
<#include "../../../yitianwms/yitianwms-include-bottom.ftl">
<script type="text/javascript">
function toAdd() {
	window.location.href = toAddUrl;
}
//导出报表
function doExpReturnDefectConfirmDetail(){
	var exprotForm=document.getElementById("queryForm");
	exprotForm.action = doExportUrl;
	exprotForm.submit();
}
function doQuery() {
	var queryForm = document.getElementById("queryForm");
	queryForm.action = doQueryUrl;
	queryForm.submit();
}
function doQueryDetail(id){
	//var viewReturnDefectUrl="${BasePath}/supply/manage/returnDefect/toSaveConfirm.sc?mainId="+id+"";
	//openwindow(viewReturnDefectUrl, 950, 700, '明细');
    window.location.href = "${BasePath}/supply/manage/returnDefect/toSaveConfirm.sc?mainId="+id+"";
}

$(document).ready(function() {
	$('#bTime').calendar({
		maxDate : '#eTime',
		format : 'yyyy-MM-dd',
		targetFormat : 'yyyy-MM-dd'
	});
	$('#eTime').calendar({
		minDate : '#bTime',
		format : 'yyyy-MM-dd',
		targetFormat : 'yyyy-MM-dd'
	});
});
</script>