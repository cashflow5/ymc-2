function validatorProduct(checkBoxFlag) {
	var rowIndexArray = new Array();
	if (checkBoxFlag == "1") {
		$("input:checked[id!=checkall]").each(function() {
					rowIndex = $(this).parent().parent().index();
					rowIndexArray[rowIndexArray.length] = rowIndex;
				});
	}
	var validatorResult = true;
	// 货品销售价格验证
	var mesg = "单价格式不正确，必须是数字且小位数点前位数最大5位。(如：0.01)";
	var unitPriceRs = validatorInputFile('unitPrice',
			/^([0-9]{1,5}|0)(\.\d{2})?$/, mesg, "unitPriceTip", rowIndexArray,
			checkBoxFlag);
	validatorResult = unitPriceRs ? validatorResult : unitPriceRs;

	var mesg = "数量格式不正确，必须是非零的正整数且位数最大7位。";
	var quantityRs = validatorInputFile('quantity', /^([0-9]{1,7}|0)(\d{0})?$/, mesg,
			"quantityTip", rowIndexArray, checkBoxFlag);
	validatorResult = quantityRs ? validatorResult : quantityRs;

	return validatorResult;
}

/**
 * 验证多行文本框信息
 * 
 * @param file
 *            validatorFile 名称
 * @param reg
 *            验证规则 正则表达式
 * @param errorMsg
 * @param mesTipId
 */
function validatorInputFile(file, reg, errorMsg, mesTipId, rowIndexArray,
		checkBoxFlag) {
	var validatorResult = true;
	var validatorNodes = $("input[validatorFile=" + file + "]");
	var mesg = "";
	if (checkBoxFlag == "1") {
		for (var i = 0; i < rowIndexArray.length; i++) {
			var row = rowIndexArray[i];
			var value = validatorNodes[row].value;
			if (!reg.test(value)) {
				mesg += "第" + (row + 1) + "行 , ";
			}
		}
	} else {
		for (var i = 0; i < validatorNodes.length; i++) {
			var value = validatorNodes[i].value;
			if (!reg.test(value)) {
				mesg += "第" + (i + 1) + "行 , ";
			}
		}
	}
	$('#' + mesTipId).empty();
	if (mesg.length > 2) {
		$('#' + mesTipId).parent().css("display", "block");
		$('#' + mesTipId).append(mesg + errorMsg);
		$('#' + mesTipId).attr("class", "onerror");
		validatorResult = false;
	} else {
		$('#' + mesTipId).attr("class", "");
	}

	return validatorResult;
}
