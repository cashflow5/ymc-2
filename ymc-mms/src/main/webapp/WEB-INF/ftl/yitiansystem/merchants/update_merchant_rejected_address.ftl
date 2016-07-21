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
				  <span><a href="#" class="btn-onselc">修改退货地址信息</a></span>
				</li>
				
			</ul>
		</div>
 <div class="modify"> 
     <form action="${BasePath}/yitiansystem/merchants/businessorder/save_merchant_reject_address.sc" name="queryForm" id="queryForm" method="post"  enctype="multipart/form-data">
               <input type="hidden"  name="id" id="id" value="<#if reject??&&reject.id??>${reject.id!''}</#if>"/>
               <input type="hidden" id="warehouseArea" name="warehouseArea">
                <table cellpadding="0" cellspacing="0" class="list_table">
                        <tr><td style="width:120px;margin-left:150px;text-align:right">
                           <span style="color:red;">&nbsp;*</span>商家名称：
                         </td><td>
                         <input type="text"  name="supplierName" id="supplierName"  readonly="readonly" value="<#if reject??&&(reject.supplierName)??>${(reject.supplierName)!''}</#if>">
                            <input type="hidden"  name="supplierCode" id="supplierCode" value="<#if reject??&&(reject.supplierCode)??>${(reject.supplierCode)!''}</#if>"/>
                            <input type="hidden"  name="supplierId" id="supplierId" value="<#if reject??&&(reject.supplierId)??>${(reject.supplierId)!''}</#if>"/>
                           <!--input type="button" value="......" onclick="addSupplierName();"/-->
                           &nbsp;&nbsp;<span style="color:red;" id="supplierError"></span>
                          </td></tr>
                        <tr><td  style="width:120px;margin-left:150px;text-align:right">
                           <span style="color:red;">&nbsp;*</span>收货人姓名：
                         </td><td>
                         <input type="text" name="consigneeName" id="consigneeName" value="<#if reject??&&reject.consigneeName??>${reject.consigneeName!''}</#if>"/>
                            &nbsp;&nbsp;<span style="color:red;" id="consigneeNameError"></span>
                        </td></tr>
                       <tr><td style="width:120px;margin-left:150px;text-align:right">
                           <span style="color:red;">&nbsp;*</span>收货人电话：
                         </td><td>
                        <input type="text" name="consigneePhone" id="consigneePhone" maxlength="20" value="<#if reject??&&reject.consigneePhone??>${reject.consigneePhone!''}</#if>"/>
                            &nbsp;&nbsp;<span style="color:#666666;" id="consigneePhoneError">只允许填写数字和“-”</span>
                        </td></tr>   
                        <tr><td  style="width:120px;margin-left:150px;text-align:right">
                           <span style="color:red;">&nbsp;*</span>优购客服电话：</td><td>
                            <input type="text" name="consigneeTell" id="consigneeTell" maxlength="20" value="<#if reject??&&reject.consigneeTell??>${reject.consigneeTell!''}</#if>"/>
                            &nbsp;&nbsp;<span style="color:#666666;" id="consigneeTellError">只允许填写数字和“-”</span>
                        </td></tr>   
                        <tr><td style="width:120px;margin-left:150px;text-align:right">
                           <span style="color:red;">&nbsp;*</span>收货人邮编：</td><td>
                            <input type="text" name="warehousePostcode" id="warehousePostcode" value="<#if reject??&&reject.warehousePostcode??>${reject.warehousePostcode!''}</#if>"/>
                            &nbsp;&nbsp;<span style="color:red;" id="warehousePostcodeError"></span>
                         </td></tr>   
                          <tr><td style="width:120px;margin-left:150px;text-align:right;">
                           <span style="color:red;">&nbsp;*</span>收货人地区：</td><td>
                           <select class="g-select" id="province" onclick="checkTwo();" onchange="checkTwo();" name="province" style="width:100px">
						 <#if areaList??>
						    <option value="-1">请选择省份</option>
						    <#list areaList as item>
						      <option value="${item['no']!''}" <#if item['name']??&&province??&&item['name']==province>selected</#if>>${item['name']!''}</option>
						    </#list>
						 </#if>
					   </select>
					<select class="g-select" id="city" onclick="checkThree();" onchange="checkThree();" name="city" style="width:100px"></select>
					<select class="g-select" id="hometown" name="hometown" style="width:100px" ></select>
					<span style="color:red;" id="provinceError"></span>
                        </td></tr>
                           
                        </td></tr>
                          <tr><td style="width:200px;margin-left:150px;text-align:right;">
                           <span style="color:red;">&nbsp;*</span>收货人地址：</td><td>
                            <input type="text" name="warehouseAdress" id="warehouseAdress"  value="<#if reject??&&reject.warehouseAdress??>${reject.warehouseAdress!''}</#if>" style="width:200px;"/>
                            &nbsp;&nbsp;<span style="color:red;" id="warehouseAdressError"></span>
                        </td></tr>   
                          <tr><td style="text-align:right"></td><td>
                           <input id="btn" type="button" value="提交" class="yt-seach-btn" onclick="return updateMerchantRejectAddress();">&nbsp;
                            <input id="btn" type="button" value="重置" class="yt-seach-btn" onclick="resertInput();">
                        </td></tr>
                </table>
       	</form>
    </div>
 <div class="blank20"></div>
</div>
</body>
</html>
<script type="text/javascript">
//重置文本框内容
function resertInput(){
  $(":input[type=text]").val("");
}
$(window).ready(function(){
    //定义默认数据
	var ar = ["请选择省份","请选择城市","请选择区县"];
	//初始化
	$("<option value='-1'>"+ar[1]+"</option>").appendTo($("#city"));
	$("<option value='-1'>"+ar[2]+"</option>").appendTo($("#hometown"));
	var id=$("#id").val();
	if(id!=""){
		checkTwo(id);
		checkThree(id);
	}
});
function checkTwo(id){
  var province=$("#province").val();
  if(province!=""&&province!="-1"){
	//根据省信息 加载市信息
	$.ajax({ 
		type: "post", 
		async:false,
		url: "${BasePath}/yitiansystem/merchants/businessorder/queryChildById.sc?level=2&no=" + province+"&id="+id, 
		success: function(result){
			if(result!=""){
			    $("#city").empty();
				result = result.replace(/(^\s*)|(\s*$)/g,'');
				var node = eval("("+result+")");
				for (i=0;i<node.length;i++){
					var area = node[i];
					var no=area.no;
					var name=area.name;
					var code=area.code;
					if(name==code){
					 $("<option value='"+no+"' selected>"+name+"</option>").appendTo($("#city"));
					}else{
					 $("<option value='"+no+"'>"+name+"</option>").appendTo($("#city"));
					}
				}
			}
		} 
	});
   }
}
function checkThree(id){
  var city=$("#city").find("option:selected").val();
  if(city!=""&&city!="-1"){
	//根据省信息 加载市信息
	$.ajax({ 
		type: "post", 
		async:false,
		url: "${BasePath}/yitiansystem/merchants/businessorder/queryChildById.sc?level=3&no=" + city+"&id="+id, 
		success: function(result){
			if(result!=""){
			    $("#hometown").empty();
				result = result.replace(/(^\s*)|(\s*$)/g,'');
				var node = eval("("+result+")");
				for (i=0;i<node.length;i++){
					var area = node[i];
					var no=area.no;
					var name=area.name;
					var code=area.code;
					if(name==code){
					 $("<option value='"+no+"' selected>"+name+"</option>").appendTo($("#hometown"));
					}else{
					 $("<option value='"+no+"'>"+name+"</option>").appendTo($("#hometown"));
					}
				}
			}
		} 
	});
   }
}
//添加商家名称
function addSupplierName(){
  openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_addSupplierName_list.sc",600,400,"添加商家名称");
}
//限制手机号码只能为输入数字
$("#consigneePhone,#consigneeTell").keydown(function(event){
	var keycode = event.which;
	if (keycode == 8) {//Backspace
		return true;
	} else if (keycode == 46) {//Delete
		return true;
	} else if (keycode == 173 || keycode == 109) {//-
		return true;
	} else if ((keycode >= 48 && keycode <= 57) || (keycode >= 96 && keycode <= 105)) {//0123456789
		return true;
	} else {
		return false;//Other
	}
});
//修改退货地址信息
function updateMerchantRejectAddress(){
    //商家名称
	var supplier = $("#supplierName").val();
	//收货人姓名
	var consigneeName = $("#consigneeName").val();
	//收货人手机
	var consigneePhone = $("#consigneePhone").val();
	//收货人电话
	var consigneeTell = $("#consigneeTell").val();
	//收货人邮编
	var warehousePostcode = $("#warehousePostcode").val();
	//收货人地址
	var warehouseAdress = $("#warehouseAdress").val();
	//省
	var province=$("#province").val();
	//市
	var city=$("#city").val();
	//区
	var hometown=$("#hometown").val();
	if(supplier=="" ){
		$("#supplierError").html("商家名称不能为空!");
		$("#supplierName").focus();
		return false;
	}else{
	   $("#supplierError").html("");
	}
	if(consigneeName=="" ){
		$("#consigneeNameError").html("收货人姓名不能为空!");
		$("#supplierName").focus();
		return false;
	}else{
	   $("#consigneeNameError").html("");
	}

	if (consigneePhone == "") {
		$("#consigneePhoneError").html("收货人电话不能为空!");
		$("#consigneePhone").focus();
		return false;
	} else if (!/^[0-9]{1}([0-9]|-){3,}[0-9]{1}$/g.test(consigneePhone)) {
		$("#consigneePhoneError").html("请输入正确的电话格式, 只允许包含数字和“-”, 并以数字开头和结尾");
		$("#consigneePhoneError").attr('style', 'color:red;');
		$("#consigneePhone").focus();
		return false;
	} else {
	   $("#consigneePhoneError").attr('style', 'color:#666666;');
	   $("#consigneePhoneError").html("只允许填写数字和“-”");
	}
	
	if(consigneeTell==""){
		$("#consigneeTellError").html("优购售后电话不能为空!");
		$("#consigneeTell").focus();
		return false;
	}else{
	   $("#consigneeTellError").html("只允许填写数字和“-”");
	}
	
	if(warehousePostcode=="" ){
		$("#warehousePostcodeError").html("收货人邮编不能为空!");
		$("#warehousePostcode").focus();
		return false;
	}else{
	   $("#warehousePostcodeError").html("");
	}
	if(province=="-1"||province==""){
        $("#provinceError").html("省份不能为空!");
        return false;
     }else{
       $("#provinceError").html("");
     }
     if(city=="-1"||city==""){
        $("#provinceError").html("城市不能为空!");
        return false;
     }else{
       $("#provinceError").html("");
     }
     if(hometown=="-1"||hometown==""){
        $("#provinceError").html("地区不能为空!");
        return false;
     }else{
       $("#provinceError").html("");
     }
	if(warehouseAdress=="" ){
		$("#warehouseAdressError").html("收货人地址不能为空!");
		$("#warehouseAdress").focus();
		return false;
	}else{
	   $("#warehouseAdressError").html("");
	}
	 var provinceText=$("#province").find("option:selected").text();//发货人省
     var cityText=$("#city").find("option:selected").text();//发货人市
     var hometownText=$("#hometown").find("option:selected").text();//发货人区
     var prh=provinceText+"-"+cityText+"-"+hometownText;//拼接省市区
      $("#warehouseArea").val(prh);
	 document.queryForm.submit();
}
</script>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
