function validatorList(checkBoxFlag) {
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
			/^(?:[1-9]\d{0,8}(?:\.\d{2})?|0\.\d{2})$/, mesg, "unitPriceTip",
			rowIndexArray, checkBoxFlag);
	validatorResult = unitPriceRs ? validatorResult : unitPriceRs;

	return validatorResult;
}