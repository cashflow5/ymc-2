var config={form:"shopLevelForm",submit:submitForm,
	fields:[
		{name:'levelNo',allownull:false,regExp:"",errorMsg:'等级编码不能为空',msgTip:'levelNoTip'}, 
		{name:'levelName',allownull:false,regExp:"",errorMsg:'等级名称不能为空',msgTip:'levelNameTip'},
		{name:'sortNo',allownull:false,regExp:"intege1",focusMsg:'请输入排序',errorMsg:'排序只能为数字',msgTip:'sortNoTip'}
	]}
  
Tool.onReady(function(){
	var f = new Fw(config);
	f.register();
});

function submitForm(){
	if(validateNameFlag && validateCodeFlag){
		if(validateNotNull()){
			return true;
		}else 
			return false;
	}else{
		return false;
	}
}

/**
 * 排序是否为空的验证
 * @return
 */
function validateNotNull(){
	var sortNo = $(":input[type='text'][name='sortNo']").val();
	
	if(sortNo==""||$.trim(sortNo)==""){
		$('#sortNoTip').removeClass("onerror").removeClass("errorMsg");
		$('#sortNoTip').addClass("onerror").html("请输入数字");
		return false;
	}else{
		$('#sortNoTip').removeClass("onerror").removeClass("errorMsg");
		$('#sortNoTip').addClass("oncorrect").html("请输入数字");
		return true;
	}
}

 /**
 * 店铺等级名称及编码的验证
 * @return
 */
function initValidate(){
	
	$('#levelName').blur(function(){
		var levelName = $(this).val();
		if("" == $.trim(levelName)){
			return;
		}
		$.ajax({
			type: "POST",
			url:  basePath + "checkShopLevel.sc",
			data: {
				"levelName":levelName,
				"levelNo":''
			},
			dataType:"json", 
			success: function(data){
				if(data[0].nameLength == 0){
					$('#levelNameTip').removeClass("onerror").removeClass("errorMsg");
					$('#levelNameTip').addClass("oncorrect").html("该名称可以使用");
					validateNameFlag = true;
				}else{
					$('#levelNameTip').removeClass("oncorrect").removeClass("errorMsg");
					$('#levelNameTip').addClass("onerror").html("该名称已经存在");
					validateNameFlag = false;
				}
			}
		});
	});
	
	$('#levelNo').blur(function(){
		var levelNo = $(this).val();
		if("" == $.trim(levelNo)){
			return;
		}
		$.ajax({
			type: "POST",
			url:  basePath + "checkShopLevel.sc",
			data: {
				"levelName":'',
				"levelNo":levelNo
			},
			dataType:"json", 
			success: function(data){
				if(data[0].codeLength == 0){
					$('#levelNoTip').remove("onerror").removeClass("errorMsg");
					$('#levelNoTip').addClass("oncorrect").html("该编码可以使用");
					validateCodeFlag = true;
				}else{
					$('#levelNoTip').remove("oncorrect").removeClass("errorMsg");
					$('#levelNoTip').addClass("onerror").html("该编码已经存在");
					validateCodeFlag = false;
				}
			}
		});
	});
}  
 
//初始化基路径
var basePath;
//验证通过标志
var validateNameFlag = false;
var validateCodeFlag = false;
$(function(){
	basePath = $('#basepath').val() + "/yitiansystem/commoditymgmt/bizmgmt/shoplevel/";
	initValidate();
});
