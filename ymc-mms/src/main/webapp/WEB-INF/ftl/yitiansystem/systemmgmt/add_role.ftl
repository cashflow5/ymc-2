<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-index.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<script type="text/javascript"  src="${BasePath}/js/yitiansystem/systemmgmt/role.js"></script>
<br/><br/>

<script type="text/javascript">
  
</script>
<div class="container">
	<div class="list_content"> 
        <div class="top clearfix">
			<ul class="tab">
				<li class="curr"><span>角色基本信息</span></li>
			</ul>
		</div>
		 <form action="c_addRole.sc" name="roleForm" method="post" style="margin:0;padding:0;" >
	        <div class="modify"> 
	        	<table class="com_modi_table">
	        		<tr>
						<th>
							<span class="star">*</span>
							<label>角色名称：</label>
						</th>
						<td>
							<input name="roleName" type="text" id="roleName" size="30"  maxLength="20"/>&nbsp;&nbsp;
	   						<span id="roleNameTip"></span>
						</td>
					</tr>
					<tr>
						<th>
							<label>备注：</label>
						</th>
						<td>
							<textarea rows="8" cols="27" style="length:80;" maxLength="100" name="remark"></textarea>
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
