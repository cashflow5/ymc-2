<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,优购网,B网络营销系统,商品管理" />
<meta name="Description" content=" , ,B网络营销系统-商品管理" />
<script type="text/javascript" src="../js/common.js"></script>
<script type="text/javascript" src="../js/jquery-1.3.2.min.js" ></script>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<#include "../../../yitiansystem/yitiansystem-include.ftl">
<#--<script type="text/javascript" src="${BasePath}/js/supply/addSupplierBase.js" ></script>-->
<title>B网络营销系统-采购管理-优购网</title>
<script type="text/javascript">

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
	},{
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
	},{
		name : 'isValid',
		allownull : false,
		regExp : /^\S+$/,
		defaultMsg : '请选择供应商状态',
		focusMsg : '请选择供应商状态',
		errorMsg : '请选择供应商状态',
		rightMsg : '供应商状态输入正确',
		msgTip : 'isValidTip'
	},{
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
	var type = $("#supplierType").val();
	 var booksCode=chencksetOfBooksCode();
	 var traderCode=chenckbalanceTraderCode();
	 if(booksCode){
	    if(traderCode){
			if(type=="招商供应商"){
			  var tr=checkRate();
			  return tr;
			}else{
			  return true;
			}
	   }
    }
    return false;
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
function cusponSet(supplierType){
	var type = $("#supplierType").val();
	
	if(type=="招商供应商"){
		$("#disID").show();
		document.getElementById("couponsAllocationProportionTip").className="onerror";
		$("#couponsAllocationProportionTip").html("请选择优惠券分摊比例");
	}else{
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

$(document).ready(function(){
 	var supplierType = '${supplier.supplierType}';
 	if(supplierType=="招商供应商"){
 		$("#disID").show();
		checkRate();
 	}
});

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
				<li class="curr"><span>修改基本信息</span></li>
				<li> <span><a href="toUpdateSupplierFinance.sc?id=${supplier.id}">财务信息</a></span> </li>
				<li> <span><a href="toSupplierContact.sc?supplier=${supplier.id}">联系人列表</a></span> </li>
				<li> <span><a href="querysupplierhistorydetail.sc?supplier=${supplier.id}&type=1">更新历史</a></span> </li>
			</ul>
		</div>
		<!--当前位置end--> 
		
		<!--主体start-->
		<div id="modify" class="modify">
			<input type="hidden" id="basePath" value="${BasePath}">
			<form action="updateSupplier.sc" method="post" id="submitForm" name="submitForm">
			<input type="hidden" name="setOfBooksName" id="setOfBooksName" value="<#if supplier??&&supplier.setOfBooksName??>${supplier.setOfBooksName!''}</#if>">
			<input type="hidden" name="balanceTraderName" id="balanceTraderName" value="<#if supplier??&&supplier.balanceTraderName??>${supplier.balanceTraderName!''}</#if>">
				<input type="hidden" name="id" value="${supplier.id}"/>
				<input type="hidden" id="supplierV" name="supplierV" value="${supplier.isValid?default("")}"/>
				<input type="hidden" id="supplierT" name="supplierT" value="${supplier.supplierType?default("")}"/>
				<table class="com_modi_table">
					<tr>
						<th> <span class="star">*</span>供应商名称：</th>
						<td>
							<input type="text" name="supplier" style="width:300px" maxlength="32" value="${supplier.supplier?default("")}" onblur=""/>
							<span id="supplierTip"></span> </td>
					</tr>
					<tr>
						<th> <span class="star">*</span>供应商简称：</th>
						<td>
							<input type="text" id="simpleName" style="width:300px" name="simpleName" maxlength="32" value="${supplier.simpleName?default("")}"/>
							<span id="simpleNameTip"></span> </td>
					</tr>
					<tr>
						<th> <span class="star">*</span>供应商地址：</th>
						<td>
							<input type="text" name="address" style="width:300px" maxlength="60" value="${supplier.address?default("")}" onblur=""/>
							<span id="addressTip"></span> </td>
					</tr>
					<tr>
						<th> <span class="star">*</span>税率：</th>
						<td> <input type="text" id="taxRate" name="taxRate" style="width:50px" maxlength="8" value="${supplier.taxRate?default("")}" onblur=""/>%  <span id="taxRateTip"></span> </td>
						
					</tr>
					<tr>
					<th>
					  <span class="star">*</span>成本帐套名称：</th>
                       <td>
                           <select id="setOfBooksCode" name="setOfBooksCode">
                           <#if costSetofBooksList??>
                            <option value="">请选择成本帐套名称</option>
                             <#list costSetofBooksList as item>
                               <option value="${item.setOfBooksCode!''}" <#if supplier??&&supplier.setOfBooksCode??&&supplier.setOfBooksCode==item.setOfBooksCode>selected</#if>>${item.setOfBooksName!''}</option>
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
                               <option value="${item.balanceTraderCode!''}" <#if supplier??&&supplier.balanceTraderCode??&&supplier.balanceTraderCode==item.balanceTraderCode>selected</#if>>${item.balanceTraderName!''}</option>
                             </#list>
                           </#if>
                           </select>
                           <span id="balanceTraderCodeTip"></span>
                       </td>
                       </tr>
					
					<tr>
						<th> <span class="star">*</span>供应商类型：</th>
						<td>
							<select id="supplierType" name="supplierType" onchange="cusponSet(this);">
								<option value="">请选择</option>
								<#if listSupplierType??> <#list listSupplierType as supplierType> <option value="${supplierType.typeValue}" <#if supplier.supplierType?? && supplier.supplierType==supplierType.typeValue>selected="selected"</#if>>${supplierType.typeValue?default("")}
								
								</option>
								</#list> </#if>
							</select>
							<span id="supplierTypeTip"></span> </td>
					</tr>
					<tr>
						<th> <span class="star">*</span>仓库类型：</th>
						<td>
							<input type="radio" name="isInputYougouWarehouse" id="isInputYougouWarehouse" value="1" <#if supplier.isInputYougouWarehouse?? && supplier.isInputYougouWarehouse==1 >checked</#if> />入优购仓库
                           <input type="radio" name="isInputYougouWarehouse" id="isInputYougouWarehouse"  value="0" <#if supplier.isInputYougouWarehouse?? && supplier.isInputYougouWarehouse==0 >checked</#if>/>不入优购仓库
						</td>
						
						
					</tr>
					
					<tr id="disID" style="display:none">
						<th> <span class="star">*</span>优惠券分摊比例：</th>
						<td> <input type="text" id="couponsAllocationProportion" name="couponsAllocationProportion" value="<#if supplier.couponsAllocationProportion?? && supplier.couponsAllocationProportion==0 >"";<#else>${supplier.couponsAllocationProportion?default("&nbsp;")}</#if>" style="width:50px" maxlength="8"  onblur="return checkRate();"/>%  <span id="couponsAllocationProportionTip"></span> </td>
					</tr>
					
					
					
					
					<tr>
						<th> <span class="star">*</span>供应商状态：</th>
						<td>
						
							<select id="isValid" name="isValid" value="" >
						<option value="" selected="selected">请选择</option>
						
						<option value="1" <#if supplier.isValid?? && supplier.isValid==1>selected="selected"</#if>>启用
								
						</option>
						<option value="2" <#if supplier.isValid?? && supplier.isValid==2>selected="selected"</#if>>新建
								
						</option>
						<option value="-1" <#if supplier.isValid?? && supplier.isValid==-1>selected="selected"</#if>>停用
								
						</option>
					</select>
							<span id="isValidTip"></span> </td>
					</tr>
					
					<tr>
						<th> <span class="star">*</span>备注：</span> </th>
						<td>
							<textarea cols="50" rows="3" name="remark" class="" maxlength="100" value="">${supplier.remark?default("")}</textarea>
							(限100个字符) </td>
					</tr>
					<tr>
						<th></th>
						<td>
							<input type="submit" value="保存" class="btn-save" onClick=""/>
							<input type="button" value="取消" class="btn-back" onClick="javascript:window.location='toManage.sc'"/>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>
</body>
</html>
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator.js"></script>