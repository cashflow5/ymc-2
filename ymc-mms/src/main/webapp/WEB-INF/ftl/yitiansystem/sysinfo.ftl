<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购科技有限公司B2C管理系统</title>
</head>
<body>
${msg!''}<br/><br/>
登录出现问题，<span id="countDown">10</span>秒钟后跳转到登录页面，请重新登录！
<script>
//设定倒数秒数  
var t = 10;  
//显示倒数秒数  
function showTime(){  
    t -= 1;  
    document.getElementById('countDown').innerHTML= t;  
    if(t==0){  
        window.top.document.location.href = '/mms/yitiansystem/systemmgmt/mainmanage/logout.sc';
    }  
    //每秒执行一次,showTime()  
    setTimeout("showTime()",1000);  
}  
  
//执行showTime()  
showTime();  
</script>
</body>
</html>