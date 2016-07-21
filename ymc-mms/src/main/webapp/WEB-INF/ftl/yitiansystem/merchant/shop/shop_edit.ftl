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
			<table  cellpadding="0" cellspacing="0" class="list_table">
				<tr>
					<td style="width:120px;margin-left:150px;text-align:right">
                     	<span style="color:red;">&nbsp;*</span>绑定商家：
                    </td>
                    <td>${(supplier.supplier)!''}</td>
                </tr>
				<tr>
                    <td style="width:120px;margin-left:150px;text-align:right">
                       	<span style="color:red;">&nbsp;*</span>店铺名称：
                    </td>
                    <td>
                     	<input type="text" name="shopName" id="shopName" value="${(shopVo.shopName)!''}"/>
                        &nbsp;&nbsp;<span style="color:red;" id="shopNameError"></span><br/>
                        <label style="color:#CCCCCC;">示例：XXX旗舰店。</label>
                    </td>
				</tr>
                <tr>
                   	<td style="width:120px;margin-left:150px;text-align:right">
                       <span style="color:red;">&nbsp;*</span>店铺域名：
                   	</td>
                   	<td>
                    	http://mall.yougou.com/m-<input type="text" style="width:120px;height:12px;" id="shopUrl" name="shopUrl" value="${(shopVo.shopURL)!''}" />.shtml
                    	<span style="color:red;" id="shopUrlError"></span><br />
						<label style="color:#CCCCCC;">内容仅包含数字、小写字母和“-”符号，并不能以“-”开头和结尾。控制在1-20个字符内。</label></span>
                   	</td>
				</tr>
				<#--<tr>
                   	<td style="width:120px;margin-left:150px;text-align:right">
                   		是否通过：
                   	</td>
	            	<td>
	            		<input type="checkbox" id="isEnable" name="isEnable" class="checkbox" value="2"/>通过
	            		<label style="color:#CCCCCC;">点击通过后，店铺将直接流入到商家中心后台。</label>
            		</td>
            	</tr>-->
            	<tr>
                	<td style="text-align:right"></td>
                	<td>
                		<input id="btn" type="button" value="保存" class="yt-seach-btn" onclick="return modifyShop();">
                		&nbsp;&nbsp;&nbsp;
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
	var shopName = $.trim($('#shopName').val());
	var shopUrl = $.trim($('#shopUrl').val());
    if(shopName == ""){
		$("#shopNameError").html("请输入店铺名称!");
		return false;
	}else if("${(shopVo.shopName)!''}"!=shopName&&checkShopName(shopName)){
		$("#shopNameError").html("店铺名称已存在!");
		return false;
	}else{
		$("#shopNameError").html("");
	}
	if(shopUrl == ""){
		$("#shopUrlError").html("请输入店铺域名!");
		return false;
	}else if("${(shopVo.shopURL)!''}"!=shopUrl&&checkShopUrl(shopUrl)){
		$("#shopUrlError").html("店铺域名已存在!");
		return false;
	}else {
		$("#shopUrlError").html("");
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
   $.ajax({
		  type : "POST",
		  url : "${BasePath}/merchant/shop/decorate/saveFssShopInfo.sc",
		  data : {
		    "shopId":shopId,
		    "merchantCode":merchantCode,
		    "shopName":shopName,
		    "shopURL":shopUrl,
		    "shopStatus":'${(shopVo.shopStatus)!'0'}'
		  },
		  dataType : "json",
		  async:false,
		  beforeSend: function(XMLHttpRequest){
			submitform.attr('state', 'running');
		  },
		  success : function(data) {
		    if(data.success==true){
				 alert("修改店铺成功！");
				 dg.curWin.toReload();
				 closewindow();
				 return true;
		    }else{
		         flag=false;
				 alert("修改店铺失败！");
				 return reslut;
		    }
		  },
		  complete: function(XMLHttpRequest, textStatus){
			submitform.attr('state', 'waiting');
//			location.reload();
		  },
		  error: function(XMLHttpRequest, textStatus, errorThrown){
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