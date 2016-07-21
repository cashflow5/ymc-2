<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#include "../merchants-include.ftl">
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script>
<style type="text/css">
.icon_edit,.icon_save{width:16px;height:16px;display:inline-block;}
.icon_edit{background:url(${BasePath}/images/icon_edit.png) no-repeat;}
.icon_save{background:url(${BasePath}/images/icon_save.png) no-repeat;}
</style>
<title>优购商城--商家后台</title>
</head>
<body>
<div class="container">
<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li  class="curr">
				  <span><a href="#" class="btn-onselc">淘宝授权管理</a></span>
				</li>
			</ul>
		</div>
 <div class="modify"> 
     <form action="${BasePath}/yitiansystem/merchants/taobao/taobao_authorize.sc" name="queryForm" id="queryForm" method="post">        	
              	<div class="add_detail_box">
					<p>
						<span>
						  <label>淘宝店铺名称：</label>
						  <input style="width:150px;" type="text" id="taobaoShopName" name="taobaoShopName" value="<#if vo??&&vo.taobaoShopName??>${vo.taobaoShopName}</#if>">
						</span>
						
						<span >
						  <label style="width:120px;">淘宝店铺账户名称：</label>
						  <input style="width:150px;" type="text" id="nickName" name="nickName" value="<#if vo??&&vo.nickName??>${vo.nickName}</#if>">
						</span>
						
						<span>
						  <label>淘宝APPKEY：</label>
						  <input style="width:150px;" type="text" id="topAppkey" name="topAppkey" value="<#if vo??&&vo.topAppkey??>${vo.topAppkey}</#if>">
						</span>
					</p>
					<p>
					    <span>
						<label>商家编码：</label>
						  <input style="width:150px;" type="text" id="merchantCode" name="merchantCode" value="<#if vo??&&vo.merchantCode??>${vo.merchantCode}</#if>">
						</span>
						<span>
						<label style="width:120px;">商家名称：</label>
						  <input style="width:150px;" type="text" id="merchantName" name="merchantName" value="<#if vo??&&vo.merchantName??>${vo.merchantName}</#if>">
						</span>
					
					    <span>
						<label>创建时间：</label>
						<input id="startTime" name="startTime" value="<#if vo??&&vo.startTime??>${vo.startTime}</#if>" />
						-
						<input id="endTime" name="endTime" value="<#if vo??&&vo.endTime??>${vo.endTime}</#if>"  />
						</span>&nbsp;&nbsp;&nbsp;
						<input type="button" value="搜索" onclick="queryData();" class="yt-seach-btn" />&nbsp;&nbsp;&nbsp;
					</p>
				</div>         	
              	</form>
                <table cellpadding="0" cellspacing="0" class="list_table">
                		<thead>
                        <tr>
                        <th style="text-align: center;">商家编码</th>
                        <th style="text-align: center;">商家名称</th>
                        <th style="text-align: center;">淘宝店铺名称</th>
                        <th style="text-align: center;">淘宝店铺昵称ID</th>
                        <th style="text-align: center;">淘宝店铺账户名称</th>
                        <th style="text-align: center;">淘宝APPKEY</th>
                        <th style="text-align: center;">授权状态</th>
                        <th style="text-align: center;width:60px;">回调时间</th>
                        <th style="text-align: center;width:60px;">创建时间</th>
                        <th style="text-align: center;">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#if pageFinder??&&pageFinder.data?? >
                        <#list pageFinder.data as item >
                		<tr>
                		   <td class="ft-cl-r" style="text-align: center;">${item['merchantCode']!''}</td>
                		   <td class="ft-cl-r" style="text-align: center;width:180px;">${item['merchantName']!''}</td>
                		   <td class="ft-cl-r" style="text-align: center;"><a target="_blank"  title="${item['taobaoShopUrl']!''}" href="${item['taobaoShopUrl']!''}" >${item['taobaoShopName']!''}</a></td>
                		   <td class="ft-cl-r" style="text-align: center;">${item['nid']!''}</td>
                		   <td class="ft-cl-r" style="text-align: center;">${item['nickName']!''}</td>
                		   <td class="ft-cl-r" style="text-align: center;" id="${item.id?default('')}_1">${item['topAppkey']!''}<a href="javascript:editTopAppkey('${item.id?default('')}','${item.topAppkey?default('')}','${item['status']!''}');" class="icon_edit fr" title="点击图标可修改appkey的绑定"></a></td>
                		   <td class="ft-cl-r" style="display:none;text-align: center;width:220px;" id="${item.id?default('')}_2"><select id="topAppkey_${item.id?default('')}" name="topAppkey_${item.id?default('')}"><option value="">--请选择--</option><#list appKeyList as item_appkey ><option value="${item_appkey['topAppkey']!''}" <#if item.topAppkey??&&item_appkey.topAppkey??&&item.topAppkey==item_appkey.topAppkey>selected="selected"</#if>>${item_appkey['topAppkey']!''}<#if item_appkey.topAppName??><#if item_appkey.topAppName??>-${item_appkey['topAppName']!''}</#if></#if></option></#list></select><a href="javascript:saveTopAppkey('${item.id?default('')}','${item.topAppkey?default('')}');" class="icon_save fr" title="点击图标可保存appkey的绑定"></a></td>
                		   <td class="ft-cl-r" style="text-align: center;">${item['statusName']!''}</td>
                		   <td><#if item.operated??>${item['operated']!''}<#else>-</#if></td>
                		   <td><#if item.createTime??>${item['createTime']?string('yyyy-MM-dd HH:mm:ss')}<#else>-</#if></td>
                		   <td class="ft-cl-r" style="text-align: center;">
                				<#if item.nickName??>
                				<#if item.status=="1"><a onclick="authorize('${item['id']!""}','${item['topAppkey']!''}','${item['status']!''}');" href="javascript:void(0)">审核通过</a>
                				<#elseif item.status=="-1">
                				<a onclick="authorize('${item['id']!""}','${item['topAppkey']!''}','${item['status']!''}');" href="javascript:void(0)">启用</a>
                				<#else>
                                <a onclick="authorize('${item['id']!""}','${item['topAppkey']!''}','${item['status']!''}');" href="javascript:void(0)">禁用</a>
                				</#if>
                				</#if>
                				<#if item.nid??>
                				<a href="${BasePath}/yitiansystem/merchants/taobao/taobao_access_track.sc?nickId=${item['nid']!''}" target="_blank">日志</a>
                				<#else><a onclick="deleteAuthorize('${item['id']!""}');" href="javascript:void(0)">删除</a>
                				</#if>
                		   </td>
                		 </tr>
                	     </#list>
                        	<#else>
                        	<tr>
                        	<td colSpan="10">
                        	抱歉，没有您要找的数据 
	                        </td>
	                        </tr>
                        </#if>
                      </tbody>
                </table>
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
$(document).ready(function() {
$("#startTime").calendar({maxDate:'#endTime'});
$("#endTime").calendar({minDate:'#startTime'});
});
//根据条件查询
function queryData(){
  document.queryForm.submit();
}
function editTopAppkey(id,topAppkey,status){
if(status==3){
   alert("商家已授权的appkey不允许再做变更！");
   return;
}
$("#"+id+"_1").hide();
$("#"+id+"_2").show();
}

function saveTopAppkey(id,topAppkey){
var newtopAppkey=$("#topAppkey_"+id).val();
//如果为空就不允许保存
if(newtopAppkey==""){
var dialog=ygdg.dialog({
    title: '温馨提示',
    lock:true,
	content: '请选择一个topAppkey来绑定相应的商家！',
	button: [{
             name: '返回修改',
             focus: true
        	},
        	{
              name: '取消',
              callback: function () {
             $("#"+id+"_1").show();
             $("#"+id+"_2").hide();
            },
        	}]
   	});
   	return;
}
if(newtopAppkey==topAppkey){
 alert("你没有修改appkey的绑定，请重新修改！");
 $("#"+id+"_1").show();
 $("#"+id+"_2").hide();
 return;
}

//开始保存绑定
   $.ajax({
		  type : "POST",
		  url : "${BasePath}/yitiansystem/merchants/taobao/update_taobao_authorize.sc",
		  data : {
		    "id":id,
		    "topAppkey":newtopAppkey
		  },
		  dataType : "json",
		  async:false,
		  success : function(data) {
		    if(data==true){
				 alert("给该商家绑定topAppkey成功！");
				 location.reload();
		    }else{
				 alert("给该商家绑定topAppkey失败！");
				 location.reload();
		    }
		  },
		  error: function(XMLHttpRequest, textStatus, errorThrown){
			art.dialog.alert('给该商家绑定topAppkey失败:' + XMLHttpRequest.responseText);
		  }
	});
}
function authorize(id,topAppkey,status){
if(topAppkey==""){
alert("请先给商家绑定topAppkey！");
return;
}
if(status=="1"){
status="2";
}else if(status=="-1"){
status="0";
}else{
status="-1";
}
saveResult(id,topAppkey,status);
}

function saveResult(id,appkey,status){
   $.ajax({
		  type : "POST",
		  url : "${BasePath}/yitiansystem/merchants/taobao/update_taobao_authorize.sc",
		  data : {
		    "id":id,
		    "topAppkey":appkey,
		    "status":status
		  },
		  dataType : "json",
		  async:false,
		  success : function(data) {
		    if(data==true){
				 alert("保存授权管理信息成功！");
				 location.reload();
		    }else{
				 alert("保存授权管理信息失败！");
		    }
		  },
		  error: function(XMLHttpRequest, textStatus, errorThrown){
			art.dialog.alert('保存授权管理信息失败:' + XMLHttpRequest.responseText);
		  }
	});
}

function deleteAuthorize(id){
   $.ajax({
		  type : "POST",
		  url : "${BasePath}/yitiansystem/merchants/taobao/delete_taobao_authorize.sc",
		  data : {
		    "id":id
		  },
		  dataType : "json",
		  async:false,
		  success : function(data) {
		    if(data==true){
				 alert("删除授权管理信息成功！");
				 location.reload();
		    }else if(data=="warn"){
				 alert("已授权的商家授权记录不能删除！");
		    }else{
				 alert("删除授权管理信息失败！");
		    }
		  },
		  error: function(XMLHttpRequest, textStatus, errorThrown){
			art.dialog.alert('删除授权管理信息失败:' + XMLHttpRequest.responseText);
		  }
	});
}
</script>