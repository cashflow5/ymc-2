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
		"type":"5",
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
