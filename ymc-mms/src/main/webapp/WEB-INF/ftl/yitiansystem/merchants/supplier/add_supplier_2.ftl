<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,优购网,B网络营销系统,商品管理" />
<meta name="Description" content=" , ,B网络营销系统-商品管理" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/js/yitiansystem/merchants/ztree/css/zTreeStyle/zTreeStyle.css"/>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script> 
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/order.js"></script> 
<script type="text/javascript" src="${BasePath}/js/yitiansystem/merchants/ztree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/merchants/ztree/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/merchants/cat_tree.js?lyx20151008"></script>

<style type="text/css">
.com_modi_table th{width: 200px;}
</style>

<title>优购网-招商供应商管理</title>
<script type="text/javascript">
var basePath = "${BasePath}";
//密码强度验证函数
function PwdStrengthValidate(obj) {
    var that = $(obj);
    var val = that.val(),
        li = $(".safetyStrength li");
    //特殊字符
    var reg = /[(\~)(\!)(\@)(\#)(\$)(\%)(\^)(\&)(\*)(\()(\))(\-)(\_)(\+)(\=)(\[)(\])(\{)(\})(\|)(\\)(\;)(\:)(\')(\")(\,)(\.)(\/)(\<)(\>)(\?)(\)]+/;
    var numberFlag = false;
    var letterFlag = false;
    var otherFlag = false;
    if(/[0-9]+/.test(val)){
    	numberFlag = true;
    }
    if(/[A-Za-z]+/.test(val)){
    	letterFlag = true;
    }
    if(reg.test(val)){
    	otherFlag = true;
    }
    if(val.length >= 6 && val.length<=20){
    	if (numberFlag && letterFlag && otherFlag) {
            li.attr("class", "").eq(2).addClass("pwdTall");
            li.eq(3).addClass('text').html('高');
        }else if ((numberFlag && !letterFlag && otherFlag) || 
    			(numberFlag && letterFlag && !otherFlag) || 
    			(!numberFlag && letterFlag && otherFlag)) {
            li.attr("class", "").eq(1).addClass("pwdMid");
            li.eq(3).addClass('text').html('中');
        }else{
        	li.attr("class", "").eq(0).addClass("pwdLow");
            li.eq(3).addClass('text').html('低');
        }
    }else{
    	li.attr("class", "").eq(0).addClass("pwdLow");
        li.eq(3).addClass('text').html('低');
    }
}

function addSupplierUser() {
	if (!validation()) return; 
	var loginAccount = $("#loginName").val();//登录账号
	//var loginName=encodeURIComponent(loginAccount);
	var id = $('#id').val();
	
	if (id == '') {
		//判断用户名与手机是否存在
		$.ajax({ 
			type: "post", 
			url: "${BasePath}/yitiansystem/merchants/businessorder/exitsLoginAccountAndMobile.sc", 
			data: {loginAccount:loginAccount,mobileCode:$.trim($("#mobileCode").val())},
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			dataType:"json",
			success: function(dt){
				if("success"==dt.isAccountExits || "success"==dt.isMobileExits){
					if("success"==dt.isAccountExits){
						$("#loginAccountError").html("登录帐号已经存在,请重新输入!");
						$("#loginName").focus();
					}
					if("success"==dt.isMobileExits){
						$("#mobileError").html("<span style='color:red;'>手机号码已经存在,请重新输入!</span>");
						$("#mobileCode").focus();
					}
				    return false;
				} else {
					$.ajax({
					   type: "POST",
					   dataType: "html",
					   url: "${BasePath}/yitiansystem/merchants/businessorder/add_supplier_user.sc",
					   data: $('#submitForm').serialize(),
					   success: function(msg){
					     if (msg != 'false') {
					     	location.href="${BasePath}/yitiansystem/merchants/businessorder/to_add_supplier_auth.sc?supplierId=" + msg;
					     } else {
					     	alert('供应商账户信息保存失败，手机号码可能已绑定其他账号！');
					     }
					   }
					});
				}
			}
		});
	} else {
		$.ajax({
		   type: "POST",
		   dataType: "html",
		   url: "${BasePath}/yitiansystem/merchants/businessorder/add_supplier_user.sc",
		   data: $('#submitForm').serialize(),
		   success: function(msg){
		     if (msg != 'false') {
		     	location.href="${BasePath}/yitiansystem/merchants/businessorder/to_add_supplier_auth.sc?supplierId=" + msg;
		     } else {
		     	alert('供应商账户信息保存失败，手机号码可能已绑定其他账号！');
		     }
		   }
		});
	}
}

</script>
</head><body>

<div class="container"> 
	<!--工具栏start--><!--操作按钮start--> 
	<#--div class="toolbar">
		<div class="t-content"> 
		</div>
	</div-->
	<!--工具栏end-->
	<div class="list_content"> 
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
			  <li class='curr'> <span><a href="">添加商家信息</a></span> </li>
			</ul>
		</div>
		<!--当前位置end--> 
<!--主体start-->
<div id="modify" class="modify">
	<div class="fdpwd_step wrap">
	    <ul class="step2 clearfix">
	        <a href="#" onclick="gotoTab(1)"><li>1.供应商基本信息</li></a>
	        <a href="#" onclick="gotoTab(2)"><li class="curr">2.账户信息</li></a>
	        <a href="#" onclick="gotoTab(3)"><li>3.品牌分类授权</li></a>
	    </ul>
	</div>
	<form action="${BasePath}/yitiansystem/merchants/businessorder/add_supplier_user.sc" method="post" id="submitForm" name="submitForm">
		<input type="hidden" name="supplierId" id="supplierId" value="${supplierId}" />
		<input type="hidden" name="id" id="id" value="<#if user?? && user.id??>${user.id!''}</#if>" />
		<table class="com_modi_table">
		    <tr>
            	<th><span class="star">*</span>登录账号：</th>
                <td>
                	<input type="text" name="loginName" id="loginName" value="<#if user?? && user.loginName??>${user.loginName}</#if>"/>
                   	<span style="color:red;" id="loginAccountError"></span>
                </td>
            </tr>
            <tr>
            	<th><span class="star">*</span>登录密码：</th>
                <td>
                  	<input type="password" name="password" maxlength="20" style="float: left;" id="password" value="<#if user?? && user.password??>${user.password}</#if>" />
                	<span id="loginPasswordError"><span style="float: left;margin-top:10px;">6-20个字符，由字母、数字和符号的两种以上组合</span></span>
                	<span id="loginPasswordPower" style="display: none;">
                		<ul class="safetyStrength">
	                        <li class="pwdLow"></li>
	                        <li></li>
	                        <li></li>
	                        <li class="text">低</li>
	                    </ul>
                	</span>
               </td>
           	</tr>
            <tr>
            	<th><span class="star">*</span>确认密码：</th>
                <td>
                	<input type="password" name="passwordTwo" id="passwordTwo" value="<#if user?? && user.password??>${user.password}</#if>" />
                	<span style="color:red;" id="passwordTwoError"></span>
                </td>
            </tr>
            <#--<tr>
            	<th>邮箱：</th>
                <td>
                	<input type="text" name="email" id="email" value="<#if user?? && user.email??>${user.email}</#if>" />
                	<span style="color:red;" id="emailError"></span>
                </td>
            </tr>-->
			<tr>
            	<th><span class="star">*</span>手机号码：</th>
                <td>
                	<input type="text" name="mobileCode" id="mobileCode" maxlength="11" value="<#if user?? && user.mobileCode??>${user.mobileCode}</#if>" />
                	<span id="mobileError">用于账号安全验证，务必填写准确</span>
                </td>
            </tr>
            <tr>
              	<td>&nbsp;</td>
				<td> 
					<input type="button" value="保存并继续" class="autoBtn mt10" onClick="return addSupplierUser();"/>
				</td>
			</tr>
		</table>
	</form>
</div>
</div>
</div>
</body>
<script type="text/javascript">
$("#password").keyup(function(event) {
	$("#loginPasswordError").hide();
	$("#loginPasswordPower").css("display","block");
    PwdStrengthValidate(this);
});
function validation() {
	var loginAccount = $("#loginName").val();//登录账号
	var loginPassword= $("#password").val();//登录密码
	var passwordTwo = $("#passwordTwo").val();//确认密码
	//var email = $("#email").val();
	var mobileCode = $.trim($("#mobileCode").val());
	
	if(loginAccount=="" ){
		$("#loginAccountError").html("登录账号不能为空!");
		$("#loginName").focus();
		return false;
	}else{
		$("#loginAccountError").html("");
	}
	var reg = /[\W]/g;
	if(reg.exec(loginAccount)){
		$("#loginAccountError").html("<span style='color:red;'>登录账号只能是英文或数字!</span>s");
		$("#loginName").focus();
		return false;
	}else{
		$("#loginAccountError").html("");
	}
	if(!(checkPassword($("#password").val(),$("#passwordTwo").val()))){
		return false;
	}
	/*if (email!='' && !verifyAddress(email)) {
		$("#emailError").html("请输入正确的邮箱！");
		$("#email").focus();
		return false;
	} else {
		$("#emailError").html("");
	}*/
	
	if(mobileCode==""){
		$("#mobileError").html("<span style='color:red;'>请输入手机号码！</span>");
		$("#mobileCode").focus();
		return false;
	}else if(!verifyMobile(mobileCode)){
		$("#mobileError").html("<span style='color:red;'>请输入正确的手机号码！</span>");
		$("#mobileCode").focus();
		return false;
	}else{
		$("#mobileError").html("");
	}
	return true;
}

var checkPassword = function(password1,password2){
	if(password1.length>=6 && password1.length<=20){
		var reg = /^[(\~)(\!)(\@)(\#)(\$)(\%)(\^)(\&)(\*)(\()(\))(\-)(\_)(\+)(\=)(\[)(\])(\{)(\})(\|)(\\)(\;)(\:)(\')(\")(\,)(\.)(\/)(\<)(\>)(\?)(\)]+$/;
		if(/^\d+$/.test(password1)){
			//全数字
			$("#loginPasswordPower").hide();
			$('#loginPasswordError').html("<span style='color:red;margin-top:10px;'>密码不能为纯数字！</span>").show();
			return false;
		}else if(/^[A-Za-z]+$/.test(password1)){
			//全字母
			$("#loginPasswordPower").hide();
			$('#loginPasswordError').html("<span style='color:red;margin-top:10px;'>密码不能为纯字母！</span>").show();
			return false;
		}else if(reg.test(password1)){
			//全是字符
			$("#loginPasswordPower").hide();
			$('#loginPasswordError').html("<span style='color:red;margin-top:10px;'>密码不能为纯符号！</span>").show();
			return false;
		}else if(password1!=password2){
			$("#loginPasswordPower").hide();
			$('#loginPasswordError').html("<span style='color:red;margin-top:10px;'>登录密码与确认密码不相同！</span>").show();
			return false;
		}else if(/[(\ )]+/.test(password1)){
			$("#loginPasswordPower").hide();
			$('#loginPasswordError').html("<span style='color:red;margin-top:10px;'>登录密码不能含有空格！</span>").show();
			return false;
		}
		$('#loginPasswordError').html("").hide();
		$("#loginPasswordPower").css("display","block");
		return true;
	}else{
		$("#loginPasswordPower").hide();
		$('#loginPasswordError').html("<span style='color:red;margin-top:10px;'>请输入6-20位密码！</span>").show();
		return false;
	}
};

function verifyAddress(email){
	var pattern =/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/; 
	var flag = pattern.test(email);
	return flag;
}

function verifyMobile(mobile){
	var telReg = !!mobile.match(/^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/);
	return telReg;
}

function gotoTab(flag) {
	var supplierId = $('#supplierId').val();
	if (supplierId != '') {
		if (flag == 1) {
			location.href="${BasePath}/yitiansystem/merchants/businessorder/to_add_supplier.sc?supplierId=" + supplierId;
		} else if (flag == 3) {
			location.href="${BasePath}/yitiansystem/merchants/businessorder/to_add_supplier_auth.sc?supplierId=" + supplierId;
		}
	}
}
</script>
</html>