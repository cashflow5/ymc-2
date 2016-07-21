function doImport() {
	var uploadFile = document.getElementById("uploadFile").value;
	if (uploadFile == '') {
		alert("请选择上传文件！");
		return;
	}

	var importForm = document.getElementById("importForm");
	importForm.action = doImportUrl;
	importForm.submit();

	$("#img").show();
}

$(document).ready(function() {
	var mainId = dg.curWin.document.getElementById("mainId").value;
	var applyCode = dg.curWin.document.getElementById("applyCode").value;
	var warehouseId = dg.curWin.document.getElementById("warehouseId").value;
	var supplierId = dg.curWin.document.getElementById("supplierId").value;
	var operateTime = dg.curWin.document.getElementById("operateTime").value;
	var remark = dg.curWin.document.getElementById("remark").value;
	var type = dg.curWin.getRadioVal("type");

	document.getElementById("mainId").value = mainId;
	document.getElementById("applyCode").value = applyCode;
	document.getElementById("warehouseId").value = warehouseId;
	document.getElementById("supplierId").value = supplierId;
	document.getElementById("operateTime").value = operateTime;
	document.getElementById("remark").value = remark;
	document.getElementById("type").value = type;
});