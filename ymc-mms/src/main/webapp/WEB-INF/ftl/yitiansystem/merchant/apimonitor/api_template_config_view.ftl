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
			<div class="btn" onclick="gotolink('${BasePath}/merchant/api/template/api_template_config.sc');">
				<span class="btn_l"></span>
	        	<b class="ico_btn back"></b>
	        	<span class="btn_txt">返回</span>
	        	<span class="btn_r"></span>
        	</div> 
		</div>
	</div>
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li  class="curr">
				  <span><a href="#" class="btn-onselc">API权限模板查看</a></span>
				</li>
			</ul>
		</div>
 <div class="modify"> 
      <form action="${BasePath}/merchant/api/template/api_template_config_view.sc" name="queryForm" id="queryForm" method="post"> 
  	<div class="wms-top">
     <label>模板编号：</label>
     <span>${template.templateNo!''}</span>&nbsp;&nbsp;&nbsp;&nbsp;
     <input type="text" name="templateNo" id="templateNo" style="display:none;" value="<#if template??&&template.templateNo??>${template.templateNo!''}</#if>"/>
     <label>模板名称：</label>
     <span>${template.templateName!''}</span>&nbsp;&nbsp;&nbsp;&nbsp;
     <label>描述：</label>
     <span>${template.templateDesc!''}</span>&nbsp;&nbsp;&nbsp;&nbsp;
    </div>
    </form>
    <table cellpadding="0" cellspacing="0" class="list_table">
       <thead>
       <tr>
         <th>序号</th>
         <th>API代码</th>
         <th>API名称</th>
         <th colspan=2 style="text-align:center;">频率上限</th>
         <th colspan=2 style="text-align:center;">日调用次数上限</th>
         <th style="text-align:center;">是否启用频率控制</th>
         <th style="text-align:center;">是否启用日调用次数控制</th>
         </tr>
       </thead>
       <tbody>
         <#if pageFinder??&&pageFinder.data??>
         <#list pageFinder.data as item >
	     <tr>
		   <td>${item_index + 1}</td>
		   <td>${item.apiCode!""}</td>
           <td>${item.apiName!""}</td>
           <td style="text-align:right;width:70px;">${item.frequency!""}</td>
           <td><#if item.frequencyUnit==1>次/小时<#elseif item.frequencyUnit==2>次/分钟<#elseif item.frequencyUnit==3>次/秒<#else></#if></td>
           <td style="text-align:right;">${item.callNum!""}</td>
           <td>次/天</td>
           <td style="text-align:center;"><#if item.isFrequency==1>是<#elseif item.isFrequency==0>否<#else></#if></td>
           <td style="text-align:center;"><#if item.isCallNum==1>是<#elseif item.isCallNum==0>否<#else></#if></td>
	     </tr>
         </#list>
         <#else>
           <tr>
           <td colSpan="8">抱歉，没有您要找的数据 ！ </td>
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
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
