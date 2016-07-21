<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${BasePath}/css/ytsys-base.css">
<#include "../../yitiansystem/yitiansystem-include.ftl">
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ytsys-comment.js"></script>
<script type="text/javascript" src="${BasePath}/js/supplys/supplier/perchaseList.js"></script>

<title>无标题文档</title>
</head>
<body>
<div class="contentMain">
        	<div class="ytback-tt-1 ytback-tt">
            	<span>您当前的位置：</span>采购管理  &gt; 采购单列表
            </div>
            
            <div class="content">
            
                <div class="mb-btn-fd-bd">
                    <div class="mb-btn-fd relative">
                    <span class="btn-extrange absolute">
                        <ul class="onselect">
                            <li class="pl-btn-lfbg"></li>
                            <li class="pl-btn-ctbg"><a  class="btn-onselc">采购单列表</a></li>
                            <li class="pl-btn-rtbg"></li>
                        </ul>
                    </span>
                    
                  </div>
                </div>
                <form action="findBypageFinder.sc" name="supplierCommodity" id="supplierCommodity" method="post">
            	<div class="yt-c-top">
                     <label for="t1">采购日期：</label>
                     <#if puVo??&&puVo.purchaseDate??>
                     	<input type="text" id="purchaseDateId" name="purchaseDate" value="${puVo.purchaseDate}">
                     <#else>
                     	<input type="text" id="purchaseDateId" name="purchaseDate">
                     </#if>
                     -
                     <#if puVo??&&puVo.deliveryDate??>
                     	  <input type="text" id="deliveryDateId" name="deliveryDate" value="${puVo.deliveryDate}">
                     <#else>
                     	  <input type="text" id="deliveryDateId" name="deliveryDate">
                     </#if>
                     
                   	 <div style="padding-top:5px;"></div>
                   	 
                   	  <label for="t4">采购类型：</label>
                      <select name="type" style="width:152px;">
                      	<option value="-1" selected>请选择</option>
                     	<option <#if puVo??&&puVo.type??&&puVo.type==102>selected</#if> value="102">自购</option>
                     	<option <#if puVo??&&puVo.type??&&puVo.type==107>selected</#if> value="107">代销-比例结算</option>
                     	<option <#if puVo??&&puVo.type??&&puVo.type==106>selected</#if> value="106">代销-协议结算</option>
                     </select>
                     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                     <label for="t3">仓库： </label>
                     &nbsp;
                      <select name="warehouse" style="width:111px;">
                      	<option value="" selected>请选择</option>
                     	<#if warehoseList??>
                     		<#list warehoseList as wh>
                     			<#if puVo??&&puVo.warehouse??&&wh.id==puVo.warehouse>
                     				<option selected value="${wh.id}">${wh.warehouseName?default("匿名")}</option>
                     			<#else>
                     				<option value="${wh.id}">${wh.warehouseName?default("匿名")}</option>
                     			</#if>
                     		</#list>
                     	<#else>
                     		<option value="" selected>请选择</option>
                     	</#if>
                     </select>
                    
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                      <label for="t4">单据状态：</label>
                      <select name="status">
                      	<option value="-1" selected>请选择</option>
                     	<option <#if puVo??&&puVo.type??&&puVo.status==1>selected</#if> value="1" >审核</option>
                     	<option <#if puVo??&&puVo.type??&&puVo.status==0>selected</#if> value="0" >未审核</option>
                     </select>
                     <label for="t5">自定义：</label>
                      <select name="searchName">
                      	<option value="" selected>请选择</option>
                     	<option <#if puVo??&&puVo.searchName??&&puVo.searchName=="purchaseCode">selected</#if> value="purchaseCode" >单据编号</option>
                     	<option <#if puVo??&&puVo.searchName??&&puVo.searchName=="sourceCode">selected</#if> value="sourceCode" >来源单号</option>
                     </select>
	                   	<#if puVo??&&puVo.searchaValue??>
	                    	<input name="searchaValue" type="text" id="t4" size="12" value="${puVo.searchaValue}" />
	                    <#else>
	                    	<input name="searchaValue" type="text" id="t4" size="12" />
	                   </#if>
                     <input type="button" value="搜&nbsp;索" class="yt-seach-btn" onclick="submits()" />
              	</div>

           </form>
                <div class="yt-c-div">
                    <table cellpadding="0" cellspacing="0" class="ytweb-table">
                    <thead>
                    <tr>
                    <th style="width:10%;font-weight:bold;">单据编号</th>
                    <th style="width:9%;font-weight:bold;">审批人</th>
                    <th style="width:10%;font-weight:bold;">采购日期</th>
                    <th style="width:10%;font-weight:bold;">收货日期</th>
                    <th style="width:9%;font-weight:bold;">收货人</th>
                    <th style="width:19%;font-weight:bold;">收货地址</th>
                    <th style="width:9%;font-weight:bold;">来源单号</th>
                    <th style="width:5%;font-weight:bold;">单据状态</th>
                    <th style="width:12%;font-weight:bold;">备注</th>
                    <th style="width:7%;font-weight:bold;">操作</th>
                    </tr>              
                    </thead>
                    <tbody id="tbd">
                    <#if pageFinder??&&pageFinder.data??>
                   		<#list pageFinder.data as pu>
	                   			 <tr>
								    <td>${pu.purchaseCode?default("&nbsp;")}</td>
				                    <td>${pu.approver?default("&nbsp;")}</td>
				                    <td>
				                    	&nbsp;
				                    	<#if pu.purchaseDate??>
				                    		${pu.purchaseDate?string("yyyy-MM-dd")}</td>
				                    	</#if>
				                    <td>
				                    	&nbsp;
				                    	<#if pu.deliveryDate??>
				                    		${pu.deliveryDate?default("")}</td>
				                    	</#if>
				                    </td>
				                    <td>${pu.receivePeople?default("&nbsp;")}</td>
				                    <td>${pu.receiveAddress?default("&nbsp;")}</td>
				                    <td>${pu.sourceCode?default("&nbsp;")}</td>
				                    <td>
				                    	<#if pu.status==1>审核</#if>
				                    	<#if pu.status==0>未审核</#if>
				                    </td>
				                    <td>${pu.memo?default("&nbsp;")}</td>
				                    <td><a href="${BasePath}/supply/supplier/suppliercommotity/findFinderDetial.sc?val=${pu.id}" >查看详情</a></td>
				                  </tr>
                   		</#list>
                   	<#else>
                   		<tr class="div-pl-list">
							<td style="text-align:center;" colspan="8">
								<font style="color:gray;font-size:14px;font-weight:bold;">抱歉，没有您要找的数据</font>
							</td>
						</tr>
                    </#if>
                    </tbody>
                    </table> 
                    <div class="blank10"></div>
                   
         </div>
    </div>
  	<!--分页-->
     <div class="div-pl-bt">
		<#import "../../common.ftl"  as page>
	    <@page.queryForm formId="supplierCommodity" />
	 </div>   
				
</div>
</body>
</html>
