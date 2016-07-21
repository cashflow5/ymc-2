<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购-商家中心-赔付工单详情</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/iconfont.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/js/fancybox/jquery.fancybox.css?v=2.1.5" media="screen" />
</head>
<body>
    <div id="newmain" class="main_bd" style="margin:0 auto;">
            <div class="main_container">
                <div class="normal_box">
                    <p class="title site">当前位置：商家中心 &gt; 售后 &gt; 赔付管理</p>
                    <div class="tab_panel relative">
                        <div class="tab_content">
                            <div class="pay-box">
                                <h3 class="pt10">赔付工单详情</h3>
                                <ul class="pay-list clearfix">
                                    <li><span class="stitle">工单号：</span><span class="s-text"><#if vo??&&vo.orderTraceNo??>${vo.orderTraceNo!''}</#if></span></li>
                                    <li><span class="stitle">创建时间：</span><span class="s-text"><#if vo??&&vo.createTime??>${vo.createTime?string("yyyy-MM-dd HH:mm:ss")}</#if></span></li>
                                    <li><span class="stitle">工单状态：</span><span class="s-text"><#if vo??&&vo.traceStatus??&&vo.traceStatus==3><span class="bold">待处理</span>
                                    	<#elseif vo??&&vo.traceStatus??&&vo.traceStatus==0>不同意（申诉中）  
                                    	<#elseif vo??&&vo.operateStatus??&&vo.operateStatus==1>同意赔付
                                    	<#elseif vo??&&vo.operateStatus??&&vo.operateStatus==2>申诉成功
                                    	<#elseif vo??&&vo.operateStatus??&&vo.operateStatus==3>申诉失败</span></#if>
                                    </li>
                                    <li><span class="stitle">订单号：</span><span class="s-text"><#if vo??&&vo.orderSubNo??>${vo.orderSubNo!''}</#if></span></li>
                                    <li><span class="stitle">订单状态：</span><span class="s-text"><#if vo??&&vo.orderStatus??>${vo.orderStatus!''}</#if></span></li>
                                    <li><span class="stitle">赔付方式：</span><span class="s-text"><#if vo??&&vo.compensateWay??&&vo.compensateWay==1>返现
                                           <#elseif  vo??&&vo.compensateWay??&&vo.compensateWay==2>礼品卡</#if></span></li>
                                    <li><span class="stitle">等值金额：</span><span class="s-text"><#if vo??&&vo.compensateAmount??><span class="cred bold">${vo.compensateAmount!0.0}</span>元</#if></span></li>
                                    <li><span class="stitle">赔付原因：</span><span class="s-text"><#if vo??&&vo.secondProblemName??>${vo.secondProblemName!''}</#if></span></li>
                                    <li class="w958 pb10"><span class="stitle">申请理由：</span><#if vo??&&vo.issueDescription??>${vo.issueDescription!''}</#if></li>
                                    <#if (vo.filePaths)??>
                                    <li class="w958 clearfix mt15"><span class="stitle fl mt20">凭证附件：</span>
	                                        <div class="nav-default fl">
	                                            <div class="navs-wrap">
	                                                <ul class="navs">
	                                               		<#list vo.filePaths?split(',') as i>
	                                               			<li class="active"><a class="fancybox fancybox-yougou" href="${i}" data-fancybox-group="gallery"><img src="${i}" title="点击看大图"/></a></li>
	                                               		</#list>
	                                                </ul>
	                                            </div>
	                                        </div>
                                    </li>
                                    </#if>
                                </ul>
                            </div>
                            
                         <#if vo??&&vo.orderSaleTraceProcs??&&(vo.orderSaleTraceProcs?size!=0)>
                         <#list vo.orderSaleTraceProcs as item >
                         	<#if item.operateType??&&item.operateType==2>
                            <div class="pay-box">
                                <h3>商家处理信息</h3>
                                <ul class="pay-list clearfix">
                                    <li><span class="stitle">处理时间：</span><span class="s-text"><#if item??&&item.createTime??>${item.createTime?string("yyyy-MM-dd HH:mm:ss")}</#if></span></li>
                                    <li><span class="stitle">处理人：</span><span class="s-text"><#if item??&&item.operateUser??>${item.operateUser!''}</#if></span></li>
                                    <li><span class="stitle">处理状态：</span><span class="s-text"><#if vo??&&vo.operateStatus??&&vo.operateStatus==1>同意赔付
                                    	<#else>不同意（申诉中）</#if></span></li>
                                    <li class="w958 clearfix"><span class="stitle">审核意见：</span>
                                        <span class="text"><#if item??&&item.processRemark??&&(''!=item.processRemark)>${item.processRemark!'无'}<#else>无</#if></span>
                                    </li>
                                    
                                    <#if item??&&(item.filePaths)?? >
	                                    <li class="w958 clearfix mt15"><span class="stitle fl mt20">凭证附件：</span>
	                                        <div class="nav-default fl">
	                                            <div class="navs-wrap">
	                                                <ul class="navs">
	                                               		<#list item.filePaths?split(',') as i>
	                                               			<li class="active"><a class="fancybox fancybox-merchant" href="${i}" data-fancybox-group="gallery"><img src="${i}" /></a></li>
	                                               		</#list>
	                                                </ul>
	                                            </div>
	                                        </div>
                                        </li>
                                    <#else>
	                                    <li class="w958 clearfix">
		                                    <span class="stitle fl">凭证附件：</span>
		                                    <span class="text">无</span>
	                                    </li>
                                    </#if>
                                    
                                </ul>
                            </div>
                            <#elseif item.operateType??&&item.operateType==1>
                            <div class="pay-box mt50">
                                <h3 class="pt10">优购处理信息</h3>
                                <ul class="pay-list clearfix">
                                    <li><span class="stitle">处理时间：</span><span class="s-text"><#if item??&&item.createTime??>${item.createTime?string("yyyy-MM-dd HH:mm:ss")}</#if></span></li>
                                    <li><span class="stitle">处理人：</span><span class="s-text"><#if item??&&item.operateUser??>${item.operateUser!''}</#if></span></li>
                                    <li><span class="stitle">处理状态：</span><span class="s-text"><#if vo??&&vo.traceStatus??&&vo.traceStatus==3><span class="bold">待处理</span>
                                    	<#elseif vo??&&vo.traceStatus??&&vo.traceStatus==0>不同意（申诉中）  
                                    	<#elseif vo??&&vo.operateStatus??&&vo.operateStatus==1>同意赔付
                                    	<#elseif vo??&&vo.operateStatus??&&vo.operateStatus==2>申诉成功
                                    	<#elseif vo??&&vo.operateStatus??&&vo.operateStatus==3>申诉失败</#if></span></li>
                                    <li class="w958 pb10"><span class="stitle">优购回复：</span><#if item??&&item.processRemark??>${item.processRemark!'无'}<#else>无</#if></li>
                                
                                 <#if item??&&(item.filePaths)?? >
	                                    <li class="w958 clearfix mt15"><span class="stitle fl mt20">凭证附件：</span>
	                                        <div class="nav-default fl">
	                                            <div class="navs-wrap">
	                                                <ul class="navs">
	                                               		<#list item.filePaths?split(',') as i>
	                                               			<li class="active"><a class="fancybox fancybox-yougou" href="${i}" data-fancybox-group="gallery"><img src="${i}" /></a></li>
	                                               		</#list>
	                                                </ul>
	                                            </div>
	                                        </div>
                                        </li>
                                    <#else>
	                                    <li class="w958 clearfix">
		                                    <span class="stitle fl">凭证附件：</span>
		                                    <span class="text">无</span>
	                                    </li>
                                    </#if>
                                 </ul>
                            </div>
                            </#if>
                           </#list>
                        <#else>
	                        <#if vo??&&vo.operateStatus??&&vo.operateStatus==1> 
	                         <div class="pay-box">
                                <h3>商家处理信息</h3>
                                <ul class="pay-list clearfix">
                                    <li><span class="stitle">处理时间：</span><span class="s-text"><#if vo??&&vo.modifyTime??>${vo.modifyTime?string("yyyy-MM-dd HH:mm:ss")}</#if></span></li>
                                    <li><span class="stitle">处理人：</span><span class="s-text"><#if  vo??&&vo.creatorName??>${vo.creatorName!''}</#if></span></li>
                                    <li><span class="stitle">处理状态：</span><span class="s-text">同意赔付</span></li>
                                    <li class="w958 clearfix"><span class="stitle">审核意见：</span><span class="text">无</span></li>
                                    <li class="w958 clearfix"><span class="stitle">凭证附件： </span><span class="text">无</span></li>
                                </ul>
                            </div>
	                        </#if>
                        </#if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>


</body>

<script src="${BasePath}/yougou/js/bootstrap.js"></script>
<script src="${BasePath}/yougou/js/navs.js"></script>
<script src="${BasePath}/yougou/js/fancybox/jquery.fancybox.pack.js?v=2.1.5"></script>
<script type="text/javascript">
    $(function() {
		$('.fancybox-merchant').fancybox();//
		$('.fancybox-yougou').fancybox();//
    });
    
</script>
</html>