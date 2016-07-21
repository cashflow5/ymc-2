<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>

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
				  <span><a href="#" class="btn-onselc">已设置商家列表</a></span>
				</li>
				<li>
				  <span><a href="${BasePath}/yitiansystem/merchants/businessorder/toSetYougouAdminMerchant.sc?merchantUserId=${merchantUserId!''}&loginName=${loginName!''}" class="btn-onselc">未设置商家列表</a></span>
				</li>
			</ul>
		</div>
 		<div class="modify"> 
     		<form action="${BasePath}/yitiansystem/merchants/businessorder/toHadYougouAdminMerchant.sc" name="queryForm" id="queryForm" method="post">
     			<input type="hidden" name="merchantUserId" value="${merchantUserId!''}" />
  			  	<div class="wms-top">
                  	<label>商家编号：</label>
                    <input type="text" name="merchantCode" id="merchantCode" value="${merchantCode!""}"/>&nbsp;
                    <label>商家名称：</label>
                    <input type="text" name="merchantName" id="merchantName" value="${merchantName!""}"/>&nbsp;
                    <label>合作模式：</label>
                    <select name="isInputYougouWarehouse" id="isInputYougouWarehouse">
                        <option value="">请选择状态</option>
                       	<#list statics['com.belle.other.model.pojo.SupplierSp$CooperationModel'].values()?sort_by('description')?reverse as item>
                       	<option value="${item.ordinal()}" <#if isInputYougouWarehouse?default(-1) == item.ordinal()>selected="selected"</#if>>${item.description}</option>
                       	</#list>
                     </select>&nbsp;
                    <input type="button" value="搜索" onclick="queryYougouAdminMerchant();" class="yt-seach-btn" />&nbsp;&nbsp;
              	</div>
            </form>
            <table cellpadding="0" cellspacing="0" class="list_table">
            	<thead>
                    <tr>
                    	<th>商家编号</th>
                        <th>商家名称</th>
                    	<th>合作模式</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                </thead>
                    <tbody>
                       	<#if pageFinder??&&pageFinder.data?? >
                    	<#list pageFinder.data as item >
	                        <tr>
	                        	<td>${(item.supplier_code)!""}</td>
		                        <td>${(item.supplier)!""}</td>
	                        	<td>
			                        <#if item.is_input_yougou_warehouse??>
			                        ${statics['com.belle.other.model.pojo.SupplierSp$CooperationModel'].values()[item.is_input_yougou_warehouse].description}
			                        </#if>
	                        	</td>
	                        	<td><#if (item.is_valid)??&&item.is_valid==1>启用<#elseif (item.is_valid)??&&item.is_valid==-1>停用<#else>新建</#if></td>
		                        <td>
			                        <a href="#" onclick="toDelYougouAdminsMerchant('${item['id']!''}')">删除</a>
		                        </td>
	                        </tr>
                        </#list>
                        <#else>
                        	<tr>
                        	<td colspan="10" align="center">
                        	暂未设置任何商家
	                        </td>
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
function queryYougouAdminMerchant(){
   document.queryForm.submit();
}
function toSetYougouAdminsMerchant(){
   openwindow("${BasePath}/yitiansystem/merchants/businessorder/toSetYougouAdminMerchant.sc?merchantUserId=${merchantUserId!''}&loginName=${loginName!''}",800,650,"设置当前管理员商家");
}
function toDelYougouAdminsMerchant(id){
   window.location = "${BasePath}/yitiansystem/merchants/businessorder/toDelYougouAdminsMerchant.sc?merchantUserId=${merchantUserId!''}&loginName=${loginName!''}&id="+id;
}
</script>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
