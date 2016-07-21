<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--#setting classic_compatible=true-->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-结算单详情</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>

<script type="text/javascript">
	
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
				<span>结算周期：${balanceBill.balanceStartDate?string('yyyy-MM-dd')}  至   ${balanceBill.balanceEndDate?string('yyyy-MM-dd')}</span>
				<span>销售金额：<strong class="f_hl">￥${balanceBill.balanceMoney?default('0.00')?string('##0.00')}</strong></span>
				</p>
				<div class="clear"></div>
				</div>
				<!--详情end-->
				<hr class="line" />
				
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
							<li class="tab_fr">
						           <a class="button" onclick="history.back();" ><span>返回</span></a>
					        </li>
							</ul>
						<div class="tab_content">
						
						
						<!--搜索start-->
				<div class="search_box">
					<form action="${BasePath}/finance/balancebill/detail.sc" name="reDetailForm" id="reDetailForm" method="post">
					<input type="hidden" name="id" id="id"  class="inputtxt" value="${id?default('')}"/>
					<p>
					<!--<span><label>货品条码：</label><input type="text" name="insideCode" id="insideCode"  class="inputtxt" value="${recruitDetail.insideCode?default('')}"/></span>-->
					<span><label style="width:120px;">商家款色编码：</label><input type="text" name="styleNo" id="styleNo"  class="inputtxt" value="${recruitDetail.styleNo?default('')}" /></span>
					<span><label>货品名称：</label><input type="text" name="proName" id="proName"  class="inputtxt" value="${recruitDetail.proName?default('')}" /></span>
					<span><label>货品编码：</label><input type="text" name="proNo" id="proNo"  class="inputtxt" value="${recruitDetail.proNo?default('')}" /></span>
					<span><a class="button" id="mySubmit"><span onclick="$('#reDetailForm').submit()">搜索</span></a></span>
					</p>
					</form>
				</div>
				<!--搜索end-->
						
						<!--列表start-->
						<table class="list_table">
						<thead>
						<tr>
						<th>订单号</th>
						<th>货品条码</th>
						<th>商家<br />款色编码</th>
						<th width="200">商品名称</th>
						<th width="70">备货时间</th>
						<th width="35">备货数量</th>
						<th width="35">退货数量</th>
						<th width="35">拒收数量</th>
						<#if balanceBill.balanceType??>
							<#if balanceBill.balanceType==1>
									<th width="45">协议价</th>
								<#elseif balanceBill.balanceType==2>
									<th width="45">吊牌价</th>
									<th width="45">配折率</th>
								<#elseif balanceBill.balanceType==3>
									<th width="45">优购价</th>
									<th width="45">倒扣率</th>
							</#if>
						</#if>
						<th width="60">使用优惠券<br />金额</th>
						<th width="60">优惠券<br />分摊比例</th>
						<th width="60">本次<br />结算金额</th>
						</tr>
						</thead>
						<tbody>
						<#if recruitDetailList??>
								<#list recruitDetailList as recruitDetail>
									<tr>
									<td>${recruitDetail.orderSubNo?default('')}</td>
									<td>${recruitDetail.insideCode?default('')}</td>
									<td>${recruitDetail.styleNo?default('')}</td>
									<td>
									
									${recruitDetail.proName?default('')}
									
									</td>
									<td>${recruitDetail.consignmentTime?string('yyyy-MM-dd HH:mm:ss')}</td>
									<td>${recruitDetail.consignmentAmount}</td>
									<td>${recruitDetail.returnrejectAmount}</td>
									<td>${recruitDetail.rejectAmount}</td>
									<#if balanceBill.balanceType??>
										<#if balanceBill.balanceType==1>
												<td>￥${recruitDetail.fixedPrice?default('')}</td>
											<#elseif balanceBill.balanceType==2>
												<td>￥${recruitDetail.originalPrice?default('')}</td>
												<td>${recruitDetail.discountRate?default('')}%</td>
											<#elseif balanceBill.balanceType==3>
												<td>￥${recruitDetail.yougouPrice?default('')}</td>
												<td>${recruitDetail.converseKeapRatio?default('')}%</td>
										</#if>
									</#if>
									<td>￥${recruitDetail.useCouponMoney?default('')}</td>
									<td>${recruitDetail.couponsCationProportion?default('')}%</td>
									<td>￥${recruitDetail.balanceMoney?default('')}</td>
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
							<@page.queryForm formId="reDetailForm"/>
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