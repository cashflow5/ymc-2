// 确认
function doCheckPass() {
	var mainId = document.getElementById("mainId").value;
	var memo = document.getElementById("memo").value;

	if (confirm("确定后该数据将不允许再被修改，你确定要继续吗？") == false) {
		return;
	}
	// 打开菊花
	juhua();
	$.ajax({
		type : "POST",
		url : doCheckPassUrl,
		data : {
			"mainId" : mainId,
			"memo" : memo
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success) {
				closewindow();
				$("#doCheckPassBtn").removeAttr('onclick');
				alert("操作成功！");
				goback();
			} else {
				alert(resultMsg.msg);
				closewindow();
			}
		}
	});
}

// 打回
function doCheckFail() {
	var mainId = document.getElementById("mainId").value;
	var memo = document.getElementById("memo").value;
	if (memo == '') {
		alert("请输入打回原因！");
		return;
	}
	if (confirm("确定后该数据将不允许再被修改，你确定要继续吗？") == false) {
		return;
	}
	// 打开菊花
	juhua();
	$.ajax({
		type : "POST",
		url : doCheckFailUrl,
		data : {
			"mainId" : mainId,
			"memo" : memo
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success) {
				closewindow();
				$("#doCheckFailBtn").removeAttr('onclick');
				alert("操作成功！");
				goback();
			} else {
				alert(resultMsg.msg);
				closewindow();
			}
		}
	});

}
function toShowRefuse(mainId) {
	window.open(toShowRefuseUrl + "?id=" + mainId);
}

function goback() {
	window.location.href = doQueryUrl;
}
// 打开菊花
function juhua() {
	openDiv({
		content : '<div style="color:#ff0000">处理中...</div>',
		title : '提示',
		lock : true,
		width : 200,
		height : 60,
		closable : false,
		left : '50%',
		top : '40px'
	});
}
