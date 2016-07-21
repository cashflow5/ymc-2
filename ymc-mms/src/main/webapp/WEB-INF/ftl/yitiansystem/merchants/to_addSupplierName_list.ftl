<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,优购网,B网络营销系统,采购管理" />
<meta name="Description" content=" , ,B网络营销系统-采购管理" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/jquery-1.3.2.min.js"></script>
<script type="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/js.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/order.js"></script>
<script type="text/javascript">
function serach(){
   documet.queryForm.submit();
}

//选择商家名称传给父窗口
function checkVlale(code,name,id){
  	if(name==""){
    	alert("商家名称为空!");
    	return;
  	}
	dg.curWin.document.getElementById('supplierName').value = name;
	dg.curWin.document.getElementById('supplierCode').value = code;
	dg.curWin.document.getElementById('supplierId').value = id;
	if(dg.curWin.parentMethod != null){
		dg.curWin.parentMethod();
	}
	closewindow();
}
</script>
<title>B网络营销系统-采购管理-优购网</title>
</head>
<body>
<div class="container">
	<div class="list_content">
		<div class="top clearfix">
<!--			<ul class="tab">
				<li class='curr'> <span>商家查询</span> </li>
			</ul>
-->		<div class="modify">
	    <form action="${BasePath}/yitiansystem/merchants/businessorder/to_addSupplierName_list.sc" method="post" id="queryForm" name="queryForm">
			<div>
				商家名称:<input type="text" name="supplier" id="supplier" value="${(merchantsVo.supplier)!""}">
				商家编号:<input type="text" name="supplierCode" id="supplierCode" value="${(merchantsVo.supplierCode)!""}">
				<input type="submit" value="查询" onclick="serach();" class="btn-add-normal">
			</div>
			<br/>
		</form>
		<table class="list_table">
				<thead>
					<tr>
					  <th width="30">操作</th>
					  <th>商家名称</th>
					  <th>商家编码</th>
					</tr>
				</thead>
				<tbody>
					<#if pageFinder??&&pageFinder.data?? >
                    	<#list pageFinder.data as item >
					    <tr>
						  <td><input type="radio" name="ra" id="ra" onclick="return checkVlale('${item.supplierCode!""}','${item.supplier!""}','${item.id}');"></td>
						  <td class="ft-cl-r">${item.supplier!''}</td>
						  <td class="ft-cl-r" >${item.supplierCode!''}</td>
						</tr>
					  	</#list>
					</#if>
				</tbody>
		</table>
    </div>
    <div class="bottom clearfix">
	  	<#if pageFinder ??><#import "../../common.ftl" as page>
	  		<@page.queryForm formId="queryForm"/></#if>
	  	</div>
      	<div class="blank20"></div>
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
