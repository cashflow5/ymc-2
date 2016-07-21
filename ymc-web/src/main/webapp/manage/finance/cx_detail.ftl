<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-结算单详情</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>

<script type="text/javascript">
	function infoDetail(activeName,supplierName,supplierCode){
		var activeName = encodeURI(encodeURI(activeName));
		var supplierName = encodeURI(encodeURI(supplierName));
		
		window.open('${BasePath}/finance/balancebill/q_detail.sc?activeName='+activeName+'&supplierName='+supplierName+'&supplierCode='+supplierCode,'','');
	}
</script>
</head>
<body>
	
	
	<div class="main_container">
		
			<div class="normal_box">
				<p class="title site">当前位置：商家中心 &gt; 财务 &gt; 结算单详情</p>
				<!--详情start-->
				<div  class="detail_box">
				<p>
				<span>结算单号：${balanceBill.balanceBillNumber?default('')}</span>
				<span>供应商名称：${balanceBill.supplierName?default('')}</span>
				<span>商家编号：${balanceBill.supplierCode?default('')}</span>
				<span>结算类型：
					<#if balanceBill.balanceType??>
						<#if balanceBill.balanceType==1>
								底价结算
							<#elseif balanceBill.balanceType==2>
								配折结算
							<#elseif balanceBill.balanceType==3>
								扣点结算
							<#elseif balanceBill.balanceType==4>
								促销结算
						</#if>
					</#if>
					</span>
				</p>
				<p>
				<span>结算周期：${balanceBill.balanceStartDate?default('')}  至   ${balanceBill.balanceEndDate?default('')}</span>
				<span>销售金额：<strong class="f_hl">￥${balanceBill.balanceMoney?default('0.00')?string('##0.00')}</strong></span>
				</p>
				<div class="clear"></div>
				</div>
				<!--详情end-->
				<hr class="line" />
				<!--搜索start-->
				<div class="search_box">
					<form action="${BasePath}/finance/balancebill/cx_detail.sc" name="cxDetailForm" id="cxDetailForm" method="post">
					<input type="hidden" name="id" id="id"  class="inputtxt" value="${id?default('')}"/>
					<p>
					<span><label>活动名称：</label><input type="text" name="activeName" id="activeName"  class="inputtxt" value="${activeName?default('')}"/></span>
					<span><a class="button" id="mySubmit"><span onclick="$('#cxDetailForm').submit()">搜索</span></a></span>
					</p>
					</form>
				</div>
				<!--搜索end-->
					<div class="tab_panel">
							<ul class="tab">
							<li class="curr"><span>招商结算单详情(
							<#if balanceBill.balanceType??>
								<#if balanceBill.balanceType==1>
									底价结算
								<#elseif balanceBill.balanceType==2>
									配折结算
								<#elseif balanceBill.balanceType==3>
									扣点结算
								<#elseif balanceBill.balanceType==4>
									促销结算
								</#if>
							</#if>
							)</span></li>
							</ul>
						<div class="tab_content">
						<!--列表start-->
						<table class="list_table">
						<thead>
						<tr>

						<th width="130">活动名称</th>
						<th>活动状态</th>
						<th>活动开始时间</th>
						<th>活动结束时间</th>
						<th>参与订单数</th>
						<th>参与产品数</th>
						<th>最终优惠金额</th>
						<th>商家承担优惠比例</th>
						<th>结算金额</th>
						<th>操作</th>
						</tr>

						</thead>
						<tbody>
						<#if psementList??>
								<#list psementList as psement>
									<tr>
										<td style="text-align:left;">
										<div style="width:130x;">
											 ${psement.activeName?default('')}
										</div>
										</td>
										<td>结束</td>
										<td>${psement.activeStartDate?default('')}</td>
										<td>${psement.activeEndDate?default('')}</td>
										<td>${psement.orderCount?default('')}</td>
										<td>${psement.productCount?default('')}</td>
										<td>￥${psement.preAmount?default('')}</td>
										<td>${psement.preRate?default('0.00')}%</td>
										<td>￥${psement.settlementAmount?default('')}</td>
										<td><a href="javascript:void(0)" onclick="infoDetail('${psement.activeName?default('')}','${psement.supplierName?default('')}','${psement.supplierCode?default('')}')">详情</a></td>
										</tr>
								</#list>
						</#if>
						</tbody>
						</table>
					<!--列表end-->
						<!--分页start-->
						<div class="page_box">
							<#if pageFinder ??>
								<#import "/manage/widget/common.ftl" as page>
								<@page.queryForm formId="cxDetailForm"/>
							</#if>
						</div>
						<!--分页end-->
					</div>
				</div>
			</div>
		
			 
 </div>
	
	</div>
</body>
</html>