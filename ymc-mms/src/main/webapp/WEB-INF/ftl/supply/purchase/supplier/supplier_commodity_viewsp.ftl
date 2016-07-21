<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,优购网,B网络营销系统,商品管理" />
<meta name="Description" content=" , ,B网络营销系统-商品管理" />
<#include "../../../yitiansystem/yitiansystem-include.ftl">
<title>B网络营销系统-采购管理-优购网</title>
<script type="text/javascript">	
	//提交按钮所在的表单
	function addSupToConfig(){
		var selectSpId = $("#supplierId").val();
		if("0" == selectSpId || "" == selectSpId){
	  		art.dialog.alert("请选择要添加的供应商");
	  		return false ;
	  	}
	  	if (confirm('该操作将把供应商加入列表，确认是否增加？')==true) {
	  		var url = '${BasePath}' + "/supply/manage/suppliercommodity/addSupplier.sc";
			$("#submitForm").attr("action",url);
			$("#submitForm").submit();
		}
	}
</script>
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
				<li class="curr"><span>供应商商品管理</span></li>
			</ul>
		</div>
		<!--当前位置end-->
		
		<div class="modify"> 
			<!--搜索start-->
			<div class="add_detail_box">
				<form action ="toSupplierCommodity.sc" id="submitForm" name="submitForm" method="post">
					<span> 供应商名称： </span>
					<select id="supplierId" name="supplierId" value="" style="width:173px">
						<option value="" selected="selected">请选择</option>
						<#if notConSp??> <#list notConSp as supplier>
						<option value="${supplier.id}">${supplier.supplier?default("&nbsp;")}</option>
						</#list> </#if>
					</select>
					<input type="button" value="添加" name="addSup" id="addSup" onClick="javascript:addSupToConfig();" class="btn-add-normal"/>
				</form>
			</div>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="list_table">
				<thead>
					<tr class="div-pl-tt">
						<th class="pl-tt-td" width="15%">名称</th>
						<th class="pl-tt-td" width="15%">编码</th>
						<th class="pl-tt-td" width="10%">操作</th>
					</tr>
				</thead>
				<tbody>
				<#if pageFinder??>
				<#if pageFinder.data??>				 
				<#list pageFinder.data as supplier>
				<tr class="div-pl-list">
					<td><#if supplier.supplier??>${supplier.supplier?default("&nbsp;")}<#else>&nbsp;</#if>&nbsp;</td>
					<td><#if supplier.supplierCode??>${supplier.supplierCode?default("&nbsp;")}<#else>&nbsp;</#if>&nbsp;</td>
					<td class="pl-edt"> <a href="toManage.sc?supplierId=${supplier.id}">编辑</a>&nbsp;&nbsp;&nbsp;&nbsp; </td>
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
