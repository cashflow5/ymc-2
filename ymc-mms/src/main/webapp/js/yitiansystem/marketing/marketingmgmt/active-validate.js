var fo = new Fo();
//栏目名称绑定
function v_activeName(){
	fo.setConfig({"input":getByID('active_name_'),"vType":1,"vLength":[1,30]});
	FoToolkit.doExe(fo.validate(),"active_name_","v_activeName");
}

//绑定最小、最多、活动最多
function bind_Number(obj){
	fo.setConfig({"input":obj,"vType":2,"format":FoReg.number2});
	fo.validate();
}

//绑定单次购买最少数量
function v_min_buy(){
	fo.setConfig({"input":getByID('onePayCount'),"vType":2,"format":FoReg.number2});
	FoToolkit.doExe(fo.validate(),"onePayCount","v_min_buy");
}
//绑定最多购买数量
function v_max_buy(){
	fo.setConfig({"input":getByID('oneManyCount'),"vType":2,"format":FoReg.number2});
	FoToolkit.doExe(fo.validate(),"oneManyCount","v_max_buy");
}
//绑定活动内最大购买数量
function v_max_active_buy(){
	fo.setConfig({"input":getByID('manyPayCount'),"vType":2,"format":FoReg.number2});
	FoToolkit.doExe(fo.validate(),"manyPayCount","v_max_active_buy");
}

//越早买越便宜数量1
function v_early_buy_count1(){
	fo.setConfig({"input":getByID('count1'),"vType":2,"format":FoReg.number});
	return fo.validate();
}
//越早买越便宜数量2
function v_early_buy_count2(){
	fo.setConfig({"input":getByID('count2'),"vType":2,"format":FoReg.number});
	return fo.validate();
}
//-----------------------------------------------------------------------[总汇方法]

$(document).ready(function(){
	
	//活动类型
	var activeType = $("#activeType_").val();
	
	//绑定活动名称
	$("#active_name_").blur(v_activeName);
	if(OP=="add"){
		setClassName("active_name_","fo_border_need");
		FoToolkit.create("active_name_","v_activeName");
	}else{
		setClassName("active_name_","fo_border_success");
		FoToolkit.create("active_name_","v_activeName",1);
	}
	
	//绑定单次购买最少数量
	$("#onePayCount").blur(v_min_buy);
	if(OP=="add"){
		setClassName("onePayCount","fo_border_need");
		FoToolkit.create("onePayCount","v_min_buy");
	}else{
		setClassName("onePayCount","fo_border_success");
		FoToolkit.create("onePayCount","v_min_buy",1);
	}
	
	
	//绑定单次购买最多数量
	$("#oneManyCount").blur(v_max_buy);
	if(OP=="add"){
		setClassName("oneManyCount","fo_border_need");
		FoToolkit.create("oneManyCount","v_max_buy");
	}else{
		setClassName("oneManyCount","fo_border_success");
		FoToolkit.create("oneManyCount","v_max_buy",1);
	}
	
	//绑定活动单词购买最多数量
	$("#manyPayCount").blur(v_max_active_buy);
	if(OP=="add"){
		setClassName("manyPayCount","fo_border_need");
		FoToolkit.create("manyPayCount","v_max_active_buy");
	}else{
		setClassName("manyPayCount","fo_border_success");
		FoToolkit.create("manyPayCount","v_max_active_buy",1);
	}
	  
	
	//alert("活动类型:"+activeType);
	//初始化优惠类型默认验证
	bind_validate_rules(1,activeType);
	//只有满额才会有多级优惠
	if(activeType=="1"){
		//绑定优惠类型验证
		$(".favorable_style").click(function(){
			bind_validate_rules($(this).val());
		});
	}
	
	//只有满额、越早买越便宜才会有参与商品范围说法
	if(activeType=="1" || activeType == "5"){
		//绑定商品参与范围验证
		var commodityRequire = $(".join_product:checked").val();
		validate_active_commoditySpace(commodityRequire);

		//越早买越便宜
		if(activeType=="5"){
			$("#count1").blur(v_early_buy_count1);
			setClassName("count1","fo_border_need");
			
			$("#count2").blur(v_early_buy_count2);
			setClassName("count2","fo_border_need");
		}
	}else{
		/*
		 * 都会有column多栏目，但是栏目下的商品，
		 * 比如送赠品活动，买一个商品，赠送另一个商品
		 * [采用多品促销与活动规则绑定]
		 */ 
		if(activeType!="6"){
			//绑定活动栏目
			$("#column_name").blur(v_columnName);
			//FoToolkit.create("column_name","v_columnName");
			setClassName("column_name","fo_border_need");
		}
	}

});


//公共优惠条件验证
function validate_public_rules(){
	if(getByID("chk_syhq").checked){
		//生成送优惠券-验证hidden
		setClassName("couponsId_","fo_border_success");
		FoToolkit.create("couponsId_","v_couponsId",1);
	}
	
	//绑定送优惠券
	$("#chk_syhq").click(v_couponsId);
	
	if(getByID("chk_sjf").checked){
		//生成送积分-验证hidden
		setClassName("sendIntegralNumber_","fo_border_success");
		FoToolkit.create("sendIntegralNumber_","v_sendIntegralNumber",1);
	}
	//绑定送积分
	$("#chk_sjf").click(v_sendIntegralNumber);	
	
	//早买早便宜没有积分倍数(加倍送积分)
	if(getByID("chk_jfbs")!=null){
		if(getByID("chk_jfbs").checked){
			//生成送积分-验证hidden
			setClassName("integralMultiples_","fo_border_success");
			FoToolkit.create("integralMultiples_","v_integralMultiples",1);
		}
		//绑定加倍送积分
		$("#chk_jfbs").click(v_integralMultiples);
	}
}


/*
* 1.验证优惠条件
* 2.根据活动类型来判别需要验证优惠条件
    [------activeType------]
*    activeType：1 满额活动
*    activeType：2 限时抢活动
*    activeType：3 多买多折扣活动
*    activeType：4 送赠品活动
*    activeType：5 越早买越便宜活动
*/

function bind_validate_rules(ptVal,activeType){
	
	//初始化一些样式
	if(OP=="add"){
		
	}else{
		//修改-[满额]换购绑定
		var hgMoneys = document.getElementsByName("hg_moneyRedemption");
		//alert("换购数："+hgMoneys.length);
		for(var i = 0 ; i < hgMoneys.length ; i++){
			hgMoneys[i].onblur = function(){
				fo.setConfig({"input":this,"vType":2,"format":FoReg.point});
				fo.validate();
			};
			setClassName2(hgMoneys[i],"fo_border_success");
		}
		
		//修改-[送赠品]送积分绑定
		var presentNumbers = getByName("presentNumber");
		//alert("送赠品数："+presentNumbers.length);
		for(var j = 0 ; j < presentNumbers.length ;j++){
			
			presentNumbers[j].onblur = function(){
				fo.setConfig({"input":this,"vType":1,"vLength":[1,30]});
				fo.validate();
			};
			setClassName2(presentNumbers[j],"fo_border_success");
		}
		var presentCounts = getByName("presentCount");
		for(var k = 0 ; k < presentCounts.length ;k++){
			presentCounts[k].onblur = function(){
				fo.setConfig({"input":this,"vType":2,"format":FoReg.number});
				fo.validate();
			};
			setClassName2(presentCounts[k],"fo_border_success");
		}
		
		//修改-[多买多优惠]最小购买件数 、最大购买件数、折扣
		var lessBuyAmounts = getByName("lessBuyAmount");
		for(var l = 0 ; l < lessBuyAmounts.length;l++){
			lessBuyAmounts[l].onblur = function(){
				fo.setConfig({"input":this,"vType":2,"format":FoReg.number});
				fo.validate();
			};
			setClassName2(lessBuyAmounts[l],"fo_border_success");
		}
		var moreBuyAmounts = getByName("moreBuyAmount");
		for(var m = 0 ; m < moreBuyAmounts.length;m++){
			moreBuyAmounts[m].onblur = function(){
				fo.setConfig({"input":this,"vType":2,"format":FoReg.number});
				fo.validate();
			};
			setClassName2(moreBuyAmounts[m],"fo_border_success");
		}
		var commodityDiscounts = getByName("commodityDiscount");
		for(var n = 0 ; n < commodityDiscounts.length ;n++){
			commodityDiscounts[n].onblur = function(){
				fo.setConfig({"input":this,"vType":2,"format":FoReg.point2});
				fo.validate();
			};
			setClassName2(commodityDiscounts[n],"fo_border_success");
		}
		
		//修改-[限时抢]抢购价
		var snappingUpPrices = getByName("snappingUpPrice");
		for(var p = 0 ; p < snappingUpPrices.length ;p++){
			snappingUpPrices[p].onblur = function(){
				fo.setConfig({"input":this,"vType":2,"format":FoReg.point});
				fo.validate();
			};
			setClassName2(snappingUpPrices[p],"fo_border_success");
		}
		
		
	}
	
	
	if(ptVal == 1){//普通优惠
		if($("#fullConsumpAmount_")!=null&&$("#fullConsumpAmount_").val()!=undefined){
			if(OP=="add"){
				//满额绑定
				setClassName("fullConsumpAmount_","fo_border_need");
				FoToolkit.create("fullConsumpAmount_","v_fullConsumpAmount");
			}else{
				setClassName("fullConsumpAmount_","fo_border_success");
				FoToolkit.create("fullConsumpAmount_","v_fullConsumpAmount",1);
			}
			$("#fullConsumpAmount_").blur(v_fullConsumpAmount);
		}
	
		//满额活动
		if(activeType == 1){
			if(getByID("chk_fd").checked){
				//生成封顶金额-验证hidden
				setClassName("icappedMoney_","fo_border_success");
				FoToolkit.create("icappedMoney_","v_icappedMoney",1);
			}
				//绑定封顶
				$("#chk_fd").click(v_icappedMoney);
			
			if(getByID("chk_zj").checked){
				//生成直减-验证hidden
				setClassName("decreaseAmount_","fo_border_success");
				FoToolkit.create("decreaseAmount_","v_decreaseAmount",1);
			}
				//绑定直减
				$("#chk_zj").click(v_decreaseAmount);
			
			if(getByID("chk_zk").checked){
				//生成折扣-验证hidden
				setClassName("discount_","fo_border_success");
				FoToolkit.create("discount_","v_discount",1);
			}
				//绑定折扣
				$("#chk_zk").click(v_discount);
			
			if(getByID("chk_szp").checked){
				//生成折扣-验证hidden
				setClassName("giftCommodityNumber_","fo_border_success");
				FoToolkit.create("giftCommodityNumber_","v_giftCommodityNumber",1);
			}
				//绑定送赠品
				$("#chk_szp").click(v_giftCommodityNumber);	
			
		}
		
		//FoToolkit.remove("lessConsumpAmount_","v_lessConsumpAmount");
		//FoToolkit.remove("moreConsumpAmount_","v_moreConsumpAmount");
	}else if(ptVal == 2){
		//小于订单金额绑定
		$("#lessConsumpAmount_").blur(v_lessConsumpAmount);
		//FoToolkit.create("lessConsumpAmount_","v_lessConsumpAmount");
		setClassName("lessConsumpAmount_","fo_border_need");
		//大于订单金额绑定
		$("#moreConsumpAmount_").blur(v_moreConsumpAmount);
		setClassName("moreConsumpAmount_","fo_border_need");
		//FoToolkit.create("moreConsumpAmount_","v_moreConsumpAmount");
		
		FoToolkit.remove("fullConsumpAmount_","v_fullConsumpAmount");
	}
	
	
	if(activeType=="5"){
		//绑定早买早便宜特殊规则
		validate_earlyBuy_bind();
	}else{
		//非早买早便宜活动绑定公共规则
		validate_public_rules();
	}
}

//---------------------------------------------------------------------[验证页面各项规则]
//加倍送积分验证
function v_integralMultiples(){
	if(getByID("chk_jfbs").checked){
		$("#integralMultiples_").blur(function(){
			if(getByID("chk_jfbs").checked){
				fo.setConfig({"input":getByID('integralMultiples_'),"vType":2,"format":FoReg.point});
				FoToolkit.doExe(fo.validate(),"integralMultiples_","v_integralMultiples");
			}else{
				setClassName("integralMultiples_","fo_border_need");
			}
		});
		FoToolkit.create("integralMultiples_","v_integralMultiples");
		setClassName("integralMultiples_","fo_border_need");
	}else{
		FoToolkit.remove("integralMultiples_","v_integralMultiples");
		setClassName("integralMultiples_","fo_border_need");
	}
}

//送积分验证
function v_sendIntegralNumber(){
	if(getByID("chk_sjf").checked){
		$("#sendIntegralNumber_").blur(function(){
			if(getByID("chk_sjf").checked){
				fo.setConfig({"input":getByID('sendIntegralNumber_'),"vType":2,"format":FoReg.number});
				FoToolkit.doExe(fo.validate(),"sendIntegralNumber_","v_sendIntegralNumber");
			}else{
				setClassName("sendIntegralNumber_","fo_border_need");
			}
		});
		FoToolkit.create("sendIntegralNumber_","v_sendIntegralNumber");
		setClassName("sendIntegralNumber_","fo_border_need");
	}else{
		FoToolkit.remove("sendIntegralNumber_","v_sendIntegralNumber");
		setClassName("sendIntegralNumber_","fo_border_need");
	}
}

//送优惠券验证
function v_couponsId(){
	if(getByID("chk_syhq").checked){
		$("#couponsId_").blur(function(){
			if(getByID("chk_syhq").checked){
				fo.setConfig({"input":getByID('couponsId_'),"vType":1,"vLength":[1,13]});
				FoToolkit.doExe(fo.validate(),"couponsId_","v_couponsId");
			}else{
				setClassName("couponsId_","fo_border_need");
			}
		});
		FoToolkit.create("couponsId_","v_couponsId");
		setClassName("couponsId_","fo_border_need");
	}else{
		FoToolkit.remove("couponsId_","v_couponsId");
		setClassName("couponsId_","fo_border_need");
	}
}


//满额验证
function v_fullConsumpAmount(){
	fo.setConfig({"input":getByID('fullConsumpAmount_'),"vType":2,"format":FoReg.point});
	FoToolkit.doExe(fo.validate(),"fullConsumpAmount_","v_fullConsumpAmount");
}

//绑定封顶
function v_icappedMoney(){
	
	if(getByID("chk_fd").checked){
		$("#icappedMoney_").blur(function(){
			if(getByID("chk_fd").checked){
				fo.setConfig({"input":getByID('icappedMoney_'),"vType":2,"format":FoReg.point});
				FoToolkit.doExe(fo.validate(),"icappedMoney_","v_icappedMoney");
			}
		});
		FoToolkit.create("icappedMoney_","v_icappedMoney");
		setClassName("icappedMoney_","fo_border_need");
	}else{
		setClassName("icappedMoney_","fo_border_need");
		FoToolkit.remove("icappedMoney_","v_icappedMoney");
	}
}

//直减金额验证
function v_decreaseAmount(){
	if(getByID("chk_zj").checked){
		$("#decreaseAmount_").blur(function(){
			if(getByID("chk_zj").checked){
				fo.setConfig({"input":getByID('decreaseAmount_'),"vType":2,"format":FoReg.point});
				FoToolkit.doExe(fo.validate(),"decreaseAmount_","v_decreaseAmount");
			}else{
				setClassName("decreaseAmount_","fo_border_need");
			}
		});
		FoToolkit.create("decreaseAmount_","v_decreaseAmount");
		setClassName("decreaseAmount_","fo_border_need");
	}else{
		FoToolkit.remove("decreaseAmount_","v_decreaseAmount");
		setClassName("decreaseAmount_","fo_border_need");
	}
}

//折扣验证
function v_discount(){
	if(getByID("chk_zk").checked){
		$("#discount_").blur(function(){
			if(getByID("chk_zk").checked){
				fo.setConfig({"input":getByID('discount_'),"vType":2,"format":FoReg.point2});
				FoToolkit.doExe(fo.validate(),"discount_","v_discount");
			}else{
				setClassName("discount_","fo_border_need");
			}
		});
		FoToolkit.create("discount_","v_discount");
		setClassName("discount_","fo_border_need");
	}else{
		FoToolkit.remove("discount_","v_discount");
		setClassName("discount_","fo_border_need");
	}
}

//送赠品验证
function v_giftCommodityNumber(){
	if(getByID("chk_szp").checked){
		$("#giftCommodityNumber_").blur(function(){
			if(getByID("chk_szp").checked){
				fo.setConfig({"input":getByID('giftCommodityNumber_'),"vType":1,"vLength":[1,13]});
				FoToolkit.doExe(fo.validate(),"giftCommodityNumber_","v_giftCommodityNumber");
			}else{
				setClassName("giftCommodityNumber_","fo_border_need");
			}
		});
		FoToolkit.create("giftCommodityNumber_","v_giftCommodityNumber");
		setClassName("giftCommodityNumber_","fo_border_need");
	}else{
		FoToolkit.remove("giftCommodityNumber_","v_giftCommodityNumber");
		setClassName("giftCommodityNumber_","fo_border_need");
	}
}

//小于订单金额验证
function v_lessConsumpAmount(){
	fo.setConfig({"input":getByID('lessConsumpAmount_'),"vType":2,"format":FoReg.point});
	if(fo.validate(),"lessConsumpAmount_","v_lessConsumpAmount"){
		validate_order_moneySpace($("#lessConsumpAmount_").val(),$("#moreConsumpAmount_").val(),$("#lessConsumpAmount_"));
	}
}

//大于订单金额验证
function v_moreConsumpAmount(){
	fo.setConfig({"input":getByID('moreConsumpAmount_'),"vType":2,"format":FoReg.point});
	if(fo.validate(),"moreConsumpAmount_","v_moreConsumpAmount"){
		validate_order_moneySpace($("#lessConsumpAmount_").val(),$("#moreConsumpAmount_").val(),$("#moreConsumpAmount_"));
	}
}


//验证满额活动规则的订单金额范围
function validate_order_moneySpace(val1,val2,obj){
	if(val1 == null || val1 == "" || val2 == null || val2 == ""){
		return -1;
	}
	
	if(parseFloat(val1)<parseFloat(val2)){
		setClassName("moreConsumpAmount_","fo_border_success");
		return 1;
	}else{
		setClassName("moreConsumpAmount_","fo_border_error");
		return 0;
	}
}

//验证加钱换购
function validate_addMoney_hg(){
	
	var hgMoneys = document.getElementsByName("hg_moneyRedemption");
	for(var i = 0 ; i < hgMoneys.length ; i++){
		hgMoneys[i].onblur = function(){
			fo.setConfig({"input":this,"vType":2,"format":FoReg.point});
			fo.validate();
		};
		setClassName2(hgMoneys[i],"fo_border_need");
	}
}

//----------------------------------------------------[参与商品范围验证]
//验证栏目名称
function v_columnName(){
	fo.setConfig({"input":getByID("column_name"),"vType":1,"vLength":[1,20]});
	return fo.validate();
}



//参与商品范围验证
function validate_active_commoditySpace(commodityRequire){
	//alert("参与商品范围-_-"+commodityRequire);
	if(commodityRequire == "0"){ return; }
	else if(commodityRequire == "1"){
		
	}else if(commodityRequire == "2"){
		
	}else if(commodityRequire == "3"){
		
	}else if(commodityRequire == "4"){
		//绑定活动栏目
		$("#column_name").blur(v_columnName);
		//FoToolkit.create("column_name","v_columnName");
		setClassName("column_name","fo_border_need");
	}
	
	/*if(commodityRequire != "4"){
		FoToolkit.remove("column_name","v_columnName");
		setClassName("column_name","fo_border_need");
	}*/
}


//-------------------------------------------------------[送赠品活动-活动规则动态验证]
//验证送赠品（赠品编码、赠品数量）
function validate_addSendGift(){
	var presentNumbers = getByName("presentNumber");
	var presentCounts = getByName("presentCount");
	//alert(presentNumbers.length+"--"+presentCounts.length);
	for(var i = 0 ; i < presentNumbers.length;i++){
		presentNumbers[i].onblur = function(){
			fo.setConfig({"input":this,"vType":1,"vLength":[1,30]});
			fo.validate();
		};
		setClassName2(presentNumbers[i],"fo_border_need");
	}
	for(var j = 0 ; j < presentCounts.length ;j++ ){
		presentCounts[j].onblur = function(){
			fo.setConfig({"input":this,"vType":2,"format":FoReg.number});
			fo.validate();
		};
		setClassName2(presentCounts[j],"fo_border_need");
	}
}

//-------------------------------------------------------[多买多优惠活动-活动规则动态验证]
//验证最小购买件数、最大购买件数、折扣
function validate_addMuchMoreCommodity(){
	var lessBuyAmounts = getByName("lessBuyAmount");
	for(var i = 0 ; i < lessBuyAmounts.length;i++){
		lessBuyAmounts[i].onblur = function(){
			fo.setConfig({"input":this,"vType":2,"format":FoReg.number});
			fo.validate();
		};
		setClassName2(lessBuyAmounts[i],"fo_border_need");
	}
	
	var moreBuyAmounts = getByName("moreBuyAmount");
	for(var j = 0 ; j < moreBuyAmounts.length;j++){
		moreBuyAmounts[j].onblur = function(){
			fo.setConfig({"input":this,"vType":2,"format":FoReg.number});
			fo.validate();
		};
		setClassName2(moreBuyAmounts[j],"fo_border_need");
	}
	
	var commodityDiscounts = getByName("commodityDiscount");
	for(var k = 0 ; k < commodityDiscounts.length ;k++){
		commodityDiscounts[k].onblur = function(){
			fo.setConfig({"input":this,"vType":2,"format":FoReg.point2});
			fo.validate();
		};
		setClassName2(commodityDiscounts[k],"fo_border_need");
	}
}
//-------------------------------------------------------[限时抢活动-活动规则动态验证]
//验证抢购价
function validate_addCessorRobCommodity(){
	var snappingUpPrices = getByName("snappingUpPrice");
	for(var i = 0 ; i < snappingUpPrices.length;i++){
		snappingUpPrices[i].onblur = function(){
			fo.setConfig({"input":this,"vType":2,"format":FoReg.point});
			fo.validate();
		};
		setClassName2(snappingUpPrices[i],"fo_border_need");
	}
}
                                                          
//-----------------------------------------------------------[特殊化验证绑定{早买早便宜}]

function validate_earlyBuy_bind(){
	if(getByID("chk_zj").checked){
		//生成直减-验证hidden
		setClassName("decreaseAmount_","fo_border_success");
		FoToolkit.create("decreaseAmount_","v_decreaseAmount",1);
	}
	//绑定直减
	$("#chk_zj").click(function(){
		if(getByID("chk_zj").checked){
			$("#decreaseAmount_").blur(function(){
				if(getByID("chk_zj").checked){
					fo.setConfig({"input":getByID('decreaseAmount_'),"vType":2,"format":FoReg.point});
					fo.validate();
				}else{
					setClassName("decreaseAmount_","fo_border_need");
				}
			});
			setClassName("decreaseAmount_","fo_border_need");
		}else{
			setClassName("decreaseAmount_","fo_border_need");
		}
	});
	

		if(getByID("chk_zk").checked){
			//生成折扣-验证hidden
			setClassName("discount_","fo_border_success");
		}
		
		//绑定折扣
		$("#chk_zk").click(function(){
			if(getByID("chk_zk").checked){
				$("#discount_").blur(function(){
					if(getByID("chk_zk").checked){
						fo.setConfig({"input":getByID('discount_'),"vType":2,"format":FoReg.point2});
						fo.validate();
					}else{
						setClassName("discount_","fo_border_need");
					}
				});
				setClassName("discount_","fo_border_need");
			}else{
				setClassName("discount_","fo_border_need");
			}
		});
		if(getByID("chk_syhq").checked){
			//生成送优惠券-验证hidden
			setClassName("couponsId_","fo_border_success");
		}
		
		//绑定送优惠券
		$("#chk_syhq").click(function(){
			if(getByID("chk_syhq").checked){
				$("#couponsId_").blur(function(){
					if(getByID("chk_syhq").checked){
						fo.setConfig({"input":getByID('couponsId_'),"vType":1,"vLength":[1,13]});
						fo.validate();
					}else{
						setClassName("couponsId_","fo_border_need");
					}
				});
				setClassName("couponsId_","fo_border_need");
			}else{
				setClassName("couponsId_","fo_border_need");
			}
		});
		
		if(getByID("chk_sjf").checked){
			//生成送积分-验证hidden
			setClassName("sendIntegralNumber_","fo_border_success");
		}
		//绑定送积分
		$("#chk_sjf").click(function(){
			if(getByID("chk_sjf").checked){
				$("#sendIntegralNumber_").blur(function(){
					if(getByID("chk_sjf").checked){
						fo.setConfig({"input":getByID('sendIntegralNumber_'),"vType":2,"format":FoReg.number});
						fo.validate();
					}else{
						setClassName("sendIntegralNumber_","fo_border_need");
					}
				});
				setClassName("sendIntegralNumber_","fo_border_need");
			}else{
				setClassName("sendIntegralNumber_","fo_border_need");
			}
		});	
		
}












