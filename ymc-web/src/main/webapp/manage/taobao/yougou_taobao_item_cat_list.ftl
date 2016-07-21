<table class="list_table">
	<thead>
		<tr>
			<th width="5%"></th>
			<th width="10%">淘宝店铺</th>
			<th width="25%">淘宝分类</th>
			<th width="25%">优购分类</th>
			<th width="15%">保存时间</th>
			<th>操作</th>
		</tr>
		<#if pageFinder??&&pageFinder.data??>
			<tr class="do_tr">
				<td colspan="17" style="padding:0;text-align:left;">
					<div class="tb_dobox">
						<div class="dobox" style="margin-left: 12px;">
							<label>全选&nbsp;&nbsp;<input class="chkall" type="checkbox" /> </label>
							<a href="javascript:;" onclick="taobaoYougouItemCat.del()">删除分类设置</a> 
							<a href="javascript:taobaoYougouItemCat.downloadItem()">下载淘宝商品</a>
							&nbsp;&nbsp;&nbsp;
						</div>
						<div class="page"> 
							<#if pageFinder ??>
								<#import "/manage/widget/page.ftl" as page>
								<@page.queryForm formId="queryVoform"/>
							</#if>
						</div>
					</div>
				</td>
			</tr>
		</#if>
	</thead>
	<tbody id="tbody">
		<#if pageFinder??&&pageFinder.data??>
			<#list pageFinder.data as item>
				<!--全选操作部分-->
				<tr>
					<td>
						<label>
							<input type="checkbox" name="bindId" value="${item.id!''}" />&nbsp;
						</label>
					</td>
					<td>${item.nickName!''}</td>
					<td>${item.taobaoCatFullName!''}</td>
					<td>${item.yougouCatFullName!''}</td>
					<td style="width:60px;">${item.operated!''}</td>
					<td>
						<a href="goUpdateTaobaoYougouPro.sc?bindId=${item.id!''}">修改属性</a> | 
						<a href="javascript:taobaoYougouItemCat.del('${item.id!''}')">删除</a>|
						<a href="javascript:taobaoYougouItemCat.downloadItem('${item.id!''}')">下载淘宝商品</a>
					</td>
				</tr>
			</#list>
		<#else>
			<tr class="do_tr">  <!--do_tr 这行客户端会自动显示隐藏-->
				<td class="td-no" colspan="17">
						没有相关数据
				</td>
			</tr>
		</#if>	
	</tbody>
</table>
					<!--列表end--> 
<#if pageFinder??&&pageFinder.data??>
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