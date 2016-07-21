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
        <!--工具栏start-->
        <#import "view_title.ftl" as page>  
			<@page.viewTitle pageName="log"/>
           
            <div class="modify">
                <div class="box-blue mt10"> <h1>商家状态流转日志</h1></div>
                <div id="content_list" class="mt15">
                    <table cellpadding="0" cellspacing="0" class="list_table table_gray_0" width="100%">
                        <thead>
                            <tr>
                                <th class="w150">时间</th>
                                <th class="w100">操作人</th>
                                <th class="w100">事件</th>
                                <th class="w100">原状态</th>
                                <th class="w100">目标状态</th>
                                <th>备注内容</th>
                            </tr>
                        </thead>
                        <tbody>
                            <#if historyList?? >
	                   		<#list historyList  as item >
 
	                   		 <#if item.type?? && item.type == '1'  >
	                   		 <tr>
                                <td class="ft-cl-r">${(item.operationTime)?default('')?datetime }</td>
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

                <div class="box-blue mt30"> <h1>商家操作日志</h1></div>
                <div id="content_list" class="mt15">
                    <table cellpadding="0" cellspacing="0" class="list_table table_gray_0" width="100%">
                        <thead>
                            <tr>
                                <th class="w150">时间</th>
                                <th class="w100">操作人</th>
                                <th class="w100">操作类型</th>
                                <th>备注内容</th>
                            </tr>
                        </thead>
                        <tbody>
                           
	
                            <#if  logList?? >
	                   		<#list  logList  as item >
	                   	     <#if item.type?? && item.type == '1'  >
	                   		 <tr>
                                <td class="ft-cl-r">${(item.operated)?default('')?datetime }</td>
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
</body>
</html>
