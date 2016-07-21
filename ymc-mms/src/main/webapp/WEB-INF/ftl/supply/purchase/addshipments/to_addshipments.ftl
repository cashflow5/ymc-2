<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,优购网,B网络营销系统,商品管理" />
<meta name="Description" content=" , ,B网络营销系统-商品管理" />
<#include "../../../yitiansystem/yitiansystem-include.ftl">
<title>B网络营销系统-采购管理-优购网</title>
<script type="text/javascript">
//新增未发货采购单明细
function addDetail(){
var purchaseCode=$("#purchaseCode").val();
var shipmentId=$("#shipmentId").val();
	if(purchaseCode!=""){
	  openwindow('${BasePath}/supply/purchase/addshipments/to_addshipmentsDetail.sc?purchaseCode='+purchaseCode+'&shipmentId='+shipmentId,'','','发货单新增明细');
	}else{
	  alert("采购单编号为空!");
	}
}

//保存
function saveAddshipments(){
    var str="";
    var count=0;
    var ids = document.getElementsByName("shipQuantity");  
    var shipId = document.getElementsByName("shipId");      
    for (var i = 0; i < ids.length; i++)       {
      if(ids[i].value==""){
        count+=1;
      }else{
        str+=shipId[i].value+"="+ids[i].value+",";  
      }  
    }  
    if(count!=0){
       alert("发货数量有空值!");
    }else{
      str=str.substring(0,str.length-1);
      var shipmentId=$("#shipmentId").val();
      var statrtDate=$("#statrtDate").val();
      var shipmentDetailNum=str;
      var purchaseCode=$("#purchaseCode").val();
      var remark=$("#remark").val();
      //ajax提交数据
      $.ajax({
	       type: "POST",
	       data:{"shipmentId":shipmentId,"statrtDate":statrtDate,"shipmentDetailNum":shipmentDetailNum,"purchaseCode":purchaseCode,"remark":remark},
	       url : "${BasePath}/supply/purchase/addshipments/update_shipmentsDetail.sc",
	       async : false,
	       success: function(data){
				if("success"==data){
				    alert("保存成功!");
					refreshpage('${BasePath}/supply/purchase/addshipments/to_addshipments.sc?shipmentsId='+shipmentId+'&purchaseCode='+purchaseCode);
				}else{
				   alert("保存失败!");
				}
	       }
		});
    }
}

//确认
function sureAddshipments(){
var sendTime=$("#statrtDate").val();
if(sendTime==''){
	alert('请输入发货日期！');
	return;
}
  var str="";
    var count=0;
    var ids = document.getElementsByName("shipQuantity");  
    var shipId = document.getElementsByName("shipId");      
    for (var i = 0; i < ids.length; i++)       {
      if(ids[i].value==""){
        count+=1;
      }else{
        str+=shipId[i].value+"="+ids[i].value+",";  
      }  
    }  
    if(count!=0){
       alert("发货数量有空值!");
    }else{
      str=str.substring(0,str.length-1);
      var shipmentId=$("#shipmentId").val();
      var statrtDate=$("#statrtDate").val();
      var shipmentDetailNum=str;
      var purchaseCode=$("#purchaseCode").val();
      var remark=$("#remark").val();
      //ajax提交数据
      $.ajax({
	       type: "POST",
	       data:{"shipmentId":shipmentId,"statrtDate":statrtDate,"shipmentDetailNum":shipmentDetailNum,"purchaseCode":purchaseCode,"remark":remark},
	       url : "${BasePath}/supply/purchase/addshipments/update_shipmentsDetail.sc",
	       async : false,
	       success: function(data){
				if("success"==data){
				     if(confirm("请确认是否继续？")){
						   var shipmentId=$("#shipmentId").val();
						   var purchaseCode=$("#purchaseCode").val();
						   var flag=$("#flag").val();//跳到到某个页面标志  1 跳转到供应商发货单页面
						   if(shipmentId!="" || purchaseCode!=""){
						    $.ajax({ 
								type: "post", 
								async:false,
								url: "${BasePath}/supply/purchase/addshipments/sure_shipmentsDetail.sc?shipmentId="+shipmentId+"&purchaseCode="+purchaseCode, 
								success: function(dt){
									if("success"==dt){
									   alert("发货单确认成功！");
									   closewindow();
									   if(flag!="" && flag=='1'){
									   		refreshpage('${BasePath}/supply/purchase/addshipments/to_queryShipmentsList.sc');
									   }else{
									   		refreshpage('${BasePath}/supply/supplier/PerchaseOrder/findFulfillOrder.sc');
									   }
									}else{
									   $("#error").html(dt);
									}
							   }
						     });
						   }else{
						     alert("未发货主表Id为空，或者采购单编号为空!");
						   }
					   }
				}else{
				   alert("确认失败!");
				}
	       }
		});
    }
}
//删除
function deleteAddshipments(){
  var shipmentId=$("#shipmentId").val();
  var status=$("#status").val();
  if(status==0){
  	if(confirm("确认删除供应商发货单？")){
       $.ajax({ 
		type: "post", 
		async:false,
		url: "${BasePath}/supply/purchase/addshipments/delete_shipmentsDetail.sc?shipmentId=" + shipmentId, 
		success: function(dt){
			if("success"==dt){
			   alert("删除成功!");
			   closewindow();
			   var flag=$("#flag").val();
			    if(flag!="" && flag=='1'){
			   		refreshpage('${BasePath}/supply/purchase/addshipments/to_queryShipmentsList.sc');
			    }else{
			   		refreshpage('${BasePath}/supply/supplier/PerchaseOrder/findFulfillOrder.sc');
			    }
			   
			}else{
			   alert("删除失败!");
			}
	   }
     });
    }
  }else{
  	alert("该发货单已经确认，不能删除!");
  }
}
//导入
function importAddshipments(){
 var shipmentId=$("#shipmentId").val();
 var purchaseCode=$("#purchaseCode").val();
 var purchaseId=$("#purchaseId").val();
 
 openwindow('${BasePath}/supply/purchase/addshipments/to_importShipmentsList.sc?shipmentId='+shipmentId+'&purchaseCode='+purchaseCode+'&purchaseId='+purchaseId,'800','500','导入供应商发货单');
}
//导出模版
function exportAddshipments(){
  window.location.href ="${BasePath}/supply/purchase/addshipments/exportShipmentsTemplate.sc";
}
$(function(){
$('#statrtDate').calendar({maxDate:'#statrtDate',format:'yyyy-MM-dd'});
});

//加载发货数量总和
$(function(){
   var shipmentId=$("#shipmentId").val();
   if(shipmentId==""){
       var commityNum=$("#commityNum").val();
	   if(commityNum==""){
	   	 $("#priceSum").html(0);
	   }else{
	   	 $("#priceSum").html(commityNum);
	   }
   }else{
      var shipQuantitys=$("#shipQuantitys").val();
	   if(shipQuantitys==""){
	   	 $("#priceSum").html(0);
	   }else{
	   	 $("#priceSum").html(shipQuantitys);
	   }
   }
});
//累加发货单数量
function addPrice(va,shipQuantity){
  var sumHtml=$("#priceSum").html();
  var sum=0;
  if(va!=""){
	  if(sumHtml==""){
	     sum+=parseInt(va);
	  }else{
	     if(shipQuantity!=""){
	     	sum+=parseInt(parseInt(va)+parseInt(sumHtml)-parseInt(shipQuantity));
	     }else{
	     	sum+=parseInt(parseInt(va)+parseInt(sumHtml));
	     }
	  }
  }else{
    alert("发货数量不能为空!");
    $("#shipQuantity").onFocus();
  }
  $("#priceSum").html(sum);
}

//采购单转发货单
function purchaseToShipments(){
	var purchaseCode=$("#purchaseCode").val();
	var shipmentId=$("#shipmentId").val();
	if(shipmentId==""){
		$.ajax({ 
			type: "post", 
			async:false,
			url: "${BasePath}/supply/purchase/addshipments/purchaseToShipments.sc?purchaseCode=" + purchaseCode, 
			success: function(dt){
				if("fail"!=dt){
				   if("exist"!=dt){
					   alert("保存发货单数据成功!");
					   $("#shipmentId").val(dt);
					   var purchaseCode=$("#purchaseCode").val();
					   refreshpage('${BasePath}/supply/purchase/addshipments/to_addshipments.sc?showDiv=1&purchaseCode='+purchaseCode+'&shipmentsId='+dt);
				   }else{
				      alert("该采购单已创建发货单，不能转发货单");
				   }
				}else {
				   alert("保存发货单数据失败!");
				}
		   }
	     });
     }else{
       alert("该采购单已创建发货单，不能转发货单");
     }
}
//导出模板
 function exportXls() {
  	var typeId = $("#typeId").val();
  	if(typeId!="") {
  		window.location.href = "${BasePath}/supply/manage/purchaseDetail/c_exportTemplate.sc?typeId="+typeId;
  	}else {
  		alert("请选择类型");
  	}
  }
</script>
</head>
<body>
<div class="container"> 
<!--工具栏start-->
<div class="toolbar">
	<div class="t-content"> <!--操作按钮start--> 
		<div class="btn" id="toAddDetailBtn" onclick="purchaseToShipments();"> <span class="btn_l"> </span> <b class="ico_btn add"> </b> <span class="btn_txt"> 采购单转发货单</span> <span class="btn_r"> </span> </div>
	     <#if shipment??&&shipment.id??>
	          <div class="line"> </div>
	   		 <div class="btn" id="doSaveBtn" onclick="saveAddshipments();"> <span class="btn_l"> </span> <b class="ico_btn save"> </b> <span class="btn_txt"> 保存</span> <span class="btn_r"> </span> </div>
	     </#if>
	      <#if showDiv??>
		  <#else>
		  	 <div class="line"> </div>
		     <div class="btn" id="importBtn" onclick="importAddshipments();"> <span class="btn_l"> </span> <b class="ico_btn delivery"> </b> <span class="btn_txt"> 导入 </span> <span class="btn_r"> </span> </div>
		  </#if>  
	     <#if shipment??&&shipment.id??>
	        <div class="line"> </div>
   		 	<div class="btn" id="doRemoveDetailBtn" onclick="deleteAddshipments();"> <span class="btn_l"> </span> <b class="ico_btn delete"> </b> <span class="btn_txt"> 删除 </span> <span class="btn_r"> </span> </div>
	     </#if>
	     <#if shipment??&&shipment.id??>
	        <div class="line"> </div>
    		<div class="btn" id="doCheckPassBtn" onclick="sureAddshipments();"> <span class="btn_l"> </span> <b class="ico_btn save"> </b> <span class="btn_txt"> 确认</span> <span class="btn_r"> </span> </div>
	     </#if>
     	<div class="line"> </div>
     	<!--
	    <div class="btn" id="exportTempBtn" onclick="exportAddshipments();"> <span class="btn_l"> </span> <b class="ico_btn delivery"> </b> <span class="btn_txt"> 导出模板 </span> <span class="btn_r"> </span> </div>
	    -->
	</div>
</div>
<div class="list_content"> 
<div class="top clearfix">
	<ul class="tab">
		<li class='curr'> <span><a href="">添加供应商发货单</a></span> </li>
	</ul>
</div>
<div id="modify" class="modify">
<form method="post" action="${BasePath}/supply/purchase/addshipments/to_addshipments.sc" id="queryForm" name="queryForm" encType="multipart/form-data">
<input type="hidden" name="commityNum" id="commityNum" value="<#if commityNum??>${commityNum!''}</#if>">
<input type="hidden" name="flag" id="flag" value="<#if flag??>${flag!''}</#if>">
<input type="hidden" name="purchaseCode" id="purchaseCode" value="<#if purchaseSp??&&purchaseSp.purchaseCode??>${purchaseSp.purchaseCode!''}</#if>">
<input type="hidden" name="purchaseId" id="purchaseId" value="<#if purchaseSp??&&purchaseSp.id??>${purchaseSp.id!''}</#if>">
<input type="hidden" name="shipmentId" id="shipmentId" value="<#if shipment??&&shipment.id??>${shipment.id!''}</#if>">
<input type="hidden" name="shipmentsId" id="shipmentsId" value="<#if shipment??&&shipment.id??>${shipment.id!''}</#if>">
<input type="hidden" name="shipmentDetailNum" id="shipmentDetailNum">
<input type="hidden" name="shipQuantitys" id="shipQuantitys" value="<#if shipQuantitys??>${shipQuantitys?c!''}</#if>">
			<div class="wms-top">
					<label style="margin-left:20px;">发货单号：</label>
					<#if shipment??>
						<#if shipment.shipmentCode??>
						  ${shipment.shipmentCode!''}
						</#if>
				    </#if>
					<label style="margin-left:<#if shipment??&&shipment.shipmentCode??>75px;<#else>176px;</#if>">状态：</label>
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
					<label style="margin-left:100px;">下载模板：</label>
					<select id="typeId" name="typeId" style="width:70px;">
						<option value="">请选择</option>
						<#list sizeTypes as sizeType>
						<option value="${sizeType.id}-${sizeType.name}" >${sizeType.name}</option>
						</#list>
					</select>
					<a href="#" style="color:blue;" onclick="exportXls();">导出模板</a>
					<br/>
					<label style="margin-left:20px;">采购单号：</label>
					<#if purchaseSp??&&purchaseSp.purchaseCode??>
				    	${purchaseSp.purchaseCode!''}
				    </#if>
				    <label style="margin-left:75px;">仓库：</label>
				    <#if warehouseName??>
						${warehouseName!''}
					<#else>
						<#if purchaseSp??>
						  <#if purchaseSp.warehouse??&&purchaseSp.warehouse.id??>
							 ${purchaseSp.warehouse.warehouseName!''}
						  </#if>
						</#if>
					</#if>
					<label style="margin-left:88px;">发货日期：</label>
					<input style="width:120px;" value="<#if shipment??&&shipment.shipmentDate??>${shipment.shipmentDate?string("yyyy-MM-dd")}</#if>" type="text" id="statrtDate" name="statrtDate"  />
					<br/>
					<label style="margin-left:43px;">备注：</label>
					<textarea  cols=70 rows=1 name="remark" id="remark"><#if shipment??&&shipment.remark??>${shipment.remark!''}</#if></textarea>
				<br/>
				<p class="searchbtn">
				 <span style="color:red;margin-top:50px;margin-left:-90px;">发货单商品数量:<span id="priceSum"></span></span>
				</p>
			</div>
			 </form>
				 <table cellpadding="0" cellspacing="0" class="list_table">
					<tr>
						<th>货品编码</th>
						<th>货品条码</th>
						<th>商品款号</th>
						<th>款色编码</th>
						<th>商品名称</th>
						<th>规格</th>
						<th>未发货数量</th>
						<th>发货数量</th>
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
							<td>${map['count']?c!''}</td>
							<td>
								<input type="text" onchange="addPrice(this.value,'${map['ship_quantity']?c!''}');" value="<#if map['ship_quantity']?c??>${map['ship_quantity']?c!''}<#else>${map['count']?c!''}</#if>" style="width:50px;" id="shipQuantity" name="shipQuantity"/>
								<input type="hidden" id="shipId" name="shipId" value="${map['id']!''}" />
							</td>
						</tr>
					</#list>	
					<#else>
						<tr><td colSpan="9">抱歉，没有您要找的数据 </td></tr>
					</#if>
				</table>
			   </div>
                <div class="bottom clearfix">
			  	<#if pageFinder??><#import "../../../common.ftl" as page>
			  		<@page.queryForm formId="queryForm"/></#if>
			   </div>
              <div class="blank20"></div>
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