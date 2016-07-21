<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商城--商家后台</title>
<#include "../../../yitianwms/yitianwms-include.ftl">
<script type="text/javascript">	 
	function doQuery(){
		var queryForm=document.getElementById("queryForm");
		queryForm.action="${BasePath}/supply/manage/areawarehousehistoryorder/queryAreaWarehouseHistoryOrder.sc";
		queryForm.submit();
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
	$("#checkall").click(function() {
				if (this.checked) {
					$("input[name='orderCheckBox']").each(function() {
								this.checked = true;
							});
				} else {
					$("input[name='orderCheckBox']").each(function() {
								this.checked = false;
							});
				}
			});
});
// 导出选中订单
function doExportSel() {
	document.getElementById("orderCodes").value = "";

	var orderSel = getCheckBoxs('orderCheckBox');
	if (orderSel.length == 0) {
		alert("请选择要导出的订单!");
		return;
	}
	var orderCodes = "";
	for (var i = 0; i < orderSel.length; i++) {
		orderCodes += orderSel[i].value + ',';
	}
	document.getElementById("orderCodes").value = orderCodes;
	var exportForm = document.getElementById("exportForm");
	exportForm.action = "${BasePath}/yitianwms/region/regionorder/doExportSel.sc";
	exportForm.submit();
}
</script>
</head>
<body>
<div class="container"> 
	<!--工具栏start-->
	<div class="toolbar">
		<div class="t-content">
			<div class="btn" onclick="doExportSel();"><span class="btn_l"></span><b class="ico_btn add"></b><span class="btn_txt">导出选中</span><span class="btn_r"></span> </div>
		</div>
	</div>
	<!--工具栏end-->
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr"><span>已出库销售订单列表 </span></li>
			</ul>
		</div>
		<!--当前位置end-->
		<div class="modify"> 
				<!--普通搜索内容开始-->
      <form name="queryForm" id="queryForm" method="POST">
        <span>订单号：</span>
        <input name="orderCode" value="${orderCode?if_exists}" />
        <span>下单时间：</span>
        <input type="text" name="bTime" id="bTime" value="${bTime?if_exists}" readonly="readonly" size="20" />
        -
        <input type="text" name="eTime" id="eTime" value="${eTime?if_exists}" readonly="readonly" size="20" />
        <input type="button" onclick="doQuery();" value="查询" class="wms-seach-btn" />
      </form>
      <form name="exportForm" id="exportForm" method="POST">
      	 <input type="hidden" id="orderCodes" name="orderCodes" />
      </form>
				<!--普通搜索内容结束--> 	
			<!--列表start-->
			<table cellpadding="0" cellspacing="0" class="list_table">
	         <thead>
          <tr>
            <th width="60"><input type="checkbox" id="checkall" name="checkall"/></th>
            <th>订单号</th>
            <th>仓库名称</th>
            <th>原始仓库名称</th>
            <th>支付方式</th>
            <th>下单时间</th>
            <th>订单状态</th>
            <th>地区仓库发货状态</th>
            <th>处理时间</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
        <#if pageFinder?? && (pageFinder.data)?? > 
        <#list pageFinder.data as item >
        <tr>
          <td><input type="checkbox" id="check" name="orderCheckBox" value="${item.orderCode?if_exists}"/></td>
          <td>${item.orderCode?if_exists}</td>
          <td>${item.wareHouseName?if_exists}</td>
          <td>${item.orgWareHouseName?if_exists}</td>
          <td>${item.payMode?if_exists}</td>
          <td><#if item.orderDate??>${item.orderDate?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
          <td>${item.orderStatus?if_exists}</td>
          <td>${item.deliveryStatus?if_exists}</td>
          <td><#if item.dealTime??>${item.dealTime?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
          <td class="td0"><a href="${BasePath}/supply/manage/areawarehousehistoryorder/toRegionOrderDetail.sc?orderId=${item.orderId}">详情</a></td>
        </tr>
        </#list> 
        <#else>
        <tr>
          <td class="td0" colspan="8">没有相关记录！</td>
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
      <form name="pageForm" id="pageForm" action="${BasePath}/supply/manage/areawarehousehistoryorder/queryAreaWarehouseHistoryOrder.sc" method="POST">
        <input type="hidden" name="orderCode" id="orderCode" value="${orderCode?if_exists}" />
        <input type="hidden" name="bTime" id="bTime" value="${bTime?if_exists}" />
        <input type="hidden" name="eTime" id="eTime" value="${eTime?if_exists}" />
      </form>
</body>
</html>
<#include "../../../yitianwms/yitianwms-include-bottom.ftl">