<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-促销结算明细</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>

<script type="text/javascript">
	
</script>
</head>
<body>
	
	
	<div class="main_container">

		

			<div class="normal_box">

				<p class="title site">当前位置：商家中心 &gt; 财务 &gt; 促销结算明细</p>

					<!--搜索start-->

					<div class="search_box">

					<form action="${BasePath}/finance/balancebill/q_detail.sc" id="prodInforForm" name="prodInforForm" method="post">

						<p>
						<input type="hidden" name="supplierName" value="${supplierName?default('')}" />
						<input type="hidden" name="supplierCode" value="${supplierCode?default('')}" />
						<input type="hidden" name="activeName" value="${activeName?default('')}" />
						<span><label>货品编号：</label><input type="text" name="proNo" id="proNo"  class="inputtxt" value="${proNo?default('')}" /></span>

						<span><label>商品名称：</label><input type="text" name="proName" id="proName"  class="inputtxt" value="${proName?default('')}" /></span>

						<span><a class="button" id="mySubmit"><span onclick="$('#prodInforForm').submit()">搜索</span></a></span>

						</p>

					</form>

					</div>

					<div class="tab_panel">

							<ul class="tab">

							<li class="curr"><span>促销结算明细</span></li>

							</ul>

						<div class="tab_content">

						<!--列表start-->

						<table class="list_table">

						<thead>

						<tr>

						<th>订单号</th>

						<th>货品编号</th>

						<th>货品名称</th>

						<th>商品规格</th>

						<th>销售数量</th>

						<th>退拒数量</th>

						<th>活动优惠金额</th>

						<th>退拒冲抵金额</th>

						<th>最终优惠金额</th>

						</tr>

						<tr>

						<td colspan="9"  style="padding:0;text-align:left;">

						<div class="tb_dobox">

							<div class="dobox">

								<span>活动名称：${activeName?default('')}</span>

							</div>

							<div class="page">

								<a class="prevdis" href="javascript:;">&nbsp;</a>

								<a class="next" href="javascript:gotoPage(2);void(0)" >下一页</a>

							</div>

						</div>

						</td>

						</tr>

						</thead>

						<tbody>

						<#if proInforList??>
								<#list proInforList as proInfor>
									<tr>

										<td>${proInfor.orderNo?default('')}</td>
				
										<td>${proInfor.productNo?default('')}</td>
				
										<td>${proInfor.productName?default('')}</td>
				
										<td>${proInfor.specification?default('')}</td>
				
										<td>${proInfor.saleQuantity?default('')}</td>
				
										<td>${proInfor.reQuantity?default('')}</td>
				
										<td>￥${proInfor.actPreferentialAmount?default('')}</td>
				
										<td>￥${proInfor.reTakeoutAmount?default('')}</td>
				
										<td>￥${proInfor.lastPreferentialAmount?default('')}</td>
			
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
								<@page.queryForm formId="prodInforForm"/>
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