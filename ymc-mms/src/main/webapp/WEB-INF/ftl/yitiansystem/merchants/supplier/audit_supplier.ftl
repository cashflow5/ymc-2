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
        <div class="list_content">
            <div class="modify">
                	
                <div class="form-box mt50">
					<form id="queryForm" name="queryForm" method="post">
                    <div>
                        <label for="" class="supplier-query-title w80"><span class="Red">*</span>审核备注：</label>
                        <textarea id="remark" name="remark" class="supplier-query-textarea w550"></textarea>
                    </div> 
                   	</form>
                    <div class="mt30">
                        <label for="" class="supplier-query-title w80"></label>
						<#if listKind??&&listKind=="B_APPROVAL">
						<a href="javascript:businessAudit('${supplierId}',true,'${listKind}');" class="yg-btn-gray c-normal"/>审核通过</a>
                        <a href="javascript:businessAudit('${supplierId}',false,'${listKind}');" class="yg-btn-gray c-normal">审核不通过</a>
						<#elseif listKind??&&listKind=='F_APPROVAL'>
						<a href="javascript:financeAudit('${supplierId}',true,'${listKind}');" class="yg-btn-gray c-normal"/>审核通过</a>
                        <a href="javascript:financeAudit('${supplierId}',false,'${listKind}');" class="yg-btn-gray c-normal">审核不通过</a>
						<#elseif listKind??&&listKind=='AUDIT_CONTRACT'>
						<a href="javascript:financeAuditContract('${supplierId}',true,'${listKind}');" class="yg-btn-gray c-normal"/>审核通过</a>
                        <a href="javascript:financeAuditContract('${supplierId}',false,'${listKind}');" class="yg-btn-gray c-normal">审核不通过</a>
						</#if>
                    </div>            
                </div>
            </div>
        </div>
    </div>

</body>
</html>
