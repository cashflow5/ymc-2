<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,优购网,B网络营销系统,商品管理" />
<meta name="Description" content=" , ,B网络营销系统-商品管理" />
<title>B网络营销系统-采购管理-优购网</title>
<#include "../../supply_include.ftl">
<#include "../../../yitiansystem/yitiansystem-include.ftl">
<script type="text/javascript">		
	function deleteSupplierCommodity(id){	
		if(window.confirm('确认删除？')==false){
			return;
		}		
	    var value = id;
	    if (null == value || "" == value) {
	    	art.dialog.alert("供应商商品参数错误，操作终止");
	    	return;
	    }
	    var url = "${BasePath}/supply/manage/suppliercommodity/deleteSupplierCommodity.sc"; 
       	$.ajax({
           type: "POST",
           url: url,
           data: {"id":value},           
           success: function(data){        
              if(data=="success"){
 		 		alert("删除成功!");
 		 		doQuery();		 			 		
 		 	  }              
           }
         }); 
      }
      function  openCommodityWindow() {
      	var supplierId = document.getElementById("supplier.id").value;
      	if ( "" == supplierId) {
      		art.dialog.alert("供应商参数错误!不能选择商品");
      		return false;
      	}
      	openwindow('${BasePath}/supply/manage/suppliercommodity/queryCommodity.sc?supplierId='+supplierId,'','','添加供应商商品');
      }
      function doQuery() {
      	var form = document.getElementById("submitForm");
	    form.submit();
      }
</script>
</head>
<body>
<div class="container"> 
	<!--工具栏start-->
	<div class="toolbar">
		<div class="t-content">
			<div class="btn" onclick="openCommodityWindow();"> <span class="btn_l" ></span> <b class="ico_btn add"></b> <span class="btn_txt">添加商品</span> <span class="btn_r"></span> </div>
			<div class="btn" onclick="gotolink('${BasePath}/supply/manage/suppliercommodity/toSupplierCommodity.sc');" > <span class="btn_l" ></span> <b class="ico_btn back"></b> <span class="btn_txt">返回</span> <span class="btn_r"></span> </div>
		</div>
	</div>
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr"><span>供应商商品列表</span></li>
			</ul>
		</div>
		<!--当前位置end-->
		
		<div class="modify"> 
			<!--搜索start-->
			<div class="add_detail_box">
				<form action ="querySupplierCommodity.sc" id="submitForm" name="submitForm" method="post" encType="multipart/form-data">
					<input type="hidden" id="supplier.id" name="supplier.id" value="<#if supplierCommonditySearchBean??>${supplierCommonditySearchBean.supplier.id?default("")} </#if>"/>
					<p>
						<label>供应商名称：</label>
						<#if currentSup??>${currentSup.supplier}</#if> <input type="hidden" id="curSupplierId" name="curSupplierId" value="<#if currentSup??>${currentSup.id?default("")} </#if>"/> </p>
					<p>
						<label> 商品编码： </label>
						<input type="text" name="commodityCode" id="commodityCode" class="blakn-supply" value="<#if supplierCommonditySearchBean??>${supplierCommonditySearchBean.commodityCode?default("")}</#if>" />
						<label> 商品名称： </label>
						<input type="text" name="commodityName" id="commodityName" class="blakn-supply" value="<#if supplierCommonditySearchBean??>${supplierCommonditySearchBean.commodityName?default("")}</#if>" />
						<label> 品牌： </label>
						<select name="brandNo" id="brand">
							<#if brandList??>
							<option value="">请选择品牌</option>
							<#list brandList as brand> <#if supplierCommonditySearchBean.brandNo?? && brand.brandNo == supplierCommonditySearchBean.brandNo>
							<option value="${brand.brandNo}" selected="selected">${brand.brandName}</option>
							<#else>
							<option value="${brand.brandNo}">${brand.brandName}</option>
							</#if> </#list> <#else>
							<option selected="selected" value="">请选择品牌</option>
							</#if>
						</select>
					</p>
					<p>
						<label> 款色编码： </label>
						<input type="text" name="supplierCode" id="supplierCode" class="blakn-supply" value="<#if supplierCommonditySearchBean??>${supplierCommonditySearchBean.supplierCode?default("")}</#if>" />
						<label> 商品款号： </label>
						<input type="text" name="styleNo" id="styleNo" class="blakn-supply" value="<#if supplierCommonditySearchBean??>${supplierCommonditySearchBean.styleNo?default("")}</#if>" />
						<input type="submit" value="搜索" class="btn-add-normal" />
					</p>
				</form>
			</div>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="list_table">
				<thead>
					<tr >
						<th  align="left" width="35%">商品名称</th>
						<th  width="15%">商品编码</th>
						<th  width="10%">款色编码</th>
						<th  width="10%">品牌</th>
						<th  width="10%">商品款号</th>
						<th  width="10%">操作</th>
					</tr>
				</thead>
				<tbody>
				<#if pageFinder??>
				<#if pageFinder.data??>				 
				<#list pageFinder.data as supCommodity>
				<tr >
					<td style="text-align:left;padding-left:6px;"><#if supCommodity.commodityName??>${supCommodity.commodityName?default("&nbsp;")}<#else>&nbsp;</#if></td>
					<td><#if supCommodity.commodityCode??>${supCommodity.commodityCode?default("&nbsp;")}<#else>&nbsp;</#if></td>
					<td><#if supCommodity.supplierCode??>${supCommodity.supplierCode?default("&nbsp;")}<#else>&nbsp;</#if></td>
					<td><#if supCommodity.brandName??>${supCommodity.brandName?default("&nbsp;")}<#else>&nbsp;</#if></td>
					<td><#if supCommodity.styleNo??>${supCommodity.styleNo?default("&nbsp;")}<#else>&nbsp;</#if></td>
					<td><a href="javascript:deleteSupplierCommodity('${supCommodity.id}')">删除</a></td>
				</tr>
				</#list>	
				</#if>
				</#if>
				
						</tbody>
				
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
