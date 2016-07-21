<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-报表-售后总览</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
<script type="text/javascript" src="${BasePath}/highcharts/js/highcharts.js"></script>
<script type="text/javascript" src="${BasePath}/highcharts/js/modules/exporting.js"></script>
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
								<th width="8%">订单号</th>
								<th width="8%">货号</th>
								<th width="8%">商品名称</th>
								<th width="8%">退回仓库</th>
								<th width="8%">下单时间</th>
								<th width="8%">订单金额</th>
								<th width="8%">申请单号</th>
								<th width="8%">售后申请时间</th>
								<th width="6%">处理状态</th>
								<th width="6%">售后类型</th>
								<th width="8%">退换货原因</th>
								<th width="8%">退回快递公司</th>
								<th width="8%">退回快递单号</th>
							</tr>
						</thead>
						<tbody>
							<#if pageFinder?? && pageFinder.data?size != 0>
								<#list pageFinder.data as detail>
									<tr>
										<td>${detail.orderNo}</td>
										<td>${detail.thirdPartyCode}</td>
										<td>${detail.commodityName}</td>
										<td>${detail.warehouseName!''}</td>
										<td>${detail.createTime?datetime}</td>
										<td>${detail.payTotalPrice?string('0.##')}</td>
										<td>${detail.applyNo}</td>
										<td>${detail.applyTime?datetime}</td>
										<td>${detail.status.description}</td>
										<td>${detail.saleType.description}</td>
										<td>${detail.saleReason}</td>
										<td>${detail.logisticsName!''}</td>
										<td>${detail.expressNo!''}</td>
									</tr>
								</#list>
							<#else>
								<tr>
									<td colspan="13">暂无数据</td>
								</tr>
							</#if>
						</tbody>
					</table>
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
