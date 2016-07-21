<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<#include "../../../yitiansystem/yitiansystem-include.ftl">
</head>
<body>
<div class="container">
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr"><span>改变状态</span></li>
			</ul>
		</div>
		<!--当前位置end-->
		<div class="modify">
			<form  method="post" id="form1">
				<input type="hidden" name="supplierId" id="supplierId" value="${sp.id}"/>
				<table class="com_modi_table">
					<tbody>
						<tr>
							<th height="63"><span class="star">*</span> 状态： </th>
							<td>
								<#if (sp.isValid) = 1>
									<label><input type="radio" name="state" id="state" checked="checked" value="1" />启用</label>
									<label><input type="radio" name="state" id="state" value="-1"/>停用</label>
								<#elseif sp.isValid = -1>
									<label><input type="radio" name="state" id="state" value="1" />启用</label>
									<label><input type="radio" name="state" id="state" checked="checked" value="-1"/>停用</label>
								<#else>
									<label><input type="radio" name="state" id="state" value="1" />启用</label>
									<label><input type="radio" name="state" id="state" value="-1"/>停用</label>
								</#if>
							</td>
						</tr>
						<tr>
							<th><span class="star">*</span> 备注：</th>
							<td>
								<textarea name="remark" id="remark" cols="35" rows="4"></textarea>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
</div>
</body>
</html>
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script>