<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,优购网,B网络营销系统,商品管理" />
<meta name="Description" content=" , ,B网络营销系统-商品管理" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<title>B网络营销系统-采购管理-优购网</title>
</head>
<body>
<script>
function updatePos() {
	var b = document.getElementById("button1");
	b.disabled=true;
	var tempParam = Math.random();
	$.post("${BasePath}/supply/purchase/manualupdates/update.sc",{"a":tempParam},function(str){
		if(str == '1'){
			alert("更新成功");
		}else{
			alert("更新失败");
		}
		document.getElementById("button1").disabled=false;
	});
}
</script>
<div class="container">
	<input type="button" onclick="updatePos();" id="button1" value="同步"/>
</div>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 

</body>
</html>

