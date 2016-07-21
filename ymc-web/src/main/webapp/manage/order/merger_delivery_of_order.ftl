<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>优购商家中心-合并发货</title>
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
	<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
</head>
<body>
	<div id="Main" class="main_container">
		<div class="normal_box">
			<div class="form_container">
				<form name="querFrom" id="querFrom" action="${BasePath}/order/fastdelivery/mergerDispatched.sc" method="post" style="padding:0px;margin:0px;">
					<input type="hidden" id="orderSubNos" name="orderSubNos" value="${orderSubNos!'' }" />
					<input type="hidden" id="expressInfoStr" name="expressInfoStr" value="${(orderSub.orderConsigneeInfo.userName)!''}${(orderSub.orderConsigneeInfo.mobilePhone)!''}${(orderSub.orderConsigneeInfo.provinceName)!''}${(orderSub.orderConsigneeInfo.cityName)!''}${(orderSub.orderConsigneeInfo.areaName)!''}${(orderSub.orderConsigneeInfo.consigneeAddress)!''}" />
					<div style="font-size:14px;font-weight:bold;">收货人信息</div>
				    <div style="padding: 15px 20px;">
				    	收货人名称：${(orderSub.orderConsigneeInfo.userName)!''}
				    	<br/>收货人地址：${(orderSub.orderConsigneeInfo.provinceName)!''}－${(orderSub.orderConsigneeInfo.cityName)!''}－${(orderSub.orderConsigneeInfo.areaName)!''}－${(orderSub.orderConsigneeInfo.consigneeAddress)!''}
				    	<br/>订单号：<#if (orderSubNos)??>${orderSubNos?substring(0,orderSubNos?length-1)}</#if>
				    </div>
				    							    
				    <div style="font-size: 14px; font-weight: bold; padding-top: 15px;">选择物流信息</div>
					    <div class="clearfix" style="padding:10px 20px;">
					    	<span class="fl">
					    	请选择物流公司：
					    	</span>
					    	<select name="logisticsCode" id="logisticsCode"   >
					    				<option value=""  >请选择</option>
		    							<#list logisticscompanList![] as item>
		    			   					<option value="${item.logistic_company_code}" >${item.logistics_company_name!''}</option>
		    			   				</#list>
		    			   	</select>
					    </div>
					    	
					    <div style="padding:10px 20px;">
					    	请录入快递单号：<input id="expressCode" name="expressCode" type="text" class="ginput" style="width:150px;"  />
					    </div>
	
						<div style="width:100%; text-align:center;padding-bottom:10px;">
							<span><a id="courier" class="button" onclick="javascript:deliveringOfOrder()"><span>发货</span></a></span>
							<span style="padding-left:10px;"><a id="closer" class="button" onclick="javascript:closewindow();"><span>关闭</span></a></span>
						</div>

				</form>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">

function checkWeight(weight){
	var reg = /^(([0-9]+[\.]?[0-9]{1,2})|[1-9])$/;
	return reg.test(weight);
}

function deliveringOfOrder() {
	//alert($("#logisticsCode").val());
	
	if ($("#logisticsCode").val() == '') {
		ygdg.dialog.alert('请选择物流公司!');
		return false;
	}
	if ($.trim($('#expressCode').val()) == '') {
		ygdg.dialog.alert('请录入快递单号!');
		return false;
	}
	
	if (!checkExpressNoValid($.trim($('#expressCode').val()))) {
		ygdg.dialog.alert("请填写正确的快递单号！");
		return false;
	}
	
	var submitform = $('#querFrom');
	if (submitform.attr('state') == 'running') {
		return false;
	}
	
	$.ajax({
		url: submitform.attr('action'),
		method: 'post',
		data: submitform.serialize(),
		dataType: 'html',
		beforeSend: function(XMLHttpRequest){
			submitform.attr('state', 'running');
			ygdg.dialog({
				id: 'submitDialog',
				title: '提示', 
				content: '请稍候，订单正在发货中...', 
				lock: true, 
				closable: false
			});
		},
		success: function(data, textStatus){
			if ($.trim(data) == 'true') {
				ygdg.dialog.alert('订单发货成功!');
			} else {
				ygdg.dialog.alert(data+' 订单发货失败!');
			}
		},
		complete: function(XMLHttpRequest, textStatus){
			submitform.attr('state', 'waiting');
			closewindow();
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			ygdg.dialog.alert('订单发货失败:' + XMLHttpRequest.responseText);
		}
	});
}

//判断快递单号是否含有非法字符（允许数字、字母（大小写均可）、*、_）
//快递单号含有非法字符返回false、反之为true
function checkExpressNoValid(expressNo) {
	var regex = new RegExp('[^\\w\*]{1,}', 'gi');
	return !regex.test(expressNo);
}

$("#expressCode").blur(function(){ 
	var expressCode = $(this).val();
	var expressInfoStr = $("#expressInfoStr").val();
	if(expressCode){
		$.ajax({
			url: '${BasePath}/order/fastdelivery/checkExpressCode.sc',
			method: 'post',
			data:{
				"expressCode":expressCode,
				"expressInfoStr":expressInfoStr
			},
			success: function(data, textStatus){
				if ($.trim(data) == 'true') {
					if(!confirm('您录入的快递单号已存在，确定要录入吗？')){
						$("#expressCode").val('');
					}
				} 
			},
			complete: function(XMLHttpRequest, textStatus){
			},
			error: function(XMLHttpRequest, textStatus, errorThrown){
				ygdg.dialog.alert('订单发货失败:' + XMLHttpRequest.responseText);
			}
		});
	}

});

</script>
</html>
