$(document).ready(function() {
	$("#barcode").keydown(function(e) {
		if (e.keyCode == 13) {
			doSaveDetail();
		}
	});
	btnDisplay();
});

function focusTo(id) {
	document.getElementById(id).focus();
}
// 保存
function doSaveDetail() {
	var mainId = document.getElementById("mainId").value;
	var barcode = document.getElementById("barcode").value;
	if (mainId == '') {
		alert('请保存！');
		return;
	}
	if (barcode == '') {
		alert('请输入货品条码！');
		return;
	}
	juhua();
	$.ajax({
		type : "POST",
		url : doSaveDetailUrl,
		data : {
			"mainId" : mainId,
			"barcode" : barcode
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == true) {
				document.getElementById("barcode").value = '';
				doQueryDetail();
				closewindow();
			} else {
				alert(resultMsg.msg);
				closewindow();
			}
		}
	});

}

function doQueryDetail() {
	var mainId = document.getElementById("mainId").value;
	document.getElementById('myIframe').src = queryDetailUrl + "?mainId="
			+ mainId;
}

function doSave() {
	var outCode = document.getElementById("outCode").value;
	var operateTime = document.getElementById("operateTime").value;
	var remark = document.getElementById("remark").value;
	// 打开菊花
	juhua();
	$.ajax({
		type : "POST",
		url : doSaveUrl,
		data : {
			"outCode" : outCode,
			"operateTime" : operateTime,
			"remark" : remark
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == true) {
				document.getElementById("saveBtn").style.display = "none";
				document.getElementById("remark").readOnly = true;
				document.getElementById("remark").style.background = "#EEEEEE";
				document.getElementById("mainId").value = resultMsg.reCode;
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

function doCheckPass() {
	var mainId = document.getElementById("mainId").value;

	if (confirm("确定后该数据将不允许再被修改，你确定要继续吗？") == false) {
		return;
	}
	// 打开菊花
	juhua();
	$.ajax({
		type : "POST",
		url : doCheckPassUrl,
		data : {
			"mainId" : mainId
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == true) {
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
// 确认
function doRemove() {
	var mainId = document.getElementById("mainId").value;
	if (confirm("确定后该数据将被删除，你确定要继续吗？") == false) {
		return;
	}
	// 打开菊花
	juhua();
	$.ajax({
		type : "POST",
		url : doRemoveUrl,
		data : {
			"mainId" : mainId
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == true) {
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
function btnDisplay() {
	var status = document.getElementById("status").value;
	if (status == '1') {
		document.getElementById("saveBtn").style.display = "none";
		document.getElementById("doCheckPassBtn").style.display = "none";
		document.getElementById("doRemoveBtn").style.display = "none";
	}
	var hasWarehouse = document.getElementById("hasWarehouse").value;
	if (hasWarehouse == '0') {
		document.getElementById("saveBtn").style.display = "none";
		document.getElementById("doCheckPassBtn").style.display = "none";
		document.getElementById("doRemoveBtn").style.display = "none";
	}
}