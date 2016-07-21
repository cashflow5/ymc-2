<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-index.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<title>网络营销系统-系统管理-角色修改</title>
<script type="text/javascript"  src="${BasePath}/js/yitiansystem/systemmgmt/role.js"></script>

<script type="text/javascript">
  	var config={form:"roleForm",submit:submitForm,
 	fields:[
		{name:'roleName',allownull:false,regExp:"name",defaultMsg:'角色名称不能为空',focusMsg:'角色名称不能为空',errorMsg:'角色名称格式不正确',rightMsg:'',msgTip:'roleNameTip'}
	]}
  
  	/**
  	 * 提交表单
  	 */
  	function submitForm(result){
  		return result;
  	}
</script>

</head>
<body>
<div class="container">
	<div class="list_content"> 
        <div class="top clearfix">
			<ul class="tab">
				<li class="curr"><span>角色修改</span></li>
			</ul>
		</div>
		<form action="u_updateRole.sc" method="post"  name="roleForm" id=roleForm style="margin:0px;padding:0p;">
	        <div class="modify"> 
	        	<table class="com_modi_table">
	        		<tr>
						<th>
							<input type="hidden" name="id" value="${role.id}" />
							<span class="star">*</span>
							<label>角色名称：</label>
						</th>
						<td>
							<input name="roleName" id="roleName" value="${role.roleName}" style="length:50;" maxLength="20"/>
	       			  	 	<span id="roleNameTip"></span>
						</td>
					</tr>
					<tr>
						<th>
							<label>备注：</label>
						</th>
						<td>
							<input type="text"  name="remark" value="${role.remark}" style="length:50;" maxLength="100"/>
						</td>
					</tr>
					<tr>
						<th></th>
						<td>
							<input type="submit" class="btn-save" value="保存" />  
							<input type="button" class="btn-back" value="取消" onclick="backList();"/>
						</td>
					</tr>
	        	</table>
	        </div>
        </form>
	</div>
</div>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
</body>
</html>

