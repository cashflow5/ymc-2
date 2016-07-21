$(document).ready(function(){	
		//文件上传
		var warehouseId = '';
		var storageCode = '';
		warehouseId= getwarehouseId();
		
		$("#otherinto").uploadify({
		'uploader'       : '../../../imageUploadComponentUI/xls/images/uploadify.swf',//指定上传控件的主体文件，默认‘uploader.swf’
		'script'         : 'importoherintostorage.sc?warehouseId='+warehouseId, //指定服务器端上传处理文件
		'scriptData'     : {'otherinto':$('#otherinto').val()},
		'cancelImg'      : '../../../imageUploadComponentUI/xls/images/cancel.png',
		'fileDataName'   : 'otherinto',
		'fileDesc'       : 'otherintoStorage*.xls或otherintoStorage*.csv',  //出现在上传对话框中的文件类型描述
		'fileExt'        : 'otherintoStorage*.xls;otherintoStorage*.csv',      //控制可上传文件的扩展名，启用本项时需同时声明fileDesc
		'sizeLimit'      : 2048000,           //控制上传文件的大小，单位byte				
		'folder'         : '/uploadImages',
		'queueID'        : 'fileQueueA',
		'auto'           : false,
		'displayData'    : 'pevcentage',
		'multi'          : false,
		
		'onerror'     	: function(event,queueID,fileObj){
			alert("生成入库单失败！");
			}
		,
		'onComplete'     :function(event,ID,fileObj,response,data){
			alert("文件 ：" + fileObj.name + "生成入库单成功！");
			}
		});
});		

function getwarehouseId(){
	var warehouseId = $("#warehouseId").val();
	return warehouseId;
}
function getstorageCode(){
	var storageCode = $("#storageCode").val();
	return storageCode;
}
setInterval('getwarehouseId()',1000)
setInterval('getstorageCode()',1000)