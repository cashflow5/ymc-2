<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>提示信息 </title>
<meta name="Author" content="belle Team" />
<meta name="Copyright" content="belle" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
</head>
<!--
<body onload="setTimeout('closeWin(<#if refreshflag ??>${refreshflag}</#if>)',1000);">

<div class="container">
	<div class="list_content">
 		<div class="modify">
 		<br/>
			<b>1秒后将自动关闭</b><br/>
            <div class="wms-div">
            <input type="hidden" value="<#if methodName ??>${methodName}</#if>" id="urlName">
            	<b>提示信息：</b>
            	<span style="font-size:14px;color:#ff0000;">
            	<#if message ??>${message}</#if>
            	</span>
            </div>
        </div>
</div>		
</div>
</body>-->
<body onload="setTimeout('closeWin(<#if refreshflag ??>${refreshflag}</#if>)',2000);">
<input type="hidden" value="<#if methodName ??>${methodName}</#if>" id="urlName">
<div style="padding:100px 20px 0 60px;">
<div class="aui_tips">
<i class="<#if result?? && result=='fail'>error<#else>ok</#if>">
</i>
<span><#if message ??>${message}</#if>
</span></div>

</div>

<script type="text/javascript">

	function closeWin(refreshflag) {
	 var url=$("#urlName").val();
	 
		if(refreshflag=='1') {
		    closewindow();
			refreshpage('${BasePath}'+url);
		}else {
			closewindow();
			refreshpage('${BasePath}'+url);
		}
	}

</script>
</body>
</html>
