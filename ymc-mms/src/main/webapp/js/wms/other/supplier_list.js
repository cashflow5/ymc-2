function doQuery() {
	var queryForm = document.getElementById("queryForm");
	queryForm.action = doQueryUrl;
	queryForm.submit();
}
function selSupplier(supplierId, supplierName) {
	var hiddenTextId = document.getElementById("hiddenTextId").value;
	var textId = document.getElementById("textId").value;

	dg.curWin.document.getElementById(hiddenTextId).value = supplierId;
	dg.curWin.document.getElementById(textId).value = supplierName;

	closewindow();
}
