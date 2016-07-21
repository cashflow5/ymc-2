<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${BasePath}/css/ytsys-base.css">

<#include "../../yitiansystem/yitiansystem-include.ftl">
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ytsys-comment.js"></script>
<script type="text/javascript" src="${BasePath}/js/supplys/supplier/perchaseDliveryDetailList.js"></script>
<title>无标题文档</title>
</head>
<body>
<div class="container">
	<div class="list_content">
		<div class="toolbar">
			<div class="btn" >
					<span class="btn_l" ></span>
					<b class="ico_btn delivery"></b>
					<span class="btn_txt">
					<a   href="findDliveryPurchase.sc?currentPage=${detailVo.currentPage?default(1)}">返&nbsp;回</a>
					</span>
					<span class="btn_r"></span>
			</div>
		</div>
		<div class="top clearfix">
			<ul class="tab">
				<li class='curr'>
				  <span>采购入库详情</span>
				</li>
			</ul>
		</div>
 <div class="modify">
               <form action="findDliveryPurchaseDetial.sc" name="purchaseDliveryForm" id="purchaseDliveryForm" method="post">
            		 <!--分页采购单号-->
					    <#if detailVo??&&detailVo.purchaseId??>
					    	<input type="hidden" value="${detailVo.purchaseId}" name="purchaseId">
					    <#else>
					    	<input type="hidden" value="" name="purchaseId">
					    </#if>
					    <#if detailVo??&&detailVo.amount??>
					    	<input type="hidden" value="${detailVo.amount}" name="amount">
					    <#else>
					    	<input type="hidden" value="" name="amount">
					    </#if>
					    <#if detailVo??&&detailVo.totalPrice??>
					    	<input type="hidden" value="${detailVo.totalPrice}" name="totalPrice">
					    <#else>
					    	<input type="hidden" value="" name="totalPrice">
					    </#if>
            	</form>
            	
            <div class="yt-c-div">
            <div style="text-align:left;color:gray;">采购单信息:</div>
               <table width="100%;">
                 <#if purcharse??>
                 	<tr >
                 		<td style="text-align:right;font-weight:bold;width:10%;">单据编号：</td>
                 		<td style="text-align:left;width:12%;">${purcharse.purchaseCode?default("&nbsp;")}</td>
                 		
                 		<td style="width:1%;">&nbsp;</td>
                 		
                 		<td style="text-align:right;font-weight:bold;width:10%;">来源单号：</td>
                 		<td style="text-align:left;width:10%;">
                 			${purcharse.sourceCode?default("&nbsp;")}
                 		</td>
                 		
                 		<td style="width:1%;">&nbsp;</td>
                 		
                 		<td style="text-align:right;font-weight:bold;width:10%;">单据状态：</td>
                 		<td style="text-align:left;width:10%;">
                 			 <#if purcharse.status??&&purcharse.status==1>
                 			 	已确认
                 			 <#elseif purcharse.status??&&purcharse.status==0>
                 			 	未确认
                 			 <#elseif purcharse.status??&&purcharse.status==-1>
                 			 	作废
                 			 </#if>
                 		</td>
                 		
                 		<td style="width:1%;">&nbsp;</td>
                 		
                 		<td style="text-align:right;font-weight:bold;width:10%;">供应商：</td>
                 		<td style="text-align:left;">
                 			<#if purcharse.supplier??&&(purcharse.supplier.supplier)??>
				                  ${purcharse.supplier.supplier}
				             </#if>
                 		</td>
                 	</tr>
                   
                 	 <tr>
                 		<td style="text-align:right;font-weight:bold;">采购日期：</td>
                 		<td style="text-align:left;">
                 			<#if purcharse.purchaseDate??>
					           ${purcharse.purchaseDate?string("yyyy-MM-dd")}
					        </#if>
                 		</td>
                 		
                 		<td style="width:1%;">&nbsp;</td>
                 		
                 		<td style="text-align:right;font-weight:bold;">建档日期：</td>
                 		<td style="text-align:left;">  
					       <#if purcharse.createDate??>
					         ${purcharse.createDate?string("yyyy-MM-dd")}
					       </#if>
					    </td>
					    
					    <td style="width:1%;">&nbsp;</td>
					    
					    <td style="text-align:right;font-weight:bold;">合计数量：</td>
                 		<td style="text-align:left;">
                 			${purcharse.amount?default("0")}
                 		</td>
                 		
                 		<td style="width:1%;">&nbsp;</td>
                 		
                 		<td style="text-align:right;font-weight:bold;">合计金额：</td>
                 		<td style="text-align:left;">
                 			${purcharse.totalPrice?default("0.00")}
                 		</td>
					    
                 	</tr>
                 	 <tr>
                 		<td style="text-align:right;font-weight:bold;">采购人：</td>
                 		<td style="text-align:left;">
                 			${purcharse.purchaser?default("&nbsp;")}
                 		</td>
                 		
                 		<td style="width:1%;">&nbsp;</td>
                 		
                 		<td style="text-align:right;font-weight:bold;">建档人：</td>
                 		<td style="text-align:left;">
                 			${purcharse.createPeople?default("&nbsp;")}
                 		</td>
                 		
                 		<td style="width:1%;">&nbsp;</td>
                 		
                 		<td style="text-align:right;font-weight:bold;">仓库：</td>
                 		<td style="text-align:left;">
                 			<#if (purcharse.warehouse.warehouseName)??>
				                    		${purcharse.warehouse.warehouseName}
				              <#else>
				              &nbsp;
				             </#if>
                 		</td>
                 		
                 		<td style="width:1%;">&nbsp;</td>
                 		
                 		<td style="text-align:right;font-weight:bold;">备注：</td>
                 		<td style="text-align:left;"> ${purcharse.memo?default("&nbsp;")}</td>
                 	
                 	</tr>
                 	</#if>
                 </table>
                </div>
            	<div></div>
            	<div style="text-align:left;color:gray;">采购入库统计详细信息:</div>
                <div class="yt-c-div">
                    <table cellpadding="0" cellspacing="0" class="list_table">
                    <thead>
                    <tr>
                   <!-- <th style="width:8%;overflow:hidden;font-weight:bold;">单据编号</th>-->
                    <th >商品编号</th>
                    <th >商品名称</th>
                    <th >分类名称</th>
                    <th >品牌名称</th>
                    <th >收货人</th>
                    <th >预计到货&nbsp;日期</th>
                    <th >交货日期</th>
                    <th >规格</th>
                    <th >单位</th>
                    <th >采购&nbsp;数量</th>
                    <th >入库&nbsp;数量</th>
                    <th >未入库&nbsp;数量</th>
                    <th >单价</th>
                    <th >折扣</th>
                    <th >总金额</th>
                    <th >备注</th>
                    </tr>              
                    </thead>
                    <tbody id="tbd">
                    <#if pageFinder??&&pageFinder.data??>
                   		<#list pageFinder.data as pu>
	                   			 <tr>
								    
								    <td>${pu.commodityCode?default("&nbsp;")}</td>
								    <td>${pu.commodityName?default("&nbsp;")}</td>
				                    <td>${pu.categoryName?default("&nbsp;")}</td>
				                    <td>${pu.brandName?default("&nbsp;")}</td>
				                    <td>${pu.purchase.receivePeople?default("&nbsp;")}</td>
				                    <td>
				                    	<#if pu.purchase??&&(pu.purchase.planTime)??>
				                    		${pu.purchase.planTime?date}
				                    	</#if>
				                    </td>
				                    <td>
				                    	<#if pu.purchase??&&(pu.purchase.deliveryDate)??>
				                    		${pu.purchase.deliveryDate?default("")}
				                    	</#if>
				                    </td>
				                    <td>${pu.specification?default("&nbsp;")}</td>
				                    <td>${pu.unit?default("&nbsp;")}</td>
				                    <td>${pu.purchaseQuantity?default("0")}</td>
				                    <td>${pu.intostoreQuantity?default("0")}</td>
				                    <td>
				                    	<#if pu.purchaseQuantity??&&pu.intostoreQuantity??>
				                    		${pu.purchaseQuantity-pu.intostoreQuantity}
				                    	<#elseif pu.purchaseQuantity??>
				                    		${pu.purchaseQuantity}
				                    	<#else>
				                    		-${pu.intostoreQuantity}}
				                    	</#if>
				                    </td>
				                    <td>${pu.purchasePrice?default("0.0")}</td>
				                    <td>${pu.deductionRate?default("1")}</td>
				                    <td>
				                    	${pu.purchaseTotalPrice?default("0.0")}
				                    </td>
				                     <td >
				                     	<div style="width:99%;height:30px;overflow:hidden;">
				                    		${pu.remark?default("&nbsp;")}
				                    	</div>
				                    </td>
				                  </tr>
                   		</#list>
                   		
                   		<tr><td colspan="16">&nbsp;</td><tr>
                   		<#--
                   		<tr>
							<th>总计:</th>
				            <th>总数量</th>
				            <td>
				            	<#if detailVo??&&detailVo.amount??>
				            		<input type="text" value="${detailVo.amount}" style="width:50px;border:1px solid #E6E6E6;color:red;" readOnly></td>
				            	<#else>
				            		<input type="text" value="0" style="width:50px;border:1px solid gray;color:red;" readOnly></td>
				            	</#if>
				            <th>总金额</th>
				            <td>
				            	<#if detailVo??&&detailVo.totalPrice??>
				            		<input type="text" value="${detailVo.totalPrice}" style="width:80px;border:1px solid #E6E6E6;color:red;text-align:right;" readOnly></td>
				            	<#else>
				            		<input type="text" value="0" style="width:80px;border:1px solid gray;color:red;text-align:right;" readOnly></td>
				            	</#if>	
				            </td>
				            <th>当前页总计：</th>
				            <th>总数量</th>
				            <td>
				            	<#if detailVo??&&detailVo.totalPageCount??>
				            		<input type="text" value="${detailVo.totalPageCount}" style="width:50px;border:1px solid #E6E6E6;color:red;" readOnly></td>
				            	<#else>
				            		<input type="text" value="0" style="width:50px;border:1px solid gray;color:red;" readOnly></td>
				            	</#if>	
				            </td>
				            <td>&nbsp;</td>
				            <td>&nbsp;</td>
				            <td>&nbsp;</td>
				            <td>&nbsp;</td>
				            
				            <th colspan="3">总金额：</th>
				            <td>
				              <#if detailVo??&&detailVo.totalPagePrice??>
				            		<input type="text" value="${detailVo.totalPagePrice}" style="width:80px;border:1px solid #E6E6E6;color:red;text-align:right;" readOnly></td>
				            	<#else>
				            		<input type="text" value="0" style="width:80px;border:1px solid #E6E6E6;color:red;text-align:right;" readOnly></td>
				            	</#if>
				             </td>
				            <td>&nbsp;</td>
                   		</tr>
                   		-->
                   		<tr>
                   			<th colspan="8" style="text-align:right;">当前页总计：</th>
                   			<th>数量</th>
                   			<td>
                   				<#if detailVo??&&detailVo.totalPageCount??>
				            		<input type="text" value="${detailVo.totalPageCount}" style="width:50px;border:1px solid #E6E6E6;color:red;" readOnly></td>
				            	<#else>
				            		<input type="text" value="0" style="width:50px;border:1px solid gray;color:red;" readOnly></td>
				            	</#if>
                   			</td>
                   			<th colspan="4" style="text-align:right;">金额</th>
                   			<td colspan="2" style="text-align:left;" >
                   				 <#if detailVo??&&detailVo.totalPagePrice??>
				            		<input type="text" value="${detailVo.totalPagePrice}" style="width:80px;border:1px solid #E6E6E6;color:red;text-align:right;" readOnly></td>
				            	<#else>
				            		<input type="text" value="0" style="width:80px;border:1px solid #E6E6E6;color:red;text-align:right;" readOnly></td>
				            	</#if>
                   			</td>
                   		</tr>
                   	<#else>
                   		<tr class="div-pl-list">
							<td style="text-align:center;" colspan="16">
								<font style="color:gray;font-size:14px;font-weight:bold;">抱歉，没有您要找的数据</font>
							</td>
						</tr>
                    </#if>
                    </tbody>
                </table> 
         </div>
    </div>
     <!--这里放置分页-->
      <div class="page">
		<div class="bottom clearfix">
			<#import "../../common.ftl"  as page>
			<@page.queryForm formId="purchaseDliveryForm" />
		</div>   
	</div>
</div>
</body>
</html>
