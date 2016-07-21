// 正则表达式完成页面验证
var config = {
	form : "mainForm",
	submit : validateform,
	fields : []
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

var rowConfig = {
	fields : [{
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
			},
			// 扩展字段
			{
				id : 'godownentryDetails[index].quantity',
				name : 'godownentryDetails[index].quantity',
				type : 'text',
				display : false,
				tdIndex : 4,
				valueIndex : -1,
				validatorFile : 'quantity',
				style : 'width:50px;'
			}]
}