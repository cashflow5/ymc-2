<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-商家查询</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>

</head>

<body>
	<div class="main_container" style="width: 100%;">
		<div class="normal_box">
			<div class="tab_panel">
				<div class="tab_content">
					<!--搜索start-->
					<form name="queryForm" id="queryForm" action="toSetMerchant.sc" method="POST">
					<input type="hidden" name="pageTag" value="1" />
					<div class="search_box">
						<p>
						<span><label>商家编码：</label><input type="text" name="merchantCode" id="merchantCode" class="inputtxt" value="${merchantCode!''}" /></span>
						<span><label>商家名称：</label><input type="text" name="merchantName" id="merchantName" class="inputtxt" value="${merchantName!''}" /></span>
						<span><label>品牌：</label><input type="text" name="brand" id="brand" class="inputtxt" value="${brand!''}"" /></span>
						<span>&nbsp;&nbsp;<a class="button" id="mySubmit" name="mySubmit" onclick="mySubmit()"><span>搜索</span></a></span>
						<!--span>&nbsp;&nbsp;<a class="button" id="setMyMerchant" name="setMyMerchant" onclick="setMyMerchant()"><span>设置当前商家</span></a></span-->
						</p>
					</div>
					<!--搜索end-->
					<!--列表start-->
					<table class="list_table">
					<thead>
						<tr>
                    	<th width="30"></th>
                    	<th width="180">商家编号</th>
                        <th width="285">商家名称</th>
                    	<th width="145">合作模式</th>
                        <th width="">状态</th>
        				</tr>
					</thead>
					<tbody>
						<#if pageFinder??&&pageFinder.data??>
							<#list pageFinder.data as item>
								<tr style="cursor: pointer;" title="点击选择" ondblclick="javascript:ondblclicksetMyMerchant('${item.supplier_code!''}');" onclick="javascript:$('#${item.supplier_code!''}').attr('checked', function(index, attr){ return !attr; });">
								<td><input type="radio" id="${(item.supplier_code)!""}" onclick="javascript:$('#${item.supplier_code!''}').attr('checked', function(index, attr){ return !attr; });" name="supplierCode" value="${(item.supplier_code)!""}"/></td>
	                        	<td>${(item.supplier_code)!""}</td>
		                        <td style="text-align:left;">${(item.supplier)!""}</td>
	                        	<td style="text-align:left;">
	                        	<#if item.is_input_yougou_warehouse??&&item.is_input_yougou_warehouse??>
	                        		<!--${(statics['com.yougou.kaidian.framework.beans.CooperationModel'].values()[item.is_input_yougou_warehouse].description)!""}${(item.is_input_yougou_warehouse)!""}-->
	                        	    <#if item.is_input_yougou_warehouse==0>不入优购仓库，商家发货<#elseif item.is_input_yougou_warehouse==1>入优购仓库，优购发货<#elseif item.is_input_yougou_warehouse==2>不入优购仓库，优购发货<#else>未知</#if>
	                        	</#if>
	                        	</td>
	                        	<td><#if (item.is_valid)??&&item.is_valid==1>启用<#elseif (item.is_valid)??&&item.is_valid==-1>停用<#else>新建</#if></td>
								</tr>
							</#list>
						<#else>
							<tr>
								<td class="td-no" colspan="8">请搜索获取数据！</td>
							</tr>
						</#if>
					</tbody>
					</table>
				</form>
				<!--列表end-->
				<#if pageFinder??&&pageFinder.data??>
					<!--分页start-->
					<div class="page_box">
						<div class="dobox">
						</div>
						<#if pageFinder??&&pageFinder.data??>
							<#import "/manage/widget/common.ftl" as page>
							<@page.queryForm formId="queryForm"/>
						</#if>
					</div>
					<!--分页end-->
				</#if>
				</div>
			</div>
		</div>
	</div>
</body>
<script language="javascript">
function mySubmit(){
	document.queryForm.submit();
}

function ondblclicksetMyMerchant(merchantCode){
	window.location = "${BasePath}/merchants/login/toSetPresentMerchant.sc?merchantCode="+merchantCode;
}

</script>
</html>