<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${BasePath}/css/ytsys-base.css">

<#include "../../yitiansystem/yitiansystem-include.ftl">
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ytsys-comment.js"></script>
<script type="text/javascript" src="${BasePath}/js/supply/supplier/supplierCommodity.js"></script>
<title>无标题文档</title>
</head>
<body>

<div class="contentMain">
<form action="findFinderDetial.sc" name="perchaseDetialForm"" id="perchaseDetialForm" method="post">
        	<div class="ytback-tt-1 ytback-tt">
            	<span>您当前的位置：</span>采购管理  &gt; 采购详情
            </div>
            
            <div class="content">
            
                <div class="mb-btn-fd-bd">
                    <div class="mb-btn-fd relative">
                    <span class="btn-extrange absolute">
                        <ul class="onselect">
                            <li class="pl-btn-lfbg"></li>
                            <li class="pl-btn-ctbg"><a  class="btn-onselc">采购订单详情</a></li>
                            <li class="pl-btn-rtbg"></li>
                        </ul>
                    </span>
                  </div>
                </div>
            	<div class="yt-c-top">
                    
                <div class="yt-c-div">
                    <table cellpadding="0" cellspacing="0" class="ytweb-table">
                    <thead>
                    <tr>
                    <th style="width:10%;overflow:hidden; font-weight:bold;">单据编号</th>
                    <th style="width:10%;overflow:hidden;font-weight:bold;">分类名称</th>
                    <th style="width:10%;overflow:hidden;font-weight:bold;">品牌名称</th>
                    <th style="width:10%;overflow:hidden;font-weight:bold;">商品名称</th>
                    <th style="width:5%;overflow:hidden;font-weight:bold;">规格</th>
                    <th style="width:5%;overflow:hidden;font-weight:bold;">单位</th>
                    <th style="width:5%;overflow:hidden;font-weight:bold;">数量</th>
                    <th style="width:5%;overflow:hidden;font-weight:bold;">单价</th>
                    <th style="width:5%;overflow:hidden;font-weight:bold;">折扣</th>
                    <th style="width:10%;overflow:hidden;font-weight:bold;">总金额</th>
                    </tr>              
                    </thead>
                    <tbody id="tbd">
                    <#if pageFinder??&&pageFinder.data??>
                   		<#list pageFinder.data as pu>
	                   			 <tr>
								    <td>${pu.purchase.purchaseCode?default("&nbsp;")}</td>
				                    <td>${pu.categoryName?default("&nbsp;")}</td>
				                    <td>${pu.brandName?default("&nbsp;")}</td>
				                    <td>${pu.commodityName?default("&nbsp;")}</td>
				                    <td>${pu.specification?default("&nbsp;")}</td>
				                    <td>${pu.unit?default("&nbsp;")}</td>
				                    <td>${pu.purchaseQuantity?default("&nbsp;")}</td>
				                    <td>${pu.purchasePrice?default("&nbsp;")}</td>
				                    <td>${pu.deductionRate?default("1")}</td>
				                    <td>
				                    	${pu.purchaseTotalPrice?default(0.0)}
				                    </td>
				                  </tr>
			                 
                   		</#list>
                   	<#else>
                   		<tr class="div-pl-list">
							<td style="text-align:center;" colspan="10">
								<font style="color:gray;font-size:14px;font-weight:bold;">抱歉，没有您要找的数据</font>
							</td>
						</tr>
                    </#if>
                    </tbody>
                </table> 
         </div>
    </div>
    <!--分页采购单号-->
    <input type="hidden" value="${perchaseId}" name="val">
  </form>
    
	<div class="div-pl-bt">
		<#import "../../common.ftl"  as page>
		<@page.queryForm formId="perchaseDetialForm" />
	</div>   
	
</div>
</body>
</html>
