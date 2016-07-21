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
<script src="${BasePath}/js/common/form/datepicker/WdatePicker.js" type="text/javascript"></script>
<script src="${BasePath}/js/supply/supplier.js" type="text/javascript"></script>
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
	<div class="cont-nav ft-sz-12">
		<span>采购管理 &gt; 采购单 &gt;添加明细</a>  </span>
	</div>
	<div class="pro-list">
				<div class="mb-btn-fd-bd">
					<div class="mb-btn-fd relative">
						<span class="btn-extrange absolute ft-sz-14">
							<ul class="onselect">
								<li class="pl-btn-lfbg">
								</li>
								<li class="pl-btn-ctbg">
									<a href="#" class="btn-onselc">添加明细</a>
								</li>
								<li class="pl-btn-rtbg">
								</li>
							</ul> </span>
					</div>
				</div>
			</div>			
 <form action="savePurchaseDetail.sc?purchaseId=${purchaseId}" method="post" id="submitForm"> 	
 <input type="hidden" id="productId" value=""/>	
    <div class="divH12"></div>   
    <div class="add-pro-div">    
        <div class="pro-baseinf">        	
			<div class="pro-baseinf-list">
            	<span class="text_details">采购类型：<font class="ft-cl-r">*</font></span>
                <select id="purchaseType" name="purchaseType" value="" >
                	<option value=""></option> 
                	<option value="102">自购</option> 
                	<option value="107">比例代销</option> 
                	<option value="106">协议代销</option> 
                	<option value="108">配折结算</option> 
                </select>              
            </div>            
            <div class="pro-baseinf-list">
            <input type="button" value="选择货品" alt="打开货品选择页面" onClick="selectCommodity('${BasePath}')"/> 
            	<span class="text_details">商品名称：<font class="ft-cl-r">*</font></span>
                <input type="text" id="commodityName" name="commodityName" value="" onblur="" />                              
            
            	<span class="text_details">货品编号：<font class="ft-cl-r">*</font></span> 
            	<input type="text" id="commodityCode" name="commodityCode" value="" onblur="" />             	          	
            
            	<span class="text_details">规格：<font class="ft-cl-r">*</font></span>
                <input type="text" id="specification" name="specification" value="" onblur="" />                
            </div>
            <div class="pro-baseinf-list">
            	<span class="text_details">采购数量：<font class="ft-cl-r">*</font></span> 
            	<input type="text" id="purchaseQuantity" name="purchaseQuantity" value="" onblur=""/> 
                <span class="text_details">单位：<font class="ft-cl-r">*</font></span>
                <input type="text" id=""unit" name="unit" value="" onblur=""/>
           
            	<span class="text_details">采购单价：<font class="ft-cl-r">*</font></span> 
            	<input type="text" id="purchasePrice" name="purchasePrice" value="" onblur=""/>         	
            
            	<span class="text_details">扣点比例：<font class="ft-cl-r">*</font></span>
                <input type="text" id="deductionRate" name="deductionRate" value="" onblur=""/>                
            </div>
           
		    
		    	<input type="button" value="保存" class="btn-sh" onClick="checkData()"/>
		    	<input type="button" value="取消" class="btn-sh" onClick="javascript:history.back() 	"/>        
		                           		
        </div>      
	</div>
	</form>	 
</div>
</body>
</html>
