<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-商品-图片管理</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/zTreeStyle.css?${style_v}"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/pic.css"/>
<script type="text/javascript" src="${BasePath}/yougou/js/ZeroClipboard.js"></script>
<script type="text/javascript" src="${BasePath}/swfupload/plugins/swfupload.speed.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery.lazyload.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/ygajaxfileupload.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/pic.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/ztree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/ztree/jquery.ztree.exedit-3.5.min.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/ztree/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	var setting = {
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
	var zNodes = [];
	//加载分类Tree
	<#if treeModes??>
		<#list treeModes as item>
			zNodes[zNodes.length] = {
				id: '${item.id!""}', 
				pId: '${item.parentId!''}', 
				name: '${item.catalogName!''}', 
				shopId:'${item.shopId!''}',
				open:true
				
			};
		</#list>
	</#if>
	
	$.fn.zTree.init($("#ztree"), setting, zNodes);
	$("#addCatalog").bind("click", {isParent:true}, addCatalog);
	<#if merchantPictureVO.catalogId??>
		//默认选中专业节点
		var treeObj = $.fn.zTree.getZTreeObj("ztree");
		var node = treeObj.getNodeByParam("id", "${merchantPictureVO.catalogId!''}");
		treeObj.selectNode(node,false);
	</#if>
});

function treeOnClick(e, treeId, treeNode,clickFlag) {
    var form=$('#queryForm');

    var shopId = $('<input type="hidden" name="shopId" />');
    shopId.attr('value', treeNode.shopId);

    form.append(shopId);
 
    $('#catalogId').val(treeNode.id);
    
    var treelevel = $('<input type="hidden" name="treelevel" />');
    treelevel.attr('value', treeNode.level);
    form.append(treelevel);
    
	form.submit();
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
		var params = {'id':treeNode.id,'catalogName':treeNode.name,'parentId':treeNode.pId,'shopId':treeNode.shopId};
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
		var params = {'id':treeNode.id,'catalogName':treeNode.name};
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
	isParent = e.data.isParent,
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
		ygdg.dialog.alert("不允许删除受保护跟目录!");
	}else{
		zTree.removeNode(treeNode,true);
	}
}
</script>
</head>

<body>
	<div class="main_container">
		<div class="normal_box" style="border-right:0px;border-left:0px;border-bottom:0px;">
			<p class="title site" style="border-right:1px solid #DEDEE0;border-left:1px solid #DEDEE0;">当前位置：商家中心 &gt; 店铺管理 &gt; 图片空间</p>
			<div class="tab_panel" style="overflow: auto;margin:auto;">
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
				<div class="picdiv">
					<!--搜索start-->
					<div class="search_box">
						<form id="queryForm" name="queryForm" method="post" action="${BasePath}/picture/list.sc">
							<p>
								<span>
									<label style="width: 70px;">上传时间：</label>
									<input type="text" name="createdStart" id="startTime" class="inputtxt" style="width: 80px;" value="<#if merchantPictureVO??&&merchantPictureVO.createdStart??>${merchantPictureVO.createdStart?date}</#if>" />
									至
									<input type="text" name="createdEnd" id="endTime" class="inputtxt" style="width: 80px;" value="<#if merchantPictureVO????&&merchantPictureVO.createdEnd??>${merchantPictureVO.createdEnd?date}</#if>" />
								</span>
								<span>
								<label style="width: 10px;">&nbsp;</label>
								<a href="javascript:changeTimeMark('1');" <#if timeMark?default('-1')=='1'>class="msg-active"</#if> >今天</a>
								<a href="javascript:changeTimeMark('2');" <#if timeMark?default('-1')=='2'>class="msg-active"</#if>>最近一个月</a>
								<a href="javascript:changeTimeMark('3');" <#if timeMark?default('-1')=='3'>class="msg-active"</#if>>最近三个月</a>
								<a href="javascript:changeTimeMark('-1');" <#if timeMark?default('-1')=='-1'>class="msg-active"</#if>>全部</a>
								</span>
								<span> 
									<label style="width: 70px;">图片名称：</label>
									<input type="text" name="srcPicName" id="innerPicName" class="inputtxt" value="${merchantPictureVO.srcPicName!''}" />
								</span>
								<input type="hidden" name="timeMark" value="${timeMark}"/>
								<input type="hidden" name="catalogId"  id="catalogId" value="${catalogId}"/>
								<span>
									<a class="button" id="mySubmit" onclick="javascript:$('#queryForm').submit()"><span>搜索</span></a>
								</span>
							</p>
						</form>
					</div>
					<!--搜索end-->
					<!--列表start-->
					<table class="list_table">
						<thead>
							<tr>
								<th style="text-align: left; padding-left: 15px;">
									<span class="fl" style="margin-top: 5px;"><input type="checkbox" id="checkAll" />&nbsp;<label for="checkAll">全选</label></span>
									<span class="fl"><a class="button" href="javascript:;" onclick="javascript:deleteSelectedPic();return false;" id="remover"><span>删除</span></a></span>
									<span class="fl"><a class="button" href="javascript:;" onclick="javascript:moveSelectedPic();return false;" id="picmove"><span>移动</span></a></span>
									<span style="margin-left: 10px;"><a class="button" id="uploader" onclick="javascript:uploadPic();return false;"><span>上传图片</span></a></span>
								</th>
							</tr>
						</thead>
					</table>
					<div id="pic-list">
						<ul class="goodsPicsList clearfixed" id="goodsPicsList">
							<#if pageFinder?? && pageFinder.data?? && pageFinder.data?size != 0>
								<#list pageFinder.data as item>
									<li>
										<p class="img relative">
										    <a href="${commodityPreviewDomain!''}${item.picPath}${item.picName!''}" target="_blank" class="f_blue">
												<img class="lazy_loading" id="${item.id}" src="${BasePath}/yougou/images/blank.gif" data-original="${commodityPreviewDomain!''}${item.picPath}${(item.thumbnaiPicName!item.picName)!''}?${random}" width="128px"/>
											</a>
										</p>
										<p>
											<input style="border: none; width: 132px; margin: 0 0 0 -5px;" readonly="readonly" value="<#if item.sourcePicName??&&item.sourcePicName!=''>${item.sourcePicName?replace("\\?[0-9]*", "","ri")}<#else>${item.picName?replace("\\?[0-9]*", "","ri")}</#if>"/>
										</p>
										<p style="margin:0;padding:0;">
										    <#if item.shopId??&&item.shopId?length gt 0><img src="${BasePath}/yougou/images/dian.png" width="12px"/><#else><img src="${BasePath}/yougou/images/shang.png" width="12px"/></#if>
											<a href="javascript:;" onclick="javascript:return false;" class="f_blue" onmouseover="copyPic('${commodityPreviewDomain!''}${item.picPath}${item.picName!''}?${random}', this);">复制链接</a>
											<a href="javascript:;" onclick="javascript:return false;" class="f_blue" onmouseover="copyPic('<img src=&quot;' + '${commodityPreviewDomain!''}${item.picPath}${item.picName!''}?${random}' + '&quot;/>', this);">复制代码</a>
										</p>
										<p style="margin:0;padding:0;">
											<input type="checkbox" name="chk" value="${item.id}" />
											<a href="javascript:;" onclick="javascript:deletePic('${item.id}');return false;" class="f_blue">删除</a>
											<a href="javascript:;" onclick="javascript:movePic('${item.id}','${item.shopId!''}');return false;" class="f_blue">移动</a>
	                                        <a href="javascript:;" data-attr="{title: '<#if item.sourcePicName??&&item.sourcePicName!=''>${item.sourcePicName!''}<#else>${item.picName?replace("\\?[0-9]*", "","ri")}</#if>', time:'${item.created?datetime}', size:'${(item.picSize / 1024)?string('0')}',wh:'${item.width!0}px X ${item.height!0}px'}" ; class="f_blue picDetail">详情</a>
										</p>
									</li>
								</#list>
								<script type="text/javascript">
									if ($('input[name="chk"]').size() == 0) {
										$('#checkAll').attr('disabled', true);
										$('#remover').addClass('dis').unbind('click').removeAttr('onclick');
									}
									<#if !merchantPictureVO.catalogId??>
										$('#picmove').addClass('dis').unbind('click').removeAttr('onclick');
									</#if>
								</script>
							<#else>
							   <li style="text-align: center;"> 查询不到相关记录!</li>
							</#if>
						</ul>
					</div>
					<!--列表end-->
					<!--分页start-->
					<#if pageFinder?? && pageFinder.data??>
						<div class="page_box">
							<#import "/manage/widget/common.ftl" as page>
							<@page.queryForm formId="queryForm"/>
						</div>
					</#if>
					<!--分页end-->
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" id="moveCatalogId" value=""/>
	<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
		<ul id="treeDemo" class="ztree" style="margin-top:0; width:300px;height:200px"></ul>
    </div>
</body>
<script type="text/javascript">
$("#startTime").calendar({maxDate:'#endTime'});
$("#endTime").calendar({minDate:'#startTime'});

function changeTimeMark(mark){
    location.href="${BasePath}/picture/list.sc?timeMark="+mark;
}
var settingDemo = {
		view: {
			dblClickExpand: false
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			onClick: onClickDemo,
			onDblClick:onDblClickDemo
		}
};
function onDblClickDemo(e, treeId, treeNode){
	hideMenu();
}
function onClickDemo(e, treeId, treeNode) {
    $("#catalog").attr("value", '');
    $("#moveCatalogId").val('');
    
	if (treeNode.level==0){
		alert("请选择子目录...");
		return false;
	}
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
	nodes = zTree.getSelectedNodes(),
	v = "";
	nodes.sort(function compare(a,b){return a.id-b.id;});
	for (var i=0, l=nodes.length; i<l; i++) {
		v += nodes[i].name + ",";
	}
	if (v.length > 0 ) v = v.substring(0, v.length-1);
	
    $("#catalog").attr("value", v);
    $("#moveCatalogId").val(treeNode.id);
}
var zIndexNum=2000;
function showMenu() {
	var cityObj = $("#catalog");
	var cityOffset = $("#catalog").offset();
	zIndexNum=zIndexNum+3;
	$("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px",zIndex:zIndexNum}).slideDown("fast");

	$("body").bind("mousedown", onBodyDown);
}
function hideMenu() {
	$("#menuContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
		hideMenu();
	}
}
function moveSelectedPic(){
	var treeObjSrc = $.fn.zTree.getZTreeObj("ztree");
	nodes = treeObjSrc.getSelectedNodes(),
	treeNode = nodes[0];
	if(treeNode){
		var ids = $('input[name="chk"]').filter(function(){
			return this.checked;
		}).map(function(){
			return this.value;
		}).get();
		
		if (ids.length <= 0) {
			ygdg.dialog.alert('请先选择需要移动的图片!');
			return false;
		}
		movePic(ids,treeNode.shopId);
	}else{
		ygdg.dialog.alert("请先选择指定目录的图片!");
	}
}
function movePic(picId,nodeid){
      if(nodeid==''){
      	nodeid='0';
      }
      var treeObjTarget = $.fn.zTree.init($("#treeDemo"), settingDemo);
      var treeObjSrc = $.fn.zTree.getZTreeObj("ztree");
      treeObjTarget.copyNode(null,treeObjSrc.getNodeByParam('id',nodeid),'inner',true );
      var dialog=ygdg.dialog({
      title: '移动图片',
      icon: null,
      content: '<span style="display: inline-block;">选择目录:<input id="catalog" type="text" readonly value="" style="padding: 2px;width:120px;" onclick="showMenu(); return false;"/></span>',
       button: [
       {
    		name: '确定',
    		callback: function () {	
				var params = {'picId':picId+'','catalogId':$("#moveCatalogId").val()};
				$.ajax({
					url: '${BasePath}/picture/movePics.sc',
					async: true,
			 		type: "POST",			
					data: params,
					dataType: "json",
					success: function(data){
						if(typeof(data) != "undefined" && data != null && data['result'] == true){
		                    //ygdg.dialog.alert("图片移动成功!");
		                    refreshpage();
						}else{
							ygdg.dialog.alert("图片移动失败,请联系技术支持!");
						}
					},
					error:function(xhr, textStatus, errorThrown){ 
						ygdg.dialog.alert("服务器错误,请稍后再试!");
						return;
					}
				});
    	    },
    	    focus: true
		},
		{
    		name: '关闭'
		}
	   ]
    });
}
//复制到剪切板
function copyPic(text, btn) {
	try {
		var clip = new ZeroClipboard.Client();
		clip.setHandCursor(true);
		clip.addEventListener('mouseOver', function(client) {
			clip.setText(text);
		});
		clip.addEventListener('complete', function(client, text) {
			alert(btn.innerHTML + "成功!");
		});
		clip.glue(btn);
	} catch (e) {
	}
}
//批量删除图片
function deleteSelectedPic() {
	var ids = $('input[name="chk"]').filter(function(){
		return this.checked;
	}).map(function(){
		return this.value;
	}).get();
	
	if (ids.length <= 0) {
		alert('请先选择需要删除的图片!');
		return false;
	}
	
	deletePic(ids);
}
//删除图片
function deletePic(ids) {
	if (confirm('确定删除' + ($.isArray(ids) ? '选择的' : '该') + '图片吗？')) {
		$.ajax({
			type: 'post',
			url: '${BasePath}/commodity/pics/delete.sc',
			dataType: 'text',
			data: 'ids=' + ids + '&rand=' + Math.random(),
			beforeSend: function(jqXHR) {
			},
			success: function(data, textStatus, jqXHR) {
				if (data == "success") {
					alert('删除图片成功!');
					refreshpage();
				} else {
					this.error(jqXHR, textStatus, data);
				}
			},
			complete: function(jqXHR, textStatus) {
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert('删除图片失败,该图片可能已被使用!');
			}
		});
	}
}
//上传图片
function uploadPic() {
	ygdgDialog.open('${BasePath}/picture/upload/ready.sc?catalogId=${merchantPictureVO.catalogId!''}', {
		width: 800,
		height: 600,
		title: '上传图片',
		close: function(){
			refreshpage();
		}
	});
}
//图片校验
function CheckFile(img) {
	// 判断图片类型
	if (!/\.(jpg)$/g.test(img.value.toLowerCase())) {
		ygdg.dialog.alert("商品图片类型必须是jpg！");
		return false;
	}
	return true;
}
//全选
$("#checkAll").click(function() {
	$("#goodsPicsList").find("input[name='chk']").attr("checked", this.checked);
});

//详情
$(".picDetail").hover(function() {
	var _this = $(this);
	var data = eval('(' + $(this).attr("data-attr") + ')');
	var _top = _this.offset().top - $(document).scrollTop();
	ygdg.dialog({
		padding : 0,
		title : data.title,
		content : '<p class="picDetail">图片大小：' + data.size + ' kb<br/>上传时间：' + data.time + '<br/>长宽：'+data.wh+'</p>',
		id : 'detailBox',
		left : $(this).offset().left + 30,
		top : _top,
		closable : false,
		fiexed : true
	});
}, function(){
	ygdg.dialog.list['detailBox'].close();
});
$(function(){
	$("#goodsPicsList img").lazyload({
		effect : "fadeIn",
		load : function(elements_left, settings) {
            AutoResizeImage(128,128,this);
        }
  	});
});
</script>
</html>
