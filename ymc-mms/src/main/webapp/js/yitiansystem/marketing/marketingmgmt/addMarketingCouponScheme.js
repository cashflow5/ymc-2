$(document).ready(function(){
	$('input[name=validateMode]').click(function(){
		if($(this).attr("value")=='1'){
			$('#validateTime_days').hide();
			$('#validateTime_period').show();
		}else{
			$('#validateTime_period').hide();
			$('#validateTime_days').show();
		}
	});

	//非空验证
    var config={
  		form:"marketingCouponSchemeForm",
  		submit:saveMarketingCouponScheme,
	 	fields:[
			{name:'couponName',allownull:false,regExp:/^[\w\u4E00-\u9FA5\uF900-\uFA2D]|[\w.*]*$/,defaultMsg:'请输入优惠券名称',focusMsg:'请输入优惠券名称',errorMsg:'请输入优惠券有误',rightMsg:'优惠券名称输入正确',msgTip:'couponNametip'},
			{name:'parValue',allownull:false,regExp:/^[1-9]\d*$/,defaultMsg:'请输入面值',focusMsg:'请输入面值',errorMsg:'请输入面值',rightMsg:'面值输入正确',msgTip:'parValuetip'},
			{name:'lowestPay',allownull:false,regExp:/^[0-9]\d*$/,defaultMsg:'请输入最低消费额',focusMsg:'请输入最低消费额',errorMsg:'请输入最低消费额',rightMsg:'最低消费额输入正确',msgTip:'lowestPaytip'},
			{name:'issueAmount',allownull:false,regExp:/^[1-9][0-9]{0,7}$/,defaultMsg:'请输入发放数量',focusMsg:'请输入发放数量',errorMsg:'请输入发放数量，最大9位',rightMsg:'发放数量输入正确',msgTip:'issueAmounttip'}
		]
	};

	Tool.onReady(function(){
		var f = new Fv(config);
		f.register();
	});

	function saveMarketingCouponScheme() {
		var issueStartdate = $("#issueStartdate").val();
		var issueEnddate = $("#issueEnddate").val();
		if(issueStartdate=="") {
			alert("发放起始时间不能为空！");
			return false;
		}
		if(issueEnddate=="") {
			alert("发放结束时间不能为空！");
			return false;
		}

		var validateMode=$('#validateMode_1');
		if(validateMode.attr("checked")==true){
			var useStartdate = $("#useStartdate").val();
			var useEnddate = $("#useEnddate").val();
			if(useStartdate=="") {
				alert("使用起始时间不能为空！");
				return false;
			}
			if(useEnddate=="") {
				alert("使用结束时间不能为空！");
				return false;
			}
		}else{
			var validDays=$('#validDays').val();
			if(YouGou.Util.isEmpty(validDays)){
				alert( "有效天数不能为空 ");
				return false;
			}
		}

		//处理发放渠道值
		var issueWayGroup=$("input[name=issueWayItem]:checked");
		var temp='';
		for(var i=0;i<issueWayGroup.size();i++){
			var issueWay=$(issueWayGroup.get(i));
			temp=temp+','+issueWay.attr('value');
		}

		if(temp==''){
			alert("请选中发放渠道！");
			return false;
		}else{
			$('#issueWays').val(temp.substring(1));
		}

		saveScope();

		return true;
	}
});
