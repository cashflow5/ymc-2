<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,优购网,B网络营销系统,商品管理" />
<meta name="Description" content=" , ,B网络营销系统-商品管理" />
<link rev="stylesheet" rel="stylesheet"  type="text/css" href="css/style.css"/>
<link rev="stylesheet" rel="stylesheet" type="text/css" href="css/css.css" />
<script type="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
<script type="text/javascript" src="${BasePath}/js/supply/supplier.js" ></script>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<#include "../../../yitiansystem/yitiansystem-include.ftl">
<title>网络营销系统-采购管理-优购网</title>
<script type="text/javascript">	
//添加联系人	
function addContact(supplierId){
	openwindow('${BasePath}/yitiansystem/merchants/businessorder/to_SupplierContactt.sc?flag=1&supplierId='+supplierId,700,500,'添加联系人');
}
</script>
</head>
<body>
<form action="saveSupplierContact.sc" method="post" id="submitForm">
<div class="container"> 
	<!--工具栏start-->
	<div class="toolbar">
		<div class="t-content"> <!--操作按钮start--> 
		</div>
	</div>
	<!--工具栏end-->
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li>
				  <#if supplierId??>
				     <span><a href="${BasePath}/yitiansystem/merchants/businessorder/to_add_supplier.sc?id=${supplierId?default("")}&flag=1" class="btn-onselc">基本信息</a></span>
				  <#else>
				    <span><a href="${BasePath}/yitiansystem/merchants/businessorder/to_updateMerchants.sc?id=${supplierId?default("")}&flag=1" class="btn-onselc">基本信息</a></span>
				  </#if>
				</li>
				  <li>
				   <span><a href="toAddFinance.sc?supplierId=${supplierId?default("")}">财务信息</a></span>
				 </li>
				<li  class="curr">
				  <span><a href="#">联系人列表</a></span>
				</li>
			</ul>
		</div>
<div class="modify">
<input type="hidden" id="basePath" value="${BasePath}">
<div class="blank10"></div>
&nbsp;<a href="${BasePath}/supply/manage/supplier/supplierlist.sc" style="color:red;">返回</a>	
&nbsp;&nbsp;<a href="javascript:addContact('${supplierId?default("")}');" style="color:red;">添加联系人</a>
 <div class="blank10"></div>	
 <input type="hidden" id="param" name="param" value="${supplierId?default("")}"/> 		
			<table cellpadding="0" cellspacing="0" class="list_table">
			  <tr>					  						
				<td width="10%">姓名</td>
				<td width="10%">类型</td>	
				<td width="15%">电话</td>					
				<td width="15%">手机</td>						
				<td width="10%">email</td>
				<td width="10%">传真</td>
				<td width="15%">地址</td>
				<td width="15%">操作</td>
			  </tr>	
			  <#if pageFinder??>
			  <#if pageFinder.data??>				 
			  <#list pageFinder.data as supplier>
			  <tr class="div-pl-list"> 												
				<td>${supplier.contact?default("")}</td>
				<td>${supplier.type?default("")}</td>				
				<td>${supplier.telePhone?default("")}</td>												
				<td>${supplier.mobilePhone?default("")}</td>						
				<td>${supplier.email?default("")}</td>
				<td>${supplier.fax?default("")}</td>
				<td>${supplier.address?default("")}</td>						
				<td class="pl-edt">		
				<a href="toUpdateSupplierContact.sc?id=${supplier.id}">编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;						
				<a href="javascript:deleteSupplierContact('${supplier.id}');">删除</a>
				
				</td>
			  </tr>
			  </#list>	
			  </#if>
 			  </#if>					 
		  </table>
	</div>					
</form>
</div>
</body>
</html>
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>