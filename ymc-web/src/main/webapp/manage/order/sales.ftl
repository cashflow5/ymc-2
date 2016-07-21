<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-销售查询</title>
</head>
<body>
	<div class="main_container">
		<div class="normal_box">
			<p class="title site">当前位置：商家中心 &gt; 订单 &gt; 销售查询</p>
			<div class="tab_panel">
				<ul class="tab">
					<li class="curr"><span>销售查询列表</span></li>
					<li class="tab_fr"><!-- onclick="doExportAllSalesDetail('queryAllSales.sc')"-->
						<a class="button" id="myExport" name="myExport"><span>导出销售明细</span></a>
					</li>
				</ul>
				<div class="tab_content">
					<form name="queryVoform" id="queryVoform" action="queryAllSales.sc" method="post">
					<!--搜索start-->
					<div class="search_box">
						<!--标识页面是否是第一次进入-->
						<input type="hidden" name="pageTag" value="1" />
						<p>
						<span><label>货品条码：</label><input type="text" name="thirdPartyCode" id="thirdPartyCode" class="inputtxt" value="${salesVO.thirdPartyCode!''}" /></span>
						<span><label>商品编码：</label><input type="text" name="no" id="no" class="inputtxt" value="${salesVO.no!''}" /></span>
						<span><label style="width:110px;">商家款色编码：</label><input type="text" name="supplierCode" id="supplierCode" class="inputtxt" value="${salesVO.supplierCode!''}" /></span>
						<span><label style="width:110px;">货品编号：</label><input type="text" name="productNo" id="productNo"  class="inputtxt" value="${salesVO.productNo!''}" /></span>
						</p>
						<p>
						<span><label>商品名称：</label><input type="text" name="commodityName" id="commodityName" class="inputtxt" value="${salesVO.commodityName!''}" /></span>
						<span><label>订单号：</label><input type="text" name="orderSubNo" id="orderSubNo" class="inputtxt" value="${salesVO.orderSubNo!''}" /></span>
						<span>
							<label>下单时间：</label>
							<input type="text" name="timeStart" id="startTime" value="${salesVO.timeStart!''}" class="inputtxt" style="width:120px;" /> 至
							<input type="text" name="timeEnd" id="endTime" value="${salesVO.timeEnd!''}" class="inputtxt" style="width:120px;" />
						</span>
						</p>
						<p>
						<span>
							<label>发货时间：</label>
							<input type="text" name="shipTimeStart" id="shipTimeStart" value="${salesVO.shipTimeStart!''}" class="inputtxt" style="width:120px;" /> 至
							<input type="text" name="shipTimeEnd" id="shipTimeEnd" value="${salesVO.shipTimeEnd!''}" class="inputtxt" style="width:120px;" />
						</span>
						<span>&nbsp;&nbsp;<a class="button" id="mySubmit" name="mySubmit" onclick="mySubmit()"><span>搜索</span></a></span>
						</p>
						<input type="hidden" id="firstFlag" name="firstFlag" value="0" />
					</div>
					<!--搜索end-->
					<!--列表start-->
					<table class="list_table">
					<thead>
					<tr>
					<th width="300">商品名称</th>
					<th>商品编码</th>
					<th>货品编码</th>
					<th>商家款色编码</th>
					<th>商家货品条码</th>
					<th>销售数量</th>
					<th>操作</th>
					</tr>
					</thead>
					<tbody>
						<#if pageFinder??&&(pageFinder.data)??&&(pageFinder.data?size > 0)>
							<#list pageFinder.data as item>
								<tr>
								    <td>${item.commodityName!''}<input type="hidden" name="goodsName" id="goods_${item.productNo!''}" value="${item.commodityName!''}"/></td>
									<td>${item.no!''}</td>
									<td>${item.productNo!''}</td>
									<td>${item.supplierCode!''}</td>
									<td>${item.thirdPartyCode!''}</td>
									<td>${item.salesCount!''}</td>
									<td><a href="javascript:;" onclick="showDetail('${item.productNo!''}','${item.supplierCode!''}','${item.no!''}','${item.salesCount!''}')" >销售明细</a> </td>
								</tr>
							</#list>
						<#elseif !pageTag??>
							<tr>
								<td class="td-no" colspan="8">请搜索获取数据！</td>
							</tr>
						<#else>
							<tr>
								<td class="td-no" colspan="8">暂无数据！</td>
							</tr>
						</#if>
					</tbody>
					</table>
					</form>
				</div>
				<!--列表end-->
				<#if pageFinder??&&pageFinder.data??&&(pageFinder.data?size > 0)>
					<!--分页start-->
					<div class="page_box">
						<div class="dobox">
							<span style="padding-right: 20px;">本页销售金额：<font color="red">${salesVO.onePageTotalAmount?default(0)?string('#.00')}</font>&nbsp;元</span>
							<span>总销售金额：<font color="red">${salesVO.allPageTotalAmount?default(0)?string('#.00')}</font>&nbsp;元</span>
						</div>
						<#if pageFinder??&&pageFinder.data??>
							<#import "/manage/widget/common.ftl" as page>
							<@page.queryForm formId="queryVoform"/>
						</#if>
					</div>
					<!--分页end-->
				</#if>
			</div>
		</div>
		
		
	</div>
</body>
</html>
<script type="text/javascript">
	$("#startTime").calendar({maxDate:'#endTime',format:'yyyy-MM-dd HH:mm:ss'});
	$("#endTime").calendar({minDate:'#startTime',format:'yyyy-MM-dd HH:mm:ss'});
	$("#shipTimeStart").calendar({maxDate:'#shipTimeEnd',format:'yyyy-MM-dd HH:mm:ss'});
	$("#shipTimeEnd").calendar({minDate:'#shipTimeStart',format:'yyyy-MM-dd HH:mm:ss'});
	
	function showDetail(productNo, supplierCode, commodityNo, salesCount){
		var commodityName = $("#goods_"+productNo).val();		
		var url = "${BasePath}/order/querySalesDetail.sc?productNo="+productNo;
			url += "&no="+commodityNo+"&salesCount="+salesCount;
		var orderNoText = $.trim($('#orderSubNo').val());
		if(orderNoText != null && orderNoText != ''){
		    url += "&orderSubNo="+orderNoText;
		}
		var createTimeStart = $('#startTime').val();
		if(createTimeStart != null && createTimeStart != '') {
			url += "&timeStart="+createTimeStart;
		}
		var createTimeEnd = $('#endTime').val();
		if(createTimeEnd != null && createTimeEnd != '') {
			url += "&timeEnd="+createTimeEnd;
		}
		var shipTimeStart = $('#shipTimeStart').val();
		if(shipTimeStart != null && shipTimeStart != '') {
			url += "&shipTimeStart="+shipTimeStart;
		}
		var shipTimeEnd = $('#shipTimeEnd').val();
		if(shipTimeEnd != null && shipTimeEnd != '') {
			url += "&shipTimeEnd="+shipTimeEnd;
		}
		url = encodeURI(encodeURI(url));
		window.location = url;
	}
	$(function(){
		$("#myExport").bind("click",function(){
			doExportAllSalesDetail("queryAllSales.sc");
		});
	});

	//提交表单
	function mySubmit(){
		if(checkAllTextNull()) {
			ygdg.dialog.alert("请输入您的查询条件！");
		} else {
			$("#mySubmit").unbind("click");
			$("#mySubmit").addClass("dis");
			$("#queryVoform").submit();
		}
	}
	
	//导出
	function doExportAllSalesDetail(url){
		if(checkAllTextNull()) {
			ygdg.dialog.alert("请输入您的查询条件！");
		} else {
			$.ajax({
				type:'POST',
				data:$("#queryVoform").serialize(),
				url:'${BasePath}/order/queryAllSalesCount.sc',
				dataType:'text',
				success:function(data){
					if(parseInt(data)>0){
						$("#myExport").unbind("click");
						$("#myExport").addClass("dis"); 
						$("#queryVoform").attr("action","doExportAllSalesList.sc");
						$("#queryVoform").submit();
						$("#queryVoform").attr("action",url);
						setTimeout(function(){
						  	$("#myExport").removeClass("dis"); 
						  	$("#myExport").bind("click",function(){
						  		doExportAllSalesDetail("queryAllSales.sc");
						  	});
						},20000);
					}else{
						ygdg.dialog.alert("销售明细查询为空！");
					}
				},
				error:function(data){
					ygdg.dialog.alert("服务器发生错误，导出失败！");
				}
			});
		} 
	}
	
	function checkAllTextNull(){
		var num = 0;
     	var str = "";
     	$("input[type$='text']").each(function(n){
          	if($(this).val() != "") {
               	num++;
          	}
     	});
     	if(num > 0) {
          	return false;
     	} else {
          	return true;
		}
	}
	$(document).ready(function(){
		$("#mySubmit").removeClass("dis"); 
		//$("#mySubmit").bind("click",function(){
		//	mySubmit();
		//});
	});
</script>