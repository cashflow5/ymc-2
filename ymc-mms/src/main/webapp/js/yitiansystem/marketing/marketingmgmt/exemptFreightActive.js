var timePeriodIndex=1;
var timePeriods=[];

String.prototype.trim = function(){
 	return this.replace(/^\s+|\s+$/g,"");
};

$('#allMember').attr('checked',true);

//显示/隐藏限制会员等级项
$('input[name=member]').change(function(){
	var memberOption=$('#restrictMember');
	if(memberOption.attr("checked")==true){
		$('#control_vip_rank').show();
	}else{
		$('#control_vip_rank').hide();
	}
});

//提交表单
$('#exemptFreightActiveForm').submit(function(){
	if($('#activeName').val().trim().length==0){
		alert('请输入活动名称！');
		return false;
	}

	if(timePeriods.length==0){
		alert('请添加活动时间段！');
		return false;
	}

	if($('#orderAmount').val().trim().length==0){
		alert('请输入订单金额！');
		return false;
	}

	//处理活动时间段
	var strTimePeriod='';
	for(i=0;i<timePeriods.length;i++){
		var tempTimePeriod=$("#timePeriods").data(timePeriods[i]);
		strTimePeriod+=','+'{\'activeStartDate\':\''+tempTimePeriod.startDate
		+'\',\'activeEndDate\':\''+tempTimePeriod.endDate+'\'}';
	}
	$('#timePeriod').val('['+strTimePeriod.substring(1)+']');

	//处理会员级别
	var checkedMember=$("input[name=member]:checked").attr('value');
	if(checkedMember=='0'){
		$('#memberRange').val('all');
	}else{
		var memberRangeGroup=$("input[name=memberRangeItem]:checked");
		var temp='';
		for(var i=0;i<memberRangeGroup.size();i++){
			temp=temp+','+$(memberRangeGroup.get(i)).attr('value');
		}

		if(temp==''){
			alert("请选中会员级别！");
			return false;
		}else{
			$('#memberRange').val(temp.substring(1));
		}
	}
});

//添加时间段
function addTimePeriod(){
	var activeStartDate=$('#activeStartDate').val().trim();
	var activeEndDate=$('#activeEndDate').val().trim();
	if(activeStartDate.length==0){
		alert('请输入开始时间');
		return false;
	}

	if(activeEndDate.length==0){
		alert('请输入结束时间');
		return false;
	}

	//验证时间有效性(不容许时间重叠)
	for(i=0;i<timePeriods.length;i++){
		var tempTimePeriod=$("#timePeriods").data(timePeriods[i]);
		if((activeStartDate>tempTimePeriod.startDate && activeStartDate<=tempTimePeriod.endDate)
				||(activeEndDate>tempTimePeriod.startDate && activeStartDate<=tempTimePeriod.endDate)){
			alert("活动时间发生重叠，请重新设置");
			return false;
		}
	}

	var timePeriodId="TimePeriod_"+timePeriodIndex;
	timePeriods.push(timePeriodId);
	$("#timePeriods").data(timePeriodId,{startDate:activeStartDate,endDate:activeEndDate});

	var timePeriodHtml='<div class="timePeriods_item" id="'+timePeriodId+'"><span>'
		+activeStartDate+'&nbsp;至&nbsp;'+activeEndDate
		+'</span><a href="javascript:void(0)" onclick="removeTimePeriod(\''+timePeriodId+'\')">删除</a></div>';
	$('#timePeriods').html($('#timePeriods').html()+timePeriodHtml);
	timePeriodIndex++;
	$('#activeStartDate').val('');
	$('#activeEndDate').val('');
}

//移除时间段
function removeTimePeriod(timePeriodId){
	timePeriods.pop(timePeriodId);
	$("#timePeriods").removeData(timePeriodId);
	$('#'+timePeriodId).remove();
}

