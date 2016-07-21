<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,优购网,B网络营销系统,商品管理" />
<meta name="Description" content=" , ,B网络营销系统-商品管理" />
<#include "../../../yitiansystem/yitiansystem-include.ftl">
<title>B网络营销系统-采购管理-优购网</title>
</head>
<body>
<div class="container">
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class='curr'>
				  <span>添加</span>
				</li>
			</ul>
		</div>
 <div class="modify">
 <form action="saveSupplierType.sc" method="post" id="submitForm"> 		
    <div class="add-pro-div">
        <div class="pro-baseinf">
        	<div class="pro-baseinf-list">
            	<span class="text_details">类型值：<font class="ft-cl-r">*</font></span>
                <input type="text" id="typeValue" name="typeValue" value="" onblur=""/>
                <span id="typeValueTip"></span>               
            </div>                  
        </div>
	    <div class="div-pl-bt">
	    	<input type="submit" value="保存" class="btn-add-normal" onClick=""/>
	    	<input type="button" value="取消" class="btn-add-normal" onClick="closewindow()"/>        
	    </div>
	</div>
	</form>
</div>
</body>
</html>
