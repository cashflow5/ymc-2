function doSaveSupplier() {
	var supplierCode = document.getElementById("supplierCode").value;
	if (supplierCode == '') {
		alert('供应商不能为空！');
		return;
	}
	var myForm = document.getElementById("myForm");
	myForm.action = doSaveSupplierUrl;
	myForm.submit();
}