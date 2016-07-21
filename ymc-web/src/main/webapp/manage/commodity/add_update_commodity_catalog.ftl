<#macro add_update_commodity_catalog>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/zTreeStyle.css?${style_v}"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/pic.css"/>
<script type="text/javascript" src="${BasePath}/yougou/js/ZeroClipboard.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/ztree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/ztree/jquery.ztree.exedit-3.5.min.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/ztree/jquery.ztree.excheck-3.5.min.js"></script>
</head>
<body>
<div style="float: left;width: 100px;">
	    <div class="picdiv2">
	       <div id="addCatalog" class="picbtn">
				<span class="btn_l"></span>
	        	<b class="ico_btn add"></b>
	        	<span class="btn_txt">添加目录</span>
	        	<span class="btn_r"></span>
        	</div>
        	<div onclick="editCatalog();" class="picbtn">
				<span class="btn_l"></span>
	        	<b class="ico_btn change"></b>
	        	<span class="btn_txt">重命名</span>
	        	<span class="btn_r"></span>
        	</div>
        	<div onclick="delCatalog();" class="picbtn">
				<span class="btn_l"></span>
	        	<b class="ico_btn delete"></b>
	        	<span class="btn_txt">删除</span>
	        	<span class="btn_r"></span>
        	</div>
		</div>
		<ul id="ztree" class="ztree"></ul>
	</div>
</body>
<script>
var setting;
$(document).ready(function(){
	setting = {
		data : {
			keep: {
				parent:true
			},
			simpleData: {
				enable: true
			}
		},
		edit: {
			enable: true,
			showRemoveBtn: false,
			showRenameBtn: false
		},
		view: {
			selectedMulti: false
		},
		callback: {
		   onRename:onRename,
		   beforeRemove: beforeRemove,
		   beforeRename: beforeRename,
		   onRemove: onRemove,
		   onClick:treeOnClick
		}
	};
	getCatalogTree();
	$("#addCatalog").bind("click", {isParent:true}, addCatalog);
});	
getCatalogTree = function(){
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data:{
			nickId:$("#nickId").val()
		},
		url :"${BasePath}/picture/loadPicCatalog.sc",
		success : function(data) {
			if(data.resultCode == "200"){
				var zNodes = [];
				var treeModes = data.treeModes;
				for(var i=0,_len=treeModes.length;i<_len;i++){
					var node = {
						id:treeModes[i].id, 
						pId:treeModes[i].parentId, 
						name:treeModes[i].catalogName, 
						shopId:treeModes[i].shopId,
						open:true
					};
					zNodes.push(node);
				}
				var treeObj = $.fn.zTree.init($("#ztree"),setting,zNodes);
				var node = treeObj.getNodeByParam("id","0");
				treeObj.selectNode(node,false);
				imageManageUpload.catalogId = "0";
			}else{
				ygdg.dialog.alert("加载分类异常!");
			}
		}
	});
}
	
function treeOnClick(e, treeId, treeNode,clickFlag) {
	if($("#image-tab").find("li").eq(0).attr("class")=="curr"){
	    imageManageUpload.catalogId = treeNode.id;
		return;
	}else{
		imageManageUpload.catalogId = treeNode.id;
		loadData(1);
	}
}
function onRemove(e, treeId, treeNode) {
		var params = {'id':treeNode.id};
		$.ajax({
			url: '${BasePath}/picture/delPicCatalog.sc',
			async: true,
	 		type: "POST",			
			data: params,
			dataType: "json",
			success: function(data){
				if(typeof(data) != "undefined" && data != null && data['result'] == true){
                    ygdg.dialog.alert("目录删除成功!");
                    treeOnClick(e, treeId, treeNode.getParentNode(),null);
				}else{
					ygdg.dialog.alert("目录删除失败,请联系技术支持!");
				}
			},
			error:function(xhr, textStatus, errorThrown){ 
				ygdg.dialog.alert("服务器错误,请稍后再试!");
				return;
			}
		});
}
var editOrSave=0;
function beforeRemove(treeId, treeNode) {
	return confirm("确认删除 目录 -- " + treeNode.name + " 吗?");
}
var oldname;
function beforeRename(treeId, treeNode, newName) {
	newName = newName.trim();
    oldname=treeNode.name;
	if (newName.length == 0) {
		ygdg.dialog.alert("目录名称不能为空!");
		var zTree = $.fn.zTree.getZTreeObj("ztree");
		zTree.cancelEditName(treeNode.name);
		return false;
	}
	return true;
}
function onRename(e,treeId, treeNode,isCancel) {
	if(editOrSave==1){
		//添加
		var params = {'id':treeNode.id,'catalogName':treeNode.name.trim(),'parentId':treeNode.pId,'shopId':treeNode.shopId};
		$.ajax({
			url: '${BasePath}/picture/savePicCatalog.sc',
			async: true,
	 		type: "POST",			
			data: params,
			dataType: "json",
			success: function(data){
				if(typeof(data) != "undefined" && data != null && data['result'] == true){
                    ygdg.dialog.alert("目录添加成功!");
				}else{
					ygdg.dialog.alert("目录添加失败,请联系技术支持!");
				}
			},
			error:function(xhr, textStatus, errorThrown){ 
				ygdg.dialog.alert("服务器错误,请稍后再试!");
				return;
			}
		});
	}else if(editOrSave==2&&oldname!=treeNode.name){
		//更新
		var params = {'id':treeNode.id,'catalogName':treeNode.name.trim()};
		$.ajax({
			url: '${BasePath}/picture/updatePicCatalog.sc',
			async: true,
	 		type: "POST",			
			data: params,
			dataType: "json",
			success: function(data){
				if(typeof(data) != "undefined" && data != null && data['result'] == true){
                    ygdg.dialog.alert("目录修改成功!");
				}else{
					ygdg.dialog.alert("目录修改失败,请联系技术支持!");
				}
			},
			error:function(xhr, textStatus, errorThrown){ 
				ygdg.dialog.alert("服务器错误,请稍后再试!");
				return;
			}
		});
	}
	editOrSave=0;
}
function timestamp() {
	var timestamp = Date.parse(new Date());
	return timestamp+parseInt(Math.random()*(1000));
} 
var newCount = 1;
//添加目录
function addCatalog(e){
	var zTree = $.fn.zTree.getZTreeObj("ztree");
	var isParent = e.data.isParent,
	nodes = zTree.getSelectedNodes(),
	treeNode = nodes[0];
	if (treeNode) {
	    if(treeNode.level<2){
    	    var shopId=treeNode.shopId;
	        treeNode = zTree.addNodes(treeNode, {id:timestamp(), pId:treeNode.id, isParent:false, name:"目录" + (newCount++),shopId:shopId});
	    }else{
	        ygdg.dialog.alert("目录不能超过三级!");
	    }
	} else {
	    ygdg.dialog.alert("请先选定父目录!");
	}
	if (treeNode) {
	    editOrSave=1;
		zTree.editName(treeNode[0]);
	}
}
//修改目录
function editCatalog(){  
		var zTree = $.fn.zTree.getZTreeObj("ztree"),
		nodes = zTree.getSelectedNodes(),
		treeNode = nodes[0];
		if (nodes.length == 0) {
		    ygdg.dialog.alert("请先选择要编辑的目录!");
			return;
		}
		editOrSave=2;
		zTree.editName(treeNode);
}
//删除目录
function delCatalog(){
    var zTree = $.fn.zTree.getZTreeObj("ztree"),
	nodes = zTree.getSelectedNodes(),
	treeNode = nodes[0];
	if (nodes.length == 0) {
	    ygdg.dialog.alert("请先选择一个目录!");
		return;
	}
	if(treeNode.level==0&&(treeNode.id=='0'||treeNode.id==treeNode.shopId)){
		ygdg.dialog.alert("不允许删除受保护的根目录!");
	}else{
		zTree.removeNode(treeNode,true);
	}
}
</script>
</html>
</#macro>