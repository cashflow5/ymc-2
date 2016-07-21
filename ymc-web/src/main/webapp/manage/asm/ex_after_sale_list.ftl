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
				  <li onclick="location.href='${BasePath}/afterSale/to_queryAfterSaleList.sc'"><span>售后查看</span></li>
				  <li class="curr"><span>异常售后查看</span></li>
				</ul>
				<div class="tab_content">
					<!--搜索start-->
					<div class="search_box">
						<form id="queryForm" name="queryForm" action="${BasePath}/afterSale/queryExAfterSaleList.sc" method="post">
							<p>
								<span><label>订单号：</label>
								<input type="text" class="inputtxt" id="orderNo" name="orderNo" value="${vo.orderNo!''}"/></span>
								<span><label style="width: 115px;">售后申请单号：</label>
								<input type="text" class="inputtxt" id="applyNo" name="applyNo" value="${vo.applyNo!''}"/></span>
								<span><label style="width: 115px;">登记时间：</label>
						              <input type="text" style="width: 115px;" class="inputtxt" id="createTimeStart" name="createTimeStart" value="<#if vo??&&vo.createTimeStart??>${vo.createTimeStart}</#if>" />
						              -
						              <input type="text" style="width: 115px;" class="inputtxt" id="createTimeEnd" name="createTimeEnd" value="<#if vo??&&vo.createTimeEnd??>${vo.createTimeEnd}</#if>"  />
						        </span>
							</p>
							<p>
							    <span><label>手机号：</label>
								<input type="text" class="inputtxt" id="mobilePhone" name="mobilePhone" value="${vo.mobilePhone!''}"/></span>
							    <span><label style="width: 115px;">登记类型：</label>
						              <select id="exceptionType" name="exceptionType" style="width:130px;">
						              <option <#if vo.exceptionType??&&vo.exceptionType==''>selected</#if> value="">全部</option>
						              <option <#if vo.exceptionType??&&vo.exceptionType=='LOST_GOODS'>selected</#if> value="LOST_GOODS">丢件</option>
						              <option <#if vo.exceptionType??&&vo.exceptionType=='DRAIN_GOODS'>selected</#if> value="DRAIN_GOODS">漏发</option>
						              <option <#if vo.exceptionType??&&vo.exceptionType=='ERROR_GOODS'>selected</#if> value="ERROR_GOODS">错发</option>
						              </select>
						        </span>
								<span><label style="width: 110px;">售后申请单状态：</label>
								<select id="status" name="status" style="width:115px;">
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
								<span style="padding-left:87px;"><a id="mySubmit" type="button" class="button" onclick="mySubmit()" ><span>搜索</span></a></span>
							</p>
						</form>
					</div>
					<!--搜索end-->
					<!--列表start-->
					<table class="list_table">
						<thead>
							<tr>
								<th>登记时间</th>
								<th>货品条码</th>
								<th>订单号</th>
								<th>售后申请单号</th>
								<th>收货人</th>
								<th>手机号</th>
								<th>登记类型</th>
								<th>售后申请单状态</th>
								<th>最后处理时间</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
						 <#if pageFinder?? && (pageFinder.data)??> 
							<#list pageFinder.data as item>
							<tr>
								<td>${item.create_time?default('')}</td>
								<td>${item.prodCode?default('')}</td>
								<td><a href="javascript:;" onclick="javascript:toDetail('${item.order_sub_no?default('')}')">${item.order_sub_no?default('')}</a></td>
								<td>${item.apply_no?default('')}</td>
								<td>${item.userName?default('-')}</td>
								<td>${item.mobile_phone?default('')}</td>
								<td>
									<#if (item.exceptionType)??>
										<#if item.exceptionType=='LOST_GOODS'>丢件
										<#elseif item.exceptionType=='DRAIN_GOODS'>漏发货
										<#elseif item.exceptionType=='ERROR_GOODS'>错发货
										<#elseif item.exceptionType=='QUALITY_GOODS'>质量问题投诉
										<#elseif item.exceptionType=='REJECT_GOODS'>拒收未质检
										</#if>
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
								<td><a href="${BasePath}/afterSale/ex_afterSaleDetail.sc?applyNo=${item.apply_no?default('')}&&orderNo=${item.order_sub_no?default('')}" target="_blank">查看</a></td>
							</tr>
							</#list>
						<#else>
							<tr>
								<td colspan="10" class="td-no">暂无记录！</td>
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
$("#createTimeStart").calendar({maxDate:'#createTimeEnd',format:'yyyy-MM-dd HH:mm:ss'});
$("#createTimeEnd").calendar({minDate:'#createTimeStart',format:'yyyy-MM-dd HH:mm:ss'});
//提交表单查询
 function mySubmit() {
	var queryForm = document.getElementById("queryForm");
	queryForm.action ="${BasePath}/afterSale/queryExAfterSaleList.sc";
	queryForm.submit();
}
//显示订单详情
function toDetail(orderSubNo){
	openwindow("${BasePath}/order/toOrderDetails.sc?orderSubNo="+orderSubNo+"&menuCode=MENU_SHDCX",960,650,'订单详情');
};
</script>
</html>
