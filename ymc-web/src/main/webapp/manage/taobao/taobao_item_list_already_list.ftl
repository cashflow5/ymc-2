
<table class="common_lsttbl mt10">
	<thead>
		<tr>
			<th>商品名称</th>
			<th>颜色</th>
			<th>价格</th>
			<th>下载时间</th>
			<th>导入时间</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody id="tbody" class="common_proitm">
		<#if pageFinder??&&pageFinder.data??&&(pageFinder.data?size>0)>
			<#list pageFinder.data as item>
				<!--全选操作部分-->
				<tr class="line_gap" id="${item.commodity_no!""}-1">
                	<td colspan="7"></td>
                </tr>
                <tr class="proitm_hd" id="${item.commodity_no!""}-2">
                	<td colspan="7">
                    	<span class="ml5">淘宝商品ID：${item.numIid!"—"}</span>
                    	<span class="ml15">商品编号：${item.yougouCommodityNo!"—"}</span>
                    	<span class="ml15">淘宝分类：${item.taobaoCatFullName!"—"}</span>
                    </td>
                 </tr>
                 <tr class="proitm_bd">
					<td style="width:300px;text-align:left">
						<div class="pro_pic fl">
	                	    <#if item.defaultPic??&&item.defaultPic!=''>
	                	        <img width="40" height="39" alt="" src="${item.defaultPic!''}"/>
	                	    <#else>
	                	        <img width="40" height="39" alt="" src="${BasePath}/yougou/images/nopic.jpg"/>
	                	    </#if>
                        </div>
                         <div class="txt_inf fl" style="width:200px;">
	                        	<p class="mb3" style="color:#3366cc">${item.title!''}</p>
	                            <p>商品款号：${item.yougouStyleNo!"—"}</p>
	                            <p>商家款色编码：${item.yougouSupplierCode!'—'}</p>
                          </div>
					</td>
					<td style="width:150px;">${item.yougouSpecName!''}</td>
					<td style="width:150px;text-align:left">
						<p>淘宝价:${item.price!"—"}</p>
						<p>优购价:${item.yougouPrice!"—"}</p>
					</td>
					<td style="width:115px;">${item.operated!''}</td>
					<td style="width:115px;">${item.importYougouTime!''}</td>
					<td style="width:115px;"><a href="/commodity/previewDetail.sc?commodityNo=${item.yougouCommodityNo!''}" target='_blank'>查看</a></td>
				</tr>
			</#list>
		<#else>
			<tr class="do_tr">
				<td class="td-no" colspan="17">
						没有相关数据
				</td>
			</tr>
		</#if>	
	</tbody>
</table>
<#if pageFinder??&&pageFinder.data??&&(pageFinder.data?size>0)>
<!--分页start-->
<div class="page_box">
<div class="dobox">
</div>
<#if pageFinder ??>
	<#import "/manage/widget/common4ajax.ftl" as page>
	<@page.queryForm formId="queryVoform"/>
</#if>
</div>
<!--分页end-->
</#if>
					