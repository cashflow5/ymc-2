<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/jquery-1.3.2.min.js"></script>
<script type="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/js.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/order.js"></script>
<title>优购商城--商家后台</title>
<!-- 日期 -->
</head>
<script type="text/javascript">
</script>
<body>
<div class="container">
	<!--工具栏start--> 
	<div class="toolbar">
		<div class="t-content">
			<div class="btn" onclick="">
        	</div> 
		</div>
	</div>
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li>
				  <span><a href="${BasePath}/supply/vindicate/extend/to_queryPurchaseVindicateList.sc" class="btn-onselc">采购单维护列表</a></span>
				</li>
				<li>
				  <span><a href="${BasePath}/supply/vindicate/extend/to_queryPurchaseTypeList.sc" class="btn-onselc">采购类型维护列表</a></span>
				</li>
				<li class="curr">
				  <span><a href="#">运动的pos采购单同步</a></span>
				</li>
			</ul>
		</div>
 <div class="modify"> 
      <input type="button" value="同步" onclick="updatePosParchase();" class="yt-seach-btn" />&nbsp;&nbsp;&nbsp;
              	
</div>
</div>
</body>
</html>
<script type="text/javascript">
function updatePosParchase(){
	 //提交数据保存
    $.ajax({ 
		type: "post", 
		url: "${BasePath}/supply/vindicate/extend/update_queryPosPurchase.sc", 
		success: function(dt){
			if("success"==dt){
			   alert("修改成功！");
			   closewindow();
			}else{
			   alert("修改失败！");
			}
		} 
	  });
}
</script>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>