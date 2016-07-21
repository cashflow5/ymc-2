function doConfigInventory() {
	if (!confirm("该操作将会立即触发库存划拨功能，您确定要继续吗？")) {
		return;
	}
	document.getElementById("doConfigBtn").disabled = true;
	$.ajax({
				type : "POST",
				url : doConfigInventoryUrl,
				dataType : "json",
				success : function(resultMsg) {
					if (resultMsg.success == "true") {
						document.getElementById("doConfigBtn").disabled = false;
						alert(resultMsg.msg);
					} else {
						document.getElementById("doConfigBtn").disabled = false;
						alert(resultMsg.msg);
					}
				}
			});
}