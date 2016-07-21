<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-添加联系人</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
</head>

<body>
<form id="queryForm" name="queryForm" action="${BasePath}/merchants/login/add_linkMan.sc" method="post">
	<div class="form_container">
	<table class="form_table">
		<tbody>
			<tr>
				<th>
					<span style="color:red;">*</span>
					姓名：</th>
				<td><input type="text" id="contact" name="contact" class="ginput" /> &nbsp;&nbsp;
					<span style="color:red;" id="contactError"></span>
				</td>
			</tr>
			<tr>
				<th>
					<span style="color:red;">*</span>
					类型：</th>
				<td>
					<select name="type"  id="type" style="width:126px;">
						<option value="">请选择</option>
						<option value="1">业务</option>
						<option value="2">售后</option>
						<option value="3">仓储</option>
						<option value="4">财务</option>
						<option value="5">技术</option>
					</select>
					&nbsp;&nbsp;
					<span style="color:red;" id="typeError"></span>
				</td>
			</tr>
			<tr>
				<th>电话号码：</th>
				<td><input type="text" id="telePhone" name="telePhone"  class="ginput" /> &nbsp;&nbsp;
					<span style="color:red;" id="telePhoneError"></span>
				</td>
			</tr>
			<tr>
				<th>
					<span style="color:red;">*</span>
					手机号码：</th>
				<td><input type="text" id="mobilePhone" maxlength="11" name="mobilePhone" class="ginput" /> &nbsp;&nbsp;
					<span style="color:red;" id="mobilePhoneError"></span>
				</td>
			</tr>
			<tr>
				<th>传真号码：</th>
				<td><input type="text" id="fax" name="fax" class="ginput" /></td>
			</tr>
			<tr>
				<th>电子邮箱：</th>
				<td><input type="text" id="email" name="email" class="ginput" style="width:200px;" /> &nbsp;&nbsp;
					<span style="color:red;" id="emailError"></span>
				</td>
			</tr>
			<tr>
				<th>
					<span style="color:red;">*</span>
					联系地址：</th>
				<td><input type="text" id="address" name="address" maxlength="50" class="ginput" style="width:200px;" /> &nbsp;&nbsp;
					<span style="color:red;" id="addressError"></span>
				</td>
			</tr>
			<!--<tr>
				<th>&nbsp;</th>
				<td style="padding-top:20px;">
				<span>
				<a class="button" id="mySubmit">
				<span id="mySubmit" onclick="addSupplierLinkMan();">保存</span>
				</a>
				</span>
				
				<span>
				<a class="button" id="mySubmit">
				<span id="mySubmit" onclick="resetLinkMan();">重置</span>
				</a>
				</span>
				</td>
			</tr>-->
		</tbody>
	</table>
	</div>
</form>

</body>
</html>
<script type="text/javascript">
$(function()
{
$("#myForm").validation(); //绑定验证框架
setTimeout(function () {$("#contact").focus()}, 240);
})
$("input[name='mobilePhone']").keydown(function(event){
	var code = event.which;
	if(code==10||code==8){
	    return true;
	}else if(code >47 && code < 58) {
		return true;
	}else if(code >95 && code < 106){
		return true;
	}else{
	   return false;
	}
	return true;
});

//添加联系人信息
  function addSupplierLinkMan(){
     var contact=$("#contact").val();//姓名
     var type=$("#type").val();//类型
     var mobilePhone=$("#mobilePhone").val();//手机号码
     var address=$("#address").val();//联系地址
     var email=$("#email").val();
     if(contact==""){
        $("#contactError").html("姓名不能为空!");
        return false;
     }else{
       $("#contactError").html("");
     }
      if(type==""){
        $("#typeError").html("类型不能为空!");
        return false;
     }else{
       $("#typeError").html("");
     }
     if(mobilePhone==""){
        $("#mobilePhoneError").html("手机号码不能为空!");
        return false;
     }else{
       $("#mobilePhoneError").html("");
     }
      if(address==""){
        $("#addressError").html("联系地址不能为空!");
        return false;
     }else{
       $("#addressError").html("");
     }
     //判断手机号码是否存在
	$.ajax({ 
		type: "post", 
		url: "${BasePath}/merchants/login/exitsTelePhone.sc?mobilePhone=" + mobilePhone, 
		success: function(dt){
			if("sucuess"==dt){
			   $("#mobilePhoneError").html("手机号码已经存在,请重新输入!");
			   return false;
			}else{
			  if(email!=""){
				    //判断email是否存在
					$.ajax({ 
						type: "post", 
						url: "${BasePath}/merchants/login/exitsEmail.sc?email=" + email, 
						success: function(dt){
							if("sucuess"==dt){
							   $("#emailError").html("电子邮箱已经存在,请重新输入!");
							   return false;
							}else{
							   //提交表单数据
							   document.queryForm.submit();
							}
						} 
					});
				}else{
				    //提交表单数据
				    $("#queryForm").submit();
				    tips('添加成功');
				}
			}
		} 
	});
  }
  
  //重置文本框信息
  function resetLinkMan(){
    $(":input[type=text]").val("");
  }
</script>