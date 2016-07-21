

//绑定从品牌搜索第一级分类按钮
function brandCatB2cBind(){
	$("#brand_brands").change(function(){
		loadBrandRootCatB2c($(this).val());
	});
}


function revert_rule(){
	$(".favorable").next().next().hide();


	$("#chk_fd").click(function(){
		var fb_value=$(this).attr("checked");
		rule_s_h("#chk_fd",fb_value);
	});

	$("#chk_zj").click(function(){
		var fb_value=$(this).attr("checked");
		rule_s_h("#chk_zj",fb_value);
	});

	$("#chk_zk").click(function(){
		var fb_value=$(this).attr("checked");
		rule_s_h("#chk_zk",fb_value);
	});

	$("#chk_syhq").click(function(){
		var fb_value=$(this).attr("checked");
		rule_s_h("#chk_syhq",fb_value);
	});

	$("#chk_szp").click(function(){
		var fb_value=$(this).attr("checked");
		rule_s_h("#chk_szp",fb_value);
	});

	$("#chk_sjf").click(function(){
		var fb_value=$(this).attr("checked");
		rule_s_h("#chk_sjf",fb_value);
	});

	$("#chk_jfbs").click(function(){
		var fb_value=$(this).attr("checked");
		rule_s_h("#chk_jfbs",fb_value);
	});
	$("#chk_zsz").click(function(){
		var fb_value=$(this).attr("checked");
		if(fb_value){
			$("#isDiscount_").val(1);
		}else{
			$("#isDiscount_").val(0);
		}
		//rule_s_h("#chk_zsz",fb_value);
	});
	
	$("#chk_my").click(function(){
		var fb_value=$(this).attr("checked");
		if(fb_value){
			$("#isSupportPostage_").val("1");
		}else{
			$("#isSupportPostage_").val("0");
		}
		//rule_s_h("#chk_zsz",fb_value);
	});
	$("#chk_jqhg").click(function(){
		var fb_value=$(this).attr("checked");
		rule_s_h("#chk_jqhg",fb_value);
	});


}


function rule_s_h(id,isShow){
	if(isShow){
		$(id).next().next().show();
	}else{
		//alert(id.substring(1,id.length));
		//document.getElementById("")
		if($(id).next().next().children(":input")&&$(id).next().next().children(":input").length>0){
			$(id).next().next().children(":input:text").val("");
			$(id).next().next().children(":input:hidden").val("");
		}
		$(id).next().next().hide();
	}
	reinitifH();
}

//取消所有选中
function unCheckSinglePreferent(){
	var ids = ["chk_fd","chk_zj","chk_zk","chk_syhq","chk_szp","chk_sjf","chk_jfbs","chk_zsz","chk_jqhg"];
	for(var i = 0 ; i < ids.length ; i++){
		$("#"+ids[i]).attr("checked",false);
	}

}

//var preferType_val = 1;
function preferentialTypeBind(){
	$(".favorable_style").click(function(){
		var mb_value=$(this).val();
		if(mb_value=="1"){//普通优惠
				//if(preferType_val==2){
					//unCheckSinglePreferent();
					//preferType_val = 1;
					$(".favorable_normal").show();
					$("#more_rank").hide();
					$(".favorable_btn").hide();
					$("#more_bank_inputs1").hide();
				//转换临时换购form
				$("#hg_before_form").html($('#tmp_hg_before_form').html());

				$("#tmp_more_form").html($("#preferentalTab").html());


				$("#preferentalTab").html("");

				$('#tmp_hg_before_form').html("");
				$("#more_bank_inputs2").show();
				//}
		}else if(mb_value=="2"){//多级优惠
		    //取消换购form集合[这里需要转存一次普通、多级优惠的form集合]
			$("#tmp_hg_before_form").html($('#hg_before_form').html());

			if($("#tmp_more_form").html().length>0){
				$("#preferentalTab").html($("#tmp_more_form").html());
			}
			//$("#tmp_more_form").html("");

			$('#hg_before_form').html("");
			//preferType_val = 2;
			$(".favorable_normal").hide();
			$("#more_rank").show();
			$(".favorable_btn").show();
			$("#more_bank_inputs1").show();
			$("#more_bank_inputs2").show();
			if(OP=="edit"){
				if($("#hgs_")){
					//alert(1);
					$("#hgs_").remove();
				}
			}
		}


		reinitifH();
	});
}

//取消所有的会员等级
function versetMemmberLevel(){
	var lids = document.getElementsByName("levelId");
	for(var i = 0 ; i < lids.length ;i++){
		if(lids[i].checked){
			lids[i].checked = false;
		}
	}

}
//会员等级参与对象绑定
function joinMemberBind(){
	$(".join_object").click(function(){
		var mb_value=$(this).val();
		if(mb_value=="0"){
			versetMemmberLevel();
			$("#control_vip_rank01").hide();
			if($("#control_vip_rank")){
				$("#control_vip_rank").hide();
			}
			reinitifH();
		}else if(mb_value=="1"){
			$("#control_vip_rank01").show();
			if($("#control_vip_rank")){
				$("#control_vip_rank").show();
			}
			reinitifH();
		}
	});
}
function joinProductBind(){
//商品范围参与对象绑定
$(".join_product").click(function(){
	var mb_value=$(this).val();
	//加入动态验证部分
	validate_active_commoditySpace(mb_value);
	switch(mb_value){
		case "0":
		$("#category").hide();
		$("#brand").hide();
		$("#category_brand").hide();
		$("#multi_brand").hide();
		$(".column_list").hide();
		reinitifH();
		break;
		case "1":
		$("#category").show();
		$("#brand").hide();
		$("#category_brand").hide();
		$("#multi_brand").hide();
		$(".column_list").hide();

		reinitifH();
		break;
		case "2":
		//生成所有的品牌
		fillWithBrand();
		$("#category").hide();
		$("#brand").show();
		$("#category_brand").hide();
		$("#multi_brand").hide();
		$(".column_list").hide();
		$("#control_vip_rank").show();
		reinitifH();
		break;
		case "3":
		createBrand_allBrand();
		$("#category").hide();
		$("#brand").hide();
		$("#category_brand").show();
		$("#multi_brand").hide();
		$(".column_list").hide();
		reinitifH();
		break;
		case "4":
		$("#category").hide();
		$("#brand").hide();
		$("#category_brand").hide();
		$("#multi_brand").show();
		$(".column_list").show();
		reinitifH();
		break;
	}
	});
}
//-------------------------------------------------------------------------------[多级别优惠规则效果-列表]
//清空早买早便宜多级优惠规则
function clear_earlyBuyMoreBrank(){
	//alert("clear_earlyBuyMoreBrank");
	$("#count1").val("");
	setClassName("count1","fo_border_need");
	$("#count2").val("");
	setClassName("count2","fo_border_need");

	$("#chk_zj").attr("checked",false);
	$("#decreaseAmount_").val("");
	$("#decreaseAmount_").parent().hide();

	setClassName("decreaseAmount_","fo_border_need");

	$("#chk_zk").attr("checked",false);
	$("#discount_").val("");
	$("#discount_").parent().hide();
	setClassName("discount_","fo_border_need");

	/*$("#chk_syhq").attr("checked",false);
	$("#couponsId_").val("");
	$("#coupons_Name").val("");
	$("#couponsId_").parent().hide();

	setClassName("couponsId_","fo_border_need");
	$("#chk_sjf").attr("checked",false);
	$("#sendIntegralNumber_").val("");
	$("#sendIntegralNumber_").parent().hide();
	setClassName("sendIntegralNumber_","fo_border_need");*/

}