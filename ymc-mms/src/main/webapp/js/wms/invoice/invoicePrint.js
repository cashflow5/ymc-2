function doPrint() {
	var invoiceCode = document.getElementById("invoiceCode").value;
	if (invoiceCode == '') {
		alert("请输入发票号！");
		return;
	}
	var strP = /^\d+(\.\d+)?$/;
	if (!strP.test(invoiceCode)) {
		alert("请输入有效发票号！");
		return;
	}
	$.ajax({
		type : "POST",
		url : doCheckInvoiceCodeUrl,
		data : {
			"invoiceCode" : invoiceCode
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == true) {
				var newSzInvoice = resultMsg.reCode;
				if (newSzInvoice == '') {
					if (!confirm("确定打印？")) {
						return;
					}
				} else {
					if (!confirm("继续打印？")) {
						return;
					}
				}

				var cks = dg.curWin.getCheckBoxs("invoiceCB");
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

				window.location.href = doPrintTitleUrl + '?printIds='
						+ printIds + '&newSzInvoice=' + newSzInvoice;
				document.getElementById("doPrintBtn").style.display = "none";
				dg.curWin.doUpdateInvoiceCode(printIds, newSzInvoice);
			} else {
				alert(resultMsg.msg);

			}
		}
	});

}
