//审核通过
function doCheckPay() {
	var mainId = document.getElementById("mainId").value;

	var table = myIframe.window.document.getElementById("subTable");
	var idAndPay = "";
	var dealType = document.getElementById("dealType").value;
	if (dealType == 'pay') {
		for ( var i = 1; i < table.rows.length; i++) {
			var rowCells = table.rows[i].cells;
			if (rowCells[9].childNodes[0] != null) {
				var input = rowCells[9].getElementsByTagName("input");
				if (input.length > 0) {
					var firstInput = input[0];
					var detailId = firstInput.id;
					var payQuantiy = firstInput.value;

					if (!/^[1-9]\d*|0$/.test(payQuantiy)) {
						alert('请输入正确的格式！');
						return;
					}

					idAndPay = idAndPay + detailId + "_" + payQuantiy + ";";
				}
			}
		}
	}
	$.ajax({
		type : "POST",
		url : doCheckPayUrl,
		data : {
			"mainId" : mainId,
			"idAndPay" : idAndPay
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == true) {
				doCheckPass(mainId, idAndPay);
			} else {
				if (confirm(resultMsg.msg) == false) {
					return;
				}
				doCheckPass(mainId, idAndPay);
			}
		}
	});
}

function doCheckPass(mainId, idAndPay) {
	juhua();
	$.ajax({
		type : "POST",
		url : doCheckPassUrl,
		data : {
			"mainId" : mainId,
			"idAndPay" : idAndPay
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
/**
 * 删除
 */
function doSavePay() {
	myIframe.window.doSavePay();
}
