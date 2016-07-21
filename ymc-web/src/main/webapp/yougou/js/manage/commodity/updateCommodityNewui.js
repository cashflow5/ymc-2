/**商品相关*/
goodsAdd.commodity = {};

/**加载商品分类相关*/
goodsAdd.commodity.cat = {};
/**加载商品品牌相关*/
goodsAdd.commodity.brand = {};
/**库存相关*/
goodsAdd.commodity.stock = {};
/**图片文件相关*/
goodsAdd.commodity.imgFile = {};

/**缓存原属性值*/
goodsAdd.commodity.originProp = null;

/**缓存原尺码值*/
goodsAdd.commodity.originSize = null;

/**加载商品url*/
goodsAdd.url.getCommodityByNo = basePath + "/commodity/loadCommodityByNo.sc?commodityNo=" + requestCommodityNo; 

/**通过货品编号获取库存url*/
goodsAdd.url.getInventoryByProdNosUrl = basePath + "/commodity/getInventoryByProdNo.sc?prodNoStr=";
/**表单提交url*/
goodsAdd.url.commodityListUrls = {};
//goodsAdd.url.commodityListUrls[QUERY_COMMODITY_PAGE_TAB_ID_DRAFT] = basePath + "/commodity/goQueryDraftCommodity.sc";
//goodsAdd.url.commodityListUrls[QUERY_COMMODITY_PAGE_TAB_ID_PENDING] = basePath + "/commodity/goQueryPendingCommodity.sc";
//goodsAdd.url.commodityListUrls[QUERY_COMMODITY_PAGE_TAB_ID_PASS] = basePath + "/commodity/goQueryPassCommodity.sc";
//goodsAdd.url.commodityListUrls[QUERY_COMMODITY_PAGE_TAB_ID_REFUSE] = basePath + "/commodity/goQueryRefuseCommodity.sc";
//goodsAdd.url.commodityListUrls[QUERY_COMMODITY_PAGE_TAB_ID_ALL] = basePath + "/commodity/goQueryAllCommodity.sc";

/** 缓存按尺码设置的价格*/
goodsAdd.commodity.sellPrice = null;
goodsAdd.commodity.markPrice = null;
goodsAdd.commodity.supplierCode = null;

/**
 * 为预览的图片添加操作
 * @param {Object} inputFile 上传框对象
 */
goodsAdd.imageUpload.addOptToPreviewImg = function(inputFile) {
	$(inputFile).parent().parent().bind("mouseover",
			function() {
				goodsAdd.imageUpload.previewImg_OnMouseOver(inputFile);
			});
	$(inputFile).parent().parent().bind("mouseout",
			function() {
				goodsAdd.imageUpload.previewImg_OnMouseOut(inputFile);
			});
};

/**
 * 收集商品属性的id信息
 */
goodsAdd.properties.collectPropIdInfo = function() {
	var msg = '[';
	var $propItemNoTxts = $("#goods_selected_properties_propItemNo input");
	var propItemTxt = null;
	for (var i = 0, len = $propItemNoTxts.length; i < len; i++) {
		propItemTxt = $propItemNoTxts[i];
		var propItemNo = $.trim($(propItemTxt).val());
		//属性是否多选
		var valueType = $("#goods_prop_valuetype_"+propItemNo).val();
		if (valueType == '0') {
			var propItemId = $.trim($("#"+propItemNo).attr("propitemid"));
			if(propItemId) {
				msg += formatString(
						'{' +
						'"propItemNo":"{#propItemNo}",' +
						'"propItemId":"{#propItemId}"' +
						'},', 
						{
							propItemNo: propItemNo,
							propItemId: propItemId
						});
			}
		} else if (valueType == '1') {//多选
			var propItemId = $.trim($("#goods_prop_valuetype_" + propItemNo).attr("propitemid"));
			var propValueNo = $.trim($("#goods_prop_valuetype_" + propItemNo).attr("propvalueno"));
			if(propItemId) {
				msg += formatString(
						'{' +
						'"propItemNo":"{#propItemNo}",' +
						'"propItemId":"{#propItemId}",' +
						'"propValueNo":"{#propValueNo}"' +
						'},', 
						{
							propItemNo: propItemNo,
							propItemId: propItemId,
							propValueNo: propValueNo
						});
			}
		}
	}
	if(msg.length != 1) {
		msg = msg.substring(0, msg.length - 1);
	}
	msg += ']';
	$("#goods_properties_propIdInfo").val(msg);
};

/**
 * 尺码点击事件 预处理
 * @param {Object} self 当前点击的尺码
 * @param {String} sizeValue 尺码no
 * @param {String} sizeName 尺码名称
 * @param {String} colorName 颜色名称
 * @return 返回false可中断事件主代码的执行
 */
goodsAdd.size.pre_size_OnClick = function(self, sizeValue, sizeName, colorName) {
	var productNo = $.trim($("#goods_product_tr_" + sizeValue).attr("productNo"));
	if(productNo != null && typeof(productNo) != "undefined" && 
			productNo.length != 0 && !self.checked) {
		var sellStatus=$("#goods_sizeNo_display_" + sizeValue).attr("sellStatus");
		if(sellStatus=='2'){
			ygdg.dialog.alert("该尺码对应的货品已经有销售数据,不允许删除!");
			$("#goods_sizeNo_display_" + sizeValue).attr("checked", "true");
			return false;
		}
		ygdg.dialog.confirm('确定要删除尺码为 "' + sizeName + '" 这个货品吗？', function(){
			var size_body = $('.size_tab tbody'),
            tr = $('tr:first', size_body),
            rowcount=$('tr', size_body).length,
            color = $('.color_radio:checked');
			deleteTableTr(sizeValue,size_body,rowcount,color,tr);
		}, 
		function(){
			$("#goods_sizeNo_display_" + sizeValue).attr("checked", "true");
		});
		return false;
	}
	return true;
};

/**
 * 收集货品id信息
 */
goodsAdd.prodInfo.collectProdIdInfo = function() {
	var msg = '[';
	var $sizeNoTxts = $(".size-list input:checked");
	var sizeNoTxt = null;
	for (var i = 0, len = $sizeNoTxts.length; i < len; i++) {
		sizeNoTxt = $sizeNoTxts[i];
		var sizeNo = $.trim($(sizeNoTxt).val());
		var productNo = $.trim($("#goods_product_tr_" + sizeNo).attr("productNo"));
		var productId = $.trim($("#goods_product_tr_" + sizeNo).attr("productId"));
		if(productNo.length != 0 && productId.length != 0) {
			msg += formatString(
				'{' +
				'"sizeNo":"{#sizeNo}",' +
				'"productNo":"{#productNo}",' +
				'"productId":"{#productId}"' +
				'},', 
				{
					sizeNo: sizeNo,
					productNo: productNo,
					productId: productId
				});
		}
	}
	if(msg.length != 1) {
		msg = msg.substring(0, msg.length - 1);
	}
	msg += ']';
	$("#goods_prodIdInfo").val(msg);
};

/**
 * 提交表单前处理
 */
goodsAdd.submit.preSubmit = function() {
	//收集商品属性的id信息
	goodsAdd.properties.collectPropIdInfo();
	//收集货品id信息
	goodsAdd.prodInfo.collectProdIdInfo();
	return true;
};

/**
 * 商品资料提交响应(后置处理)
 * @param {Object} data 响应数据
 * @param {String} errMsg 错误消息
 * @return 成功则返回true
 */
goodsAdd.submit.successPost = function(data, errMsg) {
	if(!Boolean(data.isSuccess )) {
		var errorList = goodsAdd.validate.errorList = data.errorList;
		var html = goodsAdd.validate.joinErrorList();
		if(""!=html){
			errMsg = errMsg+"，原因如下：<br/>"+html;
		}
		ygdg.dialog.alert(errMsg, function(){
			if (errorList.length > 0) {
				goodsAdd.validate.batchShowErrorList();					
			}
		});
		return;
	}
	ygdg.dialog.tips('商品资料提交成功', 1, goodsAdd.url.successImgUrl);
	
	aftersavedg=ygdg.dialog({
		title:'修改成功',
		content:"<h2 class='f16' style='font-weight:bold;'>商品修改成功!</h2><br /><span style='color:#AAAAAA'>你还可以继续以下操作：</span><br /><br /><a href=\"javascript:toAddNewGoods();\" >发布新商品</a>&nbsp;&nbsp;&nbsp;&nbsp;<span style='color:#AAAAAA'>(点击后跳转到发布新商品页面)</span><br />",
		icon: 'succeed',
		button: [
		          {
		              name: '确定',
		              callback: function () {
		            	  setTimeout(function() {
	            				if (data.isAddCommoditySaveSubmit) { //提交审核后跳转到待审核页面
	            					location.assign(goodsAdd.url.goForSaleCommodity + "?auditStatus=12");
	            				} 
	            			}, 1000);
		              },
		              focus: true
		          }
		      ]
	});
	
};

function clearVal() {
	if (aftersavedg) {
		aftersavedg.close();
	}
	// 清除相似商品的必填项
	$('#goods_specName').val('');
	$("#goods_size_color_layer").hide();
	$('#goods_supplierCode').val('');
	$("input[name='goods_size_list']").each(function(i) {
		$(this).attr('checked', false);
		$("#goods_sizeNo_" + $(this).val()).remove();
		$("#goods_sizeName_" + $(this).val()).remove();
	});
	$('#goods_color_size_tbody').empty();
}

function toAddNewGoods(){
	// 跳转到当前页面的新发布状态
	location.assign(basePath + "/commodity/addCommodityui.sc");
}
/**商品状态*/
goodsAdd.commodity.commodityStatus = '';

/**
 * 加载商品
 */
goodsAdd.commodity.load = function() {
	$.ajax({
		url: goodsAdd.url.getCommodityByNo,
		cache: false,
		type: "GET",
		dataType: "json",
		success: function(data) {
			//ygdg.dialog({id: "loadCommodityDialog"}).close();
			if(data == null || typeof(data) != "object" || data.isSuccess == null ||
					typeof(data.isSuccess) == "undefined") {
				return;
			}
			if(data.isSuccess != "true") {
				ygdg.dialog.alert(data.errorMsg);
				return;
			}
			if(!data.commodity) return;
			var commodity = data.commodity;
			//图片处理消息缓存
			var imageMessage = data.imageCache;
			//填充商品信息
			goodsAdd.commodity.fillInfo(commodity, imageMessage);
			showOrDisableBtn("enable");
			ymc_common.loading();
		},
		error: function() {
			showOrDisableBtn("enable");
			alert("网络异常，请刷新后重试!");
			//ygdg.dialog({id: "loadCommodityDialog"}).close();
			ymc_common.loading();
		}
	});
};

/**
 * 填充商品信息
 * @param {Object} commodity 商品
 */
goodsAdd.commodity.fillInfo = function(commodity, imageMessage) {
	goodsAdd.commodity.originSize = commodity.products;
	goodsAdd.commodity.originProp = commodity.commdoityProps;
	
	$("#commodity_name").val(commodity.commodityName);
	$("#commodity_selling").val(commodity.sellingPoint);
	$("#commodity_style").val(commodity.styleNo);
	$("#commodity_code").val(commodity.supplierCode);
	if(!isSetSizePrice){
		$("#commodity_price").val(commodity.sellPrice);
		$("#commodity_market_value").val(commodity.markPrice);
	}else{
		goodsAdd.commodity.sellPrice = commodity.sellPrice;
		goodsAdd.commodity.markPrice = commodity.markPrice;
		goodsAdd.commodity.supplierCode = commodity.supplierCode;
	}
	goodsAdd.commodity.setYears(commodity.years);
	goodsAdd.commodity.setProperties(commodity.commdoityProps);
	goodsAdd.commodity.setColor(commodity.colorNo, commodity.colorName);
	goodsAdd.commodity.setSize(commodity.products,commodity.colorName);
	goodsAdd.commodity.imgFile.setImg(commodity.pictures, imageMessage);
	goodsAdd.prodSpec.editor.html(goodsAdd.commodity.prodDescFormat(commodity.commodityDesc));
	//校验外链
	//goodsAdd.prodSpec.checkOuterLink();
	$("#goods_commodityId").val(commodity.id);
	$("#goods_commodityNo").val(commodity.commodityNo);
	//设置商品状态
	goodsAdd.commodity.commodityStatus = commodity.status;
	if(isSensitive){
		//检测敏感词
		 $.post(basePath+"/commodity/checkSensitiveWord.sc",{
         	name:commodity.commodityName,
         	sellingPoint:commodity.sellingPoint,
         	prodDesc:goodsAdd.prodSpec.editor.text()
         	},function(json){
         		if($(json.result).size()>0){
         			var nameArr = new Array(),wordArr = new Array(),idArr = new Array();
         			var sensiveWord = "";
            		$.each(json.result,function(i,n){
            			nameArr[i] = n.name;
            			wordArr[i] = n.sensiveWord;
            			idArr[i] = n.id;
            			$("#"+n.id+"_tip").removeClass("none").html("敏感词："+n.sensiveWord);
            			sensiveWord += wordArr[i];
            			if(i<$(json.result).size()-1){
            				sensiveWord += ",";
            			}
            		});
            		$(".fix_title span:last").html(nameArr.join("，")+"检测到敏感词：<b class='cred'>"+$.unique(sensiveWord.split(",").sort()).join("，")+"</b>");
         		}
         	},"json");
	}
};

/**
 * 设置年份
 * @param {String} years 年份
 */
goodsAdd.commodity.setYears = function(years) {
	$("#goods_years_" + years).click();
};

/**modified by lyx at 2015-3-15*/
/**设置商品属性的目标函数*/
function propertiesTargetFun() {
	if(!goodsAdd.commodity.originProp || goodsAdd.commodity.originProp.length <= 0){
		return;
	}
	var commdoityProps = goodsAdd.commodity.originProp;
	var prop = null;
	for (var i = 0, len = commdoityProps.length; i < len; i++) {
		prop =  commdoityProps[i];
		var propItemNo = prop.propItemNo;
		var propItemName = prop.propItemName;
		var propValueNo = prop.propValueNo;
		var propValueName = prop.propValueName;
		var propItemId = prop.id;
		//属性是否多选
		var valueType = $("#goods_prop_valuetype_"+propItemNo).val();
		if (valueType == '0') {
			//存在options
			if($("#"+propItemNo + " option[value="+propValueNo+"]").length>0){
				$("#"+propItemNo).parent("div").hide();
				$("#"+propItemNo).attr("show",'');
				$("#" + propItemNo).attr("propitemid", propItemId);
				$("#goods_prop_valuetype_"+propItemNo).siblings(".goods_span_val").html(propValueName);
				goodsAdd.setSelectValue(propItemNo,propValueNo);
				$("#"+propItemNo).reJqSelect();
				goodsAdd.properties.selectChange($("#"+propItemNo));
			}
		} else if (valueType == '1') {
			//属性多选
			//属性值存在时
			if($("#shut" + propItemNo + "_" + propValueNo).length>0){
				$("#shut" + propItemNo + "_" + propValueNo).attr('checked', true);
				$("#goods_prop_valuetype_"+propItemNo).siblings(".shutting_span_val").append(propValueName+",");
				$("#goods_prop_valuetype_"+propItemNo).siblings(".shutting_click").removeClass("show").addClass("hide");
				//加入属性id和商品id
				var _valueTypeObj = $("#goods_prop_valuetype_" + propItemNo);
				var _propItemId = _valueTypeObj.attr("propitemid");
				var _propValueNo = _valueTypeObj.attr("propvalueno");
				if (_propItemId == null || _propItemId.length == 0) {
					_valueTypeObj.attr("propitemid", propItemId);
					_valueTypeObj.attr("propvalueno", propValueNo);
				} else {
					_valueTypeObj.attr("propitemid", _propItemId + ";" + propItemId);
					_valueTypeObj.attr("propvalueno", _propValueNo + ";" + propValueNo);
				}
				goodsAdd.properties.confirm(propItemNo, propItemName);
			}
		}
	}
	deleteLastComma();
	li_height();
	
	if(templateId){
		useTemplate(templateId);
		templateId = null;
	}
}

/**
 * 设置商品属性
 * @param {Array} commdoityProps 商品属性数组
 */
goodsAdd.commodity.setProperties = function(commdoityProps) {
	if(commdoityProps == null || typeof(commdoityProps) == "undefined" || 
			commdoityProps.length == 0) {
		return;
	}
	/**缓存原属性值*/ 
	goodsAdd.commodity.originProp = commdoityProps;
	/**设置商品属性的条件*/
	function propertiesCondition() {
		return $("#left ul input").length > 0;
	}
	createDetector("propertiesDetector", propertiesCondition, propertiesTargetFun, 400);
};

/**
 * 设置颜色
 * @param {String} colorNo 颜色编号
 * @param {String} colorName 颜色名称
 */
goodsAdd.commodity.setColor = function(colorNo, colorName) {
	//颜色包含'_'
	if(colorName.indexOf("_")!=-1){
		colorName = colorName.substr(colorName.lastIndexOf("_")+1);
	}
	$("#goods_specName").val(colorName);
	var radios = $("#colorDiv .color_radio");
	var haveChecked = false;
	for(var i=0,_len=radios.length;i<_len;i++){
		if(radios.eq(i).siblings(".color_text").val()==colorName){
			radios.eq(i).attr('checked', true);
			haveChecked = true;
		}
	}
	if(!haveChecked){
		var conDiv = '<li><input type="radio" value="" id="c-'+(radios.length+1)+'" class="color_radio" name="color_radio" checked="checked">'+
					 '<label for="c-'+(radios.length+1)+'" class="color_bg"></label><input type="text" value="'+colorName+'" id="color-'+(radios.length+1)+'" class="color_text"></li>';
		$("#colorDiv ul").append(conDiv);
	}
};

/**设置尺寸目标函数 modified by lyx at 2015-3-11*/
function sizeTargetFun() {
	if(goodsAdd.commodity.originSize && goodsAdd.commodity.originSize.length > 0) {
		var products = goodsAdd.commodity.originSize;
		var prod = null;
		//货品编号串
		var prodNoStr = '';
		//货品编号/尺码编号 map
		var prodNoSizeNoMap = {};
		for (var i = 0, len = products.length; i < len; i++) {
			prod = products[i];
			$("#goods_sizeNo_display_" + prod.sizeNo).attr("checked", "true");
			//触发尺码的点击事件
			//goodsAdd.size.size_OnClick($("#goods_sizeNo_display_" + prod.sizeNo)[0]);
			$("#goods_sizeNo_display_" + prod.sizeNo).click();
			$("#goods_sizeNo_display_" + prod.sizeNo).attr("checked", "true");
			$("#goods_sizeNo_display_" + prod.sizeNo).attr("sellStatus", prod.sellStatus);
			//设置货品信息
			//设置重量
			$("#weight_" + prod.sizeNo).val(
				parseInt(prod.weight) == 0 ? "" : parseInt(prod.weight));
			//设置货品条码
			$("#sku_" + prod.sizeNo).val(prod.thirdPartyInsideCode);
			//加入productId
			$("#sku_" + prod.sizeNo).attr("productId", prod.id);
			$("#goods_product_tr_" + prod.sizeNo).attr("productId", prod.id);
			//加入productNo
			$("#sku_" + prod.sizeNo).attr("productNo", prod.productNo);
			$("#goods_product_tr_" + prod.sizeNo).attr("productNo", prod.productNo);
			//按尺码设置价格
			if(isSetSizePrice){
				$("#salePriceStr_" + prod.sizeNo).val(goodsAdd.commodity.sellPrice);
				$("#publicPriceStr_"+ prod.sizeNo).val(goodsAdd.commodity.markPrice);
				$("#salePriceStr_" + prod.sizeNo).attr("productId",prod.id);
				$("#salePriceStr_" + prod.sizeNo).attr("productNo",prod.productNo);
				$("#publicPriceStr_"+ prod.sizeNo).attr("productId",prod.id);
				$("#publicPriceStr_" + prod.sizeNo).attr("productNo",prod.productNo);
			}
			prodNoStr += prod.productNo + ",";
			prodNoSizeNoMap[prod.productNo] = prod.sizeNo;
		}
		//$("#goods_product_color_td").text(colorName);
		if(prodNoStr.length != 0) {
			prodNoStr = prodNoStr.substring(0, prodNoStr.length - 1);
		}
		//加载库存
		goodsAdd.commodity.stock.load(prodNoStr, prodNoSizeNoMap);
	}
}

/**
 * 设置尺寸
 * @param {String} products 货品数组
 */
goodsAdd.commodity.setSize = function(products,colorName) {
	/**尺寸存在条件*/
	function sizeCondition() {
		return $(".size-list input[name='goods_sizeNo']").length > 0;
	}
	
	if(products && products.length){
		goodsAdd.commodity.originSize = products;
	}
	 	 
	createDetector("sizeDetector", sizeCondition, sizeTargetFun, 350);
};

/**
 * 加载库存
 * @param {String} prodNoStr 货品编号串
 * @param {Object} prodNoSizeNoMap 货品编号 / 尺码编号 map
 */
goodsAdd.commodity.stock.load = function(prodNoStr, prodNoSizeNoMap) {
	//如果入优购仓
	if(isInputYougouWarehouseFlag) return;
	
	$.ajax({
		url: goodsAdd.url.getInventoryByProdNosUrl + prodNoStr,
		type: "POST",
		dataType: "json",
		success: function(data) {
			if(data == null || typeof(data) != "object") return;
			
			for(var prodNo in data) {
				var sizeNo = prodNoSizeNoMap[prodNo];
				var stock = parseInt(data[prodNo]);
				$("#stock_" + sizeNo).val(stock);
			}
		},
		error: function() {
			alert("网络异常，加载货品库存出错，请刷新后重试!");
		}
	});
};

/**
 * 图片路径数组
 */
goodsAdd.commodity.imgFile.imgPathList = [];

/**
 * 设置图片
 * @param {Array} pictures 图片数组
 */
goodsAdd.commodity.imgFile.setImg = function(pictures, imageMessage) {
	//图片缓存
	var imgFileIds = [];
	if (null != imageMessage) {
		imgFileIds = imageMessage.commodityImages;
	}
	
	var d = new Date();
	//图片域名
	var picDomain = commodityPreviewDomain;
	//img标签集合
	var $imgs = $(".pm-list .pm-box");
	var pic = null;
	var imgObj = null;
	for (var i = 0, len = ADD_OR_UPDATE_COMMODITY_IMAGE_COUNT; i < len; i++) {
		imgObj = $imgs[i];
		var imgPattern = "_" + goodsAdd.commodity.imgFile.formatNum(i + 1) + 
			IMG_NAME_SIZE_1000_1000_SUFFIX + IMG_NAME_EXT_TYPE;
		
		for (var j = 0, len1 = pictures.length; j < len1; j++) {
			pic = pictures[j];
			if(pic.picName.indexOf(imgPattern) != -1) {
				$('#img_file_id_' + (i + 1)).val('0');//设置0为已经有图片
				var imgUrl = (picDomain + pic.picPath + pic.picName).replace('_l.jpg','_ms.jpg')+"?flag="+d.getTime();
				//添加到图片路径数组中并标记状态为非瞬时
				goodsAdd.commodity.imgFile.imgPathList[i] = imgUrl;
				$(imgObj).html("<img src='"+imgUrl+"' transient='false'>");
			}
		}
		
		var re=/.jpg$/;
		//先尝试从缓存取图片
		if(imgFileIds.length>0&&i<imgFileIds.length){
			var imgUrl = imgFileIds[i].picUrl;
			if (imgUrl == '0' || imgUrl == '-1') continue;
			$('#img_file_id_' + (i + 1)).val(imgUrl);//设置临时图片Id
			$(imgObj).attr("src", imgUrl.replace(re,".png")).attr('transient', 'false');//显示小图;
			$(imgObj).html("<img src='"+imgUrl.replace(re,".png")+"' transient='false'>");
		}
	}
};

/**
 * 格式化索引号
 * @param {Number} index 图片索引号
 */
goodsAdd.commodity.imgFile.formatNum = function(index) {
	var result = "";
	if(index < 10) {
		result = "0" + index;
	} else {
		result = String(index);
	}
	return result;
};

/**
 * 格式化商品描述字符串
 * @param prodDesc 描述字符串
 */
goodsAdd.commodity.prodDescFormat = function(prodDesc) {
	if(prodDesc){
		return prodDesc.replace(/<image/gi, '<img');
	}
};

$(function() {
	//待调用的验证函数列表
	goodsAdd.validate.validateFunList = ["validateBrandNo", "validateCattegory","validateCommodityName", "validateStyleNo",
			"validateSupplierCode4Update", "validateSalePrice", "validatePublicPrice", "validateGoodsProp",
			"validateSpecNo", "validateSizeNo", "validteStock", "validateThirdPartyCode", "validateWeight",
			"validateCommodityImgForUpdate", "validateProdDesc"];
	
	//加载商品
	showOrDisableBtn("disable");
	setTimeout(function() {
		//针对修改商品，预览按钮可见
		$("#commodity_pre").show();
		$("#commodity_pre_f").show();
		goodsAdd.commodity.load();
	}, 800);
});

function showOrDisableBtn(op){
	var opResult = false;
	if(op=="disable"){
		opResult = true;
	}
	isforbiddenButtonNew('commodity_save', opResult, function(){goodsAdd.submit.submitForm(false);});
	isforbiddenButtonNew('commodity_audit', opResult, function(){goodsAdd.submit.submitForm(true);});
	isforbiddenButtonNew('commodity_pre', opResult,function(){goodsAdd.submit.preview();});
	isforbiddenButtonOfQuick('commodity_save_f', opResult, function(){goodsAdd.submit.submitForm(false);});
	isforbiddenButtonOfQuick('commodity_audit_f', opResult, function(){goodsAdd.submit.submitForm(true);});
	isforbiddenButtonOfQuick('commodity_pre_f', opResult,function(){goodsAdd.submit.preview();});
}
