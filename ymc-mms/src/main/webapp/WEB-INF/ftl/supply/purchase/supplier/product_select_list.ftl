<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<#include "../../../yitiansystem/yitiansystem-include.ftl">

<script type="text/javascript" src="${BasePath}/js/supply/product_select.js"></script>
<script src="${BasePath}/js/supply/supplier.js" type="text/javascript"></script>
<title></title>
</head>

<body>
<input type="hidden" id="basePath" value="${BasePath}">

<div class="contentMain"> 
			<form id="queryForm" name="queryForm" action="toSelectCommodity.sc" method="POST">       	
            </form>
            <div class="content">      
                <div class="yt-c-div">
                    <table cellpadding="0" cellspacing="0" class="ytweb-table">
                    <thead>
                    <tr>
                    <th></th>
	                    <th>商品名称 </th>
	                    <th>货品编号</th>
	                    <th>分类名</th>
	                    <th>规格编码</th>	                    
                    </tr>              
                    </thead>
                    <tbody>
                    
                     <#if pageFinder?? && (pageFinder.data)?? >
			      		<#list pageFinder.data as item >
			      		<tr>
			      			 <td >								
								<input type="checkbox" value="${item.productNo}" name="commName" 
								commodity="${item.commodity.commodityName?default("")}"
								product="${item.productNo?default("")}"
								specprop="<#if item.tblProSpec??>
		                    <#list item.tblProSpec as val>
		                    	${val.propValue?default("")}
		                    </#list>
		                    </#if>"/>
		                    </td>
		                    <td>
								${item.commodity.commodityName?default("")}                 
		                    </td>
		                    <td>${item.productNo?default("")}</td>
		                    <td>${item.commodity.catName?default("")}</td>                   
		                    <td><#if item.tblProSpec??>
		                    <#list item.tblProSpec as val>
		                    	${val.propValue?default("")}
		                    </#list>
		                    </#if>
		                    </td>		                   
	                    </tr>
			      		</#list>	
			      	
			      	<#else>
			      		<tr><td colspan="10"><div class="yt-tb-list-no">暂无内容</div></td></tr>
					</#if>
			      		
                    </tbody>
                    </table> 
                    <input type="button" id="chktags" name="chkAll" value="全选/反选"/>				
				<div class="div-pl-bt">										
					<div class="div-pl-bt-deal">													
						<a href="javascript:batchSelectProducts('commName')" >确认选择</a>
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
