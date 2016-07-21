<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,优购网,B网络营销系统,商品管理" />
<meta name="Description" content=" , ,B网络营销系统-商品管理" />
<link rev="stylesheet" rel="stylesheet"  type="text/css" href="css/style.css"/>
<link rev="stylesheet" rel="stylesheet" type="text/css" href="css/css.css" />
<script type="text/javascript" src="../js/common.js"></script>
<script type="text/javascript" src="../js/jquery-1.3.2.min.js" ></script>
<script type="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
<script type="text/javascript" src="${BasePath}/js/supply/supplier.js" ></script>
<#include "../../../yitiansystem/yitiansystem-include.ftl">
<!-- 日期控件 -->
<script src="${BasePath}/js/common/form/datepicker/WdatePicker.js" type="text/javascript"></script>
<title>B网络营销系统-采购管理-优购网</title>
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
				<li class="curr"><span>采购单审核</span></li>
			</ul>
		</div>
		<!--当前位置end-->
		
		<div class="modify"> 
			<!--搜索start-->
			<div class="add_detail_box">
				<form action ="checkPurchase.sc" id="submitForm" name="submitForm" method="post">
					<p> <label>单据编号：</label>
						<input  type="text" name="purchaseCode" id="purchaseCode" value="${searchPurchaseVo.purchaseCode?default('')}" />
						<label>采购员：</label>
						<input  type="text" name="purchaser" id="purchaser" value="${searchPurchaseVo.purchaser?default('')}"  />
						<label>供应商名称：</label>
						<input type="text" name="supplierName" id="supplierName" value="${searchPurchaseVo.supplierName?default('')}"   />
						
						<label>下单日期：</label>
						<input type="text" id="startCreateDate" name="startCreateDate" value="${searchPurchaseVo.startCreateDate?default('')}" class="Wdate inputtxt" style="width:75px;" />
						至
						<input type="text" id="endCreateDate" name="endCreateDate" value="${searchPurchaseVo.endCreateDate?default('')}"  class="Wdate inputtxt"  style="width:75px;"/>
						</p>
						<p>
						<label>商品款号：</label>
						<input type="text" id="styleNo" name="styleNo" value="${searchPurchaseVo.styleNo?default('')}"  />
					 	<label>仓库：</label>
						<select name="warehouseId"  >
							<option value="" selected>请选择</option>
							<#if warehouses??> <#list warehouses as wh> <#if searchPurchaseVo??&&searchPurchaseVo.warehouseId??&&wh.id==searchPurchaseVo.warehouseId>
							<option selected value="${wh.id}">${wh.warehouseName?default("匿名")}</option>
							<#else>
							<option value="${wh.id}">${wh.warehouseName?default("匿名")}</option>
							</#if> </#list> </#if>
						</select>
						<label>POS采购单编码：</label>
						<input type="text" id="posPurchaseNo" name="posPurchaseNo" value="${searchPurchaseVo.posPurchaseNo?default('')}"  />
						<input id="search" name="search" class="btn-add-normal"  value="搜索"  type="submit">
					</p>
				</form>
			</div>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="list_table">
				<thead>
					<tr >
						<th>操作</th>
						<th>单据编号</th>
						<th>POS来源</th>
						<th>POS采购单编号</th>
						<th>供应商名称</th>
						<th>采购员</th>
						<th>合计/数量</th>
						<th>总金额</th>
						<th>计划交货日期</th>
						<th>仓库</th>
						<th>状态</th>
					</tr>
				</thead>
				<tbody>
				<#if pageFinder??>
				<#if pageFinder.data??>				 
				<#list pageFinder.data as purchase>
				<tr >
					<td class="pl-edt"> <a href="#" onclick="viewDetail('${purchase['id']?default("")}')">查看详情</a>&nbsp;&nbsp;
						<#if purchase['status']?? && purchase['status']==0> <a href="#" onclick="checkStatus('${purchase['id']?default("")}','1')">确认</a>&nbsp;&nbsp;	
						</#if>
						<#if purchase['status']?? && purchase['status']==0> <a href="#" onclick="checkStatus('${purchase['id']?default("")}','-1')">作废</a> </#if>
						<a target="_blank" href="${BasePath}/supply/manage/purchase/printPact.sc?id=${purchase['id']?default("")}" >打印合同</a> 
						<a href="#" onclick="exportDate('${purchase.id?default("")}','0')" >导出数据</a> </td>
					<td>${purchase['purchase_code']?default("&nbsp;")}</td>
					<td>${purchase['pos_source_name']?default("&nbsp;")}</td>
					<td>${purchase['pos_purchase_no']?default("&nbsp;")}</td>
					<td>${purchase['supplier']?default("&nbsp;")}</td>
					<td>${purchase['purchaser']?default("&nbsp;")}</td>
					<td>${purchase['amount']?default("0")}</td>
					<td>${purchase['total_price']?default("&nbsp;")}</td>
					<td>${purchase['create_date']!"&nbsp;"}</td>
					<td>${purchase['warehouse_name']!"&nbsp;"} </td>
					<td> ${purchase['status_name']!"&nbsp;"} </td>
				</tr>
				</#list>	
				<#else>
				<tr >
					<td class="td-no" colspan="9">对不起，没有查询到你想要的数据</td>
				</tr>
				</#if>
				</#if>
				
						</tbody>
				
			</table>
		</div>
		<div class="bottom clearfix"> 
			<!-- 翻页标签 --> 
			<#if pageFinder??>
			<#import "../../../common.ftl"  as page>
			<@page.queryForm formId="submitForm" />
			</#if>
		</div>
	</div>
</div>
</body>
</html>
<script type="text/javascript">
$('#bTime').calendar({maxDate:'#eTime' }); 
$('#eTime').calendar({minDate:'#bTime' });
	function checkStatus(id,status) {
		if(window.confirm('确认此操作吗？')==false){
			return false;
		}else {
			$.ajax({
	           type: "POST",
	           url: "updatePurchaseStatus.sc",
	           data: {"id":id,"status":status},           
	           success: function(data){           
	              if(data=="success"){
	 		 		alert("操作成功!"); 	 
	 		 		window.location.reload();         		 			 		
	 		 	  }else if(data=="fail") {
	 		 	  	alert("操作失败!"); 	 
	 		 	  	return false;
	 		 	  }else if(data=="nullDetail") {
	 		 	  	alert("采购单没有任何商品，禁止审核!"); 	 
	 		 	  	return false;
	 		 	  }        
	           }
	         }); 
         }
	}
	function viewDetail(id) {
		openwindow('${BasePath}/supply/manage/purchase/detailPurchase.sc?id='+id,'','','查看详情');
	}
	function postForm() {
		var form = document.getElementById("submitForm") ;
		form.action = "${BasePath}/supply/manage/purchase/checkPurchase.sc";
		form.submit();
	}
	function exportDate(purchaseId) {
      	if(purchaseId!="") {
      		window.location.href = "${BasePath}/supply/manage/purchaseDetail/exportDatas.sc?purchaseId="+purchaseId;
      	}
      }
</script>