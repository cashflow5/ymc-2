// 正则表达式完成页面验证
var config = {
	form : "mainForm",
	submit : validateform,
	fields : []
}
var rowConfig = {
	fields : [ {
		id : 'allocationDetails[index].commodityCode',
		name : 'allocationDetails[index].commodityCode',
		type : 'hidden',
		display : true,
		tdIndex : 0,
		valueIndex : 0,
		validatorFile : '',
		style : ''
	}, {
		id : 'allocationDetails[index].goodsName',
		name : 'allocationDetails[index].goodsName',
		type : 'hidden',
		display : true,
		tdIndex : 1,
		valueIndex : 1,
		validatorFile : '',
		style : ''
	}, {
		id : 'allocationDetails[index].specification',
		name : 'allocationDetails[index].specification',
		type : 'hidden',
		display : true,
		tdIndex : 2,
		valueIndex : 2,
		validatorFile : '',
		style : ''
	}, {
		id : 'allocationDetails[index].units',
		name : 'allocationDetails[index].units',
		type : 'hidden',
		display : true,
		tdIndex : 3,
		valueIndex : 3,
		validatorFile : '',
		style : ''
	},
	// 扩展字段
	{
		id : 'allocationDetails[index].quantity',
		name : 'allocationDetails[index].quantity',
		type : 'text',
		display : false,
		tdIndex : 4,
		valueIndex : 4,
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

	var mesg = "数量格式不正确，必须是非零的正整数且位数最大7位。";
	var quantityRs = validatorInputFile('quantity', /^([0-9]{1,7}|0)(\d{0})?$/,
			mesg, "quantityTip", rowIndexArray, checkBoxFlag);
	validatorResult = quantityRs ? validatorResult : quantityRs;
	return validatorResult;
}
function toAddDetail() {
	openwindow(toAddDetailUrl, 950, 700, '新增明细');
}