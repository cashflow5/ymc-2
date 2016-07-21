<#include "../merchants-include.ftl">
<table cellpadding="0" cellspacing="0" class="list_table" width="100%">
	<thead>
		<tr>
			<th></th>
			<th style="text-align: center;">品牌编码</th>
			<th style="text-align: center;">品牌名称</th>
		</tr>
	</thead>
	<tbody>
		<#if brands????&&(brands?size>0)>
		    <#list brands as item >
				<tr class="datatr">
					<td><input type="radio" name="brandNo" value=${item['brandNo']!''}></td>
					<td class="ft-cl-r" style="text-align: center;">${item['brandNo']!''}</td>
                	<td class="ft-cl-r" style="text-align: center;">${item['brandName']!''}</td>
                </tr>	
			</#list>
			<#else>
			 	<tr><td colSpan="10">抱歉，没有您要找的数据</td></tr>
			</#if>
	</tbody>
</table>
