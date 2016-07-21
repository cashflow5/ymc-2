// 保存
function doSave() {
	var mainId = document.getElementById("mainId").value;
	var memo = document.getElementById("memo").value;
	$.ajax({
		type : "POST",
		url : doSaveUrl,
		data : {
			"mainId" : mainId,
			"memo" : memo
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == true) {
				alert(resultMsg.msg);
				goback();
			} else {
				alert(resultMsg.msg);
			}
		}
	});
}
/**
 * 审核通过
 */
function doCheckPass() {
	var mainId = document.getElementById("mainId").value;
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
				goback();
			} else {
				alert(resultMsg.msg);
			}
		}
	});
}
/**
 * 审核回退
 */
function doCheckFail() {
	var mainId = document.getElementById("mainId").value;
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
				goback();
			} else {
				alert(resultMsg.msg);
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
function exportTemp() {
	window.location.href = exportTempUrl;
}
function toImport() {
	var warehouseCode = document.getElementById("warehouseCode").value;
	if (warehouseCode == "") {
		alert("请选择仓库！");
		return;
	}
	openwindow(toImportUrl, 500, 170, '导入可申请商品明细');
}
function toAddDetail() {
	var supplierCode = document.getElementById("supplierCode").value;
	if (supplierCode == "") {
		alert("请选择供应商！");
		return;
	}
	var photoSupport = document.getElementById("photoSupport").value;
	if (photoSupport == "") {
		alert("请选择供应商提供图片！");
		return;
	}
	openwindow(toAddDetailUrl, 950, 750, '新增明细');

}
/**
 * 删除
 */
function doRemoveDetail() {
	myIframe.window.doRemoveDetail();
}

function initBtnShow() {
	var status = document.getElementById("status").value;
	if ("404" == status) {
		// 新增初始化页面
		document.getElementById("doSaveBtn").style.display = "none";
		document.getElementById("doCheckPassBtn").style.display = "none";
		document.getElementById("doCheckFailBtn").style.display = "none";
	}
	if ("2" == status) {
		// 已回退
		document.getElementById("doCheckPassBtn").style.display = "none";
		document.getElementById("doCheckFailBtn").style.display = "none";
	}
	if ("0" == status) {
		// 待审核
		document.getElementById("toAddDetailBtn").style.display = "none";
		document.getElementById("doSaveBtn").style.display = "none";
		document.getElementById("doRemoveDetailBtn").style.display = "none";
		document.getElementById("exportTempBtn").style.display = "none";
		document.getElementById("importBtn").style.display = "none";
	}
	if ("4" == status) {
		// 待提交
		document.getElementById("doCheckPassBtn").style.display = "none";
		document.getElementById("doCheckFailBtn").style.display = "none";
	}
	if ("1" == status) {
		// 已审核
		document.getElementById("toAddDetailBtn").style.display = "none";
		document.getElementById("doSaveBtn").style.display = "none";
		document.getElementById("doRemoveDetailBtn").style.display = "none";
		document.getElementById("doCheckPassBtn").style.display = "none";
		document.getElementById("doCheckFailBtn").style.display = "none";
		document.getElementById("exportTempBtn").style.display = "none";
		document.getElementById("importBtn").style.display = "none";

	}

}
