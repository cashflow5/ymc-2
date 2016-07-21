<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="Keywords" content=" , ,优购网,B网络营销系统,商品管理" />
	<meta name="Description" content=" , ,B网络营销系统-商品管理" />
	<title>优购手机CMS后台管理-优购网</title>
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script> 
	<script language="javascript">
	$(function()
	{
		$('#formContract').validation(); 
	})
	</script>
</head>
<body>
<div class="container">
  	<div class="blank10"></div>
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr"><span>修改合同</span></li>
			</ul>
		</div>
	
	    	<div class="modify">
				<form id="formContract" name="formContract" action="${BasePath}/supply/manage/suppliercontract/upSaveContractFile.sc" method="POST" enctype="multipart/form-data">
					<input type="hidden" name="id" value="${(contract.id)!""}"/>
					<table id="contarctTbl" class="com_modi_table">
					<tbody>
						<tr>
							<th height="63"><span class="star">*</span>
							供应商：
							</th>
							<td>
								<select id="supplier.id" name="supplier.id" class="validate[required]" data-rel="请选择供应商">
									<option value="">------请选择------</option>
									<#list lstSupplier as item>
										<#if item.id == contract.supplier.id>
											<option value="${(item.id)!""}" selected>${(item.supplier)!""}</option>
										<#else>
											<option value="${(item.id)!""}">${(item.supplier)!""}</option>
										</#if>
									</#list>
								</select>
							</td>
						</tr>
						<tr>
							<th height="63"><span class="star">*</span>
							合同编号：
							</th>
							<td style="padding-bottom:10px;"><input type="text" id="contractNo" name="contractNo" class="validate[required]" data-rel="请输入合同编号" maxlength="50" style="width:200px;" value="${(contract.contractNo)!""}"/></td>
							<td><font color="gray">(25个汉字或者50个字母以内)</font></td>
						</tr>
						
						
						<tr>
							<th height="63"><span class="star">*</span>
							有效日期：
							</th>
							<td>
							<input id="startDate" name="startDate" class="inputtxt validate[required]" type="text" style="width:120px;" data-rel="请选择有效开始日期" readonly="readonly" size="16" value="<#if (contract.effectiveDate)??>${(contract.effectiveDate)?string('yyyy-MM-dd')}</#if>"/>
					 		&nbsp;至&nbsp;
							<input id="endDate" name="endDate" class="inputtxt validate[required]" type="text" style="width:120px;" data-rel="请选择有效结束日期" readonly="readonly" size="16" value="<#if (contract.failureDate)??>${(contract.failureDate)?string('yyyy-MM-dd')}</#if>"/>
		                	</td>
						</tr>
						
						
						<tr>
							<th height="63"><span class="star">*</span>
							结算方式：
							</th>
							<td>
						        <select id="clearingForm" name="clearingForm" class="validate[required]" data-rel="请选择结算方式">
			        				<option value="">---请选择---</option>
			        				<#if contract.clearingForm == 1>
						        	<option value="1" selected>固定成本</option>
			        				<option value="2">代扣结算</option>
			        				<option value="3">配折结算</option>
			        				<#elseif contract.clearingForm == 2>
						        	<option value="1">固定成本</option>
			        				<option value="2" selected>代扣结算</option>
			        				<option value="3">配折结算</option>
			        				<#elseif contract.clearingForm == 3>
						        	<option value="1">固定成本</option>
			        				<option value="2">代扣结算</option>
			        				<option value="3" selected>配折结算</option>
			        				</#if>
			        			</select>
							</td>
						</tr>
						<tr>
							<th height="63"><span class="star">*</span>
							上传附件：
							</th>
							<td>
							<input type="file" id="contractFile" name="contractFile" value="${(contract.attachment)!""}"/>
							</td>
						</tr>
						</tbody>
						</table>
		         </form>
		    </div>
	  </div>
</div>	  
<script type="text/javascript"> 
$(function(){
$('#startDate').calendar({maxDate:'#endDate'});
$('#endDate').calendar({minDate:'#startDate'});
});
</script>
</body>
<#if success??>
<script language="javascript">
	tips("操作成功！");
	refreshpage();
</script>
</#if>
</html>
