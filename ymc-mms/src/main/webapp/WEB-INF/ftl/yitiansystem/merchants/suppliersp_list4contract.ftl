<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,优购网,B网络营销系统,采购管理" />
<meta name="Description" content=" , ,B网络营销系统-采购管理" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>

<script type="text/javascript">
function serach(){
   document.queryForm.submit();
}

//选择供应商值传给父窗口
function checkVlale(id,name){
  if(id==""){
    alert("供应商名称为空!");
    return;
  }
	dg.curWin.document.getElementById('supplierSpId').value = id;
	dg.curWin.document.getElementById('supplier').value =name;
	dg.curWin.document.getElementById('bindStatus').value ='1';
	dg.curWin.initContractData(id);
	closewindow();
}

</script>
<title>B网络营销系统-采购管理-优购网</title>
</head>
<body>

<div class="container">
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class='curr'> <span>商家信息列表</span> </li> 
			</ul>
		</div>
		<div class="modify">
		<form action="${BasePath}/yitiansystem/merchants/businessorder/to_suppliersp4Contract.sc" method="post" id="queryForm" name="queryForm">
		商家编号:<input type="text" name="supplierCode" id="supplierCode" value='<#if supplierCode??>${supplierCode!''}</#if>'>
		商家名称:<input type="text" name="supplier" id="supplier" value='<#if supplier??>${supplier!''}</#if>'>
		<input type="hidden" name="supplierType" id="supplierType" value='<#if supplierType??>${supplierType!''}</#if>'>
		<input type="hidden" name="invoiceFlag" id="invoiceFlag" value='<#if invoiceFlag??>${invoiceFlag!''}</#if>'>
		
		
		<input type="button" value="查询" onclick="serach();" class="btn-add-normal-4ft">
		<br/><br/>
		</form>
		<table class="list_table" cellspacing="0" cellpadding="0" border="0">
					<thead>
					<tr>
					  <th style="text-align: center;">操作</th>
					  <th style="text-align: center;">商家编号</th>
					  <th style="text-align: center;">商家名称</th>
					</tr>
					</thead>
				<tbody>
					<#if pageFinder??&&pageFinder.data??&&pageFinder.data?size!=0>
                    	<#list pageFinder.data as item >
					    <tr>
						  <td class="ft-cl-r" style="text-align: center;"><input type="radio" name="ra" id="ra"  onclick="return checkVlale('${item['id']!""}','${item['supplier']!""}');"></td>
						  <td class="ft-cl-r" style="text-align: center;" >${item['supplierCode']!''}</td>
						  <td class="ft-cl-r" style="text-align: center;">${item['supplier']!''}</td>
						</tr>
					  </#list>
					<#else>
                    	<tr>
                    	<td colspan="10" style="text-align:center">
                    	抱歉，没有您要找的数据 
                        </td>
                        </tr>
                    </#if>	
					</tbody>
				</table>
		
		</div>
		<div class="bottom clearfix">
	  	<#if pageFinder ??><#import "../../common.ftl" as page>
	  		<@page.queryForm formId="queryForm"/></#if>
	  	</div>
	
	</div>
</div>

</body>
</html>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script> 
<script type="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>