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
			<div class="btn" onclick="addRejectedAddress();">
				<span class="btn_l"></span>
	        	<b class="ico_btn add"></b>
	        	<span class="btn_txt">添加退货地址</span>
	        	<span class="btn_r"></span>
        	</div> 
		</div>
	</div>
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li  class="curr">
				  <span><a href="#" class="btn-onselc">售后退货地址列表</a></span>
				</li>
			</ul>
		</div>
 <div class="modify"> 
     <form action="${BasePath}/yitiansystem/merchants/businessorder/merchant_rejected_address_list.sc?str=1" name="queryForm" id="queryForm" method="post"> 
  			  	<div class="wms-top">
                     <label>商家名称&nbsp;&nbsp;：</label>
                     <input type="text" name="supplierName" id="supplierName" value="<#if rejectedAddress??&&rejectedAddress.supplierName??>${rejectedAddress.supplierName!""}</#if>"/>&nbsp;&nbsp;&nbsp;
                      <label>商家编号&nbsp;&nbsp;：</label>
                     <input type="text" name="supplierCode" id="supplierCode" value="<#if rejectedAddress??&&rejectedAddress.supplierCode??>${rejectedAddress.supplierCode!""}</#if>"/>&nbsp;&nbsp;&nbsp;
                      <label>商品品牌&nbsp;&nbsp;：</label>
                      <input type="text" name="brand" id="brand" value="<#if brand??>${brand!""}</#if>"/>&nbsp;&nbsp;&nbsp;
                      <label>收货人姓名：</label>
                      <input type="text" name="consigneeName" id="consigneeName" value="<#if rejectedAddress??&&rejectedAddress.consigneeName??>${rejectedAddress.consigneeName!""}</#if>"/>&nbsp;&nbsp;&nbsp;
                      <br/> <label>收货人手机：</label>
                      <input type="text" name="consigneePhone" id="consigneePhone" value="<#if rejectedAddress??&&rejectedAddress.consigneePhone??>${rejectedAddress.consigneePhone!""}</#if>"/>&nbsp;&nbsp;&nbsp;
                       <label>收货人电话：</label>
                      <input type="text" name="consigneeTell" id="consigneeTell" value="<#if rejectedAddress??&&rejectedAddress.consigneeTell??>${rejectedAddress.consigneeTell!""}</#if>"/>&nbsp;&nbsp;&nbsp;
                 		<label>货品负责人：</label>
                      <input type="text" name="supplierYgContacts" id="supplierYgContacts" value="<#if rejectedAddress??&&rejectedAddress.supplierYgContacts??>${rejectedAddress.supplierYgContacts!""}</#if>"/>&nbsp;&nbsp;&nbsp;
                    <input type="button" value="搜索" onclick="queryMerchantsRejectedAddressList();" class="yt-seach-btn" />&nbsp;&nbsp;&nbsp;
              	</div>
              	</form>
                <table cellpadding="0" cellspacing="0" class="list_table">
                		<thead>
                        <tr>
                        <th>商家名称</th>
                        <th>商家编号</th>
                        <th>收货人姓名</th>
                        <th>收货人电话</th>
                        <th>优购客服电话</th>
                        <th>收货仓库邮编</th>
                        <th>收货仓库地区</th>
                        <th>收货仓库地址</th>
                        <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                       <#if pageFinder??&&pageFinder.data?? >
                    	<#list pageFinder.data as item >
	                        <tr>
		                        <td>${item.supplierName!""}</td>
		                        <td>${item.supplierCode!""}</td>
		                        <td>${item.consigneeName!""}</td>
		                        <td>${item.consigneePhone!""}</td>
		                        <td>${item.consigneeTell!""}</td>
		                        <td>${item.warehousePostcode!""}</td>
		                        <td>${item.warehouseArea!""}</td>
		                        <td>${item.warehouseAdress!""}</td>
		                        <td>
		                           <a href="#" onclick="to_updateMerchantRejectConsignment('${item.id!''}');">修改</a>&nbsp;&nbsp;
		                           <a href="#" onclick="toViewMerchantOperationLog('${item.supplierCode!''}');">日志</a>&nbsp;&nbsp;
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
//根据条件查询
function queryMerchantsRejectedAddressList(){
  document.queryForm.submit();
}

//跳转到修改商家售后地址页面
function to_updateMerchantRejectConsignment(id){
    if(id!=""){
    	openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_update_merchant_reject_address.sc?id="+id,650,500,"修改退货地址");
	}
}
function toViewMerchantOperationLog(merchantCode){
    if(merchantCode != ""){
    	openwindow("${BasePath}/yitiansystem/merchants/businessorder/viewMOperationLog.sc?operationType=AFTER_SERVICE&merchantCode="+merchantCode,860,500,"查看退货地址操作日志");
	}
}

//跳转到添加商家售后地址页面
function addRejectedAddress(){
   openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_save_merchant_reject_address.sc",650,500,"添加退货地址");
}

</script>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
