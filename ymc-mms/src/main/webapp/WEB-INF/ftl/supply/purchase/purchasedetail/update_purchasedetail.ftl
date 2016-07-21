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
<#include "../../../yitiansystem/yitiansystem-include.ftl">
<title>B网络营销系统-采购管理-优购网</title>
<script type="text/javascript">
$(document).ready(function(){	
	$("#purchaseType").attr("value",$("#type").attr("value"));	
})
		
function typeChange() {
	alert($("#purchaseType").attr("value"));
	if($("#purchaseType").attr("value")=="102") {
		$("#deductionRateLable").hide();
		$("#deductionRate").hide();
	}
	if("#purchaseType").attr("value")=="106") {
		$("#deductionRateLable").show();
		$("#deductionRate").show();
	}
	if("#purchaseType").attr("value")=="107") {
		$("#deductionRateLable").show();
		$("#deductionRate").show();
	}
}		
</script>
</head>
<body>
<div class="main-body" id="main_body">
<input type="hidden" id="basePath" value="${BasePath}">
<div class="cont-nav ft-sz-12">
				<span>采购管理 &gt; 采购单 &gt;编辑明细</a>  </span>
			</div>
			<div class="pro-list">
				<div class="mb-btn-fd-bd">
					<div class="mb-btn-fd relative">
						<span class="btn-extrange absolute ft-sz-14">
							<ul class="onselect">
								<li class="pl-btn-lfbg">
								</li>
								<li class="pl-btn-ctbg">
									<a href="#" class="btn-onselc">编辑明细</a>
								</li>
								<li class="pl-btn-rtbg">
								</li>
							</ul> </span>
					</div>
				</div>
				<div class="add-newpd ft-sz-12 fl-rt"><a href="${BasePath}/supply/manage/purchase/toManage.sc" alt="返回采购单列表">返回</a></div>	
			</div>
 <form action="updatePurchaseDetail.sc" method="post" id="submitForm" name="submitForm">
 <input type="hidden" id="purchaseId" name="purchaseId" value="${purchaseId}"/> 
 <input type="hidden" id="purchaseDetailId" name="purchaseDetailId" value="${purchaseDetail.id?default("")}"/>
 <input type="hidden" id="type" name="type"	value="${purchaseDetail.purchaseType?default("")}"/>	
    <div class="divH12"></div>
    <div class="add-pro-div">
        <div class="pro-baseinf">        	
            <div class="pro-baseinf-list">
            	<span class="text_details">商品名称：<font class="ft-cl-r">*</font></span>
                <Strong>${purchaseDetail.commodityName?default("")}</Strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;                       
            	<span class="text_details">商品编号：<font class="ft-cl-r">*</font></span> 
            	<Strong>${purchaseDetail.commodityCode?default("")}</Strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;           	            
            	<span class="text_details">规格：<font class="ft-cl-r">*</font></span> 
            	${purchaseDetail.specification?default("")}           	
            </div>            
            <div class="pl-sort-lev">
            	<span class="text_details">库存数量：<font class="ft-cl-r">*</font></span> 
            	${purchaseDetail.stockQuantity?default("0")} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
            	<span class="text_details">采购数量：<font class="ft-cl-r">*</font></span>
                <input type="text" id="purchaseQuantity" name="purchaseQuantity" value="${purchaseDetail.purchaseQuantity?default("")}" onblur=""/>      
            	<span class="text_details">单位：<font class="ft-cl-r">*</font></span>
                <input type="text" id="unit" name="unit" value="${purchaseDetail.unit?default("")}" onblur=""/>                                       	          
            </div>
            <div class="pro-baseinf-list">
                <span class="text_details">采购单价：<font class="ft-cl-r">*</font></span>
                <input type="text" id=""purchasePrice" name="purchasePrice" value="${purchaseDetail.purchasePrice?default("")}" onblur=""/>
                <span id="deductionRateLable" class="text_details">扣点比例：<font class="ft-cl-r">*</font></span> 
            	<input type="text" id="deductionRate" name="deductionRate" value="${purchaseDetail.deductionRate?default("")}" onblur=""/>           	
            </div>
            <div class="pro-baseinf-list">
            	
            </div>                       
	    <div class="div-pl-bt">
	    	<input type="submit" value="保存" class="btn-sh" onClick=""/>
	    	<input type="button" value="取消" class="btn-sh" onClick="javascript:history.back() 	"/>        
	    </div>      
      </div>
	</div>
	</form>
</div>
</body>
</html>
