<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#include "../../yitiansystem/yitiansystem-include.ftl">
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ytsys-comment.js"></script>
<script type="text/javascript" src="${BasePath}/js/supplys/supplier/purchaseLog.js"></script>
<title>无标题文档</title>
<script>
	var path="${BasePath}";
</script>
</head><body>
<div class="container"> 
	<!--工具栏start-->
	<div class="toolbar">
		<div class="t-content">
			<div class="btn" onclick="gotolink('${BasePath}/supply/supplier/PerchaseOrder/findFulfillOrder.sc?currtentPage=${purcharsePage?default(1)}');" > <span class="btn_l" ></span> <b class="ico_btn back"></b> <span class="btn_txt"> 返回 </span> <span class="btn_r"></span> </div>
		</div>
	</div>
	<!--工具栏end-->
	
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li><span><a href="${BasePath}/supply/supplier/PerchaseOrder/findDetailById.sc?perchaseId=${purchase.id?default(0)}&purcharsePage=${purcharsePage?default(1)}" >未完采购单详情</a></span></li>
				<li class="curr"> <span>采购日志</span> </li>
			</ul>
		</div>
		<!--当前位置end-->
		<div class="modify">
			<form action="findDetailById.sc" name="perchaseDetialForm" id="perchaseDetialForm" method="post">
				
				<!--列表start-->
				<div class="detail_top_box">
					<ul  >
						<#if purchase??>
						<li>单据编号：${purchase.purchaseCode?default("&nbsp;")}</li>
						<li>来源单号：${purchase.sourceCode?default("&nbsp;")}</li>
						<li>单据状态：
							<#if purchase.status??&&purchase.status==1>已确认</#if></li>
						<li>采购类型：
							<#if purchase.type??>
							<#if purchase.type==102>自购
							<#elseif purchase.type==106>比例代销
							<#elseif purchase.type==107>协议代销
							<#elseif purchase.type==108>配折结算</#if>
							</#if> </li>
						<li>审批人：
							${purchase.approver?default("&nbsp;")} </li>
						<li>收货人：
							${purchase.receivePeople?default("&nbsp;")}</li>
						<li>合计数量：
							${purchase.amount?default("0")} </li>
						<li>合计金额：
							${purchase.totalPrice?default("0.00")} </li>
						<li> 采购日期：
							
							<#if purchase.purchaseDate??>
							${purchase.purchaseDate?string("yyyy-MM-dd")}
							</#if> </li>
						<li>预计到货日期：
							<#if purchase.deliveryDate??>
							${purchase.planTime?default("")}
							</#if> </li>
						<li>收货日期：
							<#if purchase.deliveryDate??>
							${purchase.deliveryDate?default("")}
							</#if> </li>
						<li>仓库：
							<#if (purchase.warehouse.warehouseName)??>
							${purchase.warehouse.warehouseName?default("")}
							</#if> </li>
						<li>备注： ${purchase.memo?default("&nbsp;")}</li>
						</#if>
					</ul>
				</div>
				<input type="hidden" value="${puVo.purchaseId}" id="purchaseId"/>
				<table cellpadding="0" cellspacing="0" class="list_table">
					<thead>
						<tr>
							<th style="width:5%;font-weight:bold;font-weight:bold;">序号</th>
							<th style="width:20%;font-weight:bold;font-weight:bold;">发货信息</th>
							<th style="width:20%;font-weight:bold;font-weight:bold;">入库信息</th>
							<th style="width:20%;font-weight:bold;font-weight:bold;">质检信息</th>
							<th style="width:20%;font-weight:bold;font-weight:bold;">上货架信息</th>
							<th style="width:10%;font-weight:bold;font-weight:bold;">操作</th>
						</tr>
					</thead>
					<tbody id="tbd">
					<#if pageFinder??&&pageFinder.data??>
					<#list pageFinder.data as pu>
					<tr>
						<td>${pu_index+1}</td>
						<td>
							<input type="text" value="${pu.consiqnment?default("&nbsp;")}"  style="border:none;background:none;" readonly="readonly" />
						</td>
						<td>
							<input type="text" value="${pu.storage?default("&nbsp;")}" style="border:none;background:none;" readonly="readonly"  />
						</td>
						<td>
							<input type="text" value="${pu.measure?default("&nbsp;")}" style="border:none;background:none;" readonly="readonly"  />
						</td>
						<td>
							<input type="text" value="${pu.upsupport?default("&nbsp;")}" style="border:none;background:none;" readonly="readonly"  />
						</td>
						<td>
							<input type="hidden" value="${pu.id}" />
							<a href="#" onclick="addRow(this)">添加</a>&nbsp;|&nbsp;<a href="#" onclick="delRow(this)">删除</a></td>
					</tr>
					</#list>
					<#else>
					<tr class="div-pl-list" id="dd">
						<td style="text-align:center;" colspan="6" ondblclick="dblAddRow(this)"> <font style="color:red;font-size:14px;font-weight:bold;">抱歉，没有您要找的数据,&nbsp;双击新增记录？</font> </td>
					</tr>
					</#if>
					
							</tbody>
					
				</table>
				<p class="blank10"></p>
				<input type="button"  value="保 存" onclick="save();" class="btn-add-normal" />
				<!--分页采购单号,是否入库标识-->
				<input type="hidden" value="${puVo.purchaseId}" name="perchaseId">
			</form>
		</div>
		<!--这里放置分页-->
		<div class="bottom clearfix"> <#import "../../common.ftl"  as page>
			<@page.queryForm formId="perchaseDetialForm" />
		</div>
	</div>
</div>
</body>
</html>
