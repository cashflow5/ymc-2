<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-意见反馈</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/manage/commodity/addOrUpdateCommodity.css"/>
<script type="text/javascript" src="${BasePath}/yougou/js/ZeroClipboard.js"></script>
<style>
.contentDefault{
 color:#696969
}
.bottom_div p{height:30px;}
</style>
</head>
<body>
<div class="main_container">
	<div class="normal_box">
		<p class="title site">当前位置：商家中心 &gt; 订单&gt; 发票查询  </p>
		<div class="tab_panel" style="margin-top:0;">
			<div class="tab_content" style="padding:0 20px;"> 
				<p class="mt10">
				<h3 style="font-weight: bold;">发票登记明细</h3>
				<div  class="detail_box normal">
							<div style="float:left">
								<p><span>优购订单号：${invoiceDetail.orderMainNo?default('')}</span></p>
									<p><span>发票类别：<#if invoiceDetail.invoiceType??&&invoiceDetail.invoiceType==1>普通发票<#elseif invoiceDetail.invoiceType??&&invoiceDetail.invoiceType==2>增值税发票<#else>-</#if></span></p>
									<p><span>开票金额：${invoiceDetail.invoiceAmount?default(0)}元&nbsp;(订单金额：${invoiceDetail.orderPayAmount?default(0)}元)</span></p>
									<p><span>运费：${invoiceDetail.postageCost?default(0)}元</span></p>
									<p><span>发票抬头：${invoiceDetail.invoiceTitle?default('')}&nbsp;<#if invoiceDetail.invoiceTitleType??&&invoiceDetail.invoiceTitleType=='1'>(个人)<#elseif invoiceDetail.invoiceTitleType??&&invoiceDetail.invoiceTitleType=='2'>(企业)<#else></#if></span></p>
									<#if invoiceDetail.invoiceStatus??&&invoiceDetail.invoiceStatus==7><p><span>发票号：${invoiceDetail.invoiceNo?default('')}</span></p></#if>
									<p><span>创建时间：<#if invoiceDetail.createTime ??>${invoiceDetail.createTime?string('yyyy-MM-dd HH:mm:ss')}</#if></span></p>
									<p><span>发票状态：<#if invoiceDetail.invoiceStatus??&&invoiceDetail.invoiceStatus==1>新建
	                                               <#elseif invoiceDetail.invoiceStatus??&&invoiceDetail.invoiceStatus==2>客服已审核
	                                               <#elseif invoiceDetail.invoiceStatus??&&invoiceDetail.invoiceStatus==3>客服审核未通过
	                                               <#elseif invoiceDetail.invoiceStatus??&&invoiceDetail.invoiceStatus==4>财务已审核
	                                               <#elseif invoiceDetail.invoiceStatus??&&invoiceDetail.invoiceStatus==5>财务审核未通过
	                                               <#elseif invoiceDetail.invoiceStatus??&&invoiceDetail.invoiceStatus==6>已打印
	                                               <#elseif invoiceDetail.invoiceStatus??&&invoiceDetail.invoiceStatus==7>已配送
	                                               <#elseif invoiceDetail.invoiceStatus??&&invoiceDetail.invoiceStatus==8>已退回
	                                               <#elseif invoiceDetail.invoiceStatus??&&invoiceDetail.invoiceStatus==9>已取消
	                                               <#elseif invoiceDetail.invoiceStatus??&&invoiceDetail.invoiceStatus==10>已作废
                                                   <#elseif invoiceDetail.invoiceStatus??&&invoiceDetail.invoiceStatus==11>发票拦截</#if>
	                                </span></p>
	                                <#if invoiceDetail.invoiceStatus??&&invoiceDetail.invoiceStatus==7>
	                                <p><span>配送方式：<#if invoiceDetail.shipMethod??&&invoiceDetail.shipMethod==1>挂号信&nbsp;(${invoiceDetail.expressCode?default('')})
                                               <#elseif invoiceDetail.shipMethod??&&invoiceDetail.shipMethod==2>快递&nbsp;&nbsp;(${invoiceDetail.logisticsName?default('')}：${invoiceDetail.expressCode?default('')})
                                               <#else>-</#if></span></p>
                                    </#if>
                            </div>
                            <div style="float:right;margin-right:130px;">
								<#if invoiceDetail.invoiceType??&&invoiceDetail.invoiceType==2>
								<p><span>单位名称：${orderInvoiceExtNew.unitName?default('')}</span></p>
									<p><span>税务登记号：${orderInvoiceExtNew.unitRegistrationNo?default('')}</span></p>
									<p><span>注册地址：${orderInvoiceExtNew.unitRegistrationAddress?default('')}</span></p>
									<p><span>注册电话：${orderInvoiceExtNew.unitRegistrationPhoneNo?default('')}</span></p>
									<p><span>开户银行：${orderInvoiceExtNew.bankName?default('')}</span></p>
									<p><span>银行账号：${orderInvoiceExtNew.bankAccount?default('')}</span></p>
									<p><span>营业执照：<a href="${orderInvoiceExtNew.businesLicenseUrl?default('')}" target="_blank">[查看]</a></span></p>
	                                <p><span>税务登记证：<a href="${orderInvoiceExtNew.taxRegistrationUrl?default('')}" target="_blank">[查看]</a></span></p>
	                                <p><span>一般纳税人证明：<a href="${orderInvoiceExtNew.generalTaxpayerUrl?default('')}" target="_blank">[查看]</a></span></p>
	                                <p><span>银行开户许可证：<a href="${orderInvoiceExtNew.bankAccountLicenceUrl?default('')}" target="_blank">[查看]</a></span></p>
								</#if>
							</div>
				</div>
				</p>
                <#if orderInvoiceDetailNews??&&(orderInvoiceDetailNews?size gt 0)>
                <fieldset class="x-fieldset x-fieldset-default" style="width:100%;">
						<legend class="x-fieldset-header">
						<a class="x-tool-toggle"></a>
						<span class="x-fieldset-header-text">开票商品</span>
						<div class="x-clear"></div>
						</legend>
						<div class="x-fieldset-body">
				<table class="goodsDetailTb" id="table">
						<thead>
							<tr class="bdr">
								<th>货品条码</th>
								<th>商品名称</th>
								<th>商品类别</th>
								<th>计量单位</th>
								<th>规格</th>
								<th>单价</th>
								<th>单件开票金额</th>
								<th>商品数量</th>
								<th>登记数量</th>
							</tr>
						</thead>
				<tbody>
				<#list orderInvoiceDetailNews as item >
						<tr>
							<td>${item.prodNo?default('')}</td>
							<td><div style="width:98%;float:left;word-break:break-all;text-align:left;">
							    <img src="${item['picUrl']?default('')}" align ="left" width="40" height="40" />
							    <a href="${item['static_url']?default('')}" target="_blank">${item.prodName?default('')}</a></div></td>
							<td>${item.prodType?default('')}</td>
							<td>${item.units?default('')}</td>
							<td>${item.brandSpec?default('')}</td>
							
							<td>${item.price?default('')}</td>
							<td>${item.unitPrice?default('')}</td>
							<td>x&nbsp;${item.bugNum?default('')}</td>
							<td>${item.commodityNum?default('')}</td>
						</tr>
				</#list>
				</tbody>
				</table>
				</div>
				</fieldset>
				</#if>
				<div style="margin-top:35px;">
				  <h3 style="font-weight: bold;margin-bottom:10px;">收件信息</h3>
				  <div class="bottom_div" style="margin-left:20px;">
				  <p>
				  <span><label>收件人：</label>${invoiceDetail.consigneeName?default('')}</span>
				  </p>
				  <p>
				  <span>联系手机：${invoiceDetail.mobilePhone?default('')}</span>
				  </p>
				  <p>
				  <span>收件地区：${invoiceDetail.province?default('')}${invoiceDetail.city?default('')}${invoiceDetail.area?default('')}</span>
				  </p>
				  <p>
				  <span>收件地址：${invoiceDetail.consigneeAddress?default('')}</span>
				  </p>
				  <p>
				  <span>邮政编码：${invoiceDetail.postCode?default('')}</span>
				  </p>
				  <p>
				  <span>备注：${invoiceDetail.remark?default('')}</span>
				  </p>
				  <p>
				  <a href="javascript:;" onclick="javascript:return false;" class="button" onmouseover="copyPic(this);"><span>复制信息</span></a>
				  </p>
				  </div>
				</div>
			</div>
		 </div>
	   </div>
	 </div>
</body>
<script type="text/javascript">
function copyPic(btn) {
var text="收件人：${invoiceDetail.consigneeName?default('')}  "
         +"联系手机：${invoiceDetail.mobilePhone?default('')}  "
         +"收件地区：${invoiceDetail.province?default('')}${invoiceDetail.city?default('')}${invoiceDetail.area?default('')}  "
         +"收件地址：${invoiceDetail.consigneeAddress?default('')}  "
         +"邮政编码：${invoiceDetail.postCode?default('')}";
	try {
		var clip = new ZeroClipboard.Client();
		clip.setHandCursor(true);
		clip.addEventListener('mouseOver', function(client) {
			clip.setText(text);
		});
		clip.addEventListener('complete', function(client, text) {
			alert("复制成功!");
		});
		clip.glue(btn);
	} catch (e) {
	}
}
</script>
</html>