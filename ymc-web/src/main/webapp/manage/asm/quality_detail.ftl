<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-质检查询</title>
</head>
<body>
	<div class="main_container">
		<div class="normal_box">
		<p class="title site">当前位置：商家中心 &gt; 售后 &gt; 质检查询</p>
		<div class="tab_panel">
			<div class="tab_content">

				<fieldset class="x-fieldset x-fieldset-default">
						<legend class="x-fieldset-header">
						<a class="x-tool-toggle"></a>
						<span class="x-fieldset-header-text">订单货品明细</span>
						<div class="x-clear"></div>
						</legend>
						<div class="x-fieldset-body">
						   <div class="detail_box normal">
								<ul>
								    <li style="width:155px;">订单号：<#if orderDetail??&&orderDetail.orderSubNo??>${orderDetail.orderSubNo}</#if></li>
									<li style="width:110px;">收货人：<#if orderDetail??&&orderDetail.userName??>${orderDetail.userName}</#if></li>
									<li style="width:155px;">收货人手机：<#if orderDetail??&&orderDetail.consigneeMobile??>${orderDetail.consigneeMobile}</#if></li>
									<li style="width:215px;">订单发货时间：<#if orderDetail??&&orderDetail.shipTime??>${orderDetail.shipTime?string("yyyy-MM-dd HH:mm:ss")}</#if></li>
									<li style="width:280px;">发货快递单号：<#if orderDetail??&&orderDetail.expressOrderId??>${orderDetail.expressOrderId}</#if><#if orderDetail??&&orderDetail.logisticsName??>(${orderDetail.logisticsName})</#if></li>
								</ul>
							</div>
							<table id="recorded" class="goodsDetailTb">
								<thead>
									<tr>
									    <th width="280">商品名称</th>
										<th width="110">商家货品条码</th>
										<th width="100">商家款色编码</th>
										<th width="100">货品编码</th>
										<th width="80">货品数量</th>
										<th width="80">价格</th>
										
									</tr>
								</thead>
								<tbody>
							     <#if orderDetails??>
            						<#list orderDetails as item >
									<tr>
									    <td><div style="width:98%;float:left;word-break:break-all;text-align:left;"><img src="${item['picUrl']?default('')}" align ="left" width="40" height="40" /><a href="${item['static_url']?default('')}" target="_blank">${item['commodity_name']?default('')}</a></div></td>
										<td>${item['third_party_code']!""}</td>
										<td>${item['supplier_code']!""}</td>
										<td>${item['productNo']!""}</td>
										<td>${item['commodityNum']!""}</td>
										<td>${item['price']!""}</td>
									</tr>
								</#list>
								</#if>
								</tbody>
							</table>
						</div>
					</fieldset>
				
					<fieldset class="x-fieldset x-fieldset-default">
						<legend class="x-fieldset-header">
						<a class="x-tool-toggle"></a>
						<span class="x-fieldset-header-text">顾客回寄货品</span>
						<div class="x-clear"></div>
						</legend>
						<div class="x-fieldset-body">
							<table id="qadetails" class="goodsDetailTb">
								<thead>
									<tr class="bdr">
										<th>收货快递单号</th>
										<th>物流公司</th>
										<th>快递费用</th>
										<th>商家货品条码</th>
										<th>售后申请单号</th>
										<th>质检人</th>
										<th>质检描述</th>
										<th>质检结果</th>
										<th>售后申请时间</th>
									</tr>
								</thead>
								<tbody>
									<#if qadetails??>
            						<#list qadetails as item >
										<tr>
											<td>${item.expressCode!""}</td>
											<td>${item.expressName!""}</td>
											<td>${item.expressCharges!"0.00"}元 【<#if item.cashOnDelivery??&&item.cashOnDelivery=='NO'>非到付<#elseif item.cashOnDelivery??&&item.cashOnDelivery=='YES'>到付</#if>】</td>
											<td>${item.insideCode!""}</td>
											<td><#if item.qualityType??&&item.qualityType!='拒收'&&item.applyNo??>${item.applyNo!"--"}<#else>--</#if></td>
											<td>${item.qaPerson!""}</td>
											<td>${item.qaDescription!""}</td>
											<td><#if item.qualityType??&&item.qualityType='拒收'&&item.applyNo??>--<#else>${item.isPass!"--"}</#if></td>
											<td><#if item.qualityType??&&item.qualityType!='拒收'&&item.applyDate??>${item.applyDate?string("yyyy-MM-dd HH:mm:ss")}<#else>--</#if></td>
										</tr>
									</#list>
									</#if>
								</tbody>
							</table>
						</div>
					</fieldset>
					
					<#if saleAfterDetails??&&(saleAfterDetails?size gt 0)>
					<p class="blank20"></p>
					<fieldset class="x-fieldset x-fieldset-default">
						<legend class="x-fieldset-header">
						<a class="x-tool-toggle"></a>
						<span class="x-fieldset-header-text">顾客退换货说明</span>
						<div class="x-clear"></div>
						</legend>
						<div class="x-fieldset-body">
							<table id="received" class="goodsDetailTb">
								<thead>
									<tr>
										<th>货品编码</th>
										<th>数量</th>
										<th>售后类型</th>
										<th>顾客退换货说明</th>
									</tr>
								</thead>
								<tbody>
            						<#list saleAfterDetails as item >
										<tr>
											<td>${item['prodCode']!""}</td>
											<td>${item['commodityNum']!""}</td>
											<td>${item['saleType']!""}</td>
											<td>${item['remark']!""}</td>
										</tr>
									</#list>
								</tbody>
							</table>
						</div>
					</fieldset>
					</#if>
					<p class="blank20"></p>
			</div>
		</div>
		</div>
		
		
	</div>
</body>
<script>
$(document).ready(function(){
	//$('#recorded').find('input[name="sel"]:first').click();
	//$('#received').find('input[name="detailId"]:first').click();
});
</script>
</html>
