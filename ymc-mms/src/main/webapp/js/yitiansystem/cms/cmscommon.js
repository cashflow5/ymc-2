
function removeMyselfNode(){
	remove();
}

function getSelectNode(){
	var node = $('#resourceTree').tree('getSelected');
	return node;
}


function addNewNode(node){
	append(node);
}

function updateNewNode(node){
	$("#resourceTree").tree("update",node);
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
 * 节点点击时 加载节点基础数据
 * @param nodeid
 */
function loadNodeData(nodeid){

	var url = "queryTagsById.sc";
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

		var cunType = $(window.parent.document).find("#selectType").val();

		if(node.attributes.type != cunType){

			var path = $(window.parent.document).find("#reqBasePath").val();

			var url = typeSelectOnchange(path,node.attributes.type);

			$(window.parent.document).find("#selectType").attr("value",node.attributes.type);

			window.parent.tagsmbdif.location.href=path+"/yitiansystem/cms/tags/"+url;

			return ;
		}

		window.parent.tagsmbdif.loadNodeDetailData(node);
	});
}

/**
 * 选中当前类型
 * @param path
 * @param value
 * @returns {Boolean}
 */
function typeSelectOnchange(path,type){

	var  url = "";
	if(type == "0"){
		alert("请选择类型！");
		return false;
	}else if(type == "1"){ //模板
		url = "toAddTagsTemple.sc";
	}else if(type == "2"){ //宜天团购
		url = "toAddTagsCategory.sc";
	}else if(type == "3"){ //商品品牌
		url = "toAddTagsBrand.sc";
	}else if(type == "4"){ //图片广告
		url = "toAddTagsImageAdv.sc";
	}else if(type == "5"){ //公告
		url = "toAddTagsNotice.sc";
	}else if(type == "6"){ // 商品推荐
		url = "toAddTagsProduct.sc";
	}else if(type == "7"){ // 商品排行推荐
		url = "toAddTagsProductRank.sc";
	}
	return url;
}



