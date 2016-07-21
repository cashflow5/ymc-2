
var path ;
var i = 0;
var subi = 0;
 	// 存储选中集合的val
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
           if("threeCategory" == selId){
         	  $("#"+selId).append("<option value='0'>请选择三级分类</option>");
           }else{
         	  $("#"+selId).append("<option value='0'>请选择二级分类</option>");
           }
           for(var i=0; i<data.length ; i ++){
            	var option = "<option value='"+data[i].structName+"'>"+data[i].catName+"</option>";
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