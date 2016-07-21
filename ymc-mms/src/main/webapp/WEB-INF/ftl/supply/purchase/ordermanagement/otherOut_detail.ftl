<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/wms/css.css" />
<link rev="stylesheet" rel="stylesheet"  type="text/css" href="${BasePath}/css/wms/style.css"/>
<script type="text/javascript" src="${BasePath}/js/wms/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="${BasePath}/js/wms/alertDialog/artDialog.js"></script>
<script type="text/javascript" src="${BasePath}/js/wms/js.js"></script>
<script type="text/javascript" src="${BasePath}/js/wms/region/regionOutStorage_detail.js"></script>
<title>地区仓出库单明细</title>
<#include "../../../yitianwms/yitianwms-include.ftl">
<script type="text/javascript">

var doCheckRegionOutUrl="${BasePath}/yitianwms/region/regionoutstorage/checkRegionOut.sc"

var doRemoveRegionOutDetailUrl="${BasePath}/yitianwms/region/regionoutstorage/removeRegionOutDetail.sc"

function doReturn(){
		var queryForm=document.getElementById("delelteForm");
		queryForm.action="../../../supply/manage/areawarehouseorder/queryotheroutstores.sc";
		queryForm.submit();
	}
</script>
</head>
<body>


<div class="container"> 
	<!--工具栏start-->
	<div class="toolbar">
		<div class="t-content">
			<div class="btn" onclick="goback();"> <span class="btn_l"></span><b class="ico_btn back"></b><span class="btn_txt">返回</span><span class="btn_r"></span> </div>
			<div class="line"></div>
			 <form action="applyotheroutstore.sc" method="post" id="delelteForm">
	   <#if otherOutStore??> <#if otherOutStore.status??> 
		      <#if otherOutStore.status!=1 && otherOutStore.status!=2> 
		       <#if pageFinder?? && (pageFinder.data)?? >
		       <div class="btn" onclick="doCheckRegionOut('${otherOutStore.otherOutStoreCode?if_exists}');"> <span class="btn_l"></span><b class="ico_btn back"></b><span class="btn_txt">审核</span><span class="btn_r"></span> </div>
			 <div class="btn" onclick="doRemoveRegionOutDetail('${otherOutStore.id?if_exists}');"> <span class="btn_l"></span><b class="ico_btn back"></b><span class="btn_txt">删除</span><span class="btn_r"></span> </div>
			 </#if>
		      </#if>
		    </#if>
	    </#if>
	    </form>
		
		</div>
	</div>
	<!--工具栏end-->
	
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr"><span>出库单明细 </span></li>
			</ul>
		</div>
		<!--当前位置end-->
		<div class="modify"> 
			<!--列表start-->
			<div class="detail_top_box">
        <ul class="wms-storage-ul-2">
       <#if otherOutStore??>
        <li>出库单号：<#if otherOutStore??><#if otherOutStore.otherOutStoreCode??>${otherOutStore.otherOutStoreCode}</#if></#if></li>
        <li>出库日期：<#if otherOutStore??><#if otherOutStore.outStoreDate??>${otherOutStore.outStoreDate?string('yyyy-MM-dd HH:mm:ss')}</#if></#if></li>
        <li>物理仓库：<#if otherOutStore??><#if otherOutStore.warehouse??>${otherOutStore.warehouse.warehouseName}</#if></#if></li>
        <li>物理仓库：<#if otherOutStore??><#if otherOutStore.warehouse??>${otherOutStore.warehouse.warehouseName}</#if></#if></li>
        <li>发货仓库：<#if otherOutStore??><#if otherOutStore.intoWarehouse??>${otherOutStore.intoWarehouse.warehouseName}</#if></#if></li>
        <li>类型：地区仓调出</li>
        <li>操作员：<#if otherOutStore??><#if otherOutStore.operatorId??>${otherOutStore.operatorId}</#if></#if></li>
        <li>创建人：<#if otherOutStore??><#if otherOutStore.creator??>${otherOutStore.creator}</#if></#if></li>
        <li>创建日期：<#if otherOutStore??><#if otherOutStore.createDate??>${otherOutStore.createDate?string('yyyy-MM-dd HH:mm:ss')}</#if></#if></li>
        <li>审批日期：<#if otherOutStore??><#if otherOutStore.approverDate??>${otherOutStore.approverDate?string('yyyy-MM-dd HH:mm:ss')}</#if></#if></li>
        <li>审批人：<#if otherOutStore??><#if otherOutStore.approver??>${otherOutStore.approver}</#if></#if></li>
        <li>状 态：
          <#if otherOutStore.status?? && checkStatusVos??>
	          <#list checkStatusVos as checkStatusVos>
	          	<#if checkStatusVos.status==otherOutStore.status?default('')>${checkStatusVos.statusName?if_exists}</#if>
	          </#list>
          </#if> 
        </li>
        <li>备 注：<#if otherOutStore??><#if otherOutStore.remark??>${otherOutStore.remark}</#if></#if></li>
        <#else>
				<li >没有相关记录！</li>
        </#if>
      </ul>
			</div>
			<table cellpadding="0" cellspacing="0" class="list_table">
        <thead>
          <tr>
          	<th width="60"><input type="checkbox" id="checkall" name="checkall"/></th>
          	<th>来源单号</th>
            <th>货品编码</th>
            <th>商品名称</th>
            <th>规格</th>
            <th>数量</th>
          </tr>
        </thead>
        <tbody>
        <#if pageFinder?? && (pageFinder.data)?? >
	        <#list pageFinder.data as item >
			<tr>
			<td><input type="checkbox" id="check" name="idCheckBox" value="${item.id?if_exists}"/></td>
			<td><#if item.originCode??>${item.originCode}</#if></td>
			<td><#if item.commodityCode??>${item.commodityCode}</#if></td>
			<td><#if item.goodsName??>${item.goodsName}</#if></td>
			<td><#if item.specification??>${item.specification}</#if></td>
			<td class="td0">${item.quantity?default(0)}</td>
			</tr>
	        </#list>
        <#else>
				<tr>
					<td class="td0" colspan="6">没有相关记录！</td>
				<tr>	
        </#if>
        </tbody>
      </table>
			<!--列表end--> 
			<!--分页start-->
			<div class="bottom clearfix">
				<#import "../../../common.ftl"  as page>
          	<@page.queryForm formId="pageForm" />
			</div>
			<!--分页end--> 
		</div>
	</div>
</div>

 <form name="pageForm" id="pageForm" action="../../../supply/manage/areawarehouseorder/tootheroutstoredetail.sc"; method="POST">
        <input type="hidden" id="storageId" name="storageId" value="<#if storageId??>${storageId}</#if>" />
      </form>

</body>
</html>
<#include "../../../yitianwms/yitianwms-include-bottom.ftl">