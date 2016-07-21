<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,优购网,B网络营销系统,商品管理" />
<meta name="Description" content=" , ,B网络营销系统-商品管理" />
<link rev="stylesheet" rel="stylesheet"  type="text/css" href="css/style.css"/>
<link rev="stylesheet" rel="stylesheet" type="text/css" href="css/css.css" />
<script type="text/javascript" src="../js/common.js"></script>
<script type="text/javascript" src="../js/jquery-1.3.2.min.js" ></script>
<script type="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
<#include "../../../yitiansystem/yitiansystem-include.ftl">
<!-- 日期控件 -->
<script src="${BasePath}/js/common/form/datepicker/WdatePicker.js" type="text/javascript"></script>
<title>B网络营销系统-采购管理-优购网</title>
<script type="text/javascript">	
	function deletePurchase(id){	
		if(window.confirm('确认删除？')==false){
			return;
		}		
	    var value=id;	     	
       	$.ajax({
           type: "POST",
           url: "${BasePath}/supply/purchase/posygwarehouse/delete.sc",
           data: {"id":value},           
           success: function(data){           
              if(data=="success"){
 		 		alert("删除成功!"); 	 
 		 		window.location.reload();      		 			 		
 		 	  }              
           }
         }); 
      } 
function postForm(url) {
	var form = document.getElementById("submitForm") ;
	form.action = "${BasePath}/supply/purchase/posygwarehouse/list.sc";
	form.submit();
}
</script>
</head>
<body>
<div class="main-body" id="main_body">	
			<div class="ytback-tt-1 ytback-tt">
            	<span>您当前的位置：</span>采购管理 &gt; POS仓库与优购仓库对应列表
    		</div>
			<div class="pro-list">
				<div class="mb-btn-fd-bd">
					<div class="mb-btn-fd relative">
						<span class="btn-extrange absolute ft-sz-14">
							<ul class="onselect">
								<li class="pl-btn-lfbg"></li>
								<li class="pl-btn-ctbg">仓库对应列表</li>
								<li class="pl-btn-rtbg"></li>
							</ul>
						</span>
					</div>				
				<div class="add-newpd ft-sz-12 fl-rt">
				<a href="${BasePath}/supply/purchase/posygwarehouse/toadd.sc" alt="">添加</a>
				</div>	
				</div>
			</div>
			<form action ="${BasePath}/supply/purchase/posygwarehouse/list.sc" id="submitForm" name="submitForm" method="post">			 
			<div class="div-pl">				
			<div class="div-pl-hd ft-sz-12">
				<TABLE>
				<TR>
					<TD height=30  align="right">POS来源编码：</TD>
					<TD><input name="posSourceNo" id="posSourceNo" value="${posYgWarehouse.posSourceNo?default('')}" class="blakn-sl" style="width:151px;" /></TD>
					<TD  align="right">POS仓库编码：</TD>
					<TD><input name="posWareHouseCode" id="posWareHouseCode" value="${posYgWarehouse.posWareHouseCode?default('')}" class="blakn-sl" style="width:151px;" /></TD>
				</TR>
				<TR>
					<TD  align="right">POS仓库名称：</TD>
					<TD><input name="posWareHouseName" id="posWareHouseName" value="${posYgWarehouse.posWareHouseName!''}" class="blakn-sl" style="width:151px;" /></TD>
					<TD  align="right">优购仓库编码：</TD>
					<TD><input name="ygWareHouseCode" id="ygWareHouseCode" value="${posYgWarehouse.ygWareHouseCode!''}" class="blakn-sl" style="width:151px;" /></TD>
				</TR>
				<TR>
					<TD  align="right">优购仓库名称：</TD>
					<TD><input name="ygWareHouseName" id="ygWareHouseName" value="${posYgWarehouse.ygWareHouseName!''}" class="blakn-sl" style="width:151px;" /></TD>
					<TD colspan="2">
				        <input type="submit" value="搜索" name="search" id="search" onClick="postForm('${BasePath}/supply/purchase/posygwarehouse/list.sc')" class="yt-seach-btn"/>
				     </TD>
				</TR>
				</TABLE>
				</div>
				<div class="div-pl-table" id="div-table">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					  <tr class="div-pl-tt">
					  	<td class="pl-tt-td" ><b>POS来源编码</b></td>
						<td class="pl-tt-td"><b>POS仓库编码</b></td>																			  													
						<td class="pl-tt-td"><b>POS仓库名称</b></td>
						<td class="pl-tt-td"><b>优购仓库编码</b></td>
						<td class="pl-tt-td"><b>优购仓库名称</b> </td>
						<td class="pl-tt-td"><b>操作</b></td>
					  </tr>	
					  <#if pageFinder??>
					  <#if pageFinder.data??>				 
					  <#list pageFinder.data as posyg>
					  <tr class="div-pl-list"> 					  
					  	<td>${posyg.posSourceNo?default("&nbsp;")}</td>
						<td>${posyg.posWareHouseCode?default("&nbsp;")}</td>	
						<td>${posyg.posWareHouseName?default("&nbsp;")}</td>
						<td>${posyg.ygWareHouseCode?default("&nbsp;")}</td>												
						<td>${posyg.ygWareHouseName?default("&nbsp;")}</td>
						<td class="pl-edt">		
						<a href="javascript:deletePurchase('${posyg.id}');">删除</a>&nbsp;&nbsp;
						</td>
					  </tr>
					  </#list>	
					  <#else>
					  <tr class="div-pl-list">
					  	<td colspan="9"><B>对不起，没有查询到你想要的数据</B></td>
					  </tr>
					  </#if>
		 			  </#if>					 
				  </table>
				</div>					
			</div></form>
			<div class="div-pl-bt">
				<!-- 翻页标签 -->
				 <#if pageFinder?? && (pageFinder.data)?? >
					<#import "../../../common.ftl"  as page>
					<@page.queryForm formId="submitForm" />		
					</#if>		  
		</div>			
		</div>
			
		
</body>
</html>

