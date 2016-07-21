/**
 * 增加不可配送区域
 */
function toAddDelivery(){
	//showThickBox("增加不可配送区域","../../ordermgmt/paydelivery/toAddPayDelivery.sc?TB_iframe=true&height=400&width=600&modal=true",false);
	openwindow('../../ordermgmt/paydelivery/toAddPayDelivery.sc',570,360,'新增不可配送区域');
	
}

/**
 * 修改不可配送区域
 */
function toUpdateDelivery(id){
	var params = "id="+id;
	//showThickBox("修改不可配送区域","../../ordermgmt/paydelivery/toUpdatePayDelivery.sc?TB_iframe=true&height=400&width=600&modal=true",false,params);
	openwindow('../../ordermgmt/paydelivery/toUpdatePayDelivery.sc?'+params,570,380,'修改不可配送区域');
}

/**
 * 删除不可配送区域
 */
function removeDelivery(id){
	if(confirm("确定要删除该不可配送区域")){
		window.location.href = "d_paydelivery.sc?id="+id;
	}
}

/**
 * 提交表单
 */
function submitForm(){

	var form = document.forms[0];
	form.target="mbdif";		//表单提交后在父页面显示结果
	form.submit();
	window.top.TB_remove();
}