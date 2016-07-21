function changeWarehouse(w) {
	var strsel = w.options[w.selectedIndex].text;

	document.getElementById("warehouseFont").innerHTML = strsel;

	if (w.value == '') {
		document.getElementById("inBtn").disabled = true;
		document.getElementById("outBtn").disabled = true;
	} else {
		document.getElementById("inBtn").disabled = false;
		document.getElementById("outBtn").disabled = false;
		
		document.getElementById("warehouseCode").disabled = true;
	}
}

function toImportForIn() {
	var warehouseCode = document.getElementById("warehouseCode").value;
	if (warehouseCode == '') {
		alert("请选择操作仓库！");
		return;
	}
	document.getElementById("inBtn").disabled = true;

	openwindow(toImportUrl + '?flag=IN&warehouseCode=' + warehouseCode, 500,
			170, '导入excel进行库存划入');
}

function toImportForOut() {
	var warehouseCode = document.getElementById("warehouseCode").value;
	if (warehouseCode == '') {
		alert("请选择操作仓库！");
		return;
	}
	document.getElementById("outBtn").disabled = true;

	openwindow(toImportUrl + '?flag=OUT&warehouseCode=' + warehouseCode, 500,
			170, '导入excel进行库存划出');
}
