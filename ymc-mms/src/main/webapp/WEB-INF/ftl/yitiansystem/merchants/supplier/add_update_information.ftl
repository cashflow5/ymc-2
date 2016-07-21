<#-- 新增 或 修改 招商商家的公共页面（修改普通供应商的页面和这个不一样） -->
<#-- pageTitle: 页面标题 -->
<#-- submitUrl: 表单提交url -->
<#-- importJs: 额外导入的js -->
<#macro add_update_supplier_merchant
	pageTitle=""
	submitUrl=""
	importJs=''
	 >
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
	<script type="text/javascript" src="${BasePath}/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${BasePath}/js/yitiansystem/merchants/jquery.validate.js"></script>
    <script type="text/javascript" src="${BasePath}/js/yitiansystem/merchants/jquery.validate.cn.js"></script>
    <script type="text/javascript" src="${BasePath}/js/yitiansystem/merchants/jquery.validate.methods.js"></script>
    <script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script>
    <script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>
    <script type="text/javascript" src="${BasePath}/webuploader/webuploader.js?version=2.5"></script>
    <script type="text/javascript" src="${BasePath}/js/yitiansystem/merchants/supplier_manage_common.js?v=20151204"></script>
    <!-- 添加或编辑商家专用的js -->
	${importJs}
	<style>
	.loading{display:none}
	</style>
	<script type="text/javascript">
	var basePath = '${BasePath}';
	var editType = '${editType!""}';
	var defaultSupplier = "";
	//为了商家编辑，保存原商家名称
	<#if supplier?? && (supplier.supplier)??>
	  defaultSupplier = '${supplier.supplier}';
	</#if>
	</script>

    <title>优购商城--商家后台-${pageTitle}-三联信息</title>
 
</head>

<body>
    <div class="container">
        <!--工具栏start-->
    <form action="${submitUrl}" method="post" id="submitForm" name="submitForm">
		<input type="hidden" name="supplierId" id="supplierId" value="<#if supplier?? && (supplier.id)??>${(supplier.id)!''}</#if>" />
		<input type="hidden" name="supplierExpandId"  value="<#if supplierExpand?? && (supplierExpand.id)??>${(supplierExpand.id)!''}</#if>" />
		<input type="hidden" name="merchantUserId" id="merchantUserId" value="<#if merchantUser?? && (merchantUser.id)??>${(merchantUser.id)!''}</#if>" />
		<input type="hidden" name="rejectedAddressId" id="rejectedAddressId" value="<#if rejectedAddress?? && (rejectedAddress.id)??>${(rejectedAddress.id)!''}</#if>" />
	    <input type="hidden" name="chiefId" value="<#if contactsFormVo??>${(contactsFormVo.chiefId)!''}</#if>"/>
  		<input type="hidden" name="businessId" value="<#if contactsFormVo??>${(contactsFormVo.businessId)!''}</#if>"/>
  		<input type="hidden" name="afterSaleId" value="<#if contactsFormVo??>${(contactsFormVo.afterSaleId)!''}</#if>"/>
  		<input type="hidden" name="financeId" value="<#if contactsFormVo??>${(contactsFormVo.financeId)!''}</#if>"/>
  		<input type="hidden" name="techId" value="<#if contactsFormVo??>${(contactsFormVo.techId)!''}</#if>"/>
        <input type="hidden" name="contractId" id="contractId" value="<#if supplierContract??>${(supplierContract.id)!''}</#if>">
               
		<input type="hidden" name="balanceTraderName" id="balanceTraderName">
		<input type="hidden" name="isValid" id="isValid" value="<#if supplier??&&(supplier.isValid)??>${(supplier.isValid)!''}</#if>">
	    <input type="hidden" name="supplierCode" id="supplierCode" value="<#if supplier??&&(supplier.supplierCode)??>${(supplier.supplierCode)!''}</#if>">
	    <input type="hidden" name="inventoryCode" id="inventoryCode" value="${(supplier.inventoryCode)!''}">
	    <input type="hidden" name="redisKey" id="redisKey" value="${(redisKey)!''}"><!-- 为了草稿保存 -->
	    
        <div class="list_content">
            <div class="top clearfix">
                <ul class="tab">
                    <li class="curr">
                        <span><a href="#" class="btn-onselc">
                        <#if supplier?? && (supplier.id)??&& (supplier.id)!=''>编辑商家<#else>添加商家</#if>
                        </a></span>
                    </li>
                </ul><span class="SegmentSaveInfo" ></span>&nbsp;&nbsp;
                <div class="Red" style="padding-left:576px;">注意：文件上传格式必须为doc、xls、docx、xlsx、pdf、txt、jpg、bmp、png、jpeg ,或者打包rar格式上传。</div>
            </div>
            <div class="modify">
                <ul class="step-list clearfix">
                    <li class="current"><em>1</em>填写商家信息</li>
                    <li><em>2</em>填写店铺信息</li>
                    <li><em>3</em>填写合同信息</li>
                    <li class="last"><em>4</em>等待审核</li>
                </ul>

                <h1 class="supplier-title">公司信息</h1>
                <div class="form-box">
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>商家名称：</label>
                            <input type="text" id="supplier" name="supplier" class="supplier-query-text"   placeholder="需与营业执照一致" maxlength="50"  onblur="" <#if supplier?? && (supplier.supplier)??>value="${supplier.supplier}" </#if> <#if supplier?? && (supplier.id)??> readonly="readonly"</#if>/>
					        <p class='error-message'  id="supplierError" ></p>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>公司简称：</label>
                            <input type="text" id="simpleName" name="simpleName" class="supplier-query-text" maxlength="30"  onblur="" value="<#if supplier?? && (supplier.simpleName)??>${supplier.simpleName}</#if>" />
				            <p class='error-message' id="simpleNameError"  ></p>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>营业执照经营范围：</label>
                            <textarea id="businessRange" name="businessRange"  maxlength="100"  placeholder="需填写贵公司营业执照中的营业范围一致" class="supplier-query-textarea" ><#if supplierExpand?? && (supplierExpand.businessRange)??>${supplierExpand.businessRange}</#if></textarea>
                            <p class='error-message'  id="businessRangeError" ></p>
                        </div>
                        <div class="supplier-query-right">
                                <div class="supplier-query-div">
                                    <label for="" class="supplier-query-title"><span class="Red">*</span>营业执照注册号：</label>
                                    <input type="text" class="supplier-query-text" id="businessLicense" name="businessLicense" value="<#if supplier?? && (supplier.businessLicense)??>${supplier.businessLicense}</#if>" maxlength="30"  />
                                    <p class='error-message' id="businessLicenseError"  ></p>
                                </div>
                                <label for="" class="supplier-query-title"><span class="Red">*</span>营业执照有效期：</label>
                                <input type="text" class="calendar" id="createTimeStart_1"  name="businessValidityStart" value="<#if supplierExpand?? && (supplierExpand.businessValidityStart)??>${supplierExpand.businessValidityStart}</#if>"  readonly="readonly" />至<input type="text"  class="calendar" id="createTimeEnd_1"  name="businessValidityEnd" value="<#if supplierExpand?? && supplierExpand.businessValidityEnd??>${supplierExpand.businessValidityEnd}</#if>"  readonly="readonly" />
                                <input type="checkbox" class="forever" id="foreverFor_1"  onchange="setEndForever(1);"/><label for="foreverFor_1">永久</label>
                            	<p class='error-message' id='createTimeStartError_1'/><p class='error-message' id='createTimeEndError_1'/>
                            </p>
                        </div>
                    </div>

					<input type="hidden" name="registerProvince" id="hidden_province_1"  value="<#if supplierExpand??>${(supplierExpand.registerProvince)!''}</#if>"/>
					<input type="hidden" name="registerCity" id="hidden_city_1"  value="<#if supplierExpand??>${(supplierExpand.registerCity)!''}</#if>"/>
					<input type="hidden" name="registerArea" id="hidden_area_1"  value="<#if supplierExpand??>${(supplierExpand.registerArea)!''}</#if>"/>

                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>注册地址：</label>
                            <select class="address-select" id="province_1" name="province_1"  onchange="checkTwo(1);" >                        
                            	<#if areaList??>
								<option value="-1">请选择省份</option> 
								<#list areaList as item>
								<option value="${item['no']!''}" <#if item['name']??&&(supplierExpand.registerProvince)??&&item['name']==supplierExpand.registerProvince>selected</#if>>${item['name']!''}</option>
								</#list> 
								</#if>
                            </select>
                            <select class="address-select city"  id="city_1" name="city_1" onchange="checkThree(1);" >
                            </select>
                            <select class="address-select area"  id="area_1"  name="area_1" >
                            </select>
                            <p class='error-message' id='provinceError_1'/><p class='error-message' id='cityError_1'/><p class='error-message' id='areaError_1'/>
                        
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>营业执照副本：</label>
                            <a class="yg-btn-gray-2 yg-btn-update filePicker" id="filePicker_2">上传文件</a>
                            <div id="loading_2" class="loading"><img src="${BasePath}/images/loading.gif"></div>
                            
                            <div id="attachment_2" class="inline-block">
                            <#if supplierContract??&&supplierContract.attachmentList??&&(supplierContract.attachmentList?size>0)>
				              <#list supplierContract.attachmentList as item >
				                <#if item['attachmentType']=='9'>
				                  <div class="attachment_item">
				                    <input name="contract_attachment" value="9;${item['attachmentName']!''};${item['attachmentRealName']!''}" type="hidden">
				                    <span class="supplier-query-cont Blue ml5" title="${item['attachmentName']!''}">${item['attachmentName']!''}</span><a href="javascript:void(0)" class="link-del ml10 Blue">删除</a>
				                  </div>
				                </#if>
				              </#list>
				            </#if>
						  </div>
						  <p class='error-message' id="attachmentError_2"/>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title"><span class="Red">*</span></label>
                            <input type="text" class="supplier-query-text detail-address-text" name="registerDetails"  id="registerDetails" value="<#if supplierExpand?? && (supplierExpand.registerDetails)??>${supplierExpand.registerDetails}</#if>" placeholder="详细街道地址"  maxlength="41"/>
                        	 <p class='error-message' id="registerDetailsError"/>
                        </div>
                        <div class="supplier-query-right">
                        <span class="Gray">企业营业照副本复印件(需加盖开店公司红单)<span>
                        </div>
                    </div>
                </div>

                <h1 class="supplier-title">组织机构代码证</h1>
                <div class="form-box">
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>组织机构代码：</label>
                            <input type="text" class="supplier-query-text" name="institutional" maxlength="30"  id="institutional" value="<#if supplier?? && (supplier.institutional)??>${supplier.institutional}</#if>"/>
                        	<p class='error-message' id='institutionalError'/>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>组织机构代码证：</label>
                            <a class="yg-btn-gray-2 yg-btn-update filePicker" id="filePicker_5">上传文件</a><!--id的序号不能变，和数据库表的附件类型一致 -->
                            <div id="loading_5" class="loading"><img src="${BasePath}/images/loading.gif"></div>
                            <div id="attachment_5" class="inline-block">
                            <#if supplierContract??&&(supplierContract.attachmentList)??&&(supplierContract.attachmentList?size>0)>
				              <#list supplierContract.attachmentList as item >
				                <#if item['attachmentType']=='5'>
				                  <div class="attachment_item">
				                    <input name="contract_attachment" value="5;${item['attachmentName']!''};${item['attachmentRealName']!''}" type="hidden">
				                    <span class="supplier-query-cont Blue ml5" title="${item['attachmentName']!''}">${item['attachmentName']!''}</span><a href="javascript:void(0)" class="link-del ml10 Blue">删除</a>
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
                            <label for="" class="supplier-query-title"><span class="Red">*</span>组织机构代码证有效期：</label>
                             <input type="text" class="calendar timeStart" id="createTimeStart_2"  name="institutionalValidityStart" value="<#if supplierExpand?? && supplierExpand.institutionalValidityStart??>${supplierExpand.institutionalValidityStart}</#if>" 
                              readonly="readonly"/>至<input type="text"  class="calendar timeEnd" id="createTimeEnd_2"  name="institutionalValidityEnd" value="<#if supplierExpand?? && supplierExpand.institutionalValidityEnd??>${supplierExpand.institutionalValidityEnd}</#if>"  readonly="readonly" />
                            <input type="checkbox" id="foreverFor_2"  onchange="setEndForever(2);"/><label for="forever_2">永久</label>
                        	<p class='error-message' id='createTimeStartError_2'/><p class='error-message' id='createTimeEndError_2'/>
                        </div>
                    </div>
                </div>

                <h1 class="supplier-title">税务登记证</h1>
                <div class="form-box">
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>纳税人识别号：</label>
                            <input type="text" class="supplier-query-text" name="tallageNo" id="tallageNo"  maxlength="30" value="<#if supplier?? && supplier.tallageNo??>${supplier.tallageNo}</#if>"/>
                        	<p class='error-message' id='tallageNoError'/>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>税务登记证：</label>
                            <a class="yg-btn-gray-2 yg-btn-update filePicker" id="filePicker_6">上传文件</a>
                            <div id="loading_6" class="loading"><img src="${BasePath}/images/loading.gif"></div>
                            
                            
                            <div id="attachment_6" class="inline-block">
                            <#if supplierContract??&&(supplierContract.attachmentList)??&&(supplierContract.attachmentList?size>0)>
				              <#list supplierContract.attachmentList as item >
				                <#if item['attachmentType']=='6'>
				                  <div class="attachment_item">
				                    <input name="contract_attachment" value="6;${item['attachmentName']!''};${item['attachmentRealName']!''}" type="hidden">
				                    <span class="supplier-query-cont Blue ml5" title="${item['attachmentName']!''}">${item['attachmentName']!''}</span><a href="javascript:void(0)" class="link-del ml10 Blue">删除</a>
				                  </div>
				                </#if>
				              </#list>
				            </#if>
						   </div>
						   <p class='error-message' id="attachmentError_6"/>
						   <span class="Gray">若国税和地税章不在同一个证上，请扫描至一张图片再上传<span>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>一般纳税人：</label>
                            <input type="radio"  class="supplier-query-radio" id="taxplayerType" name="taxplayerType" value="1" <#if supplier??&&(supplier.taxplayerType)??><#if supplier.taxplayerType=='1'>checked</#if></#if>>是</label>
                   			<input type="radio"  class="supplier-query-radio" id="taxplayerType" name="taxplayerType" value="0" <#if supplier??&&(supplier.taxplayerType)??><#if supplier.taxplayerType =='0'>checked</#if></#if>>否</label>
                        	<p class='error-message' />
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>增值税专用发票：</label>
                            <input type="radio" class="supplier-query-radio" name="isAddValueInvoice" id="isAddValueInvoice" value="1" <#if supplier??&&(supplier.isAddValueInvoice)??><#if supplier.isAddValueInvoice==1>checked</#if></#if>/><label for="r_1">是</label> 
                            <input type="radio" class="supplier-query-radio ml10" name="isAddValueInvoice" id="isAddValueInvoice" value="0" <#if supplier??&&(supplier.isAddValueInvoice)??><#if supplier.isAddValueInvoice ==0>checked</#if></#if>/><label for="r_2">否</label>
                        	<p class='error-message' />
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>增值税税率：</label>
                            <input type="text" class="supplier-query-text" id="taxRate" name="taxRate" placeholder="%（0-100）" maxlength="30" onblur="" value="<#if supplier?? && supplier.taxRate??>${supplier.taxRate}</#if>" />
				 			<p class='error-message' id="taxRateError" />
				
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">一般纳税人资格证：</label>
                            <a class="yg-btn-gray-2 yg-btn-update filePicker" id="filePicker_7">上传文件</a>
                            <div id="loading_7" class="loading"><img src="${BasePath}/images/loading.gif"></div>
                            <div id="attachment_7" class="inline-block">
                            <#if supplierContract??&&(supplierContract.attachmentList)??&&(supplierContract.attachmentList?size>0)>
				              <#list supplierContract.attachmentList as item >
				                <#if item['attachmentType']=='7'>
				                  <div class="attachment_item">
				                    <input name="contract_attachment" value="7;${item['attachmentName']!''};${item['attachmentRealName']!''}" type="hidden">
				                    <span class="supplier-query-cont Blue ml5" title="${item['attachmentName']!''}">${item['attachmentName']!''}</span><a href="javascript:void(0)" class="link-del ml10 Blue">删除</a>
				                  </div>
				                </#if>
				              </#list>
				            </#if>
						   </div>
						   <p class='error-message' id="attachmentError_7"/>
							
                        </div>
                    </div>
                </div>

                <h1 class="supplier-title">开票银行信息：</h1> 
                <div class="form-box">
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>银行开户名：</label>
                            <input type="text" class="supplier-query-text" name="contact" id="contact"  maxlength="30" value="<#if supplier?? && supplier.contact??>${supplier.contact}</#if>"/>
                            <p class='error-message' id="bankError" />
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>开户行支行名称：</label>
                            <input type="text" class="supplier-query-text" name="bank" id="bank" maxlength="50"  value="<#if supplier?? && supplier.bank??>${supplier.bank}</#if>" />
                        	<p class='error-message' id="subBankError" />
                        </div>
                    </div>
                    
                  <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>开票地址：</label>                        
                             <input type="text" class="supplier-query-text" name="invoiceAddress" id="invoiceAddress" maxlength="200"  value="<#if supplier?? && supplier.invoiceAddress??>${supplier.invoiceAddress}</#if>" />
                        	<p class='error-message' id="invoiceAddressError" />
                        </div>
                         <div class="supplier-query-right">
						     <label for="" class="supplier-query-title"><span class="Red">*</span>电话：</label>
						     <input type="text" class="supplier-query-text" name="invoicePhone"  id="invoicePhone"  maxlength="30" value="<#if supplier?? && supplier.invoicePhone??>${supplier.invoicePhone}</#if>"/>
                       		 <p class='error-message' id="invoicePhoneError"/>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>银行账号：</label>
                            <input type="text" class="supplier-query-text" name="account"  id="account"  maxlength="30" value="<#if supplier?? && supplier.account??>${supplier.account}</#if>"/>
                       		 <p class='error-message' id="accountError"/>
                        </div>
                    </div>
                </div>
                
                <h1 class="supplier-title">结算银行信息：</h1> 
                <div class="form-box">
                    <div class="supplier-query-wrap clearfix">                        
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>开户行支行名称：</label>
                            <input type="text" class="supplier-query-text" name="clearBank"  id="clearBank"  maxlength="50" value="<#if supplier?? && supplier.clearBank??>${supplier.clearBank}</#if>"/>
                       		<p class='error-message' id="clearBankError"/>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>银行账号：</label>
                            <input type="text" class="supplier-query-text" name="clearAccount"  id="clearAccount"  maxlength="30" value="<#if supplier?? && supplier.clearAccount??>${supplier.clearAccount}</#if>"/>
                       		<p class='error-message' id="clearAccountError"/>
                        </div>
                    </div>                   
                </div>
                
                <div class="btn-box">
                    <a href="javascript:void(0);" class="yg-btn-blue btn-next first"/>下一步，填写店铺信息</a>
                    <#if supplier?? && (supplier.id)??&& (supplier.id)!=''><#else><a href="javascript:saveSegment(this);" class="yg-btn-gray">保存草稿</a></#if>

                </div>
            </div>
        </div>
        <!-- 公司信息结束 -->

        <!-- 店铺信息开始 -->
         <!--工具栏start-->
        <div class="list_content hide">
            <div class="top clearfix">
                <ul class="tab">
                    <li class="curr">
                        <span><a href="#" class="btn-onselc"><#if supplier?? && (supplier.id)??&& (supplier.id)!=''>编辑商家<#else>添加商家</#if></a></span>
                    </li>
                </ul><span class="SegmentSaveInfo" ></span>&nbsp;&nbsp;
                 <div class="Red" style="padding-left:576px;">注意：文件上传格式必须为doc、xls、docx、xlsx、pdf、txt、jpg、bmp、png、jpeg ,或者打包rar格式上传。</div>
            </div>
            <div class="modify">
                <ul class="step-list clearfix">
                    <li class="walk"><em>1</em>填写商家信息</li>
                    <li class="current"><em>2</em>填写店铺信息</li>
                    <li><em>3</em>填写合同信息</li>
                    <li class="last"><em>4</em>等待审核</li>
                </ul>

                <h1 class="supplier-title">店铺负责人信息</h1>

                <div class="form-box">
                
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>姓名：</label>
                            <input type="text" class="supplier-query-text" name="chiefContact" id="chiefContact" maxlength="10"  value="<#if contactsFormVo??>${(contactsFormVo.chiefContact)!''}</#if>"/>
                            <p class='error-message' id='chiefContactError'/>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>手机：</label>
                            <input type="text" class="supplier-query-text" name="chiefMobilePhone"  id="chiefMobilePhone"  value="<#if contactsFormVo??>${(contactsFormVo.chiefMobilePhone)!''}</#if>"/>
                            <p class='error-message' id='chiefMobilePhoneError'/>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>电子邮箱：</label>
                            <input type="email" class="supplier-query-text" name="chiefEmail"  id="chiefEmail"  value="<#if contactsFormVo??>${(contactsFormVo.chiefEmail)!''}</#if>"/>
                       		<p class='error-message' id='chiefEmailError'/>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">座机：</label>
                            <input type="text" class="supplier-query-text" name="chiefTelePhone" id="chiefTelePhone"  value="<#if contactsFormVo??>${(contactsFormVo.chiefTelePhone)!''}</#if>"/>
                        	<p class='error-message' />
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">QQ号码：</label>
                            <input type="text" class="supplier-query-text" name="chiefQQ" id="chiefQQ" maxlength="20"   value="<#if contactsFormVo??>${(contactsFormVo.chiefQQ)!''}</#if>"/>
                        	<p class='error-message' />
                        </div>
                    </div>
                </div>

                <h1 class="supplier-title">售后退换货地址<span class="Gray">用于顾客申请退换货时原商品寄回地址信息，请务必正确填写<span></h1>

                <div class="form-box">
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>收货人姓名：</label>
                            <input type="text" class="supplier-query-text" name="consigneeName" id="consigneeName"  maxlength="10"  value="<#if rejectedAddress??>${(rejectedAddress.consigneeName)!''}</#if>"/>
                       		<p class='error-message' id='consigneeNameError'/>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>收货人电话：</label>
                            <input type="text" class="supplier-query-text" name="consigneePhone" id="consigneePhone" maxlength="20"   value="<#if rejectedAddress??>${(rejectedAddress.consigneePhone)!''}</#if>"/>
                       		<p class='error-message' id='consigneePhoneError'/>
                        </div>
                    </div>
                    
                    <input type="hidden" name="consigneeTell" id="consigneeTell" 
                    value="<#if rejectedAddress??>${(rejectedAddress.consigneeTell)!''}</#if>"/>
                    
                    <input type="hidden" name="warehouseArea" id="warehouseArea" 
                    value="<#if rejectedAddress??>${(rejectedAddress.warehouseArea)!''}</#if>"/><!--下面的3个是对它的拆分 -->
                    <input type="hidden" name="afterSaleProvince" id="hidden_province_3" value="<#if rejectedAddress??>${(rejectedAddress.afterSaleProvince)!''}</#if>"/>
					<input type="hidden" name="afterSaleCity" id="hidden_city_3" value="<#if rejectedAddress??>${(rejectedAddress.afterSaleCity)!''}</#if>"/>
					<input type="hidden" name="afterSaleArea" id="hidden_area_3" value="<#if rejectedAddress??>${(rejectedAddress.afterSaleArea)!''}</#if>"/>
					
				
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>收货人地区：</label>
                            <select class="address-select" id="province_3" name="province_3"  onchange="checkTwo(3);">
                                <#if areaList??>
								<option value="-1">请选择省份</option> 
								<#list areaList as item>
								<option value="${item['no']!''}" <#if item['name']??&&(rejectedAddress.afterSaleProvince)??&&item['name']==(rejectedAddress.afterSaleProvince)>selected</#if>>${item['name']!''}</option>
								</#list> 
								</#if>
                            </select>
                            <select class="address-select city" id="city_3" name="city_3" onchange="checkThree(3);" >
                            </select>
                            <select class="address-select area" id="area_3" name="area_3">
                            </select>
                             <p class='error-message' id='provinceError_3'/><p class='error-message' id='cityError_3'/><p class='error-message' id='areaError_3'/>
                      
                        </div>
                        
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>邮政编码：</label>
                             <input type="text" name="warehousePostcode" id="warehousePostcode" maxlength="20"  value="<#if rejectedAddress??&&rejectedAddress.warehousePostcode??>${rejectedAddress.warehousePostcode!''}</#if>"/>
                        	 <p class='error-message' id='warehousePostcodeError'/>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title"><span class="Red">*</span></label>
                            <input type="text" class="supplier-query-text detail-address-text" name="warehouseAdress" maxlength="100"   id="warehouseAdress" placeholder="详细街道地址"  value="<#if rejectedAddress??&&rejectedAddress.warehouseAdress??>${rejectedAddress.warehouseAdress!''}</#if>" />
                        	<span style="color:red;" id="warehouseAdressError"></span>
                       		<p class='error-message' />
                        </div>
                    </div>
                </div>

                <h1 class="supplier-title">业务联系人</h1>
                
                <div class="form-box">
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">姓名：</label>
                            <input type="text" class="supplier-query-text" name="businessContact" id="businessContact" maxlength="10"  value="<#if contactsFormVo??>${(contactsFormVo.businessContact)!''}</#if>"/>
                        	<p class='error-message' />
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">手机：</label>
                            <input type="text" class="supplier-query-text" name="businessMobilePhone" id="businessMobilePhone" value="<#if contactsFormVo??>${(contactsFormVo.businessMobilePhone)!''}</#if>"/>
                        	<p class='error-message' />
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">电子邮箱：</label>
                            <input type="email" class="supplier-query-text" name="businessEmail" id="businessEmail" value="<#if contactsFormVo??>${(contactsFormVo.businessEmail)!''}</#if>"/>
                        	<p class='error-message' />
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">座机：</label>
                            <input type="text" class="supplier-query-text" name="businessTelePhone" id="businessTelePhone" maxlength="20"  value="<#if contactsFormVo??>${(contactsFormVo.businessTelePhone)!''}</#if>"/>
                        	<p class='error-message' />
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">QQ号码：</label>
                            <input type="text" class="supplier-query-text" name="businessQQ" id="businessQQ" maxlength="20"  value="<#if contactsFormVo??>${(contactsFormVo.businessQQ)!''}</#if>"/>
                        	<p class='error-message' />
                        </div>
                    </div>
                </div>

                <h1 class="supplier-title">售后联系人：</h1>
                
                <div class="form-box">
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">姓名：</label>
                            <input type="text" class="supplier-query-text" name="afterSaleContact" id="afterSaleContact" maxlength="10"  value="<#if contactsFormVo??>${(contactsFormVo.afterSaleContact)!''}</#if>"/>
                        	<p class='error-message' />
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">手机：</label>
                            <input type="text" class="supplier-query-text" name="afterSaleMobilePhone" id="afterSaleMobilePhone" value="<#if contactsFormVo??>${(contactsFormVo.afterSaleMobilePhone)!''}</#if>"/>
                        	<p class='error-message' />
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">电子邮箱：</label>
                            <input type="email" class="supplier-query-text" name="afterSaleEmail" id="afterSaleEmail" value="<#if contactsFormVo??>${(contactsFormVo.afterSaleEmail)!''}</#if>"/>
                        	<p class='error-message' />
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">座机：</label>
                            <input type="text" class="supplier-query-text" name="afterSaleTelePhone" id="afterSaleTelePhone" maxlength="20"  value="<#if contactsFormVo??>${(contactsFormVo.afterSaleTelePhone)!''}</#if>"/>
                        	<p class='error-message' />
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">QQ号码：</label>
                            <input type="text" class="supplier-query-text" name="afterSaleQQ" id="afterSaleQQ" maxlength="20"  value="<#if contactsFormVo??>${(contactsFormVo.afterSaleQQ)!''}</#if>"/>
                        	<p class='error-message' />
                        </div>
                    </div>
                </div>

                <h1 class="supplier-title">财务联系人：</h1>
                
                <div class="form-box">
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">姓名：</label>
                            <input type="text" class="supplier-query-text" name="financeContact" id="financeContact" maxlength="10"  value="<#if contactsFormVo??>${(contactsFormVo.financeContact)!''}</#if>"/>
                        	<p class='error-message' />
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">手机：</label>
                            <input type="text" class="supplier-query-text" name="financeMobilePhone" id="financeMobilePhone" value="<#if contactsFormVo??>${(contactsFormVo.financeMobilePhone)!''}</#if>"/>
                        	<p class='error-message' />
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">电子邮箱：</label>
                            <input type="email" class="supplier-query-text" name="financeEmail" id="financeEmail" value="<#if contactsFormVo??>${(contactsFormVo.financeEmail)!''}</#if>"/>
                        	<p class='error-message' />
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">座机：</label>
                            <input type="text" class="supplier-query-text" name="financeTelePhone" id="financeTelePhone" maxlength="20"  value="<#if contactsFormVo??>${(contactsFormVo.financeTelePhone)!''}</#if>"/>
                        	<p class='error-message' />
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">QQ号码：</label>
                            <input type="text" class="supplier-query-text" name="financeQQ" id="financeQQ" maxlength="20"  value="<#if contactsFormVo??>${(contactsFormVo.financeQQ)!''}</#if>"/>
                        	<p class='error-message' />
                        </div>
                    </div>
                </div>

                <h1 class="supplier-title">技术联系人：</h1>
                
                <div class="form-box">
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">姓名：</label>
                            <input type="text" class="supplier-query-text" name="techContact" id="techContact" maxlength="10"  value="<#if contactsFormVo??>${(contactsFormVo.techContact)!''}</#if>"/>
                        	<p class='error-message' />
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">手机：</label>
                            <input type="text" class="supplier-query-text" name="techMobilePhone" id="techMobilePhone" value="<#if contactsFormVo??>${(contactsFormVo.techMobilePhone)!''}</#if>"/>
                        	<p class='error-message' />
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">电子邮箱：</label>
                            <input type="email" class="supplier-query-text" name="techEmail" id="techEmail" value="<#if contactsFormVo??>${(contactsFormVo.techEmail)!''}</#if>"/>
                        	<p class='error-message' />
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">座机：</label>
                            <input type="text" class="supplier-query-text" name="techTelePhone" id="techTelePhone" maxlength="20"  value="<#if contactsFormVo??>${(contactsFormVo.techTelePhone)!''}</#if>"/>
                        	<p class='error-message' />
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">QQ号码：</label>
                            <input type="text" class="supplier-query-text" name="techQQ" id="techQQ" maxlength="20"  value="<#if contactsFormVo??>${(contactsFormVo.techQQ)!''}</#if>"/>
                        	<p class='error-message' />
                        </div>
                    </div>
                </div>

                <h1 class="supplier-title">账户信息：</h1>
                
                <div class="form-box">
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>登陆账号：</label>
                            <input type="text" class="supplier-query-text" name="loginName" id="loginName" maxlength="20"  value="<#if merchantUser??>${(merchantUser.loginName)!''}</#if>" <#if supplier?? && (supplier.id)??&& merchantUser??&&(merchantUser.id)??>readonly="readonly"</#if>/>
                            <p class='error-message' id="loginAccountError"/>
                        
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>登陆密码：</label>
                            <input  class="supplier-query-text" name="password" type="password" id="password" value="<#if merchantUser??>${(merchantUser.password)!''}</#if>" <#if supplier?? && (supplier.id)??&&merchantUser??&&(merchantUser.id)??>readonly="readonly"</#if>/>
                            <!--span style="float: left;margin-top:10px;">8-20个字符，由字母、数字和符号的两种以上组合</span-->
                            <span id="loginPasswordPower" style="display: none;margin-left:150px;">
		                		<ul class="safetyStrength">
			                        <li class="pwdLow"></li>
			                        <li></li>
			                        <li></li>
			                        <li class="text">低</li>
			                    </ul>
		                	</span>
                            <p class='error-message' id="loginPasswordError"/>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>绑定手机：</label>
                            <input type="text" class="supplier-query-text" maxlength="11" name="mobileCode" id="mobileCode" value="<#if merchantUser??>${(merchantUser.mobileCode)!''}</#if>"/>
                            <!--span >用于账号安全验证，务必填写准确</span-->
                            <p class='error-message' id="mobileCodeError"/>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>确认密码：</label>
                            <input  class="supplier-query-text" name="passwordTwo" id="passwordTwo" type="password" value="<#if merchantUser?? && (merchantUser.password)??>${merchantUser.password}</#if>" <#if supplier?? && (supplier.id)??&&merchantUser??&&(merchantUser.id)??>readonly="readonly"</#if>/>
                       		<p class='error-message' id="passwordTwoError"/>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-main">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>绑定邮箱：</label>
                            <input type="email" class="supplier-query-text" name="email" id="email" value="<#if merchantUser??>${(merchantUser.email)!''}</#if>" />
                            <p class='error-message' id="emailError"/>
                        </div>
                    </div>
                </div>

                <div class="btn-box">
                    <a  href="javascript:void(0);" class="yg-btn-gray btn-prev"/>上一步</a>
                    <a  href="javascript:void(0);" class="yg-btn-blue btn-next second"/>下一步，填写合同信息</a>
                    <#if supplier?? && (supplier.id)??&& (supplier.id)!=''><#else><a href="javascript:saveSegment(this);" class="yg-btn-gray">保存草稿</a></#if>

                </div>
            </div>
        </div>
        <!-- 店铺信息结束 -->

        <!-- 合同信息开始 -->
        <div class="list_content hide flow_layout">
        <div style="<#if editType?? && editType=='F_EDIT'>position: fixed;top: 0%;  left: 0%; width: 100%;  height: 100%;z-index:1001;background-color:black;  -moz-opacity: 0.1;  opacity:.10;  filter: alpha(opacity=10);</#if>" title="<#if editType?? && editType=='F_EDIT'>您不能修改该步骤信息！</#if>"></div>
            <div class="top clearfix">
                <ul class="tab">
                    <li class="curr">
                        <span><a href="#" class="btn-onselc"><#if supplier?? && (supplier.id)??&& (supplier.id)!=''>编辑商家<#else>添加商家</#if></a></span>
                    </li>
                </ul><span class="SegmentSaveInfo" ></span>&nbsp;&nbsp;
                 <div class="Red" style="padding-left:576px;">   注意：文件上传格式必须为doc、xls、docx、xlsx、pdf、txt、jpg、bmp、png、jpeg ,或者打包rar格式上传。</div>
            </div>
            <div class="modify">
                <ul class="step-list clearfix">
                    <li class="walk"><em>1</em>填写信息</li>
                    <li class="walk"><em>2</em>填写店铺信息</li>
                    <li class="current"><em>3</em>填写合同信息</li>
                    <li class="last"><em>4</em>等待审核</li>
                </ul>

               <h1 class="supplier-title">合同信息</h1>
                <div class="form-box">
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>商家名称：</label>
                           
							<span id="copy_supplier"></span>
							<input type="hidden" name="setOfBooksName" id="setOfBooksName">
							<input type="hidden" name="_setOfBooksCode" id="_setOfBooksCode" value="<#if supplier?? && supplier.setOfBooksCode??>${supplier.setOfBooksCode}</#if>">
							<input type="hidden" name="_isInputYougouWarehouse" id="_isInputYougouWarehouse" value="<#if supplier?? && supplier.isInputYougouWarehouse??>${supplier.isInputYougouWarehouse}</#if>">
                        	<input type="hidden" name="bindStatus" value="${(supplierContract.bindStatus)!'0' }" id="bindStatus"/>
                        	<input type="hidden" name="status" value="${(supplierContract.status)!'' }" id="status"/>
								                       
          				</div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">合同编号：</label>
                            <span><#if supplierContract??&&(supplierContract.contractNo)??>${(supplierContract.contractNo)!'' }<#else>${contractNo!''}</#if></span>
                        	<input type="hidden" name="contractNo" id="contractNo" value="<#if supplierContract??&&(supplierContract.contractNo)??>${(supplierContract.contractNo)!'' }<#else>${contractNo!''}</#if>">
                                           
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>合作模式：</label>
                            
                            <!--修改时，不许编辑 -->
                            <#if supplier?? && (supplier.id)??>
	                            <#list statics['com.belle.other.model.pojo.SupplierSp$CooperationModel'].values()?sort_by('description')?reverse as item>
				                   	<#if supplier??&&supplier.isInputYougouWarehouse??&&supplier.isInputYougouWarehouse==item.ordinal()><span>${item.description!''}</span></#if>
				                </#list>
                            <#else>
	                            <select class="supplier-query-select" name="isInputYougouWarehouse" onChange="changeWms(this.value);">
				                	<#list statics['com.belle.other.model.pojo.SupplierSp$CooperationModel'].values()?sort_by('description')?reverse as item>
				                   	<option id="_option_${item_index}" value="${item.ordinal()}" <#if item.ordinal()==0>selected</#if>>${item.description!''}</option>
				                 	</#list>
			                   	</select>
		                   	</#if>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>合同起止时间：</label>
                            <input class="calendar" name="effectiveDate" id="createTimeStart_3" readonly="readonly" value="<#if supplierContract??>${supplierContract.effectiveDate!''}</#if>" type="text">
							至 <input class="calendar" name="failureDate" id="createTimeEnd_3" readonly="readonly" value="<#if supplierContract??>${supplierContract.failureDate!''}</#if>" type="text">
                        	 <input type="checkbox" class="forever" id="foreverFor_3"  onchange="setEndForever(3);"/><label for="foreverFor_3">永久</label>
                        	<p class='error-message' id='createTimeStartError_3'/>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
						<div class="supplier-query-left">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>成本账套名称：</label>
                            <select class="supplier-query-select" id="setOfBooksCode" name="setOfBooksCode">
                			<#if costSetofBooksList??>
			                    <option value="" >请选择成本帐套名称</option>
			                	<#list costSetofBooksList as item>
			                   	<option value="${item.setOfBooksCode!''}" <#if supplier??&&supplier.setOfBooksCode??&&supplier.setOfBooksCode==item.setOfBooksCode>selected</#if>>${item.setOfBooksName!''}</option>
			                 	</#list>
		                   	</#if>
                   			</select>
                   			<p class='error-message' id="setOfBooksCodeError"/>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>合同电子版：</label>
                            <a class="yg-btn-gray-2 yg-btn-update filePicker" id="filePicker_1">上传文件</a>
        					<div id="loading_1" class="loading"><img src="${BasePath}/images/loading.gif"></div>
							<div id="attachment_1" class="inline-block">
                           		 <#if supplierContract??&&(supplierContract.attachmentList)??&&(supplierContract.attachmentList?size>0)>
					              <#list supplierContract.attachmentList as item >
					                <#if item['attachmentType']=='1'>
					                  <div class="attachment_item">
					                    <input name="contract_attachment" value="1;${item['attachmentName']!''};${item['attachmentRealName']!''}" type="hidden">
					                    <span class="supplier-query-cont Blue ml5" title="${item['attachmentName']!''}">${item['attachmentName']!''}</span><a href="javascript:void(0)" class="link-del ml10 Blue">删除</a>
					                  </div>
					                </#if>
					              </#list>
					            </#if>
							</div>
				   			<p class='error-message' id="attachmentError_1"/>
                        </div>
                    </div>
					<div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>结算方式：</label>
                            <select class="supplier-query-select" name="clearingForm" id="clearingForm">
						        <option value="0" <#if supplierContract??&&(supplierContract.clearingForm)??&&supplierContract.clearingForm='0'>selected</#if>>请选择结算方式</option>
						        <option value="1" <#if supplierContract??&&(supplierContract.clearingForm)??&&supplierContract.clearingForm='1'>selected</#if>>底价结算</option>
						        <option value="2" <#if supplierContract??&&(supplierContract.clearingForm)??&&supplierContract.clearingForm='2'>selected</#if>>扣点结算</option>
						        <option value="3" <#if supplierContract??&&(supplierContract.clearingForm)??&&supplierContract.clearingForm='3'>selected</#if>>配折结算</option>
						        <option value="4" <#if supplierContract??&&(supplierContract.clearingForm)??&&supplierContract.clearingForm='4'>selected</#if>>促销结算</option>
						    </select>
						    <p class='error-message' id="clearingFormError"/>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>是否需续签：</label>
                            <input type="radio" class="supplier-query-radio" name="isNeedRenew" id="isNeedRenew1" value="1" <#if (supplierContract.isNeedRenew)??><#if supplierContract.isNeedRenew == '1'>checked</#if></#if>/><label for="isNeedRenew1" class="supplier-query-title w25">是</label>
                			<input type="radio" class="supplier-query-radio ml50" name="isNeedRenew" id="isNeedRenew2" value="0" <#if (supplierContract.isNeedRenew)?? && supplierContract.isNeedRenew == '0'>checked</#if>/><label for="isNeedRenew2">否</label>
                        	<p class='error-message' />
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>销售发票开具方：</label> 
                        	<input type="radio" class="supplier-query-radio" name="isInvoice" id="isInvoice1" value="0" <#if (supplier.isInvoice)??><#if supplier.isInvoice == 0>checked</#if></#if>/><label for="isInvoice1" class="supplier-query-title w25">优购</label>
                   			<input type="radio" class="supplier-query-radio ml50" name="isInvoice" id="isInvoice2" value="1" <#if (supplier.isInvoice)??><#if supplier.isInvoice == 1>checked</#if></#if>/><label for="isInvoice2">商家</label>
							<p class='error-message' />
						</div>
                    
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>使用优购WMS：</label>
                            <input type="radio" class="supplier-query-radio" name="isUseYougouWms" id="isUseYougouWms1" value="1"  <#if (supplier.isUseYougouWms)?? && supplier.isUseYougouWms == 1 >checked</#if>><label for="isUseYougouWms1" class="supplier-query-title w25">是</label>
                   			<input type="radio" class="supplier-query-radio ml50" name="isUseYougouWms"  id="isUseYougouWms2" value="0"  <#if (supplier.isUseYougouWms)??><#if supplier.isUseYougouWms == 0 >checked</#if></#if>><label for="isUseYougouWms2">否</label>
                       	    <p class='error-message' />
                        </div>
                       </div>

                     <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>发货类型：</label> 
                        	<input type="radio" class="supplier-query-radio" name="deliveryType" id="gnfh" value="1" <#if (supplier.deliveryType)??><#if (supplier.deliveryType)?number == 1>checked</#if><#else>checked</#if>/><label for="gnfh" >国内发货</label> 
                   			<input type="radio" class="supplier-query-radio" style="margin-left:27px" name="deliveryType" id="hwzf" value="2" <#if (supplier.deliveryType)??><#if supplier.deliveryType?number == 2>checked</#if></#if>/><label for="hwzf">海外直发</label>
							<p class='error-message' />
						</div>
                    
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">&nbsp;</label>
                       	    <p class='error-message' />
                        </div>
                       </div>    
                       
                       <div class="supplier-query-wrap clearfix">
                         <div class="supplier-query-left">
                            <#--<label for="" class="supplier-query-title"><span class="Red">*</span>优惠券分摊比例：</label>
							<input type="text" class="supplier-query-text w136" id="couponsAllocationProportion"  name="couponsAllocationProportion"  value="<#if supplier?? && (supplier.couponsAllocationProportion)??>${supplier.couponsAllocationProportion!''}</#if>"/>&nbsp;%（0-100） 
                       		<p class='error-message' id="couponsAllocationProportionError"/> -->
          
                            <label for="" class="supplier-query-title"><span class="Red">*</span>卡券最高分摊：</label>
							<input type="text" class="supplier-query-text w80" id="maxCouponAmount"  name="maxCouponAmount" maxlength="9" value="<#if supplierExpand?? && (supplierExpand.maxCouponAmount)??>${supplierExpand.maxCouponAmount?string("#")}</#if>"/>&nbsp;元
                        	<p class='error-message' />
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>使用ERP系统对接：</label>
                            <input type="radio" class="supplier-query-radio" name="isUseERP" id="isUseERP1" value="1" <#if (supplierContract.isUseERP)??><#if supplierContract.isUseERP == '1'>checked</#if></#if>/><label for="isUseERP1" class="supplier-query-title w25">是</label>
                			<input type="radio" class="supplier-query-radio ml50" name="isUseERP" id="isUseERP2" value="0" <#if (supplierContract.isUseERP)?? && supplierContract.isUseERP == '0'>checked</#if>/><label for="isUseERP2">否</label>
                       	    <p class='error-message' />
                        </div>
                      </div>
                   <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>需缴纳保证金：</label>
                            <#if supplierContract?? && supplierContract.depositStatus?? && supplierContract.depositStatus != '' &&(supplierContract.depositStatus != '1') >
                             <input type="radio" class="supplier-query-radio" disabled name="isNeedDeposit" id="isNeedDeposit1" value="1" <#if (supplierContract.isNeedDeposit)?? && supplierContract.isNeedDeposit == '1'>checked</#if>/><label for="isNeedDeposit1" class="supplier-query-title w25">是</label>
                			 <input type="radio" class="supplier-query-radio ml50" disabled name="isNeedDeposit" id="isNeedDeposit2" value="0" <#if (supplierContract.isNeedDeposit)?? && supplierContract.isNeedDeposit == '0'>checked</#if>/><label for="isNeedDeposit2">否</label>
                        	<#else>
                        	 <input type="radio" class="supplier-query-radio" name="isNeedDeposit" id="isNeedDeposit1" value="1" <#if (supplierContract.isNeedDeposit)?? && supplierContract.isNeedDeposit == '1'>checked</#if>/><label for="isNeedDeposit1" class="supplier-query-title w25">是</label>
                			 <input type="radio" class="supplier-query-radio ml50" name="isNeedDeposit" id="isNeedDeposit2" value="0" <#if (supplierContract.isNeedDeposit)?? && supplierContract.isNeedDeposit == '0'>checked</#if>/><label for="isNeedDeposit2">否</label>
                        	</#if>
                        	<p class='error-message' />
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>需缴纳平台使用费：</label>
                            <#if supplierContract?? && supplierContract.useFeeStatus?? && supplierContract.useFeeStatus != '' &&(supplierContract.useFeeStatus != '1')> 
                             <input type="radio" class="supplier-query-radio" disabled name="isNeedUseFee" id="isNeedUseFee1" value="1" <#if (supplierContract.isNeedUseFee)??><#if supplierContract.isNeedUseFee == '1'>checked</#if></#if>/><label for="isNeedUseFee1" class="supplier-query-title w25">是</label>
                			 <input type="radio" class="supplier-query-radio ml50" disabled name="isNeedUseFee" id="isNeedUseFee2" value="0" <#if (supplierContract.isNeedUseFee)?? && supplierContract.isNeedUseFee == '0'>checked</#if>/><label for="isNeedUseFee2">否</label>
                        	<#else>
                        	 <input type="radio" class="supplier-query-radio" name="isNeedUseFee" id="isNeedUseFee1" value="1" <#if (supplierContract.isNeedUseFee)??><#if supplierContract.isNeedUseFee == '1'>checked</#if></#if>/><label for="isNeedUseFee1" class="supplier-query-title w25">是</label>
                			 <input type="radio" class="supplier-query-radio ml50" name="isNeedUseFee" id="isNeedUseFee2" value="0" <#if (supplierContract.isNeedUseFee)?? && supplierContract.isNeedUseFee == '0'>checked</#if>/><label for="isNeedUseFee2">否</label>
                        	</#if>
                        	<p class='error-message' />
                        </div>
                    
                        <div class="supplier-query-left" id="readyToHideDeposit">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>保证金金额：</label>
                            <input type="text" class="supplier-query-text w136" id="deposit" name="deposit" maxlength="10" value="<#if supplierContract?? && (supplierContract.deposit)??>${(supplierContract.deposit)?string("#.##")}</#if>"
                           			 <#if supplierContract?? && supplierContract.depositStatus?? && supplierContract.depositStatus!= '' &&(supplierContract.depositStatus != '1')>readonly="readonly"</#if> /> 元
                        	<p class='error-message' id="depositError"/>
                        	
                        </div>
                        <div class="supplier-query-right" id="readyToHideUseFee">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>平台使用费金额：</label>
                            <input type="text" class="supplier-query-text w136" id="useFee" name="useFee" maxlength="10"  value="<#if supplierContract?? && (supplierContract.useFee)??>${(supplierContract.useFee)?string("#.##")}</#if>"
                            		<#if supplierContract?? && supplierContract.useFeeStatus?? && supplierContract.useFeeStatus!= '' &&(supplierContract.useFeeStatus != '1')>readonly="readonly"</#if>/> 元
                       		<p class='error-message' id="useFeeError"/>
                        </div>
                    
                       
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">申报人：</label>
                            <input class="supplier-query-text w136" type="text" name="declarant" id="declarant" value="<#if (supplierContract.declarant)??>${supplierContract.declarant!''}</#if>">
							<p class='error-message' id="declarantError"/>
						</div>
                    
                        <div class="supplier-query-main">
                            <label for="" class="supplier-query-title">备注：</label>
                            <input type="text" class="supplier-query-text w600" name="remark" id="remark"  value="<#if supplier?? && (supplier.remark)??>${supplier.remark!''}</#if>" />
                        </div>
                    </div>    
                </div>
              
                <#import "../add_trademark.ftl" as page>
                <@page.trademark/>

				
               
                <div class="btn-box">
                    <a href="javascript:void(0);" class="yg-btn-gray btn-prev" style="position:relative;z-index: 1999;">上一步</a>
                    <a href="javascript:void(0);" onclick="saveAllInfo(this);" class="yg-btn-gray" style="position:relative;z-index: 1999;">保存</a>
                    <#if (supplier??&&(supplier.isValid)??&&(supplier.isValid==-1) || (editType??))>
                    <#else>
                    <a href="javascript:void(0);" onclick="saveAllInfoAndSubmit(this);" class="yg-btn-blue"/>保存并提交审核</a>
                    </#if>
                    <#if supplier?? && (supplier.id)??&& (supplier.id)!=''><#else><a href="javascript:void(0);" onclick="saveSegment(this);" onclick="saveSegment(this);" class="yg-btn-gray">保存草稿</a></#if>
                </div>
            </div>
        </div>
        <!-- 合同信息结束 -->

	</form>
    </div>
    
	

</body>
</html>
</#macro>
