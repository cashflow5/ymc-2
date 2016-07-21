<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商城--商家后台</title>
<#include "../../../yitianwms/yitianwms-include.ftl">

<script type="text/javascript">	 
var doQueryUrl="${BasePath}/supply/purchase/posShipComparisonReport/toQueryPosShipComparison.sc";
var doExportUrl="${BasePath}/supply/purchase/posShipComparisonReport/doExportPosShipComparison.sc"
</script>
</head> 
<body>
<div class="container"> 
	<!--工具栏start-->
	<div class="toolbar">
		<div class="t-content">
			<div class="btn" onclick="javascript:doExpPosPurchaseComparison()"> <span class="btn_l" ></span> <b class="ico_btn add"></b> <span class="btn_txt">导出</span> <span class="btn_r"></span> </div>
		</div>
	</div>
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr"><span>POS发货单对比报表 </span></li>
			</ul>
		</div>
		<!--当前位置end-->
		<div class="modify"> 
				<!--普通搜索内容开始-->
				<div class="add_detail_box">
				<form name="queryForm" id="queryForm" method="POST">
				 <p>
				 </p>
			      <p>
			        <span>单据创建日期：</span>
			        <input type="text" name="beginDate" id="bTime" value="${beginDate?if_exists}" readonly="readonly" size="20" />
			        -
			        <input type="text" name="endDate" id="eTime" value="${endDate?if_exists}" readonly="readonly" size="20"  />
			        <input type="button" onclick="javascript:doQueryReport();" value="查询" class="btn-add-normal" />
			      </p>
			      <p>
				 </p>
	      	</form>
	      	</div>
				<!--普通搜索内容结束--> 	
			<!--列表start-->
			<table cellpadding="0" cellspacing="0" class="list_table">
	        <thead>
	          <tr>
	            <th>POS来源</th>
	            <th>POS发货单编码</th>
	            <th>创建时间</th>
	            <th>货品总数(POS)</th>
	            <th>是否已同步</th>
	            <th>是否在错误表</th>
	            <th>货品总数(优购)</th>
	            <th>是否差异</th>
	          </tr>
	        </thead>
	        <tbody>
	        <#if pageFinder?? && (pageFinder.data)?? > 
	        <#list pageFinder.data as item >
	        <tr>
	          <td>${item.posSourceName?if_exists}</td>
	          <td>${item.posShipCode?if_exists}</td>
	          <td>${item.createDate?if_exists}</td>
	          <td>${item.posSum?if_exists}</td>
	          <td><#if item.isSync?? && item.isSync==1>
	                                                  已同步
	              <#else>
	                                                 未同步
	              </#if></td>
	          <td><#if item.isError?? && item.isError gt 0 >
	                                                是
	              <#else>
	                                               否
	              </#if></td>
	          <td>${item.yougouSum?if_exists}</td>
	          <td><#if item.isDiff?? && item.isDiff==1>
	                                                  有
	              <#else>
	                                                 无
	              </#if></td>
	        </tr>
	        </#list> 
	        <#else>
	        <tr>
	          <td  colspan="9">没有相关记录！</td>
	        <tr> 
	        </#if>
	      </tbody>
	      </table>
	      	 <!--列表end--> 
        </div>
        
		<!-- 分页start -->
		<div class="bottom clearfix">
	    	<#import "../../../common.ftl" as page>
	      	<@page.queryForm formId="pageForm" />
		</div>
		<!-- 分页end --> 
	</div>
</div>
<form name="pageForm" id="pageForm"	action="${BasePath}/supply/purchase/posShipComparisonReport/toQueryPosShipComparison.sc" method="POST" >
        <input type="hidden" name="beginDate" value="${beginDate?if_exists}" />
        <input type="hidden" name="endDate" value="${endDate?if_exists}" />
</form>
</body>
</html>
<#include "../../../yitianwms/yitianwms-include-bottom.ftl">
<script type="text/javascript">

//导出报表
function doExpPosPurchaseComparison(){
	if($('#bTime').val()== null || $('#bTime').val()== '' || $('#eTime').val()== null || $('#eTime').val()== '' ){
		alert("请先选择起止时间！");
		return;
	}
	//异步的导出报表操作
    doWMSExport("queryForm",doExportUrl);
}
function doQueryReport() {
	if($('#bTime').val()== null || $('#bTime').val()== '' || $('#eTime').val()== null || $('#eTime').val()== '' ){
		alert("请先选择起止时间！");
		return;
	}
	var queryForm = document.getElementById("queryForm");
	queryForm.action = doQueryUrl;
	queryForm.submit();
}

$(document).ready(function() {
	$('#bTime').calendar({
		maxDate : '#eTime',
		format : 'yyyy-MM-dd',
		targetFormat : 'yyyy-MM-dd'
	});
	$('#eTime').calendar({
		minDate : '#bTime',
		format : 'yyyy-MM-dd',
		targetFormat : 'yyyy-MM-dd'
	});
});
</script>