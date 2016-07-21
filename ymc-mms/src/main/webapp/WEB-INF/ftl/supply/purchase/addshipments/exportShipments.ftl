<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,优购网,B网络营销系统,商品管理" />
<meta name="Description" content=" , ,B网络营销系统-商品管理" />
<#include "../../../yitiansystem/yitiansystem-include.ftl">
<title>B网络营销系统-采购管理-优购网</title>
<script type="text/javascript">
//更新为已完成
function finishStatus(){
  var shipmentsId=$("#shipmentsId").val();
    if(shipmentsId!=""){
    	//修改状态为已完成
		 $.ajax({ 
			type: "post", 
			async:false,
			url: "${BasePath}/supply/purchase/addshipments/to_updateShipmentsStatus.sc?shipmentsId="+shipmentsId, 
			success: function(dt){
				if("fail"!=dt){
				    var id=dt;
				 	alert("发货单已完成！");
				 	closewindow();
				    refreshpage('${BasePath}/supply/purchase/addshipments/to_queryShipmentsList.sc');
				}else{
				   alert("修改失败！");
				}
			} 
		  });
    }
}
//导出明细
function exportShipmentsDetails(){
  document.queryForm.action='${BasePath}/supply/purchase/addshipments/exportShipmentsList.sc';
  document.queryForm.method="post";
  document.queryForm.submit();
}
//加载发货数量总和
$(function(){
   var commityNum=$("#commityNum").val();
   if(commityNum==""){
   	 $("#priceSum").html(0);
   }else{
   	 $("#priceSum").html(commityNum);
   }
});
</script>
</head><body>
<div class="container"> 
	<!--工具栏start-->
	 <div class="t-content">
		</div>
	<!--工具栏end-->
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li class='curr'> <span><a href="">添加供应商发货单</a></span> </li>
			</ul>
		</div>
		<!--当前位置end--> 
		<!--主体start-->
		<div id="modify" class="modify">
		<form action="${BasePath}/supply/purchase/addshipments/to_exportShipmentsList.sc" method="post" id="queryForm" name="queryForm">
			<input type="hidden" name="purchaseCode" id="purchaseCode" value="<#if purchaseSp??&&purchaseSp.purchaseCode??>${purchaseSp.purchaseCode!''}</#if>">
			<input type="hidden" name="purchaseId" id="purchaseId" value="<#if purchaseSp??&&purchaseSp.id??>${purchaseSp.id!''}</#if>">
			<input type="hidden" name="shipmentsId" id="shipmentsId" value="<#if shipment??&&shipment.id??>${shipment.id!''}</#if>">
			<p>
			<!--
			 <div style="float:left;margin-left:10px;">
				 <input type="button" class="btn-add-normal" value="导出明细" onclick="exportShipmentsDetails()">
			 </div>
			 <div style="float:left;margin-left:10px;">
			 <input type="button" class="btn-add-normal" value="完成" onclick="finishStatus()">
			 </div>
			 -->
			 </br>
			 </p>
			 <div class="add_detail_box;">
					<p style="margin-top:-10px;">
						<span>
						<label>发货单号：</label>
						<#if shipment??>
							<#if shipment.shipmentCode??>
							  ${shipment.shipmentCode!''}
							</#if>
					    </#if>
						</span>
						<span>
						<label style="margin-left:85px;">状态：</label>
						<#if shipment??&&shipment.status??>
						   <#if shipment.status==0>待确认
						   <#elseif shipment.status==1>已确认
						   <#elseif shipment.status==2>已完成
						   <#else>已作废
						   </#if>
						   <input type="hidden" name="status" id="status" value="<#if shipment??&&shipment.status??>${shipment.status!''}</#if>">
						<#else>
						      待确认
						  <input type="hidden" name="status" id="status" value="待确认">
						</#if>
						</span>
						<span>
						<label style="margin-left:100px;">仓库：</label>
						<#if warehouseName??>
							${warehouseName!''}
						<#else>
							<#if purchaseSp??>
								<#if purchaseSp.warehouse??&&purchaseSp.warehouse.warehouseName??>
								  ${purchaseSp.warehouse.warehouseName!''}
								</#if>
						    </#if>
						</#if>
						</span>
					</p>
					<p>
						<span>
						<label>采购单号：</label>
						<#if purchaseSp??&&purchaseSp.purchaseCode??>
					    	${purchaseSp.purchaseCode!''}
					    </#if>
						</span>
						<span>
						<label style="margin-left:60px;">发货日期：</label>
						<input style="width:120px;" <#if shipment??&&shipment.status??&&shipment.status==1>disabled="disabled"</#if> value="<#if shipment??&&shipment.shipmentDate??>${shipment.shipmentDate?string("yyyy-MM-dd")!''}</#if>" type="text" id="statrtDate" name="statrtDate"  />
						</span>
					</p>
					<p>
						<span>
						<label>备注：</label>
						<textarea  cols=70 rows=1 name="remark" id="remark"  <#if shipment??&&shipment.status??&&shipment.status==1>disabled="disabled"</#if>>
						<#if shipment??&&shipment.remark??>${shipment.remark!''}</#if>
					    </textarea>
						</span>
					</p>
					<p class="searchbtn">
					 <span style="color:red;margin-top:50px;margin-left:-100px;">发货单商品数量:<span id="priceSum"></span></span>
					</p>
				</div>
			  <input type="hidden" name="commityNum" id="commityNum" value="<#if commityNum??>${commityNum!''}</#if>">
			 <br/>
			</form>
				 <table cellpadding="0" cellspacing="0" class="list_table">
					<tr>
						<th>货品编码</th>
						<th>货品条码</th>
						<th>商品款号</th>
						<th>款色编码</th>
						<th>商品名称</th>
						<th>规格</th>
						<#if shipment??&&shipment.status??&&shipment.status!=1>
						  <th>未发货数量</th>
						</#if>
						<th>发货数量</th>
						<th>清点数量</th>
						<th>入库正品数量</th>
						<th>入库残品数量</th>
						<th>抽样数量</th>
					</tr>
				    <#if pageFinder??&&pageFinder.data??>
					<#list pageFinder.data as map>
						<tr class="div-pl-list">
						    <td>${map['product_no']!''}</td>
							<td>${map['inside_code']!''}</td>
							<td>${map['style_no']!''}</td>
							<td>${map['supplier_code']!''}</td>
							<td>${map['commodity_name']!''}</td>
							<td>${map['specification']!''}</td>
							<#if shipment??&&shipment.status??&&shipment.status!=1>
							<td>${map['count']!''}</td>
							</#if>
							<td>${map['ship_quantity']!''}</td>
							<td>${map['count_quantity']!''}</td>
							<td>${map['genuine_quantity']!''}</td>
							<td>${map['defective_quantity']!''}</td>
							<td>${map['sampling_quantity']!''}</td>
						</tr>
					</#list>	
					<#else>
						<tr>
                        	<td colSpan="9">抱歉，没有您要找的数据 </td>
	                    </tr>
					</#if>
				</table>
				 </div>
               <div class="bottom clearfix">
			  	<#if pageFinder ??><#import "../../../common.ftl" as page>
			  		<@page.queryForm formId="queryForm"/></#if>
			  </div>
				<div id="error" style="margin-top:20px;color:red;">
				</div>
		</div>
	</div>
</div>
</body>
</html>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>