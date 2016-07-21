<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<#include "../orderCss.ftl">
<script type="text/javascript">
	//修改密码
	function updatePassword(id){
		var param = "id="+id;
		openwindow("../../systemmgmt/systemuser/toUpdateSystemUserPassword.sc?id="+id,450,250,"密码修改");
	}
	/**
	 *到用户角色分配
	 * @param id
	 */
	function allotUserRoles(id){
		var params = "id="+id;
		openwindow("../../systemmgmt/systemuser/toAllotUserRole.sc?id="+id,520,650,"分配角色");
	}
</script>
</head>
<body>
<#assign systemUser=login_system_user?default('') >
<div class="container">
	<div class="toolbar">
		<div class="t-content">
			<div class="btn">
				<span class="btn_l"></span>
	        	<b class="ico_btn add"></b>
	        	<span class="btn_txt"><a href="../../systemmgmt/systemuser/toAddSystemUser.sc" target="mbdif">增加</a></span>
	        	<span class="btn_r"></span>
        	</div> 
		</div>
	</div>
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class='curr' ><span>系统用户列表</span></li>
			</ul>
		</div>
		<div class="modify">
			<div class="add_detail_box">
				<form method="post" action="querySystemUserList.sc" name="queryForm" id="queryForm"  >
					<span>真实姓名：</span>
					<input type="text" name="username" value="${username?default('')}"/>
					<span>登录用户名：</span>
					<input type="text" name="loginName" value="${loginName?default('')}"/>
					<span>角色名称：</span>
					<input type="text" name="roleName" value="${roleName?default('')}"/>
					<span>菜单名称：</span>
					<input type="text" name="menuName" value="${menuName?default('')}"/>
					
					<input type="submit" value="查询" class="btn-add-normal"/>
				</form>
			</div>
			<table cellpadding="0" cellspacing="0" class="list_table">
			    <thead>
	            	<tr>
			            <th>真实姓名 </th>
			            <th>登录用户名</th>
			            <th>性别 </th>
			            <th>手机</th>
			            <th>是否为供应商</th>
			            <th>部门</th>
			            <th>电话</th>
			            <th>Email</th>
			            <th>QQ</th>
			            <th>状态</th>
			            <th>操作</th>
		            </tr>              
	            </thead>
	            <tbody>
	              <#if pageFinder?? && (pageFinder.data)?? >
		      		<#list pageFinder.data as item>		
		      		<tr id='Tr${item.id}'>
	                    <td>
							${item.username?default("")}                 
	                    </td>
	                    <td>
	                    	${item.loginName?default("")}
	                    </td>
	                    <td>
	                    	<#if (item.sex == "0")>
								男
							<#elseif (item.sex == "1") >
								女
							</#if>
	                    </td>
	                    <td>
	                    	${item.telPhone?default("")}
	                    </td>
	                    <td>
	                    	<#if item.supplierCode??  && (item.supplierCode != "") >是<#else>否</#if>
	                    </td>
	                     <td>
	                    	${item.organizName?default("")}
	                    </td>
	                    <td>
		                   ${item.mobilePhone?default("")}
	                    </td>
	                    <td>
	                    	${item.email?default("")}
	                    </td>
	                    <td>
	                    	${item.qqNum?default("")}
	                    </td>
	                    <td>
	                    	<span id="State${item.id}">
	                    	<#if item.state?? && (item.state == "1")>
								正常
							<#elseif item.state?? && (item.state == "2") >
								锁定
							<#elseif item.state?? && (item.state == "3") >
								逻辑删除
							<#else>
								${item.state?default("")}
							</#if>
							</span>
	                    </td>
	                    
	                    </td>
	                    <td class="td0" style="text-align:left;">
	                    	<#if item.state?? && (item.state != "3")>
	                    	<a href="../../systemmgmt/systemuser/toUpdateSystemUser.sc?id=${item.id}" target="mbdif" style="padding-right: 5px;padding-left: 5px;">编辑</a>
	                    	<#if (item.level)?? && (item.level == "0")>
	           					<#if (systemUser != '')  && (systemUser.level)?? && (systemUser.level == "0" )>
	           						<a href="javascript:updatePassword('${item.id}');"  style="padding-right: 5px;">修改密码</a>
	           					</#if>
							<#else>
								<a href="javascript:removeSystemUser('${item.id}');"  style="padding-right: 5px;">删除</a>
								<a href="javascript:allotUserRoles('${item.id}');"  style="padding-right: 5px;">分配角色</a>
								<a href="javascript:updatePassword('${item.id}');"  style="padding-right: 5px;">修改密码</a>
							</#if>
							
							<#if item.state?? && (item.state != "")>
	               				<a id="clockUser${item.id}" href="javascript:updateUserState('${item.id}','2');" <#if  (item.state == "2")> style="display: none;padding-right: 5px;"  </#if> title="点击进行用户锁定">锁定</a>
	               				<a id="unClockUser${item.id}" href="javascript:updateUserState('${item.id}','1');"<#if (item.state == "1") > style="display: none;padding-right: 5px;"  </#if> title="点击进行用户解锁" >解锁</a>
	              			</#if>
	                    	</#if>
	                    	<a href="${BasePath}/yitiansystem/systemmgmt/operaterlog/queryOperaterLogList.sc?userId=${item.id};"  style="padding-right: 5px;">操作日志</a>
	                    	<a href="${BasePath}/yitiansystem/systemmgmt/operaterlog/queryOperateLog.sc?url=login&user_id=${item.id}"  style="padding-right: 5px;">登录日志</a>
	                    </td>
	                </tr>
		      		</#list>	
		      	<#else>
		      		<tr><td colspan="11" style="text-align:center;"><div class="yt-tb-list-no">暂无内容</div></td></tr>
				</#if>
	            </tbody>
		    </table> 
		</div>
		<div class="bottom clearfix">
			<#if pageFinder?? && (pageFinder.data)?? >
			    <#import "../../common.ftl"  as page>
				<@page.queryForm formId="queryForm" />
			</#if>
		</div>
	</div>
</div>
<#include "../orderJs.ftl">
</body>
</html>