<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>提示信息 - Powered By systemConfig.systemName</title>
<meta name="Author" content="belle Team" />
<meta name="Copyright" content="belle" />
<#include "../../../yitiansystem/yitiansystem-include.ftl">
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<script type="text/javascript">
//刷新父页面
$(function(){
    var shipmentsId=$("#shipmentsId").val();
    var purchaseCode=$("#purchaseCode").val();
    refreshpage('${BasePath}/supply/purchase/addshipments/to_addshipments.sc?shipmentsId='+shipmentsId+'&purchaseCode='+purchaseCode);
});

//返回
function toBack(){
    var shipmentsId=$("#shipmentsId").val();
    var purchaseCode=$("#purchaseCode").val();
    closewindow();
    refreshpage('${BasePath}/supply/purchase/addshipments/to_addshipments.sc?shipmentsId='+shipmentsId+'&purchaseCode='+purchaseCode);
}
</script>
</head>
<body >
<div class="container"> 
	<!--工具栏start-->
	<div class="toolbar">
		<div class="t-content"> <!--操作按钮start--> 
		</div>
	</div>
	<!--工具栏end-->
	<div class="list_content"> 
		<div class="top clearfix">
			<ul class="tab">
				<li class='curr'> <span>供应商未发货单导入结果</span> </li>
			</ul>
		</div>
		<input type="hidden" name="purchaseCode" id="purchaseCode" value="<#if purchaseCode??>${purchaseCode!''}</#if>">
        <input type="hidden" name="shipmentsId" id="shipmentsId" value="<#if shipmentsId??>${shipmentsId!''}</#if>">
		<div id="modify" class="modify">
                	<h2>提示信息：</h2>
                	<p style="font-size:14px;padding-top:10px;color:#ff0000;">
                	<TABLE>
                	<#if result??>
							<TR>
								<TD><font color="red">${result?default('')}</font></TD>
							</TR>
						<#else>
						导入成功!
					</#if>	
					</TABLE>
					<br/>
                	</p>
                	<p><input type="button" class="btn-add-normal" onclick="toBack();"  value="返回"   />
                	</p>
                </div>
            </div>
</div>
</body>
</html>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>