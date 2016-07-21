<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><#include "/manage/widget/titlebar.ftl" /></title>
	<script type="text/javascript" src="${BasePath}/yougou/js/provinceCascade.js"></script>
	<script type="text/javascript" src="${BasePath}/yougou/js/Lodop.js?${style_v}"></script>
	<script type="text/javascript" src="${BasePath}/yougou/js/ymc.common.js?${style_v}"></script>
	<script>
		var basePath = "${BasePath}";
	</script>
</head>

<body>
	
	
	<div class="main_container">
		<div class="normal_box">
			<p class="title site">当前位置：商家中心 &gt; 订单 &gt; 快捷发货</p>
			<div class="tab_panel  relative">
			<p style="position:absolute;top:0;right:0;"><span class="fl icon_info"></span><span class="fl ml5" style="color:#999;">默认显示30天的订单，可设置条件查询更多订单，缺货订单请不要发货</span></p>
				<ul class="tab">
					<li onclick="javascript:location.href='${BasePath}/order/fastdelivery/notexported.sc';" class="curr"><span>未导出</span></li>
					<li onclick="javascript:location.href='${BasePath}/order/fastdelivery/notdelivery.sc';"><span>已导出未发货</span></li>
					<li onclick="javascript:location.href='${BasePath}/order/fastdelivery/delivered.sc';"><span>已发货</span></li>
					<li onclick="javascript:location.href='${BasePath}/order/fastdelivery/outoforders.sc';"><span>缺货订单</span></li>
					<li onclick="javascript:location.href='${BasePath}/order/fastdelivery/allorders.sc';"><span>全部</span></li>
				</ul>
				<div class="tab_content"> 
					<!--搜索start-->
					<div class="search_box">
						<form action="${BasePath}/order/fastdelivery/notexported.sc" id="queryVoform" name="queryVoform" method="post">
							<p>
								<span>
									<label style="width:110px;">订单号：</label>
									<input type="text" name="orderSubNo" id="orderSubNo" class="inputtxt" value="${(orderSubExpand.orderSubNo)!''}" />
								</span>
								<span>
									<label style="width:110px;">商家款色编码：</label>
									<input type="text" name="suppliersColorModelsCode" id="suppliersColorModelsCode"  value="${(orderSubExpand.suppliersColorModelsCode)!''}" class="inputtxt" />
								</span>
								<span>
									<label style="width:110px;">商家货品条码：</label>
									<input type="text" name="thirdPartyCode" id="thirdPartyCode"  value="${(orderSubExpand.thirdPartyCode)!''}" class="inputtxt" />
								</span>
							</p>
							<p>
								<span>
									<label style="width:110px;">收货地区：</label>
									<select class="g-select" id="province" onchange="checkTwo();" onclick="checkTwo();" name="province" style="width:85px">
									    <option value="-1">请选择省份</option>
										 <#if areaList??>
										    <#list areaList as item>
										      <option value="${(item['no'])!''}" <#if item['name']??&&orderSubExpand.province??&&item['no']==orderSubExpand.province>selected="selected"</#if>>${(item['name'])!''}</option>
										    </#list>
										 </#if>
									</select>
									<select class="g-select" id="city" onchange="checkThree();" onclick="checkThree();" name="city" style="width:85px"></select>
									<select class="g-select" id="area" name="area" style="width:85px" ></select>
								</span>
								<span>
									<label>下单时间：</label>
									<input type="text" name="timeStart" id="startTime" value="${(orderSubExpand.timeStart)!''}" class="inputtxt" style="width:80px;" /> 至
								<input type="text" name="timeEnd" id="endTime" value="${(orderSubExpand.timeEnd)!''}" class="inputtxt" style="width:80px;" />
								</span>
								<span>
									<label><input name="singleCargo" type="checkbox" value="1" <#if (orderSubExpand.singleCargo)?? && orderSubExpand.singleCargo == '1'>checked="checked"</#if> />&nbsp;一单一货</label>
									<label title="勾选“合并发货”列表中筛选出下单时间为5天内，收货人姓名、地址、手机等信息一致的订单，可按照实际需要对这些订单进行合单发货操作。"><input name="mergerCargo" type="checkbox" value="1" <#if (orderSubExpand.mergerCargo)?? && orderSubExpand.mergerCargo == '1'>checked="checked"</#if>  />&nbsp;合并发货</label>
								</span>
								<span>
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
								<th>&nbsp;<input type="checkbox" id="checkboxall" onclick="javascript:$('#tbody :checkbox').attr('checked', this.checked);"/></th>
								<th width="10%">下单时间</th>
								<th width="15%">订单号</th>
								<th width="5%">订单金额</th>
								<th width="10%">收货人姓名</th>
								<th width="8%">联系手机</th>
								<th width="15%">收货地区</th>
								<th>收货地址</th>
								<th>订单备注</th>
								<th width="15%">操作</th>
							</tr>
							<#if (pageFinder.data)?? && pageFinder.data?size != 0>
								<tr class="do_tr">
									<td colspan="14" style="padding:0;text-align:left;">
										<div class="tb_dobox">
											<div class="dobox">
												<a href="javascript:;" onclick="javascript:doExportOrder();">导出拣货清单</a> 
												<a href="javascript:;" onclick="javascript:doExportOrderdetail();" >导出订单明细</a> 
												<a href="javascript:void(0);" onclick="ordernew.printBatch()">打印购物清单</a> 
												<a href="javascript:void(0);" onclick="order.print.toBatchPrintExpressTemplate()" >打印快递单</a> 
												<a href="javascript:;" onclick="order.mergerCargo();" title="如果多个订单收货人姓名、地址、手机等信息一致的订单，可按照实际需要对这些订单进行合单发货操作。" >合并发货</a> 
												&nbsp;&nbsp;&nbsp;
											</div>
											<div class="page"> 
												<#import "/manage/widget/page.ftl" as page>
												<@page.queryForm formId="queryVoform"/>
											</div>
										</div>
									</td>
								</tr>
							</#if>
						</thead>
						<tbody id="tbody">
							<#if (pageFinder.data)?? && pageFinder.data?size != 0>
								<#list pageFinder.data as item>
								<#if orderintercept[item.orderSubNo]>
									<tr>
									<td colspan="14" style="padding:0;text-align:left;border:none;background:#fff;height:12px;padding-top:5px;">
									<div class="aui_buttons" style="text-align:left;padding:0px;">
									<button id="orderInterceptButton" class="aui_state_highlight" title="顾客申请修改商品信息或取消订单，您可点击按钮操作是否同意此次订单拦截。" onclick="order.intercept('${item.orderSubNo!''}');">订单申请拦截</button>
									</div>
									</td>
									</tr>
								</#if>
									<!--全选操作部分-->
									<tr>
										<td>&nbsp;<input type="checkbox" no="${item.orderSubNo!''}" value="${item.orderSubId!''}" /></td>
										<td>
											${item.createTime?string("yyyy-MM-dd")}<br>
											${item.createTime?string("HH:mm:ss")}
										</td>
										<td style="text-align:left;">
											<a href="javascript:;" onclick="javascript:order.print.toDetail('${item.orderSubNo!''}','MENU_KJFH')" >${item.orderSubNo!''}</a><#if item.originalOrderNo??&&item.originalOrderNo!=''><#if item.orderSubNo?substring(item.orderSubNo?index_of("_")+1)?number gt 200>[补]<#else>[换]</#if></#if>
										</td>
										<td>${(item.orderPayTotalAmont)?string('#.##')}</td>
										<td id="userName_${item.orderSubNo!''}" >${(item.userName)!''}</td>
										<td id="consigneeMobile_${item.orderSubNo!''}" >${(item.consigneeMobile)!''}</td>
										<td id="area_${item.orderSubNo!''}">${(item.provinceName)!''}${(item.cityName)!''}${(item.areaName)!''}</td>
										<td style="text-align:left;" id="consigneeAddress_${item.orderSubNo!''}" >
											<span style="float:left;width:150px; word-break:break-all;">${item.consigneeAddress!''}</span>
										</td>
										
										<td class="rel" <#if (item.orderSubNo)??>id="${item.orderSubNo!''}"</#if> ><!--备注订单（备注小红旗） -->
                                            <div class="flag" ></div>
                                            <div class="flag_tip hide"></div>
                                            <input type="hidden" class="markColor" />
                                            <input type="hidden" class="markNote" />
                                            <input type="hidden" class="orderSubNo" value="${item.orderSubNo!''}"/>
                                        </td>
										
										<td style="text-align:left;">
											<a href="javascript:order.delivering('${item.orderSubNo!''}');" >发货</a>&nbsp;|&nbsp;
											<a href="javascript:ordernew.printSingle('${item.orderSubNo!''}')">打印购物清单</a><br>
											<a href="javascript:;" onclick="order.print.updateOutOfStockConfirm('${item.orderSubNo!''}','${item.orderSubId!''}');">缺货</a>&nbsp;|&nbsp;
											<a href="../toPrintExpressTemplateAjax.sc?orderNos=${item.orderSubNo!''}" target="_blank">打印快递单</a>
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
	    order.print.BasePath = '${BasePath}';
		$("#startTime").calendar({maxDate:'#endTime',diffDate:30});
		$("#endTime").calendar({minDate:'#startTime',diffDate:30});
		
		//提交表单搜索
		function mysubmit(){
			if(!order.checkDate()){
				return false;
			}
			$("#queryVoform").attr("action","${BasePath}/order/fastdelivery/notexported.sc");
			$("#queryVoform").submit();
		}
		
		
		// 导出拣货清单
		function doExportOrder(){
			var count = 0;	
			$("#tbody input:checked").each(function(){
				count++;
			});
			if(count==0){
				ygdg.dialog.alert("请选择订单！");
			}else{
				var orderSubIds = $("#tbody input:checked").map(function(){ return 'orderSubNos=' + $(this).attr("no"); }).get();
				var pathname = window.location.pathname;
				
				ygdg.dialog({
					id: 'submitDialog',
					title: '提示', 
					content: '请稍候，正在导出拣货清单...', 
					lock: true, 
					closable: true
				});
				
				$("#queryVoform").attr('action', '${BasePath}/order/fastdelivery/exportbog.sc?' + orderSubIds.join('&')).submit();
				
				// 重载当前页面
				setInterval(function(){ $('#queryVoform').attr('action', pathname).submit(); }, 5000);
			}
		}
		
		//导出订单明细
		function doExportOrderdetail() {
			var orderSubNos="";
			if ($("#tbody input:checked").length == 0) {
				ygdg.dialog.alert('请选择要导出的订单！');
				return;
			}
			$("#tbody input:checked").each(function(){
				orderSubNos+=$(this).attr('no')+",";
			});
			$("#queryVoform").attr("action","${BasePath}/order/fastdelivery/doExportOrderdetail.sc?orderSubNos="+orderSubNos + "&flag=1");
			$("#queryVoform").submit();
			
			var pathname = window.location.pathname;
			//重载当前页面
			//setInterval(function(){
				//$("#queryVoform").attr("action", pathname);
				//$("#queryVoform").submit();
			//}, 3000);
		}
	</script>
	<script type="text/javascript" src="${BasePath}/yougou/js/manage/order/order_common.js"></script><!-- for 订单备注功能 -->
</body>
</html>
