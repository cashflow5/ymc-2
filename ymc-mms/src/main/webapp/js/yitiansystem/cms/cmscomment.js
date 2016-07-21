function selectComment(path){
	window.top.tagsId = $("#tagsId").val();
	 showThickBox("选择推荐商品评论",path+"/yitiansystem/cms/hotProductComment/queryMyIsUseComment.sc?TB_iframe=true&height=495&width=1200",false);
}

function selectGroupActivites(path)
{
	window.top.tagsId = $("#tagsId").val();
	 showThickBox("选择推荐商品团购",path+"/yitiansystem/cms/tagsGroupActivities/queryMyIsUseComment.sc?TB_iframe=true&height=495&width=950",false);
}

function batchGroupSelect(path)
{
	var items = $("input[name = 'chk_name[]']:checked");
	
	var ids = "";
	for(var i = 0;i<items.size();i++){
		ids = ids+items[i].value;
		if(i != items.size()-1){
			ids=ids+",";
		}
	}
   var url ="c_addProductToTags.sc";
   $.ajax({
       type: "POST",
       data: {"ids":ids,"tagsId" : window.top.tagsId},
       url: url,
       success: function(data){
		 	if(data=="success"){
		 		alert("添加商品团购成功!");
		 		
		 	}else{
		 		alert("添加失败!");
		 	}
       }
     });
   
}

function batchSelect(path)
{
		var items = $("input[name = 'chk_name[]']:checked");
		var ids = "";
		for(var i = 0;i<items.size();i++){
			ids = ids+items[i].value;
			if(i != items.size()-1){
				ids=ids+",";
			}
		}
	   var url ="c_addProductToTags.sc";
	   $.ajax({
	       type: "POST",
	       data: {"ids":ids,"tagsId" : window.top.tagsId},
	       url: url,
	       success: function(data){
			 	if(data=="success"){
			 		alert("添加商品评论成功!");
			 		location=path+"/yitiansystem/cms/hotProductComment/queryGroupByTagId.sc?id="+window.top.tagsId;
			 		window.top.TB_remove();
			 	}else{
			 		alert("添加失败!");
			 	}
	       }
	     });
	   
}

/**
* 启用
* @param tagsId
* @return
*/
function useRecommend(tagsId){
	 var linkObj = $("#link_"+tagsId+"> a:first-child");
	 var state = parseInt(linkObj.attr("state"));
	 if(state == 0){
		 linkObj.attr("state",1);
		 linkObj.text("停用");
		 state = 1;
	 }else{
		 linkObj.attr("state",0);
		 linkObj.text("启用");
		 state = 0;
	 }
	 var url = [];
	 url.push($("#basePath").val());
	 url.push("/yitiansystem/cms/hotProductComment/u_updateProductState.sc");
	 $.ajax({
      type: "POST",
      data: {"tagsId":tagsId,"state":state},
      url: url.join(""),
      success: function(data){
		 	if(data!="success"){
		 		alert("修改失败!");
		 	}
      }
    });
}

function useGroupRecommend(tagsId)
{
	 var linkObj = $("#link_"+tagsId+"> a:first-child");
	 var state = parseInt(linkObj.attr("state"));
	 if(state == 0){
		 linkObj.attr("state",1);
		 linkObj.text("停用");
		 state = 1;
	 }else{
		 linkObj.attr("state",0);
		 linkObj.text("启用");
		 state = 0;
	 }
	 var url = [];
	 url.push($("#basePath").val());
	 url.push("/yitiansystem/cms/tagsGroupActivities/u_updateProductState.sc");
	 $.ajax({
      type: "POST",
      data: {"tagsId":tagsId,"state":state},
      url: url.join(""),
      success: function(data){
		 	if(data!="success"){
		 		alert("修改失败!");
		 	}
      }
    });
}