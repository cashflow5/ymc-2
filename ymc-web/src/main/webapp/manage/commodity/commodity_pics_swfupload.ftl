<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-报表-经营概况</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css"/>
<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
<script type="text/javascript" src="${BasePath}/swfupload/swfupload.js"></script>
<script type="text/javascript" src="${BasePath}/swfupload/plugins/swfupload.speed.js"></script>
<script type="text/javascript" src="${BasePath}/swfupload/fileprogress.js"></script>
<script type="text/javascript" src="${BasePath}/swfupload/filevalidattion.js"></script>
<script type="text/javascript" src="${BasePath}/swfupload/handlers.js"></script>
</head>

<body>
	<div style="padding: 10px;">
		<div>
			<a class="button" style="margin: 0 0 5px 0;">
				<span><div id="spanButtonPlaceHolder">控件加载中...</div></span>
			</a>
			<a class="button" style="margin: 0 0 5px 0;" onclick="javascript:$('#fsUploadProgress').find('a').click();">
				<span>清空列表</span>
			</a>
		</div>
		<div style="float: left; display: inline; width: 70%;">
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
				2、<span>上传7张角度图片规则：<br />
  					&nbsp;&nbsp;&nbsp;•&nbsp;图片大小不超过500KB<br />
  					&nbsp;&nbsp;&nbsp;•&nbsp;尺寸为800*800至1000*1000之间的<strong>正方形</strong>图片<br />
  					&nbsp;&nbsp;&nbsp;•&nbsp;图片命名规范<br />
  					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商家款色编码_2位流水号_l.jpg<br />
  					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;示例：SJKSBM_01_l.jpg<br />
  					&nbsp;&nbsp;&nbsp;•&nbsp;角度图片排序请参考商家操作手册<br /></span>
				3、<span>上传商品描述图片规则：<br />
  					&nbsp;&nbsp;&nbsp;•&nbsp;图片大小不超过1MB<br />
  					&nbsp;&nbsp;&nbsp;•&nbsp;图片命名规范<br />
  					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商家款色编码_2位流水号_b.jpg<br />
  					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;示例：SJKSBM_01_b.jpg</span>
			</div>
			<div style="padding: 0 15px 15px 5px;">
				<a id="uploader" class="button" onclick="javascript:eval(swfu.getStats().files_queued > 0 ? swfu.startUpload() : alert('请先添加商品图片'));"><span>&nbsp;&nbsp;立即上传&nbsp;&nbsp;</span></a>
			</div>
			<div id="errorMessage" style="padding: 0 15px 15px 5px; color: red;">
			</div>
		</div>
		<div style="clear: both;"></div>
	</div>
</body>
<script type="text/javascript">
var swfu;
var swfu_post_params = { merchantCode: "${merchantUsers.supplier_code!''}", replacement: "${replacement!''}", editFilename: "${editFilename?default('true')?string}" };
$(document).ready(function(){
	swfu = new SWFUpload({
		flash_url : "${BasePath}/swfupload/swfupload.swf",
		upload_url: "${BasePath}/commodity/pics/upload.sc;JSESSIONID=${jsessionId!''}",
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
		button_image_url: "${BasePath}/yougou/images/blank.gif",
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
