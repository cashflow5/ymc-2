<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>

<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/jquery-1.3.2.min.js"></script>
<script type="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/js.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/order.js"></script>
<title>优购商城--商家后台</title>
<!-- 日期 -->
<script src="${BasePath}/js/common/form/datepicker/WdatePicker.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
</script>
<body>
<div class="container">
	<!--工具栏start--> 
	<div class="toolbar">
		<div class="t-content">
		</div>
	</div>
	<div class="list_content">
		<div class="top clearfix">
		  <ul class="tab">
			   <li class='curr'> <span><a href="${BasePath}/yitiansystem/merchants/businessorder/to_querySupplierLog.sc?supplierId=<#if supplierId??>${supplierId!''}</#if>&flag=2">更新历史</a></span> </li>
			</ul>
		</div>
 <div class="modify"> 
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
       <div class="bottom clearfix">
	  	<#if pageFinder ??><#import "../../common.ftl" as page>
	  		<@page.queryForm formId="queryForm"/></#if>
	  </div>
      <div class="blank20"></div>
  </div>
</div>
</body>
</html>
<script type="text/javascript">
//根据条件查询招商信息
function querySupplierLog(){
   document.queryForm.method="post";
   document.queryForm.submit();
}
</script>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
