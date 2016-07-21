<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#include "../../../yitianwms/yitianwms-include.ftl">
<title>优购商城--商家后台</title>
</head><!-- 商品分类四级联动 -->
<script src="${BasePath}/js/wms/axaj.js" type="text/javascript"></script>
<script type="text/javascript" src="${BasePath}/js/wms/stocksmanager/utils.js"></script>
<script type="text/javascript" src="${BasePath}/js/wms/common/common_return_defect_sel.js"></script>
<script type="text/javascript" src="${BasePath}/js/wms/ajaxfileupload.js"></script>
<script type="text/javascript">

//全选

$(document).ready(function(){
        
		$("#checkall").click(
			function(){ 
				if(this.checked ){ 
						$("input[name='commodityCB']").each(function(){
						   if(!this.disabled)
						   this.checked=true;
						}); 
				}else{ 
						$("input[name='commodityCB']").each(function(){this.checked=false;}); 
				} 
			}
		);
		
		$('#bTime').calendar({
			maxDate : '#eTime',
			format : 'yyyy-MM-dd',
			targetFormat : 'yyyy-MM-dd'
		});
		$('#eTime').calendar({
			minDate : '#bTime',
			format : 'yyyy-MM-dd',
			targetFormat : 'yyyy-MM-dd'
		});
		
});

function onsub(selValue, selId) {
	var value = selValue;
	$.ajax( {
		type : "POST",
		url : "getChildCat.sc",
		data : {
			"value" : value
		},
		dataType : "json",
		success : function(data) {
			$("#" + selId).empty();// 清空下来框
		$("#" + selId).append("<option value='0'>选择分类</option>");
		for ( var i = 0; i < data.length; i++) {
			var option = "<option value='" + data[i].structName + ";"
					+ data[i].id + "'>" + data[i].name + "</option>";
			$("#" + selId).append(option);
		}
	}
	});
}


function createUploadRow() {
	var checkedArray = getCheckBoxs("commodityCB");
	if (checkedArray.length == 0) {
		alert("请选择货品！");
		return;
	}
	if (!confirm("确定已经勾选？")) {
		return;
	}
	var idArray = ["id","outDefectCode","productNo", "goodsName",
			"specification", "units","outQty", "realConfrimQty","realApplyQty","confirmQuantity","purchaseReturnDetailId"];
	var values = rowSelectVal("commodityCB", idArray);
	dg.curWin.createTable("subTable", values, 1);
	dg.curWin.doSave(2);//提交保存
	closewindow();
	return false;
}

function allPurchaseDetailConfirm(){
	
	var totalDetailRows = $("input[name='commodityCB']").length;
	if(totalDetailRows == 0){
		alert("列表为空，无法全部确认！");
		return ;
	}
	
	if(!confirm("您确定全部确认吗？")){	
		return false;
	}
	
	dg.curWin.document.getElementById("supplierCodeQ").value = $('#supplierCodeQ').val();
	dg.curWin.document.getElementById("supplierNameQ").value = $('#supplierNameQ').val();
	dg.curWin.document.getElementById("commodityCodeQ").value = $('#commodityCodeQ').val();
	dg.curWin.document.getElementById("purchaseReturnCodeQ").value = $('#purchaseReturnCodeQ').val();
	dg.curWin.document.getElementById("beginApproverDate").value = $('#beginApproverDate').val();
	dg.curWin.document.getElementById("endApproverDate").value = $('#endApproverDate').val();
	dg.curWin.document.getElementById("returnDefectConfirmIdQ").value = $('#returnDefectConfirmIdQ').val();
	dg.curWin.document.getElementById("queryTypeFlag").value = $('#queryTypeFlag').val();
	
	dg.curWin.allConfirmDetail();//提交保存
	
	closewindow();
	
	return false;
	
}

</script>
<body>
<div class="container"> 
	<!--工具栏start-->
	<div class="toolbar">
		<div class="btn" onclick="createUploadRow();"><span class="btn_l"></span><b class="ico_btn add"></b><span class="btn_txt">勾选确定</span><span class="btn_r"></span> </div>
		<div class="line"></div>
		<div class="btn" onclick="allPurchaseDetailConfirm();"><span class="btn_l"></span><b class="ico_btn add"></b><span class="btn_txt">全部确定</span><span class="btn_r"></span> </div>
		<div class="line"></div>
		<div class="btn" onclick="closewindow();"><span class="btn_l"></span><b class="ico_btn delete"></b><span class="btn_txt">关闭</span><span class="btn_r"></span> </div>
	</div>
	<!--工具栏end-->
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr"><span>召回返回申请单-采购退库列表 </span></li>
			</ul>
		</div>
		<!--当前位置end-->
		<div class="modify"> 
			<!--普通搜索内容开始-->
			<form id="queryL" action="${BasePath}/wms/stocksmanager/purchasereturn/queryPurchaseReturnDetailForRO.sc" method="post" enctype="multipart/form-data">
			<input type="hidden" name="supplierCodeQ" id="supplierCodeQ" value="${purchaseReturnDetailQueryVo.supplierCodeQ?if_exists}" />
			<input type="hidden" name="supplierNameQ" id="supplierNameQ" value="${purchaseReturnDetailQueryVo.supplierNameQ?if_exists}" />
			<input type="hidden" name="returnDefectConfirmIdQ" id="returnDefectConfirmIdQ" value="${purchaseReturnDetailQueryVo.returnDefectConfirmIdQ?if_exists}" />
			<input type="hidden" name="queryTypeFlag" id="queryTypeFlag" value="${purchaseReturnDetailQueryVo.queryTypeFlag?if_exists}" />
			<div class="add_detail_box">
				<input type="hidden" name="url" value="/supply/purchase/recallreturn/common_recall_return_forspredef" />
					<p>
						<span>
						  <label>供&nbsp;应&nbsp;商：</label>
						  <span style="width:250px;">${purchaseReturnDetailQueryVo.supplierNameQ?if_exists}</span>
	                    </span>
                    </p>
                    <p>
						<span>
						  <label>退货出库单号：</label>
						  <input type="text" id="purchaseReturnCodeQ" name="purchaseReturnCodeQ" value="${purchaseReturnDetailQueryVo.purchaseReturnCodeQ?if_exists}" />
						</span>
					</p>
					<p>	
						<span>
						<label>退货出库时间：</label>
			        	<input type="text" name="beginApproverDate" id="bTime" value="${purchaseReturnDetailQueryVo.beginApproverDate?if_exists}" readonly="readonly" size="20" />
			        	-
			        	<input type="text" name="endApproverDate" id="eTime" value="${purchaseReturnDetailQueryVo.endApproverDate?if_exists}" readonly="readonly" size="20"  />
						</span>
					</p>
					<p>
					<span>
					  <label>货品编码：</label>
					  <input type="text" id="commodityCodeQ" name = "commodityCodeQ" value = "${purchaseReturnDetailQueryVo.commodityCodeQ?if_exists}" />
					 </span>
					 </p>
					<input type="submit" value="查询" class="btn-add-normal" />
				</div>
			</form>
			<!--普通搜索内容结束--> 
			<!--列表start-->
			<table cellpadding="0" cellspacing="0" class="list_table">
				<thead>
					<tr>
						<th width="30">
							<input type="checkbox" value="" id="checkall" />
						</th>
						<th>出库单号</th>
						<th>货品编码</th>
						<th>商品名称</th>
						<th>规格</th>
						<th>单位</th>
						<th>出库数量</th>
					    <th>已确认数量</th>
					    <th>已申请数量</th>
					</tr>
				</thead>
				<tbody id="tbdy">
				<#if pageFinder?? && (pageFinder.data)?? >
				<#list pageFinder.data as item >
				<tr>
					<td>
						   <input type="checkbox"  name="commodityCB" id = "cbox${item_index}" value="${item_index}"  />
					</td>
					<td>
							<input type="hidden" id="outDefectCode[${item_index}]" name="outDefectCode[${item_index}]" value="${item.purchaseReturnCode!''}" />
							${item.purchaseReturnCode!""}  
					</td>
					<td>
						    <input type="hidden" id="id[${item_index}]" name="id[${item_index}]"  value="${item.id!''}" />
							<input type="hidden" id="productNo[${item_index}]" name="productNo[${item_index}]" value="${item.commodityCode!''}" />
							${item.commodityCode!""} 
					</td>
					
					<td title="${item.goodsName!""}">
						<input type="hidden" id="goodsName[${item_index}]" name="goodsName[${item_index}]" value="${item.goodsName!''}" />
						<#if item.goodsName??>
						<#if item.goodsName?length lt 40 >
							${item.goodsName!""}
						<#else>
							${item.goodsName?default("")?substring(0,40)}......
						</#if>
						</#if> 
					</td>
					<td>
						<input type="hidden" id="specification[${item_index}]" name="specification[${item_index}]" value="${item.specification!''}" />
						${item.specification!""} 
					</td>
					<td>
						<input type="hidden" id="units[${item_index}]" name="units[${item_index}]" value="${item.units!''}" />
						${item.units!""} 
					</td>
					<td>
						<input type="hidden" id="outQty[${item_index}]" name="outQty[${item_index}]" value="${item.quantity!''}" />
						${item.quantity!""} 
					</td>
					<td>
						<input type="hidden" id="realConfrimQty[${item_index}]" name="realConfrimQty[${item_index}]" value="${item.confirmQuantit!''}" />
						${item.confirmQuantit!""} 
					</td>
					<td>
						<input type="hidden" id="realApplyQty[${item_index}]" name="realApplyQty[${item_index}]" value="${item.applyQuantit!''}" />
						${item.applyQuantit!""} 
					</td>
					<td>
						<input type="hidden" id="confirmQuantity[${item_index}]" name="confirmQuantity[${item_index}]" value="${item.unConfirmQuantit!''}" />
					</td>
					<td>
						<input type="hidden" id="purchaseReturnDetailId[${item_index}]" name="purchaseReturnDetailId[${item_index}]" value="${item.id!''}" />
					</td>
				</tr>
				</#list>
				<#else>
				<tr>
					<td  colspan=9">没有相关记录！<span id="showMsg"></span></td>
				
				<tr> </#if>
					
						</tbody>
			</table>
			<!--列表end--> 
		</div>
		<!--分页start-->
		<div class="bottom clearfix"> <#if pageFinder?? && (pageFinder.data)?? > 
			<#import "../../../common.ftl" as page>
			<@page.queryForm formId="pageForm" />
			</#if> </div>
		<!--分页end--> 
	</div>
</div>
<form action="${BasePath}/wms/stocksmanager/purchasereturn/queryPurchaseReturnDetailForRO.sc" name="pageForm" id="pageForm" method="POST">
	<input type="hidden" name="url" value="/supply/purchase/recallreturn/common_recall_return_forspredef" />
	<input type="hidden" name="supplierCodeQ" id="supplierCodeQ" value="${purchaseReturnDetailQueryVo.supplierCodeQ?if_exists}" />
	<input type="hidden" name="supplierNameQ" id="supplierNameQ" value="${purchaseReturnDetailQueryVo.supplierNameQ?if_exists}" />
	<input type="hidden" name="commodityCodeQ" id="commodityCodeQ" value="${purchaseReturnDetailQueryVo.commodityCodeQ?if_exists}" />
	<input type="hidden" name="purchaseReturnCodeQ" id="purchaseReturnCodeQ" value="${purchaseReturnDetailQueryVo.purchaseReturnCodeQ?if_exists}" />
	<input type="hidden" name="beginApproverDate" id="beginApproverDate" value="${purchaseReturnDetailQueryVo.beginApproverDate?if_exists}" />
	<input type="hidden" name="endApproverDate" id="endApproverDate" value="${purchaseReturnDetailQueryVo.endApproverDate?if_exists}" />
	<input type="hidden" name="returnDefectConfirmIdQ" id="returnDefectConfirmIdQ" value="${purchaseReturnDetailQueryVo.returnDefectConfirmIdQ?if_exists}" />
	<input type="hidden" name="queryTypeFlag" id="queryTypeFlag" value="${purchaseReturnDetailQueryVo.queryTypeFlag?if_exists}" />
</form>

</body>
</html>
<#include "../../../yitianwms/yitianwms-include-bottom.ftl">