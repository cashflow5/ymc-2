function submitForm(formId, buttonId) {
	$("#" + formId).submit();
	$("#" + buttonId).attr("disabled", "disabled");
}
//选择淘宝退款记录
function selectTaoBaoMoney(totalMoney) {
	$("#totalBackAmount").val(totalMoney);
	$("#hidebank1").hide();
	$("#hidebank2").hide();
	$("#hidebank3").hide();
	$("#hidebank4").hide();
	$("#selectBank1").attr("checked", false);
	$("#selectBank2").attr("checked", false);
	$("#flag").val("1");
	$("#refundWay").val("1");
	$("#payway").html("线上支付");
}


//其他退款验证提交
function configSaleRefund2(formId, buttonId) {
	var flag = $("#flag").val();
	if(flag!='1') {
		var bankAddress = $("#bankAddress").val();
		var accountNo = $("#accountNo").val();
		var accountName = $("#accountName").val();
		if (bankAddress == '') {
			alert("退款银行不能为空!");
			$("#bankAddress").focus();
			return false;
		}
		if (accountNo == '') {
			alert("退款账号不能为空!");
			$("#accountNo").focus();
			return false;
		}
		if (accountName == '') {
			alert("收款人不能为空!");
			$("#accountName").focus();
			return false;
		}
	}else {
		//判断是否选择单选按钮
		var selectFlag = false;
		$("[name=refuseId]:radio").each(function(){
			if($(this).attr("checked")){
				selectFlag = true;
			}
		});
		if(!selectFlag) {
			alert("请选择一个退款记录");
			$("[name=refuseId]:radio").focus();
			return false;
		}
	}
	var totalBackAmount = $("#totalBackAmount").val();
	var postage = $("#postage").val();
	var otherAmount = $("#otherAmount").val();
	var refundNote = $("#refundNote").val();
	if (totalBackAmount == '') {
		alert("申请退款金额不能为空!");
		$("#totalBackAmount").focus();
		return false;
	}
	if (postage == '') {
		alert("退邮费不能为空!");
		$("#postage").focus();
		return false;
	}
	if (otherAmount == '') {
		alert("其他退款不能为空!");
		$("#otherAmount").focus();
		return false;
	}
	if(refundNote == '') {
		alert("备注不能为空!");
		$("#refundNote").focus();
		return false;
	}
	
	$("#" + formId).submit();
	$("#" + buttonId).attr("disabled", "disabled");
}

function configSaleRefund(formId, buttonId, flag) {
	if(flag!='1') {
		var bankAddress = $("#bankAddress").val();
		var accountNo = $("#accountNo").val();
		var accountName = $("#accountName").val();
		if (bankAddress == '') {
			alert("退款银行不能为空!");
			$("#bankAddress").focus();
			return false;
		}
		if (accountNo == '') {
			alert("退款账号不能为空!");
			$("#accountNo").focus();
			return false;
		}
		if (accountName == '') {
			alert("收款人不能为空!");
			$("#accountName").focus();
			return false;
		}
	}
	var totalBackAmount = $("#totalBackAmount").val();
	var postage = $("#postage").val();
	var otherAmount = $("#otherAmount").val();
	var refundNote = $("#refundNote").val();
	if (totalBackAmount == '') {
		alert("申请退款金额不能为空!");
		$("#totalBackAmount").focus();
		return false;
	}
	if (postage == '') {
		alert("退邮费不能为空!");
		$("#postage").focus();
		return false;
	}
	if (otherAmount == '') {
		alert("其他退款不能为空!");
		$("#otherAmount").focus();
		return false;
	}
	if(refundNote == '') {
		alert("备注不能为空!");
		$("#refundNote").focus();
		return false;
	}
	$("#" + formId).submit();
	$("#" + buttonId).attr("disabled", "disabled");
}

function configPostageRefund(formId, buttonId) {
	var bankAddress = $("#bankAddress").val();
	var accountNo = $("#accountNo").val();
	var accountName = $("#accountName").val();
	var totalBackAmount = $("#totalBackAmount").val();
	var postage = $("#postage").val();
	var otherAmount = $("#otherAmount").val();
	if (bankAddress == '') {
		alert("退款银行不能为空!");
		$("#bankAddress").focus();
		return false;
	}
	if (accountNo == '') {
		alert("退款账号不能为空!");
		$("#accountNo").focus();
		return false;
	}
	if (accountName == '') {
		alert("收款人不能为空!");
		$("#accountName").focus();
		return false;
	}
	if (totalBackAmount == '') {
		alert("申请退款金额不能为空!");
		$("#totalBackAmount").focus();
		return false;
	}
	if (postage == '') {
		alert("退邮费不能为空!");
		$("#postage").focus();
		return false;
	}
	if (otherAmount == '') {
		alert("其他退款不能为空!");
		$("#otherAmount").focus();
		return false;
	}
	$("#" + formId).submit();
	$("#" + buttonId).attr("disabled", "disabled");
}

// 验证只能输入数字onkeydown="return checkNumber()"
function checkNumber() {
	var code = event.keyCode;
	if (code == 8 || code == 109 || code == 110|| code == 190 ) {
		return true;
	} else if (code > 47 && code < 58) {
		return true;
	}  else if (code >95 && code < 106) {
		return true;
	}else {
		return false;
	}
	return true;
}

function countMoney(productPrice,flag) {
	var postage = $("#postage").val();
	postage = $.trim(postage);
	var otherAmount = $("#otherAmount").val();
	otherAmount = $.trim(otherAmount);
	if(productPrice != "") {
		productPrice = parseFloat(productPrice);
	}else {
		productPrice = 0;
	}
	if(postage != "") {
		postage = parseFloat(postage);
	}else {
		postage = 0;
	}
	if(otherAmount != "") {
		otherAmount = parseFloat(otherAmount);
	}else {
		otherAmount = 0;
	}
	//如果是1为有值 2为没有值
	if(flag == '1') {
		var refundTotalPrice = $("#totalBackAmount").val();
		var other = refundTotalPrice - (productPrice+postage);
		other = parseFloat(other.toFixed(2));
		$("#otherAmount").val(other);
	}else if(flag == '2') {
		var countPrice = postage + productPrice + otherAmount;
		countPrice = parseFloat(countPrice.toFixed(2));
		$("#totalBackAmount").val(countPrice);
	}
}
//邮费里面的计算
function jspostprice(flag) {
	var flag = $("#flag").val();
	var postage = $("#postage").val();
	postage = $.trim(postage);
	var otherAmount = $("#otherAmount").val();
	otherAmount = $.trim(otherAmount);
	var totalBackAmount = $("#totalBackAmount").val();
	if(postage != "") {
		postage = parseFloat(postage);
	}else {
		postage = 0;
	}
	if(otherAmount != "") {
		otherAmount = parseFloat(otherAmount);
	}else {
		otherAmount = 0;
	}
	if(totalBackAmount != "") {
		totalBackAmount = parseFloat(totalBackAmount);
	}else {
		totalBackAmount = 0;
	}
	if(flag=='') {
		var count = otherAmount+postage;
		count = count.toFixed(2);
		$("#totalBackAmount").val(count);
	}else {
		var count = totalBackAmount-postage;
		$("#otherAmount").val(count);
	}
}
function selectBankAddress(message) {
	if(message=='1') {
		$("#bankAddress").val("支付宝");
	}else if(message=='2') {
		$("#bankAddress").val("");
	}
}