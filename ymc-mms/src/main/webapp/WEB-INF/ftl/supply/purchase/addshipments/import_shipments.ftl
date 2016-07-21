<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,优购网,B网络营销系统,商品管理" />
<meta name="Description" content=" , ,B网络营销系统-商品管理" />
<#include "../../../yitiansystem/yitiansystem-include.ftl">
<title>B网络营销系统-采购管理-优购网</title>
<script type="text/javascript">

//导入
function importAddshipments(){
	var file = $("#uploadFile").val();
	if(file!=""){
		file=file.substring(file.lastIndexOf('\\')+1);
	    var fileStr=file.substring(file.lastIndexOf('.')+1);
	    if(fileStr!='xls'&&fileStr!='xlsx'){
  		   alert("上传附件文件格式限制为.xls、.xlsx ");
  		   return false;
		}else {
			var form = document.getElementById("queryForm");
      		form.action = "${BasePath}/supply/purchase/addshipments/c_importShipmentsDetail.sc";
      		form.submit();
      		$("#importButton").attr("disabled", "disabled");
		}
   }else {
  	 alert("请选择要导入的xls文件!");
   }
}
</script>
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
				<li class='curr'> <span><a href="">导入供应商发货单</a></span> </li>
			</ul>
		</div>
		<!--当前位置end--> 
		<!--主体start-->
		<div id="modify" class="modify">
		<form method="post" id="queryForm" name="queryForm" encType="multipart/form-data">
		</br>
		<input type="hidden" name="shipmentId" id="shipmentId" value="<#if shipmentId??>${shipmentId!''}</#if>">
		<input type="hidden" name="shipmentsId" id="shipmentsId" value="<#if shipmentId??>${shipmentId!''}</#if>">
		<input type="hidden" name="purchaseCode" id="purchaseCode" value="<#if purchaseCode??>${purchaseCode!''}</#if>">
		<input type="hidden" name="purchaseId" id="purchaseId" value="<#if purchaseId??>${purchaseId!''}</#if>">
		<input name="uploadFile" type="file" id="uploadFile" />
		<input type="button" class="btn-add-normal" value="快速导入" onclick="importAddshipments()">
		</form>
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
