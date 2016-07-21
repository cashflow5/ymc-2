// 保存
function doSaveOutPay() {
	
	var newDeliveryStorageType = $('input[name=deliveryStorageTypeRadio]:checked', parent.document).val();
	if(newDeliveryStorageType == null || newDeliveryStorageType ==""){
		alert('出库类型不能为空！');
		return;
	}
	var shipVal = $('#typeval', parent.document).val();
    if(newDeliveryStorageType != shipVal){
     	if(!confirm("出库类型和供应商默认退货处理类型不一致，请确认是否继续？")){	
		   return false;
	 	}
    } 
	
	var table = document.getElementById("subTable");
	var outPays = "";
	for ( var i = 1; i < table.rows.length; i++) {
		var rowCells = table.rows[i].cells;
		var successPayInput = rowCells[9].getElementsByTagName("input");
		var successInput = successPayInput[0];
		var detailId = successInput.id;
		var successQuantiy = successInput.value;

		var failurePayInput = rowCells[10].getElementsByTagName("input");
		var failureInput = failurePayInput[0];
		var failureQuantiy = failureInput.value;

		if (!/^[1-9]\d*|0$/.test(successQuantiy)) {
			alert('请输入正确的格式！');
			return;
		}
		if (!/^[1-9]\d*|0$/.test(failureQuantiy)) {
			alert('请输入正确的格式！');
			return;
		}

		outPays = outPays + detailId + "_" + successQuantiy + "_"
				+ failureQuantiy + ";";
	}
	var mainId = parent.document.getElementById("mainId").value;
	var remark = parent.document.getElementById("remark").value;

	$.ajax({
		type : "POST",
		url : doSaveOutPayUrl,
		data : {
			"mainId" : mainId,
			"remark" : remark,
			"outPays" : outPays,
			"deliveryStorageType" : newDeliveryStorageType
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

function doCheckPassOutPay() {
	if (confirm("该操作将确认此索赔出库单，确认继续吗？") == false) {
		return;
	}

	var newDeliveryStorageType = $('input[name=deliveryStorageTypeRadio]:checked', parent.document).val();
	if(newDeliveryStorageType == null || newDeliveryStorageType ==""){
		alert('出库类型不能为空！');
		return;
	}
	var shipVal = $('#typeval', parent.document).val();
    if(newDeliveryStorageType != shipVal){
     	if(!confirm("出库类型和供应商默认退货处理类型不一致，请确认是否继续？")){	
		   return false;
	 	}
    } 
	
	var mainId = parent.document.getElementById("mainId").value;
	var table = document.getElementById("subTable");
	var outPays = "";
	for ( var i = 1; i < table.rows.length; i++) {
		var rowCells = table.rows[i].cells;
		var successPayInput = rowCells[9].getElementsByTagName("input");
		var successInput = successPayInput[0];
		var detailId = successInput.id;
		var successQuantiy = successInput.value;

		var failurePayInput = rowCells[10].getElementsByTagName("input");
		var failureInput = failurePayInput[0];
		var failureQuantiy = failureInput.value;

		if (!/^[1-9]\d*|0$/.test(successQuantiy)) {
			alert('请输入正确的格式！');
			return;
		}
		if (!/^[1-9]\d*|0$/.test(failureQuantiy)) {
			alert('请输入正确的格式！');
			return;
		}

		outPays = outPays + detailId + "_" + successQuantiy + "_"
				+ failureQuantiy + ";";
	}

	var remark = parent.document.getElementById("remark").value;

	$.ajax({
		type : "POST",
		url : doCheckPassOutPayUrl,
		data : {
			"mainId" : mainId,
			"remark" : remark,
			"outPays" : outPays,
			"deliveryStorageType" : newDeliveryStorageType
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == true) {
				alert(resultMsg.msg);
				parent.goback();
			} else {
				alert(resultMsg.msg);
			}
		}
	});
}