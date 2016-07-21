// 正则表达式完成页面验证
var config = {
	form : "mainForm",
	submit : validateform,
	fields : [{
				name : 'godownentry.appointmentCode',
				msgTip : 'appointmentCodeTip',
				regExp : /^[a-zA-Z0-9][a-zA-Z0-9_]{1,31}$/,
				allownull : false,
				defaultMsg : '',
				rightMsg : '',
				errorMsg : ''
			}]
}

function purchaseCodeValidate() {
	var purchaseCode = document.getElementById("godownentry.purchaseCode").value;
	if (purchaseCode != "") {
		$.ajax({
					type : "POST",
					url : "purchaseCodeValidate.sc",
					data : {
						"purchaseCode" : purchaseCode

					},
					success : function(msg) {
						if ("false" == msg) {
							addNameClass("purchaseCodeTip", "onerror");
							purchaseCodeFlag = false;
						}
						if ("true" == msg) {
							addNameClass("purchaseCodeTip", "oncorrect");
							purchaseCodeFlag = true;
						}
					}
				});
	} else {
		addNameClass("purchaseCodeTip", "onerror");
		purchaseCodeFlag = false;
	}
}

function purchaseCodeValidate2() {
    var purchaseCode = document.getElementById("godownentry.purchaseCode").value;
    if (purchaseCode != "") {
        $.ajax({
                    type : "POST",
                    url : "purchasecodevalidate2.sc",
                    data : {
                        "purchaseCode" : purchaseCode

                    },
                    success : function(result) {
                        if ("" == result || null == result) {
                            addNameClass("purchaseCodeTip", "onerror");
                            purchaseCodeFlag = false;
                        }else {
                            addNameClass("purchaseCodeTip", "oncorrect");
                            var rse = result.split(",");
                            selectSedVal("storageTypeSecond",rse[0]);
                            selectSedVal("warehouseId",rse[1]);
                            selectSedVal("sCode",rse[2]);
                           
                            $("#storageTypeId").val(rse[0]);
                            $("#wareId").val(rse[1]);
                            $("#supplierCode").val(rse[2]);
                            $("#supplierSp").val(rse[2]);
                        /*  $("#storageTypeSecond").val(rse[0]);
                            $("#warehouseId").val(rse[1]);
                            $("#supplierCode").val(rse[2]);
                        */
                            purchaseCodeFlag = true;
                        }
                    }
                });
    } else {
        addNameClass("purchaseCodeTip", "onerror");
        purchaseCodeFlag = false;
    }
}

 function selectSedVal(selectId,selectVal){
                                var ops=document.getElementById(selectId).options;
                                for(var i=0;i<ops.length;i++){
                                    if(ops[i].value == selectVal){
                                        ops[i].selected=true;
                                        break;
                                    }
                                }
                            }

                            
function storageTypeValidate()
{
   var storageTypeVal=$("#storageTypeId").val();
   //获取单据类型是否是自采 (102)
   if(storageTypeVal=="102")
    {   $("#deductionRateTh").hide();
       $(".td0").find("input").val('0.01');
        $(".td0").find("input").hide();
    }else
    {
        $("#deductionRateTh").show();
	   //$(".td0").find("input").val('');
	    $(".td0").find("input").show();
     }
}
 
//定时执行
setInterval('storageTypeValidate()',100);

function ajaxSubmit(url, formName) {
	var purchaseCode = document.getElementById("godownentry.purchaseCode").value;
	if (purchaseCode != "") {
		$.ajax({
					type : "POST",
					url : "purchaseCodeValidate.sc",
					data : {
						"purchaseCode" : purchaseCode
					},
					success : function(msg) {
						if ("false" == msg) {
							addNameClass("purchaseCodeTip", "onerror");
							purchaseCodeFlag = false;
						}
						if ("true" == msg) {
							addNameClass("purchaseCodeTip", "oncorrect");
							purchaseCodeFlag = true;
							var mainForm = document.getElementById(formName);
							mainForm.action = url;
							mainForm.submit();
						}
					}
				});
	} else {
		addNameClass("purchaseCodeTip", "onerror");
		purchaseCodeFlag = false;
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
			}, {
				id : 'godownentryDetails[index].deductionRate',
				name : 'godownentryDetails[index].deductionRate',
				type : 'text',
				display : false,
				tdIndex : 6,
				valueIndex : -1,
				validatorFile : 'deductionRate',
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

	var mesg = "数量格式不正确，必须是非零的正整数且位数最大7位。";
	var quantityRs = validatorInputFile('quantity', /^([0-9]{1,7}|0)(\d{0})?$/, mesg,
			"quantityTip", rowIndexArray, checkBoxFlag);
	validatorResult = quantityRs ? validatorResult : quantityRs;

	var mesg = "扣点比率格式不正确，必须是数字。(如：0.01-0.99或1)";
	var deductionRateRs = validatorInputFile('deductionRate',
	//		/^([0][.]\d{2})|([1])$/, mesg, "deductionRateTip",
		   /^(1[0]{2}|1|(0.[0-9][1-9])|(0.[1-9][0-9]))$/, mesg, "deductionRateTip",
	
			rowIndexArray, checkBoxFlag);
	
	//^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$ && ^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$

	validatorResult = deductionRateRs ? validatorResult : deductionRateRs;

	return validatorResult;
}