// 正则表达式完成页面验证
var config = {
	form : "mainForm",
	submit : validateform,
	fields : []
}
var rowConfig = {
	fields : [ 
		{
		id : 'sampleLendDetails[index].id',
		name : 'sampleLendDetails_temp',
		type : 'hidden',
		display : false,
		tdIndex : 0,
		valueIndex : 0,
		validatorFile : '',
		style : ''
	   }
		,
		{
		id : 'sampleLendDetails[index].commodityCode',
		name : 'sampleLendDetails[index].commodityCode',
		type : 'hidden',
		display : false,
		tdIndex : 1,
		valueIndex : 1,
		validatorFile : '',
		style : ''
	}, {
		id : 'sampleLendDetails[index].goodsName',
		name : 'sampleLendDetails[index].goodsName',
		type : 'hidden',
		display : false,
		tdIndex : 2,
		valueIndex : 2,
		validatorFile : '',
		style : ''
	}, {
		id : 'sampleLendDetails[index].specification',
		name : 'sampleLendDetails[index].specification',
		type : 'hidden',
		display : false,
		tdIndex : 3,
		valueIndex : 3,
		validatorFile : '',
		style : ''
	},  {
		id : 'sampleLendDetails[index].units',
		name : 'sampleLendDetails[index].units',
		type : 'hidden',
		display : false,
		tdIndex : 4,
		valueIndex : 4,
		validatorFile : '',
		style : ''
	},{
		id : 'sampleLendDetails[index].styleNo',
		name : 'sampleLendDetails[index].styleNo',
		type : 'hidden',
		display : false,
		tdIndex : 5,
		valueIndex : 5,
		validatorFile : '',
		style : ''
	},
	// 扩展字段

		{
		id : 'sampleLendDetails[index].sysQty',
		name : 'sampleLendDetails[index].sysQty',
		type : 'hidden',
		display : false,
		tdIndex : 6,
		valueIndex : 6,
		validatorFile : '',
		style : 'width:50px;'
	},
		{
		id : 'sampleLendDetails[index].qty',
		name : 'sampleLendDetails[index].qty',
		type : 'hidden',
		display : false,
		tdIndex : 7,
		valueIndex : 7,
		validatorFile : 'qty',
		style : 'width:50px;'
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

	var mesg = "数量格式不正确，必须是非零的正整数且位数最大7位。";


	var quantityRs = validatorInputFile('qty', /^([0-9]{1,7}|0)(\d{0})?$/,
			mesg, "quantityTip", rowIndexArray, checkBoxFlag);
	validatorResult = quantityRs ? validatorResult : quantityRs;
	return validatorResult;
}
