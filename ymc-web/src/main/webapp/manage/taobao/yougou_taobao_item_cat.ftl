<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-淘宝品牌设置</title>
<script type="text/javascript" src="${BasePath}/yougou/js/ymc.common.js?${style_v}"></script>
<script>
	var taobaoYougouItemCat = {};
	taobaoYougouItemCat.basePath  ="${BasePath}";
</script>
<script type="text/javascript" src="${BasePath}/yougou/js/manage/taobao/taobao.yougou.itemcat.js?${style_v}"></script>
</head>
<body>
<div class="main_container">
		<div class="normal_box">
			<p class="title site">当前位置：商家中心 &gt; 淘宝商品导入 &gt; 淘宝商品导入设置</p>
			<div class="tab_panel  relative">
			<p style="position:absolute;top:0;right:0;"></p>
				<ul class="tab">
					<li onclick="location.href='${BasePath}/taobao/goYougouTaobaoItemCat.sc'"  class="curr" ><span>分类对应设置</span></li>
					<li onclick="location.href='${BasePath}/taobao/goYougouTaobaoBrand.sc'" ><span>品牌对应设置</span></li>
				</ul>
				<div class="tab_content"> 
					<!--搜索start-->
					<div class="search_box">
						<form action="goYougouTaobaoItemCat.sc" id="queryVoform" name="queryVoform" method="post">
							<p>
								<span>
									<a class="button" id="mySubmit" href="toYougouTaobaoItemCat.sc" style="color:#666666"><span>新建分类设置</span></a>
								</span>
								<span>
									<label style="width:110px;">淘宝店铺：</label>
									<input type="text" name="nickName" id="nickName" class="inputtxt" />
								</span>
								<span style="margin-left:10px;">
									<label style="width:110px;">优购分类：</label>
									<select id="sel1" style="width:100px;">
										<option value="" selected="selected">一级分类</option>
										<#list yougouTree as item>
								    		<option value="${(item.structName)!""}">${(item.name)!""}</option>
								    	</#list>
									</select>
									<select id="sel2" style="width:100px;">
										<option value="" selected="selected">二级分类</option>
									</select>
									<select id="sel3" name="catId" style="width:100px;">
										<option value="" selected="selected">三级分类</option>
									</select>
								</span>
								<span style="padding-left:50px;">
									<a class="button" id="searchBtn" onclick="taobaoYougouItemCat.subform()" ><span>搜索</span></a>
								</span>
							</p>
						</form>
					</div>
					<!--搜索end-->
					<!--列表start-->
					<div id="content_list"></div>
				</div>
			</div>
		</div>
</div>
</body>
</html>