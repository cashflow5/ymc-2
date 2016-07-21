/***
 * 优惠券范围模型
 * 模型中id统一为数据库UUID
 */
 // 品类品牌Map
window.categoriesBrandMap = new YouGou.Util.Map();
// 多品Map
window.commodityMap =  new YouGou.Util.Map();
// 品牌、品类Map
window.categoriesAndBrandMap =  new YouGou.Util.Map();

// Keys Prefix
var CATEGORIESBRAND_KEY_PREFIX = "categoriesBrand_";
var COMMODITY_KEY_PREFIX = "commodity_";
var CATEGORIESANDBRAND_KEY_PREFIX = "categoriesAndBrand_";
var THIRDCATEGORIESANDBRAND_KEY_PREFIX = "thirdCategoriesAndBrand_";

// 多品
CommodityVo = function(config){
	YouGou.Base.apply(this,config);
	this.id = this.id || "";
	this.commodityId = this.commodityId || "";
	this.commodityNo = this.commodityNo || "";
	this.commodityName = this.commodityName || "";
	this.salePrice = this.salePrice || 0;
	this.stock = this.stock || 0;
}

// 品类、品牌
CategoriesBrandVo = function(config){
	YouGou.Base.apply(this,config);
	this.id = this.id || "";
	this.type = this.type || 0;//1:品牌 2:品类
	this.firstCategoriesId = this.firstCategoriesId || "";
	this.firstCategoriesNo = this.firstCategoriesNo || "";
	this.firstCategoriesName = this.firstCategoriesName || "";
	this.secondCategoriesId = this.secondCategoriesId || "";
	this.secondCategoriesNo = this.secondCategoriesNo || "";
	this.secondCategoriesName = this.secondCategoriesName || "";
	this.thirdCategoriesId = this.thirdCategoriesId || "";
	this.thirdCategoriesNo = this.thirdCategoriesNo || "";
	this.thirdCategoriesName = this.thirdCategoriesName || "";
	this.brandId = this.brandId || "";
	this.brandNo = this.brandNo || "";
	this.brandName = this.brandName || "";
}

// 品类品牌组合
CategoriesAndBrandVo = function(config){
	YouGou.Base.apply(this,config);
	this.type = this.type || 3;
	this.id = this.id || "";
	this.brandId = this.brandId || "";
	this.brandNo = this.brandNo || "";
	this.brandName = this.brandName || "";
	this.categories = this.categories || new YouGou.Util.Map();
}

//选择商品
function openCommoditySelector(callMethod){
	art.dialog.openwindow('/yitiansystem/marketing/marketingmgmt/commoditySelector.sc?callMethod='+callMethod,'800','','no',{id:'open',title: '商品选择器'});
}

//选择品牌
function openBrandSelector(callMethod){
	art.dialog.openwindow('/yitiansystem/marketing/marketingmgmt/brandSelector.sc?callMethod='+callMethod,'800','','no',{id:'open',title: '品牌选择器'});
}

//选择分类
function openCategorySelector(callMethod){
	art.dialog.openwindow('/yitiansystem/marketing/marketingmgmt/categorySelector.sc?callMethod='+callMethod,'800','','yes',{id:'open',title: '商品选择器'});
}

//选择品牌品类
function openBrandCategorySelector(callMethod){
	art.dialog.openwindow('/yitiansystem/marketing/marketingmgmt/brandCategorySelector.sc?callMethod='+callMethod,'800','','yes',{id:'open',title: '商品选择器'});
}

// 加载多品数据
function renderCommodity(){
	if(YouGou.Util.isNull(window.commodityMap)){
		return;
	}
	var _html = [];
	_html.push('<table class="list_table2">');
	_html.push('<thead>');
	_html.push('<tr>');
	_html.push('<th width="15%">商品编码</th>');
	_html.push('<th width="70%">商品名称</th>');
	//_html.push('<th width="60">库存</th>');
	_html.push('<th width="15%">操作</th>');
	_html.push('</tr>');
	_html.push('</thead>');
	_html.push('<tbody>');
	if(window.commodityMap.size() >0){
		window.commodityMap.each(function(key,value,index){
			if(!YouGou.Util.isNull(value)){
				_html.push('<tr id="'+ key +'">');
				_html.push('<td style="text-align:center">'+ value.commodityNo +'</td>');
				_html.push('<td>'+ value.commodityName +'</td>');
				//_html.push('<td>'+ value.stock +'</td>');
				_html.push('<td style="text-align:center"><a href="javascript:void(0);" onclick="removeObj(1,\''+ key +'\')">删除</a></td>');
				_html.push('</tr>');
			}
		});
	}else{
		_html.push('<tr>');
		_html.push('<td colspan="3"><center><font color="red">暂无记录！</font></center></td>');
		_html.push('</tr>');
	}

	_html.push('</tbody>');
	_html.push('</table>');
	_html.push('<p class="blank10"></p><input type="button" value="添加商品" class="yt-seach-btn-4ft" onclick="openCommoditySelector(\'addCommodity\');" />');
	$("#commodityScope").html(_html.join(''));
}

// 添加多品数据
function addCommodity(commodityVos){
	checkPattern(commodityVos);
	$.each(commodityVos,function(index,item){
		if(!YouGou.Util.isNull(item)){
			window.commodityMap.put(COMMODITY_KEY_PREFIX + item.commodityNo,item);
		}
	});
	renderCommodity();
}

//添加品牌、分类
function addCategoriesAndBrand(categoriesBrandVos){
	checkPattern(categoriesBrandVos);
	$.each(categoriesBrandVos,function(index,item){
		if(!YouGou.Util.isNull(item)){
			if(!YouGou.Util.isEmpty(item.brandId)){
				window.categoriesAndBrandMap.put(CATEGORIESBRAND_KEY_PREFIX + item.brandId,item);
			}else if(!YouGou.Util.isEmpty(item.firstCategoriesId)){
				window.categoriesAndBrandMap.put(CATEGORIESBRAND_KEY_PREFIX + item.thirdCategoriesId,item);
			}
		}
	});
	renderCategoriesAndBrand();
}

//添加品牌品类
function addCategoriesBrand(categoriesAndBrandVos){
	checkPattern(categoriesAndBrandVos);
	$.each(categoriesAndBrandVos,function(index,item){
		if(!YouGou.Util.isNull(item)){
			window.categoriesBrandMap.put(CATEGORIESANDBRAND_KEY_PREFIX + item.brandId,item);
		}
	});
	renderCategoriesBrand();
}

// 加载品牌、分类数据
function renderCategoriesAndBrand(){
	// 品牌
	var _html = [];
	_html.push('<div class="sort_select clearfix" id="choose_brand_container">');
	_html.push('<p id="choose_brand_items"><strong>已选品牌</strong></p>');
	if(window.categoriesAndBrandMap.size()==0){
		_html.push('<div style="width:100%;line-height:32px;text-align:center;color:red;">未选中任何品牌</div>');
	}
	window.categoriesAndBrandMap.each(function(key,value,index){
		if(!YouGou.Util.isNull(value) && !YouGou.Util.isEmpty(value.brandId)){
			_html.push('<span id="'+ key +'"style="width:100px;">'+ value.brandName +'&nbsp;&nbsp; <a class="del" onclick="removeObj(2,\''+ key +'\')"></a></span>');
			_html.push('&nbsp;');
		}
	});
	_html.push('</div><p class="blank10"></p>');
	_html.push('<input type="button" value="添加品牌" class="yt-seach-btn-4ft" onclick="openBrandSelector(\'addCategoriesAndBrand\');" />');
	$("#brandScope").html(_html.join(''));

	//分类
	var _html = [];
	_html.push('<div class="sort_select clearfix" id="choose_brand_container">');
	_html.push('<p id="choose_brand_items"><strong>已选分类</strong></p>');
	if(window.categoriesAndBrandMap.size()==0){
		_html.push('<div style="width:100%;line-height:32px;text-align:center;color:red;">未选中任何分类</div>');
	}
	window.categoriesAndBrandMap.each(function(key,value,index){
		if(!YouGou.Util.isNull(value) && !YouGou.Util.isEmpty(value.thirdCategoriesId)){
			var _cPath = [];
			_cPath.push(value.firstCategoriesName);
			_cPath.push('&gt');
			_cPath.push(value.secondCategoriesName);
			_cPath.push('&gt');
			_cPath.push(value.thirdCategoriesName);
			_html.push('<span id="'+ key +'"style="width:190px;"> '+ _cPath.join('') +'&nbsp;&nbsp; <a class="del" onclick="removeObj(2,\''+ key +'\')"></a></span>');
			_html.push('&nbsp;');
		}
	});
	_html.push('</div><p class="blank10"></p>');
	_html.push('<input type="button" value="添加分类" class="yt-seach-btn-4ft" onclick="openCategorySelector(\'addCategoriesAndBrand\');" />');
	$("#categoriesScope").html(_html.join(''));
}

// 加载品牌品类数据
function renderCategoriesBrand(){
	var _html = [];
	if(window.categoriesBrandMap.size()==0){
		_html.push('<div style="width:100%;line-height:32px;text-align:center;color:red;">未选中任何品牌品类</div>');
	}
	window.categoriesBrandMap.each(function(key,value,index){
		if(!YouGou.Util.isNull(value) && value.categories.size() > 0 ){
			_html.push('<div class="sort_select clearfix" id="control_vip_rank_catb2c_brand">');
			_html.push('<p id="choose_brand_items"><strong>品牌：'+ value.brandName +'</strong></p>');
			value.categories.each(function(_key,item,_index){
				if(!YouGou.Util.isNull(item)){
					var _cPath = [];
					_cPath.push(item.firstCategoriesName);
					_cPath.push('&gt');
					_cPath.push(item.secondCategoriesName);
					_cPath.push('&gt');
					_cPath.push(item.thirdCategoriesName);
					_html.push('<span id="'+ _key +'"style="width:190px;">'+ _cPath.join('') +' <a class="del" onclick="removeCategoriesBrand(\''+ key +'\',\''+ _key +'\')"></a></span>');
				}
			});
			_html.push('</div><p class="blank10"></p>');
		}
	});
	_html.push('<input type="button" value="添加品牌品类" style="font-size:11px;letter-spacing:0px;" class="yt-seach-btn-4ft" onclick="openBrandCategorySelector(\'addCategoriesBrand\');" />');
	$("#categoriesBrandScope").html(_html.join(''));
}

// 删除(多品、品牌、品类)
function removeObj(type,key){
	switch(type){
		// 多品
		case 1 :
			window.commodityMap.remove(key);
			renderCommodity();
			break;
		// 品牌、分类
		case 2 :
			window.categoriesAndBrandMap.remove(key);
			renderCategoriesAndBrand();
			break;
	}
}

// 删除品牌品类里面某一个分类
function removeCategoriesBrand(key1,key2){
	if($.browser.msie){
		alert("温馨提示：目前只支持FireFox下删除品牌品类，我们会努力解决该问题,谢谢!");
		return;
	}
	window.categoriesBrandMap.each(function(_key1,value1,index1){
		if(key1 == _key1){
			var categoriesMap = value1.categories;
			categoriesMap.each(function(_key2,value2,index2){
				if(key2 == _key2){
					categoriesMap.remove(_key2);
					if(categoriesMap.size()==0){
						window.categoriesBrandMap.remove(_key1);
					}
				}
			});
		}
	});
	// 重新渲染
	renderCategoriesBrand();
}

function checkPattern(obj){
	if(YouGou.Util.isNull(obj)){
		alert("数据为空，请重新选择!");
		return;
	}else if(!YouGou.Util.isArray(obj)){
		alert("数据不是标准结构!");
		return;
	}
}

function saveScope(){
	//alert("commodityMap====="+window.commodityMap.valArray().toJSONString());
	$("#commodities").val(window.commodityMap.valArray().toJSONString());

	//alert("categoriesBrandMap====="+window.categoriesBrandMap.valArray().toJSONString());
	$("#brandAndCategories").val(window.categoriesAndBrandMap.valArray().toJSONString());

	var result = [];
	window.categoriesBrandMap.each(function(_key1,value1,index1){
		var categoriesMap = value1.categories;
		categoriesMap.each(function(_key2,value2,index2){
			var obj = {
				"type" : 3,
				"brandId" : value1.brandId,
				"brandNo" : value1.brandNo,
				"brandName" : value1.brandName,
				"firstCategoriesId": value2.firstCategoriesId,
	            "firstCategoriesNo": value2.firstCategoriesNo,
	            "firstCategoriesName": value2.firstCategoriesName,
	            "secondCategoriesId": value2.secondCategoriesId,
	            "secondCategoriesNo": value2.secondCategoriesNo,
	            "secondCategoriesName":  value2.secondCategoriesName,
	            "thirdCategoriesId": value2.thirdCategoriesId,
	            "thirdCategoriesNo": value2.thirdCategoriesNo,
	            "thirdCategoriesName": value2.thirdCategoriesName
			};
			result.push(obj);
		});
	});
	$("#brandCategories").val(result.toJSONString());
}

// 编辑还原模型
function initScopeModel(){
	// 品牌品类
	var strBrandCategories = $("#brandCategories").val();
	// 品牌、分类
	var strBrandAndCategories = $("#brandAndCategories").val();
	var strCommodities = $("#commodities").val();
	if(!YouGou.Util.isEmpty(strCommodities)){
		var commoditiesObj = window.eval("("+ strCommodities +")");
		if(YouGou.Util.isArray(commoditiesObj)){
			for(var i=0;i<commoditiesObj.length;i++){
				window.commodityMap.put(COMMODITY_KEY_PREFIX + commoditiesObj[i].commodityNo,commoditiesObj[i]);
			}
		}
	}
	if(!YouGou.Util.isEmpty(strBrandAndCategories)){
		var brandAndCategoriesObj = window.eval("("+ strBrandAndCategories +")");
		if(YouGou.Util.isArray(brandAndCategoriesObj)){
			for(var i=0;i<brandAndCategoriesObj.length;i++){
				if(brandAndCategoriesObj[i].type == 1){
					window.categoriesAndBrandMap.put(CATEGORIESBRAND_KEY_PREFIX + brandAndCategoriesObj[i].brandId,brandAndCategoriesObj[i]);
				}else if(brandAndCategoriesObj[i].type == 2){
					window.categoriesAndBrandMap.put(CATEGORIESBRAND_KEY_PREFIX + brandAndCategoriesObj[i].thirdCategoriesId,brandAndCategoriesObj[i]);
				}
			}
		}
	}
	if(!YouGou.Util.isEmpty(strBrandCategories)){
		var brandCategoriesObj = window.eval("("+ strBrandCategories +")");
		if(YouGou.Util.isArray(brandCategoriesObj)){
			// 获取品牌分组
			var brandGroupMap=new YouGou.Util.Map();
			for(var i=0;i<brandCategoriesObj.length;i++){
				var brandKey=CATEGORIESANDBRAND_KEY_PREFIX + brandCategoriesObj[i].brandId;
				if(YouGou.Util.isEmpty(brandGroupMap.get(brandKey))){
					brandGroupMap.put(brandKey,brandCategoriesObj[i]);
				}
			}

			brandGroupMap.each(function(key,value,index){
				var tempBrandCategorieMap=new YouGou.Util.Map();
				for(var i=0;i<brandCategoriesObj.length;i++){
					var tempCategorieKey=THIRDCATEGORIESANDBRAND_KEY_PREFIX + value.brandId + "_" + brandCategoriesObj[i].thirdCategoriesId;
					if(value.brandId==brandCategoriesObj[i].brandId){
						tempBrandCategorieMap.put(tempCategorieKey,brandCategoriesObj[i]);
					}
				}

				var categoriesBrandVo = new CategoriesAndBrandVo({
					brandId : value.brandId,
					brandName : value.brandName ,
					categories : tempBrandCategorieMap
				});
				//YouGou.Util.inspect(categoriesBrandVo);
				window.categoriesBrandMap.put(key,categoriesBrandVo);
			});
		}
	}
}

$(document).ready(function(){
	initScopeModel();
	renderCommodity();
	renderCategoriesBrand();
	renderCategoriesAndBrand();
});