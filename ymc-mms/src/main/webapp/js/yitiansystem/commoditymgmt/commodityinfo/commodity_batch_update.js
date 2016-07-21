


var path ;
var i = 0;
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
           var isThree = false;
           if("threeCategory" == selId){
        	   isThree = true;
         	  $("#"+selId).append("<option value='0'>请选择三级分类</option>");
           }else{
         	  $("#"+selId).append("<option value='0'>请选择二级分类</option>");
           }
           for(var i=0; i<data.length ; i ++){
        	   var option = '';
        	   if(isThree){
        		   option = "<option value='"+data[i].structName+"'>"+data[i].catName+"</option>";
        	   }else{
        		   option = "<option value='"+data[i].structName+"'>"+data[i].catName+"</option>";
        	   }
            	$("#"+selId).append(option);
           }
        }
    });
}

//根据分类id获取品牌
function getBrandByCatId(){
	var catId = $('#threeCategory').val().split('_')[1];
	 $.ajax({
        type: "POST",
        url: path+"/yitiansystem/commoditymgmt/productlinemgmt/yt_prodLine/toRprpdLine_addCat.sc",
        data: {"value":catId},
        dataType:"json", 
        success: function(data){
           $('#brand').empty();// 清空下来框
           $('#brand').append("<option value='0'>请选择品牌</option>");
           for(var i=0; i<data.length ; i ++){
            	var option = "<option value='"+data[i].brandNo+"'>"+data[i].brandName+"</option>";
            	$('#brand').append(option);
           }
        }
    });
}

  $(document).ready(function(){
	  path = $("#basepath").val();
  /*	 $('#commodityBatchUpdateForm input[name$=Date]').each(function(){
  		 $(this).dateDisplay(path);
  	 })*/
  	 
  	
	  	 
	  	 
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
	  /**
	  $("#threeCategory").change(function(){
		    //选中项的value值
		  	var selValue = $(this).children('option:selected').val() ;
		  	// 选中项的text值
		  	var selText = $(this).children('option:selected').text();
		  	if(selValue !="0"){
		  		getBrandByCatId();
	     	}else{
	     		$("#brand").empty();// 清空下来框
	            $("#brand").append("<option value='0'>请选择品牌</option>");
	     	}
	  })*/
  });
  
  /**
 * 批量导出需要修改的数据，根据id
 */
function batchExport(chkName) {
	var action = $("#export").val();
	if(action ==""){
		alert("请选择导出数据");
		return false;
	}
	var checkVal ="";
	$("input[name='"+chkName+"']").each(function(){
		if(this.checked){
			checkVal += $(this).val() +",";
		}
	});
	var hiVal = "<input id = 'ids' type='hidden' name = 'ids' value='"+checkVal+"'>";
	var operation ="<input id = 'operation' type='hidden' name = 'operation' value='"+action+"'>";
	$("#ids").remove();
	$("#operation").remove();
	$("#commodityBatchUpdateForm").append(hiVal);
	$("#commodityBatchUpdateForm").append(operation);
	$("#commodityBatchUpdateForm").attr("action","exportTemplateExcel.sc");
	$("#commodityBatchUpdateForm").submit();
	$("#commodityBatchUpdateForm").attr("action","toCommodityBatchUpdate.sc");
	
//	window.location.href = "exportTemplateExcel.sc?value="+checkVal+"&action="+action;
}

/**
 * 导入事件
 * @return
 */
function importForward(){
	var action = $("#import").val();
	if(action ==""){
		alert("请选择导入数据");
		return false;
	}
	window.location.href = action ;
}

/**
* 全选和反选
* @param obj
* @param chkName
* @return
*/
function allChk(obj,chkName){
	var id = obj.id;
	if($("#"+id).attr("checked")) {
		 $("input[name='"+chkName+"']").attr("checked",'true');//全选
	}else{
		$("input[name='"+chkName+"']").removeAttr("checked");//取消全选
	}
}

