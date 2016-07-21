<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-联系人列表</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
<script type="text/javascript">
 //基本信息
 function baseInfo(){
  location.href="${BasePath}/merchants/login/to_MerchantsUser.sc" ;
 }
 
 //合同列表
 function contract(){
   location.href="${BasePath}/merchants/login/to_supplierContract.sc" ;
 }
 
 //联系人列表
 function linkMan(){
   location.href="${BasePath}/merchants/login/to_supplierLinkMan.sc" ;
 }
 
 //跳转到修改联系人列表
 function updateLinkMan(id){
    openwindow('${BasePath}/merchants/login/to_updateLinkMan.sc?id='+id,800,400,'修改联系人');
 }
 </script>
 </head>
<body>
	
	
<div class="main_container">
			<div class="normal_box">
				<p class="title site">当前位置：商家中心 &gt; 设置 &gt; 联系人列表</p>
					<div class="tab_panel">
							<ul class="tab">
							<li onclick="baseInfo();"><span>基本信息</span></li>
							<li onclick="contract();"><span>合同列表</span></li>
							<li onclick="linkMan();"  class="curr"><span>联系人列表</span></li>
							<li class="tab_fr">
							<a class="button" id="addLinkMan" ><span>添加联系人</span></a>
							</li>
							</ul>
						<div class="tab_content">
							<!--列表start-->
				<form name="queryVoform" id="queryVoform" action="${BasePath}/merchants/login/to_supplierLinkMan.sc" method="post">					
						<table class="list_table">
							<thead>
								<tr>
								<th width="100">姓名</th>
								<th width="80">类型</th>
								<th width="100">电话号码</th>
								<th width="100">手机号码</th>
								<th width="100">传真号码</th>
								<th width="150">电子邮箱</th>
								<th width="">联系地址</th>
								<th width="80">操作</th>
								</tr>
							</thead>
						<tbody>
						   <#if pageFinder??>
						     <#if pageFinder.data??>
						       <#list pageFinder.data as item>
							     <tr>
							      <td>${item.contact!""}</td>
							       <td><#if item.type??&&item.type==1>业务
							          <#elseif item.type??&&item.type==2>售后
							          <#elseif item.type??&&item.type==3>仓储
							          <#elseif item.type??&&item.type==4>财务
							           <#elseif item.type??&&item.type==5>技术
							          </#if></td>
							      <td>${item.telePhone!""}</td>
							      <td>${item.mobilePhone!""}</td>
							      <td>${item.fax!""}</td>
							      <td>${item.email!""}</td>
							      <td>${item.address!""}</td>
							      <td><a href="javascript:;"  onclick="updateLinkMan('${item.id}');">修改</td>
							     </tr>
							   </#list>
						     </#if>
						     <#else>
								<tr>
									<td class="td-no" colspan="8">暂无数据!</td>
								</tr>
						    </#if>
						</tbody>
						</table>
						</form>
						<!--列表end-->
							<#if pageFinder ??>
								<div class="page_box">
									<!--分页start-->
											<#import "/manage/widget/common.ftl" as page>
											<@page.queryForm formId="queryVoform"/>
									<!--分页end-->
								</div>
							</#if>
						</div>
				</div>
			</div>
 
 </div>
	
	</div>
</body>
</html>
<script type="text/javascript">

var addSubmit=function()
{
	var iframe = this.iframe.contentWindow;
	iframe.addSupplierLinkMan();
	return false;
}


var modifySubmit=function()
{
	var iframe = this.iframe.contentWindow;
	iframe.updateSupplierLinkMan();
	return false;
}



$("#addLinkMan").click(function()
{
	openwindow("${BasePath}/merchants/login/to_addLinkMan.sc",450,280,"新增联系人",addSubmit);
})

 //跳转到修改联系人列表
 function updateLinkMan(id){
    openwindow("${BasePath}/merchants/login/to_updateLinkMan.sc?id="+id,450,280,"修改联系人",modifySubmit);
 }

</script>