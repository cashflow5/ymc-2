<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${BasePath}/css/ytsys-base.css">

<#include "../../yitiansystem/yitiansystem-include.ftl">
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ytsys-comment.js"></script>
<script type="text/javascript" src="${BasePath}/js/supply/supplier/perchasefufill.js"></script>
<title>无标题文档</title>
<script>
	var path="${BasePath}";
</script>
</head>
<body>

<div class="contentMain">
<form action="findDetailById.sc" name="perchaseDetialForm"" id="perchaseDetialForm" method="post">
        	<div class="ytback-tt-1 ytback-tt">
            	<span>您当前的位置：</span>采购管理 &gt; 采购日志
            </div>
            <div class="content">
                <div class="mb-btn-fd-bd">
                   <div class="mb-btn-fd relative">
                    <span class="btn-extrange absolute">
                        <ul class="onselect">
                            <li class="pl-btn-lfbg"></li>
                            <li class="pl-btn-ctbg"><a  class="btn-onselc">采购日志</a></li>
                            <li class="pl-btn-rtbg"></li>
                        </ul>
                    </span>
                  </div>
                 
                 <div class="ft-sz-12 fl-rt"><a  class="btn-sh" href="findFulfillOrder.sc?currtentPage=${puVo.purcharsePage?default(1)}">返&nbsp;回</a></div>
                </div>
            	<div class="yt-c-top"> 
            	 <div style="text-align:left;color:gray;font-weight:bold;">采购单信息:</div>
            	<div class="yt-c-div">
       
                 
                 <table width="100%;">
                 <#if purchase??>
                 	<tr >
                 		<td style="text-align:right;font-weight:bold;width:10%;">单据编号：</td>
                 		<td style="text-align:left;width:12%;">${purchase.purchaseCode?default("&nbsp;")}</td>
                 		
                 		<td style="width:1%;">&nbsp;</td>
                 		
                 		<td style="text-align:right;font-weight:bold;width:10%;">来源单号：</td>
                 		<td style="text-align:left;width:10%;">
                 			${purchase.sourceCode?default("&nbsp;")}
                 		</td>
                 		
                 		<td style="width:1%;">&nbsp;</td>
                 		
                 		<td style="text-align:right;font-weight:bold;width:10%;">单据状态：</td>
                 		<td style="text-align:left;width:10%;">
                 			 <#if purchase.status??&&purchase.status==1>已确认</#if>
                 		</td>
                 		
                 		<td style="width:1%;">&nbsp;</td>
                 		
                 		<td style="text-align:right;font-weight:bold;width:10%;">采购类型：</td>
                 		<td style="text-align:left;">
                 			 <#if purchase.type??>
		                   		 <#if purchase.type==102>自购
		                   		 <#elseif purchase.type==106>比例代销
		                   		 <#elseif purchase.type==107>协议代销
								 <#elseif purchase.type==108>配折结算</#if>
		                   	</#if>
                 		</td>
                 	</tr>
                   
                 	 <tr>
                 		<td style="text-align:right;font-weight:bold;">采购日期：</td>
                 		<td style="text-align:left;">
                 			<#if purchase.purchaseDate??>
					           ${purchase.purchaseDate?string("yyyy-MM-dd")}
					        </#if>
                 		</td>
                 		
                 		<td style="width:1%;">&nbsp;</td>
                 		
                 		<td style="text-align:right;font-weight:bold;">收货日期：</td>
                 		<td style="text-align:left;">  
					       <#if purchase.deliveryDate??>
					          ${purchase.deliveryDate?default("")}
					       </#if>
					    </td>
					    
					    <td style="width:1%;">&nbsp;</td>
					    
					    <td style="text-align:right;font-weight:bold;">合计数量：</td>
                 		<td style="text-align:left;">
                 			${purchase.amount?default("0")}
                 		</td>
                 		
                 		<td style="width:1%;">&nbsp;</td>
                 		
                 		<td style="text-align:right;font-weight:bold;">合计金额：</td>
                 		<td style="text-align:left;">
                 			${purchase.totalPrice?default("0.00")}
                 		</td>
					    
                 	</tr>
                 	 <tr>
                 		<td style="text-align:right;font-weight:bold;">审批人：</td>
                 		<td style="text-align:left;">
                 			${purchase.approver?default("&nbsp;")}
                 		</td>
                 		
                 		<td style="width:1%;">&nbsp;</td>
                 		
                 		<td style="text-align:right;font-weight:bold;">收货人：</td>
                 		<td style="text-align:left;">${purchase.receivePeople?default("&nbsp;")}</td>
                 		
                 		<td style="width:1%;">&nbsp;</td>
                 		
                 		<td style="text-align:right;font-weight:bold;">仓库：</td>
                 		<td style="text-align:left;">
                 			<#if (purchase.warehouse.warehouseName)??>
                 				${purchase.warehouse.warehouseName?default("")}
                 			</#if>
                 		</td>
                 		
                 		<td style="width:1%;">&nbsp;</td>
                 		
                 		<td style="text-align:right;font-weight:bold;">备注：</td>
                 		<td style="text-align:left;"> ${purchase.memo?default("&nbsp;")}</td>
                 	
                 	</tr>
                 	</#if>
                 </table>
                 
                 
             </div>
            <div style="text-align:left;color:gray;font-weight:bold;">日志信息:</div>
              <div class="yt-c-div">
              		<input type="hidden" value="${puVo.purchaseId}" id="purchaseId"/>
                    <table cellpadding="0" cellspacing="0" class="ytweb-table">
                    <thead>
                    <tr>                    
                    <th style="width:5%;font-weight:bold;font-weight:bold;">序号</th>
                    <th style="width:20%;font-weight:bold;font-weight:bold;">发货信息</th>
                    <th style="width:20%;font-weight:bold;font-weight:bold;">入库信息</th>
                    <th style="width:20%;font-weight:bold;font-weight:bold;">质检信息</th>
                    <th style="width:20%;font-weight:bold;font-weight:bold;">上货架信息</th>
                    
                    </tr>              
                    </thead>
                    <tbody id="tbd">
                    <#if pageFinder??&&pageFinder.data??>
                   		<#list pageFinder.data as pu>
	                   		<tr>
	                   		   <td>${pu_index+1}</td>							   
				               <td>
				               		${pu.consiqnment?default("&nbsp;")}
				               </td>
				               <td>
				               		${pu.storage?default("&nbsp;")}
				               </td>
				               <td>
				               		${pu.measure?default("&nbsp;")}"
				               		
				               	</td>
				               <td>
				               		${pu.upsupport?default("&nbsp;")}
				               </td>
				             </tr>
                   		</#list>
                   	<#else>
                   		<tr class="div-pl-list" id="dd">
							<td style="text-align:center;" colspan="6">
								<font style="color:gray;font-size:14px;font-weight:bold;">抱歉，没有您要找的数据,双击新增记录？</font>
							</td>
						</tr>
                    </#if>
                    </tbody>
                </table> 
                <input type="button" style="border:1px solid gray;width:52px;" value="保&nbsp;存" onclick="save()" />
         </div>
    </div>
    <!--分页采购单号,是否入库标识-->
    <input type="hidden" value="${puVo.purchaseId}" name="perchaseId">
  </form>
     <!--这里放置分页-->
      <div class="page">
		<div class="div-pl-bt">
			<#import "../../common.ftl"  as page>
			<@page.queryForm formId="perchaseDetialForm" />
		</div>   
	</div>
</div>
</body>
</html>
