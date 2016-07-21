$(document).ready(function() {
	   var typeval=document.getElementById("deliveryStorageType").value;
	   if(typeval==1){
	   		$('input[name=deliveryStorageTypeRadio]').get(1).checked = true;
	   }else{
		    $('input[name=deliveryStorageTypeRadio]').get(0).checked = true;
       }
});

//审核通过
function doCheckPay() {
	var mainId = document.getElementById("mainId").value;
	$.ajax({
		type : "POST",
		url : doCheckPayUrl,
		data : {
			"mainId" : mainId
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == true) {
				doCheckPass(mainId);
			} else {
				if (confirm(resultMsg.msg) == false) {
					return;
				}
				doCheckPass(mainId);
			}
		}
	});
}

function doCheckPass(mainId) {
	juhua();
	$.ajax({
		type : "POST",
		url : doCheckPassUrl,
		data : {
			"mainId" : mainId
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == true) {
				alert(resultMsg.msg);
				closewindow();
				goback();
			} else {
				alert(resultMsg.msg);
				closewindow();
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

function doSaveOutPay() {
	myIframe.window.doSaveOutPay();
}
function doCheckPassOutPay() {
	myIframe.window.doCheckPassOutPay();
}
function toSelPay() {
	var supplierCode = document.getElementById("supplierCode").value;
	openwindow(toSelPayUrl + "?supplierCode=" + supplierCode, 850, 700, '新增明细');
}

function doRemove() {
	var mainId = document.getElementById("mainId").value;
	$.ajax({
		type : "POST",
		url : doRemoveUrl,
		data : {
			"mainId" : mainId
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

function initBtnShow() {
	var status = document.getElementById("status").value;
	if ("0" == status) {
		// 未确认
		document.getElementById("toSelPayBtn").style.display = "";
		document.getElementById("doSaveOutPayBtn").style.display = "";
		document.getElementById("doRemoveBtn").style.display = "";
		document.getElementById("doCheckPassOutPayBtn").style.display = "";
	}
	if ("1" == status) {
		// 已确认
		document.getElementById("toSelPayBtn").style.display = "none";
		document.getElementById("doSaveOutPayBtn").style.display = "none";
		document.getElementById("doRemoveBtn").style.display = "none";
		document.getElementById("doCheckPassOutPayBtn").style.display = "none";
	}
}
