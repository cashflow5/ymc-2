function goback() {
	window.location.href = gobackUrl;
}
// 导出
function doExportForWeekDetail() {
	doWMSExport("pageForm", doExportForWeekDetailUrl);
}