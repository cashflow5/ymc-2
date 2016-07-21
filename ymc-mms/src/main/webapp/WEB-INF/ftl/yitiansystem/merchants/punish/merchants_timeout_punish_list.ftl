<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#include "../merchants-include.ftl">
<script type="text/javascript" src="${BasePath}/js/yitiansystem/merchants/jquery.suggest.js"></script>  
<script>
		var basePath = "${BasePath}";
	</script>
<title>优购商城--商家后台</title>
</head>
<body>
<div class="container">
	<!--工具栏start--> 
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li  class="curr">
				  <span><a href="${BasePath}/yitiansystem/merchants/businessorder/to_punishList.sc" class="btn-onselc">超时效订单</a></span>
				</li>
				<li>
				  <span><a href="${BasePath}/yitiansystem/merchants/businessorder/to_validPunishList.sc" class="btn-onselc">已审核超时效</a></span>
				</li>
			</ul>
		</div>
   <div class="modify"> 
     <form action="${BasePath}/yitiansystem/merchants/businessorder/to_punishList.sc" name="queryForm" id="queryForm" method="post">
  			  	<div class="wms-top">
                     <label>订单号：</label>
                     <input type="text" name="orderNo" id="orderNo" value="${(punishOrderVo.orderNo)!'' }"/>&nbsp;&nbsp;&nbsp;
                     <label>商家编号：</label>
                   	 <input type="text" name="merchantCode" id="merchantCode" value="${(punishOrderVo.merchantCode)!'' }"/>
                   	 <label>发货状态：</label>
                   	<select name="shipmentStatus" id="shipmentStatus">
                        <option value="">请选择</option>
                        <option value="1" <#if (punishOrderVo.shipmentStatus)?? && punishOrderVo.shipmentStatus == "1">selected</#if> >已发货</option>
                        <option value="0" <#if (punishOrderVo.shipmentStatus)?? && punishOrderVo.shipmentStatus == "0">selected</#if> >未发货</option>
                     </select>
                   	 <label>货品负责人：</label>
                     <input type="text" name="supplierYgContacts" id="supplierYgContacts" value="<#if punishOrderVo??&&punishOrderVo.supplierYgContacts??>${punishOrderVo.supplierYgContacts!''}</#if>"/>&nbsp;&nbsp;&nbsp;
                </div>
  			  	<div class="wms-top">
  			  		<label>下单时间：</label>
                   	 <input type="text" name="orderTimeStart" id="orderTimeStart" value="<#if (punishOrderVo.orderTimeStart) ??>${punishOrderVo.orderTimeStart?string('yyyy-MM-dd HH:mm:ss')}</#if>"/>
                   	    至
                   	 <input type="text" name="orderTimeEnd" id="orderTimeEnd" value="<#if (punishOrderVo.orderTimeEnd) ??>${punishOrderVo.orderTimeEnd?datetime?string('yyyy-MM-dd HH:mm:ss')}</#if>"/>
                   	 <label>商品品类：</label>
                   	 <select name="category" id="category">
                   	 	<option value="">一级分类</option>
                   	 	<#list categorys as item>
                   	 		<option value="${ item.structName}" <#if (punishOrderVo.category)?? && (item.structName==punishOrderVo.category)>selected</#if>>${item.structCatName }</option>
                   	 	</#list>
                   	 </select>
              	</div>
              	<div class="wms-top">
              		 <label>商家名称：</label>
                    <input type="text" name="merchantName_search" placeholder="模糊搜索" id="merchantName_search" />&nbsp;&nbsp;&nbsp;
                     <select multiple="multiple"  style="width: 128px;height:60px" name="merchantName" id="merchantName" class="selecttxt">
                     	<#list merchants as merchant>
	                     	<option value="${merchant.merchantCode }" <#if (punishOrderVo.merchantName)?? && (punishOrderVo.merchantName )?contains(merchant.merchantCode)>selected</#if>>${merchant.supplierName }</option>
                     	</#list>
                     </select>
                     <label>商品品牌：</label>
                     <input type="text" name="brandNo_search" placeholder="模糊搜索" id="brandNo_search" />&nbsp;&nbsp;&nbsp;
                     <select multiple="multiple"  style="width: 128px;height:60px" name="brandNo" id="brandNo" class="selecttxt">
                     	<#list brands as brand>
	                     	<option value="${brand.brandNo }" <#if (punishOrderVo.brandNo)?? && (punishOrderVo.brandNo )?contains(brand.brandNo)>selected</#if>>${brand.brandName }</option>
                     	</#list>
                     </select>
                     &nbsp;&nbsp;&nbsp;&nbsp;
                   	 <input type="button" value="搜索" onclick="queryPunish();" class="yt-seach-btn" />&nbsp;&nbsp;&nbsp;
              	</div>
              	<div class="wms-top">
              		 <input type="button" value="手动添加超时效订单" onclick="addMerchantPunish();"  />&nbsp;&nbsp;&nbsp;
              		 <input type="button" value="批量审核" onclick="validAllPunishOrder();" class="btn-add-normal-4ft" />&nbsp;&nbsp;
                	 <a href="javascript:void(0);" style="color:#0066ff;" onclick="selectAllPunishOrder();">全选</a>&nbsp;&nbsp;
                	 <a href="javascript:void(0);" style="color:#0066ff;" onclick="cancelAllPunishOrder();">反选</a>&nbsp;&nbsp;
                	 <input type="button" value="导出" title="根据查询条件导出" onclick="exportPunishOrder();" class="btn-add-normal-4ft fr"  />
              	</div>
  			
              	</form>
                <table cellpadding="0" cellspacing="0" class="list_table">
                		<thead>
                        <tr>
                        	<th style="padding-left:0px;width:50px;"><input onClick="allChk(this,'ids')" id="chkb" type="checkbox" /></th>
	                        <th style="width:80px;">优购订单号</th>
	                        <th style="width:80px;">下单日期</th>
	                        <th style="width:150px;">商家名称</th>
	                        <th style="width:100px;">商家编号</th>
	                        <th style="width:80px;">发货状态</th>
	                        <th style="width:80px;">订单状态</th>
	                        <th style="width:80px;">发货时间</th>
	                        <th style="width:80px;">超出时长</th >
	                        <th style="width:80px;">订单金额</th>
	                        <th style="width:100px;">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        	<#if pageFinder?? && (pageFinder.data)?? >
	                   		<#list pageFinder.data as item >
	                        <tr id="td_${(item.id)!''}" >
	                        	<td><input type="checkbox" name="ids"  value='${(item.id)!''}' ></td> 
		                        <td><a target="_blank" href="${omsHost!''}/yitiansystem/ordergmt/orderdetail/toBaseDetail.sc?orderNo=${(item.order_no)!'' }" > ${(item.order_no)!'' }</a> </td>
		                        <td>${(item.order_time)!'' }</td>
		                        <td>${(item.supplier)!'' }</td>
		                        <td>${(item.merchant_code)!'' }</td>
		                        <td>
									<#if (item.shipment_status)??>
		                        		<#if item.shipment_status == "1">
		                        			已发货
		                        		<#else>
		                        			未发货
		                        		</#if>
		                        	</#if>
								</td>
		                        <td>
		                        	${(orderStatusMap[item.order_no])!'' }
		                        </td>
		                        <td>
		                        	<#if (item.shipment_time)??>
		                        		${(item.shipment_time)!'' }
		                        	<#else>
		                        		---
		                        	</#if>
		                        </td>
		                        <td>
		                        	<#if (item.punish_type)?? && (item.punish_type) == "1">
			                        	<#if (overHour)??>
			                        		${(overHour[item.order_no])!''}
			                        	</#if>
			                        <#else>
			                        	--
			                        </#if>
		                        </td>
		                        <td>${(item.order_price)!'' }</td>
	                        	<td>
		                        	<a href="javascript:;" onclick="validPunishOrder('${item.id }',2);">审核</a>
	                        	</td>
	                        </tr>
	                        </#list>
	                        </#if>
                      </tbody>
                </table>
              </div>
               <div class="bottom clearfix">
			  	<#if pageFinder ??><#import "../../../common.ftl" as page>
			  		<@page.queryForm formId="queryForm"/></#if>
			  </div>
              <div class="blank20"></div>
          </div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	$('#orderTimeStart').calendar({maxDate:'#orderTimeEnd',format:'yyyy-MM-dd HH:mm:ss'});
	$('#orderTimeEnd').calendar({minDate:'#orderTimeStart',format:'yyyy-MM-dd HH:mm:ss'});
	$('#merchantName_search').suggest({selectId:"merchantName"});
	$('#brandNo_search').suggest({selectId:"brandNo"});
});
//根据条件查询
function queryPunish(){
   document.queryForm.method="post";
   document.queryForm.submit();
}

//审核处罚订单
function validPunishOrder(ids,validStatus){
	if($.trim(ids)==""){
		parent.ygdg.dialog.alert("请先选择超时效订单！");
	}else{
		parent.ygdg.dialog.confirm('确认审核通过吗？', function(){
				var idsParam ="";
				if(ids.constructor  === Array ){
					$.each(ids, function(i, field){
						idsParam = idsParam + "&ids="+field.value;
					});
				}else{
					idsParam = "&ids=" + ids;
				}
				$.ajax({
					   type: "POST",
					   url: "${BasePath}/yitiansystem/merchants/businessorder/valid_punish_order.sc",
					   data:"validStatus="+validStatus+idsParam,
					   cache: false,
					   success: function(result){
						  if(result == "true"){
							  alert("操作成功");
							  //refreshpage("${BasePath}/yitiansystem/merchants/businessorder/to_punishList.sc");
							  $("#queryForm").submit();
						  }else{
							  alert("操作失败");
						  }
					   }
					});
		});
	}
}

function selectAllPunishOrder(){
	$(":checkbox[name='ids']").prop("checked",true);
}

function cancelAllPunishOrder(){
	var all = $(":checkbox[name='ids']");
	$.each(all,function(){
		if($(this).prop("checked")==true){
			$(this).prop("checked",false);
		}else{
			$(this).prop("checked",true);
		}
	});
}

//批量审核处罚订单
function validAllPunishOrder(){
	var ids =  $(":checkbox[name='ids']").serializeArray();
	validPunishOrder(ids,2);
}

//导出未审核的超时效订单
function exportPunishOrder(){
	window.location.href="${BasePath}/yitiansystem/merchants/businessorder/to_exportPunishOrder.sc?punishType=1&punishOrderStatus=1&"+$('#queryForm').serialize();
}

//手动添加
function addMerchantPunish(){
	 openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_addPunishOrder.sc",'600','300',"手动添加超时效订单");
}

//修改金额
function updatePunishOrderPrice(id){
	var $priceObj = $("#punishPrice_"+id);
	var price = $priceObj.val();
	if(price==""){
		alert("请输入金额");
		return false;
	}
	if(parseFloat(price)<=0){
		alert("请输入正确的金额");
		return false;
	}
	$.ajax({
		   type: "POST",
		   url: "${BasePath}/yitiansystem/merchants/businessorder/update_PunishOrderPrice.sc",
		   data:{
			 "id":id,
			 "price":price
		   },
		   cache: false,
		   success: function(result){
			  if(result == "true"){
				  alert("修改成功");
			  }else{
				  alert("操作失败");
			  }
		   }
		});

}

function zuofeiLog(id){
	$.ajax({
		   type: "POST",
		   url: "${BasePath}/yitiansystem/merchants/businessorder/addOLog.sc",
		   data:{
			 "orderNo":id
		   },
		   cache: false,
		   success: function(result){
			  if(result == "true"){
				  alert("作废成功");
			  }else{
				  alert("作废失败");
			  }
		   }
		});
}
</script>
</body>
</html>
