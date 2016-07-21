<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-质检查询</title>
</head>
<body>
	<div class="main_container">
		<div class="normal_box">
		<p class="title site">当前位置：商家中心 &gt; 售后 &gt; 质检查询</p>
		<div class="tab_panel">
			<div class="tab_content">

				<fieldset class="x-fieldset x-fieldset-default">
						<legend class="x-fieldset-header">
						<a class="x-tool-toggle"></a>
						<span class="x-fieldset-header-text">基本信息</span>
						<div class="x-clear"></div>
						</legend>
						<div class="x-fieldset-body">
						   <div class="detail_box normal">
								<ul>
								    <li style="width:280px;">订单号：${vo.orderSubNo?default('')}</li>
									<li style="width:280px;">货品条码：${vo.qaInsideCode?default('')}</li>
									<li style="width:280px;">状态：
								        <#if returnQaExt.zj_handle_status??&&returnQaExt.zj_handle_status==1>待维修
                                        <#elseif returnQaExt.zj_handle_status??&&returnQaExt.zj_handle_status==2>维修中
                                        <#elseif returnQaExt.zj_handle_status??&&returnQaExt.zj_handle_status==3>已维修待退回
                                        <#elseif returnQaExt.zj_handle_status??&&returnQaExt.zj_handle_status==4>待退回
                                        <#elseif returnQaExt.zj_handle_status??&&returnQaExt.zj_handle_status==5>已退回
                                        <#elseif returnQaExt.zj_handle_status??&&returnQaExt.zj_handle_status==6>待转为正常质检
                                        <#elseif returnQaExt.zj_handle_status??&&returnQaExt.zj_handle_status==7>已转为正常质检</#if></td></li>
									<li style="width:280px;">质检日期：<#if returnQaDetail??&&returnQaDetail.qa_apply_date??>${returnQaDetail.qa_apply_date?string("yyyy-MM-dd HH:mm:ss")}</#if></li>
									<#if returnQaExt??&&returnQaExt.zj_end_repair_date??><li style="width:280px;">维修日期：${returnQaExt.zj_end_repair_date?string("yyyy-MM-dd HH:mm:ss")}</li></#if>
									<#if returnQaExt??&&returnQaExt.zj_return_date??><li style="width:280px;">退回日期：${returnQaExt.zj_return_date?string("yyyy-MM-dd HH:mm:ss")}</li></#if>
									<#if changeOrderSubNo??><li style="width:280px;">换货单号：${changeOrderSubNo?default('')}</li></#if>
								</ul>
							</div>
						</div>
					</fieldset>
					
					<#if (!vo.csConfirmStatus??)||vo.csConfirmStatus=='1'||vo.csConfirmStatus=='2'>
					<fieldset class="x-fieldset x-fieldset-default">
						<legend class="x-fieldset-header">
						<a class="x-tool-toggle"></a>
						<span class="x-fieldset-header-text">配送信息</span>
						<div class="x-clear"></div>
						</legend>
						<div class="x-fieldset-body">
						   <div class="detail_box normal">
								<ul>
								    <li style="width:280px;">寄出快递单号：<#if returnQaExt??&&returnQaExt.return_express_code??>${returnQaExt.return_express_code?default('')}</#if></li>
									<li style="width:280px;">快递公司：<#if returnQaExt??&&returnQaExt.return_logistics_name??>${returnQaExt.return_logistics_name?default('')}</#if></li>
									<li style="width:280px;">费用：<#if returnQaExt??&&returnQaExt.retrun_fee??>${returnQaExt.retrun_fee?default('')}元</#if>
									    <#if returnQaExt??&&returnQaExt.return_is_delivery??&&returnQaExt.return_is_delivery==1>（到付）<#elseif returnQaExt??&&returnQaExt.return_is_delivery??&&returnQaExt.return_is_delivery==0>（非到付）</#if></li>
									<li style="width:280px;">寄出日期：<#if returnQaExt??&&returnQaExt.return_date??>${returnQaExt.return_date?string("yyyy-MM-dd HH:mm:ss")}</#if></li>
									<li style="width:600px;">备注：<#if returnQaExt??&&returnQaExt.return_remark??>${returnQaExt.return_remark?default('')}</#if></li>
								</ul>
							</div>
						</div>
					</fieldset>
					</#if>
					
					<fieldset class="x-fieldset x-fieldset-default">
						<legend class="x-fieldset-header">
						<a class="x-tool-toggle"></a>
						<span class="x-fieldset-header-text">收货人信息</span>
						<div class="x-clear"></div>
						</legend>
						<div class="x-fieldset-body">
						   <div class="detail_box normal">
								<ul>
								    <li style="width:280px;">收货人：<#if returnQaDetail??&&returnQaDetail.consignee??>${returnQaDetail.consignee?default('')}</#if></li>
									<li style="width:280px;">电话：<#if returnQaDetail??&&returnQaDetail.consignee_tel??>${returnQaDetail.consignee_tel?default('')}</#if></li>
									<li style="width:280px;">手机：<#if returnQaDetail??&&returnQaDetail.consignee_phone??>${returnQaDetail.consignee_phone?default('')}</#if></li>
									<li style="width:600px;">地址：<#if returnQaDetail??&&returnQaDetail.consignee_address??>${returnQaDetail.consignee_address?default('')}</#if></li>
								</ul>
							</div>
						</div>
					</fieldset>
					
				
					<fieldset class="x-fieldset x-fieldset-default">
						<legend class="x-fieldset-header">
						<a class="x-tool-toggle"></a>
						<span class="x-fieldset-header-text">顾客回寄货品</span>
						<div class="x-clear"></div>
						</legend>
						<div class="x-fieldset-body">
							<table id="qadetails" class="goodsDetailTb">
							    <col width="280" />
                                <col width="100" />
                                <col width="100" />
                                <col width="80" />
                                <col width="280" />
                                <col width="70" />
								<thead>
									<tr class="bdr">
										<th>商品名称</th>
										<th>订单号</th>
										<th>快递单号</th>
										<th>快递公司</th>
										<th>质检描述</th>
										<th>质检结果</th>
									</tr>
								</thead>
								<tbody>
									<#if returnQaDetail??>
										<tr>
											<td><div style="width:98%;float:left;word-break:break-all;text-align:left;"><img src="${returnQaDetail.picSmall?default('')}" align ="left" width="40" height="40" /><a href="${returnQaDetail.url?default('')}" target="_blank">${returnQaDetail.qa_goods_name?default('')}</a></div></td>
											<td>${vo.orderSubNo?default('')}</td>
											<td>${returnQaDetail.express_code?default('')}</td>
											<td>${returnQaDetail.express_name?default('')}</td>
											<td>${returnQaDetail.qa_description?default('')}</td>
											<td><#if returnQaDetail.is_passed??&&returnQaDetail.is_passed=='1'>通过<#elseif returnQaDetail.is_passed??&&returnQaDetail.is_passed=='0'>不通过</#if></td>
										</tr>
									</#if>
								</tbody>
							</table>
						</div>
					</fieldset>
					<p class="blank20"></p>
			</div>
		</div>
		</div>
	</div>
</body>
</html>
