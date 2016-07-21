<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,优购网,B网络营销系统,商品管理" />
<meta name="Description" content=" , ,B网络营销系统-商品管理" />
<link rev="stylesheet" rel="stylesheet"  type="text/css" href="css/style.css"/>
<link rev="stylesheet" rel="stylesheet" type="text/css" href="css/css.css" />
<script type="text/javascript" src="../js/common.js"></script>
<script type="text/javascript" src="../js/jquery-1.3.2.min.js" ></script>
<script type="text/javascript" src="${BasePath}/js/supply/supplier.js" ></script>
<#include "../../supply_include.ftl">
<#include "../../../yitiansystem/yitiansystem-include.ftl">
<script src="${BasePath}/js/supply/addSupplierPrice.js" type="text/javascript"></script>
<title>B网络营销系统-采购管理-优购网</title>
<script type="text/javascript">
$(document).ready(function(){
	$("#purchaseType").attr("value",$("#supplierT").attr("value"));		
})
</script>
</head>
<body>
<div class="main-body" id="main_body">
<input type="hidden" id="basePath" value="${BasePath}">
<div class="ytback-tt-1 ytback-tt">
            	<span>您当前的位置：</span>采购管理 &gt; 供应商管理 &gt;编辑
	</div>
			<div class="pro-list">
				<div class="mb-btn-fd-bd">
					<div class="mb-btn-fd relative">
						<span class="btn-extrange absolute ft-sz-14">
							<ul class="onselect">
								<li class="pl-btn-lfbg">
								</li>
								<li class="pl-btn-ctbg">
									<a href="#" class="btn-onselc">编辑供应商价格标签</a>
								</li>
								<li class="pl-btn-rtbg">
								</li>
							</ul> </span>
					</div>
				</div>
			</div>
 <form action="updateSupplierPrice.sc" method="post" id="submitForm" name="submitForm">
 <input type="hidden" name="id" value="${supplierPrice.id}"/> 
 <input type="hidden" id="supplierT" name="supplierT" value="${supplierPrice.purchaseType?default("")}"/> 		
 <input type="hidden" id="supplierId" name="supplierId" value="${supplierId}"/> 
    <div class="divH12"></div>
    <div class="add-pro-div">
        <div class="pro-baseinf">
        <div class="pro-baseinf-list">
            	<span class="text_details">采购类型：<font class="ft-cl-r">*</font></span>
                <select id="purchaseType" name="purchaseType" value="" >
                	<option value=""></option> 
                	<option value="102">自购</option> 
                	<option value="106">比例代销</option> 
                	<option value="107">协议代销</option> 
                </select>
                <span id="purchaseTypeTip"></span>              
            </div>            
            <div class="pl-sort-lev">
            	<span class="text_details">起购数量：<font class="ft-cl-r">*</font></span>
                <input type="text" id="basepurchaseQuantity" name="basepurchaseQuantity" value="${supplierPrice.basepurchaseQuantity?c?default("")}"/>                
           		<span id="basepurchaseQuantityTip"></span>
            </div>           
            <div class="pro-baseinf-list">
            	<span class="text_details">单价：<font class="ft-cl-r">*</font></span> 
            	<input type="text" id="actualPrice" name="actualPrice" value="${supplierPrice.actualPrice?default("")}" onblur="calculatePrice()"/>           	
            	<span id="actualPriceTip"></span>
            </div> 
                      
            <div class="pl-sort-lev">
            	<span class="text_details">截止日期：</span>
                <input type="text" id="closeDate" name="closeDate" class="Wdate" value="${supplierPrice.closeDate?string("yyyy-MM-dd")?default("")}" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'closeDate\')||\'2010-12-31\'}'})" size="18"/>                            	
            </div>
            <div class="pro-baseinf-list">
                <span class="text_details">到货周期：<font class="ft-cl-r">*</font></span>
                <input type="text" id=""round" name="round" stype="width:30px" maxlength="3" value="${supplierPrice.round?default("")}" onblur=""/>个工作日
           		<span id="roundTip"></span>
            </div> 
	    <div class="div-pl-bt">
	    	<input type="submit" value="保存" class="btn-sh" onClick=""/>
	    	<input type="button" value="取消" class="btn-sh" onClick="javascript:window.history(-1)"/>        
	    </div>      
      </div>
	</div>
	</form>
</div>
</body>
</html>

