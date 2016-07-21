function doQuery() {
	var queryForm = document.getElementById("queryForm");
	queryForm.action = doQueryUrl;
	queryForm.submit();
}

function doAddDetail() {
	var mainId = dg.curWin.document.getElementById("mainId").value;
	var applyCode = dg.curWin.document.getElementById("applyCode").value;
	var warehouseId = document.getElementById("warehouseId").value;
	var supplierId = dg.curWin.document.getElementById("supplierId").value;
	var type = dg.curWin.getRadioVal("type");
	var operateTime = dg.curWin.document.getElementById("operateTime").value;
	var remark = dg.curWin.document.getElementById("remark").value;
	var productSel = getCheckBoxs('commodityCB');
	if (productSel.length == 0) {
		alert("请选择货品!");
		return;
	}
	var flag = ",";
	var productNos = "";
	for ( var i = 0; i < productSel.length; i++) {
		if (i == productSel.length - 1) {
			flag = "";
		}
		var productNo = productSel[i].value;
		productNos = productNos + productNo + flag;
	}
	if (!confirm("确定保存？")) {
		return;
	}

	$
			.ajax({
				type : "POST",
				url : doAddDetailUrl,
				data : {
					"mainId" : mainId,
					"applyCode" : applyCode,
					"warehouseId" : warehouseId,
					"operateTime" : operateTime,
					"remark" : remark,
					"supplierId" : supplierId,
					"type" : type,
					"productNos" : productNos
				},
				dataType : "json",
				success : function(resultMsg) {
					if (resultMsg.success == true) {
						var mainId = resultMsg.reCode;
						dg.curWin.document.getElementById("mainId").value = mainId;
						dg.curWin.document.getElementById("warehouseId").disabled = true;
						dg.curWin.document.getElementById("supplierId").disabled = true;
						disabeledRadio("type");

						dg.curWin.queryDetail();
						alert(resultMsg.msg);
						closewindow();
					} else {
						alert(resultMsg.msg);
					}
				}
			});
}

function disabeledRadio(name) {
	var radio = dg.curWin.document.getElementsByName(name);
	for ( var i = 0; i < radio.length; i++) {
		radio[i].disabled = true;
	}
}
