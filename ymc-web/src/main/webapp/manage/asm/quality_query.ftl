<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/jquery-css/jquery-ui-1.9.0.custom.min.css?${style_v}"/>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-ui-1.9.0.custom.min.js"></script>

<title>优购商家中心-质检查询</title>
<style>
.search_box label {
    width: 90px;
}
.list_table tr.on td .list_detail_table tr td{background:#fff;}

.ui-autocomplete { 
max-height: 100px; 
overflow-y: auto; 
overflow-x: hidden; 
padding-right: 20px; 
} 
 
.ui-autocomplete { 
height: 100px; 
} 
</style>
</head>
<body id="body">
	<div class="main_container">
		<div class="normal_box">
			<p class="title site">当前位置：商家中心 &gt; 售后 &gt; 质检查询 </p>
			<div class="tab_panel">

				<div class="tab_content">
					<!--搜索start-->
					<div class="search_box" style="padding-top: 0px;">
						<form id="queryForm" name="queryForm" action="${BasePath}/qualityquery/qualityList.sc" method="post">
							<p>
								<span><label>订单号：</label>
								<input type="text" class="inputtxt" id="orderCode" name="orderSubNo" value="${vo.orderSubNo!''}"/></span>
								<span><label>原始订单号：</label>
								<input type="text" class="inputtxt" id="outOrderId" name="outOrderId" value="${vo.outOrderId!''}"/></span>
								<span><label>收货快递单号：</label>
								<input type="text" class="inputtxt" id="expressCode" name="expressCode" value="${vo.expressCode!''}"/></span>
								<span><label style="width: 110px;">收货快递公司：</label>
								<input type="text" id="expressName" name="expressName" class="inputtxt" value="${vo.expressName!''}">
								<!--
								<select id="expressName" name="expressName" style="width:129px;">
									<option value="">请选择</option>
									<#list expressInfos as item>
										<option <#if item['id']??&&vo.expressName??&&vo.expressName==(item['express_name'])>selected</#if> value="${item['express_name']!""}">${item['express_name']?default('')}</option>
									</#list>
								</select>
								-->
								</span>
							</p>
							<p>
								<span><label>货品编号：</label>
								<input type="text" class="inputtxt" id="qaProductNo" name="qaProductNo" value="${vo.qaProductNo!''}" /></span>
								<span><label>商家货品条码：</label>
								<input type="text" class="inputtxt" id="qaInsideCode" name="qaInsideCode" value="${vo.qaInsideCode!''}"/></span>
								<span><label>商家款色编码：</label>
								<input type="text" class="inputtxt" id="supplierCode" name="supplierCode" value="${vo.supplierCode!''}"/></span>
								<span><label style="width: 110px;">商品名称：</label>
								<input type="text" class="inputtxt" id="commodityName" name="commodityName" value="${vo.commodityName!''}"/></span>
							</p>
							<p>
								<span><label>质检状态：</label>
								<select id="statusName" name="statusName" style="width:128px;">
									<option value="">请选择</option>
									<option <#if vo.statusName??&&vo.statusName=='待确认'>selected</#if> value="待确认">待确认</option>
									<option <#if vo.statusName??&&vo.statusName=='已确认'>selected</#if> value="已确认">已确认</option>
									<option <#if vo.statusName??&&vo.statusName=='已作废'>selected</#if> value="已作废">已作废</option>
								</select>
								</span>
								<span><label>收货人姓名：</label>
								<input type="text" class="inputtxt" id="userName" name="userName" value="${vo.userName!''}"/></span>
								<span><label>联系手机：</label>
								<input type="text" class="inputtxt" id="mobilePhone" name="mobilePhone" value="${vo.mobilePhone!''}"/></span>
								<span><label style="width: 110px;">售后类型：</label>
								<select id="qualityType" name="qualityType" style="width:126px;">
									<option value="">全部</option>
									<option <#if vo.qualityType??&&vo.qualityType=='退换货'>selected</#if> value="退换货">退换货</option>
									<option <#if vo.qualityType??&&vo.qualityType=='拒收'>selected</#if> value="拒收">拒收</option>
								</select></span>
							</p>
							<p>
								<span><label>质检时间：</label>
									<input type="text" style="width: 80px;" class="inputtxt" id="qaTimeStart" name="qaTimeStart" value="${vo.qaTimeStart!''}" readonly="readonly"/> 至
									<input type="text" style="width: 80px;" class="inputtxt" id="qaTimeEnd" name="qaTimeEnd" value="${vo.qaTimeEnd!''}" readonly="readonly"/>
								</span>
								<span style="padding-left:65px;"><a id="mySubmit" type="button" class="button" onclick="mySubmit()" ><span>搜索</span></a></span>
							</p>
						</form>
					</div>
					<!--搜索end-->
				<p class="blank20"></p>
			    <ul class="tab">
					<li id="tab_1" onclick="location.href='${BasePath}/qualityquery/qualityList.sc?qaTimeStart='+getThreeMonthsAgo()+'&&qaTimeEnd='+getToday()"><span>最近三个月</span></li>
					<li id="tab_2" onclick="location.href='${BasePath}/qualityquery/qualityList.sc?qualityType=1'"><span>拒收</span></li>
					<li id="tab_3" onclick="location.href='${BasePath}/qualityquery/qualityList.sc?qualityType=2'"><span>退换货</span></li>
					<li id="tab_4" onclick="location.href='${BasePath}/qualityquery/qualityList.sc?statusName=3'"><span>作废</span></li>
					<li id="tab_5" onclick="location.href='${BasePath}/qualityquery/qualityList.sc?qaTimeStart=2010-01-01&&qaTimeEnd='+getThreeMonthsAgo()"><span>三个月前</span></li>
					<span style="margin-right:35px;float: right;">
					<a id="myExportSubmit" type="button" class="button" onclick="exportQuality();" title="根据质检时间导出"><span>导出</span></a>
					</span>
				</ul>
				
					<!--列表start-->
					<style>
						.common_proitm td{border:1px solid #f2f2f2;}
						.common_proitm .line_gap td{border:none;}
					</style>
					<table class="common_lsttbl mt10">
					        <col width="250" />
                            <col width="110" />
                            <col width="160" />
                            <col width="80" />
                            <col width="60" />
                            <col width="80" />
                            <col width="50" />
                            <col width="50" />
                            <col width="50" />
						<thead>
							<tr>
								<th>商品名称</th>
								<th>商家货品条码</th>
								<th>物流信息</th>
								<th>质检时间</th>
								<th>质检结果</th>
								<th>是否异常收货</th>
								<th>状态</th>
								<th>类型</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody class="common_proitm">
						 <#if pageFinder?? && (pageFinder.data)?? && pageFinder.data?size gt 0> 
							<#list pageFinder.data as item>
							<tr class="line_gap">
                                	<td colspan="9"></td>
                            </tr>
                            <tr class="proitm_hd">
                              <td colspan="9">
                                  <span class="ml5 fl" style="width: 170px;">订单号：<a href="javascript:;" onclick="javascript:toDetail('${item['order_sub_no']?default('')}')">${item['order_sub_no']?default('')}</a></span>
                                  <span class="ml20 fl" style="width: 120px;">收货人：${item['user_name']?default('')}</span>
                                  <!--span class="ml20">联系方式：${item['mobile_phone']?default('')}</span-->
                             </td>
                            </tr>
							<#list item.asmQcDetail as detail>
							<tr>
			                    <td><div style="width:98%;float:left;word-break:break-all;text-align:left;"><img src="${detail.picUrl?default('')}" align ="left" width="40" height="40" /><a href="${detail.commodityUrl?default('')}" target="_blank">${detail.prodName?default('')}</a></div></td>
			                    <td>${detail.insideCode?default('')}</td>
			                    <td>${detail.expressName?default('')}</br><strong>${detail['expressCode']?default('')}</strong></td>
			                    <td>${detail.qaDate?string("yyyy-MM-dd HH:mm:ss")}</td>
                                <td><#if detail.qualityType!='拒收'>${detail.isPass?default('')}<#else>-</#if></td>
                                <td>${detail.isException?default('')}</td>
                                <td>${detail.status?default('')}</td>
                                <td><strong>${detail.qualityType?default('')}</strong></td>
                                <#if detail_index == 0><td rowspan="${item.asmQcDetail?size}"><a href="${BasePath}/qualityquery/returnDetail.sc?orderSubNo=${item.order_sub_no?default('')}" target="_blank">查看</a></td></#if>
                            </tr>
							</#list>
						  </#list>
						<#else>
							<tr>
								<td colspan="12" class="td-no">暂无记录！</td>
							</tr>
						</#if>
						</tbody>
					</table>
					<!--列表end-->
				<!--分页start-->
				<#if pageFinder??&&pageFinder.data??&&pageFinder.data?size gt 0>
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
	<iframe id="exportIframe" name="exportIframe" 
	frameborder="0" style="display:none;background: none; border: 0px currentColor; 
	border-image: none; left: -9999em; top: -9999em; position: absolute;" 
	allowtransparency="true"></iframe>
</body>
<script>
$("#qaTimeStart").calendar({maxDate:'#qaTimeEnd'});
$("#qaTimeEnd").calendar({minDate:'#qaTimeStart'});
if("${vo.qualityType?default('')}"=="拒收"){
$("#tab_2").addClass('curr').siblings().removeClass('curr');
}else if("${vo.qualityType?default('')}"=="退换货"){
$("#tab_3").addClass('curr').siblings().removeClass('curr');
}else if("${vo.statusName?default('')}"=="已作废"){
$("#tab_4").addClass('curr').siblings().removeClass('curr');
}else if("${vo.qaTimeStart?default('')}"==getThreeMonthsAgo()&&"${vo.qaTimeEnd?default('')}"==getToday()){
$("#tab_1").addClass('curr').siblings().removeClass('curr');
}else if("${vo.qaTimeEnd?default('')}"==getThreeMonthsAgo()){
$("#tab_5").addClass('curr').siblings().removeClass('curr');
}



$(document).ready(function(){
	
	    $("#expressName").autocomplete({
		    source: function(request, response) {
		        $.ajax({
		            url:"${BasePath}/qualityquery/queryExpress.sc",
		            dataType: "json",
		            type: "POST",
		            data: {"expressName":request.term},
                    success: function(data){
                    	
                        response( $.map(data.expressInfo, function(item){
                            return {
                            	label:item.express_name,
                            	value:item.express_name,
                            	express_frtpinyin:item.express_frtpinyin,
                            	express_no:item.express_no
                            }
                            
                        }));	
                    },
                    error:function(){
                    	alert("加载数据失败....");
                    }
		        });
		    },
		    minLength: 1,
		    focus: function( event, ui ) {
                $(this).val(ui.item.label);
                return false;
            }
            
	   });
		

});

//提交表单查询
 function mySubmit() {
	var queryForm = document.getElementById("queryForm");
	queryForm.action ="${BasePath}/qualityquery/qualityList.sc";
	queryForm.submit();
}
 
//导出
function exportQuality(){
	var exDialog=ygdg.dialog({
		content:"正在导出中，请稍等片刻<img src='${BasePath}/yougou/images/loading.gif'/>",
		title:'提示',
		cancel:function(){exDialog=null;return true;},
		cancelVal:'取消',
		lock:true
	});
	$.getJSON("${BasePath}/qualityquery/qualityExport.sc",
			{"qaTimeStart":$("#qaTimeStart").val(),"qaTimeEnd":$("#qaTimeEnd").val(),"_t":new Date().getTime()},
			function(data){
		if(data.result){
			if(exDialog){
				//location.href="http://"+data.url+"?_t="+new Date().getTime();
				location.href="${BasePath}/qualityquery/qualityDownload.sc?name="+data.url+"&_t="+new Date().getTime();
				exDialog.close();
			}
		}else{
			exDialog.content("导出失败，服务器发生错误！");
		}
	});
}

   //显示订单详情
function toDetail(orderSubNo){
	openwindow("${BasePath}/order/toOrderDetails.sc?orderSubNo="+orderSubNo+"&menuCode=MENU_ZJCX",1000,650,'订单详情');
};

function getToday(){
  var D=new Date();
  var d=D.getFullYear()+"-"+pad(1+D.getMonth())+"-"+pad(D.getDate()); 
  return d;
}
function getThreeMonthsAgo(){
  var d = new Date();
  d.setMonth(d.getMonth()-3);
  var yd=d.getFullYear()+"-"+pad(1+d.getMonth())+"-"+pad(d.getDate());
  return yd;
}
function pad(num) {
  if(num<10){
    return '0'+num;
  }else{
    return num;
  }
  
}
</script>
</html>
