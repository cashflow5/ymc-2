<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>

<title>优购商城--商家后台</title>
</head>
<script type="text/javascript">

</script>
<body>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script> 
<div class="container">
<!--工具栏start--> 
	<div class="toolbar">
		<div class="t-content">
			<div class="btn" onclick="goBack();">
				<span class="btn_l"></span>
	        	<b class="ico_btn add"></b>
	        	<span class="btn_txt">返回</span>
	        	<span class="btn_r"></span>
        	</div> 
		</div>
	</div>
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr">
				  <span><a href="#" class="btn-onselc">添加商家</a></span>
				</li>
				
			</ul>
		</div>
 <div class="modify"> 
     <form action="${BasePath}/yitiansystem/merchants/businessorder/add_merchants.sc" name="queryForm" id="queryForm" method="post"> 
              <input type="hidden" name="balanceTraderName" id="balanceTraderName">
               <input type="hidden" name="setOfBooksName" id="setOfBooksName">
               <div style="margin-top:30px;">
                 <div  style="width:1055px;height:30px;background:#CCFFFF;"><span><b>商家信息</b></span></div>
                 <hr>
                 <table cellpadding="0" cellspacing="0" class="list_table">
                        <tr><td>
                          <label><span style="color:red;margin-left:54px;">&nbsp;*</span>登录账号：</label>
                          <input type="text" name="loginAccount" id="loginAccount"  />
                           &nbsp;&nbsp;<span style="color:red;" id="loginAccountError"></span>
                        </td></tr>
                        <tr><td>
                          <label> <span style="color:red;margin-left:50px;">&nbsp;*</span> 登录密码：</label>
                           <input type="password" name="loginPassword" id="loginPassword"  />
                        &nbsp;&nbsp;<span style="color:red;" id="loginPasswordError"></span>
                       </td></tr>
                        <tr><td>
                          <label> <span style="color:red;margin-left:50px;">&nbsp;*</span> 确认密码：</label>
                           <input type="password" name="passwordTwo" id="passwordTwo"  />
                        &nbsp;&nbsp;<span style="color:red;" id="passwordTwoError"></span>
                       </td></tr>
                </table>
               </div>
               <div style="margin-top:20px;">
                 <div  style="width:1055px;height:30px;background:#CCFFFF;"><span><b>品类权限</b></span></div>
                 <hr>
                 <table cellpadding="0" cellspacing="0" class="list_table">
                        <tr><td>
                         <label><span style="color:red;margin-left:54px;">&nbsp;*</span>商品品牌：</label>
                          <input type="hidden" name="bankNameHidden" id="bankNameHidden"  />
                          <span name="bankName" id="bankName"></span>
                          <img src="${BasePath}/images/finance/bt_del.gif" onclick="deleteBrand();"/>
                          <img src="${BasePath}/images/finance/adduser.gif" onclick="addBrand();"/>
                          &nbsp;&nbsp;<span style="color:red;" id="bankNameError"></span>
	                </td></tr>
	                
	                <tr><td>
                          <label><span style="color:red;margin-left:54px;">&nbsp;*</span>商品分类：</label>
                           <input type="hidden" name="catNameHidden" id="catNameHidden" />
                            <span name="catName" id="catName"></span>
                          <img src="${BasePath}/images/finance/bt_del.gif" onclick="deleteCat();"/>
                          <img src="${BasePath}/images/finance/adduser.gif" onclick="addCat();"/>
                          &nbsp;&nbsp;<span style="color:red;" id="catNameError"></span>
                    </td></tr>
                </table>
               </div>
               <div style="margin-top:20px;">
                <div><div style="width:1055px;height:30px;background:#CCFFFF;"><span><b>公司信息</b></span></div>
                 <hr>
                 <table cellpadding="0" cellspacing="0" class="list_table">
                        <tr><td>
                          <label><span style="color:red;margin-left:54px;">&nbsp;*</span>商家名称：</label>
                          <input type="text" name="supplier" id="supplier"  />
                           &nbsp;&nbsp;<span style="color:red;" id="supplierError"></span>
                        </td><td>&nbsp;</td>
                        </tr>
                        <tr><td>
                          <label><span style="margin-left:54px;">营业执照号：</span></label>
                           <input type="text" name="businessLicense" id="businessLicense" />
                       </td><td>&nbsp;</td>
                       </tr>
                        <tr><td>
                          <label><span style="margin-left:30px;">营业执照所在地：</span</label>
                           <input type="text" name="businessLocal" id="businessLocal" />
                       </td><td>&nbsp;</td>
                       </tr>
                        <tr><td>
                          <label><span style="margin-left:30px;">营业执照有效期：</span</label>
                           <input type="text" name="businessValidity" id="businessValidity"  />
                       </td><td>&nbsp;</td>
                       </tr>
                        <tr><td>
                          <label><span style="margin-left:44px;">税务登记证号：</span</label>
                           <input type="text" name="tallageNo" id="tallageNo"  />
                       </td><td>&nbsp;</td>
                       </tr>
                        <tr><td>
                          <label><span style="margin-left:44px;">组织机构代码：</span</label>
                           <input type="text" name="institutional" id="institutional"  />
                       </td><td>&nbsp;</td>
                       </tr>
                        <tr><td>
                          <label><span style="margin-left:44px;">纳税人识别号：</span</label>
                           <input type="text" name="taxpayer" id="taxpayer"  />
                       </td><td>&nbsp;</td></tr>
                </table>
               </div>
                <div style="margin-top:20px;">
                <div><div style="width:1055px;height:30px;background:#CCFFFF;"><span><b>财务信息</b></span></div>
                 <hr>
                 <table cellpadding="0" cellspacing="0" class="list_table">
                        <tr>
                        <td>
                          <label><span style="color:red;margin-left:40px;">&nbsp;*</span>银行开户名：</label>
                          <input type="text" name="contact" id="contact"  />
                           &nbsp;&nbsp;<span style="color:red;" id="creatorError"></span>
                        </td>
                        </tr>
                        <tr>
                       <td>
                          <label> <span style="color:red;margin-left:28px;">&nbsp;*</span>公司银行帐号：</label>
                           <input type="text" name="account" id="account"  />
                        &nbsp;&nbsp;<span style="color:red;" id="bankError" name="bankError"></span>
                       </td>
                       </tr>
                        <tr>
                       <td>
                          <label> <span style="color:red;margin-left:17px;">&nbsp;*</span>开户行支行名称：</label>
                           <input type="text" name="subBank" id="subBank"  />
                        &nbsp;&nbsp;<span style="color:red;" id="subBankError" name="subBankError"></span>
                       </td>
                       </tr>
                        <tr>
                       <td>
                          <label> <span style="color:red;margin-left:17px;">&nbsp;*</span>开户银行所在地：</label>
                           <input type="text" name="bankLocal" id="bankLocal"  />
                        &nbsp;&nbsp;<span style="color:red;" id="bankLocalError" name="bankLocalError"></span>
                       </td>
                       </tr>
                        <tr>
                       <td>
                          <label> <span style="color:red;margin-left:17px;">&nbsp;*</span>优惠券分摊比例：</label>
                           <input type="text" maxlength="3" name="couponsAllocationProportion" id="couponsAllocationProportion"  />%（0-100）
                        &nbsp;&nbsp;<span style="color:red;" id="couponsAllocationProportionError"></span>
                       </td>
                       </tr>
                        <tr>
                       <td>
                          <label> <span style="color:red;margin-left:75px;">&nbsp;*</span>税率：</label>
                           <input type="text" maxlength="3" name="taxRate" id="taxRate"  />%（0-100）
                        &nbsp;&nbsp;<span style="color:red;" id="taxRateError"></span>
                       </td>
                       </tr>
                         <tr>
                       <td>
                          <label> <span style="color:red;margin-left:50px;">&nbsp;*</span>仓库类型：</label>
                           <input type="radio" name="isInputYougouWarehouse" id="isInputYougouWarehouse" value="1"/>入优购仓库
                           <input type="radio" name="isInputYougouWarehouse" id="isInputYougouWarehouse"  value="0" checked />不入优购仓库
                       </td>
                       </tr>
                        <tr>
                       <td>
                          <label> <span style="color:red;margin-left:25px;">&nbsp;*</span>成本帐套名称：</label>
                           <select id="setOfBooksCode" name="setOfBooksCode">
                           <#if costSetofBooksList??>
                            <option value="">请选择成本帐套名称</option>
                             <#list costSetofBooksList as item>
                               <option value="${item.setOfBooksCode!''}">${item.setOfBooksName!''}</option>
                             </#list>
                           </#if>
                           </select>
                            &nbsp;&nbsp;<span style="color:red;" id="setOfBooksCodeError"></span>
                       </td>
                       </tr>
                        <tr>
                       <td>
                          <label> <span style="color:red;margin-left:25px;">&nbsp;*</span>结算商家名称：</label>
                           <select id="balanceTraderCode" name="balanceTraderCode">
                           <#if traderMaintainList??>
                            <option value="">请选择结算商家名称</option>
                             <#list traderMaintainList as item>
                                                              <option value="${item['balance_trader_code']!''}">${item['balance_trader_name']!''}</option>
                             </#list>
                           </#if>
                           </select>
                            &nbsp;&nbsp;<span style="color:red;" id="balanceTraderCodeError"></span>
                       </td>
                       </tr>
                </table>
               </div>
               <div style="margin-top:20px;margin-left:420px;">
                 <input type="button" value="保存" onclick="return addMerchants();"  class="yt-seach-btn">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
    openwindow('${BasePath}/yitiansystem/merchants/businessorder/to_addBank.sc',1100,600,'添加品牌');
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
	if(code==10||code==8){
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
	if(code==10||code==8){
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
//公司银行帐号只能为数字
$("input[name='bank']").keydown(function(event){
	var code = event.which;
	if(code==10||code==8){
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
//保存商家信息
function addMerchants(){
	var loginAccount = $("#loginAccount").val();//登录账号
	var  loginPassword= $("#loginPassword").val();//登录密码
	var passwordTwo = $("#passwordTwo").val();//确认密码
	var bankName = $("#bankName").html();//品牌名称
	var catName = $("#catName").html();//分类名称
	var supplier = $("#supplier").val();//商家名称
	var contact = $("#contact").val();//银行开户名
	var bank = $("#account").val();	//公司银行帐号
	var subBank = $("#subBank").val();//开户行支行名称
	var bankLocal = $("#bankLocal").val();//开户银行所在地
	var couponsAllocationProportion = $("#couponsAllocationProportion").val();//优惠券分摊比例
	var taxRate = $("#taxRate").val();//税率
	var setOfBooksCode= $("#setOfBooksCode").val();//成本帐套编号
	var balanceTraderCode= $("#balanceTraderCode").val();//结算商家编码
	if(loginAccount=="" ){
		$("#loginAccountError").html("登录账号不能为空!");
		$("#loginAccount").focus();
		return false;
	}else{
	   $("#loginAccountError").html("");
	}
	if(loginPassword=="" ){
		$("#loginPasswordError").html("登录密码不能为空!");
		$("#loginPassword").focus();
		return false;
	}else{
	   $("#loginPasswordError").html("");
	}
     //判断密码长度必须是在6-30之间
     var length = loginPassword.length;
     if(length >= 6 && length <= 30){
        $("#loginPassword").html("");
     }else{
     	$("#loginPassword").val("");
     	$("#loginPasswordTwo").val("");
        $("#loginPasswordError").html("密码长度必须大于等于6和小于等于30!");
        return false;
     }
     //判断原秘密是否输入正确
     var reg=new RegExp(/[A-Za-z].*[0-9]|[0-9].*[A-Za-z]/);
     if(!reg.test(loginPassword)){
     	$("#loginPassword").val("");
     	$("#loginPasswordTwo").val("");
        $("#loginPasswordError").html("密码必须是数字和字符的组合!");
        return false;
     }else{
        $("#loginPassword").html("");
     }
	if(passwordTwo=="" ){
		$("#passwordTwoError").html("确认密码不能为空!");
		$("#passwordTwo").focus();
		return false;
	}else{
	   $("#passwordTwoError").html("");
	}
	if(loginPassword!=passwordTwo ){
		$("#passwordTwoError").html("登录密码和确认密码不一致!");
		$("#loginPassword").focus();
		return false;
	}else{
	   $("#passwordTwoError").html("");
	}
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
		$("#supplier").focus();
		return false;
	}else{
	   $("#supplierError").html("");
	}
	if(contact=="" ){
		$("#creatorError").html("银行开户名不能为空!");
		$("#contact").focus();
		return false;
	}else{
	   $("#creatorError").html("");
	}
	if(bank=="" ){
		$("#bankError").html("公司银行帐号不能为空!");
		$("#bank").focus();
		return false;
	}else{
	   $("#bankError").html("");
	}
	if(subBank=="" ){
		$("#subBankError").html("开户行支行名称不能为空!");
		$("#subBank").focus();
		return false;
	}else{
	   $("#subBankError").html("");
	}
	if(bankLocal=="" ){
		$("#bankLocalError").html("开户银行所在地不能为空!");
		$("#bankLocal").focus();
		return false;
	}else{
	   $("#bankLocalError").html("");
	}
	if(couponsAllocationProportion=="" ){
		$("#couponsAllocationProportionError").html("优惠券分摊比例不能为空!");
		$("#couponsAllocationProportion").focus();
		return false;
	}else{
	   $("#couponsAllocationProportionError").html("");
	}
	if(taxRate=="" ){
		$("#taxRateError").html("税率不能为空!");
		$("#taxRate").focus();
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
	if(parseInt(couponsAllocationProportion)>100 ){
		$("#couponsAllocationProportionError").html("优惠券分摊比例只能输入1-100之内的整数!");
		$("#couponsAllocationProportion").focus();
		return false;
	}else{
	   $("#couponsAllocationProportionError").html("");
	}
	if(parseInt(taxRate)>100){
		$("#taxRateError").html("税率比例只能输入1-100之内的整数!");
		$("#taxRate").focus();
		return false;
	}else{
	   $("#taxRateError").html("");
	}
	if(supplier!=contact){
	    $("#creatorError").html("商家名称和银行开户名必须一致!");
	    $("#supplier").focus();
		return false;
	}else{
	   $("#creatorError").html("");
	}
	var balanceTraderName=$("#balanceTraderCode").find("option:selected").text();//结算商家名称
	$("#balanceTraderName").val(balanceTraderName);
	var setOfBooksName=$("#setOfBooksCode").find("option:selected").text();//成本帐套名称
	$("#setOfBooksName").val(setOfBooksName);
	var loginName=encodeURIComponent(loginAccount);
	//判断用户名是否存在
	$.ajax({ 
		type: "post", 
		url: "${BasePath}/yitiansystem/merchants/businessorder/exitsLoginAccount.sc?loginAccount=" + loginName, 
		success: function(dt){
			if("sucuess"==dt){
			   $("#loginAccountError").html("登录帐号已经存在,请重新输入!");
			   $("#loginAccount").focus();
			   return false;
			}else{
			    var supName=encodeURIComponent(supplier);
				  //判断商家名称是否已经存在
				  $.ajax({ 
					type: "post", 
					url: "${BasePath}/yitiansystem/merchants/businessorder/existMerchantSupplieName.sc?supplieName=" + supName, 
					success: function(dt){
						if("success1"==dt){
						   $("#supplierError").html("商家名称已经存在,请重新输入!");
						   $("#supplier").focus();
						   return false;
						}else if("success2"==dt){
						   $("#supplierError").html("存在普通供应商的商家名称,请重新输入!");
						   $("#supplier").focus();
						   return false;
						}else if("success3"==dt){
						   $("#supplierError").html("已存在同名的韩货供应商,请使用其它名称");
						   $("#supplier").focus();
						   return false;
						}else{
						   //提交表单数据
						   var isYougou=$("input[type='radio']:checked").val();
						   $("#isInputYougouWarehouse").val(isYougou);
						   document.queryForm.submit();
						}
					} 
				});
			}
		} 
	});

}
//充值文本框信息
function resetInput(){
  $(":input[type=text]").val("");
  $(":input[type=password]").val("");
}
//返回到商品类别页面
function goBack(){
  location.href="${BasePath}/yitiansystem/merchants/businessorder/to_merchantsList.sc";
}

</script>

