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

    <title>优购商城--商家后台-商家列表-查看资质</title>
<style>
.loading{display:none}
</style>

</head>

<body>
    <div class="container natural">
        <!--工具栏start-->
        <div class="list_content">
            <div class="modify">
        	<form id="downloadForm" action="${BasePath}/yitiansystem/merchants/businessorder/downLoadContractAttachment.sc" method="post"> 
	 			<input type="hidden" name="fileName" id="fileName">
	 			<input type="hidden" name="realName" id="realName">
	 		</form>
                <div class="form-box">
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">资质名称：</label>
                            <span>营业执照</span>
                            <#if supplierExpand?? && (supplierExpand.businessRemainingDays)??&&(supplierExpand.businessRemainingDays>90) >
                            	 <span class="Green">(有效)</span>
                            <#elseif supplierExpand??&& (supplierExpand.businessRemainingDays)?? && (supplierExpand.businessRemainingDays>0 )>
                                 <span class="Orange">(即将过期，请及时更新)</span>
                            <#elseif supplierExpand??&& (supplierExpand.businessRemainingDays)?? && (supplierExpand.businessRemainingDays<1 )>
                                 <span class="Red">(已过期，请立即更新)</span>
                            <#else>
                            </#if>
                            
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">资质名称：</label>
                            <span>组织机构代码证</span>
                            <#if supplierExpand??&& (supplierExpand.institutionalRemainingDays)?? && (supplierExpand.institutionalRemainingDays>90) >
                            	<span class="Green">(有效)</span>
                            <#elseif supplierExpand??&& (supplierExpand.institutionalRemainingDays)?? && (supplierExpand.institutionalRemainingDays>0 )>
                                <span class="Orange">(即将过期，请及时更新)</span>
                            <#elseif supplierExpand??&& (supplierExpand.institutionalRemainingDays)?? && (supplierExpand.institutionalRemainingDays<1)>
                                <span class="Red">(已过期，请立即更新)</span>
                            <#else>
                            </#if>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">上传列表：</label>
                            <div id="attachment_2" class="inline-block">
                            <#if supplierContract??&&(supplierContract.attachmentList)??&&(supplierContract.attachmentList?size>0)>
				              <#list supplierContract.attachmentList as item >
				                <#if item['attachmentType']=='9'>
				                  <div class="attachment_item">
				                    <a href="javascript:void(0)"  class="download supplier-query-cont Blue ml5" title="${item['attachmentName']!''}"  fileName="${item['attachmentName']!''}" realName="${item['attachmentRealName']!''}">${item['attachmentName']!''}</a>
				                  </div>
				                </#if>
				              </#list>
				            </#if>
						  </div>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">上传列表：</label>
                            <div id="attachment_5" class="inline-block">
                            <#if supplierContract??&&(supplierContract.attachmentList)??&&(supplierContract.attachmentList?size>0)>
				              <#list supplierContract.attachmentList as item >
				                <#if item['attachmentType']=='5'>
				                  <div class="attachment_item">
				                    <a href="javascript:void(0)"  class="download supplier-query-cont Blue ml5" title="${item['attachmentName']!''}"  fileName="${item['attachmentName']!''}" realName="${item['attachmentRealName']!''}">${item['attachmentName']!''}</a>
				                  </div>
				                </#if>
				              </#list>
				            </#if>
						   </div>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                           <label for="" class="supplier-query-title">有效期：</label>
                             <span><#if supplierExpand?? && (supplierExpand.businessValidityStart)??>${supplierExpand.businessValidityStart}</#if> 至 <#if supplierExpand?? && (supplierExpand.businessValidityEnd)??>${supplierExpand.businessValidityEnd}</#if></span>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">有效期：</label>
                             <span ><#if supplierExpand?? && (supplierExpand.institutionalValidityStart)??>${supplierExpand.institutionalValidityStart}</#if> 至 <#if supplierExpand?? && (supplierExpand.institutionalValidityEnd)??>${supplierExpand.institutionalValidityEnd}</#if></span>
                       </div>
                    </div>
                </div>

                <div class="form-box">
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">资质名称：</label>
                            <span >税务登记证</span>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">资质名称：</label>
                            <span >一般纳税人资质证</span>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">上传列表：</label>
                            <div id="attachment_6" class="inline-block">
                            <#if supplierContract??&&(supplierContract.attachmentList)??&&(supplierContract.attachmentList?size>0)>
				              <#list supplierContract.attachmentList as item >
				                <#if item['attachmentType']=='6'>
				                  <div class="attachment_item">
				                    <a href="javascript:void(0)"  class="download supplier-query-cont Blue ml5" title="${item['attachmentName']!''}"  fileName="${item['attachmentName']!''}" realName="${item['attachmentRealName']!''}">${item['attachmentName']!''}</a>
				                  </div>
				                </#if>
				              </#list>
				            </#if>
						   </div>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">上传列表：</label>
                            <div id="attachment_7" class="inline-block">
                            <#if supplierContract??&&(supplierContract.attachmentList)??&&(supplierContract.attachmentList?size>0)>
				              <#list supplierContract.attachmentList as item >
				                <#if item['attachmentType']=='7'>
				                  <div class="attachment_item">
				                    <a href="javascript:void(0)"  class="download supplier-query-cont Blue ml5" title="${item['attachmentName']!''}"  fileName="${item['attachmentName']!''}" realName="${item['attachmentRealName']!''}">${item['attachmentName']!''}</a>
				                  </div>
				                </#if>
				              </#list>
				            </#if>
						   </div>
                        </div>
                    </div>
                </div>

                 <#-- <div class="form-box">
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">资质名称：</label>
                            <span >授权书</span>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">资质名称：</label>
                            <span >商标注册证</span>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">上传列表：</label>
							<div id="attachment_3" class="inline-block">
                            <#if supplierContract??&&(supplierContract.attachmentList)??&&(supplierContract.attachmentList?size>0)>
					              <#list supplierContract.attachmentList as item >
					                <#if item['attachmentType']=='3'>
					                  <div class="attachment_item">
					                   <a href="javascript:void(0)"  class="download supplier-query-cont Blue ml5" title="${item['attachmentName']!''}"  fileName="${item['attachmentName']!''}" realName="${item['attachmentRealName']!''}">${item['attachmentName']!''}</a>
				                      </div>
					                </#if>
					              </#list>
					          </#if>
							</div>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">上传列表：</label>
							<div id="attachment_4" class="inline-block">
                            <#if supplierContract??&&(supplierContract.attachmentList)??&&(supplierContract.attachmentList?size>0)>
				              <#list supplierContract.attachmentList as item >
				                <#if item['attachmentType']=='4'>
				                  <div class="attachment_item">
				                    <a href="javascript:void(0)"  class="download supplier-query-cont Blue ml5" title="${item['attachmentName']!''}"  fileName="${item['attachmentName']!''}" realName="${item['attachmentRealName']!''}">${item['attachmentName']!''}</a>
				                  </div>
				                </#if>
				              </#list>
				            </#if>
						  </div>
                        </div>
                    </div>
                </div>-->
                <div class="form-box">                    
                    <div class="supplier-query-wrap clearfix">
                      <#--   <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">商标授权：</label>
                            <#if supplierContract??&&(supplierContract.markRemainingDays)??&&((supplierContract.markRemainingDays)>90) >
	                        <span style="color:green">有效</span></li>
							<#elseif supplierContract??&&(supplierContract.markRemainingDays)??&&((supplierContract.markRemainingDays)>0) >
							<span class="Orange">即将过期，请及时更新</span></li>
							<#elseif supplierContract??&&(supplierContract.markRemainingDays)??&&((supplierContract.markRemainingDays)<1) >
	                        <span class="Red">已过期，请立即更新</span></li>
	                        <#else>
							</#if>
                        </div>-->
                        <div class="supplier-query-left">
                        	 <label for="" class="supplier-query-title">是否需续签：</label>
                             <#if supplierContract?? && (supplierContract.isNeedRenew)??>			
							<#if supplierContract.isNeedRenew == '1'>
							<span>是</span>
							<#elseif supplierContract.isNeedRenew == '0'>
							<span>否</span>
							<#else>
							<span>--</span>
							</#if>
							</#if>
                        </div>
                    </div>
                    
                  <#import "part_detail_brand_trademark.ftl" as page>
                  <@page.trademarkView pageSrc='natural'/>
                
                 <div class="btn-box">
                     <a href="javascript:closewindow();" class="yg-btn-gray c-normal" id="yg-del-cancle">关闭</a>
                 </div>
                
                </div>
            </div>
        </div>
    </div>
    
    <script type="text/javascript" src="${BasePath}/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script>
    <script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>
    <script type="text/javascript" src="${BasePath}/webuploader/webuploader.js?version=2.5"></script>
    <script src="${BasePath}/js/common/common_tools.js?version=20151211" type="text/javascript"></script>
	<script type="text/javascript">
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

</body>

</html>
