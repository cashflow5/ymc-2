<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,宜天网,B网络营销系统,商品管理" />
<meta name="Description" content=" , ,B网络营销系统-商品管理" />
<link rev="stylesheet" rel="stylesheet"  type="text/css" href="css/style.css"/>
<link rev="stylesheet" rel="stylesheet" type="text/css" href="css/css.css" />
<script type="text/javascript" src="../js/common.js"></script>
<script type="text/javascript" src="../js/jquery-1.3.2.min.js" ></script>
<script type="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
<script type="text/javascript" src="${BasePath}/js/supply/supplier.js" ></script>
<#include "../../supply_include.ftl">
<#include "../../../yitiansystem/yitiansystem-include.ftl">
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
</script>		
</head>
<body>
<div class="main-body" id="main_body">
<input type="hidden" id="basePath" value="${BasePath}">
			<div class="ytback-tt-1 ytback-tt">
            	<span>您当前的位置：</span>采购管理 &gt; 采购单 &gt;编辑
    		</div>
			<div class="pro-list">
				<div class="mb-btn-fd-bd">
					<div class="mb-btn-fd relative">
						<span class="btn-extrange absolute ft-sz-14">
							<ul class="onselect">
								<li class="pl-btn-lfbg">
								</li>
								<li class="pl-btn-ctbg">
									<a href="#" class="btn-onselc">编辑采购单</a>
								</li>
								<li class="pl-btn-rtbg">
								</li>
							</ul> </span>
					</div>				
				<div class="add-newpd ft-sz-12 fl-rt"><a href="#" onclick="updateClose();" alt="关闭">关闭</a></div>	
				<div class="add-newpd ft-sz-12 fl-rt"><a href="#" onclick="checkStatus('${purchase.id?default("")}','0')" >提交审核</a></div>
		</div>
	</div> 
         
    <form action="updatePurchase.sc" method="post" id="updatePurchaseForm" name="updatePurchaseForm"> 	
 		<input type="hidden" id="id" name="id" value="${purchase.id?default("")}"/>  
 		<input type="hidden" id="type" value="${purchase.type?default("")}">   
    <div class="add-pro-div">   
        <div>
             <table>
               <tr>
	               	<td>    
	            		<span class="text_details">供应商：</span>
	            	</td>
	                <td>
	                	${purchase.supplier.supplier?default("")}
	                </td> 
	                <td>
	                </td>  
	                <td>	
	            		<span class="text_details">采购类型：</span>
	                </td>
	                <td>
	                <#if purchase.type?? && purchase.type == 102>自购</#if>
	                <#if purchase.type?? && purchase.type == 107>比例代销</#if>
	                <#if purchase.type?? && purchase.type == 106>协议代销</#if>
	                <#if purchase.type?? && purchase.type == 108>配折结算</#if>
	                </td>
	                <td>
	                	<span id="typeTip"></span>
	                </td>                       	                
               </tr>               
               <tr>
	               <td>	                                                  	           	
	                	<span class="text_details">物理仓库：</span>
	           		</td>
	           		 <td>
	                	${purchase.warehouse.warehouseName?default('')}
	                </td>
	                <td>
	                </td>
	                <td>
	                	<span class="text_details">采购员：<font class="ft-cl-r">*</font></span>
	                </td>
	                <td>
	                	<input type="text" id="purchaser" maxlength="20" name="purchaser" value="${purchase.purchaser?default('')}"/>            
	            	</td>
	            	<td>
	            		<span id="purchaserTip"></span>
	            	</td>
           	  </tr>           		
              <tr>  
              		<td>
	               		<span class="text_details">采购时间：<font class="ft-cl-r">*</font></span>
	                </td>
	                <td>
                		<input type="text" id="purchaseDate" name="purchaseDateStr" 
                	class="Wdate" style="width:152px;"   value="${purchase.purchaseDate?default('')}"
                	onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'planTime\')}'})" size="18"/>           
           	 		</td>
           	 		<td>
           	 		</td>              
	              	<td>
	                <span class="text_details">计划交货时间：<font class="ft-cl-r">*</font></span>
	                </td>
	                <td>
	                <input type="text" id="planTime" name="planTimeStr" 
	                class="Wdate" style="width:152px;"   value="${purchase.planTime?default('')}" 
	                onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'purchaseDate\')}'})" size="18"/>
					</td>
					<td>
					</td>
                </tr>
            	<tr>       	
            		<td>
            			<span class="text_details">收货人：</span>
            		</td>
            		<td>
            			<input type="text" maxlength="20" id="receivePeople" name="receivePeople" value="${purchase.receivePeople?default('')}"/>
            		</td>
            		<td>
            		</td>            	          		
            		<td>            	
	            	<span class="text_details">收货人联系电话：</span> 
	            	</td>
	            	<td>
	            	<input type="text" id="receivePhone" maxlength="15" name="receivePhone" value="${purchase.receivePhone?default('')}" /> 
	            	</td>
	            	<td>
	            	<span id="receivePhoneTip"></span>
	            	</td>   		            		                
	            </tr>
	            <tr>       	
            		<td>
            			<span class="text_details">收货地址：</span>
            		</td>
            		<td colspan="5">
            			<input size=90 type="text" maxlength="100" id="receiveAddress" name="receiveAddress" value="${purchase.receiveAddress?default('')}"/>
            		</td>
            	</tr> 	
	            </tr>	
	             <tr>       	
            		<td>
            			<span class="text_details">备注：</span>
            		</td>
            		<td colspan="5">
            			<textarea cols="70" rows="3" id="memo" name="memo" maxlength="60">
	                 ${purchase.memo?default('')}
	                 </textarea>
            		</td>
	            </tr>    
	         </table> 
		</div>
		<div class="add-newpd ft-sz-12 fl-lt"><a href="javascript:selectProducts()">添加货品</a></div>            
	            <div class="pro-baseinf-list">
		             <input type="hidden" id="productNos" name="productNos" /> 
		             <input type="hidden" id="supplierPriceSpIds" name="supplierPriceSpIds" />
		             <textarea cols="70" rows="3" id="commodityName" disabled="disabled" name="commodityName" >
		             </textarea>   
	            </div>
			    <div class="div-pl-bt">
			    	<input type="submit" value="提交" class="btn-sh"/>
			    	<input type="button" value="取消" class="btn-sh" onClick="chanleAddProduct();"/>        
			    </div>                       		
        	</div>      
	</form>
	
		 
      <form action="${BasePath}/supply/manage/purchase/toUpdate.sc" method="post" id="listForm" name="listForm">
      <input type="hidden" id="id" name="id" value="${purchase.id}"/>      
      <div class="add-pro-div">   
        <div class="div-pl-hd ft-sz-12">
        		<table>
               	  <tr>
               	  	<td>
	                	<span class="text_details">合计数量：</span>
	                </td>
	                <td>
	                	<B>${purchase.amount?default('')} </B>&nbsp;&nbsp;&nbsp;&nbsp;        
	            	</td>
     				 <td>
	                	<span class="text_details">合计金额：</span>
	                </td>
	                <td>
	                	<B>${purchase.totalPrice?default('')}</B>           
	            	</td>
	              </tr>
	            </table>	            
	        </div>
	     </div>	            
      <div class="div-pl-table" id="div-table">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					  <tr class="div-pl-tt">
					  	<td class="pl-tt-td" width="5%"><input type="checkbox"  name="allselect" onclick="allSelects();"/>全选</td>
					  	<td class="pl-tt-td" >货品编号</td>	
					  	<td class="pl-tt-td" >商品款色编号</td>
						<td class="pl-tt-td" >商品名称</td>	
						<td class="pl-tt-td">规格</td>												
						<td class="pl-tt-td">单位 </td>
						<td class="pl-tt-td" >采购数量 </td>
						<td class="pl-tt-td">采购单价</td>	
						<td class="pl-tt-td">总价</td>											
						<td class="pl-tt-td">扣点比例</td>
						<td class="pl-tt-td">库存数量</td>
						<td class="pl-tt-td">操作</td>
					  </tr>	
					  <#if pageFinder??>
					  <#if pageFinder.data??>				 
					  <#list pageFinder.data as purchaseDetail>
					  	<td><input type="checkbox" id="${purchaseDetail.id}_ids"  name="ids" value="${purchaseDetail.id}"/></td> 
					  	<td>${purchaseDetail.productNo?default("&nbsp;")}</td>
					  	<td>
					  	<#if purchaseDetail??&&purchaseDetail.commodity??&&purchaseDetail.commodity.supplierCode??>
					  	${purchaseDetail.commodity.supplierCode}
					  	</#if>
					  	</td>												
						<td>${purchaseDetail.commodityName?default("&nbsp;")}</td>
						<td>${purchaseDetail.specification?default("&nbsp;")}</td>				
						<td>
						<input type="text" id="${purchaseDetail.id}_unit" name="unit" size="3" value="${purchaseDetail.unit?default("&nbsp;")}"/>
						</td>
						<td>
						<#--onchange="return checkValue(this,'${purchaseDetail.purchaseQuantity?default(0)?string('##0')}')"-->
						<input type="text" id="${purchaseDetail.id}_purchaseQuantity" size="5" name="purchaseQuantity" value="${purchaseDetail.purchaseQuantity?default(0)?string('##')}"/>
						</td>
						<td>
						${purchaseDetail.purchasePrice?default(0)?string('##0.00')}
						</td>
						<td>${purchaseDetail.purchaseTotalPrice?default(0)?string('##0.00')}</td>
						<td>
						<#if purchaseDetail??&&purchaseDetail.purchaseType??&&purchaseDetail.purchaseType=107>
						${purchaseDetail.deductionRate?default(0)?string('##0.00')}
						<#else>
						&nbsp;
						</#if>
						</td>	
						<td>${purchaseDetail.stockQuantity?default("")}</td>
						<td >		
						<a href="javascript:updateDetail('${purchaseDetail.id}')">保存</a>&nbsp;&nbsp;							
						<a href="javascript:deletePurchaseDetail('${purchase.id}','${purchaseDetail.id}');">删除</a>
						</td>
					  </tr>
					  </#list>	
					  </#if>
		 			  </#if>					 
				  </table>
			</div>
			</form>
			<div class="div-pl-bt">
		    	<input type="button" onclick="updateDetails()" value="批量保存" class="btn-sh"/>
		    </div>	
			<div class="div-pl-bt">
				<!-- 翻页标签 -->
					<#import "../../../common.ftl"  as page>
					<@page.queryForm formId="listForm" />				  
			</div>	
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
					{name:'receivePhone',allownull:true,regExp:/^[1-9]\d*|0$/,defaultMsg:'请输入收货人电话',focusMsg:'请输入收货人电话',errorMsg:'电话有误',rightMsg:'采购员输入正确',msgTip:'receivePhoneTip'}
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
  		return true;
  	}
  	function selectProducts() {
  		art.dialog.openwindow('${BasePath}/supply/manage/purchase/toSelectCommodity.sc?supplierId=${purchase.supplier.id?default("")}&type=${purchase.type?default("")}',800,600,'yes',{id:'open1',title: '选择商品'});
  	}
  	function updateDetail(detailId){
  		var _unit = $("#"+detailId+"_unit").val();
  		var _purchaseQuantity = $("#"+detailId+"_purchaseQuantity").val();
  		var _purchasePrice = $("#"+detailId+"_purchasePrice").val();
  		var _deductionRate = $("#"+detailId+"_deductionRate").val();
  		$.ajax({
           type: "POST",
           url: "updatePurchaseDetail.sc",
           data: {"id":detailId,"unit":_unit,"purchaseQuantityStr":_purchaseQuantity},           
           success: function(data){           
              if(data=="success"){
 		 		alert("保存成功!"); 	 
 		 		window.location.reload();         		 			 		
 		 	  }              
           }
         }); 
  	}
  	function updateDetails(){
  		var checkedids = $(":checkbox[name=ids]");
  		var ids = "";
  		var units = "";
  		var purchaseQuantitys = "";
  		var checkednum = 0;
  		for(var i=0; i<checkedids.length; i++) {
  			if(checkedids[i].checked) {
				var id = checkedids[i].value;
	  			ids += id + "~";
	  			units += $("#"+id+"_unit").val() + "~";
	  			purchaseQuantitys += $("#"+id+"_purchaseQuantity").val() + "~";
	  			checkednum++;
  			}
  		}
  		if(checkednum>0) {
	  		$.ajax({
	           type: "POST",
	           url: "updatePurchaseDetails.sc",
	           data: {"ids":ids,"units":units,"purchaseQuantitys":purchaseQuantitys},           
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
	 		 		art.dialog.close();	 
	 		 		art.dialog.parent.location.reload();         		 			 		
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
		art.dialog.close();
		art.dialog.parent.location.reload();
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
</script>
