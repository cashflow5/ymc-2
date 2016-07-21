function goback() {
	window.location.href = gobackUrl;
}
//导出
function doExportForDayDetail() {
	doWMSExport("pageForm", doExportForDayDetailUrl);
}