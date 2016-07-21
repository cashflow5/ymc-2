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
	$('#eInTime').calendar({
		format : 'yyyy-MM-dd',
		targetFormat : 'yyyy-MM-dd'
	});
});

function doQuery() {
	if (document.getElementById("bTime").value == ''
			|| document.getElementById("eTime").value == '') {
		alert('请选择时间范围！');
		return;
	}
	var bTime = document.getElementById("bTime").value;
	var bArr = bTime.split("-");
	var bDate = new Date(bArr[0], bArr[1], bArr[2]);

	var eTime = document.getElementById("eTime").value;
	var eArr = eTime.split("-");
	var eDate = new Date(eArr[0], eArr[1], eArr[2]);

	var subTime = eDate.getTime() - bDate.getTime(); // 时间差的毫秒数
	// 计算出相差天数
	var subDays = Math.floor(subTime / (24 * 3600 * 1000));
	if (subDays > 31) {
		alert('请选择一个月范围内！');
		return;
	}
	var queryForm = document.getElementById("queryForm");
	queryForm.action = doQueryUrl;
	queryForm.submit();
}

function doExport() {
	if (document.getElementById("bTime").value == ''
			|| document.getElementById("eTime").value == '') {
		alert('请选择时间范围！');
		return;
	}
	var bTime = document.getElementById("bTime").value;
	var bArr = bTime.split("-");
	var bDate = new Date(bArr[0], bArr[1], bArr[2]);

	var eTime = document.getElementById("eTime").value;
	var eArr = eTime.split("-");
	var eDate = new Date(eArr[0], eArr[1], eArr[2]);

	var subTime = eDate.getTime() - bDate.getTime(); // 时间差的毫秒数
	// 计算出相差天数
	var subDays = Math.floor(subTime / (24 * 3600 * 1000));
	if (subDays > 31) {
		alert('请选择一个月范围内！');
		return;
	}
	var queryForm = document.getElementById("queryForm");
	queryForm.action = doExportUrl;
	queryForm.submit();
	queryForm.action = doQueryUrl;
}
