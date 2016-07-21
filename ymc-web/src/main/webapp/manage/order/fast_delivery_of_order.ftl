<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>优购商家中心-单据打印（未打印）</title>
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
	<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
</head>
 
<body>
	<div id="Main" class="main_container" style="width:99.6%;">
		<div class="normal_box" style="float:left;width: 100%;">
			<div class="form_container" style="float:left;width: 96%;">
				<form name="querFrom" id="querFrom" action="${BasePath}/order/fastdelivery/dispatched.sc" method="post" style="padding:0px;margin:0px;float:left;width:100%;">
					<input type="hidden" name="orderSubNo" id="orderSubNo" value="${orderSub.orderSubNo}"/>
					<input type="hidden" id="expressInfoStr" name="expressInfoStr" value="${(orderSub.orderConsigneeInfo.userName)!''}${(orderSub.orderConsigneeInfo.mobilePhone)!''}${(orderSub.orderConsigneeInfo.provinceName)!''}${(orderSub.orderConsigneeInfo.cityName)!''}${(orderSub.orderConsigneeInfo.areaName)!''}${(orderSub.orderConsigneeInfo.consigneeAddress)!''}" />
					<div style="font-size:14px;font-weight:bold;">收货地址</div>
				    <div style="padding: 15px 20px;border-bottom: 1px dashed grey;">
				    	${(orderSub.orderConsigneeInfo.provinceName)!''}－${(orderSub.orderConsigneeInfo.cityName)!''}－${(orderSub.orderConsigneeInfo.areaName)!''}－${(orderSub.orderConsigneeInfo.consigneeAddress)!''}
				    </div>
					<div style="font-size:14px; font-weight:bold;">选择快递
						<a style="float:right;font-size: 12px;" href="javascript:void(0);" 
						onclick="selectExpress();">常用快递</a>
					</div>
					<div style="padding:15px 20px; border-bottom: dashed 1px grey;float:left;width: 95.8%;" class="search_box logistics">
							<#if (resultFlag=="true")>
								<#list frequentUseCompanyList![] as item>
									<span style="margin: 0 0 5px;">
								  	<label style="width:120px;">${item.logistics_company_name!''}：</label>
								  	<span><input name='comExpressCode' id='${item.logistic_company_code}' type='text' class='ginput' style='width:150px;' /></span>
								  	<span><a class="button common" onclick="javascript:deliveringOrder('${item.logistic_company_code}');"><span>确认发货</span></a></span>
								  	</span>
								  	<#if !(item_has_next)>
								  		<a style="float:right;font-size: 12px;font-weight: bold;margin-right:-20px;" href="javascript:void(0);" 
										onclick="moreExpress();">更多快递</a>
								  	</#if>
								</#list>
							
							<#else>
								<#list logisticscompanList![] as item>
									<span style="margin: 0 0 5px;">
								  	<label for="${item.id}">${item.logistics_company_name!''}</label>
								  	<span><input id="${item.id}" style="height: 23px;" type="radio" name="logisticsCode" value="${item.logistic_company_code}"/></span>
								  	</span>
								</#list>
							</#if>
				    </div>
				    <#if (resultFlag=="true")>
				    	<#assign css="display:none;"/>
					</#if>
					
				    <div style="font-size: 14px; font-weight: bold; padding-top: 15px;${css!''}" class="noSetComm">录入快递单号</div>
				    <div style="padding:10px 20px;${css!''}" class="noSetComm">
				    	请录入快递单号：<input id="expressCode" name="expressCode" type="text" class="ginput" style="width:150px;"  onclick="orderIntercept('${orderSub.orderSubNo!''}')" />
				    </div>
					<div style="width:100%; text-align:center;padding-bottom:10px;${css!''}" class="noSetComm">
						<span><a id="courier" class="button" onclick="javascript:deliveringOfOrder()"><span>发货</span></a></span>
						<span style="padding-left:10px;"><a id="closer" class="button" onclick="javascript:closewindow();"><span>关闭</span></a></span>
					</div>
					
				</form>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
var flag=false;
function orderIntercept(orderSubNo) {
if(!flag){
		            $.ajax({
					url: "${BasePath}/order/fastdelivery/getOrderIntercept.sc?orderSubNo="+orderSubNo,
					type: "post",
					dataType: "json",
					async: false,
					success: function(data) {
						if ('success' == data.result) {
						if(data.flag){
					      ygdg.dialog({
					      id:'interceptDialog',
						  title:'订单申请拦截',
						  content:"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;订单号："+orderSubNo+"<br />申请拦截时间："+data.time+"<br />申请拦截备注："+data.reason+"<br /><br /><p><span style='color:#AAAAAA'>注：同意拦截则订单被置为挂起状态，请不要做发货处理。<br />&nbsp;&nbsp;&nbsp;&nbsp;关闭拦截申请，则可以继续做发货处理。</span></p>",
						  icon: 'warning',
						  button: [
						          {
						              name: '同意拦截',
						              callback: function () {
						            	  orderInterceptYes(orderSubNo);
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
						}
						} else if('intercepted_yes' == data.result||'intercepted_no' == data.result){
						}else {
							ygdg.dialog.alert('获取订单拦截信息失败.');
						}
					},
					error: function() {
						ygdg.dialog.alert("系统内部异常");
					}
				});
}

flag=true;
return true;
}

//商家同意拦截的动作
function orderInterceptYes(orderSubNo) {
        			$.ajax({
					url: "${BasePath}/order/fastdelivery/doOrderIntercept.sc?orderSubNo="+orderSubNo + "&flag=1",
					type: "post",
					dataType: "json",
					async: false,
					success: function(data) {
						if ('success' == data.result) {
							ygdg.dialog.alert('同意拦截订单成功.');
						} else {
							ygdg.dialog.alert('同意拦截订单失败.');
						}
					},
					error: function() {
						ygdg.dialog.alert("系统内部异常");
					}
				});
				closewindow();
        }
       
//商家忽略订单的动作
function orderInterceptNo(orderSubNo) {
                  			$.ajax({
					url: "${BasePath}/order/fastdelivery/doOrderIntercept.sc?orderSubNo="+orderSubNo + "&flag=2",
					type: "post",
					dataType: "json",
					async: false,
					success: function(data) {
						if ('success' == data.result) {
							ygdg.dialog.alert('忽略订单成功.');
						} else {
							ygdg.dialog.alert('忽略订单失败.');
						}
					},
					error: function() {
						ygdg.dialog.alert("系统内部异常");
					}
				});
}

function deliveringOfOrder() {
	if ($(':radio:checked').size() <= 0) {
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
	var dg=null;
	$.ajax({
		url: submitform.attr('action'),
		type: 'post',
		data: submitform.serialize(),
		dataType: 'html',
		beforeSend: function(XMLHttpRequest){
			submitform.attr('state', 'running');
			dg=ygdg.dialog({
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
				closewindow();
			} else if($.trim(data) == 'warn'){
				dg.close();
			    ygdg.dialog.alert('您录入的快递单号已存在发货信息，请重新录入！');
			} else{
				ygdg.dialog.alert('订单发货失败!');
			}
		},
		complete: function(XMLHttpRequest, textStatus){
			submitform.attr('state', 'waiting');
			dg.close();
			//closewindow();
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			ygdg.dialog.alert('订单发货失败:' + XMLHttpRequest.responseText);
		}
	});
}

function checkWeight(weight){
	var reg = /^(([0-9]+[\.]?[0-9]{1,2})|[1-9])$/;
	return reg.test(weight);
}

function deliveringOrder(code){
	<#--  var isKorea = "${resultFlag!''}";
	var weight = "";
	if(isKorea == "isKorea"){
		weight = $.trim($("#commodityWeight").val());
		if(weight==''){
			ygdg.dialog.alert('请录入包裹重量!');
			return false;
		}
		
		if(!checkWeight(weight)){
			ygdg.dialog.alert('包裹重量必须是数字，保留2位小数!');
			return false;
		}
	} -->
	if ($.trim($('#'+code).val()) == '') {
		ygdg.dialog.alert('请录入快递单号!');
		return false;
	}
	if (!checkExpressNoValid($.trim($('#'+code).val()))) {
		ygdg.dialog.alert("请填写正确的快递单号！");
		return false;
	}
	var submitform = $('#querFrom');
	if (submitform.attr('state') == 'running') {
		return false;
	}
	$("input[name='comExpressCode']").filter(":not(#"+code+")").val("");
	$("a[class='common']").removeAttr('onclick');
	var dg=null;
	$.ajax({
		url: submitform.attr('action'),
		type: 'post',
		data: "orderSubNo="+$("#orderSubNo").val()+"&logisticsCode="+code+"&expressCode="+$.trim($('#'+code).val()),
		dataType: 'html',
		beforeSend: function(XMLHttpRequest){
			submitform.attr('state', 'running');
			dg=ygdg.dialog({
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
				closewindow();
			} else if($.trim(data) == 'warn'){
				dg.close();
				var aButtons = $("a[class='common']");
				$.each(aButtons,function(i,n){
					$(n).attr('onclick','javascript:deliveringOrder("'+$(n).parent().siblings("span").children("input").attr("id")+'");');
				});
			    ygdg.dialog.alert('您录入的快递单号已存在发货信息，请重新录入！');
			} else{
				var aButtons = $("a[class='common']");
				$.each(aButtons,function(i,n){
					$(n).attr('onclick','javascript:deliveringOrder("'+$(n).parent().siblings("span").children("input").attr("id")+'");');
				});
				ygdg.dialog.alert('订单发货失败!');
			}
		},
		complete: function(XMLHttpRequest, textStatus){
			submitform.attr('state', 'waiting');
			dg.close();
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			var aButtons = $("a[class='common']");
			$.each(aButtons,function(i,n){
				$(n).attr('onclick','javascript:deliveringOrder("'+$(n).parent().siblings("span").children("input").attr("id")+'");');
			});
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

var noSetComlositicsHtml = "";
//是否首尔直发订单
var isKorea = "${isKorea!''}";
var selectExpress = function(){
	ygdgDialog.open('${BasePath}/order/fastdelivery/selectExpress.sc?isKorea='+isKorea, {
		id:'selectExpress',
		width: 500,
		height: 600,
		title: '常用快递设置',
		close:function(){
			var datacode = $(this.iframe).contents().find("#list1 li").map(function() 
					{ return $(this).children().children(".code").html(); }).get();
			var dataname = $(this.iframe).contents().find("#list1 li").map(function() 
					{ return $(this).children("div").attr("title"); }).get();
			if(datacode.length>0&&dataname.length>0){
				noSetComlositicsHtml = $("div.logistics").html();
				$("div.logistics").html("");
				$.each(datacode, function(i, n){
					  $("div.logistics").append("<span style='margin: 0 0 5px;'>"+
					  	"<label style='width:120px;'>"+dataname[i]+"：</label>"+
					  	"<span><input name='comExpressCode' id='"+n+"' type='text' class='ginput' style='width:150px;' /></span>"+
					  	"<span><a class='button common' onclick='javascript:deliveringOrder(&apos;"+n+"&apos;);'><span>确认发货</span></a></span></span>");
					  if(i>=(datacode.length-1)){
						  $("div.logistics").append(
						  	"<a style='float:right;font-size: 12px;font-weight: bold;margin-right:-20px;' href='javascript:void(0);' "+ 
							"onclick='moreExpress();'>更多快递</a>");
					  }
				});
				$("div.noSetComm").hide();
				$.ajax({
					traditional:true,
					url:"${BasePath}/order/fastdelivery/addcomexpress.sc",
					data:{"codeArr":datacode,"nameArr":dataname},
					type:"post"});
			}else{
				moreExpress();
				$.ajax({
					traditional:true,
					url:"${BasePath}/order/fastdelivery/addcomexpress.sc",
					data:{"codeArr":datacode,"nameArr":dataname},
					type:"post"});
			}
		}
	});
};

var moreExpress = function(){
	if(noSetComlositicsHtml.indexOf("radio")!=-1){
		$("div.logistics").html(noSetComlositicsHtml);
	}else{
		noSetComlositicsHtml = "";
		$.post("${BasePath}/order/fastdelivery/moreExpress.sc?isKorea="+isKorea,function(json){
			$.each(json, function(i, item){
				noSetComlositicsHtml+="<span style='margin: 0 0 5px;'>"+
			  	"<label for='"+item.id+"'>"+item.logistics_company_name+"</label>"+
			  	"<span><input id='"+item.id+"' style='height: 23px;' type='radio' name='logisticsCode' value='"+item.logistic_company_code+"'/>"+
			  	"</span></span>";
			});
			$("div.logistics").html(noSetComlositicsHtml);
		},"json");
	}
	$("div.noSetComm").show();
};
</script>
</html>
