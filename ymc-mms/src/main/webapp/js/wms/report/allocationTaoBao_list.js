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
	queryForm.action = queryUrl;
	queryForm.submit();
}
function doExport() {
	var exportForm = document.getElementById("queryForm");
	exportForm.action = exportUrl;
	exportForm.submit();
}
