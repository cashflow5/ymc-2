<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-添加商家收货地址信息</title>
<link rel="stylesheet" type="text/css"
	href="${BasePath}/yougou/css/base.css" />
<link rel="stylesheet" type="text/css"
	href="${BasePath}/yougou/css/mold.css?${style_v}" />
<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
<script type="text/javascript"
	src="${BasePath}/yougou/js/provinceCascade.js"></script>
</head>
<body>
	<div class="main_container">
		<div class="normal_box">
			<p class="title site">当前位置：商家中心 &gt; 设置 &gt; 添加商家退货地址信息</p>
			<div class="tab_panel">
				<ul class="tab">
					<li onclick="location.href='/merchants/login/to_merchant_consignmentAdress_list.sc'"><span>发货地址</span>
					</li>
					<li class="curr"><span>退货地址</span>
					</li>
				</ul>
				<div class="tab_content">
					<div id="edit" style="display:${edit_display!''}">
						<form id="queryForm" name="queryForm" action="${BasePath}/merchants/login/saveMerchantRejectedAddress.sc" method="post">
							<input type="hidden" id="id" name="id" value="${(rejectedAddress.id)!''}">
							<input type="hidden" id="supplierName" name="supplierName" value="${(rejectedAddress.supplierName)!''}">
							<input type="hidden" id="supplierCode" name="supplierCode" value="${(rejectedAddress.supplierCode)!''}">
							<input type="hidden" id="warehouseArea" name="warehouseArea" value="${(rejectedAddress.warehouseArea)!''}" />
							<div class="form_container">
								<table class="form_table">
									<tbody>
										<tr>
											<th><span style="color: red;">*</span> 收货人姓名：</th>
											<td><input type="text" id="consigneeName" name="consigneeName" value="${(rejectedAddress.consigneeName)!''}"
												class="ginput" /> &nbsp;&nbsp; <span style="color: red;"
												id="consigneeNameError"></span></td>
										</tr>
										<tr>
											<th><span style="color: red;">*</span> 收货人电话：</th>
											<td><input type="text" Maxlength=11 id="consigneePhone"
												name="consigneePhone"
												value="${(rejectedAddress.consigneePhone)!''}"
												class="ginput" /> &nbsp;&nbsp; <span style="color: red;"
												id="consigneePhoneError"></span></td>
										</tr>
										<tr>
											<th><span style="color: red;">*</span> 优购客服电话：</th>
											<td><input type="text" id="consigneeTell" name="consigneeTell"
												value="${(rejectedAddress.consigneeTell)!''}"
												class="ginput" /> &nbsp;&nbsp; <span style="color: red;"
												id="consigneeTellError"></span></td>
										</tr>
										<tr>
											<th>收货人邮编：</th>
											<td><input type="text" id="warehousePostcode" name="warehousePostcode"
												value="${(rejectedAddress.warehousePostcode)!''}"
												class="ginput" /> &nbsp;&nbsp;</td>
										</tr>
										<tr>
											<th><span style="color: red;">*</span> 收货人地区：</th>
											<td><select class="g-select" id="province"
												onchange="checkTwo();" onclick="checkTwo();"
												name="province" style="width: 85px"> <#if
													areaList??>
													<option value="-1">请选择省份</option> <#list areaList as item>
													<option value="${item['no']!''}"<#if
														item['name']??&&province??&&item['name']==province>selected</#if>>${item['name']!''}</option>
													</#list> </#if>
											</select> <select class="g-select" id="city" onchange="checkThree();"
												onclick="checkThree();" name="city" style="width: 85px"></select>
												<select class="g-select" id="area" name="area"
												style="width: 85px"></select> <span style="color: red;"
												id="provinceError"></span></td>
										</tr>
										<tr>
											<th><span style="color: red;">*</span> 详细地址：</th>
											<td><textarea id="warehouseAdress" name="warehouseAdress" cols="40"
													rows="3" class="areatxt">${(rejectedAddress.warehouseAdress)!''}</textarea>
												<span style="color: red;" id="warehouseAdressError"></span></td>
										</tr>
										<tr>
											<th></th>
											<td style="padding-top: 20px;"><a
												style="margin-left: 0;"
												onclick="return saveRejectedAddress();" class="button"><span>保存</span>
											</a></td>
										</tr>
									</tbody>
								</table>
							</div>
						</form>
					</div>
					<div id="unedit" style="display:${unedit_display!''}">
					<div class="form_container">
						<table class="form_table">
							<tbody>
								<tr>
									<th><span style="color: red;">*</span> 收货人姓名：</th>
									<td>${(rejectedAddress.consigneeName)!''}</td>
								</tr>
								<tr>
									<th><span style="color: red;">*</span> 收货人手机：</th>
									<td>${(rejectedAddress.consigneePhone)!''}</td>
								</tr>
								<tr>
									<th><span style="color: red;">*</span> 收货人电话：</th>
									<td>${(rejectedAddress.consigneeTell)!''}</td>
								</tr>
								<tr>
									<th><span style="color: red;">*</span> 收货人邮编：</th>
									<td>${(rejectedAddress.warehousePostcode)!''}</td>
								</tr>
								<tr>
									<th><span style="color: red;">*</span> 收货人地区：</th>
									<td>${(rejectedAddress.warehouseArea)!''}</td>
								</tr>
								<tr>
									<th><span style="color: red;">*</span> 详细地址：</th>
									<td>${(rejectedAddress.warehouseAdress)!''}</td>
								</tr>
								<tr>
									<th></th>
									<td style="padding-top: 0px;"><a style="margin-left: 0;"
										onclick="updateRejectedAddress();" class="button"><span>修改</span>
									</a></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</html>
<script type="text/javascript">
	var nameStr;
	//限制手机号码只能为输入数字
	$("input[name='phone']").keydown(function(event) {
		var code = event.which;
		if (code == 10 || code == 8) {
			return true;
		} else if (code > 47 && code < 58) {
			return true;
		} else if (code > 95 && code < 106) {
			return true;
		} else {
			return false;
		}
		return true;
	});

	$(window).ready(function() {
		//定义默认数据
		var ar = [ "请选择省份", "请选择城市", "请选择区县" ];
		//初始化
		$("<option value='-1'>" + ar[1] + "</option>").appendTo($("#city"));
		$("<option value='-1'>" + ar[2] + "</option>").appendTo($("#area"));
		$("#city").reJqSelect();
		$("#area").reJqSelect();

	if($("#warehouseArea").val() != null && $("#warehouseArea").val() != ""){
		nameStr = $("#warehouseArea").val().split("-");
	}
		if (nameStr != "") {
			checkTwo();
			checkThree();
		}
	});
	//选择省
	function checkTwo() {
		var province = $("#province").val();
		if (province != "") {
			if (province == "-1") {
				$("#city").empty();
				$("<option value='-1'>请选择城市</option>").appendTo($("#city"));
				$("#city").reJqSelect();

				$("#area").empty();
				$("<option value='-1'>请选择区县</option>").appendTo($("#area"));
				$("#area").reJqSelect();
				return;
			}
			//根据省信息 加载市信息
			$
					.ajax({
						type : "post",
						async : false,
						url : "${BasePath}/merchants/login/queryChildById.sc?level=2&no=" + province,
						success : function(result) {
							if (result != "") {
								$("#city").empty();
								result = result.replace(/(^\s*)|(\s*$)/g, '');
								var node = eval("(" + result + ")");
								for (i = 0; i < node.length; i++) {
									var hometown = node[i];
									var no = hometown.no;
									var name = hometown.name;
//									var nameStr = hometown.nameStr;
									if (nameStr != null && nameStr.length > 1 && name == nameStr[1]) {
										$(
												"<option value='"+no+"' selected>"
														+ name + "</option>")
												.appendTo($("#city"));
									} else {
										$(
												"<option value='"+no+"'>"
														+ name + "</option>")
												.appendTo($("#city"));
									}
								}
								$("#city").reJqSelect();

								$("#area").empty();
								$("<option value='-1'>请选择区县</option>")
										.appendTo($("#area"));
								$("#area").reJqSelect();
							}
						}
					});
		}
	}
	//选择市
	function checkThree(my_area) {
		var city = $("#city").val();
		if (city != "") {
			if (city == "-1") {
				$("#area").empty();
				$("<option value='-1'>请选择区县</option>").appendTo($("#area"));
				$("#area").reJqSelect();
				return;
			}
			//根据省信息 加载市信息
			$
					.ajax({
						type : "post",
						url : "${BasePath}/merchants/login/queryChildById.sc?level=3&no=" + city,
						success : function(result) {
							if (result != "") {
								$("#area").empty();
								result = result.replace(/(^\s*)|(\s*$)/g, '');
								var node = eval("(" + result + ")");
								for (i = 0; i < node.length; i++) {
									var hometown = node[i];
									var no = hometown.no;
									var name = hometown.name;
//									var nameStr = hometown.nameStr;
									if (nameStr != null && nameStr.length > 2 && name == nameStr[2]) {
										$(
												"<option value='"+no+"' selected>"
														+ name + "</option>")
												.appendTo($("#area"));
									} else {
										$(
												"<option value='"+no+"'>"
														+ name + "</option>")
												.appendTo($("#area"));
									}
								}
								$("#area").reJqSelect();
							}
						}
					});
		}
	}
	function updateRejectedAddress(){
		document.getElementById("edit").style.display="";
		document.getElementById("unedit").style.display="none";
	}
	//修改收货地址信息
	function saveRejectedAddress() {
		var consigneeName = $("#consigneeName").val();//收货人姓名
		var consigneePhone = $("#consigneePhone").val();//收货人手机
		var consigneeTell = $("#consigneeTell").val();//收货人电话
		var warehouseArea = $("#warehouseArea").val();//收货人地区
		var warehouseAdress = $("#warehouseAdress").val();//收货人地址
		var province = $("#province").val();//收货人省
		var city = $("#city").val();//收货人市
		var area = $("#area").val();//收货人区

		if (consigneeName == "") {
			$("#consigneeNameError").html("收货人姓名不能为空!");
			return false;
		} else {
			$("#consigneeNameError").html("");
		}
		if (consigneePhone == "" && consigneePhone == "") {
			$("#consigneePhoneError").html("收货人手机不为空!");
			return false;
		} else {
			$("#consigneePhoneError").html("");
		}
		if (consigneeTell == "" && consigneeTell == "") {
			$("#consigneeTellError").html("收货人电话不为空!");
			return false;
		} else {
			$("#consigneeTellError").html("");
		}
		if (province == "-1" || province == "") {
			$("#provinceError").html("省份不能为空!");
			return false;
		} else {
			$("#provinceError").html("");
		}
		if (city == "-1" || city == "") {
			$("#provinceError").html("城市不能为空!");
			return false;
		} else {
			$("#provinceError").html("");
		}
		if (area == "-1" || area == "") {
			$("#provinceError").html("地区不能为空!");
			return false;
		} else {
			$("#provinceError").html("");
		}
		if (warehouseAdress.trim() == "" || warehouseAdress.trim() == " ") {
			$("#warehouseAdressError").html("地址不能为空!");
			return false;
		} else {
			$("#warehouseAdressError").html("");
		}
		var provinceText = $("#province").find("option:selected").text();//收货人省
		var cityText = $("#city").find("option:selected").text();//收货人市
		var areaText = $("#area").find("option:selected").text();//收货人区
		var prh = provinceText + "-" + cityText + "-" + areaText;//拼接省市区
		$("#warehouseArea").val(prh);
		//提交表单数据
		document.queryForm.submit();
	}
</script>