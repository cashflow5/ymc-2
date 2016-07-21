<#-- -->
<#import "add_update_information.ftl" as page>
<@page.add_update_supplier_merchant 
	pageTitle="添加招商商家"
	submitUrl="${BasePath}/yitiansystem/merchants/manage/add_supplier.sc"
	importJs='<script type=\"text/javascript\" src=\"${BasePath}/js/yitiansystem/merchants/supplier_add.js?v=20151207\"></script>'
/>