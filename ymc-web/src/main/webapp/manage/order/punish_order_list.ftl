<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-我的违规订单</title>
<#include "/manage/order/orderprint_js_import.ftl" />
</head>

<body>

	<div class="main_container">
			<div class="normal_box">
				<p class="title site">当前位置：商家中心 &gt; 订单 &gt; 我的违规订单<!--选项卡--></p>
				<div class="tab_panel">
					<ul class="tab">
						<li class="curr"><span>我的违规订单</span></li>
						<li onclick="location.href='${BasePath}/order/queryPunishOrderCommodityList.sc'"><span>我的缺货商品</span></li>
						<li class="tab_fr"><!-- onclick="doExportAllSalesDetail('queryAllSales.sc')"-->
							<a class="button" onclick="order.print.doExportPunishOrder()"><span>导出违规订单</span></a>
						</li>
					</ul>
					<div class="tab_content">
					
				<!--搜索start-->
				<div class="search_box">
					<form name="queryVoform" id="queryVoform" action="queryPunishOrderList.sc" method="post">
						<p>
							<span>
								<label>优购订单号：</label>
								<input type="text" name="orderNo" id="orderNo" value="${(orderPunish.orderNo)!'' }"class="inputtxt" />
							</span>
							<span>
								<label>外部订单号：</label>
							    <input type="text" name="thirdOrderNo" id="thirdOrderNo" value="${(orderPunish.thirdOrderNo)!'' }" class="inputtxt" />
							</span>
							<span>
								<label>扣款状态：</label>
			                   	<select name="isSettle" id="isSettle">
			                        <option value="">全部</option>
			                        <option value="1" <#if (orderPunish.isSettle)?? && orderPunish.isSettle == "1">selected</#if>  >已扣款</option>
			                        <option value="0" <#if (orderPunish.isSettle)?? && orderPunish.isSettle == "0">selected</#if> >未扣款</option>
			                    </select>
							</span>
							<span>
								<label>结算单号：</label>
							    <input type="text" name="settleOrderNo" id="settleOrderNo" value="${(orderPunish.settleOrderNo)!'' }" class="inputtxt" />
							</span>
						</p>
						<p>
							<span>
								<label>违规类型：</label>
			                   	<select name="punishType" id="punishType">
			                        <option value="">全部</option>
			                        <option value="1" <#if (orderPunish.punishType)?? && orderPunish.punishType == "1">selected</#if>  >超时效</option>
			                        <option value="0" <#if (orderPunish.punishType)?? && orderPunish.punishType == "0">selected</#if> >缺货</option>
			                    </select>
							</span>
							<span>
								<label>下单时间：</label>
								<input type="text" style="width:100px;" name="orderTimeStart" id="orderTimeStart" value="<#if (orderPunish.orderTimeStart)??>${(orderPunish.orderTimeStart)?datetime }</#if>" class="inputtxt" style="width:80px;" /> 至
								<input type="text" style="width:100px;" name="orderTimeEnd" id="orderTimeEnd" value="<#if (orderPunish.orderTimeEnd)??>${(orderPunish.orderTimeEnd)?datetime }</#if>" class="inputtxt" style="width:80px;" />
							</span>
							<span>
								<a class="button" id="mySubmit">
									<span onclick="$('#queryVoform').submit()">搜索</span>
								</a>
							</span>
						</p>
					</form>
				</div>
				<!--搜索end-->
						<!--列表start-->
						<table class="list_table">
							<thead>
								<tr>
									<th><input type="checkbox" id="checkboxall" onclick="javascript:$('#tbody :checkbox').attr('checked', this.checked);"/></th>
									<th width="120">优购订单号</th>
									<th width="100">外部订单号</th>
									<th width="120">下单日期</th>
									<th width="60">扣款状态</th>
									<th width="120">结算单号</th>
									<th width="140">结算周期</th>
									<th width="60">订单金额</th>
									<th width="60">违规类型</th>
									<th width="80">超出时长</th>
									<th width="60">罚款金额</th>
									
								</tr>
							</thead>
							<tbody id="tbody">
								<#if pageFinder??&&pageFinder.data??>
									<#list pageFinder.data as item>
										<tr>
											<td>
												<label>
													<input type="checkbox" no="${item.orderNo!''}" value="${(item.orderNo)!''}" />&nbsp;
												</label>
											</td>
											<td>${(item.orderNo)!''}</td>
											<td>${(item.thirdOrderNo)!''}</td>
											<td>${(item.orderTime)!''}</td>
											<td>
												<#if (item.isSettle)?? && (item.isSettle) == '1'>
													已扣款
												<#else>
													未扣款
												</#if>
											</td>
											<td>${(item.settleOrderNo)!''}</td>
											<td>${(item.settleCycle)!''}</td>
											<td>${(item.orderPrice)!''}</td>
											<td>
												<#if (item.punishType)?? && (item.punishType) == '1'>
													超时效
												<#else>
													缺货
												</#if>
											</td>
											<td>
												<#if (overHour)?? && (item.punishType)?? && (item.punishType) == '1'>
					                        		${(overHour[item.orderNo])!''}
					                        	<#else>
					                        		--
					                        	</#if>
											</td>
											<td>${(item.punishPrice)!''}</td>
										</tr>
									</#list>
								<#else>
									<tr>
										<td colspan="11" class="td-no">没有相关记录！</td>
									</tr>
								</#if>
							</tbody>
						</table>
					<!--列表end-->
					<#if pageFinder??&&pageFinder.data??>
					<!--分页start-->
					<div class="page_box">
						<#if pageFinder ??>
							<#import "${BasePath}/manage/widget/common.ftl" as page>
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
<script>
$(document).ready(function(){
	order.print.BasePath = '${BasePath}';
	$('#orderTimeStart').calendar({maxDate: '#orderTimeStart', format: 'yyyy-MM-dd HH:mm:ss'});
	$('#orderTimeEnd').calendar({minDate: '#orderTimeEnd', format: 'yyyy-MM-dd HH:mm:ss'});
}); 
</script>