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
		"tagUrl":$("#tagUrl").val(),
		"type":"4",
		"brandNo":$("#brandNo").val(),
		"categoryNo":$("#categoryNo").val(),
		"channelNo":$("#channelNo").val(),
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
		"type":"4",
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
	$("#brandNo").attr("value",node.attributes.brandNo);
	$("#categoryNo").attr("value",node.attributes.categoryNo);
	$("#channelNo").attr("value",node.attributes.channelNo);
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
	$("#tagDesc").attr("value","");
	$("#ids").attr("value","");
	$("#no").attr("value","");
	
	$("#brandNo").attr("value","");
	$("#categoryNo").attr("value","");
	$("#channelNo").attr("value","");
	
}


/**
 * 获取弹出框传回的商品ID值
 * @param ids
 */
function saveSelectCommoditys(ids,type){
	$("#ids").attr("value",ids);
	
	submitSelectCommodity(type);
}


/**
 * 增加广告信息
 */
function toAddImageAdv(parentId){
	var params = "parentId="+parentId;
	showThickBox("增加广告信息","../../cms/imageAdv/toAddImageAdv.sc?TB_iframe=true&height=550&width=800",false,params);
}

/**
 * 修改广告信息
 */
function toUpdateImageAdv(id){
	var params = "id="+id;
	showThickBox("修改广告信息","../../cms/imageAdv/toUpdateImageAdv.sc?TB_iframe=true&height=550&width=800",false,params);
}

/**
 * 删除广告信息
 */
function removeImageAdv(id,parentId){
	if(confirm("确定要删除该广告？")){
		window.location.href = "d_remove.sc?id="+id+"&parentId="+parentId;
	}
}


/**
 * 提交表单
 */
function submitForm(result){
	if(result){
		var form = document.forms[0];
		form.target="tagscontentmbdif";		//表单提交后在父页面显示结果
//		form.submit();
		window.top.TB_remove();
	}
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

/**
 * 修改广告使用状态
 */
function changeUseState(id,state){
	
	var url = "alletTagsImages.sc";
	var data={
		"id":id,
		"state":state
	};
	
	ajaxRequest(url,data,function(result){
		if(!result) return ;
		result = result.replace(/(^\s*)|(\s*$)/g,'');
		if(result == "success"){
			alert("成功！");
			if(state == "1"){
				$("#"+id+"checkbox").attr("checked",'true');//选中
				$("#"+id+"AHidden").css("display","block");
				$("#"+id+"AShow").css("display","none");
			}else{
				$("#"+id+"checkbox").removeAttr("checked");//取消选中
				$("#"+id+"AShow").css("display","block");
				$("#"+id+"AHidden").css("display","none");
				
			}
			return ;
		}
	});
	
}


/**
 * 批量选中  将选中值传回到父页面  2011-05-05 yinhongbiao
 * @param chkName
 * @return

function batchSelect(chkName,parentNo){
	var checkVal ="";
	$("input[name='"+chkName+"']").each(function(){
		if(this.checked){
			checkVal += $(this).val() +",";
		}
	});
	
	
	var url = "alletTagsImages.sc";
	var data={
		"ids":checkVal,
		"parentNo":parentNo
	};
	
	ajaxRequest(url,data,function(result){
		if(!result) return ;
		result = result.replace(/(^\s*)|(\s*$)/g,'');
		if(result == "success"){
			alert("成功！");
			return ;
		}
	});
	
	window.top.TB_remove();
	
}
 */


