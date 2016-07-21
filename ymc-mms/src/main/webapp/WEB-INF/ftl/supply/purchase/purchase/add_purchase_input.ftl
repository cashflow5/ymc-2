<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,优购网,B网络营销系统,采购管理" />
<meta name="Description" content=" , ,B网络营销系统-采购管理" />
<#include "../../supply_include.ftl">
<#include "../../../yitiansystem/yitiansystem-include.ftl">
<script type="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
<script src="${BasePath}/js/supply/supplier.js" type="text/javascript"></script>
<script type="text/javascript">
 //选择供应商
    function tosupper(){
      openwindow('${BasePath}/supply/manage/purchase/to_suppliersp.sc',800,500,'选择供应商',1);
    }
    
    //选择类型
    function changeType(va){
       if(va==102){
          $("#dis1").show();
          $("#dis2").show();
       }else{
          $("#dis1").hide();
          $("#dis2").hide();
       }
    }
    
</script>
<title>B网络营销系统-采购管理-优购网</title>
</head>
<body>
<div class="container">
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class='curr'>
				  <span>添加采购单</span>
				</li>
			</ul>
		</div>
 <div class="modify">
 <form action="c_addPurchase.sc" method="post" id="savePurchaseForm" name="savePurchaseForm"> 	
    <div class="divH12"></div>   
              <table cellpadding="0" cellspacing="0" class="list_table">
             	<tr>
            	<td>
            	<span class="text_details">采购类型：<font class="ft-cl-r">*</font></span>
                </td>
                <td>
                <select id="type" name="type" style="width:120px;" onchange="changeType(this.value)">
                	<option value="">请选择</option> 
                	<option value="102">自购固定价结算</option> 
                	<option value="103">自购配折结算</option> 
                	<option value="106">招商底价代销</option> 
                	<option value="107">招商扣点代销 </option> 
                	<option value="108">招商配折结算</option> 
                </select> 
                </td>
                <td>
                <span id="typeTip"></span>
            	</td>
            	 <td>
                <span class="text_details">收货仓库：<font class="ft-cl-r">*</font></span>
                </td>
                <td>
                <select id="warehouse.id" name="warehouse.id" onchange="findWhourseById(this.value)"  style="width:120px;" > 
                	<option value="">请选择</option> 
                	<#list warehouses as warehouse>
                	<option value="${warehouse.id}" >${warehouse.warehouseName}</option> 
                	</#list>
                </select>
                </td>
                <td>
                <span id="warehouse.idTip"></span>
                </td>
            	</tr>
            	<tr>                                                 	           	
                <td>
                <span class="text_details">采购员：<font class="ft-cl-r">*</font></span>
                </td>
                <td>
                <input type="text" id="purchaser" maxlength="20" name="purchaser" />            
            	</td>
            	<td>
            	<span id="purchaserTip"></span>	                	                
            	</td>
            	
            	<td style="margin-left:-6px;">
                <span class="text_details" style="margin-left:-6px;">收货人：</span>
            	</td>
            	<td> 
            	<input type="text" id="receivePeople" maxlength="20" name="receivePeople" />
            	</td>
            	</tr>
            	
            	<tr>            	
            	<td>
                <span class="text_details">采购时间：<font class="ft-cl-r">*</font></span>
                </td>
                <td>
                <input type="text" id="purchaseDate" name="purchaseDateStr"  size="18"/>
           		</td>
           		<td>
           		</td>
           		
           		<td>                
                <span class="text_details">计划交货时间：<font class="ft-cl-r">*</font></span>
                </td>
                <td>
                <input type="text" id="planTime" name="planTimeStr"  size="18"/>
            	</td>
            	<td>
            	</td>
            </tr>
            <tr>	
            	<td>            	
            	<span class="text_details">收货人联系电话：</span> 
            	</td>
            	<td>
            	<input type="text" id="receivePhone" maxlength="15" name="receivePhone" /> 
            	</td>
            	<td>
            	<span id="receivePhoneTip"></span>
            	</td>
            	<td>            	
            	<span class="text_details">选择供应商：</span> 
            	</td>
            	<td>
	              <input type="hidden" name="supplier.id" id="supplierId" >
	                <input type="text" name="supplier.supplier" id="supplierName" disabled="true">
	                <input type="hidden" name="setOfBooksCode" id="setOfBooksCode">
	                <input type="button" onclick="tosupper();" class="btn-add-normal-4ft" value="选择供应商">
	                <div id="setOfBooksName" ></div>
            	</td>
            	<td>
            	<span id="supplierIdTip"></span>
            	</td>
            </tr>
            <tr>	
            	<td>            	
            	<span class="text_details">采购订补类型：</span> 
            	</td>
            	<td>
            	<select id="purchaseType" name="purchaseType"  style="width:120px;" > 
                	<option value="">请选择</option> 
                	<option value="1" >订货</option> 
                	<option value="2" >补货</option> 
                </select>
            	</td>
            	<td>
            	<span id="purchaseTypeTip"></span>
            	</td>
            	<td style="display:none;" id="dis1">   
            	<span class="text_details">物料价 ：</span>     	
            	</td>
            	<td style="display:none;"  id="dis2">
            	 <input type="radio" name="wlPrice" value="1">有
            	 <input type="radio" name="wlPrice" value="0">无
            	 <input type="hidden" id="wlFlags" name="wlFlags" >
            	</td>
            </tr>
            </table>            	  
            <span class="text_details">收货地址：</span>
            <input size=90 type="text" id="receiveAddress" maxlength="100" name="receiveAddress" />
            <div class="blank10"></div>
            	<span class="text_details">备注：</span>
                 <textarea cols="70" rows="3" id="memo" name="memo" >可以填写pos采购单备注</textarea>                
            <div class="add-newpd ft-sz-12 fl-lt" style="margin-top:5px;">
            <input type="button" onclick="javascript:selectProducts();" value="添加商品" class="btn-add-normal-4ft"/>
            </div>	
            <div class="blank10"></div> 
            <div class="pro-baseinf-list">
             <input type="hidden" id="productIds" name="productIds" /> 
             <textarea cols="70" rows="3" id="commodityName" disabled="disabled" name="commodityName" >
             </textarea>   
            </div>
		    <div class="div-pl-bt">
		    	<input type="submit" value="保存" class="btn-add-normal-4ft"/>
		    </div>                       		
        </div>      
	</div>
	</form>	 
	
</div>
</body>
</html>
<script type="text/javascript">
	//非空验证
   var config={
		  		form:"savePurchaseForm",
		  		submit:submitForm,
			 	fields:[
					{name:'type',allownull:false,regExp:/^\S+$/,defaultMsg:'请选择采购类型',focusMsg:'请选择采购类型',errorMsg:'采购类型不能为空!',rightMsg:'采购类型选择正确',msgTip:'typeTip'},
					{name:'warehouse.id',allownull:false,regExp:/^\S+$/,defaultMsg:'请选择物理仓库',focusMsg:'请选择物理仓库',errorMsg:'物理仓库不能为空',rightMsg:'物理仓库选择正确',msgTip:'warehouse.idTip'},
					{name:'purchaser',allownull:false,regExp:/^\S+$/,defaultMsg:'请输入采购员',focusMsg:'请输入采购员',errorMsg:'采购员不能为空!',rightMsg:'采购员输入正确',msgTip:'purchaserTip'},
					{name:'receivePhone',allownull:true,regExp:/^[1-9]\d*|0$/,defaultMsg:'请输入收货人电话',focusMsg:'请输入收货人电话',errorMsg:'电话有误',rightMsg:'收货人联系电话输入正确',msgTip:'receivePhoneTip'},
					{name:'supplier.supplier',allownull:false,regExp:/^\S+$/,defaultMsg:'请选择供应商',focusMsg:'请选择供应商',errorMsg:'供应商不能为空',rightMsg:'供应商输入正确',msgTip:'supplierIdTip'},
					{name:'purchaseType',allownull:false,regExp:/^\S+$/,defaultMsg:'请选择采购订补类型',focusMsg:'请选择采购订补类型',errorMsg:'采购订补类型不能为空',rightMsg:'采购订补类型输入正确',msgTip:'purchaseTypeTip'}
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
	   var typeValue = $("#type").val();
	   if(typeValue==102) {
	   	   var wlPrice=$('input:radio[name="wlPrice"]:checked').val();
	       if(wlPrice==0){
	         $("#wlFlags").val(0);
	       }else if(wlPrice==1){
	         $("#wlFlags").val(1);
	       }else {
	        alert("请选择物料价格");
	       	return false;
	       }
	   }
  	   return true;
  	}
  	function selectProducts() {
		openwindow('${BasePath}/supply/manage/purchase/selectProduct.sc',900,700,'选择商品');
  	}
  	function selectSupperList() {
  		openwindow('${BasePath}/supply/manage/supplier/toSelectSupplier.sc',800,600,'选择供应商');
  	}
  	
  	function findWhourseById(id) {
  		var time = new Date();
		$.ajax({
	       type: "POST",
	       url: "${BasePath}/supply/manage/purchase/findWhourseByCode.sc?id="+id+"&time="+time,
	       dataType:"json",
	       success: function(data){  
	       		
	       		$("#receivePeople").val(data.contact);         
		 		$("#receivePhone").val(data.telPhone);    
		 		$("#receiveAddress").val(data.warehouseAddress);    
	       }
	     });
  	}
$(function(){ 
$('#purchaseDate').calendar({maxDate:'#planTime' }); 
$('#planTime').calendar({minDate:'#purchaseDate' });
});
</script>
