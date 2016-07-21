$(document).ready(function() {
	$("#barcode").keydown(function(e) {
		if (e.keyCode == 13) {
			doAddDetail();
		}
	});
});

// 保存
function doAddDetail() {
	var productMendBackId = document.getElementById("productMendBackId").value;
	var backCode = document.getElementById("backCode").value;
	var modifyDate = document.getElementById("modifyDate").value;
	var supplierCode = document.getElementById("supplierCode").value;
	var memo = document.getElementById("memo").value;
	var barcode = document.getElementById("barcode").value;
	if (supplierCode == '') {
		alert('请选择供应商！');
		return;
	}
	if (barcode == '') {
		alert('请输入货品条码！');
		return;
	}
	var mendResult = '';
	var mendResultArray = document.getElementsByName("mendResult");
	for ( var i = 0; i < mendResultArray.length; i++) {
		if (mendResultArray[i].checked) {
			mendResult = mendResultArray[i].value;
			break;
		}
	}

	$
			.ajax({
				type : "POST",
				url : doAddDetailUrl,
				data : {
					"productMendBackId" : productMendBackId,
					"backCode" : backCode,
					"modifyDate" : modifyDate,
					"supplierCode" : supplierCode,
					"memo" : memo,
					"mendResult" : mendResult,
					"barcode" : barcode
				},
				dataType : "json",
				success : function(resultMsg) {
					if (resultMsg.success == "true") {
						mendResultArray[0].checked = true;
						document.getElementById("barcode").value = '';
						document.getElementById("productMendBackId").value = resultMsg.reCode;
						doQueryDetail();
					} else {
						alert(resultMsg.msg);
					}
				}
			});

}

function doQueryDetail() {
	var productMendBackId = document.getElementById("productMendBackId").value;
	document.getElementById('myIframe').src = queryProductMendBackDetailUrl
			+ "?productMendBackId=" + productMendBackId;
}
// 确认
function doSave() {
	var productMendBackId = document.getElementById("productMendBackId").value;
	var backCode = document.getElementById("backCode").value;
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
			"productMendBackId" : productMendBackId,
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
function doExportProductMendBackDetail() {
	var exportForm = document.getElementById("queryForm");
	exportForm.action = doExportProductMendBackDetailUrl;
	exportForm.submit();
}