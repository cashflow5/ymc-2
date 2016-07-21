<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-单据打印（缺货）</title>
</head>
<script>
	var basePath = "${BasePath}";
</script>
<body>
	
	
	<div class="main_container">
		<div class="normal_box">
			<p class="title site">当前位置：商家中心 &gt; 订单 &gt; 单据打印</p>
			<div class="tab_panel  relative">
			<p style="position:absolute;top:0;right:0;"><span class="fl icon_info"></span><span class="fl ml5" style="color:#999;">默认显示30天的订单，可设置条件查询更多订单，缺货订单请不要发货</span></p>
			    <ul class="tab">
					<li onclick="location.href='${BasePath}/order/toDocumentPrinting.sc'" ><span>未打印</span></li>
					<li onclick="location.href='${BasePath}/order/toPrintNotDelivery.sc'"   ><span>已打印未发货</span></li>
					<li onclick="location.href='${BasePath}/order/toPrintDelivery.sc'" ><span>已发货</span></li>
					<li onclick="location.href='${BasePath}/order/toPrintOutstock.sc'"  class="curr" ><span>缺货订单</span></li>
					<li onclick="location.href='${BasePath}/order/toPrintAll.sc'" > <span>全部</span></li>
				</ul>
				<div class="tab_content"> 
					<!--搜索start-->
					<div class="search_box">
						<form action="toPrintOutstock.sc" id="queryVoform" name="queryVoform" method="post">
							<!--是否一单一货-->
							<input type="hidden" id="singleCargo" name="singleCargo" value="" />
							<!--标识页面是否第一次进入-->
							<input type="hidden" id="tabNum" name="tabNum" value="1" />
							<!--选项卡页面标识-->
							<input type="hidden" id="flag" name="flag" value="${flag!''}" />
							<p>
								<span>
									<label style="width:110px;">订单号：</label>
									<input type="text" name="orderSubNo" id="orderSubNo" class="inputtxt" value="${orderSubExpand.orderSubNo!''}" />
								</span>
								<span>
									<label style="width:110px;">原始订单号：</label>
									<input type="text" name="originalOrderNo" id="originalOrderNo" class="inputtxt" value="${orderSubExpand.originalOrderNo!''}" />
								</span>
								<span>
									<label style="width:110px;">下单时间：</label>
									<input type="text" name="timeStart" id="startTime" value="${orderSubExpand.timeStart!''}" class="inputtxt" style="width:80px;" /> 至
								<input type="text" name="timeEnd" id="endTime" value="${orderSubExpand.timeEnd!''}" class="inputtxt" style="width:80px;" />
								</span>
							</p>
							<p>
								<span>
									<label style="width:110px;">商家款色编码：</label>
									<input type="text" name="suppliersColorModelsCode" id="suppliersColorModelsCode"  value="${orderSubExpand.suppliersColorModelsCode!''}" class="inputtxt" />
								</span>
								<span>
									<label style="width:110px;">缺货时间：</label>
									<input type="text" name="timeStartOutStock" id="timeStartOutStock"  value="${orderSubExpand.timeStartOutStock!''}" class="inputtxt" style="width:80px;" /> 
									至 <input type="text" name="timeEndOutStock" id="timeEndOutStock"  value="${orderSubExpand.timeEndOutStock!''}" class="inputtxt" style="width:80px;" />
								</span>
								<span style="padding-left:50px;">
									<a class="button" id="mySubmit" onclick="mysubmit()"><span>搜索</span></a>
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
								<th>下单时间</th>
								<th>订单号</th>
								<!--th>原始订单号</th-->
								<th>订单来源</th>
								<th>商品数量</th>
								<th>订单金额</th>
								<th>优惠金额</th>
								<th>礼品卡冲抵金额</th>
								<th>运费金额</th>
								<th>缺货货时间</th>
								<th>打印次数</th>
								<th>订单状态</th>
								<th>订单备注</th>
							</tr>
							<#if pageFinder??&&pageFinder.data??>
								<tr class="do_tr">
									<td colspan="13" style="padding:0;text-align:left;">
										<div class="tb_dobox">
											<div class="dobox">
												<label><input class="chkall" type="checkbox" /> 全选</label>
												<a href="javascript:;" onclick="order.print.doExportOutStockOrderOld()">导出缺货订单</a> 
												&nbsp;&nbsp;&nbsp;
											</div>
											<div class="page"> 
												<#if pageFinder ??>
													<#import "/manage/widget/page.ftl" as page>
													<@page.queryForm formId="queryVoform"/>
												</#if>
											</div>
										</div>
									</td>
								</tr>
							</#if>
						</thead>
						<tbody id="tbody">
							<#if pageFinder??&&pageFinder.data??>
								<#list pageFinder.data as item>
									<!--全选操作部分-->
									<tr>
										<td>
											<label>
												<input type="checkbox" no="${item.orderSubNo!''}" value="${item.orderSubId!''}" />&nbsp;
											</label>
										</td>
										<td>${item.createTime?string("yyyy-MM-dd HH:mm:ss")}</td>
										<td><a href="javascript:;" onclick="javascript:order.print.toDetail('${item.orderSubNo!''}','MENU_DJDY')">${item.orderSubNo!''}</a><#if item.originalOrderNo??&&item.originalOrderNo!=''><#if item.orderSubNo?substring(item.orderSubNo?index_of("_")+1)?number gt 200>[补]<#else>[换]</#if></#if></td>
										<#--td>${item.originalOrderNo!''}</td-->
										<td>${item.orderSourceName!''}</td>
										<td>${item.prodCount!''}</td>
										<td>${(item.orderPayTotalAmont)?string('#.##')}</td>
										<td>${item.discountAmount!'0'}</td>
										<td>${item.couponPrefAmount!'0'}</td>
										<td>${item.actualPostage!'0'}</td>
										<td><#if item.backorderDate??>${item.backorderDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
										<td style="text-align:left;">购物清单${item.printShoppinglistCount?default(0)}次<br />快递单${item.printLogisticslistCount?default(0)}次</td>
										<td>${item.baseStatusDesc!''}</td>
										<td class="rel" <#if (item.orderSubNo)??>id="${item.orderSubNo!''}"</#if> ><!--备注订单（备注小红旗） -->
                                            <div class="flag" ></div>
                                            <div class="flag_tip hide"></div>
                                            <input type="hidden" class="markColor" />
                                            <input type="hidden" class="markNote" />
                                            <input type="hidden" class="orderSubNo" value="${item.orderSubNo!''}"/>
                                        </td>
									</tr>
								</#list>
							<#else>
								<tr class="do_tr">  <!--do_tr 这行客户端会自动显示隐藏-->
									<td class="td-no" colspan="13">
											没有相关数据
									</td>
								</tr>
							</#if>	
						</tbody>
					</table>
					<!--列表end--> 
					<#if pageFinder??&&pageFinder.data??>
					<!--分页start-->
					<div class="page_box">
						<div class="dobox">
						</div>
						<#if pageFinder ??>
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
	<#include "/manage/order/orderprint_js_import.ftl" />
	<script type="text/javascript">
		order.print.BasePath = '${BasePath}';
		$("#startTime").calendar({maxDate:'#endTime',diffDate:30});
		$("#endTime").calendar({minDate:'#startTime',diffDate:30});
		$("#timeStartOutStock").calendar({maxDate:'#timeEndOutStock'});
		$("#timeEndOutStock").calendar({minDate:'#timeStartOutStock'});
		
		//提交表单搜索
		function mysubmit(){
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
			if(!order.checkDate()){
				return false;
			}
			$("#queryVoform").submit();
		}
	</script>
	<script type="text/javascript" src="${BasePath}/yougou/js/manage/order/order_common.js"></script><!-- for 订单备注功能 -->
</body>
</html>
