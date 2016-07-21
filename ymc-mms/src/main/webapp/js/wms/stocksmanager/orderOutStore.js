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

	var mesg = "优惠金额格式不正确，必须是数字且小位数点前位数最大5位。(如：0.01)";
	var discountPriceRs = validatorInputFile('discountPrice',
			// /^([0-9]{1,5}|0)(\.\d{2})?$/, mesg, "discountPriceTip",
			/^([0-9]{1,5}|0)(\.\d{2})?$/, mesg, "discountPriceTip",
			rowIndexArray, checkBoxFlag);
	validatorResult = discountPriceRs ? validatorResult : discountPriceRs;
	// 货品销售价格验证
	var mesg = "单价格式不正确，必须是数字且小位数点前位数最大5位。(如：0.01)";
	var unitPriceRs = validatorInputFile(
			'unitPrice',
			// /^([0-9]{1,5}|0)(\.\d{2})?$/, mesg, "unitPriceTip",
			// rowIndexArray,
			/^([0-9]{1,5}|0)(\.\d{2})?$/, mesg, "unitPriceTip", rowIndexArray,
			checkBoxFlag);
	validatorResult = unitPriceRs ? validatorResult : unitPriceRs;

	var mesg = "数量格式不正确，必须是非零的正整数且位数最大7位且位数最大7位。";
	// var quantityRs = validatorInputFile('quantity', /^\+?[1-9][0-9]*$/, mesg,
	var quantityRs = validatorInputFile('quantity', /^([0-9]{1,7}|0)(\d{0})?$/,
			mesg, "quantityTip", rowIndexArray, checkBoxFlag);
	validatorResult = quantityRs ? validatorResult : quantityRs;

	var mesg = "邮费格式不正确，必须是数字且小数点前位数最大5位。(如：0.01)";
	// var postageRs = validatorInputFile('postage',
	// /^([0-9]{1,5}|0)(\.\d{2})?$/,
	var postageRs = validatorInputFile('postage', /^([0-9]{1,5}|0)(\.\d{2})?$/,
			mesg, "postageTip", rowIndexArray, checkBoxFlag);
	validatorResult = postageRs ? validatorResult : postageRs;

	var mesg = "重量格式不正确，必须是数字且小数点前位数最大5位。(如：0.001)";
	// var weightRs = validatorInputFile('weight', /^(([1-9]\d*)|0)(\.\d{3})?$/,
	var weightRs = validatorInputFile('weight', /^([0-9]{1,5}|0)(\.\d{3})?$/,
			mesg, "weightTip", rowIndexArray, checkBoxFlag);
	validatorResult = weightRs ? validatorResult : weightRs;

	var mesg = "订单号格式不正确，最大32位。";
	var orderCodeRs = validatorInputFile('orderCode',
			/^[a-zA-Z0-9][a-zA-Z0-9_]{1,31}$/, mesg, "orderCodeTip",
			rowIndexArray, checkBoxFlag);
	validatorResult = orderCodeRs ? validatorResult : orderCodeRs;

	var mesg = "快递单号格式不正确，最大32位。";
	var expressCodeRs = validatorInputFile('expressCode',
			/^[a-zA-Z0-9][a-zA-Z0-9_]{1,31}$/, mesg, "expressCodeTip",
			rowIndexArray, checkBoxFlag);
	validatorResult = expressCodeRs ? validatorResult : expressCodeRs;

	return validatorResult;
}
var rowConfig = {
	fields : [{
				id : 'orderOutStoreDetails[index].commodityId',
				name : 'orderOutStoreDetails[index].commodityId',
				type : 'hidden',
				display : false,
				tdIndex : 0,
				valueIndex : 0,
				validatorFile : '',
				style : ''
			}, {
				id : 'orderOutStoreDetails[index].commodityCode',
				name : 'orderOutStoreDetails[index].commodityCode',
				type : 'hidden',
				display : true,
				tdIndex : 0,
				valueIndex : 1,
				validatorFile : '',
				style : ''
			}, {
				id : 'orderOutStoreDetails[index].goodsName',
				name : 'orderOutStoreDetails[index].goodsName',
				type : 'hidden',
				display : true,
				tdIndex : 1,
				valueIndex : 2,
				validatorFile : '',
				style : ''
			}, {
				id : 'orderOutStoreDetails[index].specification',
				name : 'orderOutStoreDetails[index].specification',
				type : 'hidden',
				display : true,
				tdIndex : 2,
				valueIndex : 3,
				validatorFile : '',
				style : ''
			}, {
				id : 'orderOutStoreDetails[index].units',
				name : 'orderOutStoreDetails[index].units',
				type : 'hidden',
				display : true,
				tdIndex : 3,
				valueIndex : 4,
				validatorFile : '',
				style : ''
			}, {
				id : 'orderOutStoreDetails[index].discountPrice',
				name : 'orderOutStoreDetails[index].discountPrice',
				type : 'text',
				display : false,
				tdIndex : 4,
				valueIndex : -1,
				validatorFile : 'discountPrice',
				style : 'width:50px;'
			}, {
				id : 'orderOutStoreDetails[index].unitPrice',
				name : 'orderOutStoreDetails[index].unitPrice',
				type : 'text',
				display : false,
				tdIndex : 5,
				valueIndex : 5,
				validatorFile : 'unitPrice',
				style : 'width:50px'
			},
			// 扩展字段
			{
				id : 'orderOutStoreDetails[index].quantity',
				name : 'orderOutStoreDetails[index].quantity',
				type : 'text',
				display : false,
				tdIndex : 6,
				valueIndex : -1,
				validatorFile : 'quantity',
				// maxlength:'1',
				style : 'width:50px;'
			}, {
				id : 'orderOutStoreDetails[index].postage',
				name : 'orderOutStoreDetails[index].postage',
				type : 'text',
				display : false,
				tdIndex : 7,
				valueIndex : -1,
				validatorFile : 'postage',
				style : 'width:50px;'
			}, {
				id : 'orderOutStoreDetails[index].weight',
				name : 'orderOutStoreDetails[index].weight',
				type : 'text',
				display : false,
				tdIndex : 8,
				valueIndex : -1,
				validatorFile : 'weight',
				style : 'width:50px;'
			}, {
				id : 'orderOutStoreDetails[index].orderCode',
				name : 'orderOutStoreDetails[index].orderCode',
				type : 'text',
				display : false,
				tdIndex : 9,
				valueIndex : -1,
				validatorFile : 'orderCode',
				style : 'width:50px;'
			}, {
				id : 'orderOutStoreDetails[index].expressCode',
				name : 'orderOutStoreDetails[index].expressCode',
				type : 'text',
				display : false,
				tdIndex : 10,
				valueIndex : -1,
				validatorFile : 'expressCode',
				style : 'width:50px;'
			}, {
				id : 'orderOutStoreDetails[index].logisticsCompanyCode',
				name : 'orderOutStoreDetails[index].logisticsCompanyCode',
				type : 'select',
				display : false,
				tdIndex : 11,
				valueIndex : 0,
				validatorFile : '',
				style : 'width:150px;'
			}]
}
function checkOrderCodeValidate(action) {
	var rowOrderCodes = selOrderCode("subTable");
	$.ajax({
				type : "POST",
				url : "checkOrderValidate.sc",
				data : {
					"rowOrderCodes" : rowOrderCodes
				},
				dataType : "json",
				success : function(resultMsg) {
					if (resultMsg.success == "true") {
						var mainForm = document.getElementById("mainForm");
						mainForm.action = action;
						mainForm.submit();
					} else {
						alert(resultMsg.msg);
					}
				}
			});
}

function selOrderCode(tableId) {
	var table = document.getElementById(tableId);
	var temp = "";
	var flag = ",";
	for (var i = 0; i < table.rows.length - 1; i++) {
		var orderCode = document.getElementById("orderOutStoreDetails[" + i
				+ "].orderCode").value;
		if (i == table.rows.length - 2) {
			flag = "";
		}
		var row = i + 1;
		temp += row + ":" + orderCode + flag;
	}
	return temp;
}