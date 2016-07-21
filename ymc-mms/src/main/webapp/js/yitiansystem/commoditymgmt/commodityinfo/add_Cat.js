
var result = false;
var boo = false;
var path;
//一级分类初始选中值
var rootVa;
//二级分类初始选中值 
var secondVa;
//ajax请求
function get(selValue){
	var value=selValue ;
    $.ajax({
    	type: "POST",
        url: "getChildCat.sc",
        data: {"value":value},
        dataType:"json", 
        success: function(data){
        	$("#secondCategory").empty();//清空下来框
        	$("#secondCategory").append("<option value=''>请选择二级分类</option>");
        	for(var i=0; i<data.length ; i ++){
        		if(data[i]){
	        		var option = "<option value='"+data[i].structName+"'>"+data[i].catName+"</option>";
	        		$("#secondCategory").append(option);
        		}
        	}
    	}
    });
}

/**
 * 加载事件
 */
$(document).ready(function(){
	addClass("catNameTip","onfocus").html("请输入分类名称");
	path = $("#base_path").val();
	
	//注册改变事件
	$('#rootCattegory').change(function(){
		var selValue = $(this).children('option:selected').val() ;
		selectChangeGetValue(this.value);
		if(selValue !=""){
			$("#structName").val(selValue);
			get(selValue);
		}else{
			$("#secondCategory").empty();//清空下来框
			$("#secondCategory").append("<option value=''>请选择二级分类</option>");
			$("#structName").val("");
		}
	});
	
	$('#secondCategory').change(function(){
		var value = $(this).children('option:selected').val() ;
		if(value ==""){
			var hisValue= $('#rootCattegory').children('option:selected').val();
			$("#structName").val(hisValue);
		}
		if(value !=""){
			$("#structName").val(value);
		}
	});
});
  
  /**
   * 添加分类验证
   */
var config={form:"addCatForms",
		submit:submitForms,
		fields:[
			{name:'keywords',allownull:false,regExp:"notempty",defaultMsg:'请输关键字',focusMsg:'请输输入关键字',errorMsg:'关键字不能为空',rightMsg:'输入格式正确',msgTip:'keywordsid'},
			{name:'sortNo',allownull:false,regExp:"num1",defaultMsg:'请输入数字',focusMsg:'请输入数字',errorMsg:'请输入数字',rightMsg:'输入格式正确',msgTip:'sortNoid'}
		]};

/**
 * 提交表单
 */
function submitForms(){
	checkHasCatName();
	if(submitBeforeValidateIsNumber()){
		if(boo){
			return true;
		}else{
			return false;
		}
		return true;
	}else{
		return false;
	}
}

/**
 * 提交表单前
 * 验证排序是否是数字
 * @return
 */
function submitBeforeValidateIsNumber(){
	var sortNo = $("#sortNo").val();
	var registIsNumber = /^[0-9]\d*$/.test(sortNo);
	if(registIsNumber){
		//addClass("sortNoid","oncorrect").html("请输入数字");
		return true;
	}else{
		addClass("sortNoid","onerror").html("请输入数字");
		return false;
	}
}


/**
* 验证分类名称
* @param obj
* @return
*/
function checkHasCatName(){
	var value = $("#catNameId").val();
	var regex = /^\S+$/;
	//alert(value);
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

	var strutName1=$("#rootCattegory>option:selected").val();
	var strutName2=$("#secondCategory>option:selected").val();
	
	var data__ = {"catName":value,"structName1":strutName1,"structName2":strutName2};
  	
	$.ajax({
  		type : "POST",
  		url : "checkHasCatName.sc",
  		async : false,
  		data :data__ ,
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
function selectChangeGetValue(selectedValue){
	$("#useGetStructName").val(selectedValue);
}