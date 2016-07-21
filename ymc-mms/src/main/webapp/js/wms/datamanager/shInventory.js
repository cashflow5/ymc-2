function doSynSHInventory() {
	if (!confirm("该操作将会立即触发库存同步功能，您确定要继续吗？")) {
		return;
	}
	document.getElementById("doSynSHInventoryBtn").disabled = true;
	$.ajax({
		type : "POST",
		url : doSynSHInventoryUrl,
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == "true") {
				document.getElementById("doSynSHInventoryBtn").disabled = false;
				alert(resultMsg.msg);
			} else {
				document.getElementById("doSynSHInventoryBtn").disabled = false;
				alert(resultMsg.msg);
			}
		}
	});
}