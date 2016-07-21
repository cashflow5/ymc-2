//$(document).ready(function(){
//	$("#purchaseDateId").dateDisplay();
//	$("#deliveryDateId").dateDisplay();
//});

function submits(){
//	var date1=$("#purchaseDateId").val();
//	var date2=$("#deliveryDateId").val();
//	alert(date1+"=="+date2);
	$("#supplierCommodity").submit();
}

/**
* 判断结束日期是否大于开始日期
* @return
*/
function compareDate(){
	var dt1=$("#purchaseDateId").val();
	var dt2=$("#deliveryDateId").val();
	if(dt2==""){
		$("#submitid").get(0).disabled=false;
		return false;
	}
	if(dt2!=""&&dt1==""){
		alert("请填写开始日期");
		$("#submitid").get(0).disabled=true;
		return true;
	}else{
		$("#submitid").get(0).disabled=false;
	}
	dt1=dt1.replace(/-/ig,'')
	dt2=dt2.replace(/-/ig,'')
	if(dt1>dt2){
		alert("结束日期必须大于开始日期");
		$("#submitid").get(0).disabled=true;
		return true;
	}else{
		$("#submitid").get(0).disabled=false;
	}
}
function startDate(){
	//true 不符合要求
	if(compareDate()){
		return;
	}
	var dt1=$("#purchaseDateId").val();
	var dt2=$("#deliveryDateId").val();
	if(""==dt1&&""!=dt2){
		$("#submitid").get(0).disabled=true;
		return;
	}
	if(""!=dt1){
		$("#submitid").get(0).disabled=false;
	}
}


