$(document).ready(function() {
	$('#importDate').calendar({
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
	$("#doExportBtn").removeAttr('onclick');
	var queryForm = document.getElementById("queryForm");
	queryForm.action = doExportUrl;
	queryForm.submit();
	queryForm.action = doQueryUrl;
}
