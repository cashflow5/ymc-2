<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,优购网,B网络营销系统,栏目管理" />
<meta name="Description" content=" , ,B网络营销系统-栏目管理" />
<title>B网络营销系统-栏目管理-优购网</title>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-index.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<script type="text/javascript"  src="${BasePath}/js/yitiansystem/systemmgmt/add_integral_setting.js"></script>
</head>

<body>
<input id="basePath" value="${BasePath}" type="hidden" name="basePath"/>
<div class="container">
	<div class="list_content"> 
        <div class="top clearfix">
			<ul class="tab">
				<li class="curr"><span>添加积分设置</span></li>
			</ul>
		</div>
		<form action="c_addIntegralSetting.sc"  method="POST" id="integralForm" name="integralForm">
        <div class="modify"> 
        	<table class="com_modi_table">
        		<tr>
					<th>
						<span class="star">*</span>
						<label>序号：</label>
					</th>
					<td>
						<input maxlength="3" name="integralNo" type="text" id="integralNo" size="25" />
						<span id="integralNoMsg"></span>
					</td>
				</tr>
				<tr>
					<th>
						<span class="star">*</span>
						<label>规则名称：</label>
					</th>
					<td>
						<input maxlength="50" name="integralName" type="text" id="integralName" size="25" />
						<span id="integralNameMsg"></span>
					</td>
				</tr>
				<tr>
					<th>
						<span class="star">*</span>
						<label>积分：</label>
					</th>
					<td>
						<input name="integral" maxlength="8" type="text" id="integral" size="25" />
						<span id="integralMsg"></span>
					</td>
				</tr>
				<tr>
					<th>
						<span class="star"></span>
						<label>描述：</label>
					</th>
					<td>
						<textarea name="remark" id="remark" rows="4" cols="30" onpropertychange="checkLength(this,499);" oninput="checkLength(this,499);"></textarea>
					</td>
				</tr>
				<tr>
					<th></th>
					<td>
						<input type="submit" class="btn-add-normal" value="保存" />  
		        		<input type="button" class="btn-add-normal" value="返回" onclick="backToList();"/>
					</td>
				</tr>
        	</table>
        </div>
        </form>
    </div>
</div>
</body>
</html>
<script type="text/javascript">
function checkLength(obj,maxlength){
    if(obj.value.length > maxlength){
        obj.value = obj.value.substring(0,maxlength);
    }
}
</script>
