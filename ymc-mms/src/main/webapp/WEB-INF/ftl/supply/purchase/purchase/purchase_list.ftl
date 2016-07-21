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
			<div class="btn" onclick="openwindow('${BasePath}/supply/manage/purchase/c_addPurchaseUI.sc',1200,600,'添加采购单');"> <span class="btn_l" ></span> <b class="ico_btn add"></b> <span class="btn_txt">添加采购订单</span> <span class="btn_r"></span> </div>
		</div>
	</div>
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr"><span>采购订单</span></li>
			</ul>
		</div>
		<!--当前位置end-->
		
		<div class="modify"> 
			<!--搜索start-->
				<form action ="toManage.sc" id="submitForm" name="submitForm" method="post">
				<div class="add_detail_box">
					<p>
					<span>
					 <label>单据编号：</label>
						<input  type="text" name="purchaseCode" id="purchaseCode" value="${searchPurchaseVo.purchaseCode?default('')}" />
						</span>
						<span>
						<label>采购员：</label>
						<input  type="text" name="purchaser" id="purchaser" value="${searchPurchaseVo.purchaser?default('')}"  />
					</span>
					<span>
						<label>供应商名称：</label>
						<input type="text" name="supplierName" id="supplierName" value="${searchPurchaseVo.supplierName?default('')}"   />
						
						<label>下单日期：</label>
						<input type="text" id="startCreateDate" name="startCreateDate" value="${searchPurchaseVo.startCreateDate?default('')}" class="Wdate inputtxt" style="width:75px;" />
						至
						<input type="text" id="endCreateDate" name="endCreateDate" value="${searchPurchaseVo.endCreateDate?default('')}"  class="Wdate inputtxt"  style="width:75px;"/>
						</span>
						</p>
						<p>
						<span>
						<label>商品款号：</label>
						<input type="text" id="styleNo" name="styleNo" value="${searchPurchaseVo.styleNo?default('')}"  />
					 	</span>
					 	<span>
					 	<label>仓库：</label>
						<select name="warehouseId"  >
							<option value="" selected>请选择</option>
							<#if warehouses??> <#list warehouses as wh> <#if searchPurchaseVo??&&searchPurchaseVo.warehouseId??&&wh.id==searchPurchaseVo.warehouseId>
							<option selected value="${wh.id}">${wh.warehouseName?default("匿名")}</option>
							<#else>
							<option value="${wh.id}">${wh.warehouseName?default("匿名")}</option>
							</#if> </#list> </#if>
						</select>
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
						<th>操作</th>
						<th>单据编号</th>
						<th>供应商名称</th>
						<th>采购员</th>
						<th>合计/数量</th>
						<th>总金额</th>
						<th>计划交货日期</th>
						<th>仓库</th>
						<th>状态</th>
					</tr>
				</thead>
				<tbody>
				<#if pageFinder?? &&(pageFinder.data)??>
				<#list pageFinder.data as purchase>
				<tr>
					<td> <#if purchase['status']?? && purchase['status']!=1> <a href="#" onclick="openwindow('${BasePath}/supply/manage/purchase/u_updatePurchaseUI.sc?id=${purchase['id']!''}',1200,600,'编辑查看');">编辑查看</a>&nbsp;&nbsp;
					<a href="javascript:deletePurchase('${purchase['id']!''}');">删除</a>&nbsp;&nbsp;<a href="#" onclick="checkStatus('${purchase['id']!''}','0')" >提交审核</a>&nbsp;&nbsp;
						</#if> <a target="_blank" href="${BasePath}/supply/manage/purchase/printPact.sc?id=${purchase['id']!''}" >打印合同</a>&nbsp;&nbsp;<a href="#" onclick="exportDate('${purchase['id']!''}','0')" >导出数据</a> 
					</td>
					<td>${purchase['purchase_code']?default("&nbsp;")}</td>
					<td>${purchase['supplier']?default("&nbsp;")}</td>
					<td>${purchase['purchaser']?default("&nbsp;")}</td>
					<td>${purchase['amount']?default("0")}</td>
					<td>${purchase['total_price']?default("&nbsp;")}</td>
					<td>${purchase['create_date']!"&nbsp;"}</td>
					<td>${purchase['warehouse_name']!"&nbsp;"} </td>
					<td> <#if purchase['status']?? && purchase['status']==2>新建</#if> </td>
				</tr>
				</#list>	
				<#else>
				<tr>
					<td class="td-no" colspan="11">对不起，没有查询到你想要的数据</td>
				</tr>
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
	$(function(){ 
		$('#startCreateDate').calendar({maxDate:'#endCreateDate' }); 
		$('#endCreateDate').calendar({minDate:'#startCreateDate' });
	});
	function deletePurchase(id){	
		if(window.confirm('确认删除？')==false){
			return;
		}		
	    var value=id;	     	
       	$.ajax({
           type: "POST",
           url: "deletepurchase.sc",
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
		form.action = "${BasePath}/supply/manage/purchase/toManage.sc";
		form.submit();
	}
	//添加采购单
	function addPurchse() {
		openwindow('${BasePath}/supply/manage/purchase/toAdd.sc',1200,600,'添加采购单');
	}
	//修改采购单
	function updatePurchse(id) {
		openwindow('${BasePath}/supply/manage/purchase/toUpdate.sc?id='+id,1200,600,'修改采购单');
	}
	//修改采购单
	function updatePurchse2(id) {
		openwindow('${BasePath}/supply/manage/purchase/u_updatePurchaseUI.sc?id='+id,1200,600,'修改采购单');
	}
	function checkStatus(id,status) {
		if(window.confirm('确认此操作吗？')==false){
			return false;
		}else {
			$.ajax({
	           type: "POST",
	           url: "updatePurchaseStatus.sc",
	           data: {"id":id,"status":status},           
	           success: function(data){           
	              if(data=="success"){
	 		 		alert("操作成功!"); 	 
	 		 		window.location.reload();         		 			 		
	 		 	  }else if(data=="fail") {
	 		 	  	alert("操作失败!"); 	 
	 		 	  	return false;
	 		 	  }else if(data=="nullDetail") {
	 		 	  	alert("采购单没有任何商品，禁止审核!"); 	 
	 		 	  	return false;
	 		 	  }else if(data == "nocode"){
	 		 	  	alert("采购单有货品没有条码，禁止审核!"); 	 
	 		 	  	return false;
	 		 	  }           
	           }
	         }); 
         }
	}
	function exportDate(purchaseId) {
		if(purchaseId!="") {
			window.location.href = "${BasePath}/supply/manage/purchaseDetail/exportDatas.sc?purchaseId="+purchaseId;
	  	}
	}
</script>
</body>
</html>
