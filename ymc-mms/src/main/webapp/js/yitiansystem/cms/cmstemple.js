
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
		alert('请输入新标签名称 ！');
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
		"brandNo":$("#brandNo").val(),
		"categoryNo":$("#categoryNo").val(),
		"channelNo":$("#channelNo").val(),
		"otherNo":$("#otherNo").val(),
		"rowNum":$("#rowNum").val(),
		"site":$("#site").val(),
		"no":$("#no").val(),
		"parentid":node.id
	};

	ajaxRequest(url,data,function(result){
		if(!result) return ;
		
		result = result.replace(/(^\s*)|(\s*$)/g,'');
		if(result.length == ""){
			alert("失败");
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
		
		alert("成功");
	});
}

//修改节点数据
function updateTagsNode(){
	
	if(!confirm("确认修改?"))return 
	
	var id = $("#id").val();
	if(id == null || id == ""){
		alert('资源不存在! 请先增加');
		return ;
	}
	
	var tagName = $("#tagName").val();
	if(tagName == null || tagName == ""){
		alert('名称不能为空');
		return ;
	}
	
	var data={
		"tagName":$("#tagName").val(),
		"tagDesc":$("#tagDesc").val(),
		"type":$("#type").val(),
		"no":$("#no").val(),
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



/**
 * 节点点击时 加载节点基础数据
 * @param nodeid
 */
function loadNodeDetailData(node){
		
	$("#id").attr("value",node.id);
	$("#tagName").attr("value",node.text);
	$("#tagDesc").attr("value",node.remark);
	$("#no").attr("value",node.attributes.no);
		
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
			alert("删除成功");
			return ;
		}
		
		alert("删除失败");
	});
}


/**
 * 清除页面所有文本框数据
 */
function clearInputValue(){
	$("#id").attr("value","");
	$("#tagName").attr("value","");
	$("#tagDesc").attr("value","");
	$("#no").attr("value","");
}





