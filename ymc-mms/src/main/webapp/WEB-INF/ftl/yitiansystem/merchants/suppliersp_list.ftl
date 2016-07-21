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
  var supplierCode=$("#supplierCode").val();
   $("#supplierCode").val(supplierCode);
   documet.supperAction.submit();
}

//选择供应商值传给父窗口
function checkVlale(id,name){
  if(id==""){
    alert("供应商名称为空!");
    return;
  }
	dg.curWin.document.getElementById('supplierSpId').value = id;
	dg.curWin.document.getElementById('suplierName').value =name;
	closewindow();
}
</script>
<title>B网络营销系统-采购管理-优购网</title>
</head>
<body>

<form action="${BasePath}/yitiansystem/merchants/businessorder/to_suppliersp.sc" method="post" id="supperAction" name="supperAction">
<div class="container">
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class='curr'> <span>商家信息列表</span> </li>
			</ul>
		</div>
		<div class="modify">
		<div>商家编号:<input type="text" name="supplierCode" id="supplierCode" <#if supplierCode??>${supplierCode!''}</#if>>
		<input type="submit" value="查询" onclick="serach();" class="btn-add-normal-4ft"></div>
			<br/>
			<table class="list_table" cellspacing="0" cellpadding="0" border="0">
					<tbody>
					<thead>
					<tr>
					  <th>操作<input type="radio" name="ra" style="display:none;"></th>
					  <th>商家编号<th>
					  <th>商家名称<th>
					  <th>商家英文名<th>	
					</tr>
					</thead>
					<#if suppliersps??>
					  <#list suppliersps as item>
					    <tr>
						  <td><input type="radio" name="ra" id="ra"  onclick="return checkVlale('${item.id!""}','${item.supplier!""}');"><td>
						  <td>${item.supplierCode!''}<td>
						  <td>${item.supplier!''}<td>
						  <td>${item.englishName!''}<td>	
					</tr>
					  </#list>
					</#if>
					</tbody>
				</table>
			</form>
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