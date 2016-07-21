//审核通过
function doCheck() {
	var mainId = document.getElementById("mainId").value;
	$.ajax({
		type : "POST",
		url : doCheckUrl,
		data : {
			"mainId" : mainId
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == true) {
				if (confirm("确认审核！") == false) {
					return;
				}
				doCheckPass(mainId);
			} else {
				alert(resultMsg.msg);
			}
		}
	});
}

function doCheckPass(mainId) {
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

function doCheckFail() {
	var mainId = document.getElementById("mainId").value;
	if (confirm("确认回退！") == false) {
		return;
	}
	juhua();
	$.ajax({
		type : "POST",
		url : doCheckFailUrl,
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
function queryDetail() {
	var mainId = document.getElementById("mainId").value;
	document.getElementById('myIframe').src = queryDetailUrl + "?mainId="
			+ mainId;
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

function doSave() {
	myIframe.window.doSave();
}
function doSubmit() {
	myIframe.window.doSubmit();
}
function doCheckPassOutPay() {
	myIframe.window.doCheckPassOutPay();
}
function toSelProduct() {
	var warehouseId = document.getElementById("warehouseId").value;
	if ("" == warehouseId) {
		alert("请选择仓库！");
		return;
	}
	var supplierId = document.getElementById("supplierId").value;
	if ("" == supplierId) {
		alert("请选择供应商！");
		return;
	}
	var toRealSelProductUrl = toSelProductUrl + "?warehouseId=" + warehouseId;
	openwindow(toRealSelProductUrl, 950, 750, '新增明细');

}

function doRemoveDetail() {
	myIframe.window.doRemoveDetail();
}

function toImport() {
	var warehouseId = document.getElementById("warehouseId").value;
	if ("" == warehouseId) {
		alert("请选择仓库！");
		return;
	}
	var supplierId = document.getElementById("supplierId").value;
	if ("" == supplierId) {
		alert("请选择供应商！");
		return;
	}
	openwindow(toImportUrl, 450, 100, '导入明细');
}

function toSelSupplier() {
	openwindow(toSelSupplierUrl
			+ "?hiddenTextId=supplierId&&textId=supplierName", 900, 700, '供应商');
}

function initBtnShow() {
	var status = document.getElementById("status").value;
	if ("0" == status) {
		// 新增
		document.getElementById("toSelProductBtn").style.display = "";
		document.getElementById("doSaveBtn").style.display = "";
		document.getElementById("doRemoveDetailBtn").style.display = "";
		document.getElementById("doSubmitBtn").style.display = "";
		document.getElementById("toImportBtn").style.display = "";
		document.getElementById("doExportTempBtn").style.display = "";
		document.getElementById("doCheckPassBtn").style.display = "none";
		document.getElementById("doCheckFailBtn").style.display = "none";
		document.getElementById("remark").disabled = false;
	}
	if ("1" == status) {
		// 待确认
		document.getElementById("toSelProductBtn").style.display = "none";
		document.getElementById("doSaveBtn").style.display = "none";
		document.getElementById("doRemoveDetailBtn").style.display = "none";
		document.getElementById("doSubmitBtn").style.display = "none";
		document.getElementById("toImportBtn").style.display = "none";
		document.getElementById("doExportTempBtn").style.display = "none";
		document.getElementById("doCheckPassBtn").style.display = "";
		document.getElementById("doCheckFailBtn").style.display = "";
		document.getElementById("remark").disabled = true;

	}
	if ("2" == status) {
		// 已确认
		document.getElementById("toSelProductBtn").style.display = "none";
		document.getElementById("doSaveBtn").style.display = "none";
		document.getElementById("doRemoveDetailBtn").style.display = "none";
		document.getElementById("doSubmitBtn").style.display = "none";
		document.getElementById("toImportBtn").style.display = "none";
		document.getElementById("doExportTempBtn").style.display = "none";
		document.getElementById("doCheckPassBtn").style.display = "none";
		document.getElementById("doCheckFailBtn").style.display = "none";
		document.getElementById("remark").disabled = true;
	}
	if ("3" == status) {
		// 已回退
		document.getElementById("toSelProductBtn").style.display = "";
		document.getElementById("doSaveBtn").style.display = "";
		document.getElementById("doRemoveDetailBtn").style.display = "";
		document.getElementById("doSubmitBtn").style.display = "";
		document.getElementById("toImportBtn").style.display = "";
		document.getElementById("doExportTempBtn").style.display = "";
		document.getElementById("doCheckPassBtn").style.display = "none";
		document.getElementById("doCheckFailBtn").style.display = "none";
		document.getElementById("remark").disabled = false;
	}
	if ("4" == status) {
		// 已关闭
		document.getElementById("toSelProductBtn").style.display = "none";
		document.getElementById("doSaveBtn").style.display = "none";
		document.getElementById("doRemoveDetailBtn").style.display = "none";
		document.getElementById("doSubmitBtn").style.display = "none";
		document.getElementById("toImportBtn").style.display = "none";
		document.getElementById("doExportTempBtn").style.display = "none";
		document.getElementById("doCheckPassBtn").style.display = "none";
		document.getElementById("doCheckFailBtn").style.display = "none";
		document.getElementById("remark").disabled = true;

	}
}
