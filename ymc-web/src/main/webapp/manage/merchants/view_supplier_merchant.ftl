<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   <link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/manage/merchant/merchans_center.css"/>
    
  <title>优购商城--商家后台</title>
  </head>
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
  <form id="downloadForm" action="${BasePath}/merchants/login/downLoadContractAttachment.sc" method="post"> 
	 			<input type="hidden" name="fileName" id="fileName">
	 			<input type="hidden" name="realName" id="realName">
	</form>
           <div id="newmain" class="main_bd fr">
            <div class="main_container">
                <div class="normal_box">
                    <p class="title site">当前位置：商家中心 &gt; 设置 &gt; 基本信息</p>
                    <div class="tab_panel relative">
                        <ul class="tab">
                            <li   class="curr"><span>基本信息</span></li>
                            <li onclick="location.href='${BasePath}/merchants/login/to_merchant_shop.sc'"><span>店铺信息</span></li>
                            <li onclick="location.href='${BasePath}/merchants/login/to_view_supplier_contract.sc'"><span>合同信息</span></li>
                        </ul>
                        <div class="tab_content">
                            <div class="info-box">
                                <h3>公司信息</h3>
                                <ul class="info-list clearfix">
                                    <li><span class="stitle">公司名称：</span>${(supplier.supplier)?default('')}</li>
                                    <li><span class="stitle">公司简称：</span>${(supplier.simpleName)?default('')}</li>
                                    <li><span class="stitle">营业执照经营范围：</span>${(supplierExpand.businessRange)?default('')}</li>
                                    <li><span class="stitle">营业执照注册号：</span>${(supplier.businessLicense)?default('')}</li>
                                    <li><span class="stitle">注册所在地址：</span>
                                    ${(supplierExpand.registerProvince)!''}
								${(supplierExpand.registerCity)!''}
								${(supplierExpand.registerArea)!''} </li>
                                    <li><span class="stitle">营业执照有效期：</span>从
                                    ${(supplierExpand.businessValidityStart)?default('----')} 至
                                    ${(supplierExpand.businessValidityEnd)?default('----')}
									<#if (supplierExpand.businessRemainingDays)??>
										<#if (supplierExpand.businessRemainingDays<90) && (supplierExpand.businessRemainingDays>0)>
										(<i class="yellow">即将过期，请及时更新</i>)
										<#elseif supplierExpand.businessRemainingDays<=0>
										 (<i class="red">已过期请立即更新</i>)
										</#if>
									</#if>
                                     
                                    </li>
                                    <li><span class="stitle">详细地址：</span>${(supplierExpand.registerDetails)?default('')}</li>
                                    <li><span class="stitle">营业执照副本：</span>
                                    
                                     <#if supplierContract??&&(supplierContract.attachmentList)??&&(supplierContract.attachmentList?size>0)>
						              <#list supplierContract.attachmentList as item >
						                <#if item['attachmentType']=='9'>
						                  [<a href="javascript:void(0)" class="download blue" fileName="${item['attachmentName']!''}" realName="${item['attachmentRealName']!''}">查看</a>]
						                </#if>
						              </#list>
						            </#if>
                                   
                                </ul>
                            </div>
                            <div class="info-box">
                                <h3>组织机构代码证</h3>
                                <ul class="info-list clearfix">
                                    <li><span class="stitle">组织机构代码：</span>${(supplier.institutional)?default('')}</li>
                                    <li><span class="stitle">组织机构代码证：</span>
                                     <#if supplierContract??&&(supplierContract.attachmentList)??&&(supplierContract.attachmentList?size>0)>
							              <#list supplierContract.attachmentList as item >
							                <#if item['attachmentType']=='5'>
							                  [<a href="javascript:void(0)" class="download blue" fileName="${item['attachmentName']!''}" realName="${item['attachmentRealName']!''}">查看</a>]
							                </#if>
							              </#list>
							            </#if>
                                    </li>
                                    <li><span class="stitle">组织机构代码证有效期：</span>从 
                                   
							<#if (supplierExpand.institutionalValidityStart)??>${supplierExpand.institutionalValidityStart}<#else>----</#if> 
							至 <#if (supplierExpand.institutionalValidityEnd)??>${supplierExpand.institutionalValidityEnd}<#else>----</#if>
							<#if (supplierExpand.institutionalRemainingDays)??>
							<#if (supplierExpand.institutionalRemainingDays<90) && (supplierExpand.institutionalRemainingDays>0)>
							(<i class="yellow">即将过期，请及时更新</i>)
							<#elseif supplierExpand.institutionalRemainingDays<=0>
							 (<i class="red">已过期请立即更新</i>)
							</#if>
							</#if>
                           </li>
                                </ul>
                            </div>
                            <div class="info-box">
                                <h3>税务登记证</h3>
                                <ul class="info-list clearfix">
                                    <li><span class="stitle">纳税人识别号：</span>
                                   
                                    ${(supplier.tallageNo)?default('')}
                                    </li>
                                    <li><span class="stitle">税务登记证：</span>
                                      <#if supplierContract??&&(supplierContract.attachmentList)??&&(supplierContract.attachmentList?size>0)>
						              <#list supplierContract.attachmentList as item >
						                <#if item['attachmentType']=='6'>
						                  [<a href="javascript:void(0)" class="download blue" fileName="${item['attachmentName']!''}" realName="${item['attachmentRealName']!''}">查看</a>]
						                </#if>
						              </#list>
						            </#if>
                                    </li>
                                    <li><span class="stitle">纳税人类型：</span>
                                    
                                     <#if supplier??>
	                                     <#if supplier.taxplayerType == '1'>一般纳税人</#if>
	                                     <#if supplier.taxplayerType == '0'>非一般纳税人</#if>
                                     </#if>
                                     </li>
                                    <li><span class="stitle">增值税专用发票：</span>
                                      <#if supplier??><#if supplier.isAddValueInvoice == 1><span>是</span></#if>
                                      <#if supplier.isAddValueInvoice == 0><span>否</span></#if></#if>
                                    </li>
                                    <li><span class="stitle">增值税税率：</span>${(supplier.taxRate)?default('')}%</li>
                                    <li><span class="stitle">一般纳税人资格证：</span>
                                      <#if supplierContract??&&(supplierContract.attachmentList)??&&(supplierContract.attachmentList?size>0)>
						              <#list supplierContract.attachmentList as item >
						                <#if item['attachmentType']=='7'>
						                  [<a href="javascript:void(0)" class="download blue" fileName="${item['attachmentName']!''}" realName="${item['attachmentRealName']!''}">查看</a>]
						                </#if>
						              </#list>
						            </#if>
                                    </li>   
                                </ul>
                            </div>
                            <div class="info-box">
                                <h3>开票银行信息</h3>
                                <ul class="info-list clearfix">
                                    <li><span class="stitle">银行开户名：</span>${(supplier.contact)?default('')}</li>
                                    <li><span class="stitle">开户行支行名称：</span>${(supplier.bank)?default('')}</li>
                                    <li><span class="stitle">开票地址：</span>
                                     ${(supplier.invoiceAddress)?default('')}
                                    </li>
                                    <li><span class="stitle">电话：</span>	
		 								${(supplier.invoicePhone)?default('')}
                                     </li>
                                    <li><span class="stitle">银行账号：</span>${(supplier.account)?default('')}</li>
                                </ul>
                            </div>
                            <div class="info-box">
                                <h3>结算银行信息</h3>
                                <ul class="info-list clearfix">
                                    <li><span class="stitle">开户行支行名称：</span>${(supplier.clearBank)?default('')}</li>                                    
                                    <li><span class="stitle">银行账号：</span>${(supplier.clearAccount)?default('')}</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
           
		 
 
 
</body>
</html>
