<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-备货清单</title>
<script type="text/javascript">
var basePath = "${BasePath}";
	//查看详情
	function queryGoodsInfo(orderSubId,obj){
		if(orderSubId==null||orderSubId==''){
			return;
		}
		var tempObj = $(obj);
		var tempDetailObj = tempObj.parents(".tr_detail");
		var detailContent = tempDetailObj.next(".tr_detail_content");
		if(tempObj.html()=="隐藏"){
			tempDetailObj.removeClass("curr");
			detailContent.hide();
			tempObj.html("查看");
		}else{
			tempDetailObj.addClass("curr");
			detailContent.show();
			tempObj.html("隐藏");
		}
		if($("#tbody"+orderSubId).html().trim()!=""){
			return;
		}
		$.getJSON("queryGoodsInfo.sc",{orderSubId:orderSubId},function(data){
			var html='';
			$.each(data.result,function(index,item){
				html+='<tr>';
				html+='<td>'+item.prodName+'</td>';
				html+='<td>'+item.activeName+'</td>';
				html+='<td>'+item.prodNo+'</td>';
				html+='<td>'+item.levelCode+'</td>';
				html+='<td>'+item.brandName+'</td>';
				html+='<td>'+item.commoditySpecificationStr+'</td>';
				html+='<td>'+item.commodityNum+'</td>';
				html+='</tr>';
			});
			$("#tbody"+orderSubId).html(html);
		})
	}
	
	//置为备货
	function updateStocking(){
		var orderSubNos = $("#tbody input:checked").map(function(){ return $(this).val(); }).get();
		
		if(orderSubNos.length <= 0){
			ygdg.dialog.alert("请选择要置为备货的订单！");
			return false;
		}
		if ($('.list_table').attr('state') == 'running') {
			return false;
		}
		
		$.ajax({
			type: 'post',
			url: 'updateStocking.sc',
			dataType: 'html',
			data: 'orderSubNos=' + orderSubNos,
			beforeSend: function(XMLHttpRequest) {
				ygdg.dialog({
					id: 'submitDialog',
					title: '提示', 
					content: '请稍候，正在将订单置为备货中...', 
					lock: true, 
					closable: false
				});
				$('.list_table').attr('state', 'running');
			},
			success: function(data, textStatus) {
				if (data == 'success') {
					ygdg.dialog.alert("置为备货成功!");
					$("#tabNum").val(3);//跳转到缺货列表
					$("#queryVoform").submit();
				}
			},
			complete: function(XMLHttpRequest, textStatus) {
				closewindow();
				$('.list_table').attr('state', 'waiting');
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("置为备货失败:" + XMLHttpRequest.responseText);
			}
		});
	}
	
	//导出 未导出
	function doExportOrder(exportType){
		var count = 0;	
		$("#tbody input:checked").each(function(){
			count++;
		});
		if(count==0){
			ygdg.dialog.alert("请选择订单！");
		}else{
			var orderSubNos = $("#tbody input:checked").map(function(){ return $(this).val(); }).get();
			$("#exportType").val(exportType);
			
			$("#queryVoform").attr("action", "doExportOrder.sc?orderSubNos=" + orderSubNos).submit();
			
			//跳转到已导出列表
			setTimeout(function(){
				$("#queryVoform").attr("action", "queryAll.sc");
				if(exportType==1){
					$("#tabNum").val(2);
				}else if(exportType==2){
					$("#tabNum").val(2);
				}else if(exportType==3){
					$("#tabNum").val(3);
				}else if(exportType==4){
					$("#tabNum").val(4);
				}
				$("#queryVoform").submit();
			}, 5000);
		}
	}
</script>
</head>

<body>
	
	
	<div class="main_container">
			<div class="normal_box">
				<p class="title site">当前位置：商家中心 &gt; 订单 &gt; 备货清单</p>
				<div class="tab_panel  relative">
				<p style="position:absolute;top:0;right:0;"><span class="fl icon_info"></span><span class="fl ml5" style="color:#999;">默认显示30天的订单，可设置条件查询更多订单，缺货订单请不要发货</span></p>
					<!--选项卡-->
					<ul class="tab">
						<li onclick="location.href='${BasePath}/order/queryAll.sc?tabNum=1'" <#if orderSubExpand.tabNum?default(-1)==1>class="curr"</#if> ><span>未导出</span></li>
						<li onclick="location.href='${BasePath}/order/queryAll.sc?tabNum=2'" <#if orderSubExpand.tabNum?default(-1)==2>class="curr"</#if> ><span>已导出</span></li>
						<li onclick="location.href='${BasePath}/order/queryAll.sc?tabNum=3'" <#if orderSubExpand.tabNum?default(-1)==3>class="curr"</#if> ><span>已备货</span></li>
						<li onclick="location.href='${BasePath}/order/queryAll.sc?tabNum=4'" <#if orderSubExpand.tabNum?default(-1)==4>class="curr"</#if> ><span>缺货订单</span></li>
						<li onclick="location.href='${BasePath}/order/queryAll.sc?tabNum=5'" <#if orderSubExpand.tabNum?default(-1)==5>class="curr"</#if> ><span>全部</span></li>
					</ul>
					<!--选项卡-->
					<div class="tab_content">
					
				<!--搜索start-->
				<div class="search_box70">
					<form name="queryVoform" id="queryVoform" action="queryAll.sc" method="post">
						<input type="hidden" id="tabNum" name="tabNum" value="${orderSubExpand.tabNum!'0'}" />
						<input type="hidden" id="orderSubId" name="orderSubId" value="" />
						<input type="hidden" id="exportType" name="exportType" value="" />
						
						<p>
							<span>
								<label>订单号：</label>
								<input type="text" name="orderSubNo" id="orderSubNo" value="${orderSubExpand.orderSubNo!''}" class="inputtxt" style="width:100px;" />
							</span>
							<span>
								<label>下单时间：</label>
								<input type="text" name="timeStart" id="startTime" value="${orderSubExpand.timeStart!''}" class="inputtxt" style="width:80px;" /> 至
								<input type="text" name="timeEnd" id="endTime" value="${orderSubExpand.timeEnd!''}" class="inputtxt" style="width:80px;" />
							</span>
							<#if orderSubExpand.tabNum?default(-1)==2>
								<span>
									<label>导出时间：</label>
									<input type="text" name="timeStartExport" id="timeStartExport" value="${orderSubExpand.timeStartExport!''}" class="inputtxt" style="width:80px;" /> 至
									<input type="text" name="timeEndExport" id="timeEndExport" value="${orderSubExpand.timeEndExport!''}" class="inputtxt" style="width:80px;" />
								</span>
							</#if>
							<#if orderSubExpand.tabNum?default(-1)==3>
								<span>
									<label>备货时间：</label>
									<input type="text" name="timeStartStocking" id="timeStartStocking" value="${orderSubExpand.timeStartStocking!''}" class="inputtxt" style="width:80px;" /> 至
									<input type="text" name="timeEndStocking" id="timeEndStocking" value="${orderSubExpand.timeEndStocking!''}" class="inputtxt" style="width:80px;" />
								</span>
								<span>
									<label>备货单号：</label>
									<input type="text" name="backupNo" id="backupNo" value="${orderSubExpand.backupNo!''}" class="inputtxt" style="width:100px;" />
								</span>
							</#if>
							<#if orderSubExpand.tabNum?default(-1)==4>
								<span>
									<label>缺货时间：</label>
									<input type="text" name="timeStartOutStock" id="timeStartOutStock" value="${orderSubExpand.timeStartOutStock!''}" class="inputtxt" style="width:80px;" /> 至
									<input type="text" name="timeEndOutStock" id="timeEndOutStock" value="${orderSubExpand.timeEndOutStock!''}" class="inputtxt" style="width:80px;" />
								</span>
							</#if>
							<#if orderSubExpand.tabNum?default(-1)==5>
								<span>
									<label>订单状态：</label>
									<select class="g-select" id="orderStatus" name="orderStatus">
									    <option value="">请选择</option>
									    <#list orderStatusMap?keys as itemKey>
									    	<option value="${itemKey}" <#if itemKey == (orderSubExpand.orderStatus)?default(-1)?string>selected="selected"</#if>>${orderStatusMap[itemKey]}</option>
									    </#list>
									</select>
								</span>
								<span>
									<label>备货状态：</label>
									<select id="orderExportedStatus" name="orderExportedStatus">
										<option value="">请选择</option>
										<option value="0" <#if orderSubExpand.orderExportedStatus?default(-1) == 0>selected="selected"</#if>>未导出</option>
										<option value="1" <#if orderSubExpand.orderExportedStatus?default(-1) == 1>selected="selected"</#if>>已导出</option>
										<option value="2" <#if orderSubExpand.orderExportedStatus?default(-1) == 2>selected="selected"</#if>>已备货</option>
									</select>
								</span>
							</#if>
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
									<th width="30"></th>
									<th width="140">下单时间</th>
									<th width="140">订单号</th>
									<th width="60">备货数量</th>
									<th width="80">支付方式</th>
									<#if orderSubExpand.tabNum?default(-1)==2>
										<th width="140">导出时间</th>
									</#if>
									<#if orderSubExpand.tabNum?default(-1)==3>
										<th width="140">备货时间</th>
										<th width="140">备货单号</th>
									</#if>
									<#if orderSubExpand.tabNum?default(-1)==4>
										<th width="120">缺货时间</th>
									</#if>
									<th width="80">订单状态</th>
									<#if orderSubExpand.tabNum?default(-1)==5>
										<th width="80">备货状态</th>
										<th width="140">备货时间</th>
										<th width="140">缺货时间</th>
									</#if>
									<th>订单备注</th>
									<th width="">操作</th>
								</tr>
								<#if pageFinder??&&pageFinder.data??>
								<tr class="do_tr">  <!--do_tr 这行客户端会自动显示隐藏-->
									<td <#if orderSubExpand.tabNum?default(-1)==1>
												 colspan="8"
											</#if>
											<#if orderSubExpand.tabNum?default(-1)==2>
												 colspan="9"
											</#if>
											<#if orderSubExpand.tabNum?default(-1)==3>
											 	colspan="10"
											</#if>
											<#if orderSubExpand.tabNum?default(-1)==4>
												 colspan="9"
											</#if>
											<#if orderSubExpand.tabNum?default(-1)==5>
												 colspan="11"
											</#if> style="padding:0;text-align:left;">
										<!--分页start-->
										<div class="tb_dobox">
											<div class="dobox">
												<label><input  class="chkall"  type="checkbox" /> 全选</label>
												<#if orderSubExpand.tabNum?default(-1)==1>
													<a href="javascript:void(0);" <#if pageFinder??&&pageFinder.data??>onclick="doExportOrder(1)"<#else>onclick="ygdg.dialog.alert('没有数据导出！')"</#if> >导出拣货清单</a>
												</#if>
												<#if orderSubExpand.tabNum?default(-1)==2>
													<a href="javascript:void(0);" onclick="updateStocking()">置为备货</a>
													<a href="javascript:void(0);" <#if pageFinder??&&pageFinder.data??>onclick="doExportOrder(2)"<#else>onclick="ygdg.dialog.alert('没有数据导出！')"</#if> >导出数据</a>
												</#if>
												<#if orderSubExpand.tabNum?default(-1)==3>
													<a href="javascript:void(0);" <#if pageFinder??&&pageFinder.data??>onclick="doExportOrder(3)"<#else>onclick="ygdg.dialog.alert('没有数据导出！')"</#if> >导出交接清单</a>
												</#if>
												<#if orderSubExpand.tabNum?default(-1)==4>
													<a href="javascript:void(0);" <#if pageFinder??&&pageFinder.data??>onclick="doExportOrder(4)"<#else>onclick="ygdg.dialog.alert('没有数据导出！')"</#if> >导出缺货订单3</a>
												</#if>
											</div>
											<#if pageFinder ??>
												<#import "/manage/widget/page.ftl" as page>
												<@page.queryForm formId="queryVoform"/>
											</#if>
										</div>
										<!--分页end-->
									</td>
								</tr>
								</#if>
							</thead>
							<tbody id="tbody">
							<#if pageFinder??&&pageFinder.data??>
								<#list pageFinder.data as item>
									<!--全选操作部分-->
									<tr class="tr_detail" isCommon_detail="false">
										<td><label>
											<input type="checkbox" value="${item.orderSubNo!''}" />&nbsp;
											</label>
										</td>
										<td>${item.createTime?string("yyyy-MM-dd HH:mm:ss")}</td>
										<td><#if (item.isException??)&&item.isException!=0>[异常]</#if>${item.orderSubNo!''}</td>
										<td>${item.productTotalQuantity!''}</td>
										<td>${item.paymentName!''}</td>
										<#if orderSubExpand.tabNum?default(-1)==2>
											<td><#if item.exportedDate??>${item.exportedDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
										</#if>
										<#if orderSubExpand.tabNum?default(-1)==3>
											<td><#if item.stockingDate??>${item.stockingDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
											<td>${item.backupNo!''}</td>
										</#if>
										<#if orderSubExpand.tabNum?default(-1)==4>
											<td><#if item.backorderDate??>${item.backorderDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
										</#if>
										<td>${item.baseStatusName!''}</td>
										<#if orderSubExpand.tabNum?default(-1)==5>
											<td>
												<#if item.orderExportedStatus?default(-1) == 0>
												未导出
												<#elseif item.orderExportedStatus?default(-1) == 1>
												已导出
												<#elseif item.orderExportedStatus?default(-1) == 2>
												已备货
												<#else>
												-
												</#if>
											</td>
											<td>
												<#if item.exportedDate??>
												${item.exportedDate?string("yyyy-MM-dd HH:mm:ss")}
												<#else>
												-
												</#if>
											</td>
											<td>
												<#if item.backorderDate??>
												${item.backorderDate?string("yyyy-MM-dd HH:mm:ss")}
												<#else>
												-
												</#if>
											</td>
										</#if>
										
										<td class="rel" <#if (item.orderSubNo)??>id="${item.orderSubNo!''}"</#if> ><!--备注订单（备注小红旗） -->
                                            <div class="flag" ></div>
                                            <div class="flag_tip hide"></div>
                                            <input type="hidden" class="markColor" />
                                            <input type="hidden" class="markNote" />
                                            <input type="hidden" class="orderSubNo" value="${item.orderSubNo!''}"/>
                                        </td>
										
										<td>
										     <#if orderSubExpand.tabNum?default(-1)==1 || orderSubExpand.tabNum?default(-1)==2>
										         <a href="javascript:void(0);" onclick="order.print.updateOutOfStockConfirm('${item.orderSubNo!''}','${item.orderSubId!''}');">缺货</a> |
										     </#if>
										         <a href="javascript:void(0);" onclick="queryGoodsInfo('<#if item??>${item.orderSubId!''}</#if>',this);">查看</a>
										</td>
									</tr>
									<!--订单商品信息-->
									<tr class="tr_detail_content">
										<td <#if orderSubExpand.tabNum?default(-1)==1>
											 colspan="7"
										</#if>
										<#if orderSubExpand.tabNum?default(-1)==2>
											 colspan="8"
										</#if>
										<#if orderSubExpand.tabNum?default(-1)==3>
										 	colspan="9"
										</#if>
										<#if orderSubExpand.tabNum?default(-1)==4>
											 colspan="8"
										</#if>
										<#if orderSubExpand.tabNum?default(-1)==5>
											 colspan="10"
										</#if> class="td_detail">
											<table class="list_detail_table">
											<thead>
											<tr>
											<th>商品名称</th>
											<th>款色编码</th>
											<th>货品编码</th>
											<th>商家货品条码</th>
											<th>商品品牌</th>
											<th>商品规格</th>
											<th>备货数量</th>
											</tr>
											</thead>
											<tbody id="tbody${item.orderSubId!''}">
											
											</tbody>
											</table>
										</td>
									</tr>
								</#list>
							<#else>
								<tr class="do_tr">  <!--do_tr 这行客户端会自动显示隐藏-->
									<td class="td-no" <#if orderSubExpand.tabNum?default(-1)==1>
												 colspan="7"
											</#if>
											<#if orderSubExpand.tabNum?default(-1)==2>
												 colspan="8"
											</#if>
											<#if orderSubExpand.tabNum?default(-1)==3>
											 	colspan="9"
											</#if>
											<#if orderSubExpand.tabNum?default(-1)==4>
												 colspan="8"
											</#if>
											<#if orderSubExpand.tabNum?default(-1)==5>
												 colspan="10"
											</#if> >
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
							<label><input  class="chkall"  type="checkbox" /> 全选</label>
							<#if orderSubExpand.tabNum?default(-1)==1>
								<a href="javascript:void(0);" <#if pageFinder??&&pageFinder.data??>onclick="doExportOrder(1)"<#else>onclick="ygdg.dialog.alert('没有数据导出！')"</#if> >导出拣货清单</a>
							</#if>
							<#if orderSubExpand.tabNum?default(-1)==2>
								<a href="javascript:void(0);" onclick="updateStocking()">置为备货</a>
								<a href="javascript:void(0);" <#if pageFinder??&&pageFinder.data??>onclick="doExportOrder(2)"<#else>onclick="ygdg.dialog.alert('没有数据导出！')"</#if> >导出数据</a>
							</#if>
							<#if orderSubExpand.tabNum?default(-1)==3>
								<a href="javascript:void(0);" <#if pageFinder??&&pageFinder.data??>onclick="doExportOrder(3)"<#else>onclick="ygdg.dialog.alert('没有数据导出！')"</#if> >导出交接清单</a>
							</#if>
							<#if orderSubExpand.tabNum?default(-1)==4>
								<a href="javascript:void(0);" <#if pageFinder??&&pageFinder.data??>onclick="doExportOrder(4)"<#else>onclick="ygdg.dialog.alert('没有数据导出！')"</#if> >导出缺货订单3</a>
							</#if>
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
</body>
</html>
<#include "/manage/order/orderprint_js_import.ftl" />
<script type="text/javascript">
order.print.BasePath = '${BasePath}';
$("#startTime").calendar({maxDate:'#endTime',diffDate:30});
$("#endTime").calendar({minDate:'#startTime',diffDate:30});
$("#timeStartExport").calendar({maxDate:'#timeEndExport'});
$("#timeEndExport").calendar({minDate:'#timeStartExport'});
$("#timeStartStocking").calendar({maxDate:'#timeEndStocking'});
$("#timeEndStocking").calendar({minDate:'#timeStartStocking'});
$("#timeStartOutStock").calendar({maxDate:'#timeEndOutStock'});
$("#timeEndOutStock").calendar({minDate:'#timeStartOutStock'});
</script>
<script type="text/javascript" src="${BasePath}/yougou/js/manage/order/order_common.js"></script><!-- for 订单备注功能 -->