// 导出
function doExport() {
	var exportForm = document.getElementById("pageForm");
	exportForm.action = doExportUrl;
	exportForm.submit();
	// -_-!!
	exportForm.action = doQueryUrl;
}
