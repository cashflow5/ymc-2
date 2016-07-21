
var path ;

var i = 0;
var subi = 0;


//一级分类的选中的值和文本
	var rootVal ;
// 二级分类的选中的值和文本
	var secondVal = $('#secondCategory').children('option:selected').val();
	 // 三级分类的选中的值和文本
	var threeVal;




	// 存储选中集合的val
	var arr = new Array();
	// ajax请求
	function get(selValue,selId){
	   	var value=selValue ;
       	$.ajax({
           type: "POST",
           url: path+"/yitiansystem/commoditymgmt/commodityinfo/cat/getChildCat.sc",
           data: {"value":value},
           dataType:"json", 
           success: function(data){
              $("#"+selId).empty();// 清空下来框
              $("#"+selId).append("<option value='0'>请选择二级分类</option>");
              for(var i=0; i<data.length ; i ++){
               	var option = "<option value='"+data[i].structName+"'>"+data[i].name+"</option>";
               	$("#"+selId).append(option);
              }
           }
         });
      }
	  $(document).ready(function(){
		  path = $("#basepath").val();
		  
		  $("#rootCattegory").change(function(){
		  	//选中项的value值
		  	var selValue = $(this).children('option:selected').val() ;
		  	
		  	
		  	// 选中项的text值
		  	var selText = $(this).children('option:selected').text();
		  	if(selValue !="0"){
        		get(selValue,"secondCategory");
        	}else{
        		$("#secondCategory").empty();// 清空下来框
                $("#secondCategory").append("<option value='0'>请选择二级分类</option>");
        	}
        	$("#threeCategory").empty();// 清空下来框
            $("#threeCategory").append("<option value='0'>请选择三级分类</option>");
		  })
		   $("#secondCategory").change(function(){
		  	//选中项的value值
		  	var selValue = $(this).children('option:selected').val() ;
		  	// 选中项的text值
		  	var selText = $(this).children('option:selected').text();
		  	if(selValue !="0"){
        		get(selValue,"threeCategory");
        	}else{
        		$("#threeCategory").empty();// 清空下来框
                $("#threeCategory").append("<option value='0'>请选择三级分类</option>");
        	}
		  })
	  });
	 

 	//提交按钮所在的表单
	function postForm(formId, url){
		
		rootVal = $('#rootCattegory').children('option:selected').val();
		
		threeVal = $('#secondCategory').children('option:selected').val();
		
		if(rootVal!=0&&threeVal==0){
			alert("请选择到具体子分类中");
			return ;
		}
		
		$("#"+formId).attr("action",url);
		// 添加分类的hidden到form
		addHidden(formId);
		$("#"+formId).submit();
	}
	
	//在表单中添加hidden
	function addHidden(formId){
		//遍历arr数组
		var i = 0;
		for(var v in arr){
			var tName = "structName" ;
			var hdn = "<input type='hidden' name='"+tName+"' value='"+arr[i]+"'/>";
			i++;
			$("#"+formId).append(hdn);
		}
	}
	
	
	