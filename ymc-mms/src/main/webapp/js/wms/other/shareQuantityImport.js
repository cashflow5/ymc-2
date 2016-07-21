function doImport() {
	var uploadFile = document.getElementById("uploadFile").value;
	if (uploadFile == '') {
		alert("请选择上传文件！");
		return;
	}

	var importForm = document.getElementById("importForm");
	importForm.action = doImportUrl;
	importForm.submit();

	$("#img").show();
}