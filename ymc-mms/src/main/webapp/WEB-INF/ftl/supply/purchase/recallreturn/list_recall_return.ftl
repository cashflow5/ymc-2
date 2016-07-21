<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商城--商家后台</title>
<#include "../../../yitianwms/yitianwms-include.ftl">
<script type="text/javascript">	 
var toAddUrl="${BasePath}/supply/manage/recallreturn/c_addRecallReturnUI.sc";
var doQueryUrl="${BasePath}/supply/manage/recallreturn/toQueryRecallReturnApply.sc";
</script>
</head> 
<body>
<div class="container"> 
	<!--工具栏start-->
	<div class="toolbar">
		<div class="t-content">
			<div class="btn" onclick="javascript:toAdd()"> <span class="btn_l" ></span> <b class="ico_btn add"></b> <span class="btn_txt">新增召回返回申请单</span> <span class="btn_r"></span> </div>
		</div>
	</div>
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr"><span>召回返回申请单 </span></li>
			</ul>
		</div>
		<!--当前位置end-->
		<div class="modify"> 
				<!--普通搜索内容开始-->
				<div class="add_detail_box">
				<form name="queryForm" id="queryForm" method="POST">
				<p>		
					<span>申请单号：</span>
				    <input type="text" name="defectApplyCode" value="${returnDefectSpQueryVo.defectApplyCode?if_exists}" />
					<span>供&nbsp;应&nbsp;商：</span>
						<select id="supplierCode" name="supplierCode" <#if returnDefectSpQueryVo.supplierCode??></#if>>
							<#if supplierList??>
								<#list supplierList as item>
									<#if returnDefectSpQueryVo.supplierCode??>
										<#if item.key == returnDefectSpQueryVo.supplierCode?if_exists>
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
				    <span>仓&nbsp;&nbsp;&nbsp;&nbsp;库：</span>
						<select id="warehouseId" name="warehouseId" <#if returnDefectSpQueryVo.warehouseId??></#if>>
							<#if warehouseList??>
								<#list warehouseList as item>
									<#if returnDefectSpQueryVo.warehouseId??>
										<#if item.key == returnDefectSpQueryVo.warehouseId?if_exists>
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
			   </p>   	 
		       <p>
					<span>申&nbsp;请&nbsp;人：</span>
 					<input type="text" name="creator" value="${returnDefectSpQueryVo.creator?if_exists}" />
			      	<span>状&nbsp;&nbsp;&nbsp;&nbsp;态：</span>
					 <select name="status" <#if returnDefectSpQueryVo.status??> </#if>>
						<#if statusList??>
							<#list statusList as item>
								<#if returnDefectSpQueryVo.status??>
									<#if item.key == returnDefectSpQueryVo.status?if_exists>
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
					 
					 <span>库存类型：</span>
					 <select name="goodsType" <#if returnDefectSpQueryVo.goodsType??> </#if>>
						<#if goodsTypeList??>
							<#list goodsTypeList as item>
								<#if returnDefectSpQueryVo.goodsType??>
									<#if item.key == returnDefectSpQueryVo.goodsType?if_exists>
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
			   </p>
			       
			      <p>
			        <span>申请日期：</span>
			        <input type="text" name="beginApproverDate" id="bTime" value="${returnDefectSpQueryVo.beginApproverDate?if_exists}" readonly="readonly" size="20" />
			        -
			        <input type="text" name="endApproverDate" id="eTime" value="${returnDefectSpQueryVo.endApproverDate?if_exists}" readonly="readonly" size="20"  />
			        <input type="button" onclick="doQuery();" value="查询" class="btn-add-normal" />
			      </p>
	      	</form>
	      	</div>
				<!--普通搜索内容结束--> 	
			<!--列表start-->
			<table cellpadding="0" cellspacing="0" class="list_table">
	        <thead>
	          <tr>
	            <th>申请单号</th>
	            <th>供应商</th>
	            <th>仓库</th>
	            <th>申请日期</th>
	            <th>申请数量</th>
	            <th>状态</th>
	            <th>库存类型</th>
	            <th>入库数量</th>
	            <th>操作</th>
	          </tr>
	        </thead>
	        <tbody>
	        <#if pageFinder?? && (pageFinder.data)?? > 
	        <#list pageFinder.data as item >
	        <tr>
	          <td>${item.defectApplyCode?if_exists}</td>
	          <td>${item.supplierName?if_exists}</td>
	          <td>${item.warehouseName?if_exists}</td>
	          <td>${item.createDate?if_exists}</td>
	          <td>${item.confirmQuantity?if_exists}</td>
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
		          <#if item.goodsType??>
		           		<#if item.goodsType == 1 >
		           		  正品
		          		<#else>
		          		  残品
		          		</#if>
		          </#if>
	          </td>
	          <td>${item.intostoreQuantity?if_exists}</td>
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
<form name="pageForm" id="pageForm"	action="${BasePath}/supply/manage/recallreturn/toQueryRecallReturnApply.sc" method="POST" >
      	<input type="hidden" name="defectApplyCode" value="${returnDefectSpQueryVo.defectApplyCode?if_exists}" />
        <input type="hidden" name="supplierCode" value="${returnDefectSpQueryVo.supplierCode?if_exists}" />
        <input type="hidden" name="warehouseId" value="${returnDefectSpQueryVo.warehouseId?if_exists}" />
        <input type="hidden" name="status" value="${returnDefectSpQueryVo.status?if_exists}" />
        <input type="hidden" name="creator" value="${returnDefectSpQueryVo.creator?if_exists}" />
        <input type="hidden" name="beginApproverDate" value="${returnDefectSpQueryVo.beginApproverDate?if_exists}" />
        <input type="hidden" name="endApproverDate" value="${returnDefectSpQueryVo.endApproverDate?if_exists}" />
</form>
</body>
</html>
<#include "../../../yitianwms/yitianwms-include-bottom.ftl">
<script type="text/javascript">
function toAdd() {
	window.location.href = toAddUrl;
}
function doQuery() {
	var queryForm = document.getElementById("queryForm");
	queryForm.action = doQueryUrl;
	queryForm.submit();
}
function doQueryDetail(id){
    window.location.href = "${BasePath}/supply/manage/recallreturn/toSaveSp.sc?mainId="+id+"";
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