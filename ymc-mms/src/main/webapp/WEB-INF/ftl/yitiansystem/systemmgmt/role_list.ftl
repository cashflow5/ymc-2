<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>B网络营销系统-系统管理-角色</title>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-index.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<script type="text/javascript"  src="${BasePath}/js/yitiansystem/systemmgmt/role.js"></script>
</head>
<body>
<input type="hidden" value=${BasePath} id="path">
<div class="container">
	<div class="toolbar">
		<div class="t-content">
			<div class="btn" onclick="javascript:toAddRole()">
				<span class="btn_l"></span>
	        	<b class="ico_btn add"></b>
	        	<span class="btn_txt">添加角色</span>
	        	<span class="btn_r"></span>
        	</div> 
		</div>
	</div>
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class='curr' ><span>角色列表</span></li>
			</ul>
		</div>
		<div class="modify">
			<div class="add_detail_box">
				<form action="queryRoleList.sc" name="queryForm" id="queryForm" method="post">
					<p>
						<span>
							<label>角色名称：</label>
							<input name="roleName" class="inputtxt" value="${roleName?default('')}"/>
						</span>
						<span>
							<label>菜单名称：</label>
							<input name="menuName" class="inputtxt" value="${menuName?default('')}"/>
						</span>
						<span>
							<input type="submit" class="btn-add-normal-4ft">
						</span>
					</p>
				</form>
			</div>
			<table cellpadding="0" cellspacing="0" class="list_table">
			    <thead>
			    <tr>
				    <th>角色名称</th>
				    <th>创建时间</th>
				    <th>备注</th>
				    <th>操作</th>
			    </tr>              
			    </thead>
			    <tbody>
			    
			     <#if pageFinder?? && (pageFinder.data)?? >
			  		<#list pageFinder.data as item>
			  		<tr>
			            <td>
							${item.roleName?default('')}               
			            </td>
			            <td>
							<#if item.roleCreatedate ??>
								${item.roleCreatedate?string("yyyy-MM-dd")}
							</#if>
						</td>
			            <td>${item.remark?default('')}</td>
			            <td class="td0">
			        		<a href="javascript:toUpdateRole('${item.id}');" >编辑</a>
							<a href="javascript:removeRole('${item.id}');" >删除</a>
							<a href="javascript:allotRoleResource('${item.id}');" >分配资源</a>
							<a href="javascript:queryLog();" >操作日志</a>
			            </td>
			        </tr>
			  		</#list>	
			  	
			  	<#else>
			  		<tr><td colspan="4"><div class="yt-tb-list-no" style="text-align:center;">暂无内容</div></td></tr>
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
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
</body>
</html>

