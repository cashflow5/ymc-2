<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,优购网,B网络营销系统,采购管理" />
<meta name="Description" content=" , ,B网络营销系统-采购管理" />
<link rev="stylesheet" rel="stylesheet"  type="text/css" href="css/style.css"/>
<link rev="stylesheet" rel="stylesheet" type="text/css" href="css/css.css" />
<script type="text/javascript" src="../js/common.js"></script>
<script type="text/javascript" src="../js/jquery-1.3.2.min.js" ></script>
<script src="${BasePath}/js/supply/supplier.js" type="text/javascript"></script>
<#include "../../supply_include.ftl">
<#include "../../../yitiansystem/yitiansystem-include.ftl">
<title>B网络营销系统-采购管理-优购网</title>
<script type="text/javascript">
function checkData(){
	var elObj = document.getElementById("commodityName");
	if(elObj.value == ""){
		alert("请输入商品名称！");
		elObj.focus();
		return false;
	}
	
	document.getElementById("submitForm").submit();
}

function calculatePrice(){
	var elObj = document.getElementById("normPrice");
	var plObj = document.getElementById("payPatio");
	if(elObj.value == ""){
		alert("请输入标准价格！");
		elObj.focus();
		return false;
	}	
	if(plObj.value == ""){
		alert("请输入享受折扣！");
		plObj.focus();
		return false;
	}	
	var obj=document.getElementById("actualPrice");
	obj.value=elObj.value*(plObj.value/10);	
	
}
</script>
</head>
<body>
<div class="main-body" id="main_body">
<input type="hidden" id="basePath" value="${BasePath}">
			<div class="ytback-tt-1 ytback-tt">
            	<span>您当前的位置：</span>采购管理 &gt; 供应商价格管理 &gt;添加供应商价格
	</div>
			<div class="pro-list">
				<div class="mb-btn-fd-bd">
					<div class="mb-btn-fd relative">
						<span class="btn-extrange absolute ft-sz-14">
							<ul class="onselect">
								<li class="pl-btn-lfbg">
								</li>
								<li class="pl-btn-ctbg">
									<a href="#" class="btn-onselc">添加供应商价格</a>
								</li>
								<li class="pl-btn-rtbg">
								</li>
							</ul> </span>
					</div>
				</div>
			</div>
 <form action="saveSupplierPrice.sc" method="post" id="submitForm"> 
 <input type="hidden" id="supplierId" name="supplierId" value=""/>
 <input type="hidden" id="productId" name="productId" value=""/>		
    <div class="divH12"></div>
    <div class="add-pro-div">
        <div class="pro-baseinf">
        	<div class="pro-baseinf-list">
        		<input type="button" value="选择供应商" alt="打开供应商选择页面" onClick="selectSupplier('${BasePath}')"/> 
            	<span class="text_details">供应商：<font class="ft-cl-r">*</font></span>
                <input type="text" id="supplier" name="supplier" value="" onblur=""/>
            	<span class="text_details">采购类型：<font class="ft-cl-r">*</font></span>
                <select id="purchaseType" name="purchaseType" value="" >
                	<option value="">请选择</option> 
                	<option value="102">自购</option> 
                	<option value="106">比例代销</option> 
                	<option value="107">协议代销</option> 
                </select>
                              
            </div>
            <div class="pro-baseinf-list">
            <input type="button" value="选择货品" alt="打开货品选择页面" onClick="selectCommodity('${BasePath}')"/> 
            	<span class="text_details">商品名称：<font class="ft-cl-r">*</font></span>
                <input type="text" id="commodityName" name="commodityName" value="" onblur=""/>                         
            	<span class="text_details">货品编号：<font class="ft-cl-r">*</font></span> 
            	<input type="text" id="commodityCode" name="commodityCode" value="" onblur=""/>           	
            </div>           
            
            <div class="pro-baseinf-list">                      	            
            	<span class="text_details">实际价格：<font class="ft-cl-r">*</font></span> 
            	<input type="text" id="actualPrice" name="actualPrice" value="" onblur=""/>
            	<span class="text_details">代销结算比例：<font class="ft-cl-r">*</font></span> 
            	<input type="text" id="actualPrice" name="actualPrice" value="" onblur=""/>           	
            </div> 
            <div class="pl-sort-lev">
            	<span class="text_details">起购数量：<font class="ft-cl-r">*</font></span>
                <input type="text" id="basepurchaseQuantity" name="basepurchaseQuantity" maxlength="3" value=""/>                
            
            	<span class="text_details">截止日期：<font class="ft-cl-r">*</font></span>
                <input type="text" id="closeDate" name="closeDate" class="Wdate" value="" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'closeDate\')||\'2010-12-31\'}'})" size="18"/>                
           
                <span class="text_details">到货周期：<font class="ft-cl-r">*</font></span>
                <input type="text" id=""round" name="round" maxlength="3" value="" onblur=""/>个工作日
            </div> 
	    <div class="div-pl-bt">
	    	<input type="button" value="保存" class="btn-sh" onClick="checkData()"/>
	    	<input type="button" value="取消" class="btn-sh" onClick="javascript:history.back() 	"/>        
	    </div>      
                       		
        </div>  
    
	</div>
	</form>
</div>
</body>
</html>
