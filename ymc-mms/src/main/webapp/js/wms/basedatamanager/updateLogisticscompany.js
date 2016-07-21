var flag = true;
function validateupdateform() {
	return flag;
}
var config = {
	form : "form1",
	submit : validateupdateform,
	fields : []
}
Tool.onReady(function() {
	var f = new Fw(config);
	f.register();
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
// 验证物流公司编号是否被占用
function validatorLogisticsCompanyCode() {
	var logistid = $("#logistid").val();
	// var logisticsCompanyName = $("#logisticsCompanyName").val();
	var logisticsCompanyCode = $("#logisticCompanyCode").val();
	// alert("编号 ：id:" + logistid + " logisticsCompanyName :" +
	// logisticsCompanyName + " logisticsCompanyCode :" + logisticsCompanyCode);
	var regex = /^(?!_)(?!.*?_$)[a-zA-Z0-9]{2,32}$/;
	if (!regex.exec(logisticsCompanyCode)) {
		addCodeClass("onerror").html("物流公司编号只能由2-32个字符组成");
		flag = false;
		return false;
	}
	$.ajax({
		type : "POST",
		url : "validateLogisticscompanyNameandcode.sc",
		data : {
			"logisticsCompanyid" : logistid,
			"logisticsCompanyName" : "",
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
// 验证名称是否被占用
function validatorLogisticsCompanyName() {
	var logistid = $("#logistid").val();
	var logisticsCompanyName = $("#logisticsCompanyName").val();
	// var logisticsCompanyCode = $("#logisticCompanyCode").val();
	var regex = /^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]{2,30}$/;
	if (!regex.exec(logisticsCompanyName)) {
		addNameClass("onerror").html("物流公司名称只能由2-30个字符组成");
		flag = false;
		return false;
	}
	$.ajax({
		type : "POST",
		url : "validateLogisticscompanyNameandcode.sc",
		data : {
			"logisticsCompanyid" : logistid,
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