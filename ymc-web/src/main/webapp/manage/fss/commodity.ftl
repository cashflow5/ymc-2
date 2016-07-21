<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>店铺装修-品牌旗舰店-优购网</title>
<script>
	var commondity = {};;
	var global = {};
	global.base = {};
	global.base.baseRoot = "${BasePath}";
</script>
<script type="text/javascript" src="${BasePath}/yougou/js/manage/fss/fss-cat-commodity.js"></script>

</head>
<body>
<div class="main_container">
	<div class="normal_box">
		<p class="title site">当前位置：商家中心 &gt; 我的店铺 &gt; 商品管理</p>
		<div class="tab_panel">
			<ul class="tab">
				<li class="curr">
					<span>商品列表</span>
				</li>
			</ul>
			<div class="tab_content"> 
				<!--搜索start-->
				<div class="search_box">
					<form name="queryVoform" id="queryVoform" action="${BasePath !}/fss/commodity/list.sc" method="post">
						<p>
							<span>
								<label>店铺名称：</label>
								<select id="selectStoreId" name="storeId">
						            <#if (storeSettingList?? && storeSettingList?size>0) >
							            <#list storeSettingList as item >
							            	<option value="${(item.shopId) !}">${(item.shopName) !}</option>
							            </#list>
						            </#if>
						        </select>
						        <input id="storeId" type="hidden" name="hiddenStoreId"  />
							</span>
							<span>
								<label>商品名称：</label>
								<input type="text" name="commodityName" id="commodityName" class="inputtxt" style="width: 250px;" />
							</span>
							<span>
								<label>款色编码：</label>
								<input type="text" name="styleNo" id="styleNo"  class="inputtxt" />
							</span>
						</p>
						<p>
							<span>
								<label>品牌：</label>
						    	<select id="brandId" name="brandNo" style="width:150px">
						           <#if (brandList?? && brandList?size>0) >
							            <#list brandList as item >
							            	<option value="${(item.brandNo) !}" brandid="${(item.id) !}">${(item.brandName) !}</option>
							            </#list>
						            </#if>
						        </select>
								<select class="ipt_box cate" id="cate1" data-cateLev="ctgL2" data-cateId="" style="width:150px">
						            <option value="">一级分类</option>
						        </select>
						        <select class="ipt_box cate" id="cate2" data-cateLev="ctgL3" data-cateId="" style="width:150px">
						            <option value="">二级分类</option>
						        </select>
						        <select class="ipt_box cate" id="cate3" data-cateLev="" data-cateId="" style="width:150px">
						            <option value="">三级分类</option>
						        </select>	
						        <input id="catStructname" type="hidden" name="catStructname"/>
							</span>
							<span>
								<a class="button" id="mySubmit" onclick="commondity.search();">
									<span>搜索</span>
								</a>
							</span>
						</p>
					</form>
				</div>
				<!--列表start-->
				<div id="content_list"></div>
			</div>
		</div>
	</div>
</div>
</body>
</html>
