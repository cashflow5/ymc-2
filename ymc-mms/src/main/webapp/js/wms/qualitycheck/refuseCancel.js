// 作废
function doCancel() {
	var mainId = document.getElementById("mainId").value;

	if (confirm("作废后，质检结果不能恢复，请确认是否作废？") == false) {
		return;
	}
	// 打开菊花
	juhua();
	isApply(mainId);
}

function realDoCancel(mainId) {
	var cancelMemo = document.getElementById("cancelMemo").value;
	var cancelTime = document.getElementById("cancelTime").value;
	$.ajax({
		type : "POST",
		url : doCancelUrl,
		data : {
			"mainId" : mainId,
			"cancelTime" : cancelTime,
			"cancelMemo" : cancelMemo
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success) {
				closewindow();
				$("#doCancelBtn").removeAttr('onclick');
				alert("操作成功！");
				goback();
			} else {
				alert(resultMsg.msg);
				closewindow();
			}
		}
	});
}

/**
 * 判断是否已做售后申请
 */
function isApply(mainId) {
	$.ajax({
		type : "POST",
		url : isApplyUrl,
		data : {
			"mainId" : mainId
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success) {
				if (confirm(resultMsg.msg)) {
					realDoCancel(mainId);
				} else {
					closewindow();
				}
			} else {
				realDoCancel(mainId);
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
