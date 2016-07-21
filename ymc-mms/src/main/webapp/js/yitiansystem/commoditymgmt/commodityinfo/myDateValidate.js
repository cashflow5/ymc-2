
jQuery.fn.extend({
	dateDisplay : function(url) {
		$(this).datepicker({
		    yearRange: '1960:2100', //取值范围.
		    showOn: 'both', //输入框和图片按钮都可以使用日历控件。
		    buttonImage: url + '/images/calendar.gif', //日历控件的按钮
		    buttonImageOnly: true,
		    showButtonPanel: true,
		    buttonText:'点击选择日期', 
		    onClose : closeDatePiceker
		});
	}
});

function getSystemDate(){
	var systemDate = new Date();
	var year = systemDate.getFullYear();
	var month = systemDate.getMonth() >= 9 ? systemDate.getMonth() + 1 : '0' + (systemDate.getMonth() + 1);
	var day = systemDate.getDate() >= 9 ? systemDate.getDate() : '0' + systemDate.getDate();
	return year + '-' + month + '-' + day;
}

//关闭日期控件前，验证日期的合法性
//*************id的起止（必须含Start）与结束时间（必须含End），其余字符一样
function closeDatePiceker(){
	var currDate = $(this).val();
	var isEnd = (this.id).indexOf('End') > -1 ? true : false;
	var otherId = isEnd ? (this.id).replace('End','Start') : (this.id).replace('Start','End');
	var otherDate = $('#' + otherId).val();
	if(!currDate){
		return ;
	}
	if(otherDate){
		if(isEnd){
			if(otherDate > currDate){
				alert('结束时间必须大于等于开始时间');
				$(this).val(''); 
			}else{
				$(this).val(currDate > systemDate ? systemDate : currDate);
			}
		}else{
			if(otherDate < currDate){
				alert('开始时间必须小于等于结束时间');
				$(this).val(''); 
			}else{
				$(this).val(currDate > systemDate ? systemDate : currDate);
			}
		}
	}else{
		$(this).val(currDate > systemDate ? systemDate : currDate);
	}
}

//暂时从客户端获取当前时间，应该从服务器获取。
var systemDate = getSystemDate();

//$(function(){
//	$('input[id*=Start|End]').each(function(){
//		var basepath = $('#basepath').val();
//		$(this).dateDisplay(basepath);
//	})
//})