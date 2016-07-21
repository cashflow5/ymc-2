<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,优购网,B网络营销系统,商品管理" />
<meta name="Description" content=" , ,B网络营销系统-商品管理" />
<link rev="stylesheet" rel="stylesheet"  type="text/css" href="css/style.css"/>
<link rev="stylesheet" rel="stylesheet" type="text/css" href="css/css.css" />
<script type="text/javascript" src="../js/common.js"></script>
<script type="text/javascript" src="../js/jquery-1.3.2.min.js" ></script>
<script type="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
<script type="text/javascript" src="${BasePath}/js/supply/supplier.js" ></script>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>

<#include "../../../yitiansystem/yitiansystem-include.ftl">

<title>B网络营销系统-采购管理-优购网</title>

</script>
</head>
<body>
<div class="toolbar">
		<div class="t-content"> <!--操作按钮start--> 
		</div>
	</div>
<div class="container">
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li>
				  <span><a href="querysupplierfinacedetail.sc?id=${supplier.id}" class="btn-onselc">银行账号</a></span>
				</li>
				<li>
				  <span><a href="querysupplierbasedetail.sc?id=${supplier.id?default(0)}" class="btn-onselc">基本信息</a></span>
				</li>
				<li> <span><a href="querysuppliercontactdetail.sc?supplier=${supplier.id}">联系人列表</a></span> </li>
				<li  class='curr'>
				  <span><a href="">更新历史</a></span>
				</li>
			</ul>
		</div>
 <div class="modify">  
 <form id="submitForm" action="querysupplierhistorydetail.sc">
 	<input type="hidden" id="supplier" name="supplier" value="${supplier.id}"/>
 <div class="blank10"></div>
				&nbsp;<a href="${BasePath}/supply/manage/supplier/supplierlist.sc" alt="返回供应商管理列表" style="color:red;">返回</a>	
				&nbsp;&nbsp;
				 <div class="blank10"></div>	
   <table cellpadding="0" cellspacing="0" class="list_table">
        		<thead>
                <tr>
                <th>商家名称</th>
                <th>商家编号</th>
                <th>操作类型</th>
                <th>操作字段</th>
                <th>修改前内容</th>
                <th>修改后内容</th>
                <th>最后更新时间</th>
                <th>操作人</th>
                </tr>
                </thead>
                <tbody>
               <#if pageFinder??&&pageFinder.data?? >
            	<#list pageFinder.data as item >
                    <tr>
                        <td>${item['supplier']!""}</td>
                        <td>${item['supplier_code']!""}</td>
                        <td>${item['processing']!""}</td>
                        <td>${item['update_field']!""}</td>
                        <td>${item['update_before']!""}</td>
                        <td>${item['update_after']!""}</td>
                        <td>${item['operation_time']!""}</td>
                        <td>${item['operator']!""}</td>
                    </tr>
                </#list>
                	<#else>
                	<tr>
                	<td colSpan="10">
                	抱歉，没有您要找的数据 
                    </td>
                    </tr>
                </#if>
              </tbody>
        </table>
				</div>					
			</div>	
			</form>
	<div class="bottom clearfix"> 
		<!-- 翻页标签 --> 
		<#import "../../../common.ftl"  as page>
		<@page.queryForm formId="submitForm" />
	</div>			
		</div>
			
</body>
</html>

<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>