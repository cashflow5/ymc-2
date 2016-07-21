<#include "../../yitiansystem/yitiansystem-include.ftl">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" />
<meta name="Description" content="" />
<title>B网络营销系统-系统管理-系统用户管理</title>
</head>
<script type="text/javascript"  src="${BasePath}/js/yitiansystem/systemmgmt/systemSMS.js"></script>


<body>

<div class="main-body" id="main_body">
	<div class="cont-nav ft-sz-12"><span><a href="#">系统管理</a> &gt; 短信发送列表 </span></div>
	<div class="pro-list">
		<div class="mb-btn-fd-bd">
			<div class="mb-btn-fd relative">
				<span class="btn-extrange absolute ft-sz-14">
					<ul class="onselect">
						<li class="pl-btn-lfbg"></li>
						<li class="pl-btn-ctbg">短信发送列表</li>
						<li class="pl-btn-rtbg"></li>
					</ul>
				</span>
			</div>
		</div>
	</div>
	<div class="div-pl">
		
		<form action="querysystemSMSList.sc" name="queryForm" id="queryForm" method="post">
		<div class="div-pl-hd ft-sz-12">
			<span>手机号：</span>
			<input name="username" class="blakn-sl" value="${systemSMS.telphone?default('')}"/>
			<a href="javascript:document.queryForm.submit();" class="btn-sh">搜索</a>
			
			<a href="sendMailTest.sc">发送短信测试</a>  
		</div>
		</form>
		
		
		<div class="yt-c-div">
            <table cellpadding="0" cellspacing="0" class="ytweb-table">
            <thead>
            <tr>
	            <th>手机号 </th>
	            <th>短信类型</th>
	            <th>发送人数</th>
	            <th>发送时间 </th>
	            <th>发送状态</th>
	            <th>内容</th>
	            <th>操作</th>
            </tr>              
            </thead>
            <tbody>
            
            
              <#if pageFinder?? && (pageFinder.data)?? >
	      		<#list pageFinder.result as item>		
	      		<tr id='Tr${item.id}'>
                    <td>
                    	<#if item.content?length lt 25 >   
							${item.telphone} 
                     	<#else>
                     		${item.telphone[0..25]}...   
                     	</#if>
                    </td>
                    <td>
                    	${item.type?default("")}
                    </td>
                     <td>
                    	${item.count?default("")}
                    </td>
                    <td>
                    	${item.sendTime?default("")}
                    </td>
                    <td>
                    	<#if item.state == "0" >
                     		失败
                     	<#elseif item.state == "1" >
                     		成功
						</#if>   
                    </td>
                     <td>
                     	<#if item.content?length lt 20 >   
							${item.content} 
                     	<#else>
                     		${item.content[0..20]}...   
                     	</#if>
                     	              	
                    </td>
                    
                    </td>
                    <td class="td0" style="text-align:left;">
                    	<a href="javascript:toShowDetail('${item.id}');" target="mbdif">查看内容</a>
                    	<a href="javascript:removesystemSMS('${item.id}');" >删除</a>
                    </td>
                </tr>
	      		</#list>	
	      	<#else>
	      		<tr><td colspan="6"><div class="yt-tb-list-no">暂无内容</div></td></tr>
			</#if>
            </tbody>
            </table> 
     	</div>
     	
		
		<div class="div-pl-bt">
			<!-- 翻页标签 -->
			<#import "../../common.ftl"  as page>
			<@page.queryForm formId="queryForm" />
			
		</div>
	</div>
</div>
</body>
</html>