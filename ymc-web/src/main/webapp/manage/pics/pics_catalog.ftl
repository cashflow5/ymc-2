<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-图片空间</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/zTreeStyle.css?${style_v}"/>
<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
<script type="text/javascript" src="${BasePath}/swfupload/swfupload.js"></script>
<script type="text/javascript" src="${BasePath}/swfupload/plugins/swfupload.speed.js"></script>
<script type="text/javascript" src="${BasePath}/swfupload/fileprogress.js"></script>
<script type="text/javascript" src="${BasePath}/swfupload/filevalidattion.js"></script>
<script type="text/javascript" src="${BasePath}/swfupload/handlers.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/ztree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/ztree/jquery.ztree.exedit-3.5.min.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/ztree/jquery.ztree.excheck-3.5.min.js"></script>
	<SCRIPT type="text/javascript">
		<!--
		var setting = {
			view: {
				dblClickExpand: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onClick: onClick,
				onDblClick:onDblClick
			}
		};

    var zNodes = [];
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
		function onDblClick(e, treeId, treeNode){
			hideMenu();
		}
		function onClick(e, treeId, treeNode) {
		    $("#catalog").attr("value", '');
		    $("#catalogId").val('');
            $("#shopId").val('');
            $("#catalogPath").html('');
            
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
            $("#catalogId").val(treeNode.id);
            $("#shopId").val(treeNode.shopId);
            
            var path = "";
			var p = treeNode.getParentNode();
			while (p != null){
				path = p.name + " > " + path;
				p = p.getParentNode();
			}
			path=path + treeNode.name;
			$("#catalogPath").html('目录:'+path);;
		}

		function showMenu() {
			var cityObj = $("#catalog");
			var cityOffset = $("#catalog").offset();
			$("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");

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

		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		});
		//-->
	</SCRIPT>
</head>

<body>
	<div style="padding: 10px;">
		<div style="margin: 0 0 0px 0;">
		    <span style="display: inline-block;">选择目录:<input id="catalog" type="text" readonly value="" style="padding: 2px;width:120px;" onclick="showMenu(); return false;"/></span>
			<a class="button">
				<span><div id="spanButtonPlaceHolder">控件加载中...</div></span>
			</a>
			<a class="button" onclick="javascript:$('#fsUploadProgress').find('a').click();">
				<span>清空列表</span>
			</a><input id="catalogId" type="hidden" value=""/><input id="shopId" type="hidden" value=""/>
			<span><div id="catalogPath"></div></span>
		</div>
		<div style="float: left; display: inline; width: 75%;">
			<!--列表start-->
			<table class="list_table" style="margin-top: 0;">
				<thead>
					<tr>
						<th width="50%">文件名</th>
						<th width="30%">大小</th>
						<th width="20%">操作</th>
					</tr>
				</thead>
				<tbody id="fsUploadProgress">
					<tr>
						<td colspan="3">
							<font color="red">暂无图片,请点击“添加图片”按扭添加。</font>
						</td>
					</tr>
				</tbody>
			</table>
			<!--列表end-->
		</div>
		<div style="float: right; display: inline; width: 25%; height: 400px;">
			<div style="padding: 0 15px 15px 15px;">
				<b>提示：</b><br />
				1、<span>商品图片仅支持&nbsp;<b>jpg</b>&nbsp;格式图片</span><br />
				2、<span>上传商品描述图片规则：<br />
  					&nbsp;&nbsp;&nbsp;•&nbsp;图片大小不超过1M<br />
  					&nbsp;&nbsp;&nbsp;•&nbsp;图片规范<br />
  					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;宽790px、高不限<br />
  					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;示例：SJKSBM_01_b.jpg</span>
			</div>
			<div style="padding: 0 15px 15px 5px;">
				<a id="uploader" class="button" onclick="javascript:picuploadFile();"><span>&nbsp;&nbsp;立即上传&nbsp;&nbsp;</span></a>
			</div>
			<div id="errorMessage" style="padding: 0 15px 15px 5px; color: red;">
			</div>
		</div>
		<div style="clear: both;"></div>
	</div>
	<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
		<ul id="treeDemo" class="ztree" style="margin-top:0; width:300px;"></ul>
    </div>
</body>
<script type="text/javascript">
var swfu;
var swfu_post_params = { merchantCode: "${merchantUsers.supplier_code!''}", replacement: ""};
$(document).ready(function(){
	swfu = new SWFUpload({
		flash_url : "${BasePath}/swfupload/swfupload.swf",
		upload_url: "${BasePath}/picture/uploaddescribe.sc;JSESSIONID=${jsessionId!''}",
		file_size_limit : "1 MB",
		file_types : "*.jpg;*.png",
		file_types_description : "Web Image Files",
		file_queue_limit : (swfu_post_params.replacement == '' ? 100 : 1),
		post_params: swfu_post_params,
		custom_settings : {
			progressTarget : "fsUploadProgress",
			ignoreValidation : 'true'
		},
		debug: false,
		// Button settings
		button_image_url: "${BasePath}/yougou/images/blank.gif",
		button_width: "74",
		button_height: "25",
		button_placeholder: document.getElementById('spanButtonPlaceHolder'),
		button_text: '选择添加图片',
		button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
		button_cursor: SWFUpload.CURSOR.HAND,
		button_text_top_padding: 3,
		button_text_left_padding: 0,
		// The event handler functions are defined in handlers.js
		file_queued_handler : fileQueued,
		file_queue_error_handler : fileQueueError,
		file_dialog_complete_handler : fileDialogComplete,
		upload_start_handler : uploadStart,
		upload_progress_handler : uploadProgress,
		upload_error_handler : uploadError,
		upload_success_handler : uploadSuccess,
		upload_complete_handler : uploadComplete
	});
});
function picuploadFile(){
	if($("#catalog").val()==''){
		alert('请先选择要上传的目录!')
	}else{
	    swfu.addPostParam("catalogId",$("#catalogId").val());
	    swfu.addPostParam("shopId",$("#shopId").val());
		eval(swfu.getStats().files_queued > 0 ? swfu.startUpload() : alert('请先添加图片!'));
	}
}
function FileValidation(ignoreValidation) {
	//判断是店铺图片还是商品图片
	var shopId=$("#shopId").val();
	if(shopId==''){
		this.fileIgnoreValidation = ignoreValidation;
		
		this.KB = 1024;
		this.fileSizes = [ this.KB * 10, this.KB * 60, this.KB * 10, this.KB * 10, this.KB * 500, this.KB * 1024, this.KB * 25, this.KB * 10, this.KB * 10 ];
		this.fileNameRegexps = [ '_01_s\.(jpg)$', '_0[1-7]_m\.(jpg)$', '_0[1-7]_t\.(jpg)$', '_01_c\.(jpg)$', '_0[1-7]_l\.(jpg)$', '_(0[1-9]|[1-9][0-9])_b\.(jpg)$', '_0[1-7]_mb\.(jpg)$', '_0[1-7]_ms\.(jpg)$', '_01_u\.(jpg)$' ];
		this.filePixelRegexps = [ '^160\\*160$', '^480\\*480$', '^60\\*60$', '^40\\*40$', '^([89][0-9][0-9]|1000)\\*([89][0-9][0-9]|1000)$', '^740\\*[1-9]([0-9]|[0-9][0-9][0-9])$', '^240\\*240$', '^160\\*160$', '^100\\*100$' ];
		this.filePixelMessage = [ '160*160', '480*480', '60*60', '40*40', '800-1000*800-1000', '740*100-9999', '240*240', '160*160', '100*100' ];
		this.startIndex = 4;
		this.endIndex = 6;
	}
}

// Validate file size
/*-------------------------------------------------------------------------------------
用途			|解释							|规格			|大小		|图片名称
---------------------------------------------------------------------------------------
列表页		|列表页图片（1个角度）			|160X160		|10k		|商品编码_01_s
---------------------------------------------------------------------------------------
单品页		|商品详细页左边大图（7个角度）	|480X480		|60k		|商品编码_01_m
			|商品缩略小图（7个角度）			|60X60			|10k		|商品编码_01_t
			|商品颜色选择小图（1个角度）		|40X40			|10k		|商品编码_01_c
			|放大镜 大图（7个角度）			|1000X1000		|500k		|商品编码_01_l
			|商品描述图						|740X100+		|200k		|商品编码_01_b
---------------------------------------------------------------------------------------
其他	手机 	|手机版小图（7个角度）			|240X240		|25k		|商品编码_01_mb
			|手机版小图（7个角度）			|160X160		|10k		|商品编码_01_ms
			|后台程序（1个角度）				|100X100		|10k		|商品编码_01_u
-------------------------------------------------------------------------------------*/
FileValidation.prototype.validate = function (file) {
	var defaultMessage = this.fileIgnoreValidation != 'true' ? null : '文件名称格式错误';
	if (defaultMessage != null) {
		if (file.name.replace(/[^\x00-\xff]/g,"**").length>32) {
			return '文件名称不能超过32个字符(注:1个汉字占两个字符)：' + file.name;
		}
		if (file.size > (this.KB * 1024)) {
			return '文件大小不能超过：' + SWFUpload.speed.formatBytes(this.KB * 1024);
		}
		if (file.loaded && (file.width + '*' + file.height).match(new RegExp('^7([4-8][0-9]|90)\\*[1-9]([0-9]|[0-9][0-9]|[0-9][0-9][0-9])$', 'g')) == null) {
			return '文件像素必须为：' + '790*10-9999';
		}
		return null;
	}
	
	return defaultMessage;
};
</script>
</html>
