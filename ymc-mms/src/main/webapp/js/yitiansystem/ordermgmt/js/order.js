/******************************************************************************************/
/*****************************订单相关的JS-非订单组同事请勿随便修改**************************/
/******************************************************************************************/
var BasePath="/bms";
//var BasePath="";
//设置商品缺货
function lackGoods(id, orderId, count) {
	var parm = 'id='+ id + '&orderId='+ orderId + '&count=' + count;
	openwindow(BasePath+'/yitiansystem/ordergmt/orderflowdetail/isStayGoods.sc?'+parm,400,280,'设置商品缺货');
}

//获取可退款总金额
function getRefundPrice() {
	var url = BasePath+'/yitiansystem/ordergmt/ordersale/getTotalPrice.sc';
	url = convertURL(url);
	$('input[@type="checkbox"][@checked]').each(function(){
		var val = $(this).val();
	});
	var detailId = $('#detailId').val();
	var orderId = $('#orderId').val();
	$.ajax( {
		url :url,
		type :'POST',
		data :'detailId=' + detailId + '&orderId=' + orderId,
		dataType :'text',
		success : function(data) {
			$('#backedAmount').val(data);
		}
	});
}

//打开提示页面
function openInfoWin(message){
	//如果返回的结果message中有- 说明要刷新父页面
	ygdgDialog.open(BasePath+'/yitiansystem/ordergmt/ordersale/queryInfo.sc?message='+message,{width:700,height:240,title:'提示信息',cancel:function(){if(message.indexOf('-')>0){refreshpage();}}});
}

//挂起订单
function mountOrder(orderNo) {
	var flag = confirm("确定挂起吗?");
	if(flag) {
		$.post(BasePath+"/yitiansystem/ordergmt/ordersale/u_dismountOrder.sc?orderNo="+orderNo, 
		function(data){
			openInfoWin(data);
  		});
	}
}

//设置解挂
function umountOrder(orderNo) {
	 var flag = confirm("确定解挂吗?");
	 if(flag) {
	 	$.post(BasePath+"/yitiansystem/ordergmt/ordersale/u_umountOrder.sc?orderNo="+orderNo, 
	 		function(data){
	 			openInfoWin(data);
	 	});
   	 }
}

//标记为异常订单
function openException(orderNo){
	openwindow(BasePath+'/yitiansystem/ordergmt/orderflowdetail/c_toSetException.sc?orderNo='+orderNo,700,380,'标注异常');
}


//取消订单
function cancelOrder(orderNo, isException) {
	var html='<div id="mask" class="modify" style="border:none;">';
	html+='<table><tr><td style="text-align:right;width:70px;font-weight:bolder;line-height:50px;">取消原因：</td><td><input type="radio" name="reason" value="现在不想购"/>现在不想购&nbsp;&nbsp;&nbsp;<input type="radio" name="reason" value="更换或添加新商品"/>更换或添加新商品&nbsp;&nbsp;&nbsp;<input type="radio" name="reason" value="错误或重复下单"/>错误或重复下单&nbsp;&nbsp;&nbsp;';
	html+='<input type="radio" name="reason" value="等待时间过长"/>等待时间过长&nbsp;&nbsp;&nbsp;<input type="radio" name="reason" value="代购商"/>代购商&nbsp;&nbsp;&nbsp;<input type="radio" name="reason" value="经销商"/>经销商&nbsp;&nbsp;&nbsp;<input type="radio" name="reason" value="其他原因"/>其他原因&nbsp;&nbsp;&nbsp;';
	html+='</td></tr><tr><td style="text-align:right;font-weight:bolder;">备注：</td><td><textarea id="remark" rows="4" cols="40"></textarea></td></tr></table><br/><br/>';
	html+='<div style="width:100%;text-align:center;"><input id="remarkSub" type="button" onclick="" class="btn-add-normal-4ft" value="确定"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" onclick="closewindow()" class="btn-add-normal-4ft" value="取消"/></div></div>';
	//弹出备注层
	ygdg.dialog({content:html,title:'审核意见',lock:true,width:500,height:200});
	$("#remarkSub").click(function(){
		var remarkstr=$("#remark").val();
		var reason=$('input[name="reason"]:checked').val();
		if(reason==undefined||(reason=="")){
			ygdg.dialog.alert("请选择取消原因！");
		}else{
			//点击确认按钮操作
			$.post(BasePath+"/yitiansystem/ordergmt/orderflowdetail/u_checkOrderExp.sc",{orderNo:orderNo,reason:reason,remark:remarkstr},function(data){
		    	if(data=='请退款') {
		    		//填写退款申请单
		    		//openwindow('c_applyRefundUI.sc?orderNo='+orderNo,750,600,'填写退款申请单');
		    		ygdg.dialog.alert("取消成功，请填写退款申请单！");
		    		window.location.reload();
		    	}else {
		    		ygdg.dialog.alert(data);
		    		window.location.reload();
		    	}
			});
		}
	});
}


//进入客服取消订单
function toCancelSpecialOrder(orderNo) {
	openwindow(BasePath+'/yitiansystem/ordergmt/orderflowdetail/toCancelSpecial.sc?orderNo='+orderNo,700,285,'客服取消');
}

//客服取消订单
function cancelSpecialOrder(orderNo) {
	ygdg.dialog.confirm("确定取消吗?",function(){
		//点击确认按钮操作
		$.post(BasePath+"/yitiansystem/ordergmt/orderflowdetail/u_cancelSpecial.sc?orderNo="+orderNo, function(data){
    		openInfoWin(data);
		});
	});
}

//退款申请
function refundApply(orderId) {
	openwindow(BasePath+'/yitiansystem/ordergmt/ordersale/queryRefundDetail.sc?orderId='+orderId,750,600,'退款申请');
}

//给ajax请求加上时间戳
function convertURL(url) {
	var timestamp = (new Date()).valueOf();
	if (url.indexOf("?") >= 0) {
		url = url + "&t=" + timestamp;
	} else {
		url = url + "?t=" + timestamp;
	}
	return url;
};

//审核订单
function review(){
	document.queryForm.action=BasePath+"/yitiansystem/ordergmt/orderdetail/u_reviewOrder.sc";
	document.queryForm.method="POST";
	document.queryForm.submit();
}

//拒绝订单
function reviewNot(){
	ygdg.dialog.confirm("确定拒绝吗?",function(){
		document.queryForm.action=BasePath+"/yitiansystem/ordergmt/orderdetail/u_reviewNotOrder.sc";
		document.queryForm.method="POST";
		document.queryForm.submit();
	});
}

//订单详情到基本信息页
function base(){
	$("#orderid").val();
	$("#orderNo").val();
	document.queryForm.action=BasePath+"/yitiansystem/ordergmt/orderdetail/toBaseDetail.sc";
	document.queryForm.method="POST";
	document.queryForm.submit();
}

//订单详情到商品信息页
function commodity(){
	$("#orderid").val();
	$("#orderNo").val();
	$("#consigneeUserName").val();
	document.queryForm.action=BasePath+"/yitiansystem/ordergmt/orderdetail/queryGoodsInfo.sc";
	document.queryForm.method="POST";
	document.queryForm.submit();
}

//订单详情到顾客留言页
function message(){
	$("#orderid").val();
	$("#orderNo").val();
	$("#consigneeUserName").val();
	$("#flag").val();
	document.queryForm.action=BasePath+"/yitiansystem/ordergmt/orderdetail/queryMessage.sc";
	document.queryForm.method="POST";
	document.queryForm.submit();
}

//订单详情到订单备注页
function remark(){
	$("#orderid").val();
	$("#consigneeUserName").val();
	$("#orderNo").val();
	document.queryForm.action=BasePath+"/yitiansystem/ordergmt/orderdetail/queryRemark.sc";
	document.queryForm.method="POST";
	document.queryForm.submit();
}

//订单详情中查看异常备注
function unusualRemark(){
	$("#orderid").val();
	$("#orderNo").val();
	$("#consigneeUserName").val();
	document.queryForm.action=BasePath+"/yitiansystem/ordergmt/orderdetail/queryExceptionRmk.sc";
	document.queryForm.method="POST";
	document.queryForm.submit();
}

//订单详情中查看退款申请日志
function refund(){
	$("#orderid").val();
	$("#orderNo").val();
	document.queryForm.action=BasePath+"/yitiansystem/ordergmt/orderdetail/queryRefundLog.sc";
	document.queryForm.method="POST";
	document.queryForm.submit();
}

//订单详情中查询订单日志
function orderLog(){
	$("#orderid").val();
	$("#orderNo").val();
	$("#consigneeUserName").val();
	document.queryForm.action=BasePath+"/yitiansystem/ordergmt/orderdetail/queryOrderLog.sc";
	document.queryForm.method="POST";
	document.queryForm.submit();
}

//查看订单售后日志
function orderSale(){
	$("#orderid").val();
	$("#orderNo").val();
	document.queryForm.action=BasePath+"/yitiansystem/ordergmt/orderdetail/queryOrderSaleLog.sc";
	document.queryForm.method="POST";
	document.queryForm.submit();
}

//订单详情中查看订单优惠卷
function orderDiscount(){
	$("#orderid").val();
	$("#orderNo").val();
	$("#consigneeUserName").val();
	document.queryForm.action=BasePath+"/yitiansystem/ordergmt/orderdetail/queryDisscount.sc";
	document.queryForm.method="POST";
	document.queryForm.submit();
}

//订单详情中查看订单历史
function orderHistory(){
	$("#orderid").val();
	$("#orderNo").val();
	$("#memberNo").val();
	$("#consigneeUserName").val();
	document.queryForm.action=BasePath+"/yitiansystem/ordergmt/orderdetail/queryOrderHistory.sc";
	document.queryForm.method="POST";
	document.queryForm.submit();
}

//订单详情中查看优惠方案
function privilege(){
	$("#orderid").val();
	$("#orderNo").val();
	$("#consigneeUserName").val();
	document.queryForm.action=BasePath+"/yitiansystem/ordergmt/orderdetail/queryFavorableInfo.sc";
	document.queryForm.method="POST";
	document.queryForm.submit();
}

//订单详情中查看发退货记录
function output(){
	$("#orderid").val();
	$("#orderNo").val();
	$("#consigneeUserName").val();
	document.queryForm.action=BasePath+"/yitiansystem/ordergmt/orderdetail/queryInOutGoodsInfo.sc";
	document.queryForm.method="POST";
	document.queryForm.submit();
}

//订单详情中查看售后质检
function salecheck(){
	$("#orderid").val();
	$("#orderNo").val();
	$("#consigneeUserName").val();
	document.queryForm.action=BasePath+"/yitiansystem/ordergmt/orderdetail/querySaleCheck.sc";
	document.queryForm.method="POST";
	document.queryForm.submit();
}

//留言回复
function fun(){
	var url = BasePath+"/yitiansystem/ordergmt/orderdetail/c_messageRevert.sc";
	url = convertURL(url);
	$.ajax( {
		type: "POST",
 		url :url,
 		data:$("#queryForm").serialize(),
		cache: false,
 		success : function(data) {
	 		alert(data);
 		}
 	    
 	});
}
 
//作废和激活优惠卷
 function updateDisscount(couponNo, type){
 	var url = BasePath+"/yitiansystem/ordergmt/orderdetail/u_disscount.sc";
 	var param = "couponNo="+couponNo+"&type="+type;
 	url = convertURL(url);
 	$.ajax( {
 		type: "POST",
  		url :url,
  		data:param,
 		cache: false,
  		success : function(data) {
 			alert(data);
 			document.queryForm.submit();
  		}
  	});
}
 
//表单提交验证
function checkQueryForm() {
	return true;
}

//表单提交验证
function checkMainQueryForm() {
	var pattern = /^\w+$/;
	var mainNo = $("#mainNo").val();
	if (mainNo.replace(/\s/g,"")!="") {
		if(!pattern.exec(mainNo)) {
			alert('订单号输入错误');
			return false;
		}
	}
	return true;
}


//订单导出   导出报表类型 1,导出订单 2，导出订单（含成本价） 3，地址导出 4，仓库打回订单导出
function doExportOrder(url) {
	document.queryVoform.action=BasePath+'/yitiansystem/ordergmt/orderflow/doExportOrder.sc';
	document.queryVoform.method="post";
	document.getElementById("reportType").value="1";
	document.queryVoform.submit();
	document.queryVoform.action=url;
}

//订单导出 缺货订单
function doExportOrderForExceptionOrder(url) {
	document.queryVoform.action=BasePath+'/yitiansystem/ordergmt/orderflow/doExportOrderForExceptionOrder.sc';
	document.queryVoform.method="post";
	document.queryVoform.submit();
	document.queryVoform.action=url;
}

//导出发票报表
function doExportOrderInvoice(url) {
	document.queryVoform.action=BasePath+'/yitiansystem/ordergmt/orderflow/doExportOrderInvoice.sc';
	document.queryVoform.method="post";
	document.queryVoform.submit();
	document.queryVoform.action=url;
}

//导出交易
function doExportTrade(url) {
	document.queryVoform.action=BasePath+'/yitiansystem/ordergmt/ordertrade/doExportTrade.sc';
	document.queryVoform.method="post";
	document.queryVoform.submit();
	document.queryVoform.action=url;
}

//选择仓库
function chooseHouse(orderNo) {
	openwindow(BasePath+'/yitiansystem/ordergmt/orderflowdetail/queryHouse.sc?orderNo='+orderNo,700,400,'选择仓库');
}

//选择物流
function chooseLogictis(orderNo) {
	openwindow(BasePath+'/yitiansystem/ordergmt/orderchooselogictis/queryLogictis.sc?orderNo='+orderNo,700,400,'选择物流公司');
}

//查询详情
function fromSaletoOrderDetail(orderId){
	openwindow(BasePath+'/yitiansystem/ordergmt/orderdetail/toBaseDetail.sc?flag=1&orderid='+orderId+'','','','订单详情');
}

//查询详情
function fromSaletoOrder(orderNo){
	openwindow(BasePath+'/yitiansystem/ordergmt/orderdetail/toBaseDetailFromSale.sc?flag=1&orderNo='+orderNo+'','','','订单详情');
}

//修改售后单类型
function updateSaleType(saleId, saleType){
	var url = BasePath+"/yitiansystem/ordergmt/saleApplyBill/u_saleType.sc";
	var param = "saleId="+saleId+"&saleType="+saleType;
	url = convertURL(url);
	$.ajax( {
		type: "POST",
 		url :url,
 		data:param,
		cache: false,
 		success : function(data) {
			alert(data);
			window.location.reload();
 		}
 	    
 	});
}

//修改支付
function updatePayment(orderNo, isSign) {
	ygdg.dialog.confirm("确定修改吗?",function(){
		var url = BasePath+"/yitiansystem/ordergmt/orderflowdetail/u_payment.sc";
		var param = "orderNo="+orderNo+"&isSign="+isSign;
		url = convertURL(url);
		$.ajax( {
			type: "POST",
	 		url :url,
	 		data:param,
			cache: false,
	 		success : function(data) {
				ygdg.dialog.alert(data);
				window.location.reload();
	 		}
	 	});
	});
}

//修改
function updateOrderStatus(orderId, status, updateType) {
	var flag = confirm("确定修改吗?");
	if (flag) {
		if (updateType == '1') {
			status = $('#baseStatus' + orderId).val();
		} else if (updateType == '2') {
			status = $('#payStatus' + orderId).val();
		} else if (updateType == '3') {
			status = $('#deliveryStatus' + orderId).val();
		} else if (updateType == '4') {
			status = $('#prodStatus' + orderId).val();
		} else if (updateType == '5') {
			status = $('#isSync' + orderId).val();
		} else {
			status = '';
		}
		if (status.length == 0) {
			alert("请选中要修改的状态");
			return;
		}
		var url = BasePath+"/yitiansystem/ordergmt/orderbaseset/u_order_status.sc";
		var param = "orderId="+orderId+"&status="+status+"&updateType="+updateType;
		$.ajax( {
			type: "POST",
	 		url :url,
	 		data:param,
			cache: false,
	 		success : function(data) {
				alert(data);
				$("#queryVoform").submit();
	 		}
	 	});
	}
}

//取消
function cancelSingleOrder_isNotUse(orderNo, isFlag) {
	if (confirm("确定取消吗?")) {
		$.post(BasePath+"/yitiansystem/ordergmt/orderconfirm/u_cancel_order.sc?orderNo="+orderNo +"&isFlag="+isFlag, 
			function(data){
				ygdg.dialog.alert(data);
				window.location.reload();
		});
	}
}

//审核
function confirmSingleOrder(orderNo, isFlag) {
	ygdg.dialog.confirm("确定审核吗?",function(){
		$.post(BasePath+"/yitiansystem/ordergmt/orderconfirm/u_confirm_order.sc?orderNo="+orderNo+"&isFlag="+isFlag, 
			function(data){
				ygdg.dialog.alert(data);
				window.location.reload();
		});
	});
}

//置疑订单
function dubiousSingleOrder_isNotUse(orderNo, isFlag) {
	if (confirm("确定置疑吗?")) {
		$.post(BasePath+"/yitiansystem/ordergmt/orderconfirm/u_dubious_order.sc?orderNo="+orderNo+"&isFlag="+isFlag, 
			function(data){
				ygdg.dialog.alert(data);
				window.location.reload();
			});
		}
}

//cookie排序
function cookieOrderBy(clientSign) {
	document.queryVoform.action=BasePath+"/yitiansystem/ordergmt/orderflow/queryOrderConfirm.sc";
	var newMethod = "";
	
	if ($('#orderMethod').val()=="asc"){
		newMethod = "desc";
	}
	
	if ($('#orderMethod').val()=="" || $('#orderMethod').val() == "desc") {
		newMethod = "asc";
	}
	
	document.getElementById("orderBy").value=clientSign;
	document.getElementById("orderMethod").value=newMethod;
	document.queryVoform.method="post";
	document.queryVoform.submit();
}

//淘宝退款申请
function openTaoBaoRefund(orderNo) {
	openwindow(BasePath+'/yitiansystem/ordergmt/ordertabobaorefund/queryTaoBaoRefund.sc?orderNo='+orderNo,700,500,'淘宝退款申请');
}

//审核订单
function validOrder(orderNo,orderDetailIds,cancelType) {
	$.post(BasePath+"/yitiansystem/ordergmt/orderflowdetail/u_valid_order.sc", 
			{orderNo:orderNo,orderDetailIds:orderDetailIds,cancelType:cancelType},
			function(data){
				if(data=="success"){
					ygdg.dialog.alert("订单审核成功！");
					setTimeout(function(){window.location.reload();},2000);
				}else{
					try{
						if(data==''){
							ygdg.dialog.alert("订单审核失败！");
						}else{
							ygdg.dialog.alert(data);
						}
					}catch(e){
						ygdg.dialog.alert("订单审核失败！");
					}
				}
	});
}

//设置正常
function setnormal(orderNo,orderDetailIds,cancelType) {
	$.post(BasePath+"/yitiansystem/ordergmt/orderflowdetail/u_setNormalOrder.sc", 
			{orderNo:orderNo,orderDetailIds:orderDetailIds,cancelType:cancelType},
			function(data){
				if(data=="success"){
					ygdg.dialog.alert("订单置为正常成功！");
					setTimeout(function(){window.location.reload();},2000);
				}else{
					try{
						if(data==''){
							ygdg.dialog.alert("订单置为正常失败！");
						}else{
							ygdg.dialog.alert(data);
						}
					}catch(e){
						ygdg.dialog.alert("订单置为正常失败！");
					}
				}
	});
}

//恢复订单
function regainCancelOrder(orderNo) {
	ygdg.dialog.confirm("确定恢复吗?",function(){
		$.post(BasePath+"/yitiansystem/ordergmt/orderflowdetail/regainCancelOrder.sc", 
			{orderNo:orderNo},
			function(data){
				if(data=="success"){
					ygdg.dialog.alert("订单恢复成功！");
					setTimeout(function(){window.location.reload();},2000);
				}else{
					try{
						if(data==''){
							ygdg.dialog.alert("订单恢复失败！");
						}else{
							ygdg.dialog.alert(data);
							setTimeout(function(){window.location.reload();},2000);
						}
					}catch(e){
						ygdg.dialog.alert("订单恢复失败！");
					}
				}
		});
	});
}

//申请拦截
function applyIntercept(orderId){
	openwindow(BasePath+'/yitiansystem/ordergmt/orderflowdetail/toApplyIntercept.sc?orderId='+orderId,'480','300','申请拦截订单');
}

//已挂起订单作废并释放库存
function invalidOrder(orderId){
	if (confirm("确定要作废该订单吗?")) {
		$.post(BasePath+'/yitiansystem/ordergmt/orderflowdetail/invalidOrder.sc?orderId='+orderId, 
			function(data){
				ygdg.dialog.alert(data);
				window.location.reload();
			});
	}
}

//解锁退出
function closeWin(orderNo) {
	$.post(BasePath+"/yitiansystem/ordergmt/orderflowdetail/u_closeWin.sc?orderNo="+orderNo, 
	function(data){
		if(data=='1'){
			window.close();
		}
	});
}

//锁定订单
function lockOrder(orderNo) {
	$.post(BasePath+"/yitiansystem/ordergmt/orderflowdetail/u_lockOrderSub.sc?orderNo="+orderNo, 
		function(data){
		if(data=='1'){
			ygdg.dialog.alert("锁定成功!");
			$("#queryForm").submit();
		}else{
			ygdg.dialog.alert(data);
		}
	});
} 

//激活订单
function activeOrder(orderNo) {
	$.post(BasePath+"/yitiansystem/ordergmt/orderflowdetail/u_active.sc?orderNo="+orderNo, 
		function(data){
			ygdg.dialog.alert(data);
			if(data=='激活成功'){
				$("#jihuo").hide();
			}
			window.location.reload();
		});
} 

//发票信息详情
function invoiceDetail(invoiceid){
	document.queryForm.action=BasePath+"/yitiansystem/invoice/invoiceController/invoiceDetail.sc?invoiceid="+invoiceid;
	document.queryForm.method="POST";
	document.queryForm.submit();
}

//发票操作日志
function invoiceLog(invoiceid){
	document.queryForm.action=BasePath+"/yitiansystem/invoice/invoiceController/queryInvoiceLog.sc?invoiceid="+invoiceid;
	document.queryForm.method="POST";
	document.queryForm.submit();
}

//发票审核
function invoiceVerity(invoiceid){
	document.queryForm.action=BasePath+"/yitiansystem/invoice/invoiceController/toInvoiceVefity.sc?invoiceid="+invoiceid;
	document.queryForm.method="POST";
	document.queryForm.submit();
}






 
