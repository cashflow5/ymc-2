<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>

<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/jquery-1.3.2.min.js"></script>
<script type="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/js.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/order.js"></script>
<title>优购商城--商家后台</title>
<!-- 日期 -->
<script src="${BasePath}/js/common/form/datepicker/WdatePicker.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
</script>
<body>
<div class="container">
	<!--工具栏start--> 
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li  class="curr">
				  <span><a href="#" class="btn-onselc">商家权限组列表</a></span>
				</li>
			</ul>
		</div>
 <div class="modify"> 
     <form action="${BasePath}/yitiansystem/merchants/businessorder/add_distributeRole.sc" name="queryForm" id="queryForm" method="post"> 
     			<input type="hidden" id="userId" name="userId" value="<#if userId??>${userId}</#if>">
     			<input type="hidden" id="merchantCode" name="merchantCode" value="<#if merchantCode??>${merchantCode}</#if>">
              	<div style="width:100%; height:390px; overflow:auto;">
                <table cellpadding="0" cellspacing="0" class="list_table">
                		<thead>
	                          <#if merchantsRoles??>
	                             <#list merchantsRoles as item>
		                		     <tr>
				                          <td><input type="checkbox" value="${item.id!''}"  name="role" id="role" <#if item.status??&&(item.status=='5')>checked</#if>>${item.roleName!""}</td>
				                     </tr>
	                             </#list>
	                        <#else>
				   				<span style="color:red;">商家登录帐号为空,请先添加商家登录帐号！</span>
	                         </#if>
                        </thead>
                </table>
                </div>
                </form>
              </div>
              <div class="blank20"><input type="button" value="提交" onclick="addRoleAuthrity();" class="yt-seach-btn" /></div>
          </div>
</div>
</body>
</html>
<script type="text/javascript">
//给角色分配资源
function addRoleAuthrity(){
   document.queryForm.method="post";
   document.queryForm.submit();
}
</script>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
