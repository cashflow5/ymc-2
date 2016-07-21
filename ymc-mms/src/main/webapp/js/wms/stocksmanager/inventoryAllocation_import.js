function doExport() {
	var uploadFile = document.getElementById("uploadFile");
	if (uploadFile.value == "") {
		alert("请选择上传文件！");
		return;
	}
	var uploadFileName = uploadFile.value;
	var subName = uploadFileName.substring(uploadFileName.length - 3,
			uploadFileName.length);
	if (subName != "xls") {
		alert("请选择xls文件！");
		return;
	}
	if (!confirm("确定导入？")) {
		return;
	}
	document.getElementById("uploadMsg").innerHTML = "正在上传数据到服务器中，请稍候……";

	document.getElementById("uploadFileBtn").disabled = true

	var uploadFileForm = document.getElementById("uploadFileForm");
	uploadFileForm.action = "doImport.sc";
	uploadFileForm.submit();
}
