function getHostAddress() {
	$.ajax({
		type : "POST",
		url : getHostAddressUrl,
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == "true") {
				alert(resultMsg.msg);
			} else {
				alert(resultMsg.msg);
			}
		}
	});
}
function toEncrypt() {
	openwindow(toEncryptUrl, 500, 200, '加密');
}

function doEncrypt() {
	var preEncrypt = document.getElementById("preEncrypt").value;
	if (preEncrypt == '') {
		alert("请输入所需加密串！");
		return;
	}
	$.ajax({
		type : "POST",
		url : doEncryptUrl,
		data : {
			"preEncrypt" : preEncrypt
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == "true") {
				document.getElementById("lastEncrypt").value = resultMsg.msg;
			} else {
				alert(resultMsg.msg);
			}
		}
	});
}

function doMatchSecondSample() {
	$.ajax({
		type : "POST",
		url : doMatchSecondSampleUrl,
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == "true") {
				alert(resultMsg.msg);
			} else {
				alert(resultMsg.msg);
			}
		}
	});
}