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
			<!--搜索start-->
				<form action ="${BasePath}/supply/purchase/poserror/manualPosPurchase.sc" id="submitForm" name="submitForm" method="post">
				<div class="add_detail_box">
					<p>
					<span>
						<label>POS采购单编码：</label>
						<input  type="text" name="nos" id="nos" value="${nos?default('')}"/>
						<label>pos采购类型：</label>
						<select id="brandNo" name="brandNo">
						  <option value="">请选择</option>
						 <#if sources??>				 
					      <#list sources as source>
						  <option value="<#if source??&&source.posSourceNo??>${source.posSourceNo}</#if>"> 
						  <#if source??&&source.posSourceName??>${source.posSourceName}</#if></option>
						  </#list>	
					  	 </#if>
						</select>
						<label>分仓编号：</label>
						<input  type="text" name="dbodnos" id="dbodnos" value="${dbodnos?default('')}"/>
					</span>
						</p>
						</div>
						<p class="searchbtn">
						<input id="search" name="search" class="btn-add-normal" onclick="manualPos()"  value="同步"  type="button">
						</p>
				</form>
		</div>
	</div>
</div>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator.js"></script> 
<script type="text/javascript">	
	function manualPos() {
		var nos = $("#nos").val();
		var brandNo = $("#brandNo").val();
		var dbodnos = $("#dbodnos").val();
		if(nos=='') {
			alert("请填写pos采购单号");
			return false;
		}
		if(brandNo=='') {
			alert("请选择pos采购单类型");
			return false;
		}
		if(dbodnos=='') {
			alert("请填写分仓单号");
			return false;
		}
		var form = document.getElementById("submitForm") ;
		form.submit();
	}
</script>
</body>
</html>







