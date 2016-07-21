<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,优购网,B网络营销系统,采购管理" />
<meta name="Description" content=" , ,B网络营销系统-采购管理" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/jquery-1.3.2.min.js"></script>
<script type="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/js.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/order.js"></script>
<script type="text/javascript">
function serach(){
   documet.queryForm.submit();
}

//选择耗材名称传给父窗口
function checkVlale(code,name){
  if(name==""){
    alert("耗材名称为空!");
    return;
  }
	dg.curWin.document.getElementById('consumableName').value = name;
	dg.curWin.document.getElementById('consumableCode').value =code;
	closewindow();
}
</script>
<title>B网络营销系统-采购管理-优购网</title>
</head>
<body>
<div class="container">
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class='curr'> <span>添加耗材名称</span> </li>
			</ul>
		</div>
		<div class="modify">
		<form action="${BasePath}/yitiansystem/merchants/businessorder/to_addConsumableName_list.sc" method="post" id="queryForm" name="queryForm">
		<div>耗材名称:<input type="text" name="consumableName" id="consumableName" value="<#if merchantConsumable??&&merchantConsumable.consumableName??>${merchantConsumable.consumableName!''}</#if>">
		耗材条码:<input type="text" name="consumableCode" id="consumableCode" value="<#if merchantConsumable??&&merchantConsumable.consumableCode??>${merchantConsumable.consumableCode!''}</#if>">
		<input type="submit" value="查询" onclick="serach();" class="btn-add-normal"></div>
			<br/>
			</form>
			<table class="list_table" cellspacing="0" cellpadding="0" border="0">
					<tbody>
					<thead>
					<tr>
					  <th>操作</th>
					  <th>耗材条码<th>
					  <th>耗材名称<th>
					</tr>
					</thead>
						 <#if pageFinder??&&pageFinder.data?? >
	                    	<#list pageFinder.data as item >
						     <tr>
							  <td><input type="radio" name="ra" id="ra"  onclick="return checkVlale('${item.consumableCode!""}','${item.consumableName!""}');"><td>
							  <td>${item.consumableName!''}<td>
							  <td>${item.consumableCode!''}<td>
						     </tr>
						   </#list>
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
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
