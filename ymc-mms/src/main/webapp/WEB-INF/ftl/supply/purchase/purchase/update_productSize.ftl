<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,优购网,B网络营销系统,采购管理" />
<meta name="Description" content=" , ,B网络营销系统-采购管理" />
<script src="${BasePath}/js/supply/supplier.js" type="text/javascript"></script>
<#include "../../../yitiansystem/yitiansystem-include.ftl">
<title>B网络营销系统-采购管理-优购网</title>
</head><body>
<div class="container">
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class='curr'> <span>修改商品尺码</span> </li>
			</ul>
		</div>
		<div class="modify">
			<form action="u_updateProductSize.sc" method="post" id="saveProductSizeForm" name="saveProductSizeForm">
				<input type="hidden" id="id" name="id" value="${productSize.id}">
				<table class="com_modi_table">
					<tbody>
					<tr>
						<th><span class="star"> * </span>
								<label> 尺码类型： </label></th>
						<td>
							<select id="sizetypeId" name="sizetypeId" style="width:120px;">
								<option value="">请选择</option>
								<#list sizetypes as type> <#if productSize??&&productSize.sizetypeId??&&productSize.sizetypeId==type.id>
								<option selected value="${type.id}">${type.name}</option>
								<#else>
								<option value="${type.id}">${type.name}</option>
								</#if> </#list>
							</select>
						</td>
						<td> <span id="sizetypeIdTip"></span> </td>
						</tr>
						<tr>
						<th> <span class="star"> * </span>
								<label> 尺码： </label></th>
						<td>
							<input type="text" id="size" name="size" value="${productSize.size}"/>
						</td>
						<td> <span id="sizeTip"></span> </td>
					</tr>
					<tr>
						<th>
							
						</th>
						<td><input id="submitbutton" type="submit" class="btn-save" value="保存" style="margin-top:0px;" /></td>
						<td></td>
					</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
</div>
</body>
</html>
<script type="text/javascript">
	//非空验证
   var config={
		  		form:"saveProductSizeForm",
		  		submit:submitForm,
			 	fields:[
					{name:'sizetypeId',allownull:false,regExp:/^\S+$/,defaultMsg:'请选择尺码类型',focusMsg:'请选择尺码类型',errorMsg:'尺码类型不能为空!',rightMsg:'尺码类型选择正确',msgTip:'sizetypeIdTip'},
					{name:'size',allownull:false,regExp:/^\S+$/,defaultMsg:'请输入尺码',focusMsg:'请输入尺码',errorMsg:'尺码不能为空',rightMsg:'尺码输入正确',msgTip:'sizeTip'}
				]
			}

	Tool.onReady(function(){
		var f = new Fv(config);
		f.register();
	});
	
	function submitForm(){
  		return true;
		
  	}
</script>