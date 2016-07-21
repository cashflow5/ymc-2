<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>提示信息 - Powered By systemConfig.systemName</title>
<meta name="Author" content="belle Team" />
<meta name="Copyright" content="belle" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet"  type="text/css" href="${BasePath}/css/ordermgmt/css/style.css"/>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/jquery-1.3.2.min.js"></script>
<script type="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/js.js"></script>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "../../yitiansystem/yitiansystem-include.ftl">
</head>
<body onload="setTimeout('closeWin(<#if refreshflag ??>${refreshflag}</#if>)',5000);">
<div class="container">
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class='curr'>
				  <span> 提示信息</span>
				</li>
			</ul>
		</div>
 <div class="modify">
				<center>5秒后将自动关闭</center>
                <div class="wms-div">
                	<h2>提示信息：</h2>
                	<p style="font-size:14px;padding-top:10px;color:#ff0000;">
                	<#if message ??>${message}</#if>
                	</p>
                	<p class="blank15"></p>
                	<p><input type="button" class="btn-add-normal" onclick="closeWin(<#if refreshflag ??>${refreshflag}</#if>);"  value="关闭"   />
                	</p>
                </div>
            </div>
</div>
</body>
</html>
<script type="text/javascript">
	function closeWin(refreshflag) {
		if(refreshflag=='1') {
			refreshpage();
			closeWin();
		}else {
			closeWin();
		}
	}
</script>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
