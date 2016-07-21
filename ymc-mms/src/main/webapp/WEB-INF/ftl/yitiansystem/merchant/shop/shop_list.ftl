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
<script type="text/javascript" src="${BasePath}/js/yitiansystem/merchants/cat_tree.js?lyx20151008"></script>
<link rel="stylesheet" type="text/css" href="${BasePath}/js/yitiansystem/merchants/ztree/css/zTreeStyle/zTreeStyle.css"/>
<title>优购商城--商家后台</title>
<style>
/* CHECKBOXES */
.checkbox { display: none; }
#check_frame .lf{float:left;}
#check_frame .trigger {
  cursor: pointer;
  position: relative;
  float: left;
  width: 40px; height: 20px;
  margin: 10px;
  margin-top:-6px;
  border-radius: 20px;
  background: #666;
  overflow: hidden;
  -webkit-transition: background .15s linear;
}
#check_frame .checkbox:checked + .trigger { background: #00C5FF; }
#check_frame .trigger:before {
  content: '';
  position: absolute;
  top: 0; left: 0; bottom: 0;
  width: 10px; height: 10px;
  margin: auto 5px;
  border-radius: 100%;
  box-shadow: 0 0 0 100px hsla(0, 0%, 0%, .3);
  -webkit-transition: left .15s linear;
}
#check_frame .checkbox:checked + .trigger:before { left: 20px; }
.on_changes{width:280px;height:280px;z-index:100;overflow:auto; position:absolute; top:65px;left:118px; list-style:none; background:#FFF; border:1px solid #000; display:none;}
.on_changes li{margin:8px;padding:4px;}
.on_changes li.active{ background:#CEE7FF;}
.com_modi_table th{width: 200px;}
</style>
</head>
<body>
<div class="container">
	<!--工具栏start--> 
	<div class="toolbar">
		<div class="t-content">
			<div class="btn" onclick="addMerchants();">
				<span class="btn_l"></span>
	        	<b class="ico_btn add"></b>
	        	<span class="btn_txt">创建店铺</span>
	        	<span class="btn_r"></span>
        	</div> 
		</div>
	</div>
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li  class="curr">
				  <span><a href="javascript:;" class="btn-onselc">店铺列表</a></span>
				</li>
			</ul>
		</div>
 <div class="modify"> 
     <form action="${BasePath}/merchant/shop/decorate/shoplist.sc" name="queryForm" id="queryForm" method="post">        	
              	<div class="add_detail_box">
					<p>
						<span>
						  <label>商家名称：</label>
						  <input style="width:135px;" type="text" id="merchantName" name="merchantName" value="<#if vo??&&vo.merchantName??>${vo.merchantName}</#if>">
						</span>
						
						<span>
						<label>商家编号：</label>
						<input style="width:135px;" type="text" id="merchantCode" name="merchantCode" value="<#if vo??&&vo.merchantCode??>${vo.merchantCode}</#if>">
					    </span>
					    
					    <span>
						  <label>店铺名称：</label>
						  <input style="width:135px;" type="text" id="shopName" name="shopName" value="<#if vo??&&vo.shopName??>${vo.shopName}</#if>">
						</span>
						
						<span>
						  <label>品牌：</label>
						  <input style="width:135px;" type="text" id="brandNames" name="brandNames" value="<#if vo??&&vo.brandNames??>${vo.brandNames}</#if>">
						</span>
					</p>
					<p>

	                    <span><label>店铺状态：</label>
						<select id="shopStatus" name="shopStatus" style="width:138px;">
						  <option <#if vo.shopStatus??&&vo.shopStatus==-1>selected</#if> value="-1">全部</option>
						  <option <#if vo.shopStatus??&&vo.shopStatus==0>selected</#if> value="0">新建</option>
						  <option <#if vo.shopStatus??&&vo.shopStatus==1>selected</#if> value="1">已发布</option>
						  <option <#if vo.shopStatus??&&vo.shopStatus==2>selected</#if> value="2">未发布</option>
						</select>
						</span>
	
						<span>
						<label>创建时间：</label>
						<input id="createDateTimeBegin" name="createDateTimeBegin" value="<#if vo??&&vo.createDateTimeBegin??>${vo.createDateTimeBegin}</#if>" />
						-
						<input id="createDateTimeEnd" name="createDateTimeEnd" value="<#if vo??&&vo.createDateTimeEnd??>${vo.createDateTimeEnd}</#if>"  />
						</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						
						<input type="button" value="搜索" onclick="queryShopList();" class="yt-seach-btn" />&nbsp;&nbsp;&nbsp;
					</p>
				</div>         	
              	</form>
              	<span id="check_frame">
                <table cellpadding="0" cellspacing="0" class="list_table">
                	<thead>
                        <tr>
                        <th style="text-align: center;">商家名称</th>
                        <th style="text-align: center;">店铺名称</th>
                        <th style="text-align: center;">品牌</th>
                        <th style="text-align: center;">创建时间</th>
                        <th style="text-align: center;">发布时间</th>
                        <th style="text-align: center;">店铺状态</th>
                        <th style="text-align: center;">更新人</th>
                        
                        <!--<th style="text-align: center;">是否启动</th>-->
                        
                        <th style="text-align: center;">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#if pageFinder??&&pageFinder.data?? >
                        <#list pageFinder.data as item >
                		<tr>
                		  <td class="ft-cl-r">${item['shopName']?default('')}</td>
                		  <td class="ft-cl-r">${item['shopName']?default('')}</td>
                		  <td style="text-align: center;">${item['brandNames']?default('')}</td>
                		  <td style="text-align: center;"><#if item.createDateTime??>${item['createDateTime']?string('yyyy-MM-dd HH:mm:ss')}</#if></td>  
                		  <td style="text-align: center;"><#if item.publishDateTime??>${item['publishDateTime']?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
                		  <td style="text-align: center;"><#if item.shopStatus??&&item.shopStatus==0>新建
                		  <#elseif item.shopStatus??&&item.shopStatus==1>已发布
                		  <#elseif item.shopStatus??&&item.shopStatus==2>未发布
                		  <#else>${item['shopStatus']!''}
                		  </#if></td>
                		  <td style="text-align: center;">${item['lastUpdateUserId']!''}</td>
                		  
                		  <!--<td style="text-align: center;"><input id="check${item['shopId']?default('')}" name="check${item['shopId']?default('')}" class="checkbox" type="checkbox"><label class="trigger" for="check${item['shopId']?default('')}"></label></td>-->
                		  
                		  <td class="ft-cl-r" style="text-align: center;"><a target="_blank" href="http://mall.yougou.com/m-${item['shopURL']?default('')}.shtml">查看&nbsp;&nbsp;
                		  <#if item.shopStatus==0>
                		  	<a href="javascript:;" onclick="auditOk('${item['shopId']?default('')}');">通过</a>&nbsp;&nbsp;修改&nbsp;&nbsp;删除
                		  <#elseif (item.shopStatus gt 0) && item.access?? && item.access=='Y'>
                		  	<a href="javascript:;" onclick="auditStop('${item['shopId']?default('')}');">停用</a>&nbsp;&nbsp;修改&nbsp;&nbsp;删除
                		  <#elseif (item.shopStatus gt 0) && item.access?? && item.access=='N'>
                		  	<a href="javascript:;" onclick="auditStart('${item['shopId']?default('')}');">启用</a>
                		  	<a href="javascript:;" onclick="modify('${item['shopId']?default('')}');">修改</a>
                		  	<a href="javascript:;" onclick="deleteShop('${item['shopId']?default('')}');">删除</a>
                		  </#if>
                		 </tr>
                	     </#list>
                        	<#else>
                        	<tr>
                        	<td colSpan="8">
                        	     抱歉，没有您要找的数据 
	                        </td>
	                        </tr>
                        </#if>
                      </tbody>
                </table>
                </span>
              </div>
               <div class="bottom clearfix">
			  	<#if pageFinder ??><#import "../../../common.ftl" as page>
			  		<@page.queryForm formId="queryForm"/></#if>
			  </div>
              <div class="blank20"></div>
          </div>
</div>
</body>
</html>
<script type="text/javascript">
var basePath = "${BasePath}";
$(document).ready(function() {
$("#createDateTimeBegin").calendar({maxDate:'#createDateTimeEnd'});
$("#createDateTimeEnd").calendar({minDate:'#createDateTimeBegin'});
});
//根据条件查询
function queryShopList(){
  document.queryForm.submit();
}
function addMerchants(){
var supplierList="";
<#list supplierList as item >
supplierList=supplierList+"<li spid='${item['supplierCode']}'>${item['supplierCode']}@${item['supplier']}</li>";
</#list>
var content='<span><label style="display:inline-block;color:#ff0000;">*</label><label style="font-weight:bolder;">绑定商家：</label><input type="text" style="width:150px;height:12px;" name="_merchantCode" id="_merchantCode" placeholder="商家名称/商家编码" />'
		+'<ul class="on_changes" id="on_changes">'+supplierList+'</ul></span><br/><br/>'
           +'<span><label style="display:inline-block;color:#ff0000;">*</label><label style="font-weight:bolder;">店铺名称：</label><input type="text" style="width:150px;height:12px;"  id="_shopName" name="_shopName" value="" /></span><br/><br/>'
           +'<span><label style="display:inline-block;color:#ff0000;">*</label><label style="font-weight:bolder;">店铺域名：</label>http://mall.yougou.com/m-<input type="text" style="width:120px;height:12px;"  id="_shopUrl" name="_shopUrl" value="" />.html</span><br/><br/>'
           +'<span><label style="color:#CCCCCC;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;内容仅包含数字、小写字母和“-”符号，并不能以“-”开头和结尾。控制在1-20个字符内。</label></span><br/><br/>'
           +'<div><label style="display:inline-block;color:#ff0000;">*</label><label style="font-weight:bolder;">绑定品牌：</label><input type="hidden" name="bankNoHistory" id="bankNoHistory" value="" />'
           +'<input type="hidden" name="bankNameHidden" id="bankNameHidden"  />'
           +'<input type="hidden" name="bankNoHidden" id="bankNoHidden"  />'
           +'<input type="hidden" name="catNameHidden" id="catNameHidden" />'
           +'<span id="bank_span"></span><img src="${BasePath}/images/finance/adduser.gif" onclick="addBrand();"/>&nbsp;&nbsp;<span style="color:red;" id="bankNameError"></span>'
           +'</div><br/><br/>'
           +'<span><label style="display:inline-block;color:#ff0000;">*</label><label style="font-weight:bolder;">经营类目：</label><div class="content_wrap"><ul id="ztree" class="ztree"></ul></div><input type="hidden" id="supplier_cat_brand_tree_hidden" onclick="init_supplier_cat_tree();" /></span><br/><br/>'
           +'<span><label style="color:#CCCCCC;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;绑定商品分类授权后，则该旗舰店只能已授权的类目到旗舰店内，修改类目需要商家重新装修后提交审核。</label></span><br/><br/>'
           +'<span id="check_frame"><label style="display:inline-block;color:#ff0000;" class="lf">*</label><label class="lf" style="font-weight:bolder;">是否启用：</label><input type="checkbox" id="check" name="check" class="checkbox"/><label for="check" class="trigger"></label></span><label style="color:#CCCCCC;">&nbsp;&nbsp;如果启用，店铺创建后直接流入商家后台</label><br/><br/>'
           +'<span><label id="ygdg_warn" style="display:inline-block;color:#ff0000;"></label></span>';
  var dialog=ygdg.dialog({
    title: '创建店铺',
	content: content,
	width: 720,
    height:400,
    init: function () {
	      $("#_merchantCode").changeTips({divTip:".on_changes"}); 
    },
	button: [{
             name: '创建店铺',
             callback: function () {
               var merchantCode=$("#_merchantCode").val();
               var shopName=$("#_shopName").val();
               var shopUrl=$("#_shopUrl").val();
               
               var bankNo = $("#bankNoHistory").val();//品牌BrandNo
	           var catStrs = "";
	           var treeObj = $.fn.zTree.getZTreeObj("ztree");
	           if (treeObj != null) {
		         var nodes = treeObj.getCheckedNodes();
		         for (var i=0, l=nodes.length; i < l; i++) {
			       var node = nodes[i];
			       if (node.lev == 3) { //取第三级分类
				     catStrs = catStrs+","+node.id;
			       }
		         }
	           }
	           //$("#catNameHidden").val(catStrs.join('_'));
	
	           if(bankNo=="" ){
		         $("#bankNameError").html("请选择授权品牌!");
		         return false;
	           }else{
		         $("#bankNameError").html("");
	           }
	           if(catStrs=="" ){
		         $("#bankNameError").html("请选择授权分类!");
		         return false;
	           }else{
		         $("#bankNameError").html("");
	           }
               if(merchantCode!=""&&shopName!=""&&merchantCode!=""&&shopUrl!=""){
                 $("#ygdg_warn").hide();
                 createShop(merchantCode,shopName,shopUrl,bankNo,catStrs);
               }else{
                 this.shake && this.shake();
                 $("#ygdg_warn").show();
                 $("#ygdg_warn").html("有必填的项目为空，请检查后重新输入！");
                 $("#_merchantCode").select();
                 $("#_merchantCode").focus();
                 return false;
             }
            },
               focus: true
        	},
        	{
               name: '关闭'
        	}]
   });

}

function createShop(merchantCode,shopName,shopUrl,brand,struct){
   	var submitform = $('#querFrom');
	if (submitform.attr('state') == 'running') {
		return false;
	}
   $.ajax({
		  type : "POST",
		  url : "${BasePath}/merchant/shop/decorate/create_shop.sc",
		  data : {
				    "merchantCode":merchantCode,
				    "shopName":shopName,
				    "shopURL":shopUrl,
				    "brandIds":brand,
				    "brandNames":struct,
		  },
		  dataType : "json",
		  beforeSend: function(XMLHttpRequest){
			submitform.attr('state', 'running');
		  },
		  success : function(data) {
		    if(data.success==true){
				 alert("创建成功！");
				 return true;
		    }else{
				 alert("创建失败！");
				 return reslut;
		    }
		  },
		  complete: function(XMLHttpRequest, textStatus){
			submitform.attr('state', 'waiting');
			location.reload();
		  },
		  error: function(XMLHttpRequest, textStatus, errorThrown){
			art.dialog.alert('创建店铺失败:' + XMLHttpRequest.responseText);
		  }
	});
}
function auditOk(shopId){
    if(shopId!=""){
      if(confirm("你确定审核通过吗？")){
		$.ajax({ 
			type: "post", 
			url: "${BasePath}/merchant/shop/decorate/update_shop_status.sc?shopId=" + shopId+"&shopStatus=2", 
			success: function(dt){
				if("success"==dt){
				   alert("更新成功!");
				}else{
				   alert("更新失败!");
				}
				location.reload();
			} 
		});
   }
  }
  
}
function auditStop(shopId){
    if(shopId!=""){
      if(confirm("你确定停止发布吗？")){
		$.ajax({ 
			type: "post", 
			url: "${BasePath}/merchant/shop/decorate/update_shop_status.sc?shopId=" + shopId+"&access=N", 
			success: function(dt){
				if("success"==dt){
				   alert("更新成功!");
				}else{
				   alert("更新失败!");
				}
				location.reload();
			} 
		});
   }
  }
}
function auditStart(shopId){
      if(shopId!=""){
      if(confirm("你确定启用发布吗？")){
		$.ajax({ 
			type: "post", 
			url: "${BasePath}/merchant/shop/decorate/update_shop_status.sc?shopId=" + shopId+"&access=Y", 
			success: function(dt){
				if("success"==dt){
				   alert("更新成功!");
				}else{
				   alert("更新失败!");
				}
				location.reload();
			} 
		});
   }
  }
}

function deleteShop(shopId){
      if(shopId!=""){
      if(confirm("你确定删除店铺吗？")){
		$.ajax({ 
			type: "post", 
			url: "${BasePath}/merchant/shop/decorate/delete_shop.sc?shopId=" + shopId, 
			success: function(dt){
				if("success"==dt){
				   alert("删除成功!");
				}else{
				   alert("删除失败!");
				}
				location.reload();
			}
		});
   }
  }
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
	alert(selectedBrandNos);
    openwindow('${BasePath}/yitiansystem/merchants/businessorder/to_addCat.sc?flag=1&brandNos=' + selectedBrandNos, 500, 200, '添加分类');
}


</script>