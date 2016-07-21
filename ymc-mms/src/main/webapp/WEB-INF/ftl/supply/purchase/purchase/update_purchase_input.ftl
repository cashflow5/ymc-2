<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,宜天网,B网络营销系统,商品管理" />
<meta name="Description" content=" , ,B网络营销系统-商品管理" />
<#include "../../../yitiansystem/yitiansystem-include.ftl">
<script type="text/javascript" src="${BasePath}/js/supply/supplier.js" ></script>
<title>B网络营销系统-采购管理-宜天网</title>
<script type="text/javascript">
//$(document).ready(function(){	
//	$("#type").attr("value",$("#purchaseT").attr("value"));	
//	$("#status").attr("value",$("#purchaseS").attr("value"));
//})

/*function updatePurchase() {
	 var id=$("#id").attr("value");
	 $.ajax({
	 	type: "POST",
        data: {"id":id}, 
        url: "updatePurchase.sc",        
        success: function(data){         	
 		 	if(data=="success"){
 		 		alert("保存成功!"); 		 		
 		 	}else{
 		 		alert("保存失败,请先添加货品到采购单!");
 		 		
 		 	}
 		 	window.location="toUpdate.sc?id=" + id; 		 	
        }
        
      });
}*/
function deletePurchaseDetail(purchaseId,id){	
		if(window.confirm('确认删除？')==false){
			return;
		}		
	    var value=id;	     	
       	$.ajax({
           type: "POST",
           url: "deletePurchaseDetail.sc",
           data: {"purchaseId":purchaseId,"purchaseDetailId":value},           
           success: function(data){           
              if(data=="success"){
 		 		alert("删除成功!"); 	 
 		 		window.location.reload();         		 			 		
 		 	  }              
           }
         }); 
      } 
     //选择供应商
    function tosupper(){
      openwindow('${BasePath}/supply/manage/purchase/to_suppliersp.sc',600,500,'选择供应商',1);
    }
</script>
</head><body>
<div class="container"> 
	<!--工具栏start-->
	<div class="toolbar">
		<div class="btn" onclick="checkStatus('${purchase.id?default("")}','0')">
			<span class="btn_l" >
			</span>
			<b class="ico_btn save"></b>
			<span class="btn_txt">
			提交审核
			</span>
			<span class="btn_r">
			</span>
		</div>
	</div>
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class='curr'>
					<span>
					编辑采购单
					</span>
				</li>
			</ul>
		</div>
		<div class="modify">
			<div class="blank10"></div>
			<form action="u_updatePurchase.sc" method="post" id="updatePurchaseForm" name="updatePurchaseForm">
				<input type="hidden" id="id" name="id" value="${purchase.id?default("")}"/>
				<input type="hidden" id="type" value="${purchase.type?default("")}">
				<table cellpadding="0" cellspacing="0" class="detail_table">
					<tbody>
						<tr>
							<th style="width:120px;" > 采购单号： </th>
							<td colspan="3">
								<span class="text_details">
								<B>${purchase.purchaseCode?default("")}</B>
								</span>
							</td>
						</tr>
						<tr>
							<th style="width:120px;"> 采购类型： </th>
							<td> <#if purchase.type?? && purchase.type == 102>自购固定价结算</#if>
								<#if purchase.type?? && purchase.type == 103>自购配折结算</#if>
							    <#if purchase.type?? && purchase.type == 106>招商底价代销</#if>
								<#if purchase.type?? && purchase.type == 107>招商扣点代销 </#if>
								<#if purchase.type?? && purchase.type == 108>招商配折结算</#if> </td>
							<th style="width:120px;"> 供应商： </th>
							<td> <input type="hidden" name="supplier.id" id="supplierId" value="<#if purchase?? && purchase.supplier??&& purchase.supplier.id??>${purchase.supplier.id!""}</#if>"> 
							<input class="inputtxt" style="width:200px;" type="text" name="supplier.supplier" id="supplierName" disabled="true" value="<#if purchase?? && purchase.supplier??&& purchase.supplier.supplier??>${purchase.supplier.supplier!""}</#if>">
							    <input type="hidden" name="setOfBooksCode" id="setOfBooksCode" value="<#if purchase??&&purchase.setOfBooksCode??>${purchase.setOfBooksCode!''}</#if>">
								<input type="button" onclick="tosupper();" class="btn-add-normal-4ft" value="选择供应商">
								<div id="setOfBooksName" ></div>
								<span id="supplierIdTip"></span>
							</td>
								
						</tr>
						<tr>
							<th> 收货仓库： </th>
							<td> ${purchase.warehouse.warehouseName?default('')} </td>
							<th> 采购员：<font class="ft-cl-r">*</font> </th>
							<td>
								<input class="inputtxt" type="text" id="purchaser" maxlength="20" name="purchaser" value="${purchase.purchaser?default('')}"/>
								<span id="purchaserTip">
								</span>
							</td>
								
						</tr>
						<tr>
							<th> 采购时间：<font class="ft-cl-r">*</font> </th>
							<td>
								<input class="inputtxt" type="text" id="purchaseDate" name="purchaseDateStr"  value="${purchase.purchaseDate?default('')}" />
							</td>
							<th> 计划交货时间：<font style="color:#ff0000;">*</font> </th>
							<td>
								<input class="inputtxt" type="text" id="planTime" name="planTimeStr"  value="${purchase.planTime?default('')}" />
							</td>
						</tr>
						<tr>
							<th> 收货人： </th>
							<td>
								<input class="inputtxt" type="text" maxlength="20" id="receivePeople" name="receivePeople" value="${purchase.receivePeople?default('')}"/>
							</td>
							<th> 收货人联系电话： </th>
							<td>
								<input class="inputtxt" type="text" id="receivePhone" maxlength="15" name="receivePhone" value="${purchase.receivePhone?default('')}" />
							<span id="receivePhoneTip"></span>
							</td>
						</tr>
						<tr>
							<th> 采购订补类型： </th>
							<td>
								<select id="purchaseType" name="purchaseType"  style="width:120px;" >
									<option value="">请选择</option>
									<option value="1" <#if purchase.purchaseType?? && purchase.purchaseType == '1'>selected</#if>>订货
							
									</option>
									<option value="2" <#if purchase.purchaseType?? && purchase.purchaseType == '2'>selected</#if>>补货
							
									</option>
								</select>
								<span id="purchaseTypeTip">
								</span>
							</td>
							<#if purchase.type?? && purchase.type == 102>
							</td>
				            	<th>物料价 ：</th>
				            	<td>
				            	 <input type="radio" name="wlPrice" value="1" <#if purchase.wlFlags??&&purchase.wlFlags=='1'>checked</#if>>有
				            	 <input type="radio" name="wlPrice" value="0" <#if purchase.wlFlags??&&purchase.wlFlags=='0'>checked</#if>>无
				            	 <input type="hidden" id="wlFlags" name="wlFlags" value="0">
				            	</td>
							</#if>
						</tr>
						<tr>
							<th> 收货地址： </th>
							<td colspan="5">
								<input style="width:445px;" class="inputtxt"  type="text" maxlength="100" id="receiveAddress" name="receiveAddress" value="${purchase.receiveAddress?default('')}"/>
							</td>
						</tr>
						<tr>
							<th> 备注： </th>
							<td colspan="3">
								<textarea  class="textareatxt" cols="70" rows="3" id="memo" name="memo" maxlength="200">${purchase.memo?default('')}</textarea>
							</td>
						</tr>
					</tbody>
				</table>
				<div class="blank10"></div>
				<input type="button" onclick="javascript:selectProducts();" value="添加商品" class="btn-add-normal-4ft"/>
				<div class="pro-baseinf-list">
					<input type="hidden" id="productIds" name="productIds" />
					<textarea cols="70" style="width:570px;" rows="4" id="commodityName" disabled="disabled" name="commodityName" ></textarea>
				</div>
				<div class="blank10"></div>
					<input type="submit" value="提交商品" class="btn-add-normal-4ft"/>
					<!--<input type="button" value="取消" class="btn-add-normal-4ft" onClick="chanleAddProduct();"/>    --> 
			</form>
			<div class="blank10"></div>
			<form action="${BasePath}/supply/manage/purchase/u_updatePurchaseUI.sc" method="post" id="listForm" name="listForm"  encType="multipart/form-data">
				<input type="hidden" id="id" name="id" value="${purchase.id}"/>
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td style="height:35px;line-height:35px;"> <strong>下载模板：</strong>
							<select id="typeId" name="typeId" style="width:70px;">
								<option value="">请选择</option>
								<#list sizeTypes as sizeType>
								<option value="${sizeType.id}-${sizeType.name}" >${sizeType.name}</option>
								</#list>
							</select>
							<a href="javascript:;" onclick="exportXls();"  class="blue">导出模板 </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <b>导入采购货品：</B>
							<input name="uploadFile" type="file" id="uploadFile" />
							<input type="button" id="importButton" value="导入" onclick="importDetail('${BasePath}/supply/manage/purchaseDetail/c_importPurchaseDetail.sc');" class="btn-add-normal" />
						</td>
					</tr>
					<tr>
						<td style="height:20px;line-height:20px;"> 合计数量：<strong>${purchase.amount!""}</strong>件 &nbsp;&nbsp;合计金额： <strong>${purchase.totalPrice!""}</strong> </td>
					</tr>
				</table>
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th> 商品名称：</th>
						<td>
							<input class="inputtxt" type="text" name="commodityName"  value="<#if vo??&&vo.commodityName??>${vo.commodityName}</#if>" />&nbsp;
						</td>
						<th> 商品款号：</th>
						<td>
							<input class="inputtxt" type="text" name="styleNo"  value="<#if vo??&&vo.styleNo??>${vo.styleNo}</#if>" />&nbsp;
						</td>
						<th> 款色编码：</th>
						<td>
							<input class="inputtxt" type="text" name="supplierCode"  value="<#if vo??&&vo.supplierCode??>${vo.supplierCode}</#if>" />&nbsp;
						</td>
						<th> 货品条码：</th>
						<td>
							<input class="inputtxt" type="text" name="insideCode"  value="<#if vo??&&vo.insideCode??>${vo.insideCode}</#if>" />&nbsp;
						</td>
						<th></th>
						<td><input type="submit" value="查询" class="btn-add-normal" onclick="searchDetail();"/></td>
					</tr>
				</table>
				<table width="100%" class="list_table" border="0" cellspacing="0" cellpadding="0">
					<thead>
						<tr>
							<th>
								<input type="checkbox"  name="allselect" onclick="allSelects();"/>
								全选</th>
							<th>货品条码</th>
							<th>款色编码</th>
							<th>商品名称</th>
							<th>商品款号</th>
							<th>规格</th>
							<th>单位 </th>
							<th>采购数量 </th>
							<th>库存数量</th>
							<#if purchase.type?? && purchase.type == 102>
							<th>采购单价</th>
							</#if>
							<#if purchase.type?? && purchase.type == 103>
							<th>吊牌价</th>
							<th>配折率</th>
							</#if>
							<#if purchase.type?? && purchase.type == 107>
							<th>优购价</th>
							<th>倒扣率</th>
							</#if>
							<#if purchase.type?? && purchase.type == 106>
							<th>采购单价</th>
							</#if>
							<#if purchase.type?? && purchase.type == 108>
							<th>吊牌价</th>
							<th>配折率</th>
							</#if>
							<th>总价</th>
							<th>操作</th>
						</tr>
					</thead>
					<tr>
					<tbody>
					<#if pageFinder??>
					<#if pageFinder.data??>				 
					<#list pageFinder.data as item>
					
						<td>
							<input type="checkbox" id="${item['id']!""}_ids"  name="ids" value="${item['id']!""}"/>
						</td>
						<td>${item['inside_code']!""}</td>
						<td>${item['supplier_code']!""}</td>
						<td>${item['commodity_name']!""}</td>
						<td>${item['style_no']!""}</td>
						<td>${item['specification']!""}</td>
						<td>
							<input type="text" id="${item['id']!""}_unit" name="unit" size="3" value="${item['unit']!""}"/>
						</td>
						<td>
							<input type="text" id="${item['id']!""}_purchaseQuantity" size="5" name="purchaseQuantity" value="${item['purchase_quantity']?default(0)?string('####')}"/>
						</td>
						<td>${item['stock_quantity']?default("")}</td>
						<#if purchase.type?? && purchase.type == 102>
						<td>${item['purchase_price']!""}</td>
						</#if>
						<#if purchase.type?? && purchase.type == 103>
						<td>${item['purchase_price']!""}</td>
						<td>${item['deduction_rate']!""}%</td>
						</#if>
						<#if purchase.type?? && purchase.type == 107>
						<td>${item['purchase_price']!""}</td>
						<td>${item['deduction_rate']!""}%</td>
						</#if>
						<#if purchase.type?? && purchase.type == 106>
						<td>${item['purchase_price']!""}</td>
						</#if>
						<#if purchase.type?? && purchase.type == 108>
						<td>${item['purchase_price']!""}</td>
						<td>${item['deduction_rate']!""}%</td>
						</#if>
						<input type="hidden" id="${item['id']!""}_purchasePrice" size="5" name="purchasePrice" value="${item['purchase_price']?default(0)?string('#####0.00')}"/>
						<td>${item['purchase_total_price']!""}</td>
						<td > <a href="javascript:updateDetail('${item['id']!""}')">保存</a>&nbsp;&nbsp; <a href="javascript:deletePurchaseDetail('${item['purchase_id']!""}','${item['id']!""}');">删除</a> </td>
					</tr>
					</#list>	
					</#if>
					</#if>
					
							</tbody>
					
				</table>
			</form>
			<p style="padding:10px 0 0 10px;">
				<input type="button" onclick="updateDetails()" value="批量保存" class="btn-add-normal-4ft"/>
			</p>
			<#if pageFinder??>
			<div class="bottom clearfix"> 
				<!-- 翻页标签 --> 
				<#import "../../../common.ftl"  as page>
				<@page.queryForm formId="listForm" />
			</div>
			</#if> </div>
	</div>
</div>
</body>
</html>
<script type="text/javascript">
	//非空验证
   var config={
		  		form:"updatePurchaseForm",
		  		submit:submitForm,
			 	fields:[
					{name:'purchaser',allownull:false,regExp:/^\S+$/,defaultMsg:'请输入采购员',focusMsg:'请输入采购员',errorMsg:'采购员不能为空!',rightMsg:'采购员输入正确',msgTip:'purchaserTip'},
					{name:'supplier.supplier',allownull:false,regExp:/^\S+$/,defaultMsg:'请选择供应商',focusMsg:'请选择供应商',errorMsg:'供应商不能为空',rightMsg:'供应商输入正确',msgTip:'supplierIdTip'},
					{name:'receivePhone',allownull:true,regExp:/^[1-9]\d*|0$/,defaultMsg:'请输入收货人电话',focusMsg:'请输入收货人电话',errorMsg:'电话有误',rightMsg:'收货人电话输入正确',msgTip:'receivePhoneTip'},
					{name:'purchaseType',allownull:true,regExp:/^\S+$/,defaultMsg:'请输入采购订补类型',focusMsg:'请选择采购订补类型',errorMsg:'采购订补类型有误',rightMsg:'采购订补类型输入正确',msgTip:'purchaseTypeTip'}
				]
			}

	Tool.onReady(function(){
		var f = new Fv(config);
		f.register();
	});
	
	function submitForm(){
		var purchaseDate = $("#purchaseDate").val();
		if(purchaseDate=="") {
			alert("请输入采购时间");
			return false;
		}
		var planTime = $("#planTime").val();
		if(planTime=="") {
			alert("请输入计划交货时间");
			return false;
		}
		var setOfBooksCode = $("#setOfBooksCode").val();
		if(setOfBooksCode=="") {
			alert("供应商成本帐套为空，请先添加");
			return false;
		}
	   var wlPrice=$('input:radio[name="wlPrice"]:checked').val();
       if(wlPrice==0){
         $("#wlFlags").val(0);
       }else{
         $("#wlFlags").val(1);
       }
  		return true;
  	}
  	function selectProducts() {
  		//alert("dd");
  		openwindow('${BasePath}/supply/manage/purchase/selectProduct.sc',900,700,'选择商品');
  		//alert("2");
  	}
  	function updateDetail(detailId){
  		var _unit = $("#"+detailId+"_unit").val();
  		var _purchaseQuantity = $("#"+detailId+"_purchaseQuantity").val();
  		var _purchasePrice = $("#"+detailId+"_purchasePrice").val();
  		var _deductionRate = $("#"+detailId+"_deductionRate").val();
  		var _purchasePrice = $("#"+detailId+"_purchasePrice").val();
  		$.ajax({
           type: "POST",
           url: "updatePurchaseDetail.sc",
           data: {"id":detailId,"unit":_unit,"purchaseQuantityStr":_purchaseQuantity,"deductionRateStr":_deductionRate,"purchasePriceStr":_purchasePrice},           
           success: function(data){           
              if(data=="success"){
 		 		alert("保存成功!"); 	 
 		 		//window.location.href = "${BasePath}/supply/manage/purchase/u_updatePurchaseUI.sc?id="+$('#id').val();
 		 		window.location.reload();         		 			 		
 		 	  }else{
 		 	   alert("保存失败!!");
 		 	  }              
           }
         }); 
  	}
  	function updateDetails(){
  		var checkedids = $(":checkbox[name=ids]");
  		var ids = "";
  		var units = "";
  		var purchaseQuantitys = "";
  		var _deductionRate = "";
  		var _purchasePrices = "";
  		var checkednum = 0;
  		for(var i=0; i<checkedids.length; i++) {
  			if(checkedids[i].checked) {
				var id = checkedids[i].value;
	  			ids += id + "~";
	  			units += $("#"+id+"_unit").val() + "~";
	  			purchaseQuantitys += $("#"+id+"_purchaseQuantity").val() + "~";
	  			_deductionRate += $("#"+id+"_deductionRate").val() + "~";
	  			_purchasePrices += $("#"+id+"_purchasePrice").val() + "~";
	  			checkednum++;
  			}
  		}
  		if(checkednum>0) {
	  		$.ajax({
	           type: "POST",
	           url: "updatePurchaseDetails.sc",
	           data: {"ids":ids,"units":units,"purchaseQuantitys":purchaseQuantitys,"deductionRates":_deductionRate,"purchasePrices":_purchasePrices},           
	           success: function(data){           
	              if(data=="success"){
	 		 		alert("批量保存成功!"); 	 
	 		 		window.location.reload();         		 			 		
	 		 	  }else{
	 		 	  	alert("批量保存失败!"); 
	 		 	  	return false;	 
	 		 	  }             
	           }
	         }); 
         }else {
         	alert("请选择要保存的商品!"); 
         	return false;
         }
  	}
  	function allSelects() {
		var checked = $(":checkbox[name=allselect]").attr("checked");
		$(":checkbox[name=ids]").attr("checked",checked);
	}
	function chanleAddProduct() {
		$("#productNos").val("");
		$("#supplierPriceSpIds").val("");
		$("#commodityName").val("");
	}
	function checkStatus(id,status) {
	    var setOfBooksCode = $("#setOfBooksCode").val();
		if(setOfBooksCode=="") {
			alert("供应商成本帐套为空，请先添加");
			return false;
		}
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
	 		 		closewindow();	 
	 		 		dg.curWin.location.reload();         		 			 		
	 		 	  }else if(data=="fail") {
	 		 	  	alert("操作失败!"); 	 
	 		 	  	return false;
	 		 	  }else if(data=="nullDetail") {
	 		 	  	alert("采购单没有任何商品，禁止审核!"); 	 
	 		 	  	return false;
	 		 	  }  
	           }
	         }); 
         }
	}
	function updateClose() {
		closewindow();
		dg.curWin.location.reload();
	}
	function checkValue(obj,oldprice) {
		var newprice = parseFloat(obj.value);
		var oldprice1 = parseFloat(oldprice);
		if(newprice<oldprice1) {
			alert("采购数量不能小于"+oldprice+"基本采购数量");
			obj.value = oldprice;
			return false;
		}
	}
	function importDetail(url) {
      		var file = $("#uploadFile").val();
      		if(file!=""){
		  			file=file.substring(file.lastIndexOf('\\')+1);
		  		    var fileStr=file.substring(file.lastIndexOf('.')+1);
		  		    if(fileStr!='xls'&&fileStr!='xlsx'){
		      		   alert("上传附件文件格式限制为.xls、.xlsx ");
		      		   return false;
		  			}else {
		  				var form = document.getElementById("listForm");
			      		form.action = url;
			      		form.submit();
			      		$("#importButton").attr("disabled", "disabled");
		  			}
		      }else {
		      	alert("请选择要导入的xls文件!");
		      }
      }
      function exportXls() {
      	var typeId = $("#typeId").val();
      	if(typeId!="") {
      		window.location.href = "${BasePath}/supply/manage/purchaseDetail/c_exportTemplate.sc?typeId="+typeId;
      	}else {
      		alert("请选择类型");
      	}
      }
      
      
$(function(){ 
$('#purchaseDate').calendar({maxDate:'#planTime' }); 
$('#planTime').calendar({minDate:'#purchaseDate' });
});
</script>