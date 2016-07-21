$(document).ready(function() {
	$("#barcode").keydown(function(e) {
		if (e.keyCode == 13) {
			toShowProductCheck();
		}
	});
	document.getElementById("expressCodeForCheck").readOnly = true;
	document.getElementById("orderNo").readOnly = true;
	document.getElementById("orgOrderNo").readOnly = true;
	document.getElementById("expressCompany").readOnly = true;
	document.getElementById("createTime").readOnly = true;
	document.getElementById("warehouseName").readOnly = true;
	document.getElementById("sendTime").readOnly = true;

	document.getElementById("expressCodeForCheck").focus();

	doQueryApplyProductByOrderNo();

	var isPayArrive = document.getElementById("tempIsPayArrive").value;
	radioAndCheckboxChecked("isPayArrive", isPayArrive);

});
/**
 * 审核通过
 */
function doPassCheck() {
	doCheckIsDiff();
}
/**
 * 检查是否异常收货
 */
function doCheckIsDiff() {
	var mainId = document.getElementById("mainId").value;
	if (mainId == "") {
		alert("请先扫描输入相关信息及填写质检结果!");
		return;
	}
	$.ajax({
		type : "POST",
		url : doCheckIsDiffUrl,
		data : {
			"mainId" : mainId
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == true) {
				if (confirm(resultMsg.msg)) {
					doPassForSec();// 二次审核提交
				}
			} else {
				doPass();
			}
		}
	});
}
function doPass() {
	var mainId = document.getElementById("mainId").value;
	if (mainId == "") {
		alert("请先扫描输入相关信息及填写质检结果!");
		return;
	}
	if (!confirm('确定后该数据将不允许再被修改，你确定要继续吗？')) {
		return;
	}
	var isPayArriveBtn = dg.curWin.document.getElementsByName("isPayArrive");// 到付
	var isPayArrive = "noPayArrive";
	if (isPayArriveBtn[0].checked) {
		isPayArrive = "payArrive";
	}
	var expressPrice = document.getElementById("expressPrice").value;

	var checker = document.getElementById("checker").value;

	$.ajax({
		type : "POST",
		url : doPassCheckUrl,
		data : {
			"mainId" : mainId,
			"isPayArrive" : isPayArrive,
			"expressPrice" : expressPrice,
			"checker" : checker
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == true) {
				reloadPage();
			} else {
				alert(resultMsg.msg);
			}
		}
	});
}
/**
 * 二次审核提交
 */
function doPassForSec() {
	var mainId = document.getElementById("mainId").value;
	if (mainId == "") {
		alert("请先扫描输入相关信息及填写质检结果!");
		return;
	}
	var isPayArrive = document.getElementById("isPayArrive").value;
	var expressPrice = document.getElementById("expressPrice").value;
	var checker = document.getElementById("checker").value;
	$.ajax({
		type : "POST",
		url : doPassForSecUrl,
		data : {
			"mainId" : mainId,
			"isPayArrive" : isPayArrive,
			"expressPrice" : expressPrice,
			"checker" : checker
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == true) {
				reloadPage();
			} else {
				alert(resultMsg.msg);
			}
		}
	});
}
function doQueryApplyProduct(detailId) {
	var mainId = document.getElementById("mainId").value;
	var orderNo = document.getElementById("orderNo").value;
	document.getElementById('myIframe').src = doQueryApplyProductUrl
			+ "?orderNo=" + orderNo + "&mainId=" + mainId + "&detailId="
			+ detailId;
}

/**
 * 获取申请货品信息
 */
function doQueryApplyProductByOrderNo() {
	var expressCode = document.getElementById("expressCodeForCheck").value;
	if (expressCode == "") {
		alert("请输入快递单号！");
		return;
	}
	var orderNo = document.getElementById("orderNo").value;
	orderNo = trim(orderNo);
	document.getElementById("orderNo").value = orderNo;
	if (orderNo == "") {
		alert("请输入订单号！");
		return;
	}
	$
			.ajax({
				type : "POST",
				url : checkOrderNoUrl,
				data : {
					"orderNo" : orderNo
				},
				dataType : "json",
				success : function(resultMsg) {
					if (resultMsg.success == true) {
						var orderInfoVo = resultMsg.reObj;// 原始订单号
						document.getElementById("orgOrderNo").value = orderInfoVo.orgOrderNo;
						document.getElementById("sendWarehouseName").innerHTML = orderInfoVo.sendWarehouseName;
						document.getElementById("sendTime").innerHTML = orderInfoVo.sendTime;
						document.getElementById("name").innerHTML = orderInfoVo.name;
						document.getElementById("phone").innerHTML = orderInfoVo.phone;
						document.getElementById("phoneSecond").innerHTML = orderInfoVo.phoneSecond;
						document.getElementById("userName").innerHTML = orderInfoVo.userName;
						document.getElementById("address").innerHTML = orderInfoVo.address;

						document.getElementById("orderNo").readOnly = true;
						document.getElementById("orderNo").style.background = "#EEEEEE";

						doQueryApplyProduct();

					} else {
						alert(resultMsg.msg);
					}
				}
			});
}
/**
 * 
 */
function toShowProductCheck() {
	// 是否已确定
	var status = document.getElementById("status").value;
	if (status == "CONFIRMED") {
		return;
	}
	var expressCode = document.getElementById("expressCodeForCheck").value;
	if (expressCode == "") {
		alert("请输入快递单号！");
		return;
	}
	var orderNo = document.getElementById("orderNo").value;
	if (orderNo == "") {
		alert("请输入订单号！");
		return;
	}
	var barcode = document.getElementById("barcode").value;
	barcode = trim(barcode);
	document.getElementById("barcode").value = barcode;
	if (barcode == "") {
		alert("请输入条码！");
		return;
	}
	var mainId = document.getElementById("mainId").value;
	$.ajax({
		type : "POST",
		url : checkBarcodeUrl,
		data : {
			"barcode" : barcode
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == true) {
				if (mainId == '') {
					doCheckBrandSendByOther(orderNo, barcode, mainId);
				} else {
					doCheckComplete(mainId, orderNo, barcode);
				}
			} else {
				alert(resultMsg.msg);
			}
		}
	});
}
/**
 * 检查是否已完成质检
 * 
 * @param mainId
 * @param orderNo
 */
function doCheckComplete(mainId, orderNo, barcode) {
	$.ajax({
		type : "POST",
		url : doCheckCompleteUrl,
		data : {
			"orderNo" : orderNo
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == true) {
				// 已完成质检
				alert(resultMsg.msg);
			} else {
				doCheckBrandSendByOther(orderNo, barcode, mainId);
			}
		}
	});
}
/**
 * 检查是否品牌代发货
 * 
 * @param orderNo
 * @param barcode
 */
function doCheckBrandSendByOther(orderNo, barcode, mainId) {
	$.ajax({
		type : "POST",
		url : doCheckBrandSendByOtherUrl,
		data : {
			"orderNo" : orderNo,
			"barcode" : barcode
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == true) {
				alert(resultMsg.msg);
			}
			doCheckSet(barcode, mainId);
		}
	});
}
/**
 * 检查是否套装
 * 
 * @param barcode
 * @param mainId
 */
function doCheckSet(barcode, mainId) {
	$.ajax({
		type : "POST",
		url : doCheckSetUrl,
		data : {
			"barcode" : barcode
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == true) {
				alert(resultMsg.msg);
			}
			openwindow(toShowProductCheckUrl + "?barcode=" + barcode
					+ "&mainId=" + mainId, 850, 700, '退换货结果录入');
		}
	});
}
/**
 * 
 */
function showRealQualityCheckInfo(mainId, detailId) {
	if (detailId == null || detailId == "") {
		document.getElementById("productNoSpan").innerHTML = "";
		document.getElementById("barcodeSpan").innerHTML = "";
		document.getElementById("supplierStyleNoSpan").innerHTML = "";
		document.getElementById("goodsNameSpan").innerHTML = "";

		document.getElementById("questionDescriptionSpan").innerHTML = "";

		document.getElementById("questionTypeSpan").innerHTML = "";
		document.getElementById("questionReasonSpan").innerHTML = "";
		document.getElementById("questionBelongSpan").innerHTML = "";
		document.getElementById("inTypeSpan").innerHTML = "";
		document.getElementById("descriptionSpan").innerHTML = "";
		return;
	}
	$
			.ajax({
				type : "POST",
				url : showRealQualityCheckInfoUrl,
				data : {
					"detailId" : detailId,
					"mainId" : mainId
				},
				dataType : "json",
				success : function(resultMsg) {
					if (resultMsg.success == true) {
						var realQualityCheckInfoVo = resultMsg.reObj;
						document.getElementById("productNoSpan").innerHTML = realQualityCheckInfoVo.productNo;
						document.getElementById("barcodeSpan").innerHTML = realQualityCheckInfoVo.barcode;
						document.getElementById("supplierStyleNoSpan").innerHTML = realQualityCheckInfoVo.supplierStyleNo;
						document.getElementById("goodsNameSpan").innerHTML = realQualityCheckInfoVo.goodsName;
						document.getElementById("customer_backremark").innerHTML = resultMsg.reCode;
						document.getElementById("questionDescriptionSpan").innerHTML = realQualityCheckInfoVo.questionDescription;

						document.getElementById("questionTypeSpan").innerHTML = realQualityCheckInfoVo.questionType;
						document.getElementById("questionReasonSpan").innerHTML = realQualityCheckInfoVo.questionReason;
						document.getElementById("questionBelongSpan").innerHTML = realQualityCheckInfoVo.questionBelong;
						document.getElementById("inTypeSpan").innerHTML = realQualityCheckInfoVo.inType;
						document.getElementById("descriptionSpan").innerHTML = realQualityCheckInfoVo.description;
					} else {
						alert(resultMsg.msg);
					}
				}
			});
}

function doRemove() {
	if (!confirm('确定删除？')) {
		return;
	}
	var mainId = document.getElementById("mainId").value;
	if (mainId == "") {
		alert("请先扫描输入相关信息及填写质检结果!");
		return;
	}
	$.ajax({
		type : "POST",
		url : doRemoveUrl,
		data : {
			"mainId" : mainId
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == true) {
				reloadPage();
			} else {
				alert(resultMsg.msg);
			}
		}
	});
}

function showOrderInfo() {
	var orderNo = document.getElementById("orderNo").value;
	if (orderNo == '') {
		return;
	}
	window.open(showOrderInfoUrl + "?orderNo=" + orderNo);
}

function reloadPage() {
	location.replace(reloadPageUrl);
}
function showApplyProduct(mainId, detailId) {
	showRealQualityCheckInfo(mainId, detailId);
}
function toModifyDetail(detailId, appNo) {
	var backRemark = document.getElementById("customer_backremark").innerHTML;
	// backRemark=$("#customer_backremark").val();
	openwindow(toModifyDetailUrl + "?detailId=" + detailId + "&applyNo="
			+ appNo + "&customerRemark=" + backRemark, 850, 700, '退换货结果录入');
}
function toQueryProductInfoVo() {
	openwindow(toQueryProductInfoVoUrl, 900, 700, '货品查询');
}
function toQueryApply() {
	var expressCode = document.getElementById("expressCodeForCheck").value;
	openwindow(toQueryApplyUrl + "?expressCode=" + expressCode, 900, 700,
			'售后申请查询');
}
/**
 * 去空格
 * 
 * @param val
 */
function trim(val) {
	if (val != "") {
		val = val.replace(/，/g, ",");
		val = val.replace(/(^\s*)|(\s*$)/g, "");
		if (val == "") {
			return;
		}
	}
	return val;
}

function radioAndCheckboxChecked(radioName, val) {
	var target = document.getElementsByName(radioName);

	for ( var i = 0; i < target.length; i++) {
		if (target[i].value == val) // 比较值
		{
			target[i].checked = true; // 修改选中状态
			break;
		}

	}
}