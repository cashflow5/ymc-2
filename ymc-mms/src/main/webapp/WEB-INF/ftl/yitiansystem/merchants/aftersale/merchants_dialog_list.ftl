<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#include "../merchants-include.ftl">
<title>优购商城--商家后台</title>
</head>
<body>
<div class="container">
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li  class="curr">
				  <span><a href="#" class="btn-onselc">工单处理</a></span>
				</li>
			</ul>
		</div>
 <div class="modify"> 
     <form action="${BasePath}/yitiansystem/merchants/businessorder/dialogList.sc?str=1" name="queryForm" id="queryForm" method="post">        	
              	<div class="add_detail_box">
					<p>
						<span>
						  <label>优购订单号：</label>
						  <input style="width:150px;" type="text" id="orderSubNo" name="orderSubNo" value="<#if vo??&&vo.orderSubNo??>${vo.orderSubNo}</#if>">
						</span>
						
						<span>
						<label>工单号：</label>
						<input style="width:150px;" type="text" id="orderTraceNo" name="orderTraceNo" value="<#if vo??&&vo.orderTraceNo??>${vo.orderTraceNo}</#if>">
						</span>
						
						<span><label>工单类型：</label>
						<select id="problemId" name="problemId" onchange="chooseSecondProblemId();" style="width:126px;">
						<option <#if vo.problemId??&&vo.problemId==''>selected</#if> value="">全部</option>
						<#list problem as item>
							<option <#if item['id']??&&vo.problemId??&&vo.problemId==(item['id'])>selected</#if> value="${item['id']!""}">${item['name']?default('')}</option>
						</#list>
						</select>
						</span>
						
						
						<span>
						<label>问题归属：</label>
						<select id="secondProblemId" name="secondProblemId" class="selecttxt">
							<option value="-1">请选择问题归属</option>
							<#if problemList??>
								<#list problemList as item>
									<#if item.level==2>
										<#if vo??&&vo.problemId??&&vo.problemId==item.parentId>
											<#if vo.secondProblemId??>
												<#if vo.secondProblemId==item.id>
													<option value="${item.id}" selected>${item.name!""}</option>
												<#else>
													<option value="${item.id}">${item.name!""}</option>
												</#if>
											<#else>
												<option value="${item.id}">${item.name!""}</option>
											</#if>
										</#if>
									</#if>
								</#list>
							</#if>
						</select>
						</span>
					</p>

					<p>
					<span>
						<label>商家编号：</label>
						<input style="width:150px;" type="text" id="merchantCode" name="merchantCode" value="<#if vo??&&vo.merchantCode??>${vo.merchantCode}</#if>">
					</span>

					<span>
						<label>工单状态：</label>
						 <select id="status" name="traceStatus" class="selecttxt">
							<option value="-1">请选择工单状态</option>
							   <option value="0" <#if vo.traceStatus==0>selected</#if>>待客服处理</option>
							   <option value="2" <#if vo.traceStatus==2>selected</#if>>已完成</option>
							   <option value="3" <#if vo.traceStatus==3>selected</#if>>待商家处理</option>
						  </select>
					</span>

					<span>
					  <label>紧急程度：</label>
						<select id="urgencyDegree" name="urgencyDegree" class="selecttxt">
							<option value="-1">请选择紧急程度</option>
							<option value="0" <#if vo.urgencyDegree==0>selected</#if>>一般</option>
							<option value="1" <#if vo.urgencyDegree==1>selected</#if>>紧急</option>
						</select>
					</span>
						
						
						
						
					</p>
					<p>
					    <span>
						<label>创建时间：</label>
						<input id="startTime" name="startTime" value="<#if vo??&&vo.startTime??>${vo.startTime}</#if>" />
						-
						<input id="endTime" name="endTime" value="<#if vo??&&vo.endTime??>${vo.endTime}</#if>"  />
						</span>&nbsp;&nbsp;&nbsp;
						<input type="button" value="搜索" onclick="queryMerchantsDialogList();" class="yt-seach-btn" />&nbsp;&nbsp;&nbsp;
					</p>
					 
				</div>         	
              	</form>
                <table cellpadding="0" cellspacing="0" class="list_table">
                		<thead>
                        <tr>
                        <th style="width:8%;">创建时间</th>
                        <th style="width:110px;">工单号</th>
                        <th style="width:65px;">订单号</th>
                        <th style="width:80px;">商家编号</th>
                        <th style="width:55px;">工单类型</th>
                        <th>问题描述</th>
                        <th style="width:55px;">紧急程度</th>
                        <th style="width:60px;">工单状态</th>
                        <th style="width:55px;">添加时长</th>
                        <th style="width:35px;">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#if pageFinder??&&pageFinder.data?? >
                        <#list pageFinder.data as item >
                		<tr>
                			    <td>${item['createTime']?string('yyyy-MM-dd HH:mm:ss')}</td>
                			    <td><div style="word-break:break-all;word-wrap:break-word;float:left;">${item['orderTraceNo']!""}</div></td>
                			    <td><a target="_blank" href="${omsHost!''}/yitiansystem/ordergmt/orderdetail/toBaseDetail.sc?orderNo=${item['orderSubNo']}" >${item['orderSubNo']}</a></td>
                				<td class="ft-cl-r">${item['merchantCode']!''}</td>
                				<td class="ft-cl-r">${item['problemName']!''}</td>
                				<td>${item['issueDescription']?default('')}</td>
                				<td class="ft-cl-r" style="text-align: center;"><#if item['urgencyDegree']?default("")==0>一般<#elseif item['urgencyDegree']?default("")==1><em style="color:#FF0000;">紧急</em><#else>${item['urgencyDegree']?default('')}</#if></td>
                				<td><#if item['traceStatus']?default("")==0>待客服处理<#elseif item['traceStatus']?default("")==1>处理中<#elseif item['traceStatus']?default("")==2>已完成<#elseif item['traceStatus']?default("")==3><em style="color:#FF0000;">待商家处理</em><#else>${item['traceStatus']?default('')}</#if></td>
                				<td class="ft-cl-r"><#if item['hourNumSinceCreated']?number gt 24><em style="color:#FF0000;">${item['hourNumSinceCreated']!''}</em><#else>${item['hourNumSinceCreated']!''}</#if></td>
                				<td class="ft-cl-r">
                				<a onclick="showTraceProc('${item['id']!""}','${item['orderSubNo']!""}');" href="javascript:void(0)">查看</a>
                				</td>
                		 </tr>
                	     </#list>
                        	<#else>
                        	<tr>
                        	<td colSpan="10">
                        	抱歉，没有您要找的数据 
	                        </td>
	                        </tr>
                        </#if>
                      </tbody>
                </table>
              </div>
               <div class="bottom clearfix">
			  	<#if pageFinder ??><#import "../../../common.ftl" as page>
			  		<@page.queryForm formId="queryForm"/></#if>
			  </div>
              <div class="blank20"></div>
          </div>
</div>
</body>
</html>
<script type="text/javascript">
$(document).ready(function() {
$("#startTime").calendar({maxDate:'#endTime',format:'yyyy-MM-dd'});
$("#endTime").calendar({minDate:'#startTime',format:'yyyy-MM-dd'});
	
});
//根据条件查询
function queryMerchantsDialogList(){
  document.queryForm.submit();
}

function showTraceProc(orderTraceId,orderSubNo) {

var fields = $("#queryform").serializeArray();
var temp_json = "{"; 
for (var json_i = 0; json_i < fields.length; json_i++) { 
if (json_i != fields.length - 1) { 
temp_json += '"' + fields[json_i].name + '":"' + fields[json_i].value + '",'; 
} 
else { 
temp_json += '"' + fields[json_i].name + '":"' + fields[json_i].value + '"'; 
} 
} 
var cancelFunction = function(){
	doQuery(${vo.traceStatus},'doQuery');
}
temp_json = temp_json +"}";
	openwindow('${omsHost}/yitiansystem/ordergmt/asmTraceHandle/showTraceProc.sc?orderTraceId='+orderTraceId+'&orderSubNo='+orderSubNo+'&queryVoJsonStr='+temp_json+'&pageNo=<#if pageFinder??>${pageFinder.pageNo?default("")}</#if>'+'&isopen=0','1000','800','查看工单');
}
//跳转到修改商家售后地址页面
function queryMerchantsDialogDetail(id){
    if(id!=""){
    	openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_update_merchant_reject_address.sc?id="+id,650,500,"添加退货地址");
	}
}
//工单类型和问题归属级联
function chooseSecondProblemId(){
	var problemId=$("#problemId option:selected").val();
	var html="<option value='-1'>请选择问题归属</option>";
	if(problemId !=null && problemId !=""){
		$.ajax({
			url: '${BasePath}/yitiansystem/merchants/aftersale/dialogList_SecondProblemList.sc?problemId='+problemId+'&time='+(new Date()).valueOf(),
			async: true,
			dataType: "json",
			success: function(data){
				if(data!=""){
					$.each(data["problemList"],function(i,item){
						html+="<option value="+item.id+">"+item.name+"</option>";
					});
				}
				$("#secondProblemId").html(html);
			},
			error:function(xhr, textStatus, errorThrown){ 
				alert("服务器错误,请稍后再试!");
				return;
			}
		});
	}
}
</script>