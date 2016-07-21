<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#include "supplier_include.ftl">
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ytsys-comment.js"></script>
<script type="text/javascript" src="${BasePath}/js/supplys/supplier/perchasefufill.js"></script>
<script type="text/javascript" src="${BasePath}/js/supplys/supplier/notFullExcel.js"></script>
<script>
	var path="${BasePath}";
	function exportDate(purchaseId) {
  	if(purchaseId!="") {
  		window.location.href = "${BasePath}/supply/manage/purchaseDetail/exportDatas.sc?purchaseId="+purchaseId;
  	}
  }
</script>
<title>无标题文档</title>
</head><body>
<div class="container"> 
	<!--工具栏start-->
	<div class="toolbar">
		<div class="t-content"> </div>
	</div>
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr"><span>未完采购单列表</span></li>
			</ul>
		</div>
		<!--当前位置end-->
		
		<div class="modify"> 
			<!--搜索start-->
			<div class="add_detail_box">
				<form action="queryNotFullPurchase.sc" name="pecharseForm" id="pecharseForm" method="post">
					<p>
						<label>采购类型：</label>
						<select name="type" style="width:120px;">
							<option value="-1" selected>---请选择---</option>
							<option <#if puVo??&&puVo.type??&&puVo.type==102>selected</#if> value="102">自购固定价结算</option>
							<option <#if puVo??&&puVo.type??&&puVo.type==103>selected</#if> value="107">自购配折结算</option>
							<option <#if puVo??&&puVo.type??&&puVo.type==106>selected</#if> value="107">招商底价代销</option>
							<option <#if puVo??&&puVo.type??&&puVo.type==107>selected</#if> value="107">招商扣点代销 </option>
							<option <#if puVo??&&puVo.type??&&puVo.type==108>selected</#if> value="106">招商配折结算</option>
						</select>
						<label>供应商：</label>
						<select name="supplierId" style="width:115px;">
							<option value="" selected>请选择</option>
							<#if supList??> <#list supList as su> <#if puVo??&&puVo.supplierId??&& su.id==puVo.supplierId>
							<option selected value="${su.id}" >${su.supplier}</option>
							<#else>
							<option value="${su.id}" >${su.supplier}</option>
							</#if> </#list> </#if>
						</select>
						<label >仓库： </label>
						<select name="warehouseId" style="width:115px;">
							<option value="" selected>---请选择---</option>
							<#if warehoseList??> <#list warehoseList as wh> <#if puVo??&&puVo.warehouseId??&&wh.id==puVo.warehouseId>
							<option selected value="${wh.id}">${wh.warehouseName?default("匿名")}</option>
							<#else>
							<option value="${wh.id}">${wh.warehouseName?default("匿名")}</option>
							</#if> </#list> </#if>
						</select>
						<label >采购日期：</label>
						<input type="text" id="startPurchaseDate" name="startPurchaseDate" value="${puVo.startPurchaseDate!''}" style="width:75px;" />
						至
						<input type="text" id="endPurchaseDate" name="endPurchaseDate" value="${puVo.endPurchaseDate?default("")}" style="width:75px;" />
						</p>
					<p>
						<label>商品名称：</label>
						<input type="text" value='${puVo.commodityName?default("")}'  name="commodityName" style="width:115px;" />
						<label >品牌名称：</label>
						<input type="text" value='${puVo.brandName?default("")}'  name="brandName" style="width:112px;" />
						<label >分类名称：</label>
						<input type="text" value='${puVo.categoryName?default("")}'  name="categoryName" style="width:112px;" />
						<label >采购员：</label>
						<input name="purchaser" id="purchaser" type="text" id="t4" value='${puVo.purchaser?default("")}' style="width:120px;" />
					</p>
					<p>
						<label >自定义：</label>
						<select name="searchName" style="width:115px;">
							<option value="" selected>请选择</option>
							<option <#if puVo??&&puVo.searchName??&&puVo.searchName=="purchase_code">selected</#if> value="purchase_code" >单据编号
							</option>
							<option <#if puVo??&&puVo.searchName??&&puVo.searchName=="pos_purchase_no">selected</#if> value="pos_purchase_no" >POS采购单号
							</option>
						</select>
						<input name="searchaValue" type="text" id="t4" value='${puVo.searchaValue?default("")}' style="width:120px;" />
						<label>商品款号：</label>
						<input type="text" value='${puVo.styleNo?default("")}'  name="styleNo" style="width:115px;" />
						<input type="submit" value="搜索" class="btn-add-normal" id="submitid" />
						<input type="button" value="导出Excel" class="btn-add-normal-4ft" onclick="excelSubmit();" />
					</p>
				</form>
			</div>
			<table cellpadding="0" cellspacing="0" class="list_table">
				<thead>
					<tr>
						<th>操作</th>
						<th>采购类型</th>
						<th>单据编号</th>
						<th>POS采购单编号</th>
						<th style="width:80px">供应商</th>
						<th>审批人</th>
						<th>采购日期</th>
						<th>到货日期</th>
						<th>交货日期</th>
						<th>收货人</th>
						<th>合计数量</th>
						<th>合计金额</th>
						<th>仓库</th>
						<th>单据状态</th>
					</tr>
				</thead>
				<tbody id="tbd">
				<#if pageFinder??&&pageFinder.data??>
				<#list pageFinder.data as item>
				<tr>
					<td class="td0"> 
					<a href="${BasePath}/supply/supplier/PerchaseOrder/findDetailById.sc?perchaseId=${item['id']?default('')}&purcharsePage=${currtentPage?default(1)}" >查看详情</a>|<a href="#" onclick="exportDate('${item['id']?default('')}','0')" >导出数据</a> </td>
					<td >${item['type_name']?default('')}</td>
					<td >${item['purchase_code']?default('')}</td>
					<td >${item['pos_purchase_no']?default('')}</td>
					<td> ${item['supplier']?default('')} </td>
					<td >${item['approver']?default('')}</td>
					<td >${item['purchase_date']?default('')}</td>
					<td >${item['plan_time']?default('')}</td>
					<td >${item['delivery_date']?default('')}</td>
					<td>${item['receive_people']?default('')}</td>
					<td>${item['amount']?default('')}</td>
					<td >${item['total_price']?default("0.00")} </td>
					<td> ${item['warehouse_name']?default("&nbsp;")} </td>
					<td> ${item['status_name']?default("&nbsp;")}</td>
				</tr>
				</#list>
				<#else>
				<tr >
					<td class="td-no" colspan="14">
						<input type="hidden" id="excelHidden" />
						抱歉，没有您要找的数据 </td>
				</tr>
				</#if>
				</tbody>
			</table>
			<div class="bottom clearfix">
			<#if pageFinder?? && pageFinder.data?? >
			 <#import "../../common.ftl"  as page>
				<@page.queryForm formId="pecharseForm" />
				</#if>
			</div>
		</div>
		
	</div>
</div>
</body>
</html>
<script type="text/javascript">
$('#purchaseDateId').calendar({maxDate:'#deliveryDateId' }); 
$('#deliveryDateId').calendar({minDate:'#purchaseDateId' });
</script>