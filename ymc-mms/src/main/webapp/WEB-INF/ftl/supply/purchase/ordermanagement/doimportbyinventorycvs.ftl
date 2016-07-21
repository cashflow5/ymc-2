<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#include "../../supply_include.ftl">
<title>优购商城--商家后台</title>
</head>

<!-- 文件上传开始 -->
<link href="${BasePath}/imageUploadComponentUI/xls/css/default.css" rel="stylesheet" type="text/css" />
<link href="${BasePath}/imageUploadComponentUI/xls/css/uploadify.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${BasePath}/imageUploadComponentUI/xls/js/swfobject.js"></script>
<script type="text/javascript" src="${BasePath}/imageUploadComponentUI/xls/js/jquery.uploadify.v2.1.0.min.js"></script>
<!-- 文件上传结束 -->
<body>
<!-- 显示图片 -->
<div id="pic" style="position: absolute; right: 28px; top: 79px; width: 180px; height: 230px; z-index: 2;"></div>
<div class="demo">
<p><strong>导入入库单设置(最大2M)</strong></p>
<input id = "result" type= "hidden" value ="${result?if_exists}"/>
<input id="inventory" name="inventory" type="file" />
 <a href="javascript:$('#inventory').uploadifyUpload();">导入库存</a> |
 <a href="javascript:$('#inventory').uploadifyClearQueue();">清除列表</a></div>
<script type="text/javascript">	 
			//文件上传
		
		$("#inventory").uploadify({
		'uploader'       : '../../../imageUploadComponentUI/xls/images/uploadify.swf',//指定上传控件的主体文件，默认‘uploader.swf’
		'script'         : 'doimportbyinventory.sc', //指定服务器端上传处理文件
		'scriptData'     : {'inventory':$('#inventory').val()},
		'cancelImg'      : '../../../imageUploadComponentUI/xls/images/cancel.png',
		'fileDataName'   : 'inventory',
		'fileDesc'       : 'Inventory*.csv',  //出现在上传对话框中的文件类型描述
		'fileExt'        : 'Inventory*.csv',      //控制可上传文件的扩展名，启用本项时需同时声明fileDesc
		'sizeLimit'      : 2048000,           //控制上传文件的大小，单位byte				
		'folder'         : '/uploadImages',
		'queueID'        : 'fileQueueA',
		'auto'           : 	false,
		'displayData'    : 'pevcentage',
		'multi'          :  false,
		'onError'     	: function(event,queueID,fileObj){
				alert("导入库存失败！");
		},
		'onComplete'     :function(event,ID,fileObj,response,data){
			var result = $(response).find('#result').val();
			if ( result == "true" ){
				alert("导入库存成功");
			}
			if ( result == "false" ){
				alert("导入库存失败"  );
			}
		}
	});

</script>
 <div id="fileQueueA" ></div>
</body>
</html>
