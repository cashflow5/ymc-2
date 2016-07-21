$(document).ready(function(){	
	//A文件上传
		$("#tms").uploadify({
		'uploader'       : '../../imageUploadComponentUI/xls/images/uploadify.swf',//指定上传控件的主体文件，默认‘uploader.swf’
		'script'         : 'tms.sc', //指定服务器端上传处理文件
		'scriptData'     : {'tms':$('#tms').val()},
		'cancelImg'      : '../../imageUploadComponentUI/xls/images/cancel.png',
		'fileDataName'   : 'tms',
		'fileDesc'       : '3PL.xls或3PL_S.xls',  //出现在上传对话框中的文件类型描述
		'fileExt'        : '3PL.xls;3PL_S.xls',      //控制可上传文件的扩展名，启用本项时需同时声明fileDesc
		'sizeLimit'      : 2048000,           //控制上传文件的大小，单位byte				
		'folder'         : '/uploadImages',
		'queueID'        : 'fileQueueA',
		'auto'           : true,
		'multi'          : false
	});
});		
	