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
				<li> <span><a href="querysupplierfinacedetail.sc?id=${supplier.id}">银行账号</a></span> </li>
				<li class="curr"><span>基本信息</span></li>
				<li> <span><a href="querysuppliercontactdetail.sc?supplier=${supplier.id}">联系人列表</a></span> </li>
				<li> <span><a href="querysupplierhistorydetail.sc?supplier=${supplier.id}">更新历史</a></span> </li>
			</ul>
		</div>
		<!--当前位置end--> 
		
		<!--主体start-->
		<div id="modify" class="modify">
			<input type="hidden" id="basePath" value="${BasePath}">
				<table class="com_modi_table">
					<tr>
						<th>供应商名称：</th>
						<td>
						${supplier.supplier?default("")}
							
					</tr>
					<tr>
						<th> 供应商简称：</th>
						<td>
							${supplier.simpleName?default("")} </td>
					</tr>
					<tr>
						<th> 供应商地址：</th>
						<td>
						${supplier.address?default("")}</td>
					</tr>
					<tr>
						<th> 供应商类型：</th>
						<td>
							
							${supplier.supplierType?default("")}</td>
							
					</tr>
					<tr>
						<th> 供应商状态：</th>
						<td>
						<#if supplier?? && supplier.isValid?? && supplier.isValid==1>启用<#elseif supplier?? && supplier.isValid?? && supplier.isValid==2>新建<#elseif supplier?? && supplier.isValid?? && supplier.isValid==-1>停用<#else>&nbsp;</#if>	
						</td>
					</tr>
					
					<tr>
						<th> 仓库类型：</th>
						<td>
							<#if supplier.isInputYougouWarehouse?? && supplier.isInputYougouWarehouse==1 >入优购仓库
							<#elseif supplier.isInputYougouWarehouse?? && supplier.isInputYougouWarehouse==0 >不入优购仓库
							</#if> 
						</td>
						
						
					</tr>
					<tr>
						<th> 成本帐套名称：</th>
						<td>
							<#if supplier?? && supplier.setOfBooksName??>${supplier.setOfBooksName!''}</#if>
						</td>
						
						
					</tr>
					<tr>
						<th> 结算商家名称：</th>
						<td>
							<#if supplier?? && supplier.balanceTraderName??>${supplier.balanceTraderName!''}</#if>
						</td>
						
						
					</tr>
					<tr>
						<th> 验收差异处理方式：</th>
						<td>
							<#if supplier.shipmentType?? && supplier.shipmentType==1 >销退
							<#elseif supplier.shipmentType?? && supplier.shipmentType==0 >验退
							</#if>
						</td>
						
					</tr>
					<tr>
						<th> 备注：</th>
						<td>${supplier.remark?default("")} </td>
					</tr>
					
				</table>
		</div>
	</div>
</div>
</body>
</html>
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator.js"></script>