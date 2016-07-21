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
});
function doQuery() {
	if (document.getElementById("bTime").value == ''
			|| document.getElementById("eTime").value == '') {
		alert('请选择时间范围！');
		return;
	}

	var bTime = document.getElementById("bTime").value;
	var bArr = bTime.split("-");
	var bDate = new Date(bArr[0], bArr[1], bArr[2]);

	var eTime = document.getElementById("eTime").value;
	var eArr = eTime.split("-");
	var eDate = new Date(eArr[0], eArr[1], eArr[2]);

	var subTime = eDate.getTime() - bDate.getTime(); // 时间差的毫秒数
	// 计算出相差天数
	var subDays = Math.floor(subTime / (24 * 3600 * 1000));
	if (subDays > 30) {
		alert('请选择一个月范围内！');
		return;
	}

	var days = document.getElementById("days").value;
	var r = /^[1-9]d*|0$/;
	if (!r.test(days)) {
		alert('请输入整数的日期区间！');
		return;
	}
	$.ajax({
		type : "POST",
		url : doCheckTimeUrl,
		data : {
			"bTime" : bTime,
			"eTime" : eTime
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success) {
				$("#doQueryBtn").removeAttr('onclick');
				var queryForm = document.getElementById("queryForm");
				queryForm.action = queryUrl;
				queryForm.submit();
			} else {
				alert(resultMsg.msg);
			}
		}
	});

}
// 导出
function doExportForWeek() {
	if (document.getElementById("bTime").value == ''
			|| document.getElementById("eTime").value == '') {
		alert('请选择时间范围！');
		return;
	}

	var bTime = document.getElementById("bTime").value;
	var bArr = bTime.split("-");
	var bDate = new Date(bArr[0], bArr[1], bArr[2]);

	var eTime = document.getElementById("eTime").value;
	var eArr = eTime.split("-");
	var eDate = new Date(eArr[0], eArr[1], eArr[2]);

	var subTime = eDate.getTime() - bDate.getTime(); // 时间差的毫秒数
	// 计算出相差天数
	var subDays = Math.floor(subTime / (24 * 3600 * 1000));
	if (subDays > 30) {
		alert('请选择一个月范围内！');
		return;
	}
	$.ajax({
		type : "POST",
		url : doCheckTimeUrl,
		data : {
			"bTime" : bTime,
			"eTime" : eTime
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success) {
				$("#doExportForWeekBtn").removeAttr('onclick');

				doWMSExport("queryForm", doExportForWeekUrl);

			} else {
				alert(resultMsg.msg);
			}
		}
	});
}

// 导出明细
function doExportForAllWeekDetail() {
	if (document.getElementById("bTime").value == ''
			|| document.getElementById("eTime").value == '') {
		alert('请选择时间范围！');
		return;
	}

	var bTime = document.getElementById("bTime").value;
	var bArr = bTime.split("-");
	var bDate = new Date(bArr[0], bArr[1], bArr[2]);

	var eTime = document.getElementById("eTime").value;
	var eArr = eTime.split("-");
	var eDate = new Date(eArr[0], eArr[1], eArr[2]);

	var subTime = eDate.getTime() - bDate.getTime(); // 时间差的毫秒数
	// 计算出相差天数
	var subDays = Math.floor(subTime / (24 * 3600 * 1000));
	if (subDays > 30) {
		alert('请选择一个月范围内！');
		return;
	}
	$.ajax({
		type : "POST",
		url : doCheckTimeUrl,
		data : {
			"bTime" : bTime,
			"eTime" : eTime
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success) {
				$("#doExportForAllWeekDetailBtn").removeAttr('onclick');

				doWMSExport("queryForm", doExportForAllWeekDetailUrl);

			} else {
				alert(resultMsg.msg);
			}
		}
	});
}