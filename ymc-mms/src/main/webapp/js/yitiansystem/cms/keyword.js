//增加节点
function addTagsNode(){
	var node = window.parent.tagslfbarif.getSelectNode();
	if(!node){
		alert('请选择一个节点！');
		return false;
	}
	var newNodeText = $('#tagName').val();
	if(newNodeText == null || newNodeText == ""){
		alert("名称不能为空！");
		clearInputValue();
		return false;
	}
	if(newNodeText == node.text){
		alert('请输入地区新标签名称 ！');
		clearInputValue();
		return false;
	}
	var url="c_addTagsNode.sc";
	var data={
		"tagName":$("#tagName").val(),
		"tagDesc":$("#tagDesc").val(),
		"tagUrl":$("#tagUrl").val(),
		"type":"7",
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
