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

/**加载商品url*/
goodsAdd.url.getCommodityByNo = basePath + "/commodity/getCommodityByNo.sc?commodityNo=" + requestCommodityNo; 
/**通过货品编号获取库存url*/
goodsAdd.url.getInventoryByProdNosUrl = basePath + "/commodity/getInventoryByProdNo.sc?prodNoStr=";
/**表单提交url*/
goodsAdd.url.commodityListUrls = {};
goodsAdd.url.commodityListUrls[QUERY_COMMODITY_PAGE_TAB_ID_DRAFT] = basePath + "/commodity/goQueryDraftCommodity.sc";
goodsAdd.url.commodityListUrls[QUERY_COMMODITY_PAGE_TAB_ID_PENDING] = basePath + "/commodity/goQueryPendingCommodity.sc";
goodsAdd.url.commodityListUrls[QUERY_COMMODITY_PAGE_TAB_ID_PASS] = basePath + "/commodity/goQueryPassCommodity.sc";
goodsAdd.url.commodityListUrls[QUERY_COMMODITY_PAGE_TAB_ID_REFUSE] = basePath + "/commodity/goQueryRefuseCommodity.sc";
goodsAdd.url.commodityListUrls[QUERY_COMMODITY_PAGE_TAB_ID_ALL] = basePath + "/commodity/goQueryAllCommodity.sc";



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
 * 预览图片 mouseover 事件
 * @param {Object} inputFile 上传框对象
 */
goodsAdd.imageUpload.previewImg_OnMouseOver = function(inputFile) {
	var sortNo = $.trim($(inputFile).attr("sortNo"));
	$("#goods_img_file_opt_" + sortNo).show();
};

/**
 * 预览图片 mouseout 事件
 * @param {Object} inputFile 上传框对象
 */
goodsAdd.imageUpload.previewImg_OnMouseOut = function(inputFile) {
	var sortNo = $.trim($(inputFile).attr("sortNo"));
	$("#goods_img_file_opt_" + sortNo).hide();
};

/**
 * 删除操作 点击事件
 * @param {Object} self 被点击的操作对象
 */
goodsAdd.imageUpload.previewOptDelete_OnClick = function(self, number) {
	$(self).parent().parent().unbind("mouseover");
	$(self).parent().parent().unbind("mouseout");
	$(self).parent().hide();
	
	var sortNo = $.trim($(self).attr("sortNo"));
	var src = goodsAdd.commodity.imgFile.imgPathList[sortNo - 1];
	var new_upload = false;
	if (src == null || src == 'undefined' || src.length == 0) {
		src = basePath + '/yougou/images/unknow_img.png';
		new_upload = true;
	} 
	//
	//把预览区替换成原图片
	var msg = formatString(
		'<img src="{#src}" class="goods_img_image" width="100" height="100" />',
		{
			src: src
		});
	$(self).parent().parent().find(".goods_img_layer").html(msg);
	
	//原上传input
	var originInputFile = $("#goods_img_file_" + sortNo);
	var inputId = $.trim(originInputFile.attr("id"));
	var inputName = $.trim(originInputFile.attr("name"));
	var newInputFileHtml = formatString(
		'<input type="file" id="{#inputId}" sortNo="{#sortNo}" name="{#inputName}" isFirstChange="0" class="detail_jq_file_btn" onchange="goodsAdd.imageUpload.inputFile_OnChange(this, {#number})" />',
		{
			inputId: inputId,
			inputName: inputName,
			sortNo: sortNo,
			number: number
		});
	//移除原上传input
	$(originInputFile).parent().remove();
	$("#img_file_id_"+number).val(new_upload ? "-1" : "0");
	//加入新的上传input
	$(self).parent().parent().append(newInputFileHtml);
	//渲染上传框
	$("#" + inputId).jqFileBtn({text: "上传图片"});
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
			var propItemId = $.trim($("#goods_prop_select_" + propItemNo).attr("propItemId"));
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
			var propItemId = $.trim($("#goods_prop_valuetype_" + propItemNo).attr("propItemId"));
			var propValueNo = $.trim($("#goods_prop_valuetype_" + propItemNo).attr("propValueNo"));
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
			//删除一行货品信息
			goodsAdd.prodInfo.removeTableLine(sizeValue, self, colorName);
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
	var $sizeNoTxts = $("#goods_selected_sizeNo_layer input");
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
		ygdg.dialog.alert(errMsg, function(){
			var errorList = goodsAdd.validate.errorList = data.errorList;
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
		            				var auditStatus = $("#auditStatus").val();
		            				if (data.isAddCommoditySaveSubmit && auditStatus == 0) { //提交审核后跳转到待审核页面
		            					auditStatus = 3;
		            				} 
		            				location.assign(goodsAdd.url.goForSaleCommodity + "?auditStatus=" + auditStatus);
		            			}, 1000);
		            		return true;
		              },
		              focus: true
		          }
		      ]
	});
	
};
function toAddNewGoods(){
	// 跳转到当前页面的新发布状态
	location.assign(basePath + "/commodity/goAddCommodity.sc");
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
			ygdg.dialog({id: "loadCommodityDialog"}).close();
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
		},
		error: function() {
			alert("网络异常，请刷新后重试!");
			ygdg.dialog({id: "loadCommodityDialog"}).close();
		}
	});
};

/**
 * 填充商品信息
 * @param {Object} commodity 商品
 */
goodsAdd.commodity.fillInfo = function(commodity, imageMessage) {
	goodsAdd.commodity.cat.setCats(commodity.catStructName, commodity.brandNo);
	goodsAdd.commodity.brand.setBrand(commodity.brandNo);
	$("#goods_commodityName").val(commodity.commodityName);
	$("#goods_sellingPoint").val(commodity.sellingPoint);
	$("#goods_styleNo").val(commodity.styleNo);
	$("#goods_supplierCode").val(commodity.supplierCode);
	$("#goods_salePrice").val(commodity.sellPrice);
	$("#goods_publicPrice").val(commodity.markPrice);
	goodsAdd.commodity.setYears(commodity.years);
	goodsAdd.commodity.setProperties(commodity.commdoityProps);
	goodsAdd.commodity.setColor(commodity.colorNo, commodity.colorName);
	goodsAdd.commodity.setSize(commodity.products);
	goodsAdd.commodity.imgFile.setImg(commodity.pictures, imageMessage);
	goodsAdd.prodSpec.editor.html(goodsAdd.commodity.prodDescFormat(commodity.commodityDesc));
	//校验外链
	goodsAdd.prodSpec.checkOuterLink();
	
	$("#goods_commodityId").val(commodity.id);
	$("#goods_commodityNo").val(commodity.commodityNo);
	
	//设置商品状态
	goodsAdd.commodity.commodityStatus = commodity.status;
};

/**
 * 设置分类
 * @param {String} catStructName 分类结构名称
 */
goodsAdd.commodity.cat.setCats = function(catStructName, brandNo) {
	//设置分类前要先加载分类信息
	var categories = [];
	initCatForBrand(brandNo, 'category1', categories);
	
	$("#category1").change(function() {
		reinitializeOption($(this).find(':selected').attr('id'), 2, '#category2', categories);
	});
	
	$("#category2").change(function() {
		reinitializeOption($(this).find(':selected').attr('id'), 3, '#category3', categories);
	});
	
	var cats = catStructName.split("-");
	if(cats == null || cats.length != 3) return;
	var cat1 = cats[0];  //第一级 
	var cat2 = cats[1];  //第二级
	var cat3 = cats[2];  //第三级
	
	//设置一级分类
	goodsAdd.commodity.cat.setCat1(cat1, cat2, cat3);
	//设置二级分类
	goodsAdd.commodity.cat.setCat2(cat1, cat2, cat3);
	//设置三级分类
	goodsAdd.commodity.cat.setCat3(cat1, cat2, cat3);
	
};

/**
 * 设置一级分类
 * @param {String} cat1 一级分类
 * @param {String} cat2 二级分类
 * @param {String} cat3 三级分类
 */
goodsAdd.commodity.cat.setCat1 = function(cat1, cat2, cat3) {
	function cat1Condition() {
		return true;
	}
	
	function cat1TargetFun() {
		goodsAdd.commodity.cat.setCatSelectValue("category1", cat1);
		$("#category1").reJqSelect();
		$("#category1").change();
	}
	createDetector("cat1Detector", cat1Condition, cat1TargetFun, 200);
};

/**
 * 设置二级分类
 * @param {String} cat1 一级分类
 * @param {String} cat2 二级分类
 * @param {String} cat3 三级分类
 */
goodsAdd.commodity.cat.setCat2 = function(cat1, cat2, cat3) {
	/**二级分类的条件*/
	function cat2Condition() {
		return $("#category2>option").length > 1;
	}
	/**二级分类目标函数*/
	function cat2TargetFun() {
		goodsAdd.commodity.cat.setCatSelectValue("category2", cat1 + "-" + cat2);
		$("#category2").reJqSelect();
		$("#category2").change();
	}
	createDetector("cat2Detector", cat2Condition, cat2TargetFun, 200);
};

/**
 * 设置三级分类
 * @param {String} cat1 一级分类
 * @param {String} cat2 二级分类
 * @param {String} cat3 三级分类
 */
goodsAdd.commodity.cat.setCat3 = function(cat1, cat2, cat3) {
	/**二级分类的条件*/
	function cat3Condition() {
		return $("#category3>option").length > 1;
	}
	/**二级分类目标函数*/
	function cat3TargetFun() {
		goodsAdd.commodity.cat.setCatSelectValue("category3", cat1 + "-" + cat2 + "-" + cat3);
		$("#category3").reJqSelect();
		$("#category3").change();
		//使分类变为ReadOnly
		goodsAdd.commodity.cat.makeReadonly();
	}
	createDetector("cat3Detector", cat3Condition, cat3TargetFun, 250);
};

/**
 * 使分类变为ReadOnly
 */
goodsAdd.commodity.cat.makeReadonly = function() {
	//显示为只读版的商品分类
	var rootCatName = $("#category1>option:selected").text();
	var secondCatName = $("#category2>option:selected").text();
	var threeCatName = $("#category3>option:selected").text();
	var catText = rootCatName + " &gt; " + secondCatName + " &gt; " + threeCatName;
	$("#goods_cat_layer").hide();
	$("#goods_cat_copy_layer .detail_item_content").html(catText);
	$("#goods_cat_copy_layer").show();
};

/**
 * 设置分类下拉框的值
 * @param {String} id 下拉框id
 * @param {String} curStructName 要选中的分类结构名称
 */
goodsAdd.commodity.cat.setCatSelectValue = function(id , curStructName) {
	var $options = $("#" + id + ">option");
	var option = null;
	for (var i = 0, len = $options.length; i < len; i++) {
		option = $options[i];
		var catInfo = option.value.split(";");
		if(catInfo != null && catInfo.length >= 1) {
			var structName = catInfo[0];
			if(structName == (curStructName)) {
				document.getElementById(id).selectedIndex = i;
			}
		}
	}
};

/**
 * 设置品牌
 * @param {String} brandNo 品牌编号
 */
goodsAdd.commodity.brand.setBrand = function(brandNo) {
	setSelectValue("brandNo", brandNo);
	$("#brandNo").reJqSelect();
	
	//使品牌变为只读
	goodsAdd.commodity.brand.makeReadOnly();
};

/**
 * 使品牌变为只读
 */
goodsAdd.commodity.brand.makeReadOnly = function() {
	var brandName = $("#brandNo>option:selected").text();
	$("#goods_brand_layer").hide();
	$("#goods_brand_copy_layer .detail_item_content").html(brandName);
	$("#goods_brand_copy_layer").show();
};

/**
 * 设置年份
 * @param {String} years 年份
 */
goodsAdd.commodity.setYears = function(years) {
	if($("#goods_years_" + years).length != 0) {
		$("#goods_years_" + years).click();
	} else {
		var yearHtml = formatString(
			'<input type="radio" id="goods_years_{#years}" value="{#years}" name="years" /> ' +
			'<label for="goods_years_{#years}">{#years}</label>', 
			{
				years: years
			});
		$("#goods_years_layer .detail_item_content").append(yearHtml);
		$("#goods_years_" + years).click();
	}
	//禁止修改年份
	//$(":radio","#goods_years_layer").attr("disabled","disabled");
};

/**
 * 设置商品属性
 * @param {Array} commdoityProps 商品属性数组
 */
goodsAdd.commodity.setProperties = function(commdoityProps) {
	if(commdoityProps == null || typeof(commdoityProps) == "undefined" || 
			commdoityProps.length == 0) {
		return;
	}
	/**设置商品属性的条件*/
	function propertiesCondition() {
		return $("#goods_prop_layer .detail_item_content select").length > 1;
	}
	/**设置商品属性的目标函数*/
	function propertiesTargetFun() {
		var prop = null;
		for (var i = 0, len = commdoityProps.length; i < len; i++) {
			prop =  commdoityProps[i];
			var propItemNo = prop.propItemNo;
			var propItemName = prop.propItemName;
			var propValueNo = prop.propValueNo;
			var propItemId = prop.id;
			var commodityId = prop.commodityId;
			//属性是否多选
			var valueType = $("#goods_prop_valuetype_"+propItemNo).val();
			if (valueType == '0') {
				setSelectValue("goods_prop_select_" + propItemNo, propValueNo);
				$("#goods_prop_select_" + propItemNo).reJqSelect();
				$("#goods_prop_select_" + propItemNo).change();
				//加入属性id和商品id
				$("#goods_prop_select_" + propItemNo).attr("propItemId", propItemId);
				$("#goods_prop_select_" + propItemNo).attr("commodityId", commodityId);
			} else if (valueType == '1') {
				//属性多选
				$("#goods_prop_display_" + propItemNo + "_" + propValueNo).attr('checked', true);
				goodsAdd.properties.confirm(propItemNo, propItemName);
				//加入属性id和商品id
				var _valueTypeObj = $("#goods_prop_valuetype_" + propItemNo);
				var _propItemId = _valueTypeObj.attr("propItemId");
				var _propValueNo = _valueTypeObj.attr("propValueNo");
				if (_propItemId == null || _propItemId.length == 0) {
					_valueTypeObj.attr("propItemId", propItemId);
					_valueTypeObj.attr("propValueNo", propValueNo);
				} else {
					_valueTypeObj.attr("propItemId", _propItemId + ";" + propItemId);
					_valueTypeObj.attr("propValueNo", _propValueNo + ";" + propValueNo);
				}
			}
		}
	}
	createDetector("propertiesDetector", propertiesCondition, propertiesTargetFun, 400);
};

/**
 * 设置颜色
 * @param {String} colorNo 颜色编号
 * @param {String} colorName 颜色名称
 */
goodsAdd.commodity.setColor = function(colorNo, colorName) {
	$("#goods_specName").val(colorName);
};

/**
 * 设置尺寸
 * @param {String} products 货品数组
 */
goodsAdd.commodity.setSize = function(products) {
	/**尺寸存在条件*/
	function sizeCondition() {
		return $(".goods_sizeNo_checkboxes").length > 0;
	}
	/**设置尺寸目标函数*/
	function sizeTargetFun() {
		if(products && products.length) {
			var prod = null;
			//货品编号串
			var prodNoStr = '';
			//货品编号/尺码编号 map
			var prodNoSizeNoMap = {};
			for (var i = 0, len = products.length; i < len; i++) {
				prod = products[i];
				$("#goods_sizeNo_display_" + prod.sizeNo).attr("checked", "true");
				$("#goods_sizeNo_display_" + prod.sizeNo).attr("sellStatus", prod.sellStatus);
				//触发尺码的点击事件
				goodsAdd.size.size_OnClick($("#goods_sizeNo_display_" + prod.sizeNo)[0]);
				
				//设置货品信息
				//设置重量
				$("#goods_weightStr_" + prod.sizeNo).val(
					parseInt(prod.weight) == 0 ? "" : parseInt(prod.weight));
				//设置货品条码
				$("#goods_thirdPartyCode_" + prod.sizeNo).val(prod.thirdPartyInsideCode);
				//加入productId
				$("#goods_thirdPartyCode_" + prod.sizeNo).attr("productId", prod.id);
				$("#goods_product_tr_" + prod.sizeNo).attr("productId", prod.id);
				//加入productNo
				$("#goods_thirdPartyCode_" + prod.sizeNo).attr("productNo", prod.productNo);
				$("#goods_product_tr_" + prod.sizeNo).attr("productNo", prod.productNo);
				
				prodNoStr += prod.productNo + ",";
				prodNoSizeNoMap[prod.productNo] = prod.sizeNo;
			}
			if(prodNoStr.length != 0) {
				prodNoStr = prodNoStr.substring(0, prodNoStr.length - 1);
			}
			
			//加载库存
			goodsAdd.commodity.stock.load(prodNoStr, prodNoSizeNoMap);
		}
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
				$("#goods_stock_" + sizeNo).val(stock);
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
	var $imgs = $("#goods_img_file_layer .goods_img_image");
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
				$(imgObj).attr("src", imgUrl).attr('transient', 'false');//显示小图
			}
		}
		
		var re=/.jpg$/;
		//先尝试从缓存取图片
		if(imgFileIds.length>0&&i<imgFileIds.length){
			var imgUrl = imgFileIds[i].picUrl;
			if (imgUrl == '0' || imgUrl == '-1') continue;
			$('#img_file_id_' + (i + 1)).val(imgUrl);//设置临时图片Id
			$(imgObj).attr("src", imgUrl.replace(re,".png")).attr('transient', 'false');//显示小图;
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
	return prodDesc.replace(/<image/gi, '<img');
};

$(function() {
	//待调用的验证函数列表
	goodsAdd.validate.validateFunList = ["validateCommodityName", "validateStyleNo",
			"validateSupplierCode", "validateSalePrice", "validatePublicPrice", "validateGoodsProp",
			"validateSpecNo", "validateSizeNo", "validteStock", "validateThirdPartyCode", "validateWeight",
			"validateCommodityImgForUpdate", "validateProdDesc"];
	
	ygdg.dialog({id: "loadCommodityDialog", title:'提示', 
			content: '<img src="' + goodsAdd.url.loadingImgUrl + '" width="16" height="16" /> 加载中...', 
			lock:false, closable:false});
	//加载商品
	setTimeout(function() {
		goodsAdd.commodity.load();
	}, 800);
});
