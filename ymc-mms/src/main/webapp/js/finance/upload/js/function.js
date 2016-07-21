$(document).ready(function(){	
	//A文件上传
		$("#uploadFile").uploadify({
		'uploader'       : '../../js/finance/upload/images/uploadify.swf',//指定上传控件的主体文件，默认‘uploader.swf’
		'script'         : 'excuteUpload.sc', //指定服务器端上传处理文件
		'scriptData'     : {'uploadFile':$('#uploadFile').val(), 'receiptType':$('#receiptType').val(), 'receiptId':$('#receiptId').val()},
		'cancelImg'      : '../../js/finance/upload/images/cancel.png',
		'fileDataName'   : 'uploadFile',
		'sizeLimit'      : 2048000,           //控制上传文件的大小，单位byte				
		'folder'         : '/uploadImages',
		'queueID'        : 'fileQueueA',
		'auto'           : false,
		'multi'          : false,
		'onComplete'  : function(event, queueID, fileObj, response, data) {
			var infors = '<table width="100%" height="100%" style="line-height:200%;">'
				+'<tr><td align="center" style="font-weight:bold;" height="50px">'
				+response
				+'</td>'
				+'</tr>'
				+'<tr><td align="center" height="50px">'
				//+'<input type="button" value="返  回" onclick="javascript:parent.TB_remove_this();parent.location.reload();">'
				+'</td></tr></table>';
			$('#contentInfors').parent().html(infors);
		}  
	});
});		
	