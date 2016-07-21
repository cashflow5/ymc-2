function getNewLogicCompanyInfo() {
	$.ajax({
		type : "POST",
		url : getNewLogicCompanyInfoUrl,
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == "true") {
				alert(resultMsg.msg);
			} else {
				alert(resultMsg.msg);
			}
		}
	});
}
function doBackupInventory() {
	$.ajax({
		type : "POST",
		url : doBackupInventoryUrl,
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == "true") {
				alert(resultMsg.msg);
			} else {
				alert(resultMsg.msg);
			}
		}
	});
}
function doApportion() {
	$.ajax({
		type : "POST",
		url : doApportionUrl,
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == "true") {
				alert(resultMsg.msg);
			} else {
				alert(resultMsg.msg);
			}
		}
	});
}