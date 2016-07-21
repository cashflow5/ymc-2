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
						<li onclick="location.href='${BasePath}/order/queryPunishOrderList.sc'"><span>我的违规订单</span></li>
						<li class="curr"><span>我的缺货商品</span></li>
					</ul>
					<div class="tab_content">
					
				<!--搜索start-->
				<div class="search_box">
					<form name="queryVoform" id="queryVoform" action="${BasePath}/order/queryPunishOrderCommodityList.sc" method="post">
						<p>
							<span>
								<label>订单号：</label>
								<input type="text" name="orderSubNo" id="orderSubNo" value="${(orderPunish.orderSubNo)!'' }"class="inputtxt" />
							</span>
							<span>
								<label>商品编码：</label>
							    <input type="text" name="commodityNo" id="commodityNo" value="${(orderPunish.commodityNo)!'' }" class="inputtxt" />
							</span>
							<span>
								<label>品牌名称：</label>
			                   	<select name="brandNo" id="brandNo">
			                        <option value="">全部</option>
			                        <#if lstBrand??>
										<#list lstBrand as item>
											<option value="${(item.brandNo)!""}" brandId="${(item.id)!''}" <#if orderPunish.brandNo??&&orderPunish.brandNo==(item.brandNo)!''>selected="selected"</#if> >${(item.brandName)!""}</option>
										</#list>
									</#if>
			                    </select>
							</span>
							<span>
								<label>款色编码：</label>
							    <input type="text" name="styleNo" id="styleNo" value="${(orderPunish.styleNo)!'' }" class="inputtxt" />
							</span>
						</p>
						<p>
							<span>
								<label>货品条码：</label>
								<input type="text" name="levelCode" id="levelCode" value="${(orderPunish.levelCode)!'' }" class="inputtxt" />
							</span>
							<span>
								<label>缺货时间：</label>
								<input type="text" style="width:100px;" name="backorderDateBegin" id="backorderDateBegin" value="<#if (orderPunish.backorderDateBegin)??>${orderPunish.backorderDateBegin!'' }</#if>" class="inputtxt" style="width:80px;" /> 至
								<input type="text" style="width:100px;" name="backorderDateEnd" id="backorderDateEnd" value="<#if (orderPunish.backorderDateEnd)??>${orderPunish.backorderDateEnd!'' }</#if>" class="inputtxt" style="width:80px;" />
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
								    <th></th>
									<th>商品名称</th>
									<th width="150">缺货时间</th>
									<th width="100">订单号</th>
									<th width="70">商品编码</th>
									<th width="110">款色编码</th>
									<th width="130">货品条码</th>
									<th width="70">缺货数量</th>
								</tr>
							</thead>
							<tbody id="tbody">
								<#if pageFinder??&&pageFinder.data??>
									<#list pageFinder.data as item>
										<tr>
										    <td><img width="40px;" height="40px;" src="${(item.picSmall)!''}"></td>
										    <td style="text-align: left;"><a href="${item.commodityURL!''}" target="_blank">${(item.prodName)!''}</a></td>
											<td><#if item.backorderDate??>${(item.backorderDate)?datetime}</#if></td>
											<td>${(item.orderSubNo)!''}</td>
											<td>${(item.commodityNo)!''}</td>
											<td>${(item.styleNo)!''}</td>
											<td>${(item.levelCode)!''}</td>
											<td>${(item.commodityCount)!''}</td>
										</tr>
									</#list>
								<#else>
									<tr>
										<td colspan="8" class="td-no">没有相关记录！</td>
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
	$('#backorderDateBegin').calendar({format: 'yyyy-MM-dd HH:mm:ss'});
	$('#backorderDateEnd').calendar({format: 'yyyy-MM-dd HH:mm:ss'});
}); 
</script>