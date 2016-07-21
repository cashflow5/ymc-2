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
	var queryForm = document.getElementById("queryForm");
	queryForm.action = doQueryUrl;
	queryForm.submit();
}

function toAdd() {
	window.location.href = toAddUrl;
}
function doRemove(mainId) {
	if (confirm("确认删除！") == false) {
		return;
	}
	$.ajax({
		type : "POST",
		url : doRemoveUrl,
		data : {
			"mainId" : mainId
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == true) {
				doQuery();
				alert(resultMsg.msg);
			} else {
				alert(resultMsg.msg);
			}
		}
	});
}
