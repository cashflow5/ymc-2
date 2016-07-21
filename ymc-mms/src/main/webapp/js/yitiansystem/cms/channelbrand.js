
function selectBrand(path){	
	 window.top.tagsNo = $("#channelId").val();	 
	 showThickBox("选择品牌.",path+"/yitiansystem/cms/channel/toBrandSelectList.sc?TB_iframe=true&height=600&width=950",false);
}
 
 /**
  * 确认选择品牌
  * @param inputName
  * @return
  */
 function batchSelect(inputName){	
 	var checkObj = $("input[name='"+inputName+"']"); 	 
 	var checkIds = "";
 	
 	checkObj.each(function(index){
 		if(this.checked){
 			if(checkObj.length >= index+1){
 				checkIds += $(this).val() +","; 				
 			}
 		}
 	}); 	
 	if(checkIds==""||checkIds==","){
 		alert("请选择品牌!");
 	}
 	if(checkIds!=""){
 		addProdToTags(checkIds);
 	}
 } 
 
 /**
  * 将品牌加入标签
  * @param ids
  * @return
  */
 function addProdToTags(ids){	 
 	 $.ajax({
        type: "POST",
        data: {"ids":ids,"channelId":window.top.tagsNo}, 
        url: "addBrands.sc",        
        success: function(data){       	
 		 	if("success"==data) {
 		 		alert("添加品牌成功!"); 		 	
 		 	}else {
 		 		alert("添加品牌失败!");
 		 	}
        }
      });
 }
  
 function getBack(bathpath){
	window.parent.mbdif.location.reload();
	 window.top.TB_remove();
 }
 
 function checkValue(obj){	
	 if(obj.value.match(/\D/)!=null){
			alert("必须为数字！");
			obj.value="";
			obj.focus();
			return false;
		}
 }

//提交按钮所在的表单
	function submitForm(formId, url){		
		$("#"+formId).attr("action",url);		//添加分类的hidden到form		
		$("#"+formId).submit();
	}
