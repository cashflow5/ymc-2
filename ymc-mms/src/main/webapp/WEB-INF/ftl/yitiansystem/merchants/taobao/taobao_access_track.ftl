<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#include "../merchants-include.ftl">
<title>淘宝api调用日志</title>
<!-- 日期 -->
<script src="${BasePath}/js/common/form/datepicker/WdatePicker.js" type="text/javascript"></script>
</head>
<body>
<div class="container">
  <div class="list_content">
  			<div class="tab_panel">
				<ul class="tab">
					<li>
						<span>淘宝api调用日志</span>
					</li>
				</ul>
    <div class="tab_content">   	
	  <div style="margin-top:10px;overflow:auto;"  class="modify">
	  <div style="width: 1024px;margin: 0 auto;">
        <form action="${BasePath}/yitiansystem/merchants/taobao/taobao_access_track.sc" name="queryForm" id="queryForm" method="post">        	
              <div class="add_detail_box">
					<p>
					    <span>
						<label>淘宝api：</label>
						  <input style="width:150px;" type="text" id="taobaoApi" name="taobaoApi" value="<#if vo??&&vo.taobaoApi??>${vo.taobaoApi}</#if>">
						</span>
					    <span style="display:none;">
						<label>淘宝店铺昵称ID：</label>
						  <input style="width:150px;" type="text" id="nickId" name="nickId" value="<#if vo??&&vo.nickId??>${vo.nickId}</#if>">
						</span>
					    <span>
						  <label>创建时间：</label>
						  <input id="startTime" name="startTime" value="<#if vo??&&vo.startTime??>${vo.startTime}</#if>" />
						  -
						  <input id="endTime" name="endTime" value="<#if vo??&&vo.endTime??>${vo.endTime}</#if>"  />
						  </span>&nbsp;&nbsp;&nbsp;
						  <input type="button" value="搜索" onclick="queryData();" class="yt-seach-btn" />&nbsp;&nbsp;&nbsp;
					</p>
				</div>         	
              	</form>
              		<style>
						.order-list-tb td{border:1px solid #f2f2f2;}
					</style>
		        <table class="order-list-tb mt10" style="width:1024px;float:left;">
			                <thead>
				                <tr>
				                <th>淘宝api</th>
				                <th>返回结果</th>
				                <th>开始时间</th>
				                <th>结束时间</th>
				                <th>操作人</th>
				                </tr>
			                </thead>
			                <tbody>
			                <#if pageFinder??&&pageFinder.data??&&pageFinder.data?size gt 0 >
				               <#list pageFinder.data as item >
						                <tr>
						                <td><#if item.taobaoApi??>${item.taobaoApi}</#if></td>
						                <td style="text-align: left;"><#if item.accessResult??>${item.accessResult}</#if></td>
						                <td><#if item.startTime??>${item.startTime}</#if></td>
						                <td><#if item.endTime??>${item.endTime}</#if></td>
						                <td><#if item.operater??>${item.operater}</#if></td>
						                </tr>
				               </#list>
				            <#else>
                        	   <tr>
                        	     <td colSpan="5">抱歉，没有您要找的数据</td>
	                           </tr>
                            </#if>
			                </tbody>
		          </table>
		          </div>
			</div>
			<div class="bottom clearfix">
			<#if pageFinder ??><#import "../../../common.ftl" as page>
			   <@page.queryForm formId="queryForm"/></#if>
			</div>
            <div class="blank20"></div>
</div>
</div>
</div>
</body>
</html>
<script type="text/javascript">
$(document).ready(function() {
$("#startTime").calendar({maxDate:'#endTime'});
$("#endTime").calendar({minDate:'#startTime'});
});
//根据条件查询
function queryData(){
  document.queryForm.submit();
}
</script>
