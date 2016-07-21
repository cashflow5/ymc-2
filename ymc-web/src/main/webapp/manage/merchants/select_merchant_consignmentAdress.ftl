<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-查看商家发货地址信息</title>
<link rel="stylesheet" type="text/css"
	href="${BasePath}/yougou/css/base.css" />
<link rel="stylesheet" type="text/css"
	href="${BasePath}/yougou/css/mold.css?${style_v}" />
<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
</head>
<body>
	<div class="main_container">
		<div class="normal_box">
			<p class="title site">当前位置：商家中心 &gt; 设置 &gt;查看商家发货地址信息</p>
			<div class="tab_panel">
				<ul class="tab">
					<li class="curr"><span>发货地址</span>
					</li>
					<li onclick="location.href='/merchants/login/toMerchantRejectedAddressList.sc'"><span>退货地址</span>
					</li>
				</ul>
				<div class="tab_content">
					<!--列表start-->
					<form id="queryForm" name="queryForm" method="post">
						<div class="form_container">
							<table class="form_table">
								<tbody>
									<tr>
										<th><span style="color: red;">*</span> 发货人姓名：</th>
										<td><#if
											consignmentAdress??&&consignmentAdress.consignmentName??>${consignmentAdress.consignmentName!''}</#if>
										</td>
									</tr>
									<tr>
										<th><span style="color: red;">*</span> 发货人手机：</th>
										<td><#if
											consignmentAdress??&&consignmentAdress.phone??>${consignmentAdress.phone!''}</#if>
										</td>
									</tr>
									<tr>
										<th><span style="color: red;">*</span> 发货人电话：</th>
										<td><#if
											consignmentAdress??&&consignmentAdress.tell??>${consignmentAdress.tell!''}</#if>
											<span style="color: red;" id="tellError"></span></td>
									</tr>
									<tr>
										<th>发货人邮编：</th>
										<td><#if
											consignmentAdress??&&consignmentAdress.postCode??>${consignmentAdress.postCode!''}</#if>
										</td>
									</tr>
									<tr>
										<th><span style="color: red;">*</span> 发货人地区：</th>
										<td><#if
											consignmentAdress??&&consignmentAdress.hometown??>${consignmentAdress.hometown!''}</#if>
										</td>
									</tr>
									<tr>
										<th><span style="color: red;">*</span> 发货人地址：</th>
										<td><#if
											consignmentAdress??&&consignmentAdress.adress??>${consignmentAdress.adress!''}</#if>
										</td>
									</tr>
									<tr>
										<th></th>
										<td style="padding-top: 0px;"><a style="margin-left: 0;"
											onclick="updateConsignmentAdress();" class="button"><span>修改</span>
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
</body>
</html>
<script type="text/javascript">
	//跳到到修改商家发货地址页面
	function updateConsignmentAdress() {
		location.href = "${BasePath}/merchants/login/to_merchant_consignmentAdress_list.sc?flag=1";
	}
</script>