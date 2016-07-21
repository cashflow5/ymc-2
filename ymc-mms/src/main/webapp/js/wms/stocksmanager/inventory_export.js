function doExportSel() {
	var orderSel = getCheckBoxs('exportId');
	if (orderSel.length == 0) {
		alert("请选择仓库!");
		return;
	}
	$("#doExportSel").removeAttr('onclick');
	doWMSExport("exportForm", exportUrl);
}