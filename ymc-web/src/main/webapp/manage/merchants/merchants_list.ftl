<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-子账号列表</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css?${style_v}"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
<script type="text/javascript">

var addMerchantsSubmit=function()
{
	var iframe = this.iframe.contentWindow;
	iframe.addMerchants();
	return false;
}


var modifyMerchantsSubmit=function()
{
	var iframe = this.iframe.contentWindow;
	iframe.updateMerchants();
	return false;
}

var addMerchatUserAuthoritySubmit=function()
{
	var iframe = this.iframe.contentWindow;
	iframe.addRole();
	return false;
}

 //修改账户状态
 function updateStatus(id,status){
   if(id!="" && status!=""){
	    if(status==1){
	       if(confirm("确定锁定吗?")){
	         openwindow('${BasePath}/merchants/login/updateStatus.sc?status=0&id='+id,800,400,'修改状态');
	       }
	    }else{
	      if(confirm("确定启用吗?")){
	         openwindow('${BasePath}/merchants/login/updateStatus.sc?status=1&id='+id,800,400,'修改状态');
	       }
	    }
    }
 }
 //根据条件进行搜索
 function selectMerchants(){
     document.queryVoform.submit();
 }
 //跳转到修改帐号信息页面
 function updateMerchants(id){
   if(id!=""){
     openwindow('${BasePath}/merchants/login/inits_merchants.sc?id='+id,400,300,'修改帐号',modifyMerchantsSubmit);
   }
 }
 
 //跳转到添加帐号信息页面
 function addMerchtants(){
    openwindow('${BasePath}/merchants/login/to_addMerchants.sc',400,300,'添加帐号',addMerchantsSubmit);
 }
 
 function merchantsList()
 {
   location.href="${BasePath}/merchants/login/to_merchants.sc";
 }
 
 //给子账户分配权限
 function distributeAuthority(supplierCode,uid){
    openwindow('${BasePath}/merchants/login/to_distributeAuthority.sc?supplierCode='+supplierCode+'&uid='+uid,470,500,'分配子账户权限',addMerchatUserAuthoritySubmit);
 }

</script>
</head>

<body>
 

<div class="main_container">
	<div class="normal_box">
		<p class="title site">当前位置：商家中心 &gt; 设置 &gt; 子账号列表</p>
		<div class="tab_panel">
			<ul class="tab">
				<li class="curr">
					<span>账号列表</span>
				</li>
				<#if isAdministrator?? && isAdministrator == 1>
					<li class="tab_fr"> <a  class="button" id="addAccount"  onclick="addMerchtants()">
						<span>添加账号</span>
						</a> </li>
				</#if>
			</ul>
			<div class="tab_content"> 
				
				<!--搜索start-->
				<div class="search_box">
					<form name="queryVoform" id="queryVoform" action="${BasePath}/merchants/login/to_merchants.sc" method="post">
						<p>
							<span>
							<label>账号：</label>
							<input type="text" name="loginName" id="loginName"  value="<#if merchant??&&merchant.loginName??>${merchant.loginName!''}</#if>" class="inputtxt" /></span>
							<span>
							<label style="width:100px;">真实姓名：</label>
							<input type="text" name="userName" id="userName" value="<#if merchant??&&merchant.userName??>${merchant.userName!''}</#if>" "  class="inputtxt" /></span>
							<span>
							<label>手机：</label>
							<input type="text"  class="inputtxt" name="mobileCode" id="mobileCode" value="<#if merchant??&&merchant.mobileCode??>${merchant.mobileCode!''}</#if>"/></span>
							<span>
							<label>状态：</label>
							<select name="status" id="status">
								<option value="">请选择状态</option>
								<option value="1" <#if merchant??&&merchant.status??&&merchant.status==1>selected</#if>>启用
								</option>
								<option value="0" <#if merchant??&&merchant.status??&&merchant.status==0>selected</#if>>锁定
								</option>
							</select>
							</span>
							<span><a class="button" id="mySubmit" onclick="selectMerchants();">
							<span>搜索</span>
							</a></span>
						</p>
					</form>
				</div>
				<!--列表start-->
				<table class="list_table">
					<thead>
						<tr>
							<th width="100">账号</th>
							<th width="80">真实姓名</th>
							<th width="100">手机号码</th>
							<th width="100">添加时间</th>
							<th width="100">账号状态</th>
							<th width="150">备注</th>
							<th width="80">操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
						 <#if pageFinder?? && (pageFinder.data)?? > 
							<#list pageFinder.data as item>
						<tr>
							<td>${item['login_name']!""}</td>
							<td>${item['user_name']!""}</td>
							<td>${item['mobile_code']!""}</td>
							<td>${item['create_time']!''}</td>
							<td> <#if item['status']??&&item['status']==1>启用<#elseif item['status']??&&item['status']==0>锁定</#if></td>
							<td>${item['remark']!""}</td>
							<td>
								<#if isAdministrator??>
									<#if isAdministrator == 1 || (loginName?? && loginName == item['login_name'])>
										<a href="javascript:;" onclick="updateMerchants('${item['id']}');">修改</a>
									</#if>
									<#if isAdministrator == 1 && item['is_administrator'] == 0>
										<#if item['status']?? && item['status'] == 1>
											<a href="javascript:;" onclick="updateStatus('${item['id']}', '${item['status']}');">锁定</a>
										<#elseif item['status']?? && item['status'] == 0>
											<a href="javascript:;" onclick="updateStatus('${item['id']}', '${item['status']}');">启用</a>
										</#if>
										<a href="javascript:;" onclick="distributeAuthority('${item['supplier_code']}', '${item['id']}');">分配权限</a>
									</#if>
								</#if>
							</td>
						</tr>
					</#list>
					<#else>
					<tr>
						<td class="td-no" colspan="7">暂无数据!</td>
					</tr>
					</#if>
					</tr>
					</tbody>
					
				</table>
				<!--列表end--> 
				<#if pageFinder ??>
					<!--分页start-->
					     <div class="page_box">
							<#import "/manage/widget/common.ftl" as page>
							<@page.queryForm formId="queryVoform"/>
						 </div>
					<!--分页结束--> 
				</#if>
			</div>
		</div>
	</div>
	 
	 </div>
</body>
</html>