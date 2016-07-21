<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-账户管理</title>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css?${style_v}"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
</head>
<body>
<div class="main_container">
<div class="normal_box">
<p class="title site">当前位置：商家中心 &gt; 身份验证</p>
    </div>
	 </div>
	 <!--#include file="layout/footer.shtml" -->
    <script>
    $(function() {
        dialog_obj=$.dialog.open('${BasePath}/merchants/security/authentication.sc?from=index',{
            title:"身份验证",
            max:false,
            min:false,
            width: '650px',
            height: '350px',
            lock:true,
            drag: false,
		    resize: false,
		    cancel:false
        });
    });
    </script>
</body>
</html>