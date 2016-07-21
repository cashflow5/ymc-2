function doRemove(productMendBackDetailId) {
	$.ajax({
		type : "POST",
		url : doRemoveUrl,
		data : {
			"productMendBackDetailId" : productMendBackDetailId
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == "true") {
				parent.doQueryDetail();
			} else {
				alert(resultMsg.msg);
			}
		}
	});
}