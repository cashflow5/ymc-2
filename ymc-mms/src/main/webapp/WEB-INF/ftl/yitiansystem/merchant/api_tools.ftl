<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购网上鞋城 招商 API 测试页</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<style type="text/css">
.fixed {
	list-style: none;
	margin: 0;
	padding: 0;
}
.fixed li {
	line-height: 30px;
}
.fixed span {
	display: inline-block;
	width: 120px;
	text-align: right;
}
.required {
	color: red;
}
.inputer {
	border-color: red;
	background-color: #ffffb1;
}
</style>
</head>

<body>
	<div style="float: left; margin: 0 10px 0 10px; font-size: 12px;">
		<div>
			<div style="float: left; display: inline;">
				<form id="myform" name="myform" action="/" method="post">
					<ul id="input" class="fixed">
						<li class="fixed-item">
							<span>时间戳：</span>
							<input type="text" id="timestamp" name="timestamp" readonly="readonly" style="width: 195px;"/>
							<font class="required">*</font>
						</li>
						<li class="fixed-item">
							<span>返回结果：</span>
							<select id="format" name="format" style="width: 200px;">
								<option value="XML">XML</option>
								<option value="JSON">JSON</option>
							</select>
							<font class="required">*</font>
						</li>
						<li class="fixed-item">
							<span>API类目：</span>
							<select id="category" name="category" style="width: 200px;">
								<option value="">--请选择API类目--</option>
							</select>
							<font class="required">*</font>
						</li>
						<li class="fixed-item">
							<span>API：</span>
							<select id="method" name="method" style="width: 200px;">
								<option value="">--请选择API--</option>
							</select>
							<font class="required">*</font>
						</li>
						<li class="fixed-item">
							<span>提交方式：</span>
							<input type="radio" id="postsubmit" name="submit" value="POST" checked="checked"/>
							<label for="postsubmit">POST</label>
							<input type="radio" id="getsubmit" name="submit" value="GET"/>
							<label for="getsubmit">GET</label>
							<font class="required">*</font>
						</li>
						<li class="fixed-item">
							<span>参数签名方式：</span>
							<input type="radio" id="sign_method" name="sign_method" value="MD5" checked="checked"/>
							<label for="postsubmit">MD5</label>
							<input type="radio" id="sign_method" name="sign_method" value="SHA-1"/>
							<label for="getsubmit">SHA-1</label>
							<font class="required">*</font>
						</li>
						<li class="fixed-item">
							<span>app_key：</span>
							<input type="text" id="app_key" name="app_key" value="自动分配" readonly="readonly" style="width: 195px;"/>
							<font class="required">*</font>
							<span style="display: inline-block; width: 65px; text-align: left;"><a href="javascript:;" onclick="javascript:swapSignatureMode(this);return false;">自定义</a></span>
						</li>
						<li class="fixed-item">
							<span>app_secret：</span>
							<input type="text" id="app_secret" name="app_secret" value="自动分配" readonly="readonly" style="width: 195px;"/>
							<font class="required">*</font>
						</li>
						<li class="fixed-item">
							<span>&nbsp;</span>
							<input id="submitbutton" type="button" value="提交测试" />
						</li>
					</ul>
				</form>
			</div>
			<div style="float: left; display: inline; width: 40px;">&nbsp;</div>
			<div style="float: left; display: inline;">
				<ul id="output" class="fixed">
					<li class="fixed-item">
						提交参数：
					</li>
					<li class="fixed-item">
						<textarea id="inputparams" rows="10" style="width: 440px;"></textarea>
					</li>
					<li class="fixed-item">
						返回结果：
					</li>
					<li class="fixed-item">
						<textarea id="outputparams" rows="10" style="width: 440px;"></textarea>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<div id="tooltip" style="display: none; padding: 10px; position: absolute; background-color: #ffffb1; border: 1px #ffa500 solid; line-height: 25px; width: 400px; word-wrap: break-word; word-break: break-all;"></div>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>
<script type="text/javascript">
$(document).ready(function(){
	window.setInterval('initTimestamp()', 1000);
	$.ajax({
		type: 'post', dataType: 'json', data: '',
		url: '${BasePath}/apitools/categorys.sc',
		success: function(data, textStatus) {
			if (data) {
				var documentFragment = '';
				var selectedValue = '';
				$.each(data, function(i) {
					documentFragment += '<option prop="' + this.categoryCode + '" value="' + this.id + '">' + this.categoryName + '</option>';
				});
				
				$('#category').append(documentFragment).bind('change', function() {
					selectedValue = $(this).val();
					if (selectedValue.replace(/^\s*|\s*$/g, '') != '') {
						$('#method').get(0).options.length = 1;
						$.each(data, function(j) {
							if (this.id == selectedValue) {
								$.each(this.apis, function(k) {
									$('#method').append('<option id="' + this.id + '" prop="' + this.apiCode + '" value="' + this.apiMethod + '">' + this.apiMethod + '</option>')
								});
								return false;
							} 
						});
					}
				});
				
				$('#method').bind('change', function() {
					selectedValue = $(this).val();
					if (selectedValue.replace(/^\s*|\s*$/g, '') != '') {
						var selectedOption = $(this).find('option:selected');
						$.ajax({
							type: 'post', dataType: 'json', data: 'apiId=' + selectedOption.attr('id'),
							url: '${BasePath}/apitools/inputparams.sc',
							beforeSend: function(XMLHttpRequest) {
								openDiv({ title: '请稍候', width: 300, height: 100, content: '正在努力加载数据中...' });
							},
							success: function(data, textStatus) {
								documentFragment = '';
								documentFragment += '<li class="fixed-item dynamic-fixed-item">\n';
								documentFragment += '将鼠标移至说明上，查看参数介绍；<font class="required">*</font> 表示必填；查看<a href="http://open.yougou.com/apidoc/view_api.sc?apiCode=' + selectedOption.attr('prop') + '" target="_blank">API详情</a>';
								documentFragment += '</li>\n';
								$.each(data, function(k) {
									documentFragment += '<li class="fixed-item dynamic-fixed-item">\n';
									documentFragment += '<span>' + this.paramName + '：</span>\n';
									documentFragment += '<input type="text" ctype="' + this.paramDataType.toLowerCase() + '" id="' + this.paramName + '" name="' + this.paramName + '" style="width: 195px;"/>\n';
									documentFragment += (this.isRequired == 'Y' ? '<font class="required">*</font>\n' : '');
									documentFragment += '<a href="javascript:;" style="display: inline-block;" onmouseover="javascript:showTooltip(this, \'' + this.paramDescription + '\', \'block\');" onmouseout="javascript:showTooltip(this, \'\', \'none\');" onclick="javascript:return false;">说明</a>\n';
									documentFragment += '</li>\n';
								});
								$('.dynamic-fixed-item').remove();
								$('#input li:last').before(documentFragment);
								$('input[ctype="date"]').each(function(){
									$(this).calendar({ format: 'yyyy-MM-dd HH:mm:ss' });
								});
								$('input[ctype="number"]').keypress(function(e){
									var ecode = e.keyCode || e.which
									return (ecode >= 48 && ecode <= 57) || ecode == 8;
								}).change(function(){
									if (new RegExp('[^0-9]+').test($(this).val())) {
										$(this).val('');
									}
								});
								closewindow();
							},
							error: function(XMLHttpRequest, textStatus, errorThrown) {
								alert(textStatus.toUpperCase() + ': ' + XMLHttpRequest.responseText);
								closewindow();
							}
						});
					}
				});
				
				$('#submitbutton').bind('click', function() {
					var hasError = false;
					// check required input
					$('.required').each(function(i) {
						$(this).siblings('input,select').each(function(){
							if ($(this).val().replace(/^\s*|\s*$/g, '') == '') {
								$(this).addClass('inputer');
								hasError = true;
								return false;
							} else {
								$(this).removeClass('inputer');
							}
						});
					});
					if (!hasError) {
						// disabled test button
						$(this).attr('disabled', true);
						$('#inputparams').val('');
						$('#outputparams').val('');
						// get signature token
						$.ajax({
							type: 'post', dataType: 'text', data: $('#myform').serialize(), url: '${BasePath}/api_test_url.sc',
							success: function(data, textStatus) {
								// post test
								$.ajax({
									type: 'get', dataType: 'text', data: '', url: data,
									beforeSend: function(XMLHttpRequest) {
										$('#inputparams').val(data);
									},
									success: function(data, textStatus) {
										$('#outputparams').val(data);
										$('#submitbutton').attr('disabled', false);
									},
									error: function(XMLHttpRequest, textStatus, errorThrown) {
										$('#outputparams').val(XMLHttpRequest.responseText);
										$('#submitbutton').attr('disabled', false);
									}
								});
							},
							error: function(XMLHttpRequest, textStatus, errorThrown) {
								$('#outputparams').val(XMLHttpRequest.responseText);
								$('#submitbutton').attr('disabled', false);
							}
						});
					}
				});
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert(textStatus.toUpperCase() + ': ' + XMLHttpRequest.responseText);
		}
	});
});

function swapSignatureMode(anchor) {
	if (anchor.innerHTML.replace(/^\s*|\s*$/g, '') == '自定义') {
		anchor.innerHTML = '自动分配';
		$('#app_key,#app_secret').val('').attr('readonly', false);
	}
	else if (anchor.innerHTML.replace(/^\s*|\s*$/g, '') == '自动分配') {
		anchor.innerHTML = '自定义';
		$('#app_key,#app_secret').val('自动分配').attr('readonly', true);
	}
}

function showTooltip(anchor, content, display) {
	var offset = $(anchor).offset();
	$('#tooltip').html(content).css({ 'left': offset.left + $(anchor).width() + 5, 'top': offset.top, 'display': display });
}

function initTimestamp() {
	var date = new Date();
	var dateString = '';
	dateString += date.getFullYear() + '-';
	dateString += (date.getMonth() + 1) + '-';
	dateString += date.getDate() + ' ';
	dateString += date.getHours() + ':';
	dateString += date.getMinutes() + ':';
	dateString += date.getSeconds() + '';
	$('#timestamp').val(dateString);
}
</script>
</body>
</html>
