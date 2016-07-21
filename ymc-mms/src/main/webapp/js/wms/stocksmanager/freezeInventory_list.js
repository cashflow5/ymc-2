function doQuery() {
	var queryForm = document.getElementById("queryForm");
	queryForm.action = doQueryUrl;
	queryForm.submit();
}
function doAmountExport() {
	openwindow(doAmountExportUrl, 250, 350, '汇总导出');
}
function doExportSel() {
	var orderSel = getCheckBoxs('exportId');
	if (orderSel.length == 0) {
		alert("请选择仓库!");
		return;
	}
	var exportForm = document.getElementById("exportForm");
	exportForm.action = exportUrl;
	exportForm.submit();
}

function doExportCSV() {
	var virtualId = document.getElementById("virtualId").value;
	if (virtualId == '') {
		alert('请选择仓库！');
		return;
	}
	if (!confirm("确定导出？")) {
		return;
	}
	var queryForm = document.getElementById("queryForm");
	queryForm.action = doExportCSVUrl;
	queryForm.submit();
	// -_-!!
	queryForm.action = doQueryUrl;
}
function doExport() {
	if (!confirm("确定导出？")) {
		return;
	}
	doWMSExport("queryForm", doExportUrl);
}