<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,优购网,B网络营销系统,商品管理" />
<meta name="Description" content=" , ,B网络营销系统-商品管理" />
<link rev="stylesheet" rel="stylesheet"  type="text/css" href="css/style.css"/>
<link rev="stylesheet" rel="stylesheet" type="text/css" href="css/css.css" />
<#include "../../../yitiansystem/yitiansystem-include.ftl">
<script type="text/javascript" src="${BasePath}/js/supply/addSupplierContact.js" ></script>
<script type="text/javascript" src="${BasePath}/js/supply/supplier.js" ></script>
<title>网络营销系统-采购管理-优购网</title>
</head>
<body>
<div class="main-body" id="main_body">
<input type="hidden" id="basePath" value="${BasePath}">			
 <form action="saveSupplierContact.sc" method="post" id="submitForm" id="submitForm">
 <input type="hidden" id="param" name="param" value="${param?default("")}"/> 		
    <div class="divH12"></div>
    <div class="add-pro-div">
        <div class="pro-baseinf">
        	<div class="pro-baseinf-list">
        		<table cellpadding="0" cellspacing="0" class="list_table">
        		<tr>
        		<td>
            	<span class="text_details">姓名：<font class="ft-cl-r">*</font></span>
                </td>
                <td>                
                <input type="text" id="contact" maxlength="10" name="contact" value="" onblur=""/>
                </td>
                <td>
                <span id="contactTip"></span>                        
            	</td>
            	</tr>
            	
            	<tr>
            	<td>
            	<span class="text_details">业务类型：<font class="ft-cl-r">*</font></span>
                </td>
                <td>
                <select id="type" name="type" value=""> 
	        	   <option value="">请选择</option>
	        	   <option value="1">业务</option>
	               <option value="2">售后</option>
	               <option value="3">仓储</option>
	               <option value="4">财务</option>
	               <option value="5">技术</option>
                </select> 
                </td>
                <td>
                <span id="typeTip"></span>
                </td>
                </tr>
                
                <tr>
                <td>                      
            	<span class="text_details">电话：<font class="ft-cl-r">*</font></span> 
            	</td>
            	<td>
            	<input type="text" id="telePhone" name="telePhone" maxlength="20" value="" onblur=""/>           	
           		</td>
           		<td>
           		<span id="telePhoneTip"></span>
           		</td>
           		</tr>
           		
           		<tr>           		
           		<td>
            	<span class="text_details">手机：<font class="ft-cl-r">*</font></span>
                </td>
                <td>                
                <input type="text" id="mobilePhone" name="mobilePhone" maxlength="11" value="" onblur=""/>                
           		</td>
           		<td>
           		<span id="mobilePhoneTip"></span> 
           		</td>
           		</tr>
           		
           		<tr>
           		<td>          
                <span class="text_details">Email：<font class="ft-cl-r">*</font></span>
                </td>
                <td>
                <input type="text" id="email" name="email" value="" maxlength="32" onblur=""/>
            	</td>
            	<td>
            	<span id="emailTip"></span>
            	</td>
            	</tr>
            	
            	<tr>
            	<td>
            	<span class="text_details">传真：<font class="ft-cl-r">*</font></span>
                </td>
                <td>
                <input type="text" id="fax" name="fax" maxlength="20" value="" onblur=""/> 
                </td> 
                <td>               
                <span id="faxTip"></span>
                </td>
                </tr>
                 
                <tr>
                <td>                         
                <span class="text_details">地址：</span>
                </td>
                <td>
                <input type="text" id=""address" name="address" style="width:300px" maxlength="60" value="" onblur=""/>								
           		</td>
           		<td>
           		</td>
           		</tr>
           		</table>
            </div>
                       		
        </div>
    	<div class="div-pl-bt" style="margin-left:200px;margin-top:40px;">    
	    	<input type="submit" value="保存" class="btn-add-normal-4ft" onClick=""/>
	    	<input type="button" value="取消" class="btn-add-normal-4ft" onClick="closewindow();"/>        
    	</div>    
	</div>
	</form>
</div>
</body>
</html>
