<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/wms/css.css" />
<link rev="stylesheet" rel="stylesheet"  type="text/css" href="${BasePath}/css/wms/style.css"/>
<script type="text/javascript" src="${BasePath}/js/wms/stocksmanager/orderOutStore_import.js"></script>
<#include "../../supply_include.ftl">
<title>优购商城--商家后台</title>
</head><body>

  <form method="post" enctype="multipart/form-data" name="uploadFileForm" id="uploadFileForm">
  <!-- 
    <select id="importWarehouseId" name="importWarehouseId">
      <option value="">请选择仓库</option>
      <#if importWarehouses??> 
      <#list importWarehouses as item >
      <option value="<#if item.id??>${item.id}</#if>"> <#if item.warehouseName??>${item.warehouseName}</#if></option>
      </#list> 
      </#if>
    </select><br/><br/>
     -->
    <input id="inventory" name="inventory" size="30" type="file" />      *.csv文件<br/><br/>
    <input type="button" class="wms-seach-btn" value="确定"  onClick="doExport();"/>
    <div id="uploadMsg"></div>
  </form>

</body>

<script type="text/javascript">

	var result = '${result?default("")}';
	if(result == "true"){
		alert("数据全部导入成功！");
	}
	if(result == "databaseException"){
		alert("数据导入失败，数据库连接异常，请稍候再试...");
	}

	
function doExport() {
//	var importWarehouseId = document.getElementById("importWarehouseId").value;
//	if (importWarehouseId == "") {
//		alert("请选择仓库！");
//		return;
//	}
	var uploadFile = document.getElementById("inventory");
	if (uploadFile.value == "") {
		alert("请选择上传文件！");
		return;
	}
	var uploadFileName = uploadFile.value;
	var subName = uploadFileName.substring(uploadFileName.length - 3,
			uploadFileName.length);
	if (subName != "csv") {
		alert("请选择csv文件！");
		return;
	}
	if (!confirm("确定导入？")) {
		return;
	}
	//document.getElementById("uploadMsg").innerHTML = "正在添加文件到服务器中，请稍候……<br />有可能因为网络问题，出现程序长时间无响应，请点击“<a href='?'><font color='red'>取消</font></a>”重新上传文件";

	var uploadFileForm = document.getElementById("uploadFileForm");
	uploadFileForm.action = "doimportbyinventory.sc";
	uploadFileForm.submit();
}
</script>	 

</html>
