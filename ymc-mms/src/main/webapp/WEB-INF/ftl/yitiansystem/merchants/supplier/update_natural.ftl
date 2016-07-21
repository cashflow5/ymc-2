<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css?34"/>
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
	<link rel="stylesheet" type="text/css" href="${BasePath}/webuploader/webuploader.css?version=2.5"/>
    <link rel="stylesheet" type="text/css" href="${BasePath}/css/yitiansystem/merchants/supplier-contracts.css"/>
    <link rel="stylesheet" type="text/css" href="${BasePath}/css/yitiansystem/merchants/supplier_manage.css"/>
    <link rel="stylesheet" type="text/css" href="${BasePath}/css/yitiansystem/merchants/natural.css" />
	
<script type="text/javascript">
var basePath = '${BasePath}';
</script>
 
    <script type="text/javascript" src="${BasePath}/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${BasePath}/js/yitiansystem/merchants/jquery.validate.js"></script>
    <script type="text/javascript" src="${BasePath}/js/yitiansystem/merchants/jquery.validate.cn.js"></script>
    <script type="text/javascript" src="${BasePath}/js/yitiansystem/merchants/jquery.validate.methods.js"></script>
    <script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script>
    <script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>
    <script type="text/javascript" src="${BasePath}/webuploader/webuploader.js?version=2.5"></script>
    <script type="text/javascript" src="${BasePath}/js/yitiansystem/merchants/natural_update.js?v=20151208"></script>
<title>优购商城--商家后台-商家列表-更新资质</title>

<style>
.loading{display:none}
</style>

</head>

<body>
    <div class="container natural update-natural-dialog">
        <!--工具栏start-->
        <div class="list_content">
            <div class="modify">
            <form action='${BasePath}/yitiansystem/merchants/manage/update_natural_and_auth.sc' method='post' id='submitForm' name='submitForm'>
            <input type="hidden" name="supplierId" id="supplierId" value="<#if supplier??>${supplier.id!''}</#if>" />
            <input type="hidden" name="supplierExpandId" id="supplierExpandId" value="<#if supplierExpand??>${(supplierExpand.id)!''}</#if>" />
		    <input type="hidden" name="contractId" id="contractId" value="<#if supplierContract??>${(supplierContract.id)!''}</#if>"><!-- 商标只和合同挂钩，因此查出合同ID -->
                <div class="form-box">
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">资质名称：</label>
                            <span>营业执照</span>
                            <#if (supplierExpand.businessRemainingDays)?? && (supplierExpand.businessRemainingDays>0) && (supplierExpand.businessRemainingDays<=90) >
                            	<span class="Orange">(即将过期，请及时更新)</span>
                            <#elseif (supplierExpand.businessRemainingDays)?? && (supplierExpand.businessRemainingDays<1 )>
                                <span class="Red">(已过期，请立即更新)</span>
                            <#else>
                            </#if>
                            
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">资质名称：</label>
                            <span>组织机构代码证</span>
                            <#if (supplierExpand.institutionalRemainingDays)?? && (supplierExpand.institutionalRemainingDays>0) && (supplierExpand.institutionalRemainingDays<=90) >
                            	<span class="Orange">(即将过期，请及时更新)</span>
                            <#elseif (supplierExpand.institutionalRemainingDays)?? && (supplierExpand.institutionalRemainingDays<1 )>
                                <span class="Red">(已过期，请立即更新)</span>
                            <#else>
                            </#if>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>上传列表：</label>
                             <a class="yg-btn-gray-2 yg-btn-update filePicker" id="filePicker_2">上传文件</a>
                            <div id="loading_2" class="loading"><img src="${BasePath}/images/loading.gif"></div>
                            
                            <div id="attachment_2" class="inline-block">
                            <#if supplierContract??&&supplierContract.attachmentList??&&(supplierContract.attachmentList?size>0)>
				              <#list supplierContract.attachmentList as item >
				                <#if item['attachmentType']=='9'>
				                  <div class="attachment_item">
				                    <input name="contract_attachment" value="9;${item['attachmentName']!''};${item['attachmentRealName']!''}" type="hidden"/>
				                    <a href="javascript:void(0)"  class="download supplier-query-cont Blue ml5" title="${item['attachmentName']!''}"  fileName="${item['attachmentName']!''}" realName="${item['attachmentRealName']!''}">${item['attachmentName']!''}</a>
				                    <a href="javascript:void(0)" class="link-del ml10 Blue">删除</a>
				                  </div>
				                </#if>
				              </#list>
				            </#if>
						  </div>
						  <p class='error-message' id="attachmentError_2"/>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>上传列表：</label>
                            <a class="yg-btn-gray-2 yg-btn-update filePicker" id="filePicker_5">上传文件</a><!--id的序号不能变，和数据库表的附件类型一致 -->
                            <div id="loading_5" class="loading"><img src="${BasePath}/images/loading.gif"></div>
                            <div id="attachment_5" class="inline-block">
                            <#if supplierContract??&&(supplierContract.attachmentList)??&&(supplierContract.attachmentList?size>0)>
				              <#list supplierContract.attachmentList as item >
				                <#if item['attachmentType']=='5'>
				                  <div class="attachment_item">
				                    <input name="contract_attachment" value="5;${item['attachmentName']!''};${item['attachmentRealName']!''}" type="hidden">
				                    <a href="javascript:void(0)"  class="download supplier-query-cont Blue ml5" title="${item['attachmentName']!''}"  fileName="${item['attachmentName']!''}" realName="${item['attachmentRealName']!''}">${item['attachmentName']!''}</a>
									<a href="javascript:void(0)" class="link-del ml10 Blue">删除</a>
				                  </div>
				                </#if>
				              </#list>
				            </#if>
						   </div>
						   <p class='error-message' id="attachmentError_5"/>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                           <label for="" class="supplier-query-title"><span class="Red">*</span>有效期：</label>
                                <input type="text" class="calendar" id="createTimeStart_1"  name="businessValidityStart" value="<#if supplierExpand?? && (supplierExpand.businessValidityStart)??>${supplierExpand.businessValidityStart}</#if>"  readonly="readonly" />至<input type="text"  class="calendar" id="createTimeEnd_1"  name="businessValidityEnd" value="<#if supplierExpand?? && supplierExpand.businessValidityEnd??>${supplierExpand.businessValidityEnd}</#if>"  readonly="readonly" />
                                <input type="checkbox" class="forever" id="foreverFor_1"  onchange="setEndForever(1);"/><label for="foreverFor_1">永久</label>
                            	<p class='error-message' id='createTimeStartError_1'/><p class='error-message' id='createTimeEndError_1'/>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>有效期：</label>
                             <input type="text" class="calendar timeStart" id="createTimeStart_2"  name="institutionalValidityStart" value="<#if supplierExpand?? && supplierExpand.institutionalValidityStart??>${supplierExpand.institutionalValidityStart}</#if>" 
                              readonly="readonly"/>至<input type="text"  class="calendar timeEnd" id="createTimeEnd_2"  name="institutionalValidityEnd" value="<#if supplierExpand?? && supplierExpand.institutionalValidityEnd??>${supplierExpand.institutionalValidityEnd}</#if>"  readonly="readonly" />
                            <input type="checkbox" id="foreverFor_2"  onchange="setEndForever(2);"/><label for="forever_2">永久</label>
                        	<p class='error-message' id='createTimeStartError_2'/><p class='error-message' id='createTimeEndError_2'/>
                       </div>
                    </div>
                </div>

                <div class="form-box">
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">资质名称：</label>
                            <span class="supplier-query-cont">税务登记证</span>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">资质名称：</label>
                            <span class="supplier-query-cont">一般纳税人资质证</span>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>上传列表：</label>
                            <a class="yg-btn-gray-2 yg-btn-update filePicker" id="filePicker_6">上传文件</a>
                            <div id="loading_6" class="loading"><img src="${BasePath}/images/loading.gif"></div>
                            <div id="attachment_6" class="inline-block">
                            <#if supplierContract??&&(supplierContract.attachmentList)??&&(supplierContract.attachmentList?size>0)>
				              <#list supplierContract.attachmentList as item >
				                <#if item['attachmentType']=='6'>
				                  <div class="attachment_item">
				                    <input name="contract_attachment" value="6;${item['attachmentName']!''};${item['attachmentRealName']!''}" type="hidden">
				                    <a href="javascript:void(0)"  class="download supplier-query-cont Blue ml5" title="${item['attachmentName']!''}"  fileName="${item['attachmentName']!''}" realName="${item['attachmentRealName']!''}">${item['attachmentName']!''}</a>
									<a href="javascript:void(0)" class="link-del ml10 Blue">删除</a>
				                  </div>
				                </#if>
				              </#list>
				            </#if>
						   </div>
						   <p class='error-message' id="attachmentError_6"/>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">上传列表：</label>
                            <a class="yg-btn-gray-2 yg-btn-update filePicker" id="filePicker_7">上传文件</a>
                            <div id="loading_7" class="loading"><img src="${BasePath}/images/loading.gif"></div>
                            <div id="attachment_7" class="inline-block">
                            <#if supplierContract??&&(supplierContract.attachmentList)??&&(supplierContract.attachmentList?size>0)>
				              <#list supplierContract.attachmentList as item >
				                <#if item['attachmentType']=='7'>
				                  <div class="attachment_item">
				                    <input name="contract_attachment" value="7;${item['attachmentName']!''};${item['attachmentRealName']!''}" type="hidden">
				                    <a href="javascript:void(0)"  class="download supplier-query-cont Blue ml5" title="${item['attachmentName']!''}"  fileName="${item['attachmentName']!''}" realName="${item['attachmentRealName']!''}">${item['attachmentName']!''}</a>
									<a href="javascript:void(0)" class="link-del ml10 Blue">删除</a>
				                  </div>
				                </#if>
				              </#list>
				            </#if>
						   </div>
						   <p class='error-message' id="attachmentError_7"/>
                        </div>
                    </div>
                </div>

              <#--  <div class="form-box">
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">资质名称：</label>
                            <span class="supplier-query-cont">授权书</span>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">资质名称：</label>
                            <span class="supplier-query-cont">商标注册证</span>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>上传列表：</label>
                            <a class="yg-btn-gray-2 yg-btn-update filePicker" id="filePicker_3">上传文件</a>
        					<div id="loading_3" class="loading"><img src="${BasePath}/images/loading.gif"></div>
							<div id="attachment_3" class="inline-block">
                            <#if supplierContract??&&(supplierContract.attachmentList)??&&(supplierContract.attachmentList?size>0)>
					              <#list supplierContract.attachmentList as item >
					                <#if item['attachmentType']=='3'>
					                  <div class="attachment_item">
					                    <input name="contract_attachment" value="3;${item['attachmentName']!''};${item['attachmentRealName']!''}" type="hidden">
					                    <a href="javascript:void(0)"  class="download supplier-query-cont Blue ml5" title="${item['attachmentName']!''}"  fileName="${item['attachmentName']!''}" realName="${item['attachmentRealName']!''}">${item['attachmentName']!''}</a>
										<a href="javascript:void(0)" class="link-del ml10 Blue">删除</a>
					                  </div>
					                </#if>
					              </#list>
					          </#if>
							</div>
							<p class='error-message' id="attachmentError_3"/>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>上传列表：</label>
                            <a class="yg-btn-gray-2 yg-btn-update filePicker" id="filePicker_4">上传文件</a>
        					<div id="loading_4" class="loading"><img src="${BasePath}/images/loading.gif"></div>
							<div id="attachment_4" class="inline-block">
                            <#if supplierContract??&&(supplierContract.attachmentList)??&&(supplierContract.attachmentList?size>0)>
				              <#list supplierContract.attachmentList as item >
				                <#if item['attachmentType']=='4'>
				                  <div class="attachment_item">
				                    <input name="contract_attachment" value="4;${item['attachmentName']!''};${item['attachmentRealName']!''}" type="hidden">
				                    <a href="javascript:void(0)"  class="download supplier-query-cont Blue ml5" title="${item['attachmentName']!''}"  fileName="${item['attachmentName']!''}" realName="${item['attachmentRealName']!''}">${item['attachmentName']!''}</a>
									<a href="javascript:void(0)" class="link-del ml10 Blue">删除</a>
				                  </div>
				                </#if>
				              </#list>
				            </#if>
						  </div>
						  <p class='error-message' id="attachmentError_4"/>
                        </div>
                    </div>
                </div> -->
                <div class="form-box">                
                    <div class="supplier-query-wrap clearfix">
                        <#-- div class="supplier-query-left">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>商标授权：</label>
                            <a class="yg-btn-gray-2" onclick="addSubTable()">添加</a>
                        </div -->
                        <div class="supplier-query-left">
                        	 <label for="" class="supplier-query-title"><span class="Red">*</span>是否需续签：</label>
                            <input type="radio" class="supplier-query-radio" name="isNeedRenew"  value="1" <#if supplierContract??&&supplierContract.isNeedRenew??><#if supplierContract.isNeedRenew == '1'>checked</#if></#if>/><label for="y_1" style="text-align:left;margin-right:35px">是</label>
                			<input type="radio" class="supplier-query-radio" name="isNeedRenew"  value="0" <#if supplierContract??&&supplierContract.isNeedRenew?? && supplierContract.isNeedRenew == '0'>checked</#if>/><label for="n_1">否</label>
                        </div>
                    </div>
                    
                 <#import "../add_trademark.ftl" as page>
                 <@page.trademark pageSrc="natural"/>
                
                 <div class="btn-box">
                     <a href="javascript:;" class="yg-btn-blue" onclick="updateNatural(this)"/>提交</a>
                     <a href="javascript:;" class="yg-btn-gray c-normal" onclick="closewindow();" id="yg-del-cancle">取消</a>
                 </div>
                
                </div>
              </form>
            </div>
        </div>
    </div>

	<form id="downloadForm" action="${BasePath}/yitiansystem/merchants/businessorder/downLoadContractAttachment.sc" method="post"> 
	<input type="hidden" name="fileName" id="fileName">
	<input type="hidden" name="realName" id="realName">
	</form>    
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
</body>

</html>
