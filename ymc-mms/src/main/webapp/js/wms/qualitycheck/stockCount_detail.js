$(document).ready(function() {
	$("#barcode").keydown(function(e) {
		if (e.keyCode == 13) {
			document.getElementById("actualQuantity").focus();
			document.getElementById("actualQuantity").value = "1";
		}
	});
	$("#actualQuantity").keydown(function(e) {
		if (e.keyCode == 13) {
			doAddDetail();
		}
	});
});

// 保存
function doAddDetail() {
	var stockCountId = document.getElementById("stockCountId").value;
	var barcode = document.getElementById("barcode").value;
	var actualQuantity = document.getElementById("actualQuantity").value;
	if (barcode == '') {
		alert('请输入货品条码！');
		document.getElementById("barcode").focus();
		return;
	}
	if (actualQuantity == '') {
		alert('请输入数量！');
		document.getElementById("actualQuantity").focus();
		return;
	}
	var r = /^\d+$/;
	if (!r.test(actualQuantity)) {
		alert('请输入有效数量！');
		document.getElementById("actualQuantity").focus();
		return;
	}

	$.ajax({
		type : "POST",
		url : doAddDetailUrl,
		data : {
			"stockCountId" : stockCountId,
			"barcode" : barcode,
			"actualQuantity" : actualQuantity
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == "true") {
				document.getElementById("barcode").value = "";
				document.getElementById("actualQuantity").value = "";
				doQueryDetail();
			} else {
				alert(resultMsg.msg);
			}
		}
	});

}

function doQueryDetail() {
	var stockCountId = document.getElementById("stockCountId").value;
	document.getElementById('myIframe').src = queryStockCountDetailUrl
			+ "?stockCountId=" + stockCountId;
}
// 确认
function doSave() {
	var stockCountId = document.getElementById("stockCountId").value;
	var stockCountCode = document.getElementById("stockCountCode").value;
	var createTime = document.getElementById("createTime").value;
	var type = document.getElementById("type").value;
	var inventoryType = document.getElementById("inventoryType").value;
	var memo = document.getElementById("memo").value;
	var warehouseCode = document.getElementById("warehouseCode").value;

	if (stockCountId != '') {
		alert("已开始盘点！");
		return;
	}

	if (confirm("你确定要继续吗？") == false) {
		return;
	}
	// 打开菊花
	juhua();
	$
			.ajax({
				type : "POST",
				url : doSaveUrl,
				data : {
					"stockCountId" : stockCountId,
					"stockCountCode" : stockCountCode,
					"createTime" : createTime,
					"type" : type,
					"inventoryType" : inventoryType,
					"memo" : memo,
					"warehouseCode" : warehouseCode
				},
				dataType : "json",
				success : function(resultMsg) {
					if (resultMsg.success == "true") {
						document.getElementById("type").disabled = true;
						document.getElementById("inventoryType").disabled = true;
						document.getElementById("memo").disabled = true;
						document.getElementById("stockCountId").value = resultMsg.reCode;
						document.getElementById("operateP").style.display = "";
						document.getElementById("doSave").style.display = "none";
						document.getElementById("doWaitCheck").style.display = "";
						document.getElementById("doRemove").style.display = "";
						doQueryDetail();
						alert(resultMsg.msg);
						closewindow();
					} else {
						alert(resultMsg.msg);
						closewindow();
					}
				}
			});

}
function goback() {
	var pageFlag = document.getElementById("pageFlag").value;
	if (pageFlag == 'new' || pageFlag == '') {
		window.location.href = doQueryUrl;
	}
	if (pageFlag == 'check') {
		window.location.href = doQueryForCheckUrl;
	}
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
function doWaitCheck() {
	var stockCountId = document.getElementById("stockCountId").value;

	if (stockCountId == '') {
		alert("盘点未开始！");
		return;
	}

	if (confirm("你确定要继续吗？") == false) {
		return;
	}
	// 打开菊花
	juhua();
	$.ajax({
		type : "POST",
		url : doWaitCheckUrl,
		data : {
			"stockCountId" : stockCountId
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == "true") {
				document.getElementById("status").innerHTML = resultMsg.reCode;
				document.getElementById("barcode").disabled = true;
				document.getElementById("actualQuantity").disabled = true;
				document.getElementById("doSave").style.display = "none";
				document.getElementById("doWaitCheck").style.display = "none";
				document.getElementById("doRemove").style.display = "";
				alert(resultMsg.msg);
				closewindow();
			} else {
				alert(resultMsg.msg);
				closewindow();
			}
		}
	});
}
function doRemove() {
	var stockCountId = document.getElementById("stockCountId").value;

	if (stockCountId == '') {
		alert("盘点未开始！");
		return;
	}

	if (confirm("你确定要继续吗？") == false) {
		return;
	}
	// 打开菊花
	juhua();
	$.ajax({
		type : "POST",
		url : doRemoveUrl,
		data : {
			"stockCountId" : stockCountId
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == "true") {
				alert(resultMsg.msg);
				closewindow();
				window.location.href = doQueryUrl;
			} else {
				alert(resultMsg.msg);
				closewindow();
			}
		}
	});
}
// 导出
function doExport() {
	var exportForm = document.getElementById("exportForm");
	exportForm.action = doExportUrl;
	exportForm.submit();
}
function checkStatus() {
	var stockCountId = document.getElementById("stockCountId").value;
	var hiddenStatus = document.getElementById("hiddenStatus").value;
	// 初始化
	if (stockCountId == '') {
		document.getElementById("doWaitCheck").style.display = "none";
		document.getElementById("doRemove").style.display = "none";
	}
	// 盘点中
	if (stockCountId != '' && hiddenStatus == 'COUNTING') {
		document.getElementById("type").disabled = true;
		document.getElementById("inventoryType").disabled = true;
		document.getElementById("memo").disabled = true;
		document.getElementById("operateP").style.display = "";
		document.getElementById("checkMemoP").style.display = "none";
		document.getElementById("doSave").style.display = "none";
		document.getElementById("doWaitCheck").style.display = "";
		doQueryDetail();
	}
	// 审核成功
	if (hiddenStatus == 'PASS_CHECK') {
		document.getElementById("type").disabled = true;
		document.getElementById("inventoryType").disabled = true;
		document.getElementById("memo").disabled = true;
		document.getElementById("checkMemo").disabled = true;
		document.getElementById("operateP").style.display = "none";
		document.getElementById("checkMemoP").style.display = "";
		document.getElementById("doSave").style.display = "none";
		document.getElementById("doWaitCheck").style.display = "none";
		document.getElementById("doRemove").style.display = "none";
		doQueryDetail();
	}
	// 待审核
	if (hiddenStatus == 'WAIT_CHECK') {
		document.getElementById("type").disabled = true;
		document.getElementById("inventoryType").disabled = true;
		document.getElementById("memo").disabled = true;
		document.getElementById("checkMemo").disabled = false;
		document.getElementById("operateP").style.display = "none";
		document.getElementById("checkMemoP").style.display = "none";
		document.getElementById("doSave").style.display = "none";
		document.getElementById("doWaitCheck").style.display = "none";
		doQueryDetail();
	}
}