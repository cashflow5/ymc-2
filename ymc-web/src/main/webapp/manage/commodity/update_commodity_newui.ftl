<#-- 修改商品 -->
<#import "/manage/commodity/add_update_commodity_common_newui.ftl" as page>
<@page.add_update_commodity_common_new 
	pageTitle="修改商品"
	submitUrl="updateCommodityNewui.sc"
	importJs='<script type=\"text/javascript\" src=\"${BasePath}/yougou/js/manage/commodity/updateCommodityNewui.js?${style_v}\"></script>'
	 />