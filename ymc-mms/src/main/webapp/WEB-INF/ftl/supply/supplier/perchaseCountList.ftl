<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#include "supplier_include.ftl">
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ytsys-comment.js"></script>
<script type="text/javascript" src="${BasePath}/js/supplys/supplier/perchaseList.js"></script>
<title>无标题文档</title>
</head><script type="text/javascript">
//导出采购单
function doExportPurchase(url) {
	document.purchaseCountForm.action='doExportPurchase.sc';
	document.purchaseCountForm.method="post";
	document.purchaseCountForm.submit();
	document.purchaseCountForm.action=url;
}

function showLog(purchaseCode){
	openwindow("${BasePath}/yitianwms/stocksmanager/spchangewarehouse/showLog.sc?purchaseCode="+ purchaseCode, 850, 300, '更换仓库日志');
}

</script>
<body>
<div class="container"> 
	<!--工具栏start-->
	<div class="toolbar">
		<div class="t-content"> <#if puVo??>
			<div class="btn" onclick="gotolink('${BasePath}/supply/supplier/PerchaseOrder/findDliveryPurchase.sc?purchaseCurrent=${puVo.currentPage?default(1)}');" > <span class="btn_l" ></span> <b class="ico_btn delivery"></b> <span class="btn_txt"> 入库查询 </span> <span class="btn_r"></span> </div>
			<#else>
			<div class="btn" onclick="gotolink('${BasePath}/supply/supplier/PerchaseOrder/findDliveryPurchase.sc?purchaseCurrent=1');" > <span class="btn_l" ></span> <b class="ico_btn delivery"></b> <span class="btn_txt"> 入库查询 </span> <span class="btn_r"></span> </div>
			</#if> </div>
	</div>
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr"><span>采购统计查询</span></li>
			</ul>
		</div>
		<!--当前位置end-->
		
		<div class="modify"> 
			<!--搜索start-->
			<div class="add_detail_box">
				<form action="findPurchaseCount.sc" name="purchaseCountForm" id="purchaseCountForm" method="post">
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
							<option <#if puVo??&&puVo.searchName??&&puVo.searchName=="source_code">selected</#if> value="source_code" >来源单号</option>
							<option <#if puVo??&&puVo.searchName??&&puVo.searchName=="pos_purchase_no">selected</#if> value="pos_purchase_no" >Pos采购单编码</option>
						</select>
						<input name="searchaValue" type="text" id="t4" value='${puVo.searchaValue?default("")}' style="width:120px;" />
						<label>商品款号：</label>
						<input type="text" value='${puVo.styleNo?default("")}'  name="styleNo" style="width:115px;" />
						<label>成本帐套：</label>
						 <select id="setOfBooksCode" name="setOfBooksCode">
                           <#if costSetofBooksList??>
                            	<option value="">请选择成本帐套名称</option>
                             	<#list costSetofBooksList as item>
                               		<option value="${(item.setOfBooksCode)!''}" <#if puVo??&&puVo.setOfBooksCode??&&puVo.setOfBooksCode==item.setOfBooksCode>selected</#if>>${(item.setOfBooksName)!''}</option>
                             	</#list>
                           </#if>
                           </select>
						<input type="submit" value="搜索" class="btn-add-normal" id="submitid" />
						<#--<input type="button" value="导出Excel" class="btn-add-normal-4ft" onclick="excelSubmit();" />-->
					</p>
				</form>
			</div>
			<table cellpadding="0" cellspacing="0" class="list_table">
				<thead>
					<tr>
						<th>操作</th>
						<th>采购类型</th>
						<th>单据编号</th>
						<th>成本帐套名称</th>
						<th>成本帐套编号</th>
						<th>POS来源</th>
						<th>POS采购单编号</th>
						<th>供应商</th>
						<th>单据状态</th>
						<th>采购人</th>
						<th>建档人</th>
						<th>预计到货日期</th>
						<th style="width:100px;">交货日期</th>
						<th>仓库</th>
						<th>合计数量</th>
						<th>合计金额</th>
						<th>更换仓库</th>
					</tr>
				</thead>
				<tbody id="tbd">
				<#if pageFinder??&&pageFinder.data??>
				<#list pageFinder.data as item>
				<tr>
					<td> 
					<a href="${BasePath}/supply/supplier/PerchaseOrder/findDetailById.sc?perchaseId=${item['id']?default('')}&purcharsePage=${currtentPage?default(1)}" >查看详情</a>
					<#--
					<a href="${BasePath}/supply/supplier/PerchaseOrder/findPurchaseDetial.sc?purchaseId=${item['id']?default('')}&amount=${item['amount']?default(0)}}&totalPrice=${item['total_price']?default(0)}&currentPage=${puVo.currentPage?default(1)}" >查看详情</a> 
					-->
					| <a target="_blank" href="${BasePath}/supply/manage/purchase/printPact.sc?id=${item['id']?default('')}" >打印合同</a> </td>
					<td >${item['type_name']?default('')}</td>
					<td>${item['purchase_code']?default('')}</td>
					<td>
					  <#if costSetofBooksList??>
                         <#list costSetofBooksList as cost>
                             <#if item['set_of_books_code']??&&cost.setOfBooksCode??&&cost.setOfBooksCode==item['set_of_books_code']>
					            ${cost.setOfBooksName?default('')}
					         </#if>
					     </#list>
					 </#if>
					</td>
					<td>${item['set_of_books_code']?default('')}</td>
					<td>${item['pos_source_name']?default('')}</td>
					<td>${item['pos_purchase_no']?default('')}</td>
					<td> ${item['supplier']?default('')}</td>
					<td> ${item['status_name']?default('')}</td>
					<td>${item['purchaser']?default('')}</td>
					<td>${item['create_people']?default('')}</td>
					<td >${item['plan_time']?default('')}</td>
					<td>${item['delivery_date']?default('')}</td>
					<td>${item['warehouse_name']?default('')}</td>
					<td style="text-align:center;">${item['amount']?default(0)}</td>
					<td  style="text-align:center;">${item['total_price']?default(0)}</td>
					<td><a href="#" onclick="showLog('${item['purchase_code']?default('')}');" >日志</a></td>
				</tr>
				</#list>
				<#else>
				<tr >
					<td class="td-no" colspan="14"> 抱歉，没有您要找的数据 </td>
				</tr>
				</#if>
				
						</tbody>
				
			</table>
		</div>
		<div class="bottom clearfix"> <#import "../../common.ftl"  as page>
			<@page.queryForm formId="purchaseCountForm" />
		</div>
	</div>
</div>
</body>
</html>
<script type="text/javascript">
$('#startPurchaseDate').calendar({maxDate:'#endPurchaseDate' }); 
$('#endPurchaseDate').calendar({minDate:'#startPurchaseDate' });
</script>