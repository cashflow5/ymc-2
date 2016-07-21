<#include "../merchants-include.ftl">
<table cellpadding="0" cellspacing="0" class="list_table" width="100%">
	<thead>
		<tr>
			<th></th>
			<th style="text-align: center;">品牌名称</th>
			<th style="text-align: center;">VID</th>
			<th style="text-align: center;">是否手动添加</th>
			<th style="text-align: center;">创建时间</th>
			<th style="text-align: center;">是否已经绑定</th>
			<th style="text-align: center;">操作人</th>
			<th style="text-align: center;">操作</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td colspan="10" class='selectAll'><label><input type="checkbox" id="selectAll">全选</label>&nbsp;&nbsp;<a href="javascript:deleteItem()">删除</td>
		</tr>
		<#if pageFinder??&&pageFinder.data?? >
		    <#list pageFinder.data as item >
				<tr>
					<td>
						<#if item['haveBind']=='0'&&item['isArtificialInput']=='1'>
                				<input type="checkbox" name="bindId" isArtificialInput=${item['isArtificialInput']!''} haveBind = ${item['haveBind']!''} value=${item['bid']!''}>
                			<#else>
                				<input type="checkbox" disabled = "disabled" name="bindId" isArtificialInput=${item['isArtificialInput']!''} haveBind = ${item['haveBind']!''} value=${item['bid']!''}>
                		</#if>
					</td>
                	<td class="ft-cl-r" style="text-align: center;">${item['name']!''}</td>
                	<td class="ft-cl-r" style="text-align: center;">${item['vid']?string('#')}</td>
                	<td class="ft-cl-r" style="text-align: center;">
                		<#if item['isArtificialInput']=='0'>
                			否
                		<#else>
                			是
                		</#if>
                	</td>
                	<td  class="ft-cl-r" style="text-align: center;">${item['operated']}</td>
                	<td class="ft-cl-r" style="text-align: center;">
                		<#if item['haveBind']=='1'>
                			是
                		<#else>
                			否
                		</#if>
                	</td>
                	<td class="ft-cl-r" style="text-align: center;">${item['operater']!''}</td>
                	<td class="ft-cl-r"  style="text-align: center;">
                		<#if item['haveBind']=='0'&&item['isArtificialInput']=='1'>
                				<a href="javascript:deleteItem('${item['bid']!''}')">刪除</a>&nbsp;&nbsp;
                			<#else>
                				--
                		</#if>
                	</td>
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
