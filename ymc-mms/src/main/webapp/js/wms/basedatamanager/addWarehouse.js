var flag = true;
function validateform() {
	if ($("#textfield2").val() == "") {
		addNameClass("onerror").html("请输入仓库名称！");
	}
	return flag;
}
var config = {
	form : "form1",
	submit : validateform,
	fields : [ {
		name : 'inventoryRatio',
		allownull : false,
		regExp : /^([0][.]\d{2}|[1])$/,
		defaultMsg : '请输入库存比例',
		focusMsg : '请输入库存比例',
		errorMsg : '库存比例输入错误(0.00-0.99或 1 )',
		rightMsg : '库存比例输入合法',
		msgTip : 'inventoryRatiotip'
	}, {
		name : 'orderNo',
		allownull : false,
		regExp : /^[0-9]{1,5}$/,
		defaultMsg : '请输入顺序号',
		focusMsg : '请输入顺序号',
		errorMsg : '顺序号输入错误 例：12345',
		rightMsg : '顺序号输入合法',
		msgTip : 'orderNotip'
	} ]
}
Tool.onReady(function() {
	var f = new Fw(config);
	f.register();
});

$(document).ready(function() {
	$("#warehouseNametip").addClass("onshow").html("请输入仓库名称");
});
function addNameClass(cs) {
	var name = $("#warehouseNametip");
	name.removeClass("oncorrect").removeClass("onshow").removeClass("onerror");
	return name.addClass(cs);

}
function addWarehouseNickNameClass(cs) {
	var name = $("#warehouseNickNametip");
	name.removeClass("oncorrect").removeClass("onshow").removeClass("onerror");
	return name.addClass(cs);

}
function addCkaccnoClass(cs) {
	var name = $("#ckaccnotip");
	name.removeClass("oncorrect").removeClass("onshow").removeClass("onerror");
	return name.addClass(cs);

}
// 验证名称是否被占用
function validatorWarehouseName() {
	var warehouseName = $("#textfield2").val();
	var regex = /^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]{2,30}$/;
	if (!regex.exec(warehouseName)) {
		addNameClass("onerror").html("仓库名称只能由2-30个字符组成");
		flag = false;
		return false;
	}

	$.ajax({
		type : "POST",
		url : "validateWarehouseName.sc",
		data : {
			"warehouseName" : warehouseName,
			"warehouseCode" : ""
		},
		success : function(msg) {
			if ("true" == msg) {
				addNameClass("onerror").html("仓库名称已经存在");
				flag = false;
			}
			if ("false" == msg) {
				addNameClass("oncorrect").html("仓库名称可以使用");
				flag = true;
			}
		}
	});
}
// 验证别名是否被占用
function validatorWarehouseNickName() {
	var warehouseNickName = $("#warehouseNickName").val();
	var regex = /^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]{2,30}$/;
	if (!regex.exec(warehouseNickName)) {
		addWarehouseNickNameClass("onerror").html("仓库别名只能由2-30个字符组成");
		flag = false;
		return false;
	}

	$.ajax({
		type : "POST",
		url : "validateWarehouseNickName.sc",
		data : {
			"warehouseNickName" : warehouseNickName,
			"warehouseCode" : ""
		},
		success : function(msg) {
			if ("true" == msg) {
				addWarehouseNickNameClass("onerror").html("仓库别名已经存在");
				flag = false;
			}
			if ("false" == msg) {
				addWarehouseNickNameClass("oncorrect").html("仓库别名可以使用");
				flag = true;
			}
		}
	});
}
// 验证仓库标识是否被占用
function validatorCkaccno() {
	var ckaccno = $("#textfield3").val();
	if (ckaccno == "") {
		addCkaccnoClass("onerror").html("");
		flag = false;
		return false;
	}

	$.ajax({
		type : "POST",
		url : "valickaccno.sc",
		data : {
			"ckaccno" : ckaccno,
			"warehouseCode" : ""
		},
		success : function(msg) {
			if ("true" == msg) {
				addCkaccnoClass("onerror").html("仓库标识已被占用");
				flag = false;
			}
			if ("false" == msg) {
				addCkaccnoClass("oncorrect").html("仓库标识可以使用");
				flag = true;
			}
		}
	});
}
function addRemarktClass(cs) {
	var name = $("#remarktip");
	name.removeClass("oncorrect").removeClass("onshow").removeClass("onerror");
	return name.addClass(cs);

}
// 验证描述内容是否超长
function checkRemark() {
	var remarkText = document.getElementById("remark").value;
	if (remarkText.length > 250) {
		addRemarktClass("onerror").html("备注内容只能输入250个字符");
		flag = false;
	} else {
		addRemarktClass("oncorrect").html("备注内容输入正确");
		flag = true;
	}

	/*
	 * if (remarkText.length>250) {
	 * alert("备注内容共["+remarkText.length+"]个字符只能输入250个字符");
	 * $("#submitbutton").attr("disabled","disabled"); } else{
	 * $("#submitbutton").attr("disabled",""); }
	 */
}