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
		alert("请选择excel文件！");
		return;
	}
	if (!confirm("确定导入？")) {
		return;
	}
	document.getElementById("uploadMsg").innerHTML = "正在添加文件到服务器中，请稍候……<br />有可能因为网络问题，出现程序长时间无响应，请点击“<a href='?'><font color='red'>取消</font></a>”重新上传文件";

	var uploadFileForm = document.getElementById("uploadFileForm");
	uploadFileForm.action = doImportUrl;
	uploadFileForm.submit();
}
