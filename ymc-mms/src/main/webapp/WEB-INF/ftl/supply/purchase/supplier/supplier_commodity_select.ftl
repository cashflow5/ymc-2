<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,优购网,B网络营销系统,商品管理" />
<meta name="Description" content=" , ,B网络营销系统-商品管理" />
<#include "../../supply_include.ftl">
<#include "../../../yitiansystem/yitiansystem-include.ftl">
<title>B网络营销系统-采购管理-优购网</title>
<script type="text/javascript">		
     $(document).ready(function() {
		//全选或全不选 
		$("#chkAll").click(function () {
			//当点击全选框时
			var flag = $("#chkAll").attr("checked");//判断全选按钮的状态 
			$("[id$='Item']").each(function () {
				//查找每一个Id以Item结尾的checkbox 
				$(this).attr("checked", flag);
				//选中或者取消选中 
				});
			}); 
			//如果全部选中勾上全选框，全部选中状态时取消了其中一个则取消全选框的选中状态 
			$("[id$='Item']").each(function () {
				$(this).click(function () { 
					if ($("[id$='Item']:checked").length == $("[id$='Item']").length) { 
						$("#chkAll").attr("checked", "checked"); 
					} else $("#chkAll").removeAttr("checked");
			});
		});
	});
	function addSelected(){
		var checked_num = $("[id$='Item']:checked").length;
    	if(checked_num == 0){
        	art.dialog.alert('请至少选择一项商品！');
        	return false;
    	}
   		var supplierId = $("#supplierId").val();
    	if ( "" == supplierId) {
      		art.dialog.alert("供应商参数错误!不能添加商品");
      		return false;
      	}
      	var commodityIds="";
      	$('input[type=checkbox][name=commodityCB]:checked').each(function(){
			commodityIds+=$(this).val()+",";
		});
		var url = "${BasePath}/supply/manage/suppliercommodity/addSupplierCommodity.sc";
		$.ajax({
           type: "POST",
           url: url,
           data: {"commodityIds":commodityIds,"supplierId":supplierId},
           dataType : "json",          
           success : function(resultMsg) {
					if (resultMsg.success == "true") {
						if (confirm(resultMsg.msg)) {
							doQuery();
						} else {
							art.dialog.close();
							art.dialog.parent.doQuery();
						}
					} else {
						alert(resultMsg.msg);
					}
				}
         });
	}
	function addAll(){
      	var commodityIds = $("#allCommodityIds").val();
      	if ("" == commodityIds ) {
      		art.dialog.alert("当前无商品供添加!");
      		return false;
      	}
      	var supplierId = $("#supplierId").val();
    	if ( "" == supplierId) {
      		art.dialog.alert("供应商参数错误!不能添加商品");
      		return false;
      	}
		if(window.confirm('该操作将把列表中所有的商品加入，确认是否添加?')==false){
			return;
		}
		
      	var url = "${BasePath}/supply/manage/suppliercommodity/addSupplierCommodity.sc";
		$.ajax({
           type: "POST",
           url: url,
           data: {"commodityIds":commodityIds,"supplierId":supplierId},
           dataType : "json",          
           success : function(resultMsg) {
					if (resultMsg.success == "true") {
						if (confirm(resultMsg.msg)) {
							doQuery();
						} else {
							art.dialog.close();
							art.dialog.parent.doQuery();

						}
					} else {
						alert(resultMsg.msg);
					}
				}
         });
		
	}
	function doQuery() {
		var form = document.getElementById("submitForm");
	    form.submit();
	}
	function closeDialog() {
		art.dialog.parent.doQuery();
		art.dialog.close();
	}
</script>
</head><body>
<div class="container"> 
	<!--工具栏start-->
	<div class="toolbar">
		<div class="btn" onclick="addAll();" > <span class="btn_l" ></span> <b class="ico_btn add"></b> <span class="btn_txt">添加所有</span> <span class="btn_r"></span> </div>
		<div class="line"></div>
		<div class="btn" onclick="addSelected();" > <span class="btn_l" ></span> <b class="ico_btn add"></b> <span class="btn_txt">添加选中</span> <span class="btn_r"></span> </div>
		<div class="line"></div>
		<div class="btn" onclick="closewindow();"> <span class="btn_l" ></span> <b class="ico_btn back"></b> <span class="btn_txt">关闭</span> <span class="btn_r"></span> </div>
	</div>
</div>
<div class="list_content"> 
	<!--当前位置start-->
	<div class="top clearfix">
		<ul class="tab">
			<li class="curr"><span>可供添加商品列表</span></li>
		</ul>
	</div>
	<!--当前位置end-->
	
	<div class="modify"> 
		<!--搜索start-->
		<div class="add_detail_box">
			<form action ="queryCommodity.sc" id="submitForm" name="submitForm" method="post" encType="multipart/form-data">
				<input type="hidden" id="supplierId" name="supplierId" value="<#if currentSup??>${currentSup.id?default("")}</#if>"/> <input type="hidden" id="curSupplierId" name="curSupplierId" value="<#if currentSup??>${currentSup.id?default("")}</#if>"/> <input type="hidden" id="allCommodityIds" name="allCommodityIds" value="<#if allCommodityIds??>${allCommodityIds?default("")}</#if>"/>
				<p>
					<label> 商品编码： </label>
					<input type="text" name="productNo" id="productNo"  value="<#if searchVo??>${searchVo.productNo?default("")}</#if>" />
					<label> 商品名称： </label>
					<input type="text" name="productName" id="productName"  value="<#if searchVo??>${searchVo.productName?default("")}</#if>" />
					<label> 品牌： </label>
					<select name="brandNo" id="brand">
						<#if brandList??>
						<option value="">请选择品牌</option>
						<#list brandList as brand> <#if searchVo.brandNo?? && brand.brandNo == searchVo.brandNo>
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
					<input type="text" name="supplierCode" id="supplierCode"  value="<#if searchVo??>${searchVo.supplierCode?default("")}</#if>" />
					<label> 商品款号： </label>
					<input type="text" name="styleNo" id="styleNo"  value="<#if searchVo??>${searchVo.styleNo?default("")}</#if>" />
					<input type="submit" value="查询" class="btn-add-normal" />
				</p>
			</form>
		</div>
		<table width="100%" border="0" cellspacing="0" class="list_table" cellpadding="0">
			<thead>
				<tr >
					<th width="15">
						<input type="checkbox" value="" id="chkAll" />
					</th>
					<th width="40%">商品名称</th>
					<th width="15%">商品编码</th>
					<th  width="15%">款色编码</th>
					<th width="12%">品牌</th>
					<th width="15%">商品款号</th>
				</tr>
			</thead>
			<tbody>
			<#if pageFinder??>
			<#if pageFinder.data??>				 
			<#list pageFinder.data as commodity>
			<tr class="div-pl-list">
				<td>
					<input type="checkbox" name="commodityCB" id = "cbox${commodity_index}Item" value="${commodity.id}" />
				</td>
				<td style="text-align:left;padding-left:6px;"><#if commodity.commodityName??>${commodity.commodityName?default("&nbsp;")}<#else>&nbsp;</#if></td>
				<td><#if commodity.no??>${commodity.no?default("&nbsp;")}<#else>&nbsp;</#if></td>
				<td><#if commodity.supplierCode??>${commodity.supplierCode?default("&nbsp;")}<#else>&nbsp;</#if></td>
				<td><#if commodity.brandName??>${commodity.brandName?default("&nbsp;")}<#else>&nbsp;</#if></td>
				<td><#if commodity.styleNo??>${commodity.styleNo?default("&nbsp;")}<#else>&nbsp;</#if></td>
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
</body>
</html>
