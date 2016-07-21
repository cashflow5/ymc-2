var flag = true;
function validateform() {

	if ($("#textfieldcode").val() == "") {
		addCodeClass("onerror").html("请输入物流公司编号！");
		flag = false;
	}
	if ($("#textfield2").val() == "") {
		addNameClass("onerror").html("请输入物流公司名称！");
		flag = false;
	}
	return flag;
}

// 正则表达式完成页面验证
var config = {
	form : "myform",
	submit : validateform,
	fields : []
}

Tool.onReady(function() {
	var f = new Fw(config);
	f.register();
});

$(document).ready(function() {
	$("#logisticsCompanyNametip").addClass("onshow").html("请输入物流公司名称");
	$("#logisticCompanyCodetip").addClass("onshow").html("请输入物流公司编号");
	$("#groupCodetip").addClass("onshow").html("多个物流公司通过此ID号关联，本组中设置的ID号须一致。");
	$("#groupNametip").addClass("onshow").html("多个物流公司关联后所显示的物流公司名称。本关联组中名称必须一致");
});

function addNameClass(cs) {
	var name = $("#logisticsCompanyNametip");
	name.removeClass("oncorrect").removeClass("onshow").removeClass("onerror");
	return name.addClass(cs);

}
function addCodeClass(cs) {
	var name = $("#logisticCompanyCodetip");
	name.removeClass("oncorrect").removeClass("onshow").removeClass("onerror");
	return name.addClass(cs);

}
// 验证名称是否被占用
function validatorLogisticsCompanyName() {
	var logisticsCompanyName = $("#textfield2").val();
	var regex = /^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]{2,30}$/;
	if (!regex.exec(logisticsCompanyName)) {
		addNameClass("onerror").html("物流公司名称只能由2-30个字符组成");
		flag = false;
		return false;
	}
	$.ajax({
		type : "POST",
		url : "validateLogisticscompanyName.sc",
		data : {
			"logisticsCompanyName" : logisticsCompanyName,
			"logisticsCompanyCode" : ""
		},
		success : function(msg) {
			if ("true" == msg) {
				addNameClass("onerror").html("物流公司名称已经存在");
				flag = false;
			}
			if ("false" == msg) {
				addNameClass("oncorrect").html("物流公司名称可以使用");
				flag = true;
			}
		}
	});
}

// 验证物流公司编号是否被占用
function validatorLogisticsCompanyCode() {
	var logisticsCompanyCode = $("#textfieldcode").val();
	var regex = /^(?!_)(?!.*?_$)[a-zA-Z0-9]{2,32}$/;
	if (!regex.exec(logisticsCompanyCode)) {
		addCodeClass("onerror").html("物流公司编号只能由2-32个字符组成");
		flag = false;
		return false;
	}
	$.ajax({
		type : "POST",
		url : "validateLogisticscompanycode.sc",
		data : {
			"logisticsCompanyCode" : logisticsCompanyCode
		},
		success : function(msg) {
			if ("true" == msg) {
				addCodeClass("onerror").html("物流公司编号已经存在");
				flag = false;
			}
			if ("false" == msg) {
				addCodeClass("oncorrect").html("物流公司编号可以使用");
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
}