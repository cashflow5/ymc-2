function getPurchase() {
	var purchaseCode = document.getElementById("purchaseCode").value;// 采购单号
	if (purchaseCode == '') {
		alert("请输入采购单号！");
		return;
	}
	$
			.ajax({
				type : "POST",
				url : getPurchaseUrl,
				data : {
					"purchaseCode" : purchaseCode
				},
				dataType : "json",
				success : function(resultMsg) {
					document.getElementById("purchaseCodeSpan").innerHTML = "";
					document.getElementById("purchaseManSpan").innerHTML = "";
					document.getElementById("supplierSpan").innerHTML = "";
					document.getElementById("warehouseNameSpan").innerHTML = "";

					if (resultMsg.success == true) {
						var reObj = resultMsg.reObj;
						var purchaseCode = reObj.purchaseCode;// 采购单号
						var purchaseMan = reObj.purchaseMan;// 采购人
						var supplier = reObj.supplier;// 供应商
						var warehouseName = reObj.warehouseName;// 仓库名称

						document.getElementById("purchaseCodeSpan").innerHTML = purchaseCode;
						document.getElementById("purchaseManSpan").innerHTML = purchaseMan;
						document.getElementById("supplierSpan").innerHTML = supplier;
						document.getElementById("warehouseNameSpan").innerHTML = warehouseName;

						document.getElementById("purchaseCode").readOnly = true;
						document.getElementById("purchaseCode").style.background = "#EEEEEE";

						document.getElementById("getPurchaseBtn").disabled = true;
					} else {
						alert(resultMsg.msg);
					}
				}
			});
}
function changeWarehouse() {
	var purchaseCode = document.getElementById("purchaseCode").value;// 采购单号
	if (purchaseCode == '') {
		alert("请输入采购单号！");
		return;
	}

	var changeWarehouseCode = document.getElementById("changeWarehouseCode").value;// 待更换仓库

	if (changeWarehouseCode == '') {
		alert("请先选择已待更换仓库");
		return;
	}

	$
			.ajax({
				type : "POST",
				url : getPurchaseUrl,
				data : {
					"purchaseCode" : purchaseCode
				},
				dataType : "json",
				success : function(resultMsg) {
					document.getElementById("purchaseCodeSpan").innerHTML = "";
					document.getElementById("purchaseManSpan").innerHTML = "";
					document.getElementById("supplierSpan").innerHTML = "";
					document.getElementById("warehouseNameSpan").innerHTML = "";

					if (resultMsg.success == true) {
						var reObj = resultMsg.reObj;
						var purchaseCode = reObj.purchaseCode;// 采购单号
						var purchaseMan = reObj.purchaseMan;// 采购人
						var supplier = reObj.supplier;// 供应商
						var warehouseName = reObj.warehouseName;// 仓库名称

						document.getElementById("purchaseCodeSpan").innerHTML = purchaseCode;
						document.getElementById("purchaseManSpan").innerHTML = purchaseMan;
						document.getElementById("supplierSpan").innerHTML = supplier;
						document.getElementById("warehouseNameSpan").innerHTML = warehouseName;

						document.getElementById("purchaseCode").readOnly = true;
						document.getElementById("purchaseCode").style.background = "#EEEEEE";

						document.getElementById("getPurchaseBtn").disabled = true;

						doChangeWarehouse(purchaseCode, changeWarehouseCode);

					} else {
						alert(resultMsg.msg);
					}
				}
			});
}

function doChangeWarehouse(purchaseCode, changeWarehouseCode) {
	$.ajax({
		type : "POST",
		url : checkChangeWarehouseUrl,
		data : {
			"purchaseCode" : purchaseCode,
			"changeWarehouseCode" : changeWarehouseCode
		},
		dataType : "json",
		success : function(resultMsg) {
			if (resultMsg.success == true) {
				if (!confirm(resultMsg.msg)) {
					return;
				}
				 $.ajax({
					type : "POST",
					url : changeWarehouseUrl,
					data : {
						"purchaseCode" : purchaseCode,
						"changeWarehouseCode" : changeWarehouseCode
					},
					dataType : "json",
					success : function(resultMsg) {
						if (resultMsg.success == true) {
							alert(resultMsg.msg);
							location.replace(reloadPageUrl);
						} else {
							alert(resultMsg.msg);
						}
					}
				});
			} else {
				alert(resultMsg.msg);
			}
		}
	});

}