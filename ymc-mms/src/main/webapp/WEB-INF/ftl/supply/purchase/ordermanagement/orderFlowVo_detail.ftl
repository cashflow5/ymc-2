<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/wms/css.css" />
<link rev="stylesheet" rel="stylesheet"  type="text/css" href="${BasePath}/css/wms/style.css"/>
<script type="text/javascript" src="${BasePath}/js/wms/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="${BasePath}/js/wms/js.js"></script>
<title></title>
<#include "../../supply_include.ftl">
<script type="text/javascript"
	src="${BasePath}/js/common/form/calendar.js"></script>
</head>
<body>
<div class="contentMain">
  <div class="ytback-tt-1 ytback-tt"><span>您当前的位置：</span> <a href="#">地区仓管理</a> &gt;
		待出库销售订单 &gt; 待出库销售订单明细</div>
  <div class="blank5"></div>
  <div class="wms">
  <div class="mb-btn-fd-bd">
      <div class="mb-btn-fd relative"> <span class="btn-extrange absolute ft-sz-14">
        <ul class="onselect">
          <li class="pl-btn-lfbg"></li>
          <li class="pl-btn-ctbg"><a class="btn-onselc">订单明细</a></li>
          <li class="pl-btn-rtbg"></li>
        </ul>
        </span> </div>
      <div class="yt-btn-add"> <a href="querywarehouseorder.sc" >返回</a></div>
    </div>
    <div class="wms-div">
      <ul class="wms-storage-ul-2" style="height:110px;">
        <#if orderSub??>
        <li>订单号：<#if orderSub.orderSubNo??>${orderSub.orderSubNo!""}
          </#if></li>
        <li>下单时间：<#if orderSub.createTime??>${orderSub.createTime!""}
          </#if></li>
        <li>付款方式：<#if orderSub.payStatus??><#if orderSub.payStatus ==
          1>在线支付<#elseif orderSub.payStatus == 2>货到付款<#elseif orderSub.payStatus
          == 3>银行转帐<#else>邮局汇款</#if></#if></li>
        <li>订单状态：
          <#if orderSub.baseStatus?? && checkStatusVos??>
          <#list checkStatusVos as checkStatusVos>
          <#if checkStatusVos.status==orderSub.baseStatus>${checkStatusVos.statusName?if_exists}</#if>
          </#list>
          </#if> </li>
        <!--隐藏保密数据
	        <li>订单总金额：#{orderSub.totalPrice?default(0);m2}</li>
	        <li>优惠总金额：#{orderSub.discountAmount?default(0);m2} </li>
	        <li>邮费：#{orderSub.orderConsigneeInfo.deliveryCosts?default(0);m2} </li>
	        <li>收货人：<#if
	          orderSub.orderConsigneeInfo.userName??>${orderSub.orderConsigneeInfo.userName!""}
	          </#if></li>
	        <li>联系电话：<#if
	          orderSub.orderConsigneeInfo.constactPhone??>${orderSub.orderConsigneeInfo.constactPhone!""}
	          </#if></li>
	        <li>联系手机：<#if
	          orderSub.orderConsigneeInfo.mobilePhone??>${orderSub.orderConsigneeInfo.mobilePhone!""}
	          </#if></li>
	        <li>邮政编码：<#if
	          orderSub.orderConsigneeInfo.zipCode??>${orderSub.orderConsigneeInfo.zipCode!""}
	          </#if></li>
	        <li >收货地址：<#if
	          orderSub.orderConsigneeInfo.consigneeAddress??>${orderSub.orderConsigneeInfo.consigneeAddress!""}
	          </#if></li>
          -->
          <li>订单总金额：***</li>
	        <li>优惠总金额：*** </li>
	        <li>邮费：*** </li>
	        <li>收货人：<#if orderSub.orderConsigneeInfo.userName??>${orderSub.orderConsigneeInfo.userName?substring(0,1)!""}</#if>**</li>
	        <li>联系电话：***</li>
	        <li>联系手机：***</li>
	        <li>邮政编码：***</li>
	        <li >收货地址：***</li>
        <#else>
        <li>没有相应记录</li>
        </#if>
      </ul>
      <div class="blank20"></div>
      <table cellpadding="0" cellspacing="0" class="ytweb-table">
        <thead>
          <tr>
            <th>货品编码</th>
            <th>商品名称</th>
            <th>规格</th>
            <th>优购价(元)</th>
            <th>数量</th>
<!--            <th>商品金额(元)</th>-->
            <th>优惠金额(元)</th>
            <th>总金额(元)</th>
          </tr>
        </thead>
        <tbody>
        <#if pageFinder?? && (pageFinder.data)??> <#list pageFinder.data as
        item >
        <tr>
          <td >${item.prodNo!""}</td>
          <td >${item.prodName!""}</td>
          <td >${item.commoditySpecificationStr!""}</td>
          <td ><!-- #{item.prodUnitPrice?default(0);m2} -->***</td>
          <td >${item.commodityNum!""}</td>
<!--          <td >#{item.prodTotalAmt?default(0);m2}</td>-->
          <td ><!-- #{item.prodDiscountAmount?default(0);m2} -->***</td>
          <td class="td0"><!-- #{(item.prodTotalAmt?default(0)-item.prodDiscountAmount?default(0));m2} -->***</td>
        </tr>
        </#list> <#else>
        <tr>
          <td colspan="8" class="td0">没有相应记录</td>
        </tr>
        </#if>
        </tbody>
        
      </table>
      <form name="pageForm" id="pageForm"
	action="${BasePath}/wms/stocksmanager/wmsorder/queryorderflowvodetail.sc"
	; method="POST">
        <input type="hidden" id="orderId" name="orderId"
	value="<#if orderId??>${orderId}</#if>" />
      </form>
      <div class="blank15"></div>
      <#import "../../../common.ftl" as page>
      <@page.queryForm formId="pageForm" />
      <div class="blank10"></div>
    </div>
  </div>
</div>
</body>
</html>