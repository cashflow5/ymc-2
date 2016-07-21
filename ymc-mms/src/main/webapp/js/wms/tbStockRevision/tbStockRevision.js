// 正则表达式完成页面验证
var config = {
	form : "mainForm",
	submit : validateform,
	fields : []
}
var rowConfig = {
	fields : [ {
		id : 'tbStockRevisionDetails[index].productNo',
		name : 'tbStockRevisionDetails[index].productNo',
		type : 'hidden',
		display : true,
		tdIndex : 0,
		valueIndex : 0,
		validatorFile : '',
		style : ''
	},
	{
		id : 'tbStockRevisionDetails[index].insideCode',
		name : 'tbStockRevisionDetails[index].insideCode',
		type : 'hidden',
		display : true,
		tdIndex : 1,
		valueIndex : 1,
		validatorFile : '',
		style : ''
	},
		{
		id : 'tbStockRevisionDetails[index].supplierCode',
		name : 'tbStockRevisionDetails[index].supplierCode',
		type : 'hidden',
		display : true,
		tdIndex : 2,
		valueIndex : 2,
		validatorFile : '',
		style : ''
	},
	{
		id : 'tbStockRevisionDetails[index].goodsName',
		name : 'tbStockRevisionDetails[index].goodsName',
		type : 'hidden',
		display : true,
		tdIndex : 3,
		valueIndex : 3,
		validatorFile : '',
		style : ''
	}, {
		id : 'tbStockRevisionDetails[index].specification',
		name : 'tbStockRevisionDetails[index].specification',
		type : 'hidden',
		display : true,
		tdIndex : 4,
		valueIndex : 4,
		validatorFile : '',
		style : ''
	}, {
		id : 'tbStockRevisionDetails[index].units',
		name : 'tbStockRevisionDetails[index].units',
		type : 'hidden',
		display : true,
		tdIndex : 5,
		valueIndex : 5,
		validatorFile : '',
		style : ''
	},

	// 扩展字段
	{
		id : 'tbStockRevisionDetails[index].qty',
		name : 'tbStockRevisionDetails[index].qty',
		type : 'text',
		display : false,
		tdIndex : 6,
		valueIndex : 6,
		validatorFile : 'quantity',
		style : 'width:50px;'
	} ]
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

	var mesg = "数量格式不正确，必须是非零的正整数且位数p最大7位。";


	var quantityRs = validatorInputFile('quantity', /^([-1-9]{1,7}|0)(\d{0})?$/,
			mesg, "quantityTip", rowIndexArray, checkBoxFlag);
	validatorResult = quantityRs ? validatorResult : quantityRs;
	return validatorResult;
}  

