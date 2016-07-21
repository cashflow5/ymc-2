
//总方法
function call_back_newFacte(){
	var result = FoToolkit.execute(call_back_newFacte_fn);
	
	return result;
}

function call_back_newFacte_fn(){
	var r_activeTime    = call_validate_activeTime();
	var r_joinMember    = call_validate_joinMember();
	var r_buy = call_validate_buyPrice();
	var r_counts        = call_validate_counts();
	var r_commodity = call_validate_commodity();
	
	var msg = "";
	var index = 0;
	if(!r_activeTime){
		index++;
		msg+=index+".活动时间段未设定!\r\n";
	}
	
	if(!r_counts){
		index++;
		msg+=index+".活动单次购买最少、最多,活动内最大数量设定错误,正确格式为:单次购买最少购买数量<单次最多购买数量<=活动内最大购买数量!\r\n";
	}
	
	if(!r_buy){
		index++;
		msg+=index+".购买价格设定错误!\r\n";
	}
	
	
	if(!r_commodity){
		index++;
		msg+=index+".活动商品未设定";
	}
	
	
	if(index>0){
		alert(msg);
	}
	
	//alert("验证："+(r_activeTime&& r_joinMember && r_rules && r_counts && r_buy && r_commodity));
	return r_activeTime&& r_joinMember && r_counts && r_buy && r_commodity;
	
}

//购买价格验证
function call_validate_buyPrice(){
	var fullConsumpAmount = getByID("fullConsumpAmount_");
	var val = fullConsumpAmount.value;
	if(val.length>0&&FoReg.point(val)){
		return true;
	}else{
		return false;
	}
}



function call_validate_commodity(){
	var oneAndManyCommodityNames = getByName("oneAndManyCommodityName");
	if(oneAndManyCommodityNames!=null&&oneAndManyCommodityNames.length>0){
		return true;
	}else{
		return false;
	}
}
