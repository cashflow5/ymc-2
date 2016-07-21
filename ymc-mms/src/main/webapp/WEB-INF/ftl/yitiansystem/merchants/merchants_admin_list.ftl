<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>

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
			<div class="btn" onclick="addMerchantYougouAdmin();">
				<span class="btn_l"></span>
	        	<b class="ico_btn add"></b>
	        	<span class="btn_txt">添加商家优购管理员</span>
	        	<span class="btn_r"></span>
        	</div> 
		</div>
	</div>
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li>
				  <span><a href="${BasePath}/yitiansystem/merchants/businessorder/to_merchants_user_list.sc" class="btn-onselc">商家用户列表</a></span>
				</li>
				<li class="curr">
				  <span><a href="#" class="btn-onselc">管理员用户列表</a></span>
				</li>
				<li>
				  <span><a href="${BasePath}/yitiansystem/merchants/businessorder/to_merchants_businessAdmin_list.sc" class="btn-onselc">业务管理员用户列表</a></span>
				</li>
			</ul>
		</div>
 <div class="modify"> 
     <form action="${BasePath}/yitiansystem/merchants/businessorder/to_merchants_admin_list.sc" name="queryForm" id="queryForm" method="post">
  			  	<div class="wms-top">
                  	<label>管理员名称：</label>
                    <input type="text" name="userName" id="userName" value="<#if merchantUser??&&merchantUser.userName??>${merchantUser.userName!""}</#if>"/>&nbsp;&nbsp;&nbsp;
                    <label>登录帐号：</label>
                    <input type="text" name="loginName" id="loginName" value="${(merchantUser.loginName)!""}"/>&nbsp;&nbsp;&nbsp;
                    <input type="button" value="搜索" onclick="queryMerchantsUser();" class="yt-seach-btn" />&nbsp;&nbsp;&nbsp;
              	</div>
              	</form>
                <table cellpadding="0" cellspacing="0" class="list_table">
                		<thead>
                        <tr>
                       	<th>管理员名称</th>
                        <th>登录名称</th>
                        <th>邮箱</th>
                        <th>创建人</th>
                        <th>创建时间</th>
                        <th>状态</th>
                        <th>备注</th>
                        <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                       <#if pageFinder??&&pageFinder.data?? >
                    	<#list pageFinder.data as item >
	                        <tr>
	                        	<td>${item['userName']!""}</td>
		                        <td>${item['loginName']!""}</td>
		                        <td>
		                        	<div id="${item.id}">
	                        			<img src="${BasePath}/images/finance/bt_edit.gif" onclick="to_updateEmail('${item.id}');" title="修改绑定邮箱" style="cursor:pointer;"/><span id="${item.id}_span">${item['email']!""}</span><#if item.email??>[<#if item.emailstatus == 1><span style="color:#228B22;">已激活</span><#else><span style="color:#EEC900;">未激活</span></#if>]</#if>
	                        		</div>
	                        		<div id="${item.id}_input" style="display:none;">
	                        			<input type="text" id="${item.id}_input_text" value="${item.email!''}" />&nbsp;<img src="${BasePath}/images/common/icon_save.png" onclick="updateEmail('${item.id}');" title="保存" style="cursor:pointer;"/>
	                        		</div>
		                        </td>
		                        <td>${item['creater']!""}</td>
		                        <td>${item['createTime']!""}</td>
		                        <td>
		                        	<#if item['status']??&&item['status']==1>启用
			                        <#elseif item['status']?? && item['status']==0>锁定</#if></td>
		                        <td>${item['remark']!""}</td>
		                        <td>
			                        <a href="#" onclick="deletMerchantUser('${item['id']!''}')">删除</a>
		                         	<a href="#" onclick="updateMerchantState('${item['id']!''}','${item['status']!''}')"><#if item['status']??&&item['status']==1>锁定<#elseif item['status']??&&item['status']==0>启用</#if></a>
			                        <#if (merchantUser.isYougouAdmin)??&&merchantUser.isYougouAdmin==1>
			                         	<a href="#" onclick="toEditMerchantYougouAdmin('${item['id']!''}')">修改</a>
				                        <!--<a href="#" onclick="assignAuthority('${item['id']!''}')">设置菜单</a>-->
				                        <a href="#" onclick="setMerchants('${(item.id)!''}', '${item['loginName']!''}')">设置商家</a>
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
			  	<#if pageFinder ??><#import "../../common.ftl" as page>
			  		<@page.queryForm formId="queryForm"/></#if>
			  </div>
              <div class="blank20"></div>
          </div>
</div>
</body>
</html>
<script type="text/javascript">
//修改账户状态
 function updateMerchantState(id,status){
   if(id!="" && status!=""){
	    if(status==1){
	       if(confirm("确定锁定吗?")){
	         openwindow('${BasePath}/yitiansystem/merchants/businessorder/update_merchantState.sc?state=0&id='+id,800,400,'修改账号状态');
	       }
	    }else{
	      if(confirm("确定启用吗?")){
	         openwindow('${BasePath}/yitiansystem/merchants/businessorder/update_merchantState.sc?state=1&id='+id,800,400,'修改账号状态');
	       }
	    }
    }
 }

//根据条件查询商家用户信息
function queryMerchantsUser(){
   document.queryForm.method="post";
   document.queryForm.submit();
}
//跳转到分配权限页面
function assignAuthority(id){
   openwindow("${BasePath}/yitiansystem/merchants/businessorder/assignAuthority.sc?yougouAdminId="+id,600,700,"分配商家权限");
}
//跳转到设置商家页面
function setMerchants(merchantUserId, loginName) {
	openwindow("${BasePath}/yitiansystem/merchants/businessorder/toHadYougouAdminMerchant.sc?merchantUserId="+merchantUserId+"&loginName="+loginName,900,600,"设置商家");
}
//删除帐号信息
function deletMerchantUser(id){
  if(id!=""){
   if(confirm("是否真的删除!")){
	$.ajax({ 
		type: "post", 
		url: "${BasePath}/yitiansystem/merchants/businessorder/delete_merchant_user.sc?id=" + id, 
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

function addMerchantYougouAdmin(){
   openwindow("${BasePath}/yitiansystem/merchants/businessorder/toAddMerchantYougouAdmin.sc",360,300,"添加商家优购管理员");
}
function toEditMerchantYougouAdmin(id){
   openwindow("${BasePath}/yitiansystem/merchants/businessorder/toEditMerchantYougouAdmin.sc?merchantUserId="+id,360,300,"编辑商家优购管理员");
}

function to_updateEmail(id) {
	$('#'+id).hide();
	$('#'+id + '_input').show();
}

function updateEmail(id) {
	var _email = $('#'+id+'_input_text').val();
	if (!verifyAddress(_email)) {
		alert('请输入正确的邮箱地址!');
		return false;
	}
	$.ajax({
		type: "POST",
		url: "${BasePath}/yitiansystem/merchants/businessorder/updateEmail.sc",
		data: {id:id, email:_email},
		success: function(msg){
			if ("1" == msg) { 
				alert('修改成功');
				$('#'+id+'_span').html(_email); 
				$('#'+id + '_input').hide();
				$('#'+id).show();
			} 
			else {	alert('修改失败'); }
		}
	});
}

function verifyAddress(email){
	var pattern =/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/; 
	var flag = pattern.test(email);
	return flag;
} 
</script>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
