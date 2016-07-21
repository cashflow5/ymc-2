<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include "../../../yitianwms/yitianwms-include.ftl">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- 商品分类四级联动 -->
<script src="${BasePath}/js/wms/axaj.js" type="text/javascript"></script>
<title>优购商城--商家后台</title>
<script type="text/javascript">
//加载商品四级联动
$(document).ready(function(){brandInit("${BasePath}","category1","${category1No?if_exists}","category2","${category2No?if_exists}","category3","${category3No?if_exists}");});
//全选
$(document).ready(function(){
$("#checkall").click(
function(){
if(this.checked){
$("input[name='commodityCB']").each(function(){this.checked=true;});
}else{
$("input[name='commodityCB']").each(function(){this.checked=false;});
}
});
});

	function doExport(){
		var vid=$("#vWarehouseId").val();
		if(vid==""){
			art.dialog.alert("请选择仓库");
			return;
		}
		$("#exVid").val(vid);
		$("#exGname").val($("#goodsName").val());
		$("#exCcode").val($("#commodityCode").val());
		$("#category1Nos").val($("#category1").val());
		$("#category2Nos").val($("#category2").val());
		$("#category3Nos").val($("#category3").val());
		$("#brandNos").val($("#brandNo").val());
		$("#exYears").val($("#years").val());
		$("#exSeason").val($("#season").val());
		$("#exQueryKey").val("${queryKey?if_exists}");

		$("#exportForm").submit();
	}

</script>
</head>

<body>
	<div class="container"> 
	<!--工具栏start-->
	<div class="toolbar">
		<div class="t-content">
			<div class="btn" onclick="doExport();"><span class="btn_l"></span><b class="ico_btn add"></b><span class="btn_txt">导出</span><span class="btn_r"></span> </div>
		<form action="doExportByInventory.sc" method="post" name="exportForm" id="exportForm">
			<input type="hidden" name="vWarehouseId" id="exVid" />
			<input type="hidden" name="goodsName" id="exGname" />
			<input type="hidden" name="commodityCode" id="exCcode" />
		    <input type="hidden"  id="category1Nos" name="category1No" />
		   <input type="hidden"  id="category2Nos" name="category2No" />
		   <input type="hidden"  id="category3Nos" name="category3No" />
		   <input type="hidden"  id="brandNos"  name="brandNo" />
		   <input type="hidden"  id="exYears"  name="years" />
		   <input type="hidden"  id="exSeason"  name="season" />
		   <input type="hidden"  id="exQueryKey"  name="queryKey" />
		</form>
		</div>
	</div>
	<!--工具栏end-->
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr"><span>商品库存列表 </span></li>
			</ul>
		</div>
		<!--当前位置end-->
		<div class="modify"> 
				<!--普通搜索内容开始-->
		<form action="queryInventory.sc" name="queryF" id="queryF" method="post">
				<span>仓库名称：</span>
				<input type="text" name="vWarehouseName" id="vWarehouseName" value="${queryVO.vWarehouseName?if_exists}" disabled="true"/>
				<input type="hidden" name="vWarehouseId" id="vWarehouseId" value="${queryVO.vWarehouseId?if_exists}" />
				商品名称：<input type="text" name="goodsName" id="goodsName" value="${queryVO.goodsName?if_exists}" />
				货品编码：<input type="text" name="commodityCode" id="commodityCode" value="${queryVO.commodityCode?if_exists}"/>
				<label for="t4">商品年份：</label>
	           		<select name="years" id="years">
	           			<option value="">请选择&nbsp;&nbsp;</option>
	           			<script>
	           				var date = new Date();
	 	       				var currentYear = date.getFullYear();
	 	       				var selectYear = "${queryVO.years?if_exists}";
	 	       				selectYear = (selectYear == null ) ? "" : selectYear;
	 	       				var yearCount = 30;
	 	       				for(var i = 0 ; i < yearCount ; i++){
	 	       					var value = currentYear -  1 + i;
	 	       					if(value == selectYear) document.write("<option value='"+value+"'  selected='selected'>"+value+"</option>");
	 	       					else document.write("<option value='"+value+"'>"+value+"</option>");
	 	       				}
	           			</script>
	           		</select>
	           		<label for="t4">季节：</label>
	           		<select name="season" id="season">
	           			<option value="">请选择</option>
						<#if snList??>
		           			<#list snList as it>
		           				<option value="${it.propValueNo?if_exists}" <#if queryVO.season?if_exists ==it.propValueNo>selected="selected"</#if>>
		           					${it.propValue?if_exists}
		           				</option>
		           			</#list>
	           			</#if>
	           		</select>
				 </br>
			<span>	商品品牌：</span>
				<select id="category1"  name="category1No" value = ""></select>&nbsp;&nbsp;
				<select id="category2"  name="category2No" value = "" ></select>&nbsp;&nbsp;
				<select id="category3" name="category3No" value = ""></select>&nbsp;&nbsp;
				<select id="brandNo" name="brandNo" value = "" >
					<option value="-1">请选择品牌</option>
					<#if brand??>
		          	<#list brand?keys as key>
		          		<#if key = queryVO.brandNo?if_exists>
		          		<option value="${key}" selected>${brand[key]}</option>
		          		<#else>
		          		<option value="${key}">${brand[key]}</option>
		          		</#if>
		          	</#list>
		          </#if>
				</select>&nbsp;&nbsp;
				<input type="hidden" id="queryKey" name="queryKey" value="query"/>
				<input type="submit" value="查询" class="wms-seach-btn" />
		</form>
				<!--普通搜索内容结束--> 	
			<!--列表start-->
			库存数量合计：<font color="red">${totalInventory?default("")}</font>
			<table cellpadding="0" cellspacing="0" class="list_table">
	         <thead>
				<tr>
					<th>仓库名称</th>
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
			<#if queryKey??>
				<#if pageFinder?? >
				<#if pageFinder.data?? && pageFinder.data?size != 0>
					<#list pageFinder.data as item >
						<tr>
							<td>${item.vWarehouseName?default("")}</td>
							<td>${item.goodsName?default("")}</td>
							<td>${item.commodityCode?default("")}</td>
							<td>${item.specification?default("")}</td>
							<td>${item.units?default("")}</td>
							<td>${item.inventoryQuantity?default(0)}</td>
							<td><#if item.storedNum??>${item.storedNum?if_exists}<#else>0</#if></td>
							<#if item.storedNum??>
							<td class="td0">${item.maySalesNum?default(0)}</td>
							<#else>
								<td class="td0">${item.inventoryQuantity?default(0)}</td>
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
	      	 <!--列表end--> 
        </div>
		<!--分页start-->
		<div class="bottom clearfix">
    	<#import "../../../common.ftl" as page>
      	<@page.queryForm formId="pageForm" />
		</div>
		<!--分页end--> 
	</div>
</div>
	<form action="queryInventory.sc" name="pageForm" id="pageForm" method="POST">
		<input type="hidden" value="${queryVO.vWarehouseId?if_exists}" name="vWarehouseId" />
        <input type="hidden" value="${queryVO.goodsName?if_exists}" name="goodsName" />
		<input type="hidden" value="${queryVO.commodityCode?if_exists}" name="commodityCode" />
		<input type="hidden" value="<#if category1No??>${category1No}</#if>" name="category1No" />
		<input type="hidden" value="<#if category2No??>${category2No}</#if>" name="category2No" />
		<input type="hidden" value="<#if category3No??>${category3No}</#if>" name="category3No" />
		<input type="hidden" value="${queryVO.brandNo?if_exists}" name="brandNo" />
		<input type="hidden" name="queryKey" value="${queryKey?if_exists}"/>
		<input type="hidden" name="years" value="${queryVO.years?if_exists}"/>
		<input type="hidden" name="season" value="${queryVO.season?if_exists}"/>
	</form>
</body>
</html>
<#include "../../../yitianwms/yitianwms-include-bottom.ftl">