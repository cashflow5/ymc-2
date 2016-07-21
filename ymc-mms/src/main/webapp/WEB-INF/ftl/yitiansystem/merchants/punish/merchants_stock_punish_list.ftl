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
				<li class="curr">
				  <span><a href="${BasePath}/yitiansystem/merchants/businessorder/to_punishValidList.sc" class="btn-onselc">缺货商品</a></span>
				</li>
				<li >
				  <span><a href="${BasePath}/yitiansystem/merchants/businessorder/to_punishStockValidList.sc" class="btn-onselc">已审核缺货</a></span>
				</li>
			</ul>
		</div>
   <div class="modify"> 
     <form action="${BasePath}/yitiansystem/merchants/businessorder/to_punishValidList.sc" name="queryForm" id="queryForm" method="post">
  			  	<div class="wms-top">
                     <label>订单号：</label>
                     <input type="text" name="orderNo" id="orderNo" value="${(punishOrderVo.orderNo)!'' }"/>
                     <label>货品条码：</label>
                     <input type="text" name="insideCode" id="insideCode" value="${(punishOrderVo.insideCode)!'' }"></input>
                     <label>货品负责人：</label>
                     <input type="text" name="supplierYgContacts" id="supplierYgContacts" value="<#if punishOrderVo??&&punishOrderVo.supplierYgContacts??>${punishOrderVo.supplierYgContacts!""}</#if>"/>
                     <label>商家编号：</label>
                   	 <input type="text" name="merchantCode" id="merchantCode" value="${(punishOrderVo.merchantCode)!'' }"/>
                </div>
  			  	<div class="wms-top">
                     <label>下单时间：</label>
                   	 <input type="text" name="orderTimeStart" id="orderTimeStart" value="<#if (punishOrderVo.orderTimeStart) ??>${punishOrderVo.orderTimeStart?string("yyyy-MM-dd HH:mm:ss")}</#if>"/>
                   	    至
                   	 <input type="text" name="orderTimeEnd" id="orderTimeEnd" value="<#if (punishOrderVo.orderTimeEnd) ??>${punishOrderVo.orderTimeEnd?datetime?string("yyyy-MM-dd HH:mm:ss")}</#if>"/>
                   	 <label>置缺时间：</label>
                   	 <input type="text" name="lackTimeStart" id="lackTimeStart" value="<#if (punishOrderVo.lackTimeStart) ??>${punishOrderVo.lackTimeStart?string("yyyy-MM-dd HH:mm:ss")}</#if>"/>
                   	    至
                   	 <input type="text" name="lackTimeEnd" id="lackTimeEnd" value="<#if (punishOrderVo.lackTimeEnd) ??>${punishOrderVo.lackTimeEnd?string("yyyy-MM-dd HH:mm:ss")}</#if>"/>
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
                     <select multiple="" value="" style="width: 128px;height:60px" name="merchantName" id="merchantName" class="selecttxt">
                     	<#list merchants as merchant>
	                     	<option value="${merchant.merchantCode }" <#if (punishOrderVo.merchantName)?? && (punishOrderVo.merchantName )?contains(merchant.merchantCode)>selected</#if>>${merchant.supplierName }</option>
                     	</#list>
                     </select>
                     <label>商品品牌：</label>
                     <input type="text" name="brandNo_search" placeholder="模糊搜索" id="brandNo_search" />&nbsp;&nbsp;&nbsp;
                     <select multiple="" value="" style="width: 128px;height:60px" name="brandNo" id="brandNo" class="selecttxt">
                     	<#list brands as brand>
	                     	<option value="${brand.brandNo }" <#if (punishOrderVo.brandNo)?? && (punishOrderVo.brandNo )?contains(brand.brandNo)>selected</#if>>${brand.brandName }</option>
                     	</#list>
                     </select>
                     &nbsp;&nbsp;
                   	 <input type="button" value="搜索" onclick="queryMerchantPunish();" class="yt-seach-btn" />&nbsp;&nbsp;&nbsp;
              	</div>
              	<div class="wms-top">
              		 <input type="button" value="手动添加缺货商品" onclick="addMerchantPunish();"  />&nbsp;&nbsp;&nbsp;
              		 <input type="button" value="批量审核" onclick="validAllPunishOrder();" class="btn-add-normal-4ft" />&nbsp;&nbsp;
                	 <a href="javascript:void(0);" style="color:#0066ff;" onclick="selectAllPunishOrder();">全选</a>&nbsp;&nbsp;
                	 <a href="javascript:void(0);" style="color:#0066ff;" onclick="cancelAllPunishOrder();">反选</a>&nbsp;&nbsp;
                	 <input type="button" value="导出" title="根据查询条件导出" onclick="exportPunishOrder();" class="btn-add-normal-4ft fr"  />&nbsp;&nbsp;
              	</div>
              	</form>
                <table cellpadding="0" cellspacing="0" class="list_table">
                		<thead>
                        <tr>
                        	<th style="padding-left:0px;width:50px;"><input onclick="allChk(this,'ids')" id="chkb" type="checkbox" /></th>
	                        <th style="width:80px;">优购订单号</th>
	                        <th style="width:80px;">下单日期</th>
	                        <th style="width:80px;">商家名称</th>
	                        <th style="width:80px;">商家编号</th>
	                        <th style="width:80px;">品牌</th>
	                        <th style="width:80px;">货品条码</th>
	                        <th style="width:80px;">置缺时间</th>
	                        <th style="width:80px;">单品成交价</th>
	                        <th style="width:60px;">缺货数</th>
	                        <th style="width:60px;">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        	<#if pageFinder?? && (pageFinder.data)?? >
	                   		<#list pageFinder.data as item >
	                        <tr>
	                        	<td><input type="checkbox" name="ids"  value="${(item.detail_id)!'' }" >
	                        		<input type="hidden" id="p_${(item.detail_id)!'' }"  value="${(item.id)!'' }" >
	                        	</td> 
		                        <td><a target="_blank" href="${omsHost!''}/yitiansystem/ordergmt/orderdetail/toBaseDetail.sc?orderNo=${(item.order_sub_no)!'' }" > ${(item.order_sub_no)!'' }</a> </td>
		                        <td>${(item.create_time)!'' }</td>
		                        <td>${(item.supplier)!'' }</td>
		                        <td id="${item.detail_id }_sup">${(item.supplier_code)!'' }</td>
		                        <td>
		                        	${(item.brand_name)!'' }
		                        </td>
		                        <td>
		                        	${(item.level_code)!''}
		                        </td>
		                        <td>
		                       	 	${(item.exception_time)!''}
		                        </td>
		                        <td>${(item.totalamt)!'' }</td>
		                        <td>${(item.lack_num)!'1' }</td>
		                        <td><a href="javascript:;" onclick="validPunishOrder('${item.detail_id }','2','${(item.id)!'' }');">审核</a></td>
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
</body>
<script type="text/javascript">
$(document).ready(function(){
	$('#orderTimeStart').calendar({maxDate:'#orderTimeEnd',format:'yyyy-MM-dd HH:mm:ss'});
	$('#orderTimeEnd').calendar({minDate:'#orderTimeStart',format:'yyyy-MM-dd HH:mm:ss'});
	$('#lackTimeStart').calendar({maxDate:'#lackTimeEnd',format:'yyyy-MM-dd HH:mm:ss'});
	$('#lackTimeEnd').calendar({minDate:'#lackTimeStart',format:'yyyy-MM-dd HH:mm:ss'});
	$('#merchantName_search').suggest({selectId:"merchantName"});
	$('#brandNo_search').suggest({selectId:"brandNo"});
});
//根据条件查询
function queryMerchantPunish(){
   document.queryForm.method="post";
   document.queryForm.submit();
}
function selectAllPunishOrder(){
	$(":checkbox[name='ids']").prop("checked",true);
}

//批量审核处罚订单
function validAllPunishOrder(){
	var ids =  $(":checkbox[name='ids']").serializeArray();
	validPunishOrder(ids,'2');
}

//审核处罚订单
function validPunishOrder(ids,validStatus,punishId){
	if($.trim(ids)==""){
		parent.ygdg.dialog.alert("请先选择缺货商品！");
	}else{
		parent.ygdg.dialog.confirm('确认审核通过吗？', function(){
				var idsParam ="";
				var punishIdsParam = "";
				var merchantCodeParam = "";
				if(ids.constructor  === Array ){
					$.each(ids, function(i, field){
						idsParam = idsParam + "&ids="+field.value;
						punishIdsParam = punishIdsParam + "&punishId="+$("#p_"+field.value).val();
						merchantCodeParam = merchantCodeParam +"&merchantCode="+$('#'+field.value+'_sup').text();
					});
				}else{
					idsParam = "&ids=" + ids;
					punishIdsParam = "&punishId="+punishId;
					merchantCodeParam = "&merchantCode="+$('#'+ids+'_sup').text();
				}
				$.ajax({
					   type: "POST",
					   url: "${BasePath}/yitiansystem/merchants/businessorder/valid_stockpunish_order.sc",
					   data:"validStatus="+validStatus+idsParam+punishIdsParam+merchantCodeParam,
					   cache: false,
					   success: function(result){
						  if(result == "true"){
							  alert("审核成功");
							  //refreshpage("${BasePath}/yitiansystem/merchants/businessorder/to_punishValidList.sc");
							  $("#queryForm").submit();
						  }else{
							  alert("审核失败");
						  }
					   }
					});
		});
	}
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
//手动添加
function addMerchantPunish(){
	 openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_addStockPunishOrder.sc",'700','400',"手动添加缺货商品");
}
//导出违规订单
function exportPunishOrder(){
	window.location.href = "${BasePath}/yitiansystem/merchants/businessorder/to_exportOutOfStock.sc?"+$("#queryForm").serialize();
}
</script>
</html>
