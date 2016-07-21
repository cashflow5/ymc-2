
/**获取商品属性、颜色、尺码的url*/
goodsAdd.url.getCommodityPropertitiesUrl = basePath + "/commodity/getCommodityPropertitiesByCatId.sc?catId=";
/**查询新建商品url*/
goodsAdd.url.goQueryDraftCommodityUrl = basePath + "/commodity/goQueryDraftCommodity.sc";
/**查询待审核商品url*/
goodsAdd.url.goQueryPendingCommodityUrl = basePath + "/commodity/goQueryPendingCommodity.sc";
/**查询审核拒绝商品url*/
goodsAdd.url.goQueryRefuseCommodity = basePath + "/commodity/goQueryRefuseCommodity.sc";
/**查询全部商品url*/
goodsAdd.url.goQueryAllCommodityUrl = basePath + "/commodity/goQueryAllCommodity.sc";
/**待售全部商品url*/
goodsAdd.url.goForSaleCommodity = basePath + "/commodity/goWaitSaleCommodity.sc";
/**提交成功图片url*/
goodsAdd.url.successImgUrl = basePath + "/yougou/js/ygdialog/skins/icons/succeed.png";
/**loading图片 url*/
goodsAdd.url.loadingImgUrl = basePath + "/yougou/js/ygdialog/skins/icons/loading.gif";
/**商品描述图片选择器url*/
goodsAdd.url.descImgSelectorUrl = basePath + '/commodity/pics/selector.sc';

goodsAdd.url.queryBrandCatUrl = basePath + "/commodity/queryBrandCat.sc";


/**
 * 第三级分类onchange事件
 * @param {String} value 当前选中的值
 */
goodsAdd.cat.threeCat_OnChange = function(value) {
	value = $.trim(value);
	if(value.length == 0) {
		return;
	}
	//清空货品信息
	$("#goods_color_size_tbody").html("");
	var catInfos = value.split(";");
	if(catInfos != null && catInfos.length > 2) {
		//分类id
		var catId = $.trim(catInfos[1]);
		$.ajax({
			url: goodsAdd.url.getCommodityPropertitiesUrl + catId,
			type: "GET",
			dataType: "json",
			success: function(data) {
				if(data == null || typeof(data) != "object" || data.isSuccess == null ||
						typeof(data.isSuccess) == "undefined") {
					return;
				}
				if(data.isSuccess != "true") {
					ygdg.dialog.alert(data.errorMsg);
					return;
				}
				goodsAdd.cat.setSheetIndex(data.sheetIndex);
				//加载商品属性
				goodsAdd.properties.load(data.propList);
				
				//加载尺码列表
				goodsAdd.size.load(data.sizeList);
				
				//属性变化事件
				$(".goods_properties_select").change(function() {
					goodsAdd.properties.select_OnChange(this);
				});
				
				//颜色输入框鼠标事件
				$("#goods_specName").blur(function() {			
					$("#goods_product_color_td").html($("#goods_specName").val());
				});
				
				//尺码的点击事件
				$(".goods_sizeNo_checkboxes").click(function() {
					goodsAdd.size.size_OnClick(this);					
				});
			},
			error: function() {
				ygdg.dialog.alert("网络异常，请刷新后重试!");
			}
		});
	}
};

goodsAdd.cat.setSheetIndex=function(sheetIndex){
	var indexValue=$("#img_file_id_"+sheetIndex).val();
	$("#goods_img_file_layer img[src$='master_img.png']").attr("src","/yougou/images/unknow_img.png");
	if(indexValue=="-1"){
		$("#goods_img_layer_"+sheetIndex +" img").attr("src","/yougou/images/master_img.png");
	}
}
/**
 * 清除商品属性、颜色、尺码
 */
goodsAdd.cat.clearProperties = function() {
	$("#goods_prop_layer .detail_item_content").html("");
	$("#goods_color_layer .detail_item_content").html("");
	$("#goods_size_layer .detail_item_content").html("");
	
	$("#goods_selected_sizeNo_layer").html("");
	$("#goods_selected_sizeName_layer").html("");
};

/**
 * 品牌下拉框 onchange事件
 * @param {String} value 当前选中的值
 */
goodsAdd.brand.brand_OnChange = function(value) {
	value = $.trim(value);
	if(value.length == 0) {
		return;
	}
	var selectedText = $.trim($("#brandNo option:selected").text());
	$("#goods_commodityName").val(selectedText);
	
	$('#brandNo_error_tip').hide();
};

/**
 * 加载商品属性
 * @param {Array} propList 商品属性列表
 */
goodsAdd.properties.load = function(propList) {
	if(propList == null) return;
	if(!propList.length) return;
	var html = '';
	var propObj = null;	//单个属性
	var propValues = null;  //属性值数组
	var propValueObj = null;  //单个属性值
	var optionsHtml = null;  //select框中的选项html
	for (var i = 0, len = propList.length; i < len; i++) {
		propObj = propList[i];
		propValues = propObj.propValues;
		valueType = propObj.valueType;
		if(!Boolean(propValues) || !Boolean(propValues.length)) continue;
		//应产品的要求在发布商家商品时不显示如下字段 add by zhuang.rb at 2012-12-11
		if(propObj.propItemName=="所在区域" || propObj.propItemName=="名厂直销品牌" || propObj.propItemName=="名厂直销分类" || propObj.propItemName=="货品来源") continue;
		
		if (valueType == 1) {//多选
			var checkboxhtml = '<ul class="clearfix">';
			for (var j = 0, len2 = propValues.length; j < len2; j++) {
				propValueObj = propValues[j];
				checkboxhtml += formatString(
					'<li>' + 
					'	<input type="checkbox" id="goods_prop_display_{#propItemNo}_{#value}" name="goods_prop_checkbox_{#propItemNo}" value="{#value};{#name}" /> ' +
					'	<label for="goods_prop_display_{#value}">' +
					'		{#name}' + 
					'	</label>' +
					'</li>', 
					{
						value: propValueObj.propValueNo,
						name: propValueObj.propValueName,
						propItemNo: propObj.propItemNo
					});
			}
			checkboxhtml += '</ul>';
			html += formatString(
				'<div class="goods_prop_select_layer clearfix">' +
				'	<label>{#propItemName} ：{#propIsShowMall}</label>' + 
				'   <div class="fl" id="goods_prop_button_div_{#propItemNo}">' +
				'      <span id="goods_prop_checked_result_{#propItemNo}"></span>' +
				'      <span>' +
				'            <a id="checkbox_button_{#propItemNo}" onclick="goodsAdd.properties.showGoodsCheckBoxProp(\'{#propItemNo}\');" class="button" style="margin-left:0;">' +
				'               <span id="goods_prop_checked_button_{#propItemNo}">+选择</span>' +
				'            </a>' +
				'      </span>' +
				'   </div>' +
				'   <div class="goods_prop_content" id="goods_prop_hidden_{#propItemNo}">' +
				'       {#checkboxs}' + 
				'   	<p class="clearfix" style="width:200px;margin:0 auto;margin-top:10px;">' +
				'          <a onclick="goodsAdd.properties.confirm(\'{#propItemNo}\', \'{#propItemName}\')"  class="button fl" style="margin-left:0;"><span>确定</span></a>'+
				'          <a style="float:left;padding-left:100px;" href="javascript:;" onclick="goodsAdd.properties.cancel(\'{#propItemNo}\');">取消</a>' + 
				'       </p>' +
				'   </div>' +
				'   <input type="hidden" id="goods_prop_valuetype_{#propItemNo}" propItemNo="{#propItemNo}" propItemName="{#propItemName}" prodMustInput="{#clsMustInput}" value="1" />' +
				'   <span id="{#propItemNo}_error_tip" class="goods_error_tip"><img src="/yougou/images/error.gif" class="goods_error_image" />请选择商品属性</span>' +
				'</div>', 
				{
					propItemNo: propObj.propItemNo,
					propItemName: propObj.propItemName,
					propIsShowMall: propObj.isShowMall==0?"<span class='detail_item_star'>*</span>":"",
					clsMustInput: propObj.isShowMall==0?"true":"false",
					checkboxs: checkboxhtml
				});
		} else {
			//当前属性选项
			optionsHtml = '<option value="" selected="selected">请选择</option>';
			for (var j = 0, len2 = propValues.length; j < len2; j++) {
				propValueObj = propValues[j];
				optionsHtml += formatString(
					'<option value="{#propValueNo}" propValueName="{#propValueName}">{#propValueName}</option>',
					{
						propValueNo: propValueObj.propValueNo,
						propValueName: propValueObj.propValueName
					});
			}
			html += formatString(
				'<div class="goods_prop_select_layer clearfix">' +
				'	<label>{#propItemName} ：{#propIsShowMall}</label>' +
				'	<select id="goods_prop_select_{#propItemNo}" propItemNo="{#propItemNo}" propItemName="{#propItemName}" prodMustInput="{#clsMustInput}" >{#options}</select>' +
				'   <input type="hidden" id="goods_prop_valuetype_{#propItemNo}" value="0" />' + 
				'   <span id="{#propItemNo}_error_tip" class="goods_error_tip"><img src="/yougou/images/error.gif" class="goods_error_image" />请选择商品属性</span>' +
				'</div>', 
				{
					propItemNo: propObj.propItemNo,
					propItemName: propObj.propItemName,
					//用来标记或者判断商品扩展属性是否必选 add by zhuang.rb at 2012-12-11
					propIsShowMall: propObj.isShowMall==0?"<span class='detail_item_star'>*</span>":"",
					clsMustInput: propObj.isShowMall==0?"true":"false",
					options: optionsHtml
				});
		}
	}
	$("#goods_prop_layer .detail_item_content").html(html);
	//渲染下拉框
	var $propSelects = $("#goods_prop_layer .detail_item_content select");
	var propSelect = null;
	for (var i = 0, len = $propSelects.length; i < len; i++) {
		propSelect = $propSelects[i];
		$(propSelect).jqSelect();
		$(propSelect).addClass("goods_properties_select");
	}
};

/** 展开多选属性值 */
goodsAdd.properties.showGoodsCheckBoxProp = function(propItemNo){
	$("#goods_prop_hidden_"+propItemNo).show();
	$("#goods_prop_button_div_"+propItemNo).hide();
	//error_tip hide
	$('#' + propItemNo + '_error_tip').hide();
};

/**
 * 确定属性多选值
 * @param propItemNo 属性No
 * @param propItemName 属性名称
 */
goodsAdd.properties.confirm = function(propItemNo, propItemName) {
	//重置选择结果框
	$("#goods_prop_checked_result_"+propItemNo).empty();
	var html = '';
	var propValueNo = '';
	var propValueName = '';
	$($("input[name='goods_prop_checkbox_"+propItemNo+"']:checked")).each(
		function() {
			var a = this.value.split(";");
			if (a.length == 2) {
				propValueNo += a[0] + ";";	propValueName += a[1] + ";";
				html += formatString(
						'<span class="goods_prop_span_checked" id="goods_prop_checked_result_{#propItemNo}_{#value}">' +
						'<span class="fl tt">{#name}</span><a href="javascript:;" ' +  
						'       onclick="goodsAdd.properties.deleteGoodsCheckBoxProp(\'{#propItemNo}\', \'{#value}\', \'{#name}\');">' + 
						'	    ' +
						'   </a>' + 
						'</span>', 
						{
							value: a[0],
							name: a[1],
							propItemNo: propItemNo
						});
			}
		}
	);
	$("#goods_prop_checked_result_"+propItemNo).html(html);
	if (propValueNo.length > 0) {
		if ($("#goods_properties_propItemNo_" + propItemNo).length>0) {
			$("#goods_properties_propItemNo_" + propItemNo).val(propItemNo);
			$("#goods_properties_propItemName_" + propItemNo).val(propItemName);
			$("#goods_properties_propValueNo_" + propItemNo).val(propValueNo.substring(0, propValueNo.length - 1));
			$("#goods_properties_propValueName_" + propItemNo).val(propValueName.substring(0, propValueName.length - 1));
		} else {
			//添加隐藏域
			appendHidden("goods_properties_propItemNo_" + propItemNo,
				propItemNo, "propItemNo", "#goods_selected_properties_propItemNo");
			appendHidden("goods_properties_propItemName_" + propItemNo,
				propItemName, "propItemName", "#goods_selected_properties_propItemName");
			appendHidden("goods_properties_propValueNo_" + propItemNo,
				propValueNo, "propValueNo", "#goods_selected_properties_propValueNo");
			appendHidden("goods_properties_propValueName_" + propItemNo,
				propValueName, "propValueName", "#goods_selected_properties_propValue");
		}
		$("#goods_prop_checked_button_"+propItemNo).html("继续添加");
	} else {
		$("#goods_properties_propItemNo_" + propItemNo).remove();
		$("#goods_properties_propItemName_" + propItemNo).remove();
		$("#goods_properties_propValueNo_" + propItemNo).remove();
		$("#goods_properties_propValueName_" + propItemNo).remove();
		$("#goods_prop_checked_button_"+propItemNo).html("+选择");
	}
	
	$("#goods_prop_hidden_"+propItemNo).hide();
	$("#goods_prop_button_div_"+propItemNo).show();
};

/**
 * 取消本次选择操作/保留上次操作
 * @param propItemNo 属性No
 */
goodsAdd.properties.cancel = function(propItemNo) {
	$("#goods_prop_hidden_"+propItemNo).hide();
	$("#goods_prop_button_div_"+propItemNo).show();
	$($("input[name='goods_prop_checkbox_"+propItemNo+"']:checked")).each(function() {
		$(this).attr('checked', false);
	});
	//还原上次保留的操作
	var _propObj = $("#goods_properties_propValueNo_" + propItemNo);
	if (_propObj == null || typeof(_propObj) == "undefined") return false;
	var _propValueNos_temp = $("#goods_properties_propValueNo_" + propItemNo).val();
	if (_propValueNos_temp == null || typeof(_propValueNos_temp) == "undefined" || _propValueNos_temp.length == 0) return false;
	var _propValueNos = _propValueNos_temp.split(";");
	var _propValueNo = "";
	for (var i = 0, len = _propValueNos.length; i < len; i++) {
		_propValueNo = _propValueNos[i];
		if (_propValueNo != null && _propValueNo.length != 0) {
			$("#goods_prop_display_" + propItemNo + "_"+_propValueNo).attr('checked', true);
		}
	}
	
};

/**
 * 删除某属性的多选值项
 * @param propItemNo 被删除的属性项No
 * @param propValueNo 被取消的属性项值No
 * @param propValueName 被取消的属性项值Name
 */
goodsAdd.properties.deleteGoodsCheckBoxProp = function(propItemNo, propValueNo, propValueName) {
	$("#goods_prop_checked_result_"+propItemNo+"_"+propValueNo).remove();
	$("#goods_prop_display_" + propItemNo + "_" + propValueNo).attr('checked', false);
	
	var _propValueNo = $("#goods_properties_propValueNo_" + propItemNo).val();
	var _propValueName = $("#goods_properties_propValueName_" + propItemNo).val();
	var _Nos = "";
	var _Names = "";
	if (_propValueNo.search(propValueNo + ";") == -1) {
		_Nos = _propValueNo.replace(propValueNo, "");
		_Names = _propValueName.replace(propValueName, "");	
	} else {
		_Nos = _propValueNo.replace(propValueNo + ";", "");
		_Names = _propValueName.replace(propValueName + ";", "");	
	}
	$("#goods_properties_propValueNo_" + propItemNo).val(_Nos);
	$("#goods_properties_propValueName_" + propItemNo).val(_Names);
	if (_Nos.length == 0) {
		$("#goods_prop_checked_button_"+propItemNo).html("+选择");
		$("#goods_properties_propItemNo_" + propItemNo).remove();
		$("#goods_properties_propItemName_" + propItemNo).remove();
		$("#goods_properties_propValueNo_" + propItemNo).remove();
		$("#goods_properties_propValueName_" + propItemNo).remove();
	}
};

/**
 * 属性选择框变化事件
 * @param {Object} self 当前点击的属性选择框
 */
goodsAdd.properties.select_OnChange = function(self) {
	var $self = $(self);
	var selfValue = $.trim($self.val());
	var propItemNo = $.trim($self.attr("propItemNo"));  //属性编号
	var propItemName = $.trim($self.attr("propItemName"));  //属性名称
	if(selfValue.length != 0) {  //该属性项有值
		var propValueNo = selfValue;  //属性值编号
		var propValueName = $.trim($self.find("option:selected").attr("propValueName"));  //属性值名称
		if($("#goods_properties_propItemNo_" + propItemNo).length > 0) {  //不是首次选择
			$("#goods_properties_propItemNo_" + propItemNo).val(propItemNo);
			$("#goods_properties_propItemName_" + propItemNo).val(propItemName);
			$("#goods_properties_propValueNo_" + propItemNo).val(propValueNo);
			$("#goods_properties_propValueName_" + propItemNo).val(propValueName);
		} else {  //首次选择
			//添加隐藏域
			appendHidden("goods_properties_propItemNo_" + propItemNo,
				propItemNo, "propItemNo", "#goods_selected_properties_propItemNo");
			appendHidden("goods_properties_propItemName_" + propItemNo,
				propItemName, "propItemName", "#goods_selected_properties_propItemName");
			appendHidden("goods_properties_propValueNo_" + propItemNo,
				propValueNo, "propValueNo", "#goods_selected_properties_propValueNo");
			appendHidden("goods_properties_propValueName_" + propItemNo,
				propValueName, "propValueName", "#goods_selected_properties_propValue");
		}
	} else {  //选择了“请选择”
		$("#goods_properties_propItemNo_" + propItemNo).remove();
		$("#goods_properties_propItemName_" + propItemNo).remove();
		$("#goods_properties_propValueNo_" + propItemNo).remove();
		$("#goods_properties_propValueName_" + propItemNo).remove();
	}
	
	//error_tip hide
	$('#' + propItemNo + '_error_tip').hide();
};

/**
 * 清空商品属性信息
 */
goodsAdd.properties.clear = function() {
	$("#goods_selected_properties_propItemNo").html("");
	$("#goods_selected_properties_propItemName").html("");
	$("#goods_selected_properties_propValueNo").html("");
	$("#goods_selected_properties_propValue").html("");
};

/**
 * 加载尺码列表
 * @param {Array} sizeList 尺码列表
 */
goodsAdd.size.load = function(sizeList) {
	if(sizeList == null) return;
	if(!sizeList.length) return;
	if(sizeList.length > 1) {
		if(isNaN(sizeList[0].propValueName)) {
			//尺码列表排序(字符串)
			sizeList.sort(goodsAdd.size.sizeStringComparator);
		} else {
			//尺码列表排序(数字)
			sizeList.sort(goodsAdd.size.sizeNumberComparator);
		}
	}
	var html = '<ul>';
	var sizeObj = null;
	for (var i = 0, len = sizeList.length; i < len; i++) {
		sizeObj = sizeList[i];
		html += formatString(
			'<li>' +
			'	<input type="checkbox" id="goods_sizeNo_display_{#value}" class="goods_sizeNo_checkboxes" name="goods_size_list" value="{#value}" /> ' +
			'	<label for="goods_sizeNo_display_{#value}">' +
			'		{#name}' + 
			'	</label>' +
			'</li>', 
			{
				value: sizeObj.propValueNo,
				name: sizeObj.propValueName
			});
	}
	html += '</ul>';
	$("#goods_size_layer .detail_item_content").html(html);
};

/**
 * 尺码比较器（字符串）
 * @param {Object} sizeA
 * @param {Object} sizeB
 * @return sizeA 大于 sizeB，则返回大于0的数字
 */
goodsAdd.size.sizeStringComparator = function(sizeA, sizeB) {
	return sizeA.propValueName.localeCompare(sizeB.propValueName);
};

/**
 * 尺码比较器（数字）
 * @param {Object} sizeA
 * @param {Object} sizeB
 * @return sizeA 大于 sizeB，则返回大于0的数字
 */
goodsAdd.size.sizeNumberComparator = function(sizeA, sizeB) {
	var numA = parseFloat(sizeA.propValueName);
	var numB = parseFloat(sizeB.propValueName);
	return numA - numB;
};

/**
 * 尺码的点击事件
 * @param {Object} self 当前点击的尺码
 */
goodsAdd.size.size_OnClick = function(self) {
	$("#goods_size_color_layer").show();
	//当前选中的尺码值
	var sizeValue = $.trim($(self).val());
	//当前选中的尺码名称
	var sizeName = $.trim($(self).parent().find("label").text());
	//颜色名称
	var colorName = $("#goods_specName").val();
	
	//尺码点击事件 预处理
	if(!goodsAdd.size.pre_size_OnClick(self, sizeValue, sizeName, colorName)) return;
	
	if(self.checked) {
		//新增一行货品信息
		goodsAdd.prodInfo.addTableLine(sizeValue, sizeName, colorName);
	} else {
		//删除一行货品信息
		goodsAdd.prodInfo.removeTableLine(sizeValue, self, colorName);
	}
	
	//error_tip hide
	$('#sizeNo_error_tip').hide();
};

/**
 * 新增一行货品信息
 * @param {String} sizeValue 当前选中的尺码值
 * @param {String} sizeName 当前选中的尺码名称
 * @param {String} colorName 当前选中的颜色名称
 */
goodsAdd.prodInfo.addTableLine = function(sizeValue, sizeName, colorName) {
	var trCount = $("#goods_color_size_tbody>tr").length;
	//是否为第一行数据
	var isFirstLine = trCount == 0;
	if(!isFirstLine) {
		$("#goods_product_color_td").attr("rowspan", trCount + 1);
	}
	//货品资料Html
	var productInfoHtml = formatString(
		'<tr id="goods_product_tr_{#sizeValue}">' +
		'	{#colorTd}' +
		'	<td>{#sizeName}</td>' +
		'	<td class="goods_prod_stock_column" style="display: none;"><input type="text" sizeValue="{#sizeValue}" id="goods_stock_{#sizeValue}" name="stock" class="inputtxt goods_stock_txt" value="" style="width: 80px;" maxlength="6" /></td>' +
		'	<td><input type="text" sizeValue="{#sizeValue}" id="goods_thirdPartyCode_{#sizeValue}" name="thirdPartyCode" class="inputtxt goods_thirdPartyCode_txt" value="" style="width: 120px;" maxlength="32" /></td>' +
		'	<td><input type="text" sizeValue="{#sizeValue}" id="goods_weightStr_{#sizeValue}" name="weightStr" class="inputtxt goods_weightStr_txt" value="" style="width: 120px;" maxlength="15" /></td>' +
		'</tr>',
		{
			colorTd: isFirstLine ? formatString('<td id="goods_product_color_td">{#colorName}</td>', {colorName: colorName} ) : "",
			sizeName: sizeName,
			sizeValue: sizeValue
		});
	$("#goods_color_size_tbody").append(productInfoHtml);
	//如果不入优购仓
	if(!isInputYougouWarehouseFlag) {
		$(".goods_prod_stock_column").show();
	}
	
	//尺码编号隐藏域Html
	var sizeNoHiddenHtml = formatString(
		'<input type="hidden" id="goods_sizeNo_{#sizeValue}" name="sizeNo" value="{#sizeValue}" style="display: none;" />', 
		{
			sizeName: sizeName,
			sizeValue: sizeValue
		});
	$("#goods_selected_sizeNo_layer").append(sizeNoHiddenHtml);
	
	//尺码名称隐藏域Html
	var sizeNameHiddenHtml = formatString(
		'<input type="hidden" id="goods_sizeName_{#sizeValue}" name="sizeName" value="{#sizeName}" style="display: none;" />', 
		{
			sizeName: sizeName,
			sizeValue: sizeValue
		});
	$("#goods_selected_sizeName_layer").append(sizeNameHiddenHtml);
};

/**
 * 删除一行货品信息
 * @param {String} sizeValue 当前选中的尺码值
 * @param {Object} self 当前点击的尺码
 * @param {String} colorName 当前选中的颜色名称
 */
goodsAdd.prodInfo.removeTableLine = function(sizeValue, self, colorName) {
	var lineNum = $("#goods_product_tr_" + sizeValue).index();
	var rowCount = $("#goods_color_size_tbody>tr").length;
	
	var colorTdHtml = '';
	//如果是第一行
	if(lineNum == 0) {
		colorTdHtml = formatString(
		'<td rowspan="{#rowCount}" id="goods_product_color_td">{#colorName}</td>',
		{
			rowCount: rowCount - 1,
			colorName: colorName
		});
	} else {  //如果不是第一行
		$("#goods_product_color_td").attr("rowspan", rowCount - 1);
	}
	$("#goods_product_tr_" + sizeValue).remove();
	$("#goods_sizeNo_" + sizeValue).remove();
	$("#goods_sizeName_" + sizeValue).remove();
	
	$("#goods_color_size_tbody>tr:eq(0)").prepend(colorTdHtml);
};

/**
 * 上传框变化事件
 * @param {Object} inputFile 上传框对象
 */
goodsAdd.imageUpload.inputFile_OnChange = function(inputFile,number) {
	if(CheckFile(inputFile)){
		//是否为首次变化
		var isFirstChange = parseInt($.trim($(inputFile).attr("isFirstChange")));
		if(!isFirstChange) {
			$(inputFile).attr("isFirstChange", 1);
			$(inputFile).parent().find("span").html("上传新图片");
			//为预览的图片添加操作
			goodsAdd.imageUpload.addOptToPreviewImg(inputFile);		
		}
		
		var width = 100;
		var height = 100;
		var previewDivId = inputFile.id + "_preview";
		var imgLiId = inputFile.id + "_li";
		var layer = $("#" + imgLiId + " .goods_img_layer");
		var img = layer.find("img").eq(0);
		//图片预览
		goodsAdd.imageUpload.imgPreview(inputFile, img, width, height);
		var loading = '<span id="image_loading_' + number + '" style="position:absolute;left:50%;top:50%;margin-left:-12px;margin-top:-8px;"><img style="position:relative;z-index:2;"  src="' + goodsAdd.url.loadingImgUrl + '" width="16" height="16" /><span style="width:30px;height:30px;margin-top:-15px;margin-left:-15px;position:absolute;left:50%;top:50%;z-index:1;background:#ddd;border:1px solid #ccc;-moz-opacity:0.8;opacity: 0.8;" ></span></span>';
		$('#goods_img_layer_' + number).append(loading);
		//异步上传图片、禁用保存按钮
		image_hander++;
		isforbiddenButton('commodity_save', true, null);
		isforbiddenButton('commodity_audit', true, null);
		var srcImg=$("#img_file_id_"+number).siblings("div[class='goods_img_layer']").children("img").attr("src");
		//上传图片
		ajaxFileUpload({
	        id:inputFile.id,
	        url:'/img/upload.sc?no='+number,
	        callback:function(){
	                image_hander--;
	                var src = this.responseText;
	                if(src!=null&&""!=src&&"null"!=src){
		                var obj = eval ("(" + src + ")");
		                if(obj.success==true){
		                	$("#img_file_id_"+number).val(obj.message);
		                }else{
		                	$("#img_file_id_"+number).val("-1");
		                	$("#img_file_id_"+number).siblings("div[class='goods_img_layer']").children("img").attr("src",srcImg);
		                	goodsAdd.imageUpload.imgPreviewFail(inputFile, previewDivId, width, height);
		                	if (obj.message == '1') ygdg.dialog.alert('request请求参数no为空值,请检查!');
		                	else if (obj.message == '2') ygdg.dialog.alert('获取登录会话信息失败,请尝试重新登录操作!');
		                	else if (obj.message == '3') ygdg.dialog.alert('商品图片大小超过了 500 KB');
		                	else if (obj.message == '4') ygdg.dialog.alert('商品图片分辨率不符合  800-1000px * 800-1000px的规格');
		                	else if (obj.message == '5') ygdg.dialog.alert('图片校验异常');
		                	else if (obj.message == '6') ygdg.dialog.alert('上传图片失败, 请重新再试！');
		                	else if (obj.message == '7') ygdg.dialog.alert('上传图片失败,获取不到图像对象，请重试!');
		                	else ygdg.dialog.alert("图片上传失败，请重新上传！");
		                }
	                }else{
	                	$("#img_file_id_"+number).val("-1");
	                	goodsAdd.imageUpload.imgPreviewFail(inputFile, img, width, height);
	                	ygdg.dialog.alert("图片上传，服务器返回数据格式异常,请联系管理员！");
	                }
	                //图片处理完成、释放保存按钮 (is_execute判断再绑定事件时是否执行函数)
	                is_execute = false;
	                if (image_hander <= 0) {
	                	isforbiddenButton('commodity_save', false, function() {if (is_execute) goodsAdd.submit.submitForm(false);});
	                	isforbiddenButton('commodity_audit', false, function() {if (is_execute) goodsAdd.submit.submitForm(true);});
	                }
	                is_execute = true;
	                //移除loading
	                $('#image_loading_' + number).remove();
	        }
	    }); 
	}  
	//error_tip hide
	$('#commodityImage_error_tip').hide();
};


/**商品描述编辑器选项*/
goodsAdd.prodSpec.options = {
	resizeType: 0,
	allowPreviewEmoticons: false,
	allowImageUpload: false,
	allowFlashUpload: false,
	allowMediaUpload: false,
	allowFileUpload: false,
	newlineTag: 'br',
	htmlTags:{
        font : ['color', 'size', 'face', '.background-color'],
        span : ['style'],
        div : ['class', 'align', 'style'],
        table: ['class', 'border', 'cellspacing', 'cellpadding', 'width', 'height', 'align', 'style'],
        'td,th': ['class', 'align', 'valign', 'width', 'height', 'colspan', 'rowspan', 'bgcolor', 'style'],
        a : ['class', 'href', 'target', 'name', 'style'],
        embed : ['src', 'width', 'height', 'type', 'loop', 'autostart', 'quality',
        'style', 'align', 'allowscriptaccess', '/'],
        img : ['src', 'width', 'height', 'border', 'alt', 'title', 'align', 'style', '/','usemap'],
        hr : ['class', '/'],
        br : ['/'],
        'p,ol,ul,li,blockquote,h1,h2,h3,h4,h5,h6' : ['align', 'style'],
        'tbody,tr,strong,b,sub,sup,em,i,u,strike' : [],
		'map': ['id','name'],
		'area':['alt','coords','href','nohref','shape','target']
	},
	items: [
		'source', '|', 
		'undo', 'redo', '|', 
		'preview', 'cut', 'copy', 'paste', 'plainpaste', 'wordpaste', '|', 
		'justifyleft', 'justifycenter', 'justifyright', 'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript', 'superscript', 'clearhtml', 'selectall', '/',
		'formatblock', 'fontname', 'fontsize', '|', 
		'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|',
		'image', 'table', 'hr', 'anchor', 'link', 'unlink'
	]
}; 
/**商品描述编辑器*/
goodsAdd.prodSpec.editor;
//默认isflag:true显示预览
var isflag = true;

/*
 * 初始化商品描述编辑器
 */
KindEditor.ready(function(K) {
	goodsAdd.prodSpec.editor = K.create('#goods_prodDesc', goodsAdd.prodSpec.options);
	
	//设置工具栏source按钮点击事件
/*	goodsAdd.prodSpec.editor.clickToolbar('source', function() {
		var imgs = $("img", $(".ke-edit-iframe")[0].contentWindow.document.body);
		var imgObj = null;
		var src = null;
		var yougou_img_url_reg = new RegExp(yougouValidImageRegex, 'i');
		for (var i = 0, len = imgs.length; i < len; i++) {
			imgObj = imgs[i];
			src = imgObj.src;
			if (!yougou_img_url_reg.test(src)) {
				if (isflag) { //切换到源码
					//ygdg.dialog.alert(isflag.toString());
				} else { //切换预览(显示非法链接图标)
					imgObj.src = basePath + "/yougou/images/outer_link.png";
				}
			}
		}
		isflag = !isflag;
	});*/
});

/**
 * 校验商品描述中是否存在外链
 */
goodsAdd.prodSpec.checkOuterLink = function() {
	var imgs = $("img", $(".ke-edit-iframe")[0].contentWindow.document.body);
	var imgObj = null;
	var src = null;
	var yougou_img_url_reg = new RegExp(yougouValidImageRegex, 'i');
	var isCheck = true;
/*	for (var i = 0, len = imgs.length; i < len; i++) {
		imgObj = imgs[i];
		src = imgObj.src;
		if (!yougou_img_url_reg.test(src)) {
			if (isflag) { //如果为预览(显示非法链接图标)
				imgObj.src = basePath + "/yougou/images/outer_link.png";
				isCheck = false;
			}
		}
	}*/
	//校验area
	var areaHrefs=$("area", $(".ke-edit-iframe")[0].contentWindow.document.body);
	var href = null;
	var yougou_href_url_reg = new RegExp('http://.+\\.yougou.com/.+', 'i');
	var isCheck = true;
	for (var j = 0, len = areaHrefs.length; j < len; j++) {
		href = areaHrefs[j].href;
		if (!yougou_href_url_reg.test(href)) {
				isCheck = false;
		}
	}
	return isCheck;
};

/**
 * 选择商品描述图片按钮 点击事件
 */
goodsAdd.prodSpec.imgBtn_OnClick = function() {
	openwindow(goodsAdd.url.descImgSelectorUrl, 820, 560, '选择描述图片');
	
	//error_tip hide
	$('#prodDesc_error_tip').hide();
};

/**
 * 图片选择器 关闭事件
 * @param {String} imgUrl 图片地址
 */
goodsAdd.prodSpec.imgSelector_OnClose = function(imgUrls) {
	closewindow();
	if(imgUrls == null || imgUrls == '') return;
	var msg = '<br/>';
	var urls = imgUrls.split('&&&&&');
	$.each(urls, function(n, url){
		msg += formatString('<img src="{#imgUrl}" />',
		{
			imgUrl : url
		});
		msg += '<br/>';
	});
	goodsAdd.prodSpec.editor.html(goodsAdd.prodSpec.editor.html() + msg);
	goodsAdd.prodSpec.checkOuterLink();
};


/**
 * 图片选择器 关闭事件
 * @param {String} imgUrl 图片地址
 */
function onImgSelected(imgUrl) {
	goodsAdd.prodSpec.imgSelector_OnClose(imgUrl);
}

/**
 * 提交表单
 * @param {Boolean} isSaveSubmit 是否为保存并提交
 */
goodsAdd.submit.submitForm = function(isSaveSubmit) {
	//是否校验通过
	var isCheck = true; 
	
	//同步商品描述编辑器内容
	goodsAdd.prodSpec.editor.sync();
	
	//提交表单前处理
	if(!goodsAdd.submit.preSubmit()) return;
	
	//验证表单
	if(!goodsAdd.validate.validateForm()) { isCheck = false; };
	if(isCheck) {
		$.getJSON("/commodity/checkCommodityStatus.sc?commodityNo="+requestCommodityNo,function(json){
			if(!json['result']){
				ygdgDialog.confirm ("商品已经上架销售，是否需要下架商品并保存修改内容？",function(){
					$.ajax({
						type: 'post',
						url: '/commodity/downGoods.sc',
						dataType: 'html',
						data: 'rand=' + Math.random() + '&commodityNo=' + (requestCommodityNo || ''),
						success: function(data, textStatus, jqXHR) {
							if ($.trim(data) == 'SUCCESS') {
								ygdg.dialog({id: "submitDialog",title:'操作提示', 
									content: '<img src="' + goodsAdd.url.loadingImgUrl + '" width="16" height="16" /> 正在提交,请稍候...', 
									lock:true, closable:false});
									
								var $myForm = $("#add_or_update_commodity_form");
								var actionUrl = goodsAdd.url.submitUrl;
								//标记是否为保存并提交审核
								if(isSaveSubmit) {
									if(goodsAdd.url.submitUrl.indexOf("?") == -1) {
										actionUrl = actionUrl + "?isSaveSubmit=1";
									} else {
										actionUrl = actionUrl + "&isSaveSubmit=1";
									}
								}
								$myForm.attr("action", actionUrl);
								$('#msgdiv').hide();
								//$(":radio","#goods_years_layer").removeAttr("disabled");
								$myForm.ajaxSubmit({
									cache: false,
									dataType: "json",
									success: goodsAdd.submit.success,
									error: function(XmlHttpRequest, textStatus, errorThrown) {
										if('parsererror'!=textStatus){
											alert("网络异常，请刷新后重试!");
										}
										ygdg.dialog({id: "submitDialog"}).close();
									}
								});
							} else {
								// this.error(jqXHR, textStatus, data);
								ygdg.dialog.alert(data);
							}
						},
						error: function(jqXHR, textStatus, errorThrown) {
							alert('下架商品失败');
						}
					});
				});
			}else{
				ygdg.dialog({id: "submitDialog",title:'操作提示', 
					content: '<img src="' + goodsAdd.url.loadingImgUrl + '" width="16" height="16" /> 正在提交,请稍候...', 
					lock:true, closable:false});
					
				var $myForm = $("#add_or_update_commodity_form");
				var actionUrl = goodsAdd.url.submitUrl;
				//标记是否为保存并提交审核
				if(isSaveSubmit) {
					if(goodsAdd.url.submitUrl.indexOf("?") == -1) {
						actionUrl = actionUrl + "?isSaveSubmit=1";
					} else {
						actionUrl = actionUrl + "&isSaveSubmit=1";
					}
				}
				$myForm.attr("action", actionUrl);
				$('#msgdiv').hide();
				//$(":radio","#goods_years_layer").removeAttr("disabled");
				$myForm.ajaxSubmit({
					cache: false,
					dataType: "json",
					success: goodsAdd.submit.success,
					error: function(XmlHttpRequest, textStatus, errorThrown) {
						if('parsererror'!=textStatus){
							alert("网络异常，请刷新后重试!");
						}
						ygdg.dialog({id: "submitDialog"}).close();
					}
				});
			}
		});
	}
};

/**
 * 商品资料提交响应
 * @param {Object} data
 */
goodsAdd.submit.success = function(data) {
	ygdg.dialog({id: "submitDialog"}).close();
	var errMsg = "商品资料提交失败";
	if(data == null || typeof(data) != "object" ||
			data.isSuccess == null || typeof(data.isSuccess) == "undefined") {
		ygdg.dialog.alert(errMsg);
		return;
	}
	errMsg = data.errorMsg || errMsg;
	
	//商品资料提交响应(后置处理)
	goodsAdd.submit.successPost(data, errMsg);
};

/**
 * 预览
 */
goodsAdd.submit.preview = function() {
	//同步商品描述编辑器内容
	goodsAdd.prodSpec.editor.sync();
	
	//提交表单前处理
	if(!goodsAdd.submit.preSubmit()) return;
	
	//验证表单
	if(!goodsAdd.validate.validateForm()) return;
	
	var myForm = document.getElementById("add_or_update_commodity_form");
	var actionUrl = "";
	if(goodsAdd.url.submitUrl.indexOf("?") == -1) {
		actionUrl = goodsAdd.url.submitUrl + "?isPreview=1";
	} else {
		actionUrl = goodsAdd.url.submitUrl + "&isPreview=1";
	}
	myForm.action = actionUrl;
	myForm.target = "_blank";
	myForm.submit();
	myForm.target = "";
};

/**待调用的验证函数列表*/
goodsAdd.validate.validateFunList = [];

/** 错误列表 */
goodsAdd.validate.errorList = new Array();

/**
 * 验证表单
 * @param {Array} formData 表单域数组
 * @param {Object} jqForm jq form对象
 * @param {Object} options 选项
 */
goodsAdd.validate.validateForm = function(formData, jqForm, options) {
	var validateFunList = goodsAdd.validate.validateFunList;
	for (var i = 0, len = validateFunList.length; i < len; i++) {
		eval('goodsAdd.validate[validateFunList[i]]()');
	}
	
	var errorList = goodsAdd.validate.errorList;
	if (errorList.length == 0) return true;
	
	goodsAdd.validate.batchShowErrorList();
	
	return false;
};

/**
 * 一次性展示错误列表
 */
goodsAdd.validate.batchShowErrorList = function() {
	var errorList = goodsAdd.validate.errorList;
	var error = null;
	var regexp = new RegExp('(_stock|_thirdPartyCode|_weightStr)$');
	//processing error List
	var tablehtml="<table style='width:100%;'>";
	for ( var int = 0; int < errorList.length; int++) {
		error = errorList[int];
		tablehtml=tablehtml+'<tr><td style="padding:2px;"><img src="/yougou/images/error.gif" class="goods_error_image" />'+ error.errMsg+'</td></tr>';
		if (goodsAdd.validate.endsWith(error.errorFiled, regexp)) {
			var filed = error.errorFiled.split("_");
			//定位 goods_filed[1]_filed[0] error block [filed[1]_filed[0]_error_tip]			
			var obj = $('#goods_' + filed[1] + '_' + filed[0]);
			obj.addClass("error");
			obj.attr('title', error.errMsg);
			obj.blur(function() {//绑定焦点事件
				var value = $(this).val();
				if(!isEmpty(value)) {
					$(this).removeClass('error');
				}
			});
			//obj.focus();
		} else {
			var error_html = '<img src="/yougou/images/error.gif" class="goods_error_image" />' + error.errMsg;
			$('#' + error.errorFiled + '_error_tip').show().html(error_html);
			if (goodsAdd.validate.endsWith(error.errorFiled, new RegExp('(supplierCode|styleNo|commodityName|salePrice|publicPrice|specName|prodDesc)'))) {
				$('#goods_' + error.errorFiled).focus();
			} else if (error.errorFiled == 'brandNo') {
				$('#brandNo').focus();
			} else if (error.errorFiled == 'category') {
				$('#category1').focus();
			}
		}
	}
	tablehtml=tablehtml+"</table>";
	$('#showtd').show().html(tablehtml);
	$('#msgdiv').show();
	//clear Array
	errorList.length = 0;
};

/**
 * 验证品牌 
 */
goodsAdd.validate.validateBrandNo = function() {
	var notNullMsg = '请选择品牌';
	var $thisObj = $('#brandNo');
	var thisValue = $.trim($thisObj.val());
	goodsAdd.validate.isEmpty('brandNo', thisValue, notNullMsg);
};

/**
 * 验证一级分类 
 */
goodsAdd.validate.validateRootCattegory = function() {
	var notNullMsg = "请选择一级分类";
	var $thisObj = $("#category1");
	var thisValue = $.trim($thisObj.val());
	goodsAdd.validate.isEmpty('category', thisValue, notNullMsg);
};

/**
 * 验证二级分类 
 */
goodsAdd.validate.validateSecondCategory = function() {
	var notNullMsg = "请选择二级分类";
	var $thisObj = $("#category2");
	var thisValue = $.trim($thisObj.val());
	goodsAdd.validate.isEmpty('category', thisValue, notNullMsg);
};

/**
 * 验证三级分类 
 */
goodsAdd.validate.validateThreeCategory = function() {
	var notNullMsg = "请选择三级分类";
	var $thisObj = $("#category3");
	var thisValue = $.trim($thisObj.val());
	goodsAdd.validate.isEmpty('category', thisValue, notNullMsg);
};

/**
 * 验证商品名称
 */
goodsAdd.validate.validateCommodityName = function() {
	var notNullMsg = "请填写正确的商品名称";
	var $thisObj = $("#goods_commodityName");
	var thisValue = $.trim($thisObj.val());
	goodsAdd.validate.isEmpty('commodityName', thisValue, notNullMsg);
};

/**
 * 验证商品款号
 */
goodsAdd.validate.validateStyleNo = function() {
	var notNullMsg = "请填写商品款号";
	var containsChinese = "商品款号不能包含中文";
	var $thisObj = $("#goods_styleNo");
	var thisValue = $.trim($thisObj.val());
	if(goodsAdd.validate.isEmpty('styleNo', thisValue, notNullMsg))return;
	if(!goodsAdd.validate.containsChinese('styleNo', thisValue, containsChinese)) return;
};

/**
 * 验证商家款色编码
 */
goodsAdd.validate.validateSupplierCode = function() {
	var notNullMsg = "请输入商家款色编码";
	var containsChinese = "商家款色编码不能包含中文";
	var $thisObj = $("#goods_supplierCode");
	var thisValue = $.trim($thisObj.val());
	if(goodsAdd.validate.isEmpty('supplierCode', thisValue, notNullMsg))return;
	//if(goodsAdd.validate.containsChinese('supplierCode', thisValue, containsChinese)) return;
};

/**
 * 验证优购价格
 */
goodsAdd.validate.validateSalePrice = function() {
	var notNullMsg = "请输入优购价格";
	var notNumMsg = "优购价格请输入数字";
	var lessThanZeroMsg = "优购价格请输入大于0的数字";
	var $thisObj = $("#goods_salePrice");
	var thisValue = $.trim($thisObj.val());
	if(goodsAdd.validate.isEmpty('salePrice', thisValue, notNullMsg)) return;
	if(goodsAdd.validate.isNotNum('salePrice', thisValue, notNumMsg)) return;
	if(goodsAdd.validate.isLessThanZero('salePrice', thisValue, lessThanZeroMsg)) return;
};

/**
 * 验证市场价格
 */
goodsAdd.validate.validatePublicPrice = function() {
	var notNullMsg = "请输入市场价格";
	var notNumMsg = "市场价格请输入数字";
	var lessThanZeroMsg = "市场价格请输入大于0的数字";
	var $thisObj = $("#goods_publicPrice");
	var thisValue = $.trim($thisObj.val());
	if(goodsAdd.validate.isEmpty('publicPrice', thisValue, notNullMsg)) return;
	if(goodsAdd.validate.isNotNum('publicPrice', thisValue, notNumMsg)) return;
	if(goodsAdd.validate.isLessThanZero('publicPrice', thisValue, lessThanZeroMsg)) return;
};


/**
 * 验证扩展属性是否必选 add by zhuang.rb at 2012-12-11
 */
goodsAdd.validate.validateGoodsProp = function() {
	$(".goods_prop_select_layer").each(function(){
		var $Obj = $(this).find("input[type='hidden']");
		var valueType = $Obj.val();
		if (valueType == '0') {
			var $thisObj = $(this).find("select");
			if($thisObj.attr("prodMustInput") == "true"){
				var notNullMsg = '请选择' + $thisObj.attr("propitemname");
				var thisValue = $.trim($thisObj.val());
				goodsAdd.validate.isEmpty($thisObj.attr('propItemNo'), thisValue, notNullMsg);
			}
		} else if (valueType == '1') {
			if ($Obj.attr("prodMustInput") == "true") {
				var notNullMsg = '请选择' + $Obj.attr("propitemname");
				var propItemNo = $Obj.attr("propItemNo");
				var thisValue = $("#goods_properties_propValueNo_"+propItemNo);
				goodsAdd.validate.isEmpty($Obj.attr('propItemNo'), thisValue, notNullMsg);
			}
		}
	});
};

/**
 * 验证颜色  
*/
goodsAdd.validate.validateSpecNo = function(){
	var Msg = '请输入商品颜色';
	var errMsg = '商品颜色必须为中文汉字或/,格式参考\"蓝色\",\"蓝色/黄色\"';
	var thisValue = $("#goods_specName").val();
	if(goodsAdd.validate.isEmpty('specName', thisValue, Msg)) return;
	
	var regexp = new RegExp('^[\u4E00-\u9FA5]+(/[\u4E00-\u9FA5]+)*$');
	goodsAdd.validate.RegExp('specName', thisValue, regexp, errMsg);
};

/**
 * 验证尺码 
 */
goodsAdd.validate.validateSizeNo = function() {
	var errMsg = "请选择尺码";
	if($(".goods_sizeNo_checkboxes:checked").length == 0) {
		goodsAdd.validate.isEmpty('sizeNo', '', errMsg);
	}
};

/**
 * 验证库存数量
 * modify by huang.tao 2013-05-24
 * 去掉库存限制(库存不输入时默认为0)
 */
goodsAdd.validate.validteStock = function() {
	//如果不入优购仓
	if(!isInputYougouWarehouseFlag) {
		var notNumMsg = "第 {#num} 行库存数量请输入数字";
		var lessThanZeroMsg = "第 {#num} 行库存数量请输入的数字必须大于等于0";
		var notIntMsg = "第 {#num} 行库存数量请输入的数字必须整数";
		var $stocks = $(".goods_stock_txt");
		var stock = null;
		for (var i = 0, len = $stocks.length; i < len; i++) {
			stock = $stocks[i];
			var thisValue = $.trim(stock.value);
			var sizeValue = $(stock).attr('sizeValue');
			if(thisValue.length != 0) {
				if(goodsAdd.validate.isNotNum(sizeValue + '_stock', thisValue, formatString(notNumMsg, {num: (i + 1)}))) continue;
				if(goodsAdd.validate.isThanZero(sizeValue + '_stock', thisValue, formatString(lessThanZeroMsg, {num: (i + 1)}))) continue;
				if(goodsAdd.validate.isNotInt(sizeValue + '_stock', thisValue, formatString(notIntMsg, {num: (i + 1)}))) continue;				
			}
		}
	}
	return true;
};

/**
 * 验证货品条码
 */
goodsAdd.validate.validateThirdPartyCode = function() {
	var emptyMsg = "请输入 第 {#num} 行的货品条码";
	var containsChinese = "第 {#num} 行的货品条码不能包含中文";
	var $thirdPartyCodes = $(".goods_thirdPartyCode_txt");
	var thirdPartyCode = null;
	for (var i = 0, len = $thirdPartyCodes.length; i < len; i++) {
		thirdPartyCode = $thirdPartyCodes[i];
		var thisValue = $.trim(thirdPartyCode.value);
		var sizeValue = $(thirdPartyCode).attr('sizeValue');
		if(goodsAdd.validate.isEmpty(sizeValue + '_thirdPartyCode', thisValue, formatString(emptyMsg, {num: (i + 1)}))) continue;
		if(goodsAdd.validate.containsChinese(sizeValue + '_thirdPartyCode', thisValue, formatString(containsChinese, {num: (i + 1)}))) continue;
	}
	//校验货品条码是否重复
	goodsAdd.validate.validateThirdPartyCodeRepeat();
};

/**
 * 校验货品条码是否重复
 */
goodsAdd.validate.validateThirdPartyCodeRepeat = function() {
	var isRepeat = false;
	var codeArr = [];
	var $thirdPartyCodes = $(".goods_thirdPartyCode_txt");
	if($thirdPartyCodes.length == 0 || $thirdPartyCodes.length == 1) {
		return isRepeat;
	}
	var thirdPartyCode = null;
	var sizeValue = null;
	for (var i = 0, len = $thirdPartyCodes.length; i < len; i++) {
		thirdPartyCode = $thirdPartyCodes[i];
		var thisValue = $.trim(thirdPartyCode.value);
		codeArr.push(thisValue);
	}
	//获取重复的元素
	var repeatArr = getRepeatElements(codeArr);
	if(repeatArr.length > 0) {
		var msg = "重复的货品条码： ";
		for (var i = 0, len = repeatArr.length; i < len; i++) {
			for ( var int = 0; int < $thirdPartyCodes.length; int++) {
				thirdPartyCode = $thirdPartyCodes[int];
				var thisValue = $.trim(thirdPartyCode.value);
				sizeValue = $(thirdPartyCode).attr('sizeValue');
				if (thisValue == repeatArr[i]) {
					goodsAdd.validate.isEmpty(sizeValue + '_thirdPartyCode', '', msg + repeatArr[i]);					
				}
			}
		}
		
		isRepeat = true;
	}
	return isRepeat;
};

/**
 * 验证重量
 */
goodsAdd.validate.validateWeight = function() {
	var notNumMsg = "第 {#num} 行重量请输入数字";
	var lessThanZeroMsg = "第 {#num} 行重量请输入的数字必须大于0";
	var notIntMsg = "第 {#num} 行重量请输入的数字必须整数";
	var $weightTxts = $(".goods_weightStr_txt");
	var weight = null;
	for (var i = 0, len = $weightTxts.length; i < len; i++) {
		weight = $weightTxts[i];
		var thisValue = $.trim(weight.value);
		var sizeValue = $(weight).attr('sizeValue');
		if(thisValue.length != 0) {
			if(goodsAdd.validate.isNotNum(sizeValue + '_weightStr', thisValue, formatString(notNumMsg, {num: (i + 1)}))) continue;
			if(goodsAdd.validate.isLessThanZero(sizeValue + '_weightStr', thisValue, formatString(lessThanZeroMsg, {num: (i + 1)}))) continue;
			if(goodsAdd.validate.isNotInt(sizeValue + '_weightStr', thisValue, formatString(notIntMsg, {num: (i + 1)}))) continue;
		}
	}
	return true;
};


/**
 * 验证商品描述 
 */
goodsAdd.validate.validateProdDesc = function() {
	var notNullMsg = "请输入商品描述";
	var maxLen = 30000;
	var lenLargerMsg = "商品描述长度不能超过 " + maxLen;
	var $thisObj = $("#goods_prodDesc");
	var thisValue = $.trim($thisObj.val());
	//判断是否为空格和换行符
	var notBlankLineFeedStr = $.trim($thisObj.val().replace(/&nbsp;/g, '').replace(/<br \/>/ig, ''));
	if(goodsAdd.validate.isEmpty('prodDesc', notBlankLineFeedStr, notNullMsg)) return false;
	if(goodsAdd.validate.isLenLarger('prodDesc', thisValue, maxLen,lenLargerMsg )) return false;
	
	var outerLinkError = "请勿使用带有水印、外链的图片或者外链URL";

	if (!isflag) { //编辑框在代码域时同步数据
		goodsAdd.prodSpec.editor.clickToolbar('source');		
	} 
	//增加外链判断
	if (!goodsAdd.prodSpec.checkOuterLink()) {
		goodsAdd.validate.isEmpty('prodDesc', null, outerLinkError);
		return false;
	}
	
	$("#prodDesc_error_tip").hide();
	
	return true;
};

//=====================================本次修改==================================

/**
 * 获取数组中重复的元素
 * @param {Array} 基本类型的数组
 * @return 返回重复的元素
 */
function getRepeatElements(codeArr) {
	var repeatArr = [];
	if(codeArr == null || typeof(codeArr) == "undefined" ||
			codeArr.length == 0 || codeArr.length == 1) {
		return repeatArr;
	}
	codeArr.sort();
	
	if(codeArr[0] == codeArr[1]) {
		if (!isEmpty(codeArr[0])) 
			repeatArr.push(codeArr[0]);
	}
	for (var i = 1, len = codeArr.length; i < len; i++) {
		if (isEmpty(codeArr[i])) continue;
		if(codeArr[i] == codeArr[i - 1]) {
			var isContain = false;
			for(var j = 0, len1 = repeatArr.length; j < len1; j++) {
				if(repeatArr[j] == codeArr[i]) {
					isContain = true;
					break;
				}
			}
			if(!isContain) {
				repeatArr.push(codeArr[i]);
			}
		}
	}
	return repeatArr;
}




function initCatForBrand(brandId, selId, categories) {
	if (brandId == null || brandId == '' || brandId.length == 0) {
		$("#" + selId).get(0).options.length = 1;
		$("#" + selId).change().reJqSelect();
		return false;
	}
	
	$.ajax( {
		type : "POST",
		url : goodsAdd.url.queryBrandCatUrl,
		async : false,
		data : {
			"brandId" : brandId
		},
		dataType : "json",
		success : function(data) {
			if (data == null) {
				$("#" + selId).get(0).options.length = 1;
				$("#" + selId).change().reJqSelect();
				return;
			}
			
			for ( var i = 0; i < data.length; i++) {
				categories[categories.length] = { label: data[i].catName, 
						value: data[i].structName + ";" + data[i].id + ";" + data[i].catNo + ";" + data[i].catName, 
						level: data[i].catLeave, 
						self: data[i].structName, 
						owner: data[i].parentId };
			}
			
			reinitializeOption('0', 1, '#' + selId, categories);
		}
	});
}

function reinitializeOption(id, level, selector, categories) {
	var optionText = $(categories).filter(function(){
		return (this.level == level) && (this.owner == id); 
	}).map(function(){
		return '<option id="' + this.self + '" value="' + this.value + '">' + this.label + '</option>';
	}).get().join('');
	
	$(selector).get(0).options.length = 1;
	$(selector).append(optionText).change().reJqSelect();
}



/*
 * 页面初始化
 */
$(function() {
	var categories = [];
	
	//第一、二级分类下拉框的onchage事件
	$("#category1,#category2").change(function() {
		//清除商品属性、颜色、尺码
		goodsAdd.cat.clearProperties();
		//清空货品信息列表
		$("#goods_color_size_tbody").html("");
		//清空商品属性信息
		goodsAdd.properties.clear();
	});
	
	//第一级分类下拉框 onchange事件
	$("#category1").change(function() {
		var rootCatValue = $.trim(this.value);
		var rootCatName = "";
		if(rootCatValue.length != 0) {
			rootCatName = $.trim($(this).find("option:selected").text());
		}
		$("#goods_rootCatName").val(rootCatName);
	});
	
	//第三级分类下拉框的onchange事件
	$("#category3").change(function() {
		//清除商品属性、颜色、尺码
		goodsAdd.cat.clearProperties();
		goodsAdd.cat.threeCat_OnChange(this.value);
		//清空货品信息列表
		$("#goods_color_size_tbody").html("");
		//清空商品属性信息
		goodsAdd.properties.clear();
		//显示商品属性、颜色、尺码
		$(".goods_cat_change_show").show();
		
		//error_tip hide
		$('#category_error_tip').hide();
	});
	
	//品牌下拉框onchange事件
	$("#brandNo").change(function() {
		var brandName = $(this).find("option:selected").text();
		var brandId = $.trim($(this).find("option:selected").attr("brandId"));
		if($.trim(brandName) != "请选择") {
			$("#goods_brandName").val($.trim(brandName));
			$("#goods_brandId").val(brandId);			
		} else {
			$("#goods_brandName").val("");
			$("#goods_brandId").val("");
		}
		
		// 初始化分类信息（新）
		categories = [];
		initCatForBrand(brandId, 'category1', categories);
		
		goodsAdd.brand.brand_OnChange(this.value);
	}).change();

	
	$("#category1").change(function() {
		reinitializeOption($(this).find(':selected').attr('id'), 2, '#category2', categories);
	});
	
	$("#category2").change(function() {
		reinitializeOption($(this).find(':selected').attr('id'), 3, '#category3', categories);
	});
	
	$('#goods_commodityName').blur(function() {
		var value = $(this).val();
		if(!isEmpty(value)) {
			$('#commodityName_error_tip').hide();
		} 
	});
	$('#goods_styleNo').blur(function() {
		var value = $(this).val();
		if(!isEmpty(value)) {
			$('#styleNo_error_tip').hide();
		} 
	});
	$('#goods_supplierCode').blur(function() {
		var value = $(this).val();
		if(!isEmpty(value)) {
			$('#supplierCode_error_tip').hide();
		} 
	});
	$('#goods_salePrice').blur(function() {
		var value = $(this).val();
		if(!isEmpty(value)) {
			$('#salePrice_error_tip').hide();
		} 
	});
	$('#goods_publicPrice').blur(function() {
		var value = $(this).val();
		if(!isEmpty(value)) {
			$('#publicPrice_error_tip').hide();
		}
	});
	$('#goods_specName').blur(function() {
		var value = $(this).val();
		if(!isEmpty(value)) {
			$('#specName_error_tip').hide();
		}
	});
	
});
