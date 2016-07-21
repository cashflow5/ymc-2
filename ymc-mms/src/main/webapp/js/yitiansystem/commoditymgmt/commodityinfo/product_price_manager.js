/*
 *  货品价格管理的全选与反选 
 */
function isSelectAll(){
	$("input[type='checkbox'][name='checkboxList']").each(function(){
		$(this).attr("checked", $("#selectAll").attr("checked"));
	});
}

 /*
  *  编辑货品价格列表的某一行
  */
function productPriceManagerEdit(productPriceManagerId){
	$('#productPriceManagerForm').append("<input type='hidden' value='"+ productPriceManagerId +"' name='productPriceManagerId'>");
	postForm("productPriceManagerForm",path+"/yitiansystem/commoditymgmt/commodityinfo/productpricemanager/toproductPriceManagerEdit.sc");
}

var path;
$(function(){path = $('#basepath').val();})
/*
 *  删除货品价格列表的某一行
 */
function d_productPriceManager(id){
	$.ajax({
	    type: "POST",
	    url: "d_productPriceManager.sc",
	    data: {
			"Ids":id
		},
	    dataType:"json", 
	    success: function(data){
	    	//删除点击的那一行
	    	var length = id.split("-_-").length;
	    	if(length < 2){
	    		$("#" + id).remove();
	    	}else{
	    		for(var i = 0; i < id.split("-_-").length - 1; i++){
	    			$("#" + id.split("-_-")[i]).remove();
		    	}
	    	}
	    }
	});
}
 
 /*
  *  删除货品价格列表中所选择的行
  */
 function deleteSelect(){
	 var ids = ''; //记录所有选中的要删除的行号
	 var length = $("input[type='checkbox'][name='checkboxList']:checked").length;
	 if(length == 0){
		 alert("请选择您想要删除的行");
		 return;
	 }
	 if(confirm("确定要删除选中的行吗?")){
		 $("input[type='checkbox'][name='checkboxList']:checked").each(function(){
			ids += $(this).parent().parent().attr("id") + "-_-";
		 });
		 d_productPriceManager(ids);
	 }
 }


//提交按钮所在的表单
function postForm(formId, url){
	$("#"+formId).attr("action",url);
	// 添加分类的hidden到form
	$("#"+formId).submit();
}

function get(selValue,selId,f){
   	var value=selValue ;
   	$.ajax({
       type: "POST",
       url: "getChildCat.sc",
       data: {"value":value},
       dataType:"json", 
       success: function(data){
          $("#"+selId).empty();//清空下来框
          if(f=="s"){
        	 $("#"+selId).append("<option value=''>二级分类&nbsp;&nbsp;</option>");
          }
          if(f=="t"){
         	 $("#"+selId).append("<option value=''>三级分类&nbsp;&nbsp;</option>");
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
	  	if(selValue !=""){
    		get(selValue,"secondCategory","s");
    	}else{
    		$("#secondCategory").empty();//清空下来框
            $("#secondCategory").append("<option value=''>二级分类&nbsp;&nbsp;</option>");
    	}
    	$("#threeCategory").empty();//清空下来框
        $("#threeCategory").append("<option value=''>三级分类&nbsp;&nbsp;</option>");
	  });
	  $('#secondCategory').change(function() {
		// 选中项的value值
		var selValue = $(this).children('option:selected').val();
		if (selValue != "") {
			get(selValue, "threeCategory","t");
		} else {
			$("#threeCategory").empty();// 清空下来框
			$("#threeCategory").append("<option value=''>三级分类&nbsp;&nbsp;</option>");
		}
	});
  });