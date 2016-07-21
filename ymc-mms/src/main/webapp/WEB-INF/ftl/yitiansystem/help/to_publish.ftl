<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,优购网,B网络营销系统,招商帮助中心" />
<meta name="Description" content=" , ,B网络营销系统-招商帮助中心" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/js/yitiansystem/merchants/ztree/css/zTreeStyle/zTreeStyle.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/js/common/tree/css/easyui.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/js/common/tree/css/icon.css"/>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script> 
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/order.js"></script> 
<script type="text/javascript" src="${BasePath}/js/yitiansystem/merchants/ztree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/merchants/ztree/jquery.ztree.exedit-3.5.min.js"></script>
<script type="text/javascript" src="${BasePath}/js/common/tree/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${BasePath}/js/common/kindeditor/kindeditor.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${BasePath}/js/common/kindeditor/zh_CN.js" charset="utf-8"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js"></script>
<title>B网络营销系统-招商帮助中心-优购网</title>

<script type="text/javascript">
var basePath = "${BasePath}";

var loadingImgUrl = "${BasePath}/yougou/images/layout/blue-loading.gif";

var tree = null;
var zNodes =[];
    
/**
 * 把节点都初始化到参数数组
 */
function initArrayByAllNode(nodes,arrObj){
   	for (var i = 0; i < nodes.length; i++) {
   	    arrObj[arrObj.length] = nodes[i];
   	    var tempChildren = nodes[i].children;
		if (tempChildren != null) {
			initArrayByAllNode(tempChildren,arrObj);		
		}
	}
}

/**
 * 获取Tree所有节点集合
 * @param zTree对象
 * @return Nodes
 */
function getTreeAllNode(treeObj) {
	var allNode = new Array();
	initArrayByAllNode(treeObj.getNodes(),allNode);
	return allNode;
}

/**
 * 获取Tree新节点的ID
 * @param zTree对象
 * @return newId
 */
function getTreeNewNodeId(treeObj) {
	var allNode = getTreeAllNode(treeObj);
	
	//获取最大的节点ID
	var maxId = 0;
	for (var i = 0; i < allNode.length; i++) {
	    var tempId = parseInt(allNode[i].id);
		if (tempId > maxId) {
			maxId = tempId;
		}
	}
	return (maxId + 1);
}

/**
 * 获取当前节点拥有的子节点数
 * @param treeObj zTree对象
 * @param treeNode 当前节点
 * @return num
 */
function getTreeNodeChildNum(treeObj, treeNode) {
	var index = 0;
	if (treeNode != null) {
		var childNode = treeNode.children;
		for (var i = 0; i < childNode.length; i++) {
			var l = treeObj.getNodeIndex(childNode[i]);
			if (index < l) {
				index = l;
			} 
		}
	}
	return index;
}

var setting = {
		view: {
			selectedMulti: false
		},
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "pid"
			}
		},
		callback: {
			onRightClick: onRightClick,
			onClick: onClick
		}
};

function onRightClick(event, treeId, treeNode) {
	// 判断点击了tree的“空白”部分，即没有点击到tree节点上
	if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
		tree.cancelSelectedNode();
		// 只显示添加菜单项，这个只是外观上的控制，不能控制到点击事件！菜单项的点击事件还要额外判断！
		$('#modifyNode').attr('disabled', 'true');
		$('#delNode').attr('disabled', 'true');
		$('#addSubNode').attr('disabled', 'true');
		$('#addNode').attr('disabled', 'true');
		$('#moveUpNode').attr('disabled', 'true');
		$('#moveDownNode').attr('disabled', 'true');
	} else if (treeNode && !treeNode.noR) { // 判断点击的是tree节点
		tree.selectNode(treeNode); // 选中tree节点
		$('#modifyNode').attr('disabled', 'false');
		$('#delNode').attr('disabled', 'false');
		$('#addSubNode').attr('disabled', 'false');
		$('#addNode').attr('disabled', 'false');
		$('#moveUpNode').attr('disabled', 'false');
		$('#moveDownNode').attr('disabled', 'false');
		if (treeNode.children && treeNode.children.length > 0) { // 这里父节点不能直接删除，也可以在菜单项的click事件中根据当前节点另作判断
			//$('#delNode').attr('disabled', 'true');
		}
	}
	// 在ztree右击事件中注册easyui菜单的显示和点击事件，让这两个框架实现共用event，这个是整合的关键点
	$('#mm').menu({
		onClick: function(item) {
			if (item.id == 'addSubNode' && $('#addSubNode').attr('disabled') == 'false') { //增加子类目
				if (2 != treeNode.level) {
					var newNode = [{id:getTreeNewNodeId(tree), pid:treeNode.id, name:'新子类目'}]; 
					var nodes = tree.addNodes(treeNode, newNode);
					//聚焦到新节点上，并可编辑
					tree.editName(nodes[0]);
					help.editor.html('');					
				} else {
					ygdg.dialog.alert('暂不支持增加4级菜单！');
				}
			} else if (item.id == 'addNode') { //增加同级类目
				var nodes = [];
				if ($('#addNode').attr('disabled') == 'false') {
					var newNode = [{id:getTreeNewNodeId(tree), pid:treeNode.pid, name:'新类目'}]; 
					nodes = tree.addNodes(treeNode.getParentNode(), newNode);				
				} else {
					var newNode = [{id:getTreeNewNodeId(tree), pid:0, name:'新类目'}];
					nodes = tree.addNodes(null, newNode);
				}
				//聚焦到新节点上，并可编辑
				tree.editName(nodes[0]);
				help.editor.html('');	
			} else if (item.id == 'modifyNode' && $('#modifyNode').attr('disabled') == 'false') { //重命名
				tree.editName(treeNode);
			} else if (item.id == 'moveUpNode' && $('#moveUpNode').attr('disabled') == 'false') { //类目上移
				var index = tree.getNodeIndex(treeNode);
				if (index > 0) {
					var preNode = treeNode.getPreNode();
					tree.moveNode(preNode, treeNode, 'prev');
				}
			} else if (item.id == 'moveDownNode' && $('#moveDownNode').attr('disabled') == 'false') { //类目下移
				var max = getTreeNodeChildNum(tree, treeNode.getParentNode());
				var index = tree.getNodeIndex(treeNode);
				if (index < max) {
					var nextNode = treeNode.getNextNode();
					tree.moveNode(nextNode, treeNode, 'next');						
				} 
			} else if (item.id == 'delNode' && $('#delNode').attr('disabled') == 'false') { //删除类目
				if (treeNode.children && treeNode.children.length > 0) {
					ygdg.dialog.alert('该节点是父节点，不能直接删除！');
				} else {
					tree.removeNode(treeNode);
				}
			}
		}
	});
	$('#mm').menu('show', {
		left: event.pageX, 
		top: event.pageY
	});
}
//点击事件
function onClick(event, treeId, treeNode) {
    //加载帮助内容
	if (!treeNode.children) {
	   ygdg.dialog({id: "loadDialog", title:'提示', 
			content: '<img src="' + loadingImgUrl + '" width="16" height="16" /> 加载中...', 
			lock:false, closable:false});
	   help.editor.html('');
	   
	   $.ajax({
			url: "${BasePath}/yitiansystem/merchants/help/getContentByMenuId.sc?menuId="+treeNode.hcmId + "&subId=" + treeNode.id,
			type: "post",
			dataType: "text/html",
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			async: false,
			success: function(data) {
				help.editor.html(data);
			},
			error: function() {
				ygdg.dialog.alert("系统内部异常");
				ygdg.dialog({id: "loadDialog"}).close();
			}
		});
	   
	   ygdg.dialog({id: "loadDialog"}).close();
	   //获取节点标题
	   var title = getNodeFullTitleName(treeNode);
	   $('#help_title').text(title);
	   $('#help_title').show();
	   
	   $('#error_msgId').empty();
	} else {
	   help.editor.html('');
	   $('#help_title').hide();
	}
}

/**
 * @param treeNode 节点
 * @return 节点全名 【商品管理 > 商品上传 > 单品上传】
 */
function getNodeFullTitleName(treeNode) {
	var title = '';
	var nodes = new Array();
	getNodeFullArray(treeNode, nodes);
	
	for (var i = nodes.length; i > 0; i--) {
		title += nodes[i - 1].name + (i == 1 ? '' : ' > ');
	}
	return title;
}

function getNodeFullArray(treeNode, nodes) {
	nodes[nodes.length] = treeNode;
	var parent = treeNode.getParentNode();
	if (parent) {
		getNodeFullArray(parent, nodes);
	}
}

//文档载入时加载
$(document).ready(function(){
	
	zNodes = new Array();
	//加载分类Tree
	<#if treeModes??>
		<#list treeModes as item>
			zNodes[${item_index}] = {
				id: '${item.subId!""}',
				pid: '${item.parentId!''}', 
				name: '${item.menuName!''}', 
				hcmId: '${item.id!''}',
				open:true
			};
		</#list>
	</#if>
	
	
	//初始化树组件
	tree = $.fn.zTree.init($("#ztree"), setting, zNodes);
});
		
var help= {};	
/** 文本编辑器选项 */
help.options = {
	resizeType: 0,
	allowPreviewEmoticons: false,
	allowImageUpload: false,
	allowFlashUpload: false,
	allowMediaUpload: false,
	allowFileUpload: false,
	newlineTag: 'br',
	afterFocus: function(){
	    var focusLeafNode = getFocusLeafNode();
	    if(focusLeafNode==null){
	        $('#error_msgId').text('请先选择叶子节点菜单!');
	    }else{
	        $('#error_msgId').text('');
	    }
	},
	items: [
		'source', '|', 
		'undo', 'redo', '|', 
		'preview', 'cut', 'copy', 'paste', 'plainpaste', 'wordpaste', '|', 
		'justifyleft', 'justifycenter', 'justifyright', 'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript', 'superscript', 'clearhtml', 'selectall', '/',
		'formatblock', 'fontname', 'fontsize', '|', 
		'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|',
		'image', 'table', 'hr', 'anchor', 'link', 'unlink'
	]
}; 
/** 文本编辑器 */
help.editor;

/*
 * 初始化商品描述编辑器
 */
KindEditor.ready(function(K) {
	help.editor = K.create('#help_content', help.options);
});
</script>
</head>
<body>

<div class="container"> 
	<!--工具栏start-->
	<div class="toolbar">
		<div class="t-content"> <!--操作按钮start--> 
		</div>
	</div>
	<!--工具栏end-->
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
			  <li class='curr'> <span><a href="">发布帮助文档</a></span> </li>
			</ul>
		</div>
		<!--当前位置end--> 
		
		<!--主体start-->
		<div id="modify" class="modify">
			<table>
				<tr>
					<td>
					      <ul id="ztree" class="ztree helpTree" style="width:200px;height: 600px;border: 0 solid #617775;background:none;overflow-y:auto;"></ul>
					</td>
					<td>
						<div id="help_title" class="helptitle"></div>
					    <span id="error_msgId" style="color:red;font-size:25px;"></span>
						<input type="button" value="选择图片" onclick="imgBtn_OnClick();" class="btn-add-normal-4ft clearfix" style="margin-bottom:3px;">
						<textarea id="help_content" name="helpContent" style="width:800px;height:560px;"></textarea>
					</td>
				</tr>
			</table>
			<div>
				<form id="help_content_formId" action="${BasePath}/yitiansystem/merchants/help/save_content.sc" method="post">
				</form>
				<form id="help_previewcontent_formId" action="${BasePath}/yitiansystem/merchants/help/preview.sc" method="post" target="_blank">
				</form>
			</div>
			<#-- margin-left:206px; -->
			<div style="margin-top:0px;float:left">
				<span style="width:204px; float:left;">
					<#-- input type="button" value="发布静态页" onclick="return publish();" class="btn-add-normal-4ft clearfix" style="margin-left:40px;"-->
				</span>
				<span style="margin-left:204px;float:left">
					<input type="button" value="保存" onclick="return saveContent();" class="yt-seach-btn">
					<input type="button" value="预览" onclick="return preview();" class="yt-seach-btn" style="margin-left:30px;">				
				</span>
			</div>
		</div>
		<div id="mm" class="easyui-menu" style="width:120px;">  
         	<div id="addSubNode" data-options="name:'new'">增加子类目</div>
         	<div id="addNode" data-options="name:'new'">增加同级类目</div>
         	<div id="modifyNode" data-options="name:'modify'">编辑类目</div>
         	<div id="moveUpNode" data-options="name:'move'">类目上移</div>
         	<div id="moveDownNode" data-options="name:'move'">类目下移</div>
         	<div id="delNode" data-options="name:'del'">删除类目</div>  
     	</div>
	</div>
</div>
</body>
</html>
<script type="text/javascript">
/** 图片选择器URL */
var imageSelectorURL = basePath + '/yitiansystem/merchants/help/img_selector.sc';

/**
 * 初始化表单菜单数据
 */
function initMenuForm(formObj,nodes,arrIndexObj){
   	for (var i = 0; i < nodes.length; i++) {
   	    var tempChildren = nodes[i].children;
   	    if(nodes[i].hcmId){
   	       formObj.append('<input type="hidden" name="menuArr['+arrIndexObj.index+'].id" value="'+nodes[i].hcmId+'" />');
   	    }
   	    formObj.append('<input type="hidden" name="menuArr['+arrIndexObj.index+'].menuName" value="'+nodes[i].name+'" />');
   	    var tempPid = 0;
   	    if(nodes[i].pid){
   	        tempPid = nodes[i].pid;
   	    }
   	    formObj.append('<input type="hidden" name="menuArr['+arrIndexObj.index+'].parentId" value="'+tempPid+'" />');
   	    formObj.append('<input type="hidden" name="menuArr['+arrIndexObj.index+'].subId" value="'+nodes[i].id+'" />');
   	    formObj.append('<input type="hidden" name="menuArr['+arrIndexObj.index+'].level" value="'+nodes[i].level+'" />');
   	    var tempIsLeaf = 1;
		if (tempChildren != null) {
			tempIsLeaf = 0;
		}
   	    formObj.append('<input type="hidden" name="menuArr['+arrIndexObj.index+'].isLeaf" value="'+tempIsLeaf+'" />');
   	    formObj.append('<input type="hidden" name="menuArr['+arrIndexObj.index+'].orderNo" value="'+(i+1)+'" />');
   	    arrIndexObj.index++;
		if (tempChildren != null) {
			initMenuForm(formObj,tempChildren,arrIndexObj);		
		}
	}
}

/**
 * 发布商家帮助页
 */
function publish() {
	ygdg.dialog.confirm('确定要发布帮助静态页吗?', 
			function() {//YES
			    $.ajax({
					url: "${BasePath}/yitiansystem/merchants/help/publish.sc",
					type: "post",
					dataType: "text/html",
					async: false,
					success: function(data) {
						if ('success' == data) {
							ygdg.dialog.alert('发布成功.');
						} else {
							ygdg.dialog.alert('发布失败.');
						}
					},
					error: function() {
						ygdg.dialog.alert("系统内部异常");
					}
				});
			}, 
			function() {//NO
				closewindow();
			}
	);
}

/**
 * 保存发布内容到数据库
 */
function saveContent() {
    var focusLeafNode = getFocusLeafNode();
    if(focusLeafNode==null){
        $('#error_msgId').text('请先选择叶子节点菜单!');
        return false;
    }
    var editorHtml = $.trim(help.editor.html());
    if(editorHtml==''){
        $('#error_msgId').text('请先输入帮助文档内容!');
        return false;
    }
   var formObj = $('#help_content_formId');
   formObj.html('');
   var arrIndexObj = {index:0};
   //初始化表单菜单数据
   initMenuForm(formObj,tree.getNodes(),arrIndexObj);
   //初始化表单帮助文档内容和关联的菜单id数据
   formObj.append('<input type="hidden" name="menuSubId" value="'+focusLeafNode.id+'" />');
   formObj.append('<input type="hidden" name="menuId" value="'+focusLeafNode.hcmId+'" />');
   formObj.append('<textarea name="helpMenuContent" style="display:none;">'+help.editor.html()+'</textarea>'); 
   ygdg.dialog.confirm('确定保存吗？',
		function() {//YES
	   		$.ajax({
		        type: "POST",
		        url:formObj.attr('action'),
		        data:formObj.serialize(),
		        success: function(msg) {
		            $('#error_msgId').text(msg);
		            
		            setTimeout(function() {
		            	//刷新页面
		            	window.location.reload();
		            }, 1000);
		            
		        }
	   		});
   		}, 
		function() {//NO
   			closewindow(); 
		}
   );
}

/**
 * 获取焦点叶子节点
 */
function getFocusLeafNode(){
    var nodeFocusObj = $('#ztree').find('a.curSelectedNode');
	if(nodeFocusObj.size()>0){
	     var childrenNodeObj = nodeFocusObj.next("li");
	     if(childrenNodeObj.size()==0){
		     var nodetId = nodeFocusObj.attr('id').replace('_a','');
		     return tree.getNodeByTId(nodetId);
		 }
	}
	return null;
}

/**
 * 预览商家帮助页
 */
function preview() {
    var focusLeafNode = getFocusLeafNode();
    if(focusLeafNode==null){
        $('#error_msgId').text('请先选择叶子节点菜单!');
        return false;
    }
    var editorHtml = $.trim(help.editor.html());
    if(editorHtml==''){
        $('#error_msgId').text('请先输入帮助文档内容!');
        return false;
    }
   var formObj = $('#help_previewcontent_formId');
   formObj.html('');
   //初始化表单帮助文档内容
   formObj.append('<textarea name="helpMenuContent" style="display:none;">'+help.editor.html()+'</textarea>'); 
   formObj.append('<input type="hidden" name="menuId" value="'+focusLeafNode.hcmId+'" />');
   formObj.append('<input type="hidden" name="menuName" value="'+focusLeafNode.name+'" />');
   formObj.submit();
}

/**
 * 选择图片
 */
function imgBtn_OnClick() {
	openwindow(imageSelectorURL, 680, 460, '选择图片', null, 'image_window');
}

/**
 * 图片选择器 关闭事件
 * @param {String} imgUrl 图片地址
 */
function onImgSelected(imgUrl) {
	img.imgSelector_OnClose(imgUrl);
}

var img = {};
/**
 * 图片选择器 关闭事件
 * @param {String} imgUrl 图片地址
 */
img.imgSelector_OnClose = function(imgUrls) {
	ygdgDialog.parent[3].closewindow();
	if(imgUrls == null || imgUrls == '') return;
	var msg = '<br/>';
	var urls = imgUrls.split('&&&&&');
	$.each(urls, function(n, url){
		msg += formatString('<img src="{#imgUrl}" />',
		{
			imgUrl : url
		});
		msg += '<br/>';
	});
	help.editor.html(help.editor.html() + msg);
};


/**
 * 对目标字符串进行格式化
 * @author huang.wq
 * @param {string} source 目标字符串
 * @param {Object|string...} opts 提供相应数据的对象或多个字符串
 * @remark
 * opts参数为“Object”时，替换目标字符串中的{#property name}部分。<br>
 * opts为“string...”时，替换目标字符串中的{#0}、{#1}...部分。	
 * @shortcut format
 * @returns {string} 格式化后的字符串
 * 例：
    (function(arg0, arg1){ 
		alert(formatString(arg0, arg1)); 
	})('{#0}-{#1}-{#2}',["2011年","5月","1日"]);
	(function(arg0, arg1){ 
		alert(formatString(arg0, arg1)); 
	})('{#year}-{#month}-{#day}', {year: 2011, month: 5, day: 1});   
 */
function formatString(source, opts) {
    source = String(source);
    var data = Array.prototype.slice.call(arguments,1), toString = Object.prototype.toString;
    if(data.length){
	    data = data.length == 1 ? 
	    	/* ie 下 Object.prototype.toString.call(null) == '[object Object]' */
	    	(opts !== null && (/\[object Array\]|\[object Object\]/.test(toString.call(opts))) ? opts : data) 
	    	: data;
    	return source.replace(/\{#(.+?)\}/g, function (match, key){
	    	var replacer = data[key];
	    	// chrome 下 typeof /a/ == 'function'
	    	if('[object Function]' == toString.call(replacer)){
	    		replacer = replacer(key);
	    	}
	    	return ('undefined' == typeof replacer ? '' : replacer);
    	});
    }
    return source;
};
</script>