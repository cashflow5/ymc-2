<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<title>无标题文档</title>
</head>
<body>
<div class="container"> 
	<!--工具栏start-->
	<div class="toolbar">
		<div class="t-content">
			<div class="btn" onclick="addProductSize();"> <span class="btn_l" ></span> <b class="ico_btn add"></b> <span class="btn_txt">添加</span> <span class="btn_r"></span> </div>
		</div>
	</div>
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr"><span>产品尺码管理</span></li>
			</ul>
		</div>
		<!--当前位置end-->
		
		<div class="modify"> 
			<!--搜索start-->
			<div class="add_detail_box">
				<form action="productSizeList.sc" name="productSizeList" id="productSizeList" method="post">
					<span>尺码类型：</span>
					<select name="sizetypeId" style="width:152px;" class="blakn-sl">
						<option value="" selected>请选择</option>
						<#if sizetypes??> <#list sizetypes as type> <#if productSize??&&productSize.sizetypeId??&&productSize.sizetypeId==type.id>
						<option selected value="${type.id}">${type.name}</option>
						<#else>
						<option value="${type.id}">${type.name}</option>
						</#if> </#list> </#if>
					</select>
					<span>尺码：</span>
					<input type="text" name="size" id="size" value="${productSize.size?default('')}"  />
					<input id="search" name="search" class="btn-add-normal"  value="搜索"  type="submit">
				</form>
			</div>
			<table class="list_table" cellspacing="0" cellpadding="0" border="0">
				<thead>
					<tr>
						<th>尺码</th>
						<th>所属类型</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
				<#if pageFinder?? && pageFinder.data??>
				<#list pageFinder.data as size>
				<tr>
					<td>${size.size?default("&nbsp;")}</td>
					<td>${size.typeName?default("&nbsp;")}</td>
					<td><a href="#" onclick="updateProductSize('${size.id}')" >修改</a> | <a href="#" onclick="deleteProductSize('${size.id}')" >删除</a> </td>
				</tr>
				</#list>
				<#else>
				<tr>
					<td class="td-no" colspan="3">对不起，没有查询到你想要的数据！</td>
				</tr>
				</#if>
				
						</tbody>
				
			</table>
		</div>
		<!--分页start-->
		<div class="bottom clearfix"> <#if pageFinder ??>
			<#import "../../../common.ftl" as page>
			<@page.queryForm formId="productSizeList" />
			</#if> </div>
		<!--分页end--> 
	</div>
</div>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script> 
<script type="text/javascript">	
	function addProductSize() {
		openwindow('${BasePath}/supply/manage/productSize/c_addProductSizeUI.sc',450,250,'添加尺码');
	}
	function updateProductSize(productSizeId) {
		openwindow('${BasePath}/supply/manage/productSize/u_updateProductSizeUI.sc?productSizeId='+productSizeId,450,250,'修改尺码');
	}

	function deleteProductSize (productSizeId) {
		if(confirm('确实要删除吗？')) {
			openwindow('${BasePath}/supply/manage/productSize/d_deleteProductSize.sc?productSizeId='+productSizeId,450,250,'删除尺码');
		}
	}

</script>
</body>
</html>
