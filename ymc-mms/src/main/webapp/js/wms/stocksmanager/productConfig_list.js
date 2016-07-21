function doDelete(commodityCode, btnId) {
	document.getElementById(btnId).disabled = true;
	$.ajax({
				type : "POST",
				url : doDeleteUrl,
				data : {
					"commodityCode" : commodityCode

				},
				dataType : "json",
				success : function(resultMsg) {
					if (resultMsg.success == "true") {
						doQuery();
					} else {
						document.getElementById(btnId).disabled = false;
					}
				}
			});
}
