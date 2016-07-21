<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-销售明细</title>
<script type="text/javascript">
	//提交表单
	function mySubmit(){
		$("#queryVoform").submit();
	}
	
	//导出
	function doExportSalesDetail(url){
		$("#queryVoform").attr("action","doExportSalesDetail.sc");
		$("#queryVoform").submit();
		$("#queryVoform").attr("action",url);
	}
</script>
</head>

<body>
	
	
	<div class="main_container">
		<div class="normal_box">
			<p class="title site">当前位置：商家中心 &gt; 订单 &gt; 销售查询</p>
			<!--详情start-->
			<div  class="detail_box">
				<p>
					<span>商品名称：${salesVO.commodityName!''}</span>
					<span>商家款色编码：${salesVO.supplierCode!''}</span>
					<span>商品编码：${salesVO.no!''}</span>
					<span>销售数量：<strong class="f_hl">${salesVO.salesCount!''}</strong></span>
				</p>
				<div class="clear"></div>
			</div>
			<hr class="line" />
			<!--搜索start-->
			<div class="search_box">
				<form name="queryVoform" id="queryVoform" action="querySalesDetail.sc" method="post">
					<!--货品编码-->
					<input type="hidden" name="productNo" value="${salesVO.productNo!''}" />
					<input type="hidden" name="commodityName"  value="${salesVO.commodityName!''}" />
					<input type="hidden" name="supplierCode" value="${salesVO.supplierCode!''}" />
					<input type="hidden" name="no" value="${salesVO.no!''}" />
					<input type="hidden" name="salesCount" value="${salesVO.salesCount!''}" />
					<p>
						<span><label>发货时间：</label>
							<input type="text" name="shipTimeStart" id="shipTimeStart"  value="${salesVO.shipTimeStart!''}" class="inputtxt" style="width:120px;" /> 至
							<input type="text" name="shipTimeEnd" id="shipTimeEnd"  value="${salesVO.shipTimeEnd!''}" class="inputtxt" style="width:120px;" />
						</span>
						<span><label>下单时间：</label>
							<input type="text" name="timeStart" id="timeStart"  value="${salesVO.timeStart!''}" class="inputtxt" style="width:120px;" /> 至
							<input type="text" name="timeEnd" id="timeEnd"  value="${salesVO.timeEnd!''}" class="inputtxt" style="width:120px;" />
						</span>
						<span><label>订单号：</label><input type="text" name="orderSubNo" id="orderSubNo" class="inputtxt" value="${salesVO.orderSubNo!''}" /></span>
						<span><a class="button" id="mySubmit" onclick="mySubmit()"><span>搜索</span></a></span>
					</p>
				</form>
			</div>
			<!--搜索end-->
			<!--详情end-->
			<div class="tab_panel">
				<ul class="tab">
					<li class="curr"><span>销售明细</span></li>
					<li class="tab_fr">
						<a class="button" <#if pageFinder??&&pageFinder.data??>onclick="doExportSalesDetail('querySalesDetail.sc')"<#else>onclick="alert('没有数据导出！')"</#if> ><span>导出销售明细</span></a>
					</li>
				</ul>
				<div class="tab_content">
					<!--列表start-->
					<table class="list_table">
						<thead>
							<tr>
								<th>发货时间</th>
								<th>下单时间</th>
								<th>订单号</th>
								<th>商家货品条码</th>
								<th>货品条码</th>
								<th>商品规格</th>
								<th>发货数量</th>
								<th>拒收退货</th>
								<th>售后退货</th>
							</tr>
						</thead>
						<tbody>
							<#if pageFinder??&&pageFinder.data??>
								<#list pageFinder.data as item>
									<tr>
										<td>${item.shipTimeEnd!''}</td>
										<td>${item.timeEnd!''}</td>
										<td>${item.orderSubNo!''}</td>
										<td>${item.thirdPartyCode!''}</td>
										<td>${item.insideCode!''}</td>
										<td>${item.commoditySpecification!''}</td>
										<td>${item.outQuantity!''}</td>
										<td>${item.rejectionQuantity!''}</td>
										<td>${item.returnQuantity!''}</td>
									</tr>
								</#list>
							<#else>
								<tr>
									<td colspan="9">没有相关数据</td>
								</tr>
							</#if>
						</tbody>
					</table>
					<!--列表end-->
					<#if pageFinder??&&pageFinder.data??>
					<!--分页start-->
					<div class="page_box">
						<div class="dobox">
							<a href="javascript:window.history.go(-1);">上一步</a>
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
		
		
	</div>
</body>
</html>
<script type="text/javascript">
	// 查询日期
	$("#shipTimeStart").calendar({maxDate:'#shipTimeEnd',format:'yyyy-MM-dd HH:mm:ss'});
	$("#shipTimeEnd").calendar({minDate:'#shipTimeStart',format:'yyyy-MM-dd HH:mm:ss'});
	$("#timeStart").calendar({maxDate:'#timeEnd',format:'yyyy-MM-dd HH:mm:ss'});
	$("#timeEnd").calendar({minDate:'#timeStart',format:'yyyy-MM-dd HH:mm:ss'});
</script>