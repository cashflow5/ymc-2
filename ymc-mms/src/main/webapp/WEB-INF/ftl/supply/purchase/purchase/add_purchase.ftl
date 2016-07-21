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

<title>B网络营销系统-采购管理-优购网</title>
</head>
<body>
<div class="main-body" id="main_body">
	<input type="hidden" id="basePath" value="${BasePath}">
	<div class="ytback-tt-1 ytback-tt">
            	<span>您当前的位置：</span>采购管理 &gt; 采购单 &gt;添加采购单
    </div>
	<div class="pro-list">
				<div class="mb-btn-fd-bd">
					<div class="mb-btn-fd relative">
						<span class="btn-extrange absolute ft-sz-14">
							<ul class="onselect">
								<li class="pl-btn-lfbg">
								</li>
								<li class="pl-btn-ctbg">
									<a href="#" class="btn-onselc">添加采购单</a>
								</li>
								<li class="pl-btn-rtbg">
								</li>
							</ul> </span>
					</div>
					<div class="add-newpd ft-sz-12 fl-rt"><a href="#" onclick="closeAdd();" alt="关闭">关闭</a></div>	
				</div>
			</div>			
 <form action="savePurchase.sc" method="post" id="savePurchaseForm" name="savePurchaseForm"> 	
 <input type="hidden" id="supplierId" name="supplierId" /> 
    <div class="divH12"></div>   
    <div class="add-pro-div">    
        <div class="pro-baseinf">
        	<div class="pro-baseinf-list"> 
             	<table>
             	<tr>
             	<td>   
            	<span class="text_details">供应商：<font class="ft-cl-r">*</font></span>
                </td>
                <td>
                <input type="text" id="supplier" name="supplier"  disabled="disabled"/>                                          
                </td> 
                <td>
                <input type="button" value="请选择" onClick="selectSupperList();"/>
             	<span id="supplierTip"></span>             	
            	</td>
            	
            	<td>
            	<span class="text_details">采购类型：<font class="ft-cl-r">*</font></span>
                </td>
                <td>
                <select id="type" name="type" style="width:120px;">
                	<option value="">请选择</option> 
                	<option value="102">自购</option> 
                	<option value="107">比例代销</option> 
                	<option value="106">协议代销</option>
                	<option value="107">配折结算</option> 
                </select> 
                </td>
                <td>
                <span id="typeTip"></span>
            	</td>
            	<td>
            	</tr>
            	
            	<tr>                                                 	           	
                <td>
                <span class="text_details">物理仓库：<font class="ft-cl-r">*</font></span>
                </td>
                <td>
                <select id="warehouse.id" name="warehouse.id"  style="width:120px;" > 
                	<option value="">请选择</option> 
                	<#list warehouses as warehouse>
                	<option value="${warehouse.id}">${warehouse.warehouseName}</option> 
                	</#list>
                </select>
                </td>
                <td>
                <span id="warehouse.idTip"></span>
                </td>
                
                <td>
                <span class="text_details">采购员：<font class="ft-cl-r">*</font></span>
                </td>
                <td>
                <input type="text" id="purchaser" maxlength="20" name="purchaser" />            
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
                class="Wdate" style="width:152px;"   
                onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'planTime\')}'})"" size="18"/>
           		</td>
           		<td>
           		</td>
           		
           		<td>                
                <span class="text_details">计划交货时间：<font class="ft-cl-r">*</font></span>
                </td>
                <td>
                <input type="text" id="planTime" name="planTimeStr" 
                class="Wdate" style="width:152px;"   
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
            	<input type="text" id="receivePeople" maxlength="20" name="receivePeople" />
            	</td>
            	<td>
            	</td>
            	<td>            	
            	<span class="text_details">收货人联系电话：</span> 
            	</td>
            	<td>
            	<input type="text" id="receivePhone" maxlength="15" name="receivePhone" /> 
            	</td>
            	<td>
            	<span id="receivePhoneTip"></span>
            	</td>
            </tr>
            </table>            	  
            <span class="text_details">收货地址：</span>
            <input size=90 type="text" id="receiveAddress" maxlength="100" name="receiveAddress" />
            <div class="blank10"></div>
            	<span class="text_details">备注：</span>
                 <textarea cols="70" rows="3" id="memo" name="memo" ></textarea>                
            <div class="add-newpd ft-sz-12 fl-lt"><a href="javascript:selectProducts()">添加商品</a></div>	
            <div class="blank10"></div> 
            <div class="pro-baseinf-list">
             <input type="hidden" id="productNos" name="productNos" /> 
             <input type="hidden" id="supplierPriceSpIds" name="supplierPriceSpIds" /> 
             <textarea cols="70" rows="3" id="commodityName" disabled="disabled" name="commodityName" >
             </textarea>   
            </div>
		    <div class="div-pl-bt">
		    	<input type="submit" value="保存" class="btn-sh"/>
		    	<input type="button" onclick="closewindow()" value="取消" />     
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
					{name:'receivePhone',allownull:true,regExp:/^[1-9]\d*|0$/,defaultMsg:'请输入收货人电话',focusMsg:'请输入收货人电话',errorMsg:'电话有误',rightMsg:'收货人联系电话输入正确',msgTip:'receivePhoneTip'}
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
		var supplierId = $("#supplierId").val();
		if(supplierId=="") {
			alert("请选择供应商");
			return false;
		}
  		return true;
  	}
  	function selectProducts() {
  		var supplierId = $("#supplierId").val();
  		var type =  $("#type").val();
  		if(supplierId!=""&&type!="") {
  			art.dialog.openwindow('${BasePath}/supply/manage/purchase/toSelectCommodity.sc?supplierId='+supplierId+"&type="+type,800,600,'yes',{id:'open1',title: '选择商品'});
  		}else if(supplierId=="") {
  			alert("请选择供应商");
  			return ;
  		}else if(type=="") {
  			alert("请选择采购类型");
  			return ;
  		}
  	}
  	function selectSupperList() {
  		art.dialog.openwindow('${BasePath}/supply/manage/supplier/toSelectSupplier.sc',800,600,'yes',{id:'open1',title: '选择供应商'});
  	}
</script>
