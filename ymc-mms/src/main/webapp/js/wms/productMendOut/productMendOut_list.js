function toAdd() {
	window.location.href = toAddUrl;
}
function doQuery() {
	var queryForm = document.getElementById("queryForm");
	queryForm.action = doQueryUrl
	queryForm.submit();
}

$(document).ready(function() {
	$('#bTime').calendar({
		maxDate : '#eTime',
		format : 'yyyy-MM-dd',
		targetFormat : 'yyyy-MM-dd'
	});
	$('#eTime').calendar({
		minDate : '#bTime',
		format : 'yyyy-MM-dd',
		targetFormat : 'yyyy-MM-dd'
	});
});
// 导出
function doExportProductMendOut() {
	var exportForm = document.getElementById("queryForm");
	exportForm.action = doExportProductMendOutUrl;
	exportForm.submit();
	// -_-!!
	exportForm.action = doQueryUrl;
}