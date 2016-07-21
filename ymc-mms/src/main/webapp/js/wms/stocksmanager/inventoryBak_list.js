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
function doQuery() {
	var queryForm = document.getElementById("queryForm");
	queryForm.action = doQueryUrl;
	queryForm.submit();
}

function doExport() {
	if (!confirm("确定导出？")) {
		return;
	}
	$("#doExportBtn").removeAttr('onclick');
	var queryForm = document.getElementById("queryForm");
	queryForm.action = doExportUrl;
	queryForm.submit();
	// -_-!!
	queryForm.action = doQueryUrl;
}