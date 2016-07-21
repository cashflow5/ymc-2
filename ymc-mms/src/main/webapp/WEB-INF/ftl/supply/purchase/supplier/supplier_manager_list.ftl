<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,优购网,B网络营销系统,商品管理" />
<meta name="Description" content=" , ,B网络营销系统-商品管理" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<#include "../../../yitiansystem/yitiansystem-include.ftl">
<script type="text/javascript" src="${BasePath}/js/supply/supplier.js" ></script>
<title>B网络营销系统-采购管理-优购网</title>
<script type="text/javascript">	
//提交按钮所在的表单
function postForm(formId, url){
	$("#"+formId).attr("action",url);
	//添加hidden到form		
  	var param = $('#param').val();	  	
  	if("0" == param && ""== param){
  		alert("条件为空,不能搜索!");
  		return;
  	}
	$("#"+formId).submit();
}

function deleteSupplier(id){	
	if(window.confirm('确认删除？')==false){
		return;
	}		
    var value=id;	     	
   	$.ajax({
       type: "POST",
       url: "deleteSupplier.sc",
       data: {"id":value},           
       success: function(data){           
          if(data=="success"){
	 		alert("删除成功!"); 	
	 		window.location.reload();	 		
	 	  }              
       }
     });          
  } 

function changeState(id){
	openwindow('${BasePath}/supply/manage/supplier/toupdatestate.sc?id='+id,500,300,'改变状态',submitForm);
}
function toUpdateHistoryById(id){
   openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_querySupplierLog.sc?flag=2&supplierId="+id,900,700,"更新历史");
}
var submitForm=function()
{
	var iframe = this.iframe.contentWindow;
	var state=iframe.$('input[type="radio"]:checked').val();
	var id=iframe.$('#supplierId').val();
	if(state!=""&&id!=""){
	   if(confirm("确定修改状态吗?")){
	      //修改状态
				$.ajax({
					type:"post",
					url:"${BasePath}/supply/manage/supplier/updatesupplierstate.sc",
					data:{"supplierId":id,"state":state},
					success:function(datas){
					    if(datas=="sucess"){
							alert("修改成功！");
							refreshpage();
							closewindow();
						}else{
						  alert("修改失败");
						  refreshpage();
						  closewindow();
						}
					}
				});
		}
	}
}
</script>
</head><body>
<div class="container">
	<div class="toolbar">
		<div class="t-content">
			
		</div>
	</div>
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class='curr'> <span>供应商列表</span> </li>
			</ul>
		</div>
		<div class="modify">
			<form action ="querySupplier.sc" id="submitForm" name="submitForm" method="post">
				<input type="hidden" id="action" name="action" value="1"/>
				<div class="add_detail_box">
				<p>
					<span>名称： </span>
					<input type="text" name="param" id="param" value="<#if param??>${param?default("")}</#if>" class="blakn-sl" />
					<span>编码：</span>
					<input type="text" name="supplierCode" id="supplierCode" value="<#if supplierCode??>${supplierCode?default("")}</#if>" class="blakn-sl" />
					<span>类型：</span>
					
					<select id="type" name="type"  >
						<option value="" selected="selected">请选择</option>
						<#if listSupplierType??> <#list listSupplierType as supplierType> <option value="${supplierType.typeValue}" <#if type?? && type==supplierType.typeValue>selected="selected"</#if>>${supplierType.typeValue?default("")}
								
						</option>
						</#list> </#if>
					</select>
					<span>状态：</span>
					<select id="isValid" name="isValid" value="" >
						<option value="" selected="selected">请选择</option>
						
						<option value="1" <#if isValid?? && isValid==1>selected="selected"</#if>>启用
								
						</option>
						<option value="2" <#if isValid?? && isValid==2>selected="selected"</#if>>新建
								
						</option>
						<option value="-1" <#if isValid?? && isValid==-1>selected="selected"</#if>>停用
								
						</option>
					</select>
					<input type="submit" value="搜索" name="search" id="search" onClick="" class="btn-add-normal"/>
				</p>
				<table width="100%" border="0" cellspacing="0" cellpadding="0"  class="list_table">
					<thead>
						<tr class="div-pl-tt">
							<th>供应商编码</th>
							<th>供应商名称</th>
							<th>状态</th>
							<th>供应商类型</th>
							<th>优惠券分摊比例</th>
							<th>最后更新时间</th>
							<th>最后更新人</th>
							<th>操作</th>
						</tr>
					</thead>
					<#if pageFinder??>
					<#if pageFinder.data??>				 
					<#list pageFinder.data as supplier>
					<tr class="div-pl-list">
						<td><#if supplier.supplierCode??>${supplier.supplierCode?default("&nbsp;")}<#else>&nbsp;</#if>&nbsp;</td>
						<td><#if supplier.supplier??>${supplier.supplier?default("&nbsp;")}<#else>&nbsp;</#if>&nbsp;</td>
						<td><#if supplier.isValid??> 
							<#if supplier.isValid==2>新建
							<#elseif supplier.isValid==1>启用
							<#elseif supplier.isValid==-1>停用
							<#else>&nbsp;
							</#if>
							</#if>
							&nbsp; </td>
						<td><#if supplier.supplierType??>${supplier.supplierType?default("&nbsp;")}<#else>&nbsp;</#if>&nbsp;</td>
						<td>
						<#if supplier.couponsAllocationProportion?? && (supplier.couponsAllocationProportion>0)>${supplier.couponsAllocationProportion?default("&nbsp;")}%</#if>&nbsp;</td>
						<td>${(supplier.updateDate)!''}</td>
						<td>${(supplier.updateUser)!''}</td>
						<td class="pl-edt">
						  	<a href="${BasePath}/yitiansystem/merchants/businessorder/to_updateMerchants.sc?flag=<#if supplier.supplierType == "招商供应商">2<#else>1</#if>&id=${supplier.id}">修改详情</a>&nbsp;&nbsp;
						  	<a href="javascript:changeState('${supplier.id}');">改变状态</a>&nbsp;&nbsp;
							<a href="#" onclick="toUpdateHistoryById('${(supplier.id)}')">更新日志</a>
						</td>
					</tr>
					</#list>	
					</#if>
					</#if>
				</table>
			</form>
		</div>
		
	</div>
	<div class="bottom clearfix"> 
		<!-- 翻页标签 --> 
		<#import "../../../common.ftl"  as page>
		<@page.queryForm formId="submitForm" />
	</div>
</div>
</body>
</html>
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script>