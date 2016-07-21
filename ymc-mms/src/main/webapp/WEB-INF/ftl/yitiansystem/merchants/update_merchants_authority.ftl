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
				  <span><a href="#" class="btn-onselc">修改商家资源信息</a></span>
				</li>
				
			</ul>
		</div>
 <div class="modify"> 
     <form action="${BasePath}/yitiansystem/merchants/businessorder/update_merchants_authority.sc" name="queryForm" id="queryForm" method="post"  enctype="multipart/form-data">
      <input type="hidden" name="id" id="id" value="<#if authority??&&authority.id??>${authority.id!''}</#if>"> 
                <table cellpadding="0" cellspacing="0" class="list_table">
                        <tr><td style="text-align:right">
                           <span style="color:red;">&nbsp;*</span>资源名称：
                         </td><td>
                        <input type="text" value="<#if authority??&&authority.authrityName??>${authority.authrityName!''}</#if>" name="authrityName" id="authrityName"/>
                            &nbsp;&nbsp;<span style="color:red;" id="authrityNameError"></span></td></tr>
                             <tr><td style="text-align:right">
                           <span style="color:red;">&nbsp;*</span>资源路径：
                         </td><td>
                        <input type="text"  size=50 value="<#if authority??&&authority.authrityURL??>${authority.authrityURL!''}</#if>" name="authrityURL" id="authrityURL"/>
                            &nbsp;&nbsp;<span style="color:red;" id="authrityURLError"></span></td></tr>
                       
                       <tr><td style="text-align:right">
                           <span style="color:red;">&nbsp;*</span>所属模块：
                         </td><td>
                        <select name="parentId" id="parentId"/>
                          <option value="">请选择模块</option>
                          <#if merchantsAuthority??>
                    	    <#list merchantsAuthority as item >
                          		<option value="${item['id']!""}" <#if item['id']==authority['parentId']>selected="selected"</#if>>${item['authrityName']!""}</option>
                          	</#list>
                          </#if>
                        <select>
                            &nbsp;&nbsp;<span style="color:red;" id="parentIdError"></span></td></tr>
                       
                        <tr><td style="text-align:right">
                          <span style="color:red;">&nbsp;*</span>排序号：
                           </td><td>
                          <input type="text" name="sortNo" value="<#if authority??&&authority.sortNo??>${authority.sortNo!''}</#if>" id="sortNo"/>
                        &nbsp;&nbsp;<span style="color:red;" id="sortNoError"></span></td> </tr>
                        <tr><td style="text-align:right">  备注：
                           </td><td>
                                  <textarea id="remark" name="remark" cols=25 rows=6><#if authority??&&authority.remark??>${authority.remark!''}</#if></textarea>
                          </td></tr>
                        <tr><td><input type="hidden" name="authrityModule" value="<#if authority??&&authority.authrityModule??>${authority.authrityModule!''}</#if>"/>
                        <input type="hidden" name="createTime" value="<#if authority??&&authority.createTime??>${authority.createTime!''}</#if>"/></td><td>
                         <input id="btn" type="button" value="提交" class="yt-seach-btn" onclick="return update_merchantsAuthority();">
                        </td></tr>
                </table>
       	</form>
    </div>
 <div class="blank20"></div>
</div>
</body>
</html>
<script type="text/javascript">
//添加资源信息
function update_merchantsAuthority(){
   //名称
	var authrityName = $("#authrityName").val();
	//路径
	var authrityURL = $("#authrityURL").val();
	//所属模块
	var authrityModule = $("#authrityModule").val();
	//排序号
	var sortNo = $("#sortNo").val();
	//备注
	var supplier = $("#suplierName").val();
	//ID
	var id=$("#id").val();
	if(authrityName=="" ){
		$("#authrityNameError").html("资源名称不能为空!");
		return false;
	}else{
	   $("#authrityNameError").html("");
	}
	if(authrityURL=="" ){
		$("#authrityURLError").html("资源路径不能为空!");
		return false;
	}else{
	   $("#authrityURLError").html("");
	}
	if(parentId=="" ){
		$("#parentIdError").html("所属模块不能为空!");
		return false;
	}else{
	   $("#parentIdError").html("");
	}
	if(sortNo=="" ){
		$("#sortNoError").html("排序号不能为空!");
		return false;
	}else{
	   $("#sortNoError").html("");
	}
    $("#authrityName").val(authrityName);
    $("#sortNo").val(sortNo);
    $("#id").val(id);
	 document.queryForm.submit();
}

</script>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
