$(document).ready(function() {
	$('#importDate').calendar({
		format : 'yyyy-MM-dd',
		targetFormat : 'yyyy-MM-dd'
	});
});

function toRealImport() {
	var importDate = document.getElementById("importDate").value;
	if ("" == importDate) {
		alert("请选择历史库存日期！");
		return;
	}
	openwindow(toRealImportUrl, 450, 100, '导入');
}
