<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#include "../merchants-include.ftl">
<title>优购商城--商家后台</title>
</head>
<body>
<div class="container">
	<!--工具栏start--> 
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li  class="curr">
				  <span><a href="${BasePath}/yitiansystem/merchants/punishsettle/to_punishsettle.sc" class="btn-onselc">违规结算列表</a></span>
				</li>
			</ul>
		</div>
   <div class="modify"> 
     <form action="${BasePath}/yitiansystem/merchants/punishsettle/to_punishsettle.sc" name="queryForm" id="queryForm" method="post">
  			  	<div class="wms-top">
                     <label>登记单号：</label>
                     <input type="text" name="registNum" id="registNum" value="${(punishSettleVo.registNum)!'' }"/>&nbsp;&nbsp;&nbsp;
                     <label>登记状态：</label>
                     <select name="status" id="status">
                        <option value="">全部</option>
                        <option value="0" <#if (punishSettleVo.status)?? && punishSettleVo.status == "0">selected</#if> >新建</option>
                        <option value="1" <#if (punishSettleVo.status)?? && punishSettleVo.status == "1">selected</#if> >已审核</option>
                        <option value="2" <#if (punishSettleVo.status)?? && punishSettleVo.status == "2">selected</#if> >结算中</option>
                        <option value="3" <#if (punishSettleVo.status)?? && punishSettleVo.status == "3">selected</#if> >已结算</option>
                        <option value="4" <#if (punishSettleVo.status)?? && punishSettleVo.status == "4">selected</#if> >已取消</option>
                     </select>
                     <label>商家名称：</label>
                     <input type="text" name="supplier" id="supplier" value="${(punishSettleVo.supplier)!'' }"/>&nbsp;&nbsp;&nbsp;
                     <label>商家编码：</label>
                   	 <input type="text" name="supplierCode" id="supplierCode" value="${(punishSettleVo.supplierCode)!'' }"/>
                </div>
  			  	<div class="wms-top">
  			  		<label>扣款类型：</label>
                   	<select name="deductType" id="deductType">
                        <option value="">全部</option>
                        <option value="1" <#if (punishSettleVo.deductType)?? && punishSettleVo.deductType == "1">selected</#if> >超时效订单</option>
                        <option value="0" <#if (punishSettleVo.deductType)?? && punishSettleVo.deductType == "0">selected</#if> >缺货商品</option>
                     </select>
                     <label>登记人：</label>
                   	 	<input type="text" name="registrant" id="registrant" value="${(punishSettleVo.registrant)!'' }"/>
                     <label>登记日期：</label>
                   	 <input type="text" name="registTimeStart" id="registTimeStart" value="<#if (punishSettleVo.registTimeStart) ??>${punishSettleVo.registTimeStart?string('yyyy-MM-dd HH:mm:ss')}</#if>"/>
                   	    至
                   	 <input type="text" name="registTimeEnd" id="registTimeEnd" value="<#if (punishSettleVo.registTimeEnd) ??>${punishSettleVo.registTimeEnd?string('yyyy-MM-dd HH:mm:ss')}</#if>"/>
                   	 <label>结算单号：</label>
                     <input type="text" name="settleNo" id="settleNo" value="<#if punishSettleVo??&&punishSettleVo.settleNo??>${punishSettleVo.settleNo!''}</#if>"/>&nbsp;&nbsp;&nbsp;
                   	 <input type="button" value="搜索" onclick="queryMerchantPunish();" class="yt-seach-btn" />&nbsp;&nbsp;&nbsp;
              	</div>
              	</form>
                <table cellpadding="0" cellspacing="0" class="list_table">
                		<thead>
                        <tr>
	                        <th style="width:80px;">登记单号</th>
	                        <th style="width:90px;">登记状态</th>
	                        <th style="width:100px;">商家</th>
	                        <th style="width:90px;">扣款金额</th>
	                        <th style="width:90px;">类型</th>
	                        <th style="width:120px;">结算起止日期</th>
	                        <th style="width:90px;">违规数量</th>
	                        <th style="width:70px;">登记人</th>
	                        <th style="width:90px;">登记日期</th>
	                        <th style="width:70px;">审核人</th>
	                        <th style="width:90px;">审核日期</th>
	                        <th style="width:90px;">结算单号</th>
	                        <th style="width:100px;">结算日期</th>
	                        <th style="width:50px;">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        	<#if pageFinder?? && (pageFinder.data)?? >
	                   		<#list pageFinder.data as item >
	                        <tr id="td_${(item.id)!''}" >
		                        <td title='${(item.registNum)!"" }'>
		                        		${(item.registNum)?default("--")}
		                        </td>
		                        <td>
		                        	<#if (item.status)??>
		                        		<#if item.status=="0">
		                        			新建
		                        		<#elseif item.status=="1">
		                        			已审核
		                        		<#elseif item.status=="2">
		                        			结算中
		                        		<#elseif item.status=="3">
		                        			已结算
		                        		<#elseif item.status=="4">
		                        			已取消
		                        	</#if>
		                        	<#else>
		                        		--
		                        	</#if>
		                        </td>
		                        <td>${(item.supplierCode)!'' }</br>${(item.supplier)!'' }</td>
		                        <td>
									${(item.deductMoney)!'' }
								</td>
								<td>
									<#if (item.deductType)??>
		                        		<#if item.deductType=="1">
		                        			超时效
		                        		<#elseif item.deductType=="0">
		                        			缺货
		                        		</#if>
		                        	<#else>
		                        		--
		                        	</#if>
								</td>
								<td>
									<#if (item.settleStart)??>
										${item.settleStart?string("yyyy-MM-dd HH:mm:ss") }
									</#if>
									</br>
									至
									</br>
									<#if (item.settleEnd)??>
										${item.settleEnd?string("yyyy-MM-dd HH:mm:ss") }
									</#if>
								</td>
								<td>${(item.settleOrderNum)!'' }</td>
		                        <td>
		                        	${(item.registrant)!'--' }
		                        </td>
		                        <td>
		                        	${(item.registTime)!'--' }
		                        </td>
		                        <td>
		                        	${(item.audit)!'--' }
		                        </td>
		                        <td>
		                        	${(item.auditTime)!'--' }
		                        </td>
		                        <td>
		                        	${(item.settleNo)!'--' }
		                        </td>
		                        <td>
		                        	${(item.settleTime)!'--' }
		                        </td>
	                        	<td>
	                        	<#if item.status=="0">
		                        	<a href="javascript:;" onclick="cancelPunishOrder('${item.id }');">取消</a>
		                        	</br>
		                        	</br>
		                        	<a href="javascript:;" onclick="submitPunishOrder('${item.id }');">提交</a>
	                        	</#if>
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
</body>
</html>
<script type="text/javascript">

$(document).ready(function() {
	$('#registTimeStart').calendar({maxDate:'#registTimeEnd',format:'yyyy-MM-dd HH:mm:ss'});
	$('#registTimeEnd').calendar({minDate:'#registTimeStart',format:'yyyy-MM-dd HH:mm:ss'});
	
});

//根据条件查询
function queryMerchantPunish(){
   document.queryForm.method="post";
   document.queryForm.submit();
}

function submitPunishOrder(id){
	$.ajax({
		   type: "POST",
		   url: "${BasePath}/yitiansystem/merchants/punishsettle/submitPunishOrder.sc",
		   data:{
			 "id":id
		   },
		   cache: false,
		   dataType:'json',
		   success: function(data){
			  if(data.result == "true"){
				  $("#queryForm").submit();
			  }else{
				  alert("提交失败！");
			  }
		   }
		});
}

var cancelPunishOrder = function(id){
	parent.ygdg.dialog.confirm('确认取消该违规结算记录？', function(){
		$.ajax({
			   type: "POST",
			   url: "${BasePath}/yitiansystem/merchants/punishsettle/cancelPunishOrder.sc",
			   data:{
				 "id":id
			   },
			   cache: false,
			   dataType:'json',
			   success: function(data){
				  if(data.result == "true"){
					  $("#queryForm").submit();
				  }else{
					  alert("取消失败！");
				  }
			   }
			});
	});
};

//导出违规订单
function exportPunishOrder(){
	var ids =  $(":checkbox[name='ids']").serializeArray();
	if(ids == ''){
		alert("请选择要导出的订单!");
		return false;
	}
	
	var idsParam ="";
	if(ids.constructor  === Array ){
		$.each(ids, function(i, field){
			//alert(i);
			idsParam = idsParam + "&ids="+field.value;
		});
	}else{
		idsParam = "&ids=" + ids;
	}
	window.open('${BasePath}/yitiansystem/merchants/businessorder/to_exportPunishOrder.sc?mehtod=param'+idsParam);
	$(":checkbox[name='ids'],:checkbox[id='chkb']").each(function(){
		$(this).attr("checked",false);
	});
}

//手动添加
function addMerchantPunish(){
	 openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_addPunishOrder.sc",'','',"手动添加违规订单");
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

</script>
