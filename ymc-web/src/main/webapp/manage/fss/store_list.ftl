<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>店铺装修-品牌旗舰店-优购网</title>
<#--<link rel="stylesheet" href="${BasePath}/fss/assets/css/reset.css" />
-->

</head>
<body height="100%">

<div class="main_container">
  <div class="normal_box">
	<p class="title site">当前位置：商家中心 &gt; 品牌旗舰店 &gt; 店铺列表</p>
		 
	 <div class="tab_panel">
			<ul class="tab">
				<li class="curr">
					<span>店铺列表</span>
				</li>
			</ul>
		<div class="div-pl-table" id="div-table">
		 <table class="list_table">
			<thead>
			  	<tr>
					<th  width="12%" >店铺名称</th>
					<th width="26%"  >店铺URL</th>
					<th width="14%" >品牌</th>
					<th width="6%"  >店铺状态</th>
					<th width="6%" >审核状态</th>
					<th width="7%"  >最近发布<br/>时间 </th>
					<th width="14%"  >拒绝原因 </th>
					<th width="10%" >操作</th>
			  	</tr>
			  </thead>
			  <tbody>
				<#if storeList?? && (storeList?size > 0 )>
				  <#list storeList as item>
					  		<tr >
								
							
								<td  title="${(item.shopName)?default('')}" style="text-align:left"><input type="hidden" name="id" size="10" value="${item.shopId?default('')}" >
								  <#if (item.shopName)?length lt 15>${(item.shopName)?default('')}<#else> ${(item.shopName)?default('')[0..14]}... </#if>
								</td>
								<td style="text-align:left"> 
								   <#if item.shopURL??>
								   <#if (item.generationPage)?default('') == 'Y'>
								   <a  target="_blank" href="http://mall.yougou.com/m-${item.shopURL}.shtml">
								   http://mall.yougou.com/m-${item.shopURL}.shtml
								   </a>
								   <#else>
								   http://mall.yougou.com/m-${item.shopURL}.shtml
								   </#if> 
								   </#if> 
								</td>
								<td title="${(item.brandNames)?default('')}" style="text-align:left">
									 <#if (item.brandNames)?default("")?length lt 20>
								    	${(item.brandNames)?default('')}
								     <#else>
								        ${(item.brandNames)?default('')[0..19]}...
								     </#if>
								</td>
								<td style="text-align:left">
								 <#if (item.access)?default('N') == 'N'>  
								 已关闭
								 <#else>
								     <#if (item.shopStatus)?default(0) == 0>
								    	新建
								     <#elseif (item.shopStatus)?default(0) == 1>
								             已发布
	                                 <#elseif (item.shopStatus)?default(0) == 2>
	                                     <span class="error_tips" >装修中</span>		
	                                 <#elseif (item.shopStatus)?default(0) == 99>                                                               
	                                                                                                 已逻辑删除				                      
								     <#else> 
								                      
								     </#if>
								 </#if>
									
								</td>
								<td style="text-align:left">
								   <#if (item.auditStatus)?default(0) == 0>
								    	待提交
								     <#elseif (item.auditStatus)?default(0) == 1>
								             待审核
	                                 <#elseif (item.auditStatus)?default(0) == 2>
	                                                                                      审核被拒	
	                                 <#elseif (item.auditStatus)?default(0) == 3>                                                               
	                                                                                               审核通过		                      
								     <#else>
								                
								     </#if>
								</td>
								<td title="${(item.submitDatetime?string("yyyy-MM-dd HH:mm:ss"))!''}">${(item.submitDatetime?string("yyyy-MM-dd HH:mm:ss"))!''}</td>
								<td style="text-align:left">
								<input type="hidden" name="show" id="show" value="${item.auditNote?default("")}">
								<#if (item.auditStatus)?default(0) != 3>
									 <#if (item.auditNote)?default("")?length lt 21>
									    	${(item.auditNote)?default('')}
								     <#else>
										<a href="javascript:;" onclick="javascript:toDetail('${(item.shopId) !}')"  title="点击查看详情">
									        ${(item.auditNote)?default('')[0..20]}...
										</a>
								     </#if>
							     </#if>
								</td>
								<td style="text-align:left">
							     <#if (item.access)?default('Y') == 'Y'>  
								    <#if (item.auditStatus)?default('0') != 1>
										<a href="${BasePath}/fss/store/pageDesign/${item.shopId?default('')}.sc" target="_blank">装修</a>
								    </#if>
								  </#if>	
								  <a href="${BasePath}/fss/store/pagePreview/${item.shopId?default('')}.sc"  target="_blank">预览</a>
								</td>
					  		</tr>
				   </#list>
				</#if>
				</tbody>
			</table>
			<div class="div-pl-bt">
				<#-- 翻页标签
				< #if pageFinder?? && (pageFinder.data)??>
					<#import "../../common/common.ftl"  as page>
					<@page.queryForm formId="lyxForm" />
				</ #if>	
				 -->		  
			</div>
		</div>
		</div>
	</div>
</div>
 
<script>
    
     
	//显示详情
	 function toDetail(id){
	 	openwindow("${BasePath !}/mctfss/store/showDetail.sc?id="+id,1000,400,'日志详情6');
	 };
    
</script>
</body>
</html>
