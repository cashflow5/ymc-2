<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商城--商家后台</title>
<#include "../../../yitianwms/yitianwms-include.ftl">
<script type="text/javascript">	 
	function doQuery(){
		var queryForm=document.getElementById("queryForm");
		queryForm.action="../../../supply/manage/areawarehouseorder/queryotheroutstores.sc";
		queryForm.submit();
	}
	$(document).ready(function(){
			$('#bTime').calendar({ maxDate:'#eTime', format:'yyyy-MM-dd HH:mm:ss', targetFormat:'yyyy-MM-dd HH:mm:ss'});
			$('#eTime').calendar({ minDate:'#bTime', format:'yyyy-MM-dd HH:mm:ss', targetFormat:'yyyy-MM-dd HH:mm:ss'});
		});
//全选
$(document).ready(function(){
	$("#checkall").click( 
		function(){ 
			if(this.checked){ 
			$("input[name='commodityCB']").each(function(){this.checked=true;}); 
			}else{ 
				$("input[name='commodityCB']").each(function(){this.checked=false;}); 
			} 
		}
	);
});
 
 function doExportBySelOut(){
	var selectArray = getCheckBoxs("commodityCB");
	if (selectArray.length == 0) {
		alert("请选择出库单！");
		return;
	}
	var outCodes = "";
	var flag = ",";
	for (var i = 0; i < selectArray.length; i++) {
		var code = selectArray[i].value;
		if (i == selectArray.length - 1) {
			flag = "";
		}
		outCodes += code + flag;
	}
	document.getElementById("outCodes").value=outCodes;
	var exportBySelOutForm=document.getElementById("exportBySelOutForm");
	exportBySelOutForm.action="${BasePath}/yitianwms/region/regionoutstorage/doExportBySelOut.sc";
	exportBySelOutForm.submit();
 }
 
 //导出所有订单
	function exportAll(){
	var exportForm = document.getElementById("queryForm");
	exportForm.action = "${BasePath}/yitianwms/region/regionoutstorage/doExportAllOut.sc";
	exportForm.submit();
	// -_-!!
	exportForm.action = "../../../supply/manage/areawarehouseorder/queryotheroutstores.sc";
	}
</script>
</head>
<body>
<div class="container"> 
	<!--工具栏start-->
	<div class="toolbar">
		<div class="t-content">
			<div class="btn" onclick="doExportBySelOut();"><span class="btn_l"></span><b class="ico_btn add"></b><span class="btn_txt">导出选中</span><span class="btn_r"></span> </div>
			<div class="btn" onclick="exportAll();"><span class="btn_l"></span><b class="ico_btn add"></b><span class="btn_txt">导出全部</span><span class="btn_r"></span> </div>
		</div>
	</div>
	<!--工具栏end-->
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr"><span>出库单列表 </span></li>
			</ul>
		</div>
		<!--当前位置end-->
		<div class="modify"> 
				<!--普通搜索内容开始-->
			      <form name="queryForm" id="queryForm" method="POST">
			        <span>出库单号：</span>
			        <input name="storageCode" value="${storageCode?if_exists}" />
			        <span>创建时间：</span>
			        <input type="text" name="bTime" id="bTime" value="${bTime?if_exists}" readonly="readonly" size="20" />
			        -
			        <input type="text" name="eTime" id="eTime" value="${eTime?if_exists}" readonly="readonly" size="20" />
			       	 状态：<select name="status" id="status">
			       	 			<option value="">全部</option>
			       	 			<option value="0" <#if status??><#if status=="0">selected='true'</#if></#if>>待审核</option>
			       	 			<option value="1" <#if status??><#if status=="1">selected='true'</#if></#if>>已审核</option>
			       	 	  </select>
			        <input type="button" onclick="doQuery();" value="查询" class="wms-seach-btn" />
			      </form>
			      <form action="${BasePath}/yitianwms/region/regionoutstorage/doExportAllOut.sc" method="post" id="exportAll">
			      </form>
			      <form name="exportBySelOutForm" id="exportBySelOutForm" method="POST">
			      	<input type="hidden" id="outCodes" name="outCodes" />
			      </form>
				<!--普通搜索内容结束--> 	
			<!--列表start-->
			<table cellpadding="0" cellspacing="0" class="list_table">
	         <thead>
          <tr>
          	<th width="30"><input type="checkbox" value="" id="checkall" /></th>
            <th>出库单号</th>
            <th>物理仓库</th>
            <th>类型</th>
            <th>出库日期</th>
            <th>创建日期</th>
            <th>操作员</th>
            <th>状态</th>
            <th>货品总数</th>
            <th>订单总数</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
        <#if pageFinder?? && (pageFinder.data)?? > 
        <#list pageFinder.data as item >
        <tr>
          <td><input type="checkbox" name="commodityCB" id = "cbox${item_index}" value="${item.otherOutStoreCode?if_exists}" /></td>
          <td>${item.otherOutStoreCode?if_exists}</td>
          <td><#if item.warehouse??>${item.warehouse.warehouseName?if_exists}</#if></td>
          <td>
	          <!-- <#if item.outStoreType?? && outTypeVos??>
	           <#list outTypeVos as outTypeVos>
		          <#if outTypeVos.outType==item.outStoreType>${outTypeVos.outTypeName?if_exists}</#if>
	           </#list>
	          </#if>
	          -->
	          	地区仓调出
          </td>
          <td><#if item.outStoreDate??>${item.outStoreDate?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
          <td><#if item.createDate??>${item.createDate?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
          <td>${item.operatorId?if_exists}</td>
          <td>
	          <#if item.status?? && checkStatusVos??>
	           <#list checkStatusVos as checkStatusVos>
		          <#if checkStatusVos.status==item.status>${checkStatusVos.statusName?if_exists}</#if>
	           </#list>
	          </#if> 
          </td>
          <td><#if item.productTotal?? >
          			 ${item.productTotal?string('0')}
          		</#if>
          </td>
         <td>
         <#if item.orderTotal?? >${item.orderTotal?string('0')}</#if>
          </td>
          
          <td class="td0">
          	<a href="../../../supply/manage/areawarehouseorder/tootheroutstoredetail.sc?storageId=${item.id?if_exists}">详细</a> 
          </td>
        </tr>
        </#list> 
        <#else>
				<tr>
					<td class="td0" colspan="9">没有相关记录！</td>
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
      <form name="pageForm" id="pageForm"	action="../../../supply/manage/areawarehouseorder/queryotheroutstores.sc" method="POST">
        <input type="hidden" name="warehouseId"	id="warehouseId" value="${warehouseId?if_exists}" />
        <input type="hidden" name="storageCode" id="storageCode" value="${storageCode?if_exists}" />
	     <input type="hidden" name="bTime" id="bTime" value="${bTime?if_exists}" />
	    <input type="hidden" name="eTime" id="eTime" value="${eTime?if_exists}" />
	    <input type="hidden" name="status" value="${status?if_exists}" />
      </form>
</body>
</html>
<#include "../../../yitianwms/yitianwms-include-bottom.ftl">