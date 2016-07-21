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
	$('#qaBTime').calendar({
		maxDate : '#qaETime',
		format : 'yyyy-MM-dd',
		targetFormat : 'yyyy-MM-dd'
	});
	$('#qaETime').calendar({
		minDate : '#qaBTime',
		format : 'yyyy-MM-dd',
		targetFormat : 'yyyy-MM-dd'
	});
});

function doQuery() {
	if($('#qaBTime').val()==null||$('#qaBTime').val()==''||$('#qaETime').val()==null||$('#qaETime').val()==''){
			alert("请输入质检的起止日期！");
			return;
	}
	var queryForm = document.getElementById("queryForm");
	queryForm.action = doQueryUrl;
	queryForm.submit();
}
