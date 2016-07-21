<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,优购网,B网络营销系统,商品管理" />
<meta name="Description" content=" , ,B网络营销系统-商品管理" />
<title>B网络营销系统-会员管理-优购网</title>
</head>
<#include "../../../yitiansystem/yitiansystem-include.ftl">
<body>

<div class="main-body" id="main_body">
	<div class="ytback-tt-1 ytback-tt">
            	<span>您当前的位置：</span>采购管理 &gt; 添加POS仓库与优购仓库对应关系
    		</div>
	<div class="wms-top">
		<form action="" name="form1" id="form1" method="POST">
			<table>
				<tr>
					<td>POS来源编码:</td>
					<td><input type="text" name="posSourceNo" id="posSourceNo"/></td>
				</tr>
				<tr>
					<td>POS仓库编码:</td>
					<td><input type="text" name="posWareHouseCode" id="posWareHouseCode"/></td>
				</tr>
				<tr>
					<td>POS仓库名称：</td>
					<td><input type="text" name="posWareHouseName" id="posWareHouseName"/></td>
				</tr>
				<tr>
					<td>优购仓库编码：</td>
					<td><input type="text" name="ygWareHouseCode" id="ygWareHouseCode"/></td>
				</tr>
				<tr>
					<td>优购仓库名称：</td>
					<td><input type="text" name="ygWareHouseName" id="ygWareHouseName"/></td>
				</tr>
			</table>
			<input class="wms-form-btn-1" type="button" value="添加" onclick="add()"/> 
         	<input class="wms-seach-btn" type="button" value="关闭" onclick="getback('1')"/>
		</form>
	</div>
</div>
</body>
<script type="text/javascript">
function getback(str){
	if(str == "1"){
		$("#posSourceNo").val("");
		$("#posWareHouseCode").val("");
		$("#posWareHouseName").val("");
		$("#ygWareHouseCode").val("");
		$("#ygWareHouseName").val("");
	}
	document.form1.action="${BasePath}/supply/purchase/posygwarehouse/list.sc";
	document.form1.method="POST";
	document.form1.submit();
}


function add(){
	var posSourceNo=$("#posSourceNo").val();
	var posWareHouseCode=$("#posWareHouseCode").val();
	var posWareHouseName=$("#posWareHouseName").val();
	var ygWareHouseCode=$("#ygWareHouseCode").val();
	var ygWareHouseName=$("#ygWareHouseName").val();
	$.post("${BasePath}/supply/purchase/posygwarehouse/add.sc",
	{"posSourceNo" : posSourceNo,"posWareHouseCode":posWareHouseCode,
	"posWareHouseName":posWareHouseName,"ygWareHouseCode":ygWareHouseCode,
	"ygWareHouseName":ygWareHouseName},
		function(data){
			if (data == "1") {
				alert("添加成功！");
				getback("23");
			} else if (data == "2") {
				alert("添加失败,请先查证数据是否正确！");
			} 
			//art.dialog.parent.queryAll()
			//art.dialog.close();
			
		});
}
</script>
</html>
