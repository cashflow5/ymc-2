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
				<li>
				  <span><a href="${BasePath}/yitiansystem/merchants/businessorder/to_punishValidList.sc" class="btn-onselc">缺货商品</a></span>
				</li>
				<li class="curr">
				  <span><a href="${BasePath}/yitiansystem/merchants/businessorder/to_punishStockValidList.sc" class="btn-onselc">已审核缺货</a></span>
				</li>
			</ul>
		</div>
   <div class="modify"> 
     <form action="${BasePath}/yitiansystem/merchants/businessorder/to_punishStockValidList.sc" name="queryForm" id="queryForm" method="post">
  			  	<div class="wms-top">
                     <label>订单号：</label>
                     <input type="text" name="orderNo" id="orderNo" value="${(punishOrderVo.orderNo)!'' }"/>&nbsp;&nbsp;&nbsp;
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
                     <label>结算状态：</label>
                     <select name="isSettle" id="isSettle">
                        <option value="">请选择</option>
                        <option value="1" <#if (punishOrderVo.isSettle)?? && (punishOrderVo.isSettle)=="1">selected</#if>>已结算</option>
                        <option value="0" <#if (punishOrderVo.isSettle)?? && (punishOrderVo.isSettle)=="0">selected</#if>>未结算</option>
                     </select>
                 </div>
              	<div class="wms-top">
              		<label>提交状态：</label>
                     <select name="isSubmit" id="isSubmit">
                        <option value="">请选择</option>
                        <option value="1" <#if (punishOrderVo.isSubmit)?? && (punishOrderVo.isSubmit)=="1">selected</#if> >已提交</option>
                        <option value="0" <#if (punishOrderVo.isSubmit)?? && (punishOrderVo.isSubmit)=="0">selected</#if>>未提交</option>
                     </select>
                      &nbsp;&nbsp;
                   	 <input type="button" value="搜索" onclick="queryMerchantPunish();" class="yt-seach-btn" />&nbsp;&nbsp;&nbsp;
              	</div>
              	<div class="wms-top">
              		 <input type="button" value="批量取消" onclick="cancelAllPunishOrder();" class="btn-add-normal-4ft" />
              		 &nbsp;&nbsp;
              		 <span style="color:red;">注：统计罚款金额只会根据下单时间（只能属于一个月）与商家名称两个查询条件统计，其余条件将会忽略！</span>
                	 <input type="button" value="导出" title="根据查询条件导出" onclick="exportPunishOrder();" class="btn-add-normal-4ft fr"  />&nbsp;&nbsp;
                	 &nbsp;&nbsp;&nbsp;&nbsp;
              		 <input type="button" value="统计罚款金额" style="margin: 0 30px;" onclick="countPunishOrder();" class="fr"/>
              	</div>
              	</form>
                <table cellpadding="0" cellspacing="0" class="list_table">
                		<thead>
                        <tr>
                        	<th style="padding-left:0px;width:50px;"><input onclick="selectAllPunishOrder(this,'ids')" id="chkb" type="checkbox" /></th>
	                        <th style="width:80px;">优购订单号</th>
	                        <th style="width:80px;">下单日期</th>
	                        <th style="width:80px;">商家名称</th>
	                        <th style="width:80px;">商家编号</th>
	                        <th style="width:70px;">品牌</th>
	                        <th style="width:80px;">货品条码</th>
	                        <th style="width:80px;">置缺时间</th>
	                        <th style="width:80px;">单品成交价</th>
	                        <th style="width:70px;">结算状态</th>
	                        <th style="width:70px;">提交状态</th>
	                        <th style="width:70px;">缺货数</th>
	                        <th style="width:100px;">罚款金额</th>
	                        <th style="width:100px;">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        	<#if pageFinder?? && (pageFinder.data)?? >
	                   		<#list pageFinder.data as item >
	                        <tr>
	                        	<td><input type="checkbox" name="ids"  value='${(item.id)!""}' ${(item.is_submit=='1')?string('disabled','') }/></td> 
		                        <td><a target="_blank" href="${omsHost!''}/yitiansystem/ordergmt/orderdetail/toBaseDetail.sc?orderNo=${(item.order_sub_no)!'' }" > ${(item.order_sub_no)!'' }</a> </td>
		                        <td>${(item.order_time)!'' }</td>
		                        <td>${(item.supplier)!'' }</td>
		                        <td>${(item.supplier_code)!'' }</td>
		                        <td>
		                        	${(item.brand_name)!'' }
		                        </td>
		                        <td>
		                        	${(item.level_code)!''}
		                        </td>
		                        <td>${(item.exception_time)!''}</td>
		                        <td>${(item.totalamt)!'' }</td>
		                        <td>
		                        <#if (item.is_settle)?? && item.is_settle=="1">
		                        	已结算
		                        <#else>
		                        	未结算
		                        </#if>
		                        </td>
		                        <td id="${(item.id)!'' }_submit" name="${(item.is_submit)!'0' }">
		                        <#if (item.is_submit)?? && item.is_submit=="1">
		                        	已提交
		                        <#else>
		                        	未提交
		                        </#if>
		                        </td>
		                        <td>
		                        	${(item.lack_num)!'1' }
		                        </td>
		                        <td>
		                        <#if (item.is_submit)?? && item.is_submit=="1">
		                        	<input id="punishPrice_${(item.id)!'' }" size="5" disabled="disabled" value="${(item.punish_price)!'0' }" title="只能对0保存，其余值会随缺货率改变"/>
		                        	<#else>
		                        	<input id="punishPrice_${(item.id)!'' }" size="5"  value="${(item.punish_price)!'0' }" title="只能对0保存，其余值会随缺货率改变"/>
		                        	<a href="javascript:;" onclick="updatePunishOrderPrice('${(item.id)!'' }')" title="只能对0保存，其余值会随缺货率改变">保存</a>
		                        </#if>
		                        </td>
		                        <td>
		                        <#if (item.is_submit)?? && item.is_submit=="1">
		                        	<#else>
		                        	<a href="javascript:;" onclick="cancelPunishOrder('${item.id}','1');">取消</a>
		                        </#if>
		                        	<a href="javascript:;" onclick="viewLog('${item.id }');">日志</a>
		                        </td>
	                        </tr>
	                        </#list>
	                        </#if>
                      </tbody>
                </table>
                <form style="display: none;" id="tempForm">
                	<input type="hidden" name="supplierCode" id="supplierCode"/>
                	<input type="hidden" name="supplier" id="supplier"/>
                	<input type="hidden" name="deductMoney" id="deductMoney"/>
                	<input type="hidden" name="deductType" id="deductType"/>
                	<input type="hidden" name="settleStart" id="settleStart"/>
                	<input type="hidden" name="settleEnd" id="settleEnd"/>
                	<input type="hidden" name="settleOrderNum" id="settleOrderNum"/>
                </form>
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
	$('#orderTimeEnd').calendar({maxDate:'%y-%M-%d',minDate:'#orderTimeStart',format:'yyyy-MM-dd HH:mm:ss'});
	$('#lackTimeStart').calendar({maxDate:'#lackTimeEnd',format:'yyyy-MM-dd HH:mm:ss'});
	$('#lackTimeEnd').calendar({maxDate:'%y-%M-%d',minDate:'#lackTimeStart',format:'yyyy-MM-dd HH:mm:ss'});
	$('#merchantName_search').suggest({selectId:"merchantName"});
	$('#brandNo_search').suggest({selectId:"brandNo"});
});
//根据条件查询
function queryMerchantPunish(){
   document.queryForm.method="post";
   document.queryForm.submit();
}
function selectAllPunishOrder(obj,chkName){
	var id = obj.id;
	if($("#"+id).attr("checked")) {
		var arr = $("input[name='"+chkName+"']");
		$.each(arr,function(i,n){
			if(!($(n).attr("disabled"))){
				$(n).attr("checked",'true');//全选
			}
		});
	}else{
		$("input[name='"+chkName+"']").removeAttr("checked");//取消全选
	}
}

function updatePunishOrderPrice(id){
	var $priceObj = $("#punishPrice_"+id);
	var price = $priceObj.val();
	if(price==""){
		alert("请输入金额");
		return false;
	}
	if(parseFloat(price)==0){
		$.ajax({
			   type: "POST",
			   url: "${BasePath}/yitiansystem/merchants/businessorder/update_PunishOrderPrice.sc",
			   data:{
				 "id":id,
				 "price":price,
				 "type":'0'
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
	}else{
		alert("只能输入0才可以保存！");
		return false;
	}
}

function viewLog(id){
	openwindow("${BasePath}/yitiansystem/merchants/businessorder/viewTimeoutPunishLog.sc?merchantCode="+id,'800','600',"缺货商品日志");
}

//取消处罚订单
function cancelPunishOrder(ids,validStatus){
	if($.trim(ids)==""){
		parent.ygdg.dialog.alert("请先选择缺货商品！");
	}else{
		var flag = false;
		if(ids.constructor  === Array ){
			$.each(ids, function(i, field){
				if("1"==$("#"+field.value+"_submit").attr("name")){
					parent.ygdg.dialog.alert("选中的缺货商品包含了已提交到结算列表的商品，请筛选出再批量取消！");
					flag = false;
					return false;
				}else{
					flag = true;
				}
			});
		}else{
			if("1"==$("#"+ids+"_submit").attr("name")){
				parent.ygdg.dialog.alert("选中的缺货商品已提交到结算列表中，不可取消！");
			}else{
				flag = true;
			}
		}
		if(flag){
			parent.ygdg.dialog.confirm('确认取消缺货商品吗？', function(){
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
							  alert("取消成功");
							  $("#queryForm").submit();
						  }else{
							  alert("取消失败");
						  }
					   }
					});
			});
		}
	}
}

//批量取消处罚订单
function cancelAllPunishOrder(){
	var ids =  $(":checkbox[name='ids']").serializeArray();
	cancelPunishOrder(ids,'1');
}

function countPunishOrder(){
	var startDate = $('#orderTimeStart').val();
	var endDate = $('#orderTimeEnd').val();
	if($('#orderTimeStart').val()!='' && $('#orderTimeEnd').val()!='' && $('#merchantName').val()!= null){
		if($('#merchantName').val().length>1){
			parent.ygdg.dialog.alert("请选择至多一位商家！");
		}else{
			var str = "<br/><table cellpadding='0' cellspacing='0'>"+
              	"<tr><td style='width:80px;padding:5px;text-align:right'>违规类型：</td><td>缺货商品<td/></tr>"+
				"<tr><td style='width:80px;padding:5px;text-align:right'>商家名称：</td><td id='tempMerchant'>"+$("#merchantName").find("option:selected").text()+"</td></tr>"+
				"<tr><td style='width:80px;padding:5px;text-align:right'>结算周期：</td><td>"+startDate+"至"+endDate+"</td></tr>"+
				"<tr><td style='width:80px;padding:5px;text-align:right'>罚款总金额：</td><td><input type='text' id='totalAmount' name='totalAmount' style='width:50px;'/>元</td>"+
				"</tr></table><br/>";
			parent.ygdg.dialog({
				id:'sumOrderId',
				title:'缺货违规商品结算',
				content:str,
				okVal:'提交',
				init:function(){
					$("#totalAmount",parent.document).focus();
				},
				ok:function(){
					var total = $.trim($("#totalAmount",parent.document).val());
					if(total!='' && (!isNaN(total))){
						var crow = $.trim($("#countrow",parent.document).val());
						if(crow>0){//存在违规数量
							$("#settleStart").val(startDate);
							$("#settleEnd").val(endDate);
							$("#supplierCode").val($("#merchantCode",parent.document).val());
							$("#supplier").val($("#tempMerchant",parent.document).text());
							$("#deductMoney").val(total);
							$("#deductType").val("0");
							$("#settleOrderNum").val($("#countrow",parent.document).val());
							//提交到结算列表
							$.post("${BasePath}/yitiansystem/merchants/businessorder/submitAmount.sc",
									$("#tempForm").serialize(),
									function(json){
								if(json.result=="success"){
									parent.ygdg.dialog.alert("提交成功，请到【违规结算列表】菜单查看！");
									$("#queryForm").submit();
								}else{
									parent.ygdg.dialog.alert(json.msg);
									return false;
								}
							},"json");
						}else{
							parent.ygdg.dialog.alert("该时间范围内无违规订单或已全部提交了！");
							return false;
						}
					}else{
						$("#totalAmount",parent.document).focus();
						$("#totalAmount",parent.document).css({ "color": "red"});;
						return false;
					}
				},
				cancelVal:'取消',
				cancel:function(){}
			});
			$.getJSON("${BasePath}/yitiansystem/merchants/businessorder/getTotalAmount.sc",{
					'orderTimeStart':startDate,
					'orderTimeEnd':endDate,
					'merchantCode':($('#merchantName').val())[0],
					'punishType':'0'
				},function(json){
				$("#totalAmount",parent.document).val(parseFloat(json.countamount).toFixed(2));
				$("#countrow",parent.document).remove();
				$("#merchantCode",parent.document).remove();
				$("#totalAmount",parent.document).after("<input type='hidden' value='"+json.countrow+"' id='countrow'>");
				$("#countrow",parent.document).after("<input type='hidden' id='merchantCode' value='"+($('#merchantName').val())[0]+"'>");
			});
		}
	}else{
		parent.ygdg.dialog.alert("请选择一个商家并设置下单时间周期！");
	}
}


//手动添加
function addMerchantPunish(){
	 openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_addStockPunishOrder.sc",'600','300',"手动添加缺货订单");
}
//导出违规订单
function exportPunishOrder(){
	window.location.href = "${BasePath}/yitiansystem/merchants/businessorder/to_exportOutOfStock.sc?punishOrderStatus=2&"+$("#queryForm").serialize();
}
</script>
</html>
