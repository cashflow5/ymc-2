
/**
 * 配置大图片的大小
 */

var imgLarge = {width:600, height:200};
/**
 * 配置小图片的大小 
 */
var imgSmall = {width:300, height:150};

/**
 * 
 * @return
 */
function initValidate(){
	
	$('#shopName').blur(function(){
		var shopName = $(this).val();
		if("" == $.trim(shopName)){
			return;
		}
		$.ajax({
			type: "post",
			url:  basePath + "checkNameSame.sc",
			data: {
				"shopName":shopName
			},
			dataType:"text", 
			success: function(data){
				var obj = eval("("+data+")");
				if(obj.reg=="exist"){
					$('#shopNameMsg').removeClass("oncorrect").removeClass("errorMsg");
					$('#shopNameMsg').addClass("onerror").html("该名称已经存在");
					validateNameFlag = false;
				}else if(obj.reg=="notExist"){
					$('#shopNameMsg').removeClass("onerror").removeClass("errorMsg");
					$('#shopNameMsg').addClass("oncorrect").html("该名称可以使用");
					validateNameFlag = true;
				}
			}
		});
	})
} 

// 初始化基路径
var basePath;
// 验证通过标志
var validateNameFlag = false;
$(function(){
	basePath = $('#basepath').val() + "/yitiansystem/commoditymgmt/bizmgmt/shopInformation/";
	initValidate();
	KE.util.setData('shopIntroduction');
	KE.util.setData('brandIntroduction');
});

var config={
		form:"addShopForm",submit:submitForm,
		fields:[
			{name:'shopName',allownull:false,regExp:"",errorMsg:'店铺名称不能为空',msgTip:'shopNameMsg'}, 
//			{name:'shopPerson',allownull:false,regExp:"notempty",focusMsg:'请输入店铺联系人',errorMsg:'店铺联系人不能为空!',msgTip:'shopPersonMsg'},
//			{name:'shopPhone',allownull:false,regExp:"tellAndMobile",focusMsg:'请输入联系电话',errorMsg:'联系电话输入不正确',msgTip:'shopPhoneMsg'},
//			{name:'shopLevel',allownull:false,regExp:"notempty",errorMsg:'店铺等级不能为空',msgTip:'shopLevelMsg'},
//			{name:'shopAssort',allownull:false,regExp:"notempty",errorMsg:'店铺分类不能为空',msgTip:'shopAssortMsg'},
			{name:'brandId',allownull:false,regExp:"",errorMsg:'经营品牌不能为空',msgTip:'shopBrandMsg'}
		]};
	  
Tool.onReady(function(){
	var f = new Fw(config);
	f.register();
});

/**
 * 提交表单
 */
function submitForm(){

	var shopIntroduction = $("#shopIntroduction").val();
	var brandIntroduction = $("#brandIntroduction").val();

	if(validateNameFlag){
		if(shopIntroduction.length>=5000){
			alert("店铺简介,输入文本过长!");
			return false;
		}else if(brandIntroduction.length>=5000){
			alert("品牌简介,输入文本过长!");
			return false;
		}else{
			return true;
		}
	}else{
		return false;
	}
}

//var image = null;
/* 显示图片 */
var imgData ;
function showpic(file,position,img_size){
	var dFile = document.getElementById(file.id);
	if(!dFile.value.match(/.jpg|.gif|.png|.bmp/i)){
		alert('图片类型必须是: .jpg, .gif, .bmp or .png !');
		dFile.value = '';
		return;
	}
	$('#' + position + '_default').remove();
	
	//$("#"+position).empty();
	
	if(dFile.files){
		$("#"+position).html("");
		
		var	image = $("<img>");
		
		imgData = dFile.files[0].getAsDataURL();
		image.attr("src", imgData);
//		image.attr("width","300");
//		image.attr("height","200");
		image.attr("id", "imgid");
		
		if(img_size==1){
			image.attr("width",imgLarge.width);
			image.attr("height",imgLarge.height);
		}else if(img_size==0){
			image.attr("width",imgSmall.width);
			image.attr("height",imgSmall.height);
		}
		$("#"+position).append(image);
		resetH();
	}
	else{
		/* 这步骤是用来在ie6,ie7中显示图片的 */
		var newPreview = document.getElementById(position);
		newPreview.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = dFile.value;
		
		if(img_size==1){
			newPreview.style.width = imgLarge.width + "px";		
			newPreview.style.height = imgLarge.height + "px";
		}else if(img_size==0){
			newPreview.style.width = imgSmall.width + "px";		
			newPreview.style.height = imgSmall.height + "px";
		}
		resetH();
	}
}


