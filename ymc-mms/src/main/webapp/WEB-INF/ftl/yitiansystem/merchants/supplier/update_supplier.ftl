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

<style type="text/css">
.com_modi_table th{width: 200px;}
</style>

<title>B网络营销系统-采购管理-优购网</title>
<script type="text/javascript">
var basePath = "${BasePath}";

//提交表单
function updateSupplier() {
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
		$("#bankError").html("");
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

	var setOfBooksName=$("#setOfBooksCode").find("option:selected").text();//成本帐套名称
	$("#setOfBooksName").val(setOfBooksName);
	var isYougou=$('input:radio[name="isInputYougouWarehouse"]:checked').val();
	$("#isInputYougouWarehouse").val(isYougou);
	var shipmentType=$('input:radio[name="shipmentType"]:checked').val();
	$("#shipmentType").val(shipmentType);
    
    if(errorCount==0){
		var defaultSupplier = "";
		<#if supplierSp?? && supplierSp.supplier??>
		  defaultSupplier = '${supplierSp.supplier}';
		</#if>
   if(defaultSupplier != supplier){
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
				   return;
				}else if("success2"==dt){
				   $("#supplierError").html("已存在同名的普通供应商,请使用其它名称");
				   $("#supplier").focus();
				   return;
				}else if("success3"==dt){
				   $("#supplierError").html("已存在同名的韩货供应商,请使用其它名称");
				   $("#supplier").focus();
				   return false;
				} else {
					$.ajax({
					   type: "POST",
					   dataType: "html",
					   url: "${BasePath}/yitiansystem/merchants/businessorder/update_supplier.sc",
					   data: $('#submitForm').serialize(),
					   success: function(msg){
					     if (msg == 'true') {
					     	alert('修改供应商基本信息成功！');
					     } else {
					     	alert('修改供应商基本信息失败！');
					     }
					   }
					});
				}
			}
		});
    }else{
    	$.ajax({
		   type: "POST",
		   dataType: "html",
		   url: "${BasePath}/yitiansystem/merchants/businessorder/update_supplier.sc",
		   data: $('#submitForm').serialize(),
		   success: function(msg){
		     if (msg == 'true') {
		     	alert('修改供应商基本信息成功！');
		     } else {
		     	alert('修改供应商基本信息失败！');
		     }
		   }
		});
    }
	     
    }
}

function updateSupplierUser() {
	if (!validation()) return; 
	
	var loginAccount = $("#loginName").val();//登录账号	
	$.ajax({
	   type: "POST",
	   dataType: "html",
	   url: "${BasePath}/yitiansystem/merchants/businessorder/add_supplier_user.sc",
	   data: $('#submitForm').serialize(),
	   success: function(msg){
	     if (msg != 'false') {
	     	alert('供应商账户信息保存成功!');
	     } else {
	     	alert('供应商账户信息保存失败!');
	     }
	   }
	});
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
	<!--<div class="toolbar">
		<div class="t-content">  
		</div>
	</div>-->
	<!--工具栏end-->
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
			   <li <#if flag??&&flag==1>class="curr"</#if>><span><a href="#" onclick="onclick_tab(1);">商家基本信息</a></span></li>
			   <li <#if flag??&&flag==2>class="curr"</#if>><span><a href="#" onclick="onclick_tab(2);">账户信息</a></span></li>
			   <!--li <#if flag??&&flag==3>class="curr"</#if>><span><a href="#" onclick="onclick_tab(3);">品类授权</a></span></li-->
			</ul>
		</div>
		<!--当前位置end-->
<!--主体start-->
<div id="modify" class="modify">
	<input type="hidden" id="basePath" value="${BasePath}">
	
	<form action="${BasePath}/yitiansystem/merchants/businessorder/update_supplier.sc" method="post" id="submitForm" name="submitForm">
	<#if flag??&&flag==1>
		<input type="hidden" name="supplierType" id="supplierType" value="招商供应商">
		<h3 style="width:740px;">商家基础资料</h3>
		<table class="com_modi_table">
		<#if supplierSp??&&supplierSp.isValid??&&supplierSp.isValid!=1>
			<tr>
				<th width="150px;"><span class="star">*</span>供应商名称：</th>
				<td>
					<input type="text" id="supplier" name="supplier" maxlength="32"  onblur="" value="<#if supplierSp?? && supplierSp.supplier??>${supplierSp.supplier}</#if>"/>
					<span id="supplierError" style="color:red;"></span>
				</td>
				<th width="150px;"><span class="star">*</span>供应商类型：</th>
				<td>
				    <b>普通供应商</b>
				</td>
			</tr>
			<tr>
				<th><span class="star">*</span>供应商简称：</th>
				<td>
				    <input type="text" id="simpleName" name="simpleName" maxlength="32"  onblur="" value="<#if supplierSp?? && supplierSp.simpleName??>${supplierSp.simpleName}</#if>"/>
				    <span id="simpleNameError" style="color:red;"></span>
				</td>
				<th>POS供应商编码：</th>
				<td>
					<input type="text" id="posSourceNo" name="posSourceNo" maxlength="32"  onblur="" value="<#if supplierSp?? && supplierSp.posSourceNo??>${supplierSp.posSourceNo}</#if>"/>
				</td>
			</tr>
			<tr>
				<th><span class="star">*</span>合作模式：</th>
				<td>
				    <select name="isInputYougouWarehouse" onChange="changeWms(this.value);">
	                	<#list statics['com.belle.other.model.pojo.SupplierSp$CooperationModel'].values()?sort_by('description')?reverse as item>
	                   	<option id="_option_${item_index}" value="${item.ordinal()}" <#if item.ordinal()==supplierSp.isInputYougouWarehouse>selected</#if>>${item.description!''}</option>
	                 	</#list>
                   	</select>
				</td>
				<th><span class="star">*</span>使用优购WMS：</th>
                <td>
                   	<input type="radio" name="isUseYougouWms" value="1"  <#if supplierSp?? && supplierSp.isUseYougouWms == 1 >checked</#if>>是</label>
                   	<input type="radio" name="isUseYougouWms" value="0"  <#if supplierSp??><#if supplierSp.isUseYougouWms == 0 >checked</#if><#else>checked</#if>>否</label>
               </td>
			</tr>
			<tr>
			    <th>合同期限(月)：</th>
				<td>
					<input type="text" id="conTime" maxlength="3" name="conTime"  maxlength="8" value="<#if supplierSp?? && supplierSp.conTime??>${supplierSp.conTime}</#if>"/>
                	<span id="conTimeError" style="color:red;"></span> 
                </td>
                <th> <span class="star">*</span>一般纳税人：</th>
                <td>
                	<input type="radio" name="taxplayerType" value="1" <#if supplierSp?? && supplierSp.taxplayerType??><#if supplierSp.taxplayerType == '1' >checked</#if><#else>checked</#if>>是</label>
                   	<input type="radio" name="taxplayerType" value="0" <#if supplierSp?? && supplierSp.taxplayerType?? && supplierSp.taxplayerType == '0' >checked</#if>>否</label>
                </td>
            </tr>
            <tr>
               <th> <span class="star">*</span>增值税专用发票：</th>
                <td>
                   	<input type="radio" name="isAddValueInvoice" value="1" <#if supplierSp?? && supplierSp.isAddValueInvoice == 1 >checked</#if>>是</label>
                   	<input type="radio" name="isAddValueInvoice" value="0" <#if supplierSp??><#if supplierSp.isAddValueInvoice == 0 >checked</#if><#else>checked</#if>>否</label>
               </td>
				<th><span class="star">*</span>增值税税率：</th>
				<td>
					<input type="text" id="taxRate" name="taxRate" maxlength="3" onblur="" value="<#if supplierSp?? && supplierSp.taxRate??>${supplierSp.taxRate}</#if>" />&nbsp;%（0-100）
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
	                   	<option value="${item.setOfBooksCode!''}" <#if supplierSp??&&supplierSp.setOfBooksCode??&&supplierSp.setOfBooksCode==item.setOfBooksCode>selected</#if>>${item.setOfBooksName!''}</option>
	                 	</#list>
                   	</#if>
                   	</select>
                   	<span id="setOfBooksCodeError"  style="color:red;"></span>
               	</td>
			    <th> <span class="star">*</span>验收差异处理方式：</th>
				<td> 
					<input type="radio" name="shipmentType" id="shipmentType" value="1" <#if supplierSp??><#if supplierSp.shipmentType == 1>checked</#if><#else>checked</#if>/>销退
                	<input type="radio" name="shipmentType" id="shipmentType" value="0" <#if supplierSp?? && supplierSp.shipmentType == 0>checked</#if>/>验退
			 	</td>
            </tr>
			 <tr>
				<th>备注：</th>
				<td colspan=3>
					<input style="width:582px;" type="text" id="remark" maxlength="3" name="remark"  maxlength="8" value="<#if supplierSp?? && supplierSp.remark??>${supplierSp.remark}</#if>"/> 
                	<span id="remarkError" style="color:red;"></span> 
                </td>
			</tr>
			</table>
			<h3 style="width:740px;">销售环节信息</h3>
			<table class="com_modi_table">
			<tr>
                <th> <span class="star">*</span>销售发票开具方：</th>
                <td>
                	<input type="radio" name="isInvoice" value="0" <#if supplierSp??><#if supplierSp.isInvoice == 0 >checked</#if><#else>checked</#if>>优购开具</label>
                   	<input type="radio" name="isInvoice" value="1" <#if supplierSp?? && supplierSp.isInvoice == 1 >checked</#if>>商家开具</label>
               </td>
				<th><span class="star">*</span>优惠券分摊比例：</th>
				<td>
					<input type="text" id="couponsAllocationProportion" maxlength="3" name="couponsAllocationProportion"  maxlength="8" value="<#if supplierSp?? && supplierSp.couponsAllocationProportion??>${supplierSp.couponsAllocationProportion}</#if>"/>&nbsp;%（0-100） 
                	<span id="couponsAllocationProportionError" style="color:red;"></span> 
                </td>
            </tr>
			</table>
			<h3 style="width:740px;">开票资料信息</h3>
			<table class="com_modi_table">
            <tr>
                <th  width="150px;"><span class="star">*</span>开票名称：</th>
                <td>
                	<input type="text" name="contact" id="contact" value="<#if supplierSp?? && supplierSp.contact??>${supplierSp.contact}</#if>" />
                   	<span style="color:red;" id="contactError"></span>
               	</td>
               	<th><span class="star">*</span>纳税人识别号：</th>
                <td>
                	<input type="text" name="tallageNo" id="tallageNo" value="<#if supplierSp?? && supplierSp.tallageNo??>${supplierSp.tallageNo}</#if>" />
                   	<span style="color:red;" id="tallageNoError"></span>
               	</td>
            </tr>
            <tr>
            	<th><span class="star">*</span>开票地址：</th>
               	<td>
               		<input type="text" name="invoiceAddress" id="invoiceAddress" value="<#if supplierSp?? && supplierSp.invoiceAddress??>${supplierSp.invoiceAddress}</#if>" />
                    <span style="color:red;" id="invoiceAddressError" ></span>
               	</td>
               	<th><span class="star">*</span>电话：</th>
               	<td>
               		<input type="text" name="invoicePhone" id="invoicePhone" value="<#if supplierSp?? && supplierSp.invoicePhone??>${supplierSp.invoicePhone}</#if>" />
                    <span style="color:red;" id="invoicePhoneError" ></span>
               	</td>
         	</tr>
           	<tr>
				<th><span class="star">*</span>开户行：</th>
               	<td>
               		<input type="text" name="bank" id="bank" value="<#if supplierSp?? && supplierSp.bank??>${supplierSp.bank}</#if>" />
                   	<span style="color:red;" id="bankError"></span> 
               	</td>
               	<th><span class="star">*</span>银行账户：</th>
               	<td>
               		<input type="text" name="account" id="account" value="<#if supplierSp?? && supplierSp.account??>${supplierSp.account}</#if>" />
                   	<span style="color:red;" id="accountError"></span> 
               	</td>
			</tr>
			<tr>
				<th colspan=4 style="border:solid #add9c0; border-width:0px 0px 1px 0px; text-align:left;">附加:银行账户信息</th>
			</tr>
			<tr>
				<th>开户行1：</th>
				<td> 
					<input type="text" name="bankSecond" id="bankSecond" value="<#if supplierExtend?? && supplierExtend.bankSecond??>${supplierExtend.bankSecond}</#if>"/> 
				</td>
				<th>银行账户1：</th>
				<td> 
					<input type="text" name="accountSecond" id="accountSecond" value="<#if supplierExtend?? && supplierExtend.accountSecond??>${supplierExtend.accountSecond}</#if>"/> 
				</td>
			</tr>
			<tr>
				<th>开户行2：</th>
				<td> 
					<input type="text" name="bankThird" id="bankThird" value="<#if supplierExtend?? && supplierExtend.bankThird??>${supplierExtend.bankThird}</#if>"/> 
				</td>
				<th>银行账户2：</th>
				<td> 
					<input type="text" name="accountThird" id="accountThird" value="<#if supplierExtend?? && supplierExtend.accountThird??>${supplierExtend.accountThird}</#if>"/> 
				</td>
			</tr>
			<tr>
				<td colspan=4 style="text-align:center;"> 
					<input type="button" value="保存" class="btn-save" onClick="return updateSupplier();"/>
				</td>
			</tr>
			<#else><#--启用状态下  不可编辑-->
			<tr>
				<th width="150px;"><span class="star">*</span>供应商名称：</th>
				<td>
					<label><#if supplierSp?? && supplierSp.supplier??>${supplierSp.supplier}</#if></label> 
				</td>
				<th width="150px;"><span class="star">*</span>供应商类型：</th>
				<td>
				    <b>招商供应商</b>
				</td>
			</tr>
			<tr>
				<th><span class="star">*</span>供应商简称：</th>
				<td>
				    <label><#if supplierSp?? && supplierSp.simpleName??>${supplierSp.simpleName}</#if></label> 
				</td>
				<th>POS供应商编码：</th>
				<td>
					<label><#if supplierSp?? && supplierSp.posSourceNo??>${supplierSp.posSourceNo}</#if></label> 
				</td>
			</tr>
						<tr>
				<th><span class="star">*</span>合作模式：</th>
				<td>
				    <select disabled name="isInputYougouWarehouse" onChange="changeWms(this.value);">
	                	<#list statics['com.belle.other.model.pojo.SupplierSp$CooperationModel'].values()?sort_by('description')?reverse as item>
	                   	<option id="_option_${item_index}" value="${item.ordinal()}" <#if item.ordinal()==supplierSp.isInputYougouWarehouse>selected</#if>>${item.description!''}</option>
	                 	</#list>
                   	</select>
				</td>
				<th><span class="star">*</span>使用优购WMS：</th>
                <td>
                   	<input type="radio" disabled name="isUseYougouWms" value="1"  <#if supplierSp?? && supplierSp.isUseYougouWms == 1 >checked</#if>>是</label>
                   	<input type="radio" disabled name="isUseYougouWms" value="0"  <#if supplierSp??><#if supplierSp.isUseYougouWms == 0 >checked</#if><#else>checked</#if>>否</label>
               </td>
			</tr>
			<tr>
			    <th>合同期限(月)：</th>
				<td>
					<label><#if supplierSp?? && supplierSp.conTime??>${supplierSp.conTime}</#if></label> 
                </td>
                <th> <span class="star">*</span>一般纳税人：</th>
                <td>
                	<input type="radio" disabled name="taxplayerType" value="1" <#if supplierSp?? && supplierSp.taxplayerType??><#if supplierSp.taxplayerType == '1' >checked</#if><#else>checked</#if>>是</label>
                   	<input type="radio" disabled name="taxplayerType" value="0" <#if supplierSp?? && supplierSp.taxplayerType?? && supplierSp.taxplayerType == '0' >checked</#if>>否</label>
                </td>
            </tr>
            <tr>
               <th> <span class="star">*</span>增值税专用发票：</th>
                <td>
                   	<input type="radio" disabled name="isAddValueInvoice" value="1" <#if supplierSp?? && supplierSp.isAddValueInvoice == 1 >checked</#if>>是</label>
                   	<input type="radio" disabled name="isAddValueInvoice" value="0" <#if supplierSp??><#if supplierSp.isAddValueInvoice == 0 >checked</#if><#else>checked</#if>>否</label>
               </td>
				<th><span class="star">*</span>增值税税率：</th>
				<td>
					<label><#if supplierSp?? && supplierSp.taxRate??>${supplierSp.taxRate}</#if></label> %
				 </td>
            </tr>
			<tr>
				<th><span class="star">*</span>成本帐套名称：</th>
               	<td>
					<label>
						<#if supplierSp??&&supplierSp.setOfBooksName??>${supplierSp.setOfBooksName!''}</#if>
					</label>
               	</td>
			    <th> <span class="star">*</span>验收差异处理方式：</th>
				<td> 
					<input type="radio" disabled name="shipmentType" id="shipmentType" value="1" <#if supplierSp??><#if supplierSp.shipmentType == 1>checked</#if><#else>checked</#if>/>销退
                	<input type="radio" disabled name="shipmentType" id="shipmentType" value="0" <#if supplierSp?? && supplierSp.shipmentType == 0>checked</#if>/>验退
			 	</td>
            </tr>
			 <tr>
				<th>备注：</th>
				<td colspan=3>
					<label><#if supplierSp?? && supplierSp.remark??>${supplierSp.remark}</#if></label> 
                </td>
			</tr>
			</table>
			<h3 style="width:740px;">销售环节信息</h3>
			<table class="com_modi_table">
			<tr>
                <th> <span class="star">*</span>销售发票开具方：</th>
                <td>
                	<input type="radio" disabled name="isInvoice" value="0" <#if supplierSp??><#if supplierSp.isInvoice == 0 >checked</#if><#else>checked</#if>>优购开具</label>
                   	<input type="radio" disabled name="isInvoice" value="1" <#if supplierSp?? && supplierSp.isInvoice == 1 >checked</#if>>商家开具</label>
               </td>
				<th><span class="star">*</span>优惠券分摊比例：</th>
				<td>
					<label><#if supplierSp?? && supplierSp.couponsAllocationProportion??>${supplierSp.couponsAllocationProportion}</#if></label>%
                </td>
            </tr>
			</table>
			<h3 style="width:740px;">开票资料信息</h3>
			<table class="com_modi_table">
            <tr>
                <th  width="150px;"><span class="star">*</span>开票名称：</th>
                <td>
                	<label><#if supplierSp?? && supplierSp.contact??>${supplierSp.contact}</#if></label> 
               	</td>
               	<th><span class="star">*</span>纳税人识别号：</th>
                <td>
                	<label><#if supplierSp?? && supplierSp.tallageNo??>${supplierSp.tallageNo}</#if></label> 
               	</td>
            </tr>
            <tr>
            	<th><span class="star">*</span>开票地址：</th>
               	<td>
               		<label><#if supplierSp?? && supplierSp.invoiceAddress??>${supplierSp.invoiceAddress}</#if></label> 
               	</td>
               	<th><span class="star">*</span>电话：</th>
               	<td>
               		<label><#if supplierSp?? && supplierSp.invoicePhone??>${supplierSp.invoicePhone}</#if></label> 
               	</td>
         	</tr>
           	<tr>
				<th><span class="star">*</span>开户行：</th>
               	<td>
               		<label><#if supplierSp?? && supplierSp.bank??>${supplierSp.bank}</#if></label> 
               	</td>
               	<th><span class="star">*</span>银行账户：</th>
               	<td>
               		<label><#if supplierSp?? && supplierSp.account??>${supplierSp.account}</#if></label> 
               	</td>
			</tr>
			<tr>
				<th colspan=4 style="border:solid #add9c0; border-width:0px 0px 1px 0px; text-align:left;">附加:银行账户信息</th>
			</tr>
			<tr>
				<th>开户行1：</th>
				<td> 
					<label><#if supplierExtend?? && supplierExtend.bankSecond??>${supplierExtend.bankSecond}</#if></label> 
				</td>
				<th>银行账户1：</th>
				<td> 
					<label><#if supplierExtend?? && supplierExtend.accountSecond??>${supplierExtend.accountSecond}</#if></label>
				</td>
			</tr>
			<tr>
				<th>开户行2：</th>
				<td> 
					<label><#if supplierExtend?? && supplierExtend.bankThird??>${supplierExtend.bankThird}</#if></label>
				</td>
				<th>银行账户2：</th>
				<td> 
					<label><#if supplierExtend?? && supplierExtend.accountThird??>${supplierExtend.accountThird}</#if></label>
				</td>
			</tr>
			</#if>
		</table>
	<#elseif flag??&&flag==2><!-- 账户信息 -->
		<table class="com_modi_table">
			<tr>
			 	<th><span class="star"></span>供应商：</th>
                <td>
                	<label>${supplierSp.supplier}(<b>${supplierSp.supplierCode}</b>)</label>
            	</td>
            </tr>
			<tr>
				<th><span class="star">*</span>登录账号：</th>
				<td>
					<#if merchantUser??&&merchantUser.loginName??>
						${merchantUser.loginName!''}
					<#else>
						<input type="text" name="loginName" id="loginName" value="<#if merchantUser?? && merchantUser.loginName??>${merchantUser.loginName}</#if>"/>
                   		<span style="color:red;" id="loginAccountError"></span>
					</#if>
				</td>
			</tr>
			<#if !merchantUser??>
				<tr>
	            	<th><span class="star">*</span>登录密码：</th>
	                <td>
	                  	<input type="password" name="password" id="password" value="<#if merchantUser?? && merchantUser.password??>${merchantUser.password}</#if>" />
	                	<span style="color:red;" id="loginPasswordError"></span>
	               </td>
	           	</tr>
	            <tr>
	            	<th><span class="star">*</span>确认密码：</th>
	                <td>
	                	<input type="password" name="passwordTwo" id="passwordTwo" value="<#if merchantUser?? && merchantUser.password??>${merchantUser.password}</#if>" />
	                	<span style="color:red;" id="passwordTwoError"></span>
	                </td>
	            </tr>
			</#if>
			<#if merchantUser??&&merchantUser.email??>
				<tr>
	            	<th>邮箱：</th>
	                <td>
	                	${merchantUser.email}
	                </td>
	            </tr>
            </#if>
            <#if !merchantUser??>
            <tr>
				<th>&nbsp;</th>
				<td> 
					<input type="button" value="保存" class="btn-save" onClick="return updateSupplierUser();"/>
				</td>
			</tr>
			</#if>
		</table>
	</#if>
	
	<input type="hidden" id="flag" name="flag" value="${flag?default(1)}" />
    <input type="hidden" name="setOfBooksName" id="setOfBooksName" value="<#if supplierSp??&&supplierSp.setOfBooksName??>${supplierSp.setOfBooksName!''}</#if>">
	<input type="hidden" name="id" id="id" value="<#if supplierSp??&&supplierSp.id??>${supplierSp.id!''}</#if>">
	<input type="hidden" name="supplierId" id="supplierId" value="<#if supplierSp??&&supplierSp.id??>${supplierSp.id!''}</#if>">
	<input type="hidden" name="isValid" id="isValid" value="<#if supplierSp??&&supplierSp.isValid??>${supplierSp.isValid!''}</#if>">
	<input type="hidden" name="supplierCode" id="supplierCode" value="<#if supplierSp??&&supplierSp.supplierCode??>${supplierSp.supplierCode!''}</#if>">
	<input type="hidden" name="inventoryCode" id="inventoryCode" value="${supplierSp.inventoryCode!''}">
	</form>
	</div>
</div>
</div>
</body>
<script type="text/javascript">
//切换tab页
function onclick_tab(flag) {
	var url = "${BasePath}/yitiansystem/merchants/businessorder/to_update_supplier.sc";
	$('#flag').val(flag);
	location.href=url + '?flag=' + flag + '&id=' + $('#id').val();
}
function changeWms(value) {
	if("1"==value){
		$("input:radio[name=isUseYougouWms][value='1']").attr('checked','true');
	}else{
		$("input:radio[name=isUseYougouWms][value='0']").attr('checked','true');
	}
}
function addValueInvoiceCeck(show) {
    if('none'==show){
      $("#isAddValueInvoice2").attr("checked","checked");
    }
	document.getElementById('isAddValueInvoiceTr').style.display=show;
}
function validation() {
	var loginAccount = $("#loginName").val();//登录账号
	var loginPassword= $("#password").val();//登录密码
	var passwordTwo = $("#passwordTwo").val();//确认密码
	
	if(loginAccount=="" ){
		$("#loginAccountError").html("登录账号不能为空!");
		$("#loginName").focus();
		return false;
	}else{
		$("#loginAccountError").html("");
	}
	var reg = /[\W]/g;
	if(reg.exec(loginAccount)){
		$("#loginAccountError").html("登录账号不能是中文字符!");
		$("#loginName").focus();
		return false;
	}else{
		$("#loginAccountError").html("");
	}
	if(loginPassword=="" ){
		$("#loginPasswordError").html("登录密码不能为空!");
		$("#password").focus();
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
		$("#password").focus();
		return false;
	}else{
		$("#loginPasswordError").html("");
	}
	if(loginPassword!=passwordTwo ){
		$("#passwordTwoError").html("登录密码和确认密码不一致!");
		$("#password").focus();
		return false;
	}else{
		$("#passwordTwoError").html("");
	}
	
	return true;
}

function verifyAddress(email){
	var pattern =/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/; 
	var flag = pattern.test(email);
	return flag;
} 
</script>
</html>