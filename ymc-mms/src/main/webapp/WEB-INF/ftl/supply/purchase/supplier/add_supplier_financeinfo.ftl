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
var validateFlag=true;
var config = {
	form : "submitForm",
	submit : submitForms,
	fields : [{
		name : 'bank',
		allownull : false,
		regExp : /^\S+$/,
		defaultMsg : '请输入开户行',
		focusMsg : '请输入开户行',
		errorMsg : '请输入字符',
		rightMsg : '开户行输入正确',
		msgTip : 'bankTip'
	},{
		name : 'subBank',
		allownull : false,
		regExp : /^\S+$/,
		defaultMsg : '请输入支行名称',
		focusMsg : '请输入支行名称',
		errorMsg : '请输入字符',
		rightMsg : '支行名称输入正确',
		msgTip : 'subBankTip'
	},{
		name : 'contact',
		allownull : false,
		regExp : /^\S+$/,
		defaultMsg : '请输入开户人',
		focusMsg : '请输入开户人',
		errorMsg : '请输入字符',
		rightMsg : '开户人输入正确',
		msgTip : 'creatorTip'
	},{
		name : 'account',
		allownull : false,
		regExp : "integezero",
		defaultMsg : '请输入账号',
		focusMsg : '请输入账号',
		errorMsg : '请输入数字',
		rightMsg : '账号输入正确',
		msgTip : 'accountTip'
	},{
		name : 'dutyCode',
		allownull : false,
		regExp : /^\S+$/,
		defaultMsg : '请输入税号',
		focusMsg : '请输入税号',
		errorMsg : '请输入数字',
		rightMsg : '税号输入正确',
		msgTip : 'dutyCodeTip'
	},{
		name : 'conTime',
		allownull : false,
		regExp : "intege1",
		defaultMsg : '请输入合作期限',
		focusMsg : '请输入合作期限',
		errorMsg : '请输入非负数',
		rightMsg : '合作期限输入正确',
		msgTip : 'conTimeTip'
	}]
}

Tool.onReady( function() {
	var f = new Fw(config);
	f.register();
});

/**
 * 提交表单
 */
function submitForms() {
	result=true;
	if(result){		
		return result;
	}
	return false;

}
</script>
</head>
<body>
 <form action="editSupplierFinance.sc" method="post" id="submitForm" name="submitForm">
<div class="container"> 
	<!--工具栏start-->
	<div class="toolbar">
		<div class="t-content"> <!--操作按钮start--> 
		</div>
	</div>
	<!--工具栏end-->
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li>
				  <#if supplierId??>
				     	<span><a href="${BasePath}/yitiansystem/merchants/businessorder/to_updateMerchants.sc?id=${supplierId?default("")}&flag=1" class="btn-onselc">基本信息</a></span>
				  <#else>
				   	   <span><a href="${BasePath}/yitiansystem/merchants/businessorder/to_add_supplier.sc?id=${supplierId?default("")}&flag=1" class="btn-onselc">基本信息</a></span>
				  </#if>
				</li>
				<li  class='curr'>
				  <span>银行账号</span>
				</li>
				<li>
				  <span><a href="toAddContact.sc?supplierId=${supplierId?default("")}">联系人列表</a></span>
				</li>
			</ul>
		</div>
		<!--当前位置end--> 
		
		<!--主体start-->
		<div id="modify" class="modify">
 <input type="hidden" name="id" value="${supplierId?default("")}"/> 		
<input type="hidden" name="supplierCode" id="supplierCode" value="${supplier.supplierCode!''}"/>
       <table class="com_modi_table">
        	<tr>
        		<th><span class="star">*</span>开户行：      </th>
            	<td>
				<input type="text" name="bank" maxlength="32" value="<#if supplier??&&supplier.bank??>${supplier.bank?default("")}</#if>" onblur=""/>     
            	<span id="bankTip"></span>
            	</td>
            </tr>
            <tr>
            	<th>
            	<span class="star">*</span>支行名称：
               
                </th>
                <td>
										 
                <input type="text" name="subBank" maxlength="32"  value="<#if supplier??&&supplier.subBank??>${supplier.subBank?default("")}</#if>"/> 
                <span id="subBankTip"></span>
                </td>
            </tr> 
            <tr>
                <th>
                <span class="star">*</span>开户人：
               
                </th>
                <td>
				
                <input type="text" name="contact" maxlength="32"  value="<#if supplier??&&supplier.contact??>${supplier.contact?default("")}</#if>"/> 
                <span id="creatorTip"></span>
                </td> 
          </tr>                    
          <tr>
          	   <th>
            	<span class="star">*</span>账号：
                
            	</th>
            	<td>
				             
                <input type="text" name="account" maxlength="32"  value="<#if supplier??&&supplier.account??>${supplier.account?default("")}</#if>"/>
            	<span id="accountTip"></span>
               </td>
           </tr>             
           <tr>
               <th>
            	<span class="star">*</span>税号： 
            	
            	</th>
            	<td>
				
            	<input type="text" name="dutyCode" id="dutyCode" maxlength="32"  value="<#if supplier??&&supplier.dutyCode??>${supplier.dutyCode?default("")}</#if>" />
            	<span id="dutyCodeTip"></span> 
                </td>
            </tr>
            <tr>    
               <th>
                <span class="star">*</span>合作期限：
              
               </th>
               <td>
			   
                <input type="text" name="conTime" maxlength="11"  value="<#if supplier??>${supplier.conTime?default("")}</#if>" onblur=""/>个月
                <span id="conTimeTip"></span>
               </td>          	
           </tr>
           
           <tr>
		   <th></th>
           <td>
   			
           		<input type="submit" value="保存" class="btn-save" onClick=""/>
				<input type="button" value="取消" class="btn-back" onClick="javascript:window.location='supplierlist.sc'"/>   
        	</td>
        	
           </tr>
          </table>       	                
	</form>
</div>
</div>
</div>
</body>
</html>
