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
	var dispaly = false;
	var isEnabled = 0;
	//加载分类Tree
	<#if treeModes??>
		<#list treeModes as item>
			isEnabled = ${item.isEnabled!''};
			if (isEnabled > 0) {
				dispaly = true;
			} else {
				dispaly = false;
			}
//			console.info("enable="+isEnabled+":struct="+'${item.structName!""}'+":dispaly="+dispaly);
			zNodes[zNodes.length] = {
				id: '${item.structName!""}', 
				pId: '${item.parentId!''}', 
				name: '${item.catName!''}', 
				lev: '${item.catLeave!''}',
				checked: dispaly,
				open: dispaly,
			};
		</#list>
	</#if>
	
	$.fn.zTree.init($("#ztree"), setting2, zNodes);
	if (zNodes.length > 0)
	$("#supplier_category_brand_tree_tr").show();
});
</script>

</head>
<body>
<div class="container">
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr">
				  <span><a href="#" class="btn-onselc">修改店铺</a></span>
				</li>
				
			</ul>
		</div>
 		<div class="modify"> 
     	<form action="${BasePath}/yitiansystem/merchants/businessorder/save_merchant_reject_address.sc" name="queryForm" id="queryForm" method="post"  enctype="multipart/form-data">
        	<input type="hidden" id="shopId" name="shopId" value="${(shopVo.shopId)!''}"/>
        	<input type="hidden" id="merchantCode" name="merchantCode" value="${(shopVo.merchantCode)!''}"/>
		<table cellpadding="0" cellspacing="0" class="list_table">
				<tr>
					<td style="width:120px;margin-left:150px;text-align:right">
                     	<span style="color:red;">&nbsp;*</span>绑定商家：
                    </td>
                    <td>${(supplier.supplier)!''}</td>
                </tr>
				<tr>
                   	<td style="width:120px;margin-left:150px;text-align:right">
                   		<span style="color:red;">&nbsp;*</span>经营品类：
                   	</td>
	            	<td>
    	        		<!-- <#-- 分类树结构展示 --> -->
        	    		<div class="content_wrap"><ul id="ztree" class="ztree"></ul></div><span style="color:red;" id="bankNameError"></span>
            			<input type="hidden" id="supplier_cat_brand_tree_hidden" onclick="init_supplier_cat_tree();" />
            			<span><label style="color:#CCCCCC;">绑定商品分类授权后，则该旗舰店只能已授权的类目到旗舰店内。</label></span>
            		</td>
            	</tr>
                <tr>
                	<td style="text-align:right"></td>
                	<td>
                    	<input id="btn" type="button" value="保存" class="yt-seach-btn" onclick="return modifyShop();">&nbsp;
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

function modifyShop(){
	var shopId = $('#shopId').val();
	var merchantCode = $('#merchantCode').val();
	var bankNo = $("#bankNoHistory").val();//品牌BrandNo
	var catStrs = [];
	var struts = "";
	var treeObj = $.fn.zTree.getZTreeObj("ztree");
	if (treeObj != null) {
		var nodes = treeObj.getCheckedNodes();
		for (var i=0, l=nodes.length; i < l; i++) {
			var node = nodes[i];
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
	$("#catNameHidden").val(catStrs.join('_'));
	
	if(bankNo=="" ){
		$("#bankNameError").html("请绑定品牌!");
		return false;
	}else{
		$("#bankNameError").html("");
	}
	if(struts.length <= 0 ){
		$("#bankNameError").html("请选择经营类目!");
		return false;
	}else{
		$("#bankNameError").html("");
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
	var exDialog=ygdg.dialog({
		content:"正在执行中...",
		title:'提示',
		lock:true
	});
	
   $.ajax({
		  type : "POST",
		  url : "${BasePath}/merchant/shop/decorate/saveFssShopRule.sc",
		  data : {
		    "shopId":shopId,
		    "merchantCode":merchantCode,
		    "brandIds":bankNo,
		    "brandNames":struts,
		    "shopStatus":'${(shopVo.shopStatus)!'0'}'
		  },
		  dataType : "json",
		  async:false,
		  beforeSend: function(XMLHttpRequest){
			submitform.attr('state', 'running');
		  },
		  success : function(data) {
			exDialog.close();
		    if(data.success==true){
				 alert("修改店铺成功！");
				 dg.curWin.toReload();
				 closewindow();
				 return true;
		    }else{
		         flag=false;
				 alert(data.msg);
				 return false;
		    }
		  },
		  complete: function(XMLHttpRequest, textStatus){
			submitform.attr('state', 'waiting');
//			location.reload();
		  },
		  error: function(XMLHttpRequest, textStatus, errorThrown){
			exDialog.close();
			art.dialog.alert('修改店铺失败:' + XMLHttpRequest.responseText);
		  }
	});
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