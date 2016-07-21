<#-- -->
<#import "add_update_information.ftl" as page>
<@page.add_update_supplier_merchant 
	pageTitle="修改招商商家"
	submitUrl="${BasePath}/yitiansystem/merchants/manage/update_supplier_merchant.sc"
	importJs='<script type=\"text/javascript\" src=\"${BasePath}/js/yitiansystem/merchants/supplier_update.js?v=20151207\"></script>'
/>