<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>B网络营销系统-招商帮助中心-优购网</title>
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/yitiansystem/merchants/merchants-sys-global.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/js/common/swfupload/swfupload.js"></script>
<script type="text/javascript" src="${BasePath}/js/common/swfupload/plugins/swfupload.speed.js"></script>
<script type="text/javascript" src="${BasePath}/js/common/swfupload/fileprogress.js"></script>
<script type="text/javascript" src="${BasePath}/js/common/swfupload/filevalidattion.js"></script>
<script type="text/javascript" src="${BasePath}/js/common/swfupload/handlers.js"></script>
</head>

<body>
	<div style="padding: 10px;">
		<div>
			<input id="spanButtonPlaceHolder" type="button" value="控件加载中" onclick="javascript:void(0);" class="btn-add-normal-4ft" style="margin: 0 0 5px 0;">				
			<input type="button" value="清空列表" onclick="javascript:$('#fsUploadProgress').find('a').click();" class="btn-add-normal-4ft" style="margin: 0 0 20px 0;">
		</div>
		<div style="float: left; display: inline; width: 70%;">
			<!--列表start-->
			<table class="list_table_01" style="margin-top: 0;">
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
							<font color="red">暂无图片,请点击“添加商品图片”按扭添加。</font>
						</td>
					</tr>
				</tbody>
			</table>
			<!--列表end-->
		</div>
		<div style="float: right; display: inline; width: 30%; height: 400px;">
			<div style="padding: 0 15px 15px 15px;">
				<b>提示：</b><br />
				1、<span>仅支持&nbsp;<b>JPG</b>&nbsp;格式图片</span><br />
				2、<span>图片不要使用中文命名<br />
			</div>
			<div style="padding: 0 15px 15px 5px;">
				<input id="uploader" type="button" value="立即上传" onclick="javascript:eval(swfu.getStats().files_queued > 0 ? swfu.startUpload() : alert('请先添加商品图片'));" class="btn-add-normal-4ft">
			</div>
			<div id="errorMessage" style="padding: 0 15px 15px 5px; color: red;">
			</div>
		</div>
		<div style="clear: both;"></div>
	</div>
</body>
<script type="text/javascript">
var basePath = '${BasePath}';

var swfu;
var swfu_post_params = { replacement: "${replacement!''}", editFilename: "${editFilename?default('true')?string}" };
$(document).ready(function(){
	swfu = new SWFUpload({
		flash_url: "${BasePath}/js/common/swfupload/swfupload.swf",
		upload_url: "${BasePath}/upload/help_image.sc;JSESSIONID=${jsessionId!''}",
		file_size_limit : "1 MB",
		file_types : "*.jpg",
		file_types_description : "All Files",
		file_queue_limit : (swfu_post_params.replacement == '' ? 100 : 1),
		post_params: swfu_post_params,
		custom_settings : {
			progressTarget : "fsUploadProgress",
			ignoreValidation : swfu_post_params.editFilename
		},
		debug: false,
		// Button settings
		button_image_url: '${BasePath}/yougou/images/icon/btn-normal-4ft.gif',
		button_width: "74",
		button_height: "25",
		button_placeholder: document.getElementById('spanButtonPlaceHolder'),
		button_text: '添加商品图片',
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
</script>
</html>
