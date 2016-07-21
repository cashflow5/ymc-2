<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-打包发货</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
</head>

<body>
	
	
	<div class="main_container">
		<div class="normal_box">
			<p class="title site">当前位置：商家中心 &gt; 订单 &gt; 打包发货</p>
			<div class="form_container">
				<form action="" method="post" id="myForm" onsubmit="return;">
					<ul class="sendStatuBar" id="statuBar">
						<li class="curr">1、扫描发货订单号</li>
						<li>2、校验发货商品</li>
						<li>3、校验发货快递单</li>
					</ul>
					<p class="blank20"></p>
					<!--step1-->
					<div id="step1" style="display:block;"> 请扫描发货订单号：<input type="text" class="ginput"  style="width:150px;" id="orderNo" value="" /></div>
					<!--step2-->
					<div id="step2" class="none">
						<fieldset class="x-fieldset x-fieldset-default">
							<legend  class="x-fieldset-header">
							<a  class="x-tool-toggle" ></a>
							<span class="x-fieldset-header-text">订单信息</span>
							<div class="x-clear"></div>
							</legend>
							<div  class="x-fieldset-body">
								<div id="scanningOrderInfo" class="detail_box normal">
								</div>
							</div>
						</fieldset>
						<p class="blank20"></p>
						<fieldset class="x-fieldset x-fieldset-default">
							<legend  class="x-fieldset-header">
							<a  class="x-tool-toggle" ></a>
							<span class="x-fieldset-header-text">订单明细</span>
							<div class="x-clear"></div>
							</legend>
							<div  class="x-fieldset-body">
								<p>请扫描商品条码：
									<input type="text" class="ginput" style="width:150px;" id="goodsCode" value=""  /> 
									&nbsp;&nbsp;需扫描 <b class="f_red" id="needScanningCount">0</b> 个，已扫描 <b class="f_red" id="alreadyScanningCount">0</b> 个，未扫描 <b class="f_red" id="notyetScanningCount">0</b> 个</p>
								<p class="blank10"></p>
								<table class="goodsDetailTb show" style="width:874px;">
									<thead>
										<tr>
											<th width="150" >商品条码</th>
											<th width="100">商品类型</th>
											<th width="350" style="text-align:left;padding-left:10px;">商品名称</th>
											<th width="100">需校验数</th>
											<th width="100">已校验数</th>
											<th width="100" class="bdr">未校验数</th>
										</tr>
									</thead>
									<tbody id="inspectGoods">
									</tbody>
								</table>
							</div>
						</fieldset>
					</div>
					<!--step3-->
					<div id="step3" class="none">
						<p>请扫描快递单号：<input type="text" class="ginput" style="width:150px;" id="expressCode" value="" /></p>
						<p class="blank10"></p>
						<div>
							<div class="detail_box normal" id="scanningOrderInfoExpress">
								
							</div>
							<p class="blank10"></p>
							<table class="goodsDetailTb">
								<thead>
									<tr>
										<th width="150" >商品条码</th>
										<th width="100">商品类型</th>
										<th width="350" style="text-align:left;padding-left:10px;">商品名称</th>
										<th width="100">需校验数</th>
										<th width="100">已校验数</th>
										<th width="100">未校验数</th>
									</tr>
								</thead>
								<tbody id="inspectGoodsExpresss">
								</tbody>
							</table>
						</div>
					</div>
				</form>
			</div>
		</div>
		
	</div>
	<script type="text/javascript">
		//扫描发货订单号  
		$("#orderNo").keydown(function(e){
			if(e.keyCode==13){
				
				var orderNo=$("#orderNo").val();
				if(orderNo==""){
					ygdg.dialog.alert("请输入要扫描的订单号！");
					return false;
				}
				
				$.ajax({
					type: 'post',
					url: 'scanningOrder.sc?param=' + new Date(),
					dataType: 'json',
					data: 'orderNo=' + orderNo,
					success: function(data, textStatus) {
						if(data['msg']!=null&&data['msg']!='客服申请拦截'){
							ygdg.dialog.alert(data['msg']);
							return;
						}else if(data['msg']=='客服申请拦截'){
						   orderIntercept(orderNo);
						}
						//订单信息
						var html="<p><span>订单号："+data['orderSub'].orderSubNo+"</span><span>物流公司："+data['orderSub'].logisticsName+"</span><span>收货人："+data['orderConsigneeInfo'].userName+"</span><span>收货地区："+data['orderConsigneeInfo'].provinceName+data['orderConsigneeInfo'].cityName+data['orderConsigneeInfo'].areaName+"</span></p>";
							html+="<p><span>收货地址："+data['orderConsigneeInfo'].consigneeAddress+"</span><span>邮政编码："+data['orderConsigneeInfo'].zipCode+"</span><span>快递单号："+data['orderSub'].expressOrderId+"</span><span>配送费用："+data['orderSub'].actualPostage+"元</span></p>";
						$("#scanningOrderInfo").html(html);//订单信息
						$("#needScanningCount").html(data['orderSub'].prodCount);//需扫描
						$("#alreadyScanningCount").html('0');//已扫描
						$("#notyetScanningCount").html(data['orderSub'].prodCount);//未扫描
						//订单明细
						if(data['orderDetailslist']!=null){
							var htmlGoods='';
							$.each(data['orderDetailslist'],function(i,item){
								htmlGoods+='<tr>'
								htmlGoods+='<td>'+item.thirdPartyCode+'</td>';
								htmlGoods+='<td>'+item.commodityTypeName+'</td>';
								htmlGoods+='<td style="text-align:left;padding-left:10px;">'+item.prodName+'</td>';
								htmlGoods+='<td>'+item.commodityNum+'</td>';
								htmlGoods+='<td id="'+item.thirdPartyCode+'alreadyCheck">0</td>';
								htmlGoods+='<td id="'+item.thirdPartyCode+'notYetCheck">'+item.commodityNum+'</td>';
								htmlGoods+='</tr>';
								//alert(item.id);
							});
							$("#inspectGoods").html(htmlGoods);
						}
						//跳转到第二部 层的显示隐藏
						$(".sendStatuBar").addClass("step2");
						$(".sendStatuBar li").eq(0).addClass("passd");
						$(".sendStatuBar li").eq(1).addClass("curr");
						$("#step1").hide();
						$("#step2").show();
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
						ygdg.dialog.alert(XMLHttpRequest.responseText);
					}
				});
				return false;
			}
		});
		
		//校验发货商品
		$("#goodsCode").keydown(function(e){
			if(e.keyCode==13){
				var goodsCode = $("#goodsCode").val();
				var orderNo = $("#orderNo").val();
				if(goodsCode==""){
					ygdg.dialog.alert("请输入要扫描的货品条码！");
					return false;
				}
				//去后台进行校验 
				$.ajax({
					type: 'post',
					url: 'inspectGoods.sc?param=' + new Date(),
					dataType: 'json',
					data: {orderNo:orderNo, goodsCode:goodsCode},
					success: function(data, textStatus) {
						if(data['msg']!=null&&data['msg']!='客服申请拦截'){
							ygdg.dialog.alert(data['msg']);
							return;
						}else if(data['msg']=='客服申请拦截'){
						   orderIntercept(orderNo);
						}
						if(data['thirdPartyCode']!=null){
							if(parseInt($("#"+data['thirdPartyCode']+"notYetCheck").html())==0){
								//ygdg.dialog.alert("该条码已校验过！");
								if(!confirm("该条码已校验过，确定录入吗？")){
									$("#goodsCode").val('');
									return;
								}
							}
							var alreadyCheckCount=parseInt($("#"+data['thirdPartyCode']+"alreadyCheck").html())+1;//已校验货品数量加1
							var notYetCheckCount=parseInt($("#"+data['thirdPartyCode']+"notYetCheck").html())-1;//未校验数减1
							var alreadyScanningCount=parseInt($("#alreadyScanningCount").html())+1;//已扫描数量加1
							var notyetScanningCount=parseInt($("#notyetScanningCount").html())-1;//未扫描数减1
							//如果校验成功返回货品条码
							$("#"+data['thirdPartyCode']+"alreadyCheck").html(alreadyCheckCount);//已校验货品数量加1
							$("#"+data['thirdPartyCode']+"notYetCheck").html(notYetCheckCount);//未校验数减1
							
							$("#alreadyScanningCount").html(alreadyScanningCount);//已扫描
							$("#notyetScanningCount").html(notyetScanningCount);//未扫描
							
							 $("#goodsCode").val("");//清空商品条码
							
							//如果未校验数为0 灰掉该明细行
					
							//如果未扫描数为0  跳转到第三步
							if(parseInt($("#notyetScanningCount").html())==0){
								$("#scanningOrderInfoExpress").html($("#scanningOrderInfo").html());
								$("#inspectGoodsExpresss").html($("#inspectGoods").html());
								$(".sendStatuBar").addClass("step3");
								$(".sendStatuBar li").eq(1).addClass("passd");
								$(".sendStatuBar li").eq(2).addClass("curr");
								$("#step2").hide();
								$("#step3").show();	
							}
						}
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
						ygdg.dialog.alert(XMLHttpRequest.responseText);
					}
				});
				return false;	
			}
		});
		
		//校验发货快递单
		$("#expressCode").keydown(function(e){
			if(e.keyCode==13){
				var expressCode=$("#expressCode").val();
				if(expressCode==""){
					ygdg.dialog.alert("请输入快递单号！");
					return false;
				}
				
				if (!checkExpressNoValid($.trim(expressCode))) {
					ygdg.dialog.alert("请填写正确的快递单号！");
				}
				
				$.ajax({
					type: 'post',
					url: 'chexkExpress.sc?param=' + new Date().getTime(),
					dataType: 'html',
					data: 'orderNo=' + $("#orderNo").val() + '&expressCode=' + expressCode,
					beforeSend: function(XMLHttpRequest) {
						ygdg.dialog({
							id: "submitDialog",
							title:'操作提示', 
							content: '<img src="${BasePath}/yougou/js/ygdialog/skins/icons/loading.gif" width="16" height="16" /> 正在提交,请稍候...', 
							lock:true,
							closable:false
						});
					},
					success: function(data, textStatus) {
						if(data=="success"){
							ygdg.dialog.alert("快递单校验成功！订单已成功发货！");
							//清空
							$("#orderNo").val("");//清空订单号
							$("#goodsCode").val("");//清空商品条码
							$("#expressCode").val("");//清空快递单号
							$(".sendStatuBar").attr("class","sendStatuBar");
							$(".sendStatuBar li").eq(0).attr("class","curr");
							$(".sendStatuBar li").eq(2).removeClass("curr");
							$("#step3").hide();
							$("#step1").show();
						} else {
							ygdg.dialog.alert(data);
						}
					},
					complete: function(XMLHttpRequest, textStatus) {
						ygdg.dialog({id: "submitDialog"}).close();
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
						ygdg.dialog.alert(XMLHttpRequest.responseText);
					}
				});
				return false;
			}
		});
		
		//初始扫描发货单号 选中
		$(function(){
			$("#orderNo").focus();
		});
		
		
		//判断快递单号是否含有非法字符（允许数字、字母（大小写均可）、*、_）
		//快递单号含有非法字符返回false、反之为true
		function checkExpressNoValid(expressNo) {
			var regex = new RegExp('[^\\w\*]{1,}', 'gi');
			return !regex.test(expressNo);
		}
		
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
						  content:"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;订单号："+orderSubNo+"<br />申请拦截时间："+data.time+"<br />申请拦截备注："+data.reason+"<br /><br /><p><span style='color:#AAAAAA'>注：同意拦截则订单被置为挂起状态，请不要做发货处理。<br />&nbsp;&nbsp;&nbsp;&nbsp;关闭拦截则该订单状态不变，可继续做发货处理。</span></p>",
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
				return true;
        }
       
//商家忽略拦截的动作
function orderInterceptNo(orderSubNo) {
                  			$.ajax({
					url: "${BasePath}/order/fastdelivery/doOrderIntercept.sc?orderSubNo="+orderSubNo + "&flag=2",
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
}

	</script>
</body>
</html>

