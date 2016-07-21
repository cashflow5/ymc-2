<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#include "../../yitiansystem/yitiansystem-include.ftl">
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ytsys-comment.js"></script>
<script type="text/javascript" src="${BasePath}/js/supply/updateRemark.js"></script>
<title>无标题文档</title>
<script>
	var path="${BasePath}";
	function selectSupplier(perchaseDetialIds,prechaseIds,currentPage){
      openwindow(path+"/supply/supplier/PerchaseOrder/toUpdateRemark.sc?prechaseId="+prechaseIds+"&perchaseDetialId="+perchaseDetialIds+"&currentPage="+currentPage,530,300,"修改备注");
	}
</script>
</head><body>
<div class="container"> 
	<!--工具栏start-->
	<div class="toolbar">
		<div class="t-content">
			<div class="btn"   > <span class="btn_l" ></span> <b class="ico_btn print"></b> <span class="btn_txt">
			<a href="${BasePath}/supply/supplier/PerchaseOrder/printPurchase.sc?perchaseId=${purchase.id?default(0)}" target="_blank">打印预览 </a>
			 </span> <span class="btn_r"></span> </div>
			<div class="line"></div>
			<div class="btn" onclick="goback();" > <span class="btn_l" ></span> <b class="ico_btn back"></b> <span class="btn_txt"> 返回 </span> <span class="btn_r"></span> </div>
		</div>
	</div>
	<!--工具栏end-->
	
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr"><span>未完采购详情</span></li>
				<li> <span><a href="${BasePath}/supply/supplier/PerchaseOrder/queryPurchaseLog.sc?purchaseId=${(purchase.id)?default(0)}&purcharsePage=${purcharsePage?default(1)}" class="btn-onselc">采购日志</a></span> </li>
			</ul>
		</div>
		<!--当前位置end-->
		<div class="modify">
			<form action="findDetailById.sc" name="perchaseDetialForm" id="perchaseDetialForm" method="post">
					<p>
						<label>商品名称：</label>
						<input type="text" value='${puVo.commodityName?default("")}'  name="commodityName" style="width:115px;" />
						<label >品牌名称：</label>
						<input type="text" value='${puVo.brandName?default("")}'  name="brandName" style="width:112px;" />
						<label >分类名称：</label>
						<input type="text" value='${puVo.categoryName?default("")}'  name="categoryName" style="width:112px;" />
					</p>
					<p>
						<label>商品款号：</label>
						<input type="text" value='${puVo.styleNo?default("")}'  name="styleNo" style="width:115px;" />
						<input type="submit" value="搜索" class="btn-add-normal" id="submitid" />
						<#--<input type="button" value="导出Excel" class="btn-add-normal-4ft" onclick="excelSubmit();" />-->
					</p>
				<!--列表start-->
				<div class="detail_top_box">
					<ul  >
						<#if purchase??>
						<li>单据编号：${purchase.purchaseCode?default("&nbsp;")}</li>
						<li>来源单号：${purchase.sourceCode?default("&nbsp;")} </li>
						<li>单据状态：
							<#if purchase.status??&&purchase.status==1>已确认</#if> </li>
						<li>采购类型：
							<#if purchase.type??>
								<#if purchase.type==102>自购固定价结算
									<#elseif purchase.type==103>自购配折结算
									<#elseif purchase.type==106>招商底价代销
									<#elseif purchase.type==107>招商扣点代销 
									<#elseif purchase.type==108>招商配折结算
								</#if>
							</#if> 
							</li>
						<li>审批人：
							${purchase.approver?default("&nbsp;")} </li>
						<li>收货人：${purchase.receivePeople?default("&nbsp;")}</li>
						<li> 合计数量：
							${purchase.amount?default("0")} </li>
						<li>合计金额：
							${purchase.totalPrice?default("0.00")} </li>
						<li>采购日期：
							<#if purchase.purchaseDate??>
							${purchase.purchaseDate?string("yyyy-MM-dd")}
							</#if> </li>
						<li>预计到货日期：
							<#if purchase.deliveryDate??>
							${purchase.planTime?date}
							</#if> </li>
						<li>收货日期：
							<#if purchase.deliveryDate??>
							${purchase.deliveryDate?default("")}
							</#if> </li>
						<li>仓库：
							<#if (purchase.warehouse.warehouseName)??>
							${purchase.warehouse.warehouseName?default("")}
							</#if> </li>
						<li>备注：${purchase.memo?default("&nbsp;")} </li>
						<li>物料价：
						    <#if (purchase.wlFlags)??&&purchase.wlFlags=='1'>
								有
								<#else>
								无
							</#if> </li>
						</#if>
					</ul>
				</div>
				<table cellpadding="0" cellspacing="0" class="list_table">
					<thead>
						<tr>
							<th>分类名称</th>
							<th >品牌名称</th>
							<th>商品名称</th>
							<th>商品款号</th>
							<th>规格</th>
							<th>采购数量</th>
							<th>入库数量</th>
							<th>未入库数量</th>
							<th>总额</th>
							<th>入库日期</th>
							<th>采购类型</th>
							<th>备注</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody id="tbd">
					<#if pageFinder??&&pageFinder.data??>
					<#list pageFinder.data as pu>
					<tr>
						<td>${pu.categoryName?default("&nbsp;")}</td>
						<td>${pu.brandName?default("&nbsp;")}</td>
						<td>${pu.commodityName?default("&nbsp;")}</td>
						<td>${pu.commodity.styleNo?default("&nbsp;")}</td>
						<td>${pu.specification?default("&nbsp;")}</td>
						<#--
						<td>${pu.purchasePrice?default("0.00")}</td>
						-->
						<td>${pu.purchaseQuantity?default("&nbsp;")}</td>
						<td>${pu.intostoreQuantity?default("0")}</td>
						<td> &nbsp;
							<#if pu.purchaseQuantity??&&pu.intostoreQuantity??>
							${pu.purchaseQuantity-pu.intostoreQuantity}
							<#elseif pu.purchaseQuantity??>
							${pu.purchaseQuantity}
							<#else>
							-${pu.intostoreQuantity}}
							</#if> </td>
						<td>${pu.purchaseTotalPrice?default("0.00")}</td>
						<td>${lastDate?default("&nbsp;")}</td>
						<td> <#if pu??&&pu.purchaseType??&&pu.purchaseType==102>
							自购
							</#if>
							<#if pu??&&pu.purchaseType??&&pu.purchaseType==106>
							比例代销
							</#if>
							<#if pu??&&pu.purchaseType??&&pu.purchaseType==107>
							协议代销
							</#if> </td>
						<td >
							<div style="width:99%;height:30px;overflow:hidden;"> ${pu.remark?default("&nbsp;")} </div>
						</td>
						<td> <a href="#" onclick="selectSupplier('${pu.id?default(0)}','${perchaseId?default(-1)}','${currentPage?default(1)}')">更新备注</a> 
						</td>
					</tr>
					</#list>
					<#else>
					<tr >
						<td  class="td-no" colspan="12"> 抱歉，没有您要找的数据 </td>
					</tr>
					</#if>
					
							</tbody>
					
				</table>
				
				<!--分页采购单号,是否入库标识-->
				<input type="hidden" value="${perchaseId}" name="perchaseId">
			</form>
		</div>
		<div class="bottom clearfix"> <#import "../../common.ftl"  as page>
			<@page.queryForm formId="perchaseDetialForm" />
		</div>
	</div>
</div>
</body>
</html>
