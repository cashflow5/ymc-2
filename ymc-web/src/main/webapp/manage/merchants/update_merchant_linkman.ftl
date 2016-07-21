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
<form id="queryForm" name="queryForm" action="${BasePath}/merchants/login/update_linkMan.sc" method="post">
	<input type="hidden" name="id" id="id" value="<#if spContact??&&spContact.id??>${spContact.id!''}</#if>"> <input type="hidden" name="supplierId" id="supplierId" value="<#if spContact??&&spContact.supplierId??>${spContact.supplierId!''}</#if>">
	<input type="hidden" name="oldPhone" id="oldPhone" value="<#if spContact??&&spContact.mobilePhone??>${spContact.mobilePhone!''}</#if>">
	<div class="form_container">
		<table class="form_table">
			<tbody>
				<tr>
					<th>
						<span style="color:red;">*</span>
						姓名：</th>
					<td><input type="text" id="contact" name="contact" class="ginput" value="<#if spContact??&&spContact.contact??>${spContact.contact!''}</#if>"/> &nbsp;&nbsp;
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
							<option value="1" <#if spContact??&&spContact.type??&&spContact.type==1>selected</#if>>业务
							</option>
							<option value="2" <#if spContact??&&spContact.type??&&spContact.type==2>selected</#if>>售后
							</option>
							<option value="3" <#if spContact??&&spContact.type??&&spContact.type==3>selected</#if>>仓储
							</option>
							<option value="4" <#if spContact??&&spContact.type??&&spContact.type==4>selected</#if>>财务
							</option>
							<option value="5" <#if spContact??&&spContact.type??&&spContact.type==5>selected</#if>>技术
							</option>
						</select>
						&nbsp;&nbsp;
						<span style="color:red;" id="typeError"></span>
					</td>
				</tr>
				<tr>
					<th>电话号码：</th>
					<td><input type="text" name="telePhone" id="telePhone"  class="ginput" value="<#if spContact??&&spContact.telePhone??>${spContact.telePhone!''}</#if>"> &nbsp;&nbsp;
						<span style="color:red;" id="telePhoneError"></span>
					</td>
				</tr>
				<tr>
					<th>
						<span style="color:red;">*</span>
						手机号码：</th>
					<td><input type="text" name="mobilePhone" id="mobilePhone"  class="ginput" value="<#if spContact??&&spContact.mobilePhone??>${spContact.mobilePhone!''}</#if>"> &nbsp;&nbsp;
						<span style="color:red;" id="mobilePhoneError"></span>
					</td>
				</tr>
				<tr>
					<th>传真号码：</th>
					<td><input type="text" id="fax" name="fax" class="ginput" value="<#if spContact??&&spContact.fax??>${spContact.fax!''}</#if>" /></td>
				</tr>
				<tr>
					<th>电子邮箱：</th>
					<td><input type="text" id="email" name="email" class="ginput" style="width:200px;" value="<#if spContact??&&spContact.email??>${spContact.email!''}</#if>"/></td>
				</tr>
				<tr>
				<tr>
					<th>
						<span style="color:red;">*</span>
						联系地址：</th>
					<td><input type="text" id="address" name="address" class="ginput" style="width:200px;" value="<#if spContact??&&spContact.address??>${spContact.address!''}</#if>"/> &nbsp;&nbsp;
						<span style="color:red;" id="addressError"></span>
					</td>
				</tr>
				<!--<tr>
					<td style="text-align:left;">&nbsp;</td>
					<td style="text-align:left;"> <a class="button" id="mySubmit" onclick="updateSupplierLinkMan();">
						<span>保存</span>
						<a class="button" id="mySubmit" onclick="resetLinkMan();">
						<span>重置</span>
					</td>
				</tr>-->
			</tbody>
		</table>
	</div>
</form>
</body>
</html>
<script type="text/javascript">
//修改联系人信息
  function updateSupplierLinkMan(){
     var contact=$("#contact").val();//姓名
     var type=$("#type").val();//类型
     var mobilePhone=$("#mobilePhone").val();//手机号码
     var address=$("#address").val();//联系地址
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
	  var oldPhone=$("#oldPhone").val();
	  if(oldPhone!=""){
		    $.ajax({ 
				type: "post", 
				anysc:false,
				url: "${BasePath}/merchants/login/exitsNewPhone.sc?newPhone=" + mobilePhone+"&oldPhone="+oldPhone, 
				success: function(dt){
					if("sucuess"==dt){
					   $("#mobilePhoneError").html("手机号码已经存在,请重新输入!");
					   return false;
					}else{
					   //提交表单数据
					   document.queryForm.submit();
					}
				} 
			});
	  }else{
          alert("手机号码不存在!");
          return false;	    
	  }
  }
  
  
  //重置文本框信息
  function resetLinkMan(){
    $(":input[type=text]").val("");
  }
</script>