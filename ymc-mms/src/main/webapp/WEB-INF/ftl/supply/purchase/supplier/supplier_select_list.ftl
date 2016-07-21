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
<script type="text/javascript" src="${BasePath}/js/supply/supplier.js" ></script>
<#include "../../../yitiansystem/yitiansystem-include.ftl">
<title>B网络营销系统-采购管理-优购网</title>
</head>
<body>
<div class="main-body" id="main_body">				 
		<div class="div-pl">
		<form action ="${BasePath}/supply/manage/supplier/querySupplierSelect.sc" id="submitForm" name="submitForm" method="post">				
			<div class="div-pl-hd ft-sz-12">			
				<table>
					<tr>
					<td>
					<span>供应商名称：</span>
					</td>
					<td>
					<input name="param" id="param" value="<#if param??>${param?default("")}</#if>" class="blakn-sl" />
					</td>
					<td>
					<span>供应商编码：</span>
					</td>
					<td>
					<input name="supplierCode" id="supplierCode" value="<#if supplierCode??>${supplierCode?default("")}</#if>" class="blakn-sl" />
					</td>
					</tr>
					
					<tr>
					<td>
					<span>供应商类型：</span>
					</td>
					<td>
					<select id="type" name="type" value="">
	            		<option value="" selected="selected">请选择</option>
	            		<#if listSupplierType??>
	            		<#list listSupplierType as supplierType>
	            		<option value="${supplierType.typeValue}" <#if type?? && type==supplierType.typeValue>selected="selected"</#if>>${supplierType.typeValue?default("")}</option>
	            		</#list>
	            		</#if>
	            	</select> 
					</td>
					<td>
					<span>供应商状态：</span>
					</td>
					<td>
					<select id="isValid" name="isValid" value="">
	               		<option value="" selected="selected">请选择</option>
	               		<option value="1" <#if isValid?? && isValid==1>selected="selected"</#if>>正常</option>
	               		<option value="2" <#if isValid?? && isValid==2>selected="selected"</#if>>锁定</option>
	               		<option value="-1" <#if isValid?? && isValid==-1>selected="selected"</#if>>关闭</option>
                	</select>
					</td>
					<td>					
					<input type="submit" value="搜索" name="search" id="search" onClick="" class="btn-sh"/>
					</td>
					</tr>
				</table>				
			</div>			
			<div class="div-pl-table" id="div-table">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
				  <tr class="div-pl-tt">					  						
					<td class="pl-tt-td" width="20%">供应商名称</td>	
					<td class="pl-tt-td" width="20%">供应商编码</td>					
					<td class="pl-tt-td" width="5%">类型</td>						
					<td class="pl-tt-td" width="5%">状态</td>
					<td class="pl-tt-td" width="20%">备注</td>
				  </tr>	
				  <#if pageFinder??>
				  <#if pageFinder.data??>				 
				  <#list pageFinder.data as supplier>
				  <tr class="div-pl-list"> 												
					<td style="text-align:left">
					<input type="radio" name="commName" supplierTag="${supplier.id?default("")}" value="${supplier.supplier?default("")}" onclick="supplierSelect('${supplier.id?default("")}','${supplier.supplier?default("")}')"/>
					${supplier.supplier?default("")}
					</td>				
					<td>${supplier.supplierCode?default("")}</td>												
					<td>${supplier.supplierType?default("")}</td>						
					<td><#if supplier.isValid??>
						<#if supplier.isValid==1>正常 
						<#elseif supplier.isValid==0>锁定
						<#elseif supplier.isValid==-1>关闭
						</#if>
					</#if>
					</td>
					<td>${supplier.remark?default("")}</td>					
				  </tr>
				  </#list>	
				  </#if>
	 			  </#if>					 
			  </table>
			</div>					
		</div>
		</form>		
			<div class="div-pl-bt">
				<!-- 翻页标签 -->
					<#import "../../../common.ftl"  as page>
					<@page.queryForm formId="submitForm" />				  
			</div>			
		</div>		
	</div>		
</body>
</html>

