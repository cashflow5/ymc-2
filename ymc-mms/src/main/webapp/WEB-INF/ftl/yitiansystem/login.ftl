<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购科技有限公司B2C管理系统</title>
<link rel="icon" href="${BasePath}/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-index.css"/>
</head>
<body class="login_bg">
<table width="100%" align="center">
	<tbody>
		<tr>
			<td>
				<form action="login.sc" method="post"  name="loginForm" id="loginForm" class="loginForm" >
				<input type="hidden" name="PrivateFromSubmitKey"  value="${PrivateFromSubmitKey?default('')}" />
        	
        		<input type="hidden" name="sysMenuId" value="0" />
					<div class="login" id="loginDiv">
						<div class="img"><img src="${BasePath}/yougou/images/layout/logo.gif" width="251" height="102" /></div>
						<div class="txt">
							<table class="com_modi_table" width="338">
								<tr>
									<th  style="width:80px;">用户名：</th>
									<td colspan="2">
										<input id="loginName" name="loginName" type="text" class="g-ipt"/>
										<span id="loginNameTip"></span>
										<div class="tip" id="loginNameFixTip" ></div>
									</td>
								</tr>
								<tr id="loginPasswordTr" style="display:">
									<th style="width:80px;">密码：</th>
									<td colspan="2">
										<input id="loginPassword"  name="loginPassword" type="password" class="g-ipt" />
										<span id="loginPasswordTip"></span>
										<div class="tip" id="loginPasswordFixTip" ></div>
									</td>
								</tr>
								  <tr id="newLoginPasswordTr" style="display:none">
									<th style="width:80px;">新密码：</th>
									<td colspan="2">
										<input id="newLoginPassword"  name="newLoginPassword" type="password" class="g-ipt" />
									</td>
								</tr>
								  <tr id="checknewPasswordTr" style="display:none">
									<th style="width:80px;">确认新密码：</th>
									<td colspan="2">
										<input id="checknewPassword"  name="checknewPassword" type="password" class="g-ipt" />
									</td>
								</tr>
								<tr>
									<th style="width:80px;">验证码：</th>
									<td colspan="2">
										<input type="text" class="g-ipt" id="loginCode"  name="validateCode" style="width:80px;">
										<img id="imgValidateCode" src="${BasePath}/servlet/imageValidate?dt=<%=new Date() %>" width="55" height="20" align="top" border="0" style="cursor:hand"/>
										<div class="tip" id="loginCodeFixTip" ></div>
										<div class="tip" id="activeRemainDayTip" ></div>
									</td>
								</tr>
								<tr>
									<th style="width:80px;">&nbsp;</th>
									<td>
										<input type="checkbox" name="rememberMe" id="rememberMe" tabindex="4" />
										<label for="rememberMe">记住登录</label>
									</td>
								</tr>
								<tr>
									<th style="width:80px;">&nbsp;</th>
									<td>
										<input name="" id="submitButton" type="button" class="button_login" value="" onclick="submitForm()"/>
									</td>
								</tr>
							</table>
						</div>
					</div>
					<div class="footer_login">©2011 Yougou Technology Co., Ltd.</div>
				</form>
			
			          
			</td>
		</tr>
	</tbody>
</table>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script> 

<script type="text/javascript">
document.onkeydown = function (e) { 
var theEvent = window.event || e; 
var code = theEvent.keyCode || theEvent.which; 
if (code == 13) { 
$("#submitButton").click(); 
} 
} 


	  function reloadCode(){
			var verify=document.getElementById('imgValidateCode');
			verify.setAttribute('src','${BasePath}/servlet/imageValidate?rand='+Math.random());
		}
    
    
  	//var config={form:"loginForm",submit:submitForm,
 	//fields:[
	//	{name:'loginName',allownull:false,regExp:"name",defaultMsg:null,focusMsg:null,errorMsg:'',rightMsg:'',msgTip:'loginNameTip'}
	//]}
  
  //	Tool.onReady(function(){
		//var f = new Fv(config);
		//f.register();
	//});
  	
  	/**
  	 * 提交表单
  	 */
  	//function submitForm(){
  	//	return true;
  	//}
  	
  	function submitForm() {
  	var data = "loginName="+$('#loginName').val()+"&loginPassword="+$('#loginPassword').val()+"&rand="+Math.random();
  		$.ajax({
			  type: "POST",
			  async :false,
			  url: "${BasePath}/yitiansystem/systemmgmt/mainmanage/checkActiveRemainDay.sc",
			  data:{
			"loginName" : $('#loginName').val(),
			"loginPassword" : $('#loginPassword').val(),
			"validateCode" :  $('#loginCode').val(),
			"rand" : Math.random()
		},
			  cache: false,
			  success: function(data){
			  if(data!='fail'&& isInteger(data)) {
			  	var activeRemainDay = parseInt(data);
			  	if(0== activeRemainDay) 
			  	{
			  			//$('#outOfActiveTip').html('	<span style="color:red;">离密码过期还有'+activeRemainDay+'天，请尽快修改密码。</span>');
			  			$('#loginNameFixTip').html('<span style="color:red;">该账号密码已过期，请输入新密码。</span>');
			  			//$('#loginNameFixTip').show();
			  			//$('#loginPasswordTr').hide();
			  			//$('#loginPassword').val('');
			  			//$('#newLoginPasswordTr').show();
			  			//$('#checknewPasswordTr').show();
			  			//$("#submitButton").bind("click",function(){
						//	  doChangePassWord();
						//	});
			  			setTimeout('toUpdatePassWord()',1000);
			  		
			  	}
			  	if(0< activeRemainDay && activeRemainDay <= 5 ){
			  		$('#activeRemainDayTip').html('	<span style="color:red;">离密码过期还有'+activeRemainDay+'天，请尽快修改密码。</span>');
			  		 	setTimeout('doSubmit()',1000);
			  	}
			  	if(activeRemainDay > 5) 
			  	{
			  		doSubmit();
			  	}
			  }
			  if('loginNameFail'==data)
			   {
			    		$('#loginNameFixTip').html('<span style="color:red;">您输入的用户名不存在，请重新输入。</span>');
			    		$('#loginNameFixTip').show();
			  }
			   if('passwordFail'==data)
			   {
			    		$('#loginPasswordFixTip').html('<span style="color:red;">您输入的密码错误，请重新输入。</span>');
			    		$('#loginPasswordFixTip').show();
			  }
			  if('validateCodeFail'==data)
			   {
			    		$('#loginCodeFixTip').html('<span style="color:red;">您输入的验证码错误，请重新输入。</span>');
			    		$('#loginCodeFixTip').show();
			    		reloadCode();
			  }
			  }
			});
  	}
  	
   function toUpdatePassWord() {
   	openwindow('${BasePath}/yitiansystem/systemmgmt/mainmanage/toUpdateSystemUserPassword.sc?oldPasswrod='+$('#loginPassword').val(),500,350,"修改密码");
   }
  	
  	function doChangePassWord() {
  	 	//alert("new=="+$("#newLoginPassword").val());
  	 	if($("#loginPassword").val() == '')
  	{
  		return;
  	}
  	if($("#newLoginPassword").val() == '')
  	{
  		return;
  	}
  	//alert("new=="+$("#newLoginPassword").val());
  		//alert("checknewPassword=="+$("#checknewPassword").val());
  		if($("#newLoginPassword").val() != $("#checknewPassword").val()){
	  			ygdg.dialog.alert("2次密码输入不一致");
	  			return;
	  		}
	  	var data = "loginName="+$('#loginName').val()+"&newLoginPassword="+$('#newLoginPassword').val();
  		$.ajax({
			  type: "POST",
			  async :false,
			  url: "${BasePath}/yitiansystem/systemmgmt/mainmanage/checkActiveRemainDay.sc",
			  data:data,
			  cache: false,
			  success: function(data){
			  
			  
			  if('changePasswordFail'==data)
			   {
			    	ygdg.dialog.alert("密码不能少于6个字符，须同时包含字母与数字。");
			  }
			  if('changePasswordSuccess'==data)
			   {
			    	$('#loginForm').submit();
			  }
			  }
			});
  		//$('#loginForm').attr('action','login.sc');
  		
  	}
  	function doSubmit() {
  	SetPwdAndChk();
  		if($('#newLoginPasswordTr').is(":hidden"))
  	 {
  	 	$('#loginForm').submit();
  	 }else 
  	 {
  	 	doChangePassWord();
  	 }
  		
  	}
  	//判断是否为正整数
	function isInteger(num) {
		if(num==undefined){
			return false;
		}
		if(num==''){
			return false;
		}
		num=parseInt(num);
		var patrn=/^[0-9]*[0-9][0-9]*$/;  
		if (patrn.test(num)){
		   return true;
		}else{
		  // alert("请输入正整数！");
		   return false;
		}
	}
</script>

<script type="text/javascript">
$(function(){
var passwordMatchRulesResult = '<#if passwordMatchRulesResult ??>${passwordMatchRulesResult}</#if>';
if(''!=passwordMatchRulesResult) 
{
	ygdg.dialog.alert("密码长度至少6位且必须包含数字及字母");
}
    $.formValidator.initConfig({theme:"default",submitOnce:true,formID:"loginForm",
        onError:function(msg){},
        submitAfterAjaxPrompt : '有数据正在异步验证，请稍等...'
    });
    
    $("#loginName").formValidator({onShowFixText:" ",onCorrect:" "}).inputValidator({min:2,onError:"用户名不能为空！"});
    $("#loginPassword").formValidator({onShowFixText:" ",onCorrect:" "}).inputValidator({min:1,onError:"用户密码不能为空！"});
    $("#loginCode").formValidator({onShowFixText:" ",onCorrect:" "}).inputValidator({min:1,onError:"验证码不能为空！"});
    
   
});
</script>
<script type="text/javascript"> 
    window.onload=function onLoginLoaded() { 
       // if (isPostBack == "False") { 
            GetLastUser(); 
       // } 
    } 
      
    function GetLastUser() { 
        var id = "49BAC005-7D5B-4231-8CEA-16939BEACD67";//GUID标识符 
        var usr = GetCookie(id); 
        if (usr != null) { 
            $('#loginName').val(usr);
            //document.getElementById('txtUserName').value = usr; 
        } else { 
           // document.getElementById('txtUserName').value = "001"; 
        } 
        GetPwdAndChk(); 
    } 
    //点击登录时触发客户端事件 
      
    function SetPwdAndChk() { 
        //取用户名 
        var usr = $('#loginName').val(); 
        //alert(usr); 
        //将最后一个用户信息写入到Cookie 
        SetLastUser(usr); 
        //如果记住密码选项被选中 
        if (document.getElementById('rememberMe').checked == true) { 
            //取密码值 
            var pwd = $('#loginPassword').val(); 
          //  alert(pwd); 
            var expdate = new Date(); 
            expdate.setTime(expdate.getTime() + 365 * (24 * 60 * 60 * 1000)); 
            //将用户名和密码写入到Cookie 
            SetCookie(usr, pwd, expdate); 
        } else { 
            //如果没有选中记住密码,则立即过期 
            ResetCookie(); 
        } 
    } 
      
    function SetLastUser(usr) { 
        var id = "49BAC005-7D5B-4231-8CEA-16939BEACD67"; 
        var expdate = new Date(); 
        //当前时间加上两周的时间 
        expdate.setTime(expdate.getTime() + 14 * (24 * 60 * 60 * 1000)); 
        SetCookie(id, usr, expdate); 
    } 
    //用户名失去焦点时调用该方法 
      
    function GetPwdAndChk() { 
        var usr =  $('#loginName').val(); 
        var pwd = GetCookie(usr); 
        if (pwd != null) { 
            document.getElementById('rememberMe').checked = true; 
            document.getElementById('loginPassword').value = pwd; 
        } else { 
            document.getElementById('rememberMe').checked = false; 
            document.getElementById('loginPassword').value = ""; 
        } 
    } 
    //取Cookie的值 
      
    function GetCookie(name) { 
        var arg = name + "="; 
        var alen = arg.length; 
        var clen = document.cookie.length; 
        var i = 0; 
        while (i < clen) { 
            var j = i + alen; 
            //alert(j); 
            if (document.cookie.substring(i, j) == arg) return getCookieVal(j); 
            i = document.cookie.indexOf(" ", i) + 1; 
            if (i == 0) break; 
        } 
        return null; 
    } 
   // var isPostBack = "<%= IsPostBack %>"; 
      
    function getCookieVal(offset) { 
        var endstr = document.cookie.indexOf(";", offset); 
        if (endstr == -1) endstr = document.cookie.length; 
        return unescape(document.cookie.substring(offset, endstr)); 
    } 
    //写入到Cookie 
      
    function SetCookie(name, value, expires) { 
        var argv = SetCookie.arguments; 
        //本例中length = 3 
        var argc = SetCookie.arguments.length; 
        var expires = (argc > 2) ? argv[2] : null; 
        var path = (argc > 3) ? argv[3] : null; 
        var domain = (argc > 4) ? argv[4] : null; 
        var secure = (argc > 5) ? argv[5] : false; 
        document.cookie = name + "=" + escape(value) + ((expires == null) ? "" : ("; expires=" + expires.toGMTString())) + ((path == null) ? "" : ("; path=" + path)) + ((domain == null) ? "" : ("; domain=" + domain)) + ((secure == true) ? "; secure" : ""); 
    } 
      
    function ResetCookie() { 
        var usr = document.getElementById('loginName').value; 
        var expdate = new Date(); 
        SetCookie(usr, null, expdate); 
    } 
</script> 
    
</body>
</html>
