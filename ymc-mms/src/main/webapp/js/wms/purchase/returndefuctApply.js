// 正则表达式完成页面验证
var config = {
	form : "mainForm",
	submit : validateform,
	fields : []
}
var rowConfig = {
	fields : [ 
		{
		id : 'returnDefectConfirmDetails[index].id',
		name : 'returnDefectConfirmDetails_temp',
		type : 'hidden',
		display : true,
		tdIndex : 0,
		valueIndex : 0,
		validatorFile : '',
		style : ''
	   }
		,
		{
		id : 'returnDefectConfirmDetails[index].outDefectCode',
		name : 'returnDefectConfirmDetails[index].outDefectCode',
		type : 'hidden',
		display : true,
		tdIndex : 1,
		valueIndex : 1,
		validatorFile : '',
		style : ''
	}, 
	{
		id : 'returnDefectConfirmDetails[index].productNo',
		name : 'returnDefectConfirmDetails[index].productNo',
		type : 'hidden',
		display : true,
		tdIndex : 2,
		valueIndex : 2,
		validatorFile : '',
		style : ''
	},
	{
		id : 'returnDefectConfirmDetails[index].goodsName',
		name : 'returnDefectConfirmDetails[index].goodsName',
		type : 'hidden',
		display : true,
		tdIndex : 3,
		valueIndex : 3,
		validatorFile : '',
		style : ''
	}, {
		id : 'returnDefectConfirmDetails[index].specification',
		name : 'returnDefectConfirmDetails[index].specification',
		type : 'hidden',
		display : true,
		tdIndex : 4,
		valueIndex : 4,
		validatorFile : '',
		style : ''
	},  {
		id : 'returnDefectConfirmDetails[index].units',
		name : 'returnDefectConfirmDetails[index].units',
		type : 'hidden',
		display : true,
		tdIndex : 5,
		valueIndex :5,
		validatorFile : '',
		style : ''
	},
	{
		id : 'returnDefectConfirmDetails[index].outQty',
		name : 'returnDefectConfirmDetails[index].outQty',
		type : 'hidden',
		display : true,
		tdIndex : 6,
		valueIndex : 6,
		validatorFile : '',
		style : ''
	},
	// 扩展字段
	{
		id : 'returnDefectConfirmDetails[index].realConfrimQty',
		name : 'returnDefectConfirmDetails[index].realConfrimQty',
		type : 'hidden',
		display : true,
		tdIndex : 7,
		valueIndex : 7,
		validatorFile : '',
		style : 'width:50px;'
	},
	{
		id : 'returnDefectConfirmDetails[index].realApplyQty',
		name : 'returnDefectConfirmDetails[index].realApplyQty',
		type : 'hidden',
		display : true,
		tdIndex : 8,
		valueIndex : 8,
		validatorFile : '',
		style : 'width:50px;'
	},
	{
		id : 'returnDefectConfirmDetails[index].confirmQuantity',
		name : 'returnDefectConfirmDetails[index].confirmQuantity',
		type : 'hidden',
		display : true,
		tdIndex : 9,
		valueIndex : 9,
		validatorFile : 'quantity',
		style : 'width:50px;'
	},
	{
		id : 'returnDefectConfirmDetails[index].purchaseReturnDetailId',
		name : 'returnDefectConfirmDetails[index].purchaseReturnDetailId',
		type : 'hidden',
		display : true,
		tdIndex : 10,
		valueIndex : 10,
		validatorFile : '',
		style : 'width:50px;'
	}]
}


function validatorProduct(checkBoxFlag) {
	var rowIndexArray = new Array();
	if (checkBoxFlag == "1") {
		$("input:checked[id!=checkall]").each(function() {
			rowIndex = $(this).parent().parent().index();
			rowIndexArray[rowIndexArray.length] = rowIndex;
		});
	}
	var validatorResult = true;

	var mesg = "数量格式不正确，必须是非零的正整数且位数最大7位。";


	var quantityRs = validatorInputFile('quantity', /^([0-9]{1,7}|0)(\d{0})?$/,
			mesg, "quantityTip", rowIndexArray, checkBoxFlag);
	validatorResult = quantityRs ? validatorResult : quantityRs;
	return validatorResult;
}


//检查输入数据是否正确
function checkInput(){
	 var linkman=$("#linkman").val();
	 var 	linkmanregExp= /^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]{2,30}$/;
	 if(linkman!=null&&linkman!=""){
	   if( !linkman.match(linkmanregExp)){
			alert("商家联系人只能有2-30个汉字、数字、字母、下划线，不能以下划线开头和结尾");
			return false;
		}
	 }
		
	var phone=$("#phone").val();
	var phoneregExp = /^(\d{3}-?\d{7,8}|\d{4}-?\d{7,8}|\d{7,8})$/;
	if(phone!=null&&phone!=""){
	   if(!phone.match(phoneregExp)){
		   alert("电话号码输入错误 例：0755-88888888");
		   	return false;
		}
	}
	return true;
}
