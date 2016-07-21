<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,优购网,B网络营销系统,商品管理" />
<meta name="Description" content=" , ,B网络营销系统-商品管理" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<title>B网络营销系统-采购管理-优购网</title>
</head>
<body>
<div class="container"> 
	<!--工具栏start-->
	<div class="toolbar">
		<div class="t-content">
			<div id="btnID" class="btn" onclick="exportDate();">
				<span class="btn_l" ></span>
				<b class="ico_btn delivery"></b>
				<span class="btn_txt">导出</span>
				<span class="btn_r"></span>
			</div>
		</div>
	</div>
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr"><span>POS厂商入库单同步错误列表</span></li>
			</ul>
		</div>
		<!--当前位置end-->
		
		<div class="modify"> 
			<!--搜索start-->
			<form action ="${BasePath}/supply/purchase/PosStorageInvoiceSyncError/errorlist.sc" id="submitForm" name="submitForm" method="post">
				<div class="add_detail_box">
					<p>
						<span>
							<label>POS发货单：</label>
							<input  type="text" name="posOrderNo" id="posOrderNo" value="${posSyncErrorShipment.posOrderNo?default('')}"/>
						</span>
					</p>
				</div>
				<p class="searchbtn">
					<input id="search" name="search" class="btn-add-normal"  value="搜索"  type="submit">
				</p>
			</form>
			<div class="tbox">
			<table class="list_table" cellspacing="0" cellpadding="0" border="0" style="white-space:nowrap;">
				<thead> 
	                    <tr>
		                    <th>POS来源</th>
		                    <th>发货单</th>
		                    <th>采购人</th>
		                    <th>同步时间</th>
		                    <th>错误类型</th>
		                    <th>款色编码</th>
		                    <th>POS尺码</th>
		                    <th>错误描述</th>
	                    </tr>              
	            </thead>
				<tbody>
	                	<#if pageFinder??>
					  		<#if pageFinder.data??>				 
					  		<#list pageFinder.data as posyg>
								  <tr> 					  
								  	<td>${posyg.posSourceName?default("&nbsp;")}</td>
									<td>${posyg.posOrderNo?default("&nbsp;")}</td>	
									<td>${posyg.purchaser?default("&nbsp;")}</td>
									<td>${posyg.lastSyncTime?default("&nbsp;")}</td>
									<td>
										<#if posyg.errorType??&&posyg.errorType == 1>
											商品信息不全
										<#else>
											其他
										</#if>
									</td>
									<td>${posyg.posSupplierCode?default("&nbsp;")}</td>
									<td>${posyg.posSize?default("&nbsp;")}</td>
									<td>${posyg.syncDesc?default("&nbsp;")}</td>												
								  </tr>
					  		</#list>	
					  		<#else>
							  <tr>
							  	<td colspan="8"><B>对不起，没有查询到你想要的数据</B></td>
							  </tr>
							  </#if>
			 			  </#if>
	                </tbody>
			</table>
		</div>
		</div>
		<!--分页start-->
		<div class="bottom clearfix"> <#if pageFinder ??>
			<#import "../../../common.ftl" as page>
			<@page.queryForm formId="submitForm" />
			</#if> </div>
		<!--分页end--> 
	</div>
</div>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator.js"></script> 
<script type="text/javascript">	
	function exportDate() {
		window.location.href = "${BasePath}/supply/purchase/PosStorageInvoiceSyncError/exportDatas.sc?posOrderNo="+$("#posOrderNo").val();
	}
</script>
</body>
</html>







