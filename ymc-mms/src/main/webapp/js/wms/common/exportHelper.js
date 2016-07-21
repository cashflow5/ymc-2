// 导出
function doWMSExport(formName, doExportUrl) {
	// 打开juhua
	doExportLoading();

	var exportDate = serializeJson(formName);

	$.ajax({
		type : "POST",
		url : doExportUrl,
		data : exportDate,
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == true) {
				var exportPath = resultMsg.reCode;
				var fileName = resultMsg.idCode;

				doRealWMSExport(exportPath, fileName);// 导出

				// 关闭juhua
				closewindow();
			} else {
				alert(resultMsg.msg);
				closewindow();
			}
		}
	});
}

function serializeJson(formName) {

	var form = $('#' + formName);
	var obj = form.serializeArray();
	return obj;
}
/**
 * 导出
 * 
 * @param exportPath
 *            导出文件地址
 * @param fileName
 *            导出文件名
 */
function doRealWMSExport(exportPath, fileName) {

	var browser = getBrowser();

	form = $("<form>");
	form.attr('id', 'tempExportForm');
	form.attr('name', 'tempExportForm');
	form.attr('action', wmsBaseExportUrl);
	form.attr('method', 'post');

	input1 = $("<input type='text' name='exportPath' />");
	input1.attr('value', exportPath);

	input2 = $("<input type='text' name='fileName' />");
	input2.attr('value', fileName);

	input3 = $("<input type='text' name='browser' />");
	input3.attr('value', browser);

	form.append(input1);
	form.append(input2);
	form.append(input3);

	form.appendTo("body");
	form.css('display', 'none');

	form.submit();

	form.remove();

}

function getBrowser() {
	var bro = $.browser;

	var name = '';
	if (bro.msie) {
		name = 'Microsoft Internet Explorer';
	} else if (bro.mozilla) {
		name = 'Firefox';
	} else if (bro.safari) {
		name = 'Safari';
	} else if (bro.opera) {
		name = 'Opera';
	} else {
		name = 'other';
	}
	var version = bro.version;

	var browser = name + ',' + version;

	return browser;
}