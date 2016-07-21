<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>提示信息 - Powered By systemConfig.systemName</title>
<meta name="Author" content="belle Team" />
<meta name="Copyright" content="belle" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
</head>
<body onload="setTimeout('closeWin(<#if refreshflag ??>${refreshflag}</#if>)',1000);">

<div class="container">
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class='curr'>
				  <span><a href="">提示信息</a></span>
				</li>
			</ul>
		</div>
 		<div class="modify">
			<center>1秒后将自动关闭</center> 
            <div class="wms-div">
              <input type="hidden" value="<#if images??>${images!''}</#if>" id="images">
               <input type="hidden" value="<#if logisticsId??>${logisticsId!''}</#if>" id="logisticsId">
              <input type="hidden" value="<#if methodName??>${methodName!''}</#if>" id="urlName">
            	<h2>提示信息：</h2>
            	<p style="font-size:14px;padding-top:10px;color:#ff0000;">
            	<#if message ??>${message}</#if>
            	</p>
            	<p class="blank15"></p><br/>
            	<p><input type="button" class="btn-add-normal" onclick="closeWin('<#if refreshflag ??>${refreshflag}</#if>');"  value="关闭"   />
            	</p>
            </div>
        </div>
</div>
</body>
</html>
<script type="text/javascript">
	function closeWin(refreshflag) {
	 var url=$("#urlName").val();
		if(refreshflag=='1') {
		    closewindow();
			refreshpage('${BasePath}'+url);
		}else if(refreshflag=='2'){
		   var images=$("#images").val();
		   var logisticsId=$("#logisticsId").val();
//		   closewindow();
//		   openwindow('${BasePath}'+url+'?images='+images+'&logisticsId='+logisticsId,1000,800,"修改快递单模版");
		   window.location = '${BasePath}'+url+'?images='+images+'&logisticsId='+logisticsId;
		}else {
			closewindow();
			refreshpage('${BasePath}'+url);
		}
	}
</script>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator.js"></script> 
