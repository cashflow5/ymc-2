<#-- 修改商品 -->
<#import "/manage/taobao/update_taobao_item_new.ftl" as page>
<@page.update_taobao_item_new 
	pageTitle="修改商品"
	submitUrl="updateTaobaoItem.sc"
	importJs='<script type=\"text/javascript\" src=\"${BasePath}/yougou/js/manage/taobao/updateTaobaoNewui.js?${style_v}\"></script>'
	 /> 