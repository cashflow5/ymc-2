<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,优购网,B网络营销系统,商品管理" />
<meta name="Description" content=" , ,B网络营销系统-商品管理" />
<script type="text/javascript" src="../js/common.js"></script>
<script type="text/javascript" src="../js/jquery-1.3.2.min.js" ></script>
<script type="text/javascript" src="${BasePath}/js/supply/supplier.js" ></script>
<#include "../../supply_include.ftl">
<#include "../../../yitiansystem/yitiansystem-include.ftl">
<title>B网络营销系统-采购管理-优购网</title>
<script type="text/javascript">		
	function deleteSupplier(id){	
		if(window.confirm('确认删除？')==false){
			return;
		}		
	    var value=id;	     	
       	$.ajax({
           type: "POST",
           url: "deleteSupplierPrice.sc",
           data: {"id":value},           
           success: function(data){           
              if(data=="success"){
 		 		alert("删除成功!"); 
 		 		window.location.reload(); 	 		 			 		
 		 	  }              
           }
         }); 
      }
      function importPrice(url) {
      		var file = $("#uploadFile").val();
      		if(file!="") {
      			var form = document.getElementById("submitForm");
	      		form.action = url;
	      		form.submit();
      		}else {
      			alert("请选择要导入的xls文件!");
      		}
      }
</script>
</head><body>
<div class="container">
	<div class="toolbar">
		<div class="t-content"> </div>
	</div>
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class='curr' ><span>价格列表</span></li>
			</ul>
		</div>
		<div class="modify">
			<div class="add_detail_box">
				<form action ="querySupplierPrice.sc" id="submitForm" name="submitForm" method="post" encType="multipart/form-data">
					<input type="hidden" id="typeT" name="typeT" value="<#if supplierPriceSearchVo??>${supplierPriceSearchVo.purchaseTypeS?default("")} </#if>"/>
					<p>
						<label>采购类型：</label>
						<select id="purchaseTypeS" name="purchaseTypeS" style="width:135px" >
							<option value="" selected="selected">请选择</option>
							<option value="102" <#if supplierPriceSearchVo??&&supplierPriceSearchVo.purchaseTypeS??&&supplierPriceSearchVo.purchaseTypeS=102>selected</#if>>自购
				
							</option>
							<option value="106" <#if supplierPriceSearchVo??&&supplierPriceSearchVo.purchaseTypeS??&&supplierPriceSearchVo.purchaseTypeS=106>selected</#if>>比例代销
				
							</option>
							<option value="107" <#if supplierPriceSearchVo??&&supplierPriceSearchVo.purchaseTypeS??&&supplierPriceSearchVo.purchaseTypeS=107>selected</#if>>协议代销
				
							</option>
						</select>
						<label>供应商：</label>
						<input type="text" name="supplierName" id="supplierName"  value="<#if supplierPriceSearchVo??>${supplierPriceSearchVo.supplierName?default("")}</#if>" />
						<label>商品编码：</label>
						<input type="text" name="commodityCode" id="commodityCode"  value="<#if supplierPriceSearchVo??>${supplierPriceSearchVo.commodityCode?default("")}</#if>" />
						<label>商品名称：</label>
						<input type="text" name="commodityName" id="commodityName"  value="<#if supplierPriceSearchVo??>${supplierPriceSearchVo.commodityName?default("")}</#if>" /> </p>
					<p>
						<label>分类编码：</label>
						<input type="text" name="catNo" id="catNo"  value="<#if supplierPriceSearchVo??>${supplierPriceSearchVo.catNo?default("")}</#if>" />
						<label>分类名称：</label>
						<input type="text" name="catName" id="catName"  value="<#if supplierPriceSearchVo??>${supplierPriceSearchVo.catName?default("")}</#if>" />
						<label>品牌编码：</label>
						<input type="text" name="brandNo" id="brandNo"  value="<#if supplierPriceSearchVo??>${supplierPriceSearchVo.brandNo?default("")}</#if>" />
						<label>品牌名称：</label>
						<input  type="text" name="brandName" id="brandName"  value="<#if supplierPriceSearchVo??>${supplierPriceSearchVo.brandName?default("")}</#if>" /> </p>
					<p>
						<label>款色编码：</label>
						<input type="text" name="supplierCode" id="supplierCode"  value="<#if supplierPriceSearchVo??>${supplierPriceSearchVo.supplierCode?default("")}</#if>" />
						<label>商品款号：</label>
						<input type="text" name="styleNo" id="styleNo"  value="<#if supplierPriceSearchVo??>${supplierPriceSearchVo.styleNo?default("")}</#if>" />
						<label>截止日期：</label>
						<input type="text" name="closeDate" id="closeDate" 
					 value="<#if supplierPriceSearchVo??>${supplierPriceSearchVo.closeDate?default("")}</#if>" 
						class="Wdate"   
						onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})"" />
						<input type="submit" value="搜索" name="search" id="search" onClick=""  class="btn-add-normal" />
					</p>
					<p>
						<label>下载模板：</label>
						<a href="${BasePath}/template/import_price_template.xls" ><font color=blue>下载</font></a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 导入价格：
						<input name="uploadFile" type="file" id="uploadFile" />
						<input type="button" value="导入" onclick="importPrice('${BasePath}/supply/manage/supplierprice/c_importSupperPrices.sc');" class="btn-add-normal-4ft" />
					</p>
				</form>
			</div>
			<table width="100%" border="0" cellspacing="0" cellpadding="0"  class="list_table">
				<thead>
					<tr>
						<th>供应商</th>
						<th>商品名称</th>
						<th>商品编码</th>
						<th>款色编码</th>
						<th>商品款号</th>
						<th>截止日期</th>
						<th>采购类型</th>
						<th>起购数量</th>
						<th>单价</th>
						<th>比例代销/扣点比例</th>
						<th>到货周期(工作日)</th>
					</tr>
				<thead>
				<#if pageFinder??>
				<#if pageFinder.data??>				 
				<#list pageFinder.data as supplier>
				<tbody>
					<tr>
						<td><#if supplier.supplier??>${supplier.supplier.supplier?default("&nbsp;")}<#else>&nbsp;</#if></td>
						<td><#if supplier.commodityName??>${supplier.commodityName?default("&nbsp;")}<#else>&nbsp;</#if></td>
						<td><#if supplier.commodityCode??>${supplier.commodityCode?default("&nbsp;")}<#else>&nbsp;</#if></td>
						<td><#if supplier.supplierCode??>${supplier.supplierCode?default("&nbsp;")}<#else>&nbsp;</#if></td>
						<td><#if supplier.styleNo??>${supplier.styleNo?default("&nbsp;")}<#else>&nbsp;</#if></td>
						<td><#if supplier.closeDate??>${supplier.closeDate?string("yyyy-MM-dd")?default("&nbsp;")}<#else>&nbsp;</#if></td>
						<td> <#if supplier.purchaseType??>
							<#if supplier.purchaseType==102>自购
							<#elseif supplier.purchaseType==106>比例代销
							<#elseif supplier.purchaseType==107>协议代销	
							<#else>&nbsp;						
							</#if>						
							</#if> </td>
						<td><#if supplier.basepurchaseQuantity??>${supplier.basepurchaseQuantity?default("0")}<#else>0</#if></td>
						<td><font color="red"><#if supplier.actualPrice??><#setting number_format="0.##">${supplier.actualPrice?string("0.00")}<#else>0.00</#if></font></td>
						<td><font color="red"><#if supplier.payPatio??><#setting number_format="0.###">${supplier.payPatio?string("0.000")}<#else>0.000</#if></font></td>
						<td><#if supplier.round??>${supplier.round?default("0")}<#else>0</#if></td>
					</tr>
				</tbody>
				</#list>	
				</#if>
				</#if>
			</table>
		</div>
		<div class="bottom clearfix"> 
			<!-- 翻页标签 --> 
			<#import "../../../common.ftl"  as page>
			<@page.queryForm formId="submitForm" />
		</div>
	</div>
</div>
</body>
</html>
