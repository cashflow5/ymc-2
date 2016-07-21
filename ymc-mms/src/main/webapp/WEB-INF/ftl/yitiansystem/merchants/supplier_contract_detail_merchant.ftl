<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
  <link rel="stylesheet" type="text/css" href="${BasePath}/css/yitiansystem/merchants/supplier-contracts.css"/>
  <link rel="stylesheet" type="text/css" href="${BasePath}/css/yitiansystem/merchants/supplier_manage.css"/>
  <script type="text/javascript" src="${BasePath}/js/jquery-1.8.3.min.js"></script>   
  <script type="text/javascript" src="${BasePath}/js/yitiansystem/merchants/view_supplier.js"></script>
  <script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>   
<title>优购商城--商家后台</title>
<!-- 日期 -->
<script src="${BasePath}/js/common/form/datepicker/WdatePicker.js" type="text/javascript"></script>
<script src="${BasePath}/js/common/common_tools.js?version=20151210" type="text/javascript"></script>
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
var basePath = '${BasePath}';
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
	<#if listKind??&&listKind=="AUDIT_CONTRACT">
	<div class="toolbar">
		<div class="t-content">		
			<div class="btn" onclick="toFinanceAuditContract('${supplierContract.id}','${listKind}');">
				<span class="btn_l"></span>
	        	<b class="ico_btn complete"></b>
	        	<span class="btn_txt">立即审核</span>
	        	<span class="btn_r"></span>
        	</div> 
		</div>
	</div>
    </#if>
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr">
				    <span><a href="${BasePath}/yitiansystem/merchants/businessorder/getContractDetail_merchant.sc?id=${(supplierContract.id)}<#if listKind??>&listKind=${listKind}</#if>" class="btn-onselc">招商供应商合同</a></span>
				</li>
				<li>
                    <span><a href="${BasePath}/yitiansystem/merchants/businessorder/getContractDetail_log.sc?contractNo=${(supplierContract.contractNo)}&contractId=${(supplierContract.id)}<#if listKind??>&listKind=${listKind}</#if>" class="btn-onselc">日志信息</a></span>
                </li>
			</ul>
		</div>
	 	<div class="modify"> 
	 		<form id="downloadForm" action="${BasePath}/yitiansystem/merchants/businessorder/downLoadContractAttachment.sc" method="post"> 
	 			<input type="hidden" name="fileName" id="fileName">
	 			<input type="hidden" name="realName" id="realName">
	 		</form>
	 		<div class="form-box">
                
                <#import "supplier/part_contract_page_top.ftl" as page>
                <@page.contractPageTop/>       
                
                <#import "supplier/part_detail_brand_trademark.ftl" as page>
                <@page.trademarkView/>
			</div>
	    </div>
  	</div>
</div>
</body>
</html>
