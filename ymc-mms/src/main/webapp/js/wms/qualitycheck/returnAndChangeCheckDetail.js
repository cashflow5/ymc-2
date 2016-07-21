$(document).ready(function() {
	getCheckItem();
});
/**
 * 保存
 */
function doSaveCheckItem() {
	var mainId = dg.curWin.document.getElementById("mainId").value;
	var detailId = document.getElementById("detailId").value;
	var expressCode = dg.curWin.document.getElementById("expressCodeForCheck").value;// 快递单号
	var orderNo = dg.curWin.document.getElementById("orderNo").value;// 订单号
	var isPayArriveBtn = dg.curWin.document.getElementsByName("isPayArrive");// 到付
	var isPayArrive = "noPayArrive";
	if (isPayArriveBtn[0].checked) {
		isPayArrive = "payArrive";
	}
	var expressPrice = dg.curWin.document.getElementById("expressPrice").value;// 快递费用
	var createTime = dg.curWin.document.getElementById("createTime").value;// 质检时间
	var checker = dg.curWin.document.getElementById("checker").value;// 判定人
	var barcode = document.getElementById("barcode").value;// 条码
	var realApplyNo = document.getElementById("realApplyNo").value;// 申请单号（异常情况）
	var realProductNo = document.getElementById("realProductNo").value;// 货品编号（异常情况）

	var questionType = getRadioVal("questionType");// 问题类型
	var questionReason = getCheckBoxVal("questionReason");// 问题原因

	var badType = getRadioVal("badType");// 残品新旧类型

	var customerBackRemark = document.getElementById("customerBackRemark").value;
	if (questionReason == '' && questionType == 'BAD') {
		alert("请选择问题原因！");
		return;
	}
	var questionBelong = getCheckBoxVal("questionBelong");// 问题归属
	if (questionBelong == '') {
		alert("请选择问题归属！");
		return;
	}
	var inType = getRadioVal("inType");// 入库类型
	if (inType == '') {
		alert("请选择入库类型！");
		return;
	}

	var description = document.getElementById("description").value;// 质检描述
	if (description == '') {
		alert("请输入质检描述！");
		return;
	}
	$("#doSaveBtn").removeAttr('onclick');
	$
			.ajax({
				type : "POST",
				url : doSaveCheckItemUrl,
				data : {
					"mainId" : mainId,
					"detailId" : detailId,
					"expressCode" : expressCode,
					"orderNo" : orderNo,
					"isPayArrive" : isPayArrive,
					"expressPrice" : expressPrice,
					"createTime" : createTime,
					"barcode" : barcode,
					"questionType" : questionType,
					"questionReason" : questionReason,
					"questionBelong" : questionBelong,
					"inType" : inType,
					"description" : description,
					"realApplyNo" : realApplyNo,
					"customerBackRemark" : customerBackRemark,
					"checker" : checker,
					"realProductNo" : realProductNo,
					"badType" : badType
				},
				dataType : "json",
				success : function(resultMsg) {
					if (resultMsg.success == true) {
						var tempMainId = dg.curWin.document
								.getElementById("mainId").value;
						if (tempMainId == '') {
							tempMainId = resultMsg.idCode;
							dg.curWin.document.getElementById("mainId").value = tempMainId;
						}
						dg.curWin.document.getElementById("barcode").value = "";

						var detailId = resultMsg.reCode;
						dg.curWin.doQueryApplyProduct(detailId);
						dg.curWin.showApplyProduct(tempMainId, detailId);
						closewindow();
					} else {
						if (resultMsg.reCode == '404') {
							alert(resultMsg.msg);
							return;
						} else {
							// 异常质检
							if (!confirm(resultMsg.msg)) {
								return;
							}
							openwindow(toApplyProductForSelUrl + "?orderNo="
									+ orderNo, 850, 300, '待质检商品');
						}

					}
				}
			});
}

/**
 * 获取质检选项(问题描述,问题类型)
 */
function getCheckItem() {
	var detailId = document.getElementById("detailId").value;
	var orderNo = dg.curWin.document.getElementById("orderNo").value;
	var barcode = dg.curWin.document.getElementById("barcode").value;
	$
			.ajax({
				type : "POST",
				url : getCheckItemUrl,
				data : {
					"detailId" : detailId,
					"orderNo" : orderNo,
					"barcode" : barcode
				},
				dataType : "json",
				success : function(resultMsg) {
					if (resultMsg.success == true) {
						var reObj = resultMsg.reObj;

						// 创建(问题描述,问题类型)
						// 问题描述
						var questionDescription = reObj.questionDescription;
						document.getElementById("questionDescriptionDiv").innerHTML = questionDescription;
						// 问题类型
						var questionTypeList = reObj.questionTypeList;
						createBtn(questionTypeList, "radio", "questionType",
								"questionTypeDiv", true, "getCheckOtherItem");
						// 根据问题类型创建(问题原因,问题归属, 入库类型)
						var questionType = reObj.questionType;
						// 选中问题类型
						radioChecked("questionType", questionType);
						getCheckOtherItem(questionType);

					} else {
						alert(resultMsg.msg);
					}
				}
			});
}

/**
 * 根据问题类型创建(问题原因,问题归属,入库类型,新残旧残)
 * 
 * @param inType
 */
function getCheckOtherItem(questionType) {
	var detailId = document.getElementById("detailId").value;
	var barcode = document.getElementById("barcode").value;
	var orderNo = dg.curWin.document.getElementById("orderNo").value;
	$
			.ajax({
				type : "POST",
				url : getCheckOtherItemUrl,
				data : {
					"detailId" : detailId,
					"barcode" : barcode,
					"questionType" : questionType,
					"orderNo" : orderNo
				},
				dataType : "json",
				success : function(resultMsg) {
					if (resultMsg.success == true) {
						var reObj = resultMsg.reObj;
						// 问题原因
						if (questionType == "BAD") {

							document.getElementById("questionReasonTr").style.display = "";
							document.getElementById("colspanTr").style.display = "";

							var questionReasonList = reObj.questionReasonList;
							createTalbe(questionReasonList, "checkbox",
									"questionReason", "questionReasonTable");
							var questionReason = reObj.questionReason;
							checkBoxChecked("questionReason", questionReason);

							document.getElementById("badTypeTr").style.display = "";
							document.getElementById("colspanTr1").style.display = "";

							var badTypeList = reObj.badTypeList;
							createBtn(badTypeList, "radio", "badType",
									"badTypeSpan", false, "");

							var badType = reObj.badType;
							radioChecked("badType", badType);

						} else {
							document.getElementById("questionReasonTr").style.display = "none";
							document.getElementById("colspanTr").style.display = "none";

							document.getElementById("badTypeTr").style.display = "none";
							document.getElementById("colspanTr1").style.display = "none";
						}

						// 问题归属
						var questionBelongList = reObj.questionBelongList;
						createBtn(questionBelongList, "checkbox",
								"questionBelong", "questionBelongSpan", false);
						var questionBelong = reObj.questionBelong;
						checkBoxChecked("questionBelong", questionBelong);

						// 入库类型
						var inTypeList = reObj.inTypeList;
						createBtn(inTypeList, "radio", "inType", "inTypeSpan",
								true, "setMemo");
						// 选中
						var inType = reObj.inType;
						radioChecked("inType", inType);

						var description = reObj.description;
						document.getElementById("description").value = description;
					} else {
						alert(resultMsg.msg);
					}
				}
			});
}
function setMemo(type) {
	document.getElementById("description").value = "";
	if (type == "MAINTENANCE") {
		document.getElementById("description").value = "维修后不影响二次销售";
	}
	if (type == "GOOD") {
		document.getElementById("description").value = "无质量问题";
	}

}
/**
 * 创建表格
 * 
 * @param data
 * @param type
 * @param inputName
 * @param tagId
 */
function createTalbe(data, type, inputName, tagId) {
	var table = document.getElementById(tagId);
	// 先初始化元素
	if (window.ActiveXObject) {
		for ( var i = table.children.length; i > 0; i--) {
			table.children[i - 1].removeNode(true);
		}
	} else {
		table.innerHTML = "";
	}

	for ( var i = 0; i < data.length; i++) {
		var key = data[i].key;
		var val = data[i].value;
		var inputId = inputName + i;
		var input = createElement("input", inputName);
		input.setAttribute("type", type);
		input.setAttribute("value", val);
		input.setAttribute("id", inputId);
		var row;
		if (i % 8 == 0) {
			// 换行
			row = table.insertRow(table.rows.length);
		}
		var col = row.insertCell(row.cells.length);

		col.appendChild(input);
		var label = document.createElement("label");
		label.setAttribute("for", inputId);
		label.appendChild(document.createTextNode(key));
		col.appendChild(label);

	}

}

function createBtn(data, type, inputName, tagId, openOnclick, methodName) {
	var target = document.getElementById(tagId);
	target.innerHTML = "";// 先初始化元素
	for ( var i = 0; i < data.length; i++) {
		var key = data[i].key;
		var val = data[i].value;
		var inputId = inputName + i;
		var input = createElement("input", inputName);
		input.setAttribute("type", type);
		input.setAttribute("value", val);
		input.setAttribute("id", inputId);
		if (openOnclick == true) {
			input.setAttribute("onclick", methodName + "('" + val + "');");
		}
		target.appendChild(input);
		var label = document.createElement("label");
		label.setAttribute("for", inputId);
		label.appendChild(document.createTextNode(key));

		target.appendChild(label);
	}
}
function createElement(type, name) {
	var element = null;
	try {
		// First try the IE way; if this fails then use the standard way
		element = document.createElement("<" + type + " name='" + name + "'>");

	} catch (e) {
		// Probably failed because we’re not running on IE
	}
	if (!element) {
		element = document.createElement(type);
		element.name = name;
	}
	return element;
}
/**
 * 单选选中
 */
function radioChecked(radioName, val) {
	var target = document.getElementsByName(radioName);

	for ( var i = 0; i < target.length; i++) {
		if (target[i].value == val) // 比较值
		{
			target[i].checked = true; // 修改选中状态
			break;
		}

	}
}

function checkBoxChecked(name, val) {
	var target = document.getElementsByName(name);
	var strArray = val.split(",");
	for ( var i = 0; i < target.length; i++) {
		for ( var j = 0; j < strArray.length; j++) {
			if (target[i].value == strArray[j]) // 比较值
			{
				target[i].checked = true; // 修改选中状态
			}
		}
	}
}
/**
 * 获取复选框值
 * 
 * @param nameOfCheckBox
 * @returns
 */
function getCheckBoxVal(nameOfCheckBox) {
	var returnValue = "";
	var theCheckboxInputs = document.getElementsByName(nameOfCheckBox);
	var flag = ",";
	for ( var i = 0; i < theCheckboxInputs.length; i++) {
		if (theCheckboxInputs[i].checked) {
			returnValue = returnValue + theCheckboxInputs[i].value + flag;
		}
	}
	var endIndex = returnValue.length;
	if (endIndex > 0) {
		endIndex = endIndex - 1;
	}
	returnValue = returnValue.substr(0, endIndex);
	return returnValue;
}
/**
 * 获取单选框值
 * 
 * @param nameOfRadio
 * @returns {String}
 */
function getRadioVal(nameOfRadio) {
	var returnValue = "";
	var theRadioInputs = document.getElementsByName(nameOfRadio);
	for ( var i = 0; i < theRadioInputs.length; i++) {
		if (theRadioInputs[i].checked) {
			returnValue = theRadioInputs[i].value;
			break;
		}
	}
	return returnValue;
}