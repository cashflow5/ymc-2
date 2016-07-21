/**
 * 获取复选框选择值
 * 
 * @param {}
 *            checkBoxName 复选框名称
 * @param {}
 *            idArray ID
 */
function rowSelectVal(checkBoxName, idArray) {
	// 获循环索引
	var rowIndexArray = getCheckBoxs(checkBoxName);
	var tableArray = new Array();
	for (var i = 0; i < rowIndexArray.length; i++) {
		// ID后缀
		var idPostfix = rowIndexArray[i].value;
		tableArray[i] = new Array();
		// 获取行元素所有值
		for (var j = 0; j < idArray.length; j++) {
			var id = idArray[j] + "[" + idPostfix + "]";
			var val = document.getElementById(id).value;
			tableArray[i][j] = val;
		}
	}
	return tableArray;
}
/**
 * 生成行
 * 
 * @param {}
 *            targetTableId
 * @param {}
 *            types
 * @param {}
 *            ids
 * @param {}
 *            names
 * @param {}
 *            values
 */
function insertRow(targetTableId, values, rowConfig) {
	var targetTable = document.getElementById(targetTableId);
	var objRow = targetTable.insertRow(targetTable.rows.length);
	var objCell = null;
	var fields = rowConfig.fields;
	for (var i = 0; i < fields.length; i++) {
		var f = fields[i];
		var id = f.id;
		var name = f.name;
		var type = f.type;
		var display = f.display;
		var tdIndex = f.tdIndex;
		var valueIndex = f.valueIndex;
		var validatorFile = f.validatorFile;
		var style = f.style;

		var innerHTML = "";

		if (typeof objRow.cells[tdIndex] == "undefined") {
			objCell = objRow.insertCell(tdIndex);
		} else {
			innerHTML = objCell.innerHTML;
		}
		var value = "";
		if (valueIndex != -1 && type != "select") {
			value = values[valueIndex];
		}
		if (display) {
			innerHTML = innerHTML
					+ genInput(type, id, name, value, validatorFile, style)
					+ "<span>" + value + "</span>";
		} else {
			if (type == "select") {
				innerHTML = innerHTML
						+ genSelect(id, name, style, extendsValues[valueIndex]);
			} else {
				innerHTML = innerHTML
						+ genInput(type, id, name, value, validatorFile, style);
			}
		}
		objCell.innerHTML = innerHTML;

		if (i == fields.length - 1) {
			objCell.className = "td0";
		}
	}
}
/**
 * 生成input
 * 
 * @param {}
 *            type
 * @param {}
 *            id
 * @param {}
 *            name
 * @param {}
 *            value
 * @param {}
 *            validatorFile
 * @param {}
 *            style
 * @return {}
 */
function genInput(type, id, name, value, validatorFile, style) {
	var tempType = typeof(type) == "undefined" ? "text" : type;
	var tempId = typeof(id) == "undefined" ? "" : id;
	var tempName = typeof(name) == "undefined" ? "" : name;
	var tempValue = typeof(value) == 'undefined' ? "" : value;
	var tempStr = new StringBuffer();
	tempStr.add("<input type='").add(tempType).add("' ").add("id='")
			.add(tempId).add("' ").add("name='").add(tempName).add("' ")
			.add("value='").add(tempValue).add("' ").add("validatorFile='")
			.add(validatorFile).add("' ").add("style='").add(style).add("' ")
			.add("/>");
	return tempStr.toString();
}

function createRow() {
	var checkedArray = getCheckBoxs("commodityCB");
	if (checkedArray.length == 0) {
		alert("请选择货品！");
		return;
	}
	if (!confirm("确定已经勾选？")) {
		return;
	}
	var idArray = ["commodityId", "commodityCode", "goodsName",
			"specification", "units"];
	var values = rowSelectVal("commodityCB", idArray);
	art.dialog.parent.createTable("subTable", values, 1);
	alert("成功添加" + values.length + "条货品信息！");
}

function onclose() {
	art.dialog.close();
}
/**
 * 生成下拉框
 * 
 * @param {}
 *            id
 * @param {}
 *            name
 * @param {}
 *            style
 * @param {}
 *            valueArray 值数组
 * @param {}
 *            textArray 显示数组
 */
function genSelect(id, name, style, objValues) {
	var tempStr = new StringBuffer();
	tempStr.add("<select id='").add(id).add("' ").add("name='").add(name)
			.add("' ").add("style='").add(style).add("' >");
	for (var i = 0; i < objValues.length; i++) {
		tempStr.add("<option value='").add(objValues[i].value).add("' >")
				.add(objValues[i].text).add("</option>");
	}
	return tempStr.add("</select>");
}
