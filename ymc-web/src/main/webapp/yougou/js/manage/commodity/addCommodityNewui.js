
/**
 * 为预览的图片添加操作
 * @param {Object} inputFile 上传框对象
 */
goodsAdd.imageUpload.addOptToPreviewImg = function(inputFile) {
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
	return true;
};

/**
 * 提交表单前处理
 * @return 返回false可中断提交表单主代码的执行
 */
goodsAdd.submit.preSubmit = function() {
	return true;
};

/**
 * 商品资料提交响应(后置处理)
 * @param {Object} data 响应数据
 * @param {String} errMsg 错误消息
 * @return 成功则返回true
 */
var aftersavedg = null;
goodsAdd.submit.successPost = function(data, errMsg) {
	if(!Boolean(data.isSuccess )) {
		if(!(isSetSizePrice)){
			var errorList = goodsAdd.validate.errorList = data.errorList;
			if (errorList.length > 0) {
				goodsAdd.validate.batchShowError();
			}else{
				ygdg.dialog.alert(errMsg);
			}
		}else{
			//按尺码设置价格
			aftersavedg=ygdg.dialog({
				title:'提示',
				content:"<h2 class='f16' style='font-weight:bold;'>商品保存成功<span style='color:green;'>"+data.successSize+"</span>条，失败<span style='color:red;'>"+data.errorSize+"</span>条!</h2>",
				icon: 'warning',
				button: [
				          {
				              name: '确定',
				              callback: function () {
				            	  aftersavedg.close();
				            	  var errorList = goodsAdd.validate.errorList = data.errorList;
									if (errorList.length > 0) {
										//goodsAdd.validate.batchShowErrorList();					
										goodsAdd.validate.batchShowError();
									}
				              },
				              focus: true
				          }
				      ]
			});
		}
		return;
	}
	// 是否发布相似商品
	if ($("#alikegoods").attr('checked') != 'checked') {
		aftersavedg=ygdg.dialog({
			title:'保存成功',
			content:"<h2 class='f16' style='font-weight:bold;'>商品保存成功!</h2><br /><span style='color:#AAAAAA'>你还可以继续以下操作：</span><br /><br /><a href=\"javascript:clearVal();\" >发布同款商品</a>&nbsp;&nbsp;<span style='color:#AAAAAA'>(点击后当前资料保留)</span><br /><br /><a href=\"javascript:toAddNewGoods();\" >发布新商品</a>&nbsp;&nbsp;&nbsp;&nbsp;<span style='color:#AAAAAA'>(点击后当前资料清空)</span><br />",
			icon: 'succeed',
			button: [
			          {
			              name: '确定',
			              callback: function () {
			            	  goodsAdd.submit.success.redirect(data);
			              },
			              focus: true
			          }
			      ]
		});
	} else {
		ygdg.dialog.tips('商品资料提交成功', 3);
		// 清除相似商品的必填项
		clearVal();
	}
};
function toAddNewGoods(){
	// 跳转到当前页面的新发布状态
	location.assign(basePath + "/commodity/addCommodityui.sc");
}

function clearVal() {
	if (aftersavedg) {
		aftersavedg.close();
	}
	// 清除相似商品的必填项
	$("#right .size_tab").hide();
	$('#commodity_code').val('');
	$(".size-list input[name='goods_sizeNo']:checked").each(function(i) {
		$(this).attr('checked', false);
		$("#goods_sizeName_" + $(this).val()).remove();
		$("#goods_sizeNo_" + $(this).val()).remove();
	});
	$('#goods_color_size_tbody').empty();
}
/**
 * 商品资料提交成功后，跳转到指定页面
 */
goodsAdd.submit.success.redirect = function(data) {
	if(data.isAddCommoditySaveSubmit) {  //如果为保存并提交审核
		setTimeout(function() {location.assign(goodsAdd.url.goForSaleCommodity + "?auditStatus=" + 12);}, 1000);
		return true;
	}
	setTimeout(function() {location.assign(goodsAdd.url.goForSaleCommodity + "?auditStatus=" + 11);}, 1000);
	return true;
};

$(function() {
	//待调用的验证函数列表
	goodsAdd.validate.validateFunList = ["validateBrandNo", "validateCattegory", 
			"validateCommodityName", "validateStyleNo",
			"validateSupplierCode", "validateSalePrice", "validatePublicPrice", "validateGoodsProp",
			"validateSpecNo", "validateSizeNo", "validteStock", "validateThirdPartyCode", "validateWeight",
			"validateCommodityImg", "validateProdDesc"];
	goodsAdd.validate.errorList = new Array();
	$(document).ajaxComplete(function() {
		setTimeout(function() {
			if(templateId){
				useTemplate(templateId);
				//不设置可能进入死循环
				templateId = null;
			}
			ymc_common.loading();
			li_height();
		},200);
	});
});
