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
