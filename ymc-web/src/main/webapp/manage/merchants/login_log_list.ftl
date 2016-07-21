<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-登录日志列表</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>

<script type="text/javascript">

</script>
</head>

<body>
 

<div class="main_container">
	<div class="normal_box">
		<p class="title site">当前位置：商家中心 &gt; 首页</p>
		<div class="tab_panel">
			<ul class="tab">
				<li class="curr">
					<span>登录日志</span>
				</li>
			
			</ul>
			<div class="tab_content"> 
				
				<!--搜索start-->
				<div class="search_box">
					<form name="queryVoform" id="queryVoform" action="${BasePath}/merchants/login/queryLoginLog.sc" method="post">
						
					</form>
				</div>
				<!--列表start-->
				<table class="list_table">
					<thead>
						<tr>
							<th width="100">账号</th>
							<th width="80">登录时间</th>
							<th width="100">IP</th>
							<th width="100">所在地</th>

						</tr>
					</thead>
					<tbody>
						
					<#if pageFinder?? && (pageFinder.data)?? > 
						<#list pageFinder.data as item>
							<tr>
								<td>${item.loginName!""}</td>
								<td>								
									${item.operated?string("yyyy-MM-dd HH:mm:ss")!""}
								</td>
								<td>${item.loginHost!""}</td>
								<td>${item.loginAddress!''}</td>
								
							</tr>
						</#list>
					<#else>
						<tr>
							<td class="td-no" colspan="7">暂无数据!</td>
						</tr>
					</#if>
					
					</tbody>
					
				</table>
				<!--列表end--> 
				<#if pageFinder ??>
					<!--分页start-->
					     <div class="page_box">
							<#import "/manage/widget/common.ftl" as page>
							<@page.queryForm formId="queryVoform"/>
						 </div>
					<!--分页结束--> 
				</#if>
			</div>
		</div>
	</div>
	 
	 </div>
</body>
</html>