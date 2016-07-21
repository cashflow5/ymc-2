var printexpressnew = {};
$(function(){
	$('input[name="logisticsCompan"]').eq(0).attr("checked",true);
	$("#con1").attr("checked",true);
	//printexpressnew.choosePrintExpressTemplate($('input[name="logisticsCompan"]').eq(0).val());
	
	
	
});

document.onkeydown = function(e){
	var ev = document.all ? window.event : e;  
    if(ev&&ev.keyCode==13) {// 如（ev.ctrlKey && ev.keyCode==13）为ctrl+Center 触发  
    	printexpressnew.choosePrintExpressTemplate('print');
    }  
};

printexpressnew.choosePrintExpressTemplate = function(type){
	var logisticsId = $('input[name="logisticsCompan"]:checked').val();
	var logisticsName = $('input[name="logisticsCompan"]:checked').attr("logisticsName");
	var logisticsCode  = $('input[name="logisticsCompan"]:checked').attr("logisticsCode");
	var connection = $('input[name="connection"]:checked').val();
	if($("#expressId").val()==""&&$("#expressIds").val()==""){
		ygdg.dialog.alert("请输入快递单号！");	
	}else if(!checkExpressNo()){
		ygdg.dialog.alert("请填写正确的快递单号！");	
	}else{
		ymc_common.loading("show","正在获取打印信息......");
		$.ajax({
			cache: false,
	        async: false,
	        type: 'POST',
	        dataType:'json',
	        url: printexpressnew.basePath+"/order/getPrintExpressTemplateInfoAjax.sc",
	        data: {
	        	logisticsId:logisticsId,
	        	orderNos:printexpressnew.orderNos,
	        	expressNos:getExpressNos(),
	        	connection:connection,
	        	logisticsName:logisticsName,
	        	logisticsCode:logisticsCode
	        },
	        success: function(data){
	        	ymc_common.loading();
	        	if(data.result=="success"){
	        		var tempList = data.expressTemplateList;
	        		//console.info(tempList);
	             	LODOP=getLodop();
	             	if(LODOP==null){
	             		return;
	             	}
	        		for(var i=0,length=tempList.length;i<length;i++){
	        			LODOP.NewPage();
	        			eval(tempList[i]);
	        			LODOP.SET_SHOW_MODE("BKIMG_PRINT",0);
		        		//LODOP.SET_PRINT_PAGESIZE(1,0,0,"");
	        		}
	        		LODOP.SET_SHOW_MODE("LANDSCAPE_DEFROTATED",1);//纵向打印时显示正面朝上
	        		if(type=="view"){
	        			LODOP.PREVIEW();
	        		}else if(type=="print"){
	        			LODOP.PRINT();
	        		}
	        		
	        	}else{
	        		ygdg.dialog.alert(data.msg);	
	        	}
			}
	     });
	}
};
printexpressnew.showExpressType = function(obj){
	var val = obj.value;
	$("#expressId").val("");
	$("#expressIds").val("");
	if(val == 0){
		$("#conId1").show();
		$("#conId2").hide();
	} else {
		$("#conId1").hide();
		$("#conId2").show();
	}
};

function checkExpressNoValid(expressNo) {
	var regex = new RegExp('[^\\w\*]{1,}', 'gi');
	return !regex.test(expressNo);
}

function checkExpressNo() {
	if(printexpressnew.orderCount==1){
		var expressId = $("#expressId").val();
		if (expressId != '' && expressId.length != 0) {
			return checkExpressNoValid($.trim(expressId));
		}
	}else if(printexpressnew.orderCount>1){
		if($("#con1").attr("checked")){
			var expressId = $("#expressId").val();
			if (expressId != '' && expressId.length != 0) {
				return checkExpressNoValid($.trim(expressId));
			}
		}else if($("#con2").attr("checked")){
			var expressIds = $("#expressIds").val();
			if (expressIds != '' && expressIds.length != 0) {
				var array = expressIds.split(",");
				for (var i = 0; i < array.length; i++) {
					if (!checkExpressNoValid($.trim(array[i]))) {
						return false;
					} 
				}
			}
		}
	}
	return true;
}

function getExpressNos(){
	if(printexpressnew.orderCount==1){
		var expressId = $("#expressId").val();
		return expressId;
	}else if(printexpressnew.orderCount>1){
		if($("#con1").attr("checked")){
			var expressId = $("#expressId").val();
			return expressId;
		}else if($("#con2").attr("checked")){
			var expressIds = $("#expressIds").val();
			return expressIds;
		}
	}
	return null;
}

//清空空格的方法
String.prototype.trim = function() {
	return this.replace(/(^[\s\t\xa0\u3000]+)|([\u3000\xa0\s\t]+$)/g, "");
};