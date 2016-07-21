$(document).ready(function() {
	$("#checkall").click(function() {
		if (this.checked) {
			$("input[name='commodityCB']").each(function() {
				if (!this.disabled)
					this.checked = true;
			});
		} else {
			$("input[name='commodityCB']").each(function() {
				this.checked = false;
			});
		}
	});
});

function doRemoveDetail() {
	var checkedArray = getCheckBoxs("commodityCB");
	if (checkedArray.length == 0) {
		alert("请选择货品！");
		return;
	}
	if (!confirm("确定删除？")) {
		return;
	}
	var flag = ",";
	var detailIds = "";
	for ( var i = 0; i < checkedArray.length; i++) {
		if (i == checkedArray.length - 1) {
			flag = "";
		}
		var detailId = checkedArray[i].value;
		detailIds = detailIds + detailId + flag;
	}
	$.ajax({
		type : "POST",
		url : doRemoveDetailUrl,
		data : {
			"detailIds" : detailIds
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == true) {
				alert(resultMsg.msg);
				if (resultMsg.reCode == "ALL") {
					parent.goback();
				} else {
					parent.queryDetail();
				}
			} else {
				alert(resultMsg.msg);
			}
		}
	});
}