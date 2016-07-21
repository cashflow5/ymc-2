//总方法
function call_back_sendGift(){
	
	return FoToolkit.execute(call_back_sendGift_fn);
	
}

function call_back_sendGift_fn(){
	//alert(call_validate_activeTime()+"--"+call_validate_rules_public()+"--"+call_validate_joinMember()+"--"+call_validate_present());
	var r_activeTime  = call_validate_activeTime();
	var r_rulesPublic = call_validate_rules_public();
	var r_joinMember  = call_validate_joinMember();
	var r_present     = call_validate_present();
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
	if(!r_rulesPublic){
		index++;
		msg+=index+".活动规则设定错误!\r\n";
	}
	if(!r_joinMember){
		index++;
		msg+=index+".参与会员未设定!\r\n";
	}
	if(!r_present){
		index++;
		msg+=index+".赠品优惠设定错误!\r\n";
	}
	if(index>0){
		alert(msg);
	}
	return r_activeTime && r_rulesPublic && r_joinMember && r_present && r_counts;
}



//栏目验证
function call_validate_present(){
	var result1 = true;
	var result2 = true;
	var presentNumbers = getByName("presentNumber");
	var presentCounts = getByName("presentCount");
	
	if(presentNumbers.length <= 0 || presentCounts.length <= 0){
		return false;
	}
	
	for(var i = 0 ; i < presentNumbers.length ;i++){
		fo.setConfig({"input":presentNumbers[i],"vType":1,"vLength":[1,50]});
		if(fo.validate()){
			setClassName2(presentNumbers[i],"fo_border_success");
		}else{
			result1 = false;
			setClassName2(presentNumbers[i],"fo_border_error");
		}
	}
	
	
	
	for(var j = 0 ; j < presentCounts.length ;j++){
		fo.setConfig({"input":presentCounts[j],"vType":2,"format":FoReg.number});
		if(fo.validate()){
			setClassName2(presentCounts[j],"fo_border_success");
		}else{
			result2 = false;
			setClassName2(presentCounts[j],"fo_border_error");
		}
	}
	return result1&&result2;
}