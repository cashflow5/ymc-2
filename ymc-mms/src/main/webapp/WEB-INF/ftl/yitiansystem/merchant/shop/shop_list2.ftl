<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#include "../../merchants/merchants-include.ftl">
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css?version=0.0.1"/>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
<link rel="stylesheet" type="text/css" href="${BasePath}/js/yitiansystem/merchants/ztree/css/zTreeStyle/zTreeStyle.css"/>
<title>优购商城--商家后台</title>
</head>
<body>
<div class="container">
	<!--工具栏start--> 
	<div class="toolbar">
		<div class="t-content">
			<div class="btn" onclick="toCreateShop();">
				<span class="btn_l"></span>
	        	<b class="ico_btn add"></b>
	        	<span class="btn_txt">创建店铺</span>
	        	<span class="btn_r"></span>
        	</div>
        	
        	<div class="btn" onclick="doExport();">
				<span class="btn_l"></span>
	        	<b class="ico_btn add"></b>
	        	<span class="btn_txt">导出列表</span>
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
						  <input style="width:135px;" type="text" id="merchantName" name="merchantName" value="${(vo.merchantName)!''}"/>
						</span>
						
						<span>
						<label>商家编号：</label>
						<input style="width:135px;" type="text" id="merchantCode" name="merchantCode" value="${(vo.merchantCode)!''}"/>
					    </span>
					    
					    <span>
						  <label>店铺名称：</label>
						  <input style="width:135px;" type="text" id="shopName" name="shopName" value="${(vo.shopName)!''}"/>
						</span>
						
						<span>
						  <label>品牌：</label>
						  <input style="width:135px;" type="text" id="brandNames" name="brandNames" value="${(vo.brandNames)!''}"/>
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
	
						<span>
						<label>创建时间：</label>
						<input id="createDateTimeBegin" name="createDateTimeBegin" value="${(vo.createDateTimeBegin)!''}" />
						-
						<input id="createDateTimeEnd" name="createDateTimeEnd" value="${(vo.createDateTimeEnd)!''}"  />
						</span>
						<span>
<#--					<label>发布时间：</label>
						<input id="publishDateTimeBegin" name="publishDateTimeBegin" value="${(vo.publishDateTimeBegin)!''}" />
						-
						<input id="publishDateTimeEnd" name="publishDateTimeEnd" value="${(vo.publishDateTimeEnd)!''}"  />
						</span>
-->						
						<span>
							<label>&nbsp;<input type="button" value="搜索" onclick="queryShopList();" class="yt-seach-btn" /></label>
						</span>
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
                        <th style="text-align: center;">最近发布时间</th>
                        <th style="text-align: center;">店铺状态</th>
                        <th style="text-align: center;">更新人</th>
                        <th style="text-align: center;">是否需要审核</th>
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
                		  <#else>
                		  ${item['shopName']?default('')}
                		  </#if></td>
                		  <td style="text-align: center;">${item['brandNames']?default('')}</td>
                		  <td style="text-align: center;"><#if item.createDateTime??>${item['createDateTime']?string('yyyy-MM-dd HH:mm:ss')}</#if></td>  
                		  <td style="text-align: center;"><#if item.publishDateTime??>${item['publishDateTime']?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
                		  <td style="text-align: center;">
                		  <#if item.access??&&item.access=='N'>已关闭
                		  <#elseif item.shopStatus??&&item.shopStatus==0>新建
                		  <#elseif item.shopStatus??&&item.shopStatus==1>已发布
                		  <#elseif item.shopStatus??&&item.shopStatus==2>装修中
                		  <#else>${item['shopStatus']!''}
                		  </#if>
                		  </td>
                		  <td style="text-align: center;">${item['lastUpdateUserId']!''}</td>
                		  
                		  <td style="text-align: center;">
                		  	<span>
                		  	<#if item['auditFlag']?? && item['auditFlag'] == "1">
	            		  		<a id="statusImg${item['shopId']?default('')}" href="javascript:;" class="mdl_sts_stop" val="${item['shopId']?default('')}"></a>   
	            		  	<#else>
	            		  		<a id="statusImg${item['shopId']?default('')}" href="javascript:;" class="mdl_sts_start" val="${item['shopId']?default('')}"></a>
	            		  	</#if>
	            		  	</span>
                		  </td>
                		  
                		  <td class="ft-cl-r" style="text-align: center;">
                		  <#if item.shopStatus==0>
                		  	<a href="javascript:;" onclick="auditOk('${item['shopId']?default('')}');">通过</a>
                		  	<a href="javascript:;" onclick="toModifyShop('${item['shopId']?default('')}');">修改资料</a>
                		  	<a href="javascript:;" onclick="toModifyShopRule('${item['shopId']?default('')}');">经营品类</a>
                		  	<a href="javascript:;" onclick="deleteShop('${item['shopId']?default('')}');">删除</a>
                		  <#elseif (item.access)?? && item.access=='Y'>
                		  	<a href="javascript:;" onclick="auditStop('${item['shopId']?default('')}');">关闭</a>
                		  	<a href="javascript:;" onclick="toModifyShop('${item['shopId']?default('')}');">修改资料</a>
 
                		  	<a href="javascript:;" onclick="toModifyShopRule('${item['shopId']?default('')}');">经营品类</a>
 
                		  <#elseif (item.shopStatus gt 0) && item.access?? && item.access=='N'>
                		  	<a href="javascript:;" onclick="auditStart('${item['shopId']?default('')}');">开启</a>
                		  	<a href="javascript:;" onclick="toModifyShop('${item['shopId']?default('')}');">修改资料</a>
                		  	<a href="javascript:;" onclick="toModifyShopRule('${item['shopId']?default('')}');">经营品类</a>
                		  	<a href="javascript:;" onclick="deleteShop('${item['shopId']?default('')}');">删除</a>
                		  </#if>
                		  <!--#if (item.shopStatus)?? && (item.shopStatus == 1 )-->
                		  	  <a target="_blank" href="http://kaidian.yougou.com/fss/store/pagePreviewMMS.sc?storeId=${item['shopId']?default('')}&key=${item['key']?default('')}">预览</a>
                		  	  <a href="javascript:;" onclick="viewLog('${item['shopId']?default('')}');">查看日志</a>
                		  <!--/#if-->
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
	  			<@page.queryForm formId="queryForm"/>
	  		</#if>
		</div>
    	<div class="blank20"></div>
  	</div>
</div>
</body>
</html>
<script type="text/javascript">
var basePath = "${BasePath}",
    queryUrl = "${BasePath}/merchant/shop/decorate/shoplist.sc",
    exportUrl = "${BasePath}/merchant/shop/decorate/exportShoplist.sc";
$(document).ready(function() {
	$("#createDateTimeBegin").calendar({maxDate:'#createDateTimeEnd'});
	$("#createDateTimeEnd").calendar({minDate:'#createDateTimeBegin'});
	$("#publishDateTimeBegin").calendar({maxDate:'#publishDateTimeEnd'});
	$("#publishDateTimeEnd").calendar({minDate:'#publishDateTimeBegin'});
	//是否需要审核
	//选择属性为val【a标签自定义属性】点击事件
	$("[val]").click(function(){
  		var shopId = $(this).attr("val");
  		var auditFlag = 0;
  		if($(this).hasClass("mdl_sts_stop")){
			$(this).removeClass("mdl_sts_stop");
			$(this).addClass("mdl_sts_start");
			auditFlag = 0;
  		} else {
			$(this).removeClass("mdl_sts_start");
			$(this).addClass("mdl_sts_stop");
			auditFlag = 1;
  		}
		updateAuditShopFlag($(this), shopId, auditFlag);
 	});
});
function updateAuditShopFlag(operateObj, shopId, auditFlag){
	$.ajax({
		type: "POST",
		url : "${BasePath}/merchant/shop/decorate/goUpdateAuditShopFlag.sc?shopId="+shopId+"&auditFlag="+auditFlag,
	    dataType : "json",
		success: function(data){
			if(true==data.success){
				if(auditFlag == 1){
			   		alert("已经成功更新为不需要审核!");
			   	} else {
			   		alert("已经成功更新为需要审核!");
			   	}
			   	return ;
			} else {
				alert(data.msg);
				if(auditFlag == 1){
					operateObj.removeClass("mdl_sts_stop");
					operateObj.addClass("mdl_sts_start");
				} else {
					operateObj.removeClass("mdl_sts_start");
					operateObj.addClass("mdl_sts_stop");
				}
			}
		} 
	});
}
//根据条件查询
function queryShopList(){
  	document.queryForm.submit();
}
function toCreateShop(){
	openwindow('${BasePath}/merchant/shop/decorate/toCreateFssShop.sc?flag=1',800,500,'创建店铺');
}
function toModifyShop(shopId){
	openwindow('${BasePath}/merchant/shop/decorate/toModifyFssShop.sc?shopId='+shopId,800,400,'修改店铺资料');
}
function toModifyShopRule(shopId){
	openwindow('${BasePath}/merchant/shop/decorate/toModifyFssShopRule.sc?shopId='+shopId,800,500,'设置经营分类');
}
function auditOk(shopId){
    if(shopId!=""){
      if(confirm("你确定通过吗？")){
		$.ajax({ 
			type: "post", 
			dataType : "json",
			url: "${BasePath}/merchant/shop/decorate/update_shop_status.sc?shopId=" + shopId + "&shopStatus=2", 
			success: function(data){
				if(true==data.success){
				   alert("通过成功!");
				}else{
				   alert(data.reCode+":"+data.msg);
				}
				location.reload();
			} 
		});
   		}
  	}
}
function auditStop(shopId){
    if(shopId!=""){
      if(confirm("你确定关闭当前店铺吗？")){
		$.ajax({ 
			type: "post",
			dataType : "json",
			url: "${BasePath}/merchant/shop/decorate/update_shop_access_status.sc?shopId=" + shopId + "&access=N", 
			success: function(data){
				if(true==data.success){
				   alert("关闭当前店铺成功!");
				}else{
				   alert(data.reCode+":"+data.msg);
				}
				location.reload();
			} 
		});
   		}
  	}
}
function auditStart(shopId){
      if(shopId!=""){
      if(confirm("你确定开启当前店铺吗？")){
		$.ajax({ 
			type: "post", 
			dataType : "json",
			url: "${BasePath}/merchant/shop/decorate/update_shop_access_status.sc?shopId=" + shopId + "&access=Y", 
			success: function(data){
				if(true==data.success){
				   alert("开启当前店铺成功!");
				}else{
				   alert(data.reCode+":"+data.msg);
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
			dataType : "json",
			url: "${BasePath}/merchant/shop/decorate/delete_shop.sc?shopId=" + shopId, 
			success: function(data){
				if(true==data.success){
				   alert("删除成功!");
				}else{
				   alert(data.reCode+":"+data.msg);
				}
				location.reload();
			}
		});
   		}
  	}
}

function viewLog(shopId){
	openwindow("${BasePath}/merchant/shop/decorate/viewShopOperationLog.sc?merchantCode=" + shopId, 900, 700, "查看日志");
}
function toReload(){
	location.reload();
}

function doExport(){
	if(!confirm("确定导出！")){
		return;
	}
    document.queryForm.action = exportUrl;
    document.queryForm.submit();
    document.queryForm.action = queryUrl;
}
</script>
