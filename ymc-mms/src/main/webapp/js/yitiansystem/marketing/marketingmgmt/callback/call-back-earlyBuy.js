

function call_back_earlyBuy(){
	
	return FoToolkit.execute(call_back_earlyBuy_fn);
}


function call_back_earlyBuy_fn(){
	//alert(call_validate_activeTime()+"--"+call_validate_joinMember()+"--"+call_validate_earlyBuy_moreBank()+"--"+call_validate_joinCommodity());
	
	var r_activeTime    = call_validate_activeTime();
	var r_earlyBuy      = call_validate_earlyBuy_moreBank();
	var r_joinMember    = call_validate_joinMember();
	var r_joinCommotity = call_validate_joinCommodity();
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
	if(!r_earlyBuy){
		index++;
		msg+=index+".活动优惠设定错误!\r\n";
	}
	if(!r_joinMember){
		index++;
		msg+=index+".参与会员未设定!\r\n";
	}
	if(!r_joinCommotity){
		index++;
		msg+=index+".参与商品设定错误!\r\n";
	}
	
	if(index>0){
		alert(msg);
	}
	return r_activeTime && r_earlyBuy && r_joinCommotity && r_joinMember && r_counts;
}

function call_validate_earlyBuy_moreBank(){
	var more_brankses = getByName("more_branks");
	if(more_brankses.length>0)
		return true;
	else
		return false;
	
}


//新增优惠级别验证
function validate_earlyBuy_addMoreBrank(){
	
	var result = true;
	if(v_early_buy_count1()&&v_early_buy_count2){
		
		if(getByID("chk_zj").checked){
			fo.setConfig({"input":getByID("decreaseAmount_"),"vType":2,"format":FoReg.point});
			if(!fo.validate()) result = false;
		}
		if(getByID("chk_zk").checked){
			fo.setConfig({"input":getByID("discount_"),"vType":2,"format":FoReg.point2});
			if(!fo.validate()) result =  false;
		}
		if(getByID("chk_syhq").checked){
			fo.setConfig({"input":getByID("couponsId_"),"vType":1,"vLength":[1,50]});
			if(!fo.validate()) result =  false;
		}
		if(getByID("chk_sjf").checked){
			fo.setConfig({"input":getByID("sendIntegralNumber_"),"vType":2,"format":FoReg.number});
			if(!fo.validate()) result =  false;
		}
	}else{
		result =  false;
	}
	
	return result;

}