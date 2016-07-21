//全选
$(document).ready(function(){
	$("#checkall").click(function(){ 
		if(this.checked){ 
			$("input[name='checkname']").each(function(){this.checked=true;}); 
		}else{ 
			$("input[name='checkname']").each(function(){this.checked=false;}); 
		} 
	});
});

function Submit()
{
	var strhtml="";
	var id="";//编号
	$("input:checked[id!=checkall]").parent().parent().each(function(){
		var aa=$("input:checked[id!=checkall]").parent();
		aa.remove();
		strhtml="<TR>"+$(this).html()+"</TR>";
		id=$(this).find("td:eq(0)").text();
		art.dialog.parent.Do(strhtml,id);
	});
	art.dialog.close();
}


/*选择商品时确定点击事件*/
function fn_getCommoditys(){

	var checknames = $("input[name='checkname']:checked").size();
	if(checknames<=0){
		alert("请选择商品!");
		return;
	}
	$("checkbox[name='checkname']:checked")
	
	var commoditys = [];
	var rows = $("#tbl_commoditys>tbody");
	
	
	$("input[type='checkbox'][name='checkname']:checked").each(function(i,obj){
		//alert(i+"====="+$(obj).parent().parent().find("td:eq(0)"));
		var commodityId_ = $(obj).parent().parent().find("td:eq(0)");// rows.find("tr:eq("+i+")>td:eq(0)");
		var commodityName_ = $(obj).parent().parent().find("td:eq(1)");// rows.find("tr:eq("+i+")>td:eq(1)");
		var publicPrice_ = $(obj).parent().parent().find("td:eq(4)");//rows.find("tr:eq("+i+")>td:eq(4)"); 
		var prodDesc_ = $(obj).parent().parent().find("td:eq(5)");//rows.find("tr:eq("+i+")>td:eq(5)");
		commodity = {'commodityId':commodityId_,
				'commodityName':commodityName_,
				'prodDesc':prodDesc_,
				'publicPrice':publicPrice_};
			
		commoditys.push(commodity);
	});
	
	/*
	for(var i = 0;i< rows.find("tr").size();i++){
		var commodityId_ = rows.find("tr:eq("+i+")>td:eq(0)");
		var commodityName_ = rows.find("tr:eq("+i+")>td:eq(1)");
		var publicPrice_ = rows.find("tr:eq("+i+")>td:eq(4)"); 
		var prodDesc_ = rows.find("tr:eq("+i+")>td:eq(5)");
		
		commodity = {'commodityId':commodityId_,
			'commodityName':commodityName_,
			'prodDesc':prodDesc_,
			'publicPrice':publicPrice_};
		
		commoditys.push(commodity);
	}
	*/
	art.dialog.parent.commodityList=commoditys;
	art.dialog.parent.addCommodityToTable();
	
	art.dialog.close();
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
        	   if(data[i]){
        		   var option = "<option value='"+data[i].structName+"'>"+data[i].catName+"</option>";
        		   $("#"+selId).append(option);
        	   }
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
	});
	
	
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
	});
});


//提交按钮所在的表单
function postForm(formId, url){
	$("#"+formId).attr("action",url);
	// 添加分类的hidden到form
	$("#"+formId).submit();
}