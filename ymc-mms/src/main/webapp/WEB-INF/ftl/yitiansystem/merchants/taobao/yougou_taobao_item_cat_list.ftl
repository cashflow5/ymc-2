<#include "../merchants-include.ftl">
<table cellpadding="0" cellspacing="0" class="list_table">
	<thead>
		<tr>
			<th></th>
			<th style="text-align: center;">优购分类</th>
			<th style="text-align: center;">淘宝分类</th>
			<th style="text-align: center;">创建时间</th>
			<th style="text-align: center;">操作人</th>
			<th style="text-align: center;">操作</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td colspan="10" class='selectAll'><label><input type="checkbox" id="selectAll">全选</label>&nbsp;&nbsp;<a href="javascript:deleteItem()">删除分类设置</td>
		</tr>
		<#if pageFinder??&&pageFinder.data?? >
		    <#list pageFinder.data as item >
				<tr>
					<td><input type="checkbox" name="catBindId" value=${item['id']!''}></td>
                	<td class="ft-cl-r" style="text-align: center;">${item['yougouCatFullName']!''}</td>
                	<td class="ft-cl-r" style="text-align: center;">${item['taobaoCatFullName']!''}</td>
                	<td  class="ft-cl-r" style="text-align: center;">${item['operated']}</td>
                	<td class="ft-cl-r" style="text-align: center;">${item['operater']!''}</td>
                	<td class="ft-cl-r"  style="text-align: center;"><a href="goYougouTaobaoItemCatProBind.sc?bindId=${item['id']!''}&catNo=${item['yougouCatNo']!''}">修改</a><a href="javascript:deleteItem('${item['id']!''}')">刪除</a></td>
                </tr>	
			</#list>
			<#else>
			 	<tr><td colSpan="10">抱歉，没有您要找的数据</td></tr>
			</#if>
	</tbody>
</table>
<div class="bottom clearfix">
	<#if pageFinder ??><#import "../../../common4ajax.ftl" as page>
	<@page.queryForm formId="queryForm"/></#if>
</div>
