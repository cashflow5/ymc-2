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
		</div>
 <div class="modify"> 
     <form action="${BasePath}/yitiansystem/merchants/businessorder/update_MerchantConsumable.sc" name="queryForm" id="queryForm" method="post"  enctype="multipart/form-data">
               <input type="hidden" id="id" name="id" value="<#if merchantConsumable??&&merchantConsumable.id??>${merchantConsumable.id!''}</#if>">
                <table cellpadding="0" cellspacing="0" class="list_table">
                        <tr><td style="text-align:right">
                           <span style="color:red;">&nbsp;*</span>耗材名称：
                         </td><td>
                         <input type="text"  name="consumableName" id="consumableName" value="<#if merchantConsumable??&&merchantConsumable.consumableName??>${merchantConsumable.consumableName!''}</#if>"/>
                            &nbsp;&nbsp;<span style="color:red;" id="consumableNameError"></span></td></tr>
                            
                        <tr><td style="text-align:right">
                           <span style="color:red;">&nbsp;*</span>耗材货号：
                         </td><td>
                        <input type="text" name="consumableCode" id="consumableCode" value="<#if merchantConsumable??&&merchantConsumable.consumableCode??>${merchantConsumable.consumableCode!''}</#if>"/>
                            &nbsp;&nbsp;<span style="color:red;" id="consumableCodeError"></span></td></tr>
                            
                            <tr><td style="text-align:right">
                           <span style="color:red;">&nbsp;*</span>耗材价格：
                         </td><td>
                        <input type="text" name="price" id="price" value="<#if merchantConsumable??&&merchantConsumable.price??>${merchantConsumable.price!''}</#if>"/>
                            &nbsp;&nbsp;<span style="color:red;" id="priceError"></span></td></tr>   
                            
                            <tr><td style="text-align:right">
                           <span style="color:red;">&nbsp;*</span>耗材说明：
                         </td><td>
                          <textarea id="remark" name="remark">
                          <#if merchantConsumable??&&merchantConsumable.remark??>${merchantConsumable.remark!''}</#if>
                          </textarea>
                            &nbsp;&nbsp;<span style="color:red;" id="remarkError"></span></td></tr>      
                            
                        <tr><td>&nbsp;</td><td>
                         <input id="btn" type="button" value="提交" class="yt-seach-btn" onclick="return updateMerchantConsumable();">
                        </td></tr>
                </table>
       	</form>
    </div>
 <div class="blank20"></div>
</div>
</body>
</html>
<script type="text/javascript">
//修改耗材信息
function updateMerchantConsumable(){
   //耗材名称
	var consumableName = $("#consumableName").val();
	//耗材货号
	var consumableCode = $("#consumableCode").val();
	//耗材价格
	var price = $("#price").val();
	if(consumableName=="" ){
		$("#consumableNameError").html("耗材名称不能为空!");
		return false;
	}else{
	   $("#consumableNameError").html("");
	}
	if(consumableCode=="" ){
		$("#consumableCodeError").html("耗材货号不能为空!");
		return false;
	}else{
	   $("#consumableCodeError").html("");
	}
	if(price=="" ){
		$("#priceError").html("耗材价格不能为空!");
		return false;
	}else{
	   $("#priceError").html("");
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
