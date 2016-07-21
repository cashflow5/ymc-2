<!-----start------>
<div class="header">
	<div class="wrap header_bd rel">
    	<a class="logo" href="${BasePath}/merchants/login/to_index.sc"><img src="${BasePath}/yougou/images/logo2.jpg" width="285" height="47" alt="商家中心" /></a>
        <p class="login_inf abs">
	                        欢迎您，<a href="${BasePath }/merchants/security/accountSecurity.sc"><#if merchantUsers??>${(merchantUsers.login_name)!''}</#if></a>
	        <a class="ml10" href="${BasePath}/merchants/login/to_Back.sc">退出</a>
	        <#if (merchantUsers.isSetMerchant)??&&merchantUsers.isSetMerchant=="true"><a style="color:#3366CC" href="javascript:;" onclick="setPresendMerchant();">[设置商家]</a></#if>
        </p>
    </div>
</div>

<div class="nav">
	<div class="wrap nav_bd clearfix">
    	<div class="bsns_name fr">当前商家：<#if merchantUsers??>${merchantUsers.supplier!''}</#if></div>
    	<ul class="fr navlst clearfix">
        	<li><a href="${BasePath}/merchants/login/to_index.sc">商家首页</a><i>|</i></li>
        	<li><a href="http://open.yougou.com" target="_blank">开放平台</a><i>|</i></li>
            <li><a href="http://www.yougou.com" target="_blank">优购商城</a><i>|</i></li>
            <li><a href="http://open.yougou.com/help/help_index.shtml" target="_blank">帮助中心</a><i>|</i></li>
            <li><a href="${BasePath}/training/training_list.sc" target="_blank">培训中心</a><i>|</i></li> 
            <li><a href="${BasePath}/merchants/feeback/list.sc">意见反馈</a></li>
        </ul>
    </div>
</div>
<script type="text/javascript">
function setPresendMerchant(){
   openwindow("${BasePath}/merchants/login/toSetMerchant.sc", 800, 620, "选择商家");
} 
<#if isSetMerchant?? && isSetMerchant=="true">
 	$(document).ready(function(){
 		setPresendMerchant();
 	 });
</#if>
</script>