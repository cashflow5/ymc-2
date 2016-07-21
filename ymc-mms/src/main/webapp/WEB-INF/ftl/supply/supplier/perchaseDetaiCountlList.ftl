<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#include "../../yitiansystem/yitiansystem-include.ftl">
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ytsys-comment.js"></script>
<script type="text/javascript" src="${BasePath}/js/supply/supplier/supplierCommodity.js"></script>
<script>
function addMemo(purchaseId) {
	openwindow('${BasePath}/supply/manage/purchase/addMomeUI.sc?purchaseId='+purchaseId,630,320,'添加备注');
}
</script>
<title>无标题文档</title>
</head><body>
<div class="container"> 
	<!--工具栏start-->
	<div class="toolbar">
		<div class="t-content">
			<div class="btn" onclick="addMemo('${purcharse.id}');" > <span class="btn_l" ></span> <b class="ico_btn add"></b> <span class="btn_txt"> 添加备注 </span> <span class="btn_r"></span> </div>
			<div class="line"></div>
			<div class="btn" onclick="gotolink('findPurchaseCount.sc?currentPage=${currentPage?default(1)}');" > <span class="btn_l" ></span> <b class="ico_btn back"></b> <span class="btn_txt"> 返回 </span> <span class="btn_r"></span> </div>
		</div>
	</div>
	<!--工具栏end-->
	
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr"><span>采购统计详情</span></li>
			</ul>
		</div>
		<!--当前位置end-->
		<div class="modify">
			<form action="findPurchaseDetial.sc" name="perchaseDetialForm" id="perchaseDetialForm" method="post">
				<!--列表start-->
				<div class="detail_top_box">
					<ul  >
						<#if purcharse??>
						<li>单据编号：${purcharse.purchaseCode?default("&nbsp;")}</li>
						<li>来源单号：${purcharse.sourceCode?default("&nbsp;")}</li>
						<li>单据状态：
							<#if purcharse.status??&&purcharse.status==1>
							已确认
							<#elseif purcharse.status??&&purcharse.status==0>
							未确认
							<#elseif purcharse.status??&&purcharse.status==-1>
							作废
							<#elseif purcharse.status??&&purcharse.status==2>
							新建
							</#if> </li>
						<li>供应商：
							
							<#if purcharse.supplier??&&(purcharse.supplier.supplier)??>
							${purcharse.supplier.supplier}
							</#if> </li>
						<li>采购日期：
							<#if purcharse.purchaseDate??>
							${purcharse.purchaseDate?string("yyyy-MM-dd")}
							</#if> </li>
						<li>到货日期：  
							<#--
							<#if purcharse.createDate??>
							${purcharse.createDate?string("yyyy-MM-dd")}
							</#if>
							-->
							<#if purcharse.deliveryDate??>
							${purcharse.deliveryDate?default("")}
							</#if> </li>
						<li>合计数量：
							${purcharse.amount?default("0")}</li>
						<li>合计金额：
							${purcharse.totalPrice?default("0.00")}</li>
						<li>采购人：
							${purcharse.purchaser?default("&nbsp;")}</li>
						<li>建档人：
							${purcharse.createPeople?default("&nbsp;")}</li>
						<li>仓库：
							<#if (purcharse.warehouse.warehouseName)??>
							${purcharse.warehouse.warehouseName}
							<#else>
							&nbsp;
							</#if></li>
						<li>备注：
							${purcharse.memo?default("&nbsp;")}</li>
						<li>POS来源编码：
							${purcharse.posSourceNo?default("&nbsp;")}</li>
						<li>POS来源单号：
							
							${purcharse.posPurchaseNo?default("&nbsp;")} </li>
						</#if>
					</ul>
				</div>
				<table cellpadding="0" cellspacing="0"  class="list_table">
					<thead>
						<tr>
							<th>商品编码</th>
							<th style="width:250px;">商品名称</th>
							<th>分类名称</th>
							<th>品牌名称</th>
							<th>预计到货日期</th>
							<th>商品款号</th>
							<th>规格</th>
							<th style="text-align:center;">单位</th>
							<th style="text-align:center;">数量</th>
							<th>折扣</th>
							<th style="text-align:right;">总金额</th>
							<th style="width:200px;">备注</th>
						</tr>
					</thead>
					
					<tbody id="tbd">
					<#if pageFinder??&&pageFinder.data??>
					<#list pageFinder.data as pu>
					<tr>
						<td>${pu.commodityCode?default("&nbsp;")}</td>
						<td>${pu.commodityName?default("&nbsp;")}</td>
						<td>${pu.categoryName?default("&nbsp;")}</td>
						<td>${pu.brandName?default("&nbsp;")}</td>
						<td> <#if pu.purchase.planTime??>
							${pu.purchase.planTime?date}		
							</#if> </td>
						<td>${pu.commodity.styleNo?default("&nbsp;")}</td>
						<td>${pu.specification?default("&nbsp;")}</td>
						<td  style="text-align:center;">${pu.unit?default("&nbsp;")}</td>
						<td  style="text-align:center;">${pu.purchaseQuantity?default("0")}</td>
						<#--
						<td>${pu.purchasePrice?default("0.00")}</td>
						-->
						<td style="text-align:center;">${pu.deductionRate?default("1")}</td>
						<td style="text-align:right;"> ${pu.purchaseTotalPrice?default(0.00)} </td>
						<td>
							<div style="width:99%;height:30px;overflow:hidden;"> ${pu.remark?default("&nbsp;")} </div>
						</td>
					</tr>
					</#list> 
					<!--<tr>
						<th> 总计: </th>
						<th>总数量</th>
						<td> <#if detailVo??&&detailVo.amount??>
							<input type="text" value="${detailVo.amount}" style="width:50px;border:1px solid #E6E6E6;color:red;" readOnly>
						</td>
						<#else>
						<input type="text" value="0" style="width:50px;border:1px solid gray;color:red;" readOnly>
						
							</td>
						</#if>
						<th>总金额</th>
						<td> <#if detailVo??&&detailVo.totalPrice??>
							<input type="text" value="${detailVo.totalPrice}" style="width:80px;border:1px solid #E6E6E6;color:red;text-align:right;" readOnly>
						</td>
						<#else>
						<input type="text" value="0" style="width:80px;border:1px solid gray;color:red;text-align:right;" readOnly>
						
							</td>
						</#if>
						
							</td>
						<th >当前页<br/>
							总计：</th>
						<th>总数量</th>
						<td> <#if detailVo??&&detailVo.totalPageCount??>
							<input type="text" value="${detailVo.totalPageCount}" style="width:50px;border:1px solid #E6E6E6;color:red;" readOnly>
						</td>
						<#else>
						<input type="text" value="0" style="width:50px;border:1px solid gray;color:red;" readOnly>
						
							</td>
						</#if>
						
							</td>
						<td>&nbsp;</td>
						<th>总金额：</th>
						<td> <#if detailVo??&&detailVo.totalPagePrice??>
							<input type="text" value="${detailVo.totalPagePrice}" style="width:80px;border:1px solid #E6E6E6;color:red;text-align:right;" readOnly>
						</td>
						<#else>
						<input type="text" value="0" style="width:80px;border:1px solid #E6E6E6;color:red;text-align:right;" readOnly>
						
							</td>
						</#if>
						
							</td>
						<td>&nbsp;</td>
					</tr>
					-->
					<tr>
						<td  style="text-align:left;">当前页总计：</td>
						<td></td><td></td><td></td><td></td><td></td><td></td>
						<td style="text-align:center;color:red;font-weight:bold;">
						${purcharse.amount?default('0')}
						<!-- <input type="text" value="${purcharse.amount?default('0')}" style="font-weight:bold; background:none;border:none;color:red;width:50px; text-align:center;"  readonly="readonly"></td>-->
						<td></td>
						<td  style="text-align:right;color:red;font-weight:bold;">
							<!--<input type="text" value="${purcharse.totalPrice?default('0.00')}" style="font-weight:bold; background:none;border:none;color:red;width:80px;text-align:right;" readonly="readonly">-->
							${purcharse.totalPrice?default('0.00')}
						</td>
						<td></td>
					</tr>
					<#else>
					<tr>
						<td class="td-no" colspan="11"> 抱歉，没有您要找的数据 </td>
					</tr>
					</#if>
					
							</tbody>
					
				</table>
				<!--分页采购单号--> 
				<#if detailVo??&&detailVo.purchaseId??>
				<input type="hidden" value="${detailVo.purchaseId}" name="purchaseId">
				<#else>
				<input type="hidden" value="" name="purchaseId">
				</#if>
				<#if detailVo??&&detailVo.amount??>
				<input type="hidden" value="${detailVo.amount}" name="amount">
				<#else>
				<input type="hidden" value="" name="amount">
				</#if>
				<#if detailVo??&&detailVo.totalPrice??>
				<input type="hidden" value="${detailVo.totalPrice}" name="totalPrice">
				<#else>
				<input type="hidden" value="" name="totalPrice">
				</#if>
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
