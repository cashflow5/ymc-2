
//列表url
freeShip.url.listActive = freeShip.url.basePath + "/yitiansystem/chain/active/list.sc";
//品牌选择器 url
freeShip.url.freeShippingBrandSelector = freeShip.url.basePath + "/yitiansystem/chain/active/toFreeShippingBrandSelector.sc";
//ygdg成功icon url
freeShip.url.ygdgSuccessIcon = freeShip.url.basePath + "/yougou/js/ygdialog/skins/icons/succeed.png";

/**
 * 初始化各个域
 */
freeShip.init.initFields = function() {
	//开始时间
	$('#freeShip_startTime').calendar({maxDate:'#freeShip_endTime', format:'yyyy-MM-dd HH:mm:ss'});
	//结束时间
	$('#freeShip_endTime').calendar({minDate:'#freeShip_startTime',format:'yyyy-MM-dd HH:mm:ss'});
};

freeShip.init.bindEvent = {};
/**
 * 绑定事件
 */
freeShip.init.bindEvent.bind = function() {
	//参与商品单选按钮 点击事件
	$(".freeShip_commodityRequire").click(freeShip.init.bindEvent.commodityRequireRadio_OnClick);
	//保存按钮 点击事件
	$(".freeShip_save_btn").click(freeShip.init.bindEvent.saveBtn_OnClick);
};

/**
 * 参与商品单选按钮 点击事件
 */
freeShip.init.bindEvent.commodityRequireRadio_OnClick = function() {
	var value = $(this).val();
	if(value == freeShip.constant.COMMODITY_REQUIER_BRAND) {
		openwindow(freeShip.url.freeShippingBrandSelector, 600, 300, "选择品牌");
		$("#freeShip_brand_list_layer").show();	
	} else {
		$("#freeShip_brand_list_layer").hide();
	}
};

/**
 * 保存按钮 点击事件
 */
freeShip.init.bindEvent.saveBtn_OnClick = function() {
	//参与商品单选按钮值
	var commodityRequireValue = $.trim($(".freeShip_commodityRequire:checked").val());
	//如果选中了指定品牌
	if(freeShip.constant.COMMODITY_REQUIER_BRAND == commodityRequireValue) {
		var brandIs = '';
		var chks = $(".free_brand_chk");
		var chkTmp = null;
		for (var i = 0, len = chks.length; i < len; i++) {
			chkTmp = chks[i];
			brandIs += chkTmp.value + ",";
		}
		$("#freeShip_brandIds").val(brandIs.substring(0, brandIs.length - 1));
	}
	//验证所有字段
	if(!freeShip.validate.valAll()) {
		return;
	}
//	alert("OK");
//	return;
	$.ajax({
		url: freeShip.url.submitUrl,
		data: {
			"activeName" : $.trim($("#freeShip_activeName").val()),
			"startTime" : $.trim($("#freeShip_startTime").val()),
			"endTime" : $.trim($("#freeShip_endTime").val()),
			"commodityRequire" : $.trim($(".freeShip_commodityRequire:checked").val()),
			"brandIds" : $.trim($("#freeShip_brandIds").val()),
			"commodityRequirePrice" : $.trim($("#freeShip_commodityRequirePrice").val())
		},
		type: "POST",
		dataType: "json",
		success: function(data) {
			//验证ajax success 的data，用于增删改
			data = freeShip.util.validateAjaxSuccessData(data, freeShip.msg.errorMsg);
			if(data == null) return;
			if(data.success == "true") {
				ygdg.dialog.tips(data.msg, 1, freeShip.url.ygdgSuccessIcon);
				window.setTimeout(function() {
					window.location.assign(freeShip.url.listActive);
				}, 1000);
			} else {
				ygdg.dialog.alert(data.msg);
			}
		},
		error: function() {
			ygdg.dialog.alert(freeShip.msg.errorMsg);
		}
	});
};

freeShip.validate = {};
/**
 * 验证所有字段
 * @return 成功返回true
 */
freeShip.validate.valAll = function() {
	$(".error_msg_tip").html("");
	//验证所有字段
	if(freeShip.validate.valActiveName() &&  //验证活动名称
			freeShip.validate.valStartTime() &&  //验证开始时间
			freeShip.validate.valEndTime() &&  //验证结束时间
			freeShip.validate.valBrandIds() && //验证选中的品牌
			freeShip.validate.valCommodityRequirePrice()  //验证达到免运费条件的商品价格
			) {
		return true;
	}
	return false;
};

/**
 * 验证活动名称
 * @return 成功返回true
 */
freeShip.validate.valActiveName = function() {
	var fieldName = "freeShip_activeName";
	var nullMsg = "请输入免运费活动名称";
	var thisValue = $.trim($("#" + fieldName).val());
	if(thisValue == null || thisValue.length == 0) {
		$("#" + fieldName + "_error").html(nullMsg);
		return false;
	}
	return true;
};

/**
 * 验证开始时间
 * @return 成功返回true
 */
freeShip.validate.valStartTime = function() {
	var fieldName = "freeShip_startTime";
	var nullMsg = "请选择开始时间";
	var thisValue = $.trim($("#" + fieldName).val());
	if(thisValue == null || thisValue.length == 0) {
		$("#" + fieldName + "_error").html(nullMsg);
		return false;
	}
	return true;
};

/**
 * 验证结束时间
 * @return 成功返回true
 */
freeShip.validate.valEndTime = function() {
	var fieldName = "freeShip_endTime";
	var nullMsg = "请选择结束时间";
	var thisValue = $.trim($("#" + fieldName).val());
	if(thisValue == null || thisValue.length == 0) {
		$("#" + fieldName + "_error").html(nullMsg);
		return false;
	}
	return true;
};

/**
 * 验证选中的品牌
 * @return 成功返回true
 */
freeShip.validate.valBrandIds = function() {
	var commodityRequireValue = $.trim($(".freeShip_commodityRequire:checked").val());
	//如果选择的指定品牌
	if (freeShip.constant.COMMODITY_REQUIER_BRAND == commodityRequireValue) {
		var fieldName = "freeShip_brandIds";
		var nullMsg = "请指定品牌";
		var thisValue = $.trim($("#" + fieldName).val());
		if (thisValue == null || thisValue.length == 0) {
			ygdg.dialog.alert(nullMsg);
			return false;
		}
		return true;
	} else {
		return true;
	}
};

/**
 * 验证达到免运费条件的商品价格
 * @return 成功返回true
 */
freeShip.validate.valCommodityRequirePrice = function() {
	var fieldName = "freeShip_commodityRequirePrice";
	var nullMsg = "请输入商品价格";
	var priceMsg = "请输入正整数";
	var thisValue = $.trim($("#" + fieldName).val());
	if(thisValue == null || thisValue.length == 0) {
		$("#" + fieldName + "_error").html(nullMsg);
		return false;
	}
	if(!/^[1-9]([0-9]*)$/.test(thisValue)) {
		$("#" + fieldName + "_error").html(priceMsg);
		return false; 
	}
	return true;
};

freeShip.util = {};
/**
 * 验证ajax success 的data，用于增删改
 * @param {String} data 数据
 * @param {String} errorMsg 错误消息
 * @return 合法返回转换后的data，不合法返回null
 */
freeShip.util.validateAjaxSuccessData = function(data, errorMsg) {
	if (data != null && typeof(data) == "object") {
		if (data.success != null && typeof(data.success) != "undefined") {
			data.msg = data.msg || "";
			return data;
		} else {
			ygdg.dialog.alert(errorMsg);
			return null;
		}
	} else {
		ygdg.dialog.alert(errorMsg);
		return null;
	}
};