<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><#include "/manage/widget/titlebar.ftl" /></title>
	<script type="text/javascript" src="${BasePath}/yougou/js/provinceCascade.js"></script>
	
</head>

<body>

	<div class="main_container">
		<div class="normal_box">
			<p class="title site">当前位置：商家中心 &gt; 订单 &gt; 快捷发货</p>
			<div class="tab_panel  relative">
			<p style="position:absolute;top:0;right:0;"><span class="fl icon_info"></span><span class="fl ml5" style="color:#999;">默认显示30天的订单，可设置条件查询更多订单，缺货订单请不要发货</span></p>
				<ul class="tab">
					<li onclick="javascript:location.href='${BasePath}/order/fastdelivery/notexported.sc';"><span>未导出</span></li>
					<li onclick="javascript:location.href='${BasePath}/order/fastdelivery/notdelivery.sc';"><span>已导出未发货</span></li>
					<li onclick="javascript:location.href='${BasePath}/order/fastdelivery/delivered.sc';"><span>已发货</span></li>
					<li onclick="javascript:location.href='${BasePath}/order/fastdelivery/outoforders.sc';"><span>缺货订单</span></li>
					<li onclick="javascript:location.href='${BasePath}/order/fastdelivery/allorders.sc';" class="curr"><span>全部</span></li>
				</ul>
				<div class="tab_content"> 
					<!--搜索start-->
					<div class="search_box">
						<form action="${BasePath}/order/fastdelivery/allorders.sc" id="queryVoform" name="queryVoform" method="post">
							<p>
								<span>
									<label style="width:110px;">订单号：</label>
									<input type="text" name="orderSubNo" id="orderSubNo" class="inputtxt" value="${(orderSubExpand.orderSubNo)!''}" />
								</span>
								<span>
									<label style="width:144px;">商家款色编码：</label>
									<input type="text" name="suppliersColorModelsCode" id="suppliersColorModelsCode"  value="${(orderSubExpand.suppliersColorModelsCode)!''}" class="inputtxt" />
								</span>
								<span>
									<label style="width:175px;">商家货品条码：</label>
									<input type="text" name="thirdPartyCode" id="thirdPartyCode"  value="${(orderSubExpand.thirdPartyCode)!''}" class="inputtxt" />
								</span>
							</p>
							<p>
								<span>
									<label style="width:110px;">快递单号：</label>
									<input type="text" name="expressCode" id="expressCode"  value="${(orderSubExpand.expressCode)!''}" class="inputtxt" />
								</span>
								<span>
									<label style="width:144px;">下单时间：</label>
									<input type="text" name="timeStart" id="startTime" value="${(orderSubExpand.timeStart)!''}" class="inputtxt" style="width:80px;" /> 至
									<input type="text" name="timeEnd" id="endTime" value="${(orderSubExpand.timeEnd)!''}" class="inputtxt" style="width:80px;" />
								</span>
								<span>
									<label style="width:110px;">物流公司：</label>
									<select class="g-select" id="logisticsCode" name="logisticsCode">
									    <option value="">请选择</option>
									    <#list logisticscompanList![] as item>
									    	<option value="${(item.logistic_company_code)!''}" <#if item.logistic_company_code == (orderSubExpand.logisticsCode)!''>selected="selected"</#if>>${(item.logistics_company_name)!''}</option>
									    </#list>
									</select>
								</span>
							</p>
							<p>
								<span>
									<label style="width:110px;">导出时间：</label>
									<input type="text" name="timeStartExport" id="timeStartExport" value="${(orderSubExpand.timeStartExport)!''}" class="inputtxt" style="width:80px;" /> 至
									<input type="text" name="timeEndExport" id="timeEndExport" value="${(orderSubExpand.timeEndExport)!''}" class="inputtxt" style="width:80px;" />
								</span>
								<span>
									<label style="width:80px;">发货时间：</label>
									<input type="text" name="timeStartShipTime" id="timeStartShipTime" value="${(orderSubExpand.timeStartShipTime)!''}" class="inputtxt" style="width:80px;" /> 至
									<input type="text" name="timeEndShipTime" id="timeEndShipTime" value="${(orderSubExpand.timeEndShipTime)!''}" class="inputtxt" style="width:80px;" />
								</span>
								<span>
									<label style="width:110px;">订单状态：</label>
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
								<span style="padding-left:20px;">
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
								<th width="13%">下单时间</th>
								<th width="15%">订单号</th>
								<th width="6%">订单金额</th>
								<th width="6%">优惠金额</th>
								<th width="6%">运费金额</th>
								<th width="8%">物流公司</th>
								<th width="10%">快递单号</th>
								<th width="13%">导出时间</th>
								<th width="13%">发货时间</th>
								<th>订单状态</th>
								<th>订单备注</th>
							</tr>
						</thead>
						<tbody id="tbody">
							<#if (pageFinder.data)?? && pageFinder.data?size != 0>
								<#list pageFinder.data as item>
									<!--全选操作部分-->
									<tr>
										<td><#if (item.createTime)??>${item.createTime?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
										<td>
											<#if (item.isException??)&&item.isException!=0>[异常]</#if><a href="javascript:;" onclick="javascript:order.print.toDetail('${item.orderSubNo!''}','MENU_KJFH');">${item.orderSubNo!''}</a><#if item.originalOrderNo??&&item.originalOrderNo!=''><#if item.orderSubNo?substring(item.orderSubNo?index_of("_")+1)?number gt 200>[补]<#else>[换]</#if></#if>
										</td>
										<td>${(item.orderPayTotalAmont)?string('#.##')}</td>
										<td>${(item.discountAmount)!'0'}</td>
										<td>${(item.actualPostage)!'0'}</td>
										<td>${(item.logisticsName)!''}</td>
										<td>${(item.expressOrderId)!''}</td>
										<td><#if (item.exportedDate)??>${(item.exportedDate)?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
										<td><#if (item.shipTime)??>${(item.shipTime)?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
										<td>${(item.baseStatusDesc)!''}</td>
										
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
									<td class="td-no" colspan="13">没有相关数据</td>
								</tr>
							</#if>	
						</tbody>
					</table>
					<!--列表end--> 
					
					<!--分页start-->
					<#if (pageFinder.data)??>
					<div class="page_box">
						<div class="dobox">
						</div>
							<#import "/manage/widget/common.ftl" as page>
							<@page.queryForm formId="queryVoform"/>
						</div>
					</#if>
					<!--分页end-->
				</div>
			</div>
		</div>
		
		
	</div>
	<#include "/manage/order/orderprint_js_import.ftl" />
	<script type="text/javascript">
		var basePath ='${BasePath}';//for 订单备注功能
		
		order.print.BasePath = '${BasePath}';
		$("#startTime").calendar({maxDate:'#endTime',diffDate:30});
		$("#endTime").calendar({minDate:'#startTime',diffDate:30});
		$('#timeStartExport').calendar({maxDate:'#timeEndExport'});
		$('#timeEndExport').calendar({minDate:'#timeStartExport'});
		$('#timeStartShipTime').calendar({maxDate:'#timeEndShipTime'});
		$('#timeEndShipTime').calendar({minDate:'#timeStartShipTime'});
		
		//提交表单搜索
		function mysubmit(){
			if(!order.checkDate()){
				return false;
			}
			$("#queryVoform").submit();
		}
		
	</script>
	<script type="text/javascript" src="${BasePath}/yougou/js/manage/order/order_common.js"></script><!-- for 订单备注功能 -->
</body>
</html>
