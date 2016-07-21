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
				  <span><a href="#" class="btn-onselc">商家发货地址列表</a></span>
				</li>
			</ul>
		</div>
 <div class="modify"> 
     <form action="${BasePath}/yitiansystem/merchants/businessorder/to_merchant_consignmentAdress_list.sc" name="queryForm" id="queryForm" method="post"> 
  			  	<div class="wms-top">
                     <label>商家名称：</label>
                     <input type="text" name="supplier" id="supplier" value="<#if supplierSp??&&supplierSp.supplier??>${supplierSp.supplier!""}</#if>"/>&nbsp;&nbsp;&nbsp;
                      <label>商家编号：</label>
                     <input type="text" name="supplierCode" id="supplierCode" value="<#if supplierSp??&&supplierSp.supplierCode??>${supplierSp.supplierCode!""}</#if>"/>&nbsp;&nbsp;&nbsp;
                      <label>状态：</label>
                      <select name="isValid" id="isValid">
                        <option value="0">请选择状态</option>
                        <option value="-1" <#if supplierSp??&&supplierSp.isValid??&&supplierSp.isValid==-1>selected</#if>>停用</option>
                        <option value="1" <#if supplierSp??&&supplierSp.isValid??&&supplierSp.isValid==1>selected</#if>>启用</option>
                        <option value="2" <#if supplierSp??&&supplierSp.isValid??&&supplierSp.isValid==2>selected</#if>>新建</option>
                      </select>
                    <input type="button" value="搜索" onclick="queryMerchantsConsignmentAdressList();" class="yt-seach-btn" />&nbsp;&nbsp;&nbsp;
              	</div>
              	</form>
                <table cellpadding="0" cellspacing="0" class="list_table">
                		<thead>
                        <tr>
                        <th>商家名称</th>
                        <th>商家编号</th>
                        <th>仓库编号</th>
                        <th>优惠券分摊比例</th>
                        <th>状态</th>
                        <th>最后更新时间</th>
                        <th>更新人</th>
                        <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                       <#if pageFinder??&&pageFinder.data?? >
                    	<#list pageFinder.data as item >
	                        <tr>
		                        <td>${item.supplier!""}</td>
		                        <td>${item.supplierCode!""}</td>
		                         <td>${item.inventoryCode!""}</td>
		                        <td>${item.couponsAllocationProportion!""}</td>
		                        <td><#if item.isValid??&&item.isValid==1>启用
			                        <#elseif item.isValid?? && item.isValid==2>新建
			                        <#elseif item.isValid?? && item.isValid==-1>停用</#if></td>
		                        <td>${item.updateDate!""}</td>
		                        <td>${item.updateUser!""}</td>
		                        <td>
		                         <#if item.isValid??&&item.isValid==1>
		                           <a href="#" onclick="to_updateMerchantConsignment('${item.id!''}');">设置发货地址</a>&nbsp;&nbsp;
		                         </#if>
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
//跳转到分配权限页面
function assignAuthority(id){
   openwindow("${BasePath}/yitiansystem/merchants/businessorder/assignAuthority.sc?id="+id,600,700,"分配商家权限");
}
function queryMerchantsConsignmentAdressList(){
  document.queryForm.submit();
}

//跳转到设置商家发货地址页面
function to_updateMerchantConsignment(id){
    if(id!=""){
    	openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_updateMerchantConsignment.sc?id="+id,600,700,"添加商家发货地址设置");
	}
}
</script>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
