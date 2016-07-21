$(document).ready(function(){
	// Tab页切换
	$('#coupons_tab').find('.unselect').click(function(){
		// 清空查询条件
		$('#startCreateTime').val('');
		$('#endCreateTime').val('');
		$('#state').val('');
		$('#issueWays').val('');

		// alert($('page').val());

		$('#type').val($(this).attr('type'));
		$('#marketingCouponSchemeForm').submit();
	});
});

String.prototype.trim = function(){
 	return this.replace(/^\s+|\s+$/g,"");
}

//Tab选中样式
function getTabSelectClass(category,curSelect){
	if(category ==curSelect){
		document.write('<ul class="onselect" type="'+curSelect+'">');
	}else{
		document.write('<ul class="unselect" type="'+curSelect+'">');
	}
}

//删除优惠券方案
function deleteMarketingCouponScheme(id) {
	Boxy.confirm('<div style="padding:20px 30px;">确认删除吗？</div>',function() {
		$.post(basePath+'/yitiansystem/marketing/marketingmgmt/deleteMarketingCouponScheme.sc',{
			couponSchemeId : id
		},function(data){
			if(data=='1') {
				alert('删除成功!');
				window.location.reload();
			}else {
				Boxy.alert('<div style="padding:20px 30px;">操作失败</div>', null, {title: '操作提醒'});
			}
		},'json');
	}, {title: '下载优惠券'});
}

//审核优惠券方案
function checkMarketingCouponScheme(couponSchemeId){
	Boxy.confirm('<div style="padding:20px 30px;">确定审批通过吗？</div>',function() {
		$.post(basePath+'/yitiansystem/marketing/marketingmgmt/checkMarketingCouponScheme.sc',{
			couponSchemeId : couponSchemeId
		},function(data){
			//操作成功
			if(data.flag==1){
				//Boxy.alert('<div style="padding:20px 30px;">操作成功</div>', null, {title: '操作提醒'});
				alert('操作成功!');
				window.location.reload();
				//refreshCouponShow(couponSchemeId,data.state);
			}else{
				Boxy.alert('<div style="padding:20px 30px;">操作失败</div>', null, {title: '操作提醒'});
			}
		},'json');
	}, {title: '下载优惠券'});
}

//停止优惠券方案
function stopMarketingCouponScheme(couponSchemeId){
	$.post(basePath+'/yitiansystem/marketing/marketingmgmt/stopMarketingCouponScheme.sc',{
		couponSchemeId : couponSchemeId
	},function(data){
		//操作成功
		if(data.flag==1){
			//Boxy.alert('<div style="padding:20px 30px;">操作成功</div>', null, {title: '操作提醒'});
			//refreshCouponShow(couponSchemeId,data.state);
			alert('操作成功!');
			window.location.reload();
		}else{
			Boxy.alert('<div style="padding:20px 30px;">操作失败</div>', null, {title: '操作提醒'});
		}
	},'json');
}

//启动优惠券方案
function startMarketingCouponScheme(couponSchemeId){
	$.post(basePath+'/yitiansystem/marketing/marketingmgmt/checkMarketingCouponScheme.sc',{
		couponSchemeId : couponSchemeId
	},function(data){
		//操作成功
		if(data.flag==1){
			//Boxy.alert('<div style="padding:20px 30px;">操作成功</div>', null, {title: '操作提醒'});
			alert('操作成功');
			window.location.reload();
			//refreshCouponShow(couponSchemeId,data.state);
		}else{
			Boxy.alert('<div style="padding:20px 30px;">操作失败</div>', null, {title: '操作提醒'});
		}
	},'json');
}

//刷新页面显示
function refreshCouponShow(couponSchemeId,state){
	var objTD_1=$('#td_1_'+couponSchemeId);
	var objTD_2=$('#td_2_'+couponSchemeId);
	var objTD_3=$('#td_3_'+couponSchemeId);

	var issueWays=objTD_1.attr('issueWays');
	if(state==1){
		if(issueWays.indexOf('COUPON_ISSUE_WAY_OFF_LINE')>0){
			objTD_1.html('<a href="javascript:void(0)" onclick="generateCoupon(\''+couponSchemeId+'\')">生成</a>&nbsp;&nbsp;'
					+'<a href="javascript:void(0)" onclick="downloadCoupon(\''+couponSchemeId+'\')">下载</a>');
		}
		objTD_2.html('活动中');
		objTD_3.html('<a href="javascript:void(0)" onclick="stopMarketingCouponScheme(\''+couponSchemeId+'\')">做停</a>&nbsp;&nbsp;'
			+'<a href="'+basePath+'/yitiansystem/marketing/marketingmgmt/queryCouponListForOneScheme.sc?id='+couponSchemeId+'">活动追踪</a>');
	}else if(state==3){
		objTD_2.html('停止');
		objTD_3.html('<a href="javascript:void(0)" onclick="startMarketingCouponScheme(\''+couponSchemeId+'\')">启用</a>&nbsp;&nbsp;'
			+'<a href="'+basePath+'/yitiansystem/marketing/marketingmgmt/queryCouponListForOneScheme.sc?id='+couponSchemeId+'">活动追踪</a>');
	}
}

//生成优惠券
function generateCoupon(id){
	var objTD_1=$('#td_1_'+id);
	var amount=objTD_1.attr('amount');
	var haveUsedNum=objTD_1.attr('haveUsedNum');
	var name=objTD_1.attr('name');
	var canUsedNum=amount-haveUsedNum;
	var contentHtml='<div style="width: 350px;">';
	contentHtml+='<div style="color:red;padding:4px 22px;display:none;" id="errorMsgDiv"></div>';
	contentHtml+='<div class="fullpay_tr01"><span class="fullpay_td01">优 惠 券 ：</span>';
	contentHtml+=name+'</div>';
	contentHtml+='<div class="blank5"></div>';
	contentHtml+='<div class="fullpay_tr01"><span class="fullpay_td01">生成数量：</span>';
	contentHtml+='<input type="text" name="canUsedNum" id="canUsedNum" size="12" value="'+canUsedNum+'"/>';
	contentHtml+='<font class="ft-cl-Exp">&nbsp;&nbsp;最多'+canUsedNum+'张</font></div>';
	contentHtml+='<div class="blank5"></div>';
	contentHtml+='<p style="color:red;padding:4px 22px;">注：每次最多生成10000张优惠券</p></div>';

	Boxy.form(contentHtml,function(){
		var usedNum=$('#canUsedNum').val().trim();
		if(usedNum.length==0){
			$('#errorMsgDiv').html('请输入生成优惠券数量').show();
			return false;
		}

		if(parseInt(usedNum)>10000){
			$('#errorMsgDiv').html('每次最多生成10000张优惠券').show();
			return false;
		}

		if(parseInt(usedNum)>canUsedNum){
			$('#errorMsgDiv').html('最多可生成'+canUsedNum+'张优惠券').show();
			return false;
		}
		return true;
	},function() {
		$("#contentMain").mask("Waiting...");
		$.post(basePath+'/yitiansystem/marketing/marketingmgmt/u_GenerateMarketingCoupon.sc',{
			id : id,num:$('#canUsedNum').val()
		},function(data){
			alert("已成功生成"+data+"张优惠券");
			$("#contentMain").unmask();
			window.location.reload();
		},'json');
	}, {title: '生成优惠券'});
}

function checkValid(haveUsedNum){
	var startIndex=$('#startIndex');
	var endIndex=$('#endIndex');

	var isNumber =/^[0-9]*[1-9][0-9]*$/;//正整数正则
	if(!isNumber.test(endIndex.val())||endIndex.val()>haveUsedNum){
		$('#endIndex').val(haveUsedNum);
	}

	if(!isNumber.test(startIndex.val())||startIndex.val()>endIndex.val()){
		$('#startIndex').val(1);
	}
}

//下载优惠券
function downloadCoupon(id){
	var objTD_1=$('#td_1_'+id);
	var amount=objTD_1.attr('amount');
	var haveUsedNum=objTD_1.attr('haveUsedNum');
	var name=objTD_1.attr('name');
	var contentHtml='<div style="width: 380px;">';
	contentHtml+='<div style="color:red;padding:4px 22px;display:none;" id="errorMsgDiv"></div>';
	contentHtml+='<div class="fullpay_tr01"><span class="fullpay_td01">优 惠 券 ：</span>';
	contentHtml+=name+'</div>';
	contentHtml+='<div class="blank5"></div>';
	contentHtml+='<div class="fullpay_tr01"><span class="fullpay_td01">下载数量：</span>';
	contentHtml+='第 <input type="text" name="startIndex" id="startIndex" onblur="checkValid('+haveUsedNum;
	contentHtml+=')" size="8" value="1"/> 到 ';
	contentHtml+='<input type="text" name="endIndex" id="endIndex" size="8" onblur="checkValid('+haveUsedNum;
	contentHtml+=')" value="'+haveUsedNum+'"/> 张 ';
	contentHtml+='<font class="ft-cl-Exp">&nbsp;&nbsp;（最多'+haveUsedNum+'张）</font></div>';
	contentHtml+='<div class="blank5"></div>';
	contentHtml+='<p style="color:red;padding:4px 22px;">注：只能下载尚未被领取的优惠券，且每次最多下载60000张。</p></div>';

	Boxy.form(contentHtml,function(){
		var startIndex=parseInt($('#startIndex').val().trim());
		var endIndex=parseInt($('#endIndex').val().trim());
		haveUsedNum=parseInt(haveUsedNum);

		if(haveUsedNum == 0){
			$('#errorMsgDiv').html('暂时无优惠券可下载，您可以先生成优惠券再下载').show();
			return false;
		}

		if(startIndex<1 || endIndex < startIndex || endIndex>haveUsedNum){
			$('#errorMsgDiv').html('参数设置有误，请重新设置').show();
			return false;
		}

		if(endIndex-startIndex > 60000){
			$('#errorMsgDiv').html('每次最多下载60000张优惠券').show();
			return false;
		}

		return true;
	},function() {
		window.location.href=basePath+'/yitiansystem/marketing/marketingmgmt/downloadMarketingCoupon.sc?id='
			+id+'&startIndex='+$('#startIndex').val()+"&endIndex="+$('#endIndex').val();
	}, {title: '下载优惠券'});
}
