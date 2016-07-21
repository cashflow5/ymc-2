<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#include "../../merchants/merchants-include.ftl">
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/merchants/ztree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/merchants/ztree/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/merchants/shop_cat_tree.js?lyx20151008"></script>
<link rel="stylesheet" type="text/css" href="${BasePath}/js/yitiansystem/merchants/ztree/css/zTreeStyle/zTreeStyle.css"/>
<title>优购商城--商家后台</title>
<script type="text/javascript">
var setting2 = {
		check : {
			enable : true
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		view : {
			showIcon : false,
			expandSpeed : "fast"
		}
	};
$(document).ready(function(){
/**	//加载品牌显示框
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
**/	
	var zNodes = [];
	//加载分类Tree
	<#if treeModes??>
		<#list treeModes as item>
			zNodes[zNodes.length] = {
				id: '${item.structName!""}',
				pId: '${item.parentId!''}',
				name: '${item.catName!''}',
				title: '${item.catName!''}',
				lev: '${item.catLeave!''}',
				checked: false
			};
		</#list>
	</#if>
//	console.info(zNodes);
	$.fn.zTree.init($("#ztree"), setting2, zNodes);
});
</script>

</head>
<body>
<div class="container">
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr">
				  <span><a href="#" class="btn-onselc">创建店铺</a></span>
				</li>
				
			</ul>
		</div>
 		<div class="modify"> 
     	<form action="${BasePath}/yitiansystem/merchants/businessorder/save_merchant_reject_address.sc" name="queryForm" id="queryForm" method="post"  enctype="multipart/form-data">
            <table cellpadding="0" cellspacing="0" id="firstStep" class="list_table">
				<tr>
					<td style="width:120px;margin-left:150px;text-align:right">
                     	<span style="color:red;">&nbsp;*</span>绑定商家：
                    </td>
                    <td>
                        <input type="text" name="supplierName" length="20" readonly="readonly" id="supplierName" value="${(supplierName)!''}"/>
                        <input type="hidden"  name="supplierCode" id="supplierCode" value="${(supplierCode)!''}"/>
                        <input type="hidden"  name="supplierId" id="supplierId" value="${(supplierId)!''}"/>
                       	<input type="button" value="......" onclick="addSupplierName();"/>
                       	&nbsp;&nbsp;<span style="color:red;" id="supplierNameError"></span>
                    </td>
                </tr>
<#--                <tr>
                   	<td style="width:120px;margin-left:150px;text-align:right">
                   		<span style="color:red;">&nbsp;*</span>绑定品牌：
                   	</td>
                	<td>
                		<input type="hidden" name="bankNoHistory" id="bankNoHistory" value="" />
                		<input type="hidden" name="bankNameHidden" id="bankNameHidden" value="${brandStrs!''}" />
                		<input type="hidden" name="bankNoHidden" id="bankNoHidden"  />
                		<input type="hidden" name="catNameHidden" id="catNameHidden" />
                		<span id="bank_span"></span>
                		<span style="color:red;" id="bankNameError"></span>
            		</td>
				</tr>
-->				<tr>
                   	<td style="width:120px;margin-left:150px;text-align:right">
                   		<span style="color:red;">&nbsp;*</span>经营品类：
                   	</td>
	            	<td>
    	        		<#-- 分类树结构展示 -->
        	    		<div class="content_wrap"><ul id="ztree" class="ztree"></ul></div><span style="color:red;" id="bankNameError"></span>
            			<#--<input type="hidden" id="supplier_cat_brand_tree_hidden" onclick="init_supplier_cat_tree();" />-->
            			<span><label style="color:#CCCCCC;">绑定商品分类授权后，则该旗舰店只能已授权的类目到旗舰店内。</label></span>
            		</td>
            	</tr>
<#--				<tr>
                   	<td style="width:120px;margin-left:150px;text-align:right">
                   		是否通过：
                   	</td>
	            	<td>
	            		<input type="checkbox" id="isEnable" name="isEnable" class="checkbox" value="2"/>通过
	            		<label style="color:#CCCCCC;">点击通过后，店铺将直接流入到商家中心后台。</label>
            		</td>
            	</tr>
-->            	
                <tr>
                	<td style="text-align:right"></td>
                	<td>
                		<input id="btn" type="button" value="下一步" class="yt-seach-btn" onclick="return nextStep();">&nbsp;
<!--                    	<input id="btn" type="button" value="创建" class="yt-seach-btn" onclick="return createShop();">&nbsp;-->
<!--						<input id="btn" type="button" value="重置" class="yt-seach-btn" onclick="resertInput();">-->
                    </td>
                </tr>
			</table>
			
			<table cellpadding="0" cellspacing="0" id="nextStep" class="list_table" style="display: none;">
				<tr>
					<td style="width:120px;margin-left:150px;text-align:right">
                       	<span style="color:red;">&nbsp;*</span>商家名称：
                    </td>
                    <td id="supplierNameTd">
                    </td>
				</tr>
				<tr>
                    <td style="width:120px;margin-left:150px;text-align:right">
                       	<span style="color:red;">&nbsp;*</span>店铺名称：
                    </td>
                    <td>
                     	<input type="text" name="shopName" id="shopName"/>
                        &nbsp;&nbsp;<span style="color:red;" id="shopNameError"></span><br/>
                        <label style="color:#CCCCCC;">示例：XXX旗舰店。</label>
                    </td>
				</tr>
                <tr>
                   	<td style="width:120px;margin-left:150px;text-align:right">
                       <span style="color:red;">&nbsp;*</span>店铺域名：
                   	</td>
                   	<td>
                    	http://mall.yougou.com/m-<input type="text" style="width:120px;height:12px;" id="shopUrl" name="shopUrl" value="" />.shtml
                    	<span style="color:red;" id="shopUrlError"></span><br />
						<label style="color:#CCCCCC;">内容仅包含数字、小写字母和“-”符号，并不能以“-”开头和结尾。控制在1-20个字符内。</label>
                   	</td>
				</tr>
				<tr>
                   	<td style="width:120px;margin-left:150px;text-align:right">
                   		是否通过：
                   	</td>
	            	<td>
	            		<input type="checkbox" id="isEnable" name="isEnable" class="checkbox" value="2"/>通过
	            		<label style="color:#CCCCCC;">点击通过后，店铺将直接流入到商家中心后台。</label>
            		</td>
            	</tr>
            	<tr>
                	<td style="text-align:right"></td>
                	<td>
                		<input id="btn" type="button" value="创建" class="yt-seach-btn" onclick="return createShop();">
                		&nbsp;&nbsp;
                		<a href="javascript:void(0);" onclick="preStep();">返回上一步</a>
                    </td>
                </tr>
			</table>
       	</form>
    </div>
</div>
</body>
</html>
<script type="text/javascript">
var basePath = "${BasePath}";
var flag=false;
$(document).ready(function() {
$("#createDateTimeBegin").calendar({maxDate:'#createDateTimeEnd'});
$("#createDateTimeEnd").calendar({minDate:'#createDateTimeBegin'});
});
//添加商家名称
function addSupplierName(){
  	openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_addSupplierName_list.sc",600,500,"商家查询");
}
function preStep(){
	$("#nextStep").hide();
	$("#firstStep").show();
}

function nextStep(){
	var merchantName = $('#supplierName').val();
	if(merchantName == ""){
		$("#supplierNameError").html("请选择商家!");
		return false;
	} else {
		$("#supplierNameError").html("");
	}
	var bankNo = $("#bankNoHistory").val();//品牌BrandNo
	var catStrs = [];
	var struts = "";
	var treeObj = $.fn.zTree.getZTreeObj("ztree");
	if (treeObj != null) {
		var nodes = treeObj.getCheckedNodes();
		for (var i=0, l=nodes.length; i < l; i++) {
			var node = nodes[i];
			//alert("node.lev="+node.lev+"==node.level="+node.level+"==node.id="+node.id+"parentNode="+node.getParentNode());
			catStrs[catStrs.length] = node.id;
			if(node.level==0){
				struts += node.id+",";
			}else{
				var parentNode = node.getParentNode();
				if(parentNode&&
						parentNode.level==0&&
						parentNode.getCheckStatus().half){
					struts += node.id+",";
				}
			}
		}
	}
	//alert("===struts=="+struts);
	$("#catNameHidden").val(catStrs.join('_'));
	if(struts.length <= 0 ){
		$("#bankNameError").html("请选择经营类目!");
		return false;
	}else{
		$("#bankNameError").html("");
	}
	var isEnable = 0;
	if($('#isEnable').is(':checked')){
		isEnable = 2;
	}
   	var submitform = $('#querFrom');
	if (submitform.attr('state') == 'running') {
		return false;
	}
	$("#firstStep").hide();
	$("#supplierNameTd").html($("#supplierName").val());
	$("#nextStep").show();
}
function createShop(){
	var merchantCode = $('#supplierCode').val();
	var merchantName = $('#supplierName').val();
	var shopName = $.trim($('#shopName').val());
	var shopUrl = $.trim($('#shopUrl').val());
	
	if(merchantName == ""){
		$("#supplierNameError").html("请选择商家!");
		return false;
	} else {
		$("#supplierNameError").html("");
	}

	if(shopName == ""){
		$("#shopNameError").html("请输入店铺名称!");
		return false;
	}else if(checkShopName(shopName)){
		$("#shopNameError").html("店铺名称已存在!");
		return false;
	}else{
		$("#shopNameError").html("");
	}
	if(shopUrl == ""){
		$("#shopUrlError").html("请输入店铺域名!");
		return false;
	}else if(checkShopUrl(shopUrl)){
		$("#shopUrlError").html("店铺域名已存在!");
		return false;
	}else {
		$("#shopUrlError").html("");
	}

	var struts = "";
	var treeObj = $.fn.zTree.getZTreeObj("ztree");
	if (treeObj != null) {
		var nodes = treeObj.getCheckedNodes();
		for (var i=0, l=nodes.length; i < l; i++) {
			var node = nodes[i];
			//catStrs[catStrs.length] = node.id;
			if(node.level==0){
				struts += node.id+",";
			}else{
				var parentNode = node.getParentNode();
				if(parentNode&&
						parentNode.level==0&&
						parentNode.getCheckStatus().half){
					struts += node.id+",";
				}
			}
		}
	}
	var bankNo = $("#bankNoHistory").val();//品牌BrandNo
	var isEnable = 0;
	if($('#isEnable').is(':checked')){
		isEnable = 2;
	}
   	var submitform = $('#querFrom');
	if (submitform.attr('state') == 'running') {
		return false;
	}
	if(flag){
	    alert("请不要重复提交！");
	    return false;
	}else{
		flag=true;
	}
	//alert("struts=="+struts);
   $.ajax({
		  type : "POST",
		  url : "${BasePath}/merchant/shop/decorate/saveFssShopAndRule.sc",
		  data : {
		    "merchantCode":merchantCode,
		    "shopName":shopName,
		    "shopURL":shopUrl,
		    "brandIds":bankNo,
		    "brandNames":struts,
		    "shopStatus":isEnable
		  },
		  dataType : "json",
		  async:false,
		  beforeSend: function(XMLHttpRequest){
			submitform.attr('state', 'running');
		  },
		  success : function(data) {
		    if(data.success==true){
				 alert("创建店铺成功！");
				 dg.curWin.toReload();
				 closewindow();
				 return true;
		    }else{
		         flag=false;
				 alert(data.msg);
//				 closewindow();
				 return reslut;
		    }
		  },
		  complete: function(XMLHttpRequest, textStatus){
			submitform.attr('state', 'waiting');
//			parent.location.reload();
		  },
		  error: function(XMLHttpRequest, textStatus, errorThrown){
			art.dialog.alert('创建店铺失败:' + XMLHttpRequest.responseText);
		  }
	});
}
function parentMethod(){
	var supplierId = $("#supplierId").val();
	var supplierCode = $("#supplierCode").val();
	var supplierName = $("#supplierName").val();
	
	if(supplierId != null){
		window.location = "${BasePath}/merchant/shop/decorate/toCreateFssShop.sc?supplierId="+supplierId+"&supplierCode="+supplierCode+"&supplierName="+supplierName;
	}
}

function checkShopName(shopName){
var result=true;
    $.ajax({
	type: "POST",
	url : "${BasePath}/merchant/shop/decorate/checkShopName.sc?shopName="+shopName,
	dataType : "json",
	async:false,
	success: function(data){
	  if(false==data.success){
	    result=false;
	  }
	}
	});
	return result;
}
function checkShopUrl(shopUrl){
var result=true;
    $.ajax({
	type: "POST",
	url : "${BasePath}/merchant/shop/decorate/checkShopUrl.sc?shopUrl="+shopUrl,
	dataType : "json",
	async:false,
	success: function(data){
	    if(false==data.success){ 
			result=false;
		}
	}
	});
	return result;
}

</script>