$(function(){
	var val=$("#excelHidden").val();
	//有数据
	if(undefined==val){
		$("#excel1").show();
		//$("#excel2").show();
	}else{
		//没有数据
		$("#excel1").hide();
		//$("#excel2").hide();
	}
});