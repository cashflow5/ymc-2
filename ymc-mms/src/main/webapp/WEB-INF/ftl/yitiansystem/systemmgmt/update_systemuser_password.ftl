<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>网络营销系统-系统管理-系统用户密码修改</title>
<#include "../orderCss.ftl">
<script type="text/javascript">
	//提交表单
  	function submitForm(){
  		if($("#newPassword").val()==""){
  			ygdg.dialog.alert("原密码不允许为空！");
  			return;
  		}else{
  			if($("#newPassword").val() != $("#checknewPassword").val()){
	  			ygdg.dialog.alert("2次密码输入不一致");
	  			return;
	  		}
  		}
  		//if($("#newPassword").val().length<6)) {
  		//	ygdg.dialog.alert("密码长度至少6位");
	  	//		return;
  		//}
  		//var pattern = /^(?!\D+$)(?![^a-zA-Z]+$)\S{6,20}$/;
  		//if(!pattern.test($("#newPassword").val())) {
  		//	ygdg.dialog.alert("密码必须包含数字及字母");
	  	//		return;
  		//}
  		var data = "newPassword="+$('#newPassword').val();
  		$.ajax({
			  type: "POST",
			  async :false,
			  url: "${BasePath}/yitiansystem/systemmgmt/systemuser/checkPasswordMatchRules.sc",
			  data:data,
			  cache: false,
			  success: function(data){
			  if(data=='success') {
			  		document.systemUserForm1.submit();
				closewindow();
			  }else {
			    	//ygdg.dialog.alert("密码长度至少6位且必须包含数字及字母");
			    		$('#newPasswordTip').html('	<span style="color:red;">密码不能少于6个字符，须同时包含字母与数字。</span>');
			  }
			  
			  }
			});
  	
  	}
  	
  	$(function(){
  		//如果标识该页面关闭
		<#if closeFlag??&&closeFlag=='1'>
			ygdg.dialog.confirm("用户角色更新成功，您确认关闭退出吗？",function(){
				closewindow();//关闭退出
			});
		</#if>
	});
</script>
</head>
<body>
<div class="container">
	<div class="toolbar">
		<div class="t-content">
		</div>
	</div>
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li id="orderAll" class='curr' ><span>密码修改</span></li>
			</ul>
		</div>
		<div class="modify" id="modify">
			<form action="u_updateSystemUserPassword.sc" method="post"  name="systemUserForm1" id="systemUserForm1" style="margin:0px;padding:0p;">
   				<script>document.write("<input type='hidden' name='parentSourcePage' value='"+getThickBoxUrl()+"'/>");</script>
	   		 	<#if warnMessagekey?? >
	            	<div class="warning-show warn-case ft-cl-n ft-sz-12" style="text-align:left;padding-left:5px;">
		            	<img src="${BasePath}/images/yitiansystem/war-stop.gif" alt="" />
		            	<@spring.message code="${warnMessagekey}" />
	            	</div>
	        	<#else>
	        		<div class="warning-show"></div>
	           	</#if>
	            <div>
	            <div class="add_detail_box">
	            	<input type="hidden" name="id" value="${id?default('')}" />
					<p>
						<span>
	            			<label>新密码：</label>
			       			<input name="newPassword" type="password" class="inputtxt" id="newPassword" maxLength="50"/>
			       			<div class="tip" id="newPasswordTip" ></div>
			       		</span>
			       	</p>
			       	<p>
			       		<span>
	            			<label>确认密码：</label>
			       			<input type="password" id="checknewPassword" class="inputtxt" maxLength="50"/>
			       		</span>
			       	</p>
			       	<p>
			       		<span>
	            			<label>&nbsp;&nbsp;&nbsp;&nbsp;</label>
	            			<input type="button" class="btn-add-normal" onclick="submitForm();" value="保存" />  
				  			<input type="button" class="btn-add-normal" value="取消" onclick="closewindow();"/>
			       		</span>
			       	</p>
	            </div>
	        </form>
		</div>
	</div>
</div>
<#include "../orderJs.ftl">
</body>
</html>
