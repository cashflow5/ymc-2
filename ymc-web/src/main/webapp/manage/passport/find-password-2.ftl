<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>重置密码</title>
<link href="${BasePath}/yougou/css/base.css" rel="stylesheet" />
<link href="${BasePath}/yougou/css/mold.css?${style_v}" rel="stylesheet" />
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
    <ul class="step2 clearfix">
        <li>1.输入账号</li>
        <li class="curr">2.密码重置</li>
        <li>3.修改成功</li>
    </ul>
</div>
<div class="wrap" style="width:620px;">
    <form id="myForm" action="${BasePath}/merchants/login/resetpassword.sc?code=${code}" method="post">
        <div class="mt50 c9">
            已通过验证，请设置您的新密码。
        </div>
        <div class="mt20">
            <table class="form_table fdpwd_table">
                <tbody>
                    <tr>
                        <th> <span class="cred">*</span>
                            设置密码：</th>
                        <td><input type="password" class="ginput" datatype="s4-30" nullmsg="请输入新密码！" errormsg="请输入新密码！"  name="userPwd" id="userPwd" style="width:250px;" placeholder="请输入新密码" ></td>
                    </tr>
                    <tr>
                        <th> <span  class="cred">*</span>
                            确认密码：</th>
                        <td><input type="password" class="ginput" recheck="userPwd" nullmsg="请确认新密码！" datatype="*" errormsg="两次密码输入不一致！" name="confirmPwd" id="confirmPwd" placeholder="请确认新密码" style="width:250px;" ></td>
                    </tr>
                    <tr>
                        <th></th>
                        <td ><input type="submit" class="fdpwd_btn" value="确 认" id="btnStep1" /></td>
                    </tr>
                </tbody>
            </table>
        </div>
    </form>
</div>
<script src="${BasePath}/yougou/js/jquery-1.7.2.min.js"></script> 
<script src="${BasePath}/yougou/js/validator/Validform_v5.3.2.js"></script> 
<script language="javascript" type="text/javascript">
	$("#myForm").Validform({
	    tiptype:3,
		callback:function(data){
			setTimeout(function(){
				$.Hidemsg(); //公用方法关闭信息提示框;显示方法是$.Showmsg("message goes here.");
			},2000);
		}
	});
</script>
</body>
</html>
