<table class="list_table">
<thead>
	<tr>
		<th width="5%"></th>
		<th width="40%">商品名称</th>
		<th width="10%">款色编码</th>
		<th width="8%">优购价</th>
		<th width="15%">品牌</th>
		<th width="7%">状态</th>
		<th width="20%">所属分类</th>
	</tr>
</thead>
<tbody>
	<#if pageFinder?? && (pageFinder.data)?? && pageFinder.data?size != 0> 
		<#list pageFinder.data as item>
		<tr>
			<td>
				<img alt="" src="${(item.defaultPic) !}" height="40" width="40">
			</td>
			<td style="text-align: left;">
				<a href="${(item.prodUrl) !}" target="_blank">
					${(item.commodityName) !}
				</a>
			</td>
			<td>${(item.styleNo) !}</td>
			<td>￥${(item.salePrice)?number}</td>
			<td>${(item.brandName) !}</td>
			<td>
				<#if (item.commodityStatus == 1) >
					下架
				<#elseif (item.commodityStatus == 2) >
					上架
				<#elseif (item.commodityStatus == 3) >
					停用
				<#elseif (item.commodityStatus == 4) >
					待进货
				<#elseif (item.commodityStatus == 5) >
					待售
				<#elseif (item.commodityStatus == 6) >
					预售
				<#elseif (item.commodityStatus == 11) >
					新建
				<#elseif (item.commodityStatus == 12) >
					提交审核
				<#elseif (item.commodityStatus == 13) >
					审核拒绝审核拒绝
				<#elseif (item.commodityStatus == 14) >
					基础资料维护
				<#elseif (item.commodityStatus == 15) >
					销售资料维护
				</#if>
			</td>
			<td>${(item.catStructname) !}</td>
		</tr>
		</#list>
	<#else>
		<tr>
			<td class="td-no" colspan="8">暂无数据!</td>
		</tr>
	</#if>
</tbody>
</table>
<#if pageFinder??&&pageFinder.data??&&(pageFinder.data?size>0)>
<!--分页start-->
<div class="page_box">
<div class="dobox">
</div>
<#if pageFinder ??>
	<#import "/manage/widget/common4ajax.ftl" as page>
	<@page.queryForm formId="queryVoform"/>
</#if>
</div>
<!--分页end-->
</#if>			
