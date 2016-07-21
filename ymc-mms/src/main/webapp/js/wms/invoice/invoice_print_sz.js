//全选
$(document).ready(function() {
	$("#checkall").click(function() {
		if (this.checked) {
			$("input[name='invoiceCB']").each(function() {
				if (!this.disabled)
					this.checked = true;
			});
		} else {
			$("input[name='invoiceCB']").each(function() {
				this.checked = false;
			});
		}
	});
});

function doPrintTitle() {
	var cks = getCheckBoxs("invoiceCB");
	var printIds = "";
	if (cks.length == 0) {
		alert("请选择记录！");
		return;
	}
	for ( var i = 0; i < cks.length; i++) {
		var ck = cks[i];
		var val = ck.value;
		if (ck.checked && i < cks.length - 1) {
			printIds = printIds + val + ',';
		} else {
			printIds = printIds + val;
		}
	}
	$.ajax({
		type : "POST",
		url : doCheckIsPrintUrl,
		data : {
			"printIds" : printIds
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == true) {
				alert(resultMsg.msg);
			} else {
				doCheckIsSimple(printIds);
			}
		}
	});
}

function doCheckIsSimple(printIds) {
	$.ajax({
		type : "POST",
		url : doCheckIsSimpleUrl,
		data : {
			"printIds" : printIds
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == true) {
				openwindow(toInvoicePrintUrl + '?printIds=' + printIds, 500,
						170, '打印衔头发票');
			} else {
				alert(resultMsg.msg);
			}
		}
	});
}

function doRePrintTitle() {
	var cks = getCheckBoxs("invoiceCB");
	var printIds = "";
	if (cks.length == 0) {
		alert("请选择记录！");
		return;
	}
	for ( var i = 0; i < cks.length; i++) {
		var ck = cks[i];
		var val = ck.value;
		if (ck.checked && i < cks.length - 1) {
			printIds = printIds + val + ',';
		} else {
			printIds = printIds + val;
		}
	}
	$.ajax({
		type : "POST",
		url : doCheckIsPrintUrl,
		data : {
			"printIds" : printIds
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == true) {
				doCheckIsSimpleForRE(printIds);
			} else {
				alert(resultMsg.msg);
			}
		}
	});
}
function doCheckIsSimpleForRE(printIds) {
	$.ajax({
		type : "POST",
		url : doCheckIsSimpleUrl,
		data : {
			"printIds" : printIds
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == true) {
				if (!confirm("确定打印？")) {
					return;
				}
				window.location.href = doRePrintTitleUrl + '?printIds='
						+ printIds;
			} else {
				alert(resultMsg.msg);
			}
		}
	});
}

function doExportExcel() {
	var cks = getCheckBoxs("invoiceCB");
	var printIds = "";
	if (cks.length == 0) {
		alert("请选择记录！");
		return;
	}
	document.getElementById("printIds").value = "";
	for ( var i = 0; i < cks.length; i++) {
		var ck = cks[i];
		var val = ck.value;
		if (ck.checked && i < cks.length - 1) {
			printIds = printIds + val + ',';
		} else {
			printIds = printIds + val;
		}
	}
	document.getElementById("printIds").value = printIds;

	if (!confirm("确定导出？")) {
		return;
	}
	doWMSExport("queryForm", doExportExcelUrl);
}

function printAddress() {
	var cks = getCheckBoxs("invoiceCB");
	var storageId = "";
	if (cks.length == 0) {
		alert("请选择记录！");
		return;
	}
	for ( var i = 0; i < cks.length; i++) {
		var ck = cks[i];
		if (ck.checked && i < cks.length - 1) {
			storageId = storageId + ck.value + ',';
		} else {
			storageId = storageId + ck.value;
		}
	}
	var url = addressPrintUrl + '&printIds=' + storageId;
	openwindow(url, 800, 400, '打印地址条');
}

function updateInvoiceStatus() {
	var cks = getCheckBoxs("invoiceCB");
	var storageId = "";
	if (cks.length == 0) {
		alert("请选择记录！");
		return;
	}
	for ( var i = 0; i < cks.length; i++) {
		var ck = cks[i];
		if (ck.checked && i < cks.length - 1) {
			storageId = storageId + ck.value + ',';
		} else {
			storageId = storageId + ck.value;
		}
	}

	$.ajax({
		type : "POST",
		url : updateInvoiceUrl,
		data : {
			"printIds" : storageId
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == true) {
				alert("操作成功");
				window.location = queryUrl;
			} else {
				alert(resultMsg.msg);
			}
		}
	});
}

function clearDate() {
	var cks = getCheckBoxs("invoiceCB");
	var storageId = "";
	if (cks.length == 0) {
		alert("请选择记录！");
		return;
	}
	for ( var i = 0; i < cks.length; i++) {
		var ck = cks[i];
		if (ck.checked && i < cks.length - 1) {
			storageId = storageId + ck.value + ',';
		} else {
			storageId = storageId + ck.value;
		}
	}
	$.ajax({
		type : "POST",
		url : clearDateUrl,
		data : {
			"printIds" : storageId
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == true) {
				alert("操作成功");
				window.location = queryUrl;
			} else {
				alert("操作失败，请联系管理员");
			}
		}
	});
}



function initExport() {
	var url = doExportUrl;
	openwindow(url, 800, 400, '增值发票导入');
}

function exportInvoiceList() {
	window.location = doexportInvoiceList;
}

function doUpdateInvoiceCode(printIds, newSzInvoice) {
	$.ajax({
		type : "POST",
		url : doUpdateInvoiceCodeUrl,
		data : {
			"printIds" : printIds,
			"newSzInvoice" : newSzInvoice
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == true) {
				reloadPage();
			} else {
				alert(resultMsg.msg);
			}
		}
	});
}

function reloadPage() {
	location.replace(queryUrl);
}
