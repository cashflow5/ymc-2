<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,优购网,B网络营销系统,栏目管理" />
<meta name="Description" content=" , ,B网络营销系统-栏目管理" />
<title>B网络营销系统-栏目管理-优购网</title>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-index.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<script type="text/javascript"  src="${BasePath}/js/yitiansystem/systemmgmt/integral_manage.js"></script>
</head>
<body>
<input id="basePath" value="${BasePath}" type="hidden" name="basePath"/>
<div class="container">
	<div class="toolbar">
		<div class="t-content">
			<div class="btn">
				<span class="btn_l"></span>
	        	<b class="ico_btn add"></b>
	        	<span class="btn_txt"><a href="${BasePath}/yitiansystem/systemmgmt/systemintegral/toAddIntegral.sc"> 添 加</a></span>
	        	<span class="btn_r"></span>
        	</div> 
		</div>
	</div>
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class='curr' ><span>积分设置</span></li>
			</ul>
		</div>
		<div class="modify">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="list_table">
		  		<thead>
		  			<tr>
						<th>积分规则</th>
						<th>积分</th>
						<th>描述</th>
						<th>操作</th>
		  			</tr>
		  		</thead>
  				<tbody>
  					<#if pageFinder.data??>
  					<#list pageFinder.data as item>
	  				<tr class="div-pl-list">
						<td>${item.integralName!''}&nbsp;</td>
						<td >
							${item.integral!''}&nbsp;
						</td>
						<td>
							${item.remark!''}&nbsp;
						</td>
						<td>
							<a href="toEditIntegral.sc?integralId=${item.id!''}" onclick="">编辑</a>&nbsp;&nbsp;&nbsp;
							<a href="#" onclick="deleteIntegralById(this,'${item.id!''}');">删除</a>
						</td>
	  				</tr>
	  				</#list>
	  				<#else>
	  					<tr>
	  						<td colspan="4"><div class="yt-tb-list-no" id="">暂无内容</div></td>
	  					</tr>
	  				</#if>
  				</tbody>
			</table>
		</div>
		<div class="bottom clearfix">
		    <form name="IntegralListForm" id="IntegralListForm" method="get" action="toIntegralList.sc"></form>
			<#import "../../common.ftl"  as page>
			<@page.queryForm formId="IntegralListForm" />
		</div>
	</div>
</div>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
</body>
</html>
