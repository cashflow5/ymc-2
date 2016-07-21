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


function ajaxUploadFile(){
	//锁屏
	 $.blockUI({ css: { 
         border: 'none', 
         padding: '15px', 
         backgroundColor: '#000', 
         '-webkit-border-radius': '10px', 
         '-moz-border-radius': '10px', 
         opacity: .5, 
         color: '#fff' 
     } });
	
//	$.blockUI({message:'Welcome To JavaEye'}); // 锁屏
    $.ajaxFileUpload({
		url:'${BasePath}/yitiansystem/commoditymgmt/commodityinfo/productlist/inportAllExcel.sc',
		secureuri:false,
		fileElementId:'excelFile',
		dataType: 'text',
		beforeSend:function(){//上传前需要处理的工作，如显示Loading...
    	
		},
		success: function (data, status){
			$.unblockUI();// 解屏 
			if(data =="<pre>1</pre>" ){
				alert("导入成功");
			}else{
				alert("导入失败");
			}
		},
		error: function (data , status ,e){
			$.unblockUI();// 解屏 
			alert("导入失败");
		}
	});	    
}
