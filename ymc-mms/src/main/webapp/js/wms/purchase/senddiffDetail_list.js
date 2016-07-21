// 保存
function doSavePay() {

	var table = document.getElementById("subTable");
	var idAndPay = "";
	for ( var i = 1; i < table.rows.length; i++) {
		var rowCells = table.rows[i].cells;
		if (rowCells[9].childNodes[0] != null) {
			var input = rowCells[9].getElementsByTagName("input");
			if (input.length > 0) {
				var firstInput = input[0];
				var detailId = firstInput.id;
				var payQuantiy = firstInput.value;
				
				if (!/^[1-9]\d*|0$/.test(payQuantiy)) {
					alert('请输入正确的格式！');
					return;
				}

				idAndPay = idAndPay + detailId + "_" + payQuantiy + ";";
			}
		}
	}

	$.ajax({
		type : "POST",
		url : doSavePayUrl,
		data : {
			"idAndPay" : idAndPay
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == true) {
				alert(resultMsg.msg);
			} else {
				alert(resultMsg.msg);
			}
		}
	});
}
