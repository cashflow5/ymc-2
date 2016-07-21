$(document).ready(function() {
			$("#checkall").click(function() {
						if (this.checked) {
							$("input[name='idCheckBox']").each(function() {
										this.checked = true;
									});
						} else {
							$("input[name='idCheckBox']").each(function() {
										this.checked = false;
									});
						}
					});
		});

// 审核出库单
function doCheckRegionOut(outCode) {
	if (outCode == '') {
		return;
	}
	if (confirm("确定审核出库单？")) {
		$.ajax({
					type : "POST",
					url : doCheckRegionOutUrl,
					data : {
						"outCode" : outCode
					},
					dataType : "json",
					success : function(resultMsg) {
						if (resultMsg.success == "true") {
							alert(resultMsg.msg);
							window.location.href = 'queryotheroutstores.sc';
						} else {
							alert(resultMsg.msg);
						}
					}
				});
	};
}
// 审核出库单(只减库存)
function checkRegionOutForCutQuantity(outCode) {
	if (outCode == '') {
		return;
	}
	if (confirm("确定审核出库单？")) {
		$.ajax({
					type : "POST",
					url : doCheckRegionOutForCutQuantityUrl,
					data : {
						"outCode" : outCode
					},
					dataType : "json",
					success : function(resultMsg) {
						if (resultMsg.success == "true") {
							alert(resultMsg.msg);
							window.location.href = 'queryotheroutstores.sc';
						} else {
							alert(resultMsg.msg);
						}
					}
				});
	};
}
// 减预占
function cutPreQuantity(outCode) {
	if (outCode == '') {
		return;
	}
	if (confirm("确定清预占？")) {
		$.ajax({
					type : "POST",
					url : doCutPreQuantityUrl,
					data : {
						"outCode" : outCode
					},
					dataType : "json",
					success : function(resultMsg) {
						if (resultMsg.success == "true") {
							alert(resultMsg.msg);
							window.location.href = 'queryotheroutstores.sc';
						} else {
							alert(resultMsg.msg);
						}
					}
				});
	};
}
// 删除出库单明细
function doRemoveRegionOutDetail(regionOutId) {
	if (regionOutId == '') {
		return;
	}
	var idSel = getCheckBoxs('idCheckBox');
	if (idSel.length == 0) {
		alert("请选择要删除的明细!");
		return;
	}
	var regionOutDetailIds = "";
	for (var i = 0; i < idSel.length; i++) {
		regionOutDetailIds += idSel[i].value + ',';
	}
	if (confirm("确定删除？")) {
		$.ajax({
			type : "POST",
			url : doRemoveRegionOutDetailUrl,
			data : {
				"regionOutId" : regionOutId,
				"regionOutDetailIds" : regionOutDetailIds
			},
			dataType : "json",
			success : function(resultMsg) {
				if (resultMsg.success == "true") {
					alert(resultMsg.msg);
					if (resultMsg.reCode == '110') {
						window.location.href = 'queryotheroutstores.sc';
					} else {
						window.location.href = 'tootheroutstoredetail.sc?storageId='
								+ regionOutId;
					}
				} else {
					alert(resultMsg.msg);
				}
			}
		});
	};
}