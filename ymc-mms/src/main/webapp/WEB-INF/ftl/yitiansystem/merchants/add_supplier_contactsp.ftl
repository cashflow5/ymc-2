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
<!-- 日期控件 -->
<script src="${BasePath}/js/common/form/datepicker/WdatePicker.js" type="text/javascript"></script>
</head>
<script type="text/javascript">

</script>
<body>
<div class="container">
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr">
				  <span><a href="#" class="btn-onselc">添加联系人信息</a></span>
				</li>
			</ul>
		</div>
 <div class="modify"> 
     <form action="${BasePath}/yitiansystem/merchants/businessorder/add_linkmanList.sc" name="queryForm" id="queryForm" method="post"> 
                <table cellpadding="0" cellspacing="0" class="list_table">
                  <input type="hidden" name="supplierSpId" id="supplierSpId" value="<#if supplierId??>${supplierId!''}</#if>">
                  <input type="hidden" name="flag" id="flag" value="<#if flag??>${flag!''}</#if>">
                       
                        <#if supplierId??>
                            <tr>
                            	<td style="text-align:right;">
                            		<label> <span style="color:red;">&nbsp;*</span>商家名称：</label>
                            	</td>
                          	 	<td>
	                           		<#if supplierName??>${supplierName!''}</#if>
	                            	<input type="hidden" name="suplierName" id="suplierName" value="<#if supplierName??>${supplierName!''}</#if>"/>
                            	</td>
                         	</tr>
                        <#else>
                             <tr>
                             	<td style="text-align:right;">
                             		<label> <span style="color:red;">&nbsp;*</span>商家名称：</label>
                             	</td>
	                            <td>
	                            	<input type="text" readonly="readonly" name="suplierName" id="suplierName"/>
	                             	<input type="hidden" name="updateAndAdd" id="updateAndAdd" value="1">
	                             	<input type="button" value="选择" onclick="tosupper();"  class="yt-seach-btn">
	                            	&nbsp;&nbsp;<span style="color:red;" id="supplierError" name="supplierError"></span>
	                            </td>
                           	</tr>
                        </#if>
                        	<tr>
                        		<td style="text-align:right;">
                          			<label> <span style="color:red;">&nbsp;*</span>姓名：</label>
                          		</td>
                          		<td>
                          			<input type="text" name="contact" id="contact"/>
                        			&nbsp;&nbsp;<span style="color:red;" id="contactError" name="contactError"></span>
                        		</td> 
                        	</tr>
                        	<tr>
                        		<td style="text-align:right;">
                         			<label> <span style="color:red;">&nbsp;*</span>类型：</label>
                         		</td>
                         		<td>
		                         <select name="type" id="type">
		                           <option value="0">请选择类型</option>
		                           <option value="1">业务</option>
		                           <option value="2">售后</option>
		                           <option value="6">店铺负责人</option>
		                           <option value="4">财务</option>
		                           <option value="5">技术</option>
		                         </select>
		                         &nbsp;&nbsp;<span style="color:red;" id="typeError" name="typeError"></span>
		                         </td>
		                      </tr>   
                        	<tr>
                        		<td style="text-align:right;">
                          			<label><span style="color:red;">&nbsp;*</span>电话：</label>
                          		</td>
                          		<td><input type="text" name="telePhone" id="telePhone"/>
                        			&nbsp;&nbsp;<span style="color:red;" id="telePhoneError" name="telePhoneError"></span>
                        		</td>
                        	</tr>
                        	<tr>
                        		<td style="text-align:right;">
                         		 <label><span style="color:red;">&nbsp;*</span>电子邮箱：</label>
                         		</td>
                         		<td>
                         			<input type="text" name="email" id="email"/>
                           			&nbsp;&nbsp;<span style="color:red;" id="emailError" name="emailError"></span>
                           		</td>
                           	</tr>
                        	<tr>
                        		<td style="text-align:right;">
                          			<label>地址：</label>
                          		</td>
	                          	<td>
	                          		<input type="text"  style="width:250px;" name="address" id="address"/>
	                        		&nbsp;&nbsp;<span style="color:red;" id="addressError" name="addressError"></span>
	                        	</td>
                        	</tr>
                        	<tr>
                        		<td>&nbsp;</td>
                         		<td>
                         			<input id="btn" type="button" value="提交" class="yt-seach-btn" onclick="return addLinkmanList();">
                        		</td>
                        	</tr>
                </table>
       	</form>
    </div>
 <div class="blank20"></div>
</div>
</body>
</html>
<script type="text/javascript">
//选择供应商
function tosupper(){
  openwindow('${BasePath}/yitiansystem/merchants/businessorder/to_suppliersp.sc',600,500,'选择供应商');
}
//添加联系人信息
function addLinkmanList(){
   //姓名
	var contact = $("#contact").val();
	//类型
	var  type= $("#type").val();
	//电话
	var telePhone = $("#telePhone").val();
	//地址
	var address = $("#address").val();
	//email
	var email = $("#email").val();
	//供应商名称
	var supplier = $("#suplierName").val();
	if(supplier=="" ){
		$("#supplierError").html("供应商名称不能为空!");
		return false;
	}else{
	   $("#supplierError").html("");
	}
	if(contact=="" ){
		$("#contactError").html("姓名不能为空!");
		return false;
	}else{
	   $("#contactError").html("");
	}
   if(type==""||type==0){
		$("#typeError").html("类型不能为空!");
		return false;
	}else{
	   $("#typeError").html("");
	}
	
   if(telePhone==""){
		$("#telePhoneError").html("电话不能为空!");
		return false;
	}else{
	    $("#telePhoneError").html("");
	}
	if(email==""){
		$("#emailError").html("电子邮箱不能为空!");
		return false;
	}else{
	    $("#emailError").html("");
	}
	//判断联系人是否已存在
	$.ajax({ 
			type: "post", 
			data:$("#queryForm").serialize(),
			url: "${BasePath}/yitiansystem/merchants/businessorder/exist_linkman.sc", 
			success: function(dt){
				if( dt && "true"==dt.success){
				   		$("#supplierError").html(dt.message);
				  		return false;
				}else if( dt && "500"==dt.success){
				   		$("#supplierError").html(dt.message);
				  		return false;  		
				}else{
					    $("#contact").val(contact);
					    $("#type").val(type);
					    $("#address").val(address);
					    $("#suplierName").val(supplier);
					    document.queryForm.submit();
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
