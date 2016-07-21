<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-合同列表</title>
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
 
 //查看附件
 function downContract(attachment){
   if(attachment==""){
	     alert("没有上传附件,不能进行下载!");
	     return;
   }else{
        location.href="${BasePath}/merchants/login/downMerchantsContractFile.sc?fileName="+attachment ;
   }
 }
 </script>
 </head>
<body>
	
	
<div class="main_container">
			<div class="normal_box">
				<p class="title site">当前位置：商家中心 &gt; 设置 &gt; 合同列表</p>
					<div class="tab_panel">
							<ul class="tab">
							<li onclick="baseInfo();"><span>基本信息</span></li>
							<li onclick="contract();"  class="curr"><span>合同列表</span></li>
							<li onclick="linkMan();"><span>联系人列表</span></li>
							</ul>
						<div class="tab_content">
							<!--列表start-->
<form name="queryVoform" id="queryVoform" action="${BasePath}/merchants/login/to_supplierContract.sc" method="post">					
						<table class="list_table">
							<thead>
								<tr>
								<th>合同编号</th>
								<th>状态</th>
								<th>结算方式</th>
								<th>生效时间</th>
								<th>失效时间</th>
								<th>操作</th>
							</tr>
							</thead>
							<tbody>
							   <#if pageFinder??>
							    <#if pageFinder.data??>
							       <#list pageFinder.data as item>
								     <tr>
								      <td>${item['contract_no']!""}</td>
								      <td><#if item['is_valid']??&&item['is_valid']==1>正常
							          <#elseif item['is_valid']??&&item['is_valid']==2>锁定
							          <#elseif item['is_valid']??&&item['is_valid']==-1>关闭
							          </#if></td>
							          <td><#if item['clearing_form']??&&item['clearing_form']==1>底价结算
							          <#elseif item['clearing_form']??&&item['clearing_form']==2>扣点结算
							          <#elseif item['clearing_form']??&&item['clearing_form']==3>配折结算
							          <#elseif item['clearing_form']??&&item['clearing_form']==4>促销结算
							          </#if></td>
								      <td>${item['effective_date']!''}</td>
								      <td>${item['failure_date']!''}</td>
								      <td><a href="javascript:;" onclick="downContract('${item['attachment']!''}');">查看附件</a></td>
								     </tr>
								   </#list>
							     </#if>
							     <#else>
								<tr>
									<td class="td-no" colspan="6">暂无数据!</td>
								</tr>
							    </#if>
							</tbody>
					</table>
						</table>
						</form>
						<!--列表end-->
						<!--分页start-->
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