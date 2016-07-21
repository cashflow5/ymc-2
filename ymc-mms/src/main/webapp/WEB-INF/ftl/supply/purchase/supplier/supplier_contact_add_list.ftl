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
<script type="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
<script type="text/javascript" src="${BasePath}/js/supply/supplier.js" ></script>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>

<#include "../../../yitiansystem/yitiansystem-include.ftl">

<title>B网络营销系统-采购管理-优购网</title>
<script type="text/javascript">	
	//提交按钮所在的表单
	function postForm(formId, url){
		$("#"+formId).attr("action",url);
		//添加hidden到form		
	  	var param = $('#param').val();
	  	alert(param);
	  	if("0" == param && ""== param){
	  		alert("条件为空,不能搜索!");
	  		return;
	  	}
		$("#"+formId).submit();
	}
	
	function deleteSupplierContact(id){	
		if(window.confirm('确认删除？')==false){
			return;
		}		
	    var value=id;	     	
       	$.ajax({
           type: "POST",
           url: "deleteSupplierContact.sc",
           data: {"id":value},           
           success: function(data){           
              if(data=="success"){
 		 		alert("删除成功!"); 	
 		 		window.location.reload();	 		
 		 	  }              
           }
         });          
      } 
	
//添加联系人	
function addContact(path,supplierId){
	openwindow(path+'/yitiansystem/merchants/businessorder/to_SupplierContactt.sc?flag=1&supplierId='+supplierId,700,500,'添加联系人');
}
</script>
</head>
<body>
<form action ="querySupplier.sc" id="supplierListForm" name="supplierListForm" method="post">
<#if supplier??&&supplier.id??>
	<input type="hidden" id="id" name="id" value="${supplier.id}"/>
<#else>
	<input type="hidden" id="id" name="id" value=""/>
</#if>
<!--工具栏start-->
	<div class="toolbar">
		<div class="t-content"> <!--操作按钮start--> 
		</div>
	</div>
<div class="container">
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li>
				 <#if supplier??&&supplier.id??>
				     	<span><a href="${BasePath}/yitiansystem/merchants/businessorder/to_updateMerchants.sc?id=${supplier.id?default("")}&flag=1" class="btn-onselc">基本信息</a></span>
				  <#else>
				   	   <span><a href="${BasePath}/yitiansystem/merchants/businessorder/to_add_supplier.sc?id=${supplier.id?default("")}&flag=1" class="btn-onselc">基本信息</a></span>
				  </#if>
				</li>
				<li> <span><a href="toAddFinance.sc?supplierId=<#if supplier??&&supplier.id??>${supplier.id?default("")}</#if>">财务信息</a></span> </li>
				<li  class='curr'>
				  <span><a href="">联系人列表</a></span>
				</li>
			</ul>
		</div>
 <div class="modify">  
 <div class="blank10"></div>
				&nbsp;<a href="${BasePath}/supply/manage/supplier/supplierlist.sc" alt="返回供应商管理列表" style="color:red;">返回</a>	
				&nbsp;&nbsp;<a href="javascript:addContact('${BasePath}','${supplier.id}');" alt="添加供应商到所选标签"  style="color:red;">添加联系人</a>
				 <div class="blank10"></div>	
					 <table cellpadding="0" cellspacing="0" class="list_table">
					  <tr>					  						
						<td class="pl-tt-td" width="10%">姓名</td>
						<td class="pl-tt-td" width="10%">类型</td>	
						<td class="pl-tt-td" width="15%">电话</td>					
						<td class="pl-tt-td" width="15%">手机</td>						
						<td class="pl-tt-td" width="10%">email</td>
						<td class="pl-tt-td" width="10%">传真</td>
						<td class="pl-tt-td" width="15%">地址</td>
					  </tr>	
					  <#if pageFinder??>
					  <#if pageFinder.data??>				 
					  <#list pageFinder.data as supplier>
					  <tr class="div-pl-list"> 												
						<td>${supplier.contact?default("")}</td>
						<td><#if supplier.type==1>业务
						<#elseif supplier.type==2>售后
						<#elseif supplier.type==3>仓储
						<#elseif supplier.type==4>财务
						<#elseif supplier.type==5>技术
						</#if></td>				
						<td>${supplier.telePhone?default("")}</td>												
						<td>${supplier.mobilePhone?default("")}</td>						
						<td>${supplier.email?default("")}</td>
						<td>${supplier.fax?default("")}</td>
						<td>${supplier.address?default("")}</td>						
						<td class="pl-edt">		
						
						
						</td>
					  </tr>
					  </#list>	
					  </#if>
		 			  </#if>					 
				  </table>
				</div>					
			</div>		
		</div>
		</form>		
</body>
</html>

<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>