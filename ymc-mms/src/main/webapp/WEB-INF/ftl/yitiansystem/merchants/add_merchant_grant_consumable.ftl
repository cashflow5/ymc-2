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
</head>
<script type="text/javascript">

</script>
<body>
<div class="container">
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr">
				  <span><a href="#" class="btn-onselc">添加商家发放耗材信息</a></span>
				</li>
				
			</ul>
		</div>
 <div class="modify"> 
     <form action="${BasePath}/yitiansystem/merchants/businessorder/add_merchantGrantConsumable.sc" name="queryForm" id="queryForm" method="post"  enctype="multipart/form-data">
                <table cellpadding="0" cellspacing="0" class="list_table">
                        <tr><td style="text-align:right">
                           <span style="color:red;">&nbsp;*</span>商家名称：
                         </td><td>
                         <input type="text"  name="supplierName" id="supplierName"/>
                         <input type="hidden"  name="supplierCode" id="supplierCode"/>
                         <input type="button" value="......" onclick="addSupplierName();"/>
                            &nbsp;&nbsp;<span style="color:red;" id="consumableNameError"></span></td></tr>
                            
                        <tr><td style="text-align:right">
                           <span style="color:red;">&nbsp;*</span>耗材名称：
                         </td><td>
                        <input type="text" name="consumableName" id="consumableName"/>
                        <input type="hidden" name="consumableCode" id="consumableCode"/>
                          <input type="button" value="......" onclick="addconsumableName();"/>
                            &nbsp;&nbsp;<span style="color:red;" id="consumableNameError"></span></td></tr>
                            
                            <tr><td style="text-align:right">
                           <span style="color:red;">&nbsp;*</span>发放数量：
                         </td><td>
                        <input type="text" name="num" id="num"/>
                            &nbsp;&nbsp;<span style="color:red;" id="numError"></span></td></tr>   
                            
                            <tr><td style="text-align:right">
                           <span style="color:red;">&nbsp;*</span>原单据号：
                         </td><td>
                        <textarea id="remark" name="remark"></textarea>
                            &nbsp;&nbsp;<span style="color:red;" id="remarkError"></span></td></tr>      
                            
                        <tr><td>&nbsp;</td><td>
                         <input id="btn" type="button" value="保存" class="yt-seach-btn" onclick="return addMerchantGrantConsumable();">
                        </td></tr>
                </table>
       	</form>
    </div>
 <div class="blank20"></div>
</div>
</body>
</html>
<script type="text/javascript">
//添加商家名称
function addSupplierName(){
  openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_addSupplierName_list.sc",600,400,"添加商家名称");
}
//添加耗材名称
function addconsumableName(){
  openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_addConsumableName_list.sc",600,400,"添加耗材名称");
}
//添加耗材信息
function addMerchantGrantConsumable(){
   //耗材名称
	var consumableName = $("#consumableName").val();
	//耗材货号
	var supplierName = $("#supplierName").val();
	//耗材价格
	var num = $("#num").val();
	if(supplierName=="" ){
		$("#supplierNameError").html("商家名称不能为空!");
		return false;
	}else{
	   $("#supplierNameError").html("");
	}
	if(consumableName=="" ){
		$("#consumableNameeError").html("耗材名称不能为空!");
		return false;
	}else{
	   $("#consumableNameeError").html("");
	}
	if(num=="" ){
		$("#numError").html("发放数量不能为空!");
		return false;
	}else{
	   $("#numError").html("");
	}
	 document.queryForm.submit();
}

</script>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
