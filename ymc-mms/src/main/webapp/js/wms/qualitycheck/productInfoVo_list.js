function doQuery() {
	var queryForm = document.getElementById("queryForm");
	queryForm.action = doQueryUrl;
	queryForm.submit();
}
function selProduct(barcode) {
	dg.curWin.document.getElementById("barcode").value = barcode;
	dg.curWin.document.getElementById("barcode").focus();
	closewindow();
}
