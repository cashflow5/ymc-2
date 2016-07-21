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
	<div class="toolbar">
		<div class="t-content">
			<div class="btn" onclick="javascript:batchSelect2()"> <span class="btn_l" ></span> <b class="ico_btn delivery"></b> 
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
				<li class='curr' ><span>选择商品</span></li>
			</ul>
		</div>
		<div class="modify" id="modify">
			<form id="queryForm" name="queryForm" action="selectProduct.sc" method="POST">
				<div class="add_detail_box">
					<p>
					<span>商品名称：</span>
					<input id="commodityName" name="commodityName" value="${commodity.commodityName?default('')}" class="blakn-sl" />
					<span>商品编号：</span>
					<input id="no" name="no" value="${commodity.no?default('')}" class="blakn-sl" />
					</p>
					<p>
					<span>商品款号：</span>
					<input id="styleNo" name="styleNo" value="${commodity.styleNo?default('')}" class="blakn-sl" />
					<span>供应商款色编码：</span>
					<input id="supplierCode" name="supplierCode" value="${commodity.supplierCode?default('')}" class="blakn-sl" />
					<input type="submit" value="搜索" name="search" id="search" onClick="" class="btn-add-normal"/>
					</p>
				</div>
			</form>
			<table cellpadding="0" cellspacing="0" class="list_table">
                <thead>
                <tr>
                	<th><input type="checkbox" name="allselect" id="allselect" onclick="allSelects()"/>全选</th>
                	<th>商品编号 </th>
                    <th>商品名称 </th>
                    <th>商品款号</th>
                    <th>供应商款色编码</th>
                    <th>规格 </th>
                    <th>货品条码</th>
                    <th>单价</th>
                </tr>              
                </thead>
                <tbody>
                
                 <#if pageFinder?? && (pageFinder.data)?? >
		      		<#list pageFinder.data as item >
		      		<tr>
		      			 <td >								
							<input type="checkbox"  value="${item.id?default("")}~${item.commodity.commodityName?default("")}}" name="productIds"/>
	                    </td>
	                    <td>${item.commodity.no?default("")}</td>
	                    <td>${item.commodity.commodityName?default("")}</td>
	                    <td>${item.commodity.styleNo?default("")}</td>
	                    <td>${item.commodity.supplierCode?default("")}</td>
	                    <td>${item.sizeName?default("")}</td>
	                    <td>${item.insideCode?default("")}</td>                   
	                    <td>${item.commodity.costPrice?default(0)?string('##0.00')}</td>
                    </tr>
		      		</#list>	
		      	<#else>
		      		<tr><td colspan="8"><div class="yt-tb-list-no">没有查询你想要的数据</div></td></tr>
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
	$(":checkbox[name=productIds]").attr("checked",checked);
}
function batchSelect2(){   	
   	var checkObj = $("input[name=productIds]"); 	 
   	var productIds = "";
   	var productNames = "";
   	for(var i=0; i<checkObj.length; i++) {
   		if(checkObj[i].checked) {
   			var proNoName = checkObj[i].value;
   	   		var proarray = proNoName.split("~");
   	   		productIds += proarray[0]+"~";
   	   		productNames += proarray[1]+";  ";
   		}
   	}
   	if(productIds==""){
   		alert("请选择货品!");
   		return false;
   	}else {
   		var pnos = dg.curWin.document.getElementById('productIds').value;
   		dg.curWin.document.getElementById('productIds').value = pnos + productIds;
   		var cNames = dg.curWin.document.getElementById('commodityName').value;
		dg.curWin.document.getElementById('commodityName').value = cNames + productNames;
		closewindow();
   	}
   }
</script>	