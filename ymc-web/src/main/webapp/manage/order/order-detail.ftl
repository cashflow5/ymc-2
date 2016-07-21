<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-订单详情</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
<style type="text/css">
	.list {width:100%;overflow:hidden;zoom:1;}
	.list li{float:left;width:45%;padding:1px;}
	.qi {text-align:left;}
	.qi span{margin-right: 30px;color: #3366CC;}
	
	.wms_warm {
	    background: none repeat scroll 0 0 #FEFEDA;
	    border: 1px solid #FFE8C2;
	}
</style>
<style>
    .order-step-div{ border:1px solid #ddd; background:#fafafa; height:92px; }
    .order-step-left, .order-step-right{ float:left; padding:10px; }
    .order-step-left{ border-right:1px solid #ddd; width:730px; height:72px; }
    .order-step-right{line-height:22px;}
    .order-progress, .order-progress .bar{ background:url(${BasePath}/yougou/images/progress.png) no-repeat; width:675px; height:16px; }
    .order-progress{ overflow:hidden; }
    .order-progress .bar{ background-position:0 -18px; position:relative; }
    .order-progress i, .order-progress em{ width:0; height:0; font-size:0; position:absolute; right:-7px; top:1px; }
    .order-progress i{ border:7px dashed transparent; border-left:7px solid #fff; border-right:0; }
    .order-progress em{ border:5px dashed transparent; border-left:5px solid #ff791c; border-right:0; top:3px; right:-5px; }
    .order-progress .bar.step1{ width:180px; }
    .order-progress .bar.step2{ width:360px; }
    .order-progress .bar.step3{ width:600px; }
    .order-progress .bar.step4{ width:680px; }
    .order-step-list{ height:25px; overflow:hidden; line-height:25px; }
    .order-step-list li{ width:210px; float:left; }
    .order-step-list li.last{ width:80px; }
</style>
<script type="text/javascript">
	//订单详情选项卡 div显示隐藏
	$(function(){	
		$(".tab li").click(function(){
			var index=$(".tab li").index(this);
			$(this).addClass("curr").siblings().removeClass("curr");
			$(".tab_content>div").hide();
			$(".tab_content>div").eq(index).show();
		});
	})
	
	//查看物流消息
	function selectExpressLog(logisticsCompanyName,expressCode,logisticsCompanyCode, orderSubNo){
	    var htmlStr="";
		$.ajax({
			url: 'selectExpressLog.sc',
			type: 'post',
			data: {expressCode:expressCode, logisticsCompanyCode:logisticsCompanyCode, orderSubNo:orderSubNo},
			dataType: 'json',
			success: function(json){
				if(json.error!=null){
					htmlStr+='<div>'+json.error+logisticsCompanyName+'！</div>';
					$("#wait").html(htmlStr);	//显示
				}else{
					if(json.data!=null&&json.data!=''){
						htmlStr+='<div><table width="100%"><tr style="border-bottom: 1px solid #AED2FF;"><th style="text-algin:center;width:200px;">处理时间</th><th style="text-algin:center;">处理信息</th></tr>';
						for(var i=0;i<json.data.length;i++){
							 htmlStr+='<tr><td style="text-algin:center;">'+json.data[i].time+'</td>';
							 htmlStr+='<td style="text-algin:left;">'+json.data[i].context+'</td></tr>';
						}
						htmlStr+='</table><div style="background: none repeat scroll 0 0 #FEFEDA;border: 1px solid #FFE8C2;"><img src="${BasePath}/yougou/images/warn.png" alt="">该信息由物流公司提供，如有疑问请登录 <a href="'+json.logisticsWeb+'" style="color:blue;" target="_blank"><b>'+logisticsCompanyName+'</b></a> 查询或拨打官方查询电话 <span style="color:#D27248;"><b>'+json.logisticsTel+'</b></span></div>';
						htmlStr+='</div>';
						$("#wait").html(htmlStr);	//显示
					}else{
						htmlStr+='<div style="background: none repeat scroll 0 0 #FEFEDA;border: 1px solid #FFE8C2;"><img src="${BasePath}/yougou/images/warn.png" alt="">该信息由物流公司提供，如有疑问请登录 <a href="'+json.logisticsWeb+'" style="color:blue;" target="_blank"><b>'+logisticsCompanyName+'</b></a> 查询或拨打官方查询电话 <span style="color:#D27248;"><b>'+json.logisticsTel+'</b></span></div>很抱歉,未获取到快递公司的配送信息,可能是由于快递官网信息未及时录入,请耐心等待,如有问题可登录快递公司官网或者致电快递公司客服查询.</div>';
						$("#wait").html(htmlStr);	//显示
					}
				}
			}
		});
	}
		
</script>
</head>

<body>
	<div class="main_container" style="width:99%;">
		<div class="normal_box">
			<p class="title site">当前位置：商家中心 &gt; 订单 &gt; 订单详情</p>
			<!--详情start-->
			<div class="order-step-div">
			    <div class="order-step-left">
			        <ul class="order-step-list">
			            <li>顾客下单</li>
			            <li>订单审核</li>
			            <li>订单出库</li>
			            <li class="last" style="padding-left:20px;">顾客退货</li>
			        </ul>
			        <div class="order-progress">
			            <div class="bar <#if returnAndRejectionTime??>step4<#elseif expand.shipTime??>step3<#elseif expand.verifyTime??>step2<#elseif expand.createTime??>step1</#if>">
			                <i></i>
			                <em></em>
			            </div>
			        </div>
			        <ul class="order-step-list">
			            <li><#if expand.createTime??>${expand.createTime?datetime}</#if></li>
			            <li><#if expand.verifyTime??>${expand.verifyTime?datetime}</#if></li>
			            <li><#if expand.shipTime??>${expand.shipTime?datetime}</#if></li>
			            <li class="last" style="line-height:14px;"><#if returnAndRejectionTime??>${returnAndRejectionTime?datetime}</#if></li>
			        </ul>
			    </div>
			    <div class="order-step-right">
			        <strong class="cred">${expand.orderSubNo!''}</strong><br>
			        <span class="c9">订单状态：</span><em class="f_red">${expand.orderStatusName!''}</em><br>
			        <span class="c9">下单日期：</span><#if expand.createTime??>${expand.createTime?datetime}</#if>
			    </div>
			</div>
			<!--详情end-->
			<hr class="line" />
			<div class="tab_panel">
				<ul class="tab">
					<li class="curr">
						<span>订单信息</span>
					</li>
					<li>
						<span>物流跟踪</span>
					</li>
					<li>
						<span>订单日志</span>
					</li>
					<li>
						<span>退回包裹</span>
					</li>
				</ul>
				<div class="tab_content"> 
					<!--订单信息-->
					<div id="orderInfo" style="margin-top: 10px;">
					   <fieldset class="x-fieldset x-fieldset-default">
							<legend  class="x-fieldset-header">
								<a  class="x-tool-toggle" ></a>
								<span class="x-fieldset-header-text">订单基本信息</span>
								<div class="x-clear"></div>
							</legend>
							<div  class="x-fieldset-body">
							      <ul class="list">
							         <li>收货人姓名:<span style="color: #3366CC;"><#if expand.orderConsigneeInfo??>${orderConsigneeInfo.userName!''}</#if></span></li>
							         <li>订单号:<span style="color: #FF0000;">${expand.orderSubNo!''}</span>&nbsp;<#if expand.originalOrderNo??&&expand.originalOrderNo!=''><#if expand.orderSubNo?substring(expand.orderSubNo?index_of("_")+1)?number gt 200>[补]<#else>[换]</#if></#if></li>
							         <li>收货人手机:<span style="color: #3366CC;"><#if expand.orderConsigneeInfo??>${orderConsigneeInfo.mobilePhone!''}</#if></span></li>
							         <li>外部订单号:<span style="color: #3366CC;">${expand.outOrderId!''}</span></li>
							         <li>固定电话:<span style="color: #3366CC;"><#if expand.orderConsigneeInfo??>${orderConsigneeInfo.constactPhone!''}</#if></span></li>
							         <!--<li>下单日期:<span style="color: #3366CC;"><#if expand.createTime??>${expand.createTime?datetime}</#if></span></li>-->
							         <li>支付方式:<span style="color: #3366CC;">${expand.paymentName!''}</span></li>
							         <li>收货人地址:<span style="color: #3366CC;"><#if expand.orderConsigneeInfo??>${orderConsigneeInfo.provinceName!''}${orderConsigneeInfo.cityName!''}${orderConsigneeInfo.areaName!''}${orderConsigneeInfo.consigneeAddress!''}</#if></span></li>
							         <li>订单来源:<span style="color: #3366CC;">${expand.outShopName!''}</span></li>
							         <li>邮政编码:<span style="color: #3366CC;"><#if expand.orderConsigneeInfo??>${orderConsigneeInfo.zipCode!''}</#if></span></li>
							         <li>送货时间:<span style="color: #3366CC;">
							         	<#if expand.orderConsigneeInfo??>
							         		<#if orderConsigneeInfo.deliveryDate == 'WORKDAY_DELIVERY'>只工作日送货(双休日、假日不用送)
						                	<#elseif orderConsigneeInfo.deliveryDate == 'ALL_CAN_DELIVER'>工作日、双休日与假日均可送货
						                	<#elseif orderConsigneeInfo.deliveryDate == 'HOLIDAY_DELIVERY'>只双休日、假日送货(工作日不用送)
						                	<#elseif orderConsigneeInfo.deliveryDate == 'OTHER_TIME_DELIVERY'>学校地址/地址白天没人，请尽量安排其他时间送货</#if>
							         	</#if></span></li>
							         <li style="width:45%;">顾客留言:<span style="color: #FF0000;"><#if expand.message??>${expand.message}</#if></span></li>
							         <li style="width:54%;">订单备注:<span style="color: #3366CC;"><#if markNote??>${markNote!''}</#if></span></li>
							      
							      </ul>
							</div>
						</fieldset>
						<fieldset class="x-fieldset x-fieldset-default">
							<legend  class="x-fieldset-header">
								<a  class="x-tool-toggle" ></a>
								<span class="x-fieldset-header-text">订单基本信息</span>
								<div class="x-clear"></div>
							</legend>
							<div  class="x-fieldset-body">
							    <div>
							                      订单总金额:<#if expand.productTotalPrice??>${expand.productTotalPrice}</#if> 
							        
									<#if expand.couponPrefAmount??&&(expand.couponPrefAmount>0 )>
										 <#if expand.discountAmount??&&(expand.discountAmount-expand.couponPrefAmount>0 )>
										 - 促销活动优惠:${expand.discountAmount?default(0)-expand.couponPrefAmount?default(0)}
										 	<#if outdetails[7] ??&& (outdetails[7]>0)>（含下单立减优惠：${outdetails[7]?default(0)}）
										 	</#if>
										 </#if>- 优惠券:${expand.couponPrefAmount}
										
									<#else>
										 	<#if expand.discountAmount??&&(expand.discountAmount>0 )>
										 - 促销活动优惠:${expand.discountAmount?default(0)}
										 	<#if outdetails[7] ??&& (outdetails[7]>0)>（含下单立减优惠：${outdetails[7]?default(0)}）
										 	</#if>
										 </#if>
									</#if>
									

								    <#if expand.couponPrefAmount5??&&(expand.couponPrefAmount5>0 )>- 礼品卡:${expand.couponPrefAmount5}</#if>
								    + 运费:<#if expand.actualPostage??>${expand.actualPostage}</#if> =
								         实付金额:<b style="font-size: 25px;color: #FF0000;"><#if expand.payTotalPrice??>${expand.payTotalPrice}</#if></b>
							    </div>
								<!--列表start--> 
								<table class="goodsDetailTb">
									<thead>
										<tr>
										    <th width="40"></th>
											<th width="310">商品名称</th>
											<th width="80">商品编号</th>
										    <th width="110">货品条码</th>
											<th width="50">规格</th>
											<th width="50">数量</th>
											<th width="70">优购价</th>
											<th width="80" class="bdr">小计</th>
										</tr>
									</thead>
									<tbody>
										<#if detailList??>
											<#list detailList as item>
												<tr>
													<td style="border-right: 0px;"><img src="${item.commodityImage!''}" width="40px;" height="40px;" /></td>
													<td style="text-align:left;border-left: 0px;">
														<a href="${item.tempCommodityUrl!''}" target="_blank">${item.prodName!''}</a>
													</td>
													<td>${item.commodityNo!''}</td>
												    <td>${item.levelCode!''}</td>
													<td>${item.commoditySpecificationStr!''}</td>
													<td>x${item.commodityNum!''}</td>
													<td>${item.activePrice!''}</td>
													<td>${item.prodTotalAmt!''}</td>
												</tr>
											</#list>
										</#if>
									</tbody>
								</table>
							</div>
						</fieldset>
					</div> 
					<!--订单跟踪-->
					<div style="display:none;margin-top:10px;padding: 5px 5px 5px 5px;">
					    <table style="width:90%;">
				            <#if outdetails ??>
						            <tr>
					                   <td style="width:60px;">配送公司:</td>
					                   <td><span style="color: #3366CC;"><#if outdetails[5] ??>${outdetails[5]}</#if></span></td>
					                </tr>
					                <tr>
					                   <td>发货时间:</td>
					                   <td><span style="color: #3366CC;"><#if outdetails[1] ??>${outdetails[1]?string('yyyy-MM-dd HH:mm:ss')}</#if></span></td>
					                </tr>
					               <tr>
					                   <td>运单号:</td>
					                   <td><span style="color: #3366CC;"><#if outdetails[3] ??>${outdetails[3]}</#if></span></td>
					                </tr>
					                <tr>
					                   <td>物流跟踪:</td>
					                   <td></td>
					                </tr>
					                <tr>
					                   <td></td>
					                   <td>	
					                      <script type="text/javascript">
					                          selectExpressLog('${outdetails[5]!''}','${outdetails[3]!''}','${outdetails[6]!''}','${expand.orderSubNo!''}');
					                      </script>
						                   <div id="wait" style="padding-left: 5px;line-height:25px;vertical-align:top;">
													<img src="${BasePath}/yougou/images/blue-loading.gif"/>载入中，请稍后...
											</div>
										</td>
					                </tr> 
				            </#if> 
				          </table>
					</div>
					<!--订单日志-->
					<div style="display:none;margin-top:10px;">
		                <table class="goodsDetailTb" width="100%">
			                <thead>
				                <tr>
				                <th>日志类型</th>
				                <th>时间</th>
				                <th>操作人</th>
				                <th>行为</th>
				                <th>备注</th>
				                <th width="100" class="bdr">操作结果</th>
				                </tr>
			                </thead>
			                <tbody>
				                <#if log ??>
				                	<#list log as item>
						                <tr>
						                <td><#if item.logType ?? && item.logType == 1>操作日志</#if>
						                	<#if item.logType ?? && item.logType == 2>售后日志</#if>
						                	<#if item.logType ?? && item.logType == 3>退款日志</#if>
						                	<#if item.logType ?? && item.logType == 4>正常流转日志</#if>
						                </td>
						                <td><#if item.createTime ??>${item.createTime?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
						                <td><#if item.operateUser ??>${item.operateUser}</#if></td>
						                <td><#if item.behavioutDescribe ??>${item.behavioutDescribe}</#if></td>
						                <td><#if item.remark ??>${item.remark!''}</#if></td>
						                <td class="td0">
						                 <#if item.operateResult ?? && item.operateResult == 1>
						                   	成功
						                 <#else>
						                                                    失败
						                 </#if>
						                 </td>
						                </tr>
				                	</#list>
				                </#if>
			                </tbody>
		                </table>
					</div>
					<!--退换货-->
					<div style="display:none;margin-top:10px;">
						<table class="goodsDetailTb" width="100%">
						<#if returnAndRejectionList??&&(returnAndRejectionList?size>0)>
							<#list returnAndRejectionList as item>
								  <tr>
								  	<th>第${item.index?string}个退回包裹</th>
								  	<th class="qi" style="text-align:left;">寄回快递公司:<span>${item.expressName!''}</span>寄回快递单号:<span>${item.expressCode!''}</span>寄回快递费:<span>${item.expressCharges?string}</span></th>
								  </tr>
								  <#if item.returnAndRejectionDetailList??>
								  	<#list item.returnAndRejectionDetailList as item2>
									  <tr>
									  	<td style="text-align:left;">
									  		<div class="pro_pic fl">
                                        		<img width="40" height="40" src="${item2.picUrl!''}" alt="">
                                            </div>
                                            <div class="txt_inf fl">
	                                        	<p class="mb3"><a href="${item2.prodUrl!''}" target="_blank">${item2.commodityName!''}</a></p>
	                                            <p>货品条码：${item2.productNo!''}</p>
	                                            <p>规格：${item2.specName!''},${item2.commoditySize!''}</p>
                                            </div>
									  	</td>
									  	<td class="qi" style="text-align:left;">申请类型:<span>${item2.type!''}&nbsp;</span>售后原因:<span>${item2.reason!''}&nbsp;</span>售后申请单状态:<span>${item2.status!''}&nbsp;</span>质检结果:<span>${item2.result!''}&nbsp;</span></br>质检日期:<span>${item2.qaDate?datetime}&nbsp;</span>质检描述:<span>${item2.description!''}&nbsp;</span></td>
									  </tr>
									</#list>
								</#if>
							</#list>
						   <#else>
						     <tr><td>暂无数据!</td><tr>
				           </#if>
						</table>
					</div>
				</div>
			</div>
		</div>
		
		
	</div>
</body>
</html>
