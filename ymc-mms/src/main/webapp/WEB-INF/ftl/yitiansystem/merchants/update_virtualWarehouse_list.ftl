<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link TYPE="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" TYPE="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" TYPE="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>

<script TYPE="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/jquery-1.3.2.min.js"></script>
<script TYPE="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
<script TYPE="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/js.js"></script>
<script TYPE="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/order.js"></script>
<title>优购商城--商家后台</title>
<!-- 日期 -->
<script src="${BasePath}/js/common/form/datepicker/WdatePicker.js" TYPE="text/javascript"></script>
</head>
<script TYPE="text/javascript">
</script>
<body>
<DIV class="container">
	<DIV class="list_content">
		<DIV class="top clearfix">
			<ul class="tab">
				<li  class="curr">
				  <span><a href="#" class="btn-onselc">仓库列表</a></span>
				</li>
			</ul>
		</DIV>
 <DIV class="modify"> 
     <form ACTION="${BasePath}/yitiansystem/merchants/businessorder/to_linkmanList.sc" NAME="queryForm" id="queryForm" method="post"> 
  			  	<DIV class="wms-top">
  			  	<input type="hidden" value="<#if id??>${id!''}</#if>" name="id" id="id" >
  			  	
  			  	<span>仓库名称:</span>
  			  	<#if virtualWarehouseList??>
	  			  	<select id="warehouseNam">
	  			  	  <option value="">请选择仓库</option>
	  			  	  <#list virtualWarehouseList as item>
	  			  	      <option value="${item.virtualWarehouseCode!''}">${item.virtualWarehouseName!''}</option>
	  			  	  </#list>
	  			  	</select>
  			  	</#if>
  			  	
  			  	<input type="button" value="保存" class="yt-seach-btn" onclick="updateVirtualWarehouseName();">
              	</form>
              <DIV class="blank20"></DIV>
</DIV>
</body>
</html>
<script TYPE="text/javascript">
//根据商家ID修改帮到仓库名称仓库
function updateVirtualWarehouseName(){
  var supplier=$("#id").val();
  var warehouseNam=$("#warehouseNam").val();
   if(warehouseNam==""){
      alert("请选择仓库名称");
  }else{
    $.ajax({ 
		type: "post", 
		url: "${BasePath}/yitiansystem/merchants/businessorder/update_merchants_virtualWarehouseName.sc?id=" + supplier+"&warehouseNam="+warehouseNam, 
		success: function(dt){
			if("success"==dt){
			   alert("修改成功!");
			   closewindow();
			   refreshpage();
			}else{
			   alert("修改失败!");
			     closewindow();
			   refreshpage();
			}
		} 
	});
  }
}

</script>
<script TYPE="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script TYPE="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script TYPE="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script TYPE="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script TYPE="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script TYPE="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>