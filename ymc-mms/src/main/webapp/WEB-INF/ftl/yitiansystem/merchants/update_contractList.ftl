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
				  <span><a href="#" class="btn-onselc">修改合同信息</a></span>
				</li>
				
			</ul>
		</div>
 <div class="modify"> 
     <form action="${BasePath}/yitiansystem/merchants/businessorder/update_supplierContract.sc" name="queryForm" id="queryForm" method="post"  enctype="multipart/form-data">
      <input type="hidden" name="supplierSpId" id="supplierSpId" value="<#if supplierId??>${supplierId!''}</#if>">
      <input type="hidden" name="id" id="id" value="<#if contract??&&contract.id??>${contract.id!''}</#if>"> 
       <input type="hidden" name="contract_no" id="contract_no" value="<#if contract??&&contract.contractNo??>${contract.contractNo!''}</#if>"> 
                <table cellpadding="0" cellspacing="0" class="list_table">
                         <tr>
                        <#if supplierId??>
                           <td style="text-align:left;"><label> <span style="color:red;">&nbsp;*</span>商家名称：</label>
                           <#if contract??&&contract.supplier??&&contract.supplier.supplier??>${contract.supplier.supplier!''}</#if>
                            <input type="hidden" name="suplierName" id="suplierName" value="<#if contract??&&contract.supplier??&&contract.supplier.supplier??>${contract.supplier.supplier!''}</#if>"/></td></tr>
                        <#else>
                            <td style="text-align:left;"><label> <span style="color:red;">&nbsp;*</span>商家名称：</label><input type="text" value="<#if contract??&&contract.supplier??&&contract.supplier.supplier??>${contract.supplier.supplier!''}</#if>" readonly="readonly" name="suplierName" id="suplierName"/>
                             <input type="button" value="选择" onclick="tosupper();"  class="yt-seach-btn">
                            &nbsp;&nbsp;<span style="color:red;" id="supplierError" name="supplierError"></span></td></tr>
                        </#if>
                        <tr><td>
                          <label><span style="color:red;">&nbsp;*</span>合同编号：</label><input type="text" name="contractNo" value="<#if contract??&&contract.contractNo??>${contract.contractNo!''}</#if>" id="contractNo"/>
                        &nbsp;&nbsp;<span style="color:red;" id="contractNoError" name="contractNoError"></span></td> </tr>
                        <tr><td>
                         <label><span style="color:red;">&nbsp;*</span>有效日期：</label>
	                                                       从 <input type="text" name="effective" id="effective"  value="<#if contract??&&contract.effectiveDate??>${contract.effectiveDate!''}</#if>"/>&nbsp;&nbsp;&nbsp;
	                                                      到 <input type="text" name="failure" id="failure"  value="<#if contract??&&contract.failureDate??>${contract.failureDate!''}</#if>"/>&nbsp;&nbsp;&nbsp;
                        &nbsp;&nbsp;<span style="color:red;" id="effectiveDateError" name="effectiveDateError"></span></td> </tr>
                        <tr><td>
                          <label><span style="color:red;">&nbsp;*</span>结算方式：</label>
                          <select name="clearingForm" id="clearingForm">
                            <option value="0">请选择结算方式</option>
                            <option value="1" <#if contract??&&contract.clearingForm??&&contract.clearingForm==1>selected</#if>>底价结算</option>
                            <option value="2" <#if contract??&&contract.clearingForm??&&contract.clearingForm==2>selected</#if>>扣点结算</option>
                            <option value="3" <#if contract??&&contract.clearingForm??&&contract.clearingForm==3>selected</#if>>配折结算</option>
                            <option value="4" <#if contract??&&contract.clearingForm??&&contract.clearingForm==4>selected</#if>>促销结算</option>
                          </select>
                        &nbsp;&nbsp;<span style="color:red;" id="clearingFormError" name="clearingFormError"></span></td></tr>
                        <tr><td>
                        <tr><td>
                          <label>上传附件：</label>
                            <span id="fileNames"><#if contract??&&contract.attachment??>${contract.attachment!''}</#if></span>
                            <input type="file"  name="contractFile" id="contractFile">
                            <input type="button" class="yt-seach-btn" style="margin-left:-25px;" value= "上传 " onclick= "checkFileType()"/> 
                          </td></tr>
                        <tr><td>
                         <input id="btn" type="button" value="提交" class="yt-seach-btn" onclick="return update_supplierContract();">
                        </td></tr>
                </table>
       	</form>
    </div>
 <div class="blank20"></div>
</div>
</body>
</html>
<script type="text/javascript">
//选择附件
function checkFileType(){
   var file = $("#contractFile").val();
    if(file!=""){
  		file=file.substring(file.lastIndexOf('\\')+1);
  		    var fileStr=file.substring(file.lastIndexOf('.')+1);
  		    if(fileStr!='doc'&& fileStr!='xls'&& fileStr!='docx'&& fileStr!='xlsx'&& fileStr!='pdf'&& fileStr!='txt'){
      		   alert("上传附件文件格式限制为.doc、.xls、.docx、.xlsx 、pdf、txt");
      		   return false;
  		    }else{
  		      $("#fileNames").html("");
  		    }
     }else{
        alert("请选择上传的文件!");
     }
}

//选择供应商
function tosupper(){
  openwindow('${BasePath}/yitiansystem/merchants/businessorder/to_suppliersp.sc?supplierCode=',600,300,'选择商家');
}

//修改合同信息
function update_supplierContract(){
   //要修改的合同编号
	var contractNo = $("#contractNo").val();
	//加载的合同编号
	var contract_no=$("#contract_no").val();
	//有效日期开始日期
	var  effectiveDate= $("#effective").val();
	//有效日期结束日期
	var failureDate = $("#failure").val();
	//结算方式
	var clearingForm = $("#clearingForm").val();
	//供应商名称
	var supplier = $("#suplierName").val();
	//ID
	var id=$("#contractId").val();
	//供应商id
	var supplierSpId=$("#supplierSpId").val();
	if(supplier=="" ){
		$("#supplierError").html("供应商名称不能为空!");
		return false;
	}else{
	   $("#supplierError").html("");
	}
	if(contractNo=="" ){
		$("#contractNoError").html("姓名不能为空!");
		return false;
	}else{
	   $("#contractNoError").html("");
	}
   if(effectiveDate==""){
		$("#effectiveDateError").html("开始日期不能为空!");
		return false;
	}else{
	    $("#effectiveDateError").html("");
	}
	if(failureDate==""){
		$("#effectiveDateError").html("结束日期不能为空!");
		return false;
	}else{
	    $("#effectiveDateError").html("");
	}
	if(clearingForm==""||clearingForm==0){
		$("#clearingFormError").html("类型不能为空!");
		return false;
	}else{
	   $("#clearingFormError").html("");
	}
	
    $("#contractNo").val(contractNo);
    $("#effective").val(effectiveDate);
    $("#failure").val(failureDate);
    $("#clearingForm").val(clearingForm);
    $("#suplierName").val(supplier);
    
    var file = $("#contractFile").val();
    if(file!=""){
  		file=file.substring(file.lastIndexOf('\\')+1);
  		    var fileStr=file.substring(file.lastIndexOf('.')+1);
  		    if(fileStr!='doc'&& fileStr!='xls'&& fileStr!='docx'&& fileStr!='xlsx'&& fileStr!='pdf'&& fileStr!='txt'){
      		   alert("上传附件文件格式限制为.doc、.xls、.docx、.xlsx 、pdf、txt");
      		   return false;
  		    }
     }
     if(supplierSpId!=""&&contract_no!=""&&contractNo!=contract_no){
		  //判断合同编号是否重复
		   $.ajax({ 
				type: "post", 
				url: "${BasePath}/yitiansystem/merchants/businessorder/exits_contractNo.sc?contractNo=" + contractNo+"&supplierSpId="+supplierSpId, 
				success: function(dt){
					if("sucuess"==dt){
					   $("#contractNoError").html("合同编号不能重复,请重新输入!");
					   $("#contractNo").focus();
					   return false;
					}else{
					     document.queryForm.submit();
					}
				} 
			});
		}else{
		   document.queryForm.submit();
		}
}

$(function(){
$('#effective').calendar({maxDate:'#failure',format:'yyyy-MM-dd HH:mm:ss'});
$('#failure').calendar({minDate:'#effective',format:'yyyy-MM-dd HH:mm:ss'});
});
</script>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
