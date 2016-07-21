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
		</div>
	</div>
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li  class="curr">
				  <span><a href="#" class="btn-onselc">物流公司列表</a></span>
				</li>
			</ul>
		</div>
 <div class="modify"> 
     <form action="${BasePath}/yitiansystem/merchants/businessorder/to_logistics_corporate_list.sc" name="queryForm" id="queryForm" method="post"> 
  			  	<div class="wms-top">
                     <label>物流公司名称：</label>
                     <input type="text" name="logisticsCompanyName" id="logisticsCompanyName" value="<#if logisticsCorporate??&&logisticsCorporate.logisticsCompanyName??>${logisticsCorporate.logisticsCompanyName!""}</#if>"/>&nbsp;&nbsp;&nbsp;
                      <label>状态：</label>
                      <select name="status" id="status">
                        <option value="-1">请选择状态</option>
                        <option value="2" <#if logisticsCorporate??&&logisticsCorporate.status??&&logisticsCorporate.status==2>selected</#if>>未启用</option>
                        <option value="1" <#if logisticsCorporate??&&logisticsCorporate.status??&&logisticsCorporate.status==1>selected</#if>>启用</option>
                      </select>
                    <input type="button" value="搜索" onclick="queryLogisticsCorporate();" class="yt-seach-btn" />&nbsp;&nbsp;&nbsp;
              	</div>
              	</form>
                <table cellpadding="0" cellspacing="0" class="list_table">
                		<thead>
                        <tr>
                        <th>物流公司</th>
                        <th>是否已启用</th>
                        <th>物流公司网址</th>
                        <th>联系人</th>
                        <th>联系电话</th>
                        <th>快递单模板</th>
                        </tr>
                        </thead>
                        <tbody>
                       <#if pageFinder??&&pageFinder.data?? >
                    	<#list pageFinder.data as item >
	                        <tr>
	                          <td>${item.logisticsCompanyName!""}</td>
	                           <td><#if item.status??&&item.status==2>未启用<#elseif item.status??&&item.status==1>启用</#if></td>
	                           <td>${item.logisticsCompanyAddress!""}</td>
	                           <td>${item.contact!""}</td>
	                           <td>${item.telPhone!""}</td>
	                           <td><a href="#" onclick="toUpdateExpressTemplate('${item.id!''}')">修改快递单模板</a>
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
//跳转到修改快递单模块页面
function toUpdateExpressTemplate(id){
   openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_update_express_template.sc?logisticsId="+id,1000,800,"修改快递单模板");
}
//查询
function queryLogisticsCorporate(){
  document.queryForm.submit();
}
</script>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
