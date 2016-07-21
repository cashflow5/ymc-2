
/*定义ajax验证名称重复的验证结果变量*/
var result = false;
/*定义全局变量path*/
var path;
var catName_ ;
//structName1初始值
var initStrName1 ;
//structName2初始值
var initStrName2 ;

var rStart = 0;
var sStart = 0;

var boo1 = true;
var bb = true;
var i = 0;

/*添加分类验证*/
var config={form:"updateCatform",submit:submitForm_Update,
	 	fields:[
			{name:'keywords',allownull:false,regExp:"notempty",defaultMsg:'请输关键字',focusMsg:'请输输入关键字',errorMsg:'关键子不能为空',rightMsg:'',msgTip:'keywordsid'},
			{name:'sortNo',allownull:false,regExp:"num1",defaultMsg:'请输入数字',focusMsg:'请输入数字',errorMsg:'请输入数字',rightMsg:'输入格式正确',msgTip:'sortNoid'}
		]};


/*加载验证框架*/
Tool.onReady(function(){
	var f = new Fw(config);
	f.register();
});
	
var selfParent;
var selfParentParent;
var selfParentList;
$(document).ready(function(){
	path = $("#base_path").val();
	
	selfParent = $("#rootCattegory").val(); 
	selfParentParent = $("#secondCategory").val();
	$("#useGetStructName").val(selfParent);
	selfParentList = $("#secondCategory>option");
});


/*提交表单..*/
function submitForm_Update(){
	var submitResult_=true;
//	alert("1="+initStrName1+";2="+initStrName2);
	var rootVal = $("#rootCattegory").children('option:selected').val();
	var secondVal = $("#secondCategory").children('option:selected').val();
	
	//alert("root="+rootVal+";second="+secondVal);
	catName_  = $.trim($("#catName_").val());
	var catName  = $.trim($("#catNameId").val());
	if( catName_!= catName ){
		checkEditHasCatName();
		if(boo1){//如果验证名称重复通过....则再验证是不是可以移动
			var validateIsMove =submitBeforeValidate();// 
			submitResult_ = validateIsMove;
		}else{
			submitResult_ = submitBeforeValidate();
		}
	}else if(rootVal == initStrName1 && secondVal==initStrName2){
		addClass("catNameTip","oncorrect").html("");
		submitResult_ = true;
	}else{
		checkEditHasCatName();
		//submitBeforeValidate();//
		if(boo1){
			submitResult_ = submitBeforeValidate();//
		}else{
			submitResult_ = submitBeforeValidate();
		}
	}
	return submitResult_;
}

function removeTip(tipId){
	$("#"+tipId).removeClass("oncorrect").removeClass("onshow").removeClass("onerror");
	$("#"+tipId).html("");
}

/* 提交分类修改表单 */
function submitUpdate(){
	
	submitBeforeValidate();
	var catName=$("#catName").val();
	var keywords=$("#keywords").val();
	var sortNo=$("#sortNo").val();
	if(""==catName||""==keywords||""==sortNo){
		return;
	}
	//去掉分割符 “,”
	sortNo=sortNo.replace(",","");
	var sortNo=$("#sortNo").val(sortNo);
	$("#updateCatform").submit();
}

function checkEditHasCatName(){
	var cat_name_ = $("#catName_").val();

	
	var value = $("#catNameId").val();
	var regex = /^\S+$/;
	if(""==value){
		addClass("catNameTip","onerror").html("分类名称不能为空");
		boo1=false;
		return false;
	}
 	if (!regex.exec(value)) {
 		addClass("catNameTip","onerror").html("分类名称不能为空");
 		boo1 = false;
			return false;
 	}
	if(cat_name_==value){
		bool = true;
		return true;
	}
 	var catIds = $("#catIds").val();
 	var strutName1=$("#rootCattegory>option:selected").val();
	var strutName2=$("#secondCategory>option:selected").val();
	$.ajax( {
		type : "POST",
		url : "checkEditHasCatName.sc",
		async : false,
		data : {
			"catIds":catIds,
			"catName":value,
			"structName1":strutName1,
			"structName2":strutName2
		},
		dataType:"json",
		success:function(data) {
			//alert(data);
			if(data == "1"){
				addClass("catNameTip","onerror").html("该名称已经存在");
 				boo1 = false;
 				return false;
			}else if(data == "2"){
				addClass("catNameTip","onerror").html("该分类下面有子分类");
 				boo1 = false;
 				return false;
			}else{
				addClass("catNameTip","oncorrect").html("该名称可以使用");
 				boo1 = true;
 				return true;
			}
		}
	});
}


/**
 * 提交前验证是不是可以移动
 * @return
 */
function submitBeforeValidate(){
	var selfParent_now = $("#rootCattegory").val(); 
	var selfParentParent_now = $("#secondCategory").val();
	//alert("selfParent_now："+selfParent_now+"===="+"selfParentParent_now:"+selfParentParent_now+"==\n==selfParentParent:"+selfParentParent+"====selfParent:"+selfParent);
	if(selfParent_now==selfParent&&selfParentParent_now==selfParentParent){
		return true;
	}
	var checkResult = true;
	var structName = $("#selfStructName").val();
	var catId = $("#catIds").val();
	var url="validateYesOrNoHasCommodity.sc?structName="+structName+"&catId="+catId;
	var data="";
	$.ajax({
		url:url,
		data:data,
		async:false,
		dataType:'text',
		success:function(d){
			//alert(d);
			if(d > 0){
				//alert("不能移动....");
				addClass("catNameTip","onerror").html("该分类已被引用");
				//$("#rootCattegory").val(selfParent);
				//$("#secondCategory").append(selfParentList);
				//$("#secondCategory").val(selfParentParent);
				
				checkResult = false;
			}else{
				checkResult = validateCateNameExist();
			}
		}
	});
	return checkResult;
}
/**
 * 下拉框改变的时候 .给一个隐藏赋值
 * @param selectedValue
 * @return
 */
function selectChangeGetValue(selectedValue){
	$("#useGetStructName").val(selectedValue);
}




/**
 * 验证添加分类----子类分类名称是否存在
 */
function validateCateNameExist(){
	var catName=$.trim($("#catNameId").val());
	if(catName==""){
		bb=false;
		return bb;
	}
	var strutName1=$("#rootCattegory").val();
	var strutName2=$("#secondCategory>option:selected").val()==undefined?0:$("#secondCategory>option:selected").val();

	var str="catName:'"+catName+"'";
	str+=",structName1:'"+strutName1+"'";
	str+=",structName2:'"+strutName2+"'";
	str="{"+str+"}";
	var url = path+"/yitiansystem/commoditymgmt/commodityinfo/cat/validateCatName.sc";
	
	$.ajax({url:url,
			data : {'value':str},
			type : "POST",
			dataType :'json',
			async : false,
			success : function(data){
				var va =parseInt(data);
				//alert(va);
				if(va==0){
					bb=true;
				}else if(va > 0){
					addClass("catNameTip","onerror").html("该名称已经存在");
					bb=false;
				}else{
					bb=true;
				}
			}
		});
	return bb;
}
 
 
 
/*异步 请求验证名称是否已经存在*/
//function validateCateNames(){
//	
//	var catName=$.trim($("#catNameId").val());
//	var catName_  = $.trim($("#catName_").val());
//	
//	if(catName==""){//如果是空
//		result=false;
//		return;
//	}
//	if(catName==catName_){//如果没有改变名称,则不用进行验证
//		result = true;
//		return;
//	}
//	
//	
//	var strutName1=$("#rootCattegory>option:selected").val();
//	var strutName2=$("#secondCategory>option:selected").val()==undefined?0:$("#secondCategory>option:selected").val();
//
//	var str="catName:'"+catName+"'";
//	str+=",structName1:'"+strutName1+"'";
//	str+=",structName2:'"+strutName2+"'";
//	str="{"+str+"}";
//	
//	//alert(str);
//	
//	$.ajax({url:
//		path+"/yitiansystem/commoditymgmt/commodityinfo/cat/validateCatName.sc",
//		data : {'value':str},
//		type : "POST",
//		dataType :'json',
//		async : false,
//		success : function(data){
//			var va = parseInt(eval("("+data+")"));
//			if(va==0){
//				result=true;
//			}else if(va > 0){
//				addClass("catNameTip","onerror").html("该名称已经存在");
//				$("#catNameId").select();
//				//不能保存
//				result=false;
//			}else{
//				result=false;
//			}
//		}
//	});
//}
