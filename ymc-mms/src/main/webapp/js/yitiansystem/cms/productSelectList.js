/**
 * 确认选择商品
 * @param inputName
 * @return
 */
function batchSelect(inputName){
	var checkObj = $("input[name='"+inputName+"']");
	var checkIds = "";
	checkObj.each(function(index){
		if(this.checked){
			if(checkObj.length != index+1){
				checkIds += $(this).val() +",";
			}else{
				checkIds += $(this).val();
			}
		}
	});
	if(checkIds!=""){
		addProdToTags(checkIds);
	}
}
//print Object
function inspect(obj) {
	var s = obj + "\n";
	for (var a in obj) {
		if (typeof obj[a] != "function") {
			s += a + "=" + obj[a] + ",\n";
		}
	}
	alert("obj=" + s);
}

/**
 * 将商品加入标签
 * @param ids
 * @return
 */
function addProdToTags(ids){
	 var url = [];
	 url.push($("#basePath").val());
	 url.push("/yitiansystem/cms/product/c_addProductToTags.sc");
	 $.ajax({
       type: "POST",
       data: {"ids":ids,"tagsId" : window.top.tagsId},
       url: url.join(""),
       success: function(data){
		 	if(data=="success"){
		 		alert("添加商品成功!");
		 		var tagsContentFrame = window.top.frames['mbdif'].document.getElementById("tagscontentmbdif");
				tagsContentFrame.src = $("#basePath").val() + "/yitiansystem/cms/product/queryProductByTagId.sc?id="+window.top.tagsId;
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
	 url.push("/yitiansystem/cms/product/u_updateProductState.sc");
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

/**
 * 删除商品引用
 * @param tagsId
 * @return
 */
function delRecommend(tagsId){
	 if(!confirm("确定要删除？")){
		 return;
	 }
	 var url = [];
	 url.push($("#basePath").val());
	 url.push("/yitiansystem/cms/product/d_removeProductFormTags.sc");
	 $.ajax({
       type: "POST",
       data: {"tagsId":tagsId},
       url: url.join(""),
       success: function(data){
		 	if(data=="success"){
		 		alert("删除推荐商品成功!");
		 		$("#link_"+tagsId).parent().remove();
		 	}else{
		 		alert("删除推荐商品失败!");
		 	}
       }
     });
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