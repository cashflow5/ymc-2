var config={
	form:"updateShopSortForm",submit:submitForm,
 	fields:[
        {name:'assortCode',allownull:false,regExp:"",errorMsg:'分类编码不能为空',msgTip:'assortCodeTip'},
		{name:'assortName',allownull:false,regExp:"",errorMsg:'分类名称不能为空',msgTip:'assortNameTip'},
		{name:'assortNo',allownull:true,regExp:"intege1",focusMsg:'请输入数字',errorMsg:'排序只能位数字',msgTip:'assortNoTip'}
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
	
	$('#assortName').blur(function(){
		var assortName = $(this).val();
		if("" == $.trim(assortName) || oldAssortName == $.trim(assortName)){
			return;
		}
		$.ajax({
			type: "POST",
			url:  basePath + "checkShopAssort.sc",
			data: {
				"assortName":assortName,
				"assortCode":''
			},
			dataType:"json", 
			success: function(data){
				if(data[0].nameLength == 0){
					$('#assortNameTip').removeClass("onerror").removeClass("errorMsg");
					$('#assortNameTip').addClass("oncorrect").html("该名称可以使用");
					validateNameFlag = true;
				}else{
					$('#assortNameTip').removeClass("oncorrect").removeClass("errorMsg");
					$('#assortNameTip').addClass("onerror").html("该名称已经存在");
					validateNameFlag = false;
				}
			}
		});
	})
	
	$('#assortCode').blur(function(){
		var assortCode = $(this).val();
		if("" == $.trim(assortCode) || oldAssortCode == $.trim(assortCode)){
			return;
		}
		$.ajax({
			type: "POST",
			url:  basePath + "checkShopAssort.sc",
			data: {
				"assortName":'',
				"assortCode":assortCode
			},
			dataType:"json", 
			success: function(data){
				if(data[0].codeLength == 0){
					$('#assortCodeTip').remove("onerror").removeClass("errorMsg");
					$('#assortCodeTip').addClass("oncorrect").html("该编码可以使用");
					validateCodeFlag = true;
				}else{
					$('#assortCodeTip').remove("oncorrect").removeClass("errorMsg");
					$('#assortCodeTip').addClass("onerror").html("该编码已经存在");
					validateCodeFlag = false;
				}
			}
		});
	})
} 

//初始化基路径
var basePath;
var oldAssortName;//存储分类名称初始值
var oldAssortCode;//存储分类编码初始值
//验证通过标志
var validateNameFlag = true;
var validateCodeFlag = true;
$(function(){
	basePath = $('#basepath').val() + "/yitiansystem/commoditymgmt/bizmgmt/shopassortlist/";
	oldAssortName = $('#assortName').val();
	oldAssortCode = $('#assortCode').val();
	initValidate();
});



