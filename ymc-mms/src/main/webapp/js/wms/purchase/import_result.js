function doConfirm() {
	dg.curWin.document.getElementById("mainId").value = document
			.getElementById("mainId").value;
	dg.curWin.document.getElementById("warehouseId").disabled = true;
	dg.curWin.document.getElementById("supplierId").disabled = true;
	dg.curWin.queryDetail();
	closewindow();
}

$(document).ready(
		function() {
			dg.curWin.document.getElementById("mainId").value = document
					.getElementById("mainId").value;
			dg.curWin.document.getElementById("warehouseId").disabled = true;
			dg.curWin.document.getElementById("supplierId").disabled = true;
			dg.curWin.queryDetail();
		});