<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-修改密码</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
</head>
<body>
	<div class="main_container">
		<div class="normal_box">
			<p class="title site">当前位置：商家中心 &gt; 账户管理 &gt; 修改密码</p>
			<div class="tab_panel">
				<ul class="tab">
					<li class="curr"><span>修改密码</span></li>
				</ul>
				<div class="tab_content">
<form id="queryForm" name="queryForm" action="${BasePath}/merchants/login/update_password.sc" method="post">
	<div class="form_container">
		<table class="form_table">
			<tbody>
				<tr>
					<th>
						<span style="color:red;">*</span>
						原密码：</th>
					<td><input type="password" id="password" name="password" class="ginput" /> &nbsp;&nbsp;
						<span style="color:red;" id="passwordError"></span>
					</td>
				</tr>
				<tr>
					<th>
						<span style="color:red;">*</span>
						新密码：</th>
					<td><input type="password" id="newPassword" name="newPassword" class="ginput"/> &nbsp;&nbsp;
						<span style="color:red;" id="newPasswordError"></span>
					</td>
				</tr>
				<tr>
					<th>
						<span style="color:red;">*</span>
						确认新密码：</th>
					<td><input type="password" id="newPasswordTwo" name="newPasswordTwo" class="ginput"/> &nbsp;&nbsp;
						<span style="color:red;" id="newPasswordTwoError"></span>
					</td>
				</tr>
				<tr>
					<td style="text-align:left;">&nbsp;</td>
					<td style="text-align:left;"> <a class="button" id="mySubmit" onclick="return updatePassword();">
						<span>保存</span>
						<a class="button" id="mySubmit" onclick="resetPassword();">
						<span>重置</span>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</form>
</div>
			</div>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
//修改密码
  function updatePassword(){
     var password=$("#password").val();//原密码
     var newPassword=$("#newPassword").val();//新密码
     var newPasswordTwo=$("#newPasswordTwo").val();//确认新密码
     if(password==""){
        $("#passwordError").html("原密码不能为空!");
        return false;
     }else{
       $("#passwordError").html("");
     }
      if(newPassword==""){
        $("#newPasswordError").html("新密码不能为空!");
        return false;
     }else{
       $("#newPasswordError").html("");
     }
     //判断密码长度必须是在6-30之间
     var length = newPassword.length;
     if(length >= 6 && length <= 30){
        $("#newPassword").html("");
     }else{
     	$("#newPassword").val("");
     	$("#newPasswordTwo").val("");
        $("#newPasswordError").html("密码长度必须大于等于6和小于等于30!");
        return false;
     }
     //判断原秘密是否输入正确
     var reg=new RegExp(/[A-Za-z].*[0-9]|[0-9].*[A-Za-z]/);
     if(!reg.test(newPassword)){
     	$("#newPassword").val("");
     	$("#newPasswordTwo").val("");
        $("#newPasswordError").html("密码必须是数字和字符的组合!");
        return false;
     }else{
        $("#newPassword").html("");
     }
     if(newPasswordTwo==""){
        $("#newPasswordTwoError").html("确认新密码不能为空!");
        return false;
     }else{
       $("#newPasswordTwoError").html("");
     }
     if(newPassword!=newPasswordTwo){
        $("#newPasswordTwoError").html("新密码和确认新密码不一致!");
        return false;
     }else{
       $("#newPasswordTwoError").html("");
     }
	$.ajax({ 
		type: "post", 
		url: "${BasePath}/merchants/login/extisPassword.sc", 
		data : {"password":password},
		success: function(dt){
			if("sucuess"!=dt){
			   $("#passwordError").html("原密码不正确!");
			   return false;
			}else{
			   //提交表单数据
			   document.queryForm.submit();
			}
		} 
	});
  }
  
  //重置文本框信息
  function resetPassword(){
    $(":input[type=text]").val("");
    $(":input[type=password]").val("");
  }
</script>