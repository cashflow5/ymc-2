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
<script type="text/javascript">	
	function goback(url) {
		window.location.href=url;
	}
</script>
</head>
<body >
<div class="contentMain">
        	<div class="ytback-tt-1 ytback-tt">
            	<span>您当前的位置：</span>采购管理 &gt; 供应商价格管理 &gt; 提示信息
	</div>
            <div class="blank5"></div>
            <div class="wms">
                <div class="wms-div">
                	<h2>提示信息：</h2>
                	<p style="font-size:14px;padding-top:10px;color:#ff0000;">
                	<TABLE>
                	<#if result??>
                		<TR>
								<TD colspan="3">导入失败提示</TD>
					 	</TR>
	                	<#list result as message>
							<TR>
								<TD><font color="red">${message.supplierCode?default('')}</font></TD>
								<TD><font color="red">${message.commodityCode?default('')}</font></TD>
								<TD><font color="red">${message.creator?default('')}</font></TD>
							</TR>
						</#list>
						<#else>
						导入成功!
					</#if>	
					</TABLE>
                	</p>
                	<p class="blank15"></p>
                	<p><input type="button" class="wms-form-btn-2" onclick="goback('${BasePath}/supply/manage/supplierprice/toManage.sc');"  value="返回"   />
                	</p>
                </div>
            </div>
</div>
</body>
</html>
