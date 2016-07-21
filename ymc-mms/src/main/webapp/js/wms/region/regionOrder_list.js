function doQuery() {
	var queryForm = document.getElementById("queryForm");
	queryForm.action = queryUrl;
	queryForm.submit();
}

$(document).ready(
		function() {
			$('#bTime').calendar({
				maxDate : '#eTime',
				format : 'yyyy-MM-dd',
				targetFormat : 'yyyy-MM-dd'
			});
			$('#eTime').calendar({
				minDate : '#bTime',
				format : 'yyyy-MM-dd',
				targetFormat : 'yyyy-MM-dd'
			});
			selectSedVal("isExported", document
					.getElementById("tempIsExported").value);

			$("#checkall").click(function() {
				if (this.checked) {
					$("input[name='orderCheckBox']").each(function() {
						this.checked = true;
					});
				} else {
					$("input[name='orderCheckBox']").each(function() {
						this.checked = false;
					});
				}
			});
		});

// 导出选中订单
function doExportSel() {
	document.getElementById("orderCodes").value = "";

	var orderSel = getCheckBoxs('orderCheckBox');
	if (orderSel.length == 0) {
		alert("请选择要导出的订单!");
		return;
	}
	var orderCodes = "";
	for ( var i = 0; i < orderSel.length; i++) {
		orderCodes += orderSel[i].value + ',';
	}
	document.getElementById("orderCodes").value = orderCodes;
	var exportForm = document.getElementById("exportForm");
	exportForm.action = doExportSelUrl;
	exportForm.submit();
}
// 导出所有订单
function doExportAll() {
	var exportForm = document.getElementById("queryForm");
	exportForm.action = doExportAllUrl;
	exportForm.submit();
	// -_-!!
	exportForm.action = queryUrl;
}
// 置为缺货
function doSaleOut(ckname) {
	var orderSel = getCheckBoxs('orderCheckBox');
	if (orderSel.length == 0) {
		alert("请选择要处理的订单!");
		return;
	}
	var orderCodes = "";
	for ( var i = 0; i < orderSel.length; i++) {
		orderCodes += orderSel[i].value + ',';
	}
	if (confirm("确定缺货？")) {
		$.ajax({
			type : "POST",
			url : doSaleOutUrl,
			data : {
				"orderCodes" : orderCodes
			},
			dataType : "json",
			success : function(resultMsg) {
				if (resultMsg.success == "true") {
					alert(resultMsg.msg);
					doQuery();
				} else {
					alert(resultMsg.msg);
				}
			}
		});
	}
	;
}
// 生成出库单
function doCreateOut() {
	var orderSel = getCheckBoxs('orderCheckBox');
	if (orderSel.length == 0) {
		alert("请选择订单!");
		return;
	}
	var orderCodes = "";
	for ( var i = 0; i < orderSel.length; i++) {
		orderCodes += orderSel[i].value + ',';
	}
	if (confirm("确定生成出库单？")) {
		$.ajax({
			type : "POST",
			url : doCreateOutUrl,
			data : {
				"orderCodes" : orderCodes
			},
			dataType : "json",
			success : function(resultMsg) {
				if (resultMsg.success == "true") {
					alert(resultMsg.msg);
					doQuery();
				} else {
					alert(resultMsg.msg);
				}
			}
		});
	}
	;
}