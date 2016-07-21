<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-售后单查询</title>
<style>
.search_box label {
    width: 90px;
}
</style>
</head>
 
<body>
	
	
	<div class="main_container">
		<div class="normal_box">
			<p class="title site">当前位置：商家中心 &gt; 售后 &gt; 售后单查询 </p>
			<div class="tab_panel">
				<ul class="tab">
				  <li class="curr"><span>售后查看</span></li>
				  <li onclick="location.href='${BasePath}/afterSale/queryExAfterSaleList.sc'"><span>异常售后查看</span></li>
				</ul>
				<div class="tab_content">
					<!--搜索start-->
					<div class="search_box">
						<form id="queryForm" name="queryForm" action="${BasePath}/afterSale/to_queryAfterSaleList.sc" method="post">
							<p>
								<span><label>订单号：</label>
								<input type="text" class="inputtxt" id="orderCode" name="orderSubNo" value="${vo.orderSubNo!''}"/></span>
								<span><label style="width: 115px;">原始订单号：</label>
								<input type="text" class="inputtxt" id="originalOrderNo" name="originalOrderNo" value="${vo.originalOrderNo!''}"/></span>
								<span><label style="width: 115px;">收货人：</label>
								<input type="text" class="inputtxt" id="consignee" name="consignee" value="${vo.consignee!''}"/></span>
							</p>
							<p>
								<span><label>收货人手机：</label>
								<input type="text" class="inputtxt" id="mobilePhone" name="mobilePhone" value="${vo.mobilePhone!''}"/></span>
								<span><label style="width: 115px;">售后类型：</label>
								<select id="saleType" name="saleType" style="width:127px;">
									<option value="">请选择</option>
									<option <#if vo.saleType??&&vo.saleType=='QUIT_GOODS'>selected</#if> value="QUIT_GOODS">退货</option>
									<option <#if vo.saleType??&&vo.saleType=='TRADE_GOODS'>selected</#if> value="TRADE_GOODS">换货</option>
								</select>
								</span>
								<span><label style="width: 115px;">售后申请单状态：</label>
								<select id="status" name="status" style="width:127px;">
									<option value="">请选择</option>
									<option <#if vo.status??&&vo.status=='SALE_APPLY'>selected</#if> value="SALE_APPLY">未审核</option>
									<option <#if vo.status??&&vo.status=='SALE_COMFIRM'>selected</#if> value="SALE_COMFIRM">已审核</option>
									<option <#if vo.status??&&vo.status=='SALE_REFUSE'>selected</#if> value="SALE_REFUSE">拒绝申请</option>
									<option <#if vo.status??&&vo.status=='SALE_EXCHANGE_GOODS'>selected</#if> value="SALE_EXCHANGE_GOODS">已换货</option>
									<option <#if vo.status??&&vo.status=='SALE_NOT_GOODS'>selected</#if> value="SALE_NOT_GOODS">未收到货</option>
									<option <#if vo.status??&&vo.status=='SALE_RECEIVE_GOODS'>selected</#if> value="SALE_RECEIVE_GOODS">收到退货</option>
									<option <#if vo.status??&&vo.status=='SALE_CALL_BACK'>selected</#if> value="SALE_CALL_BACK">打回</option>
									<option <#if vo.status??&&vo.status=='PART_SALE_QC'>selected</#if> value="PART_SALE_QC">部分质检</option>
									<option <#if vo.status??&&vo.status=='SALE_QC'>selected</#if> value="SALE_QC">已质检</option>
									<option <#if vo.status??&&vo.status=='SALE_SEND_GOODS'>selected</#if> value="SALE_SEND_GOODS">已发货</option>
									<option <#if vo.status??&&vo.status=='SALE_REFUND_APPLY'>selected</#if> value="SALE_REFUND_APPLY">退款申请中</option>
									<option <#if vo.status??&&vo.status=='SALE_REFUND_COMFIRM'>selected</#if> value="SALE_REFUND_COMFIRM">退款审核通过</option>
									<option <#if vo.status??&&vo.status=='SALE_REFUND_REFUSE'>selected</#if> value="SALE_REFUND_REFUSE">退款拒绝</option>
									<option <#if vo.status??&&vo.status=='SALE_REFUND_YES'>selected</#if> value="SALE_REFUND_YES">已退款</option>
									<option <#if vo.status??&&vo.status=='SALE_SUPPLY_YES'>selected</#if> value="SALE_SUPPLY_YES">已补款</option>
									<option <#if vo.status??&&vo.status=='SALE_SUPPLY_FAIL'>selected</#if> value="SALE_SUPPLY_FAIL">补款拒绝</option>
									<option <#if vo.status??&&vo.status=='SALE_SUCCESS'>selected</#if> value="SALE_SUCCESS">已完成</option>
									<option <#if vo.status??&&vo.status=='SALE_CANCEL'>selected</#if> value="SALE_CANCEL">已作废</option>
									<option <#if vo.status??&&vo.status=='SALE_NO_QC'>selected</#if> value="SALE_NO_QC">质检不通过</option>
									<option <#if vo.status??&&vo.status=='SALE_WAIT_REPAIR'>selected</#if> value="SALE_WAIT_REPAIR">待维修</option>
									<option <#if vo.status??&&vo.status=='SALE_REPAIR_PROCESS'>selected</#if> value="SALE_REPAIR_PROCESS">维修中</option>
									<option <#if vo.status??&&vo.status=='SALE_REPAIR_FAILED_PROCESSED'>selected</#if> value="SALE_REPAIR_FAILED_PROCESSED">维修失败待处理</option>
									<option <#if vo.status??&&vo.status=='SALE_REPAIR_COMPLETE'>selected</#if> value="SALE_REPAIR_COMPLETE">已维修待退回</option>
									<option <#if vo.status??&&vo.status=='SALE_REPAIR_WAITRETURN'>selected</#if> value="SALE_REPAIR_WAITRETURN">待退回</option>
									<option <#if vo.status??&&vo.status=='SALE_REPAIR_RETURN'>selected</#if> value="SALE_REPAIR_RETURN">已退回</option>
									<option <#if vo.status??&&vo.status=='SALE_REPLACEMENT_GOODS'>selected</#if> value="SALE_REPLACEMENT_GOODS">已申请补发</option>
								</select>
								</span>
							</p>
							<p>
								<span><label>售后申请单号：</label>
								<input type="text" class="inputtxt" id="applyNo" name="applyNo" value="${vo.applyNo!''}"/></span>
								<span><label style="width: 115px;">寄回快递单号：</label>
								<input type="text" class="inputtxt" id="expressNo" name="expressNo" value="${vo.expressNo!''}"/></span>
								<span><label style="width: 115px;">申请时间：</label>
									<input type="text" style="width: 80px;" class="inputtxt" id="applyTimeStart" name="applyTimeStart" value="${vo.applyTimeStart!''}"/> 至
									<input type="text" style="width: 80px;" class="inputtxt" id="applyTimeEnd" name="applyTimeEnd" value="${vo.applyTimeEnd!''}"/>
								</span>
								<#--<span><label>质检时间：</label>
									<input type="text" style="width: 80px;" class="inputtxt" id="qcTimeStart" name="qcTimeStart" value="${vo.qcTimeStart!''}"/> 至
									<input type="text" style="width: 80px;" class="inputtxt" id="qcTimeEnd" name="qcTimeEnd" value="${vo.qcTimeEnd!''}"/>
								</span>-->
								<input type="hidden" id="isfirst" name="isfirst" value="${isfirst}" />
								<span style="padding-left:65px;"><a id="mySubmit" type="button" class="button" onclick="mySubmit()" ><span>搜索</span></a></span>
							</p>
						</form>
					</div>
					<!--搜索end-->
					<!--列表start-->
					<table class="list_table">
						<thead>
							<tr>
								<th>申请时间</th>
								<th>申请单号</th>
								<th>优购订单号</th>
								<th>外部订单号</th>
								<th>收货人</th>
								<th>订单状态</th>
								<th>售后申请单状态</th>
								<th>最后处理时间</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
						 <#if pageFinder?? && (pageFinder.data)??> 
							<#list pageFinder.data as item>
							<tr>
								<td>${item.createTime?default('')}</td>
								<td>${item.applyNo?default('')}</td>
								<td><a href="javascript:;" onclick="javascript:toDetail('${item.orderSubNo?default('')}')">${item.orderSubNo?default('')}</a></td>
								<td>${item.outOrderId?default('-')}</td>
								<td>${item.userName?default('-')}</td>
								<td>
									<#if (item.orderStatus)??>
										<#if item.orderStatus==1>已创建
										<#elseif item.orderStatus==2>已付定金
										<#elseif item.orderStatus==3>已支付
										<#elseif item.orderStatus==4>已通知客服处理
										<#elseif item.orderStatus==5>已审核
										<#elseif item.orderStatus==6>已打包
										<#elseif item.orderStatus==7>已申请修改订单
										<#elseif item.orderStatus==8>已申请取消订单
										<#elseif item.orderStatus==9>已取消
										<#elseif item.orderStatus==10>已申请退款
										<#elseif item.orderStatus==11>已退款
										<#elseif item.orderStatus==12>已通知库房拣货
										<#elseif item.orderStatus==13>已发货
										<#elseif item.orderStatus==14>拒收已质检
										<#elseif item.orderStatus==15>拒收已申请退款
										<#elseif item.orderStatus==16>拒收已拒绝退款
										<#elseif item.orderStatus==17>拒收已退款
										<#elseif item.orderStatus==18>拒收已换货
										<#elseif item.orderStatus==19>已归档
										</#if>
										<#else>
										--
									</#if>
								</td>
								<td>
									<#if (item.status)??>
										<#if item.status == 'SALE_APPLY'>未审核
										<#elseif item.status == 'SALE_COMFIRM'>已审核
										<#elseif item.status == 'SALE_REFUSE'>拒绝申请
										<#elseif item.status == 'SALE_EXCHANGE_GOODS'>已换货
										<#elseif item.status == 'SALE_NOT_GOODS'>未收到货
										<#elseif item.status == 'SALE_RECEIVE_GOODS'>收到退货
										<#elseif item.status == 'SALE_CALL_BACK'>打回
										<#elseif item.status == 'PART_SALE_QC'>部分质检
										<#elseif item.status == 'SALE_QC'>已质检
										<#elseif item.status == 'SALE_SEND_GOODS'>已发货
										<#elseif item.status == 'SALE_REFUND_APPLY'>退款申请中
										<#elseif item.status == 'SALE_REFUND_COMFIRM'>退款审核通过
										<#elseif item.status == 'SALE_REFUND_REFUSE'>退款拒绝
										<#elseif item.status == 'SALE_REFUND_YES'>已退款
										<#elseif item.status == 'SALE_SUPPLY_YES'>已补款
										<#elseif item.status == 'SALE_SUPPLY_FAIL'>补款拒绝
										<#elseif item.status == 'SALE_SUCCESS'>已完成
										<#elseif item.status == 'SALE_CANCEL'>已作废
										<#elseif item.status == 'SALE_NO_QC'>质检不通过
										<#elseif item.status == 'SALE_WAIT_REPAIR'>待维修
										<#elseif item.status == 'SALE_REPAIR_PROCESS'>维修中
										<#elseif item.status == 'SALE_REPAIR_FAILED_PROCESSED'>维修失败待处理
										<#elseif item.status == 'SALE_REPAIR_COMPLETE'>已维修待退回
										<#elseif item.status == 'SALE_REPAIR_WAITRETURN'>待退回
										<#elseif item.status == 'SALE_REPAIR_RETURN'>已退回
										<#elseif item.status == 'SALE_REPLACEMENT_GOODS'>已申请补发
								        </#if>
									</#if>
								</td>
								<td><#if item.updateTime??>${item.updateTime?default('-')}</#if></td>
								<td><a href="${BasePath}/afterSale/afterSaleDetail.sc?applyNo=${item.applyNo?default('')}" target="_blank">查看</a></td>
							</tr>
							</#list>
						<#elseif isfirst == 'true'>
							<tr>
								<td colspan="12" class="td-no">请搜索获取数据！</td>
							</tr>
						<#else>
							<tr>
								<td colspan="12" class="td-no">暂无记录！</td>
							</tr>
						</#if>
						</tbody>
					</table>
					<!--列表end-->
				<!--分页start-->
				<#if pageFinder??&&pageFinder.data??>
				<div class="page_box">
						<#import "/manage/widget/common.ftl" as page>
						<@page.queryForm formId="queryForm"/>
				</div>
				</#if>
				<!--分页end-->
			</div>
			</div>
		</div>
		
		
	</div>
</body>
<script>
$("#applyTimeStart").calendar({maxDate:'#applyTimeEnd',format:'yyyy-MM-dd'});
$("#applyTimeEnd").calendar({minDate:'#applyTimeStart',format:'yyyy-MM-dd'});
//$("#qcTimeStart").calendar({maxDate:'#qcTimeEnd'});
//$("#qcTimeEnd").calendar({minDate:'#qcTimeStart'});
//提交表单查询
 function mySubmit() {
	var queryForm = document.getElementById("queryForm");
	queryForm.action ="${BasePath}/afterSale/to_queryAfterSaleList.sc";
	queryForm.submit();
}
function toDetail(orderSubNo){
	openwindow("${BasePath}/order/toOrderDetails.sc?orderSubNo="+orderSubNo+"&menuCode=MENU_SHDCX",960,650,'订单详情');
};
</script>
</html>
