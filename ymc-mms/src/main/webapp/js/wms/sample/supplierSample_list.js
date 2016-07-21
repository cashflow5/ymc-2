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

function toAdd() {
	window.location.href = toAddUrl;
}
