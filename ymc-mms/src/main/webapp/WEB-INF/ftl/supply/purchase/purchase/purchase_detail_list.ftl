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
<script type="text/javascript" src="${BasePath}/js/supply/supplier.js" ></script>
<#include "../../../yitiansystem/yitiansystem-include.ftl">
<title>B网络营销系统-采购管理-优购网</title>
<script type="text/javascript">	
	//提交按钮所在的表单
	function postForm(formId, url){
		$("#"+formId).attr("action",url);
		//添加hidden到form		
	  	var param = $('#param').val();	  	
	  	if("0" == param && ""== param){
	  		alert("条件为空,不能搜索!");
	  		return;
	  	}
		$("#"+formId).submit();
	}
	
	function deletePurchaseDetail(id){	
		if(window.confirm('确认删除？')==false){
			return;
		}		
	    var value=id;	     	
       	$.ajax({
           type: "POST",
           url: "deletePurchaseDetail.sc",
           data: {"purchaseDetailId":value},           
           success: function(data){           
              if(data=="success"){
 		 		alert("删除成功!"); 	 		 			 		
 		 	  }              
           }
         }); 
         window.location.reload();         
      } 



</script>
</head>
<body>
<form action ="queryPurchaseDetail.sc" id="submitForm" name="submitForm" method="post">
<input type="hidden" id="purchaseId" name="purchaseId" value="${purchase.id?default("")}">
<div class="main-body" id="main_body">	
			<div class="ytback-tt-1 ytback-tt">
            	<span>您当前的位置：</span>采购管理 &gt; 采购单&gt; 采购单明细
    		</div>
			<div class="pro-list">
				<div class="mb-btn-fd-bd">
					<div class="mb-btn-fd relative">
						<span class="btn-extrange absolute ft-sz-14">
							<ul class="onselect">
								<li class="pl-btn-lfbg"></li>
								<li class="pl-btn-ctbg">采购单明细</li>
								<li class="pl-btn-rtbg"></li>
							</ul>
						</span>
					</div>
				<div class="add-newpd ft-sz-12 fl-rt"><a href="${BasePath}/supply/manage/purchase/toManage.sc" alt="返回采购单列表">返回</a></div>	
				<div class="add-newpd ft-sz-12 fl-rt"><a href="javascript:selectProduct('${BasePath}')" alt="向采购单添加商品">添加商品</a></div>					
				</div>
			</div>			 
			<div class="div-pl">
				<div class="div-pl-hd ft-sz-12">										
					<div class="bid-sort">					
				        采购单编号：<Strong>${purchase.purchaseCode?default("")}</Strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;                	
                                                   采购类型 ：<Strong><#if purchase.type??>
							<#if purchase.type==102>自销
							<#elseif purchase.type==107>比例代销
							<#elseif purchase.type==106>协议代销	
							<#elseif purchase.type==108>配折结算						
							</#if>						
						</#if></Strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                                                  供应商：<Strong>${purchase.supplier.supplier?default("")}</Strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                  总金额： <Strong>${purchase.totalPrice?default("")}</Strong>  				
	              	</div>                                				                
				</div>				
				<div class="div-pl-hd ft-sz-12">
					<br/>
					<span>货品编号：</span>
					<input name="param" id="param" class="blakn-sl" />
					<input type="button" value="搜索" name="search" id="search" onClick="postForm('submitForm', '${BasePath}/supply/manage/purchase/queryPurchaseDetail.sc')" class="btn-sh"/>
				</div>			
				<div class="div-pl-table" id="div-table">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					  <tr class="div-pl-tt">
					  	<td class="pl-tt-td" width="8%">采购类型</td>
					  	<td class="pl-tt-td" width="16%">货品编号</td>					  						
						<td class="pl-tt-td" width="16%">货品名称</td>													
						<td class="pl-tt-td" width="8%">规格</td>
						<td class="pl-tt-td" width="8%">单位 </td>
						<td class="pl-tt-td" width="8%">采购数量 </td>
						<td class="pl-tt-td" width="8%">采购单价</td>												
						<td class="pl-tt-td" width="8%">扣点比例</td>
						<td class="pl-tt-td" width="8%">库存数量</td>
						<td class="pl-tt-td" width="10%">操作</td>
					  </tr>	
					  <#if pageFinder??>
					  <#if pageFinder.data??>				 
					  <#list pageFinder.data as purchaseDetail>
					  <tr class="div-pl-list">
					  	<td>
					  	<#if purchaseDetail.purchaseType??>
							<#if purchaseDetail.purchaseType==102>自销
							<#elseif purchaseDetail.purchaseType==106>比例代销
							<#elseif purchaseDetail.purchaseType==107>协议代销							
							</#if>						
						</#if>
					  	</td> 					  
					  	<td>${purchaseDetail.commodityCode?default("")}</td>												
						<td>${purchaseDetail.commodityName?default("")}</td>				
						<td>${purchaseDetail.specification?default("")}</td>												
						<td>${purchaseDetail.unit?default("")}</td>
						<td>${purchaseDetail.purchaseQuantity?default("")}</td>
						<td>${purchaseDetail.purchasePrice?default("")}</td>				
						<td>${purchaseDetail.deductionRate?default("")}</td>												
						<td>${purchaseDetail.stockQuantity?default("")}</td>																	
						<td class="pl-edt">		
						<a href="toUpdatePurchaseDetail.sc?purchaseId=${purchase.id}&purchaseDetailId=${purchaseDetail.id}">编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;								
						<a href="javascript:deletePurchaseDetail('${purchase.id}','${purchaseDetail.id}');">删除</a>
						
						</td>
					  </tr>
					  </#list>	
					  </#if>
		 			  </#if>					 
				  </table>
				</div>					
			</div>		
		</div>
		</form>	
		<div class="div-pl-bt">
				<!-- 翻页标签 -->
				<#if pageFinder?? && (pageFinder.data)?? >
					<#import "../../../common.ftl"  as page>
					<@page.queryForm formId="submitForm" />
					</#if>				  
		</div>		
</body>
</html> 