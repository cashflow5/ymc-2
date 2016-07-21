<table class="list_table">
	<thead>
		<tr>
			<th width="25%">淘宝分类</th>
			<th width="25%">优购分类</th>
			<th width="15%">保存时间</th>
			<th>操作</th>
		</tr>
		<#if pageFinder??&&pageFinder.data??&&(pageFinder.data?size>0)>
			<tr class="do_tr">
				<td colspan="17" style="padding:0;text-align:left;">
					<div class="tb_dobox">
						<div class="page"> 
							<#if pageFinder ??>
								<#import "/manage/widget/page.ftl" as page>
								<@page.queryForm formId="queryVoform"/>
							</#if>
						</div>
					</div>
				</td>
			</tr>
		</#if>
	</thead>
	<tbody id="tbody">
		<#if pageFinder??&&pageFinder.data??&&(pageFinder.data?size>0)>
			<#list pageFinder.data as item>
				<!--全选操作部分-->
				<tr>
					<td>${item.taobaoCatFullName!''}</td>
					<td>${item.yougouCatFullName!''}</td>
					<td style="width:60px;">${item.operated!''}</td>
					<td>
						<a href="goYougouTaobaoItemProTemplet.sc?bindId=${item.id!''}&catNo=${item['yougouCatNo']!''}">查看属性</a>
					</td>
				</tr>
			</#list>
		<#else>
			<tr class="do_tr">  <!--do_tr 这行客户端会自动显示隐藏-->
				<td class="td-no" colspan="17">
						没有相关数据
				</td>
			</tr>
		</#if>	
	</tbody>
</table>
					<!--列表end--> 
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