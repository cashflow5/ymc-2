<#include "../../yitiansystem/yitiansystem-include.ftl">
<div class="div-pl">
	<div class="div-pl-table" id="div-table">
		<table width="98%" align="center" border="0" cellspacing="0" cellpadding="0">
			<tr class="div-pl-tt">
				<td width="5%" class="pl-tt-td">
					<span class="pl-name">&nbsp;&nbsp;序号</span>
				</td>
				<td width="25%">
					日志内容
				</td>
				<td width="10%" class="pl-tt-td">
					操作人名称
				</td>
				<td width="8%" class="pl-tt-td">
					模块名
				</td>
				<td width="12%" class="pl-tt-td">
					类名
				</td>
				<td width="5%" class="pl-tt-td">
					日志类型
				</td>
				<td width="15%" class="pl-tt-td">
					日志时间
				</td>
				<td width="20%">
					remark
				</td>
			</tr>
		
			<#if pageFinder?? && (pageFinder.data)?? >
				<#assign i=0 />
				<#list pageFinder.result as item>		
				<#assign i=i+1 />
				<tr class="div-pl-list">
					<td class="pl-name-dt">
						${i}
					</td>
					<td class="prod-num">
						${item.message?default("")}
					</td>
					<td>
						${item.username?default("")}
						<input type="hidden" value="${item.userid?default("")}" name="userid"/>
					</td>
					<td>
						${item.codeModule?default("")}
					</td>
					<td>
						${item.classname?default("")}
					</td>
					<td>
						${item.logType?default("")}
					</td>
					<td>
						<#if item.logtime ??>
							${item.logtime?string("yyyy-MM-dd HH:mm:ss")}
						</#if>
					</td>
					<td>
						${item.remark?default("")}
					</td>
				</tr>
					
				</#list>
			</#if>
	
		</table>
	</div>

	<div class="div-pl-bt">
		<!-- 翻页标签 -->
		<#import "../../common.ftl"  as page>
		<@page.queryForm formId="queryForm" />
		<form action="querySystemLog.sc" name="queryForm" id="queryForm" method="POST"></form>
	</div>
</div>


