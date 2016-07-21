<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/css/yitiansystem/merchants/merchants.css"/>

<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/jquery-1.3.2.min.js"></script>
<script type="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/js.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/order.js"></script>
<title>优购商城--商家后台</title>
<!-- 日期 -->
<script src="${BasePath}/js/common/form/datepicker/WdatePicker.js" type="text/javascript"></script>
</head>
<style>
.list_table th{text-align:center;}
table.detail-table td{padding:0px 0px 15px 0px;}
table.detail-table td.tit{text-align:right;vertical-align:top;}
.attachment a{color:#487DC8;display:block;margin-bottom:10px;}
table.trademark{border-collapse: collapse;}
table.trademark td{border:1px solid #DDD;}
table.trademark th{background:#CCCCCC;color:#000;padding:8px 0px;border:1px solid #DDD;}
table.trademark td{text-align:center;padding:8px 0px;}
</style>
<script>
$(function(){
	$(".download").live("click",function(){
		var fileName = $(this).attr("fileName");
		var realName = $(this).attr("realName");
		$("#fileName").val(fileName);
		$("#realName").val(realName);
		$("#downloadForm").submit();
	});
});
</script>
<body>
<div class="container">
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li  class="curr">
				  <span><a href="#" class="btn-onselc">普通供应商合同</a></span>
				</li>
			</ul>
		</div>
	 	<div class="modify"> 
	 		<form id="downloadForm" action="${BasePath}/yitiansystem/merchants/businessorder/downLoadContractAttachment.sc" method="post"> 
	 			<input type="hidden" name="fileName" id="fileName">
	 			<input type="hidden" name="realName" id="realName">
	 		</form>
	 		<div class="info-box">
                    <ul class="info-list clearfix">
                        <li><span class="stitle">商家名称：</span>${(supplierContract.supplier)!"--"}</li>
                        <li><span class="stitle">合同编号：</span>${(supplierContract.contractNo)!""}</li>
                        <li><span class="stitle">结算方式：</span>
                        <#if supplierContract??&&supplierContract.clearingForm='1'>底价结算</#if>
						<#if supplierContract??&&supplierContract.clearingForm='2'>扣点结算</#if>
						<#if supplierContract??&&supplierContract.clearingForm='3'>配折结算</#if>
						<#if supplierContract??&&supplierContract.clearingForm='4'>促销结算</#if>
						</li>
                        <li><span class="stitle">合同起止时间：</span>${(supplierContract.effectiveDate)!""} 至 ${(supplierContract.failureDate)!""}
						<#if supplierContract.contractRemainingDays??>
						<#if (supplierContract.contractRemainingDays<=90) && (supplierContract.contractRemainingDays>0)>
						(<i style="color:orange">即将过期，请及时更新</i>)
						<#elseif supplierContract.contractRemainingDays<=0>
						 (<i style="color:red">已过期，请立即更新</i>)
						</#if>
						</#if>
						</li>
                        <li><span class="stitle">申报人：</span>${(supplierContract.declarant)!""}</li>
                        <li><span class="stitle">合同电子版：</span>
                        <#if (supplierContract.attachmentList)??&&(supplierContract.attachmentList?size>0)>
		 					<#list supplierContract.attachmentList as item >
		 						<#if item['attachmentType']=='1'>
		 							[<a href="javascript:void(0)" class="download blue" fileName="${item['attachmentName']!''}" realName="${item['attachmentRealName']!''}">查看</a>]
		 						</#if>
		 					</#list>
	 					</#if>
                        </li>
                    </ul>
                </div>
                <div class="info-box">
                    <div class="subtitle">授权资质</div>
                    <ul class="info-list clearfix">
                        <li><span class="stitle">授权书：</span>
						<#if (supplierContract.attachmentList)??&&(supplierContract.attachmentList?size>0)>
		 					<#list supplierContract.attachmentList as item >
		 						<#if item['attachmentType']=='3'>
		 							[<a href="javascript:void(0)" class="download blue" fileName="${item['attachmentName']!''}" realName="${item['attachmentRealName']!''}">查看</a>]
		 						</#if>
		 					</#list>
	 					</#if>
                        </li>
                        <li><span class="stitle">商标注册证：</span>
						<#if (supplierContract.attachmentList)??&&(supplierContract.attachmentList?size>0)>
		 					<#list supplierContract.attachmentList as item >
		 						<#if item['attachmentType']=='4'>
		 							[<a href="javascript:void(0)" class="download blue" fileName="${item['attachmentName']!''}" realName="${item['attachmentRealName']!''}">查看</a>]
		 						</#if>
		 					</#list>
	 					</#if>
                        </li>
                        <li><span class="stitle">商标授权：</span>
                        <#if (supplierContract.markRemainingDays)??&&((supplierContract.markRemainingDays)>90) >
                        <span style="color:green">有效</span></li>
						<#elseif (supplierContract.markRemainingDays)??&&((supplierContract.markRemainingDays)>0) >
						<span style="color:orange">即将过期，请及时更新</span></li>
						<#elseif (supplierContract.markRemainingDays)??&&((supplierContract.markRemainingDays)<1) >
                        <span style="color:red">已过期，请立即更新</span></li>
                        <#else>
						</#if>
                    </ul>                
	 		<table class="authorised-list">
	 			<thead>
	 			<tr>
		            <th width="5%">商标</th>
		            <th width="5%">商标专利授权人</th>
		            <th width="2%">类别</th>
		            <th width="5%">注册商标号</th>
		            <th width="5%">注册开始日期</th>
		            <th width="5%">注册截止日期</th>
		            <th width="5%">授权级别</th>
		            <th width="5%">被授权人</th>
		            <th width="5%">授权期起</th>
		            <th width="5%">授权期止</th>
	 			</tr>
				</thead>
	 			<#assign size=1>
	 			<#if supplierContract??&&(supplierContract.trademarkList)??&&(supplierContract.trademarkList?size>0)>
 					<#list supplierContract.trademarkList as item >
 						<#if (item.trademarkSubList?size=0)>
 							<#assign size=1>
 						<#else>
 							<#assign size=(item.trademarkSubList?size)>
 						</#if>
 						<tr>
 							<td rowspan="${size}">${item.trademark!''}</td>
 							<td rowspan="${size}">${item.authorizer!''}</td>
 							<td rowspan="${size}">${item.type!''}</td>
 							<td rowspan="${size}">${item.registeredTrademark!''}</td>
 							<td rowspan="${size}">${item.registeredStartDate!''}</td>
 							<td rowspan="${size}">${item.registeredEndDate!''}</td>
 							<#if (item.trademarkSubList?size>0)>
 								<#list item.trademarkSubList as sub >
 									<#if sub_index==0>
 										<td>${sub.levelStr!''}级授权</td>
 										<td>${sub.beAuthorizer!''}</td>
 										<td>${sub.authorizStartdate!''}</td>
 										<td>${sub.authorizEnddate!''}</td></tr>
 										<#else>
 										<tr>
 											<td>${sub.levelStr!''}级授权</td>
 											<td>${sub.beAuthorizer!''}</td>
 											<td>${sub.authorizStartdate!''}</td>
 											<td>${sub.authorizEnddate!''}</td>
 										</tr>
 									</#if>
 								</#list>
 							<#else>
 								<td></td>
 								<td></td>
 								<td></td>
 								<td></td></tr>
 							</#if>
 					</#list>
	 			</#if>
	 		</table>
	 		</div>
	    </div>
  	</div>
</div>
</body>
</html>
<script type="text/javascript">
//根据合同ID加载修改信息页面
function updateContract(){
  document.location.href="${BasePath}/yitiansystem/merchants/businessorder/to_add_contract.sc?id=${(supplierContract.id)!''}";
}
</script>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
