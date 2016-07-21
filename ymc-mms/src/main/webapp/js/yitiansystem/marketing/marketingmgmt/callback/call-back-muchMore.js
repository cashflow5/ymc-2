

//总方法
function call_back_muchMore(){
	
	return FoToolkit.execute(call_back_muchMore_fn);
	
}


function call_back_muchMore_fn(){
	//alert(call_validate_activeTime()+"--"+call_validate_rules_public()+"--"+call_validate_joinMember()+"--"+call_validate_column());
	var r_activeTime    = call_validate_activeTime();
	var r_column       = call_validate_column();
	var r_joinMember    = call_validate_joinMember();
	var r_rules_public  = call_validate_rules_public();
	//2011.06.08验证活动内最多、最少购买数量
	var r_counts        = call_validate_counts();
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
	if(!r_joinMember){
		index++;
		msg+=index+".参与会员未设定!\r\n";
	}
	if(!r_rules_public){
		index++;
		msg+=index+".活动规则设定错误!\r\n";
	}
	if(!r_column){
		index++;
		msg+=index+".活动规则-商品优惠设定错误!\r\n";
	}
	
	if(index>0){
		alert(msg);
	}
	return r_activeTime && r_column && r_joinMember && r_rules_public && r_counts;
}



//动态验证
function call_validate_column(){
	var result1 = true;
	var result2 = true;
	var result3 = true;
	var lessBuyAmounts = getByName("lessBuyAmount");
	var moreBuyAmounts = getByName("moreBuyAmount");
	var commodityDiscounts = getByName("commodityDiscount");
	if(lessBuyAmounts.length <= 0 || moreBuyAmounts.length <= 0 || commodityDiscounts.length <= 0){
		return false;
	}
	
	for(var i = 0 ; i < lessBuyAmounts.length;i++){
		fo.setConfig({"input":lessBuyAmounts[i],"vType":2,"format":FoReg.number});
		if(fo.validate()){
			setClassName2(lessBuyAmounts[i],"fo_border_success");
		}else{
			setClassName2(lessBuyAmounts[i],"fo_border_error");
			result1 = false;
		}
	}
	
	
	for(var j = 0 ; j < moreBuyAmounts.length;j++){
		fo.setConfig({"input":moreBuyAmounts[j],"vType":2,"format":FoReg.number});
		if(fo.validate()){
			setClassName2(moreBuyAmounts[j],"fo_border_success");
		}else{
			setClassName2(moreBuyAmounts[j],"fo_border_error");
			result2 = false;
		}
	}
	
	
	for(var k = 0 ; k < commodityDiscounts.length ;k++){
		fo.setConfig({"input":commodityDiscounts[k],"vType":2,"format":FoReg.point2});
		if(fo.validate()){
			setClassName2(commodityDiscounts[k],"fo_border_success");
		}else{
			setClassName2(commodityDiscounts[k],"fo_border_error");
			result3 = false;
		}
	}
	return result1 && result2 && result3;
}

