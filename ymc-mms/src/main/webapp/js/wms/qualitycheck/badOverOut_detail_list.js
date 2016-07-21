function doRemoveDetail(detailId) {
	$.ajax({
		type : "POST",
		url : doRemoveDetailUrl,
		data : {
			"detailId" : detailId
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == true) {
				parent.doQueryDetail();
			} else {
				alert(resultMsg.msg);
			}
		}
	});
}