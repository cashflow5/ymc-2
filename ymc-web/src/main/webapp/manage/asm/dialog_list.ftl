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
			<p class="title site">当前位置：商家中心 &gt; 售后 &gt; 工单处理 </p>
			<div class="tab_panel">
			<!--搜索start-->
					<div class="search_box" style="padding-top: 0px;">
						<form id="queryForm" name="queryForm" action="${BasePath}/dialoglist/query.sc" method="post">
							<p>
								<span><label>工单号：</label>
								<input type="text" class="inputtxt" id="orderTraceNo" name="orderTraceNo" value="${vo.orderTraceNo!''}"/></span>
								<span><label>订单号：</label>
								<input type="text" class="inputtxt" id="orderSubNo" name="orderSubNo" value="${vo.orderSubNo!''}"/></span>
								<span><label>创建时间：</label>
									<input type="text" style="width: 80px;" class="inputtxt" id="startTime" name="startTime" value="${vo.startTime!''}"/> 至
									<input type="text" style="width: 80px;" class="inputtxt" id="endTime" name="endTime" value="${vo.endTime!''}"/>
								</span>
							</p>
							<p>
							    <span><label>工单状态：</label>
								<select id="traceStatus" name="traceStatus" style="width:126px;">
									<option <#if vo.traceStatus??&&vo.traceStatus==-1>selected</#if> value="-1">全部</option>
									<option <#if vo.traceStatus??&&vo.traceStatus==3>selected</#if> value="3">待商家处理</option>
									<option <#if vo.traceStatus??&&vo.traceStatus==0>selected</#if> value="0">待优购处理</option>
								</select>
								</span>
								
								<span><label>工单类型：</label>
								<select id="problemId" name="problemId" style="width:126px;">
									<option <#if vo.problemId??&&vo.problemId==''>selected</#if> value="">全部</option>
									<#list problem as item>
										<option <#if item['id']??&&vo.problemId??&&vo.problemId==(item['id'])>selected</#if> value="${item['id']!""}">${item['name']?default('')}</option>
									</#list>
								</select>
								</span>
								
								<span><label>紧急程度：</label>
								<select id="urgencyDegree" name="urgencyDegree" style="width:126px;">
									<option <#if vo.urgencyDegree??&&vo.urgencyDegree==-1>selected</#if> value="-1">全部</option>
									<option <#if vo.urgencyDegree??&&vo.urgencyDegree==0>selected</#if> value="0">一般</option>
									<option <#if vo.urgencyDegree??&&vo.urgencyDegree==1>selected</#if> value="1">紧急</option>
								</select>
								</span>
								<span style="padding-left:65px;"><a id="mySubmit" type="button" class="button" onclick="mySubmit()" ><span>搜索</span></a></span>
							</p>
						</form>
					</div>
				<!--搜索end-->
			    <p class="blank20"></p>
			    <ul class="tab">
					<li onclick="location.href='${BasePath}/dialoglist/query.sc?traceStatus=3'" <#if vo.traceStatus?default("3")==3>class="curr"</#if> ><span>待商家处理</span></li>
					<li onclick="location.href='${BasePath}/dialoglist/query.sc?traceStatus=-1'" <#if vo.traceStatus?default("3")!=3>class="curr"</#if> ><span>全部</span></li>
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
								<th width="80"><#if vo.traceStatus?default("-1")!=3>最近更新时间<#else>未处理时长</#if></th>
								<th width="60">详情</th>
							</tr>
						</thead>
						<tbody>
						 <#if pageFinder?? && (pageFinder.data)?? > 
							<#list pageFinder.data as item>
							<tr>
								<td><a href="${BasePath}/dialoglist/detail.sc?id=${item['id']?default('')}" target="_blank">${item['orderTraceNo']?default('')}</a></td>
								<td><a href="javascript:;" onclick="javascript:toDetail('${item['orderSubNo']?default('')}')">${item['orderSubNo']?default('')}</a></td>
								<td>${(item['secondProblemName'])!'--'}</td>
								<td style="text-align:left"><#if item['issueDescription']??&&(item['issueDescription']?length gt 60)>${item['issueDescription']?substring(0,58)}...<#else>${item['issueDescription']?default('')}</#if></td>
								<td><#if item['traceStatus']?default("")==0>待客服处理<#elseif item['traceStatus']?default("")==1>处理中<#elseif item['traceStatus']?default("")==2>已完成<#elseif item['traceStatus']?default("")==3><em style="color:#FF0000;">待商家处理</em><#else>${item['traceStatus']?default('')}</#if></td>
								<td><#if item['urgencyDegree']?default("")==0>一般<#elseif item['urgencyDegree']?default("")==1>紧急<#else>${item['urgencyDegree']?default('')}</#if></td>
								<td><#if vo.traceStatus?default("3")!=3><#if item['modifyTime']??>${item['modifyTime']?string('yyyy-MM-dd HH:mm:ss')}<#else>${item['createTime']?string('yyyy-MM-dd HH:mm:ss')}</#if><#else>${item['hourNumSinceCreated']?default('')}小时</#if></td>
								<td><a href="${BasePath}/dialoglist/detail.sc?id=${item['id']?default('')}" target="_blank"><#if item['traceStatus']?default("-1")!=3>查看<#else>处理</#if></a></td>
							</tr>
							</#list>
						<#else>
							<tr>
								<td colspan="12" class="td-no">没有相关记录！</td>
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
$("#startTime").calendar({maxDate:'#endTime',format:'yyyy-MM-dd'});
$("#endTime").calendar({minDate:'#startTime',format:'yyyy-MM-dd'});
//提交表单查询
 function mySubmit() {
	var queryForm = document.getElementById("queryForm");
	queryForm.action ="${BasePath}/dialoglist/query.sc";
	queryForm.submit();
}
//显示订单详情
function toDetail(orderSubNo){
	openwindow("${BasePath}/order/toOrderDetails.sc?orderSubNo="+orderSubNo+"&menuCode=MENU_GDCL",1100,500,'订单详情');
};
</script>
</html>
