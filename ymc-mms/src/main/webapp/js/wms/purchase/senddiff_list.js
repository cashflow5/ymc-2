$(document).ready(function() {
	$('#operatorBTime').calendar({
		maxDate : '#operatorETime',
		format : 'yyyy-MM-dd',
		targetFormat : 'yyyy-MM-dd'
	});
	$('#operatorETime').calendar({
		minDate : '#operatorBTime',
		format : 'yyyy-MM-dd',
		targetFormat : 'yyyy-MM-dd'
	});
	$('#approveBTime').calendar({
		maxDate : '#approveETime',
		format : 'yyyy-MM-dd',
		targetFormat : 'yyyy-MM-dd'
	});
	$('#approveETime').calendar({
		minDate : '#approveBTime',
		format : 'yyyy-MM-dd',
		targetFormat : 'yyyy-MM-dd'
	});
	$('#sendBTime').calendar({
		maxDate : '#sendETime',
		format : 'yyyy-MM-dd',
		targetFormat : 'yyyy-MM-dd'
	});
	$('#sendETime').calendar({
		minDate : '#sendBTime',
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
	doWMSExport("queryForm", doExportUrl);
}