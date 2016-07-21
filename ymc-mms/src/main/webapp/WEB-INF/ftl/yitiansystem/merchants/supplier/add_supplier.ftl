<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,优购网,B网络营销系统,商品管理" />
<meta name="Description" content=" , ,B网络营销系统-商品管理" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css?34"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script> 
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/order.js"></script> 

<style type="text/css">
.com_modi_table th{width: 200px;}
</style>

<title>优购网-招商供应商管理</title>
<script type="text/javascript">
var basePath = "${BasePath}";

function addSupplier() {
	if (!validation()) return;
	var setOfBooksName=$("#setOfBooksCode").find("option:selected").text();//成本帐套名称
	$("#setOfBooksName").val(setOfBooksName);
	
	var supplier=$("#supplier").val();//供应商名称
	var supplierId = $('#supplierId').val();	
	
	if (supplierId == '') {
		//判断商家名称是否已经存在
		$.ajax({ 
			type: "post", 
			url: "${BasePath}/yitiansystem/merchants/businessorder/existMerchantSupplieName.sc",
			data: {supplieName:supplier},
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			success: function(dt){
				if("success1"==dt){
				   $("#supplierError").html("已存在同名的招商供应商，请使用其它名称");
				   $("#supplier").focus();
				   return false;
				}else if("success2"==dt){
				   $("#supplierError").html("已存在同名的普通供应商,请使用其它名称");
				   $("#supplier").focus();
				   return false;
				}else if("success3"==dt){
				   $("#supplierError").html("已存在同名的韩货供应商,请使用其它名称");
				   $("#supplier").focus();
				   return false;
				} else {
					$.ajax({
					   type: "POST",
					   dataType: "html",
					   url: "${BasePath}/yitiansystem/merchants/businessorder/add_supplier.sc",
					   data: $('#submitForm').serialize(),
					   success: function(msg){
					     if (msg != 'false') {
					     	$('#supplierId').val(msg);
					     	location.href="${BasePath}/yitiansystem/merchants/businessorder/to_add_supplier_user.sc?supplierId=" + msg;
					     } else {
					     	alert('供应商基本信息保存失败');
					     }
					   }
					});
				}
			}
		});
	} else {
		$.ajax({
		   type: "POST",
		   dataType: "html",
		   url: "${BasePath}/yitiansystem/merchants/businessorder/add_supplier.sc",
		   data: $('#submitForm').serialize(),
		   success: function(msg){
		     if (msg != 'false') {
		     	$('#supplierId').val(msg);
		     	location.href="${BasePath}/yitiansystem/merchants/businessorder/to_add_supplier_user.sc?supplierId=" + msg;
		     } else {
		     	alert('供应商基本信息保存失败');
		     }
		   }
		});
	}
}
</script>
</head><body>
<div class="container"> 
	<!--工具栏start--><!--操作按钮start-->
	<#--<div class="toolbar">
		<div class="t-content">  
		</div>
	</div>-->
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
	<div class="fdpwd_step wrap">
	    <ul class="step1 clearfix">
	        <a href="#" onclick="gotoTab(1)"><li class="curr">1.供应商基本信息</li></a>
	        <a href="#" onclick="gotoTab(2)"><li>2.账户信息</li></a>
	        <a href="#" onclick="gotoTab(3)"><li>3.品牌分类授权</li></a>
	    </ul>
	</div>
	<form action="${BasePath}/yitiansystem/merchants/businessorder/add_supplier.sc" method="post" id="submitForm" name="submitForm">
		<input type="hidden" name="supplierId" id="supplierId" value="<#if supplier?? && supplier.id??>${supplier.id}</#if>" />
		<input type="hidden" name="balanceTraderName" id="balanceTraderName">
		<input type="hidden" name="setOfBooksName" id="setOfBooksName">
		<input type="hidden" name="_setOfBooksCode" id="_setOfBooksCode" value="<#if supplier?? && supplier.setOfBooksCode??>${supplier.setOfBooksCode}</#if>">
		<input type="hidden" name="_isInputYougouWarehouse" id="_isInputYougouWarehouse" value="<#if supplier?? && supplier.isInputYougouWarehouse??>${supplier.isInputYougouWarehouse}</#if>">
		<input type="hidden" name="supplierType" id="supplierType" value="招商供应商">
		<h3 style="width:892px;">商家基础资料</h3>
		<table class="com_modi_table">
			<tr>
				<th width="150px;"><span class="star">*</span>供应商名称：</th>
				<td>
					<input type="text" id="supplier" name="supplier" maxlength="32"  onblur="" value="<#if supplier?? && supplier.supplier??>${supplier.supplier}</#if>"/>
					<span id="supplierError" style="color:red;"></span>
				</td>
				<th width="150px;"><span class="star">*</span>供应商类型：</th>
				<td>
				    <b>招商供应商</b>
				</td>
			</tr>
			<tr>
				<th><span class="star">*</span>供应商简称：</th>
				<td>
				    <input type="text" id="simpleName" name="simpleName" maxlength="32"  onblur="" value="<#if supplier?? && supplier.simpleName??>${supplier.simpleName}</#if>"/>
				    <span id="simpleNameError" style="color:red;"></span>
				</td>
				<th>POS供应商编码：</th>
				<td>
					<input type="text" id="posSourceNo" name="posSourceNo" maxlength="32"  onblur="" value="<#if supplier?? && supplier.posSourceNo??>${supplier.posSourceNo}</#if>"/>
				</td>
			</tr>
			<tr>
				<th><span class="star">*</span>合作模式：</th>
				<td>
				    <select name="isInputYougouWarehouse" onChange="changeWms(this.value);">
	                	<#list statics['com.belle.other.model.pojo.SupplierSp$CooperationModel'].values()?sort_by('description')?reverse as item>
	                   	<option id="_option_${item_index}" value="${item.ordinal()}" <#if item.ordinal()==0>selected</#if>>${item.description!''}</option>
	                 	</#list>
                   	</select>
				</td>
				<th><span class="star">*</span>使用优购WMS：</th>
                <td>
                   	<input type="radio" name="isUseYougouWms" value="1"  <#if supplier?? && supplier.isUseYougouWms == 1 >checked</#if>>是</label>
                   	<input type="radio" name="isUseYougouWms" value="0"  <#if supplier??><#if supplier.isUseYougouWms == 0 >checked</#if><#else>checked</#if>>否</label>
               </td>
			</tr>
			<tr>
			    <th>合同期限(月)：</th>
				<td>
					<input type="text" id="conTime" maxlength="3" name="conTime"  maxlength="8" value="<#if supplier?? && supplier.conTime??>${supplier.conTime}</#if>"/>
                	<span id="conTimeError" style="color:red;"></span> 
                </td>
                <th> <span class="star">*</span>一般纳税人：</th>
                <td>
                	<input type="radio" name="taxplayerType" value="1">是</label>
                   	<input type="radio" name="taxplayerType" value="0" checked>否</label>
                </td>
            </tr>
            <tr>
               <th> <span class="star">*</span>增值税专用发票：</th>
                <td>
                   	<input type="radio" name="isAddValueInvoice" value="1" >是</label>
                   	<input type="radio" name="isAddValueInvoice" value="0" checked>否</label>
               </td>
				<th><span class="star">*</span>增值税税率：</th>
				<td>
					<input type="text" id="taxRate" name="taxRate" maxlength="3" onblur="" value="<#if supplier?? && supplier.taxRate??>${supplier.taxRate}</#if>" />&nbsp;%（0-100）
				 	<span id="taxRateError"  style="color:red;"></span>
				 </td>
            </tr>
			<tr>
				<th><span class="star">*</span>成本帐套名称：</th>
               	<td>
                	<select id="setOfBooksCode" name="setOfBooksCode">
                	<#if costSetofBooksList??>
	                    <option value="">请选择成本帐套名称</option>
	                	<#list costSetofBooksList as item>
	                   	<option value="${item.setOfBooksCode!''}" <#if supplier??&&supplier.setOfBooksCode??&&supplier.setOfBooksCode==item.setOfBooksCode>selected</#if>>${item.setOfBooksName!''}</option>
	                 	</#list>
                   	</#if>
                   	</select>
                   	<span id="setOfBooksCodeError"  style="color:red;"></span>
               	</td>
			    <th> <span class="star">*</span>验收差异处理方式：</th>
				<td> 
					<input type="radio" name="shipmentType" id="shipmentType" value="1" <#if supplier??><#if supplier.shipmentType == 1>checked</#if><#else>checked</#if>/>销退
                	<input type="radio" name="shipmentType" id="shipmentType" value="0" <#if supplier?? && supplier.shipmentType == 0>checked</#if>/>验退
			 	</td>
            </tr>
			 <tr>
				<th>备注：</th>
				<td colspan=3>
					<input style="width:582px;" type="text" id="remark" maxlength="100" name="remark"  value="<#if supplier?? && supplier.remark??>${supplier.remark}</#if>"/> 
                	<span id="remarkError" style="color:red;"></span> 
                </td>
			</tr>
			</table>
			<h3 style="width:892px;">销售环节信息</h3>
			<table class="com_modi_table">
			<tr>
                <th width="150px;"> <span class="star">*</span>销售发票开具方：</th>
                <td>
                	<input type="radio" name="isInvoice" value="0" checked>优购开具</label>
                   	<input type="radio" name="isInvoice" value="1">商家开具</label>
               </td>
               	<th><span class="star">*</span>优惠券分摊比例：</th>
				<td>
					<input type="text" id="couponsAllocationProportion" maxlength="3" name="couponsAllocationProportion"  maxlength="8" value="<#if supplier?? && supplier.couponsAllocationProportion??>${supplier.couponsAllocationProportion}</#if>"/>&nbsp;%（0-100） 
                	<span id="couponsAllocationProportionError" style="color:red;"></span> 
                </td>
            </tr>
			</table>
			<h3 style="width:892px;">开票资料信息</h3>
			<table class="com_modi_table">
            <tr>
                <th  width="150px;"><span class="star">*</span>开票名称：</th>
                <td>
                	<input type="text" name="contact" id="contact" value="<#if supplier?? && supplier.contact??>${supplier.contact}</#if>" />
                   	<span style="color:red;" id="contactError"></span>
               	</td>
               	<th><span class="star">*</span>纳税人识别号：</th>
                <td>
                	<input type="text" name="tallageNo" id="tallageNo" value="<#if supplier?? && supplier.tallageNo??>${supplier.tallageNo}</#if>" />
                   	<span style="color:red;" id="tallageNoError"></span>
               	</td>
            </tr>
            <tr>
            	<th><span class="star">*</span>开票地址：</th>
               	<td>
               		<input type="text" name="invoiceAddress" id="invoiceAddress" value="<#if supplier?? && supplier.invoiceAddress??>${supplier.invoiceAddress}</#if>" />
                    <span style="color:red;" id="invoiceAddressError" ></span>
               	</td>
               	<th><span class="star">*</span>电话：</th>
               	<td>
               		<input type="text" name="invoicePhone" id="invoicePhone" value="<#if supplier?? && supplier.invoicePhone??>${supplier.invoicePhone}</#if>" />
                    <span style="color:red;" id="invoicePhoneError" ></span>
               	</td>
         	</tr>
           	<tr>
				<th><span class="star">*</span>开户行：</th>
               	<td>
               		<input type="text" name="bank" id="bank" value="<#if supplier?? && supplier.bank??>${supplier.bank}</#if>" />
                   	<span style="color:red;" id="bankError"></span> 
               	</td>
               	<th><span class="star">*</span>银行账户：</th>
               	<td>
               		<input type="text" name="account" id="account" value="<#if supplier?? && supplier.account??>${supplier.account}</#if>" />
                   	<span style="color:red;" id="accountError"></span> 
               	</td>
			</tr>
			<tr>
				<th colspan=4 style="border:solid #add9c0; border-width:0px 0px 1px 0px; text-align:left;">附加:银行账户信息</th>
			</tr>
			<tr>
				<th>开户行1：</th>
				<td> 
					<input type="text" name="bankSecond" id="bankSecond" value="<#if bankSecond??>${bankSecond}</#if>"/> 
				</td>
				<th>银行账户1：</th>
				<td> 
					<input type="text" name="accountSecond" id="accountSecond" value="<#if accountSecond??>${accountSecond}</#if>"/> 
				</td>
			</tr>
			<tr>
				<th>开户行2：</th>
				<td> 
					<input type="text" name="bankThird" id="bankThird" value="<#if bankThird??>${bankThird}</#if>"/> 
				</td>
				<th>银行账户2：</th>
				<td> 
					<input type="text" name="accountThird" id="accountThird" value="<#if accountThird??>${accountThird}</#if>"/> 
				</td>
			</tr>
			<tr>
				<td colspan=4 style="text-align:center;"> 
					<input type="button" value="保存并继续" class="autoBtn mt10" onClick="return addSupplier();"/>
				</td>
			</tr>
		</table>
	</form>
</div>
</div>
</div>
</body>
<script type="text/javascript">
$(document).ready(function(){
  var _isInputYougouWarehouse = $('#_isInputYougouWarehouse').val();
  if (_isInputYougouWarehouse != '') {
  	$("input[@name=isInputYougouWarehouse][@value="+_isInputYougouWarehouse+"]").attr("checked",true);
  }
  var _setOfBooksCode = $('#_setOfBooksCode').val();
  if (_setOfBooksCode != '') {
  	$("#setOfBooksCode").find("option[value="+_setOfBooksCode+"]").attr("selected",true);
  }
});
function changeWms(value) {
	if("1"==value){
		$("input:radio[name=isUseYougouWms][value='1']").attr('checked','true');
	}else{
		$("input:radio[name=isUseYougouWms][value='0']").attr('checked','true');
	}
	if("0"==value){
	    $("#setOfBooksCode option[value='ZT20140903837400']").attr("selected", true); 
	}else{
		$("#setOfBooksCode option[value='']").attr("selected", true); 
	}
}
function validation() {
	var supplier=$("#supplier").val();//供应商名称
	var simpleName=$("#simpleName").val();//供应商简称
    var taxRate=$("#taxRate").val();//税率
    var setOfBooksCode=$("#setOfBooksCode").val();//成本帐套名称
    var conTime=$("#conTime").val();
    var errorCount=0;
	if(supplier=="" ){
		$("#supplierError").html("供应商名称不能为空!");
		$("#supplier").focus();
		errorCount++;
	}else{
	   $("#supplierError").html("");
	}
	if(simpleName=="" ){
		$("#simpleNameError").html("供应商简称不能为空!");
		$("#simpleName").focus();
		errorCount++;
	}else{
	   $("#supplierError").html("");
	}
	if(setOfBooksCode=="" ){
		$("#setOfBooksCodeError").html("成本帐套名称不能为空!");
		$("#setOfBooksCode").focus();
		errorCount++;
	}else{
	   $("#setOfBooksCodeError").html("");
	}
	if(taxRate=="" ){
		$("#taxRateError").html("税率不能为空!");
		$("#taxRate").focus();
		errorCount++;
	} else if (!/^([0-9]|[1-9][0-9]|[1][0][0])$/.test(taxRate)) {
		$("#taxRateError").html("税率输入不符合规范!");
		$("#taxRate").focus();
		errorCount++;
	} else{
	   $("#taxRateError").html("");
	}
	if (!/^[0-9]*$/.test(conTime)) {
		$("#conTimeError").html("合同期限输入不符合规范!");
		$("#conTime").focus();
		errorCount++;
	} else{
	   $("#conTimeError").html("");
	}
   
	var couponsAllocationProportion = $("#couponsAllocationProportion").val();//优惠券分摊比例
	var bank = $("#bank").val();//开户行
	var account = $("#account").val();	//银行账号
	var contact = $("#contact").val();//开票名称
	var invoiceAddress = $("#invoiceAddress").val();//开票地址
	var invoicePhone = $("#invoicePhone").val();//开票电话
	var tallageNo = $("#tallageNo").val();//纳税人识别号
	if(couponsAllocationProportion=="" ){
		$("#couponsAllocationProportionError").html("优惠券分摊比例不能为空!");
		$("#couponsAllocationProportion").focus();
		errorCount++;
	} else if (!/^([0-9]|[1-9][0-9]|[1][0][0])$/.test(couponsAllocationProportion)) {
		$("#couponsAllocationProportionError").html("优惠券分摊比例输入不符合规范!");
		$("#couponsAllocationProportion").focus();
		errorCount++;
	}else{
		$("#couponsAllocationProportionError").html("");
	}
	if(bank=="" ){
		$("#bankError").html("开户行不能为空!");
		$("#bank").focus();
		errorCount++;
	}else{
		$("#contactError").html("");
	}
	if(account=="" ){
		$("#accountError").html("银行账号不能为空!");
		$("#account").focus();
		errorCount++;
	}else{
		$("#accountError").html("");
	}
	if(contact=="" ){
		$("#contactError").html("开票名称不能为空!");
		$("#contact").focus();
		errorCount++;
	}else{
		$("#contactError").html("");
	}
	if(invoiceAddress=="" ){
		$("#invoiceAddressError").html("开票地址不能为空!");
		$("#invoiceAddress").focus();
		errorCount++;
	}else{
		$("#invoiceAddressError").html("");
	}
	if(invoicePhone=="" ){
		$("#invoicePhoneError").html("电话不能为空!");
		$("#invoicePhone").focus();
		errorCount++;
	}else{
		$("#invoicePhoneError").html("");
	}
	if(tallageNo=="" ){
		$("#tallageNoError").html("纳税人识别号不能为空!");
		$("#tallageNo").focus();
		errorCount++;
	}else{
		$("#tallageNoError").html("");
	}
	if(errorCount>0){
	    return false;
	}else{
		return true;
	}
}

function gotoTab(flag) {
	var supplierId = $('#supplierId').val();
	if (supplierId != '') {
		if (flag == 2) {
			location.href="${BasePath}/yitiansystem/merchants/businessorder/to_add_supplier_user.sc?supplierId=" + supplierId;
		} else if (flag == 3) {
			location.href="${BasePath}/yitiansystem/merchants/businessorder/to_add_supplier_auth.sc?supplierId=" + supplierId;
		}
	}
}
</script>
</html>