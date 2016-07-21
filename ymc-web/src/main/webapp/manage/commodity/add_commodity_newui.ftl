<#-- 发布商品 -->
<#import "/manage/commodity/add_update_commodity_common_newui.ftl" as page>
<@page.add_update_commodity_common_new 
	pageTitle="发布商品"
	submitUrl="saveCommodity.sc" 
	importJs='<script type=\"text/javascript\" src=\"${BasePath}/yougou/js/manage/commodity/addCommodityNewui.js?${style_v}\"></script>' 
	 />