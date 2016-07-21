<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#include "../../merchants/merchants-include.ftl">
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css?version=0.0.1"/>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
<title>优购商城--商家后台</title>
</head>
<body>
<div class="container">
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li  class="curr">
				  <span>店铺审核</span>
				</li>
			</ul>
		</div>
 		<div class="modify"> 
     		<form action="${BasePath}/merchant/shop/decorate/shop_audit.sc" name="queryForm" id="queryForm" method="post">        	
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
						  <option <#if vo.shopStatus??&&vo.shopStatus==2>selected</#if> value="2">装修中</option>
						</select>
						</span>
						
						<span><label>审核状态：</label>
						<select id="auditStatus" name="auditStatus" style="width:138px;">
						  <option <#if vo.auditStatus??&&vo.auditStatus==-1>selected</#if> value="-1">全部</option>
						  <option <#if vo.auditStatus??&&vo.auditStatus==0>selected</#if> value="0">待提交</option>
						  <option <#if vo.auditStatus??&&vo.auditStatus==1>selected</#if> value="1">待审核</option>
						  <option <#if vo.auditStatus??&&vo.auditStatus==2>selected</#if> value="2">审核未通过</option>
						  <option <#if vo.auditStatus??&&vo.auditStatus==3>selected</#if> value="3">审核通过</option>
						</select>
						</span>
						
	                    <span>
						  <label>提交审核时间：</label>
						  <input id="submitDatetimeBegin" name="submitDatetimeBegin" value="<#if vo??&&vo.submitDatetimeBegin??>${vo.submitDatetimeBegin}</#if>" />
						  -
						  <input id="submitDatetimeEnd" name="submitDatetimeEnd" value="<#if vo??&&vo.submitDatetimeEnd??>${vo.submitDatetimeEnd}</#if>"  />
						</span>
						</p>
						
					    <p>
					    <span>
						  <label>审核完成时间：</label>
						  <input id="auditDatetimeBegin" name="auditDatetimeBegin" value="<#if vo??&&vo.auditDatetimeBegin??>${vo.auditDatetimeBegin}</#if>" />
						  -
						  <input id="auditDatetimeEnd" name="auditDatetimeEnd" value="<#if vo??&&vo.auditDatetimeEnd??>${vo.auditDatetimeEnd}</#if>"  />
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
                        <th style="text-align: center;">提交审核时间</th>
                        <th style="text-align: center;">审核完成时间</th>
                        <th style="text-align: center;">审核状态</th>
                        <th style="text-align: center;">更新人</th>
                        <th style="text-align: center;">拒绝原因</th>
                        <th style="text-align: center;">操作</th>
                        </tr>
                   	</thead>
                    <tbody>
                        <#if pageFinder??&&pageFinder.data?? >
                        <#list pageFinder.data as item >
                		<tr>
                		  	<td class="ft-cl-r">${item['merchantName']?default('')}</td>
                		  	<td class="ft-cl-r">
                		    <#if (item.generationPage)?? && (item.generationPage == 'Y' )>
                		    <a href="${item['fullPathUrl']?default('')}" target="_blank">${item['shopName']?default('')}</a>
                		    <#else>${item['shopName']?default('')}</#if></td>
                		  	<td style="text-align: right;" class="brand">
                		  	<#if item.brandNames??>
                			<#list item.brandNames?split(";") as brand>
            					<#assign x=brand?split(",")>
								<span>${x[1]?default('')}
								<#if x[2]?? && x[2] == '0'>
    	            		  		<a id="statusImg${x[0]?default('')}" href="javascript:;" class="mdl_sts_stop" val="${x[0]?default('')}" shp="${item['shopId']?default('')}"></a>   
    	            		  	<#else>
    	            		  		<a id="statusImg${x[0]?default('')}" href="javascript:;" class="mdl_sts_start" val="${x[0]?default('')}" shp="${item['shopId']?default('')}"></a>
    	            		  	</#if>
								</span>
								<br/>
							</#list>
							</#if>
							</td>
                		  	<td style="text-align: center;"><#if item.submitDatetime??>${item['submitDatetime']?string('yyyy-MM-dd HH:mm:ss')}</#if></td>  
                		  	<td style="text-align: center;"><#if (item.auditDatetime)??>${item['auditDatetime']?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
                		  	<td style="text-align: center;">
                		  	<#if item.auditStatus??&&item.auditStatus==0>待提交
                		  	<#elseif item.auditStatus??&&item.auditStatus==1>待审核
                		  	<#elseif item.auditStatus??&&item.auditStatus==2>审核未通过
                		  	<#elseif item.auditStatus??&&item.auditStatus==3>审核通过
                		  	</#if>
                		  	</td>
                		  	<td style="text-align: center;">${item['lastUpdateUserId']!''}</td>
                		  	<td style="text-align: center;" title="${item['auditNote']!''}"><#if item['auditNote']??&&(item['auditNote']?length gt 30)>${item['auditNote']?substring(0,28)}...<#else>${item['auditNote']?default('')}</#if></td>
                		  	<td class="ft-cl-r" style="text-align: center;">
                		  	<#if item.auditStatus==1>
                		  		<a href="javascript:;" onclick="auditOk('${item['shopId']?default('')}');">通过</a>
                		  		<a href="javascript:;" onclick="auditRefuseOpen('${item['shopId']?default('')}');">拒绝</a>
                		  	</#if>
                		  	     <a target="_blank" href="http://kaidian.yougou.com/fss/store/pagePreviewMMS.sc?storeId=${item['shopId']?default('')}&key=${item['key']?default('')}">预览</a>
	                		</td>
                		</tr>
                	    </#list>
                        <#else>
                        <tr>
                        	<td colSpan="9">
                        	     抱歉，没有您要找的数据 
	                        </td>
	                        </tr>
                        </#if>
					</tbody>
				</table>
			</span>
		</div>
		<div class="bottom clearfix">
  			<#if pageFinder ??>
  				<#import "../../../common.ftl" as page>
  				<@page.queryForm formId="queryForm"/></#if>
  		</div>
  		<div class="blank20"></div>
	</div>
</div>
</body>
</html>
<script type="text/javascript">
$(document).ready(function() {
	$("#submitDatetimeBegin").calendar({maxDate:'#submitDatetimeEnd'});
	$("#submitDatetimeEnd").calendar({minDate:'#submitDatetimeBegin'});
	$("#auditDatetimeBegin").calendar({maxDate:'#auditDatetimeEnd'});
	$("#auditDatetimeEnd").calendar({minDate:'#auditDatetimeBegin'});
	
	//是否品牌推荐
	//选择属性为val【a标签自定义属性】点击事件
  	$("[val]").click(function(){
  		var shopId = $(this).attr("shp");
  		var recommendBrandId = $(this).attr("val");
  		if($(this).hasClass("mdl_sts_stop")){
			$(this).removeClass("mdl_sts_stop");
			$(this).addClass("mdl_sts_start");
			updateRecommendTheBrand($(this), shopId, recommendBrandId);
		} else {
			$(this).removeClass("mdl_sts_start");
			$(this).addClass("mdl_sts_stop");
			cancelRecommendBrand($(this), shopId, recommendBrandId);
		}
	});
});
function updateRecommendTheBrand(operateObj, shopId, recommendBrandId){
	$.ajax({
		type: "POST",
		url : "${BasePath}/merchant/shop/decorate/goRecommendTheBrand.sc?shopId="+shopId+"&recommendBrandId="+recommendBrandId,
	    dataType : "json",
		success: function(data){
			if(true==data.success){
			   alert("品牌推荐成功!");
			   return ;
			}else{
				if(data.reCode != null){
					if(data.reCode == -2){
						if(confirm(data.msg)){
							overideRecommendBrand(operateObj, shopId, recommendBrandId);
							return ;
						}
					} else {
						alert(data.msg);
					}
				} else {
					alert(data.msg);
				}
				operateObj.removeClass("mdl_sts_start");
				operateObj.addClass("mdl_sts_stop");
//				location.reload();
			}
		} 
	});
}
function cancelRecommendBrand(operateObj, shopId, recommendBrandId){
	$.ajax({
		type: "POST",
		url : "${BasePath}/merchant/shop/decorate/goCancelRecommendBrand.sc?shopId="+shopId+"&recommendBrandId="+recommendBrandId,
	    dataType : "json",
		success: function(data){
			if(true==data.success){
			   alert("品牌取消推荐成功!");
			   return ;
			} else {
				alert(data.msg);
				operateObj.removeClass("mdl_sts_stop");
				operateObj.addClass("mdl_sts_start");
//				location.reload();
			}
		} 
	});
}
function overideRecommendBrand(operateObj, shopId, recommendBrandId){
	$.ajax({
		type: "POST",
		url : "${BasePath}/merchant/shop/decorate/goOverideRecommendBrand.sc?shopId="+shopId+"&recommendBrandId="+recommendBrandId,
	    dataType : "json",
		success: function(data){
			if(true==data.success){
			   alert("品牌推荐成功!");
			   return ;
			} else {
				alert(data.msg);
				operateObj.removeClass("mdl_sts_start");
				operateObj.addClass("mdl_sts_stop");
//				location.reload();
			}
		} 
	});
}
//根据条件查询
function queryShopList(){
  document.queryForm.submit();
}
function auditOk(shopId){
   	if(shopId!=""){
     	if(confirm("你确定通过审核吗？")){
		$.ajax({ 
			type: "post",
			dataType : "json",
			url: "${BasePath}/merchant/shop/decorate/audit_shop.sc?shopId=" + shopId+"&auditStatus=3", 
			success: function(data){
				if(true==data.success){
				   alert("审核成功!");
				}else{
				   alert(data.msg);
				}
				location.reload();
			} 
		});
   		}
  	}
}
function auditRefuseOpen(shopId){

var content='<span><label style="display:inline-block;color:#ff0000;">*</label><label style="font-weight:bolder;">拒绝原因：</label>'
			+'<textarea style="width:500px;height:250px;"  name="auditNote" id="auditNote" class="contentDefault inputtxt" flag="true" ></textarea></span><br/><br/>'
           	+'<span><label id="ygdg_warn" style="display:inline-block;color:#ff0000;"></label></span>';
  	var dialog=window.parent.ygdg.dialog({
    title: '店铺装修审核',
	content: content,
	width: 720,
    height:400,
	button: [{
             name: '审核拒绝',
             callback: function () {
               var auditNote=$("#auditNote").val();
               if(auditNote!=""){
                 $("#ygdg_warn").hide();
                 auditRefuse(shopId,auditNote);
               }else{
                 this.shake && this.shake();
                 $("#ygdg_warn").show();
                 $("#ygdg_warn").html("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请输入拒绝原因！");
                 $("#auditNote").select();
                 $("#auditNote").focus();
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
function auditRefuse(shopId,auditNote){
   	var submitform = $('#querFrom');
	if (submitform.attr('state') == 'running') {
		return false;
	}
   $.ajax({
		  type : "POST",
		  url : "${BasePath}/merchant/shop/decorate/audit_shop.sc",
		  data : {
				    "shopId":shopId,
				    "auditStatus":'2',
				    "auditNote":auditNote
		  },
		  dataType : "json",
		  beforeSend: function(XMLHttpRequest){
				submitform.attr('state', 'running');
		  },
		  success : function(data) {
		    if(data.success==true){
				 alert("审核拒绝成功！");
				 return true;
		    }else{
				 alert(data.msg);
				 return reslut;
		    }
		  },
		  complete: function(XMLHttpRequest, textStatus){
			submitform.attr('state', 'waiting');
			location.reload();
		  },
		  error: function(XMLHttpRequest, textStatus, errorThrown){
			art.dialog.alert('审核拒绝失败:' + XMLHttpRequest.responseText);
		  }
	});
}
</script>