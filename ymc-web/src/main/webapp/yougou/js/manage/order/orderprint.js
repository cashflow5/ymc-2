order = {};
order.print = {};
/**
 * 显示订单详情
 */
order.print.toDetail = function(orderSubNo,menuCode){
	openwindow(order.print.BasePath + "/order/toOrderDetails.sc?orderSubNo="+orderSubNo+"&menuCode="+menuCode,1100,500,'订单详情');
};

/**
 * 批量打印购物清单
 */
/*
 * 删除不用的js方法
order.print.toBatchPrintShoppingListTemplate = function(){
	var orderSubNos="";
	var count = 0;
	$("#tbody input:checked").each(function(){
		orderSubNos+=$(this).attr('no')+","; 
		count++;
	});
	if(count==0){
		ygdg.dialog.alert("请选择要打印购物清单的订单！");
	}else{
		window.open(order.print.BasePath + "/order/toBatchPrintShoppingListTemplate.sc?orderNos="+orderSubNos);
	}
};
*/
/**
 * 批量打印快递单
 */
order.print.toBatchPrintExpressTemplate = function(orderSubNo){
	var orderSubNos="";
	var count=0;
	$("#tbody input:checked").each(function(){
		orderSubNos+=$(this).attr('no')+","; 
		count++;
	});
	if(count==0){
		ygdg.dialog.alert("请选择要打印快递单的订单！");
	}else{
		window.open(order.print.BasePath + "/order/toPrintExpressTemplateAjax.sc?orderNos="+orderSubNos);
	}
};

/**
 * 置为缺货确认框
 */
order.print.updateOutOfStockConfirm = function(orderSubNo,orderSubId){
	$.getJSON(order.print.BasePath + "/order/queryGoodsInfo.sc",{orderSubId:orderSubId},function(data){
		var tempArr = data.result;
		if(tempArr.length>1){
			var htmlContent = '';
			htmlContent += '<form id="queryForm" name="queryForm" action="' + order.print.BasePath + '/order/updateOutOfStock.sc" method="post">';
			htmlContent +=    '<div>注：请勾选订单中的缺货商品，订单商品标记缺货后，系统会进行拆单并流入商家系统重新发货。</div>';
			htmlContent +=    '<div style="height:200px;overflow:auto;">';
			htmlContent +=        '<div style="padding-bottom:10px;">&nbsp;<span id="sub_form_errorId" style="color:red"></span></div>';
			htmlContent +=        '<table class="list_detail_table">';
			htmlContent +=           '<thead>';
			htmlContent +=              '<tr>';
			htmlContent +=                '<th width="20"></th>';
			htmlContent +=                '<th width="100">商品款号</th>';
			htmlContent +=                '<th width="100">商品颜色</th>';
			htmlContent +=                '<th width="100">商品尺码</th>';
			htmlContent +=                '<th width="100">货品条码</th>';
			htmlContent +=             '</tr>';
			htmlContent +=          '</thead>';
			htmlContent +=          '<tbody id="sub_form_tbodyId">';
			$.each(tempArr,function(index,item){
				htmlContent +=             '<tr>';
				htmlContent +=                '<td><input type="checkbox"  value="'+item.id+'" /></td>';
				htmlContent +=                '<td>'+item.styleNo+'</td>';
				htmlContent +=                '<td>'+item.specName+'</td>';
				htmlContent +=                '<td>'+item.sizeName+'</td>';
				htmlContent +=                '<td>'+item.levelCode+'</td>';
				htmlContent +=             '</tr>';
			});
			htmlContent +=         '</tbody>';
			htmlContent +=      '</table>';
			htmlContent +=      '<div style="padding-top:10px;padding-left:30%;"><a class="button" onclick="order.print.updateOutOfStockCheck(\''+orderSubNo+'\',\''+orderSubId+'\');"><span>确定</span></a><a class="button" onclick="closewindow();"><span>取消</span></a></div>';
			htmlContent +=   '</div>';
			htmlContent += '</form>';
			openDiv({
				content:htmlContent,
				title: '选择缺货商品',
				width: 450
			});
		}else{
			ygdg.dialog.confirm("确定要将该订单置为缺货吗？", function() {
				order.print.updateOutOfStock(orderSubNo,orderSubId,tempArr[0].id);
			});
		}
	});	
};

/**
 * 置为缺货确认框检验
 */
order.print.updateOutOfStockCheck = function(orderSubNo,orderSubId){
	var orderDetail4subIds="";
	$("#sub_form_tbodyId input:checked").each(function(){
		orderDetail4subIds+=$(this).val()+","; 
	});
	if(orderDetail4subIds==""){
		$("#sub_form_errorId").text('请勾选缺货商品');
	}else{
		order.print.updateOutOfStock(orderSubNo,orderSubId,orderDetail4subIds);
	}
};


/**
 * 置为缺货
 */
order.print.updateOutOfStock = function(orderSubNo,orderSubId,orderDetail4subIds){
	if ($('.list_table').attr('state') == 'running') {
		return false;
	}
	$.ajax({
		type: 'post',
		url: order.print.BasePath + '/order/updateOutOfStock.sc',
		dataType: 'html',
		data: {orderSubNos:orderSubNo,orderSubId:orderSubId,orderDetail4subIds:orderDetail4subIds},
		beforeSend: function(XMLHttpRequest) {
			ygdg.dialog({
				id: 'submitDialog',
				title: '提示', 
				content: '请稍候，正在将订单置为缺货中...', 
				lock: true, 
				closable: false
			});
			$('.list_table').attr('state', 'running');
		},
		success: function(data, textStatus) {
			if (data == 'success') {
				ygdg.dialog.alert("置为缺货成功!");
				$("#queryVoform").submit();
			}else if(data == 'noOrderSubNos'){
				ygdg.dialog.alert("订单不存在!");
			}else if(data == 'errorStatus'){
				ygdg.dialog.alert("该订单目前状态不能置为缺货!");
			}else{
				ygdg.dialog.alert("置为缺货失败!");
			}
		},
		complete: function(XMLHttpRequest, textStatus) {
			window.setTimeout('closewindow();', 2000);
			$('.list_table').attr('state', 'waiting');
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert("置为缺货失败数据：" + XMLHttpRequest.responseText);
			$("#queryVoform").submit();
		}
	});
};

/**
 * 导出订单
 */
order.print.doExportCommon = function(exportMethodName,documentPrintMethodName){
	var count = 0;	
	var orderSubIds="";
	var orderSubNos="";
	$("#tbody input:checked").each(function(){
		orderSubIds+=$(this).val()+","; 
		orderSubNos+=$(this).attr("no")+","; 
		count++;
	});
	if(count==0){
		ygdg.dialog.alert("请选择订单！");
	}else{
		var url = "";
		if(exportMethodName.indexOf("?") > 0){
			url += exportMethodName+"&orderSubId="+orderSubIds+"&&orderSubNos="+orderSubNos;
		}else{
			url += exportMethodName+"?orderSubId="+orderSubIds+"&&orderSubNos="+orderSubNos;
		}
		$("#queryVoform").attr("action",url);
		$("#queryVoform").submit();
		//重载当前页面
//		setInterval(function(){
//			$("#queryVoform").attr("action",documentPrintMethodName);
//			$("#queryVoform").submit();
//		}, 3000);
	}
};

/**
 * 导出拣货清单Old
 */
order.print.doExportOrderOld = function(url){
	order.print.doExportCommon(order.print.BasePath + "/order/doExportPrintOrder.sc", order.print.BasePath + url);
};

/**
 * 导出拣货清单
 */
order.print.doExportOrder = function(url){
	order.print.doExportCommon(order.print.BasePath + "/order/doExportPrintOrderNew.sc", order.print.BasePath + url);
};

/**
 * 导出缺货订单Old
 */
order.print.doExportOutStockOrderOld = function(){
	order.print.doExportCommon(order.print.BasePath + "/order/doExportOutStockOrder.sc?menuCode=MENU_DJDY", order.print.BasePath + "/order/toPrintOutstock.sc");
};

/**
 * 导出缺货订单
 */
order.print.doExportOutStockOrder = function(){
	order.print.doExportCommon(order.print.BasePath + "/order/doExportOutStockOrder.sc?menuCode=MENU_DJDYX", order.print.BasePath + "/order/toPrintNewOutstock.sc");
};

/**
 * 导出违规订单 
 */
order.print.doExportPunishOrder = function(){
	order.print.doExportCommon(order.print.BasePath + "/order/doExportPunishOrder.sc", order.print.BasePath + "/order/queryPunishOrderList.sc");
};

/**
 * 快捷发货
 * @param orderSubNo
 */
order.delivering = function(orderSubNo) {
	ygdgDialog.open(order.print.BasePath + '/order/fastdelivery/delivering.sc?orderSubNo=' + orderSubNo, { width: 1000, height: 500, lock: true, title: orderSubNo + ' 发货', close: function(){ refreshpage(); } });
};


//合并发货
order.mergerCargo = function mergerCargo(){
	var orderSubNos="";
	var orderStrArray = new Array();
	$("#tbody input:checked").each(function(){
		var no = $(this).attr('no');
		orderSubNos+=no+",";
		var userName = $("#userName_"+no).text();
		var consigneeMobile = $("#consigneeMobile_"+no).text();
		var area = $("#area_"+no).text();
		var consigneeAddress = $("#consigneeAddress_"+no).text();
		var strTemp = $.trim(userName+consigneeMobile+area+consigneeAddress);
		orderStrArray.push(strTemp);
	});
	
	if(orderSubNos =="" ){
		alert("请选择需要合单发货的订单。");
		return false;
	}
	
	if(orderStrArray.length < 2){
		alert("请至少选择2个订单进行合并发货。");
		return false;
	}
	
	
	if(orderStrArray.deleteEleDis().length >1){
		alert("您勾选的订单发货信息不一致，无法合并发货，请重新选择。");
		return false;
	};
	
	ygdgDialog.open(order.print.BasePath+'/order/fastdelivery/mergerDelivering.sc?orderSubNos=' + orderSubNos, { width: 1000, height: 500, lock: true, title: '订单发货', close: function(){ refreshpage(); } });
};

//删除多余项
Array.prototype.deleteEleDis = function () {
    var a = [], b = [];
    for (var prop in this) {
        var d = this[prop];
        if (d === a[prop]) continue; //防止循环到prototype
        if (b[d] != 1) {
            a.push(d);
            b[d] = 1;
        }
    }
    return a;
};

//优购客服申请拦截订单
order.intercept=function (orderSubNo) {
            $.ajax({
			url: order.print.BasePath+"/order/fastdelivery/getOrderIntercept.sc?orderSubNo="+orderSubNo,
			type: "post",
			dataType: "json",
			async: false,
			success: function(data) {
				if ('success' == data.result) {
					ygdg.dialog({
						id:'interceptDialog',
						title:'订单申请拦截',
						content:"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;订单号："+orderSubNo+"<br />申请拦截时间："+data.time+"<br />申请拦截备注："+data.reason+"<br /><br /><p><span style='color:#AAAAAA'>注：同意拦截则订单被置为挂起状态，请不要做发货处理。<br />&nbsp;&nbsp;&nbsp;&nbsp;关闭拦截申请，则可以继续做发货处理。</span></p>",
						icon: 'warning',
						button: [
						          {
						              name: '同意拦截',
						              callback: function () {
						            	  order.interceptYes(orderSubNo);
						              },
						              focus: true
						          },
						          {
						              name: '关闭',
						              callback: function () {
						            	  ygdg.dialog.list['interceptDialog'].close();
						              }
						          }
						      ]
					});
				//ygdg.dialog.confirm.orderintercept("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;订单号："+orderSubNo+"<br />申请拦截时间："+data.time+"<br />申请拦截备注："+data.reason+"<br /><br /><p><span style='color:#AAAAAA'>注：同意拦截则订单被置为挂起状态，请不要做发货处理。<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;不同意拦截则该订单状态不变，可继续做发货处理。</span></p>",function(){order.interceptYes(orderSubNo);},function(){order.interceptNo(orderSubNo);});
				} else if('intercepted_yes' == data.result){
					ygdg.dialog.alert('该拦截申请已经被同意，请不要重复点击！');
				} else if('intercepted_no' == data.result){
					ygdg.dialog.alert('商家已经忽略该拦截申请，请不要重复点击！');
				}else {
					ygdg.dialog.alert('获取订单拦截信息失败.');
				}
			},
			error: function() {
				ygdg.dialog.alert("系统内部异常");
			}
		});
   return;
};
//商家同意拦截的动作
order.interceptYes=function (orderSubNo) {
			$.ajax({
			url: order.print.BasePath+"/order/fastdelivery/doOrderIntercept.sc?orderSubNo="+orderSubNo + "&flag=1",
			type: "post",
			dataType: "json",
			async: false,
			success: function(data) {
				if ('success' == data.result) {
					ygdg.dialog.alert('同意拦截订单成功.');
					$("#queryVoform").submit();
				} else {
					ygdg.dialog.alert('同意拦截订单失败.');
				}
			},
			error: function() {
				ygdg.dialog.alert("系统内部异常");
			}
		});
		closewindow();
};

//商家忽略拦截的动作
order.interceptNo=function (orderSubNo) {
          			$.ajax({
			url: order.print.BasePath+"/order/fastdelivery/doOrderIntercept.sc?orderSubNo="+orderSubNo + "&flag=2",
			type: "post",
			dataType: "json",
			async: false,
			success: function(data) {
				if ('success' == data.result) {
					ygdg.dialog.alert('忽略拦截订单成功.');
				} else {
					ygdg.dialog.alert('忽略拦截订单失败.');
				}
			},
			error: function() {
				ygdg.dialog.alert("系统内部异常");
			}
		});
  closewindow();
};



//计算天数差的函数，通用  
order.dateDiff = function (sDate1,  sDate2){    //sDate1和sDate2是2002-12-18格式  
	 var  aDate,  oDate1,  oDate2,  iDays; 
     aDate  =  sDate1.split("-");
     oDate1 = new Date();
     oDate1.setUTCFullYear(aDate[0], aDate[1] - 1, aDate[2]);
     oDate1.setUTCHours(0, 0, 0, 0);
     //oDate1  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0]);    //转换为12-18-2002格式  
     aDate  =  sDate2.split("-");
     oDate2 = new Date();
     oDate2.setUTCFullYear(aDate[0], aDate[1] - 1, aDate[2]);
     oDate2.setUTCHours(0, 0, 0, 0);
     //oDate2  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0]);  
     iDays  =  parseInt(Math.abs(oDate1  -  oDate2)  /  1000  /  60  /  60  /24);   //把相差的毫秒数转换为天数  
     return  iDays;
};

order.checkDate = function (){
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
//	if(!startTime && endTime){
//		alert("当下单结束时间不为空，下单开始时间也不能为空！");
//		return false;
//	}
//	if(startTime && !endTime){
//		alert("当下单开始时间不为空，下单结束时间也不能为空！");
//		return false;
//	}
	var day = order.dateDiff(endTime,startTime);
	if(day >= 30){
		alert("下单时间隔必须小于等于30天");
		return false;
	}
	return true;
};