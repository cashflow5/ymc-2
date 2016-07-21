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
<script>
var basePath = '${BasePath}';
</script>
<title>优购商城--商家后台</title>
</head>

<body>
<div class="container">
	<#if listKind??&&listKind=="AUDIT_CONTRACT">
	<div class="toolbar">
		<div class="t-content">		
			<div class="btn" onclick="toFinanceAuditContract('${contractId}','${listKind}');">
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
				<li>
				    <span><a href="${BasePath}/yitiansystem/merchants/businessorder/getContractDetail_merchant.sc?id=${(contractId)}<#if listKind??>&listKind=${listKind}</#if>" class="btn-onselc">招商供应商合同</a></span>
				</li>
				<li class="curr">
                    <span><a href="${BasePath}/yitiansystem/merchants/businessorder/getContractDetail_log.sc?contractNo=${(contractNo)}&contractId=${(contractId)}<#if listKind??>&listKind=${listKind}</#if>" class="btn-onselc">日志信息</a></span>
                </li>
			</ul>
		</div>
	 	<div class="modify">         
                <div class="box-blue mt30"> <h1>合同状态流转日志</h1></div>
                <div id="content_list" class="mt15">
                    <table cellpadding="0" cellspacing="0" class="list_table table_gray_0" width="100%">
                        <thead>
                            <tr>
                                <th class="w150">时间</th>
                                <th class="w100">合同编号</th>
                                <th class="w100">操作人</th>
                                <th class="w100">事件</th>
                                <th class="w100">原状态</th>
                                <th class="w100">目标状态</th>
                                <th>备注内容</th>
                            </tr>
                        </thead>
                        <tbody>
                            
                          
                            
                             <#if historyList??  >
	                   		<#list historyList   as item >
	                   		 <#if  item.type?? && item.type == '0' >
	                   		 <tr>
                                <td class="ft-cl-r">${(item.operationTime)?default('')?datetime }</td>
                                 <td class="ft-cl-r">${(item.contractNo)!'' }</td>
                                <td class="ft-cl-r">${(item.operator)!'' }</td>
                                <td class="ft-cl-r">${(item.processing)!'' }</td>
                                <td class="ft-cl-r">${(item.updateBefore)!'' }</td>
                                <td class="ft-cl-r">${(item.updateAfter)!'' }</td>
                                <td class="ft-cl-r">${(item.remark)!'' }</td>
                            </tr>
                            </#if>
	                   		</#list>
	                   		</#if>
                        </tbody>
                    </table>
                </div>

                <div class="box-blue mt30"> <h1>合同操作日志</h1></div>
                <div id="content_list" class="mt15">
                    <table cellpadding="0" cellspacing="0" class="list_table table_gray_0" width="100%">
                        <thead>
                            <tr>
                                <th class="w150">时间</th>
                                <th class="w100">合同编号</th>
                                <th class="w100">操作人</th>
                                <th class="w100">操作类型</th>
                                <th>备注内容</th>
                            </tr>
                        </thead>
                        <tbody>
                            <#if  logList?? >
	                   		<#list  logList  as item >
	                   	     <#if  item.type?? && item.type == '0'  >
  
	                   		 <tr>
                                <td class="ft-cl-r">${(item.operated)?default('')?datetime }</td>
                                <td class="ft-cl-r">${(item.contractNo)!'' }</td>
                                <td class="ft-cl-r">${(item.operator)!'' }</td>
                                <td class="ft-cl-r">${(item.operationType)!'' }</td>
                                <td class="ft-cl-r">${(item.operationNotes)!'' }</td>
                             </tr>
                             </#if>
	                   		</#list>
	                   		</#if>
                        </tbody>
                    </table>
                </div>
            </div>
	    </div>
  	</div>
</div>
</body>
</html>
