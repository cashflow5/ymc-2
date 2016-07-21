<table class="list_table">
	<thead>
		<tr>
			<th  width="10%">保存时间</th>
			<th  width="25%">商品名称</th>
			<th>关键属性</th>
			<th  width="10%">操作</th>
		</tr>
	</thead>
	<tbody id="tbody">
		<#if pageFinder??&&pageFinder.data??&&(pageFinder.data?size>0)>
			<#list pageFinder.data as item>
                <tr class="proitm_hd">
                	<td>${item.operated!""}</td>
                    <td style="text-align:left;">${item.title!""}</td>
                    <td style="text-align:left;"><div style="height:37px;overflow:hidden" title="${item.propNames!""}">${item.propNames!""}</div></td>
                    <td><a href="javascript:useTemplate('${item.id!""}')">使用</a>&nbsp;&nbsp;<a href="javascript:delTemplate('${item.id!""}')">删除</a></td>
                 </tr>
			</#list>
		<#else>
			<tr class="do_tr">
				<td class="td-no" colspan="17">
						没有相关数据
				</td>
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
