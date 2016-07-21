//验证名称的结构标志
var validateFlag=true;
var config = {
	form : "submitForm",
	submit : submitForms,
	fields : [{
		name : 'type',
		allownull : false,
		regExp : "intege",
		defaultMsg : '请选择采购类型',
		focusMsg : '请选择采购类型',
		errorMsg : '请选择采购类型',
		rightMsg : '采购类型输入正确',
		msgTip : 'typeTip'
	},{
		name : 'warehouse.id',
		allownull : false,
		regExp : "intege",
		defaultMsg : '请选择物理仓库',
		focusMsg : '请选择物理仓库',
		errorMsg : '请选择物理仓库',
		rightMsg : '物理仓库输入正确',
		msgTip : 'warehouse.idTip'
	},{
		name : 'supplier',
		allownull : false,
		regExp : "name",
		defaultMsg : '请选择输入供应商',
		focusMsg : '请选择输入供应商',
		errorMsg : '请选择输入供应商',
		rightMsg : '供应商输入正确',
		msgTip : 'supplierTip'
	},{
		name : 'purchaser',
		allownull : false,
		regExp : "name",
		defaultMsg : '请输入采购员',
		focusMsg : '请输入采购员',
		errorMsg : '请输入采购员',
		rightMsg : '采购员输入正确',
		msgTip : 'purchaserTip'
	}]
}

Tool.onReady( function() {
	var f = new Fw(config);
	f.register();
});

/**
 * 提交表单
 */
function submitForms() {
	result=true;
	if(result){		
		return result;
	}
	return false;

}