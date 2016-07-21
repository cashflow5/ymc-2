
//总方法
function call_back_group()
{
	return FoToolkit.execute(call_back_group_fn);
}

function call_group_pay_limit()
{
	var c1 = document.getElementById("onePayCount").value;
	var c2 = document.getElementById("oneManyCount").value;
	var c3 = document.getElementById("manyPayCount").value;
	
	var count1 = parseInt(c1);
	var count2 = parseInt(c2);
	var count3 = parseInt(c3);
	
	if(count1 == 0 || count2 == 0 || count3 == 0) return true;
	
	if(FoReg.number(c1)&& FoReg.number(c2) &&FoReg.number(c3)){
		return true;
	}else{
		return false;
	}
	
	
}


function call_back_group_fn(){
	
	var count1 = parseInt($("#onePayCount").val());
	var count2 = parseInt($("#oneManyCount").val());
	var count3 = parseInt($("#manyPayCount").val());

	var r_activeTime    = call_group_validate_activeTime();
	var r_joinMember    = call_validate_joinMember();
	var r_rules_public  = call_validate_rules_public();
	
	//2011.06.08验证活动内最多、最少购买数量
	var r_counts        = call_validate_group_counts();
	
	var r_group_rule    = call_group_validate_other();
	//alert("团购规则："+r_group_rule);
	var r_joinCommodity = joinCommodity();
	
	var msg = "";
	var index = 0;
	if(!r_activeTime){
		index++;
		msg+=index+".活动开始时间或结束时间未设定!\r\n";
	}
	if(!r_counts)
	{
		index++;
		msg+=index+".活动单次购买最少、最多,活动内最大数量设定错误,正确格式为:单次购买最少购买数量<=单次最多购买数量<=活动内最大购买数量!\r\n";
	}
	
	if(!call_group_pay_limit())
	{
		index++;
		msg+=index+".活动单次购买最少、最多,活动内最大数量设定错误,只能输入正整数!\r\n";
	}
	if(!r_joinCommodity){
		index++;
		msg+=index+".团购商品设定错误!\r\n";
	}
	if(!r_group_rule){
		index++;
		msg+=index+".团购的价格(多级或单级价格)、成团数量设定错误!\r\n";
	}
	if(!r_joinMember){
		index++;
		msg+=index+".参与会员未设定!\r\n";
	}
	if(!r_rules_public){
		index++;
		msg+=index+".活动规则设定错误!\r\n";
	}
	
	if(!call_group_validate_other_coupons())
	{
		index++;
		msg+=index+".送积分或加倍送积分只能输入正整数!\r\n";
	}
	
	if(index>0){
		alert(msg);
	}
	
	return r_activeTime && r_joinMember && r_rules_public && r_counts && call_group_pay_limit() && r_group_rule && r_joinCommodity && call_group_validate_other_coupons();
}

function joinCommodity(){
	var groupCommodityName = $("#groupCommodityName").val();
	return groupCommodityName.length>0?true:false;
}

//验证时间
function call_group_validate_activeTime(){
	var sd = $("#groupStartDate").val();
	var ed = $("#groupEndDate").val();
	if(sd==""||ed==""){
		return false;
	}
	return true;
}

//验证其他优惠
function call_group_validate_other_coupons()
{
	var sendIntegralNumber = $("#sendIntegralNumber_").val();
	var integralMultiples = $("#integralMultiples_").val();
	
	if(document.getElementById("chk_sjf").checked)
	{
		if(!FoReg.number(sendIntegralNumber) )
		{
			return false;
		}
	}
	
	if(document.getElementById("chk_jfbs").checked)
	{
		if(!FoReg.number(integralMultiples))
		{
			return false;
		}
	}
	
	
	return true;
}

//验证活动内单次最多、最少购买数量
function call_validate_group_counts()
{
	var c1 = $("#onePayCount").val();
	var c2 = $("#oneManyCount").val();
	var c3 = $("#manyPayCount").val();
	var count1 = parseInt(c1);
	var count2 = parseInt(c2);
	var count3 = parseInt(c3);
	if(count1 == 0 || count2 ==0 || count3 ==0) return true;
	
	if(count1<=count2&&count2<=count3){
		return true;
	}else{
		return false;
	}
	
	
}

//验证团购限制
function call_group_validate_other(){
	var groupType_val = $("#groupType:checked").val();
	
	var groupNumber_val = $("#groupNumber").val();
	
	if(groupType_val=="1"){
		var groupPrice_val  = $("#groupPrice").val();
		
		if(FoReg.point(groupPrice_val)&& FoReg.number(groupPrice_val) && FoReg.number(groupNumber_val))
		{
			return true;
		}
		return false;
	}else{
		var lesses = getByName("lessPayNumber");
		if(lesses.length<=0 && FoReg.number(groupNumber_val)){
			return false;
		}
		return true;
	}
	
}