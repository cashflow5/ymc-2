function orderSourceInit(path, obj_1, val_1, obj_2, val_2, obj_3, val_3) {
	// 定义默认数据
	var ar = [ "请选择一级来源", "请选择二级来源", "请选择店铺" ];
	// 初始化
	$("<option value=''>" + ar[0] + "</option>").appendTo($("#" + obj_1));
	$("<option value=''>" + ar[1] + "</option>").appendTo($("#" + obj_2));
	$("<option value=''>" + ar[2] + "</option>").appendTo($("#" + obj_3));

	// 一级分类
	getOrderSource(1, '', obj_1, val_1, path);
	if (val_1 != '') {
		// 二级分类
		getOrderSource(2, val_1, obj_2, val_2, path);
	}
	if (val_2 != '') {
		// 三级分类
		getOrderSource(3, val_2, obj_3, val_3, path);
	}
	// 响应obj_1的change事件
	$("#" + obj_1).change(function() {
		// 清空二三级
		$("#" + obj_2).empty();
		$("#" + obj_3).empty();

		$("<option value=''>请选择二级来源</option>").appendTo($("#" + obj_2));
		$("<option value=''>请选择店铺</option>").appendTo($("#" + obj_3));

		var selVal_1 = $("#" + obj_1).val();

		if (selVal_1 != '') {
			getOrderSource(2, selVal_1, obj_2, '', path);// 生成二级分类
		}
	});

	// 响应obj_2的change事件
	$("#" + obj_2).change(function() {
		// 清空三级
		$("#" + obj_3).empty();

		$("<option value=''>请选择店铺</option>").appendTo($("#" + obj_3));

		var selVal_2 = $("#" + obj_2).val();

		if (selVal_2 != '') {
			getOrderSource(3, selVal_2, obj_3, '', path);// 生成三级分类
		}
	});

}

function getOrderSource(level, fSelVal, selId, selVal, path) {

	var url = path + "/wms/other/common/getOrderSource.sc";

	$.ajax({
		type : 'POST',
		url : url,
		data : {
			'level' : level,
			'sourceNo' : fSelVal
		},
		dataType : 'json',
		success : function(orderSource) {

			for ( var i = 0; i < orderSource.length; i++) {
				var orderSourceObj = orderSource[i];
				var sourceNo = orderSourceObj.key;
				var sourceName = orderSourceObj.value;

				if (sourceNo == selVal) {
					$(
							"<option selected value='" + sourceNo + "'>"
									+ sourceName + "</option>").appendTo(
							$("#" + selId));
				} else {
					$(
							"<option value='" + sourceNo + "'>" + sourceName
									+ "</option>").appendTo($("#" + selId));
				}
			}

		}
	});
}
