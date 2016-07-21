<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-添加商家发货地址信息</title>
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
			<p class="title site">当前位置：商家中心 &gt; 设置 &gt; 添加商家发货地址信息</p>
			<div class="tab_panel">
				<ul class="tab">
					<li class="curr"><span>发货地址</span>
					</li>
					<li onclick="location.href='/merchants/login/toMerchantRejectedAddressList.sc'"><span>退货地址</span>
					</li>
				</ul>
				<div class="tab_content">
					<!--列表start-->
					<form id="queryForm" name="queryForm"
						action="${BasePath}/merchants/login/update_merchant_consignmentAdress.sc"
						method="post">
						<input type="hidden" id="hometown" name="hometown">
							<div class="form_container">
								<table class="form_table">
									<tbody>
										<tr>
											<th><span style="color: red;">*</span> 发货人姓名：</th>
											<td><input type="text" id="consignmentName"
												name="consignmentName" class="ginput" /> &nbsp;&nbsp; <span
												style="color: red;" id="consignmentNameError"></span></td>
										</tr>
										<tr>
											<th><span style="color: red;">*</span> 发货人手机：</th>
											<td><input type="text" Maxlength=11 id="phone"
												name="phone" class="ginput" /> &nbsp;&nbsp; <span
												style="color: red;" id="phoneError"></span></td>
										</tr>
										<tr>
											<th><span style="color: red;">*</span> 发货人电话：</th>
											<td><input type="text" id="tell" name="tell"
												class="ginput" /> &nbsp;&nbsp; <span style="color: red;"
												id="tellError"></span></td>
										</tr>
										<tr>
											<th>发货人邮编：</th>
											<td><input type="text" id="postCode" name="postCode"
												class="ginput" /> &nbsp;&nbsp;</td>
										</tr>
										<tr>
											<th><span style="color: red;">*</span> 发货人地区：</th>
											<td><select class="g-select" id="province"
												onchange="checkTwo();" onclick="checkTwo();" name="province"
												style="width: 85px"> <#if areaList??>
													<option value="-1">请选择省份</option> <#list areaList as item>
													<option value="${item['no']!''}">${item['name']!''}</option>
													</#list> </#if>
											</select> <select class="g-select" id="city" onchange="checkThree();"
												onclick="checkThree();" name="city" style="width: 85px"></select>
												<select class="g-select" id="area" name="area"
												style="width: 85px"></select> <span style="color: red;"
												id="provinceError"></span></td>
										</tr>

										<span style="color: red;" id="areaError"></span>
										</td>
										</tr>
										<tr>
											<th><span style="color: red;">*</span> 发货人地址：</th>
											<td><textarea id="adress" name="adress" cols="40"
													rows="3" class="areatxt"></textarea> <span
												style="color: red;" id="adressError"></span></td>
										</tr>
										<tr>
											<th></th>
											<td style="padding-top: 20px;"><a
												style="margin-left: 0;"
												onclick="return saveConsignmentAdress();" class="button"><span>保存</span>
											</a></td>
										</tr>
									</tbody>
								</table>
							</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</html>
<script type="text/javascript">
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

	//修改发货地址信息
	function saveConsignmentAdress() {
		var consignmentName = $("#consignmentName").val();//发货人姓名
		var phone = $("#phone").val();//发货人手机
		var tell = $("#tell").val();//发货人电话
		var area = $("#area").val();//发货人地区
		var adress = $("#adress").val();//发货人地址
		var province = $("#province").val();//发货人省
		var city = $("#city").val();//发货人市
		var area = $("#area").val();//发货人区

		if (consignmentName == "") {
			$("#consignmentNameError").html("发货人姓名不能为空!");
			return false;
		} else {
			$("#consignmentNameError").html("");
		}
		if (phone == "" && tell == "") {
			$("#phoneError").html("发货人手机和电话至少一个不为空!");
			return false;
		} else {
			$("#phoneError").html("");
		}
		if (adress == "") {
			$("#adressError").html("发货人地址不能为空!");
			return false;
		} else {
			$("#adressError").html("");
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
		if (adress.trim() == "" || adress.trim() == " ") {
			$("#adressError").html("地址不能为空!");
			return false;
		} else {
			$("#adressError").html("");
		}
		var provinceText = $("#province").find("option:selected").text();//发货人省
		var cityText = $("#city").find("option:selected").text();//发货人市
		var areaText = $("#area").find("option:selected").text();//发货人区
		var prh = provinceText + "-" + cityText + "-" + areaText;//拼接省市区
		$("#hometown").val(prh);
		//提交表单数据
		document.queryForm.submit();
	}
</script>