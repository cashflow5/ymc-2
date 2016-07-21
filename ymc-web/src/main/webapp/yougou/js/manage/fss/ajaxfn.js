//ajax操作
define(function(require, exports, module) {

	var AjaxFn = {};
	var utils = require('common/utils');
	var global = require('common/global');
	var ajaxReg = require('ajax_regGlobal');
	ajaxReg.ajax_regGlobalEvent();
	AjaxFn = {
		// ==================商品推荐======================
		ajax_getGoodsNec : function(ids, callbackFun) {
			var result = null;
			$.post(global.base.baseRoot + '/goodsrec/goods/'
					+ global.base.storeId + '.sc', {
				ids : ids
			}, function(obj) {
				if (obj && obj.scode && obj.scode == 0) {
					result = "success";
					// 回调插入方法
					callbackFun(obj.data);
					// art.dialog.opener.global.Module.Index.insertLayoutHtml(lytCode,
					// obj.data);
				} else {
					utils.tips("操作失败，请重试！", 2);
					result = "failure";
				}
			}, "json");
			return result;
		},
		// ==================end==========================
		// ==================添加店招======================
		ajax_addShopSign : function(storeId, callbackFun) {
			var result = null;
			var url = global.base.baseRoot + "/shopsign/insert.sc";
			$.post(url, {
				storeId : storeId
			}, function(obj) {
				if (obj.scode && obj.scode == 0) {
					result = "success";
					// 回调插入方法
					callbackFun(obj.data);
				} else {
					utils.tips("操作失败，请重试！", 2);
					result = "failure";
				}
			}, "json");
			return result;
		},
		// ==================end==========================

		// 获取STYLE
		ajax_getTemplateStyle : function(code, callbackFun) {
			var result = null;
			$.post(global.base.baseRoot + '/store/getStyles.sc', {
				code : code
			}, function(obj) {
				if (obj && obj.scode && obj.scode == 0) {
					result = "success";
					// 回调插入方法
					callbackFun(obj.data);
					// art.dialog.opener.global.Module.Index.insertLayoutHtml(lytCode,
					// obj.data);
				} else {
					utils.tips("操作失败，请重试！", 2);
					result = "failure";
				}
			}, "json");
			return result;
		},

		// ==================check module max ======================
		ajax_checkModuleMax : function(storeId, callbackFun) {
			var result = null;
			var url = global.base.baseRoot + "/module/checkMaxModule.sc";
			$.post(url, {
				storeId : storeId
			}, function(obj) {
				if (obj.scode && obj.scode == 0) {
					result = "success";
					// 回调插入方法
					callbackFun(obj.data);
				} else {
					alert(obj.smsg);
					result = "failure";
				}
			}, "json");
			return result;
		},

		// ==================check layout max ======================
		ajax_checkLayoutMax : function(storeId, callbackFun) {
			var result = null;
			var url = global.base.baseRoot + "/layout/checkMaxLayout.sc";
			$.post(url, {
				storeId : storeId
			}, function(obj) {
				if (obj.scode && obj.scode == 0) {
					result = "success";
					// 回调插入方法
					callbackFun(obj.data);
				} else {
					alert(obj.smsg);
					result = "failure";
				}
			}, "json");
			return result;
		},

		// ==================添加导航条======================
		ajax_addCate : function(storeId, callbackFun) {
			var result = null;
			var url = global.base.baseRoot + "/categorynavigation/insert.sc";
			$.post(url, {
				storeId : storeId
			}, function(obj) {
				if (obj.scode && obj.scode == 0) {
					result = "success";
					// 回调插入方法
					callbackFun(obj.data);
				} else {
					utils.tips("操作失败，请重试！", 2);
					result = "failure";
				}
			}, "json");
			return result;
		},
		// ==================end==========================
		ajax_gallery_delPic : function(ids, callbackFun) {
			var result = null;
			$.post(global.base.baseRoot + "/gallery/del.sc", {
				ids : ids
			}, function(obj) {
				if (obj.scode && obj.scode == 0) {
					result = "success";
					// 回调插入方法
					callbackFun(obj.data);
				} else {
					utils.tips(obj.smsg, 3);
					result = "failure";
				}
			}, "json");
			return result;
		},

		/***********************************************************************
		 * 插入一个导航 参数 1 moduleId 导航模块的ID 2 masterId 父导航单元ID，如果增加的是父导航则为null
		 * 
		 **********************************************************************/
		ajax_insertNav : function(masterId, callbackFun) {
			// alert("/fss/navigation/insert.sc");
			var storeId = $('#storeId').val();
			var result = null;
			$.post(global.base.baseRoot + "/navigation/insert.sc", {
				storeId : storeId,
				masterId : masterId
			}, function(obj) {
				if (obj.scode && obj.scode == 0) {
					result = "success";
					// 回调插入方法
					callbackFun(obj.data);
				} else {
					utils.tips("操作失败，请重试！", 2);
					result = "failure";
				}
			}, "json");
			return result;
		},
		/***********************************************************************
		 * 更新图片 参数 1 navigationId ID 2 imageId
		 * 
		 **********************************************************************/
		ajax_update_nav_image : function(navigationId, imageId, callbackFun) {
			var result = null;
			$.post(global.base.baseRoot + "/navigation/update_image.sc", {
				storeId : global.base.storeId,
				navigationId : navigationId,
				imageId : imageId
			}, function(obj) {
				if (obj.scode && obj.scode == 0) {
					result = "success";
					// 回调插入方法
					callbackFun(obj.data);
				} else {
					utils.tips("操作失败，请重试！", 2);
					result = "failure";
				}
			}, "json");
			return result;
		},
		/***********************************************************************
		 * 删除图片 参数 1 navigationId ID
		 * 
		 * 
		 **********************************************************************/
		ajax_delete_nav_image : function(navigationId, callbackFun) {
			var result = null;
			$.post(global.base.baseRoot + "/navigation/delete_image.sc", {
				storeId : global.base.storeId,
				navigationId : navigationId
			}, function(obj) {
				if (obj.scode && obj.scode == 0) {
					result = "success";
					// 回调插入方法
					callbackFun(obj.data);
				} else {
					utils.tips("操作失败，请重试！", 2);
					result = "failure";
				}
			}, "json");
			return result;
		},
		/**
		 * 更新名称 参数 1 navigationId ID 2 name
		 * 
		 */
		ajax_update_nav_name : function(navigationId, name, callbackFun) {
			var result = null;
			$.post(global.base.baseRoot + "/navigation/update_name.sc", {
				storeId : global.base.storeId,
				navigationId : navigationId,
				name : name
			}, function(obj) {
				if (obj.scode && obj.scode == 0) {
					result = "success";
					// 回调插入方法
					callbackFun(obj.data);
				} else {
					utils.tips("操作失败，请重试！", 2);
					result = "failure";
				}
			}, "json");
			return result;
		},
		/**
		 * 更新url 参数 1 navigationId ID 2 name
		 * 
		 */
		ajax_update_nav_url : function(navigationId, url, thisObj, callbackFun) {
			var result = null;
			$.post(global.base.baseRoot + "/navigation/update_url.sc", {
				storeId : global.base.storeId,
				navigationId : navigationId,
				url : url
			}, function(obj) {
				if (obj.scode && obj.scode == 0) {
					result = "success";
					// 回调插入方法
					callbackFun(obj.data);
				} else {
					if(obj.smsg){
                		utils.alert(obj.smsg);
                		thisObj.val("");
                	}else{
                		utils.tips("操作失败，请重试！", 2);
                	}
                    result = "failure";
				}
			}, "json");
			return result;
		},

		/***********************************************************************
		 * 更新排列 参数 1 navigationId ID 2 oder
		 * 
		 **********************************************************************/
		ajax_update_nav_oder : function(navigationId, oder, callbackFun) {
			var result = null;
			$.post(global.base.baseRoot + "/navigation/update_oder.sc", {
				storeId : global.base.storeId,
				navigationId : navigationId,
				oder : oder
			}, function(obj) {
				if (obj.scode && obj.scode == 0) {
					result = "success";
					// 回调插入方法
					callbackFun(obj.data);
				} else {
					utils.tips("操作失败，请重试！", 2);
					result = "failure";
				}
			}, "json");
			return result;
		},

		/***********************************************************************
		 * 批量删除 参数 1 navigationId ID 2 oder
		 * 
		 **********************************************************************/
		ajax_delete_navs : function(ids, callbackFun) {
			var result = null;
			$.post(global.base.baseRoot + "/navigation/delete_nav.sc", {
				storeId : global.base.storeId,
				ids : ids
			}, function(obj) {
				if (obj.scode && obj.scode == 0) {
					result = "success";
					// 回调插入方法
					callbackFun(obj.data);
				} else {
					utils.tips("操作失败，请重试！", 2);
					result = "failure";
				}
			}, "json");
			return result;
		},

		/***********************************************************************
		 * ====================================== end
		 * ===========================
		 */
		/*
		 * *********************
		 * 店铺规则***********************************************************
		 */
		/***********************************************************************
		 * 更新信息状态 参数 1 id MsgID
		 * 
		 **********************************************************************/
		ajax_updateStoreMsg : function(id, callbackFun) {
			var result = null;
			$.post(global.base.baseRoot + "/rule/update_msg.sc", {
				id : id
			}, function(obj) {
				if (obj.scode && obj.scode == 0) {
					result = "success";
					// 回调插入方法
					callbackFun(obj.data);
				} else {
					utils.tips("操作失败，请重试！", 2);
					result = "failure";
				}
			}, "json");
			return result;
		},

		/** 删除店铺规则* */
		ajax_RuleDelete : function(ruleId, callbackFun) {
			var result = null;
			$.post(global.base.baseRoot + "/rule/deleteRule" + "/"
					+ global.base.storeId + ".sc", {
				ruleId : ruleId
			}, function(obj) {
				if (obj.scode && obj.scode == 0) {
					result = "success";
					// 回调插入方法
					callbackFun(obj.data);
				} else {
					utils.tips("操作失败，请重试！", 2);
					result = "failure";
				}
			}, "json");
			return result;
		},

		/** 商品翻页* */
		ajax_Goods_Page : function(ruleId, type, currentPage, callbackFun) {
			var result = null;
			$.post(global.base.baseRoot + "/rule/getRuleCommdityList.sc", {
				ruleId : ruleId,
				type : type,
				currentPage : currentPage
			}, function(obj) {
				if (obj.scode && obj.scode == 0) {
					result = "success";
					// 回调插入方法
					callbackFun(obj.data);
				} else {
					utils.tips("操作失败，请重试！", 2);
					result = "failure";
				}
			}, "json");
			return result;
		},

		/** 全部商品更换* */
		ajax_Batch_all : function(ids, ruleId, storeId, callbackFun) {
			var result = null;
			$.ajax({
				type : "POST",
				url : global.base.baseRoot + "/rule/batch_change.sc",
				data : {
					ruleId : ruleId,
					ids : ids,
					storeId : storeId
				},
				async : false,
				dataType : "json",
				beforeSend : function(xmlHttpRequest) {
					utils.tips('提交中，请稍等...', 10);
				},
				success : function(obj) {
					// obj = eval("("+obj+")");增加dataType:json,则不需要此句
					if (obj.scode && obj.scode == 0) {
						result = "success";
						// 回调插入方法
						callbackFun(obj.data);
					} else {
						$("#all_change").removeAttr("disabled").val(
								"绑定所有商品到本店铺");
						utils.tips("操作失败，请重试！", 2);
						result = "failure";
					}
				},
				error : ajaxfn.ajax_errorEvent
			});

			return result;
		},

		ajax_checkExist : function(brandNo, structName, keyword, callbackFun) {
			var result = null;
			$.ajax({
				type : "POST",
				url : global.base.baseRoot + "/rule/check.sc",
				data : {
					brandNo : brandNo,
					structName : structName,
					keyword : keyword
				},
				async : false,
				dataType : "json",
				beforeSend : function(xmlHttpRequest) {
					utils.tips('提交中，请稍等...', 10);
				},
				success : function(obj) {
					// obj =
					// eval("("+obj+")");增加dataType:json后obj已经是javascript对象,则不需要此句再转换成javascript对象.
					if (obj.scode && obj.scode == 0) {
						result = "success";
						// 回调插入方法
						callbackFun(obj);
					} else {
						// $("#all_change").removeAttr("disabled").val("绑定所有商品到本店铺");
						utils.tips("操作失败，请重试！", 2);
						result = "failure";
					}
				},
				error : ajaxfn.ajax_errorEvent
			});

			return result;
		},
		/* ************店铺规则 end ************** */
		ajax_insertLyt : function(lytCode, currentIndex) { // 插入布局单元
			var result = null;
			$.post(global.base.baseRoot + "/layout/insert.sc", {
				pageId : global.base.currentPageId,
				storeId : global.base.storeId,
				sortOrder : currentIndex,
				layoutTypeCode : lytCode
			}, function(obj) {
				if (obj.scode && obj.scode == 0) {
					result = "success";
					// 回调插入方法
					parDesign.insertLayoutHtml(lytCode, obj.data);
				} else {
					utils.tips(obj.smsg, 2);
					result = "failure";
				}
			}, "json");
			return result;
		},
		ajax_delLyt : function(lytObj, callbackFun) { // 删除布局单元
			var parent = lytObj.parent();
			var inx = $(".fss_lyt").index(parent);
			var lytId = parent.attr("data-lytid");
			$.post(global.base.baseRoot + "/layout/delete.sc", {
				pageId : global.base.currentPageId,
				storeId : global.base.storeId,
				id : lytId,
				sortOrder : inx
			}, function(obj) {
				if (obj.scode && obj.scode == 0) {
					// 回调插入方法
					callbackFun(lytObj);
				} else {
					utils.tips("操作失败，请重试！", 2);
				}
			}, "json");

		},
		ajax_delMdl : function(mdlObj, thisObj, callbackFun) { // 删除模块
			$.post(global.base.baseRoot + "/module/delete.sc", {
				pageId : global.base.currentPageId,
				storeId : global.base.storeId,
				id : mdlObj.attr('id')
			}, function(obj) {
				if (obj.scode && obj.scode == 0) {
					// 回调删除方法
					callbackFun(thisObj);
				} else {
					utils.tips("操作失败，请重试！", 2);
				}
			}, "json");
		},
		ajax_MdlUpDwn : function(toMdlId, oldMdlId, col, prevMdl, nextMdl,
				oldMdl, callbackFun) { // 上下移动模块
			$.post(global.base.baseRoot + "/module/mdlUpAndDwn.sc", {
				pageId : global.base.currentPageId,
				storeId : global.base.storeId,
				targetMdlId : toMdlId,
				originalMdlId : oldMdlId
			}, function(obj) {
				if (obj.scode && obj.scode == 0) {
					// 回调方法
					callbackFun(col, prevMdl, nextMdl, oldMdl);
				} else {
					utils.tips("操作失败，请重试！", 2);
				}
			}, "json");
		},
		ajax_moveMdl : function(mdlId, newSortOrder, layoutId, colIndex,
				colWidth, ui, currDrop, callbackFun) { // 拖拽模块
			$.post(global.base.baseRoot + "/module/moveMdl.sc", {
				pageId : global.base.currentPageId,
				storeId : global.base.storeId,
				id : mdlId,
				sortOrder : newSortOrder,
				layoutId : layoutId,
				colIndex : colIndex,
				colWidth : colWidth
			}, function(obj) {
				if (obj.scode && obj.scode == 0) {
					utils.tips("操作成功！", 2);
					callbackFun(ui, currDrop);
					if (obj.data != null && obj.data.length > 0) {
						$("#" + mdlId).find('.skin_box').html(obj.data);
						parDesign.initLayout();
					}
				} else {
					utils.tips("操作失败，请重试！", 2);
				}
			}, "json");
		},
		ajax_delSaleRank : function(saleRandId, item, callbackFun) { // 删除一个销售排行选项
			$.post(global.base.baseRoot + "/saleRank/del.sc", {
				pageId : global.base.currentPageId,
				storeId : global.base.storeId,
				saleRandId : saleRandId
			}, function(obj) {
				if (obj.scode && obj.scode == 0) {
					// 回调方法
					callbackFun(item);
					utils.tips("操作成功！", 2);
				} else {
					utils.tips("操作失败，请重试！", 2);
				}
			}, "json");
		},

		ajax_setup : ajaxReg.ajax_setup,
		ajax_sendEvent : ajaxReg.ajax_sendEvent,
		ajax_successEvent : ajaxReg.ajax_successEvent,
		ajax_errorEvent : ajaxReg.ajax_errorEvent,
		ajax_regGlobalEvent : ajaxReg.ajax_regGlobalEvent

	};

	module.exports = AjaxFn;

});