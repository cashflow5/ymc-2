function r_propGroup(){
	$('#propGroupForm').submit();
}

/*
 *  商品属性组的全选与反选 
 */
function isSelectAll(){
	$("input[type='checkbox'][name='checkboxList']").each(function(){
		$(this).attr("checked", $("#selectAll").attr("checked"));
	});
}

function u_propGroup(propGroupId){
	$('#propGroupForm').append("<input type='hidden' value='"+ propGroupId +"' name='id'>");
	postForm("propGroupForm",path+"/yitiansystem/commoditymgmt/commodityinfo/propgroup/toPropGroupEdit.sc");
}

/*
* 删除商品属性组
*/
function d_propGroup(id){
	 if(confirm("确定删除该属性组？")){
		 var isSpec = $('input[name="isSpec"]:checked').val();
		 $.ajax({
	        type: "POST",
	        dataType:"json", 
	        url: path+"/yitiansystem/commoditymgmt/commodityinfo/propgroup/d_propGroup.sc",
	        data: {
			 	"id":id,
		 		"isSpec":isSpec
			},
	        success: function(data){
	        	if(data[0].length == 0){
	        		postForm("propGroupForm",path+"/yitiansystem/commoditymgmt/commodityinfo/propgroup/toPropGroup.sc");
	        	}else{
	        		alert("该属性组关联属性项，不能直接删除！");
	        	}
	        }
		 });
	 }
}
 
 /*
  *  删除商品属性组管理列表中所选择的行
  */
 function deleteSelect(){
	 var ids = ''; //记录所有选中的要删除的行号
	 var length = $("input[type='checkbox'][name='checkboxList']:checked").length;
	 if(length == 0){
		 alert("请选择您想要删除的行");
		 return;
	 }
	 $("input[type='checkbox'][name='checkboxList']:checked").each(function(){
		ids += $(this).parent().parent().attr("id") + "-_-";
	 });
	 d_propGroup(ids);
 }

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

/*
 * 跟据上次已经选中的值获取该下拉列表的所有值
 */
function getSelectedValue(value,selectedValue,selId,defaultStr){
    $.ajax({
        type: "POST",
        url: path+"/yitiansystem/commoditymgmt/commodityinfo/cat/getChildCat.sc",
        data: {"value":value},
        dataType:"json", 
        success: function(data){
           $("#"+selId).empty();// 清空下来框
           $("#"+selId).append("<option value='0'>"+ defaultStr +"</option>");
           for(var i=0; i<data.length ; i ++){
        	   var option = "";
        	   if(data[i].structName == selectedValue){
        		   option = "<option selected='selected' value='"+data[i].structName+"'>"+data[i].catName+"</option>";
        	   }else{
        		   option = "<option value='"+data[i].structName+"'>"+data[i].catName+"</option>";
        	   }
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
	
	$("#"+formId).attr("action",url);
	// 添加分类的hidden到form
	$("#"+formId).submit();
}