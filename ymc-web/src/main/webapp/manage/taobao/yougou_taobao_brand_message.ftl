<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-淘宝品牌设置</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/ymc.common.js?${style_v}"></script>
<script>
	var taobaoYougouBrand = {};
	taobaoYougouBrand.basePath  ="${BasePath}";
</script>
<script type="text/javascript" src="${BasePath}/yougou/js/manage/taobao/taobao.yougou.brand.js?${style_v}"></script>
</head>
<body>
<div class="main_container" style="width:600px;">
		<div>
			<div class="tab_panel  relative">
			<p style="position:absolute;top:0;right:0;"></p>
				<div class="tab_content"> 
					<!--搜索end-->
					<!--列表start-->
					<form id="bindForm" method="post">
					<table class="list_table">
						<thead>
							<tr>
								<th>淘宝店铺</th>
								<th>淘宝品牌</th>
								<th>优购品牌</th>
							</tr>
						</thead>
						<tbody id="tbody">
							<#if pageFinder??&&pageFinder.data??>
								<#list pageFinder.data as item>
									<!--全选操作部分-->
									<tr>
										<input type="hidden" name="id" value="${item.id!''}">
										<td>${item.nickName!''}</td>
										<td>${item.taobaoBrandName!''}</td>
										<td align="center">
											<#if yougouBrands??>
												<select name="yougouBrandNos">
													<#list yougouBrands as brand>
														<option name="yougouBrandNo" value="${brand.brandNo}" <#if item.yougouBrandNo??&&brand.brandNo==item.yougouBrandNo>selected="true"</#if>>${brand.brandName}</option>
													</#list>
												</select>
											</#if>
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
					</form>
				</div>
			</div>
			<div style="text-align:center;margin-bottom:50px;"><a class="button" id="searchBtn" onclick="taobaoYougouBrand.bind()" ><span>&nbsp;&nbsp;保存设置&nbsp;&nbsp;</span></a></div>
		</div>
</div>
</body>
</html>