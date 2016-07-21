<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>

<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/jquery-1.3.2.min.js"></script>
<script type="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/js.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/order.js"></script>
<title>优购商城--商家后台</title>
<!-- 日期控件 -->
<script src="${BasePath}/js/common/form/datepicker/WdatePicker.js" type="text/javascript"></script>
</head>
<script type="text/javascript">

</script>
<body>
<div class="container">
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr">
				  <span><a href="#" class="btn-onselc">修改商家信息</a></span>
				</li>
				
			</ul>
		</div>
 <div class="modify"> 
     <form action="${BasePath}/yitiansystem/merchants/businessorder/update_merchants.sc" name="queryForm" id="queryForm" method="post">
         <input type="hidden" name="balanceTraderName" id="balanceTraderName"  value="<#if supplierSp??&&supplierSp.balanceTraderName??>${supplierSp.balanceTraderName!''}</#if>"> 
        <input type="hidden" name="setOfBooksName" id="setOfBooksName" value="<#if supplierSp??&&supplierSp.setOfBooksName??>${supplierSp.setOfBooksName!''}</#if>">
               <div style="margin-top:30px;">
                 <div  style="width:1145px;height:30px;background:#CCFFFF;"><span><b>商家信息</b></span></div>
                 <hr>
                 <table cellpadding="0" cellspacing="0" class="list_table">
                        <tr><td>
                         <input type="hidden" name="id" id="id" value="<#if supplierSp??&&supplierSp.id??>${supplierSp.id!''}</#if>">
                          <input type="hidden" name="supplierCode" id="supplierCode" value="<#if supplierSp??&&supplierSp.supplierCode??>${supplierSp.supplierCode!''}</#if>">
                          <label><span style="color:red;margin-left:54px;">&nbsp;*</span>登录账号：</label>
                          <#if merchantUser??&&merchantUser.loginName??>${merchantUser.loginName!''}</#if>
                        </td></tr>
                </table>
               </div>
               
              <div style="margin-top:20px;">
                 <div  style="width:1145px;height:30px;background:#CCFFFF;"><span><b>品类权限</b></span></div>
                 <hr>
                 <table cellpadding="0" cellspacing="0" class="list_table">
                        <tr><td>
                         <label><span style="color:red;margin-left:54px;">&nbsp;*</span>商品品牌：</label>
                          <input type="hidden" name="bankNameHidden" id="bankNameHidden" value="<#if brandValue??>${brandValue!''}</#if>" />
                           <span name="bankName" id="bankName"><#if brandList??>${brandList!''}</#if></span>
                          <img src="${BasePath}/images/finance/bt_del.gif" onclick="deleteBrand();"/>
                          <img src="${BasePath}/images/finance/adduser.gif" onclick="addBrand();"/>
                          &nbsp;&nbsp;<span style="color:red;" id="bankNameError"></span>
	                </td></tr>
	                
	                <tr><td>
                          <label><span style="color:red;margin-left:54px;">&nbsp;*</span>商品分类：</label>
                           <input type="hidden" name="catNameHidden" id="catNameHidden"  value="<#if catValue??>${catValue!''}</#if>"/>
                          <span name="catName" id="catName"><#if catList??>${catList!''}</#if></span>
                          <img src="${BasePath}/images/finance/bt_del.gif" onclick="deleteCat();"/>
                          <img src="${BasePath}/images/finance/adduser.gif" onclick="addCat();"/>
                          &nbsp;&nbsp;<span style="color:red;" id="catNameError"></span>
                    </td></tr>
                </table>
               </div>
               
             <div style="margin-top:20px;">
                <div><div style="width:1145px;height:30px;background:#CCFFFF;"><span><b>公司信息</b></span></div>
                 <hr>
                 <table cellpadding="0" cellspacing="0" class="list_table">
                        <tr><td>
                          <label><span style="color:red;margin-left:54px;">&nbsp;*</span>商家名称：</label>
                          <input type="text" name="supplier" id="supplier"  value="<#if supplierSp??&&supplierSp.supplier??>${supplierSp.supplier!''}</#if>"/>
                           &nbsp;&nbsp;<span style="color:red;" id="supplierError"></span>
                        </td>
                        </tr>
                        <tr><td>
                          <label><span style="margin-left:54px;">营业执照号：</span></label>
                           <input type="text" name="businessLicense" id="businessLicense" value="<#if supplierSp??&&supplierSp.businessLicense??>${supplierSp.businessLicense!''}</#if>"/>
                       </td>
                       </tr>
                        <tr><td>
                          <label><span style="margin-left:30px;">营业执照所在地：</span</label>
                           <input type="text" name="businessLocal" id="businessLocal" value="<#if supplierSp??&&supplierSp.businessLocal??>${supplierSp.businessLocal!''}</#if>"/>
                       </td>
                       </tr>
                        <tr><td>
                          <label><span style="margin-left:30px;">营业执照有效期：</span</label>
                           <input type="text" name="businessValidity" id="businessValidity" value="<#if supplierSp??&&supplierSp.businessValidity??>${supplierSp.businessValidity!''}</#if>" />
                       </td>
                       </tr>
                        <tr><td>
                          <label><span style="margin-left:44px;">税务登记证号：</span</label>
                           <input type="text" name="tallageNo" id="tallageNo"  value="<#if supplierSp??&&supplierSp.tallageNo??>${supplierSp.tallageNo!''}</#if>"/>
                       </td>
                       </tr>
                        <tr><td>
                          <label><span style="margin-left:44px;">组织机构代码：</span</label>
                           <input type="text" name="institutional" id="institutional" value="<#if supplierSp??&&supplierSp.institutional??>${supplierSp.institutional!''}</#if>" />
                       </td>
                       </tr>
                        <tr><td>
                          <label><span style="margin-left:44px;">纳税人识别号：</span</label>
                           <input type="text" name="taxpayer" id="taxpayer" value="<#if supplierSp??&&supplierSp.taxpayer??>${supplierSp.taxpayer!''}</#if>" />
                       </td>
                       </tr>
                </table>
               </div>
               
               
                    <div style="margin-top:20px;">
                <div><div style="width:1145px;height:30px;background:#CCFFFF;"><span><b>财务信息</b></span></div>
                 <hr>
                 <table cellpadding="0" cellspacing="0" class="list_table">
                        <tr>
                        <td>
                          <label><span style="color:red;margin-left:20px;">&nbsp;*</span>银行开户名：</label>
                          <input type="text" name="contact" id="contact"  value="<#if supplierSp??&&supplierSp.contact??>${supplierSp.contact!''}</#if>"/>
                           &nbsp;&nbsp;<span style="color:red;" id="creatorError"></span>
                        </td>
                        </tr>
                        <tr>
                        <td>
                          <label> <span style="color:red;margin-left:10px;">&nbsp;*</span>公司银行账号：</label>
                           <input type="text" name="account" id="account" value="<#if supplierSp??&&supplierSp.account??>${supplierSp.account!''}</#if>"/>
                        &nbsp;&nbsp;<span style="color:red;" id="bankError" name="bankError"></span>
                       </td>
                       </tr>
                        <tr>
                        <td>
                          <label> <span style="color:red;">&nbsp;*</span>开户行支行名称：</label>
                           <input type="text" name="subBank" id="subBank" value="<#if supplierSp??&&supplierSp.subBank??>${supplierSp.subBank!''}</#if>" />
                        &nbsp;&nbsp;<span style="color:red;" id="subBankError" name="subBankError"></span>
                       </td>
                       </tr>
                        <tr>
                        <td>
                          <label> <span style="color:red;">&nbsp;*</span>开户银行所在地：</label>
                           <input type="text" name="bankLocal" id="bankLocal"  value="<#if supplierSp??&&supplierSp.bankLocal??>${supplierSp.bankLocal!''}</#if>"/>
                        &nbsp;&nbsp;<span style="color:red;" id="bankLocalError" name="bankLocalError"></span>
                       </td>
                       </tr>
                        <tr>
                       <td>
                          <label> <span style="color:red;">&nbsp;*</span>优惠券分摊比例：</label>
                           <input type="text" name="couponsAllocationProportion" id="couponsAllocationProportion" value="<#if supplierSp??&&supplierSp.couponsAllocationProportion??>${supplierSp.couponsAllocationProportion!''}</#if>" />%（0-100）
                        &nbsp;&nbsp;<span style="color:red;" id="couponsAllocationProportionError"></span>
                       </td>
                       </tr>
                        <tr>
                       <td>
                          <label> <span style="color:red;margin-left:60px;">&nbsp;*</span>税率：</label>
                           <input type="text" name="taxRate" id="taxRate" value="<#if supplierSp??&&supplierSp.taxRate??>${supplierSp.taxRate!''}</#if>" />%（0-100）
                        &nbsp;&nbsp;<span style="color:red;" id="taxRateError"></span>
                       </td>
                       </tr>
                         <tr>
                        <td>
                          <label> <span style="color:red;margin-left:35px;">&nbsp;*</span>仓库类型：</label>
                           <input type="radio" name="isInputYougouWarehouse" id="isInputYougouWarehouse" value="1" <#if supplierSp??&&supplierSp.isInputYougouWarehouse??&&supplierSp.isInputYougouWarehouse==1>checked</#if> />入优购仓库
                           <input type="radio" name="isInputYougouWarehouse" id="isInputYougouWarehouse"  value="0" <#if supplierSp??&&supplierSp.isInputYougouWarehouse??&&supplierSp.isInputYougouWarehouse==0>checked</#if>/>不入优购仓库
                       </td>
                       </tr>
                       
                        <tr>
                       <td>
                          <label> <span style="color:red;margin-left:10px;">&nbsp;*</span>成本帐套名称：</label>
                           <select id="setOfBooksCode" name="setOfBooksCode">
                           <#if costSetofBooksList??>
                             <option value="">请选择成本帐套名称</option>
                             <#list costSetofBooksList as item>
                               <option value="${item.setOfBooksCode!''}" <#if supplierSp??&&supplierSp.setOfBooksCode??&&supplierSp.setOfBooksCode==item.setOfBooksCode>selected</#if>>${item.setOfBooksName!''}</option>
                             </#list>
                           </#if>
                           </select>
                            &nbsp;&nbsp;<span style="color:red;" id="setOfBooksCodeError"></span>
                       </td>
                       </tr>
                       <tr>
                       <td>
                          <label> <span style="color:red;margin-left:10px;">&nbsp;*</span>结算商家名称：</label>
                           <select id="balanceTraderCode" name="balanceTraderCode">
                           <#if traderMaintainList??>
                            <option value="">请选择结算商家名称</option>
                             <#list traderMaintainList as item>
                                <option value="${item['balance_trader_code']!''}" <#if supplierSp??&&supplierSp.balanceTraderCode??&&supplierSp.balanceTraderCode==item['balance_trader_code']>selected</#if>>${item['balance_trader_name']!''}</option>
                             </#list>
                           </#if>
                           </select>
                            &nbsp;&nbsp;<span style="color:red;" id="balanceTraderCodeError"></span>
                       </td>
                       </tr>
                </table>
               </div>
               <div style="margin-top:20px;margin-left:420px;">
                 <input type="button" value="保存" onclick="return updateMerchants();"  class="yt-seach-btn">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <input type="button" value="重置" onclick="resetInput();" class="yt-seach-btn">
               </div>
       	</form>
    </div>
 <div class="blank20"></div>
</div>
</body>
</html>
<script type="text/javascript">
//删除品牌信息
function deleteBrand(){
  $("#bankName").html("");
  $('#bankNameHidden').val('');
  deleteCat();
}
//删除分类信息
function deleteCat(){
  $("#catName").html("");
  $('#catNameHidden').val('');
}
//跳转到添加品牌权限设置
function addBrand(){
    openwindow('${BasePath}/yitiansystem/merchants/businessorder/to_addBank.sc',900,600,'添加品牌');
}

//添加分类权限设置
function addCat(){
	var selectedBrandInfos = $.trim($('#bankNameHidden').val());
	if (selectedBrandInfos.length <= 0) {
		alert('请先添加品牌!');
		return false;
	}
	
	var brandNoIndex = 1;
	var selectedBrandNos = [];
	$.each(selectedBrandInfos.split(';'), function(){
		selectedBrandNos[selectedBrandNos.length] = this.split('_')[brandNoIndex];
	});
	
    openwindow('${BasePath}/yitiansystem/merchants/businessorder/to_addCat.sc?brandNos=' + selectedBrandNos, 500, 200, '添加分类');
}
//限制只能输入1-100数字
$("input[name='couponsAllocationProportion']").keydown(function(event){
	var code = event.which;
	if(code==10){
	    return true;
	}else if(code >47 && code < 58) {
		return true;
	}else if(code >95 && code < 106){
		return true;
	}else{
	   return false;
	}
	return true;
});
$("input[name='taxRate']").keydown(function(event){
	var code = event.which;
	if(code==10){
	    return true;
	}else if(code >47 && code < 58) {
		return true;
	}else if(code >95 && code < 106){
		return true;
	}else{
	   return false;
	}
	return true;
});
function updateMerchants(){
//保存商家信息
	var bankName = $("#bankName").html();//品牌名称
	var catName = $("#catName").html();//分类名称
	var supplier = $("#supplier").val();//商家名称
	var contact = $("#contact").val();//银行开户名
	var bank = $("#account").val();	//公司银行账号
	var subBank = $("#subBank").val();//开户行支行名称
	var bankLocal = $("#bankLocal").val();//开户银行所在地
	var couponsAllocationProportion = $("#couponsAllocationProportion").val();//优惠券分摊比例
	var taxRate = $("#taxRate").val();//税率
	var setOfBooksCode= $("#setOfBooksCode").val();//成本帐套编号
	var balanceTraderCode= $("#balanceTraderCode").val();//结算商家编码
	if(bankName=="" ){
		$("#bankNameError").html("商品品牌不能为空!");
		return false;
	}else{
	   $("#bankNameError").html("");
	}
	if(catName=="" ){
		$("#catNameError").html("商品分类不能为空!");
		return false;
	}else{
	   $("#catNameError").html("");
	}
	if(supplier=="" ){
		$("#supplierError").html("商家名称不能为空!");
		return false;
	}else{
	   $("#supplierError").html("");
	}
	if(contact=="" ){
		$("#creatorError").html("银行开户名不能为空!");
		return false;
	}else{
	   $("#creatorError").html("");
	}
	if(bank=="" ){
		$("#bankError").html("公司银行名称不能为空!");
		return false;
	}else{
	   $("#bankError").html("");
	}
	if(subBank=="" ){
		$("#subBankError").html("开户行支行名称不能为空!");
		return false;
	}else{
	   $("#subBankError").html("");
	}
	if(bankLocal=="" ){
		$("#bankLocalError").html("开户银行所在地不能为空!");
		return false;
	}else{
	   $("#bankLocalError").html("");
	}
	if(couponsAllocationProportion=="" ){
		$("#couponsAllocationProportionError").html("优惠券分摊比例不能为空!");
		return false;
	}else{
	   $("#couponsAllocationProportionError").html("");
	}
	if(taxRate=="" ){
		$("#taxRateError").html("税率不能为空!");
		return false;
	}else{
	   $("#taxRateError").html("");
	}
	if(setOfBooksCode=="" ){
		$("#setOfBooksCodeError").html("成本帐套名称不能为空!");
		$("#setOfBooksCode").focus();
		return false;
	}else{
	   $("#setOfBooksCodeError").html("");
	}
	if(balanceTraderCode=="" ){
		$("#balanceTraderCodeError").html("结算商家名称为空!");
		return false;
	}else{
	   $("#balanceTraderCodeError").html("");
	}
	var setOfBooksName=$("#setOfBooksCode").find("option:selected").text();//成本帐套名称
	$("#setOfBooksName").val(setOfBooksName);
	var balanceTraderName=$("#balanceTraderCode").find("option:selected").text();//结算商家名称
	$("#balanceTraderName").val(balanceTraderName);
	 var isYougou=$("input[type='radio']:checked").val();
	 $("#isInputYougouWarehouse").val(isYougou);
   //提交表单数据
   document.queryForm.submit();
			
}
//重置文本框信息
function resetInput(){
  $(":input[type=text]").val("");
  $(":input[type=password]").val("");
}

</script>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
