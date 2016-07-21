function doQueryDetail() {
	var mainId = document.getElementById("mainId").value;
	document.getElementById('myIframe').src = doQueryDetailUrl + "?mainId="
			+ mainId;
}
function goback() {
	window.location.href = doQueryUrl;
}

function doQueryAllocationOutVoList() {
	var mainId = document.getElementById("mainId").value;
	window.location.href = doQueryAllocationOutVoListUrl + "?mainId=" + mainId;
}
function doConfirmIn() {
	var mainId = document.getElementById("mainId").value;
	if (mainId == "") {
		return;
	}
	$.ajax({
		type : "POST",
		url : doCheckOutUrl,
		data : {
			"mainId" : mainId
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == true) {
				if (!confirm('确认入库，将增加调入仓虚拟库存，请确认是否继续？')) {
					return;
				}
				$("#doConfirmInBtn").removeAttr('onclick');
				$.ajax({
					type : "POST",
					url : doConfirmInUrl,
					data : {
						"mainId" : mainId
					},
					dataType : "json",
					success : function(resultMsg) {
						if (resultMsg.success == true) {
							alert(resultMsg.msg);
							goback();
						} else {
							alert(resultMsg.msg);
						}
					}
				});
			} else {
				alert(resultMsg.msg);
			}
		}
	});
}

// 导出
function doExportApply() {
	var exportForm = document.getElementById("exportForm");
	exportForm.action = doExportApplyUrl;
	exportForm.submit();
}