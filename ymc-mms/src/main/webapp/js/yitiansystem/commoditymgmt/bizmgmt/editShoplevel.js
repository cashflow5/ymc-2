var config={form:"shopLevelForm",submit:submitForm,
	fields:[
		{name:'levelNo',allownull:false,regExp:"",errorMsg:'等级编码不能为空',msgTip:'levelNoTip'}, 
		{name:'levelName',allownull:false,regExp:"",errorMsg:'等级名称不能为空',msgTip:'levelNameTip'},
		{name:'sortNo',allownull:true,regExp:"intege1",focusMsg:'请输入排序',errorMsg:'排序只能为数字',msgTip:'sortNoTip'}
	]}
  
Tool.onReady(function(){
	var f = new Fw(config);
	f.register();
});

/**
 * 提交表单
 */
function submitForm(){
	if(validateNameFlag && validateCodeFlag){
		return true;
	}else{
		return false;
	}
} 

/**
 * 店铺等级名称及编码的验证
 * @return
 */
function initValidate(){
	
	$('#levelName').blur(function(){
		var levelName = $(this).val();
		if("" == $.trim(levelName) || oldLevelName == $.trim(levelName)){
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
	})
	
	$('#levelNo').blur(function(){
		var levelNo = $(this).val();
		if("" == $.trim(levelNo) || oldLevelNo == $.trim(levelNo)){
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
	})
}  
 

//初始化基路径
var basePath;
var oldLevelName;//存储分类名称初始值
var oldLevelNo;//存储分类编码初始值
//验证通过标志
var validateNameFlag = true;
var validateCodeFlag = true;
$(function(){
	basePath = $('#basepath').val() + "/yitiansystem/commoditymgmt/bizmgmt/shoplevel/";
	oldLevelNo = $('#levelNo').val();
	oldLevelName = $('#levelName').val();
	initValidate();
});
