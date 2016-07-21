/**
 * 判断表单域的值是否为空
 * @param {String} value 表单域的值
 * @param {String} notNollMsg 报错信息
 */
goodsAdd.validate.isEmpty = function(filed, value, errMsg) {
	if(value == null || typeof(value) == "undefined" || value.length == 0) {
		var errorList = goodsAdd.validate.errorList;
		goodsAdd.validate.errorList[errorList.length] = { errorFiled: filed, errMsg: errMsg };
		return true;
	};
	return false;
};

/**
 * 判断表单域的值是否不为数字
 * @param {String} value 表单域的值
 * @param {String} errMsg 报错信息
 */
goodsAdd.validate.isNotNum = function(filed, value, errMsg) {
	if(value == null || typeof(value) == "undefined" || isNaN(value)) {
		var errorList = goodsAdd.validate.errorList;
		goodsAdd.validate.errorList[errorList.length] = { errorFiled: filed, errMsg: errMsg };
		return true;
	};
	return false;
};

/**
 * 判断小数后必须是0
 */
goodsAdd.validate.isNumberPointZero = function(filed, value, errMsg) {
	if(!/^\d+\.?[0]+$|^\d+$/.test(value)) {
		var errorList = goodsAdd.validate.errorList;
		goodsAdd.validate.errorList[errorList.length] = { errorFiled: filed, errMsg: errMsg };
		return true;
	};
	return false;
};


/**
 * 判断表单域的值是否小于或等于0
 * @param {String} value 表单域的值
 * @param {String} errMsg 报错信息
 */
goodsAdd.validate.isLessThanZero = function(filed, value, errMsg) {
	if(value == null || typeof(value) == "undefined" || parseFloat(value) <= 0.0) {
		var errorList = goodsAdd.validate.errorList;
		goodsAdd.validate.errorList[errorList.length] = { errorFiled: filed, errMsg: errMsg };
		return true;
	};
	return false;
};

/**
 * 判断表单域的值是否小于0
 * @param {String} value 表单域的值
 * @param {String} errMsg 报错信息
 */
goodsAdd.validate.isThanZero = function(filed, value, errMsg) {
	if(value == null || typeof(value) == "undefined" || parseFloat(value) < 0.0) {
		var errorList = goodsAdd.validate.errorList;
		goodsAdd.validate.errorList[errorList.length] = { errorFiled: filed, errMsg: errMsg };
		return true;
	};
	return false;
};

/**
 * 判断表单域的值是否为整数
 * @param {String} value 表单域的值
 * @param {String} errMsg 报错信息
 */
goodsAdd.validate.isNotInt = function(filed, value, errMsg) {
	if(value == null || typeof(value) == "undefined" || value.indexOf(".") != -1) {
		var errorList = goodsAdd.validate.errorList;
		goodsAdd.validate.errorList[errorList.length] = { errorFiled: filed, errMsg: errMsg };
		return true;
	};
	return false;
};


/**
 * 判断表单域的值 是否为.jpg后缀
 * @param {String} value 表单域的值
 * @param {String} errMsg 报错信息
 */
goodsAdd.validate.isNotJPG = function(filed, value, errMsg) {
	if(value == null || typeof(value) == "undefined" || value.lastIndexOf(".jpg") == -1) {
		var errorList = goodsAdd.validate.errorList;
		goodsAdd.validate.errorList[errorList.length] = { errorFiled: filed, errMsg: errMsg };
		return true;
	};
	return false;
};

/**
 * 判断表单域的值 的长度 是否大于 指定长度
 * @param {String} value 表单域的值
 * @param {Object} len 指定长度
 * @param {String} errMsg 报错信息
 */
goodsAdd.validate.isLenLarger = function(filed, value, len, errMsg) {
	if(value == null || typeof(value) == "undefined" || value.length > len) {
		var errorList = goodsAdd.validate.errorList;
		goodsAdd.validate.errorList[errorList.length] = { errorFiled: filed, errMsg: errMsg };
		return true;
	};
	return false;
};

/**
 * 判断表单域的值 是否符合正则表达式
 * @param {String} filed 报错字段
 * @param {String} value 表单域的值
 * @param {Object} regexp 正则表达式
 * @param {String} errMsg 报错信息
 */
goodsAdd.validate.RegExp = function(filed, value, regexp, errMsg) {
	if (!regexp.test(value)) {
		var errorList = goodsAdd.validate.errorList;
		goodsAdd.validate.errorList[errorList.length] = { errorFiled: filed, errMsg: errMsg };
	};
};

/**
 * 判断值是否符合正则表达式，以结尾
 * @param value 输入值
 * @param regexp 正则表达式
 */
goodsAdd.validate.endsWith = function(value, regexp) {
	if (regexp.test(value)) {
		return true;
	}
	return false;
};

/**
 * 判断值是否符合正则表达式，以开头
 * @param value 输入值
 * @param regexp 正则表达式
 */
goodsAdd.validate.startWith = function(value, regexp) {
	if (regexp.test(value)) {
		return true;
	}
	return false;
};

/**
 * 是否包含中文
 * 包含中文返回true
 */
goodsAdd.validate.containsChinese  = function(filed, value, errMsg) {
	if(/[\u4e00-\u9fa5]/.test(value)) {
		var errorList = goodsAdd.validate.errorList;
		goodsAdd.validate.errorList[errorList.length] = { errorFiled: filed, errMsg: errMsg };
		return true;
	};
	return false;
};
/**
 * 值允许输入数字、字母、下划线、横杠
 */
goodsAdd.validate.allowInput  = function(filed, value, errMsg) {
	if(!/^[\\/0-9a-zA-Z_-]+$/.test(value)) {
		var errorList = goodsAdd.validate.errorList;
		goodsAdd.validate.errorList[errorList.length] = { errorFiled: filed, errMsg: errMsg };
		return true;
	};
	return false;
};

/**
 * 值允许输入数字、字母、下划线、横杠、点
 */
goodsAdd.validate.allowInputAndDot = function(filed, value, errMsg) {
	if(!/^[\\/0-9a-zA-Z\._-]+$/.test(value)) {
		var errorList = goodsAdd.validate.errorList;
		goodsAdd.validate.errorList[errorList.length] = { errorFiled: filed, errMsg: errMsg };
		return true;
	};
	return false;
};

/**
 * 值允许输入数字、字母、下划线、横杠、中文
 */
goodsAdd.validate.allowInputContainsChinese  = function(filed, value, errMsg) {
	if(!/^[\w\u4e00-\u9fa5-]+$/.test(value)) {
		var errorList = goodsAdd.validate.errorList;
		goodsAdd.validate.errorList[errorList.length] = { errorFiled: filed, errMsg: errMsg };
		return true;
	};
	return false;
};
//=================================update end ==================================

isEmpty = function(value) {
	if(value == null || typeof(value) == "undefined" || value.length == 0) {
		return true;
	}
	return false;
};

//====update by ln at 2015-4-9 按尺码设置价格
//优购价必须小于市场价
//判断前者是否大于后者的值
goodsAdd.validate.isFormerGtLatter = function(filed, former,latter, errMsg){
	if(former == null || typeof(former) == "undefined" 
		|| latter ==null || typeof(latter)=="undefined" || 
		(parseFloat(former)>parseFloat(latter))) {
		var errorList = goodsAdd.validate.errorList;
		goodsAdd.validate.errorList[errorList.length] = { errorFiled: filed, errMsg: errMsg };
		return true;
	};
	return false;
};