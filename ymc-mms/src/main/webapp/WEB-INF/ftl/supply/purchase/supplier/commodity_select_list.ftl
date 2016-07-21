<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<#include "../../../yitiansystem/yitiansystem-include.ftl">
<script type="text/javascript" src="${BasePath}/js/common/hashMap.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/commoditymgmt/commodityinfo/myDateValidate.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/commoditymgmt/commodityinfo/product_list.js"></script>
<script type="text/javascript" src="${BasePath}/js/supply/supplier.js" ></script>
<title>商品列表</title>
</head>

<body>
<div class="main-body" id="main_body">			
			<div class="div-pl">
<input type="hidden" id="basepath" value="${BasePath}">
<div class="div-pl-hd ft-sz-12">
<form id="queryForm" name="queryForm" action="queryCommodityList.sc" method="POST">
    	<table>
					<tr>
					<td height=30 align="right">
					<span>商品名称：</span>
					</td>
					<td>
					<input name="productName" id="productName" class="blakn-supply" value="<#if commoditySearchVo??>${commoditySearchVo.commodityName?default("")}</#if>"/>
					</td>						          	
					<td >
					<span>商品编码：</span>
					</td>
					<td>
					<input name="productNo" id="productNo" class="blakn-supply" value="<#if commoditySearchVo??>${commoditySearchVo.productNo?default("")}</#if>"/>
					</td>
					<td>					
					<span>款色编码：</span>
					</td>
					<td>
					<input name="supplierCode" id="supplierCode" class="blakn-supply" value="<#if commoditySearchVo??>${commoditySearchVo.supplierCode?default("")}</#if>"/>
					</td>											               	
					<td>
					</td>	
					<td>
					</td>						
					</tr>
					
					<tr>
					<td height=30 align="right">
					<span>商品款号：</span>
					</td>
					<td>
					<input name="styleNo" id="styleNo" class="blakn-supply" value="<#if commoditySearchVo??>${commoditySearchVo.styleNo?default("")}</#if>"/>
					</td>
					<td>					
					<span>分类名称：</span>
					</td>
					<td>
					<input name="catName" id="catNo" class="blakn-supply" value="<#if commoditySearchVo??>${commoditySearchVo.catName?default("")}</#if>"/>
					</td>	
					<td>
					<span>品牌名称：</span>
					</td>
					<td>
					<select name="brandNo" id="brandNo">
		            		<option value="">请选择品牌</option>
		            		<#list brandList as item >
		            		<option <#if commoditySearchVo??&&commoditySearchVo.brandNo??&&commoditySearchVo.brandNo==item.brandNo>selected</#if> value="${item.brandNo?default('')}">${item.brandName?default('')}</option>
		            		</#list>	   
		            </select>		
					</td>														
                					
					<td>
					</td>	
					<td>
					</td>	
					</tr>
					
					<tr>
					<td>
					</td>	
					<td>
					</td>						
					<td>
					</td>	
					<td>
					</td>
					<td>
					</td>	
					<td>
					</td>	
					<td>
					</td>					
					<td>
					<input type="submit" value="搜索" name="search" id="search" onClick="" class="btn-sh"/>
					</td>
					</tr>
					</table>
      	</form>
      	</div>
      	</div>
      	</div>
<div class="contentMain">    	
            	
    	
    	
        <div class="yt-c-div">        
        
            <table cellpadding="0" cellspacing="0" class="ytweb-table">
            <thead>
            <tr>
            <th><input type="checkbox" id="chk" onClick="allChk(this,'commName')" /></th>
                <th>商品名称 </th>
                <th>商品编号</th>
                <th>款色编码</th>
                <th>商品款号</th>                
                <th>分类名</th>
                <th>品牌</th>
                <th>市场价</th>                
                <th>上传时间 </th>                
            </tr>              
            </thead>
            <tbody>
            
             <#if pageFinder?? && (pageFinder.data)?? >
	      		<#list pageFinder.data as item >
	      		<tr>
	      			 <td >						
						<input type="checkbox" value="${item.no?default("")}" name="commName" 						 
								commodity="${item.commodityName?default("")}"								
								/>
                    </td>
                    <td>
						${item.commodityName?default("")}                 
                    </td>
                    <td>${item.no?default("")}</td>
                    <td>${item.supplierCode?default("")}</td>
                    <td>${item.styleNo?default("")}</td>
                    <td>${item.catName?default("")}</td>
                    <td>${item.brandName?default("")}</td>
                    <td>${item.publicPrice?default("")}</td>                    
                    <td>
                    	<#if item.createDate??>
                    		${item.createDate?string("yyyy-MM-dd")} 
                    	</#if>
                    </td>                    
                </tr>
	      		</#list>	      	
	      	<#else>
	      		<tr><td colspan="10"><div class="yt-tb-list-no">暂无内容</div></td></tr>
			</#if>
	      		
            </tbody>
            </table> 
            <div class="div-pl-bt">										
					<div class="div-pl-bt-deal">													
						<a href="javascript:batchSelectCommodity('commName')" >确认选择</a>
						<a href="javascript:getBack('${BasePath}')" >返回</a>
					</div>				
			</div>
            <div class="blank10"></div>
            
            <#if pageFinder?? && (pageFinder.data)?? >
            	<#import "../../../common.ftl"  as page>
				<@page.queryForm formId="queryForm" />   	                    
			 </#if>
            <div class="blank10"></div>	
     	</div>
    </div>
</div>

</body>
</html>
