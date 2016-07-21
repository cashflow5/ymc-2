
//修改节点数据
function updateTagsNode(){
	
	if(!confirm("确认修改?"))return 
	
	var id = $("#id").val();
	if(id == null || id == ""){
		alert('资源不存在! 请先增加');
		return ;
	}
	
	var data={
		"tagName":$("#tagName").val(),
		"tagDesc":$("#tagDesc").val(),
		"tagUrl":$("#tagUrl").val(),
		"type":$("#type").val(),
		"no":$("#no").val(),
		"channelNo":$("#channelNo").val(),
		"categoryNo":$("#categoryNo").val(),
		"id":$("#id").val()
	};
	
	var url="u_updateTagsNode.sc";
	
	ajaxRequest(url,data,function(result){
		if(!result) return ;
		
		result = result.replace(/(^\s*)|(\s*$)/g,'');
		if(result.length == ""){
			alert("修改失败");
			return ;
		}
		
		
		var selectNode = window.parent.tagslfbarif.getSelectNode();
		var resultNode = eval("("+result+")");
		selectNode.text= resultNode.text;
		window.parent.tagslfbarif.updateNewNode(selectNode);
	    
		alert("修改成功");
	});
	
}




//增加节点
function addTagsNode(){
	
	var node = window.parent.tagslfbarif.getSelectNode();
	if(!node){
		alert('请选择一个节点！');
		return false;
	}
	
	var newNodeText = $('#tagName').val();
	if(newNodeText == null || newNodeText == ""){
		alert("标签名称不能为空！");
		clearInputValue();
		return false;
	}
	
	if(newNodeText == node.text){
		alert('请输入地区新标签名称 ！');
		clearInputValue();
		return false;
	}
	var templateNo = $('#templateNo').val();
	if(templateNo == ""){
		alert("请选择模板类型！");
		clearInputValue();
		return false;
	}
	else if(templateNo == "1" || templateNo == 1){
		$('#otherNo').val("index");
	}
	var rowNum = $('rowNum').val();
	if(rowNum == ""){
		alert("请输入图片所在行数！");
		clearInputValue();
		return false;
	}
	var site = $('site').val();
	if(site == ""){
		alert("请输入图片所在位置！");
		clearInputValue();
		return false;
	}
	var url="c_addTagsNode.sc";
	var data={
		"tagName":$("#tagName").val(),
		"tagDesc":$("#tagDesc").val(),
		"tagUrl":$("#tagUrl").val(),
		"type":$("#type").val(),
		"no":$("#no").val(),
		"brandNo":$("#brandNo").val(),
		"categoryNo":$("#categoryNo").val(),
		"channelNo":$("#channelNo").val(),
		"otherNo":$("#otherNo").val(),
		"rowNum":$("#rowNum").val(),
		"site":$("#site").val(),
		"parentid":node.id
	};

	
	ajaxRequest(url,data,function(result){
		if(!result) return ;
		
		result = result.replace(/(^\s*)|(\s*$)/g,'');
		if(result.length == ""){
			alert("增加失败");
			return ;
			
		}

		var node = eval("("+result+")");
		if(node.id){
			var nodeData = [{
				id:node.id,
				text:node.text
			}];
			
			window.parent.tagslfbarif.addNewNode(nodeData);
			clearInputValue();
		}
	});
}

/**
 * 节点点击时 加载节点基础数据
 * @param nodeid
 */
function loadNodeDetailData(node){
	$("#id").attr("value",node.id);
	$("#tagName").attr("value",node.text);
	$("#tagDesc").attr("value",node.remark);
	$("#no").attr("value",node.attributes.no);
	$("#tagUrl").attr("value",node.url);
	$("#type").attr("value",node.attributes.type);
}



function removeTagsNode(){
	
	var node = window.parent.tagslfbarif.getSelectNode();
	if(!node){
		alert('请选择要删除的节点');
		return ;
	}
	
	if(!confirm("确认删除?"))return 
	
	var url = "d_removeTagsNode.sc";
	var data={
		"tagsid":node.id
	};
	
	ajaxRequest(url,data,function(result){
		if(!result) return ;
		result = result.replace(/(^\s*)|(\s*$)/g,'');
		if(result == "success"){
			clearInputValue();
			window.parent.tagslfbarif.removeMyselfNode();
			return ;
		}
	});
}


/**
 * 清除页面所有文本框数据
 */
function clearInputValue(){
	$("#id").attr("value","");
	$("#tagName").attr("value","");
	$("#tagUrl").attr("value","");
	$("#tagDesc").attr("value","");
	$("#type").attr("value","");
	$("#ids").attr("value","");
	$("#no").attr("value","");
}


function selectCommodity(path){
	 showThickBox("选择发布商品.",path+"/yitiansystem/commoditymgmt/commodityinfo/productlist/toProductSelectList.sc?TB_iframe=true&height=600&width=1200",false);
}

function selectAdvImages(path){
	 showThickBox("选择发布广告.",path+"/yitiansystem/cms/imageAdv/queryAdvImages.sc?TB_iframe=true&height=600&width=950",false);
}
function selectBrand(path){
	 showThickBox("选择品牌.",path+"/yitiansystem/commoditymgmt/commodityinfo/brand/toBrandSelectList.sc?TB_iframe=true&height=600&width=950",false);
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
	var nodeid = $("#id").val();
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
 * 获取弹出框传回的商品ID值
 * @param ids
 */
function submitSelectCommodity(type){
	
	var checkResult = checkSelect();
	if(!checkResult){
		return ;
	}
	var url = "";
	if(type=="1")  //1 关联商品
		url = "u_updateTagsCommodity.sc";
	else if(type=="2")  //2关联广告
		url = "u_updateTagsAdvImages.sc";
	else if(type=="3") //3关联品牌
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




