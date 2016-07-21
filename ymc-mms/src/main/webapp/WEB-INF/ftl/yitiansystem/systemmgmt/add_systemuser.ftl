<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>网络营销系统-系统管理-系统用户基本信息</title>
<#include "../orderCss.ftl"/>
<script type="text/javascript">
	$(function(){
		//页面加载完后启动验证
		$("#systemUserForm").validation();
	})

	//选择用户组织机构
	function toSelectUserOrganiz(){
		openwindow("../../systemmgmt/organiz/toSelectUserOrganiz.sc",550,450,"选择用户组织机构");
	}
	
	//提交表单
	function mysubmit(){
		if($("#loginPassword").val()==""){
			ygdg.dialog.alert("密码不允许为空！");
			return;
		}else{
			if($("#loginPassword").val()!=$("#checkloginPassword").val()){
				ygdg.dialog.alert("确认密码与原密码不一致！");
				return;
			}
		}
		$("#systemUserForm").submit();
	}
</script>
</head>
<body>
<div class="container">
	<div class="toolbar">
		<div class="t-content">
			<div class="btn">
				<span class="btn_l"></span>
	        	<b class="ico_btn add"></b>
	        	<span class="btn_txt"><a href="#" onclick="history.go(-1);">返回</a></span>
	        	<span class="btn_r"></span>
        	</div> 
		</div>
	</div>
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li id="orderAll" class='curr' ><span>添加系统用户基本信息</span></li>
			</ul>
		</div>
		<form action="c_addSystemUser.sc" method="post"  name="systemUserForm" id="systemUserForm" style="margin:0px;padding:0p;">
	     	<script>document.write("<input type='hidden' name='parentSourcePage' value='"+getBackUrl()+"'/>");</script>
			<div class="modify" id="modify">
				<div class="add_detail_box">
					<div class="add_detail_box">
					<p>
						<span>
			       			<label for="username">真实姓名：<font class="ft-cl-r">*</font>&nbsp;</label>
			       			  <input name="username" type="text" id="username" size="30"  maxLength="40" class="validate[required]" data-rel="真实姓名不能为空！"/>
			       		</span>
			       	</p>
			       	<p>
						<span>
			       			<label for="loginName">登录用户名：<font class="ft-cl-r">*</font>&nbsp;</label>
			       			  <input name="loginName"  id="loginName" type="text"size="30"  maxLength="50" class="validate[required]" data-rel="登录用户名不能为空！"/>
			       		</span>
			       	</p>
			       	<p>
						<span>
			       			<label for="loginPassword">登录密码：&nbsp;</label>
			       			  <input name="loginPassword" class="inputtxt" type="password" id="loginPassword" maxLength="50"/>
			       		</span>
			       	</p>
			       	<p>
						<span>
			       			<label for="checkloginPassword">确认密码：&nbsp;</label>
			       			  <input type="password" id="checkloginPassword" class="inputtxt" name="checkloginPassword"  maxLength="50"/>
			       		</span>
			       	</p>
			       	<p>
						<span>
			       			<label>是否为供应商：&nbsp;</label>
			       			<input type="radio"  name="isSupplier" value="0" />是
								&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio"  name="isSupplier" value="1" checked/>否
			       		</span>
			       	</p>
			       	<p>
						<span>
			       			<label for="supplierCode">供应商选择：&nbsp;</label>
			       			<select id="supplierCode" name="supplierCode">
			       				<#if supplierSpList??>
			       					<option value="-1">--请选择供应商--</option>
			       					<#list supplierSpList as item>
			       						<option value="<#if item.supplierCode??>${item.supplierCode}</#if>"><#if item.supplier??>${item.supplier}</#if></option>
			       					</#list>
			     				<#else>
			      					<option value="-1">--供应商不存在--</option>
			       				</#if>
			       			</select>
			       		</span>
			       	</p>
			       	<p>
						<span>
			       			<label>数据操作权限：&nbsp;</label>
			       			<select name="permissionGroup.id" id="permissionGroup.id" >
			       			 <option value="">请选择数据操作权限</option>
			       			   <#if userPermissionGroupList??>
				 	              <#list userPermissionGroupList as item>
			       			     	 <option value="${item.id!''}">${item.groupName!''}</option>
			       			      </#list>
							  </#if>
			       			</select>
			       		</span>
			       	</p>
			       	<p>
						<span>
			       			<label for="organizName">部门：&nbsp;</label>
			       			  <input name="organizName" type="text" id="organizName" size="30" readonly="readonly" />
							  <input type="butotn" onClick="toSelectUserOrganiz();" value="选择" id="addUserOrganiz"  class="btn-add-normal" />
			       			  <input name="organizNo" type="hidden" id="organizNo" />
			       		</span>
			       	</p>
			       	
			       	
			       	<p>
						<span>
			       			<label for="sex">性别：&nbsp;</label>
			     			 <input type="radio"  name="sex" value="0" checked/>男
								&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio"  name="sex" value="1"/>女
			       		</span>
			       	</p>
			       	<p>
						<span>
			       			<label for="telPhone">手机：&nbsp;</label>
			       			  <input name="telPhone" type="text" id="telPhone" size="30"  maxLength="11"/>
			       		</span>
			       	</p>
			       	<p>
						<span>
			       			<label for="mobilePhone">电话：&nbsp;</label>
			       			  <input name="mobilePhone" type="text" id="mobilePhone" size="30"  maxLength="15"/>
			       		</span>
			       	</p>
			       	<p>
						<span>
			       			<label for="email">E-mail：&nbsp;</label>
			       			  <input name="email" type="text" id="email" size="30"  maxLength="50"/>
			       		</span>
			       	</p>
			       	<p>
						<span>
			       			<label for="qqNum">QQ：&nbsp;</label>
			       			  <input name="qqNum" type="text" id="qqNum" size="30"  maxLength="11"/>
			       		</span>
			       	</p>
			       	<p>
						<span>
			       			<label for="msnNum">MSN：&nbsp;</label>
			       			  <input name="msnNum" type="text" id="msnNum" size="30"  maxLength="30"/>
			       		</span>
			       	</p>
			       	<p>
						<span>
			       			<label for="msnNum">所属仓库：&nbsp;</label>
			       		    <select id="warehouseCode" name="warehouseCode">
			       			  	<#if wareHouses ??>
			       			  		<option value="">--请选择仓库--</option>
			                	<#list wareHouses?keys as itemKey>
									<option  value="${itemKey?default('')}">${wareHouses[itemKey]?default('')}</option>
						 		</#list>
						 		</#if>
			       			</select>
			       		</span>
			       	</p>
			       	<p>
			       		<span>
			       			<label>&nbsp;</label>
			       			<input type="button" onclick="mysubmit()" class="btn-add-normal" value="保存" />  
							<input type="button" class="btn-add-normal" value="取消" onclick="history.go(-1);" />
			       		</span>
		       		</p>
				</div>
			</div>
		</form>
	</div>
</div>
<#include "../orderJs.ftl"/>
</body>
</html>
