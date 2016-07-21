/**
 * 显示质检货品信息
 */
function showRealQualityCheckInfo(detailId) {
	var mainId=parent.$("#mainId").val();
	parent.showRealQualityCheckInfo(mainId,detailId);
}
/**
 * 删除明细
 * 
 * @param detailId
 */
function doRemoveDetail(detailId) {
	if (detailId == '') {
		alert("请选择要删除的明细!");
		return;
	}
	$.ajax({
		type : "POST",
		url : doRemoveDetailUrl,
		data : {
			"detailId" : detailId
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == true) {
				if (resultMsg.msg == 'DELETE_ALL') {
					parent.reloadPage();
				} else {
					parent.doQueryApplyProduct(detailId);
//					var mainId=parent.$("#mainId").val();
					parent.showApplyProduct();
				}
			} else {
				alert(resultMsg.msg);
			}
		}
	});
}
/**
 * 跳转修改页面
 * 
 * @param detailId
 */
function toModifyDetail(detailId,applyNo) {
	parent.toModifyDetail(detailId,applyNo);
}
/**
 * 默认显示第一行
 * 
 * @returns {String}
 */
function defCheckFirst() {
	// 如果为确认状态
	var isDeal = document.getElementById("isDeal").value;
	var detailId = "";
	if ("DEAL" == isDeal) {
		var theRadioInputs = document.getElementsByName("applyProductRadio");
		for ( var i = 0; i < theRadioInputs.length; i++) {
			theRadioInputs[i].checked = true;
			detailId = theRadioInputs[i].value;
			break;
		}
		var mainId=parent.$("#mainId").val();
		parent.showRealQualityCheckInfo(mainId,detailId);
	}
}
