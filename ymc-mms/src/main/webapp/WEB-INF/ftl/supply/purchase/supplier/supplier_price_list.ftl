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
<#include "../../supply_include.ftl">
<#include "../../../yitiansystem/yitiansystem-include.ftl">
<script type="text/javascript" src="${BasePath}/js/supply/supplier.js" ></script>
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

function updateDetails(){
		var supplierId = $("#supplierId").attr("value");
  		var checkedids = $(":checkbox[name=ids]");
  		var ids = "";
  		var closeDates = "";  
  		var purchaseTypes = "";		
  		var purchaseQuantitys = "";
  		var purchasePrices = "";
  		var payPatio="";
  		var rounds = "";
  		var checkednum = 0;
  		for(var i=0; i<checkedids.length; i++) {
  			if(checkedids[i].checked) {
				var id = checkedids[i].value;
	  			ids += id + "~";
	  			closeDates += $("#"+id+"_closeDate").val() + "~";
	  			purchaseTypes += $("#"+id+"_purchaseType").val()+ "~";	  			
	  			purchaseQuantitys += $("#"+id+"_basepurchaseQuantity").val() + "~";
	  			purchasePrices += $("#"+id+"_actualPrice").val() + "~";
	  			if($("#"+id+"_payPatio").val()>1) {
	  				alert("扣点比例不能大于1.00");
	  				return false;
	  			}
	  			payPatio += $("#"+id+"_payPatio").val() + "~";
	  			rounds += $("#"+id+"_round").val() + "~";
	  			checkednum++;
  			}
  		}
  		if(checkednum>0) {
	  		$.ajax({
	           type: "POST",
	           url: "updateSupplierPrices.sc",
	           data: {"supplierId":supplierId,"ids":ids,"closeDates":closeDates,"purchaseTypes":purchaseTypes,"purchaseQuantitys":purchaseQuantitys,"purchasePrices":purchasePrices,"payPatio":payPatio,"rounds":rounds},           
	           success: function(data){           
	              if(data=="success"){
	 		 		alert("批量保存成功!"); 	 
	 		 		window.location.reload();         		 			 		
	 		 	  }else{
	 		 	  	alert("批量保存失败!"); 
	 		 	  	return false;	 
	 		 	  }             
	           }
	         }); 
         }else {
         	alert("请选择要保存的商品!"); 
         	return false;
         }
  	}
	function allSelects() {
		var checked = $(":checkbox[name=allselect]").attr("checked");
		$(":checkbox[name=ids]").attr("checked",checked);
	}
	
	$(document).ready(function(){
		$("#purchaseTypeS").attr("value",$("#typeT").attr("value"));
		var checkedids = $(":checkbox[name=ids]");
		for(var i=0; i<checkedids.length; i++) {
			var id = checkedids[i].value;				
			$("#"+id+"_purchaseType").attr("value",$("#"+id+"_purchaseTypeT").attr("value"));
			var purchaseTypes=$("#"+id+"_purchaseType").val();									
			if(purchaseTypes=='106') {
				$("#"+id+"_payPatio").show();
			}else {
				$("#"+id+"_payPatio").hide();
			}	
		}	
})

function selectPurchaseType(id) {
	var purchaseTypes=$("#"+id+"_purchaseType").val();	
	if(purchaseTypes=='106') {
		$("#"+id+"_payPatio").show();
	}else {
		$("#"+id+"_payPatio").hide();
	}
}
 	
</script>
</head>
<body>
<div class="main-body" id="main_body">	
			<div class="ytback-tt-1 ytback-tt">
            	<span>您当前的位置：</span>采购管理 &gt;供应商管理 &gt; 编辑
	</div>
			<div class="pro-list">
				<div class="mb-btn-fd-bd">
					<div class="mb-btn-fd relative">
						<span class="btn-extrange absolute ft-sz-14">
							<ul class="unselect">
								<li class="pl-btn-lfbg">
								</li>
								<li class="pl-btn-ctbg">
									<a href="toUpdate.sc?id=${supplier.id}" class="btn-onselc">基本信息</a>
								</li>
								<li class="pl-btn-rtbg">
								</li>
							</ul>
							<ul class="unselect">								
								<li class="pl-btn-lfbg"></li>
								<li class="pl-btn-ctbg"><a href="toUpdateSupplierFinance.sc?id=${supplier.id}">财务信息</a></li>
								<li class="pl-btn-rtbg"></li>								
							</ul>												
							<ul class="unselect">								
								<li class="pl-btn-lfbg"></li>
								<li class="pl-btn-ctbg"><a href="toSupplierContact.sc?supplier=${supplier.id}">联系人列表</a></li>
								<li class="pl-btn-rtbg"></li>								
							</ul>
							<ul class="onselect">
								<li class="pl-btn-lfbg"></li>
								<li class="pl-btn-ctbg">价格列表</li>
								<li class="pl-btn-rtbg"></li>
							</ul>																		
						</span>
					</div>
				<div class="add-newpd ft-sz-12 fl-rt"><a href="${BasePath}/supply/manage/supplier/toManage.sc" alt="返回供应商列表">返回</a></div>	
				<div class="add-newpd ft-sz-12 fl-rt"><a href="javascript:selectCommodityList('${BasePath}');" alt="添加商品">添加 商品</a></div>	
				</div>
			</div>
			<form action ="${BasePath}/supply/manage/supplier/querySupplierPrice.sc" id="supplierListForm" name="supplierListForm" method="post">
			<input type="hidden" id="supplierId" name="supplierId" value="${supplier.id?default("")}">			 
			<input type="hidden" id="typeT" name="typeT" value="<#if supplierPriceSearchVo??>${supplierPriceSearchVo.purchaseType?default("")}</#if>">
			<div class="div-pl">
				<div class="div-pl-hd ft-sz-12">										
					<div class="bid-sort">					
				        供应商名称：<Strong>${supplier.supplier?default("")}</Strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;                	
                                                  供应商编号：<Strong>${supplier.supplierCode?default("")}</Strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                                                  供应商类型：<Strong>${supplier.supplierType?default("")}</Strong>
                   <br/>    				
	              	</div>                                				                
				</div>						
				<div class="div-pl-hd ft-sz-12">					
					<table>
					<tr>
					<td>
					<span>采购类型：</span>
					</td>
					<td>
					<select id="purchaseTypeS" name="purchaseTypeS" style="width:152px">
	                	<option value=""  selected="selected">请选择</option> 
	                	<option value="102" <#if supplierPriceSearchVo??&&supplierPriceSearchVo.purchaseTypeS??&&supplierPriceSearchVo.purchaseTypeS=102>selected</#if>>自购</option> 
	                	<option value="107" <#if supplierPriceSearchVo??&&supplierPriceSearchVo.purchaseTypeS??&&supplierPriceSearchVo.purchaseTypeS=107>selected</#if>>比例代销</option> 
	                	<option value="106" <#if supplierPriceSearchVo??&&supplierPriceSearchVo.purchaseTypeS??&&supplierPriceSearchVo.purchaseTypeS=106>selected</#if>>协议代销</option> 
	                	<option value="108" <#if supplierPriceSearchVo??&&supplierPriceSearchVo.purchaseTypeS??&&supplierPriceSearchVo.purchaseTypeS=108>selected</#if>>配折结算</option> 
                	</select>
                	</td>                	
					<td>
					<span>商品编码：</span>
					</td>
					<td>
					<input name="commodityCode" id="commodityCode" class="blakn-supply" value="<#if supplierPriceSearchVo??>${supplierPriceSearchVo.commodityCode?default("")}</#if>"/>
					</td>
					<td>
					<span>商品名称：</span>
					</td>
					<td>
					<input name="commodityName" id="commodityName" class="blakn-supply" value="<#if supplierPriceSearchVo??>${supplierPriceSearchVo.commodityName?default("")}</#if>"/>
					</td>
					<td>
					</td>	
					<td>
					</td>						
					</tr>
					
					<tr>					
                	<td>					
					<span>分类编码：</span>
					</td>
					<td>
					<input name="catNo" id="catNo" class="blakn-supply" value="<#if supplierPriceSearchVo??>${supplierPriceSearchVo.catNo?default("")}</#if>"/>
					</td>
					<td>
					<span>分类名称：</span>
					</td>
					<td>
					<input name="catName" id="catName" class="blakn-supply" value="<#if supplierPriceSearchVo??>${supplierPriceSearchVo.catName?default("")}</#if>"/>
					</td>
					<td>
					<span>品牌编码：</span>
					</td>
					<td>
					<input name="brandNo" id="brandNo" class="blakn-supply" value="<#if supplierPriceSearchVo??>${supplierPriceSearchVo.brandNo?default("")}</#if>"/>
					</td>
					<td>
					</td>	
					<td>
					</td>
					</tr>
					
					<tr>					
                	<td>					
					<span>款色编码：</span>
					</td>
					<td>
					<input name="supplierCode" id="supplierCode" class="blakn-supply" value="<#if supplierPriceSearchVo??>${supplierPriceSearchVo.supplierCode?default("")}</#if>"/>
					</td>
					<td>
					<span>商品款号：</span>
					</td>
					<td>
					<input name="styleNo" id="styleNo" class="blakn-supply" value="<#if supplierPriceSearchVo??>${supplierPriceSearchVo.styleNo?default("")}</#if>"/>
					</td>
					<td>
					<span>品牌名称：</span>
					</td>
					<td>
					<input name="brandName" id="brandName" class="blakn-supply" value="<#if supplierPriceSearchVo??>${supplierPriceSearchVo.brandName?default("")}</#if>"/>
					</td>						
					<td>					
					</td>					
					<td>
					<input type="submit" value="搜索" name="search" id="search" onClick="" class="btn-sh"/>
					</td>
					</tr>
					</table>
				</div>			
				<div class="div-pl-table" id="div-table">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					  <tr class="div-pl-tt">
					  	<td class="pl-tt-td" width="4%">
					  	<input type="checkbox"  name="allselect" onclick="allSelects();"/></td>		  					  						
						<td class="pl-tt-td" width="15%">商品名称</td>	
						<td class="pl-tt-td" width="15%">商品编码</td>
						<td class="pl-tt-td" width="10%">供应商款色编码</td>	
						<td class="pl-tt-td" width="10%">商品款号</td>						
						<td class="pl-tt-td" width="10%">截止日期</td>
						<td class="pl-tt-td" width="6%">采购类型</td>					
						<td class="pl-tt-td" width="8%">起购数量</td>												
						<td class="pl-tt-td" width="8%">单价</td>
						<td class="pl-tt-td" width="5%">比例代销<br/>扣点比例</td>						
						<td class="pl-tt-td" width="5%">到货周期<br/>(工作日)</td>			
						<td class="pl-tt-td" width="5%">操作</td>
					  </tr>	
					  <#if pageFinder??>
					  <#if pageFinder.data??>				 
					  <#list pageFinder.data as item>
					  <tr class="div-pl-list"> 
					  	<td><input type="checkbox"  name="ids" id="${item.id}_ids" value="${item.id}"/></td>	  												
						<td>${item.commodityName?default("")}</td>				
						<td>${item.commodityCode?default("")}</td>
						<td>${item.supplierCode?default("")}</td>				
						<td>${item.styleNo?default("")}</td>							
						<td>
						<input type="text" id="${item.id}_closeDate" name="${item.id}_closeDate" class="Wdate" value="<#if item.closeDate??>${item.closeDate?string("yyyy-MM-dd")}</#if>" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'${item.id}_closeDate\')||\'2010-12-31\'}'})" size="18"/> 
						</td>											
						<td>
						<input type="hidden" id="${item.id}_purchaseTypeT" name="purchaseTypeT" value="${item.purchaseType?default("")}"/>
					  	<select id="${item.id}_purchaseType" name="purchaseType" value="" onchange="selectPurchaseType('${item.id}')">
		                	<option value="">请选择</option> 
		                	<option value="102">自购</option> 
		                	<option value="106">比例代销</option> 
		                	<option value="107">协议代销</option> 
                		</select>
                		</td>	
						<td>
						<input type="text" id="${item.id}_basepurchaseQuantity" style="width:50px" name="basepurchaseQuantity" value="<#if item.basepurchaseQuantity??>${item.basepurchaseQuantity?c?default("")}</#if>"/>  
						</td>																												
						<td>
						<input type="text" id="${item.id}_actualPrice" style="width:50px" name="actualPrice" value="${item.actualPrice?default("0.00")}" style="color:red"/> 
						</td>
						<td>
						<input type="text" id="${item.id}_payPatio" name="payPatio" style="width:50px" maxlength="5" value="${item.payPatio?default("0.000")}" style="color:red"/>
						</td>										
						<td>
						<input type="text" id="${item.id}_round" name="round" style="width:50px" maxlength="3" value="${item.round?default("")}" onblur=""/>
						</td>																							
						<td class="pl-edt">											
						<a href="javascript:deleteSupplier('${item.id}');">删除</a>
						
						</td>
					  </tr>
					  </#list>	
					  </#if>
		 			  </#if>					 
				  </table>
				</div>					
			</div>
		</form>	
		<div class="div-pl-bt">
	    	<input type="button" onclick="updateDetails()" value="批量保存" class="btn-sh"/>
	    </div>	
	    <div class="div-pl-bt">
			<!-- 翻页标签 -->
				<#import "../../../common.ftl"  as page>
				<@page.queryForm formId="supplierListForm" />				  
		</div>	
	</div>				
</body>
</html>

