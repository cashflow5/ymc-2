<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,优购网,B网络营销系统,采购管理" />
<meta name="Description" content=" , ,B网络营销系统-采购管理" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<script src="${BasePath}/js/supply/supplier.js" type="text/javascript"></script>

<script type="text/javascript">
function serach(){
   documet.supperAction.submit();
}

alertConfirm(content,function(){alertConfirmaaa();})

//确认对话框
function alertConfirmaaa(content,yesFn)
{
	ygdg.dialog.confirm(content, yesFn, function(){checkVlale();})
}

//选择供应商值传给父窗口
function checkVlale(id,name,setOfBooksName,setOfBooksCode){
  if(id==""){
    alert("供应商名称为空!");
    return;
  }
	dg.curWin.document.getElementById('supplierId').value = id;
	dg.curWin.document.getElementById('supplierName').value =name;
	dg.curWin.document.getElementById('setOfBooksName').innerHTML ="成本账套名称:"+setOfBooksName;
	dg.curWin.document.getElementById('setOfBooksCode').value =setOfBooksCode;
	closewindow();
}
</script>
<#include "../../../yitiansystem/yitiansystem-include.ftl">
<title>B网络营销系统-采购管理-优购网</title>
</head>
<body>
<div class="container">
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class='curr'> <span>添加供应商</span> </li>
			</ul>
		</div>
		<div class="modify">
		<form action="${BasePath}/supply/manage/purchase/to_suppliersp.sc" method="post" id="supperAction" name="supperAction">
		<div>供应商编号:<input type="text" name="supplierCode" id="supplierCode" value="<#if supplier??&&supplier.supplierCode??>${supplier.supplierCode}</#if>">
		供应商名称:<input type="text" name="supplier" id="supplier" value="<#if supplier??&&supplier.supplier??>${supplier.supplier}</#if>">
		<input type="submit" value="查询" onclick="serach();" class="btn-add-normal"></div>
			<br/>
		</form>
			<table class="list_table" cellspacing="0" cellpadding="0" border="0">
					<tbody>
					<thead>
					<tr>
					  <th>操作<input type="radio" name="ra" style="display:none;"></th>
					  <th>供应商编号<th>
					  <th>供应商名称<th>
					  <th>供应商英文名<th>	
					  <th>成本账套名称<th>
					</tr>
					</thead>
					<#if pageFinder?? && pageFinder.data??>
					  <#list pageFinder.data as item>
					    <tr>
						  <td><input type="radio" name="ra" id="ra"  onclick="return checkVlale('${item.id!""}','${item.supplier!""}','${item.setOfBooksName!""}','${item.setOfBooksCode!""}');"><td>
						  <td>${item.supplierCode!''}<td>
						  <td>${item.supplier!''}<td>
						  <td>${item.englishName!''}<td>	
						  <td>${item.setOfBooksName!''}<td>
					</tr>
					  </#list>
					</#if>
					</tbody>
				</table>
			</div>
			<!--分页start-->
				<div class="bottom clearfix"> <#if pageFinder ??>
					<#import "../../../common.ftl" as page>
					<@page.queryForm formId="supperAction" />
					</#if> 
			    </div>
			<!--分页end--> 
		
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