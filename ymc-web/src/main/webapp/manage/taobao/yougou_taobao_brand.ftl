<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-淘宝品牌设置</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
<script>
	var taobaoYougouBrand = {};
	taobaoYougouBrand.basePath  ="${BasePath}";
</script>
<script type="text/javascript" src="${BasePath}/yougou/js/manage/taobao/taobao.yougou.brand.js?${style_v}"></script>
</head>
<body>
<div class="main_container">
		<div class="normal_box">
			<p class="title site">当前位置：商家中心 &gt; 淘宝商品导入 &gt; 淘宝商品导入设置</p>
			<div class="tab_panel  relative">
			<p style="position:absolute;top:0;right:0;"></p>
				<ul class="tab">
					<li onclick="location.href='${BasePath}/taobao/goYougouTaobaoItemCat.sc'" ><span>分类对应设置</span></li>
					<li onclick="location.href='${BasePath}/taobao/goYougouTaobaoBrand.sc'"  class="curr" ><span>品牌对应设置</span></li>
				</ul>
				<div class="tab_content"> 
					<!--搜索start-->
					<div class="search_box">
						<form action="goYougouTaobaoBrand.sc" id="queryVoform" name="queryVoform" method="post">
							<p>
								<span>
									<a class="button" id="mySubmit" onclick="taobaoYougouBrand.save()"><span>新建品牌设置</span></a>
								</span>
								<span>
									<label style="width:110px;">淘宝店铺：</label>
									<input type="text" name="nickName" id="nickName" class="inputtxt" value="${params.nickName!''}" />
								</span>
								<span>
									<label style="width:110px;">品牌名称：</label>
									<input type="text" name="brandName" id="brandName" class="inputtxt" value="${params.brandName!''}" />
								</span>
								<span style="padding-left:50px;">
									<a class="button" id="searchBtn" onclick="taobaoYougouBrand.search()" ><span>搜索</span></a>
								</span>
							</p>
						</form>
					</div>
					<!--搜索end-->
					<!--列表start-->
					<table class="list_table">
						<thead>
							<tr>
								<th width="5%"></th>
								<th width="10%">淘宝店铺</th>
								<th width="15%">淘宝品牌</th>
								<th width="15%">优购品牌</th>
								<th width="15%">保存时间</th>
								<th width="15%">操作</th>
							</tr>
							<#if pageFinder??&&pageFinder.data??>
								<tr class="do_tr">
									<td colspan="17" style="padding:0;text-align:left;">
										<div class="tb_dobox">
											<div class="dobox" style="margin-left: 12px;">
												<label>全选&nbsp;&nbsp;<input class="chkall" type="checkbox" /> </label>
												<a href="javascript:;" onclick="taobaoYougouBrand.del()">删除品牌设置</a> 
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
										<td>${item.taobaoBrandName!''}</td>
										<td>${item.yougouBrandName!''}</td>
										<td style="width:60px;">${item.operated!''}</td>
										<td>
											<a href="javascript:taobaoYougouBrand.save('${item.id!''}')">修改</a> | 
											<a href="javascript:taobaoYougouBrand.del('${item.id!''}')">删除</a>
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
							<#import "/manage/widget/common.ftl" as page>
							<@page.queryForm formId="queryVoform"/>
						</#if>
					</div>
					<!--分页end-->
					</#if>
				</div>
			</div>
		</div>
</div>
</body>
</html>