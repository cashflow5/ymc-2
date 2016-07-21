$(document).ready(function() {
	$("#orderNo").keydown(function(e) {
		if (e.keyCode == 13) {
			focusTo('barcode');
		}
	});
	$("#barcode").keydown(function(e) {
		if (e.keyCode == 13) {
			doAddDetail();
		}
	});
});

function focusTo(id) {
	document.getElementById(id).focus();
}
// 保存
function doAddDetail() {
	var productMendOutId = document.getElementById("productMendOutId").value;
	var outCode = document.getElementById("outCode").value;
	var modifyDate = document.getElementById("modifyDate").value;
	var supplierCode = document.getElementById("supplierCode").value;
	var memo = document.getElementById("memo").value;
	var orderNo = document.getElementById("orderNo").value;
	var barcode = document.getElementById("barcode").value;
	if (supplierCode == '') {
		alert('请选择供应商！');
		return;
	}
	if (orderNo == '') {
		alert('请输入订单号！');
		return;
	}
	if (barcode == '') {
		alert('请输入货品条码！');
		return;
	}
	$
			.ajax({
				type : "POST",
				url : doAddDetailUrl,
				data : {
					"productMendOutId" : productMendOutId,
					"outCode" : outCode,
					"modifyDate" : modifyDate,
					"supplierCode" : supplierCode,
					"memo" : memo,
					"orderNo" : orderNo,
					"barcode" : barcode
				},
				dataType : "json",
				success : function(resultMsg) {
					if (resultMsg.success == "true") {
						document.getElementById("orderNo").value = '';
						document.getElementById("barcode").value = '';
						document.getElementById("productMendOutId").value = resultMsg.reCode;
						doQueryDetail();
					} else {
						alert(resultMsg.msg);
					}
				}
			});

}

function doQueryDetail() {
	var productMendOutId = document.getElementById("productMendOutId").value;
	document.getElementById('myIframe').src = queryProductMendOutDetailUrl
			+ "?productMendOutId=" + productMendOutId;
}
// 确认
function doSave() {
	var productMendOutId = document.getElementById("productMendOutId").value;
	var supplierCode = document.getElementById("supplierCode").value;
	var memo = document.getElementById("memo").value;
	if (supplierCode == '') {
		alert('请选择供应商！');
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
			"productMendOutId" : productMendOutId,
			"supplierCode" : supplierCode,
			"memo" : memo
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success) {
				alert(resultMsg.msg);
				closewindow();
				goback();
			} else {
				alert(resultMsg.msg);
				closewindow();
			}
		}
	});

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

// 导出
function doExportProductMendOutDetail() {
	var exportForm = document.getElementById("queryForm");
	exportForm.action = doExportProductMendOutDetailUrl;
	exportForm.submit();
}