function doRemove(productMendOutDetailId) {
	$.ajax({
				type : "POST",
				url : doRemoveUrl,
				data : {
					"productMendOutDetailId" : productMendOutDetailId
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