<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-报表-经营概况</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}" />
<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
</head>

<body>
	<div class="main_container">
		<div class="normal_box">
			<div class="tab_panel">
				<div class="tab_content">
					<!--列表start-->
					<table class="list_table">
						<thead>
							<tr>
								<th width="20%">时间段</th>
								<th width="20%">下单量</th>
								<th width="20%">下单客户数</th>
								<th width="20%">下单商品数</th>
								<th width="20%">下单金额</th>
							</tr>
						</thead>
						<tbody>
							<#if summaryOfOperationsVo?? && summaryOfOperationsVo.summaryOfOperationsVoDetails?? && summaryOfOperationsVo.summaryOfOperationsVoDetails?size != 0>
							<#list summaryOfOperationsVo.summaryOfOperationsVoDetails as detailVo>
							<tr>
								<td series="CATEGORIES">${detailVo.timeRange}</td>
								<td series="PURCHASE_QUANTITY">${detailVo.orderNums}</td>
								<td series="PURCHASE_CUSTOMER_NUM">${detailVo.orderCustomers}</td>
								<td series="PURCHASE_COMMODITY_NUM">${detailVo.orderCommodityNums}</td>
								<td series="PURCHASE_AMOUNT">${detailVo.orderPayTotalPrices?string('0.##')}</td>
							</tr>
							</#list>
							<tr>
								<td><b>总和</b></td>
								<td>${summaryOfOperationsVo.orderNums}</td>
								<td>${summaryOfOperationsVo.orderCustomers}</td>
								<td>${summaryOfOperationsVo.orderCommodityNums}</td>
								<td>${summaryOfOperationsVo.orderPayTotalPrices?string('0.##')}</td>
							</tr>
							<tr>
								<td><b>平均值</b></td>
								<td>${summaryOfOperationsVo.avgOrderNums}</td>
								<td>${summaryOfOperationsVo.avgOrderCustomers}</td>
								<td>${summaryOfOperationsVo.avgOrderCommodityNums}</td>
								<td>${summaryOfOperationsVo.avgOrderPayTotalPrices?string('0.##')}</td>
							</tr>
							<#else>
							<tr>
								<td colspan="5">暂无数据</td>
							</tr>
							</#if>
						</tbody>
					</table>
					<!--列表end-->
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">window.print();</script>
</html>
