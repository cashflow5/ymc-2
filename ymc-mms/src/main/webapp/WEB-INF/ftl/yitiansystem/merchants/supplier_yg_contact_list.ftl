<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>

<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/jquery-1.3.2.min.js"></script>
<script type="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
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
			<div class="btn" onclick="addContact();">
				<span class="btn_l"></span>
	        	<b class="ico_btn add"></b>
	        	<span class="btn_txt">添加货品负责人</span>
	        	<span class="btn_r"></span>
        	</div> 
		</div>
	</div>
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li  class="curr">
				  <span><a href="#" class="btn-onselc">货品负责人列表</a></span>
				</li>
			</ul>
		</div>
 <div class="modify"> 
     <form action="${BasePath}/yitiansystem/merchants/supplierContact/to_contact_list.sc" name="queryForm" id="queryForm" method="post"> 
  			  	<div class="wms-top">
  			  	     <p>
                     <label>姓&nbsp;&nbsp;&nbsp;&nbsp;名：</label>
                     <input type="text" name="userName" value="<#if supplierYgContact??&&supplierYgContact.userName??>${supplierYgContact.userName!""}</#if>"/>
                     <label>商家名称：</label>
                     <input type="text" name="merchantName" value="<#if merchantName????>${merchantName!""}</#if>"/>
                     <label>商家品牌：</label>
                     <input type="text" name="brand" value="<#if brand??>${brand!""}</#if>"/>
                     </p>
                     <p>
                     <label>联系电话：</label>
                     <input type="text" name="telePhone" value="<#if supplierYgContact??&&supplierYgContact.telePhone??>${supplierYgContact.telePhone!""}</#if>"/>
                     <label>邮箱地址：</label>
                     <input type="text" name="email"   value="<#if supplierYgContact??&&supplierYgContact.email??>${supplierYgContact.email!""}</#if>"/>
                     <input type="button" value="搜索" onclick="queryMerchantConsumable();" class="yt-seach-btn" />&nbsp;&nbsp;&nbsp;
                     </p>
              	</div>
              	
              	</form>  
                <table cellpadding="0" cellspacing="0" class="list_table" width="100%">
                		<thead>
                        <tr>
                        <th>姓名</th>
                        <th>邮箱地址</th>
                        <th>电话</th>
                        <th>手机</th>
                        <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                       <#if pageFinder??&&pageFinder.data?? >
                    	<#list pageFinder.data as item >
	                        <tr>
	                          <td>${item.userName!""}</td>
	                           <td>${item.email!""}</td>
	                           <td>${item.telePhone!""}</td>
	                           <td>${item.mobilePhone!""}</td>
	                           <td>
	                           <a href="#" onclick="updateContact('${item.userId!''}')">修改个人信息</a>
	                           <a href="#" onclick="manageSupplier('${item.userId!''}')">商家管理</a>
	                           </td>
	                        </tr>
                        </#list>
                        	<#else>
                        	<tr>
                        	<td colSpan="10">抱歉，没有您要找的数据 </td>
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
//删除耗材资料
function deleteMerchantConsumable(userId){
 if(id!=""){
   if(confirm("是否真的删除!")){
	$.ajax({ 
		type: "post", 
		url: "${BasePath}/yitiansystem/merchants/businessorder/delete_merchantConsumable.sc?userId=" + userId, 
		success: function(dt){
			if("success"==dt){
			   alert("删除成功!");
			   refreshpage();
			}else{
			   alert("删除失败!");
			   refreshpage();
			}
		} 
	});
   }
 }
}

function manageSupplier(userId){
	ygdgDialog.open("${BasePath}/yitiansystem/merchants/supplierContact/showSupplierInfo.sc?userId="+userId, {
		id:"dialog",
		width: 800,
		height: 600,
		title: '商家管理',
		close:function(){
			document.location.reload();
		}
	});
}

function updateContact(userId){
	ygdgDialog.open("${BasePath}/yitiansystem/merchants/supplierContact/to_save_yg_contact.sc?userId="+userId, {
		id:"dialog",
		width: 650,
		height: 400,
		title: '保存货品负责人',
		close:function(){
			document.location.reload();
		}
	});
}

//跳转到添加耗材资料页面
function addContact(id){
	ygdgDialog.open("${BasePath}/yitiansystem/merchants/supplierContact/to_save_yg_contact.sc?id="+id, {
		id:"dialog",
		width: 650,
		height: 400,
		title: '保存货品负责人',
		close:function(){
			document.location.reload();
		}
	});
}

function closeDialog(){
	ygdg.dialog({id: "dialog"}).close();
}

//查询
function queryMerchantConsumable(){
  document.queryForm.submit();
}
$(function(){
$('#startTime').calendar({maxDate:'#endTime',format:'yyyy-MM-dd'});
$('#endTime').calendar({minDate:'#startTime',format:'yyyy-MM-dd'});
});
</script>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
