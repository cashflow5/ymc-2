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

//提交表单
function addSupplier() {
    var supplier=$("#supplier").val();//供应商名称
    var simpleName=$("#simpleName").val();//供应商简称
    var address=$("#address").val();//供应商地址
    var taxRate=$("#taxRate").val();//税率
    var supplierType=$("#supplierType").val();//供应商类型
    var setOfBooksCode=$("#setOfBooksCode").val();//成本帐套名称
    var balanceTraderCode=$("#balanceTraderCode").val();//结算商家名称
    var loginAccount = $("#loginAccount").val();//登录账号
	var  loginPassword= $("#loginPassword").val();//登录密码
	var passwordTwo = $("#passwordTwo").val();//确认密码
	var bankNo = $("#bankNoHistory").val();//品牌BrandNo
	var email = $("#email").val();
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
	var reg = /[\W]/g;
	if(reg.exec(loginAccount)){
		$("#loginAccountError").html("登录账号不能是中文字符!");
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
	if (email!='' && !verifyAddress(email)) {
		$("#emailError").html("请输入正确的邮箱！");
		$("#email").focus();
		return false;
	} else {
		$("#emailError").html("");
	}
	if(bankNo=="" ){
		$("#bankNameError").html("请选择授权品牌!");
		return false;
	}else{
		$("#bankNameError").html("");
	}
	if(catStrs.length <= 0 ){
		$("#bankNameError").html("请选择授权分类!");
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
	if(setOfBooksCode=="" ){
		$("#setOfBooksCodeError").html("成本帐套名称不能为空!");
		$("#setOfBooksCode").focus();
		return false;
	}else{
	   $("#setOfBooksCodeError").html("");
	}
	if(taxRate=="" ){
		$("#taxRateError").html("税率不能为空!");
		$("#taxRate").focus();
		return false;
	}else{
	   $("#taxRateError").html("");
	}
   
	var couponsAllocationProportion = $("#couponsAllocationProportion").val();//优惠券分摊比例
	var contact = $("#contact").val();//银行开户名
	var account = $("#account").val();	//公司银行帐号
	var subBank = $("#subBank").val();//开户行支行名称
	var bankLocal = $("#bankLocal").val();//开户银行所在地
	if(couponsAllocationProportion=="" ){
		$("#couponsAllocationProportionError").html("优惠券分摊比例不能为空!");
		$("#couponsAllocationProportion").focus();
		return false;
	}else{
		$("#couponsAllocationProportionError").html("");
	}
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
						  document.submitForm.submit();
						}
					} 
				});
			}
		} 
	});
    
}
//跳转到添加品牌权限设置
function addBrand(){
    openwindow('${BasePath}/yitiansystem/merchants/businessorder/to_addBank.sc?flag=1',1100,600,'添加品牌');
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
	
    openwindow('${BasePath}/yitiansystem/merchants/businessorder/to_addCat.sc?flag=1&brandNos=' + selectedBrandNos, 500, 200, '添加分类');
}

//返回到商品列表页面
function goBack(){
     location.href="${BasePath}/yitiansystem/merchants/businessorder/to_merchantsList.sc";
}

function verifyAddress(email){
	var pattern =/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/; 
	var flag = pattern.test(email);
	return flag;
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
			  <li class='curr'> <span><a href="">添加商家信息</a></span> </li>
			</ul>
		</div>
		<!--当前位置end--> 
<!--主体start-->
<div id="modify" class="modify">
<input type="hidden" id="basePath" value="${BasePath}">
	<form action="${BasePath}/yitiansystem/merchants/businessorder/add_merchants.sc" method="post" id="submitForm" name="submitForm">
			 <input type="hidden" name="balanceTraderName" id="balanceTraderName">
			 <input type="hidden" name="setOfBooksName" id="setOfBooksName">
				<table class="com_modi_table">
				    <tr>
                    	<th><span class="star">*</span>登录账号：</th>
                        <td>
                        	<input type="text" name="loginAccount" id="loginAccount" value=""/>
                           	<span style="color:red;" id="loginAccountError"></span>
                        </td>
                    </tr>
                    <tr>
                    	<th><span class="star">*</span>登录密码：</th>
                        <td>
                          	<input type="password" name="loginPassword" id="loginPassword"  />
                        	<span style="color:red;" id="loginPasswordError"></span>
                       </td>
                   	</tr>
                    <tr>
                    	<th><span class="star">*</span>确认密码：</th>
                        <td>
                        	<input type="password" name="passwordTwo" id="passwordTwo"  />
                        	<span style="color:red;" id="passwordTwoError"></span>
                        </td>
                    </tr>
                    <tr>
                    	<th>邮箱：</th>
                        <td>
                        	<input type="text" name="email" id="email"  />
                        	<span style="color:red;" id="emailError"></span>
                        </td>
                    </tr>
					<tr>
					 	<th><span class="star">*</span>品类授权：</th>
                        <td>
                        	<input type="hidden" name="bankNoHistory" id="bankNoHistory" value="" />
                        	<input type="hidden" name="bankNameHidden" id="bankNameHidden"  />
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
						<th><span class="star">*</span>供应商名称：</th>
						<td>
							<input type="text" id="supplier" name="supplier" maxlength="32"  onblur=""/>
							<span id="supplierError" style="color:red;"></span>
						</td>
					</tr>
					<tr>
						<th><span class="star">*</span>供应商类型：</th>
						<td>
						    <input type="text" id="supplierType" name="supplierType" readonly="readonly"  value="<#if flag??&&flag=='1'>普通供应商<#elseif flag??&&flag=='2'>招商供应商</#if>"/>
							<span id="supplierTypeError"  style="color:red;"></span>
						</td>
					</tr>
					<tr>
						<th><span class="star">*</span>合作模式：</th>
						<td>
                           	<#list statics['com.belle.other.model.pojo.SupplierSp$CooperationModel'].values()?sort_by('description')?reverse as item>
                           	<input type="radio" id="_radio_${item_index}" name="isInputYougouWarehouse" value="${item.ordinal()}" <#if item.checked>checked="checked"</#if>/><label for="_radio_${item_index}">${item.description}</label>
                           	</#list>
						</td>
					</tr>
					<tr>
                        <th> <span class="star">*</span>使用优购WMS：</th>
                        <td>
                           	<input type="radio" name="isUseYougouWms" value="1"  <#if supplierSp?? && supplierSp.isUseYougouWms == 1 >checked</#if>  >是</label>
                           	<input type="radio" name="isUseYougouWms" value="0"  <#if supplierSp?? ><#if supplierSp.isUseYougouWms == 0 >checked</#if><#else>checked</#if> >否</label>
                       </td>
                    </tr>
					<tr>
						<th>成本帐套名称：</th>
                       	<td>
                        	<select id="setOfBooksCode" name="setOfBooksCode">
                        	<#if costSetofBooksList??>
                            <option value="">请选择成本帐套名称</option>
                            	<#list costSetofBooksList as item>
                               	<option value="${item.setOfBooksCode!''}">${item.setOfBooksName!''}</option>
                             	</#list>
                           	</#if>
                           	</select>
                           	<span id="setOfBooksCodeError"  style="color:red;"></span>
                       	</td>
                    </tr>
					<tr>
						<th><span class="star">*</span>税率：</th>
						<td>
							<input type="text" id="taxRate" name="taxRate" maxlength="3" onblur=""/>%（0-100）
						 	<span id="taxRateError"  style="color:red;"></span>
						 </td>
					</tr>
					<tr>
						<th> <span class="star">*</span>验收差异处理方式：</th>
						<td> 
							<input type="radio" name="shipmentType" id="shipmentType" value="1" checked/>销退
                        	<input type="radio" name="shipmentType" id="shipmentType"  value="0"/>验退
					 	</td>
					</tr>
					<#--
					<tr>
						<th> <span class="star">*</span>结算币种：</th>
						<td>
						<select name="tradeCurrency">
							<option value="">请选择结算币种</option>
							<#list tradeCurrencyEnum as item>
							<#if tradeCurrency??&&item == tradeCurrency>
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
						<th><span class="star">*</span>优惠券分摊比例：</th>
						<td>
							<input type="text" id="couponsAllocationProportion" maxlength="3" name="couponsAllocationProportion"  maxlength="8"/>%（0-100） 
                        	<span id="couponsAllocationProportionError" style="color:red;"></span> 
                        </td>
					</tr>
                    <tr>
                        <th><span class="star">*</span>银行开户名：</th>
                        <td>
                        	<input type="text" name="contact" id="contact"  />
                           	<span style="color:red;" id="contactError"></span>
                       	</td>
                        <th>营业执照所在地：</th>
                        <td>
                        	<input type="text" name="businessLocal" id="businessLocal" />
                        </td>
                    </tr>
                    <tr>
                    	<th><span class="star">*</span>公司银行帐号：</th>
                       	<td>
                       		<input type="text" name="account" id="account"  />
	                        <span style="color:red;" id="accountError" ></span>
	                   	</td>
						<th> 营业执照有效期：</th>
						<td>
							<input type="text" name="businessValidity" id="businessValidity"  />
						</td>
                 	</tr>
                   	<tr>
						<th><span class="star">*</span>开户行支行名称：</th>
                       	<td>
                       		<input type="text" name="subBank" id="subBank"  />
	                       	<span style="color:red;" id="subBankError"></span> 
	                   	</td>
						<th>税务登记证号：</th>
						<td>
							<input type="text" name="tallageNo" id="tallageNo"  />
						</td>
					</tr>
					<tr>
						<th><span class="star">*</span>开户银行所在地：</th>
                       	<td>
                       		<input type="text" name="bankLocal" id="bankLocal"  />
	                        <span style="color:red;" id="bankLocalError"></span>
	                  	</td>
						<th>组织机构代码：</th>
						<td>
							<input type="text" name="institutional" id="institutional"  />
						</td>
					</tr>
					<tr>
						<th>营业执照号：</th>
						<td> 
							<input type="text" name="businessLicense" id="businessLicense" /> 
						</td>
						<th>纳税人识别号：</th>
						<td>
							<input type="text" name="taxpayer" id="taxpayer"  /> 
						</td>
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