<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<meta http-equiv='content-type' content='application/ms-excel; charset=UTF-8'/>
<html xmlns="http://www.w3.org/1999/xhtml">
<#include "../../../yitianwms/yitianwms-include.ftl">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/wms/css.css" />
<link rev="stylesheet" rel="stylesheet"  type="text/css" href="${BasePath}/css/wms/style.css"/>
<script type="text/javascript" src="${BasePath}/js/wms/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="${BasePath}/js/wms/js.js"></script>
<!-- 商品分类四级联动 -->
<script src="${BasePath}/js/wms/axaj.js" type="text/javascript"></script>
<title>优购商城--商家后台</title>
<script type="text/javascript">
//加载商品四级联动
$(document).ready(function(){brandInit("${BasePath}","category1","${category1No!""}","category2","${category2No!""}","category3","${category3No!""}","brandNo","${brandNo!""}");});

	$(function(){
		var id=$("#hiddenid").val();
		$("#virtualWarehouseId option").each(function(){
			if($(this).val()==id){
				$(this).attr("selected","selected");
			}
		});
	});
	function doExport(){
		$("#exGname").val($("#goodsName").val());
		$("#exCcode").val($("#commodityCode").val());
		$("#category1Nos").val($("#category1").val());
		$("#category2Nos").val($("#category2").val());
		$("#category3Nos").val($("#category3").val());
		$("#brandNos").val($("#brandNo").val());
		
		$("#exportForm").submit();
		
	}
	function doImport(){
		javascript:art.dialog.openwindow('${BasePath}/supply/manage/areawarehouseorder/todoimportbyinventory.sc',380,80,'yes',{id:'open',title: '导入库存数据'});
	}
</script>
</head>

<body>
	<div class="contentMain">
	<div class="ytback-tt-1 ytback-tt">
	<!--	<span>您当前的位置：</span> -->
		<a href="#">供应商管理</a> &gt;
		地区仓库库存查询
	</div>
	<div class="blank5"></div>
	<div class="wms">

	<div class="mb-btn-fd-bd">
		<div class="mb-btn-fd relative">
			<span class="btn-extrange absolute ft-sz-14">
				<ul class="onselect">
					<li class="pl-btn-lfbg"></li>
					<li class="pl-btn-ctbg"><a class="btn-onselc">商品库存列表 </a></li>
					<li class="pl-btn-rtbg"></li>
				</ul>
			</span>
		</div>
		<form action="doExportByInventory.sc" method="post" name="exportForm" id="exportForm">
					     <div class="yt-btn-add-4ft"> <a href="${BasePath}/supply/manage/areawarehouseorder/exportTemplate.sc">导出模板</a></div>
		 	<div class="yt-btn-add-4ft"> <a href="javascript:void(0);doExport();">批量导出</a> </div>
		 	<div  class="yt-btn-add-4ft"><a href="#" onclick="doImport();">批量导入</a></div>
			<input type="hidden" name="goodsName" id="exGname" />
			<input type="hidden" name="commodityCode" id="exCcode" />
			<input type="hidden"  id="category1Nos" name="category1No" />
		   <input type="hidden"  id="category2Nos" name="category2No" />
		   <input type="hidden"  id="category3Nos" name="category3No" />
		   <input type="hidden"  id="brandNos"  name="brandNo" />
		</form>
	</div>
	<div class="wms-top">
		<form action="queryInventory.sc" name="queryF" id="queryF" method="post">
			<span>仓库名称：</span> 
				 <input type="text" value="<#if vir??>${vir.virtualWarehouseName}</#if>" disabled />
				商品名称：<input type="text" name="goodsName" id="goodsName" value="<#if goodsName??>${goodsName}</#if>" />
				货品编码：<input type="text" name="commodityCode" id="commodityCode" value="<#if ccode??>${ccode}</#if>"/>
				<input type="hidden" id="hiddenid" value="<#if vid??>${vid}</#if>" />
								 </br>
			<span>	商品品牌：</span>
				<select id="category1"  name="category1No" value = ""></select>&nbsp;&nbsp;
				<select id="category2"  name="category2No" value = "" ></select>&nbsp;&nbsp;
				<select id="category3" name="category3No" value = ""></select>&nbsp;&nbsp;
				<select id="brandNo" name="brandNo" value = "" ></select>&nbsp;&nbsp;
				<input type="submit" value="查询" class="wms-seach-btn" />
		</form>
	</div>
	<div class="wms-div">

		<table cellpadding="0" cellspacing="0" class="ytweb-table">
			<thead>
				<tr>
					<th>商品名称</th>
					<th>货品编码</th>
					<th>规格</th>
					<th>单位</th>
					<th>库存数量</th>
					<th>预占数量</th>
					<th>可售数量</th>
				</tr>
			</thead>
			<tbody>
			<#if isQuery=="true">
				<#if pageFinder?? > 
				<#if pageFinder.data?? && pageFinder.data?size != 0>
					<#list pageFinder.data as item >
						<tr>
							<td>${item.goodsName?default("")}</td>
							<td>${item.commodityCode?default("")}</td>
							<td>${item.specification?default("")}</td>
							<td>${item.units?default("")}</td>
							<td>${item.inventoryQuantity?default(0)}</td>
							<td><#if item.storedNumber??>${item.storedNumber?if_exists}<#else>0</#if></td>
							<#if item.storedNumber??>
							<td>${item.inventoryQuantity?default(0)-item.storedNumber?default(0)}</td>
							<#else>
								<td>${item.inventoryQuantity?default(0)}</td>
							</#if>
						</tr>
					</#list> 
					<#else> 
						<tr><td colspan="8" class="td0">没有相应记录</td></tr>
				</#if>
				</#if>
			</#if>
			</tbody>
		</table>
		<div class="blank15"></div>
		<form action="queryInventory.sc" name="pageForm" id="pageForm" method="POST">
					<input type="hidden" name="commodityCode" value="<#if ccode??>${ccode}</#if>"/>
					<input type="hidden" value="<#if category1No??>${category1No}</#if>" name="category1No" />
		             <input type="hidden" value="<#if category2No??>${category2No}</#if>" name="category2No" />
		            <input type="hidden" value="<#if category3No??>${category3No}</#if>" name="category3No" />
		           <input type="hidden" value="<#if brandNo??>${brandNo}</#if>" name="brandNo" />
			
				</form>
				<#if pageFinder?? && (pageFinder.data)??>
					<#import "../../../common.ftl" as page>
          			<@page.queryForm formId="pageForm" />
				</#if>
		<div class="blank10"></div>
	</div>


</div>
</div>
</div>
</body>
</html>
