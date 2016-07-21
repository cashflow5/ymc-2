<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,优购网,B网络营销系统,栏目菜单管理" />
<meta name="Description" content=" , ,B网络营销系统-栏目菜单管理" />
<script type="text/javascript" src="../../js/common.js"></script>
<title>B网络营销系统-采购管理-优购网</title>
<#include "../../../yitiansystem/yitiansystem-include.ftl">
<script type="text/javascript" src="${BasePath}/js/supply/addSupplierLogin.js" ></script>
</head>
<body>
<div class="main-body" id="main_body">
	<div class="ytback-tt-1 ytback-tt">
            	<span>您当前的位置：</span>采购管理 &gt;供应商管理 &gt; 编辑
	</div>
    <div class="pro-list">
	<div class="mb-btn-fd-bd">
    <div class="add-pro-div">
    	<span class="ft-cl-n ft-sz-14 ft-wt-b">
    	<ul class="unselect">
			<li class="pl-btn-lfbg">
			</li>
			<li class="pl-btn-ctbg">
				<a href="toUpdate.sc?id=${supplier.id}" class="btn-onselc">基本信息</a>
			</li>
			<li class="pl-btn-rtbg">
			</li>
		</ul> 
    	<ul class="onselect">								
			<li class="pl-btn-lfbg"></li>
			<li class="pl-btn-ctbg"><a href="#">登录信息</a></li>
			<li class="pl-btn-rtbg"></li>								
		</ul>
		<ul class="unselect">								
			<li class="pl-btn-lfbg"></li>
			<li class="pl-btn-ctbg"><a href="toUpdateSupplierFinance.sc?id=${supplier.id}">财务信息</a></li>
			<li class="pl-btn-rtbg"></li>								
		</ul>
		<ul class="unselect">								
			<li class="pl-btn-lfbg"></li>
			<li class="pl-btn-ctbg"><a href="toSupplierContact.sc?supplier=${supplier.id}">联系人列表</a></li>
			<li class="pl-btn-rtbg"></li>								
		</ul>	
		<ul class="unselect">								
			<li class="pl-btn-lfbg"></li>
			<li class="pl-btn-ctbg"><a href="toSupplierPrice.sc?supplierId=${supplier.id}"">价格列表</a></li>
			<li class="pl-btn-rtbg"></li>								
		</ul>										
		</span>
		</div>
		</div>
		</div>
		<div class="add-pro-div">
		<form id="submitForm" name="submitForm" action="updateSupplierLogin.sc" method="post">
			<input type="hidden" name="id" id="id" value="${supplier.id}"/>
			
	        <div class="pro-baseinf">
	        	<div class="pro-baseinf-list">
	            	<span class="div-targe ft-sz-14">登录名：</span>
	                ${supplier.loginAccount?default("")}	            	
	            </div>       

	            <div class="pro-baseinf-list">
	            	<span class="div-targe ft-sz-14">登录密码：<font class="ft-cl-r">*</font></span>
	            	<input type="password" name="loginPassword" id="loginPassword" maxLength="8" value="${supplier.loginPassword?default("")}"/>
	           		<span id="loginPasswordTip"></span>
	            </div>	            
	            
			    <div class="div-pl-bt">
			    	<input type="submit" value="保存" onclick="" class="btn-sh"/>
			        <input type="button" value="取消" onclick="javascript:window.location='toManage.sc';" class="btn-sh"/>
			    </div>
			</div>
		</form>
	</div>
</div>
</body>
</html>
