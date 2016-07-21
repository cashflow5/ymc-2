<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>优购商城--商家后台</title>
<#include "../../../yitiansystem/yitiansystem-include.ftl">
<script type="text/javascript">

   $(document).ready(function() {
	   var typeval=document.getElementById("typeval").value;
	   if(typeval== 1){
	   		$('input[name=deliveryStorageTypeRadio]').get(1).checked = true;
	   }else{
		    $('input[name=deliveryStorageTypeRadio]').get(0).checked = true;
       }
       $("#type01").attr("disabled",true);
	   $("#type02").attr("disabled",true);
   });
	
function toMainMenue(){
	window.location.href="${BasePath}/supply/manage/recall/toQueryRecallConfirm.sc";
}

</script>
</head>
<body>
<div class="container">
	<div class="toolbar">
		<div class="t-content">
			<div class="btn" onclick="toMainMenue();"> <span class="btn_l"> </span> <b class="ico_btn back"> </b> <span class="btn_txt"> 返回 </span> <span class="btn_r"> </span> </div>
		</div>
	</div>
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr"><span>查看召回确认单</span> </li>
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
						<input type="hidden" id="typeval" value="${returnDefectConfirm.deliveryStorageType?if_exists}"/> 
				 </span>
				 </p>
				 <p>
					 <span>
					   <label>供应商：</label>
						<span style="width:250px;"> ${returnDefectConfirm.supplierName?if_exists}</span>
					 </span>
				</p>
				<p>
					<label style="float:left;clear:left;">备注：</label>
					<textarea name="returnDefectConfirm.remark" id="remark" rows="3" disabled="disabled" cols="100">${returnDefectConfirm.remark?if_exists}</textarea>
				</p>
					</div>
					<div id="resMsg" style="float:left;width:90%; text-align:right; height:15px ;" >总记录数:<span class="star" id="crow">${countRow?if_exists}</span></div>
			<div id="resMsg01" style="float:left; text-align:left;width:90%;height:15px ;" ><span class="star" id="cfjl"></span></div>
			<div id="msg" style="float:left; text-align:left; width:90%;"></div>
			<table id="subTableUp" class="list_table" border="0" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
		                <th>出库单号</th>
						<th>货品编码</th>
						<th>商品名称</th>
						<th>规格</th>
						<th>单位</th>
						<th>出库数量</th>
					    <th>本次确认数量</th>
					</tr>
				</thead>
		<tbody id="childBody">
          <#if returnDefectConfirm?? && (returnDefectConfirm.returnDefectConfirmDetails)?? >
          <#list returnDefectConfirm.returnDefectConfirmDetails as item >
          <tr>
            <td>
	            	<input type="hidden" name="returnDefectConfirmDetails[${item_index}].id" value="${item.id?if_exists}" />
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
</body>
</html>
<#include "../../../yitianwms/yitianwms-include-bottom.ftl">
<script type="text/javascript" src="${BasePath}/js/wms/common/commonCommodity_sel.js"></script>
<!--客户端验证-->
<script type="text/javascript" src="${BasePath}/js/wms/stocksmanager/overrideValidate.js"></script>
<script type="text/javascript" src="${BasePath}/js/wms/purchase/returndefuctApply.js"></script>