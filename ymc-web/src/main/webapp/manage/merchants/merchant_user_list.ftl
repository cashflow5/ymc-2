<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-基本信息</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
<script type="text/javascript">
 //基本信息
 function baseInfo(){
  location.href="${BasePath}/merchants/login/to_MerchantsUser.sc" ;
 }
 
 //合同列表
 function contract(){
   location.href="${BasePath}/merchants/login/to_supplierContract.sc" ;
 }
 
 //联系人列表
 function linkMan(){
   location.href="${BasePath}/merchants/login/to_supplierLinkMan.sc" ;
 }
 
  </script>
</head>

<body>


	<div class="main_container">
			<div class="normal_box">
				<p class="title site">当前位置：商家中心 &gt; 设置 &gt; 基本信息</p>
					<div class="tab_panel">
							<ul class="tab">
							<li onclick="baseInfo();" class="curr"><span>基本信息</span></li>
							<li onclick="contract();"><span>合同列表</span></li>
							<li onclick="linkMan();"><span>联系人列表</span></li>
							</ul>
						<div class="tab_content">
							<div class="busindess_baseinfo">
							<fieldset class="x-fieldset x-fieldset-default" style="float:left;margin-right:10px;height:195px; width:275px;">
							<legend  class="x-fieldset-header">
							<a  class="x-tool-toggle" ></a><span class="x-fieldset-header-text">商家信息</span>
							<div class="x-clear"></div>
							</legend>
							<div  class="x-fieldset-body">
								商家编号： <#if userList??&&userList['supplier_code']??>${userList['supplier_code']!""}</#if><br />
								登录账户： <#if userList??&&userList['login_name']??>${userList['login_name']!""}</#if>
							</div>
							</fieldset>
							
							<fieldset class="x-fieldset x-fieldset-default" style="float:left;margin-right:10px;height:195px; width:275px;">
							<legend  class="x-fieldset-header">
							<a  class="x-tool-toggle" ></a><span class="x-fieldset-header-text">公司信息</span>
							<div class="x-clear"></div>
							</legend>
							<div  class="x-fieldset-body">
								公司名称：<#if userList??&&userList['supplier']??>${userList['supplier']!""}</#if><br />
								营业执照号：<#if userList??&&userList['business_license']??>${userList['business_license']!""}</#if><br />
								营业执照所在地：<#if userList??&&userList['business_local']??>${userList['business_local']!""}</#if><br />
								营业执照有效期：<#if userList??&&userList['business_vilidity']??>${userList['business_vilidity']!""}</#if><br />
								税务登记证号：<#if userList??&&userList['tallage_no']??>${userList['tallage_no']!""}</#if><br />
								组织机构代码：<#if userList??&&userList['institutional']??>${userList['institutional']!""}</#if><br />
								纳税人识别号：<#if userList??&&userList['taxpayer']??>${userList['taxpayer']!""}</#if><br />
							</div>
							</fieldset>
							
							
							<fieldset class="x-fieldset x-fieldset-default" style="float:right;height:195px; width:275px;">
							<legend  class="x-fieldset-header">
							<a  class="x-tool-toggle" ></a><span class="x-fieldset-header-text">财务信息</span>
							<div class="x-clear"></div>
							</legend>
							<div  class="x-fieldset-body">
								银行开户名：<#if userList??&&userList['contact']??>${userList['contact']!""}</#if><br />
								公司银行账号：<#if userList??&&userList['account']??>${userList['account']!""}</#if><br />
								开户行支行名称：<#if userList??&&userList['sub_bank']??>${userList['sub_bank']!""}</#if><br />
								开户银行所在地：<#if userList??&&userList['bank_local']??>${userList['bank_local']!""}</#if><br />
								优惠券分摊比例：<#if userList??&&userList['coupons_allocation_proportion']??>${userList['coupons_allocation_proportion']!""}</#if>%<br />
							</div>
							</fieldset>
							<p class="clear"></p>
							</div>
						</div>
				</div>
			</div>
		
			 
 </div>
	
	</div>
</body>
</html>