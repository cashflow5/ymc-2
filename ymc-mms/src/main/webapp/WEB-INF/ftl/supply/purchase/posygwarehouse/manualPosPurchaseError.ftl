<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,优购网,B网络营销系统,商品管理" />
<meta name="Description" content=" , ,B网络营销系统-商品管理" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<title>B网络营销系统-采购管理-优购网</title>
</head>
<body>
<div class="container"> 
	<!--工具栏start-->
	<div class="toolbar">
		<div class="t-content">
		</div>
	</div>
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr"><span>POS手动同步</span></li>
			</ul>
		</div>
		<!--当前位置end-->
		
		<div class="modify"> 
			<table class="list_table" cellspacing="0" cellpadding="0" border="0" style="white-space:nowrap;">
				<thead> 
	                    <tr>
		                    <th>描述</th>
	                    </tr>              
	            </thead>
				<tbody>
	                	<#if errors??>
					  		<#list errors as error>
								  <tr> 					  
									<td>${error.syncDesc?default("&nbsp;")}</td>												
								  </tr>
					  		</#list>	
					  		<#else>
							  <tr>
							  	<td colspan="1"><B>同步成功</B></td>
							  </tr>
			 			  </#if>
	                </tbody>
	                <input id="search" name="search" class="btn-add-normal" onclick="goback()"  value="返回"  type="button">
			</table>
		</div>
	</div>
</div>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator.js"></script> 
</body>
</html>







