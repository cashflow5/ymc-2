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
			<div class="btn" onclick="addMerchantConsumable();">
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
				  <span><a href="#" class="btn-onselc">耗材列表</a></span>
				</li>
			</ul>
		</div>
 <div class="modify"> 
     <form action="${BasePath}/yitiansystem/merchants/businessorder/to_merchant_consumable_list.sc" name="queryForm" id="queryForm" method="post"> 
  			  	<div class="wms-top">
                     <label>耗材名称：</label>
                     <input type="text" name="consumableName" id="consumableName" value="<#if merchantConsumable??&&merchantConsumable.consumableName??>${merchantConsumable.consumableName!""}</#if>"/>
                      <label>耗材货号：</label>
                     <input type="text" name="consumableCode" id="consumableCode" value="<#if merchantConsumable??&&merchantConsumable.consumableCode??>${merchantConsumable.consumableCode!""}</#if>"/>
                     <label>更新时间：</label>
                                                     从 <input type="text" style="width:80px;" name="startTime" id="startTime" value="<#if startTime??>${startTime!""}</#if>"/>到
                     <input type="text" style="width:80px;" name="endTime" id="endTime" value="<#if endTime??>${endTime!""}</#if>"/>
                    <input type="button" value="搜索" onclick="queryMerchantConsumable();" class="yt-seach-btn" />&nbsp;&nbsp;&nbsp;
              	</div>
              	
              	</form>  
                <table cellpadding="0" cellspacing="0" class="list_table">
                		<thead>
                        <tr>
                        <th>耗材名称</th>
                        <th>耗材货号</th>
                        <th>更新人</th>
                        <th>更新时间</th>
                        <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                       <#if pageFinder??&&pageFinder.data?? >
                    	<#list pageFinder.data as item >
	                        <tr>
	                          <td>${item.consumableName!""}</td>
	                           <td>${item.consumableCode!""}</td>
	                           <td>${item.creater!""}</td>
	                           <td>${item.createTime!""}</td>
	                           <td>
	                           <a href="#" onclick="updateMerchantConsumable('${item.id!''}')">修改</a>
	                           <a href="#" onclick="deleteMerchantConsumable('${item.id!''}')">删除</a>
	                           </td>
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
//删除耗材资料
function deleteMerchantConsumable(id){
 if(id!=""){
   if(confirm("是否真的删除!")){
	$.ajax({ 
		type: "post", 
		url: "${BasePath}/yitiansystem/merchants/businessorder/delete_merchantConsumable.sc?id=" + id, 
		success: function(dt){
			if("success"==dt){
			   alert("删除成功!");
			   refreshpage();
			}else{
			   alert("删除失败!");
			   refreshpage();
			}
		} 
	});
   }
 }
}

//跳转到添加耗材资料页面
function updateMerchantConsumable(id){
   openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_update_MerchantConsumable.sc?id="+id,600,400,"修改耗材资料");
}

//跳转到添加耗材资料页面
function addMerchantConsumable(){
   openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_add_merchantConsumable.sc",600,400,"添加耗材资料");
}
//查询
function queryMerchantConsumable(){
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
