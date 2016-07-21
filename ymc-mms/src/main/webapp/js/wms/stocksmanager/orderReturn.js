// 正则表达式完成页面验证
var config = {
	form : "mainForm",
	submit : validateform,
	fields : []
}
var rowConfig = {
	fields : [{
				id : 'godownentryDetails[index].commodityId',
				name : 'godownentryDetails[index].commodityId',
				type : 'hidden',
				display : false,
				tdIndex : 0,
				valueIndex : 0,
				validatorFile : '',
				style : ''
			}, {
				id : 'godownentryDetails[index].commodityCode',
				name : 'godownentryDetails[index].commodityCode',
				type : 'hidden',
				display : true,
				tdIndex : 0,
				valueIndex : 1,
				validatorFile : '',
				style : ''
			}, {
				id : 'godownentryDetails[index].goodsName',
				name : 'godownentryDetails[index].goodsName',
				type : 'hidden',
				display : true,
				tdIndex : 1,
				valueIndex : 2,
				validatorFile : '',
				style : ''
			}, {
				id : 'godownentryDetails[index].specification',
				name : 'godownentryDetails[index].specification',
				type : 'hidden',
				display : true,
				tdIndex : 2,
				valueIndex : 3,
				validatorFile : '',
				style : ''
			}, {
				id : 'godownentryDetails[index].units',
				name : 'godownentryDetails[index].units',
				type : 'hidden',
				display : true,
				tdIndex : 3,
				valueIndex : 4,
				validatorFile : '',
				style : ''
			}, {
				id : 'godownentryDetails[index].unitPrice',
				name : 'godownentryDetails[index].unitPrice',
				type : 'text',
				display : false,
				tdIndex : 4,
				valueIndex : 5,
				validatorFile : 'unitPrice',
				style : 'width:50px'
			},
			// 扩展字段
			{
				id : 'godownentryDetails[index].quantity',
				name : 'godownentryDetails[index].quantity',
				type : 'text',
				display : false,
				tdIndex : 5,
				valueIndex : -1,
				validatorFile : 'quantity',
				style : 'width:50px;'
			}, {
				id : 'godownentryDetails[index].orderCode',
				name : 'godownentryDetails[index].orderCode',
				type : 'text',
				display : false,
				tdIndex : 6,
				valueIndex : -1,
				validatorFile : 'orderCode',
				style : 'width:80px;'
			}]
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

	var mesg = "订单号格式不正确，最大32位。";
	var orderCodeRs = validatorInputFile('orderCode',
			/^[a-zA-Z0-9][a-zA-Z0-9_]{1,31}$/, mesg, "orderCodeTip",
			rowIndexArray, checkBoxFlag);
	validatorResult = orderCodeRs ? validatorResult : orderCodeRs;

	return validatorResult;
}