<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-工单处理</title>
<style>
.search_box label {
    width: 90px;
}
.list_table tr.on td .list_detail_table tr td{background:#fff;}
</style>
</head>
<body>
	<div class="main_container">
		<div class="normal_box">
			<p class="title site">当前位置：商家中心 &gt; 售后 &gt; 工单处理(新)</p>
			<div class="tab_panel">
			<!--搜索start-->
					<div class="search_box" style="padding-top: 0px;">
						<form id="queryForm" name="queryForm" action="${BasePath}/dialoglist/queryTodo.sc" method="post">
							<p>
								<span><label>工单号：</label>
								<input type="text" class="inputtxt" id="workOrderNo" name="workOrderNo" value="${vo.workOrderNo!''}"/></span>
								<span><label>订单号：</label>
								<input type="text" class="inputtxt" id="orderNo" name="orderNo" value="${vo.orderNo!''}"/></span>
								<span><label>创建时间：</label>
									<input type="text" style="width: 80px;" class="inputtxt" id="startTime" name="startTime" value="${vo.startTime!''}"/> 至
									<input type="text" style="width: 80px;" class="inputtxt" id="endTime" name="endTime" value="${vo.endTime!''}"/>
								</span>
							</p>
							<p>
							    <span><label>工单状态：</label>
								<select id="workOrderStatus" name="workOrderStatus" style="width:126px;">
									<option value="2">全部</option>
									<option <#if (vo.workOrderStatus)??&&vo.workOrderStatus=='0'>selected</#if> value="0">待商家处理</option>
									<option <#if (vo.workOrderStatus)??&&vo.workOrderStatus=='1'>selected</#if> value="1">待优购处理</option>
								</select>
								</span>
								
								<span><label>工单类型：</label>
								<select id="workOrderName" name="workOrderName" style="width:126px;">
									<option value="">全部</option>
									<#if workOrderNames??>
									<#list workOrderNames as item>
										<option <#if item??&&(vo.workOrderName)??&&vo.workOrderName==item>selected</#if> value="${item!""}">${item?default('')}</option>
									</#list>
									</#if>
								</select>
								</span>
								
								<span><label>紧急程度：</label>
								<select id="emergencyLevel" name="emergencyLevel" style="width:126px;">
									<option value="">全部</option>
									<option <#if (vo.emergencyLevel)??&&vo.emergencyLevel=='0'>selected</#if> value="0">一般</option>
									<option <#if (vo.emergencyLevel)??&&vo.emergencyLevel=='1'>selected</#if> value="1">紧急</option>
								</select>
								</span>
								<span style="padding-left:65px;"><a id="mySubmit" type="button" class="button" onclick="mySubmit()" ><span>搜索</span></a></span>
							</p>
						</form>
					</div>
				<!--搜索end-->
			    <p class="blank20"></p>
			    <ul class="tab">
					<li onclick="location.href='${BasePath}/dialoglist/queryTodo.sc?workOrderStatus=0'" <#if vo.workOrderStatus?default("2")=='0'>class="curr"</#if> ><span>待商家处理</span></li>
					<li onclick="location.href='${BasePath}/dialoglist/queryTodo.sc?workOrderStatus=2'" <#if vo.workOrderStatus?default("2")!='0'>class="curr"</#if> ><span>全部</span></li>
				</ul>
				<div class="tab_content">
					
					<!--列表start-->
					<table class="list_table" id="table" >
						<thead>
							<tr>
								<th width="100">工单号</th>
								<th width="120">订单号</th>
								<th width="80">工单类型</th>
								<th width="375">工单内容</th>
								<th width="90">工单状态</th>
								<th width="60">紧急程度</th>
								<th width="80"><#if vo.workOrderStatus?default("-1")!='0'>最近更新时间<#else>未处理时长</#if></th>
								<th width="60">详情</th>
							</tr>
						</thead>
						<tbody>
						 <#if pageFinder?? && (pageFinder.data)?? && (pageFinder.data)?size gt 0 > 
							<#list pageFinder.data as item>
							<tr>
								<td><a href="${BasePath}/dialoglist/detailTodo.sc?workOrderNo=${item['workOrderNo']?default('')}&workOrderStatus=${item['workOrderStatus']?default('')}">${item['workOrderNo']?default('')}</a></td>
								<td><a href="javascript:;" onclick="javascript:toDetail('${item['orderNo']?default('')}')">${item['orderNo']?default('')}</a></td>
								<td>${(item['workOrderName'])!'--'}</td>
								<td style="text-align:left"><#if item['questionDescrition']??&&(item['questionDescrition']?length gt 60)>${item['questionDescrition']?substring(0,58)}...<#else>${item['questionDescrition']?default('')}</#if></td>
								<td><#if item['workOrderStatus']?default("")=='1'>待客服处理<#elseif item['workOrderStatus']?default("")=='2'>已完成<#elseif item['workOrderStatus']?default("")=='0'><em style="color:#FF0000;">待商家处理</em><#else>${item['workOrderStatus']?default('')}</#if></td>
								<td><#if item['emergencyLevel']?default("")=='0'>一般<#elseif item['emergencyLevel']?default("")=='1'>紧急<#else>${item['emergencyLevel']?default('')}</#if></td>
								<td><#if vo.workOrderStatus?default("-1")!='0'><#if item['lastDealTime']??>${item['lastDealTime']?default('')}</#if><#else>${item['duration']}小时</#if></td>
								<td><a href="${BasePath}/dialoglist/detailTodo.sc?workOrderNo=${item['workOrderNo']?default('')}&workOrderStatus=${item['workOrderStatus']?default('')}"><#if item['workOrderStatus']?default("-1")!='0'>查看<#else>处理</#if></a></td>
							</tr>
							</#list>
						<#else>
							<tr>
								<td colspan="12">没有相关记录！</td>
							</tr>
						</#if>
						</tbody>
					</table>
					<!--列表end-->
				<!--分页start-->
				<#if pageFinder??&&pageFinder.data?? && (pageFinder.data)?size gt 0>
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
$("#startTime").calendar({maxDate:'#endTime',format:'yyyy-MM-dd'});
$("#endTime").calendar({minDate:'#startTime',format:'yyyy-MM-dd'});
//提交表单查询
 function mySubmit() {
	var queryForm = document.getElementById("queryForm");
	queryForm.action ="${BasePath}/dialoglist/queryTodo.sc";
	queryForm.submit();
}
//显示订单详情
function toDetail(orderSubNo){
	openwindow("${BasePath}/order/toOrderDetails.sc?orderSubNo="+orderSubNo+"&menuCode=MENU_GDCL",1100,500,'订单详情');
};
</script>
</html>
