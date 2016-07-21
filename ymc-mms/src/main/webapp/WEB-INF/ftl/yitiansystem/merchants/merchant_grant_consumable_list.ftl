<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>

<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/jquery-1.3.2.min.js"></script>
<script type="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/js.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/order.js"></script>
<title>优购商城--商家后台</title>
<!-- 日期 -->
<script src="${BasePath}/js/common/form/datepicker/WdatePicker.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
</script>
<body>
<div class="container">
	<!--工具栏start--> 
	<div class="toolbar">
		<div class="t-content">
			<div class="btn" onclick="addMerchantGrantConsumable();">
				<span class="btn_l"></span>
	        	<b class="ico_btn add"></b>
	        	<span class="btn_txt">添加</span>
	        	<span class="btn_r"></span>
        	</div> 
		</div>
	</div>
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li  class="curr">
				  <span><a href="#" class="btn-onselc">商家发放耗材列表</a></span>
				</li>
			</ul>
		</div>
 <div class="modify"> 
     <form action="${BasePath}/yitiansystem/merchants/businessorder/to_merchant_grant_consumable_list.sc" name="queryForm" id="queryForm" method="post"> 
  			  	<div class="wms-top">
                     <label>商家名称：</label>
                     <input type="text" name="supplierName" id="supplierName" value="<#if merchantGrantConsumable??&&merchantGrantConsumable.supplierName??>${merchantGrantConsumable.supplierName!""}</#if>"/>
                      <label>商家名称：</label>
                     <input type="text" name="supplierCode" id="supplierCode" value="<#if merchantGrantConsumable??&&merchantGrantConsumable.supplierCode??>${merchantGrantConsumable.supplierCode!""}</#if>"/>
                      <label>耗材名称：</label>
                     <input type="text" name="consumableName" id="consumableName" value="<#if merchantGrantConsumable??&&merchantGrantConsumable.consumableName??>${merchantGrantConsumable.consumableName!""}</#if>"/>
                      <label>耗材货号：</label>
                     <input type="text" name="consumableCode" id="consumableCode" value="<#if merchantGrantConsumable??&&merchantGrantConsumable.consumableCode??>${merchantGrantConsumable.consumableCode!""}</#if>"/>
                     <br/>
                     <label>单据号：</label>
                     <input type="text" name="invoicesNo" id="invoicesNo" value="<#if merchantGrantConsumable??&&merchantGrantConsumable.invoicesNo??>${merchantGrantConsumable.invoicesNo!""}</#if>"/>
                      <label>单据类型：</label>
                      <select name="invoicesType" id="invoicesType">
                       <option value="-1">请选择单据类型</option>
                        <option value="1" <#if merchantGrantConsumable.invoicesType??&&merchantGrantConsumable.invoicesType==1>selected</#if>>发放调拨</option>
                        <option value="2" <#if merchantGrantConsumable.invoicesType??&&merchantGrantConsumable.invoicesType==2>selected</#if>>剩余登记</option>
                      </select>
                      <label>创建时间：</label>
                                                     从 <input type="text" style="width:80px;" name="startTime" id="startTime" value="<#if startTime??>${startTime!""}</#if>"/>到
                     <input type="text" style="width:80px;" name="endTime" id="endTime" value="<#if endTime??>${endTime!""}</#if>"/>
                    <input type="button" value="搜索" onclick="queryMerchantGrantConsumable();" class="yt-seach-btn" />&nbsp;&nbsp;&nbsp;
              	</div>
              	</form>
                <table cellpadding="0" cellspacing="0" class="list_table">
                		<thead>
                        <tr>
                        <th>单据号</th>
                        <th>商家名称</th>
                        <th>商家编号</th>
                        <th>耗材名称</th>
                        <th>耗材货号</th>
                        <th>单据类型</th>
                        <th>耗材数量</th>
                        <th>原单据号</th>
                        <th>创建人</th>
                        <th>创建时间</th>
                        </tr>
                        </thead>
                        <tbody>
                       <#if pageFinder??&&pageFinder.data?? >
                    	<#list pageFinder.data as item >
	                        <tr>
		                       <td>${item.invoicesNo!""}</td>
		                       <td>${item.supplierName!""}</td>
		                       <td>${item.supplierCode!""}</td>
	                           <td>${item.consumableName!""}</td>
	                           <td>${item.consumableCode!""}</td>
	                           <td><#if item.invoicesType??&&item.invoicesType==1>发放调拨<#elseif item.invoicesType??&&item.invoicesType==2>剩余登记</#if></td>
	                           <td>${item.num!""}</td>
	                           <td>${item.oldInvoicesNo!""}</td>
	                           <td>${item.creater!""}</td>
	                           <td>${item.createTime!""}</td>
	                        </tr>
                        </#list>
                        	<#else>
                        	<tr>
                        	<td colSpan="10">抱歉，没有您要找的数据 </td>
	                        </tr>
                        </#if>
                      </tbody>
                </table>
              </div>
               <div class="bottom clearfix">
			  	<#if pageFinder ??><#import "../../common.ftl" as page>
			  		<@page.queryForm formId="queryForm"/></#if>
			  </div>
              <div class="blank20"></div>
          </div>
</div>
</body>
</html>
<script type="text/javascript">
//跳转到添加耗材资料页面
function updateMerchantConsumable(id){
   openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_update_MerchantConsumable.sc?id="+id,600,400,"修改耗材资料");
}

//跳转到添加耗材资料页面
function addMerchantGrantConsumable(){
   openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_add_merchantGrantConsumable.sc",600,400,"添加耗材资料");
}
//查询
function queryMerchantGrantConsumable(){
  document.queryForm.submit();
}
$(function(){
$('#startTime').calendar({maxDate:'#endTime',format:'yyyy-MM-dd'});
$('#endTime').calendar({minDate:'#startTime',format:'yyyy-MM-dd'});
});
</script>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
