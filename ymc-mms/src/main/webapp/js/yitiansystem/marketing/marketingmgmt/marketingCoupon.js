
//作废优惠券
function discardCoupons(){
	var checkedBox=$("input[name=idBox]:checked");
	var couponIds='';
	checkedBox.each(function(){
		//只有未使用的优惠券可作废
		if($(this).attr('state')==1){
			couponIds+=','+$(this).attr('id');
		}
	});

	if(couponIds==''){
		Boxy.alert('<div style="padding:20px 30px;">请选中要作废的优惠券，只有未使用的优惠券可作废</div>', null, {title: '操作提醒'});
		return false;
	}else{
		couponIds=couponIds.substring(1);
		$.post(basePath+'/yitiansystem/marketing/marketingmgmt/discardCoupons.sc',{
			couponIds : couponIds
		},function(data){
			//操作成功
			if(data.flag==1){
				Boxy.alert('<div style="padding:20px 30px;">操作成功</div>', null, {title: '操作提醒'});
				var arrayCoupon=couponIds.split(',');
				for(i=0;i<arrayCoupon.length;i++){
					$('#td_1_'+arrayCoupon[i]).html('已作废');
					$('#'+arrayCoupon[i]).attr('state',4);
				}
			}else{
				Boxy.alert('<div style="padding:20px 30px;">操作失败</div>', null, {title: '操作提醒'});
			}
		},'json');
	}
}

//使优惠券生效
function validateCoupons(){
	var checkedBox=$("input[name=idBox]:checked");
	var couponIds='';
	checkedBox.each(function(){
		//只有作废的优惠券可生效
		if($(this).attr('state')==4){
			couponIds+=','+$(this).attr('id');
		}
	});

	if(couponIds==''){
		Boxy.alert('<div style="padding:20px 30px;">请选中要启用的优惠券，只有作废的优惠券可启用</div>', null, {title: '操作提醒'});
		return false;
	}else{
		couponIds=couponIds.substring(1);
		$.post(basePath+'/yitiansystem/marketing/marketingmgmt/validateCoupons.sc',{
			couponIds : couponIds
		},function(data){
			//操作成功
			if(data.flag==1){
				Boxy.alert('<div style="padding:20px 30px;">操作成功</div>', null, {title: '操作提醒'});
				var arrayCoupon=couponIds.split(',');
				var arrayState=data.states.split(',');
				for(i=0;i<arrayCoupon.length;i++){
					var objTD_1=$('#td_1_'+arrayCoupon[i]);
					var state=arrayState[i];
					$('#'+arrayCoupon[i]).attr('state',state);
					if(state==1){
						objTD_1.html('未使用');
					}else if(state==2){
						objTD_1.html('已使用');
					}else if(state==3){
						objTD_1.html('已过期');
					}else if(state==4){
						objTD_1.html('已作废');
					}
				}
			}else{
				Boxy.alert('<div style="padding:20px 30px;">操作失败</div>', null, {title: '操作提醒'});
			}
		},'json');
	}
}

//优惠券延期
function deferCoupons(useStartdate,useEnddate){
	var checkedBox=$("input[name=idBox]:checked");
	var couponIds='';
	checkedBox.each(function(){
		//过滤掉已使用和作废的优惠券
		if($(this).attr('state')==1||$(this).attr('state')==3){
			couponIds+=','+$(this).attr('id');
		}
	});

	if(couponIds==''){
		Boxy.alert('<div style="padding:20px 30px;">请选中要延期的优惠券，只有未使用的或过期的优惠券可延期</div>', null, {title: '操作提醒'});
		return false;
	}else{
		Boxy.form($('#deferCouponHtml').html(),null, function() {
			couponIds=couponIds.substring(1);
			var useStartdate=$('.boxy-wrapper input[name="useStartdate"]').val();
			var useEnddate=$('.boxy-wrapper input[name="useEnddate"]').val();
			$.post(basePath+'/yitiansystem/marketing/marketingmgmt/deferCoupons.sc',{
				couponIds:couponIds,
				useStartdate:useStartdate,
				useEnddate:useEnddate
			},function(data){
				//操作成功
				if(data.flag==1){
					Boxy.alert('<div style="padding:20px 30px;">操作成功</div>', null, {title: '操作提醒'});
					var arrayCoupon=couponIds.split(',');
					var arrayState=data.states.split(',');
					for(i=0;i<arrayCoupon.length;i++){
						var objTD_1=$('#td_1_'+arrayCoupon[i]);
						var state=arrayState[i];
						$('#'+arrayCoupon[i]).attr('state',state);
						if(state==1){
							objTD_1.html('未使用');
						}else if(state==2){
							objTD_1.html('已使用');
						}else if(state==3){
							objTD_1.html('已过期');
						}else if(state==4){
							objTD_1.html('已作废');
						}
						var objTD_2=$('#td_2_'+arrayCoupon[i]);
						objTD_2.html('截止至'+useEnddate.substring(0,10));
					}
				}else{
					Boxy.alert('<div style="padding:20px 30px;">操作失败</div>', null, {title: '操作提醒'});
				}
			},'json');

		}, {title: '延期优惠券'});
	}
}

//导出优惠券明细
function exportCouponDetail(){
	var checkedBox=$("input[name=idBox]");

	if(checkedBox.length==0){
		Boxy.alert('<div style="padding:20px 30px;">当前没有数据可导出</div>', null, {title: '操作提醒'});
		return false;
	}

	$('#marketingCouponForm').attr('action',basePath+'/yitiansystem/marketing/marketingmgmt/exportCouponDetail.sc');
	$('#marketingCouponForm').submit();
	//window.location.href=basePath+'/yitiansystem/marketing/marketingmgmt/downloadCouponDetailList.sc?id='+id+"&num="+$('#canUsedNum').val();
}

//全部选择或全部取消
$("input[name=allBox]").live('click',function(){
	var allIdBox=$("input[name=idBox]");
	if($(this).attr("checked")==true){
		allIdBox.attr("checked",true);
		$("input[name=allBox]").attr("checked",true);
	}else{
		allIdBox.removeAttr("checked");
		$("input[name=allBox]").removeAttr("checked");
	}
});

//checkbox值改变时改变全选checkbox状态
$("input[name=idBox]").live('change',function(){
	var allIdBoxCount=$("input[name=idBox]").length;
	var checkedBoxCount=$("input[name=idBox]:checked").length;
	var allBox=$("input[name=allBox]");
	if(allIdBoxCount==checkedBoxCount){
		allBox.attr("checked",true);
	}else{
		allBox.removeAttr("checked");
	}
});


//解绑优惠券
function unbindCoupons(){
	var checkedBox=$("input[name=idBox]:checked");
	var couponIds='';
	checkedBox.each(function(){
		//只有已绑定的优惠券可解绑
		if(!YouGou.Util.isEmpty($(this).attr('useOrderNumber'))){
			couponIds+=','+$(this).attr('id');
		}
	});

	if(couponIds==''){
		Boxy.alert('<div style="padding:20px 30px;">请选中要解绑的优惠券，只有已绑定的优惠券可解绑</div>', null, {title: '操作提醒'});
		return false;
	}else{
		couponIds=couponIds.substring(1);
		$.post(basePath+'/yitiansystem/marketing/marketingmgmt/unbindCoupons.sc',{
			couponIds : couponIds
		},function(data){
			//操作成功
			if(data.flag==1){
				Boxy.alert('<div style="padding:20px 30px;">操作成功</div>', null, {title: '操作提醒'});
			}else{
				Boxy.alert('<div style="padding:20px 30px;">操作失败</div>', null, {title: '操作提醒'});
			}
		},'json');
	}
}


//分发优惠券
function presentCoupons(){
	var checkedBox=$("input[name=idBox]:checked");
	var couponIds='';
	checkedBox.each(function(){
		//只有未领取的优惠券可分发
		if(YouGou.Util.isEmpty($(this).attr('loginAccountId'))){
			couponIds+=','+$(this).attr('id');
		}
	});

	if(couponIds==''){
		Boxy.alert('<div style="padding:20px 30px;">请选中要分发的优惠券，只有未领取的优惠券可分发</div>', null, {title: '操作提醒'});
		return false;
	}else{
		var contentHtml='<div style="width: 380px;">';
		contentHtml+='<div style="color:red;padding:4px 22px;display:none;" id="errorMsgDiv"></div>';
		contentHtml+='<div style="padding-left:24px;">将选中的优惠券分发给指定用户</div>';
		contentHtml+='<div class="blank5"></div>';
		contentHtml+='<div class="fullpay_tr01"><span class="fullpay_td01">email：</span>';
		contentHtml+=' <input type="text" name="email" id="present_email"/>  </div>';
		contentHtml+='<div class="blank5"></div>';
		contentHtml+='<p style="color:red;padding:4px 22px;">注：只有未领取的优惠券可分发给用户。</p></div>';

		Boxy.form(contentHtml,function(){
			$('#errorMsgDiv').html('').hide();
			$.ajax( {
				type : "POST",
				async : false,
				url : basePath+"/yitianmall/usercener/memberLoginaccount/checkUserEmailIsExist.sc",
				data : {"email" : $('#present_email').val()},
				success : function(data) {
					if(data=='false'){
						$('#errorMsgDiv').html('该邮箱会员不存在，请重新输入').show();
					}
				}
			});

			return YouGou.Util.isEmpty($('#errorMsgDiv').html())?true:false;
		},function() {
			couponIds=couponIds.substring(1);
			$.ajax( {
				type : "POST",
				async : true,
				url : basePath+"/yitiansystem/marketing/marketingmgmt/presentCoupons.sc",
				data : {"email" : $('#present_email').val(),"couponIds":couponIds},
				dataType : "json",
				success : function(data) {
					//操作成功
					if(data.flag==1){
						Boxy.alert('<div style="padding:20px 30px;">操作成功</div>', null, {title: '操作提醒'});
					}else{
						Boxy.alert('<div style="padding:20px 30px;">操作失败</div>', null, {title: '操作提醒'});
					}
				}
			});
		}, {title: '分发优惠券'});
	}
}




