<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-单据打印（全部）</title>
</head>
<script>
	var basePath = "${BasePath}";
</script>
<body>
	
	
	<div class="main_container">
		<div class="normal_box">
			<p class="title site">当前位置：商家中心 &gt; 订单 &gt; 单据打印(新)</p>
			<div class="tab_panel  relative">
			<p style="position:absolute;top:0;right:0;"><span class="fl icon_info"></span><span class="fl ml5" style="color:#999;">默认显示30天的订单，可设置条件查询更多订单，缺货订单请不要发货</span></p>
			    <!-- 单据打印导航tab模版引入 -->
			     <ul class="tab">
					<li onclick="location.href='${BasePath}/order/toDocumentPrintingNew.sc'"  ><span>未打印</span></li>
					<li onclick="location.href='${BasePath}/order/toPrintNewNotDelivery.sc'" ><span>已打印未发货</span></li>
					<li onclick="location.href='${BasePath}/order/toPrintNewDelivery.sc'"  ><span>已发货</span></li>
					<li onclick="location.href='${BasePath}/order/toPrintNewOutstock.sc'"  ><span>缺货订单</span></li>
					<li onclick="location.href='${BasePath}/order/toPrintNewAll.sc'"  class="curr" > <span>全部</span></li>
				</ul>
				<div class="tab_content"> 
					<!--搜索start-->
					<div class="search_box">
						<form action="toPrintNewAll.sc" id="queryVoform" name="queryVoform" method="post">
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
									<label style="width:110px;">物流公司：</label>
									<input type="text" name="logisticsName" id="logisticsName" class="inputtxt" value="${orderSubExpand.logisticsName!''}" />
								</span>
								<span>
									<label style="width:110px;">商家款色编码：</label>
									<input type="text" name="suppliersColorModelsCode" id="suppliersColorModelsCode"  value="${orderSubExpand.suppliersColorModelsCode!''}" class="inputtxt" />
								</span>
								<span>
									<label style="width:110px;">商家货品条码：</label>
									<input type="text" name="thirdPartyCode" id="thirdPartyCode"  value="${orderSubExpand.thirdPartyCode!''}" class="inputtxt" />
								</span>
							</p>
							<p>
								<span>
									<label style="width:110px;">快递单号：</label>
									<input type="text" name="expressOrderId" id="expressOrderId" value="${orderSubExpand.expressOrderId!''}"  class="inputtxt" />
								</span>
								<span>
									<label style="width:110px;">发货时间：</label>
									<input type="text" name="timeStartShipTime" id="timeStartShipTime"  value="${orderSubExpand.timeStartShipTime!''}" class="inputtxt" style="width:80px;" /> 
									至 <input type="text" name="timeEndShipTime" id="timeEndShipTime" value="${orderSubExpand.timeEndShipTime!''}"  class="inputtxt" style="width:80px;" />
								</span>
								<span>
									<label>订单状态：</label>
									<select class="g-select" id="orderStatus" name="orderStatus">
									    <option value="">请选择</option>
									    <#list orderStatusMap?keys as itemKey>
									    	<option value="${itemKey}" <#if itemKey == (orderSubExpand.orderStatus)?default(-1)?string>selected="selected"</#if>>${orderStatusMap[itemKey]}</option>
									    </#list>
									</select>
								</span>
							</p>
							<p>
								<span>
									<label style="width:110px;">收货人手机：</label>
									<input type="text" name="consigneeMobile" id="consigneeMobile"  value="${(orderSubExpand.consigneeMobile)!''}" class="inputtxt" />
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
								<th>下单时间</th>
								<th>订单号</th>
								<!--th>原始订单号</th-->
								<th>订单来源</th>
								<th>商品数量</th>
								<th>订单金额</th>
								<th>优惠金额</th>
								<th>礼品卡冲抵金额</th>
								<th>运费金额</th>
								<th>收货人姓名</th>
								<th>收货人电话</th>
								<th>物流公司</th>
								<th>快递单号</th>
								<th>发货时间</th>
								<th>订单状态</th>
								<th width="90">打印次数</th>
								<th>订单备注</th>
							</tr>
						</thead>
						<tbody>
							<#if pageFinder??&&pageFinder.data??>
								<#list pageFinder.data as item>
									<!--全选操作部分-->
									<tr>
										<td style="width:60px;">${item.createTime?string("yyyy-MM-dd HH:mm:ss")}</td>
										<td><#if (item.isException??)&&item.isException!=0>[异常]</#if><a href="javascript:;" onclick="javascript:order.print.toDetail('${item.orderSubNo!''}','MENU_DJDYX')">${item.orderSubNo!''}</a><#if item.originalOrderNo??&&item.originalOrderNo!=''><#if item.orderSubNo?substring(item.orderSubNo?index_of("_")+1)?number gt 200>[补]<#else>[换]</#if></#if></td>
										<#--td>${item.originalOrderNo!''}</td-->
										<td>${item.orderSourceName!''}</td>
										<td>${item.prodCount!''}</td>
										<td>${(item.orderPayTotalAmont)?string('#.##')}</td>
										<td>${item.discountAmount!'0'}</td>
										<td>${item.couponPrefAmount!'0'}</td>
										<td>${item.actualPostage!'0'}</td>
										<td><#if item.userName??>${item.userName}</#if></td>
										<td><#if item.consigneeMobile??>${item.consigneeMobile}</#if></td>
										<td>${item.logisticsName!''}</td>
										<td>${item.expressOrderId!''}</td>
										<td style="width:60px;"><#if item.shipTime??>${item.shipTime?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
										<td>${item.baseStatusDesc!''}</td>
										<td style="text-align:left;">购物清单${item.printShoppinglistCount?default(0)}次<br />快递单${item.printLogisticslistCount?default(0)}次</td>
									
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
									<td class="td-no" colspan="16">
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
		$("#timeStartShipTime").calendar({maxDate:'#timeEndShipTime'});
		$("#timeEndShipTime").calendar({minDate:'#timeStartShipTime'});
		
		//提交表单搜索
		function mysubmit(){
			//验证是否至少输入一个条件
			//if($("#orderSubNo").val()==""&&$("#startTime").val()==""&&$("#endTime").val()==""&&$("#logisticsName").val()==""&&$("#suppliersColorModelsCode").val()==""&&$("#thirdPartyCode").val()==""&&$("#expressOrderId").val()==""&&$("#timeStartShipTime").val()==""&&$("#timeEndShipTime").val()==""&&$("#baseStatus").val()==""){
				//alert("请至少输入一项搜索条件");
				//return;
			//}
			if(!order.checkDate()){
				return false;
			}
			$("#queryVoform").submit();
		}
	</script>
	<script type="text/javascript" src="${BasePath}/yougou/js/manage/order/order_common.js"></script><!-- for 订单备注功能 -->
</body>
</html>
