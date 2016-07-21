
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
      <link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
    
    <!-- 排序样式 -->
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/active/css/sortfilter.css"/>
    

    <link rel="stylesheet" href="${BasePath}/yougou/styles/active/css/university.css">
    <link rel="stylesheet" href="${BasePath}/yougou/styles/active/css/offActivit_list.css">
    <title>优购商城--商家后台-活动规则</title>
</head>

<body>
    <div class="container">
        <!-- 合同信息开始 -->
        <div class="list_content">
            <div class="top clearfix">
                <ul class="tab">
                    <li>
                         <span><a href="${BasePath}/active/web/merchantOfficialActiveController/queryActiveRule.sc?activeId=${activeId}" class="btn-onselc">活动介绍</a></span>
                    </li>
                    <li class="curr">
                      <span><a href="${BasePath}/active/web/merchantOfficialActiveController/queryActiveIntroduce.sc?activeId=${activeId}" class="btn-onselc">活动规则</a></span>
                    </li>
                </ul>
            </div>
            <div class="modify">
                <div class="dl-wrap">
                    <h1 class="dl-title">活动主体设置----单品优惠</h1>
                    <dl class="dl-form clearfix">
                        <dt>活动名称：</dt>
                        <dd>${officialActive.activeName}</dd>
                    </dl>
                     <dl class="dl-form clearfix">
                        <dt>报名时间：</dt>
                        <dd> ${officialActive.signUpStartTime?string("yyyy-MM-dd HH:mm:ss ")} 至 ${officialActive.signUpEndTime?string("yyyy-MM-dd HH:mm:ss ")}</dd>
                        
                        
                        
                    </dl>
                    <dl class="dl-form clearfix">
                        <dt>审核时间：</dt>
                        <dd>${officialActive.merchantAuditStartTime?string("yyyy-MM-dd HH:mm:ss ")} 至  ${officialActive.merchantAuditEndTime?string("yyyy-MM-dd HH:mm:ss ")}</dd>
                    </dl>
                    <dl class="dl-form clearfix">
                        <dt>活动时间：</dt>
                        <dd>${officialActive.startTime?string("yyyy-MM-dd HH:mm:ss")} 至  ${officialActive.endTime?string("yyyy-MM-dd HH:mm:ss ")}</dd>
                    </dl>
                    <dl class="dl-form clearfix">
                        <dt>上传商品范围：</dt>
                        <dd>品牌：
                        <#list brands as item >
                        	    ${item}
                        </#list>                                                           
                                                                                  品类：
                         <#list cats as item >
                        	    ${item}
                        </#list>                                                             
                       </dd>
                    </dl>
                     <dl class="dl-form clearfix">
                        <dt>购买限制：</dt>
                        <dd>每个商品最高购买数量：${officialActive.maxPerProduct}</span></dd>
                    </dl>
                     <dl class="dl-form clearfix">
                        <dt>商品数量：</dt>
                        <dd>报名活动商品数量必须高于：${officialActive.minRuleAmount}</dd>
                    </dl>
                     <dl class="dl-form clearfix">
                        <dt>支持优惠券：</dt>
                        	
                    		<dd><label><input type="checkbox"   <#if officialActive.isSupportCoupons == 1>  disabled="disabled"   checked = "checked" </#if>   />支持使用优惠券</label></dd>
                    </dl>
                    <dl class="dl-form clearfix">
                    <dt>香港潮牌商品：</dt>
                        <#if officialActive.isHongkong==1>
                    		 <dd><label><input type="checkbox" checked = "checked"  disabled="disabled" /></label></dd>
                    	<#else>
                    		 <dd><label><input type="checkbox" disabled="disabled" /></label></dd>
                    	</#if>
                       
                    </dl>
                    <dl class="dl-form last clearfix">
                        <dt>支持平台：</dt>
                        <#if officialActive.platform=='ALL'>
                    		 <dd><label><input type="checkbox"  disabled="disabled" checked = "checked"  />网站</label> <label><input type="checkbox" disabled="disabled" checked = "checked"  />手机</label></dd>
                    	<#elseif officialActive.platform=='website'>
                    		 <dd><label><input type="checkbox" disabled="disabled" checked = "checked"  />网站</label> <label><input type="checkbox"  disabled="disabled" />手机</label></dd>
                    	<#elseif officialActive.platform=='mobile'>
                    		<dd><label><input type="checkbox"  disabled="disabled" />网站</label> <label><input type="checkbox" checked = "checked"  disabled="disabled" />手机</label></dd>
                    		
                    	</#if>
                        
                    </dl>
                </div>
                <div class="dl-wrap">
                    <h1 class="dl-title">活动规则设置</h1>
                   
                     <dl class="dl-form last clearfix">
                        <#if officialActive.activeType == 10>
                            <dd class="c-h-contain">
                            	<#list officialActive.activeRuleList as item>
                                <p>买${item.minRuleAmount}件，${item.decreaseAmount}折</p>          
                                </#list>                      
                            </dd>
                            </#if>
							<#if officialActive.activeType == 11>
                            <dd class="c-h-contain">
                                <#list officialActive.activeRuleList as item>
                                <p>买${item.minRuleAmount}件，${item.decreaseAmount}元</p>          
                                </#list> 
                            </dd>
                            </#if>
							<#if officialActive.activeType == 13>
                            <dd class="c-h-contain">
                                <#list officialActive.activeRuleList as item>
                                <p>满${item.minRuleAmount}元，打${item.decreaseAmount}折</p>          
                                </#list> 
                            </dd>
                            </#if>
							<#if officialActive.activeType == 2>
                            <dd>
                               	请导入活动价
                            </dd>
                            </#if>
							<#if officialActive.activeType == 1>
                            <dd class="c-h-contain">
                                <#list officialActive.activeRuleList as item>
                                <#if officialActive.loopLimit == 0>
                                <p>每满${item.minRuleAmount}元，减${item.decreaseAmount}元</p>    
                                <#else>
                                 <p>满${item.minRuleAmount}元，减${item.decreaseAmount}元</p>    
                                </#if>      
                                </#list> 
                            </dd>
                            </#if>
                    </dl>
                </div>
                <div class="dl-wrap">
                    <h1 class="dl-title">活动设置及说明</h1>
                    <dl class="dl-form clearfix">
                        <dt>活动参与对象：</dt>
                        <dd><label>
                        
                        <#list memberLevelVoList as item >
                        	<input type="checkbox"  checked = "checked"  disabled="disabled" />${item.levelName}</label>
                        </#list>
                        </dd>
                    </dl>
                   
                    <dl class="dl-form last clearfix">
                        <dt>活动说明：</dt>
                        <dd><textarea cols="50" disabled="disabled" rows="3">${officialActive.activeStatement}</textarea></dd>
                    </dl>
                </div>
                
            </div>
        </div>
        <!-- 合同信息结束 -->
    </div>
    
    <script>
        function closewind(){
            $('.aui_close').click();
            return false;
        };
    </script>
    <script type="text/javascript" src="http://10.0.30.164:8080/mms/yougou/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="http://10.0.30.164:8080/mms/yougou/js/calendar/lhgcalendar.min.js"></script>
    <script  type="text/javascript" src="static/js/supplier-contracts.js"></script>

<script>
    $(function(){
        $('#yg-del-cancle').click(function(){
              //$('.aui_close',window.parent.document).eq(0).click();
              //console.log($('.aui_close',window.parent.document).eq(0));
              window.parent.dialog_close();
        })
    })
</script>
</body>

</html>
