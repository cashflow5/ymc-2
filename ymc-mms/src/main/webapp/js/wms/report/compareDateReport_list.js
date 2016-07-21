$(document).ready(function() {
	$('#compareDate').calendar({
		format : 'yyyy-MM-dd',
		targetFormat : 'yyyy-MM-dd'
	});
	var tempIsDiff = document.getElementById("tempIsDiff").value;
	var isDiffBox = document.getElementsByName("isDiff");
	if (tempIsDiff == "1") {
		isDiffBox[0].checked = true;
	}
});

function doQuery() {
	if (document.getElementById("compareDate").value == '') {
		alert('请选择日期！');
		return;
	}
	var queryForm = document.getElementById("queryForm");
	queryForm.action = doQueryUrl;
	queryForm.submit();
}

function doExport() {
	if (document.getElementById("compareDate").value == '') {
		alert('请选择日期！');
		return;
	}
	if (!confirm("确定导出？")) {
		return;
	}
	doWMSExport("queryForm", doExportUrl);
}
