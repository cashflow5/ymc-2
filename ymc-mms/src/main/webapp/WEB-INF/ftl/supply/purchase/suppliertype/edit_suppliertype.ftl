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
<script type="text/javascript" src="${BasePath}/js/yitiansystem/cms/brand.js" ></script>
<#include "../../../yitiansystem/yitiansystem-include.ftl">
<script type="text/javascript" src="${BasePath}/js/supply/addSupplierType.js" ></script>
<title>B网络营销系统-采购管理-优购网</title>
</head>
<body>
<div class="container">
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class='curr'>
				  <span>编辑</span>
				</li>
			</ul>
		</div>
 <div class="modify">
 <form action="${BasePath}/supply/manage/suppliertype/u_editType.sc" method="post" id="submitForm"> 	
 	<input type="hidden" name="id" value="${type.id}"/>
    <div class="divH12"></div>
    <div class="add-pro-div">
        <div class="pro-baseinf">
        	<div class="pro-baseinf-list">
            	<span class="text_details">类型值：<font class="ft-cl-r">*</font></span>
                <input type="text" id="typeValue" name="typeValue" value="${type.typeValue!''}"/>
                <span id="typeValueTip"></span>               
            </div>                  
                       		
        </div>
    <div class="div-pl-bt">
    	<input type="submit" value="保存" class="btn-add-normal" />
    	<input type="button" value="取消" class="btn-add-normal" onClick="closewindow();"/>        
    </div>
    
	</div>
	</form>
</div>
</body>
</html>
