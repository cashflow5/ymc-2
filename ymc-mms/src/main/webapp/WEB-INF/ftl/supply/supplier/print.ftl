<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${BasePath}/css/ytsys-base.css">

<#include "../../yitiansystem/yitiansystem-include.ftl">
<script type="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ytsys-comment.js"></script>
<script type="text/javascript" src="${BasePath}/js/supplys/supplier/perchasefufill.js"></script>
<script type="text/javascript" src="${BasePath}/js/supplys/supplier/print.js"></script>
<title>无标题文档</title>
<script>
	var path="${BasePath}";
</script>
</head>
<body>

<div class="contentMain">
<form action="findDetailById.sc" name="perchaseDetialForm"" id="perchaseDetialForm" method="post">
        	<div class="ytback-tt-1 ytback-tt">
            	<span>您当前的位置：</span>采购管理 &gt; 打印采购单
            </div>
            <div class="content">
            
                <div class="mb-btn-fd-bd">
                   <div class="mb-btn-fd relative">
                    <span class="btn-extrange absolute">
                        <ul class="onselect">
                            <li class="pl-btn-lfbg"></li>
                            <li class="pl-btn-ctbg"><a  class="btn-onselc" >打印采购单</a></li>
                            <li class="pl-btn-rtbg"></li>
                        </ul>
                    </span>
                  </div>
                 <div class="ft-sz-12 fl-rt">
                 	<!--findFulfillOrder.sc?currtentPage=${purcharsePage?default(1)}-->
                 	<a  class="btn-sh" href="#" onclick="printPage('printConent')">打&nbsp;印</a>
                 	
                 	<a  class="btn-sh" href="findDetailById.sc?perchaseId=${perchaseId?default(0)}&currentPage=${currentPage?default(1)}">返&nbsp;回</a>
                 </div>
                </div> 
                
                <!--打印层start-->
                <div style="border:0px solid red;width:100%;" id="printConent">
                <!--标题层-->
						<div style="border:1px solid gray;width:75%;text-align:left;">
								<div style="text-align:center;"><h1>未完采购单</h1></div>
								<div style="text-align:right;font-size:12px;">打印日期：<#if dt??>${dt?date}</#if></div>
								<br/>
								<div>
									<span style="border:0px solid green;font-size:12px;">
										单据编号：
										<#if (purchase.purchaseCode)??>
											${purchase.purchaseCode?default("&nbsp;")}
										</#if>
									</span>
										
									<span style="padding-left:40%;">
										最后交货日期：
										<#if lastDate??>
										     ${lastDate?default("")}
										</#if>
									</span>
								</div>
								<br/>
								 <!--数据层start-->
							     <div>
							     	<!--表格层-->
									<div>	
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
					                 		<td style="text-align:right;font-weight:bold;">审批人：</td>
					                 		<td style="text-align:left;">
					                 			${purchase.approver?default("&nbsp;")}
					                 		</td>
					                 		
					                 		<td style="width:1%;">&nbsp;</td>
					                 		
					                 		<td style="text-align:right;font-weight:bold;">收货人：</td>
					                 		<td style="text-align:left;">${purchase.receivePeople?default("&nbsp;")}</td>
					                 		
										    
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
					                 	
					                 		<td style="text-align:right;font-weight:bold;">采购日期：</td>
					                 		<td style="text-align:left;">
					                 			<#if purchase.purchaseDate??>
										           ${purchase.purchaseDate?string("yyyy-MM-dd")}
										        </#if>
					                 		</td>
					                 		
					                 		<td style="width:1%;">&nbsp;</td>
					                 		
					                 		<td style="text-align:right;font-weight:bold;">预计到货日期：</td>
					                 		<td style="text-align:left;">  
										       <#if purchase.deliveryDate??>
										          ${purchase.planTime?date}
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
					                 		
					                 		<td style="text-align:right;font-weight:bold;">仓库：</td>
					                 		<td style="text-align:left;">
					                 			<#if (purchase.warehouse.warehouseName)??>
					                 				${purchase.warehouse.warehouseName?default("")}
					                 			</#if>
					                 		</td>
					                 		
					                 		
					                 		
					                 	
					                 	</tr>
					                 	<tr>
					                 		<td style="text-align:right;font-weight:bold;">备注：</td>
					                 		<td style="text-align:left;" colspan="7"> ${purchase.memo?default("&nbsp;")}</td>
					                 	</tr>
					                 	</#if>
					                 </table>
					                 </div>
					                 <!--表格层-->
					                 
					                 <div style="text-align:left;color:gray;font-weight:bold;">采购详细信息:</div>
						              <div class="yt-c-div">
						                    <table cellpadding="0" cellspacing="0" class="ytweb-table">
						                    <thead>
						                    <tr>                    
						                    <th style="width:9%;font-weight:bold;font-weight:bold;">商品款号</th>
						                    <th style="width:9%;font-weight:bold;font-weight:bold;">分类名称</th>
						                    <th style="width:9%;font-weight:bold;font-weight:bold;">品牌名称</th>
						                    <th style="width:13%;font-weight:bold;font-weight:bold;">商品名称</th>
						                    <th style="width:3%;font-weight:bold;font-weight:bold;">单位</th>
						                     <th style="width:3%;font-weight:bold;font-weight:bold;">单价</th>
						                    <th style="width:6%;font-weight:bold;font-weight:bold;">采购数量</th>
						                    <th style="width:6%;font-weight:bold;font-weight:bold;">入库数量</th>
						                    <th style="width:6%;font-weight:bold;font-weight:bold;">未入库<br/>数量</th>
						                    <th style="width:6%;font-weight:bold;font-weight:bold;">总额</th>
						                    <th style="width:6%;font-weight:bold;font-weight:bold;">库存数量</th>
						                    <th style="width:6%;font-weight:bold;font-weight:bold;">采购类型</th>
						                    <th style="width:6%;font-weight:bold;font-weight:bold;">备注</th>
						                    
						                    </tr>              
						                    </thead>
						                    <tbody id="tbd">
						                    <#if pageFinder??&&pageFinder.data??>
						                   		<#list pageFinder.data as pu>
							                   			 <tr>					
							                   			 	<td>${pu.commodity.styleNo?default("&nbsp;")}</td>			   
										                    <td>${pu.categoryName?default("&nbsp;")}</td>
										                    <td>${pu.brandName?default("&nbsp;")}</td>
										                    <td>${pu.commodityName?default("&nbsp;")}</td>
										                    <td>${pu.unit?default("&nbsp;")}</td>
										                    <td>${pu.purchasePrice?default("0.00")}</td>
										                    <td>${pu.purchaseQuantity?default("&nbsp;")}</td>
										                    <td>${pu.intostoreQuantity?default("0")}</td>
										                    <td>
										                    	&nbsp;
										                    	<#if pu.purchaseQuantity??&&pu.intostoreQuantity??>
										                    		${pu.purchaseQuantity-pu.intostoreQuantity}
										                    	<#elseif pu.purchaseQuantity??>
										                    		${pu.purchaseQuantity}
										                    	<#else>
										                    		-${pu.intostoreQuantity}}
										                    	</#if>
										                    </td>
										                    <td>${pu.purchaseTotalPrice?default("0.00")}</td>
										                    <td>${pu.stockQuantity?default("&nbsp;")}</td>
										                    <td>
										                    	<#if pu??&&pu.purchaseType??&&pu.purchaseType==102>
										                    		自购
										                    	</#if>
										                    	<#if pu??&&pu.purchaseType??&&pu.purchaseType==106>
										                    		比例代销
										                    	</#if>
										                    	<#if pu??&&pu.purchaseType??&&pu.purchaseType==107>
										                    		协议代销
										                    	</#if>
										                    <td >
										                    	${pu.remark?default("&nbsp;")}
										                    </td>
										                  </tr>
									                 
						                   		</#list>
						                   	<#else>
						                   		<tr class="div-pl-list">
													<td style="text-align:center;" colspan="13">
														<font style="color:gray;font-size:14px;font-weight:bold;">抱歉，没有您要找的数据</font>
													</td>
												</tr>
						                    </#if>
						                    </tbody>
						                </table> 
						         </div>
           					     </div>
							     <!--数据层end-->
						</div>	
						<!--标题层-->
				</div>	
                 <!--打印层end-->
         </div>
  </form>
</div>
</body>
</html>
