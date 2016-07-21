<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,优购网,B网络营销系统,商品管理" />
<meta name="Description" content=" , ,B网络营销系统-商品管理" />
<script type="text/javascript" src="../js/common.js"></script>
<script type="text/javascript" src="../js/jquery-1.3.2.min.js" ></script>
<#include "../../../yitiansystem/yitiansystem-include.ftl">
<#--<script type="text/javascript" src="${BasePath}/js/supply/addSupplierBase.js" ></script>-->
<title>B网络营销系统-采购管理-优购网</title>
<script type="text/javascript">
function checkData(){
	var elObj = document.getElementById("supplier");
	if(elObj.value == ""){
		alert("请输入供应商名称！");
		elObj.focus();
		return false;
	}
	document.getElementById("submitForm").submit();
}

//验证名称的结构标志
var validateFlag=true;
var config = {
	form : "submitForm",
	submit : submitForms,
	fields : [{
		name : 'supplier',
		allownull : false,
		regExp :/^\S+$/,
		defaultMsg : '请输入供应商名称',
		focusMsg : '请输入供应商名称',
		errorMsg : '请输入数字 字母  下划线 与中文',
		rightMsg : '供应商名称输入正确',
		msgTip : 'supplierTip'
	},
	{
		name : 'simpleName',
		allownull : false,
		regExp :/^\S+$/,
		defaultMsg : '请输入供应商简称',
		focusMsg : '请输入供应商简称',
		errorMsg : '请输入数字 字母  下划线 与中文',
		rightMsg : '供应商简称输入正确',
		msgTip : 'simpleNameTip'
	},
	{
		name : 'address',
		allownull : false,
		regExp : /^\S+$/,
		defaultMsg : '请输入供应商地址',
		focusMsg : '请输入供应商地址',
		errorMsg : '请输入数字 字母  下划线 与中文',
		rightMsg : '供应商地址输入正确',
		msgTip : 'addressTip'
	},{
		name : 'supplierType',
		allownull : false,
		regExp : /^\S+$/,
		defaultMsg : '请选择供应商类型',
		focusMsg : '请选择供应商类型',
		errorMsg : '请选择供应商类型',
		rightMsg : '供应商类型输入正确',
		msgTip : 'supplierTypeTip'
	},
	{
		name : 'taxRate',
		regExp :/^((\d|[123456789]\d)(\.\d+)?|100)$/,
		defaultMsg :'请输入税率(0-100)',
		focusMsg : '请输入税率(0-100)',
		errorMsg : '税率必须为数字,在0-100之间',
		rightMsg : '税率输入正确',
		msgTip : 'taxRateTip'
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
  var flag=false;
	var type = $("#supplierType").val();
	 var isYougou=$("input[type='radio']:checked").val();
	 $("#isInputYougouWarehouse").val(isYougou);
	 var booksCode=chencksetOfBooksCode();
	 var traderCode=chenckbalanceTraderCode();
	 if(booksCode){
	    if(traderCode){
			 var bool=existSupplieName();
			 if(bool){
				if(type=="招商供应商"){
				  var tr=checkRate();
				  flag=tr;
				}else{
				  flag=true;
				}
			}
	    }
	}
	return flag;
}

//判断是否选择结算商家编码
function chenckbalanceTraderCode(){
    var traderCode=false;
	var balanceTraderCode= $("#balanceTraderCode").val();//结算商家编码
    if(balanceTraderCode==""){
        document.getElementById("balanceTraderCodeTip").className="onerror";
		$("#balanceTraderCodeTip").html("请选择结算商家名称!");
		traderCode=false;
    }else{
       var balanceTraderName=$("#balanceTraderCode").find("option:selected").text();//成本帐套名称
	   $("#balanceTraderName").val(balanceTraderName);
       document.getElementById("balanceTraderCodeTip").className="oncorrect";
	   $("#balanceTraderCodeTip").html("结算商家名称正确!");
	   traderCode=true;
    }
    return traderCode;
}
//判断是否选择成本帐套名称
function chencksetOfBooksCode(){
    var booksCode=false;
	var setOfBooksCode= $("#setOfBooksCode").val();//成本帐套编号
    if(setOfBooksCode==""){
        document.getElementById("setOfBooksCodeTip").className="onerror";
		 $("#setOfBooksCodeTip").html("请选择成本帐套名称!");
		 booksCode=false;
    }else{
       var setOfBooksName=$("#setOfBooksCode").find("option:selected").text();//成本帐套名称
	   $("#setOfBooksName").val(setOfBooksName);
       document.getElementById("setOfBooksCodeTip").className="oncorrect";
	   $("#setOfBooksCodeTip").html("成本帐套名称正确!");
	   booksCode=true;
    }
    return booksCode;
}

//判断供应商名称是否存在
function existSupplieName(){
  	var a=false;
	var supplierName = $("#supplier").val();
	if(supplierName!=""){
		$.ajax({
           type: "POST",
           async:false,
           url: "${BasePath}/supply/manage/supplier/existSupplieName.sc",
           data: {"supplier":supplierName},           
           success: function(data){           
              if(data=="success"){
 		 	    document.getElementById("supplierTip").className="onerror";
			    $("#supplierTip").html("供应商名称已经存在，请重新输入!");
			    a= false; 			 		
 		 	  }else{
 		 	  	 document.getElementById("supplierTip").className="oncorrect";
				 $("#supplierTip").html("供应商名称正确");
		    	 a= true;
 		 	  }             
           }
         }); 
	}
	return a;
}

function cusponSet(supplierType){
	var type = $("#supplierType").val();
	if(type=="招商供应商"){
		$("#disID").show();
		document.getElementById("couponsAllocationProportionTip").className="onshow";
		$("#couponsAllocationProportionTip").html("请选择优惠券分摊比例");
	}else{
		document.getElementById("couponsAllocationProportion").value='';
		$("#disID").hide();
	}
}

function checkRate(){
    var couponsAllocationProportion=$("#couponsAllocationProportion").val();
	var re_order=/^((\d|[123456789]\d)(\.\d+)?|100)$/;
	if(couponsAllocationProportion==""){
    	document.getElementById("couponsAllocationProportionTip").className="onshow";
		$("#couponsAllocationProportionTip").html("请选择优惠券分摊比例");
		return false;
	}else if(!re_order.test(couponsAllocationProportion)){
    	document.getElementById("couponsAllocationProportionTip").className="onerror";
		$("#couponsAllocationProportionTip").html("优惠券分摊比例必须为数字,在0-100之间");
		return false;
    }else{
    	document.getElementById("couponsAllocationProportionTip").className="oncorrect";
		$("#couponsAllocationProportionTip").html("优惠券分摊比例正确");
    	return true;
    }
    return false;
}
</script>
</head>
<body>
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
				<li class='curr'><span><a href="">基本信息</a></span></li>
				<li><span><a href="toAddFinance.sc?supplierId=${supplierId?default("")}">财务信息</a></span></li>
				<li><span><a href="toAddContact.sc?supplierId=${supplierId?default("")}">联系人列表</a></span></li>
			</ul>
		</div>
		<!--当前位置end--> 
		
		<!--主体start-->
		<div id="modify" class="modify">
			<input type="hidden" id="basePath" value="${BasePath}">
			<form action="addSupplier.sc" method="post" id="submitForm">
			<input type="hidden" name="balanceTraderName" id="balanceTraderName">
			 <input type="hidden" name="setOfBooksName" id="setOfBooksName">
				<input type="hidden" id="typeT" name="typeT" value="<#if supplier??>${supplier.supplierType?default("")}</#if>"/>
				<input type="hidden" id="isValidT" name="isValidT" value="<#if supplier??>${supplier.isValid?default("")}</#if>"/>
				<div class="divH12"><font color="red">提示：请先输入保存基本信息!</font></div>
				<table class="com_modi_table">
					<tr>
						<th> <span class="star">*</span>供应商名称AAA：</th>
						<td> <input type="text" id="supplier" name="supplier" maxlength="32" value="${(supplier.supplier)!''}" onblur=""/> <span id="supplierTip"></span></td>
					</tr>
					<tr>
						<th> <span class="star">*</span>供应商简称：</th>
						<td>
							<input type="text" id="simpleName" name="simpleName" maxlength="32" value=""/> <span id="simpleNameTip"></span>
						</td>
					</tr>
					<tr>
						<th> <span class="star">*</span>供应商地址：</th>
						<td> <input type="text" id="address" name="address" style="width:300px" maxlength="60" value="${(supplier.address)!''}" onblur=""/>  <span id="addressTip"></span> </td>
					</tr>
					<tr>
						<th> <span class="star">*</span>税率：</th>
						<td> <input type="text" id="taxRate" name="taxRate" style="width:50px" maxlength="8" value="" onblur=""/>%  <span id="taxRateTip"></span> </td>
					</tr>
					<tr>
						<th> <span class="star">*</span>供应商类型：</th>
						<td>
							<select id="supplierType" name="supplierType" onchange="cusponSet(this);">
								<option selected="selected" value="">请选择</option>
								<#if listSupplierType??>
									<#list listSupplierType as supplierType>
										<option value="${(supplierType.typeValue)!''}">${(supplierType.typeValue)!''}</option>
									</#list>
								</#if>
							</select> <span id="supplierTypeTip"></span>
						</td>
					</tr>
					<tr>
						<th> <span class="star">*</span>仓库类型：</th>
						<td>
							<input type="radio" name="isInputYougouWarehouse" id="isInputYougouWarehouse" value="1" checked />入优购仓库
                           <input type="radio" name="isInputYougouWarehouse" id="isInputYougouWarehouse"  value="0"/>不入优购仓库
						</td>
					</tr>
					<tr id="disID" style="display:none">
						<th> <span class="star">*</span>优惠券分摊比例：</th>
						<td> <input type="text" id="couponsAllocationProportion" name="couponsAllocationProportion" id="couponsAllocationProportion" style="width:50px" maxlength="8"  onblur="return checkRate();"/>%  <span id="couponsAllocationProportionTip"></span> </td>
					</tr>
					  <tr>
					<th>
					  <span class="star">*</span>成本帐套名称：</th>
                       <td>
                           <select id="setOfBooksCode" name="setOfBooksCode">
                           <#if costSetofBooksList??>
                            	<option value="">请选择成本帐套名称</option>
                             	<#list costSetofBooksList as item>
                               		<option value="${(item.setOfBooksCode)!''}">${(item.setOfBooksName)!''}</option>
                             	</#list>
                           </#if>
                           </select>
                           <span id="setOfBooksCodeTip"></span>
                       </td>
                   	</tr>
				<tr>
					<th>
					  	<span class="star">*</span>结算商家名称：</th>
                       	<td>
                           	<select id="balanceTraderCode" name="balanceTraderCode">
                            <#if traderMaintainList??>
	                            <option value="">请选择结算商家名称</option>
                             	<#list traderMaintainList as item>
                               		<option value="${(item.balanceTraderCode)!''}">${(item.balanceTraderName)!''}</option>
                             	</#list>
                           </#if>
                           </select>
                           <span id="balanceTraderCodeTip"></span>
                       </td>
                       </tr>
					<tr>
					<th></th>
						<td> 
							<input type="submit" value="保存" class="btn-save" onClick=""/>
							<input type="button" value="取消" class="btn-back" onClick="javascript:window.location='supplierlist.sc';"/>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>
</body>
</html>
