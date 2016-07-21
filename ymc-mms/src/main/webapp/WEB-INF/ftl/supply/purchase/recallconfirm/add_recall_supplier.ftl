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
function confrimSupplier(){
	
   var supplierCode = $('#supplierCode').val();
   var supplierName = $('#supplierName').val();
   if(supplierCode == null || supplierCode =='' || supplierName == null || supplierName =='' ){
   		alert("请先选择供应商！");
   		return;
   }
   
   $('#supplierCodeSel').val(supplierCode);
   $('#supplierNameSel').val(supplierName);
		
   supperAction.submit();
   
}

//选择供应商
function tosupper(){
   openwindow('${BasePath}/supply/manage/recall/to_suppliersp.sc',800,500,'选择供应商',1);
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
				<li class='curr'> <span>召回确认-选择供应商</span> </li>
			</ul>
		</div>
		<div class="modify">
				<!--普通搜索内容开始-->
			<div class="add_detail_box">
				<form action="${BasePath}/supply/manage/recall/toAddRecallConfirm.sc" method="post" id="supperAction" name="supperAction">
					<input type="hidden" name="supplierCodeSel" id="supplierCodeSel" value="" />
        			<input type="hidden" name="supplierNameSel" id="supplierNameSel" value="" />
					<div>
						<br/>
						<p>	
							<input type="button" value="确定供应商" onclick="confrimSupplier();">
						</p>
						<br/>
						<p>	
							<span>供应商：</span>
							<input type="hidden" name="supplierCode" id="supplierCode" >
			                <input type="text" name="supplierName" id="supplierName" disabled="true" >
			                <input type="button" onclick="tosupper();" class="btn-add-normal-4ft" id="selectSuppilder" value="选择供应商">
				   		</p>
					</div>
				</form>
			</div>	
		</div>
	</div>
</div>
</body>
</html>
