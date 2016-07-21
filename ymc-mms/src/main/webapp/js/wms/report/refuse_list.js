function doQuery() {
	var queryForm = document.getElementById("queryForm");
	queryForm.action = queryUrl;
	queryForm.submit();
}
// 导出
function doExportRefuse() {
	$("#doExportRefuseBtn").removeAttr('onclick');

	if (!confirm("确定导出？")) {
		return;
	}
	doWMSExport("queryForm", doExportRefuseReportUrl);

}