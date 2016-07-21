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
<#include "../../../yitiansystem/yitiansystem-include.ftl">
<script type="text/javascript" src="${BasePath}/js/supply/supplier.js" ></script>
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

function test(path){
	alert(path+"===13===");
}



</script>
<script language="javascript1.4">
function mytest(url){
	alert(url);
}
</script>
</head>
<body>
<form action ="${BasePath}/supply/manage/supplier/querySupplierPrice.sc" id="supplierListForm" name="supplierListForm" method="post">
<input type="hidden" id="supplierId" name="supplierId" value="${supplierId?default("")}">
<div class="main-body" id="main_body">	
<div class="ytback-tt-1 ytback-tt">
            	<span>您当前的位置：</span>采购管理 &gt; 供应商管理&gt; 价格列表
	</div>
			<div class="pro-list">
				<div class="mb-btn-fd-bd">
					<div class="mb-btn-fd relative">
						<span class="btn-extrange absolute ft-sz-14">
							<ul class="unselect">
								<li class="pl-btn-lfbg">
								</li>
								<li class="pl-btn-ctbg">
									<a href="toAdd.sc?supplierId=${supplierId?default("")}" class="btn-onselc">基本信息</a>
								</li>
								<li class="pl-btn-rtbg">
								</li>
							</ul> 	
							<ul class="unselect">								
								<li class="pl-btn-lfbg"></li>
								<li class="pl-btn-ctbg"><a href="toAddFinance.sc?supplierId=${supplierId?default("")}">财务信息</a></li>
								<li class="pl-btn-rtbg"></li>								
							</ul>				
							<ul class="unselect">								
								<li class="pl-btn-lfbg"></li>
								<li class="pl-btn-ctbg"><a href="toAddContact.sc?supplierId=${supplierId?default("")}">联系人列表</a></li>
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
			<div class="div-pl">
				<div class="div-pl-hd ft-sz-12">										
					<div class="bid-sort">					
				        供应商名称：<Strong>${supplier.supplier?default("")}</Strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;                	
                                                  供应商编号：<Strong>${supplier.supplierCode?default("")}</Strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                                                  供应商类型：<Strong>${supplier.supplierType?default("")}</Strong>    				
	              	</div>                                				                
				</div>					
				<div class="div-pl-hd ft-sz-12">
					<br/>
					<span>商品编码：</span>
					<input name="param" id="param" class="blakn-sl" />
					<input type="button" value="搜索" name="search" id="search" onClick="postForm('supplierListForm', '${BasePath}/supply/manage/supplier/querySupplierPrice.sc')" class="btn-sh"/>
				</div>			
				<div class="div-pl-table" id="div-table">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					  <tr class="div-pl-tt">
					  	<td class="pl-tt-td" width="8%">采购类型</td>					  						
						<td class="pl-tt-td" width="8%">商品名称</td>	
						<td class="pl-tt-td" width="8%">商品编码</td>					
						<td class="pl-tt-td" width="8%">起购数量</td>						
						<td class="pl-tt-td" width="8%">截止日期</td>
						<td class="pl-tt-td" width="8%">单价</td>						
						<td class="pl-tt-td" width="8%">到货周期</td>
						<td class="pl-tt-td" width="8%">建档人</td>
						<td class="pl-tt-td" width="8%">建档时间</td>
						<td class="pl-tt-td" width="12%">操作</td>
					  </tr>	
					  <#if pageFinder??>
					  <#if pageFinder.data??>				 
					  <#list pageFinder.data as item>
					  <tr class="div-pl-list"> 
					  	<td><#if item.purchaseType??>
							<#if item.purchaseType==102>自购
							<#elseif item.purchaseType==106>比例代销
							<#elseif item.purchaseType==107>协议代销	
							<#elseif item.purchaseType==108>配折结算					
							</#if>						
						</#if></td>												
						<td>${item.commodityName?default("")}</td>				
						<td>${item.commodityCode?default("")}</td>												
						<td>${item.basepurchaseQuantity?default("")}</td>
						<td>${item.closeDate?string("yyyy-MM-dd")}</td>																						
						<td>${item.actualPrice?default("")}</td>										
						<td>${item.round?default("")}</td>												
						<td>${item.creator?default("")}</td>						
						<td>${item.createTime?default("")}</td>												
						<td class="pl-edt">		
						<a href="${BasePath}/supply/manage/supplierprice/toUpdate.sc?id=${item.id}&supplierId=${supplier.id}" target="_blank">编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;						
						<a href="javascript:deleteSupplier('${item.id}');">删除</a>
						
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
					<#import "../../../common.ftl"  as page>
					<@page.queryForm formId="supplierListForm" />				  
		</div>			
</body>
</html>

