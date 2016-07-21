/**
 * 验证多行文本框信息
 * @param file  validatorFile 名称
 * @param reg	验证规则  正则表达式
 * @param errorMsg
 * @param mesTipId
 */
function validatorProduct(viewFields){
	var validatorResult = true;
	var checkall = document.getElementById("checkall");
	var inputCheck = $("input:checked[id!=checkall]");
	var tempArr = [];
	var checkedBox = false ;
	inputCheck.each(function(){
		rowIndex=$(this).parent().parent().index();
		tempArr[tempArr.length] = rowIndex;
	});
	if(null != viewFields){
		for(var i = 0; i < viewFields.length; i++) {
			var input = viewFields[i];
			var msgId = input.vid + "Tip";
			if(checkall == null) {
				validatorResult = validatorInputFile(input.vid,input.vReg,input.vErrInfo,msgId);
				if(!validatorResult) {
					break;
				}
			} else {
				validatorResult = validatorInputArr(input.vid,input.vReg,input.vErrInfo,msgId,tempArr);
			}
		}
	}
	return validatorResult;
}

/**
 * 验证多行文本框信息
 * @param file  validatorFile 名称
 * @param reg	验证规则  正则表达式
 * @param errorMsg
 * @param mesTipId
 */
function validatorInputFile(file,reg,errorMsg,mesTipId){
	var validatorResult = true;
	var validatorNodes = $("input[id="+file+"]");
	var mesg = "";
	for ( var i = 0; i < validatorNodes.length; i++) {
		var value = validatorNodes[i].value;
		if (!reg.test(value)) mesg += "第" + (i + 1) + "行 , ";
		$('#'+mesTipId).empty();
		if (mesg.length > 2) {
			$('#'+mesTipId).parent().css("display", "inline-block");
			$('#'+mesTipId).append(mesg + errorMsg);
			$('#'+mesTipId).attr("class", "onerror");
			validatorResult = false;
			break;
		} else {
			$('#'+mesTipId).attr("class", "");
		}
	}
	//alert("validatorInputFile : " +validatorResult +" mesg.length:"+mesg.length);
	return validatorResult;
}

/**
 * 验证多行文本框信息
 * @param file  validatorFile 名称
 * @param reg	验证规则  正则表达式
 * @param errorMsg
 * @param mesTipId
 */
function validatorInputArr(file,reg,errorMsg,mesTipId,checkArr){
	var validatorResult = true;
	var validatorNodes = $("input[id="+file+"]");
	var mesg = "";
	for ( var i = 0; i < checkArr.length; i++) {
		var rowIndex = checkArr[i];
		var value = validatorNodes[rowIndex].value;
		if (!reg.test(value)) mesg += "第" + (rowIndex + 1) + "行 , ";
	}
	$('#'+mesTipId).empty();
	if (mesg.length > 2) {
		$('#'+mesTipId).parent().css("display", "inline-block");
		$('#'+mesTipId).append(mesg + errorMsg);
		$('#'+mesTipId).attr("class", "onerror");
		validatorResult = false;
	} else {
		$('#'+mesTipId).attr("class", "");
	}
	
	return validatorResult;
}