// 正则表达式完成页面验证
var config = {
	form : "mainForm",
	submit : validateform,
	fields : []
	
}
function addNameClass(cs) {
    var name = $("#appointmentCodeTip");
    name.removeClass("oncorrect").removeClass("onshow").removeClass("onerror");
    return name.addClass(cs);

}

function allocationCodeValidate() {
var allocationCode = document.getElementById("godownentry.appointmentCode").value;
//	var allocationCode = $("#godownentry.appointmentCode").val();
	 var name = $("#appointmentCodeTip");
	if (allocationCode != "") {
		$.ajax({
					type : "POST",
					url : "allocationCodeValidate.sc",
					data : {
						"allocationCode" : allocationCode

					},
					success : function(msg) {
						if ("false" == msg) {
						//addNameClass("appointmentCodeTip", "onerror");
						//name.removeClass("oncorrect").removeClass("onshow").removeClass("onerror");
						//addNameClass("appointmentCodeTip").html("申请单号错01误！");
						addNameClass("onerror").html("申请单号错误");
						
							allocationCodeFlag = false;
							
						}
						if ("true" == msg) {
						//	addNameClass("appointmentCodeTip", "oncorrect");
							addNameClass("purchaseCodeTip", "oncorrect");
							addNameClass("oncorrect").html("申请单号正确！");
							allocationCodeFlag = true;
						}
					}
				});
	} else {
		//addNameClass("appointmentCodeTip", "onerror");
		 // name.removeClass("oncorrect").removeClass("onshow").removeClass("onerror");
		//addNameClass("appointmentCodeTip").html("申请单号错03误！");.
		addNameClass("onerror").html("请输入申请单号！");
		allocationCodeFlag = false;
	}
	
}

function ajaxSubmit(url, formName) {
	var allocationCode = document.getElementById("godownentry.appointmentCode").value;
	if (allocationCode != "") {
		$.ajax({
					type : "POST",
					url : "allocationCodeValidate.sc",
					data : {
						"allocationCode" : allocationCode
					},
					success : function(msg) {
						if ("false" == msg) {
							addNameClass("appointmentCodeTip", "onerror");
							allocationCodeFlag = false;
						}
						if ("true" == msg) {
							addNameClass("appointmentCodeTip", "oncorrect");
							allocationCodeFlag = true;
							var mainForm = document.getElementById(formName);
							mainForm.action = url;
							mainForm.submit();
						}
					}
				});
	} else {
		addNameClass("appointmentCodeTip", "onerror");
	   addNameClass("onerror").html("请输入申请单号！");
		allocationCodeFlag = false;
	}
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

	return validatorResult;
}
