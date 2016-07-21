// 正则表达式完成页面验证
var config = {
	form : "mainForm",
	submit : validateform,
	fields : [{
				name : 'allocationOutStore.totalWeight',
				msgTip : 'totalWeightTip',
				regExp : /^(([1-9]\d*)|0)(\.\d{3})?$/,
				allownull : false,
				defaultMsg : '',
				rightMsg : '',
				errorMsg : ''
			}]
}

function allocationCodeValidate() {
	var allocationCode = document
			.getElementById("allocationOutStore.allocationCode").value;
	if (allocationCode != "") {
		$.ajax({
					type : "POST",
					url : "allocationCodeValidate.sc",
					data : {
						"allocationCode" : allocationCode

					},
					success : function(msg) {
						if ("false" == msg) {
							addNameClass("allocationCodeTip", "onerror");
							allocationCodeFlag = false;
						}
						if ("true" == msg) {
							addNameClass("allocationCodeTip", "oncorrect");
							allocationCodeFlag = true;
						}
					}
				});
	} else {
		addNameClass("allocationCodeTip", "onerror");
		allocationCodeFlag = false;
	}
}

function ajaxSubmit(url, formName) {
	var allocationCode = document
			.getElementById("allocationOutStore.allocationCode").value;
	if (allocationCode != "") {
		$.ajax({
					type : "POST",
					url : "allocationCodeValidate.sc",
					data : {
						"allocationCode" : allocationCode
					},
					success : function(msg) {
						if ("false" == msg) {
							addNameClass("allocationCodeTip", "onerror");
							allocationCodeFlag = false;
						}
						if ("true" == msg) {
							addNameClass("allocationCodeTip", "oncorrect");
							allocationCodeFlag = true;
							var mainForm = document.getElementById(formName);
							mainForm.action = url;
							mainForm.submit();
						}
					}
				});
	} else {
		addNameClass("allocationCodeTip", "onerror");
		allocationCodeFlag = false;
	}
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

	return validatorResult;
}

var rowConfig = {
	fields : [{
				id : 'allocationOutStoreDetails[index].commodityId',
				name : 'allocationOutStoreDetails[index].commodityId',
				type : 'hidden',
				display : false,
				tdIndex : 0,
				valueIndex : 0,
				validatorFile : '',
				style : ''
			}, {
				id : 'allocationOutStoreDetails[index].commodityCode',
				name : 'allocationOutStoreDetails[index].commodityCode',
				type : 'hidden',
				display : true,
				tdIndex : 0,
				valueIndex : 1,
				validatorFile : '',
				style : ''
			}, {
				id : 'allocationOutStoreDetails[index].goodsName',
				name : 'allocationOutStoreDetails[index].goodsName',
				type : 'hidden',
				display : true,
				tdIndex : 1,
				valueIndex : 2,
				validatorFile : '',
				style : ''
			}, {
				id : 'allocationOutStoreDetails[index].specification',
				name : 'allocationOutStoreDetails[index].specification',
				type : 'hidden',
				display : true,
				tdIndex : 2,
				valueIndex : 3,
				validatorFile : '',
				style : ''
			}, {
				id : 'allocationOutStoreDetails[index].units',
				name : 'allocationOutStoreDetails[index].units',
				type : 'hidden',
				display : true,
				tdIndex : 3,
				valueIndex : 4,
				validatorFile : '',
				style : ''
			}, {
				id : 'allocationOutStoreDetails[index].unitPrice',
				name : 'allocationOutStoreDetails[index].unitPrice',
				type : 'text',
				display : false,
				tdIndex : 4,
				valueIndex : 5,
				validatorFile : 'unitPrice',
				style : 'width:50px'
			},
			// 扩展字段
			{
				id : 'allocationOutStoreDetails[index].quantity',
				name : 'allocationOutStoreDetails[index].quantity',
				type : 'text',
				display : false,
				tdIndex : 5,
				valueIndex : -1,
				validatorFile : 'quantity',
				style : 'width:50px;'
			}]
}
