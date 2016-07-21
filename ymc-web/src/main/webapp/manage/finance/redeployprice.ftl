<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-调价单查询</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>

<script type="text/javascript">
	
</script>
</head>
<body>
	
	
	<div class="main_container">
		
			<div class="normal_box">
				<p class="title site">当前位置：商家中心 &gt; 财务 &gt; 调价单查询</p>
					<div class="tab_panel">
							<ul class="tab">
							<li class="curr"><span>调价单查询列表</span></li>
							</ul>
						<div class="tab_content">
						
				<!--搜索start-->
				<div class="search_box">
					<form action="" method="post" id="queryVoform" action="${BasePath}/finance/redeployprice/queryAll.sc">
					<p>
					<span><label style="width:100px;">调价单单编号：</label><input type="text" name="billNumber" id="billNumber" value="${redeployPrice.billNumber?default('')}"  class="inputtxt" style="width:188px;" /></span>
					<span><label style="width:100px;">商品编码：</label><input type="text" name="commodityNo" id="commodityNo" value="${redeployPrice.commodityNo?default('')}"  class="inputtxt" style="width:100px;" /></span>
					<span><label>结算类型：</label>
					<select id="type" name="type" style="width:130px;">
						<option value="">请选择</option>
						<option  value="1" <#if redeployPrice.type??><#if redeployPrice.type=1>selected="selected"</#if></#if> >底价结算</option>
						<option  value="2" <#if redeployPrice.type??><#if redeployPrice.type=2>selected="selected"</#if></#if>>配折结算</option>
						<option  value="3" <#if redeployPrice.type??><#if redeployPrice.type=3>selected="selected"</#if></#if>>扣点结算</option>
					</select>
					</span>
					<span><a class="button" id="mySubmit"><span onclick="$('#queryVoform').submit()">搜索</span></a></span>
					</p>
					</form>
				</div>
				<!--搜索end-->
						
						<!--列表start-->
						<table class="list_table">
						<thead>
						<tr>
						<th>调价单编号</th>
						<th>商品编码</th>
						<th>商品名称</th>
						<th>结算方式</th>
						<th>协议价</th>
						<th>吊牌价</th>
						<th>配折率</th>
						<th>优购价</th>
						<th>倒扣率</th>
						<th>开始时间</th>
						<th>截止时间</th>
						</tr>
						</thead>
						<tbody>
							<#if redeployPriceList??>
								<#list redeployPriceList as redeployPrice>
									<tr class="tr_detail" >
										<td>${redeployPrice.billNumber?default('')}</td>
										<td>${redeployPrice.commodityNo?default('')}</td>
										<td>${redeployPrice.commodityName?default('')}</td>
										<td>
											<#if redeployPrice.type??&&redeployPrice.type==1>
													<span>底价结算</span>
											<#elseif redeployPrice.type??&&redeployPrice.type==2>
													<span>配折结算</span>
											<#elseif redeployPrice.type??&&redeployPrice.type==3>
													<span>扣点结算</span>
											</#if>
										</td>
										<td>
											<#if redeployPrice.fixedPrice??>
												<#if redeployPrice.fixedPrice!=0.0>
													￥${redeployPrice.fixedPrice}
													<#else>
													-
												</#if>
											</#if>
										</td>
										<td>
											<#if redeployPrice.originalPrice??>
												<#if redeployPrice.originalPrice!=0.0>
													￥${redeployPrice.originalPrice}
													<#else>
													-
												</#if>
											</#if>
										</td>
										<td>
											<#if redeployPrice.discountRate??>
												<#if redeployPrice.discountRate!=0.0>
													${redeployPrice.discountRate}%
													<#else>
													-
												</#if>
											</#if>
										</td>
										<td>
											<#if redeployPrice.yougouPrice??>
												<#if redeployPrice.yougouPrice!=0.0>
													￥${redeployPrice.yougouPrice}
													<#else>
													-
												</#if>
											</#if>
										</td>
										<td>
											<#if redeployPrice.converseKeapRatio??>
												<#if redeployPrice.converseKeapRatio!=0.0>
													${redeployPrice.converseKeapRatio}%
													<#else>
													-
												</#if>
											</#if>
										</td>
										<td>
											<#if redeployPrice.priceStartTime??>${redeployPrice.priceStartTime?string("yyyy-MM-dd")}</#if>
										</td>
										<td>
											<#if redeployPrice.priceEndTime??>${redeployPrice.priceEndTime?string("yyyy-MM-dd")}</#if>
										</td>
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
							<@page.queryForm formId="queryVoform"/>
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