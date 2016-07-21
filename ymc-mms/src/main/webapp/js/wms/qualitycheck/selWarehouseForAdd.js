function toRealAdd() {
	var warehouseCode = document.getElementById("warehouseCode").value;
	if (warehouseCode == '') {
		alert('请选择仓库！');
		return;
	}
	var myForm = document.getElementById("myForm");
	myForm.action = toRealAddUrl;
	myForm.submit();
}