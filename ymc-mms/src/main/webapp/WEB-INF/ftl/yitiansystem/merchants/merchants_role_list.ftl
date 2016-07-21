<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>

<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/jquery-1.3.2.min.js"></script>
<script type="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/js.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/order.js"></script>
<title>优购商城--商家后台</title>
<!-- 日期 -->
<script src="${BasePath}/js/common/form/datepicker/WdatePicker.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
</script>
<body>
<div class="container">
	<!--工具栏start--> 
	<div class="toolbar">
		<div class="t-content">
			<div class="btn" onclick="addMerchantsRole();">
				<span class="btn_l"></span>
	        	<b class="ico_btn add"></b>
	        	<span class="btn_txt">添加权限组</span>
	        	<span class="btn_r"></span>
        	</div> 
		</div>
	</div>
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li  class="curr">
				  <span><a href="#" class="btn-onselc">权限组列表</a></span>
				</li>
			</ul>
		</div>
 <div class="modify"> 
     <form action="${BasePath}/yitiansystem/merchants/businessorder/to_merchants_role_list.sc" name="queryForm" id="queryForm" method="post"> 
  			  	<div class="wms-top">
                     <label>权限组名称：</label>
                     <input type="text" name="roleName" id="roleName" value="<#if merchantsRole??&&merchantsRole.roleName??>${merchantsRole.roleName!""}</#if>"/>&nbsp;&nbsp;&nbsp;
                     <label>配置权限项：</label>
                     <input type="text" name="authorityName" id="authorityName" value="<#if authorityName??>${authorityName!""}</#if>"/>&nbsp;&nbsp;&nbsp;
                    <input type="button" value="搜索" onclick="queryMerchantsRole();" class="yt-seach-btn" />&nbsp;&nbsp;&nbsp;
              	</div>
              	</form>
                <table cellpadding="0" cellspacing="0" class="list_table">
                		<thead>
                        <tr>
                        <th width="300px">权限组名称</th>
                        <th width="150px">创建时间</th>
                        <th>备注</th>
                        <th width="120px">操作人</th>
                        <th width="50px">状态</th>
                        <th width="120px">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                       <#if pageFinder??&&pageFinder.data?? >
                    	<#list pageFinder.data as item >
	                        <tr>
		                        <td>${item['role_name']!""}</td>
		                        <td><#if item['create_time']?? >${item['create_time']!datetime}</#if></td>
		                        <td>${item['remark']!""}</td>
		                        <td>${item['operator']!""}</td>
		                        <td><#if item['status']??&&(item['status']=="1") >启用<#else>禁用</#if></td>
		                        <td>
		                        <a href="#" onclick="changeStatus('${item['id']!''}')"><#if item['status']??&&(item['status']=="1") >禁用<#else>启用</#if></a>
		                        <a href="#" onclick="updateRole('${item['id']!''}')">修改</a>
		                        <a href="#" onclick="deleteRole('${item['id']!''}')">删除</a>
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
			  	<#if pageFinder ??><#import "../../common.ftl" as page>
			  		<@page.queryForm formId="queryForm"/></#if>
			  </div>
              <div class="blank20"></div>
          </div>
</div>
</body>
</html>
<script type="text/javascript">
//删除商家角色
function deleteRole(id){
  if(id!=""){
      if(confirm("是否真的删除!")){
		$.ajax({ 
			type: "post", 
			url: "${BasePath}/yitiansystem/merchants/businessorder/delete_role.sc?id=" + id, 
			success: function(dt){
				if("success"==dt){
				   alert("删除成功!");
				   refreshpage();
				}else{
				   alert("删除失败,该角色可能已被分配使用!");
				   refreshpage();
				}
			} 
		});
   }
  }
}

//跳转到修改角色页面
function updateRole(id){
      openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_update_merchants_role.sc?rid="+id,700,550,"修改商家角色");
}

//修改状态
function changeStatus(id){
    $.ajax({ 
		type: "post", 
		url: "${BasePath}/yitiansystem/merchants/businessorder/update_roleStatus.sc?id="+id, 
		success: function(dt){
			if("success"==dt){
			   refreshpage();
			}else{
			   alert("修改状态失败!");
			   refreshpage();
			}
		} 
	});
}
//根据条件查询商家用户信息
function queryMerchantsRole(){
   document.queryForm.method="post";
   document.queryForm.submit();
}
function addMerchantsRole(){
   openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_add_merchants_role.sc",700,550,"添加权限组");
}
</script>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
