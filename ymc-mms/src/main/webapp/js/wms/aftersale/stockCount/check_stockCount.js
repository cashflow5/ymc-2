function doQueryDetail() {
	var stockCountId = document.getElementById("stockCountId").value;
	document.getElementById('myIframe').src = queryStockCountDetailUrl
			+ "?stockCountId=" + stockCountId;
}
// 确认
function doCheckPass() {
	var stockCountId = document.getElementById("stockCountId").value;
	var checkMemo = document.getElementById("checkMemo").value;

	if (stockCountId == '') {
		alert("未开始盘点！");
		return;
	}

	if (confirm("你确定要继续吗？") == false) {
		return;
	}
	// 打开菊花
	juhua();
	$.ajax({
		type : "POST",
		url : doPassCheckUrl,
		data : {
			"stockCountId" : stockCountId,
			"checkMemo" : checkMemo
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == "true") {
				document.getElementById("status").innerHTML = resultMsg.reCode;
				document.getElementById("doCheckPass").style.display = "none";
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
	window.location.href = doQueryForCheckUrl;
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
function doExport() {
	var exportForm = document.getElementById("exportForm");
	exportForm.action = doExportUrl;
	exportForm.submit();
}
