// 重新构建索引
function initIndexBak(tableName) {
	var table = document.getElementById(tableName);
	var indexId = 0;
	alert(indexId);
	for (var i = 1; i < table.rows.length; i++) {
		table.rows[i].id = i - 1;
		var rowCells = table.rows[i].cells;
		for (var j = 0; j < rowCells.length; j++) {
			// 重新构建索引
			if (rowCells[j].childNodes[0] != null) {
				var tmpName = rowCells[j].childNodes[0].name;
				if (tmpName != null) {
					var bIndex = tmpName.indexOf("[");
					var eIndex = tmpName.indexOf("]");
					tmpName = tmpName.substring(0, bIndex + 1) + indexId
							+ tmpName.substring(eIndex, tmpName.length);
					rowCells[j].childNodes[0].name = tmpName;
				}
			}
		}
		indexId++;
	}
}

// 重新构建索引
function initIndex(tableName) {
	var table = document.getElementById(tableName);
	for (var i = 1; i < table.rows.length; i++) {
		table.rows[i].id = i;
		var rowCells = table.rows[i].cells;
		var indexId = i - 1;
		for (var j = 0; j < rowCells.length; j++) {
			// 重新构建索引
			if (rowCells[j].childNodes[0] != null) {
				var rowTr = rowCells[j].childNodes[0], tempInput, tempEx;
				// 此处为兼容FF
				if (rowTr.nodeType == 3) {
					// 此table只有第一个td作隐藏ID操作
					var input = rowCells[j].getElementsByTagName("input");
					var select = rowCells[j].getElementsByTagName("select");
					for (var o = 0; o < input.length; o++) {
						var tempEx = input[o];
						var tmpName = tempEx.name;
						var bIndex = tmpName.indexOf("[");
						var eIndex = tmpName.indexOf("]");
						tempEx.name = tmpName.substring(0, bIndex + 1)
								+ indexId
								+ tmpName.substring(eIndex, tmpName.length);
						tempEx.id = tempEx.name;
						// tempEx.name=tmpName;
					}
					for (var o = 0; o < select.length; o++) {
						var tempEx = select[o];
						var tmpName = tempEx.name;
						var bIndex = tmpName.indexOf("[");
						var eIndex = tmpName.indexOf("]");
						tempEx.name = tmpName.substring(0, bIndex + 1)
								+ indexId
								+ tmpName.substring(eIndex, tmpName.length);
						tempEx.id = tempEx.name;
					}
				} else {
					tempInput = rowCells[j].childNodes;
					for (var o = 0; o < tempInput.length; o++) {
						var tempEx = tempInput[o];
						if (!tempEx.tagName) {
							continue;
						}
						if (tempEx.tagName.toLowerCase() == "input"
								|| tempEx.tagName.toLowerCase() == "select") {
							// alert(tempEx.outerHTML);
							var tmpName = tempEx.name;
							var bIndex = tmpName.indexOf("[");
							var eIndex = tmpName.indexOf("]");
							tempEx.name = tmpName.substring(0, bIndex + 1)
									+ indexId
									+ tmpName.substring(eIndex, tmpName.length);
							tempEx.id = tempEx.name;
						}
					}
				}
			}
		}
	}
}
// 下拉框选定
function selectSedVal(selectId, selectVal) {
	var ops = document.getElementById(selectId).options;
	for (var i = 0; i < ops.length; i++) {
		if (ops[i].value == selectVal) {
			ops[i].selected = true;
			break;
		}
	}
}
function addNameClass(tip, cs) {
	var name = $("#" + tip + "");
	name.removeClass("oncorrect").removeClass("onshow").removeClass("onerror");
	return name.addClass(cs);
}
// 获得已经选中的checkBox,返回对象数组
function getCheckBoxs(name) {
	var count = 0;
	var checkedArray = new Array();
	var checkBoxs = document.getElementsByName(name);
	for (var i = 0; i < checkBoxs.length; i++) {
		var e = checkBoxs[i];
		if (e.checked) {
			checkedArray[count++] = checkBoxs[i];
		}
	}
	return checkedArray;
}
/**
 * 创建表格
 * 
 * @param {}
 *            values
 * @param {}
 *            rowConfig
 */
function createTable(tableId, values, uniqueIndex) {
	for (var i = 0; i < values.length; i++) {
		// 防止重复添加相同货品
		var check = uniqueCheck(tableId, values[i][uniqueIndex]);
		if (check) {
			insertRow(tableId, values[i], rowConfig);
		}
	}
}

function uniqueCheck(tableId, uniqueValue) {
	var flag = true;
	$("#" + tableId + " tr:not(:first)").each(function() {
				if ($(this).find("td:eq(0)").text() == uniqueValue) {
					flag = false;
				}
			});
	return flag;
}

function lessNow(nowDate, compareDate, tip) {
	var ck = true;
	if (compareDate != "" && compareDate > nowDate) {
		addNameClass(tip, "onerror");
		ck = false;
	} else {
		addNameClass(tip, "oncorrect");
		ck = true;
	}
	return ck;
}

/**
 * 验证多行文本框信息
 * 
 * @param file
 *            validatorFile 名称
 * @param reg
 *            验证规则 正则表达式
 * @param errorMsg
 * @param mesTipId
 */
function validatorInputFile(file, reg, errorMsg, mesTipId, rowIndexArray,
		checkBoxFlag) {
	var validatorResult = true;
	var validatorNodes = $("input[validatorFile=" + file + "]");
	var mesg = "";
	if (checkBoxFlag == "1") {
		for (var i = 0; i < rowIndexArray.length; i++) {
			var row = rowIndexArray[i];
			var value = validatorNodes[row].value;
			if (!reg.test(value)) {
				mesg += "第" + (row + 1) + "行 , ";
			}
		}
	} else {
		for (var i = 0; i < validatorNodes.length; i++) {
			var value = validatorNodes[i].value;
			if (!reg.test(value)) {
				mesg += "第" + (i + 1) + "行 , ";
			}
		}
	}
	$('#' + mesTipId).empty();
	if (mesg.length > 2) {
		$('#' + mesTipId).parent().css("display", "block");
		$('#' + mesTipId).append(mesg + errorMsg);
		$('#' + mesTipId).attr("class", "onerror");
		validatorResult = false;
	} else {
		$('#' + mesTipId).attr("class", "");
	}

	return validatorResult;
}