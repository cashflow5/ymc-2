<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>优购商城--商家后台</title>
<#include "../../../yitianwms/yitianwms-include.ftl">
<script type="text/javascript">

	$(document).ready(function() {
	   var typeval=document.getElementById("typeval").value;
	   if(typeval== 1){
	   		$('input[name=deliveryStorageTypeRadio]').get(1).checked = true;
	   }else{
		    $('input[name=deliveryStorageTypeRadio]').get(0).checked = true;
       }
       //$("#type01").attr("disabled",true);
	   //$("#type02").attr("disabled",true);
   });
   
   function doSave(type) {
   		
   	   var mainId = $('#id').val();	
	   var remark=document.getElementById("remark").value;
	   var regex = /^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]{0,250}$/;
       if (remark.length>250) {
	    alert("备注内容[共"+remark.length+"]个字符只能输入250个字符");
	    return ;
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
				viewReturnConfirmFrom.submit();
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			
			alert(textStatus.toUpperCase() + ' : ' + XMLHttpRequest.responseText);
		}
	});
		
	}

	function toMainMenue(){
		window.location.href="${BasePath}/supply/manage/recall/toQueryRecallConfirm.sc";
	}
		
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
	var toAddDetailUrl="${BasePath}/wms/stocksmanager/purchasereturn/toPurchaseReturnSel.sc?supplierCode="+supplierCode+"&supplierName="+supplierName+"&returnDefectConfirmId="+$('#id').val()+"&queryTypeFlag=2&url=supply/purchase/recallconfirm/recall_purchasereturn_sel";
	openwindow(toAddDetailUrl, 950, 700, '新增明细');
	
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

function returnRefresh(id,outWarehouseId){
	window.location.href="${BasePath}/yitianwms/stocksmanager/allocationApplyTaoBao/toSavedate.sc?warehouseId="+outWarehouseId+"&storageId="+id;
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
			<div class="btn" onclick="toAddDetail();"> <span class="btn_l"> </span> <b class="ico_btn add"> </b> <span class="btn_txt"> 新增明细 </span> <span class="btn_r"> </span> </div>
			<div class="line"> </div>
			<div class="btn" onclick="toMainMenue();"> <span class="btn_l"> </span> <b class="ico_btn back"> </b> <span class="btn_txt"> 返回 </span> <span class="btn_r"> </span> </div>
		</div>
	</div>
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr"> <span> 新增召回确认单</span> </li>
			</ul>
		</div>
		<!--当前位置end-->
		<div class="modify">
		<form id="mainForm" name="mainForm" method="post" >
		    <input type="hidden" name="returnDefectConfirm.defectConfirmCode" id="defectConfirmCode" value="${defectConfirmCodeChek?if_exists}"/>
		    <input type="hidden" name="returnDefectConfirm.supplierCode" id="supplierCode" value="${supplierCodeChek?if_exists}"/>
		    <input type="hidden" name="returnDefectConfirm.supplierName" id="supplierName" value="${supplierNameChek?if_exists}"/>
		    <input type="hidden" name="returnDefectConfirm.id" id="id" value="${returnDefectConfirmId?if_exists}"/>
			<div class="add_detail_box">
			   <p>
				 <span>
				   <label>召回确认单号：</label>
				      ${defectConfirmCodeChek?if_exists}
				 </span>
				 <span>
				   <label>状态：</label>
				   		待确认
				 </span>
				 <span> 
				        <label>出库类型：</label>
			  		    <input type="radio" id="type01" name="deliveryStorageTypeRadio" value="0" />采购出库
						<input type="radio" id="type02" name="deliveryStorageTypeRadio" value="1" />销售出库
						<input type="hidden" id="typeval" value="${deliveryStorageTypeChek?if_exists}"/> 
				</span>
				</p>
				<p>
				   <label>供应商：</label>
				   <span style="width:250px;">${supplierNameChek?if_exists}</span>
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
					    <th>已申请数量</th>
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
</div>
<form name="viewReturnConfirmFrom" id="viewReturnConfirmFrom"	action="${BasePath}/supply/manage/recall/toSaveConfirm.sc" method="POST">
    <input type="hidden" name="mainId" value="${returnDefectConfirmId?if_exists}"/>
</form>

<form name="toAddDetailFrom" id="toAddDetailFrom" action="${BasePath}/wms/stocksmanager/purchasereturn/toPurchaseReturnSel.sc" method="POST">
    <input type="hidden" name="supplierCode" id="supplierCode" value="${supplierCodeChek?if_exists}"/>
	<input type="hidden" name="supplierName" id="supplierName" value="${supplierNameChek?if_exists}"/>
	<input type="hidden" name="url" id="url" value="supply/purchase/returndefect/common_purchasereturn_sel"/>
</form>

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