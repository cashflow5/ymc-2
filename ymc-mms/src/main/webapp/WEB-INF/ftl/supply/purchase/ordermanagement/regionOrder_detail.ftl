<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<#include "../../../yitianwms/yitianwms-include.ftl">
<script type="text/javascript">
	function goback(){
		window.location.href="${BasePath}/supply/manage/areawarehousehistoryorder/queryAreaWarehouseHistoryOrder.sc";
	}
</script>
</head>
<body>
<div class="container"> 
	<!--工具栏start-->
	<div class="toolbar">
		<div class="t-content">
			<div class="btn" onclick="goback();"> <span class="btn_l"></span><b class="ico_btn back"></b><span class="btn_txt">返回</span><span class="btn_r"></span> </div>
		</div>
	</div>
	<!--工具栏end-->
	
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr"><span>已出库销售订单明细 </span></li>
			</ul>
		</div>
		<!--当前位置end-->
		<div class="modify"> 
			<!--列表start-->
			<div class="detail_top_box">
        <ul class="wms-storage-ul-2">
       <li>订单号：${regionOrderFormVo.orderCode?if_exists}</li>
       <li>支付方式：${regionOrderFormVo.payMode?if_exists}</li>
       <li>下单时间：<#if regionOrderFormVo.orderDate??>${regionOrderFormVo.orderDate?string('yyyy-MM-dd HH:mm:ss')}</#if></li>
       <li>订单状态：${regionOrderFormVo.orderStatus?if_exists}</li>
      </ul>
			</div>
			<table class="list_table" border="0" cellpadding="0" cellspacing="0">
				 <thead>
          <tr>
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
          <td>${item.commodityCode?if_exists}</td>
          <td>${item.goodsName?if_exists}</td>
		  <td>${item.specification?if_exists}</td>
          <td class="td0">${item.quantity?if_exists}</td>
        </tr>
        </#list>
        <#else>
        <tr>
          <td class="td0" colspan="4">没有相关记录！</td>
        <tr> 
        </#if>
          </tbody>
			</table>
			<!--列表end--> 
			<!--分页start-->
			<div class="bottom clearfix">
				<div class="page">       
				<#import "../../../common.ftl"  as page>
      			<@page.queryForm formId="pageForm" />
				</div>
			</div>
			<!--分页end--> 
		</div>
	</div>
</div>
      <form name="pageForm" id="pageForm" action="${BasePath}/yitianwms/region/regionorder/toRegionOrderDetail.sc"; method="POST">
        <input type="hidden" id="orderId" name="orderId" value="${orderId?if_exists}" />
      </form>
</body>
</html>
<#include "../../../yitianwms/yitianwms-include-bottom.ftl">