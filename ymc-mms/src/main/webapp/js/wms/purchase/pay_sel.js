$(document).ready(function() {
	$('#bTime').calendar({
		maxDate : '#eTime',
		format : 'yyyy-MM-dd',
		targetFormat : 'yyyy-MM-dd'
	});
	$('#eTime').calendar({
		minDate : '#bTime',
		format : 'yyyy-MM-dd',
		targetFormat : 'yyyy-MM-dd'
	});
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

function doQuerySel() {
	var queryForm = document.getElementById("querySelForm");
	queryForm.action = doQueryUrl;
	queryForm.submit();
}

function doAddDetail() {
	var checkBoxs = document.getElementsByName("commodityCB");
	var selProduct = "";
	for ( var i = 0; i < checkBoxs.length; i++) {
		var e = checkBoxs[i];
		if (e.checked) {
			selProduct = selProduct + checkBoxs[i].value + ";";
		}
	}
	if (selProduct == "") {
		alert("请选择货品！");
		return;
	}
	var mainId = dg.curWin.document.getElementById("mainId").value;

	var jh = juhua();
	$.ajax({
		type : "POST",
		url : doAddDetailUrl,
		data : {
			"mainId" : mainId,
			"selProduct" : selProduct
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == true) {
				jh.close();
				dg.curWin.queryDetail();
				if (resultMsg.msg != "") {
					alert(resultMsg.msg);
				}
				closewindow();
			} else {
				alert(resultMsg.msg);
				closewindow();
			}
		}
	});
}

function doAddAllDetail() {
	var mainId = dg.curWin.document.getElementById("mainId").value;
	var supplierCode = document.getElementById("supplierCode").value;
	var sendCode = document.getElementById("sendCode").value;
	var bTime = document.getElementById("bTime").value;
	var eTime = document.getElementById("eTime").value;
	var productNos = document.getElementById("productNos").value;

	var jh = juhua();
	$.ajax({
		type : "POST",
		url : doAddAllDetailUrl,
		data : {
			"mainId" : mainId,
			"supplierCode" : supplierCode,
			"sendCode" : sendCode,
			"bTime" : bTime,
			"eTime" : eTime,
			"productNos" : productNos
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == true) {
				jh.close();
				dg.curWin.queryDetail();
				if (resultMsg.msg != "") {
					alert(resultMsg.msg);
				}
				closewindow();
			} else {
				alert(resultMsg.msg);
				closewindow();
			}
		}
	});
}
// 打开菊花
function juhua() {
	var jh = ygdg.dialog({
		content : '处理中...',
		title : '',
		lock : true,
		closable : false
	});
	return jh;
}