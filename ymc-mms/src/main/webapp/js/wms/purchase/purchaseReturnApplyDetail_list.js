$(document).ready(function() {
	$("#checkall").click(function() {
		if (this.checked) {
			$("input[name='detailCB']").each(function() {
				this.checked = true;
			});
		} else {
			$("input[name='detailCB']").each(function() {
				this.checked = false;
			});
		}
	});
});
// 保存
function doSave() {
	var mainId = parent.document.getElementById("mainId").value;
	if (mainId == "") {
		alert("请添加明细！");
		return;
	}
	var table = document.getElementById("subTable");
	var details = "";
	for ( var i = 1; i < table.rows.length; i++) {
		var rowCells = table.rows[i].cells;
		var quantityInputs = rowCells[8].getElementsByTagName("input");
		var quantityInput = quantityInputs[0];
		var detailId = quantityInput.id;
		var quantity = quantityInput.value;

		if (!/^[1-9]\d*|0$/.test(quantity)) {
			alert('请输入正确的格式！');
			return;
		}

		details = details + detailId + "_" + quantity + ";";
	}
	var mainId = parent.document.getElementById("mainId").value;
	var remark = parent.document.getElementById("remark").value;

	$.ajax({
		type : "POST",
		url : doSaveUrl,
		data : {
			"mainId" : mainId,
			"remark" : remark,
			"details" : details
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == true) {
				alert(resultMsg.msg);
			} else {
				alert(resultMsg.msg);
			}
		}
	});
}
function doSubmit() {

	var table = document.getElementById("subTable");
	var details = "";
	for ( var i = 1; i < table.rows.length; i++) {
		var rowCells = table.rows[i].cells;
		var quantityInputs = rowCells[8].getElementsByTagName("input");
		var quantityInput = quantityInputs[0];
		var detailId = quantityInput.id;
		var quantity = quantityInput.value;

		if (!/^[1-9]\d*|0$/.test(quantity)) {
			alert('请输入正确的格式！');
			return;
		}

		details = details + detailId + "_" + quantity + ";";
	}
	var mainId = parent.document.getElementById("mainId").value;
	var remark = parent.document.getElementById("remark").value;
	if (confirm("确认提交！") == false) {
		return;
	}
	$.ajax({
		type : "POST",
		url : doSubmitUrl,
		data : {
			"mainId" : mainId,
			"remark" : remark,
			"details" : details
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == true) {
				alert(resultMsg.msg);
				parent.goback();
			} else {
				alert(resultMsg.msg);
			}
		}
	});
}

function doRemoveDetail() {
	var detailSel = getCheckBoxs('detailCB');
	if (detailSel.length == 0) {
		alert("请选择明细!");
		return;
	}
	var flag = ",";
	var detailIds = "";
	for ( var i = 0; i < detailSel.length; i++) {
		if (i == detailSel.length - 1) {
			flag = "";
		}
		var detailId = detailSel[i].value;
		detailIds = detailIds + detailId + flag;
	}
	if (confirm("确认删除！") == false) {
		return;
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
				parent.queryDetail();
			} else {
				alert(resultMsg.msg);
			}
		}
	});
}