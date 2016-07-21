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
<#include "../../../yitiansystem/yitiansystem-include.ftl">
<script type="text/javascript" src="${BasePath}/js/supply/addSupplierFinanceinfo.js" ></script>
<title>网络营销系统-采购管理-优购网</title>
</head><body>
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
				<li class='curr'> <span>银行账号</span> </li>
				<li> <span><a href="querysupplierbasedetail.sc?id=${supplier.id}">基本信息</a></span> </li>
				<li> <span><a href="querysuppliercontactdetail.sc?supplier=${supplier.id}">联系人列表</a></span> </li>
				<li> <span><a href="querysupplierhistorydetail.sc?supplier=${supplier.id}">更新历史</a></span> </li>
			</ul>
		</div>
		<!--当前位置end--> 
		
		<!--主体start-->
		<div id="modify" class="modify">
			<form action="updateSupplierFinance.sc" method="post" id="submitForm" name="submitForm">
				<input type="hidden" name="id" value="${supplier.id}"/>
				<input type="hidden" name="supplierCode" id="supplierCode" value="${supplier.supplierCode!''}"/>
				<table class="com_modi_table">
					<tr>
						<th> <span class="star">*</span>开户行：</span> </th>
						<td>
							${supplier.bank?default("")} </td>
					</tr>
					<tr>
						<th> <span class="star">*</span>支行：</span> </th>
						<td>
						${supplier.subBank?default("")} </td>
					</tr>
					<tr>
						<th> <span class="star">*</span>开户人：</span> </th>
						<td>
							${supplier.contact?default("")} </td>
					</tr>
					<tr>
						<th> <span class="star">*</span>账号：</span> </th>
						<td>
							${supplier.account?default("")}</td>
					</tr>
					<tr>
						<th> <span class="star">*</span>税号： </th>
						<td>
							${supplier.dutyCode?default("")} </td>
					</tr>
					<tr>
						<th> <span class="star">*</span>合作期限： </th>
							<td><#if supplier.conTime??>${supplier.conTime?c}<#else>&nbsp;</#if>&nbsp;</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>
</body>
</html>
