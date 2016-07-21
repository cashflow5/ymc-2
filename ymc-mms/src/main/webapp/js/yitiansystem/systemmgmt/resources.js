//修改节点数据
function updateMenuNode(){
	
	var newNodeText = $('#menuName').val();

	if(!confirm("确认修改?"))return 
	
	if($("#id").val() == null || $("#id").val() == ""){
		alert('资源不存在! 请先增加');
		return ;
	}
	
	var data={
		"menuName":$("#menuName").val(),
		"memuUrl": $("#memuUrl").val(),
		"type" : $("#type").val(),
		"remark": $("#remark").val(),
		"flag" : $("#flag").val(),
		"sort" : $("#sort").val(),
		"id":$("#id").val()
	};
	
	var url="u_updateResourcesNode.sc";
	
	ajaxRequest(url,data,function(result){
		if(!result) return ;
		
		result = result.replace(/(^\s*)|(\s*$)/g,'');
		if(result.length == ""){
			alert("修改失败");
			return ;
			
		}
		
		var selectNode = $('#resourceTree').tree('getSelected');
		var resultNode = eval("("+result+")");
		selectNode.text= resultNode.text;
	    $("#resourceTree").tree("update",selectNode);
		
		alert("修改成功");
	});
	
	
}

//增加节点
function addMenuNode(){
	
	var node = $('#resourceTree').tree('getSelected');
	if(!node){
		alert('请选择一个节点！');
		return false;
	}
	
	var newNodeText = $('#menuName').val();

	if(newNodeText == node.text){
		alert('子目录名称不能与父级相同！');
		return false;
	}
	
	
	var url="c_addResourcesNode.sc";
	var data={
		"menuName":$("#menuName").val(),
		"memuUrl": $("#memuUrl").val(),
		"type" : $("#type").val(),
		"remark": $("#remark").val(),
		"flag" : $("#flag").val(),
		"sort" : $("#sort").val(),
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
			
			append(nodeData);
			clearInputValue();
		}
	});
}

function loadNodeData(nodeid){
	var url = "queryResourceById.sc";
	var data={
		"id":nodeid
	};
	
	ajaxRequest(url,data,function(result){
		
		result = result.replace(/(^\s*)|(\s*$)/g,'');
		//如果获取数据为空  则清空数据
		if(result.length == ""){
			clearInputValue();
			return ;
		}

		var node = eval("("+result+")");
		
		$("#id").attr("value",node.id);
		$("#menuName").attr("value",node.text);
		$("#menuName").blur();
		$("#memuUrl").attr("value",node.url);
		$("#type").attr("value",node.type);
		$("#remark").attr("value",node.remark);
		$("#flag").attr("value",node.flag);
		$("#sort").attr("value",node.order);
		$("#sort").blur();
	});
	
}

function removeMenuNode(){
	var node = $('#resourceTree').tree('getSelected');
	if(!node){
		alert('请选择要删除的节点');
		return;
	}
	if(!confirm("确认删除?"))return 
	
	if(node.attributes != null && node.attributes.struc != null && node.attributes.struc == "root"){
		alert("根目录不能删除");
		return false;
	}
	
	var url = "d_removeResourcesNode.sc";
	var data={
		"resourceid":node.id
	};
	
	ajaxRequest(url,data,function(result){
		if(!result) return ;
		result = result.replace(/(^\s*)|(\s*$)/g,'');
		if(result == "success"){
			clearInputValue();
			remove();
			return ;
		}else{
			alert("删除失败,被使用的资源不允许删除");
		}
	});
}

function clearInputValue(){
	$("#id").attr("value","");
	$("#menuName").attr("value","");
	$("#menuName").blur();
	$("#memuUrl").attr("value","");
	$("#type").attr("value","0");
	$("#remark").attr("value","");
	$("#flag").attr("value","all");
	$("#sort").attr("value","");
	$("#sort").blur();
}

//发达ajax请求
function ajaxRequest(url,reqParam,callback){
	$.ajax({
		  type: 'POST',
		  url: url,
		  data: reqParam,
		  cache: true,
		  success: callback
	});
}



/**
 * 提交资源分配表单
 */
function submitSelectOrgForm(){
	
	$("#allCheckResources").attr("value",getCheckedNodes());
	
	var form = document.forms[0];
	form.target="mbdif";		//表单提交后在父页面显示结果
	form.submit();
	window.top.TB_remove();
}