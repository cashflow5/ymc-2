// 正则表达式完成页面验证
var config = {
	form : "mainForm",
	submit : validateform,
	fields : [{
				name : 'minInventoryQuantity',
				msgTip : 'minInventoryQuantitytip',
				regExp : /^\+?[1-9][0-9]{0,11}$/,
				allownull : false,
				defaultMsg : '',
				rightMsg : '',
				errorMsg : ''
			},{
				name : 'maxInventoryQuantity',
				msgTip : 'maxInventoryQuantitytip',
				regExp : /^\+?[1-9][0-9]{0,11}$/,
				allownull : false,
				defaultMsg : '',
				rightMsg : '',
				errorMsg : ''
			}]
}

