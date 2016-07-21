<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>优购商家中心-打印购物清单</title>
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
	<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
	<style>
		body {font-size:10pt;font-family:\5fae\8f6f\96c5\9ed1;}
		p{overflow:hidden;}
		.printGoodsList {width:730px;margin:0 auto;position:relative;overflow:hidden;*margin-top:2px;margin-top:0\0;}
		.printGoodsList h1 {font-size:30px;margin-top:5px;letter-spacing:1px;font-weight:normal;margin-left:95px;}
		.printGoodsList .orderInfo{font-size:12px;}
		.printGoodsList .orderInfo li{float:left;margin-right:40px;}
		.printGoodsList .goodsTb{border-collapse:collapse;width:730px;margin-top:10px;}
		.printGoodsList .goodsTb th,.printGoodsList .goodsTb td{text-align:center;border-bottom:1px dashed #000;}
		.printGoodsList .goodsTb th{height:24px;border-bottom:1px solid #000;}
		.printGoodsList .goodsTb td{background:#fff;font-size:11px;height:20px;line-height:20px;}
		.printBtn{background:url(${BasePath}/yougou/images/goodListPrintBtn.gif) no-repeat;position:absolute;right:20px;top:20px;width:179px;height:36px; display:block;cursor:pointer;}
		.qTitle{font-size:14px;}
		.qaP{margin-top:10px;font-size:11px;}
		.listH{min-height:531px; overflow:hidden;}
		.loading{position:absolute;height:100%;width:100%;}
	</style>
	<style MEDIA="PRINT">    
		.noprint{display:none;}
	</style>
	<style MEDIA="SCREEN">    
		.noprint {display:block;}
	</style>
	
	<object id=eprint classid="clsid:CA03A5A8-9890-49BE-BA4A-8C524EB06441" codebase="/yougou/js/eprint.cab#Version=3,0,0,6" viewasext VIEWASTEXT>
	</object>
	<script language="javascript">
		var print = null;
		var paperTypes = {
			A4:{name:"A4",width:210,height:297}
		};

		//打印预览
		function Preview(){
			if ('undefined' == typeof print.defaultPrinterName||print.defaultPrinterName.length==0){
				alert("请先安装打印机控件和打印机，再执行此功能！");
				return;
			}
			PrintInit();
			print.Preview();
		}

		//直接打印
		function Print(){  
			if ('undefined' == typeof print.defaultPrinterName||print.defaultPrinterName.length==0){
				alert("请先安装打印机控件和打印机，再执行此功能！");
				return;
			}
			PrintInit();
			setParameters();
			print.Print(true);  //true   不出打印对话框直接打印
		}
		
		function PrintInit(){  //打印机初始化
			print.InitPrint();
			print.companyName = "优购科技";
			print.seriesNo = "8756-1131-1853-1185" ; 
			setParameters();
		}
		
    	var printCall = null;
		
		function setParameters(){
		    print.SetMarginMeasure(1);  //设置单位  1:毫米(默认值) 2:英寸
		    print.marginTop = 8;   //页面上边距
		    print.marginLeft =8; //页面左边距
		    print.marginRight = 8; //页面右边距
		    print.marginBottom = 8; //页面底边距
		    print.header = "";  //页面的页眉信息,设置值可以和下面IE的页眉页脚代码合并设置
        	print.paperSize = "A4"; //定制A4 纸打印 
        	print.zoomValue = "100";//打印预览时候的显示缩放比例(打开预览页面按100％的方式显示)
        	print.orientation = 1;  //1:纵向，2:横向

		}
			
	</script>
</head>
<body>
<div id="Load" class="noprint" style="position:absolute">加载中，请稍后...</div>
<div id="Main" style="display:none;" >
<a class="noprint" href="javascript:;" onclick="Preview();">预览</a>
<a class="noprint printBtn" href="javascript:;" onclick="GoodsPrint();">&nbsp;</a>
<#if orderSubList??>
	<#list orderSubList as item>
		<#if item.orderSourceNo??&&item.orderSourceNo?starts_with("YG")>
		<div class="printGoodsList" id="PrintArea" <#if item_index?default(0) &gt; 0>style="page-break-before: always"</#if>>
			  <div style="width:728px;">
			   <div style="height:120px;overflow:hidden;">
			    <span class="fl"><img src="${BasePath}/yougou/images/printLogo2013.jpg" /></span>
			    <h1 class="fl">优购网购物清单</h1>
			    </div>
			    <p style="padding-top:15px;">亲爱的会员：非常感谢您的购物，优购时尚商城期待您的再次光临！</p>
			  </div>
			  <div style="clear:both;">
			  </div>
			  <ul class="orderInfo" style="border-top:1pt solid #000;padding-top:3px;">
			    <li>客户姓名：${item.orderConsigneeInfo.userName!''}</li>
			    <li>订单号：${item.orderSubNo!''}</li>
				<li>订购时间：${item.createTime?string("yyyy-MM-dd HH:mm:ss")}</li>
				<li>商品总数：${item.productTotalQuantity?default(0)}</li>
			  </ul>
			  <div style="clear:both;">
			  </div>
			  <div  style="position:absolute;right:0;top:55px;height:80px; overflow:hidden;display:block;">
			    <div style="font-size:14px;">
			      批次订单序号：<span style="font-weight:bold;">${item_index+1}</span>
			    </div>
			    <div>
			      <img src='/barcode?code=${item.orderSubNo!''}&hrsize=0mm' width="260" height="40px" /><br/><p style="width:100%;text-align:center;">${item.orderSubNo!''}</p>
			    </div>
			  </div>
			<div class="listH">
				<table border="0" cellpadding="0" cellspacing="0" class="goodsTb">
				  <thead>
				    <tr>
				      <th>商品编号</th>
				      <th>商品名称</th>
				      <th>规格</th>
				      <th>单价</th>
				      <th>数量</th>
				      <th>小计</th>
				      <th>货品条码</th>
				    </tr>
				  </thead>
				  <tbody>
					<#if item.orderDetail4subs??>
						<#list item.orderDetail4subs as orderDetail>
							<tr>
								<td><div style="width:80px;;">${orderDetail.commodityNo!''}</div></td>
								<td><div style="width:240px; overflow:hidden;">${orderDetail.prodName!''}</div></td>
								<td><div style="width:52px;">${orderDetail.commoditySpecificationStr!''}</div></td>
								<td><div style="width:51px;">${(orderDetail.prodUnitPrice)!''}</div></td>
								<td><div style="width:41px;">${(orderDetail.commodityNum)!''}</div></td>
								<td><div style="width:52px;">${(orderDetail.tempSaleAmount)!''}</div></td>
								<td><div style="width:210px;padding:4px 0;"><img src='/barcode?code=${(orderDetail.levelCode)!''}&hrsize=0mm' width="200" height="23px" />${orderDetail.levelCode!''}</div></td>
							</tr>
						</#list>
					</#if>
				  </tbody>
				</table>
				<p style="width:560pt; text-align:right;margin-top:0px;border-top:1px solid #000;">
					<span style="margin-right:20pt;">
						<span>优惠金额(元)：${item.discountAmount}</span>
						<span style="margin-left:10px;">实付金额(元)：${item.payTotalPrice?default('0.0')}</span>
					</span> 
				</p>
				<h3 class="qTitle">&#9733;退换货地址:</h3>
			    <p style="font-size:9pt;padding:10px;padding-top:5px;height:54px;overflow:hidden;">
				<#if merchantRejectedAddress??>
					${merchantRejectedAddress.warehouseArea!''}${merchantRejectedAddress.warehouseAdress!''}<br />
					收货人：${(merchantRejectedAddress.consigneeName)!''}<br />
					邮编：${merchantRejectedAddress.warehousePostcode!''} 
					&nbsp;&nbsp;收货电话：${merchantRejectedAddress.consigneePhone!''} 
					<#if merchantRejectedAddress.consigneeTell??>&nbsp;&nbsp;优购客服：${merchantRejectedAddress.consigneeTell!''}</#if>
				</#if>
			   </p>
			</div>
			<h3 class="qTitle">&#9733;常见问题Q&amp;A:</h3>
			<p class="qaP">Q：如果收到商品不满意怎么办？是否可以办理退换货？<br />
			A：优购时尚商城对于所售商品均提供“自收到商品之日起10日内退换货”服务。<br />
			<span style="padding-left:20px;">如需退换货，请您确保所购商品未经穿着，包装完好且配件齐全，不影响二次销售（鞋底、鞋面已磨损，已有穿着痕迹属于影响二次销售），登录个人账户在线填写退换货申请，并在3日内寄回商品。</span>
			</p>
			<p class="qaP"> Q：换货需要多长时间？<br />
			A：我们收到换货商品后，将在1个工作日内进行质检，并根据您的要求办理换货。<br />
			<span style="padding-left:20px;">换货商品发货成功后，会有短信发送至收货人手机，敬请留意。</span>
			</p>
			<p class="qaP"> Q：退货需要多长时间？<br />
			A：我们收到换货商品后，将在1个工作日内进行质检；退款提交财务后，约2-5个工作日可到账。<br />
			<span style="padding-left:20px;">我们将以最快的速度为您办理退换货及退款手续，请耐心等待，谢谢！</span>
			</p>
			<div style="padding-top:10px;"></div>
			<h3 class="qTitle">&#9733;温馨提示：</h3>
			<p class="qaP"> *为使退换货过程更加顺畅，试穿时请在鞋子下面加垫干净的纸张，以免鞋底弄脏或磨损；试穿其他商品时，请保持其洁净。<br />
			*请妥善保留此单，如需办理退换货，此单需同商品一并寄回。关于“退换货说明”及“流程”请参考背面信息。 </p>
			<p style="text-align:center;height:44px;"><img src="${BasePath}/yougou/images/print_bar.gif" /></p>
		</div>
		<#else>
		<div class="printGoodsList" id="PrintArea" <#if item_index?default(0) &gt; 0>style="page-break-before: always"</#if> >
			 <div style="width:728px;">
			   <div style="height:120px;overflow:hidden;">
			    <h1 style="text-align:center;margin-left: 0px;">购物清单</h1>
			    </div>
			    <p style="padding-top:15px;">亲爱的会员：非常感谢您的购物，待您的再次光临！</p>
			  </div>
			<div style="clear:both;">
			</div>
			<ul class="orderInfo" style="border-top:1pt solid #000;padding-top:3px;">
				<li>订单编号：${item.orderSubNo!''}</li>
				<li>订购时间：${item.createTime?string("yyyy-MM-dd HH:mm:ss")}</li>
				<li>客户姓名：${item.orderConsigneeInfo.userName!''}</li>
				<li>商品总数：${item.productTotalQuantity?default(0)}</li>
			</ul>
			<div style="clear:both;">
			</div>
			<div  style="position:absolute;right:0;top:55px;height:80px; overflow:hidden;display:block;">
				<div style="font-size:14px;">
			      	批次订单序号：<span style="font-weight:bold;">${item_index+1}</span>
			    </div>
			    <div>
			      	<img src='/barcode?code=${item.orderSubNo!''}&hrsize=0mm' width="260" height="40px" /><br/><p style="width:100%;text-align:center;">${item.orderSubNo!''}</p>
			    </div>
			</div>
			<div class="listH">
				<table border="0" cellpadding="0" cellspacing="0" class="goodsTb">
				  	<thead>
				    	<tr>
				      		<th>商品编号</th>
				      		<th>商品名称</th>
				      		<th>规格</th>
				      		<th>单价</th>
				      		<th>数量</th>
				      		<th>小计</th>
				      		<th>货品条码</th>
				    	</tr>
				  	</thead>
				  	<tbody>
					<#if item.orderDetail4subs??>
						<#list item.orderDetail4subs as orderDetail>
							<tr>
								<td><div style="width:102px;;">${orderDetail.commodityNo!''}</div></td>
								<td><div style="width:288px; overflow:hidden;">${orderDetail.prodName!''}</div></td>
								<td><div style="width:52px;">${orderDetail.commoditySpecificationStr!''}</div></td>
								<td><div style="width:51px;">${orderDetail.activePrice}</div></td>
								<td><div style="width:41px;">${orderDetail.commodityNum}</div></td>
								<td><div style="width:52px;">${orderDetail.prodTotalAmt}</div></td>
								<td><div style="width:210px;padding:2px 0;"><img src='/barcode?code=${(orderDetail.levelCode)!''}&hrsize=0mm' width="200" height="25px" />${orderDetail.levelCode!''}</div></td>
							</tr>
						</#list>
					</#if>
				  	</tbody>
				</table>
				<p style="width:560pt; text-align:right;margin-top:5px;">
					<span style="margin-right:20pt;">
						<span>优惠金额(元)：${item.discountAmount}</span>
						<span style="margin-left:10px;">实付金额(元)：${item.totalPrice?default('0.0')}</span>
					</span> 
				</p>
				<p><img src="${BasePath}/yougou/images/print_thdz.gif" /></p>
				<p style="font-size:9pt;padding:10px;padding-top:5px;height:54px;overflow:hidden;">
					<#if merchantRejectedAddress??>
						${(merchantRejectedAddress.warehouseArea)!''}${(merchantRejectedAddress.warehouseAdress)!''}<br />
						收货人：${(merchantRejectedAddress.consigneeName)!''}<br />
						邮编：${(merchantRejectedAddress.warehousePostcode)!''}
						&nbsp;&nbsp;收货电话：${merchantRejectedAddress.consigneePhone!''} 
						&nbsp;&nbsp;优购客服：${(merchantRejectedAddress.consigneeTell)!''}
					</#if>
				</p>
			</div>
			<h3 class="qTitle">&#9733;常见问题Q&amp;A:</h3>
			<p class="qaP">Q：如果收到商品不满意怎么办？是否可以办理退换货？<br />
			A：自签收起7日内一张订单可享受一次退换货服务。<br />
			<span style="padding-left:20px;">如需退换货，请您确保所购商品未经穿着，包装完好且配件齐全，不影响二次销售（鞋底、鞋面已磨损，已有穿着痕迹属</span><br />
            <span style="padding-left:20px;">于影响二次销售），并在线联系客服，登记退换信息。</span>
			</p>
			<p class="qaP"> Q：换货需要多长时间？<br />
			A：我们收到换货商品后，将在1-2日内进行收货登记，登记后1-2日内会根据您的要求办理换货。<br />
            <span style="padding-left:20px;">换货商品发货成功后，需要3-5日的商品派送时间，请耐心等待，谢谢！</span><br />
			</p>
			<p class="qaP"> Q：退货需要多长时间？<br />
			A：我们收到退货商品后，将在1-2日内进行收货登记，登记后1-2日内会根据您的要求办理退货，货款将会在受理退货后1-2<br />
            <span style="padding-left:20px;">日内给您办理退款。</span><br />
			<span style="padding-left:20px;">我们将以最快的速度为您办理退换货及退款手续，请耐心等待，谢谢！</span>
			</p>
			<div style="padding-top:10px;"></div>
			<h3 class="qTitle">&#9733;温馨提示：</h3>
			<p class="qaP"> *为使退换货过程更加顺畅，试穿时请在鞋子下面加垫干净的纸张，以免鞋底弄脏活磨损；试穿其他商品时，请保持其洁净。<br />
			*请妥善保留此单，如需办理退换货，此单需同商品一并寄回。关于“退换货说明”及“流程”请参考背面信息。 </p>
		</div>
		</#if>
	</#list>
</#if>
<script language="JavaScript">
	$('.goodsTb').each(function(){
		var _this=$(this);
		$('tbody tr:last td',this).css({'border-bottom-width':'0'}); 
	});

	function GoodsPrint(){
		//购物清单打印次数加一
		$.get("doBatchPrintShoppingListTemplate.sc",{orderSubNos:'${orderNos!''}'},function(data){
			if(data=="success"){
				Print();
			    var dg=ygdg.dialog.alert("打印成功！");
			    setTimeout(function(){dg.close();},1000);
			}
		});
	}
window.onload = function(){
$("#Load").hide();
$("#Main").show();
print = document.all.eprint;
setParameters();

}
</script>
</div>
</body>
</html>
