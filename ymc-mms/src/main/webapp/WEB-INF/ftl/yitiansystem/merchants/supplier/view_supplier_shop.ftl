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
        <!--工具栏start-->
        <#import "view_title.ftl" as page>  
			<@page.viewTitle pageName="shop"/>
                 
            <div class="modify">
                <h1 class="supplier-title">店铺负责人信息</h1>
                <div class="form-box">
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">姓名：</label>
                            <span class="supplier-query-cont"><#if contactsFormVo??>${(contactsFormVo.chiefContact)!''}</#if></span>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">手机：</label>
                            <span class="supplier-query-cont"><#if contactsFormVo??>${(contactsFormVo.chiefMobilePhone)!''}</#if></span>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">电子邮箱：</label>
                            <span class="supplier-query-cont"><#if contactsFormVo??>${(contactsFormVo.chiefEmail)!''}</#if></span>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">座机：</label>
                            <span class="supplier-query-cont"><#if contactsFormVo??>${(contactsFormVo.chiefTelePhone)!''}</#if></span>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">QQ号码：</label>
                            <span class="supplier-query-cont">${(contactsFormVo.chiefQQ)!''}</span>
                        </div>
                    </div>
                </div>

                <h1 class="supplier-title">售后退换货地址</h1>
                <div class="form-box">
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">收货人姓名：</label>
                            <span class="supplier-query-cont"><#if rejectedAddress??>${(rejectedAddress.consigneeName)!''}</#if></span>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">收货人电话：</label>
                            <span class="supplier-query-cont"><#if rejectedAddress??>${(rejectedAddress.consigneePhone)!''}</#if></span>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">收货人地址：</label>
							<span class="supplier-query-cont" >
							<#if rejectedAddress??>${(rejectedAddress.warehouseArea)!''}</#if>
							</span>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">邮编号码：</label>
                            <span class="supplier-query-cont"><#if rejectedAddress??&&rejectedAddress.warehousePostcode??>${rejectedAddress.warehousePostcode!''}</#if></span>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-main">
                            <label for="" class="supplier-query-title">详细地址：</label>
							<span class="supplier-query-cont" title="<#if rejectedAddress??>${(rejectedAddress.warehouseAdress)!''}</#if>"><#if rejectedAddress??>${(rejectedAddress.warehouseAdress)!''}</#if></span>
                        </div>
                    </div>
                </div>

                <h1 class="supplier-title">业务联系人</h1>   
                <div class="form-box">
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">姓名：</label>
                            <span class="supplier-query-cont"><#if contactsFormVo??>${(contactsFormVo.businessContact)!''}</#if></span>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">手机：</label>
                            <span class="supplier-query-cont"><#if contactsFormVo??>${(contactsFormVo.businessMobilePhone)!''}</#if></span>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">电子邮箱：</label>
                            <span class="supplier-query-cont"><#if contactsFormVo??>${(contactsFormVo.businessEmail)!''}</#if></span>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">座机：</label>
                            <span class="supplier-query-cont"><#if contactsFormVo??>${(contactsFormVo.businessTelePhone)!''}</#if></span>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">QQ号码：</label>
                            <span class="supplier-query-cont"><#if contactsFormVo??>${(contactsFormVo.businessQQ)!''}</#if></span>
                        </div>
                    </div>
                </div>

                <h1 class="supplier-title">售后联系人：</h1>
                <div class="form-box">
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">姓名：</label>
                            <span class="supplier-query-cont"><#if contactsFormVo??>${(contactsFormVo.afterSaleContact)!''}</#if></span>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">手机：</label>
                            <span class="supplier-query-cont"><#if contactsFormVo??>${(contactsFormVo.afterSaleMobilePhone)!''}</#if></span>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">电子邮箱：</label>
                            <span class="supplier-query-cont"><#if contactsFormVo??>${(contactsFormVo.afterSaleEmail)!''}</#if></span>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">座机：</label>
                            <span class="supplier-query-cont"><#if contactsFormVo??>${(contactsFormVo.afterSaleTelePhone)!''}</#if></span>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">QQ号码：</label>
                            <span class="supplier-query-cont"><#if contactsFormVo??>${(contactsFormVo.afterSaleQQ)!''}</#if></span>
                        </div>
                    </div>
                </div>

                <h1 class="supplier-title">财务联系人：</h1>
                <div class="form-box">
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">姓名：</label>
                            <span class="supplier-query-cont"><#if contactsFormVo??>${(contactsFormVo.financeContact)!''}</#if></span>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">手机：</label>
                            <span class="supplier-query-cont"><#if contactsFormVo??>${(contactsFormVo.financeMobilePhone)!''}</#if></span>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">电子邮箱：</label>
                            <span class="supplier-query-cont"><#if contactsFormVo??>${(contactsFormVo.financeEmail)!''}</#if></span>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">座机：</label>
                            <span class="supplier-query-cont"><#if contactsFormVo??>${(contactsFormVo.financeTelePhone)!''}</#if></span>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">QQ号码：</label>
                            <span class="supplier-query-cont"><#if contactsFormVo??>${(contactsFormVo.financeQQ)!''}</#if></span>
                        </div>
                    </div>
                </div>

                <h1 class="supplier-title">技术联系人：</h1>
                <div class="form-box">
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">姓名：</label>
                            <span class="supplier-query-cont"><#if contactsFormVo??>${(contactsFormVo.techContact)!''}</#if></span>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">手机：</label>
                            <span class="supplier-query-cont"><#if contactsFormVo??>${(contactsFormVo.techMobilePhone)!''}</#if></span>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">电子邮箱：</label>
                            <span class="supplier-query-cont"><#if contactsFormVo??>${(contactsFormVo.techEmail)!''}</#if></span>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">座机：</label>
                            <span class="supplier-query-cont"><#if contactsFormVo??>${(contactsFormVo.techTelePhone)!''}</#if></span>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">QQ号码：</label>
                            <span class="supplier-query-cont"><#if contactsFormVo??>${(contactsFormVo.techQQ)!''}</#if></span>
                        </div>
                    </div>
                </div>

                <h1 class="supplier-title">账户信息：</h1>
                
                <div class="form-box">
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">登陆账号：</label>
                            <span class="supplier-query-cont"><#if merchantUser??>${(merchantUser.loginName)!''}</#if></span>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">登陆密码：</label>
                            <span class="supplier-query-cont"><#if merchantUser??&&merchantUser.password??>************</#if></span>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">绑定邮箱：</label>
                            <span class="supplier-query-cont"><#if merchantUser??>${(merchantUser.email)!''}</#if></span>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">绑定手机：</label>
                            <span class="supplier-query-cont"><#if merchantUser??>${(merchantUser.mobileCode)!''}</#if></span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
