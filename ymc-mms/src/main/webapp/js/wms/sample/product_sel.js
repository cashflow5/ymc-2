// 全选
$(document).ready(function() {
	$("#checkall").click(function() {
		if (this.checked) {
			$("input[name='commodityCB']").each(function() {
				this.checked = true;
			});
		} else {
			$("input[name='commodityCB']").each(function() {
				this.checked = false;
			});
		}
	});
});
function doQuery() {
	var queryForm = document.getElementById("queryForm");
	queryForm.action = doQueryUrl;
	queryForm.submit();
}
// 保存
function doSaveDetail() {
	var checkedArray = getCheckBoxs("commodityCB");
	if (checkedArray.length == 0) {
		alert("请选择货品！");
		return;
	}
	var flag = ",";
	var productNos = "";
	for ( var i = 0; i < checkedArray.length; i++) {
		if (i == checkedArray.length - 1) {
			flag = "";
		}
		var productNo = checkedArray[i].value;
		productNos = productNos + productNo + flag;
	}
	if (!confirm("确定保存？")) {
		return;
	}
	$.ajax({
		type : "POST",
		url : doSaveDetailUrl,
		data : {
			"mainId" : mainId,
			"memo" : memo
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == true) {
				alert(resultMsg.msg);
				goback();
			} else {
				alert(resultMsg.msg);
			}
		}
	});
}
function queryDetail() {
	var mainId = document.getElementById("mainId").value;
	document.getElementById('myIframe').src = queryDetailUrl + "?mainId="
			+ mainId;
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
