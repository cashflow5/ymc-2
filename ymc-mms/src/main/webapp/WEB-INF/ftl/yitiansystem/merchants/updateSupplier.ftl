<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,优购网,B网络营销系统,商品管理" />
<meta name="Description" content=" , ,B网络营销系统-商品管理" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/js/yitiansystem/merchants/ztree/css/zTreeStyle/zTreeStyle.css"/>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script> 
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/order.js"></script> 
<script type="text/javascript" src="${BasePath}/js/yitiansystem/merchants/ztree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/merchants/ztree/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/merchants/cat_tree.js?lyx20151008"></script>

<title>B网络营销系统-采购管理-优购网</title>
<script type="text/javascript">
var basePath = "${BasePath}";

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

//提交表单
function addSupplier() {
    var supplier=$("#supplier").val();//供应商名称
    var simpleName=$("#simpleName").val();//供应商简称
    var address=$("#address").val();//供应商地址
    var taxRate=$("#taxRate").val();//税率
    var supplierType=$("#supplierType").val();//供应商类型
    var setOfBooksCode=$("#setOfBooksCode").val();//成本帐套名称
    var balanceTraderCode=$("#balanceTraderCode").val();//结算商家名称
    // var bankName = $("#bankName").html();//品牌名称
    // var catName = $("#catName").html();//分类名称
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
	
	if(bankNo=="" ){
		$("#bankNameError").html("商品品牌不能为空!");
		return false;
	}else{
		$("#bankNameError").html("");
	}
	if(catStrs.length <= 0 ){
		$("#bankNameError").html("商品分类不能为空!");
		return false;
	}else{
		$("#bankNameError").html("");
	}
	if(supplier=="" ){
		$("#supplierError").html("供应商名称不能为空!");
		$("#supplier").focus();
		return false;
	}else{
	   $("#supplierError").html("");
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
	if(supplierType=="" ){
		$("#supplierTypeError").html("供应商类型不能为空!");
		$("#supplierType").focus();
		return false;
	}else{
	   $("#supplierTypeError").html("");
	}
	if(supplierType=="招商供应商"){
	  var couponsAllocationProportion = $("#couponsAllocationProportion").val();//优惠券分摊比例
	  if(couponsAllocationProportion=="" ){
		  $("#couponsAllocationProportionError").html("优惠券分摊比例不能为空!");
		  $("#couponsAllocationProportion").focus();
		  return false;
	  }else{
		   $("#couponsAllocationProportionError").html("");
	  }
	}
	
	var contact = $("#contact").val();//银行开户名
	var account = $("#account").val();	//公司银行帐号
	var subBank = $("#subBank").val();//开户行支行名称
	var bankLocal = $("#bankLocal").val();//开户银行所在地
	if(contact=="" ){
		$("#contactError").html("银行开户名不能为空!");
		$("#contact").focus();
		return false;
	}else{
		$("#contactError").html("");
	}
	if(account=="" ){
		$("#accountError").html("公司银行帐号不能为空!");
		$("#account").focus();
		return false;
	}else{
		$("#accountError").html("");
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

	var setOfBooksName=$("#setOfBooksCode").find("option:selected").text();//成本帐套名称
	$("#setOfBooksName").val(setOfBooksName);
	var isYougou=$('input:radio[name="isInputYougouWarehouse"]:checked').val();
	$("#isInputYougouWarehouse").val(isYougou);
	var shipmentType=$('input:radio[name="shipmentType"]:checked').val();
	$("#shipmentType").val(shipmentType);
    //提交表单数据
    document.submitForm.submit();
}

//跳转到添加品牌权限设置
function addBrand(){
    openwindow('${BasePath}/yitiansystem/merchants/businessorder/to_addBank.sc?flag=2',1100,600,'添加品牌');
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
	
    openwindow('${BasePath}/yitiansystem/merchants/businessorder/to_addCat.sc?flag=2&brandNos=' + selectedBrandNos, 500, 200, '添加分类');
}

//返回到商品列表页面
function goBack(){
     closewindow();
     location.href="${BasePath}/yitiansystem/merchants/businessorder/to_merchantsList.sc";
}

</script>
</head><body>


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
			   <li class='curr'> <span><a href="${BasePath}/yitiansystem/merchants/businessorder/to_updateMerchants.sc?id=<#if supplierSp??&&supplierSp.id??>${supplierSp.id!''}</#if>&flag=2">修改商家信息</a></span> </li>
			</ul>
		</div>
		<!--当前位置end--> 
<!--主体start-->
<div id="modify" class="modify">
<input type="hidden" id="basePath" value="${BasePath}">
	<form action="${BasePath}/yitiansystem/merchants/businessorder/update_merchants.sc" method="post" id="submitForm" name="submitForm">
		<input type="hidden" name="flag" id="flag" value="<#if flag??>${flag!''}</#if>">
		<input type="hidden" name="str" id="str" value="<#if str??>${str!''}</#if>">
		<input type="hidden" name="balanceTraderName" id="balanceTraderName"  value="<#if supplierSp??&&supplierSp.balanceTraderName??>${supplierSp.balanceTraderName!''}</#if>"> 
    	<input type="hidden" name="setOfBooksName" id="setOfBooksName" value="<#if supplierSp??&&supplierSp.setOfBooksName??>${supplierSp.setOfBooksName!''}</#if>">
		<input type="hidden" name="id" id="id" value="<#if supplierSp??&&supplierSp.id??>${supplierSp.id!''}</#if>">
		<input type="hidden" name="isValid" id="isValid" value="<#if supplierSp??&&supplierSp.isValid??>${supplierSp.isValid!''}</#if>">
		<input type="hidden" name="supplierCode" id="supplierCode" value="<#if supplierSp??&&supplierSp.supplierCode??>${supplierSp.supplierCode!''}</#if>">
		<input type="hidden" name="inventoryCode" id="inventoryCode" value="${supplierSp.inventoryCode!''}">
		<table class="com_modi_table">
			<tr>
				<th> <span class="star">*</span>登录账号：</th>
				<td> <#if merchantUser??&&merchantUser.loginName??>${merchantUser.loginName!''}</#if></td>
			</tr>
			<tr>
				<th><span class="star">*</span>品类授权：</th>
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
				<th></th>
				<td>
	                <#-- 分类树结构展示 -->
	                <div class="content_wrap"><ul id="ztree" class="ztree"></ul></div>
	                <input type="hidden" id="supplier_cat_brand_tree_hidden" onclick="init_supplier_cat_tree();" />
				</td>
			</tr>
			<tr>
				<th> <span class="star">*</span>供应商名称：</th>
				<td>
					<input type="text" name="supplier" id="supplier"  value="<#if supplierSp??&&supplierSp.supplier??>${supplierSp.supplier!''}</#if>"/>
					<span id="supplierError" style="color:red;"></span>
				</td>
			</tr>
			<tr>
				<th> <span class="star">*</span>供应商类型：</th>
				<td>
					<select id="supplierType" name="supplierType">
					<#if supplierSp.supplierType == "普通供应商">
						<option value="普通供应商" selected="selected">普通供应商</option>
						<option value="招商供应商">招商供应商</option>
					<#elseif supplierSp.supplierType == "招商供应商">
						<option value="普通供应商">普通供应商</option>
						<option value="招商供应商" selected="selected">招商供应商</option>
					<#else>
						<option value="">请选择供应商</option>
						<option value="普通供应商">普通供应商</option>
						<option value="招商供应商">招商供应商</option>
					</#if>
					</select>
					<span id="supplierTypeError"  style="color:red;"></span>
				</td>
			</tr>					
			<tr>
				<th> <span class="star">*</span>合作模式：</th>
				<td>
					<#list statics['com.belle.other.model.pojo.SupplierSp$CooperationModel'].values()?sort_by('description')?reverse as item>
					<input type="radio" id="_radio_${item_index}" name="isInputYougouWarehouse" value="${item.ordinal()}" <#if supplierSp?? && supplierSp.isInputYougouWarehouse?default(-1) == item.ordinal()>checked="checked"</#if>/><label for="_radio_${item_index}">${item.description}</label>
					</#list>
				</td>
			</tr>		
			<tr>
				<th> <span class="star">*</span>使用优购WMS：</th>
				<td>
					<input type="radio" name="isUseYougouWms" value="1"  <#if supplierSp?? && supplierSp.isUseYougouWms == 1 >checked</#if>  >是</label>
					<input type="radio" name="isUseYougouWms" value="0"  <#if supplierSp?? && supplierSp.isUseYougouWms == 0 >checked</#if> >否</label>
				</td>
			</tr>
			<tr>
				<th>成本帐套名称：</th>
				<td>
					<select id="setOfBooksCode" name="setOfBooksCode">
					<#if costSetofBooksList??>
						<option value="">请选择成本帐套名称</option>
						<#list costSetofBooksList as item>
						<option value="${item.setOfBooksCode!''}"  <#if supplierSp??&&supplierSp.setOfBooksCode??&&supplierSp.setOfBooksCode==item.setOfBooksCode>selected</#if>>${item.setOfBooksName!''}</option>
						</#list>
					</#if>
					</select>
					<span id="setOfBooksCodeError"  style="color:red;"></span>
				</td>
			</tr>	    
			<tr>
				<th> <span class="star">*</span>税率：</th>
				<td><input type="text" id="taxRate" name="taxRate"  maxlength="3" value="<#if supplierSp??&&supplierSp.taxRate??>${supplierSp.taxRate!''}</#if>"/>%（0-100） 
					<span id="taxRateError"  style="color:red;"></span> 
				</td>
			</tr>
			<tr>
				<th> <span class="star">*</span>验收差异处理方式：</th>
				<td> 
					<input type="radio" name="shipmentType" id="shipmentType" value="1" <#if supplierSp??&&supplierSp.shipmentType??&&supplierSp.shipmentType==1>checked</#if>/>销退
					<input type="radio" name="shipmentType" id="shipmentType"  value="0" <#if supplierSp??&&supplierSp.shipmentType??&&supplierSp.shipmentType==0>checked</#if>/>验退
				</td>
			</tr>
			<#--
			<tr>
				<th> <span class="star">*</span>结算币种：</th>
				<td>
					<select name="tradeCurrency">
						<option value="">请选择结算币种</option>
						<#list tradeCurrencyEnum as item>
						<#if supplierSp.tradeCurrency??&&item == supplierSp.tradeCurrency>
							<option selected="selected" value="${item.code}">${item.desc}</option>
						<#else>
							<option value="${item.code}">${item.desc}</option>
						</#if>
						</#list>
					</select>
				</td>
			</tr>
			-->
			<tr>
				<th> <span class="star">*</span>优惠券分摊比例：</th>
				<td>
					<input type="text" name="couponsAllocationProportion" maxlength="3" id="couponsAllocationProportion" value="<#if supplierSp??&&supplierSp.couponsAllocationProportion??>${supplierSp.couponsAllocationProportion!''}</#if>" />%（0-100） 
					<span style="color:red;" id="couponsAllocationProportionError"></span> 
				</td>
			</tr>
			<tr>
				<th> <span class="star">*</span>银行开户名：</th>
				<td>
					<input type="text" name="contact" id="contact"  value="<#if supplierSp??&&supplierSp.contact??>${supplierSp.contact!''}</#if>"/>
					<span style="color:red;" id="contactError"></span>
				</td>
				<th>营业执照所在地：</th>
				<td> <input type="text" name="businessLocal" id="businessLocal" value="<#if supplierSp??&&supplierSp.businessLocal??>${supplierSp.businessLocal!''}</#if>"/></td>
			</tr>
			<tr>
				<th> <span class="star">*</span>公司银行帐号：</th>
				<td><input type="text" name="account" id="account" value="<#if supplierSp??&&supplierSp.account??>${supplierSp.account!''}</#if>"/>
	                        &nbsp;&nbsp;<span style="color:red;" id="accountError"></span> 
				</td>
				<th> 营业执照有效期：</th>
				<td><input type="text" name="businessValidity" id="businessValidity" value="<#if supplierSp??&&supplierSp.businessValidity??>${supplierSp.businessValidity!''}</#if>" /></td>
			</tr>
			<tr>
				<th> <span class="star">*</span>开户行支行名称：</th>
				<td><input type="text" name="subBank" id="subBank" value="<#if supplierSp??&&supplierSp.subBank??>${supplierSp.subBank!''}</#if>" />
	                         &nbsp;&nbsp;<span style="color:red;" id="subBankError"></span> </td>
				<th>税务登记证号：</th>
				<td><input type="text" name="tallageNo" id="tallageNo"  value="<#if supplierSp??&&supplierSp.tallageNo??>${supplierSp.tallageNo!''}</#if>"/></td>
			</tr>
			<tr>
				<th> <span class="star">*</span>开户银行所在地：</th>
				<td><input type="text" name="bankLocal" id="bankLocal"  value="<#if supplierSp??&&supplierSp.bankLocal??>${supplierSp.bankLocal!''}</#if>"/>
	                        &nbsp;&nbsp;<span style="color:red;" id="bankLocalError"></span></td>
				<th>组织机构代码：</th>
				<td> <input type="text" name="institutional" id="institutional" value="<#if supplierSp??&&supplierSp.institutional??>${supplierSp.institutional!''}</#if>" /></td>
			</tr>
			<tr>
				<th>营业执照号：</th>
				<td> <input type="text" name="businessLicense" id="businessLicense" value="<#if supplierSp??&&supplierSp.businessLicense??>${supplierSp.businessLicense!''}</#if>"/></td>
				<th>纳税人识别号：</th>
				<td><input type="text" name="taxpayer" id="taxpayer" value="<#if supplierSp??&&supplierSp.taxpayer??>${supplierSp.taxpayer!''}</#if>" /></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td> 
					<input type="button" value="保存" class="btn-save" onClick="return addSupplier();"/>
					<input type="button" value="取消" class="btn-back" onClick="goBack();"/>
				</td>
			</tr>
		</table>
	</form>
	</div>
</div>
</div>
</body>
</html>