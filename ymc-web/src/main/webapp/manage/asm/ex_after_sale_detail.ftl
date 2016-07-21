<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-售后单详情</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
<style>
.search_box label {
    width: 90px;
}
</style>
</head>
<body>
<div class="main_container">
    <div class="normal_box">
    <div class="list_content">
    <p class="title site">当前位置：商家中心 &gt; 售后 &gt; 异常售后详情 </p>
  			<div class="tab_panel">
				<ul class="tab">
					<li class="curr">
						<span>异常售后申请</span>
					</li>
					<li>
						<span>订单日志</span>
					</li>
				</ul>
	<div class="tab_content"> 
    <div class="modify"> 
      <div class="add_detail_box">
           <div class="blank10"></div>
           <div class="afterSale_dt">
            <span>优购订单号：${saleApplyBill.orderNo?default('')}</span>
            <span>外部订单号：${orderSub.outOrderId?default('')}</span>
            <span>售后申请单：${saleApplyBill.applyNo?default('')}</span>        
            <span>售后申请单状态：${statusName?default('')}</span>
           </div>
           <#if abnoramalBill??>
           <div class="afterSale_dc afterSale_dc1">
             <h5><strong>异常类型：<#if abnoramalBill.exceptionType??&&abnoramalBill.exceptionType=='LOST_GOODS'>丢件
                                   <#elseif abnoramalBill.exceptionType??&&abnoramalBill.exceptionType=='DRAIN_GOODS'>漏发货
                                   <#elseif abnoramalBill.exceptionType??&&abnoramalBill.exceptionType=='ERROR_GOODS'>错发货
                                   <#elseif abnoramalBill.exceptionType??&&abnoramalBill.exceptionType=='QUALITY_GOODS'>质量问题投诉
                                   <#elseif abnoramalBill.exceptionType??&&abnoramalBill.exceptionType=='REJECT_GOODS'>拒收未质检
                                   <#else>-</#if>
             </strong></h5>
           </div>
           </#if>

           <div class="panel-body pd10 c5f" id="ckPanel">
		     <p><span><lable>登记：</label>${saleApplyBill.createor?default('')}（${saleApplyBill.createTime?string('yyyy-MM-dd HH:mm:ss')}）${saleApplyBill.remark?default('')}</span></p>
		     <#if abnoramalBill??>
             <#if abnoramalBill.reviewer??><p><span><lable>审核：</label>${abnoramalBill.reviewer?default('')}<#if abnoramalBill.auditTime??>（${abnoramalBill.auditTime?string('yyyy-MM-dd HH:mm:ss')}）</#if>${abnoramalBill.auditRemark?default('')}</span></p></#if>
             </#if>
           </div>

           <div class="afterSale_dc afterSale_dc1">
                   <table class="afterSale_table afterSale_table2">
                		<thead>
                          <tr>
                            <th style="text-align:center;">商品名称</th>
                            <th style="text-align:center;">商品规格</th>
                            <th style="text-align:center;">商品款色编码</th>
                            <th style="text-align:center;">登记数量</th>
                            <th style="text-align:center;">成交价</th>
                          </tr>
                        </thead>
                        <tbody>
                        <#if goodList??>
                        <#list goodList as item >
                		  <tr>
                		    <td><div style="width:98%;float:left;word-break:break-all;text-align:left;"><a href="${item['url']?default('')}" target="_blank">${item['prodName']?default('')}</a></div></td>
                			<td class="ft-cl-r">${item['commoditySpecificationStr']!''}</td>
                			<td class="ft-cl-r">${item['levelCode']!''}</td>
                			<td class="ft-cl-r">${item['commodityNum']!''}</td>
                			<td class="ft-cl-r">${item['prodTotalAmt']!''}</td>
                			</tr>
                	     </#list>
                        <#else>
                        	<tr>
                        	<td colSpan="5">
                        	    抱歉，没有您要找的数据 
	                        </td>
	                        </tr>
                        </#if>
                      </tbody>                    
                    </table>
			</div>
			
			<#if saleApplyBill.status=='SALE_APPLY'>
			<div class="afterSale_dc afterSale_dc1">
             <h5><strong>还未进行处理</strong></h5>
            </div>
            <#elseif saleApplyBill.status=='SALE_CANCEL'>
            <div class="afterSale_dc afterSale_dc1">
             <h5><strong>售后申请单已作废</strong></h5>
            </div>
            <#elseif saleApplyBill.status=='SALE_COMFIRM'||saleApplyBill.status=='SALE_REFUND_APPLY'||saleApplyBill.status=='SALE_REFUND_YES'||saleApplyBill.status=='SALE_REPLACEMENT_GOODS'>
			<div class="afterSale_dc afterSale_dc1">
             <h5><strong>售后类型：<#if abnoramalBill??&&abnoramalBill.isFreeOrder??&&abnoramalBill.isFreeOrder=='YES'>免单</#if>
                                   <#if saleApplyBill.saleType??&&saleApplyBill.saleType=='QUIT_GOODS'>退货
                                   <#elseif saleApplyBill.saleType??&&saleApplyBill.saleType=='TRADE_GOODS'>换货
                                   <#elseif saleApplyBill.saleType??&&saleApplyBill.saleType=='REPAIR_GOODS'>维修
                                   <#elseif saleApplyBill.saleType??&&saleApplyBill.saleType=='RETURN_GOODS'>退回
                                   <#elseif saleApplyBill.saleType??&&saleApplyBill.saleType=='REISSUE_GOODS'>补发
                                   <#elseif saleApplyBill.saleType??&&saleApplyBill.saleType=='REFUND_GOODS'>退款
                                   <#else>-</#if>
             </strong></h5>
            </div>
            <#else>
			</#if>
            <#if newOrderNo??>
			<div class="panel-body pd10 c5f" id="ckPanel">
			     <p><span><lable>优购订单号：</label>${newOrderNo?default('')}（补）</span>&nbsp;&nbsp;&nbsp;&nbsp;
			        <span><lable>订单状态：</label>${newOrderSub.baseStatusName?default('')}</span>&nbsp;&nbsp;&nbsp;&nbsp;
			        <span><lable>快递信息：</label>${newOrderSub.logisticsName?default('')}（${newOrderSub.expressOrderId?default('')}）</span>
			     </p>
                 <p><span><lable>收货信息：</label>${newOrderConsigneeInfo.userName?default('')},${newOrderConsigneeInfo.mobilePhone?default('')}</span>&nbsp;&nbsp;&nbsp;&nbsp;
                    <span><lable>收货地址：</label>${newOrderConsigneeInfo.provinceName?default('')}${newOrderConsigneeInfo.cityName?default('')}${newOrderConsigneeInfo.areaName?default('')}&nbsp;&nbsp;${newOrderConsigneeInfo.consigneeAddress?default('')},${newOrderConsigneeInfo.zipCode?default('')}</span>
                 </p>
            </div>
            
            <div class="afterSale_dc afterSale_dc1">
                   <table class="afterSale_table afterSale_table2">
                		<thead>
                          <tr>
                            <th style="text-align:center;">商品名称</th>
                            <th style="text-align:center;">商品规格</th>
                            <th style="text-align:center;">商品款色编码</th>
                            <th style="text-align:center;">优购价</th>
                            <th style="text-align:center;">成交价</th>
                            <th style="text-align:center;">购买数量</th>
                          </tr>
                        </thead>
                        <tbody>
                        <#if newGoodList??>
                        <#list newGoodList as item >
                		  <tr>
                		    <td><div style="width:98%;float:left;word-break:break-all;text-align:left;"><a href="${item['url']?default('')}" target="_blank">${item['prodName']?default('')}</a></div></td>
                			<td class="ft-cl-r">${item['commoditySpecificationStr']!''}</td>
                			<td class="ft-cl-r">${item['levelCode']!''}</td>
                			<td class="ft-cl-r">${item['prodUnitPrice']!''}</td>
                			<td class="ft-cl-r">${item['prodTotalAmt']!''}</td>
                			<td class="ft-cl-r">${item['commodityNum']!''}</td>
                			</tr>
                	     </#list>
                        <#else>
                        	<tr>
                        	<td colSpan="6">
                        	    抱歉，没有您要找的数据 
	                        </td>
	                        </tr>
                        </#if>
                      </tbody>                    
                    </table>
			</div>
            </#if>
     </div>   
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
						                <td>
						                    <#if item.logType ?? && item.logType == 1>操作日志</#if>
						                	<#if item.logType ?? && item.logType == 2>售后日志</#if>
						                	<#if item.logType ?? && item.logType == 3>退款日志</#if>
						                	<#if item.logType ?? && item.logType == 4>正常流转日志</#if>
						                </td>
						                <td><#if item.createTime ??>${item.createTime?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
						                <td><#if item.operateUser ??>${item.operateUser}</#if></td>
						                <td><#if item.behavioutDescribe ??>${item.behavioutDescribe}</#if></td>
						                <td><#if item.remark ??>${item.remark!''}</#if></td>
						                <td class="td0">
						                  <#if item.operateResult ?? && item.operateResult == 1>成功<#else>失败</#if>
						                </td>
						                </tr>
				                	</#list>
				                </#if>
			                </tbody>
		                </table>
					</div>
</div>  
</div>   
</div>
</div>
</div>
</body>
<script type="text/javascript">
$(function(){	
	$(".tab li").click(function(){
	  var index=$(".tab li").index(this);
	  $(this).addClass("curr").siblings().removeClass("curr");
	  $(".tab_content>div").hide();
	  $(".tab_content>div").eq(index).show();
	});
})
</script>
</html>

