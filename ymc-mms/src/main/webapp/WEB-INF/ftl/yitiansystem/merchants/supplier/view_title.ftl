<!-- 自定义翻页标签宏 -->
<#macro viewTitle pageName>    
    <#if listKind??&&listKind=="B_APPROVAL">
	<div class="toolbar">
		<div class="t-content">		
			<div class="btn" onclick="bussinessAuditMerchants('${supplier.id}','${listKind}');">
				<span class="btn_l"></span>
	        	<b class="ico_btn complete"></b>
	        	<span class="btn_txt">立即审核</span>
	        	<span class="btn_r"></span>
        	</div> 
		</div>
	</div>
	<#elseif listKind??&&listKind=='F_APPROVAL'>
	<div class="toolbar">
		<div class="t-content">		
			<div class="btn" onclick="financeAuditMerchants('${supplier.id}','${listKind}');">
				<span class="btn_l"></span>
	        	<b class="ico_btn complete"></b>
	        	<span class="btn_txt">立即审核</span>
	        	<span class="btn_r"></span>
        	</div> 
		</div>
	</div>
	</#if>	
     <div class="list_content">  
     <div class="top clearfix">
        <ul class="tab showDetail">
            <li <#if pageName == 'merchant'>class="curr"</#if>>
                <span><a href="${BasePath}/yitiansystem/merchants/manage/to_view_supplier_merchant.sc?id=${supplier.id}<#if listKind??>&listKind=${listKind}</#if>"  class="btn-onselc">商家信息</a></span>
            </li>
            <li <#if pageName == 'shop'>class="curr"</#if>>
                <span><a href="${BasePath}/yitiansystem/merchants/manage/to_view_supplier_shop.sc?id=${supplier.id}<#if listKind??>&listKind=${listKind}</#if>" class="btn-onselc">店铺信息</a></span>
            </li>
            <li <#if pageName == 'contract'>class="curr"</#if>>
                <span><a href="${BasePath}/yitiansystem/merchants/manage/to_view_supplier_contract.sc?id=${supplier.id}<#if listKind??>&listKind=${listKind}</#if>" class="btn-onselc">合同信息</a></span>
            </li>
            <li <#if pageName == 'log'>class="curr"</#if>>
                <span><a href="${BasePath}/yitiansystem/merchants/manage/to_view_supplier_log.sc?id=${supplier.id}<#if listKind??>&listKind=${listKind}</#if>" class="btn-onselc">日志信息</a></span>
            </li>
        </ul>
    </div>        
	 
</#macro>
