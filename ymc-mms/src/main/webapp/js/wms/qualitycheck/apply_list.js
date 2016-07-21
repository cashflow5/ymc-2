function doQuery() {
	var expressCode = document.getElementById("expressCode").value;
	if (expressCode == '') {
		alert('寄回快递单号为空，不能查询');
		return;
	}

	var queryForm = document.getElementById("queryForm");
	queryForm.action = doQueryUrl;
	queryForm.submit();
}
