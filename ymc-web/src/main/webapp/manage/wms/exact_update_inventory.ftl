<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-库存修改</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
<script type="text/javascript" src="${BasePath}/highcharts/js/highcharts.js"></script>
<script type="text/javascript" src="${BasePath}/highcharts/js/modules/exporting.js"></script>
</head>

<body>
	
	
	<div class="main_container">
		
		<div class="normal_box">
		<p class="title site">当前位置：商家中心 &gt; 库存 &gt; 库存修改</p>
		<div class="tab_panel">
			<ul class="tab">
				<li class="curr">
					<span>库存修改</span>
				</li>
			</ul>
			<div class="tab_content">
				<form id="queryForm" name="queryForm" method="post" action="">
					<div class="search_box">
						<p>
							<span>
								<label style="width: 120px;">商家货品条码：</label>
								<input type="text" id="thirdPartyCode" name="thirdPartyCode" class="inputtxt" />
							</span>
							<span>
								<label>
									<a class="button" id="viewer"><span>查看</span></a>
								</label>
							</span>
						</p>
						<div>
							<!--列表start-->
							<table class="list_table">
								<thead>
									<tr>
										<th width="40%">商品名称</th>
										<th width="15%">货品编码</th>
										<th width="20%">商家货品条码</th>
										<th width="10%">规格</th>
										<th width="15%">库存数量</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td colspan="5" align="center">暂无数据</td>
									</tr>
								</tbody>
							</table>
							<!--列表end-->
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	
	
</div>
	
</body>
<script type="text/javascript">
/*****************
 * 文档初始化脚本  *
 *****************/
function updateInventory() {
	if (!/^\d+$/.test($('#quantity').val())) {
		$('#quantity').focus();
		alert('库存数量含有非法字符!');
		return false;
	}
	$.ajax({
		url: '${BasePath}/wms/supplyStockInput/exactUpdateInventory.sc',
		type: 'post',
		dataType: 'html',
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		data: 'commodityCode=' + $('#commodityCode').text() + '&quantity=' + $('#quantity').val() + '&rand=' + Math.random(),
		success: function(data, textStatus) {
			if (data && String.prototype.toString.call(data) == 'true') {
				alert('更新库存成功!');
			} else {
				alert(data);
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert('更新库存失败!');
		}
	});
}

function onInputKeyPress(event) {
	event = window.event || event;
	var keynum = event.keyCode || event.which;
	return (keynum >= 48 && keynum <= 57) || keynum == 8;
}

$(document).ready(function(){
	$('#viewer').click(function(){
		var thirdPartyCode = $('#thirdPartyCode').val();
		$.ajax({
			url: '${BasePath}/wms/supplyStockInput/queryWaitingUpdateInventory.sc?rand=' + Math.random(),
			type: 'post',
			dataType: 'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			data: {thirdPartyCode:thirdPartyCode},
			beforeSend: function(XMLHttpRequest) {
				$('.list_table > tbody').html('<tr><td colspan="5"><font color="#006600">数据载入中...</font></td></tr>');
			},
			success: function(data, textStatus) {
				if (data) {
					var html = '<tr>';
					html += '<td>' + data.goods_name + '</td>';
					html += '<td id="commodityCode">' + data.commodity_code + '</td>';
					html += '<td>' + data.third_party_code + '</td>';
					html += '<td>' + data.specification + '</td>';
					html += '<td valign="middle" style="padding-left: 20px;"><input id="quantity" type="text" value="' + data.inventory_quantity + '" class="inputtxt" style="width: 50px; float: left;" onkeypress="javascript:return onInputKeyPress.call(this, event);"/><a class="button" style="float: left;" onclick="javascript:updateInventory.call(this);"><span>保存</span></a></td>';
					html += '</tr>';
					$('.list_table > tbody').html(html);
				} else {
					if (isEmpty($.trim(thirdPartyCode))) 
						$('.list_table > tbody').html('<tr><td colspan="5"><font color="#ff0000">请输入货品条码. </font></td></tr>');
					else 
						$('.list_table > tbody').html('<tr><td colspan="5"><font color="#ff0000">商家货品条码 ' + thirdPartyCode + '不存在.</font></td></tr>');
				}
			},
			complete: function(XMLHttpRequest, textStatus) {
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				$('.list_table > tbody').html('<tr><td colspan="5"><font color="#ff0000">数据载入异常：ERROR</font></td></tr>');
			}
		});
	});
});

function isEmpty(str) {
	if (str == '' || str == null || str == undefined) {
		return true;
	} else {
		return false;
	}
}
</script>
</html>
