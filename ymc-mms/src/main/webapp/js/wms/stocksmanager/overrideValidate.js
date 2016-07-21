
var flag = true;
function validateform() {
	return flag;
}
Tool.onReady(function () {
	var f = new Fw(config);
	this.cls = new Cls;//初使化消息样式
	this.regexp = new Reg;//初使化正则表达式
	this.form = ID(this.config.form);
		//注册当前form表单对象
	this.register = function () {
		this.reg(this.config.fields);
	};
	f.register();
});
function validatorDate(dateId) {
	var dateFlag = true;
	var date = $("#" + dateId).val();
	var regex = /^\S+$/;
	if (!regex.exec(date)) {
		dateFlag = false;
	} else {
		dateFlag = true;
	}
	return dateFlag;
}
function formValidate() {
	var f = this.form;
	var re = this.regexp;
	var style = this.cls;
	var config = this.config;
	var result = true;
	var c, o, cla, msg, tip, dateLimit;
	var vf = config.fields;
	for (var i = 0; i < vf.length; i++) {
		c = vf[i];
		o = f[c.name];
		tip = Tool.getEl(c.msgTip);
		var r = (typeof (c.regExp) == "string") ? re[c.regExp] : c.regExp;
		if (isPassed(o, c, r)) {
			cla = style.errorCls;
			msg = c.errorMsg;
			result = false;
			setMsg(tip, cla, msg);
		}
	}
	//验证日期
	var vdates = config.dates;
	if (vdates != null) {
		for (var i = 0; i < vdates.length; i++) {
			c = vdates[i];
			tip = Tool.getEl(c.msgTip);
			if (!validatorDate(c.id)) {
				cla = style.errorCls;
				msg = c.errorMsg;
				result = false;
				setMsg(tip, cla, msg);
			} else {
				cla = style.rightCls;
				msg = c.rightMsg;
				setMsg(tip, cla, msg);
			}
		}
	}
	return result;
}

