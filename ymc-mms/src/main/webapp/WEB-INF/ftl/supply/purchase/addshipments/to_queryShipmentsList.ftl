<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>

<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/jquery-1.3.2.min.js"></script>
<script type="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/js.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/order.js"></script>
<!-- 日期 -->
<script src="${BasePath}/js/common/form/datepicker/WdatePicker.js" type="text/javascript"></script>

<!-- 排序样式 -->
<link rel="stylesheet" type="text/css" href="${BasePath}/css/yitiansystem/merchants/sortfilter.css" />
<!-- 小图标库的样式 -->
<link rel="stylesheet" type="text/css" href="${BasePath}/css/yitiansystem/merchants/icon.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/css/yitiansystem/merchants/iconfont.css" />


<title>优购商城--商家后台</title>
</head>
<body>
<div class="container">
	<!--工具栏start--> 
	<div class="toolbar">
		<div class="t-content">
			<div class="btn" onclick="javascript:doExpQaWarehH()"> 
			  <span class="btn_l" ></span> <b class="ico_btn add"></b> <span class="btn_txt">导出详情</span> <span class="btn_r"></span> 
			</div>
		</div>
	</div>
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li  class="curr">
				  <span><a href="#" class="btn-onselc">供应商发货单</a></span>
				</li>
			</ul>
		</div>
 <div class="modify"> 
<form action ="to_queryShipmentsList.sc" id="queryForm" name="queryForm" method="post">
	<div class="wms-top">
						 <label>采购类型：</label>
							<select id="purchaseType" name="purchaseType">
							    <option value="">全部</option>
								<option value="102" <#if vo??&&vo.purchaseType??&&vo.purchaseType==102>selected</#if>>自购固定价结算</option>
								<option value="103" <#if vo??&&vo.purchaseType??&&vo.purchaseType==103>selected</#if>>自购配折结算</option>
								<option value="106" <#if vo??&&vo.purchaseType??&&vo.purchaseType==106>selected</#if>>招商底价代销</option>
								<option value="107" <#if vo??&&vo.purchaseType??&&vo.purchaseType==107>selected</#if>>招商扣点代销 </option>
								<option value="108" <#if vo??&&vo.purchaseType??&&vo.purchaseType==108>selected</#if>>招商配折结算</option>
							</select>
							<label style="margin-left:50px;">供应商：</label>
							<select name="supplierId" style="width:115px;">
							<option value="" selected>请选择</option>
							<#if supList??> 
							<#list supList as su> 
							<#if vo??&&vo.supplierId??&& su.id==vo.supplierId>
							<option selected value="${su.id}" >${su.supplier}</option>
							<#else>
							<option value="${su.id}" >${su.supplier}</option>
							</#if> </#list> </#if>
						</select>
							<label style="margin-left:50px;">仓库：</label>
							<select name="warehouseId"  >
							<option value="" selected>请选择</option>
							<#if warehouses??> 
							<#list warehouses as wh> 
							<#if vo??&&vo.warehouseId??&&wh.id==vo.warehouseId>
							<option selected value="${wh.id}">${wh.warehouseName?default("匿名")}</option>
							<#else>
							<option value="${wh.id}">${wh.warehouseName?default("匿名")}</option>
							</#if> </#list> </#if>
						</select>
						 <label>状态：</label>
							<select id="shipStatus" name="shipStatus">
								<option value="">全部</option>
								<option value="0" <#if vo??&&vo.shipStatus??&&vo.shipStatus==0>selected</#if>>待确认</option>
								<option value="1" <#if vo??&&vo.shipStatus??&&vo.shipStatus==1>selected</#if>>已确认</option>
								<%--<option value="2" <#if vo??&&vo.shipStatus??&&vo.shipStatus==2>selected</#if>>已完成</option>
								<option value="3" <#if vo??&&vo.shipStatus??&&vo.shipStatus==3>selected</#if>>已作废</option>--%>
							</select>
						 <br/>
							<label style="margin-left:20px;">采购员：</label>
							<input type="text" style="width:110px;" id="purchaser" name="purchaser" value="${vo.purchaser?default('')}"  />
						 	</span>
						 	<span>
							<label style="margin-left:40px;">发货单号：</label>
							<input type="text" style="width:110px;" id="shipmentCode" name="shipmentCode" value="${vo.shipmentCode?default('')}"  />
						 	</span>
						 	<span>
							<label style="margin-left:28px;">采购单号：</label>
							<input type="text" style="width:110px;" id="supplierCode" name="purchaseCode" value="${vo.purchaseCode?default('')}"  />
							</span>
							<span>
							<label style="margin-left:25px;">POS单号：</label>
							<input type="text" style="width:110px;" id="posPurchaseNo" name="posPurchaseNo" value="${vo.posPurchaseNo?default('')}"  />
							</span>
						 <br/>
						 <span>
							<label style="margin-left:13px;">POS品牌：</label>
							<select name="posSourceName" id="posSourceName">
							  <#if posSource??>
							  	<option value="">请选择POS品牌</option>
							    <#list posSource as item>
							  		<option value="${item.posSourceName!''}" <#if vo??&&vo.posSourceName??&&vo.posSourceName==item.posSourceName>selected</#if>>${item.posSourceName!''}</option>
							  	</#list>
							  </#if>
							</select>
							</span>
							<label style="margin-left:40px;">采购日期：</label>
							<input type="text" id="statrtDate" readOnly="readOnly" name="statrtDate" value="${vo.statrtDate?default('')}" class="Wdate inputtxt" style="width:75px;" />
							至
							<input type="text" id="endDate" readOnly="readOnly" name="endDate" value="${vo.endDate?default('')}"  class="Wdate inputtxt"  style="width:75px;"/>
							<label style="margin-left:40px;">发货日期：</label>
							<input type="hidden" name="isDate" id="isDate" value="true">
							<input type="text" id="statrtShipmentDate" readOnly="readOnly" name="statrtShipmentDate" value="${vo.statrtShipmentDate?default('')}" class="Wdate inputtxt" style="width:75px;" />
							至
							<input type="text" id="endShipmentDate" readOnly="readOnly" name="endShipmentDate" value="${vo.endShipmentDate?default('')}"  class="Wdate inputtxt"  style="width:75px;"/>
							<!--ty 2013-4-19  增加来货完毕查询条件-->
							<label>来货完毕：</label>
							<select id="isFinish" name="isFinish">
								<option value="">全部</option>
								<option value="1" <#if vo??&&vo.isFinish??&&vo.isFinish==1>selected</#if>>是</option>
								<option value="0" <#if vo??&&vo.isFinish??&&vo.isFinish==0>selected</#if>>否</option>
							</select>
							<br/>
						<input id="search" name="search" class="btn-add-normal" onclick="query();" value="搜索"  type="button">
              	</div>
              	</form>
                <table cellpadding="0" cellspacing="0" class="list_table">
				   <thead>
					<tr>
						<th>采购类型</th>
						<th>发货单号</th>
						<th>采购单号</th>
						<th>POS品牌</th>
						<th>POS单号</th>
						<th>供应商</th>
						<th>采购人</th>
						<th>仓库</th>
						<th>状态</th>
						<th>发货日期</th>
						<th>发货数量</th>
						<th>清点数量</th>
						<th>来货完毕</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
				<#if pageFinder??&&(pageFinder.data)??>
					<#list pageFinder.data as item>
						<tr>
							<td>
							<#if item['type']??&&item['type']==102>自购固定价结算
							<#elseif item['type']??&&item['type']==103>自购配折结算
							<#elseif item['type']??&&item['type']==106>招商底价代销
							<#elseif item['type']??&&item['type']==107>招商扣点代销 
							<#elseif item['type']??&&item['type']==108>招商配折结算</#if>
							</td>
							<td>${item['shipment_code']?default("&nbsp;")}</td>
							<td>${item['purchase_code']?default("&nbsp;")}</td>
							<td>${item['pos_source_name']?default("&nbsp;")}</td>
							<td>${item['pos_shipment_no']?default("&nbsp;")}</td>
							<td>${item['supplier']?default("0")}</td>
							<td>${item['purchaser']?default("&nbsp;")}</td>
							<td>
							   <#if warehouses??>
								  <#list warehouses as wh> 
								    <#if item['warehouse_id']??&&wh.id??&&wh.id==item['warehouse_id']>
										${wh.warehouseName?default("匿名")}
								    </#if>
								  </#list>
							   </#if>
							</td>
							<td><#if item['status']??&& item['status']==0>待确认
							<#elseif item['status']??&& item['status']==1>已确认
							<#elseif item['status']??&& item['status']==2>已完成
							<#elseif item['status']??&& item['status']==3>已作废</#if></td>
							<td>${item['shipment_date']!"&nbsp;"} </td>
							<td>${item['count']!"&nbsp;"} </td>
							<td>${item['count_quantity']?default("0")}</td>
							<td>
							<#if item['is_finish']??&&item['is_finish']==1>是
							<#else>否</#if>
							</td>
							<td>
							<a href="#" onclick="queryShipmentsDetail('${item['status']}','${item['id']}','${item['purchase_code']}');">详情</a></td>
						</tr>
					</#list>	
				      <#else>
                        	<tr>
                        	<td colSpan="12">
                        	抱歉，没有您要找的数据 
	                        </td>
	                        </tr>
                        </#if>
                      </tbody>
                </table>
              </div>
               <div class="bottom clearfix">
			  	<#if pageFinder ??><#import "../../../common.ftl" as page>
			  		<@page.queryForm formId="queryForm"/></#if>
			  </div>
              <div class="blank20"></div>
          </div>
</div>
</body>
</html>
<script type="text/javascript">	
$(function(){ 
	$('#statrtDate').calendar({maxDate:'#endDate' }); 
	$('#endDate').calendar({minDate:'#statrtDate' });
	
	$('#statrtShipmentDate').calendar({maxDate:'#statrtShipmentDate' }); 
	$('#endShipmentDate').calendar({minDate:'#endShipmentDate' });
	
	
});
//查看未发货采购单详情
function queryShipmentsDetail(status,shipmentsId,purchaseCode) {
  if(status!="" && shipmentsId!="" && purchaseCode!=""){
	    //如果为待确认 跳转到添加明细页面
	    if(status==0){
	        openwindow('${BasePath}/supply/purchase/addshipments/to_addshipments.sc?flag=1&shipmentsId='+shipmentsId+'&purchaseCode='+purchaseCode,1200,600,'新增供应商发货单');
	    }else if(status==1 || status==2 || status==3){//否则跳转到完成、导出数据页面
	    	 openwindow('${BasePath}/supply/purchase/addshipments/to_exportShipmentsList.sc?shipmentsId='+shipmentsId+'&purchaseCode='+purchaseCode,1200,600,'新增供应商发货单');
	    }else{
	      alert("该单据已完成或者已作废!");
	    }
  }
}

function query()
{
    var queryForm = document.getElementById("queryForm");
    var startDate = document.getElementById("statrtShipmentDate").value;
    if(startDate=='')
    {
       alert("发货开始时间不能为空");
       return false;
    }
    var endDate = document.getElementById("endShipmentDate").value;
     if(endDate=='')
    {
       alert("发货结束时间不能为空");
       return false;
    }
    
    if(daysBetween(startDate,endDate)>60)
    {
       alert("发货前后时间不能超过60天,请重新选择时间");
    }
    else
    {    
		queryForm.action = "${BasePath}/supply/purchase/addshipments/to_queryShipmentsList.sc";
		queryForm.submit();
	}
}

function daysBetween(DateOne,DateTwo)  
{   
    var OneMonth = DateOne.substring(5,DateOne.lastIndexOf ('-'));  
    var OneDay = DateOne.substring(DateOne.length,DateOne.lastIndexOf ('-')+1);  
    var OneYear = DateOne.substring(0,DateOne.indexOf ('-'));  
  
    var TwoMonth = DateTwo.substring(5,DateTwo.lastIndexOf ('-'));  
    var TwoDay = DateTwo.substring(DateTwo.length,DateTwo.lastIndexOf ('-')+1);  
    var TwoYear = DateTwo.substring(0,DateTwo.indexOf ('-'));  
  
    var cha=((Date.parse(OneMonth+'/'+OneDay+'/'+OneYear)- Date.parse(TwoMonth+'/'+TwoDay+'/'+TwoYear))/86400000);   
    return Math.abs(cha);  
}  

function doExpQaWarehH()
{
    var queryForm = document.getElementById("queryForm");
    var startDate = document.getElementById("statrtShipmentDate").value;
    if(startDate=='')
    {
       alert("发货开始时间不能为空");
       return false;
    }
    var endDate = document.getElementById("endShipmentDate").value;
     if(endDate=='')
    {
       alert("发货结束时间不能为空");
       return false;
    }
    
    if(daysBetween(startDate,endDate)>60)
    {
       alert("发货前后时间不能超过60天,请重新选择时间");
    }
    else
    {   
	  queryForm.action = "${BasePath}/supply/purchase/addshipments/doExpQaWarehH.sc";
	  queryForm.submit();
	}
}
</script>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
