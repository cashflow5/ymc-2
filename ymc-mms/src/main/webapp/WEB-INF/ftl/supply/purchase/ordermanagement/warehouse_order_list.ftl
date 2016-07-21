<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/wms/css.css" />
<link rev="stylesheet" rel="stylesheet"  type="text/css" href="${BasePath}/css/wms/style.css"/>
<script type="text/javascript" src="${BasePath}/js/wms/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="${BasePath}/js/wms/alertDialog/artDialog.js"></script>
<script type="text/javascript" src="${BasePath}/js/wms/js.js"></script>
<title>优购商城--商家后台</title>
<#include "../../../yitianwms/yitianwms-include.ftl">
<#setting number_format="0">
<script type="text/javascript" src="${BasePath}/js/common/form/calendar.js"></script>
<script type="text/javascript">
	var alertMessage = '${exec}';
 	if (alertMessage != ""){
 		if (alertMessage == "fail"){
 			alert("生成出库单失败！");
 		}else{
 			alert("生成出库单成功，出库单号为：" + alertMessage);
 		}
 	}
	$(function(){
		$("#checkall").click(function(){
			if($(this).attr("checked")==true){
				$("input:checkbox").attr("checked",true);
			}else{
				$("input:checkbox").attr("checked",false);
			}
		})
		//循环选中订单
		var orderList = $("#orderList").val();
		var ol = orderList.split(',');
		var cks = $("input[name='ckorder']");
		for(var a = 0;a < cks.length; a++){
			for(var b = 0;b < ol.length; b++){
				if(cks[a].value == ol[b]){
					cks[a].checked = true;
				}
			}
		}
	});
	
	$(document).ready(function(){
			J('#bTime').calendar({ maxDate:'#eTime', format:'yyyy-MM-dd HH:mm:ss', targetFormat:'yyyy-MM-dd HH:mm:ss'});
			J('#eTime').calendar({ minDate:'#bTime', format:'yyyy-MM-dd HH:mm:ss', targetFormat:'yyyy-MM-dd HH:mm:ss'});
		});
		
	function ischeckbox(ckname){
		var bool=true;
		var checklength = $("input:checkbox[id!=checkall]:checked").length;
		if(checklength==0){
 			art.dialog.alert("请选择"+ckname+"的订单！");
 			bool=false;
		}
		return bool;
	}
	function warehousestockShortages(ckname){
		if(ischeckbox(ckname)){
			if(confirm("确定？")){
			var form=$("#myform");
			form.attr("action","u_SettingsShortages.sc");
			form.submit();
			}
		};
	}

	function ConfirmDerived(ckname){
		if(ischeckbox(ckname)){
			var form=$("#myform");
			form.attr("action","u_ConfirmDerived.sc");
			form.submit();

		};

	}
	
	//保存到其他入库
	function saveToOtherOutStroe(){
		var orders="";
		$("input[name='ckorder']").each(function(){
			if(this.checked){
				orders=orders+this.value+',';
			}
		});
		if(orders==""){
			art.dialog.alert("请勾选要生成出库单的订单");
		}else{
		  	if(confirm("确定生成？")){
		  		$("#orders").val(orders);
				var queryForm=document.getElementById("selectForm");
				queryForm.action="c_otheroutstorage.sc";
				queryForm.submit();
		  	}
		}
	}
	
	//导出选中订单
	function exportChecked(){
		var orderNo="";
		$("input[name='ckorder']").each(function(){
			if(this.checked){
				orderNo=orderNo+this.value+',';
			}
		});
		if(orderNo==""){
			art.dialog.alert("请选择要导出的订单");
		}else{
			$("#orderList").val(orderNo);
			$("#exportChecked").submit();
		}
	}


//导出所有订单
	function exportAll(){
		$.post("isEmpty4NoExport.sc",function(returnData,status){
				if(status=="success"){
					if(returnData=="ok"){
						art.dialog.alert("没有未导出的订单数据");
					}else{
						$("#exportAll").submit();
					}
				}
		});
	}
	
</script>
</head>
<body>
<div class="contentMain">
	<div class="ytback-tt-1 ytback-tt">
	<!--	<span>您当前的位置：</span> -->
		<a href="#">地区仓管理</a> &gt;
		待出库销售订单
	</div>
  <div class="blank5"></div>
    	<div class="wms">

    	<div class="mb-btn-fd-bd">
<div class="mb-btn-fd relative" style="width:40%"><span class="btn-extrange absolute ft-sz-14">
<ul class="onselect">
	<li class="pl-btn-lfbg"></li>
	<li class="pl-btn-ctbg"><a class="btn-onselc"> 订单列表 </a></li>
	<li class="pl-btn-rtbg"></li>
</ul>

</span></div>
<div class="yt-btn-edit-4ft"><a href="#" onclick="warehousestockShortages('需要设置缺货');">置为缺货</a></div>
<!-- <div class="yt-btn-edit-4ft"><a href="#" onclick="ConfirmDerived('需要设置调出')">地区仓调出</a></div> -->
<div class="yt-btn-edit-4ft"><a href="#" onclick="saveToOtherOutStroe();">生成出库单</a></div>
<form action="${BasePath}/supply/manage/areawarehouseorder/doExportAll.sc" method="post" id="exportAll">
	<div class="yt-btn-add-4ft"><a href="#" onclick="exportAll();">导出全部</a></div>
</form>
<form action="${BasePath}/supply/manage/areawarehouseorder/doExportChecked.sc" method="post" id="exportChecked">
	<div class="yt-btn-add-4ft"><a href="#" onclick="exportChecked();">导出选中</a></div>
	<input type="hidden" name="orderList" id="orderList" />
</form>
</div>
	<div class="yt-c-top">
		<form name="selectForm" action="querywarehouseorder.sc" method="post" id = "selectForm">
			<input type="hidden" name="orders" id="orders" value=""/>
			订单号：<input type="text" name="orderSubNo" id="orderCode" value="<#if orderSubNo??>${orderSubNo?if_exists}</#if>"/>
			 <span>入库时间：</span>
        <input type="text" name="bTime" id="bTime" value="${bTime?if_exists}" readonly="readonly" size="20" />
        -
        <input type="text" name="eTime" id="eTime" value="${eTime?if_exists}" readonly="readonly" size="20" />
			是否已导出：<select name="isDerived" id="isDerived">
							<option value="">全部</option>
							<option value="2" <#if isDerived??><#if isDerived=="2">selected="true"</#if></#if> >否</option>
							<option value="1" <#if isDerived??><#if isDerived=="1">selected="true"</#if></#if> >是</option>
						</select>
			<input class="yt-seach-btn" type="submit" value="搜索" />
		</form>
	</div>
		<form id="myform" name="myform" action="" method="post">
		          <#if warehouse??>
			          	<input type="hidden" name="warehousecode" value="${warehouse.warehouseCode?if_exists}"/>
		          </#if>
			
			<div class="yt-c-div">
				<table cellpadding="0" cellspacing="0" class="ytweb-table">
                <thead>
                <tr>
                	  <th  width="60"><input type="checkbox" id="checkall" name="" /></th>
	                <th>订单号</th>
	                <th>收货人</th>
	                <th>仓库名称</th>
	                <th>付款方式</th>
	                <th>总金额</th>
	                <th>状态</th>
	                <th>下单时间</th>
	                <th>地区仓库发货状态</th>
	                <th>是否导出</th>
	                <th>操作</th>
                </tr>
                </thead>
                <tbody>
				<#if pageFinder?? && (pageFinder.data)?? && (pageFinder.data?size > 0)>
					<#list pageFinder.data as item >
					<tr >
						<td><input type="checkbox" id="check" name="ckorder" value="${item.orderCode?if_exists}"/></td>
						<td >${item.orderCode!""}</td>
						<td>${item.receivePeople!""}</td>
						<td>${item.warehouseName!""}</td>
						<td >${item.paymentName!""}</td>
						<!--<td >#{item.orderTotalPrice?default(0);m2}</td>--> 
						<td>***</td> 
						<td ><#if item.status?? && checkStatusVos??>
						          <#list checkStatusVos as checkStatusVos>
						          	<#if checkStatusVos.status==item.status>${checkStatusVos.statusName?if_exists}</#if>
						          </#list>
					          </#if>
					     </td>
						<td >${item.orderDate!""}</td>
							<td >	<#if item.warehouseDeliveryStatus?? && (item.warehouseDeliveryStatus==2)> 已出货
						<#elseif item.warehouseDeliveryStatus?? && item.warehouseDeliveryStatus==1>待出库
						<#else>待出货</#if></td>
								<td ><#if item.isDerived??><#if item.isDerived==1>是<#else>否</#if><#else>否</#if></td>
						<td class="td0"><a href="queryorderflowvodetail.sc?orderId=${item.orderID}">详情</a>
						</td>
					</tr>
					</#list>
					<#else>
					<tr><td colspan="11"><div style="color:red;">对不起，暂时没有记录</div></td></tr>
				</#if>
				</tbody>
                </table>
                </form>
				<div class="blank15"></div>
				<form action="querywarehouseorder.sc" name="pageForm" id="pageForm" method="POST">
					<input type="hidden" name="orderSubNo" value="<#if orderSubNo??>${orderSubNo}</#if>"/>
					<input type="hidden" name="isDerived" value="<#if isDerived??>${isDerived}</#if>" />
					<input type="hidden" name="bTime" id="bTime" value="${bTime?if_exists}" />
	    			<input type="hidden" name="eTime" id="eTime" value="${eTime?if_exists}" />
				</form>
				<#if pageFinder?? && (pageFinder.data)??>
					<#import "../../../common.ftl" as page>
          			<@page.queryForm formId="pageForm" />
				</#if>
				<div class="blank10"></div>
		</div>
	</div>
</div>
</body>
</html>