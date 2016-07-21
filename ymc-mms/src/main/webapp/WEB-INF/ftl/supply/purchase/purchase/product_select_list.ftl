<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#include "../../../yitiansystem/yitiansystem-include.ftl">
<script type="text/javascript" src="${BasePath}/js/supply/product_select.js"></script>
<script src="${BasePath}/js/supply/supplier.js" type="text/javascript"></script>
<title></title>
</head>
<body>
<input type="hidden" id="basePath" value="${BasePath}">
<div class="container">
	<!--工具栏start-->
	<div class="toolbar">
		<div class="t-content">
			<div class="btn" onclick="javascript:batchSelect()"> <span class="btn_l" ></span> <b class="ico_btn delivery"></b> 
			<span class="btn_txt">确认</span> 
			<span class="btn_r"></span> </div>
			<div class="line">
			</div>
			<div class="btn" onclick="javascript:closewindow();"> <span class="btn_l" ></span> <b class="ico_btn delivery"></b> 
			<span class="btn_txt">关闭</span> 
			<span class="btn_r"></span> </div>
		</div>
	</div>
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class='curr'>
				  <span>选择商品</span>
				</li>
			</ul>
		</div>
<div class="modify">	

		<form id="queryForm" name="queryForm" action="toSelectCommodity.sc" method="POST">
			<input type="hidden" name="supplierId" value="${supplierId?default('')}"/>
			<input type="hidden" name="type" value="${type?default('')}" />
			<div class="div-pl-hd ft-sz-12">
					<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商品编号：</span>
					<input id="no" name="no" value="${commodity.no?default('')}" class="blakn-sl" />
					<span>商品款号：</span>
					<input id="styleNo" name="styleNo" value="${commodity.styleNo?default('')}" class="blakn-sl" />
					<div class="blank10"></div>
					<span>供应商款色编码：</span>
					<input id="supplierCode" name="supplierCode" value="${commodity.supplierCode?default('')}" class="blakn-sl" />
					<input type="submit" value="搜索" name="search" id="search" onClick="" class="btn-sh"/>
				</div>       	
            </form>
            <div class="content">      
                <div class="yt-c-div">
                    <table cellpadding="0" cellspacing="0" class="ytweb-table">
                    <thead>
                    <tr>
                    	<th><input type="checkbox" name="allselect" id="allselect" onclick="allSelects()"/>全选</th>
                    	<th>商品编号 </th>
	                    <th>商品名称 </th>
	                    <th>商品款号</th>
	                    <th>供应商款色编码</th>
	                    <th>规格 </th>
	                    <th>货品编号</th>
	                    <th>起购数量</th>
	                    <th>单价</th>
                    </tr>              
                    </thead>
                    <tbody>
                    
                     <#if pageFinder?? && (pageFinder.data)?? >
			      		<#list pageFinder.data as item >
			      		<tr>
			      			 <td >								
								<input type="checkbox"  value="${item.productNo?default("")}~${item.commodityName?default("")}~${item.supplierPriceSpId?default('')}" name="productNos"/>
		                    </td>
		                    <td>${item.commodityCode?default("")}</td>
		                    <td>${item.commodityName?default("")}</td>
		                    <td>${item.styleNo?default("")}</td>
		                    <td>${item.supplierCode?default("")}</td>
		                    <td>${item.tblProSpec?default("")}</td>
		                    <td>${item.productNo?default("")}</td>                   
		                    <td>${item.basepurchaseQuantity?default("")}</td>	
		                    <td>${item.actualPrice?default(0)?string('##0.00')}</td>
	                    </tr>
			      		</#list>	
			      	<#else>
			      		<tr><td colspan="9"><div class="yt-tb-list-no">没有查询你想要的数据</div></td></tr>
					</#if>
                    </tbody>
                    </table> 
                    </div>
                    <div class="bottom clearfix">                    
                    <#if pageFinder?? && (pageFinder.data)?? >
                    	<#import "../../../common.ftl"  as page>
						<@page.queryForm formId="queryForm" />	   
					 </#if>	
					 </div>
			
            </div>
</div>

</body>
</html>
<script type="text/javascript">
function allSelects() {
	var checked = $(":checkbox[name=allselect]").attr("checked");
	$(":checkbox[name=productNos]").attr("checked",checked);
}
</script>	