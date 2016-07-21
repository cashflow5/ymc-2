<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
  <link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
  <link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
  <link rel="stylesheet" type="text/css" href="${BasePath}/css/yitiansystem/merchants/supplier-contracts.css"/>
  <link rel="stylesheet" type="text/css" href="${BasePath}/css/yitiansystem/merchants/supplier_manage.css"/>
  
  <script type="text/javascript" src="${BasePath}/js/jquery-1.8.3.min.js"></script>  
  <script type="text/javascript" src="${BasePath}/js/yitiansystem/merchants/view_supplier.js"></script>
  <script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>    
  <script>
	var basePath = '${BasePath}';
	$(function(){
		$(".download").live("click",function(){	 	
			var fileName = $(this).attr("fileName");
			var realName = $(this).attr("realName");
			$("#fileName").val(fileName);
			$("#realName").val(realName);
			$("#downloadForm").submit();
		});
	});
	</script>  
  <title>优购商城--商家后台</title>
  </head>
<body>	
    <div class="container">
        <!--工具栏start-->
        
            <#import "view_title.ftl" as page>  
			<@page.viewTitle pageName="merchant"/>
			  
            <div class="modify">
                <h1 class="supplier-title">公司信息</h1>
               <div class="form-box">
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">商家名称：</label>
                            <span class="supplier-query-cont" title="<#if supplier?? && (supplier.supplier)??>${supplier.supplier}</#if>"><#if supplier?? && (supplier.supplier)??>${supplier.supplier}</#if></span>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">公司简称：</label>
                            <span class="supplier-query-cont" title="<#if supplier?? && (supplier.simpleName)??>${supplier.simpleName}</#if>"><#if supplier?? && (supplier.simpleName)??>${supplier.simpleName}</#if></span>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">营业执照经营范围：</label>
                            <span class="supplier-query-cont" title="<#if supplierExpand?? && (supplierExpand.businessRange)??>${supplierExpand.businessRange}</#if>"><#if supplierExpand?? && (supplierExpand.businessRange)??>${supplierExpand.businessRange}</#if></span>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">营业执照注册号：</label>
                            <span class="supplier-query-cont"><#if supplier?? && (supplier.businessLicense)??>${supplier.businessLicense}</#if></span>
                        </div>
                    </div>

                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">注册地址：</label>
                            <span class="supplier-query-cont">
                            <#if supplierExpand??>		
								${(supplierExpand.registerProvince)!''}
								${(supplierExpand.registerCity)!''}
								${(supplierExpand.registerArea)!''}
							</#if>
							</span>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">营业执照有效期：</label>
                            <span> 
								<#if supplierExpand??> 
								<#if (supplierExpand.businessValidityStart)??>${supplierExpand.businessValidityStart}</#if> 
								至 <#if supplierExpand.businessValidityEnd??>${supplierExpand.businessValidityEnd}</#if>
								<#if supplierExpand.businessRemainingDays??>
								<#if (supplierExpand.businessRemainingDays<90) && (supplierExpand.businessRemainingDays>0)>
								(<i class="Orange">即将过期，请及时更新</i>)
								<#elseif supplierExpand.businessRemainingDays<=0>
								 (<i class="Red">已过期请立即更新</i>)
								</#if>
								</#if>
								</#if>
							</span>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">详细地址：</label>
                            <span class="supplier-query-cont" title="<#if supplierExpand?? && (supplierExpand.registerDetails)??>${supplierExpand.registerDetails}</#if>"> <#if supplierExpand?? && (supplierExpand.registerDetails)??>${supplierExpand.registerDetails}</#if></span>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">营业执照副本：</label>
                            <#if supplierContract??&&supplierContract.attachmentList??&&(supplierContract.attachmentList?size>0)>
				              <#list supplierContract.attachmentList as item >
				                <#if item['attachmentType']=='9'>
				                  [<a href="javascript:void(0)" class="download blue" fileName="${item['attachmentName']!''}" realName="${item['attachmentRealName']!''}">查看</a>]
				                </#if>
				              </#list>
				            </#if>
                        </div>
                    </div>
                </div>

               <h1 class="supplier-title">组织机构代码证</h1>
                <div class="form-box">
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">组织机构代码：</label>
                            <span class="supplier-query-cont"><#if supplier?? && (supplier.institutional)??>${supplier.institutional}</#if></span>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">组织机构代码证：</label>
                            <#if supplierContract??&&supplierContract.attachmentList??&&(supplierContract.attachmentList?size>0)>
				              <#list supplierContract.attachmentList as item >
				                <#if item['attachmentType']=='5'>
				                  [<a href="javascript:void(0)" class="download blue" fileName="${item['attachmentName']!''}" realName="${item['attachmentRealName']!''}">查看</a>]
				                </#if>
				              </#list>
				            </#if>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">组织机构代码证有效期：</label>
                            <span>
							<#if supplierExpand??> 
							<#if (supplierExpand.institutionalValidityStart)??>${supplierExpand.institutionalValidityStart}</#if> 
							至 <#if supplierExpand.institutionalValidityEnd??>${supplierExpand.institutionalValidityEnd}</#if>
							<#if supplierExpand.institutionalRemainingDays??>
							<#if (supplierExpand.institutionalRemainingDays<90) && (supplierExpand.institutionalRemainingDays>0)>
							(<i class="Orange">即将过期，请及时更新</i>)
							<#elseif supplierExpand.institutionalRemainingDays<=0>
							 (<i class="Red">已过期请立即更新</i>)
							</#if>
							</#if>
							</#if>
							</span>
                        </div>
                    </div>
                </div>

                <h1 class="supplier-title">税务登记证</h1>
                <div class="form-box">
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">纳税人识别号：</label>
                            <span class="supplier-query-cont"><#if supplier?? && supplier.tallageNo??>${supplier.tallageNo}</#if></span>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">税务登记证：</label>
                            <#if supplierContract??&&supplierContract.attachmentList??&&(supplierContract.attachmentList?size>0)>
				              <#list supplierContract.attachmentList as item >
				                <#if item['attachmentType']=='6'>
				                  [<a href="javascript:void(0)" class="download blue" fileName="${item['attachmentName']!''}" realName="${item['attachmentRealName']!''}">查看</a>]
				                </#if>
				              </#list>
				            </#if>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">纳税人类型：</label>
                            <#if supplier??><#if supplier.taxplayerType == '1'><span>一般纳税人</span></#if>
                            <#if supplier.taxplayerType == '0'><span>非一般纳税人</span></#if></#if>
                        </div>
                         <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">增值税专用发票：</label>
                            <#if supplier??><#if supplier.isAddValueInvoice == 1><span>是</span></#if>
                            <#if supplier.isAddValueInvoice == 0><span>否</span></#if></#if>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">增值税税率：</label>
                            <span class="supplier-query-cont"><#if supplier?? && supplier.taxRate??>${supplier.taxRate}</#if>%</span>
                        </div>
                         <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">一般纳税人资格证：</label>
                            <#if supplierContract??&&supplierContract.attachmentList??&&(supplierContract.attachmentList?size>0)>
				              <#list supplierContract.attachmentList as item >
				                <#if item['attachmentType']=='7'>
				                  [<a href="javascript:void(0)" class="download blue" fileName="${item['attachmentName']!''}" realName="${item['attachmentRealName']!''}">查看</a>]
				                </#if>
				              </#list>
				            </#if>
                        </div>
                    </div>
                </div>

                <h1 class="supplier-title">开票银行信息：</h1> 
                <div class="form-box">
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">银行开户名：</label>
                            <span class="supplier-query-cont"><#if supplier?? && supplier.contact??>${supplier.contact}</#if></span>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">开户行支行名称：</label>
                            <span class="supplier-query-cont"><#if supplier?? && supplier.bank??>${supplier.bank}</#if></span>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">开票地址：</label>                        
                            <span class="supplier-query-cont">
                            <#if supplier??>
							${(supplier.invoiceAddress)!''}                            
                            </#if>
							</span>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">电话：</label>
                            <#if supplier??>
							${(supplier.invoicePhone)!''}                            
                            </#if>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">银行账号：</label>
                            <span class="supplier-query-cont"><#if supplier?? && supplier.account??>${supplier.account}</#if></span>
                        </div>
                    </div>
                </div>
                
                <h1 class="supplier-title">结算银行信息：</h1> 
                <div class="form-box">
                    <div class="supplier-query-wrap clearfix">                        
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">开户行支行名称：</label>
                            <span class="supplier-query-cont"><#if supplier?? && supplier.clearBank??>${supplier.clearBank}</#if></span>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">银行账号：</label>
                            <span class="supplier-query-cont"><#if supplier?? && supplier.clearAccount??>${supplier.clearAccount}</#if></span>
                        </div>
                    </div>                   
                </div>
                
                <#if inventoryCode?? && inventoryCode!='' >
                <h1 class="supplier-title">仓库信息</h1>
                <div class="form-box">
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">仓库名称：</label>
                            <span class="supplier-query-cont"><#if warehouseName?? >${warehouseName!''}</#if></span>
                        </div>
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">仓库编码：</label>
                            <span class="supplier-query-cont">${inventoryCode!''}</span>
                        </div>
                    </div>
                </div>  
                </#if>
            </div>
        </div>
    </div>
<form id="downloadForm" action="${BasePath}/yitiansystem/merchants/businessorder/downLoadContractAttachment.sc" method="post"> 
	<input type="hidden" name="fileName" id="fileName">
	<input type="hidden" name="realName" id="realName">
</form>    
<script>
$(function(){
	$(".download").live("click",function(){	 	
		var fileName = $(this).attr("fileName");
		var realName = $(this).attr("realName");
		$("#fileName").val(fileName);
		$("#realName").val(realName);
		$("#downloadForm").submit();
	});
});
</script>
</body>
</html>
