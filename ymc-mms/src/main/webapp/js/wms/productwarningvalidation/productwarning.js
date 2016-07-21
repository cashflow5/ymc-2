function validatorProduct(checkBoxFlag) {
   var rowIndexArray=new Array();
   if(checkBoxFlag=="1"){
		$("input:checked[id!=checkall]").each(function(){
		var	rowIndex;
			if($.browser.msie){
				rowIndex=$(this).parent().parent().index() -1;
			}
			if($.browser.mozilla){
				rowIndex  = $(this).parent().parent().index() - 7;
			}
			rowIndexArray[rowIndexArray.length] = rowIndex;
		});
		
	}
   
	var validatorResult = true;
	//最低库存数量设置验证/^[1-9]d*|0$/
	var mesg = "最低预警数量格式不正确，必须是正整数。"
	var minWarningQuantityRs = validatorInputFile('minWarningQuantity',/^\d+$/,mesg,"minWarningQuantityTip",rowIndexArray,checkBoxFlag);
	validatorResult = minWarningQuantityRs ? validatorResult : minWarningQuantityRs;
	//最高库存数量设置验证
	var mesg = "最高预警数量格式不正确，必须是非零的正整数且位数最大7位。";
	var maxWarningQuantityRs = validatorInputFile('maxWarningQuantity',/^([0-9]{1,7}|0)(\d{0})?$/,mesg,"maxWarningQuantityTip",rowIndexArray,checkBoxFlag);
	validatorResult = maxWarningQuantityRs ? validatorResult : maxWarningQuantityRs;
	return validatorResult;
}

/**
 * 验证多行文本框信息
 * @param file  validatorFile 名称
 * @param reg	验证规则  正则表达式
 * @param errorMsg
 * @param mesTipId`
 */
function validatorInputFile(file,reg,errorMsg,mesTipId,rowIndexArray,checkBoxFlag){
	var validatorResult = true;
	var validatorNodes = $("input[validatorFile="+file+"]");
	var mesg = "";
	if(checkBoxFlag=="1"){
		for ( var i = 0; i < rowIndexArray.length; i++) {
			var row=rowIndexArray[i];
			var value = validatorNodes[row].value;
			if (!reg.test(value)) {
				mesg += "第" + (row+1) + "行 , ";
			}
		}
	}else{
		for ( var i = 0; i < validatorNodes.length; i++) {
			var value = validatorNodes[i].value;
			if (!reg.test(value)) {		
				mesg += "第" + (i + 1) + "行 , ";
			}
		}
	}
	$('#'+mesTipId).empty();
	if (mesg.length > 2) {
		$('#'+mesTipId).parent().css("display", "block");
		$('#'+mesTipId).append(mesg + errorMsg);
		$('#'+mesTipId).attr("class", "onerror");
		validatorResult = false;
	} else {
		$('#'+mesTipId).attr("class", "");
	}
	return validatorResult;
}
