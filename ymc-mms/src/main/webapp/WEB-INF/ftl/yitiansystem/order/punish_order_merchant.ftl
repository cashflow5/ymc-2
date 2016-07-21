<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#include "../merchants/merchants-include.ftl">
<title>优购商城--商家后台</title>
</head>
<body>
<div class="container">
	<!--工具栏start--> 
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li  class="curr">
				  <span>商家缺货统计</span>
				</li>
				<li >
				  <span><a href="${BasePath}/yitiansystem/merchant/order/punishOrder_brand.sc" class="btn-onselc">品牌缺货统计</a></span>
				</li><span style="color:red;">&nbsp;&nbsp;&nbsp;注意:由于数据取自BI接口,不是实时的，只能查询昨天到以前的数据，时间跨度不要超过3个月!</span>
			</ul>
		</div>
   <div class="modify"> 
     <form action="${BasePath}/yitiansystem/merchant/order/punishOrder_merchant.sc" name="queryForm" id="queryForm" method="post">
  			  	<div class="wms-top">
                     <label>商家名称：</label>
                     <input type="text" name="merchantName" id="merchantName" value="${(params.merchantName)!'' }"/>&nbsp;&nbsp;&nbsp;
                     <label>商家编号：</label>
                     <input type="text" name="merchantCode" id="merchantCode" value="${(params.merchantCode)!'' }"/>&nbsp;&nbsp;&nbsp;
                     <label>品牌名称：</label>
                     <input type="text" name="brandName" id="brandName" value="${(params.brandName)!'' }"/>&nbsp;&nbsp;&nbsp;
                </div>
  			  	<div class="wms-top">
                     <label>下单日期：</label>
                   	 <input type="text" name="stockoutDateStart" id="stockoutDateStart" value="<#if (params.stockoutDateStart) ??>${params.stockoutDateStart!''}</#if>"/>
                   	    至
                   	 <input type="text" name="stockoutDateEnd" id="stockoutDateEnd" value="<#if (params.stockoutDateEnd) ??>${params.stockoutDateEnd!''}</#if>"/>
                   	 <label>排序方式：</label>
                     <select id="orderType" name="orderType">
                        <option value="0">请选择排序方式</option>
                        <option value="1" <#if params.orderType??&& params.orderType=="1">selected=selected</#if>>缺货率由高到低</option>
                        <option value="2" <#if params.orderType??&& params.orderType=="2">selected=selected</#if>>缺货率由低到高</option>
                        <option value="3" <#if params.orderType??&& params.orderType=="3">selected=selected</#if>>缺货件数由高到低</option>
                        <option value="4" <#if params.orderType??&& params.orderType=="4">selected=selected</#if>>缺货件数由低到高</option>
                      </select>
                      &nbsp;&nbsp;&nbsp;
                   	 <input type="button" value="搜索" onclick="queryMerchantPunish();" class="yt-seach-btn" />
              	</div>
              	</form>
                <table cellpadding="0" cellspacing="0" class="list_table">
                		<thead>
                        <tr>
	                        <th style="width:80px;">商家名称</th>
	                        <th style="width:80px;">商家编码</th>
	                        <th style="width:80px;">缺货订单数</th>
	                        <th style="width:80px;">实际发货数</th>
	                        <th style="width:80px;">缺货商品数</th>
	                        <th style="width:80px;">缺货率<a class="brand_no" data-attr="{title: '缺货率计算公式', reason:'缺货率=缺货总件数/(缺货总件数+实际发货数)'}" href="javascript:;"><img src="${BasePath}/yougou/images/icon/bulb_ico.gif"></a></th>
	                        <th style="width:80px;">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        	<#if pageFinder??&&pageFinder.data??>
	                   		<#list pageFinder.data as item >
	                         <tr>
		                        <td>${(item.merchantName)!'' }</td>
		                        <td>${(item.merchantCode)!'' }</td>
		                        <td>${(item.orderSubNoCount)!'0' }</td>
		                        <td>${(item.sendCommodityNum)!'0' }</td>
		                        <td>${(item.lackCommodityNum)!'0' }</td>
		                        <td>${(item.stockoutRatio)!'0' }</td>
		                        <td><a href="javascript:doExport('${(item.merchantCode)!'' }','${params.stockoutDateStart!''}','${params.stockoutDateEnd!''}')">导出商品缺货明细</a></td>
	                        </tr>
	                        </#list>
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
$(document).ready(function() {
	$('#stockoutDateStart').calendar({maxDate:'#stockoutDateEnd',format:'yyyy-MM-dd HH:mm:ss',diffDate:90});
	$('#stockoutDateEnd').calendar({minDate:'#stockoutDateStart',format:'yyyy-MM-dd HH:mm:ss',diffDate:90});
	
});
function queryMerchantPunish(){
   document.queryForm.method="post";
   document.queryForm.submit();
}

// 导出数 据
function doExport(merchantCode,stockoutDateStart,stockoutDateEnd) {
    var params="?merchantCode="+merchantCode+"&stockoutDateStart="+stockoutDateStart+"&stockoutDateEnd="+stockoutDateEnd;
 	location.href = "${BasePath}/yitiansystem/merchant/order/exportPunishCommodity.sc"+params;
}
$(".brand_no").hover(function() {
	var _this = $(this);
	var data = eval('(' + $(this).attr("data-attr") + ')');
	var _top = _this.offset().top - $(document).scrollTop();
	ygdg.dialog({
		title : data.title,
		content : '<p class="picDetail">' + data.reason + '</p>',
		id : 'detailBox',
		left : $(this).offset().left - 310,
		top : _top,
		width : 300,
		closable : false
	});
}, function(){
	ygdg.dialog.list['detailBox'].close();
});
</script>
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
