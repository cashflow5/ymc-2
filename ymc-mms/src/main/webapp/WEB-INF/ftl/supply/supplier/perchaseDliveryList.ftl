<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${BasePath}/css/ytsys-base.css">
<#include "supplier_include.ftl">
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ytsys-comment.js"></script>
<script type="text/javascript" src="${BasePath}/js/supplys/supplier/perchasefufill.js"></script>
<script>
var path="${BasePath}";
</script>

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
					<#if puVo??>
                  		<a   href="findPurchaseCount.sc?currentPage=${puVo.purchaseCurrent?default(1)}">返&nbsp;回</a>
                  	<#else>
                  		<a  href="findPurchaseCount.sc?currentPage=1">返&nbsp;回</a>
                  	</#if>
					</span>
					<span class="btn_r"></span>
			</div>
		</div>
		<div class="top clearfix">
			<ul class="tab">
				<li class='curr'>
				  <span>采购入库查询</span>
				</li>
			</ul>
		</div>
 <div class="modify">
               <form action="findDliveryPurchase.sc" name="purchaseDliveryForm" id="purchaseDliveryForm" method="post">
            <div class="yt-c-top">
            		<label for="t5">供应商：</label>
                      <select name="supplierId" style="width:105px;">
                      	<option value="" selected>请选择</option>
                     	<#if supList??>
                     		<#list supList as su>
                     		   <#if puVo??&&puVo.supplierId??&& su.id==puVo.supplierId>
                     		   		<option selected value="${su.id}" >${su.supplier}</option>
                     		   <#else>
                     				<option value="${su.id}" >${su.supplier}</option>
                     			</#if>
                     		</#list>
                     	</#if>
                     </select>
              
                    <label for="t5">&nbsp;&nbsp;&nbsp;&nbsp;仓库：</label>
            		
                      <select name="warehouseId" style="width:105px;">
                      	<option value="" selected>请选择</option>
                     	<#if warehoseList??>
                     		<#list warehoseList as wh>
                     		   <#if puVo??&&puVo.warehouseId??&& wh.id==puVo.warehouseId>
                     		   		<option selected value="${wh.id}" >${wh.warehouseName}</option>
                     		   <#else>
                     				<option value="${wh.id}" >${wh.warehouseName}</option>
                     			</#if>
                     		</#list>
                     	</#if>
                     </select>
                    
                     <label for="t5">单据状态：</label>
            		
                      <select name="status" style="width:80px;">
                      	<option value="" selected>请选择</option>
                     		<option <#if puVo.status??&&puVo.status=="1"> selected </#if> value="1" >已审核</option>
                     		<option <#if puVo.status??&&puVo.status=="0"> selected </#if> value="0" >待审核</option>
                     		<option <#if puVo.status??&&puVo.status=="2"> selected </#if> value="2" >新建</option>
                     		<option <#if puVo.status??&&puVo.status=="-1"> selected </#if> value="-1" >已作废</option>
                     		<option <#if puVo.status??&&puVo.status=="3"> selected </#if> value="3" >已关闭</option>
                       </select>
                    
                     <label for="t1">采购日期：</label>
                   
                     <#if puVo??&&puVo.purchaseDate??>
                     	<!--<input type="text" size="10" id="purchaseDateId" name="purchaseDate" value="${puVo.purchaseDate}" />-->
                     	<input type="text" id="purchaseDateId" name="purchaseDate" value="${puVo.purchaseDate}" class="Wdate" style="width:100px;" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'deliveryDateId\')}'})" onchange="startDate()"  />
                     <#else>
                     	<input type="text" id="purchaseDateId" name="purchaseDate" class="Wdate" style="width:100px;" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'deliveryDateId\')}'})" onchange="startDate()" />
                     	<!--<input type="text" size="10" id="purchaseDateId" name="purchaseDate">-->
                     </#if>
                    
                    &nbsp;&nbsp;&nbsp;到&nbsp;&nbsp;&nbsp;
                   
                     <#if puVo??&&puVo.deliveryDate??>
                     	 <!-- <input type="text" size="10" id="deliveryDateId" name="deliveryDate" value="${puVo.deliveryDate}" />-->
                     	 <input type="text" id="deliveryDateId" name="deliveryDate" value="${puVo.deliveryDate}" class="Wdate" style="width:100px;" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'purchaseDateId\')}'})" onchange="compareDate()" />
                     <#else>
                     	 <!-- <input type="text" size="10" id="deliveryDateId" name="deliveryDate">-->
                     	 <input type="text" id="deliveryDateId" name="deliveryDate"  class="Wdate" style="width:100px;" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'purchaseDateId\')}'})"  onchange="compareDate()" />
                     </#if>                    
                   	<br/>
                   	<label for="t5">采购人：</label>
                   	<#if (puVo.purchaser)??>
                   		<input type="text" name="purchaser" value='${puVo.purchaser}' style="width:100px;" />
                   	<#else>
                   		<input type="text" name="purchaser" style="width:100px;" />
                   	</#if>
                   	<label for="t5">建档人：</label>
                   	<#if (puVo.createPeople)??>
                   		<input type="text" name="createPeople" value='${puVo.createPeople}' style="width:100px;" />
                   	<#else>
                   		<input type="text" name="createPeople" style="width:100px;" />
                   	</#if>
	                <label for="t5">&nbsp;&nbsp;&nbsp;&nbsp; 自定义：</label>
	             
	                 <select name="selectValue">
                      	<option value="" selected>请选择</option>
                     	<option <#if puVo??&&puVo.selectValue??&&puVo.selectValue=="-1">selected</#if> value="-1" >单据编号</option>
                     	<option <#if puVo??&&puVo.selectValue??&&puVo.selectValue=="-2">selected</#if> value="-2" >商品编号</option>
                     </select>
                    
                     <#if puVo??&&puVo.textValue??>
	                	<input type="text" style="width:100px;" name="textValue" value='${puVo.textValue}'  />
	                <#else>
	                	<input type="text" name="textValue" style="width:100px;" />
	                </#if>
	               
                     <input type="submit" value="搜&nbsp;索" class="yt-seach-btn" id="submitid" />
                    
              	</div>

         	 </form>
                <div class="yt-c-div">
              <table cellpadding="0" cellspacing="0" class="list_table">
                    <thead>
                    <tr>
                    <th >单据编号</th>
                     <th >供应商</th>
                    <th>单据&nbsp;状态</th>
                    <th>采购人</th>
                     <th>建档人</th>
                     <th>采购日期</th>
                    <th>建档日期</th>
                   <!-- <th style="width:8%;overflow:hidden;font-weight:bold;">来源单号</th>-->
                   
                    <th>仓库</th>
                    <th>合计&nbsp;数量</th>
                    <th>合计&nbsp;金额</th>
                    <th>备注</th>
                    <th>操作</th>
                    </tr>              
                    </thead>
                    <tbody id="tbd">
                    <#if pageFinder??&&pageFinder.data??>
                   		<#list pageFinder.data as pu>
	                   			 <tr>
								    <td>${pu.purchaseCode?default("&nbsp;")}</td>
								      <td>
				                    	&nbsp;
				                    	<#if pu.supplier??&&pu.supplier.supplier??>
				                    		${pu.supplier.supplier}
				                    	</#if>
				                    </td>
								    <td>
				                    	<#if pu.status??&&pu.status==1>已审核</#if>
				                    	<#if pu.status??&&pu.status==0>待审核</#if>
				                    	<#if pu.status??&&pu.status==-1>已作废</#if>
				                    	<#if pu.status??&&pu.status==2>新建</#if>
				                    	<#if pu.status??&&pu.status==3>已关闭</#if>
				                    </td>
				                     <td>${pu.purchaser?default("&nbsp;")}</td>
				                    <td>${pu.createPeople?default("&nbsp;")}</td>
				                    <td>&nbsp;
				                    	<#if pu.purchaseDate??>
				                    		${pu.purchaseDate?string("yyyy-MM-dd")}
				                    	</#if>
				                    </td>
				                    <td>
				                    	&nbsp;
				                    	<#if pu.createDate??>
				                    		${pu.createDate?string("yyyy-MM-dd")}
				                    	</#if>
				                    </td>
				                   <!-- <td>${pu.sourceCode?default("&nbsp;")}</td>-->
				                    <td>
				                    	<#if (pu.warehouse.warehouseName)??>
				                    		${pu.warehouse.warehouseName}
				                    	<#else>
				                    		&nbsp;
				                    	</#if>
				                    </td>
				                    <td>${pu.amount?default("0")}</td>
				                    <td>${pu.totalPrice?default("0")}</td>
				                    <td>${pu.memo?default("&nbsp;")}</td>
				                    <td>
				                       <a href="${BasePath}/supply/supplier/PerchaseOrder/findDliveryPurchaseDetial.sc?purchaseId=${pu.id}&amount=${pu.amount?default(0)}&totalPrice=${pu.totalPrice?default(0.0)}&currentPage=${puVo.currentPage?default(1)}" >查看详情</a>
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
         <div class="bottom clearfix">
			<#import "../../common.ftl"  as page>
			<@page.queryForm formId="purchaseDliveryForm" />
		</div> 
    </div>					
</div>
</body>
</html>
