function doSave() {
	var selectArray = getCheckBoxs("commodityCB");
	if (selectArray.length == 0) {
		alert("请选择货品！");
		return;
	}
	var codesRates = "";
	var flag = ",";
	for (var i = 0; i < selectArray.length; i++) {
		var index = selectArray[i].value;
		var commodityCodes = document.getElementById("commodityCode[" + index
				+ "]").value;
		var storageRates = document
				.getElementById("storageRate[" + index + "]").value;
		var notKeep = 0;
		if (document.getElementById("notKeep[" + index + "]").checked){
			notKeep = 1;
		}
		if (i == selectArray.length - 1) {
			flag = "";
		}
		codesRates += commodityCodes + ":" + storageRates + ":" + notKeep + flag;
	}
	document.getElementById("doSaveBtn").disabled = true;

	$.ajax({
				type : "POST",
				url : url,
				data : {
					"codesRates" : codesRates

				},
				dataType : "json",
				success : function(resultMsg) {
					if (resultMsg.success == "true") {
						document.getElementById("doSaveBtn").disabled = false;
						if (confirm(resultMsg.msg)) {
						} else {
							dg.curWin.doQuery();
							dg.curWin.close();
						}
					} else {
						document.getElementById("doSaveBtn").disabled = false;
						alert(resultMsg.msg);
					}
				}
			});
}

function validatorProduct(checkBoxFlag) {
	var rowIndexArray = new Array();
	if (checkBoxFlag == "1") {
		$("input:checked[id!=checkall]").each(function() {
			rowIndex = $(this).parent().parent().index();
			rowIndexArray[rowIndexArray.length] = rowIndex;
		});
	}
	var validatorResult = true;

	var mesg = "数量格式不正确，必须是非零的正整数且位数最大7位。";


	var quantityRs = validatorInputFile('quantity', /^([0-9]{1,7}|0)(\d{0})?$/,
			mesg, "quantityTip", rowIndexArray, checkBoxFlag);
	validatorResult = quantityRs ? validatorResult : quantityRs;
	return validatorResult;
}

//按勾选设置预留数
function doCheckBoxSavebyObligateNum() {
	var selectArray = getCheckBoxs("commodityCB");
	if (selectArray.length == 0) {
		alert("请选择货品！");
		return;
	}
	var batchStorageRate = document.getElementById("batchStorageRate").value;
	if (batchStorageRate == "" || !/^([0-9]{1,7}|0)(\d{0})?$/.test(batchStorageRate)) {
		$("#batchStorageRateTip").text("请输入正确的优购库存预留数！");
		$("#batchStorageRateTip").show();
		return;
	} else {
		$("#batchStorageRateTip").hide();
	}
	$("#batchStorageRateTip").hide();
	
	if (window.confirm('您本次操作将会对"已勾选"的货品的库存预留数据进行更新。\n您确认要继续吗?') == false) {
		return;
	}
	
	var rowIndexArray = new Array();
		$("input:checked[name='commodityCB']").each(function() {
					rowIndex = $(this).parent().parent().index();
					rowIndexArray[rowIndexArray.length] = rowIndex;
		});
	var codesRates = "";
	var flag = ",";
	for (var i = 0; i < selectArray.length; i++) {
		var index = selectArray[i].value;
		var commodityCodes = document.getElementById("commodityCode[" + index
				+ "]").value;
		if (i == selectArray.length - 1) {
			flag = "";
		}
		codesRates += commodityCodes + flag;
	}
	
	$.ajax({
				type : "POST",
				url : url,
				data : {
					"batchStorageRate" : batchStorageRate,
					"codesRates" : codesRates
				},
				dataType : "json",
				beforeSend: function() {
					$("#doCheckBoxSaveBtn").attr("disabled", true);
					$("#doBatchSaveBtn").hide();
				},
				success : function(resultMsg) {
					$('#doCheckBoxSaveBtn').attr('disabled',false);
					$("#doBatchSaveBtn").show();
					if (resultMsg.success) {
						if (window.confirm(resultMsg.msg)==false) {
							
						} else {
							closewindow();
							dg.curWin.doQuery();
						}
					} else {
						alert(resultMsg.msg);
					}
				}
			});
}
//按列表设置分销虚拟库存
function doBatchSavebyObligateNum() {
	var checkBoxs = document.getElementsByName("commodityCB");
	if (checkBoxs.length == 0) {
		alert("无货品设置！");
		return;
	}
	var batchStorageRate = document.getElementById("batchStorageRate").value;
	if (batchStorageRate == ""  || !/^([0-9]{1,7}|0)(\d{0})?$/.test(batchStorageRate)) {
		$("#batchStorageRateTip").text("请输入正确的优购库存预留数！");
		$("#batchStorageRateTip").show();
		return;
	} else {
		$("#batchStorageRateTip").hide();
	}
	$("#batchStorageRateTip").hide();
	
	if (window.confirm('您本次操作将会对"当前列表"所有货品的库存预留数据进行更新。\n您确认要继续吗?') == false) {
		return;
	}
	// 查询条件
	var firstCatNo = document.getElementById("firstCatNo").value;
	var secondCatNo = document.getElementById("secondCatNo").value;
	var thirdCatNo = document.getElementById("thirdCatNo").value;
	var brandNo = document.getElementById("brandNo").value;
	var name = document.getElementById("name").value;
	var productNo = document.getElementById("productNo").value;
	var styleNo = document.getElementById("styleNo").value;
	var supplierCode = document.getElementById("supplierCode").value;
	var commodityStyleNo = document.getElementById("commodityStyleNo").value;
	$.ajax({
				type : "POST",
				url : batchUrl,
				data : {
					"batchStorageRate" : batchStorageRate,
					"firstCatNo" : firstCatNo,
					"secondCatNo" : secondCatNo,
					"thirdCatNo" : thirdCatNo,
					"brandNo" : brandNo,
					"name" : name,
					"productNo" : productNo,
					"styleNo" : styleNo,
					"supplierCode" : supplierCode,
					"commodityStyleNo" : commodityStyleNo
				},
				beforeSend: function() {
						$("#doBatchSaveBtn").attr("disabled", true);
						$("#doCheckBoxSaveBtn").hide();
				},
				dataType : "json",
				success : function(resultMsg) {
					$("#doBatchSaveBtn").attr("disabled", false);
					$("#doCheckBoxSaveBtn").show();
					if (resultMsg.success) {
						if (window.confirm(resultMsg.msg)==false) {
							
						} else {
							closewindow();
							dg.curWin.doQuery();
						}
					} else {
						alert(resultMsg.msg);
					}
				}
			});
}

function doBatchSave() {
	var checkBoxs = document.getElementsByName("commodityCB");
	if (checkBoxs.length == 0) {
		alert("无货品设置！");
		return;
	}
	var batchStorageRate = document.getElementById("batchStorageRate").value;
	if (batchStorageRate == "") {
		$("#batchStorageRateTip").text("请输入正确的优购库存占比数！");
		$("#batchStorageRateTip").show();
		return;
	} else {
		$("#batchStorageRateTip").hide();
	}
	if (!/^([0][.]\d{2})|([1]|([0]))$/.test(batchStorageRate)) {
		$("#batchStorageRateTip").text("请输入正确的优购库存占比数！");
		$("#batchStorageRateTip").show();
		return;
	} else {
		$("#batchStorageRateTip").hide();
	}
	if (window.confirm('您本次操作将会对"当前列表"所有数据的比例进行修改。\n您确认要继续吗?') == false) {
		return;
	}
	// 查询条件
	var firstCatNo = document.getElementById("firstCatNo").value;
	var secondCatNo = document.getElementById("secondCatNo").value;
	var thirdCatNo = document.getElementById("thirdCatNo").value;
	var brandNo = document.getElementById("brandNo").value;
	var name = document.getElementById("name").value;
	var insideCode = document.getElementById("insideCode").value;
	var productNo = document.getElementById("productNo").value;
	var styleNo = document.getElementById("styleNo").value;
	var supplierCode = document.getElementById("supplierCode").value;
	var commodityStyleNo = document.getElementById("commodityStyleNo").value;

	$.ajax({
				type : "POST",
				url : batchUrl,
				data : {
					"batchStorageRate" : batchStorageRate,
					"firstCatNo" : firstCatNo,
					"secondCatNo" : secondCatNo,
					"thirdCatNo" : thirdCatNo,
					"brandNo" : brandNo,
					"name" : name,
					"insideCode" : insideCode,
					"productNo" : productNo,
					"styleNo" : styleNo,
					"supplierCode" : supplierCode,
					"commodityStyleNo" : commodityStyleNo
				},
				dataType : "json",
				success : function(resultMsg) {
					if (resultMsg.success == "true") {
						document.getElementById("doBatchSaveBtn").disabled = false;
						if (confirm(resultMsg.msg)) {
						} else {
							dg.curWin.doQuery();
							dg.curWin.close();
						}
					} else {
						document.getElementById("doBatchSaveBtn").disabled = false;
						alert(resultMsg.msg);
					}
				}
			});
}

function doClose() {
	dg.curWin.doQuery();
	dg.curWin.close();
}

function validatorList(checkBoxFlag) {
	var rowIndexArray = new Array();
	if (checkBoxFlag == "1") {
		$("input:checked[name='commodityCB']").each(function() {
					rowIndex = $(this).parent().parent().index();
					rowIndexArray[rowIndexArray.length] = rowIndex;
				});
	}
	var validatorResult = true;
	var mesg = "占比格式不正确，必须是数字且保留两位小数。(如：0.01)";
	var unitPriceRs = validatorInputFile('storageRate',
			/^([0][.]\d{2})|([1]|([0]))$/, mesg, "storageRateTip",
			rowIndexArray, checkBoxFlag);
	validatorResult = unitPriceRs ? validatorResult : unitPriceRs;

	return validatorResult;
}
