<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>优购商城--商家后台</title>
<#include "../../../yitiansystem/yitiansystem-include.ftl">
<script type="text/javascript">
	 $(document).ready(function(){
	 	$('#forUpdateForm').hide();
	 	dg.curWin.closewindow();
	 	
	   var typeval=document.getElementById("deliveryStorageType").value;
	   if(typeval== 1){
	   		$('input[name=deliveryStorageTypeRadio]').get(1).checked = true;
	   }else{
		    $('input[name=deliveryStorageTypeRadio]').get(0).checked = true;
       }
       //$("#type01").attr("disabled",true);
	   //$("#type02").attr("disabled",true);
	 });
	function toMainMenue(){
		window.location.href="${BasePath}/supply/manage/recall/toQueryRecallConfirm.sc";
	}
	
//全选
$(document).ready(function(){					   
	$("#checkallUp").click( 
	function(){ 
	
	upCheckboxName();
	if(this.checked ){ 
	$("input[name='returnDefectConfirmDetails']").each(function(){
	   if(!this.disabled)
	   this.checked=true;
	}); 
	}else{ 
	$("input[name='returnDefectConfirmDetails']").each(function(){this.checked=false;}); 
	} 
	});
});


   function doSave(type) {
   		
   	   var mainId = $('#id').val();	
	   var remark=document.getElementById("remark").value;
	   var regex = /^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]{0,250}$/;
       if (remark.length>250) {
	    alert("备注内容[共"+remark.length+"]个字符只能输入250个字符");
	    return ;
       }
	    
	   if(type==2){
			$("#status").val("4");
			}
		var flag=formValidate();
		if(flag!=true){
			return ;
		}
		
	    var table = document.getElementById("subTable");
		if(table.rows.length==1){
			alert("请添加明细！");
			return ;
		}
		initIndex("subTable");
		if(!validatorProduct()){
			return ;
		}
	
	$.ajax({ type : 'post', url: '${BasePath}/supply/manage/recall/u_recallConfirm.sc', dataType: 'json', data: $('#mainForm').serialize(),
		
		beforeSend: function(XMLHttpRequest) {
			
			openDiv({
				content:'<div style="color:#ff0000">处理中...</div>',
				title: '提示',
				lock: true,
				width: 200,
				height: 60,
				closable: false,
				left: '50%',
				top: '40px'
			});
		},
		success: function(resultMsg, textStatus){
		   closewindow();
		   var result=resultMsg.success;
		   if(result){
				alert("成功");
				viewReturnConfirmFrom.submit();
			}else
			{
				if(resultMsg.msg!=""){
				   alert(resultMsg.msg);
				}
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			
			alert(textStatus.toUpperCase() + ' : ' + XMLHttpRequest.responseText);
		}
	});
		
	}


function upCheckboxName(){
	// 这里用getElementsByTagName把所有的input对象取出来,这是你这个问题的关键性的地方,用ByTagName而不是ByName。
//var inputList = document.getElementsByTagName("input");
  var list = $("input[type=checkbox]");	
// 循坏这个集合，包括了所有的input。
  for(i=0;i<list.length;i++){ 
// 这里弹出的就是'kk'，当然也可以根据需要输出别的。比如：list[i].id; list[i].value等等。
// 这是修改这个值
  if(list[i].id!="checkallUp"&&list[i].name!="returnDefectConfirmDetails"){
  list[i].name = 'returnDefectConfirmDetails';
  }
// 这时弹出的就是'mm'了
//alert(list[i].name);
 }
}

//删除确认单
function deleteConfrimOrder(){
	if(!confirm("您确定要删除此召回确认单吗？")){	
			return false;
  	}
	viewReturnConfirmFrom.action = "${BasePath}/supply/manage/recall/deleteConfrimOrder.sc";
	viewReturnConfirmFrom.submit();
}	
	
//删除明细
function deleteAllocationDetails(){
	//initIndex("subTableUp"); //重建索引
  if(!confirm("您确定要删除！")){	
			return false;
  }
  upCheckboxName();
  var cks = getCheckBoxs("returnDefectConfirmDetails");
// var listLeg=document.getElementById("listLegth").value;
    var listLeg=$("input[type=checkbox]").length-1;	
 var Id="";
	if (cks.length == 0) {
		alert("请选择记录！");
		return;
	}
	if(listLeg==cks.length){
		alert("至少需要保留一条明细！");
		return;
	}
	for(var i=0;i<cks.length;i++){   
            var ck = cks[i];   
            if(ck.checked){ 
            	 Id=Id+","+ck.value.substring(0,32);
            }   
     }
		$.ajax({
		type : "POST",
		url : "deleteDetails.sc",
		data : {
			"Ids" : Id
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg) {
			 	alert("删除成功！");
			 	$("input[name='returnDefectConfirmDetails']").each(function(){this.checked=false;}); 
				//window.location.reload(); 
				var mainID = $('#returnDefectConfirmId').val();
				window.location.href = "${BasePath}/supply/manage/recall/toSaveConfirm.sc?mainId="+mainID+"";
			} else {
				alert("提示：["+resultMsg+"]");
			}
		}
	});
 }
 
 
 //新增明细
function toAddDetail() {
   var supplierCode=$('#supplierCode').val();
	 if(supplierCode==null || supplierCode==""){
		  alert("未获取到供应商编码！");
		  return;
	 }
	 var supplierName=$('#supplierName').val();
	  if(supplierName==null || supplierName==""){
		  alert("未获取到供应商名称！");
		  return;
	 }
	var toAddDetailUrl="${BasePath}/wms/stocksmanager/purchasereturn/toPurchaseReturnSel.sc?supplierCode="+supplierCode+"&supplierName="+supplierName+"&returnDefectConfirmId="+$('#returnDefectConfirmId').val()+"&queryTypeFlag=2&url=supply/purchase/recallconfirm/recall_purchasereturn_sel";
	openwindow(toAddDetailUrl, 950, 700, '新增明细');
	
}	
//判断数组是否重复
function checkcommodityCode(){
	var strtemp="";
	for(i=0;i<document.getElementById('subTableUp').rows.length-1;i++){
	  strtemp = document.getElementById("returnDefectConfirmDetails["+i+"].outDefectCode")+"_"+document.getElementById("returnDefectConfirmDetails["+i+"].productNo");
	  strtemp = strtemp+","; 
	}
	   
	   
	var strArray = strtemp.split(",");
	var nary=strArray.sort();
	var codeStr="";
	 for(var i=0;i<strArray.length;i++){
	 if (nary[i]==nary[i+1]){
	     // alert("数组重复内容："+nary[i]);
		 codeStr=codeStr+nary[i]+",";
	   }
	 }
	  if(codeStr!=""){
	    $("#cfjl").text("出库单和货品编码重复：["+codeStr+"]");
	  }
	  if(codeStr!=""){
		   return false;
	  }else{
		 return true;
	  }
	return false;
}

function allConfirmDetail(){	
	
	$.ajax({ type : 'post', url: '${BasePath}/supply/manage/recall/updateAllPurchaseDetailConfirm.sc', dataType: 'json', data: $('#allConfrimForm').serialize(),
		
		beforeSend: function(XMLHttpRequest) {
			
			openDiv({
				content:'<div style="color:#ff0000">处理中...</div>',
				title: '提示',
				lock: true,
				width: 200,
				height: 60,
				closable: false,
				left: '50%',
				top: '40px'
			});
		},
		success: function(resultMsg, textStatus){
		   closewindow();
		   var result=resultMsg.success;
		   if(result){
				alert("成功");
				viewReturnConfirmFrom.submit();
			}else
			{
				if(resultMsg.msg!=""){
				   alert(resultMsg.msg);
				}
				viewReturnConfirmFrom.submit();
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
		
			alert(textStatus.toUpperCase() + ' : ' + XMLHttpRequest.responseText);
		}
	});

}

function returnRefresh(id,warehouseId){
	window.location.href="${BasePath}/yitianwms/stocksmanager/allocationApplyTaoBao/toSavedate.sc?warehouseId="+warehouseId+"&storageId="+id;
	}
	
function getRadioVal(nameOfRadio) {
	var returnValue = "";
	var theRadioInputs = document.getElementsByName(nameOfRadio);
	for ( var i = 0; i < theRadioInputs.length; i++) {
		if (theRadioInputs[i].checked) {
			returnValue = theRadioInputs[i].value;
			break;
		}
	}
	return returnValue;
}
</script>
</head>
<body>
<div class="container">
	<div class="toolbar">
		<div class="t-content">
		<#if returnDefectConfirm.status??>
	        <#if returnDefectConfirm.status != 1 >
				<div class="btn" onclick="toAddDetail();"> <span class="btn_l"> </span> <b class="ico_btn add"> </b> <span class="btn_txt"> 新增明细 </span> <span class="btn_r"> </span> </div>
				 <div class="line"> </div>
				 <div class="btn" onclick="deleteAllocationDetails();"><span class="btn_l"></span><b class="ico_btn delete"></b><span class="btn_txt">删除明细</span><span class="btn_r"></span> </div>
				<div class="line"> </div>
				<div class="btn" onclick="doSaveOrConfrim(0);"> <span class="btn_l"> </span> <b class="ico_btn add"> </b> <span class="btn_txt"> 保存</span> <span class="btn_r"> </span> </div>
				<div class="line"> </div>
				<div class="btn" onclick="doSaveOrConfrim(1);"> <span class="btn_l"> </span> <b class="ico_btn save"> </b> <span class="btn_txt"> 确认 </span> <span class="btn_r"> </span> </div>
				<div class="line"> </div>
				<div class="btn" onclick="deleteConfrimOrder();"> <span class="btn_l"> </span> <b class="ico_btn save"> </b> <span class="btn_txt"> 删除 </span> <span class="btn_r"> </span> </div>
				<div class="line"> </div>
			</#if>
	     </#if>	
			<div class="btn" onclick="toMainMenue();"> <span class="btn_l"> </span> <b class="ico_btn back"> </b> <span class="btn_txt"> 返回 </span> <span class="btn_r"> </span> </div>
		</div>
	</div>
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr"><span>召回确认单提交</span> </li>
			</ul>
		</div>
		<!--当前位置end-->
		<div class="modify">
		<form id="mainFormUp" name="mainFormUp" method="post" >
		<input type="hidden" name="returnDefectConfirm.status" id="status" value="${returnDefectConfirm.status?if_exists}"/>
		<input type="hidden" id="returnDefectConfirmId" name="returnDefectConfirm.id" value="${returnDefectConfirm.id?if_exists}" />
		<input type="hidden" name="returnDefectConfirm.defectConfirmCode" value="${returnDefectConfirm.defectConfirmCode?if_exists}" />
		<input type="hidden" id = "supplierCode" name="returnDefectConfirm.supplierCode" value="${returnDefectConfirm.supplierCode?if_exists}" />
		<input type="hidden" id = "supplierName" name="returnDefectConfirm.supplierName" value="${returnDefectConfirm.supplierName?if_exists}" />
		<input type="hidden" id="deliveryStorageType" name="returnDefectConfirm.deliveryStorageType" value="${returnDefectConfirm.deliveryStorageType?if_exists}"/> 
			<div class="add_detail_box">
				 <p>
				 <span>
				   <label>召回确认单号：</label>
				    ${returnDefectConfirm.defectConfirmCode?if_exists}
				 </span>
				 <span>
				   <label>状态：</label>
				   		<#if returnDefectConfirm.status??>
	        				<#if returnDefectConfirm.status == 1 >
				   				已确认
				   			<#else>
			          		 	待确认
			          		</#if>
			          </#if>
				 </span>
				 <span> 
				        <label>出库类型：</label>
			  		    <input type="radio" id="type01" name="deliveryStorageTypeRadio" value="0" />采购出库
						<input type="radio" id="type02" name="deliveryStorageTypeRadio" value="1" />销售出库
						<input type="hidden" id="typeval" value="${deliveryStorageTypeChek?if_exists}"/> 
				 </span>
				 </p>
				 <p>
					 <span>
					   <label>供应商：</label>
						<span style="width:250px;"> ${returnDefectConfirm.supplierName?if_exists} </span>
					 </span>
				</p>
				<p>
					<label style="float:left;clear:left;">备注：</label>
					<textarea name="returnDefectConfirm.remark" id="remark" rows="3" cols="100">${returnDefectConfirm.remark?if_exists}</textarea>
				</p>
					</div>
					<div id="resMsg" style="float:left;width:90%; text-align:right; height:15px ;" >总记录数:<span class="star" id="crow">${countRow?if_exists}</span></div>
			<div id="resMsg01" style="float:left; text-align:left;width:90%;height:15px ;" ><span class="star" id="cfjl"></span></div>
			<div id="msg" style="float:left; text-align:left; width:90%;"></div>
			<table id="subTableUp" class="list_table" border="0" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						 <th width="30"><input type="checkbox" value="" id="checkallUp" /></th>
		                <th>出库单号</th>
						<th>货品编码</th>
						<th>商品名称</th>
						<th>规格</th>
						<th>单位</th>
						<th>出库数量</th>
						<th>已确认数量</th>
						<th>已申请数量</th>
					    <th>本次确认数量</th>
					</tr>
				</thead>
		<tbody id="childBody">
          <#if returnDefectConfirm?? && (returnDefectConfirm.returnDefectConfirmDetails)?? >
          <#list returnDefectConfirm.returnDefectConfirmDetails as item >
          <tr>
		  	<td width="30">
					<input type="checkbox" name="returnDefectConfirmDetails" id="returnDefectConfirmDetails[${item_index}].id" value="${item.id?if_exists}"/>
			</td>
            <td>
	            	<input type="hidden" name="returnDefectConfirmDetails[${item_index}].id" value="${item.id?if_exists}" />
	            	<input type="hidden" name="returnDefectConfirmDetails[${item_index}].purchaseReturnDetailId" value="${item.purchaseReturnDetailId?if_exists}" />
	            	<input type="hidden" name="returnDefectConfirmDetails[${item_index}].warehouseId" value="${item.warehouseId?if_exists}" />
					<input type="hidden" name="listLegth" id="listLegth" value="${returnDefectConfirm.returnDefectConfirmDetails?size}" />
	              	<input type="hidden" name="returnDefectConfirmDetails[${item_index}].outDefectCode" value="${item.outDefectCode?if_exists}" />
	              	${item.outDefectCode?if_exists} 
             </td>
			 <td>
			 		<input type="hidden" name="returnDefectConfirmDetails[${item_index}].productNo" value="${item.productNo?if_exists}" />
             	    ${item.productNo?if_exists} 
             </td>
			 <td>
			 		<input type="hidden" name="returnDefectConfirmDetails[${item_index}].goodsName" value="${item.goodsName?if_exists}" />
              		${item.goodsName?if_exists} 
            </td>
            <td>
            		<input type="hidden" name="returnDefectConfirmDetails[${item_index}].specification" value="${item.specification?if_exists}" />
              		${item.specification?if_exists} 
            </td>
            <td>
            		<input type="hidden" name="returnDefectConfirmDetails[${item_index}].units" value="${item.units?if_exists}"/>
              		${item.units?if_exists} 
            </td>
            <td>
			       <#if outQtyMap??>
					  <#if outQtyMap["${item.id}"]??>
					     	${outQtyMap["${item.id}"]!""} 
			 				<input type="hidden" id="returnDefectConfirmDetails[index].outQty" name="returnDefectConfirmDetails[index}].outQty" value="${outQtyMap["${item.id}"]!""}" style="width:50px;"  maxlength="7"  />
						<#else>
						   <font color="#FF0000">0  </font>
						   <input type="hidden" id="returnDefectConfirmDetails[index].outQty"   name="returnDefectConfirmDetails[index}].outQty" value="0" style="width:50px;"  maxlength="7"  />
					  </#if>
				     </#if>
			</td>
           	<td>
			       <#if realConfrimQtyMap??>
					  <#if realConfrimQtyMap["${item.id}"]??>
					     	${realConfrimQtyMap["${item.id}"]!""} 
			 				<input type="hidden" id="returnDefectConfirmDetails[index].realConfrimQty" name="returnDefectConfirmDetails[index}].realConfrimQty" value="${realConfrimQtyMap["${item.id}"]!""}" style="width:50px;"  maxlength="7"  />
						<#else>
						   <font color="#FF0000">0  </font>
						   <input type="hidden" id="returnDefectConfirmDetails[index].realConfrimQty"   name="returnDefectConfirmDetails[index}].realConfrimQty" value="0" style="width:50px;"  maxlength="7"  />
					  </#if>
				     </#if>
			</td>
			<td>
			       <#if realApplyQtyMap??>
					  <#if realApplyQtyMap["${item.id}"]??>
					     	${realApplyQtyMap["${item.id}"]!""} 
			 				<input type="hidden" id="returnDefectConfirmDetails[index].realApplyQty" name="returnDefectConfirmDetails[index}].realApplyQty" value="${realApplyQtyMap["${item.id}"]!""}" style="width:50px;"  maxlength="7"  />
						<#else>
						   <font color="#FF0000">0  </font>
						   <input type="hidden" id="returnDefectConfirmDetails[index].realApplyQty"   name="returnDefectConfirmDetails[index}].realApplyQty" value="0" style="width:50px;"  maxlength="7"  />
					  </#if>
				     </#if>
			</td>
            <td>
            		<input type="text" name="returnDefectConfirmDetails[${item_index}].confirmQuantity" value="${item.confirmQuantity?if_exists}" style="width:50px;" validatorFile="quantity"   maxlength="7"  />
            </td>
          </tr>
          </#list>
          <#else>
          <tr>
            <td  colspan="5">没有相关记录！</td>
          <tr> </#if>
            </tbody>
			</table>
		</div>
		</form>
	</div>
</div>
<form name="viewReturnConfirmFrom" id="viewReturnConfirmFrom"	action="${BasePath}/supply/manage/recall/toSaveConfirm.sc" method="POST">
    <input type="hidden" name="mainId" value="${returnDefectConfirm.id?if_exists}"/>
</form>

<div id="forUpdateForm">
	<form id="mainForm" name="mainForm" method="post" >
		<input type="hidden" name="returnDefectConfirm.status" id="status" value="${returnDefectConfirm.status?if_exists}"/>
		<input type="hidden" id="returnDefectConfirmId" name="returnDefectConfirm.id" value="${returnDefectConfirm.id?if_exists}" />
		<input type="hidden" name="returnDefectConfirm.defectConfirmCode" value="${returnDefectConfirm.defectConfirmCode?if_exists}" />
		<input type="hidden" id = "supplierCode" name="returnDefectConfirm.supplierCode" value="${returnDefectConfirm.supplierCode?if_exists}" />
		<input type="hidden" id = "supplierName" name="returnDefectConfirm.supplierName" value="${returnDefectConfirm.supplierName?if_exists}" />
			
				<div class="add_detail_box">
				   <p>
					 <span>
					   <label>召回确认单号：</label>
					   <label>${defectConfirmCodeChek?if_exists}</label>
					 </span>
					 <span>
					   <label>状态：</label>
					   <label>待确认</label>
					 </span>
					 <span> 
				        <label>出库类型：</label>
			  		    <input type="radio" id="type01" name="deliveryStorageTypeRadio" value="0" />采购出库
						<input type="radio" id="type02" name="deliveryStorageTypeRadio" value="1" />销售出库
						<input type="hidden" id="typeval" value="${deliveryStorageTypeChek?if_exists}"/> 
				 	</span>
					 <span>
					   <label>供应商：</label>
						<label>${supplierNameChek?if_exists}</label>
					 </span>
					</p>
					<p>
						<label style="float:left;clear:left;">备注：</label>
						<textarea name="returnDefectConfirm.remark" id="remark" rows="3" cols="100"></textarea>
					</p>
				</div>
		        <div id="resMsg" style="float:left;width:90%; text-align:right; height:15px ;" >总记录数:<span class="star" id="crow">${countRow?if_exists}</span></div>
				<div id="resMsg01" style="float:left; text-align:left;width:90%;height:15px ;" ><span class="star" id="cfjl"></span></div>
				<div id="msg" style="float:left; text-align:left; width:90%;"></div>
				<table id="subTable" class="list_table" border="0" cellpadding="0" cellspacing="0">
					<thead>
						<tr>
						 <th><input type="checkbox" value="" id="checkall" /></th>
			              	<th>出库单号</th>
							<th>货品编码</th>
							<th>商品名称</th>
							<th>规格</th>
							<th>单位</th>
							<th>出库数量</th>
						    <th>已确认数量</th>
						    <th>本次确认数量</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
				<p>
					<input type="button" value="保存" class="btn-save" onclick="doSave();"/>
					<input type="button" value="返回" class="btn-back" onclick="toMainMenue();"/>
				</p>
			</div>
		</form>
	
</div>
<form name="allConfrimForm" id="allConfrimForm" method="POST">
	<input type="hidden" name="supplierCodeQ" id="supplierCodeQ" value="" />
	<input type="hidden" name="supplierNameQ" id="supplierNameQ" value="" />
	<input type="hidden" name="commodityCodeQ" id="commodityCodeQ" value="" />
	<input type="hidden" name="purchaseReturnCodeQ" id="purchaseReturnCodeQ" value="" />
	<input type="hidden" name="beginApproverDate" id="beginApproverDate" value="" />
	<input type="hidden" name="endApproverDate" id="endApproverDate" value="" />
	<input type="hidden" name="returnDefectConfirmIdQ" id="returnDefectConfirmIdQ" value="" />
	<input type="hidden" name="queryTypeFlag" id="queryTypeFlag" value="" />
</form>
</body>
</html>
<#include "../../../yitianwms/yitianwms-include-bottom.ftl">
<script type="text/javascript" src="${BasePath}/js/wms/common/commonCommodity_sel.js"></script>
<!--客户端验证-->
<script type="text/javascript" src="${BasePath}/js/wms/stocksmanager/overrideValidate.js"></script>
<script type="text/javascript" src="${BasePath}/js/wms/purchase/returndefuctApply.js"></script>
<script type="text/javascript" >
function doSaveOrConfrim(type) {
	 
	var upTable = document.getElementById("subTableUp");
	if(upTable.rows.length==1){
			alert("请添加明细！");
			return ;
	}
	
	 var remark=document.getElementById("remark").value;
	 var regex = /^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]{0,250}$/;
     if (remark.length>250) {
	    alert("备注内容[共"+remark.length+"]个字符只能输入250个字符");
	    return ;
      }
      
     var newVal = $('input[name=deliveryStorageTypeRadio]:checked').val();
     var shipVal = $('#typeval').val();
     if(newVal != shipVal){
     	if(!confirm("出库类型和供应商默认退货处理类型不一致，请确认是否继续？")){	
		   return false;
	 	}
     } 
     $('#deliveryStorageType').val(newVal);
	
	 if(type == 1){
		 if(!confirm("您确定输入的确认数量已核实无误，并执行确认操作吗？")){	
				return false;
	  	 }
	 }
	
   	 //设置状态	
   	 $("#status").val(type);	
   	
     //检查输入是正确
     if(!checkInput()){
		return false;
      }	
		var flag=formValidate();
		if(flag!=true){
			return ;
		}
		 var table = document.getElementById("subTableUp");
		if(table.rows.length==1){
			alert("请添加明细！");
			return ;
		}
		initIndex("subTableUp");
		
		if(!validatorProduct()){
		    alert("输入的数量格式不正确，必须是非零的正整数！！！");
			return ;
		}
		
		//判断是否重复	
		if(type==1){
			if(!checkcommodityCode()){
				return false;
			}
		 }
		 
		//去除未选择的行
		var i = 0;
		var bool=true;
	    //重组索引
	    for(i=0;i<document.getElementById('subTableUp').rows.length-1;i++){
            var j=i+1;
			  var outQty =document.getElementById("returnDefectConfirmDetails["+i+"].outQty").value;
			  var realConfrimQty=document.getElementById("returnDefectConfirmDetails["+i+"].realConfrimQty").value;
			  var realApplyQty=document.getElementById("returnDefectConfirmDetails["+i+"].realApplyQty").value;
			  var confirmQuantity=document.getElementById("returnDefectConfirmDetails["+i+"].confirmQuantity").value;
			  if(parseInt(confirmQuantity) > (parseInt(outQty)-parseInt(realConfrimQty)-parseInt(realApplyQty))){
				  	   alert("第["+ (i+1) +"]行本次确认数量不能大于可确认数量！");
			       	   bool=false;
			       	   return false;
			   }
			   if(parseInt(confirmQuantity) < 1 ){
				  	   alert("第["+ (i+1) +"]行本次确认数量应该大于0 ！！！");
			       	   bool=false;
			       	   return false;
			   }
		}
	   	if(!bool){
		   return false;
		}
		
		$.ajax({ type : 'post', url: '${BasePath}/supply/manage/recall/toUpdateReturnDefeDetail.sc', dataType: 'json', data: $('#mainFormUp').serialize(),
		
		beforeSend: function(XMLHttpRequest) {
			
		/*	openDiv({
				content:'<div style="color:#ff0000">处理中...</div>',
				title: '提示',
				lock: true,
				width: 200,
				height: 60,
				closable: false,
				left: '50%',
				top: '40px'
			});*/
		},
		success : function(resultMsg,textStatus) {	
			var result=resultMsg.success;
			if(result){
				alert("成功");
				if(type==1)
				{
				  window.location.href="${BasePath}/supply/manage/recall/toQueryRecallConfirm.sc";
				}else{
				  window.location.href="${BasePath}/supply/manage/recall/toSaveConfirm.sc?mainId="+$("#returnDefectConfirmId").val()+"";
				  //var updateConfrimUrl = "${BasePath}/supply/manage/recall/toSaveConfirm.sc?mainId="+$("#returnDefectConfirmId").val()+"";	
				  //openwindow(updateConfrimUrl, 950, 700, '新增明细');
				}
			}else
			{
				if(resultMsg.msg!=""){
				   alert(resultMsg.msg);
				}else{
				   $("#msg").html("<font color=red>"+错误+"</font>");
				}
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			
			alert(textStatus.toUpperCase() + ' : ' + XMLHttpRequest.responseText);
		}
	});
	}
	</script>