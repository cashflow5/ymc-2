<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-修改商家发货地址信息</title>
<link rel="stylesheet" type="text/css"
	href="${BasePath}/yougou/css/base.css" />
<link rel="stylesheet" type="text/css"
	href="${BasePath}/yougou/css/mold.css?${style_v}" />
<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
</head>
<body>


	<div class="main_container">
		<div class="normal_box">
			<p class="title site">当前位置：商家中心 &gt; 设置 &gt; 修改商家发货地址信息</p>
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
						<input type="hidden" id="id" name="id"
							value="<#if consignmentAdress??&&consignmentAdress.id??>${consignmentAdress.id!''}</#if>">
							<input type="hidden" id="hometown" name="hometown">
								<div class="form_container">
									<table class="form_table">
										<tbody>
											<tr>
												<th><span style="color: red;">*</span> 发货人姓名：</th>
												<td><input type="text" id="consignmentName"
													name="consignmentName"
													value="<#if consignmentAdress??&&consignmentAdress.consignmentName??>${consignmentAdress.consignmentName!''}</#if>"
													class="ginput" /> &nbsp;&nbsp; <span style="color: red;"
													id="consignmentNameError"></span></td>
											</tr>
											<tr>
												<th><span style="color: red;">*</span> 发货人手机：</th>
												<td><input type="text" Maxlength=11 id="phone"
													name="phone"
													value="<#if consignmentAdress??&&consignmentAdress.phone??>${consignmentAdress.phone!''}</#if>"
													class="ginput" /> &nbsp;&nbsp; <span style="color: red;"
													id="phoneError"></span></td>
											</tr>
											<tr>
												<th><span style="color: red;">*</span> 发货人电话：</th>
												<td><input type="text" id="tell" name="tell"
													value="<#if consignmentAdress??&&consignmentAdress.tell??>${consignmentAdress.tell!''}</#if>"
													class="ginput" /> &nbsp;&nbsp; <span style="color: red;"
													id="tellError"></span></td>
											</tr>
											<tr>
												<th>发货人邮编：</th>
												<td><input type="text" id="postCode" name="postCode"
													value="<#if consignmentAdress??&&consignmentAdress.postCode??>${consignmentAdress.postCode!''}</#if>"
													class="ginput" /> &nbsp;&nbsp;</td>
											</tr>
											<tr>
												<th><span style="color: red;">*</span> 发货人地区：</th>
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
												<th><span style="color: red;">*</span> 发货人地址：</th>
												<td><textarea id="adress" name="adress" cols="40"
														rows="3" class="areatxt"><#if consignmentAdress??&&consignmentAdress.adress??>${consignmentAdress.adress!''}</#if></textarea>
													<span style="color: red;" id="adressError"></span></td>
											</tr>
											<tr>
												<th></th>
												<td style="padding-top: 20px;"><a
													style="margin-left: 0;"
													onclick="return updateConsignmentAdress();" class="button"><span>保存</span>
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
	/**限制手机号码只能为输入数字
	 $("input[name='phone']").keydown(function(event){
	 var code = event.which;
	 if(code==10||code==8){
	 return true;
	 }else if(code >47 && code < 58) {
	 return true;
	 }else if(code >95 && code < 106){
	 return true;
	 }else{
	 return false;
	 }
	 return true;
	 });
	 **/

	$(window).ready(function() {
		//定义默认数据
		var ar = [ "请选择省份", "请选择城市", "请选择区县" ];
		//初始化
		$("<option value='-1'>" + ar[1] + "</option>").appendTo($("#city"));
		$("<option value='-1'>" + ar[2] + "</option>").appendTo($("#area"));
		$("#city").reJqSelect();
		$("#area").reJqSelect();

		var id = $("#id").val();
		if (id != "") {
			checkTwo(id);
			checkThree(id);
		}
	});
	//选择省
	function checkTwo(id) {
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
						url : "${BasePath}/merchants/login/queryChildById.sc?level=2&no="
								+ province + "&id=" + id,
						success : function(result) {
							if (result != "") {
								$("#city").empty();
								result = result.replace(/(^\s*)|(\s*$)/g, '');
								var node = eval("(" + result + ")");
								for (i = 0; i < node.length; i++) {
									var hometown = node[i];
									var no = hometown.no;
									var name = hometown.name;
									var nameStr = hometown.nameStr;
									if (name == nameStr) {
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
	function checkThree(id) {
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
						url : "${BasePath}/merchants/login/queryChildById.sc?level=3&no="
								+ city + "&id=" + id,
						success : function(result) {
							if (result != "") {
								$("#area").empty();
								result = result.replace(/(^\s*)|(\s*$)/g, '');
								var node = eval("(" + result + ")");
								for (i = 0; i < node.length; i++) {
									var hometown = node[i];
									var no = hometown.no;
									var name = hometown.name;
									var nameStr = hometown.nameStr;
									if (name == nameStr) {
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

	//修改发货地址信息
	function updateConsignmentAdress() {
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