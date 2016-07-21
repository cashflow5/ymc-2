<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-售后管理-赔付管理列表</title>
<style>
.search_box label {
    width: 90px;
}
</style>
</head>

<script type="text/javascript" src="${BasePath}/yougou/js/jquery.form.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/countDown.js"></script>
<body>
 <div id="newmain" class="main_bd fr">
	<div class="main_container">
		<div class="normal_box">
			<p class="title site">当前位置：商家中心 &gt; 售后 &gt; 赔付管理 </p>
			<div class="tab_panel  relative">
                        <div class="tab_content"> 
                            <!--搜索start-->
                            <div class="search_box pt0">
                                <form action="${BasePath}/afterSale/compensate_list.sc" id="queryForm" name="queryForm" method="post">
                                    <!--周末标志 （不用实时刷新剩余时间） -->
                                    <input type="hidden" id="isWeekendFlag" name="isWeekendFlag" value="<#if isWeekendFlag??>${isWeekendFlag!"false"}</#if>" />
                                    
                                    <p>
                                        <span>
                                            <label style="width:110px;">工单号：</label>
                                            <input type="text" name="orderTraceNo" id="orderTraceNo" class="inputtxt" value="<#if vo??&&vo.orderTraceNo??>${vo.orderTraceNo!""}</#if>" />
                                        </span>
                                        <span>
                                            <label style="width:110px;">订单号：</label>
                                            <input type="text" name="orderSubNo" id="orderSubNo" class="inputtxt" value="<#if vo??&&vo.orderSubNo??>${vo.orderSubNo!""}</#if>" />
                                        </span>
                                        <span>
                                            <label style="width:110px;">创建时间：</label>
                                            <input type="text" name="startTime" id="startTime" value="<#if vo??&&vo.startTime??>${vo.startTime!""}</#if>" class="inputtxt" style="width:80px;" /> 至
                                       		<input type="text" name="endTime" id="endTime" value="<#if vo??&&vo.endTime??>${vo.endTime!""}</#if>" class="inputtxt" style="width:80px;" />
                                        </span>
                                    </p>
                                    <p>
                                        <span>
                                            <label style="width:110px;">工单状态：</label>
                                            <select name="status" id="status">
                                            	<option value=''>--请选择--</option>
                                            	<option value='0' <#if status??&&status=='0'>selected</#if>>待处理</option>
                                            	<option value='1' <#if status??&&status=='1'>selected</#if>>不同意（申诉中）</option>
                                            	<option value='2' <#if status??&&status=='2'>selected</#if>>同意赔付</option>
                                            	<option value='3' <#if status??&&status=='3'>selected</#if>>申诉成功</option>
                                            	<option value='4' <#if status??&&status=='4'>selected</#if>>申诉失败</option>
                                            </select>
                                        </span>
                                        <span>
                                            <label style="width:110px;">赔付原因：</label>
                                            <select name="secondProblemId" >
                                                <option value="">--请选择--</option>
	                                            <#list problemList as item>
							                   	<option value="${item.id!''}" <#if vo??&&vo.secondProblemId??&&vo.secondProblemId==item.id>selected</#if>>${item.name!''}</option>
							                 	</#list>
                                            </select>
                                        </span>
                                        <span>
                                            <a class="button" id="mySubmit" onclick="mySubmit();"><span>搜索</span></a>
                                        </span>
                                    </p>
                                </form>
                            </div>
                            <!--搜索end-->

                            <p style="position:absolute;top:82px;left:175px;"><span class="fl ml5" style="color:#e60011;">提示：请按时处理，如72小时（不包括周末）内未处理，系统则认为单据已认定无需申诉</span></p>
                            <!-- 单据打印导航tab模版引入 -->
                            <ul class="tab">
                                <li onclick="chooseTab('0');" <#if status??&&status=='0'>class="curr"</#if> ><span>待商家处理</span></li>
                                <li onclick="chooseTab('');" <#if status??&&status=='0'><#else>class="curr"</#if>><span>全部</span></li>
                            </ul>
                            
								<div class="gray-box">
									<span class="fl" style="margin-right:5px;">
										<label class="cblue fl mr5" style="_margin-top:5px;"><input id="selectAll" type="checkbox" 
										onclick="javascript:$('.list_table :checkbox:enabled').attr('checked', this.checked);"/> 全选</label>
									</span>
									<span class="fl">
										<a href="#" onclick="batchApprove();" class="btn-blue-2 fl mt3">同意赔付</a>
									</span>
								</div>
                            
                            <!--列表start-->
                            <table class="list_table">
                                <thead>
                                    <tr>
                                        <th></th>
                                        <th>工单号</th>
                                        <th>订单号</th>
                                        <th>赔付方式</th>
                                        <th>等值金额(元)</th>
                                        <th>创建时间</th>
                                        <th>赔付原因</th>
                                        <th>工单状态</th>
                                        <th>剩余时间</th>
                                        <th>操作</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <#if pageFinder??&&pageFinder.data??&&pageFinder.data?size!=0>
                    				<#list pageFinder.data as item >
                                    <tr>  <!--do_tr 这行客户端会自动显示隐藏-->
                                        <td><input type="checkbox" name="rowCheckBox" <#if item['traceStatus']??&&item['traceStatus']==3><#else>disabled</#if> 
                                        value="${item['id']!''}"/></td>
                                        <td><a href="${BasePath}/afterSale/compensate_view.sc?id=${item['id']!''}" target="_blank" >${item['orderTraceNo']!''}</a></td>
                                        <td><a href="#" onclick="javascript:toDetail('${item['orderSubNo']?default('')}')">${item['orderSubNo']!''}</a></td>
                                        <td><#if item['compensateWay']??&&item['compensateWay']==1>返现
                                           <#elseif item['compensateWay']??&&item['compensateWay']==2>礼品卡</#if></td>
                                        <td>${item['compensateAmount']!0.0}</td>
                                        <td>${item['createTime']?string("yyyy-MM-dd HH:mm:ss")}</td>
                                        <td>${item['secondProblemName']!''}</td>
                                        <td><#if item['traceStatus']??&&item['traceStatus']==3>待处理
                                        	<#elseif item['traceStatus']??&&item['traceStatus']==0>不同意（申诉中）  
                                        	<#elseif item['operateStatus']??&&item['operateStatus']==1>同意赔付
                                        	<#elseif item['operateStatus']??&&item['operateStatus']==2>申诉成功
                                        	<#elseif item['operateStatus']??&&item['operateStatus']==3>申诉失败</#if></td>
                                        
                                        <td><#if isWeekendFlag??&&isWeekendFlag=="false">
                                       		<input type="hidden" id="deadline_${item_index}" name="deadline" 
                                       			value="<#if (item.leftTime)??>${item['leftTime']}<#else>-1</#if>" />
                                        	<span id="jsTime_${item_index}"></span>
                                        	<#else><#if item.leftTimeToArray??><font color="#e60011;">${item['leftTimeToArray'][0]}</font>小时<font color="#e60011;">${item['leftTimeToArray'][1]}</font>分钟<font color="#e60011;">${item['leftTimeToArray'][2]}</font>秒</#if>
                                       		</#if>
                                        </td>
                                      
                                        <td><#if item['traceStatus']??&&item['traceStatus']==3>
                                        <a href="javascript:toReply('${item['id']!''}');">处理</a>
                                        <#else>
                                        <a href="${BasePath}/afterSale/compensate_view.sc?id=${item['id']!''}" target="_blank" >查看</a>
                                        </#if></td>
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
	  </div>
	</div>
</body>

<script type="text/javascript">

$(function() {
	$("#startTime").calendar({maxDate:'#startTime',format:'yyyy-MM-dd HH:mm:ss'});
	$("#endTime").calendar({minDate:'#endTime',format:'yyyy-MM-dd HH:mm:ss'});
	
	var isWeekendFlag = $('#isWeekendFlag').val();
	if(isWeekendFlag=="false"){
		$("input[name='deadline']").each(function(){
			var index=this.id.substring(this.id.indexOf("_")+1);
			var domTime=$("#jsTime_"+index);
			d={timeMillis:$(this).val()};
			domTime.attr('endTime',d.timeMillis);
			domTime.countDown();
		}); 
	}
});

//提交表单查询
function mySubmit() {
	document.queryForm.submit();
}
function view(id){
	openwindow("${BasePath}/afterSale/compensate_view.sc?id="+id,975,500,'赔付工单详情');
}
function toReply(id){
   location.href="${BasePath}/afterSale/to_compensate_reply.sc?id="+id;
}
function batchApprove(){
    if( $('.list_table :checkbox:enabled:checked').length>0 ){
	   ygdg.dialog.confirm("确定同意赔付吗?",function(){
	    var ids = $('.list_table :checkbox:enabled:checked').map(function(){
					  return $(this).val();
					}).get().join(",") ;
		$.ajax({ 
			type: "post", 
			url: "${BasePath}/afterSale/batch_approve.sc?ids="+ids, 
			success: function(response){
				if("success"==response){
				    ygdg.dialog.alert("批量同意赔付成功!",function(){
				    	document.location.href="${BasePath}/afterSale/compensate_handling_list.sc";
				    });
				}else{
				    ygdg.dialog.alert("批量同意赔付失败!"+response,function(){
				    	document.location.href="${BasePath}/afterSale/compensate_handling_list.sc";
				    });
				}
				
			} 
		});
	  
	   });
   }else{
   		ygdg.dialog.alert("请先选择待处理工单！");
   }
}

function chooseTab(i){
	$('#status').find("option[value="+i+"]").attr("selected",true);
	mySubmit();
}
//显示订单详情
function toDetail(orderSubNo){
	openwindow("${BasePath}/order/toOrderDetails.sc?orderSubNo="+orderSubNo+"&menuCode=MENU_GDCL",1100,500,'订单详情');
};
</script>
</html>
