<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/js/yitiansystem/merchants/ztree/css/zTreeStyle/zTreeStyle.css"/>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script>
<script type="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/js.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/order.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/merchants/ztree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/merchants/ztree/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/merchants/cat_tree.js?lyx20151008"></script>

<title>优购商城--商家后台</title>
<!-- 日期控件 -->
<script src="${BasePath}/js/common/form/datepicker/WdatePicker.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
var basePath = "${BasePath}";

//返回到商品类别页面
function goBack(){
  closewindow();
}

$(document).ready(function(){
	//加载品牌显示框
	var brandStrs = $('#bankNameHidden').val();
	var brandNos = [];
	if (brandStrs != null || brandStrs != '' || brandStrs.length > 0) {
		var str = brandStrs.split('_');
		for(var i = 0; i < str.length; i++) {
			var array = str[i].split(';');
			showBrandframe(array[0], array[1]);
			brandNos[brandNos.length] = array[0];
		}
	}
	$('#bankNoHistory').val(brandNos.join(';'));
	$('#bankNoHidden').val(brandNos.join(';'));
	
	var zNodes = [];
	//加载分类Tree
	<#if treeModes??>
		<#list treeModes as item>
			var dispaly = false;
			var isEnabled = '${item.isEnabled!''}';
			if (isEnabled == 1) {
				dispaly = true;
			}
			zNodes[zNodes.length] = {
				id: '${item.structName!""}', 
				pId: '${item.parentId!''}', 
				name: '${item.catName!''}', 
				lev: '${item.catLeave!''}',
				checked: dispaly
			};
		</#list>
	</#if>
	
	$.fn.zTree.init($("#ztree"), setting, zNodes);
	if (zNodes.length > 0)
	$("#supplier_category_brand_tree_tr").show();
});
</script>
<body>
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
		</div>
 <div class="modify"> 
     <form action="${BasePath}/yitiansystem/merchants/businessorder/update_historyMerchants.sc" name="queryForm" id="queryForm" method="post">
        <input type="hidden" name="supplier" id="supplier" value="<#if supplierSp??&&supplierSp.supplier??>${supplierSp.supplier!''}</#if>" />
        <input type="hidden" name="supplierCode" id="supplierCode" value="<#if supplierSp??&&supplierSp.supplierCode??>${supplierSp.supplierCode!''}</#if>" />
        <input type="hidden" name="id" id="id" value="<#if supplierSp??&&supplierSp.id??>${supplierSp.id!''}</#if>" />
        <div style="margin-top:30px;">
			<div  style="width:1145px;height:30px;background:#CCFFFF;"><span><b>商家信息</b></span></div>
			<hr>
			<table cellpadding="0" cellspacing="0" class="list_table">
				<tr>
					<td>
						<label><span style="color:red;margin-left:54px;">&nbsp;*</span>登录账号：</label>
						<input type="text" name="loginAccount" id="loginAccount"  />
	                           &nbsp;&nbsp;<span style="color:red;" id="loginAccountError"></span>
					</td>
				</tr>
				<tr>
					<td>
						<label> <span style="color:red;margin-left:50px;">&nbsp;*</span> 登录密码：</label>
						<input type="password" name="loginPassword" id="loginPassword"  />
	                        &nbsp;&nbsp;<span style="color:red;" id="loginPasswordError"></span>
					</td>
				</tr>
				<tr>
					<td>
						<label> <span style="color:red;margin-left:50px;">&nbsp;*</span> 确认密码：</label>
						<input type="password" name="passwordTwo" id="passwordTwo"  />
	                        &nbsp;&nbsp;<span style="color:red;" id="passwordTwoError"></span>
					</td>
				</tr>
			</table>
		</div>
                
		<div style="margin-top:20px;">
			<div  style="width:1145px;height:30px;background:#CCFFFF;"><span><b>品类权限</b></span></div>
			<hr>
			<table cellpadding="0" cellspacing="0" class="list_table">
				<tr>
					<td>
						<input type="hidden" name="bankNoHistory" id="bankNoHistory" value="" />
                        <input type="hidden" name="bankNameHidden" id="bankNameHidden"  value="<#if brandStrs??>${brandStrs!''}</#if>"/>
                        <input type="hidden" name="bankNoHidden" id="bankNoHidden"  />
                        <input type="hidden" name="catNameHidden" id="catNameHidden" />
                        <span id="bank_span"></span>
                            <#-- img src="${BasePath}/images/finance/bt_del.gif" onclick="deleteBrand();"/-->
                        <img src="${BasePath}/images/finance/adduser.gif" onclick="addBrand();"/>
                            &nbsp;&nbsp;<span style="color:red;" id="bankNameError"></span>
	                </td>
				</tr>
	            <tr id="supplier_category_brand_tree_tr" style="display: none;">
	            	<td>
	                		<#-- 分类树结构展示 -->
	                	<div class="content_wrap"><ul id="ztree" class="ztree"></ul></div>
	                	<input type="hidden" id="supplier_cat_brand_tree_hidden" onclick="init_supplier_cat_tree();" />
	                </td>
				</tr>
			</table>
		</div>
               
        <div style="margin-top:20px;">
                <div><div style="width:1145px;height:30px;background:#CCFFFF;"><span><b>公司信息</b></span></div>
                 <hr>
                 <table cellpadding="0" cellspacing="0" class="list_table">
                        <tr><td>
                          <label><span style="color:red;margin-left:54px;">&nbsp;*</span>商家名称：</label>
                          <#if supplierSp??&&supplierSp.supplier??>${supplierSp.supplier!''}</#if>
                           &nbsp;&nbsp;<span style="color:red;" id="supplierError"></span>
                        </td>
                        </tr>
                        <tr><td>
                          <label><span style="margin-left:54px;">营业执照号：</span></label>
                          <#if supplierSp??&&supplierSp.businessLicense??>${supplierSp.businessLicense!''}</#if>
                       </td>
                       </tr>
                        <tr><td>
                          <label><span style="margin-left:30px;">营业执照所在地：</span</label>
                       <#if supplierSp??&&supplierSp.businessLocal??>${supplierSp.businessLocal!''}</#if>
                       </td>
                       </tr>
                        <tr><td>
                          <label><span style="margin-left:30px;">营业执照有效期：</span</label>
                           <#if supplierSp??&&supplierSp.businessValidity??>${supplierSp.businessValidity!''}</#if>
                       </td>
                       </tr>
                        <tr><td>
                          <label><span style="margin-left:44px;">税务登记证号：</span</label>
                         <#if supplierSp??&&supplierSp.tallageNo??>${supplierSp.tallageNo!''}</#if>
                       </td>
                       </tr>
                        <tr><td>
                          <label><span style="margin-left:44px;">组织机构代码：</span</label>
                           <#if supplierSp??&&supplierSp.institutional??>${supplierSp.institutional!''}</#if>
                       </td>
                       </tr>
                        <tr><td>
                          <label><span style="margin-left:44px;">纳税人识别号：</span</label>
                          <#if supplierSp??&&supplierSp.taxpayer??>${supplierSp.taxpayer!''}</#if>
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
                         <#if supplierSp??&&supplierSp.contact??>${supplierSp.contact!''}</#if>
                           &nbsp;&nbsp;<span style="color:red;" id="creatorError"></span>
                        </td>
                        </tr>
                        <tr>
                        <td>
                          <label> <span style="color:red;margin-left:10px;">&nbsp;*</span>公司银行账号：</label>
                           <#if supplierSp??&&supplierSp.bank??>${supplierSp.bank!''}</#if>
                        &nbsp;&nbsp;<span style="color:red;" id="bankError" name="bankError"></span>
                       </td>
                       </tr>
                        <tr>
                        <td>
                          <label> <span style="color:red;">&nbsp;*</span>开户行支行名称：</label>
                           <#if supplierSp??&&supplierSp.subBank??>${supplierSp.subBank!''}</#if>
                        &nbsp;&nbsp;<span style="color:red;" id="subBankError" name="subBankError"></span>
                       </td>
                       </tr>
                        <tr>
                        <td>
                          <label> <span style="color:red;">&nbsp;*</span>开户银行所在地：</label>
                           <#if supplierSp??&&supplierSp.bankLocal??>${supplierSp.bankLocal!''}</#if>
                        &nbsp;&nbsp;<span style="color:red;" id="bankLocalError" name="bankLocalError"></span>
                       </td>
                       </tr>
                        <tr>
                       <td>
                          <label> <span style="color:red;">&nbsp;*</span>优惠券分摊比例：</label>
                           <#if supplierSp??&&supplierSp.couponsAllocationProportion??>${supplierSp.couponsAllocationProportion!''}</#if> %（0-100）
                        &nbsp;&nbsp;<span style="color:red;" id="couponsAllocationProportionError"></span>
                       </td>
                       </tr>
                        <tr>
                       <td>
                          <label> <span style="color:red;margin-left:60px;">&nbsp;*</span>税率：</label>
                           <#if supplierSp??&&supplierSp.taxRate??>${supplierSp.taxRate!''}</#if>%（0-100）
                        &nbsp;&nbsp;<span style="color:red;" id="taxRateError"></span>
                       </td>
                       </tr>
                       <tr>
                        <td>
                          <label> <span style="color:red;margin-left:35px;">&nbsp;*</span>合作模式：</label>
                           	<#list statics['com.belle.other.model.pojo.SupplierSp$CooperationModel'].values()?sort_by('description')?reverse as item>
                           	<input type="radio" id="_radio_${item_index}" name="isInputYougouWarehouse" value="${item.ordinal()}" <#if supplierSp?? && supplierSp.isInputYougouWarehouse?default(-1) == item.ordinal()>checked="checked"</#if>/><label for="_radio_${item_index}">${item.description}</label>
                           	</#list>
                       </td>
                       </tr>
                        <tr>
                        <td>
                          <label> <span style="color:red;margin-left:10px;">&nbsp;*</span>成本帐套名称：</label>
                          <#if supplierSp??&&supplierSp.setOfBooksName??>${supplierSp.setOfBooksName!''}</#if>
                       </td>
                       </tr>
                        <tr>
                        <td>
                          <label> <span style="color:red;margin-left:10px;">&nbsp;*</span>结算商家名称：</label>
                          <#if supplierSp??&&supplierSp.balanceTraderName??>${supplierSp.balanceTraderName!''}</#if>
                       </td>
                       </tr>
                </table>
               </div>
               <div style="margin-top:20px;margin-left:420px;">
                  <input type="button" value="保存" onclick="return updatetMerchantsBankCat();"  class="yt-seach-btn">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <input type="button" value="返回" onclick="goBack();" class="yt-seach-btn">
               </div>
       	</form>
    </div>
    </div>
 <div class="blank20"></div>
</div>
</body>
</html>
<script type="text/javascript" >

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
function updatetMerchantsBankCat(){
var loginAccount = $("#loginAccount").val();//登录账号
	var  loginPassword= $("#loginPassword").val();//登录密码
	var passwordTwo = $("#passwordTwo").val();//确认密码
	var bankName = $("#bankName").html();//品牌名称
	var catName = $("#catName").html();//分类名称
	//品牌分类
	var bankNo = $("#bankNoHistory").val();//品牌BrandNo
	var catStrs = [];
	var treeObj = $.fn.zTree.getZTreeObj("ztree");
	if (treeObj != null) {
		var nodes = treeObj.getCheckedNodes();
		for (var i=0, l=nodes.length; i < l; i++) {
			var node = nodes[i];
			if (node.lev == 3) { //取第三级分类
				catStrs[catStrs.length] = node.id;
			}
		}
	}
	$("#catNameHidden").val(catStrs.join('_'));
	
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
	if(passwordTwo=="" ){
		$("#passwordTwoError").html("确认密码不能为空!");
		$("#passwordTwo").focus();
		return false;
	}else{
	   $("#passwordTwoError").html("");
	}
	if(loginPassword.length<6){
		$("#loginPasswordError").html("登录密码长度必须大于等于6位数!");
		$("#loginPassword").focus();
		return false;
	}else{
	   $("#loginPasswordError").html("");
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
	if(catStrs.length <= 0 ){
		$("#bankNameError").html("请选择分类!");
		return false;
	}else{
	   $("#bankNameError").html("");
	}

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
			   document.queryForm.submit();
			}
		} 
	});

}

</script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
