<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<#include "../merchants-include.ftl">
	<script>
		var taobaoYougouBrandBind = {};
		taobaoYougouBrandBind.basePath = "${BasePath}";
	</script>
	<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>
	<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script>
	<script type="text/javascript" src="${BasePath}/js/mms.common.js?${version}"></script>
	<script type="text/javascript" src="${BasePath}/js/yitiansystem/merchants/taobao/yougou.taobao.brandbind.js?${version}"></script>
	<style type="text/css">
	.form-table{width:70%;}
	.form-table-tr{background: #F2F2F2}
	.form-table th{font-weight: 600}
	.form-table td,.form-table th{border:1px solid #DFDFDF;padding: 8px 10px;text-align: center;}
	.form-table th.op a{color:#0066FF;font-weight:normal;text-decoration: underline;}
	.form-table td .brand i,.form-table td.op i{background:url(${BasePath}/images/del-class.gif);background-repeat: no-repeat;background-position: 0px 2px;display:inline-block;width: 9px;height: 11px;margin-left: 5px;vertical-align: middle}
	.form-table td .brand{display: none;}
	.form-table td .brand input{border:1px solid #DFDFDF;height:25px;width:200px;}
	.form-table a.addBtn{text-decoration: none;display: inline-block;background:#278296;padding:5px 10px;color:#fff;}
	</style>
	<title>优购商城--商家后台</title>
</head>
<body>
	<div class="continer">
		<div class="list_content">
			<div class="top clearfix">
				<ul class="tab">
					<li  class="curr">
						<span>
							<a href="#" class="btn-onselc">品牌对应</a>
						</span>
					</li>
				</ul>
			</div>
			<div class="modify">
				<form action="" id="bindForm" name="queryVoform" method="post">
					<table class="form-table">
						<tr class="form-table-tr" style="height:40px;">
							<th width="45%">优购品牌</th>
							<th width="45%">淘宝品牌</th>
							<th class="op" width="10%"><a href="javascript:taobaoYougouBrandBind.addLine()">增加一行</a></th>
						</tr>
						<tr num="1">
							<td><div class='brand'><input type="text" name="yougouBrandName" readonly="true"><input type="hidden" name="yougouBrandNo"><a href="javascript:void(0)" class="removeYougouBrand"><i></i></a></div><a href="javascript:void(0)" class="addBtn addYougouBrand">添加优购品牌</a></td>
							<td><div class='brand'><input type="text" readonly="true" name="taobaoBrandName"><input type="hidden" name="taobaoBrandNo"><a href="javascript:void(0)" class="removeTaobaoBrand"><i></i></a></div><a href="javascript:void(0)" class="addBtn addTaobaoBrand">添加淘宝品牌</a></td>
							<td class="op"></td>
						</tr>
					</table>
					<div style="margin-top:10px;text-align:center;width:70%;"><input type="button" value="保存" onclick="taobaoYougouBrandBind.saveBind()" class="yt-seach-btn" /><input type="button" value="返回" onclick="history.back(-1)" class="yt-seach-btn" /></div>
				</form>
			</div>
		</div>
</body>
</html>