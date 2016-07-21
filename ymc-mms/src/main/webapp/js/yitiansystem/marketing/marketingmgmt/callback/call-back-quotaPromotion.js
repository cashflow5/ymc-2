
//总方法
function call_back_quotaPromotion(){
	var result = FoToolkit.execute(call_back_quotaPromotion_fn);
	//alert(result);
	
	return result;
}

function call_back_quotaPromotion_fn(){
	var r_activeTime    = call_validate_activeTime();
	var r_hg            = call_validate_hg();
	var r_joinMember    = call_validate_joinMember();
	var r_joinCommodity = call_validate_joinCommodity();
	var r_rules         = call_validate_rules();
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
	
	var r_limit = true;
	if(!r_rules){
		index++;
		msg+=index+".活动规则设定错误!\r\n";
	}else{
		
		
		var preferentialTypes = getByName("preferentialType");
		var preferentialType = "1";
		for(var i = 0 ; i < preferentialTypes.length;i++){
			if(preferentialTypes[i].checked){
				preferentialType = preferentialTypes[i].value;
				break;
			}
		}
		
		//alert("优惠类型:"+preferentialType);
		if(preferentialType=="1"){
			var full_val = parseFloat($("#fullConsumpAmount_").val());
			//alert("订单满:"+full_val);
			//alert("chk_fd"+getByID("chk_fd").checked);
			if(getByID("chk_fd").checked){
				var fd_val = parseFloat($("#icappedMoney_").val());
				//alert("封顶:"+fd_val);
				if(full_val > fd_val){
					index++;
					msg+=index+".封顶金额需要大于订单满额条件!\r\n";
					r_limit = false;
				}
			}
			//alert("chk_zj"+getByID("chk_zj").checked);
			if(getByID("chk_zj").checked){
				var zj_val = parseFloat($("#decreaseAmount_").val());
				//alert("直减:"+zj_val);
				if(full_val < zj_val){
					index++;
					msg+=index+".直减金额需要小于订单满额条件!\r\n";
					r_limit = false;
				}
			}	
		}
		
	}
	
	
	if(!r_hg){
		index++;
		msg+=index+".换购商品优惠设定错误!\r\n";
	}
	if(!r_joinMember){
		index++;
		msg+=index+".参与会员未设定!\r\n";
	}
	if(!r_joinCommodity){
		index++;
		msg+=index+".参与商品设定错误!\r\n";
	}
	
	if(index>0){
		alert(msg);
	}
	return r_activeTime && r_hg && r_joinMember && r_joinCommodity && r_rules && r_counts && r_limit;
	
}

//验证规则必须要选择一项
function call_validate_rules(){
	/*alert(getByID("chk_fd").checked+"--"+getByID("chk_zj").checked+"--"+getByID("chk_zk").checked+"--"+getByID("chk_syhq").checked
			+"--"+getByID("chk_szp").checked+"--"+getByID("chk_sjf").checked+"--"+getByID("chk_jfbs").checked
			+"--"+getByID("chk_zsz").checked+"--"+getByID("chk_jqhg").checked);
	*/
	var preferentialTypes = getByName("preferentialType");
	for(var i = 0 ; i < preferentialTypes.length;i++){
		if(preferentialTypes[i].checked){
			if(preferentialTypes[i].value=="2"){
				return true;
			}
		}
	}
	if(getByID("chk_fd").checked
	    ||getByID("chk_zj").checked
	    ||getByID("chk_zk").checked
	    ||getByID("chk_syhq").checked
	    ||getByID("chk_szp").checked
	    ||getByID("chk_sjf").checked
	    ||getByID("chk_jfbs").checked
	    ||getByID("chk_zsz").checked
	    ||getByID("chk_jqhg").checked){
		return true;
	}
	return false;
}

//验证换购
function call_validate_hg(){
	var result = true;
	if(getByID("chk_jqhg").checked){
		var hgMoneys = getByName("hg_moneyRedemption");
		if(hgMoneys.length<=0){
			return false;
		}
		for(var i = 0 ; i < hgMoneys.length ; i++){
			fo.setConfig({"input":hgMoneys[i],"vType":2,"format":FoReg.point});
			if(fo.validate()){
				setClassName2(hgMoneys[i],"fo_border_success");
			}else{
				setClassName2(hgMoneys[i],"fo_border_error");
				result = false;
			}
		}
	}
	//alert("result--hg:"+result);
	return result;
}
