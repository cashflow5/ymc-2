<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/js/yitiansystem/merchants/ztree/css/zTreeStyle/zTreeStyle.css"/>
<script type="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
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
	        	<b class="ico_btn back"></b>
	        	<span class="btn_txt">返回</span>
	        	<span class="btn_r"></span>
        	</div> 
		</div>
	</div>
	<div class="list_content">
		<div class="top clearfix">
		</div>
 <div class="modify"> 
<form action="${BasePath}/yitiansystem/merchants/businessorder/update_merchantsBankAndCat.sc" name="queryForm" id="queryForm" method="post"> 
	<input type="hidden" name="id" id="id" value="<#if supplierSp??&&supplierSp.id??>${supplierSp.id!''}</#if>" />
	<input type="hidden" name="supplierCode" id="supplierCode" value="<#if supplierSp??&&supplierSp.supplierCode??>${supplierSp.supplierCode!''}</#if>" />
	<div style="margin-top:15px;">
		<div style="width:1145px;height:30px;background:#CCFFFF;"><span><b>商家信息</b></span></div>
        <hr>
		<table cellpadding="0" cellspacing="0" class="list_table">
			<tr>
				<td>
					<label><span style="color:red;margin-left:54px;">&nbsp;*</span>登录账号：</label>
					<#if merchantUser??&&merchantUser.loginName??>${merchantUser.loginName!''}</#if>
				</td>
			</tr>
		</table>
	</div>
	<div style="margin-top:20px;">
		<div style="width:1145px;height:30px;background:#CCFFFF;"><span><b>品类权限</b></span></div>
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
			<tr>
				<td>
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
                           	<input type="radio" id="_radio_${item_index}" name="isInputYougouWarehouse" value="${item.ordinal()}" disabled="disabled" <#if supplierSp?? && supplierSp.isInputYougouWarehouse?default(-1) == item.ordinal()>checked="checked"</#if>/><label for="_radio_${item_index}">${item.description}</label>
                           	</#list>
                       </td>
                       </tr>
                       <tr>
                        <td>
                          <label> <span style="color:red;margin-left:35px;">&nbsp;*</span>使用优购WMS：</label>
                           	<input type="radio" name="isUseYougouWms" value="1" disabled="disabled" <#if supplierSp?? && supplierSp.isUseYougouWms == 1 >checked</#if>  >是</label>
                           	<input type="radio" name="isUseYougouWms" value="0" disabled="disabled" <#if supplierSp?? && supplierSp.isUseYougouWms == 0 >checked</#if> >否</label>
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
                  <input type="button" value="保存" onclick="return updateMerchantsBankAndCat();"  class="yt-seach-btn">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
function updateMerchantsBankAndCat(){
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
	
	var bankName = $("#bankName").html();//品牌名称
	var catName = $("#catName").html();//分类名称

	if(bankNo=="" ){
		$("#bankNameError").html("商品品牌不能为空!");
		return false;
	}else{
	   $("#bankNameError").html("");
	}
	if(catStrs.length <= 0){
		$("#bankNameError").html("商品分类不能为空!");
		return false;
	}else{
	   $("#bankNameError").html("");
	}
	document.queryForm.submit();
}



/**
 * 对目标字符串进行格式化
 * @author huang.wq
 * @param {string} source 目标字符串
 * @param {Object|string...} opts 提供相应数据的对象或多个字符串
 * @remark
 * opts参数为“Object”时，替换目标字符串中的{#property name}部分。<br>
 * opts为“string...”时，替换目标字符串中的{#0}、{#1}...部分。	
 * @shortcut format
 * @returns {string} 格式化后的字符串
 * 例：
    (function(arg0, arg1){ 
		alert(formatString(arg0, arg1)); 
	})('{#0}-{#1}-{#2}',["2011年","5月","1日"]);
	(function(arg0, arg1){ 
		alert(formatString(arg0, arg1)); 
	})('{#year}-{#month}-{#day}', {year: 2011, month: 5, day: 1});   
 */
function formatString(source, opts) {
    source = String(source);
    var data = Array.prototype.slice.call(arguments,1), toString = Object.prototype.toString;
    if(data.length){
	    data = data.length == 1 ? 
	    	/* ie 下 Object.prototype.toString.call(null) == '[object Object]' */
	    	(opts !== null && (/\[object Array\]|\[object Object\]/.test(toString.call(opts))) ? opts : data) 
	    	: data;
    	return source.replace(/\{#(.+?)\}/g, function (match, key){
	    	var replacer = data[key];
	    	// chrome 下 typeof /a/ == 'function'
	    	if('[object Function]' == toString.call(replacer)){
	    		replacer = replacer(key);
	    	}
	    	return ('undefined' == typeof replacer ? '' : replacer);
    	});
    }
    return source;
};
</script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
