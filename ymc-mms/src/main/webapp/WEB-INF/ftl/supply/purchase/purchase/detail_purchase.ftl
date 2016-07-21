<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,宜天网,B网络营销系统,商品管理" />
<meta name="Description" content=" , ,B网络营销系统-商品管理" />
<link rev="stylesheet" rel="stylesheet"  type="text/css" href="css/style.css"/>
<link rev="stylesheet" rel="stylesheet" type="text/css" href="css/css.css" />
<script type="text/javascript" src="../js/common.js"></script>
<script type="text/javascript" src="../js/jquery-1.3.2.min.js" ></script>
<script type="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
<script type="text/javascript" src="${BasePath}/js/supply/supplier.js" ></script>
<#include "../../supply_include.ftl">
<#include "../../../yitiansystem/yitiansystem-include.ftl">
<title>B网络营销系统-采购管理-宜天网</title>
</head>
<body>
<div class="container">
	<div class="list_content">
		<div class="toolbar">
			<div class="btn">
					<span class="btn_l" ></span>
					<b class="ico_btn delivery"></b>
					<span class="btn_txt">
					<a href="#" onClick="javascript:closewindow();">关闭</a>
					</span>
					<span class="btn_r"></span>
			</div>
			<div class="btn">
					<span class="btn_l" ></span>
					<b class="ico_btn delivery"></b>
					<span class="btn_txt">
					<#if purchase.status??&&purchase.status==0>
						<a href="#" onClick="checkStatus('${purchase.id?default("")}','1')">确认</a>
					</#if>
					</span>
					<span class="btn_r"></span>
			</div>
		</div>
		<div class="top clearfix">
			<ul class="tab">
				<li class='curr'>
				  <span>查看采购单</span>
				</li>
			</ul>
		</div>
 <div class="modify">
    <form action="detailPurchase.sc" method="post" id="detailPurchaseForm" name="detailPurchaseForm"> 	
    	<input type="hidden" name="id" value="${purchase.id?default('')}"/>
        <div class="div-pl-table">
             <table>
               <tr>
               <td align="right">	                                                  	           	
	                	<span align="right">物理仓库：</span>
	           		</td>
	           		 <td>
	                	${purchase.warehouse.warehouseName?default('')}
	                </td>
	               	<td align="right">    
	            	</td>
	                <td width="200" >
	                </td> 
	            	
               </tr>               
               <tr>
	               	<td align="right">	
	            		<span >采购类型：</span>
	                </td>
	                <td>
	                <#if purchase.type?? && purchase.type == 102>自购固定价结算</#if>
					<#if purchase.type?? && purchase.type == 103>自购配折结算</#if>
				    <#if purchase.type?? && purchase.type == 106>招商底价代销</#if>
					<#if purchase.type?? && purchase.type == 107>招商扣点代销 </#if>
					<#if purchase.type?? && purchase.type == 108>招商配折结算</#if>
		             </td>   	
	                <td align="right">
	               		<span >采购时间：</span>
	                </td>
	                <td>
	                	${purchase.purchaseDate?default('')}
           	 		</td>
           	  </tr>           		
              <tr>              
					<td align="right">
	                	<span >采购员：</span>
	                </td>
	                <td>
	                	${purchase.purchaser?default('')}
	            	</td>
	            	<td align="right">
	                <span >计划交货时间：</span>
	                </td>
	                <td>
	                ${purchase.planTime?default('')}
					</td>
                </tr>
            	<tr>       	
            		<td align="right">
            			<span >收货人：</span>
            		</td>
            		<td>
            			${purchase.receivePeople?default('')}
            		</td>
            		<td align="right">            	
	            	<span >收货人联系电话：</span> 
	            	</td>
	            	<td>
	            	${purchase.receivePhone?default('')}
	            	</td>
	            </tr>
	            <tr>       	
            		<td align="right">
            			<span >审核人：</span>
            		</td>
            		<td>
            			${purchase.approver?default('')}
            		</td>
            		<td align="right">            	
	            	<span >状态：</span> 
	            	</td>
	            	<td>
	            	<#if purchase.status??>
						<#if purchase.status==0>未确认
						<#elseif purchase.status==1>已确认
						<#else>已作废
						</#if>
						</#if>
	            	</td>
	            </tr>
				<tr>       	
            		<td align="right">
            			<span >收货地址：</span>
            		</td>
            		<td colspan="3">
            			 ${purchase.receiveAddress?default('')}
            		</td>
	            </tr>    
	            <tr>       	
            		<td align="right">            	
	            	<span >备注：</span> 
	            	</td>
	            	<td colspan="3">
	            	${purchase.memo?default('')}          
	            	</td>
	            </tr>            
	         </table> 
	        <div class="blank10"></div>
		</div>
		</div>
	</form>	 
      <div class="add-pro-div">   
        <div class="div-pl-hd ft-sz-12">
        		<table>
               	  <tr>
               	  	<td>
	                	<span >合计数量：</span>
	                </td>
	                <td>
	                	<B>${purchase.amount?default('')} 件</B>&nbsp;&nbsp;&nbsp;&nbsp;        
	            	</td>
     				 <td>
	                	<span >合计金额：</span>
	                </td>
	                <td>
	                	<B>￥${purchase.totalPrice?default('')}</B>           
	            	</td>
	              </tr>
	            </table>	            
	        </div>
	     </div>	            
      <div class="div-pl-table" id="div-table">
					<table width="100%" border="0" cellspacing="0" class="list_table" cellpadding="0">
					<thead>
					  <tr class="div-pl-tt">
					  	<th width="3%">货品条码</th>
					  	<th width="3%">款色编码</th>
						<th width="6%">商品名称</th>	
						<th width="3%">商品款号</th>
						<th width="2%">规格</th>												
						<th width="2%">单位 </th>
						<th width="3%">采购数量 </th>
						<th width="3%">采购单价</th>	
						<th width="2%">总价</th>											
						<th width="3%">扣点比例</th>
						<th width="3%">库存数量</th>
					  </tr>	
					  </thead>
					  <tbody>
					  <#if pageFinder??>
					  <#if pageFinder.data??>				 
					  <#list pageFinder.data as purchaseDetail>
					  	<td>${purchaseDetail.insideCode?default("&nbsp;")}</td>		
					  	<td>${purchaseDetail.commodity.supplierCode?default("&nbsp;")}</td>										
						<td>${purchaseDetail.commodityName?default("&nbsp;")}</td>
						<td>${purchaseDetail.commodity.styleNo?default("&nbsp;")}</td>
						<td>${purchaseDetail.specification?default("&nbsp;")}</td>				
						<td>
						${purchaseDetail.unit?default("&nbsp;")}
						</td>
						<td>
						${purchaseDetail.purchaseQuantity?default(0)?string('##')}
						</td>
						<td>
						${purchaseDetail.purchasePrice?default(0)?string('###0.00')}
						</td>
						<td>${purchaseDetail.purchaseTotalPrice?default(0)?string('###0.00')}</td>
						<td>
						<#if purchaseDetail??&&purchaseDetail.purchaseType??&&purchaseDetail.purchaseType=107>
						${purchaseDetail.deductionRate?default(0)?string('###0.00')}
						<#else>
						&nbsp;
						</#if>
						</td>	
						<td>${purchaseDetail.stockQuantity?default("")}</td>
					  </tr>
					  </#list>
					  <#else>
					  <tr class="div-pl-list">
					  	<td colspan="9"><B>对不起，没有商品信息，请添加</B></td>
					  </tr>	
					  </#if>
		 			  </#if>
		 			  </tbody>	
				  </table>
			</div>
			<div class="bottom clearfix">
				<!-- 翻页标签 -->
					<#import "../../../common.ftl"  as page>
					<@page.queryForm formId="detailPurchaseForm" />				  
			</div>	
	</div>	
</div>
</body>
</html>
<script type="text/javascript">
	function checkStatus(id,status) {
		if(window.confirm('确认此操作吗？')==false){
			return false;
		}else {
			$.ajax({
	           type: "POST",
	           url: "updatePurchaseStatus.sc",
	           data: {"id":id,"status":status},           
	           success: function(data){           
	              if(data=="success"){
	 		 		alert("审核成功!"); 	 
	 		 		art.dialog.close();
	 		 		art.dialog.parent.location.reload();         		 			 		
	 		 	  }else if(data=="fail") {
	 		 	  	alert("操作失败!"); 	 
	 		 	  	return false;
	 		 	  }else if(data=="nullDetail") {
	 		 	  	alert("采购单没有任何商品，禁止审核!"); 	 
	 		 	  	return false;
	 		 	  }        
	           }
	         }); 
         }
	}
</script>