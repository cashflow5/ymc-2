

var basePath;
$(document).ready(function(){
	 basePath = $("#basepath").val();
});


function u_colorValue(colorId){
	window.location = basePath + "/yitiansystem/commoditymgmt/commodityinfo/colorvalue/toEditColorValue.sc?colorId="+colorId;
}


function d_colorValue(colorId){
	if(confirm("确定要删除该颜色吗？")){
		window.location = basePath + "/yitiansystem/commoditymgmt/commodityinfo/colorvalue/d_deleteColorValue.sc?colorId="+colorId;
	}
}