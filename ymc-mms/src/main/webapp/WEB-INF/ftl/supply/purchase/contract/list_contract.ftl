<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商城--商家后台</title>
<#include "../../../yitianwms/yitianwms-include.ftl">
<script type="text/javascript">	 
function downloadContract(fileName){
	window.location = '${BasePath}/supply/manage/suppliercontract/downContractFile.sc?fileName='+fileName;
}
function doQuery(){
	var queryForm=document.getElementById("queryForm");
	queryForm.action="../../../supply/manage/suppliercontract/queryContractList.sc";
	queryForm.submit();
}
//全选
$(document).ready(function(){
	$("#checkall").click( 
		function(){ 
			if(this.checked){ 
			$("input[name='contractCB']").each(function(){this.checked=true;}); 
			}else{ 
				$("input[name='contractCB']").each(function(){this.checked=false;}); 
			} 
		}
	);
});
</script>
</head>
<body>
<div class="container"> 
	<!--工具栏start-->
	<div class="toolbar">
		<div class="t-content">
			<div class="btn" onclick="openwindow('${BasePath}/supply/manage/suppliercontract/goAddContract.sc',600,400,'添加合同',submitFn)">
				<span class="btn_l">
				</span>
				<b class="ico_btn add"></b>
				<span class="btn_txt">
				添加合同
				</span>
				<span class="btn_r">
				</span>
			</div>
		</div>
	</div>
	<!--工具栏end-->
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr">
					<span>
					供应商合同管理
					</span>
				</li>
			</ul>
		</div>
		<!--当前位置end-->
		<div class="modify"> 
			<!--普通搜索内容开始-->
			<form name="queryForm" id="queryForm" method="POST">
				<div class="add_detail_box">
					<p>
					<span>
					<label>合同编号：</label>
					<input type="text" name="contractNo" value="" />
					</span>
					<span>
					<label>合同状态：</label>
					<select name="contractState">
						<option value="">全部</option>
						<option value="1">有效</option>
						<option value="0">过期</option>
					</select>
					</span>
					<span>
					<label>供应商类型：</label>
					<select name="supplierType">
						<option value="">全部</option>
						<option value="1">普通供应商</option>
						<option value="2">招商供应商</option>
					</select>
					</span>
					<span>
					<label>结算方式：</label>
					<select name="clearingForm">
						<option value="">全部</option>
						<option value="1">固定成本</option>
						<option value="2">代扣结算</option>
						<option value="3">配折结算</option>
					</select>
					</span>
					</p>
					<p>
					<span>
					<label>供应商编号：</label>
					<input  type="text" name="supplierCode" value="" />
					</span>
					<span>
					<label>供应商名称：</label>
					<input  type="text" name="supplierName" value="" />
					</span>
					</p>
				</div>
				<p class="searchbtn">
					<input type="button" onclick="doQuery();" value="查询" class="btn-add-normal" />
				</p>
			</form>
			<!--普通搜索内容结束--> 
			<!--列表start-->
			<table cellpadding="0" cellspacing="0" class="list_table">
				<thead>
					<tr>
						<th width="30">
							<input type="checkbox" value="" id="checkall" />
						</th>
						<th>合同编号</th>
						<th>状态</th>
						<th>供应商编号</th>
						<th>供应商名称</th>
						<th>供应商类型</th>
						<th>结算方式</th>
						<th>生效时间</th>
						<th>失效日期</th>
						<th>最后更新时间</th>
						<th>最后更新人</th>
						<th>功能操作</th>
					</tr>
				</thead>
				<tbody>
				<#if pageFinder?? && (pageFinder.data)??> 
				<#list pageFinder.data as item>
				<tr>
					<td>
						<input type="checkbox" name="contractCB" id = "cbox${item_index}" value="${(item.id)!""}" />
					</td>
					<td>${(item.contractNo)!""}</td>
					<td><#if (item.contractState)==0>
						<span class="star">
						过期
						</span>
						<#else>有效</#if></td>
					<td>${(item.supplierCode)!""}</td>
					<td>${(item.supplier)!""}</td>
					<td>${(item.supplierType)!""}</td>
					<td><#if item.clearingForm==1>固定成本<#elseif item.clearingForm==2>代扣结算<#elseif item.clearingForm==3>配折结算</#if></td>
					<td>${(item.effectiveDate)!""}</td>
					<td>${(item.failureDate)!""}</td>
					<td>${(item.updateTime)!""}</td>
					<td>${(item.updateUser)!""}</td>
					<td class="td0"> <a href="#" onclick="openwindow('${BasePath}/supply/manage/suppliercontract/goModContract.sc?id=${item.id}',600,400,'修改合同',updateFn)">修改</a> <#if item.attachment??><a href="#" onclick="downloadContract('${item.attachment}');">下载附件</a></#if> </td>
				</tr>
				</#list> 
				<#else>
				<tr>
					<td class="td-no" colspan="12">没有相关记录！</td>
				
				<tr> </#if>
					
						</tbody>
			</table>
			<!--列表end--> 
		</div>
		<!--分页start-->
		<div class="bottom clearfix"> <#import "../../../common.ftl" as page>
			<@page.queryForm formId="pageForm" />
		</div>
		<!--分页end--> 
	</div>
</div>
<form name="pageForm" id="pageForm"	action="../../../supply/manage/areawarehouseorder/queryotheroutstores.sc" method="POST">
	<input type="hidden" name="contractNo"	id="contractNo" value="" />
</form>
</body>
<script>
var submitFn=function()
{
	var iframe = this.iframe.contentWindow;
	iframe.$('#formContract').submit();
	return false;
}
var updateFn=function()
{
	var iframe = this.iframe.contentWindow;
	iframe.$('#formContract').submit();
	return false;
}
</script>
</html>
<#include "../../../yitianwms/yitianwms-include-bottom.ftl">