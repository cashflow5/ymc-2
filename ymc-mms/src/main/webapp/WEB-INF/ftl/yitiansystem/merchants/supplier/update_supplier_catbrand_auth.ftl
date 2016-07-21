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
div.content_wrap {
    width: 580px;
}
</style>


<title>优购网-招商供应商管理</title>
<script type="text/javascript">
var basePath = "${BasePath}";

function addBrandcatAuth() {
	var bankNo = $("#bankNoHistory").val();//品牌BrandNo
	var catStrs = [];
	var brankStrs = [];
	var treeObj = $.fn.zTree.getZTreeObj("ztree");
	if (treeObj != null) {
		var nodes = treeObj.getCheckedNodes();
		for (var i=0, l=nodes.length; i < l; i++) {
			var node = nodes[i];
			if (node.lev == 3) { //取第三级分类
				catStrs[catStrs.length] = node.id;
			}
			if(node.lev == 1) { //保存品牌
				var nodeId = (node.id.split(";"))[0]
				var flag = true;
				for( var j = 0, ll=brankStrs.length; j<ll ; j++){
					if(brankStrs[j]==nodeId){
						flag = false;
					}
				}
				if(flag){
					brankStrs[brankStrs.length] = nodeId;
				}
			}
		}
	}
	$("#catNameHidden").val(catStrs.join('_'));

	$('#bankNoHidden').val(brankStrs.join(';'));
	
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

$(document).ready(function(){
	//加载品牌显示框
	var brandStrs = $('#bankNameHidden').val();
	var brandNos = [];
	if (brandStrs != null || brandStrs != '' || brandStrs.length > 0) {
		var str = brandStrs.split('_');
		for(var i = 0; i < str.length; i++) {
			var array = str[i].split(';');
			if (array[0] == '') continue;
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
			<!--ul class="tab">
			  <li class='curr'> <span><a href="">添加商家信息</a></span> </li>
			</ul-->
		</div>
		<!--当前位置end--> 
<!--主体start-->
<div id="modify" class="modify">
	<form action="${BasePath}/yitiansystem/merchants/businessorder/add_supplier_auth.sc" method="post" id="submitForm" name="submitForm">
		<input type="hidden" name="supplierId" id="supplierId" value="${supplierId}" />
		<table class="com_modi_table">
			<tr>
			 	<th><span class="star"></span>供应商：</th>
                <td>
                	<label>${supplier.supplier}(<b>${supplier.supplierCode}</b>)</label>
            	</td>
            </tr>
			<tr>
			 	<th><span class="star">*</span>品类授权：</th>
                <td>
                	<input type="hidden" name="bankNoHistory" id="bankNoHistory" value="" />
                	<input type="hidden" name="bankNameHidden" id="bankNameHidden" value="<#if brandStrs??>${brandStrs!''}</#if>" />
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