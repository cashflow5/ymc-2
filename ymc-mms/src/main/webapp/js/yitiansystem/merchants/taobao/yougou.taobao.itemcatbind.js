var setting4yougou = {
		data: {
			simpleData: {
				enable:true,
				idKey: "id",
				pIdKey: "pId",
				isParent:"parent",
				structName:"structName",
				rootPId: ""
			}
		},
		async: {
			enable: true,
			dataType: "json",
			url:taobaoYougouItemCatBind.basePath+"/yitiansystem/taobao/getYougouItemTree.sc?filterBind=true",
			autoParam:["structName"],
			dataFilter: ajaxDataFilter
		},
		view: {
			selectedMulti: true,
			showLine: true,
			showIcon:false
		},callback: {
			onClick: treeOnClick4yougou
		}
};

var setting4taobao = {
		data: {
			simpleData: {
				enable:true,
				idKey: "id",
				pIdKey: "pId",
				isParent:"parent",
				structName:"structName",
				rootPId: ""
			}
		},
		async: {
			enable: true,
			dataType: "json",
			url:taobaoYougouItemCatBind.basePath+"/yitiansystem/taobao/getTaobaoItemTree.sc?notBind=y",
			autoParam:["id"],
			dataFilter: ajaxDataFilter4Taobao
		},
		view: {
			selectedMulti: true,
			showLine: true,
			showIcon:false
		},callback: {
			onClick: treeOnClick4taobao
		}
};


function ajaxDataFilter(treeId, parentNode, responseData) {
	responseData = responseData.yougouTree;
    return responseData;
};

function ajaxDataFilter4Taobao(treeId, parentNode, responseData) {
	responseData = responseData.taobaoTree;
    return responseData;
};

$(function(){
	var t_yougou = $("#tree_yougou");
	t_yougou = $.fn.zTree.init(t_yougou, setting4yougou);
	
	var t_taobao = $("#tree_taobao");
	t_taobao = $.fn.zTree.init(t_taobao, setting4taobao);
	
	$("#yougouInput").click(function(event){
		event.stopPropagation();
		showYougouTree();
	});
	$("#taobaoInput").click(function(event){
		event.stopPropagation();
		showTaobaoTree();
	});
	
	$(document).bind("click", function(e){
		$(".tree-con").hide();
	});
	
	$(".tree-con").click(function(event) {
		event.stopPropagation();
	});
});

function treeOnClick4taobao(e, treeId, treeNode,clickFlag) {
	if(!treeNode.isParent){
		var nodeName= [];
		getAllNodeName(nodeName,treeNode);
		var nodeNameStr ="";
		for(var i = nodeName.length;i>0;i--){
			if(i==nodeName.length){
				nodeNameStr = nodeName[i-1];
			}else{
				nodeNameStr = nodeNameStr+" > "+nodeName[i-1];
			}
		}
		$("#taobaoItemName").text(nodeNameStr);
		$("#taobaoTree").hide();
	}
}

function treeOnClick4yougou(e, treeId, treeNode,clickFlag) {
	if(!treeNode.isParent){
		var nodeName= [];
		getAllNodeName(nodeName,treeNode);
		var nodeNameStr ="";
		for(var i = nodeName.length;i>0;i--){
			if(i==nodeName.length){
				nodeNameStr = nodeName[i-1];
			}else{
				nodeNameStr = nodeNameStr+" > "+nodeName[i-1];
			}
		}
		$("#yougouItemName").text(nodeNameStr);
		$("#yougouTree").hide();
	}
}

function getAllNodeName(nodeName,node){
	nodeName.push(node.name);
	if(node.getParentNode().parentTId!=null){
		getAllNodeName(nodeName,node.getParentNode());
	}else{
		nodeName.push(node.getParentNode().name);
	}
}

/**
 * 展示优购分类树
 */
function showYougouTree(){
	$(".tree-con").hide();
	$("#yougouTree").show();
};

function showTaobaoTree(){
	$(".tree-con").hide();
	$("#taobaoTree").show();
};


taobaoYougouItemCatBind.nextStep = function(){
	var tree_yougou = $.fn.zTree.getZTreeObj("tree_yougou");
	var tree_taobao = $.fn.zTree.getZTreeObj("tree_taobao");
	
	var nodes_yougou = tree_yougou.getSelectedNodes();
	if(nodes_yougou.length==0||$("#yougouItemName").text()==""){
		ygdg.dialog.alert("请选择优购分类");
		return;
	}
	var nodes_taobao = tree_taobao.getSelectedNodes();
	if(nodes_taobao.length==0||$("#taobaoItemName").text()==""){
		ygdg.dialog.alert("请选择淘宝分类");
		return;
	}
	var yougouItemId = nodes_yougou[0].itemId;
	var taobaoCid = nodes_taobao[0].itemId;
	$("#yougouItemId").val(yougouItemId);
	$("#taobaoCid").val(taobaoCid);
	$("#taobaoCatFullName").val($("#taobaoItemName").text());
	$("#yougouCatFullName").val($("#yougouItemName").text());
	$("#bindForm").submit();
};
