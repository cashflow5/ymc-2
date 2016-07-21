function doCheck() {
	var importDate = dg.curWin.document.getElementById("importDate").value;
	$.ajax({
		type : "POST",
		url : doCheckUrl,
		data : {
			"importDate" : importDate
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == true) {
				if (confirm("此日期存在历史库存记录，是否覆盖？") == false) {
					return;
				}
			}
			doImport();
		}
	});
}

function doImport() {
	var uploadFile = document.getElementById("uploadFile").value;
	if (uploadFile == '') {
		alert("请选择上传文件！");
		return;
	}
	document.getElementById("importDate").value = dg.curWin.document
			.getElementById("importDate").value;

	if (confirm("确认导入！") == false) {
		return;
	}

	var importForm = document.getElementById("importForm");
	importForm.action = doImportUrl;
	importForm.submit();

	$("#img").show();
}