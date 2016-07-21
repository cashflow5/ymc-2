<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<#if authrityMap??&&authrityMap?size!=0>
<div class="itm">
	<#list authrityMap?keys as key>
		<h3 class="hd"><span>${key?substring(0,key?index_of("@~"))}</span></h3>
		<ul class="bd">
		    <#if authrityMap[key]??>
				<#list authrityMap[key] as item>
					<#if (item['authrityURL']!'')?index_of("addCommodityui.sc")!=-1 || 
					(item['authrityURL']!'')?index_of("gotoReportManagementSurvey.sc")!=-1>
						<#assign link="target='_blank'">
						<#else>
						<#assign link="">
					</#if>
					<li id="_${item['authrityURL']?replace('/', '_')?replace('.', '_')}"><a href="${BasePath}/${item['authrityURL']!''}" ${link!''}>${item['authrityName']!''}</a></li>
				</#list>
			</#if>
		</ul>
	</#list>
</div>
</#if>
</body>
<script type="text/javascript">
var id=location.pathname.replace(new RegExp(/(\/|\.)/g),'_');
if(document.getElementById(id)){
 $("#"+id).addClass('on');
 $('.on').parent().show();
 $('.on').parent().prev().find('span').removeClass('collapse');
}else if(id=='_order_toPrintNewNotDelivery_sc'||id=='_order_toPrintNewDelivery_sc'||id=='_order_toPrintNewOutstock_sc'||id=='_order_toPrintNewAll_sc'){
 $("#_order_toDocumentPrintingNew_sc").addClass('on');
}else if(id=='_order_toPrintNotDelivery_sc'||id=='_order_toPrintDelivery_sc'||id=='_order_toPrintOutstock_sc'||id=='_order_toPrintAll_sc'){
 $("#_order_toDocumentPrinting_sc").addClass('on');
}else if(id=='_order_fastdelivery_notdelivery_sc'||id=='_order_fastdelivery_delivered_sc'||id=='_order_fastdelivery_outoforders_sc'||id=='_order_fastdelivery_allorders_sc'){
 $("#_order_fastdelivery_notexported_sc").addClass('on');
}else if(id=='_quality_to_queryOrderNoOrExpressNo_sc'){
 $("#_quality_to_qualityRegister_sc").addClass('on');
}else if(id=='_qualityquery_returnDetail_sc'){
 $("#_qualityquery_qualityList_sc").addClass('on');
}else if(id=='_finance_balancebill_detail_sc'){
 $("#_finance_balancebill_queryAll_sc").addClass('on');
}else if(id=='_commodity_goUpdateCommodity_sc'){
 $("#_commodity_goAddCommodity_sc").addClass('on');
}else if(id=='_order_querySalesDetail_sc'){
 $("#_order_queryAllSales_sc").addClass('on');
}else if(id=='_merchants_login_to_supplierContract_sc'||id=='_merchants_login_to_supplierLinkMan_sc'){
 $("#_merchants_login_to_MerchantsUser_sc").addClass('on');
}else if(id=='_dialoglist_detail_sc'){
 $("#_dialoglist_query_sc").addClass('on');
}else if(id=='_wms_supplyStockInput_queryOrderTipsStockUnder_sc'){
 $("#_wms_supplyStockInput_querySupplyGenStock_sc").addClass('on');
}else if(id=='_quality_returnDetail_sc'){
 $("#_quality_notPassList_sc").addClass('on');
}else if(id=='_afterSale_to_queryAfterSaleList_sc' || id=='_afterSale_queryAfterSale_sc'){
 $("#_afterSale_queryAfterSaleList_sc").addClass('on');
}else if(id=='_afterSale_to_compensate_reply_sc' || id.indexOf('afterSale_compensate_list_sc')!=-1||id=='_afterSale_compensate_handling_list_sc'||id=='_afterSale_compensate_view_sc'){
 $("#_afterSale_compensate_handling_list_sc").addClass('on');
}else if(id=='_invoice_to_detail_sc'){
 $("#_invoice_query_sc").addClass('on');
}else if(id=='_taobao_goYougouTaobaoBrand_sc'){
 $("#_taobao_goYougouTaobaoItemCat_sc").addClass('on');
}else if(id=='_taobao_toUpdateTaobaoItem_sc'){
	$("#_taobao_goTaobaoItemList_sc").addClass('on');
}else if(id=='_taobao_goYougouTaobaoItemProTemplet_sc'){
	$("#_taobao_goYougouTaobaoTemplet_sc").addClass('on');
}else if(id=='_commodity_go4SaleCommodityRecycle_sc'){
	$("#_commodity_goWaitSaleCommodity_sc").addClass('on');
}else if(id=='_taobao_goRecycle_sc'){
	$("#_taobao_goTaobaoItemList_sc").addClass('on');
}else if(id=='_commodity_goWaitSaleSensitiveCommodity_sc'){
	$("#_commodity_goWaitSaleCommodity_sc").addClass("on");
}
$('#newleft .itm span').click(function(){
	var _this=$(this);
	if(!_this.hasClass('collapse')){
		_this.parent().next(0).slideUp(200);
		_this.addClass('collapse');
	}else{
		_this.parent().next(0).slideDown(200);
		_this.removeClass('collapse');
	}
});

</script>
</html>