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
			if (resultMsg.success == "true") {
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