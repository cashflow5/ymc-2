<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-报表-商品销售明细</title>
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
					<#if commoditySaleDetailsVo.salesQueryState?default('') == 'MORE_SALE'>
					<table class="list_table">
						<thead>
							<tr>
								<th width="6%">序号</th>
								<th width="8%">商品图片</th>
								<th width="24%">商品名称</th>
								<th width="12%">货号</th>
								<th width="10%">最近上架时间</th>
								<th width="10%">下单商品件数</th>
								<th width="10%">下单数量</th>
								<th width="10%">下单金额</th>
								<th width="10%">销售均价<img src="${BasePath}/yougou/images/bi_help.jpg" title="统计期内（先款订单付款后列入统计，先货订单提交后列入统计），某商品的平均销售单价。销售均价=下单金额/下单商品件数"/></th>
							</tr>
						</thead>
						<tbody>
							<#if pageFinder?? && pageFinder.data?size != 0>
								<#list pageFinder.data as detail>
									<tr class="series">
										<td>${detail_index + 1}</td>
										<td><img src="${detail.commodityImage!''}" alt="${detail.commodityName}" width="50%" height="100%" /></td>
										<td>${detail.commodityName}</td>
										<td>${detail.thirdPartyCode}</td>
										<td><#if detail.lastSalesTime??>${detail.lastSalesTime?datetime}</#if></td>
										<td>${detail.orderCommodityNums!'0'}</td>
										<td>${detail.orderNums!'0'}</td>
										<td>${detail.orderPayTotalPrices?string('0.##')}</td>
										<td>${detail.avgOrderAmounts?string('0.##')}</td>
									</tr>
								</#list>
							<#else>
								<tr>
									<td colspan="10" style="color: red;">暂无数据</td>
								</tr>
							</#if>
						</tbody>
					</table>
					<#elseif commoditySaleDetailsVo.salesQueryState?default('') == 'ZERO_SALE'>
					<table class="list_table">
						<thead>
							<tr>
								<th width="6%">序号</th>
								<th width="8%">商品图片</th>
								<th width="24%">商品名称</th>
								<th width="12%">货号</th>
								<th width="10%">最近上架时间</th>
							</tr>
						</thead>
						<tbody>
							<#if pageFinder?? && pageFinder.data?size != 0>
								<#list pageFinder.data as detail>
									<tr class="series">
										<td>${detail_index + 1}</td>
										<td><img src="${detail.commodityImage!''}" alt="${detail.commodityName}" width="50%" height="100%" /></td>
										<td>${detail.commodityName}</td>
										<td>${detail.thirdPartyCode}</td>
										<td><#if detail.lastSalesTime??>${detail.lastSalesTime?datetime}</#if></td>
									</tr>
								</#list>
							<#else>
								<tr>
									<td colspan="5" style="color: red;">暂无数据</td>
								</tr>
							</#if>
						</tbody>
					</table>
					</#if>
					<!--列表end-->
					<!--分页start-->
					<div class="page_box">
						<#if pageFinder ??>
							<#import "/manage/widget/common.ftl" as page>
							<@page.queryForm formId="queryForm"/>
						</#if>
					</div>
					<!--分页end-->
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">window.print();</script>
</html>
