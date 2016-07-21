var i = 0;
var bool = true;
var flag = 1;
	//存储选中集合的val
	var arr = new Array();
	//验证名称的结构标志
	var validateFlag = true;
	var basePath ;
	//ajax请求
	function get(selValue,selId,tp){
	   	var value=selValue ;
       	$.ajax({
           type: "POST",
           url: "getChildCat.sc",
           data: {"value":value},
           dataType:"json", 
           success: function(data){
              $("#"+selId).empty();//清空下来框
              if(tp == "s"){
            	  $("#"+selId).append("<option value='0'>二级分类&nbsp;</option>");
              }
              if(tp == "t"){
            	  $("#"+selId).append("<option value='0'>三级级分类&nbsp;</option>");
              }
              for(var i=0; i<data.length ; i ++){
               	var option = "<option value='"+data[i].structName+"'>"+data[i].catName+"</option>";
               	$("#"+selId).append(option);
              }
           }
         });
      }
	  $(document).ready(function(){
		  $("#rootCattegory").change(function(){
		  	//选中项的value值
		  	var selValue = $(this).children('option:selected').val() ;
		  	//选中项的text值
		  	var selText = $(this).children('option:selected').text();
		  	if(selValue !="0"){
        		get(selValue,"secondCategory","s");
        	}else{
        		$("#secondCategory").empty();//清空下来框
                $("#secondCategory").append("<option value='0'>二级分类&nbsp;</option>");
        	}
        	$("#threeCategory").empty();//清空下来框
            $("#threeCategory").append("<option value='0'>三级分类&nbsp;</option>");
		  })
		   $("#secondCategory").change(function(){
		  	//选中项的value值
		  	var selValue = $(this).children('option:selected').val() ;
		  	//选中项的text值
		  	var selText = $(this).children('option:selected').text();
		  	if(selValue !="0"){
        		get(selValue,"threeCategory","t");
        	}else{
        		$("#threeCategory").empty();//清空下来框
                $("#threeCategory").append("<option value='0'>三级分类&nbsp;</option>");
        	}
		  })
	  });
	  //添加新分类
	  function addNewCat(){
		 flag = 0;
		readAllStruct();
	  	basePath = $("#basePath").val();
	  	//一级分类的选中的值和文本
	  	var rootVal = $('#rootCattegory').children('option:selected').val();
	  	var rootText = $('#rootCattegory').children('option:selected').text();
	  	//二级分类选中的值和文本
	  	var secondVal = $('#secondCategory').children('option:selected').val();
	  	var secondText = $('#secondCategory').children('option:selected').text();
	  	//三级分类选中的值和文本
	  	var threeVal = $('#threeCategory').children('option:selected').val();
	  	var threeText = $('#threeCategory').children('option:selected').text();
	  	if(rootVal == "0"){
	  		addClass("cattip","onerror").html("请选择分类");
	  		return ;
	  	}
	  	if(threeVal == "0" ){
	  		addClass("cattip","onerror").html("必须选择三级分类");
	  		return ;
	  	}
	  	i = $("#showCat li").length;
	  	var rowid = "row" + i;
	  	var li = "<span><li id='"+rowid+"'><span>" ;
	  	//var li = "<li id='"+rowid+"'><span>" ;
	  	var removeF = "removeLiT(this)";
	  	//var removeF = "removeLi('"+rowid+"')";
	  	if(rootVal != "0"){
	  		li += rootText +"</span>" ;
	  		if(secondVal != "0"){
	  			li += "  >  "+"<span>"+secondText+"</span>" ;
	  			if(threeVal != "0"){
	  				if(isExist(arr,threeVal)){
	  					li += "  >  "+"<span>"+threeText+"</span>" ;
	  					li += "<a onClick="+removeF+"><img src='"+basePath+"/images/delete.gif'/></a></li>";
	  					li +="</span>";
	  					arr.push(threeVal);
	  					$("#showCat").append(li);
	  					addClass("cattip","oncorrect").html("");
	  				}else{
	  					addClass("cattip","onerror").html("该分类已经存在");
	  				}
	  			}else{
	  				if(isExist(arr,secondVal)){
	  					arr.push(secondVal);
	  					li += "<a onClick="+removeF+"><img src='" + basePath+ "/images/delete.gif'/></a></li>";
	  					li +="</span>";
	  					$("#showCat").append(li);
	  					addClass("cattip","oncorrect").html("");
	  				}else{
	  					addClass("cattip","onerror").html("该分类已经存在");
	  				}
	  			}
	  		}else{
	  			//true为不存在相同的分类，可以添加
	  			if(isExist(arr,rootVal)){
	  				arr.push(rootVal);
	  				li += "<a onClick="+removeF+"><img src='" + basePath+ "/images/delete.gif'/></a></li>";
	  				li +="</span>";
	  				$("#showCat").append(li);
	  				addClass("cattip","oncorrect").html("");
	  			}else{
	  				addClass("cattip","onerror").html("该分类已经存在");
  				}
	  		}
	  	}
	  	i++;
	  	resetH();
	  }
	  
  /**
   * 读取所有的结构字符串放入Array
   * @return
   */
  function readAllStruct(){
	   if(bool){
		   $("input[name='structName']").each(function(){
			   arr.push($(this).val());
		   });
		   bool = false;
	   }
  }
  
	//删除分类行
	function  removeLiT(obj){
		flag =0;
		readAllStruct();
		var rowId = $(obj).parent().attr("id");
		
		//rowId后缀，从0开始，并转为数字
		var rowIndex = rowId.substring(3,rowId.length) * 1;
		//行号和数组的索引号对应
		//alert("移除前"+arr);
		//alert(rowIndex);
		//alert("arr.length="+arr.length +";arr="+arr);
		var structName = arr[rowIndex];
		$.ajax({
	           type: "POST",
	           url: "isHasProductLine.sc",
	           data: {"struncName":structName},
	           dataType:"text", 
	           success: function(data){
	        	   if(data == "1"){
	        		   addClass("cattip","onerror").html("该分类与产品线关联，无法删除");
	        	   }else{
	        		   $("#"+rowId).remove();
	        		   renameLi();
	        		   arr.splice(rowIndex,1);
	        		   addClass("cattip","oncorrect").html("");
	        	   }
	           }
	     });
		//alert("移除后"+arr);
	}
	
	//重新索引
	function renameLi(){
		$("#showCat li").each(function(i){
			this.id = "row" + i ;
		});
	}

 	//提交按钮所在的表单
	function postForm(formId, url){
		$("#"+formId).attr("action",url);
		//添加分类的hidden到form
		addHidden(formId);
		$("#"+formId).submit();
	}
	
	//在表单中添加hidden
	function addHidden(formId){
		if(flag ==0){
			removeInput("structName");
		}
		//遍历arr数组
		var i = 0;
		for(var v in arr){
			var tName = "structName";
			var hdn = "<input type='hidden' name='"+tName+"' value='"+arr[i]+"'/>";
			i++;
			$("#"+formId).append(hdn);
		}
	}
	
	var config = {
			form : "brandUpdateFrom",
			submit : submitUpdateForm,
			fields : [ {
				name : 'speelingName',
				allownull : true,
				regExp : "notempty",
				defaultMsg : null,
				focusMsg : '',
				errorMsg : '拼音名称必须为英文字符',
				rightMsg : '',
				msgTip : 'speelingNameTip'
			}, {
				name : 'keywords',
				allownull : true,
				regExp : "notempty",
				defaultMsg : null,
				focusMsg : '',
				errorMsg : '关键字不能为空',
				rightMsg : '',
				msgTip : 'keywordsTip'
			},{
				name : 'sortNo',
				allownull : true,
				regExp : "intege1",
				defaultMsg:'请输入排序数字',
				focusMsg : '请输入排序数字',
				errorMsg : '排序必须为数字',
				rightMsg : '输入正确',
				msgTip : 'sortNoTip'
			},{
				name : 'url',
				allownull : true,
				regExp :  /^((http|https|ftp):(\/\/|\\\\)((\w)+[.]){1,}(net|com|cn|org|cc|tv|[0-9]{1,3})(((\/[\~]*|\\[\~]*)(\w)+)|[.](\w)+)*(((([?](\w)+){1}[=]*))*((\w)+){1}([\&](\w)+[\=](\w)+)*)*)$/,
				defaultMsg : null,
				focusMsg : '',
				errorMsg : '网址格式错误',
				rightMsg : '网址输入正确',
				msgTip : 'urlTip'
			}]
	};

Tool.onReady( function() {
	var f = new Fw(config);
	f.register();
});

/**
 * 提交表单
 */
function submitUpdateForm(result) {
	//removeHidden();
	if(result){
		addHidden("hidDiv");
		var length = $("input[name='structName']").length;
		var value = $("#brandNameId").val();
		if(""==value.trim()){
			addClass("brandNameTip","onerror").html("中文名称不能为空");
			validateFlag = false;
			return false;
		}
		
		var valueEn = $("#englishNameId").val();
		if(""==valueEn.trim()){
			addClass("englishNameTip","onerror").html("英文名称不能为空");
			validateFlag = false;
			return false;
		}
		if(validateFlag){
			if(length>0){
				return true;
			}else{
				addClass("cattip","onerror").html("至少选择一个分类");
				//alert("至少选择一个分类 ");
				return false;
			}
		}
	}
	return false;
}

/**
 * 验证品牌名称
 * @param obj
 * @return
 */
function validateBrandName(spanId){
		var id = $("#brandId").val();
		var value = $("#brandNameId").val();
//		var regex = /^[\u4E00-\u9FA5\uF900-\uFA2D]{2,30}$/;
//    	if (!regex.exec(value)) {
//			addClass(spanId,"onerror").html("中文名称只能由2-30个字符组成且必须为中文");
//			validateFlag = false;
//			return false;
//    	}
//		if(""==value){
//			return;
//		}
		if(""==value.trim()){
			addClass(spanId,"onerror").html("中文名称不能为空");
			validateFlag = false;
			return false;
		}
		$.ajax({
           type: "POST",
           url: "validateEditBrandName.sc",
           data: {
           	"value":value,
           	"id":id
           	},
           dataType:"text", 
           success: function(data){
             if("1"==data){
				addClass(spanId,"onerror").html("该名称已经存在");
				validateFlag = false;
			}else{
				addClass(spanId,"oncorrect").html("该中文名称可以使用");
				validateFlag = true;
			}
           }
        });
}

 /**
  * 验证英文名称不能为空
  * @param spanId
  * @return
  */
 function validateBrandEnName(spanId){
	 var value = $("#englishNameId").val();
	 if(""==value.trim()){
		addClass(spanId,"onerror").html("英文名称不能为空");
		validateFlag = false;
		return false;
	 }else{
		 addClass(spanId,"oncorrect").html("");
		 validateFlag = true;
	 }
 }
 
/**
 * 删除名称为inputName的所有input
 * @param inputName
 * @return
 */
function removeInput(inputName){
	$("input[name='"+inputName+"']").remove();
}

/**
* 清空div
* @return
*/
function removeHidden(){
	$("#hidDiv").html("");
}

