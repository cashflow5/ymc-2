
function selectBrand(path){	
	 window.top.tagsNo = $("#tagsNo").val();	 
	 showThickBox("选择品牌.",path+"/yitiansystem/cms/manage/brand/toBrandSelectList.sc?TB_iframe=true&height=600&width=950",false);
}

/**
 * 获取弹出框传回的商品ID值
 * @param ids
 */
function saveSelectCommoditys(ids,type){
	$("#ids").attr("value",ids);
	submitSelectCommodity(type);
}


function checkSelect(){
	var nodeid = $("#tagsId").val();
	var ids = $("#ids").val();
	
	if(nodeid == null || nodeid == ""){
		alert("请选择一个标签！");
		return false;
	}
	
	if(ids == null || ids == "" || ids.length==1){
		alert("请选择要分配数据！");
		return false;
	}
	
	var data={
		"id":nodeid,
		"ids":ids
	};
	
	return data;
}

/**
 * 获取弹出框传回的品牌ID值
 * @param ids
 */
function submitSelectBrand(type){
	
	var checkResult = checkSelect();
	if(!checkResult){
		return ;
	}
	var url = "";
	 if(type=="3") //3关联品牌
		url = "u_updateTagsBrand.sc";
	ajaxRequest(url,checkResult,function(result){
		if(!result) return ;
		result = result.replace(/(^\s*)|(\s*$)/g,'');
		if(result == "success"){
			alert("分配成功!");
			return ;
		}
	});
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
        data: {"ids":ids,"tagsId":$("#tagId").val()},
        url: "c_addBrandToTags.sc",        
        success: function(data){         	
 		 	if(data=="success"){
 		 		alert("添加品牌成功!");
 		 	}else{
 		 		alert("添加品牌失败!");
 		 	}
        }
      });
 }
  
 function getBack(bathpath){
	window.parent.mbdif.tagscontentmbdif.location.reload();
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
