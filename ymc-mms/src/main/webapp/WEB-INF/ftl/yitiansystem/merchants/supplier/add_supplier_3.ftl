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

<title>优购网-招商供应商管理</title>
<script type="text/javascript">
var basePath = "${BasePath}";

function addBrandcatAuth() {
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
	
	document.submitForm.submit();
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

function gotoTab(flag) {
	var supplierId = $('#supplierId').val();
	if (supplierId != '') {
		if (flag == 2) {
			location.href="${BasePath}/yitiansystem/merchants/businessorder/to_add_supplier_user.sc?supplierId=" + supplierId;
		} else if (flag == 1) {
			location.href="${BasePath}/yitiansystem/merchants/businessorder/to_add_supplier.sc?supplierId=" + supplierId;
		}
	}
}
</script>
</head><body>

<div class="container"> 
	<!--工具栏start--><!--操作按钮start--> 
	<#--div class="toolbar">
		<div class="t-content"> 
		</div>
	</div-->
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
	    <ul class="step3 clearfix">
	        <a href="#" onclick="gotoTab(1)"><li>1.供应商基本信息</li></a>
	        <a href="#" onclick="gotoTab(2)"><li>2.账户信息</li></a>
	        <a href="#" onclick="gotoTab(3)"><li class="curr">3.品牌分类授权</li></a>
	    </ul>
	</div>
	<form action="${BasePath}/yitiansystem/merchants/businessorder/add_supplier_auth.sc" method="post" id="submitForm" name="submitForm">
		<input type="hidden" name="supplierId" id="supplierId" value="${supplierId}" />
		<table class="com_modi_table">
			<tr>
			 	<th><span class="star">*</span>品类授权：</th>
                <td>
                	<input type="hidden" name="bankNoHistory" id="bankNoHistory" value="" />
                	<input type="hidden" name="bankNameHidden" id="bankNameHidden"  />
                	<input type="hidden" name="bankNoHidden" id="bankNoHidden"  />
                	<input type="hidden" name="catNameHidden" id="catNameHidden" />
                    <span id="bank_span"></span>
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
              	<td>&nbsp;</td>
				<td>
					<input type="button" value="保存" class="btn-save" onClick="return addBrandcatAuth();"/>
				</td>
			</tr>
		</table>
	</form>
</div>
</div>
</div>
</body>
</html>