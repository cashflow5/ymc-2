$(document).ready(function() {
	$('#bTime').calendar({
		maxDate : '#eTime',
		format : 'yyyy-MM-dd',
		targetFormat : 'yyyy-MM-dd'
	});
	$('#eTime').calendar({
		minDate : '#bTime',
		format : 'yyyy-MM-dd',
		targetFormat : 'yyyy-MM-dd'
	});
});
function doQuery() {
	if(document.getElementById("commodityCode").value==""||document.getElementById("commodityCode").value==null){
		 alert("货品编码不能为空！");
		 return false;
		}
	
	var queryForm = document.getElementById("queryForm");
	queryForm.action = queryUrl;
	queryForm.submit();
}
// 
function doExportRefuse() {
	if($("commodityCode")==""||$("commodityCode")==null){
		 alert("货品编码不能为空！");
		}
	var exportForm = document.getElementById("queryForm");
	exportForm.action = doExportRefuseReportUrl;
	exportForm.submit();
	// -_-!!
	exportForm.action = queryUrl;
}