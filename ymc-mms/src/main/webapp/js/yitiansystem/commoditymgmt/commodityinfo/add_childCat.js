//分类验证
var boo=true;
//排序验证
var sortBoo=false;
var path;
var b=true;

//验证添加分类----子类分类名称是否存在
function validateCateName(){
	var catName=$.trim($("#catNameId").val());
	if(catName==""){
		b=false;
		return;
	}
	var strutName1=$("#rootCattegory").val();
	var strutName2=$("#secondCategory>option:selected").val()==undefined?0:$("#secondCategory>option:selected").val();

	var str="catName:'"+catName+"'";
	str+=",structName1:'"+strutName1+"'";
	str+=",structName2:'"+strutName2+"'";
	str="{"+str+"}";
	$.ajax({url:
			path+"/yitiansystem/commoditymgmt/commodityinfo/cat/validateCatName.sc",
			data : {'value':str},
			type : "POST",
			dataType :'json',
			async : false,
			success : function(data){
				var va =parseInt(data);
				alert(va);
				if(va==0){
					b=true;
				}else if(va > 0){
					addClass("catNameTip","onerror").html("该名称已经存在");
					b=false;
				}else{
					b=true;
				}
			}
		});
}

/**
 * 验证分类名称是否存在
 * @param structName
 * @return
 */
/*function checkHasCatName() {
	var catName=$("#catNameId").val();
	if(""==catName){
		boo=false;
		return;
	}
	var strutName1=$("#rootCattegory>option:selected").val();
	var strutName2=$("#secondCategory>option:selected").val();
	$.ajax( {
		type : "POST",
		url : "checkHasCatName.sc",
		async : false,
		data : {
			"catName":catName,
			"structName1":strutName1,
			"structName2":strutName2
		},
		dataType:"json",
		success:function(data) {
			if(data == "1"){
				//alert("分类名称已经存在");
				//$("#catNameId").focus();
				boo = true ;
			}else{
				boo = false;
			}
		}
	});
}*/

 /**
  * 验证分类名称
  * @param obj
  * @return
  */
 function checkHasCatName(){
 		var value = $("#catNameId").val();
 		var regex = /^\S+$/;
 		if(""==value){
 			addClass("catNameTip","onerror").html("分类名称不能为空");
 			boo=false;
 			return false;
 		}
     	if (!regex.exec(value)) {
     		addClass("catNameTip","onerror").html("分类名称不能为空");
     		boo = false;
 			return false;
     	}
     	
     	var strutName1=$("#rootCattegory").val();
    	var strutName2="";//$("#secondCategory>option:selected").val();
    	$.ajax( {
    		type : "POST",
    		url : "checkHasCatName.sc",
    		async : false,
    		data : {
    			"catName":value,
    			"structName1":strutName1,
    			"structName2":strutName2
    		},
    		dataType:"json",
    		success:function(data) {
    			if(data == "1"){
    				addClass("catNameTip","onerror").html("该名称已经存在");
     				boo = false;
     				return false;
    			}else{
    				addClass("catNameTip","oncorrect").html("该名称可以使用");
     				boo = true;
     				return true;
    			}
    		}
    	});
 }
 
 function addClass(spanId,cs) {
	    var name = $("#"+spanId);
	    name.removeClass("oncorrect").removeClass("onshow").removeClass("onerror");
	    return name.addClass(cs);
}

//保存，提交表单
function addCat(){
	if(boo){
		addClass("catNameTip","onerror").html("分类名称已经存在");
//		$("#catNameId").focus();
		return;
	}
//	if(sortBoo){
//		alert("排序序号重复或不是数字");
//		$("#sortNo").focus();
//		return;
//	}
	var catName=$("#catNameId").val();
	var keyWords=$("#keywords").val();
	if(""==catName||""==keyWords){
		return false;
	}
	$("#addCatForm").submit();
}

//保存子类，提交表单
function addChildCat(){
	if(boo){
		alert("该分类已存在");
		
		return;
	}
	var catName=$("#catNameId").val();
	var keyWords=$("#keywords").val();
	if(""==catName||""==keyWords){
		return false;
	}
	$("#addChildForm").submit();
}
///**
// * 添加 添加之类 修改编辑 排序 数字验证
// */
//function validateNumber(obj){
//	 if(""==obj.value){
//	     sortBoo=true;
//	     return;
//	 }
//	//验证是否存在
//	$.post(path+"/yitiansystem/commoditymgmt/commodityinfo/cat/validateSortNo.sc",{val:obj.value},function(data){
//		if(""==data){
//			sortBoo=true;
//			return;
//		}
//		var da=eval("("+data+")");
//		if("1"==da){
//			alert("序号已存在，请重新输入");
//			obj.focus();
//			//不用验证排序...排序可以重复
//			sortBoo=false;
//		}else{
//			sortBoo=false;
//		}
//	});
//}
/**
 * 验证  排序只能输入数字
 * 应用于，添加，编辑 添加子类
 * */
$(document).ready(function(){
	path = $("#base_path").val();
	$("#sortNo").keydown(function(event){
		if(event.keyCode>=48&&event.keyCode<=57||event.keyCode==8||event.keyCode==9||event.keyCode==46||event.keyCode>=37&&event.keyCode<=40||event.keyCode>=96&&event.keyCode<=105){
			return true;
		}
		return false;
	});
	
	addClass("catNameTip","onshow").html("&nbsp;");
});
 
 
 
/**
 * 添加资料验证
 */
var config={form:"addChildForm",submit:submitForm,
	 	fields:[
			{name:'keywords',allownull:false,regExp:"notempty",defaultMsg:'请输关键字',focusMsg:'请输关键字',errorMsg:'关键字不能为空',rightMsg:'输入格式正确',msgTip:'keywordsid'},
			{name:'sortNo',allownull:false,regExp:"num1",defaultMsg:'请输入数字',focusMsg:'请输入数字',errorMsg:'请输入数字',rightMsg:'输入格式正确',msgTip:'sortNoid'}
		]};


/**
 * 提交表单
 */
function submitForm(){
	validateCateName();
	if(boo){
		return boo;
	}else{
		//alert("名称已经存在!");
		return false;
	}
}

