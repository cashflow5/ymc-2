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
				<li>
				  <span><a href="${BasePath}/yitiansystem/merchants/businessorder/toHadYougouAdminMerchant.sc?merchantUserId=${merchantUserId!''}&loginName=${loginName!''}" class="btn-onselc">已设置商家列表</a></span>
				</li>
				<li  class="curr">
				  <span><a href="#" class="btn-onselc">未设置商家列表</a></span>
				</li>
			</ul>
		</div>
 		<div class="modify"> 
     		<form action="${BasePath}/yitiansystem/merchants/businessorder/toSetYougouAdminMerchant.sc" name="queryForm" id="queryForm" method="post"> 
     			<input type="hidden" name="merchantUserId" value="${merchantUserId!''}" />
     			<input type="hidden" name="loginName" value="${loginName!''}" />
  			  	<div class="wms-top">
                  	<label>商家编号：</label>
                    <input type="text" name="merchantCode" id="merchantCode" value="${merchantCode!""}"/>&nbsp;&nbsp;
                    <label>商家名称：</label>
                    <input type="text" name="merchantName" id="merchantName" value="${merchantName!""}"/>&nbsp;&nbsp;
                    <label>合作模式：</label>
                    <select name="isInputYougouWarehouse" id="isInputYougouWarehouse">
                        <option value="">请选择状态</option>
                       	<#list statics['com.belle.other.model.pojo.SupplierSp$CooperationModel'].values()?sort_by('description')?reverse as item>
                       	<option value="${item.ordinal()}" <#if isInputYougouWarehouse?default(-1) == item.ordinal()>selected="selected"</#if>>${item.description}</option>
                       	</#list>
                    </select>&nbsp;
                    <input type="button" value="搜索" onclick="queryYougouAdminMerchants();" class="yt-seach-btn" />
                    <br />
                    <input type="button" value="设置" onclick="saveYougouAdminMerchants();" class="yt-seach-btn" style="margin-top:20px;" />&nbsp;&nbsp;&nbsp;
              	</div>
            </form>
     		<form action="${BasePath}/yitiansystem/merchants/businessorder/toSaveYougouAdminsMerchant.sc" name="saveForm" id="saveForm" method="POST">
     		<input type="hidden" name="merchantUserId" value="${merchantUserId!''}" /> 
   			<input type="hidden" name="loginName" value="${loginName!''}" />
            <table cellpadding="0" cellspacing="0" class="list_table">
            	<thead>
                    <tr>
                    	<th><input type="checkbox" onclick="javascript:$('input[name=&quot;merchantCode&quot;]').attr('checked', this.checked);"/></th>
                    	<th>商家编号</th>
                        <th>商家名称</th>
                    	<th>合作模式</th>
                        <th>状态</th>
                    </tr>
                </thead>
                    <tbody>
                       	<#if pageFinder??&&pageFinder.data?? >
                    	<#list pageFinder.data as item >
	                        <tr>
	                        	<td><input type="checkbox" id="merchantCode" name="merchantCode" value="${(item.supplier_code)!""}"/>
	                        	<td>${(item.supplier_code)!""}</td>
		                        <td>${(item.supplier)!""}</td>
	                        	<td>
			                        <#if item.is_input_yougou_warehouse??>
			                        ${statics['com.belle.other.model.pojo.SupplierSp$CooperationModel'].values()[item.is_input_yougou_warehouse].description}
			                        </#if>
	                        	</td>
	                        	<td><#if (item.is_valid)??&&item.is_valid==1>启用<#elseif (item.is_valid)??&&item.is_valid==-1>停用<#else>新建</#if></td>
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
              </form>
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
function queryYougouAdminMerchants(){
	document.queryForm.submit();
}
function saveYougouAdminMerchants(){
	if ($('input[name="merchantCode"]:checked').size() <= 0) {
		alert('请先选择待设置的商家');
		return false;
	}
	
	document.saveForm.submit();
}
</script>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
