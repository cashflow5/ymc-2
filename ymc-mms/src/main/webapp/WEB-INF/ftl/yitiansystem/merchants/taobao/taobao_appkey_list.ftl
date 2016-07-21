<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#include "../merchants-include.ftl">
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<title>优购商城--商家后台</title>
</head>
<body>
<div class="container">
	<!--工具栏START--> 
	<div class="toolbar">
		<div class="t-content">
			<div class="btn" onclick="addTopAppkey();">
				<span class="btn_l"></span>
	        	<b class="ico_btn add"></b>
	        	<span class="btn_txt">添加淘宝Appkey</span>
	        	<span class="btn_r"></span>
        	</div> 
		</div>
	</div>
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li  class="curr">
				  <span><a href="#" class="btn-onselc">淘宝Appkey管理</a></span>
				</li>
			</ul>
		</div>
 <div class="modify"> 
     <form action="${BasePath}/yitiansystem/merchants/taobao/taobao_appkey.sc" name="queryForm" id="queryForm" method="post">        	
              	<div class="add_detail_box">
					<p>
						<span>
						  <label>淘宝APPKEY：</label>
						  <input style="width:150px;" type="text" id="topAppkey" name="topAppkey" value="<#if vo??&&vo.topAppkey??>${vo.topAppkey}</#if>">
						</span>
						<span>
						  <label>淘宝APPKEY名称：</label>
						  <input style="width:150px;" type="text" id="topAppName" name="topAppName" value="<#if vo??&&vo.topAppName??>${vo.topAppName}</#if>">
						</span>
						<span>
						  <label>操作人：</label>
						  <input style="width:150px;" type="text" id="operater" name="operater" value="<#if vo??&&vo.operater??>${vo.operater}</#if>">
						</span>&nbsp;&nbsp;&nbsp;
						<input type="button" value="搜索" onclick="queryData();" class="yt-seach-btn" />&nbsp;&nbsp;&nbsp;
					</p>
				</div>         	
              	</form>
                <table cellpadding="0" cellspacing="0" class="list_table">
                		<thead>
                        <tr>
                        <th style="text-align: center;">淘宝App名称</th>
                        <th style="text-align: center;">淘宝APPKEY</th>
                        <th style="text-align: center;">淘宝SECRET</th>
                        <th style="text-align: center;">是否可用</th>
                        <th style="text-align: center;">创建人</th>
                        <th style="text-align: center;">创建时间</th>
                        <th style="text-align: center;">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#if pageFinder??&&pageFinder.data?? >
                        <#list pageFinder.data as item >
                		<tr>
                		   <td class="ft-cl-r" style="text-align: center;">${item['topAppName']!''}</td>
                		   <td class="ft-cl-r" style="text-align: center;">${item['topAppkey']!''}</td>
                		   <td class="ft-cl-r" style="text-align: center;">${item['topSecret']!''}</td>
                		   <td class="ft-cl-r" style="text-align: center;">
                           <#if item.isUseble??&&item.isUseble==1>是<#else>否</#if></td>
                		   <td class="ft-cl-r" style="text-align: center;">${item['operater']!''}</td>
                		   <td class="ft-cl-r" style="text-align: center;">${item['operated']!''}</td>
                		   <td class="ft-cl-r" style="text-align: center;">
                				<a onclick="authorize('${item['id']!""}','${item['topAppName']!''}','${item['topAppkey']!''}','${item['topSecret']!''}','${item['isUseble']!''}');" href="javascript:void(0)">修改</a>
                				<a href="${BasePath}/yitiansystem/merchants/taobao/export_taobao_txt.sc?topAppkey=${item['topAppkey']!''}" target="_blank" title="处于禁用状态的店铺将不会被导出">导出txt</a>
                		   </td>
                		 </tr>
                	     </#list>
                        	<#else>
                        	<tr>
                        	<td colSpan="6">
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
//根据条件查询
function queryData(){
  document.queryForm.submit();
}
function authorize(id,topAppName,topAppkey,topSecret,isUseble){
var content='<span><label style="width:100px; display:inline-block; text-align:right;">淘宝APPKEY：</label><label>'+topAppkey+'</label></br></br>'
+'<label style="width:100px; display:inline-block; text-align:right;">淘宝App名称：</label><input style="width:220px;" id="topAppName_'+id+'" name="topAppName_'+id+'" value="'+topAppName+'" /></br></br>'
+'<label style="width:100px; display:inline-block; text-align:right;">淘宝topSecret：</label><input style="width:220px;" id="topSecret_'+id+'" name="topSecret_'+id+'" value="'+topSecret+'" /></br></br>'
+'<label style="width:100px; display:inline-block; text-align:right;">是否可用：</label><input type="radio" name="isUseble_'+id+'" value="1" '+(isUseble!='0'?'checked':'')+'/>可用<input type="radio" name="isUseble_'+id+'" value="0" '+(isUseble=='0'?'checked':'')+'/>不可用</br>'
+'<label id="ygdg_warn" style="display:inline-block;color:#ff0000;"></label></span>';
  	var dialog=ygdg.dialog({
    title: '淘宝appkey修改',
	content: content,
	width: 480,
    height:240,
    lock:true,
	button: [{
             name: '保存',
             callback: function () {
               var _name="isUseble_"+id;
               var isUseble_value= $('input[name="'+_name+'"]:checked').val();
               var newtopSecret=$("#topSecret_"+id).val();
               var newtopAppName=$("#topAppName_"+id).val();
               if(newtopSecret!=""&&newtopAppName!=""){
                 $("#ygdg_warn").hide();
                 if(topSecret==newtopSecret&&isUseble==isUseble_value&&topAppName==newtopAppName){
                    alert("您没有更新数据，选择取消按钮可以退出！");
                    return false;
                 }
                 updateResult(id,newtopAppName,newtopSecret,isUseble_value);
               }else if(newtopSecret==""){
                 $("#ygdg_warn").show();
                 $("#ygdg_warn").html("<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;淘宝topSecret不能为空！");
                 $("#topSecret_"+id).select();
                 $("#topSecret_"+id).focus();
                 return false;
               }else if(newtopAppName==""){
                 $("#ygdg_warn").show();
                 $("#ygdg_warn").html("<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;淘宝topAppKey名称不能为空！");
                 $("#topAppName_"+id).select();
                 $("#topAppName_"+id).focus();
                 return false;
               }
            },
               focus: true
        	},
        	{
               name: '取消'
        	}]
   	});
}

function updateResult(id,topAppName,topSecret,isUseble){
   $.ajax({
		  type : "POST",
		  url : "${BasePath}/yitiansystem/merchants/taobao/update_taobao_appkey.sc",
		  data : {
		    "id":id,
		    "topSecret":topSecret,
		    "isUseble":isUseble,
		    "topAppName":topAppName
		  },
		  dataType : "json",
		  async:false,
		  success : function(data) {
		    if(data==true){
				 alert("保存淘宝appkey信息成功！");
				 location.reload();
		    }else{
				 alert("保存淘宝appkey信息失败！");
		    }
		  },
		  error: function(XMLHttpRequest, textStatus, errorThrown){
			art.dialog.alert('保存授权管理信息失败:' + XMLHttpRequest.responseText);
		  }
	});
}

function addTopAppkey(){
var content='<span><label style="width:100px; display:inline-block; text-align:right;">淘宝App名称：</label><input style="width:220px;" id="topAppName" name="topAppName"/></br></br>'
+'<label style="width:100px; display:inline-block; text-align:right;">淘宝topAppkey：</label><input style="width:220px;" id="topAppkey" name="topAppkey"/></br></br>'
+'<label style="width:100px; display:inline-block; text-align:right;">淘宝topSecret：</label><input style="width:220px;" id="topSecret" name="topSecret" value="" /></br></br>'
+'<label style="width:100px; display:inline-block; text-align:right;">是否可用：</label><input type="radio" name="isUseble" value="1" checked/>可用<input type="radio" name="isUseble" value="0"/>不可用</br>'
+'<label id="ygdg_warn" style="display:inline-block;color:#ff0000;"></label></span>';
  	var dialog=ygdg.dialog({
    title: '添加新的淘宝appkey',
	content: content,
	width: 480,
    height:240,
	button: [{
             name: '保存',
             callback: function () {
               var isUseble= $('input[name="isUseble"]:checked').val();
               var topSecret=$("#topSecret").val();
               var topAppkey=$("#topAppkey").val();
               var topAppName=$("#topAppName").val();
               if(topAppkey!=""&&topSecret!=""&&topAppName!=""){
                 $("#ygdg_warn").hide();
                 saveResult(topAppName,topAppkey,topSecret,isUseble);
               }else if(topAppkey==""){
                 $("#ygdg_warn").show();
                 $("#ygdg_warn").html("<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;淘宝topAppkey不能为空！");
                 $("#topAppkey").select();
                 $("#topAppkey").focus();
                 return false;
               }else if(topSecret==""){
                 $("#ygdg_warn").show();
                 $("#ygdg_warn").html("<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;淘宝topSecret不能为空！");
                 $("#topSecret").select();
                 $("#topSecret").focus();
                 return false;
               }else if(topAppName==""){
                 $("#ygdg_warn").show();
                 $("#ygdg_warn").html("<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;淘宝topAppName不能为空！");
                 $("#topAppName").select();
                 $("#topAppName").focus();
                 return false;
               }
            },
               focus: true
        	},
        	{
               name: '取消'
        	}]
   	});
}

function saveResult(topAppName,topAppkey,topSecret,isUseble){
   $.ajax({
		  type : "POST",
		  url : "${BasePath}/yitiansystem/merchants/taobao/add_taobao_appkey.sc",
		  data : {
		    "topAppkey":topAppkey,
		    "topSecret":topSecret,
		    "isUseble":isUseble,
		    "topAppName":topAppName
		  },
		  dataType : "json",
		  async:false,
		  success : function(data) {
		    if(data==true){
				 alert("保存淘宝appkey信息成功！");
				 location.reload();
		    }else if(data=="exist"){
				 alert("系统中已存在相同的淘宝AppKey！");
		    }else{
				 alert("保存淘宝appkey信息失败！");
		    }
		  },
		  error: function(XMLHttpRequest, textStatus, errorThrown){
			alert('保存授权管理信息失败:' + XMLHttpRequest.responseText);
		  }
	});
}
</script>