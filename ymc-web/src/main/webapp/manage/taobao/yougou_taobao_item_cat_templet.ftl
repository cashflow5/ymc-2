<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-淘宝品牌设置</title>
<script type="text/javascript" src="${BasePath}/yougou/js/ymc.common.js?${style_v}"></script>
<script>
	var taobaoYougouItemCatTemplet = {};
	taobaoYougouItemCatTemplet.basePath  ="${BasePath}";
</script>
<script type="text/javascript" src="${BasePath}/yougou/js/manage/taobao/taobao.yougou.itemcat.templet.js?${style_v}"></script>
</head>
<body>
<div class="main_container">
		<div class="normal_box">
			<p class="title site">当前位置：商家中心 &gt; 淘宝商品导入 &gt; 查看类目模板</p>
			<div class="tab_panel  relative">
			<p style="position:absolute;top:0;right:0;"></p>
				<div class="tab_content"> 
					<!--搜索start-->
					<div class="search_box">
						<form action="goYougouTaobaoItemCat.sc" id="queryVoform" name="queryVoform" method="post">
							<p>
								<span>
									<label style="width:60px;">优购分类：</label>
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
								<span style="margin-left:10px;" id="taobaoSelt">
									<label style="width:60px;">淘宝分类：</label>
									<select id="selt1" style="width:160px;">
										<option value="" selected="selected">一级分类</option>
										<#list taobaoTree as item>
								    		<option value="${(item.cid)!""}">${(item.name)!""}</option>
								    	</#list>
									</select>
									<select id="selt2" style="width:150px;">
										<option value="" selected="selected">二级分类</option>
									</select>
								</span>
								<input type="hidden" name="taobaoCid" id="taobaoCid">
								<span style="padding-left:10px;">
									<a class="button" id="searchBtn" onclick="taobaoYougouItemCatTemplet.subform()" ><span>搜索</span></a>
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