<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-公告列表</title>
<script type="text/javascript" src="${BasePath}/yougou/js/manage/common.util.js"></script>
</head>

<body>
	<div class="main_container">
		<div class="normal_box">
			<p class="title site">当前位置：商家中心 &gt; 公告列表</p>
			<div class="tab_panel">
				<ul class="tab">
					<li class="curr"><span>更多公告信息</span></li>
				</ul>
				<div class="tab_content">
				    <form name="queryVoform" id="queryVoform" action="${BasePath}/notice/queryAll.sc" method="post">
						<ul class="common_lst1 notice_lst mt15">
							<#if pageFinder??&&(pageFinder.data)??&&(pageFinder.data?size>0)>
								<#list pageFinder.data as item>
									<li><span class="c9 fr"><#if item.createTime??>${item.createTime?date}</#if></span><a <#if item.isRed=="1" >style="color: #FF6600;"<#else>class="cred"</#if> href="${BasePath}/notice/showdetail.sc?id=${item.id!""}">【<#if item.noticeType=="1" >新功能<#else>重要通知</#if>】${item.title!""}</a></li>
	                    	    </#list>
	                        <#else>
								<li>暂无公告</li>
							</#if>
	                    </ul>
                    </form>
                </div>
            </div>
				
			<!--分页start-->
			<#if pageFinder??&&pageFinder.data??>
				<div class="page_box">
						<#import "/manage/widget/common.ftl" as page>
						<@page.queryForm formId="queryVoform"/>
				</div>
			</#if>
			<!--分页end-->
		</div>
	</div>
</body>
</html>