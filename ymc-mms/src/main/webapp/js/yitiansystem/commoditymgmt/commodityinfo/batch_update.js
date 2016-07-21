var config = {
	form : "batchUpdateFrom",
	submit : submitForm,
	fields : [{
		name : 'excelFile',
		allownull : false,
		regExp : /(.*)\.(xls)$/,
		defaultMsg : '',
		focusMsg : '',
		errorMsg : '必须导入xls文件',
		rightMsg : '',
		msgTip : 'excelFilsTip'
	}]
}

Tool.onReady( function() {
	var f = new Fw(config);
	f.register();
});

function submitForm(result) {
	if(result){
		return true;
	}
	return false;
}

	