<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>优购商家中心-单据打印</title>
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css"/>
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>
	<script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>
	<script type="text/javascript" src="${BasePath}/yougou/js/Lodop.js?${style_v}"></script>
	<script type="text/javascript" src="${BasePath}/yougou/js/ymc.common.js?${style_v}"></script>
	<script type="text/javascript" src="${BasePath}/yougou/js/manage/order/printexpressnew.js?${style_v}"></script>
	<STYLE MEDIA="PRINT">    
		.noprint{display:none;}
		.loading{position:absolute;height:100%;width:100%;}
	</STYLE>
	<script>
		printexpressnew.basePath ="${BasePath}";
		printexpressnew.orderNos ="${orderNos}";
		printexpressnew.orderCount = "${orderCount}";
	</script>
</head>
 
<body>
<div id="Load" class="noprint" style="position:absolute">加载中，请稍后...</div>
	<div id="Main" class="main_container" style="display:none;margin-top:10px;" >
		<div class="normal_box">
			<!--<p class="title site"></p>-->
			<div class="form_container">
				<form name="querFrom" id="querFrom" onSubmit="return false;" method="post" style="padding:0px;margin:0px;">
					<div style="font-size:14px;font-weight:bold;">选择物流公司</div>
					<div style="padding:5px;float:left;border-bottom:1px dashed grey;width: 99%; " class="search_box logistics">
					     <#list logisticscompanList![] as item>
					     	<span style="margin: 0 0 5px;">
					       	<label for="${item.id!''}">${item.logistics_company_name!''}</label>
					       	<span><input type="radio" id="${item.id!''}" style="height: 23px;"  name="logisticsCompan" value="${item.id!''}" logisticsName="${item.logistics_company_name!''}" logisticsCode="${item.logistic_company_code!''}"/></span>
					       	</span>
					     </#list>
				    </div>
				    <div style="font-size:14px;font-weight:bold;padding-top:5px;">录入快递单号</div>
				    <div style="padding:10px 20px;" class="noSetComm">
					    <#if orderCount==1 >
					    	快递单号：<input id="expressId" name="expressId" type="text" class="ginput" style="width:150px;"  />
					    	<input type="hidden" id="expressIdCount" name="expressIdCount" value="1"/>
					    <#else>
					    <div style="padding:5px;">
					    	<input type="radio" id="con1" name="connection" checked="checked" onclick="printexpressnew.showExpressType(this)" value="0"/>连号&nbsp;&nbsp;&nbsp;&nbsp;
					    	<input type="radio" id="con2" name="connection" onclick="printexpressnew.showExpressType(this)" value="1"/>非连号
					    </div>
					    <div id="conId1" style="padding:5px;">
					    	录入首个快递单号：<input id="expressId" name="expressId" type="text" class="ginput" style="width:150px;"  /> (连号)&nbsp; 
					    	<input type="hidden" id="expressIdCount" name="expressIdCount" value="${orderCount!''}"/> 
					    </div>
						<div id="conId2" style="padding:5px;display:none;">
							录入全部快递单号：<textarea id="expressIds" name="expressIds" class="areatxt" cols="45" rows="6" ></textarea> (非连号，多个快递单号用英文逗号隔开)
						</div>
						</#if>
					</div>
					<div style="padding:5px;word-break:break-all; ">
						[有${orderCount!''}个快递单即将打印，它们是：${orderNos!''}]
					</div>
					<div style="width:100%; text-align:center;padding-bottom:10px;margin-top:10px;">
					<span><a class="button" onclick="printexpressnew.choosePrintExpressTemplate('print')"><span>打印</span></a></span>
					<span style="padding-left:10px;"><a class="button" onclick="printexpressnew.choosePrintExpressTemplate('view')"><span>预览</span></a></span>
					</div>
				</form>
			</div>
		</div>
	</div>
<script>
window.onload = function(){
	$("#Load").hide();
	$("#Main").show();
	//错误
	var error = $('#error').val();
	if (error != null && error.length > 0) {
		ygdg.dialog.alert(error);
	}
};
</script>
</body>
</html>
