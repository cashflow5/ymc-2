function get(selValue, selId) {
	var value = selValue;
	$.ajax( {
		type : "POST",
		url : "getChildCat.sc",
		data : {
			"value" : value
		},
		dataType : "json",
		success : function(data) {
			$("#" + selId).empty();// 清空下来框
		$("#" + selId).append("<option value='0'>选择分类</option>");
		for ( var i = 0; i < data.length; i++) {
			var option = "<option value='" + data[i].structName + ";"
					+ data[i].id + "'>" + data[i].catName + "</option>";
			$("#" + selId).append(option);
		}
	}
	});
}
$(document).ready( function() {
	$('#rootCattegory').change( function() {
		//选中项的value值
			var selValue = $(this).children('option:selected').val();
			var value = selValue.split(";");
			// 选中项的text值
			var selText = $(this).children('option:selected').text();
			if (selValue != "0") {
				get(value[0], "secondCategory");
			} else {
				$("#secondCategory").empty();// 清空下来框
				$("#secondCategory").append("<option value='0'>二级分类</option>");
			}
			$("#threeCategory").empty();// 清空下来框
			$("#threeCategory").append("<option value='0'>三级分类</option>");
		})
	$('#secondCategory').change( function() {
		//选中项的value值
			var selValue = $(this).children('option:selected').val();
			var value = selValue.split(";");
			// 选中项的text值
			var selText = $(this).children('option:selected').text();
			if (selValue != "0") {
				get(value[0], "threeCategory");
			} else {
				$("#threeCategory").empty();// 清空下来框
				$("#threeCategory").append("<option value='0'>三级分类</option>");
			}
		})
});

/**
 * 导出模板js
 */
function ajExport() {
	var arr = new Array();
	//一级节点选中的值
	var rootValue = $("#rootCattegory").children('option:selected').val();
	//二级节点选中的值
	var secondValue = $("#secondCategory").children('option:selected').val();
	//三级节点选中的值
	var threeValue = $("#threeCategory").children('option:selected').val();
	if(threeValue != "0"){
		arr = threeValue.split(";");
	}else{
		if(secondValue != "0"){
			arr = secondValue.split(";");
		}else{
			if(rootValue != "0"){
				arr = rootValue.split(";");
			}
		}
	}
	
	
	if(arr.length > 0){
		window.location.href = "exportTemplateExcel.sc?value="+arr[1];
	}
	
}

/**
* 导出修改资料模板js
*/
function ajaxUpdateExport() {
	var arr = new Array();
	//一级节点选中的值
	var rootValue = $("#rootCattegory").children('option:selected').val();
	//二级节点选中的值
	var secondValue = $("#secondCategory").children('option:selected').val();
	//三级节点选中的值
	var threeValue = $("#threeCategory").children('option:selected').val();
	if(threeValue != "0"){
		arr = threeValue.split(";");
	}else{
		if(secondValue != "0"){
			arr = secondValue.split(";");
		}else{
			if(rootValue != "0"){
				arr = rootValue.split(";");
			}
		}
	}
	
	if(arr.length > 0){
		window.location.href = "exportTemplateUpdateExcel.sc?value="+arr[1];
	}
	
}

function ajaxUpdateExportFirst() {
	var arr = new Array();
	//一级节点选中的值
	var rootValue = $("#rootCattegory").children('option:selected').val();
	//二级节点选中的值
	var secondValue = $("#secondCategory").children('option:selected').val();
	//三级节点选中的值
	var threeValue = $("#threeCategory").children('option:selected').val();
	if(threeValue != "0"){
		arr = threeValue.split(";");
	}else{
		if(secondValue != "0"){
			arr = secondValue.split(";");
		}else{
			if(rootValue != "0"){
				arr = rootValue.split(";");
			}
		}
	}
	
	if(rootValue =="0"){
		alert("请选择分类");
		return;
	}
	
	var startDate = $("#hidden_createStartTime").val();
//	alert(startDate);
	var endDate = $("#hidden_createEndTime").val();
//	alert(endDate);
	if(arr.length > 0){
		
		window.location.href = "exportTemplateUpdateExcelFirst.sc?value="+arr[1]+"&startDate="+startDate+"&endDate="+endDate;
	}
	
}

function ajaxUpdateExportFirstAll() {
	var arr = new Array();
	//一级节点选中的值
	var rootValue = $("#rootCattegory").children('option:selected').val();
	//二级节点选中的值
	var secondValue = $("#secondCategory").children('option:selected').val();
	//三级节点选中的值
	var threeValue = $("#threeCategory").children('option:selected').val();
	if(threeValue != "0"){
		arr = threeValue.split(";");
	}else{
		if(secondValue != "0"){
			arr = secondValue.split(";");
		}else{
			if(rootValue != "0"){
				arr = rootValue.split(";");
			}
		}
	}
	
	if(arr.length > 0){
		
		window.location.href = "exportTemplateUpdateExcelFirstAll.sc?value="+arr[1];
	}
	
}

function check(){
	$("#batchUpdateFrom").attr("action","inportExcelCheck.sc");
	$("#batchUpdateFrom").submit();
	$("#batchUpdateFrom").attr("action","inportExcel.sc");
}

function updateImport(){
	$("#batchUpdateFrom").attr("action","importTemplateUpdateExcel.sc");
	$("#batchUpdateFrom").submit();
	$("#batchUpdateFrom").attr("action","inportExcel.sc");
}


