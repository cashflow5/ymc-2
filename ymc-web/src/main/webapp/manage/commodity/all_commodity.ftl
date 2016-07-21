
<#--审核状态，新建-->
<#assign AUDIT_STATUS_DRAFT = (statics["com.yougou.kaidian.commodity.constant.CommodityConstant"].AUDIT_STATUS_DRAFT)!''>
<#--审核状态，已审核-->
<#assign AUDIT_STATUS_PASS = (statics["com.yougou.kaidian.commodity.constant.CommodityConstant"].AUDIT_STATUS_PASS)!''>
<#--审核状态，已拒绝-->
<#assign AUDIT_STATUS_REFUSE = (statics["com.yougou.kaidian.commodity.constant.CommodityConstant"].AUDIT_STATUS_REFUSE)!''>
<#--审核状态，待审核-->
<#assign AUDIT_STATUS_PENDING = (statics["com.yougou.kaidian.commodity.constant.CommodityConstant"].AUDIT_STATUS_PENDING)!''>
<#--审核状态map-->
<#assign AUDIT_STATUS_NAMES = (statics["com.yougou.kaidian.commodity.constant.CommodityConstant"].AUDIT_STATUS_NAMES)!''>

<#--查询商品的页面tabId，未提交审核-->
<#assign QUERY_COMMODITY_PAGE_TAB_ID_DRAFT = (statics["com.yougou.kaidian.commodity.constant.CommodityConstant"].QUERY_COMMODITY_PAGE_TAB_ID_DRAFT)!''>
<#--查询商品的页面tabId，待审核-->
<#assign QUERY_COMMODITY_PAGE_TAB_ID_PENDING = (statics["com.yougou.kaidian.commodity.constant.CommodityConstant"].QUERY_COMMODITY_PAGE_TAB_ID_PENDING)!''>
<#--查询商品的页面tabId，审核通过-->
<#assign QUERY_COMMODITY_PAGE_TAB_ID_PASS = (statics["com.yougou.kaidian.commodity.constant.CommodityConstant"].QUERY_COMMODITY_PAGE_TAB_ID_PASS)!''>
<#--查询商品的页面tabId，审核拒绝-->
<#assign QUERY_COMMODITY_PAGE_TAB_ID_REFUSE = (statics["com.yougou.kaidian.commodity.constant.CommodityConstant"].QUERY_COMMODITY_PAGE_TAB_ID_REFUSE)!''>
<#--查询商品的页面tabId，全部商品-->
<#assign QUERY_COMMODITY_PAGE_TAB_ID_ALL = (statics["com.yougou.kaidian.commodity.constant.CommodityConstant"].QUERY_COMMODITY_PAGE_TAB_ID_ALL)!''>

<#--查询全部商品操作，是否显示预览-->
<#assign QUERY_COMMODITY_OPT_IS_SHOW_DETAIL = (statics["com.yougou.kaidian.commodity.constant.CommodityConstant"].QUERY_COMMODITY_OPT_IS_SHOW_DETAIL)!''>
<#--查询全部商品操作，是否显示修改-->
<#assign QUERY_COMMODITY_OPT_IS_SHOW_UPDATE = (statics["com.yougou.kaidian.commodity.constant.CommodityConstant"].QUERY_COMMODITY_OPT_IS_SHOW_UPDATE)!''>
<#--查询全部商品操作，是否显示提交审核-->
<#assign QUERY_COMMODITY_OPT_IS_SHOW_SUBMIT_AUDIT = (statics["com.yougou.kaidian.commodity.constant.CommodityConstant"].QUERY_COMMODITY_OPT_IS_SHOW_SUBMIT_AUDIT)!''>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-所有商品</title>
<style type="text/css">
#query_goods_tab_panel .tab li {cursor: pointer;}
#queryForm .query_goods_condition_p .query_goods_condition_item {padding-bottom: 2px; padding-top: 3px;}
</style>
<script type="text/javascript">
var basePath = "${BasePath}";
</script>
</head>
 
<body>
	<div class="main_container">
		<div class="normal_box">
			<p class="title site">当前位置：商家中心 &gt; 商品 &gt; <span id="query_goods_bread_crumb_title"></span></p>
			<div class="tab_panel" id="query_goods_tab_panel">
				<ul class="tab">
					<li id="${QUERY_COMMODITY_PAGE_TAB_ID_DRAFT!''}" onclick="qc.nav.tabItemClick(this)">
						<span><label>待提交</label>(${(totalCountMap[QUERY_COMMODITY_PAGE_TAB_ID_DRAFT])!'0'})</span>
					</li>
					<li id="${QUERY_COMMODITY_PAGE_TAB_ID_PENDING!''}" onclick="qc.nav.tabItemClick(this)">
						<span><label>待审核</label>(${(totalCountMap[QUERY_COMMODITY_PAGE_TAB_ID_PENDING])!'0'})</span>
					</li>
					<li id="${QUERY_COMMODITY_PAGE_TAB_ID_PASS!''}" onclick="qc.nav.tabItemClick(this)">
						<span><label>审核通过</label>(${(totalCountMap[QUERY_COMMODITY_PAGE_TAB_ID_PASS])!'0'})</span>
					</li>
					<li id="${QUERY_COMMODITY_PAGE_TAB_ID_REFUSE!''}" onclick="qc.nav.tabItemClick(this)">
						<span><label>审核拒绝</label>(${(totalCountMap[QUERY_COMMODITY_PAGE_TAB_ID_REFUSE])!'0'})</span>
					</li>
					<li id="${QUERY_COMMODITY_PAGE_TAB_ID_ALL!''}" onclick="qc.nav.tabItemClick(this)">
						<span><label>全部商品</label>(${(totalCountMap[QUERY_COMMODITY_PAGE_TAB_ID_ALL])!'0'})</span>
					</li>
				</ul>
				<div class="tab_content">
				
					<!--搜索start-->
					<div class="search_box">
						<form id="queryForm" name="queryForm" method="post">
							<p class="query_goods_condition_p">
								<span class="query_goods_condition_item">
									<label>商品名称：</label>
									<input type="text" class="inputtxt" id="commodityName" name="commodityName" value="${(commodityQueryVo.commodityName)!''}"/>
								</span>
								<span class="query_goods_condition_item">
									<label>商品编码：</label>
									<input type="text" class="inputtxt" id="commodityNo" name="commodityNo" value="${(commodityQueryVo.commodityNo)!''}"/>
								</span>
								<span class="query_goods_condition_item">
									<label style="width: 110px;">商家款色编码：</label>
									<input type="text" class="inputtxt" id="supplierCode" name="supplierCode" value="${(commodityQueryVo.supplierCode)!''}"/>
								</span>
								<span class="query_goods_condition_item">
									<label style="width: 110px;">商品款号：</label>
									<input type="text" class="inputtxt" id="styleNo" name="styleNo" value="${(commodityQueryVo.styleNo)!''}" />
								</span>
								<span class="query_goods_condition_item">
									<label>优购价格：</label>
									<input type="text" style="width: 50px;" class="inputtxt" id="minSalePrice" name="minSalePrice" value="${(commodityQueryVo.minSalePrice)!''}"/> 
									至
									<input type="text" style="width: 50px;" class="inputtxt" id="maxSalePrice" name="maxSalePrice" value="${(commodityQueryVo.maxSalePrice)!''}"/>
								</span>
								<span id="query_goods_audit_submit_time" class="query_goods_condition_item" style="display: none;">
									<label style="width:110px;">提交审核时间：</label>
									<input type="text" style="width: 80px;" class="inputtxt" id="minSubmitAuditDate" name="minSubmitAuditDate" value="${(commodityQueryVo.minSubmitAuditDate)!''}" readonly="readonly"/> 
									至
									<input type="text" style="width: 80px;" class="inputtxt" id="maxSubmitAuditDate" name="maxSubmitAuditDate" value="${(commodityQueryVo.maxSubmitAuditDate)!''}" readonly="readonly"/>
								</span>
								<span id="query_goods_is_audit_opt" style="display: none;"  class="query_goods_condition_item">
									<label>审核状态：</label>
									<select id="auditStatus" name="auditStatus" style="width:129px;">
										<option value="">请选择</option>
										<option <#if (commodityQueryVo.auditStatus) ?? && commodityQueryVo.auditStatus == AUDIT_STATUS_DRAFT>selected</#if> value="${AUDIT_STATUS_DRAFT}">
											${(AUDIT_STATUS_NAMES[AUDIT_STATUS_DRAFT])!''}
										</option>
										<option <#if (commodityQueryVo.auditStatus) ?? && commodityQueryVo.auditStatus == AUDIT_STATUS_PENDING>selected</#if> value="${AUDIT_STATUS_PENDING}">
											${(AUDIT_STATUS_NAMES[AUDIT_STATUS_PENDING])!''}
										</option>
										<option <#if (commodityQueryVo.auditStatus) ?? && commodityQueryVo.auditStatus == AUDIT_STATUS_PASS>selected</#if> value="${AUDIT_STATUS_PASS}">
											${(AUDIT_STATUS_NAMES[AUDIT_STATUS_PASS])!''}
										</option>
										<option <#if (commodityQueryVo.auditStatus) ?? && commodityQueryVo.auditStatus == AUDIT_STATUS_REFUSE>selected</#if> value="${AUDIT_STATUS_REFUSE}">
											${(AUDIT_STATUS_NAMES[AUDIT_STATUS_REFUSE])!''}
										</option>
									</select>
								</span>
								<span class="query_goods_condition_item">
									<label>商品品牌：</label>
									<select id="brandNo" name="brandNo" style="width: 129px;">
										<option value="">请选择</option>
										<#list lstBrand as item>
											<option <#if commodityQueryVo.brandNo??&&commodityQueryVo.brandNo==item.brandNo>selected</#if> value="${(item.brandNo)!""}">${(item.brandName)!""}</option>
										</#list>
									</select>
								</span>
								<span class="query_goods_condition_item">
									<label>商品分类：</label>
								    <select id="rootCattegory"  name="rootCattegory">
									    <option value="" selected="selected">一级分类</option>
									    <#list lstCat as item>
									    	<option <#if commodityQueryVo.rootCattegory??&&item.structName??&&commodityQueryVo.rootCattegory==item.structName >selected</#if> value="${(item.structName)!""}">${(item.catName)!""}</option>
									    </#list>
									</select>
				                    <select name="secondCategory" class="fl-lf" id="secondCategory">
				                    	<option value="" selected="selected">二级分类</option>
				                    </select>
				                    <select name="threeCategory" class="fl-lf" id="threeCategory">
				                    	<option value="" selected="selected">三级分类</option>
				                    </select>
								</span>
								<span style="padding-left:10px;margin-top:2px; ">
									<a id="mySubmit" class="button" onclick="qc.submit()"><span>搜索</span></a>
								</span>
							</p>
							<div class="blank10"></div>
							<p>
								<#if (commodityQueryVo.auditStatus) ?? && commodityQueryVo.auditStatus == AUDIT_STATUS_DRAFT>
									<a id="batchAudit" class="button" onclick="javascript:qc.opt.submitBatchAudit_OnClick();"><span>批量审核</span></a>
									<span class="fl" style="margin-right:0px;">
										<label style="width:52px;"><input class="checkedall" type="checkbox" onclick="javascript:qc.opt.selectBatchCommodity_OnClick(this);">全选</label>
									</span>
								</#if>
							</p>
						</form>
					</div>
					<!--搜索end-->
				
					<!--列表start-->
					<table class="list_table" style="margin-top:0px;">
						<thead>
							<tr>
								<#if (commodityQueryVo.auditStatus) ?? && commodityQueryVo.auditStatus == AUDIT_STATUS_DRAFT><th></th></#if>
								<th style="width:250px;">商品名称</th>
								<th>商品图片</th>
								<th>商品编码</th>
								<th>商家款色编码</th>
								<th>商品款号</th>
								<th>优购价</th>
								<th class="query_goods_sale_quantity_th" style="display: none;">商品销量</th>
								<th>可售数量</th>
								<th><#if (commodityQueryVo.auditStatus) ?? && commodityQueryVo.auditStatus == AUDIT_STATUS_DRAFT>创建时间<#else>提交审核时间</#if></th>
								<th>审核状态</th>
								<th id="query_goods_refuse_reason_th" style="display: none;">拒绝原因</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<#if pageFinder?? && (pageFinder.data)?? > 
								<#list pageFinder.data as item>
									<tr id="goods_${item_index}">
										<#if (commodityQueryVo.auditStatus) ?? && commodityQueryVo.auditStatus == AUDIT_STATUS_DRAFT><td style="width:40px;"><input type="checkbox" name="commodityNo" code="${item.supplier_code!''}" value="${item.commodity_no!""}"/></td></#if>
										<td id="td_${item.commodity_no!""}" style="text-align:left;">
											<div style="width:280px;word-break:break-all;">
												${(item.commodity_name)!""}
											</div>
										</td>
										<td><img src="${(item.pic_small)!""}" alt="" width="40" height="40" /></td>
										<td>${(item.commodity_no)!""}</td>
										<td>${(item.supplier_code)!""}</td>
										<td>${(item.style_no)!""}</td>
										<td>${(item.sale_price)!""}</td>
										<td class="query_goods_sale_quantity_td" style="display: none;">${(item.sale_quantity)!""}</td>
										<td>${(item.on_sale_quantity)!""}</td>
										<td><#if (commodityQueryVo.auditStatus) ?? && commodityQueryVo.auditStatus == AUDIT_STATUS_DRAFT>${(item.create_date)!"-"}<#else>${(item.submit_audit_date)!"-"}</#if></td>
										<td>${(AUDIT_STATUS_NAMES[commodityUtil.getAuditStatus(item.is_audit,item.commodity_status)])!''}</td>
										<td class="query_goods_refuse_reason_td" style="display: none;">
											<div style="width:55px; word-break:break-all;color:red;">
												${(item.refuse_reason)!"<span style='color:black;'>-</span>"}
											</div>
										</td>
										<td>
											<#--用于非全部商品-->
											<#if (optMap[QUERY_COMMODITY_OPT_IS_SHOW_DETAIL])!false><a href="javascript:qc.opt.preview_OnClick('${(item.commodity_no)!''}')" >预览</a></#if>
											<#if (optMap[QUERY_COMMODITY_OPT_IS_SHOW_UPDATE])!false><a href="javascript:qc.opt.update_OnClick('${(item.commodity_no)!''}')" >修改</a></#if>
											<#if (optMap[QUERY_COMMODITY_OPT_IS_SHOW_UPDATE])!false><#if (commodityQueryVo.auditStatus) ?? && commodityQueryVo.auditStatus == AUDIT_STATUS_DRAFT><a href="javascript:qc.opt.delete_OnClick('${(item.commodity_no)!''}', '${(item.supplier_code)!''}')" >删除</a></#if></#if>
											<#if (optMap[QUERY_COMMODITY_OPT_IS_SHOW_SUBMIT_AUDIT])!false><a href="javascript:qc.opt.submitAudit_OnClick('${(item.commodity_no)!''}', '${(item.supplier_code)!''}')" >提交审核</a></#if>
											
											<#--用于全部商品-->
											<#if (item[QUERY_COMMODITY_OPT_IS_SHOW_DETAIL])!false><a href="javascript:qc.opt.preview_OnClick('${(item.commodity_no)!''}')" >预览</a></#if>
											<#if (item[QUERY_COMMODITY_OPT_IS_SHOW_UPDATE])!false><a href="javascript:qc.opt.update_OnClick('${(item.commodity_no)!''}')">修改</a></#if>
											<#if (item[QUERY_COMMODITY_OPT_IS_SHOW_SUBMIT_AUDIT])!false><a href="javascript:qc.opt.submitAudit_OnClick('${(item.commodity_no)!''}', '${(item.supplier_code)!''}')" >提交审核</a></#if>
										</td>
									</tr>
								</#list>
							<#else>
								<tr>
									<td colspan="12" class="td-no">暂无记录！</td>
								</tr>
							</#if>
						</tbody>
					</table>
					<!--列表end-->
				
				<!--分页start-->
				<#if pageFinder??&&pageFinder.data??>
					<div class="page_box">
						<#import "/manage/widget/common.ftl" as page>
						<@page.queryForm formId="queryForm"/>
					</div>
				</#if>
				<!--分页end-->
			</div>
			</div>
		</div>
	</div>
	
<script type="text/javascript">
/**查询商品的页面tabId，未提交审核*/
var QUERY_COMMODITY_PAGE_TAB_ID_DRAFT = "${QUERY_COMMODITY_PAGE_TAB_ID_DRAFT!''}";
/**查询商品的页面tabId，待审核*/
var QUERY_COMMODITY_PAGE_TAB_ID_PENDING = "${QUERY_COMMODITY_PAGE_TAB_ID_PENDING!''}";
/**查询商品的页面tabId，审核通过*/
var QUERY_COMMODITY_PAGE_TAB_ID_PASS = "${QUERY_COMMODITY_PAGE_TAB_ID_PASS!''}";
/**查询商品的页面tabId，审核拒绝*/
var QUERY_COMMODITY_PAGE_TAB_ID_REFUSE = "${QUERY_COMMODITY_PAGE_TAB_ID_REFUSE!''}";
/**查询商品的页面tabId，全部商品*/
var QUERY_COMMODITY_PAGE_TAB_ID_ALL = "${QUERY_COMMODITY_PAGE_TAB_ID_ALL!''}";
</script>

<script type="text/javascript">
var qc = {};

/**当前选中的tab标签id*/
qc.commodityTabId = "${commodityTabId!''}";

/**url相关*/
qc.url = {};
/**表单提交url*/
qc.url.submitUrls = {};
qc.url.submitUrls[QUERY_COMMODITY_PAGE_TAB_ID_DRAFT] = basePath + "/commodity/goQueryDraftCommodity.sc";
qc.url.submitUrls[QUERY_COMMODITY_PAGE_TAB_ID_PENDING] = basePath + "/commodity/goQueryPendingCommodity.sc";
qc.url.submitUrls[QUERY_COMMODITY_PAGE_TAB_ID_PASS] = basePath + "/commodity/goQueryPassCommodity.sc";
qc.url.submitUrls[QUERY_COMMODITY_PAGE_TAB_ID_REFUSE] = basePath + "/commodity/goQueryRefuseCommodity.sc";
qc.url.submitUrls[QUERY_COMMODITY_PAGE_TAB_ID_ALL] = basePath + "/commodity/goQueryAllCommodity.sc";

/**修改页面url*/
qc.url.updateCommodityUrl = basePath + "/commodity/preUpdateCommodity.sc?commodityNo=";
/**提交审核url*/
qc.url.submitAuditUrl = basePath + "/commodity/submitAudit.sc";
/**预览url*/
qc.url.previewUrl = basePath + "/commodity/previewDetail.sc?commodityNo=";
/**提交成功图片url*/
qc.url.successImgUrl = basePath + "/yougou/js/ygdialog/skins/icons/succeed.png";

/**配置相关*/
qc.conf = {};
/**隐藏审批状态下拉框配置*/
qc.conf.hideAuditSelect = [
	QUERY_COMMODITY_PAGE_TAB_ID_DRAFT,
	QUERY_COMMODITY_PAGE_TAB_ID_PENDING,
	QUERY_COMMODITY_PAGE_TAB_ID_PASS,
	QUERY_COMMODITY_PAGE_TAB_ID_REFUSE
];
/**隐藏拒绝原因列配置*/
qc.conf.hideHideRefuseColumn = [
	QUERY_COMMODITY_PAGE_TAB_ID_DRAFT,
	QUERY_COMMODITY_PAGE_TAB_ID_PENDING,
	QUERY_COMMODITY_PAGE_TAB_ID_PASS
];
/**显示提交审批时间查询条件 配置*/
qc.conf.showAuditSubmitTime = [
	QUERY_COMMODITY_PAGE_TAB_ID_PENDING
];
/**显示销量列 配置*/
qc.conf.showSaleQuantityColumn = [
	QUERY_COMMODITY_PAGE_TAB_ID_ALL
];



/**初始化相关*/
qc.init = {};
/**
 * 隐藏审批状态下拉框
 */
qc.init.hideAuditSelectInit = function() {
	var isExist = false;
	for (var i = 0, len = qc.conf.hideAuditSelect.length; i < len; i++) {
		if(qc.commodityTabId == qc.conf.hideAuditSelect[i]) {
			isExist = true;
			break;
		}
	}
	if(!isExist) {
		$("#query_goods_is_audit_opt").show();
	}
};

/**
 * 隐藏拒绝原因列
 */
qc.init.hideHideRefuseColumnInit = function() {
	var isExist = false;
	for (var i = 0, len = qc.conf.hideHideRefuseColumn.length; i < len; i++) {
		if(qc.commodityTabId == qc.conf.hideHideRefuseColumn[i]) {
			isExist = true;
			break;
		}
	}
	if(!isExist) {
		$("#query_goods_refuse_reason_th").show();
		$(".query_goods_refuse_reason_td").show();
	}
};

/**
 * 显示提交审批时间
 */
qc.init.showAuditSubmitTime = function() {
	for (var i = 0, len = qc.conf.showAuditSubmitTime.length; i < len; i++) {
		if(qc.commodityTabId == qc.conf.showAuditSubmitTime[i]) {
			$("#query_goods_audit_submit_time").show();
			break;
		}
	}
};
/**
 * 显示销量列
 */
qc.init.showSaleQuantityColumn = function() {
	for (var i = 0, len = qc.conf.showSaleQuantityColumn.length; i < len; i++) {
		if(qc.commodityTabId == qc.conf.showSaleQuantityColumn[i]) {
			$(".query_goods_sale_quantity_th").show();
			$(".query_goods_sale_quantity_td").show();
			break;
		}
	}
};

/**操作相关*/
qc.opt = {};
/**
 * 预览 点击事件
 * @param {String} commodityNo 商品编号
 */
qc.opt.preview_OnClick = function(commodityNo) {
	window.open(qc.url.previewUrl + commodityNo);
};

/**
 * 修改 点击事件
 * @param {String} commodityNo 商品编号
 */
qc.opt.update_OnClick = function(commodityNo) {
//	window.open(qc.url.updateCommodityUrl + commodityNo);
	location.assign((qc.url.updateCommodityUrl + commodityNo + "&fromTabId=" + qc.commodityTabId));
};
/**
 * 删除 点击事件
 * @param {String} commodityNo 商品编号
 */
// 
qc.opt.delete_OnClick = function(commodityNo, supplierCode) {
	if (confirm('确定删除该商品吗？')) {
		$.ajax({
			type: 'post',
			url: '/commodity/import/delete.sc',
			dataType: 'html',
			data: 'rand=' + Math.random() + '&commodityNo=' + (commodityNo || '') + '&supplierCode=' + (supplierCode || ''),
			beforeSend: function(jqXHR) {
			},
			success: function(data, textStatus, jqXHR) {
				if ($.trim(data) == 'true') {
					$('input[value="' + commodityNo + '"]').parent().parent().fadeOut(1000, function(){ $(this).remove(); });
				} else {
					this.error(jqXHR, textStatus, data);
				}
			},
			complete: function(jqXHR, textStatus) {
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert('删除商品失败');
			}
		});
	}
}

/**
 * 提交审核 点击事件
 * @param {String} commodityNo 商品编号
 * @param {String} supplierCode 供应商款色编码
 */
qc.opt.submitAudit_OnClick = function(commodityNo, supplierCode) {
	if (confirm('您确定将该商品提交审核吗？')) {
	var auditUrl = qc.url.submitAuditUrl + "?commodityNo=" + commodityNo + 
			"&supplierCode=" + supplierCode;
	$.ajax({
		url: auditUrl,
		cache: false,
		type: "GET",
		dataType: "json",
		success: function(data) {
			if(data == null || typeof(data) != "object" ||
					typeof(data.success) != "string")
				return; 
			var errMsg = data.msg || "该商品提交审核失败";
			if("true" != data.success) {
				ygdg.dialog.alert(data.msg);
				return;
			}
			ygdg.dialog.tips('该商品提交审核成功', 1, qc.url.successImgUrl);
			setTimeout(function() {location.reload(true);}, 1000);
		},
		error: function() {
			alert("网络异常，请刷新后重试!");
		}
	});
	}
};

/**导航相关*/
qc.nav = {};
/**
 * 初始化导航
 */
qc.nav.init = function() {
	$("#query_goods_bread_crumb_title").text(
			$("#" + qc.commodityTabId + " label").text());
	$("#" + qc.commodityTabId).addClass("curr");
};

/**
 * 标签项点击事件
 * @param {Object} tabObj 当前点击的标签li 
 */
qc.nav.tabItemClick = function(tabObj) {
	location.assign(qc.url.submitUrls[tabObj.id]);
};

/**
 * 提交表单查询
 */
qc.submit = function() {
	var queryForm = document.getElementById("queryForm");
	queryForm.action = qc.url.submitUrls[qc.commodityTabId];
	queryForm.submit();
};
	
$(function() {
	//初始化导航
	qc.nav.init();
	
	//隐藏审批状态下拉框
	qc.init.hideAuditSelectInit();
	
	//隐藏拒绝原因列配置
	qc.init.hideHideRefuseColumnInit();
	
	//显示提交审批时间
	qc.init.showAuditSubmitTime();
	
	//显示销量列
	qc.init.showSaleQuantityColumn();
	
	$("#minSubmitAuditDate").calendar({maxDate:'#maxSubmitAuditDate'});
	$("#maxSubmitAuditDate").calendar({minDate:'#minSubmitAuditDate'});
	//加载三级分类
	setGoodsCattegory();	
});

/**
 *	批量选择商品
 */
qc.opt.selectBatchCommodity_OnClick = function(checkbox) {
	$('.list_table > tbody').find('input[name="commodityNo"]').filter(function(){ return !this.disabled; }).attr('checked', checkbox.checked);
}
/**
 * 批量提交审核商品
 */
qc.opt.submitBatchAudit_OnClick = function() {
	var rowSet = $('.list_table > tbody').find('input[name="commodityNo"]').filter(function(){ return this.checked && !this.disabled; });
	if (rowSet.size() <= 0) {
		alert('请您先选择需要提交审核的商品再进行操作!');
		return false;
	}
	if (window.confirm('您确定将选择的商品提交审核吗?')) {
		$.ajax({
			type: 'post',
			url: '/commodity/import/audit.sc',
			dataType: 'json',
			data: 'rand=' + Math.random() + '&' + rowSet.map(function(){ return 'commodityNo=' + this.value + '&supplierCode=' + this.getAttribute('code') }).get().join('&'),
			beforeSend: function(jqXHR) {
			},
			success: function(data, textStatus, jqXHR) {
				var spanMessage;
				$.each(data, function(i){
					if ($.trim(this.auditMessage) == 'true') {
						$('#td_' + this.commodityNo).append('<font style="font-size: 12px; color: green;"><br/>提交审核成功</font>').parent().fadeOut(3000, function(){ $(this).remove(); }).find('td:last,td:first').html('');
					} else {
						$('#td_' + this.commodityNo).find('font').remove();
						$('#td_' + this.commodityNo).append('<font style="font-size: 12px; color: red;"><br/>提交审核失败：' + this.auditMessage + '</font>');
					}
				});
			},
			complete: function(jqXHR, textStatus) {
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert('提交审核失败');
			}
		});
	}
}
</script>
</body>
</html>
