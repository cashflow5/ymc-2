<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>重置密码</title>
<link href="${BasePath}/yougou/css/base.css" rel="stylesheet" />
<link href="${BasePath}/yougou/css/mold.css?${style_v}" rel="stylesheet" />
<script src="${BasePath}/yougou/js/jquery-1.7.2.min.js"></script> 
<script language="javascript" type="text/javascript">
	//回车提交表单
	document.onkeydown = function(e){
	    if(!e) e = window.event;//火狐中是 window.event
	    if((e.keyCode || e.which) == 13){
	        document.getElementById("fdpwd_btn").click();
	    }
	} 
  function changeLevel(){
    var verify=document.getElementById('imgValidateCode');
	verify.setAttribute('src','${BasePath}/servlet/imageValidate?dt='+Math.random());
  }
</script>
</head>

<body>
<div class="wrap mt20">
    <a href="${BasePath}/merchants/login/to_index.sc" class="logo"><img height="47" width="285" alt="商家中心" src="${BasePath}/yougou/images/logo.jpg"></a>
</div>
<div class="wrap topline mt20">
</div>
<div class="wrap mt25">
    您当前所在位置 &gt; 商家中心 &gt;
    <span class="cblue">找回密码</span>
</div>
<div class="fdpwd_step wrap">
    <ul class="step1 clearfix">
        <li class="curr">1.输入账号</li>
        <li>2.密码重置</li>
        <li>3.修改成功</li>
    </ul>
</div>
<div class="wrap">
    <form id="myForm" action="${BasePath}/merchants/login/checkpassport.sc" method="post">
        <table class="form_table fdpwd_table" style="margin:30px auto 0 auto;width:650px;">
            <tbody>
                <tr>
                    <th> <span class="cred">*</span>
                        登录用户名：</th>
                    <td><input type="text" class="ginput" datatype="s4-30" errormsg="请输入用户登录名！"  name="loginName" id="loginName" style="width:250px;" placeholder="请输入您的登录用户名" ></td>
                </tr>
                <tr>
                    <th> <span  class="cred">*</span>
                        输入验证码：</th>
                    <td><input type="text" class="ginput" datatype="s4-4" nullmsg="请输入验证码！" errormsg="请输入验证码！" name="captcha" id="captcha" placeholder="请输入验证码" maxlength="4" > &nbsp;&nbsp; <img id="imgValidateCode" src="${BasePath}/servlet/imageValidate" width="60" height="30" /> <a class="cblue" href="javascript:;" onclick="changeLevel();return false;">看不清，换一个</a></td>
                </tr>
                <tr>
                    <th></th>
                    <td ><input type="submit" class="fdpwd_btn" value="提交" id="btnStep1" /></td>
                </tr>
            </tbody>
            <tfoot>
                <tr>
                    <td colspan="2"  class="pt20 pl20 c9"> 如果您是优购招商供应商，但未填写绑定，请联系您的招商经理添加。 </td>
                </tr>
            </tfoot>
        </table>
    </form>
</div>
<script src="${BasePath}/yougou/js/validator/Validform_v5.3.2.js"></script> 
<script language="javascript" type="text/javascript">
	$("#myForm").Validform({
	    tiptype:3,
		ajaxPost:true,
		callback:function(data){
			if(data.sucess=="true"){
                $.Showmsg("<img src='${BasePath}/yougou/images/right.png'><font color='#71B83D'>"+data.message+"</font>");
			}else{
			    $.Showmsg("<img src='${BasePath}/yougou/images/error.png'><font color='#ff0000'>"+data.message+"</font>");
			}
			changeLevel();
		}
	});
</script>
</body>
</html>
