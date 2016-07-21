$(document).ready(function() {
	$("#code").keydown(function(e) {
		if (e.keyCode == 13) {
			doQueryInvoiceDelivery();
		}
	});
	focusTo('code');

});

function focusTo(id) {
	document.getElementById(id).focus();
}

function doQueryInvoiceDelivery() {
	var code = document.getElementById("code").value;
	var tempInvoiceCodes = document.getElementById("invoiceCodes").value;

	$
			.ajax({
				type : "POST",
				url : doCheckCodeUrl,
				data : {
					"code" : code,
					"tempInvoiceCodes" : tempInvoiceCodes
				},
				dataType : "json",
				success : function(resultMsg) {
					if (resultMsg.success == true) {
						var newTempInvoiceCodes = resultMsg.reCode;
						document.getElementById('invoiceCodes').value = newTempInvoiceCodes;

						var type = resultMsg.idCode;

						document.getElementById('myIframe').src = doQueryInvoiceDeliveryUrl
								+ "?code="
								+ code
								+ "&type="
								+ type
								+ "&tempInvoiceCodes=" + tempInvoiceCodes;

					} else {
						alert(resultMsg.msg);
					}
				}
			});

}

function doCheck() {
	var selVal = getRadioVal('shipMethod');
	var flag = true;
	var msg = "";

	var invoiceCodes = document.getElementById('invoiceCodes').value;
	if (invoiceCodes == '') {
		msg = msg + '无有效发票提交！\n';
		flag = false;
	} else {
		if (selVal == '1') {
			var expressCode = document.getElementById("expressCode").value;
			if (expressCode == '') {
				msg = msg + '请输入配送单号！\n';
				flag = false;
			}
		} else if (selVal == '2') {
			var logisticsCompanyCode = document
					.getElementById("logisticsCompanyCode").value;
			if (logisticsCompanyCode == '') {
				msg = msg + '请选择物流公司！\n';
				flag = false;
			}
			var isFreightCollect = document.getElementById("isFreightCollect").value;
			if (isFreightCollect == '') {
				msg = msg + '请选择运费到付！\n';
				flag = false;
			}
			var expressCode = document.getElementById("expressCode").value;
			if (expressCode == '') {
				msg = msg + '请输入配送单号！\n';
				flag = false;
			}
		}
	}
	if (!flag) {
		alert(msg);
	}
	return flag;
}

function selShipMethod() {
	var selVal = getRadioVal('shipMethod');
	if (selVal == '0') {
		document.getElementById("logisticsCompanyCode").style.display = "none";
		document.getElementById("isFreightCollectSpan").style.display = "none";
		$("#logisticsCompanyCode").val("");
		$("#isFreightCollect").val("");
		document.getElementById("expressCode").value="0";
	} else if (selVal == '1') {
		document.getElementById("logisticsCompanyCode").style.display = "none";
		document.getElementById("isFreightCollectSpan").style.display = "none";
		$("#logisticsCompanyCode").val("");
		$("#isFreightCollect").val("");
		document.getElementById("expressCode").value="";
	} else if (selVal == '2') {
		document.getElementById("logisticsCompanyCode").style.display = "";
		document.getElementById("isFreightCollectSpan").style.display = "";
		document.getElementById("expressCode").value="";
	}

}

function getRadioVal(nameOfRadio) {
	var returnValue = "";
	var theRadioInputs = document.getElementsByName(nameOfRadio);
	for ( var i = 0; i < theRadioInputs.length; i++) {
		if (theRadioInputs[i].checked) {
			returnValue = theRadioInputs[i].value;
			break;
		}
	}
	return returnValue;
}
/**
 * 单选选中
 */
function radioChecked(radioName, val) {
	var target = document.getElementsByName(radioName);

	for ( var i = 0; i < target.length; i++) {
		if (target[i].value == val) // 比较值
		{
			target[i].checked = true; // 修改选中状态
			break;
		}

	}
}

// 确认
function doSave() {
	if (!doCheck()) {
		return;
	}
	var invoiceCodes = document.getElementById('invoiceCodes').value;// 发票号
	var shipMethod = getRadioVal('shipMethod');// 配送方式
	var logisticsCompanyCode = document.getElementById("logisticsCompanyCode").value;// 物流公司
	var isFreightCollect = document.getElementById("isFreightCollect").value;// 运费到付
	var expressCode = document.getElementById("expressCode").value;// 快递单号

	if (invoiceCodes == '') {
		alert('无有效发票提交!');
		return;
	}

	if (confirm("确定后该数据将不允许再被修改，你确定要继续吗？") == false) {
		return;
	}

	// 打开菊花
	juhua();
	$.ajax({
		type : "POST",
		url : doSaveUrl,
		data : {
			"invoiceCodes" : invoiceCodes,
			"shipMethod" : shipMethod,
			"logisticsCompanyCode" : logisticsCompanyCode,
			"isFreightCollect" : isFreightCollect,
			"expressCode" : expressCode
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success) {
				alert(resultMsg.msg);
				closewindow();
				reloadPage();
			} else {
				alert(resultMsg.msg);
				closewindow();
			}
		}
	});

}
function reloadPage() {
	location.replace(toQueryInvoiceDeliveryUrl);
}

function goback() {
	window.location.href = doQueryUrl;
}
// 打开菊花
function juhua() {
	openDiv({
		content : '<div style="color:#ff0000">处理中...</div>',
		title : '提示',
		lock : true,
		width : 200,
		height : 60,
		closable : false,
		left : '50%',
		top : '40px'
	});
}
