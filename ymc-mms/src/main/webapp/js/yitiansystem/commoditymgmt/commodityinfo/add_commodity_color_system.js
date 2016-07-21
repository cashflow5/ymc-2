var config={
	form:"colorSystemForm",submit:submitForm,
 	fields:[
 	    {name:'colorSystemNo',allownull:false,errorMsg:'色系编码不能为空',msgTip:'colorNoTip'},
 		{name:'colorSystemName',allownull:false,errorMsg:'色系名称不能为空',msgTip:'colorNameTip'},
		{name:'sortNo',allownull:true,regExp:"intege1",focusMsg:'请输入数字',errorMsg:'排序只能为数字',msgTip:'sortNoTip'}
	]
}

Tool.onReady(function(){
	var f = new Fw(config);
	f.register();
});
 
function submitForm(){
	if(validateNameFlag && validateCodeFlag){
		return true;
	}else{
		return false;
	}
}

 /**
  * 店铺分类名称及编码的验证
  * @return
  */
function initValidate(){
	
	$('#colorSystemName').blur(function(){
		var colorSystemName = $(this).val();
		if("" == $.trim(colorSystemName)){
			return;
		}
		$.ajax({
			type: "POST",
			url:  basePath + "checkColorSystemNoOrName.sc",
			data: {
				"colorSystemName":colorSystemName,
				"colorSystemNo":''
			},
			async:false,
			dataType:"text", 
			success: function(d){
				if(d == 0){
					$('#colorSystemNameTip').removeClass("onerror").removeClass("errorMsg");
					$('#colorSystemNameTip').addClass("oncorrect").html("该名称可以使用");
					validateNameFlag = true;
				}else{
					$('#colorSystemNameTip').removeClass("oncorrect").removeClass("errorMsg");
					$('#colorSystemNameTip').addClass("onerror").html("该名称已经存在");
					validateNameFlag = false;
				}
			}
		});
	})
	
	$('#colorSystemNo').blur(function(){
		var colorSystemNo = $(this).val();
		if("" == $.trim(colorSystemNo)){
			return;
		}
		$.ajax({
			type: "POST",
			url:  basePath + "checkColorSystemNoOrName.sc",
			async: false,
			data: {
				"colorSystemNo":colorSystemNo,
				"colorSystemName":''
			},
			dataType:"text", 
			success: function(d){
				if(d == 0){
					$('#colorSystemNoTip').remove("onerror").removeClass("errorMsg");
					$('#colorSystemNoTip').addClass("oncorrect").html("该编码可以使用");
					validateCodeFlag = true;
				}else{
					$('#colorSystemNoTip').remove("oncorrect").removeClass("errorMsg");
					$('#colorSystemNoTip').addClass("onerror").html("该编码已经存在");
					validateCodeFlag = false;
				}
			}
		});
	})
} 
 
//初始化基路径
var basePath;
//验证通过标志
var validateNameFlag = false;
var validateCodeFlag = false;

$(function(){
   basePath = $('#basepath').val() + "/yitiansystem/commoditymgmt/commodityinfo/colorsystem/";
   initValidate();
});