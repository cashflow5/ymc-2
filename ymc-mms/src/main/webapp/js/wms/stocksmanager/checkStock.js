// 正则表达式完成页面验证
var config = {
	form : "mainForm",
	submit : validateform,
	fields : []
}
var rowConfig = {
	fields : [{
				id : 'checkStorageDetails[index].commodityId',
				name : 'checkStorageDetails[index].commodityId',
				type : 'hidden',
				display : false,
				tdIndex : 0,
				valueIndex : 0,
				validatorFile : '',
				style : ''
			}, {
				id : 'checkStorageDetails[index].commodityCode',
				name : 'checkStorageDetails[index].commodityCode',
				type : 'hidden',
				display : true,
				tdIndex : 0,
				valueIndex : 1,
				validatorFile : '',
				style : ''
			}, {
				id : 'checkStorageDetails[index].goodsName',
				name : 'checkStorageDetails[index].goodsName',
				type : 'hidden',
				display : true,
				tdIndex : 1,
				valueIndex : 2,
				validatorFile : '',
				style : ''
			}, {
				id : 'checkStorageDetails[index].specification',
				name : 'checkStorageDetails[index].specification',
				type : 'hidden',
				display : true,
				tdIndex : 2,
				valueIndex : 3,
				validatorFile : '',
				style : ''
			}, {
				id : 'checkStorageDetails[index].units',
				name : 'checkStorageDetails[index].units',
				type : 'hidden',
				display : true,
				tdIndex : 3,
				valueIndex : 4,
				validatorFile : '',
				style : ''
			}, {
				id : 'checkStorageDetails[index].unitPrice',
				name : 'checkStorageDetails[index].unitPrice',
				type : 'text',
				display : false,
				tdIndex : 4,
				valueIndex : 5,
				validatorFile : 'unitPrice',
				style : 'width:50px'
			},
			// 扩展字段
			{
				id : 'checkStorageDetails[index].realCheckQuantity',
				name : 'checkStorageDetails[index].realCheckQuantity',
				type : 'text',
				display : false,
				tdIndex : 5,
				valueIndex : -1,
				validatorFile : 'realCheckQuantity',
				style : 'width:50px;'
			}, {
				id : 'checkStorageDetails[index].displayQuantity',
				name : 'checkStorageDetails[index].displayQuantity',
				type : 'text',
				display : false,
				tdIndex : 6,
				valueIndex : -1,
				validatorFile : 'displayQuantity',
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
	var mesg = "单价格式不正确，必须是数字且小位数点前位数最大5位。(如：0.01)";
	var unitPriceRs = validatorInputFile('unitPrice',
			/^([0-9]{1,5}|0)(\.\d{2})?$/, mesg, "unitPriceTip", rowIndexArray,
			checkBoxFlag);
	validatorResult = unitPriceRs ? validatorResult : unitPriceRs;

	var mesg = "实盘数量格式不正确，必须是正整数且最大位7位。";
	var realCheckQuantityRs = validatorInputFile('realCheckQuantity',
			/^([0-9]{1,7}|0)(\d{0})?$/, mesg, "realCheckQuantityTip", rowIndexArray,
			checkBoxFlag);
	validatorResult = realCheckQuantityRs
			? validatorResult
			: realCheckQuantityRs;

	var mesg = "账面数量格式不正确，必须是正整数且最大位数7位。";
	var displayQuantityRs = validatorInputFile('displayQuantity',
			/^([0-9]{1,7}|0)(\d{0})?$/, mesg, "displayQuantityTip", rowIndexArray,
			checkBoxFlag);
	validatorResult = displayQuantityRs ? validatorResult : displayQuantityRs;

	return validatorResult;
}

function checkStorageValidate() {
	var warehouseId = document.getElementById("warehouseId").value;
	var commodityCodes = selectAllCommodityCode("subTable");
	$.ajax({
		type : "POST",
		url : "checkStorageValidate.sc",
		data : {
			"warehouseId" : warehouseId,
			"commodityCodes" : commodityCodes
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == "true") {
				var mainForm = document.getElementById("mainForm");
				mainForm.action = "c_checkStorage.sc";
				mainForm.submit();
			} else {
				alert(resultMsg.msg);
			}
		}
	});
}

function selectAllCommodityCode(tableId) {
	var strTemp = "";
	$("#" + tableId + " tr:not(:first)").each(function() {
				strTemp += $(this).find("td:eq(0)").text() + ",";
			});
	return strTemp;
}