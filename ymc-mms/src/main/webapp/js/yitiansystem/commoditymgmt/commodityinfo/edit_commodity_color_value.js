var config={
	form:"colorValueForm",submit:submitForm,
 	fields:[
        {name:'colorNo',allownull:false,errorMsg:'色系编码不能为空',msgTip:'colorNoTip'},
		{name:'colorName',allownull:false,errorMsg:'色系名称不能为空',msgTip:'colorNameTip'},
		{name:'sortNo',allownull:true,focusMsg:'请输入数字',errorMsg:'排序只能位数字',msgTip:'sortNoTip'}
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

/*
* 修改店铺分类名称及编码的验证
*/ 
function initValidate(){
	$('#colorValueName').blur(function(){
		var colorValueName = $(this).val();
		if("" == $.trim(colorValueName) || oldColorValueName == $.trim(colorValueName)){
			$('#colorValueNameTip').removeClass("onerror").removeClass("errorMsg");
			return;
		}
		$.ajax({
			type: "POST",
			url:  basePath + "checkColorValueNoOrName.sc",
			data: {
				"colorName":colorValueName,
				"colorNo":''
			},
			dataType:"text", 
			success: function(data){
				if(data == 0){
					$('#colorValueNameTip').removeClass("onerror").removeClass("errorMsg");
					$('#colorValueNameTip').addClass("oncorrect").html("该名称可以使用");
					validateNameFlag = true;
				}else{
					$('#colorValueNameTip').removeClass("oncorrect").removeClass("errorMsg");
					$('#colorValueNameTip').addClass("onerror").html("该名称已经存在");
					validateNameFlag = false;
				}
			}
		});
	})
	
	$('#colorValueNo').blur(function(){
		var colorValueNo = $(this).val();
		if("" == $.trim(colorValueNo) || oldColorValueNo == $.trim(colorValueNo)){
			$('#colorValueNoTip').removeClass("onerror").removeClass("errorMsg");
			return;
		}
		$.ajax({
			type: "POST",
			url:  basePath + "checkColorValueNoOrName.sc",
			data: {
				"colorName":'',
				"colorNo":colorValueNo
			},
			dataType:"text", 
			success: function(data){
				if(data == 0){
					$('#colorValueNoTip').remove("onerror").removeClass("errorMsg");
					$('#colorValueNoTip').addClass("oncorrect").html("该编码可以使用");
					validateCodeFlag = true;
				}else{
					$('#colorValueNoTip').remove("oncorrect").removeClass("errorMsg");
					$('#colorValueNoTip').addClass("onerror").html("该编码已经存在");
					validateCodeFlag = false;
				}
			}
		});
	})
} 

//初始化基路径
var basePath;
var oldColorValueName;//存储颜色名称初始值
var oldColorValueNo;//存储颜色编码初始值
//验证通过标志
var validateNameFlag = true;
var validateCodeFlag = true;
$(function(){
	basePath = $('#basepath').val() + "/yitiansystem/commoditymgmt/commodityinfo/colorvalue/";
	oldColorValueName = $('#colorValueName').val();
	oldColorValueNo = $('#colorValueNo').val();
	initValidate();
});

/*显示图片*/
function showpic(e){

	var dFile = $(e)[0];
	var div = $(e).parent().find('div');
	if(!dFile.value.match(/.jpg|.gif|.png|.bmp/i)){
		alert('图片类型必须是: .jpg, .gif, .bmp or .png !');
		return;
	}
	if(dFile.files){
		div.empty();
		var image = $("<img>");
		var imgData = dFile.files[0].getAsDataURL();
		image.attr("src", imgData);
		image.attr("id", "imgid");
		image.attr("width","10");
		image.attr("height","10");
		div.append(image);
	}
	else{
		/*这步骤是用来在ie6,ie7中显示图片的*/
		div.empty();
		div[0].filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = dFile.value;
		div[0].style.width = "10px";
		div[0].style.height = "10px";
	}
}