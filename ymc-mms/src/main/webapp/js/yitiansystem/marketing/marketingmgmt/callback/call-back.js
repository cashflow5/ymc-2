/**
 * call-back*\-*.js
 * 满额验证回调方法
 * 经过fo.js对 “fo_validate_container” 验证容器中的hidden的name进行
 * 分割后获取相应验证的input控件名、和验证函数进行二次绑定验证,验证完的
 * 结果直接反应与控件表面，和验证容器内部。进过此验证，系统自动调用传入
 * 回调函数，进行二次验证，从而防止业务性质验证疏漏。
 */

//验证活动时间不为空
function call_validate_activeTime(){
	
	var startTimes = getByName("activeStartTime");
	var stopTimes  = getByName("activeStopTime");
	//alert("开始时间，结束时间："+startTimes.length+","+stopTimes.length);
	if(startTimes.length>0&&stopTimes.length>0){
		return true;
	}else{
		//alert("请选择活动时间段!");
		return false;
	}
}

//验证活动内单次最多、最少购买数量
function call_validate_counts(){
	var c1 = $("#onePayCount").val();
	var c2 = $("#oneManyCount").val();
	var c3 = $("#manyPayCount").val();
	var count1 = parseInt(c1);
	var count2 = parseInt(c2);
	var count3 = parseInt(c3);
	if(count1 == 0 || count2 ==0 || count3 ==0) return true;
	
	if(count1<count2&&count2<=count3){
		return true;
	}else{
		return false;
	}
	
	
}

//验证参与对象
function call_validate_joinMember(){
	
	var memberRequires = getByName("memberRequire");
	var memberRequireVal = "";
	for(var i = 0 ; i < memberRequires.length;i++){
		if(memberRequires[i].checked){
			memberRequireVal = memberRequires[i].value;
			break;
		}
	}
	
	var result = false;
	if(memberRequireVal == "1"){
		var levels = getByName("levelId");
		for(var i = 0 ; i < levels.length ;i++){
			if(levels[i].checked){
				result = true;
				break;
			}
		}
	}else{
		result = true;
	}
	return result;
}

//验证规则必须要选择一项[公共方法-除满额、早买早便宜活、团购动没有]
//修改bug  BC-875  其他优惠非必选项
function call_validate_rules_public(){
	/*
		if(getByID("chk_syhq").checked
		    ||getByID("chk_sjf").checked
		    ||getByID("chk_jfbs").checked
		    ||getByID("chk_zsz").checked){
			return true;
		}
		return false;
	*/
	return true;
	
}

//验证商品范围
function call_validate_joinCommodity(){
	
	var commodityRequires = getByName("commodityRequire");
	var commodityRequireVal = "";
	for(var i = 0 ; i < commodityRequires.length;i++){
		if(commodityRequires[i].checked){
			commodityRequireVal = commodityRequires[i].value;
			break;
		}
	}
	var result = false;
	//alert(commodityRequireVal);
	if(commodityRequireVal == "0"){//全站
		result = true;
	}else if(commodityRequireVal == "1"){//品类
		result = call_validate_joinCommodity_catb2c();
	}else if(commodityRequireVal == "2"){//品牌
		result = call_validate_joinCommodity_brand();
	}else if(commodityRequireVal == "3"){//品牌品类
		result = call_validate_joinCommodity_brand_catb2c();
	}else if(commodityRequireVal == "4"){//多栏
		result = call_validate_joinCommodity_column();
	}
	
	return result;
}


//商品范围-品类
function call_validate_joinCommodity_catb2c(){
	var categoriesIds = getByName("categoriesId");
	if(categoriesIds.length>0){
		return true;
	}else{
		return false;
	}
}

//商品范围-品牌
function call_validate_joinCommodity_brand(){
	var brandIds = getByName("brandId");
	if(brandIds.length>0){
		return true;
	}else{
		return false;
	}
}

//商品范围-品牌-品类
function call_validate_joinCommodity_brand_catb2c(){
	
	return true;
}

//商品范围-品牌-多栏
function call_validate_joinCommodity_column(){
	var oneAndManyCommodityIds = getByName("oneAndManyCommodityId");
	//alert(oneAndManyCommodityIds.length);
	if(oneAndManyCommodityIds.length>0){
		return true;
	}else{
		return false;
	}
}

