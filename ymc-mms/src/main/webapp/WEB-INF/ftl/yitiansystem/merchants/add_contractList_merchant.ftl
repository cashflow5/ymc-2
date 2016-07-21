<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
  <link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
  <link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
  <link rel="stylesheet" type="text/css" href="${BasePath}/css/yitiansystem/merchants/supplier-contracts.css"/>
  <link rel="stylesheet" type="text/css" href="${BasePath}/css/yitiansystem/merchants/supplier_manage.css"/>
  <script type="text/javascript" src="${BasePath}/js/jquery-1.8.3.min.js"></script>    
  <script type="text/javascript" src="${BasePath}/webuploader/webuploader.js?version=2.5"></script>
  <link rel="stylesheet" type="text/css" href="${BasePath}/webuploader/webuploader.css?version=2.5"/>
  <style>
  	.loading{display:none}
	
  </style> 
  <title>优购商城--商家后台</title>
  <!-- 日期控件 -->
  <script src="${BasePath}/js/common/form/datepicker/WdatePicker.js" type="text/javascript"></script>
  </head>
  <script type="text/javascript"></script>
<body>
  <div class="container">
        <!--工具栏start-->
        <div class="list_content">
            <div class="top clearfix">
                <ul class="tab">
                    <li class="curr">
                        <span><a href="#" class="btn-onselc"><#if supplierContract??>修改招商供应商合同<#else>添加招商供应商合同</#if></a></span>
                    </li>
                </ul>
            	<div style="padding-left:576px;padding-top:5px;">注意：文件上传格式必须为doc、xls、docx、xlsx、pdf、txt、jpg、bmp、png、jpeg ,或者打包rar格式上传。</div>
            </div>
            <div class="modify">
            	<form  name="queryForm" id="queryForm" method="post">
				<input type="hidden" name="supplierType" id="supplierType" value="招商供应商">
                <div class="form-box">
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>商家名称：</label>
                            <#if supplierContract??&&supplierContract.id??>
							<span>${(supplierContract.supplier)!'' }</span>
                            <input type="hidden" name="supplier" id="supplier" value="${(supplierContract.supplier)!'' }" > 
							<#else>
                            <input class="supplier-query-text" readonly="readonly" style="width:250px" type="text" name="supplier" id="supplier" value="<#if supplier??>${(supplier.supplier)!'' }</#if>">
							<a class="yg-btn-gray-2" href="javascript:tosupper();">选择</a>
							</#if>
                        	<input type="hidden" name="status" value="${(supplierContract.status)!'' }" id="status"/>
                        	<input type="hidden" name="bindStatus" value="${(supplierContract.bindStatus)!'' }" id="bindStatus"/>
							<input type="hidden" name="id" value="<#if supplierContract??>${supplierContract.id!''}</#if>">
          					<input type="hidden" name="supplierId" id="supplierSpId" value="<#if supplier??>${supplier.id!''}</#if>">                        
          				</div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">合同编号：</label>
                            <span><#if supplierContract??>${(supplierContract.contractNo)!'' }<#else><#if contractNo??>${contractNo}</#if></#if></span>
                        	<input type="hidden" name="contractNo" id="contractNo" value="<#if supplierContract??>${(supplierContract.contractNo)!'' }<#else><#if contractNo??>${contractNo}</#if></#if>">
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">合作模式：</label>
                            <#list statics['com.belle.other.model.pojo.SupplierSp$CooperationModel'].values()?sort_by('description')?reverse as item>
			                   	<#if supplier??&&supplier.isInputYougouWarehouse??&&supplier.isInputYougouWarehouse==item.ordinal()><span>${item.description!''}</span></#if>
			                </#list>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>合同起止时间：</label>
                            <input class="calendar" name="effectiveDate" id="effective" readonly="readonly" value="<#if supplierContract??>${supplierContract.effectiveDate!''}</#if>" type="text">
							至 <input class="calendar" name="failureDate" id="failure" readonly="readonly" value="<#if supplierContract??>${supplierContract.failureDate!''}</#if>" type="text">
							<input type="checkbox" class="forever" id="foreverFor"  onchange="setEndForever();"/><label for="foreverFor_1">永久</label>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
						<div class="supplier-query-left">
                            <label for="" class="supplier-query-title">成本账套名称：</label>
                            <#if costSetofBooksList??>
			                	<#list costSetofBooksList as item>
			                   	<#if supplier??&&supplier.setOfBooksCode??&&supplier.setOfBooksCode==item.setOfBooksCode><span>${item.setOfBooksName!''}</span></#if>
			                 	</#list>
		                   	</#if>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>合同电子版：</label>
                            <a class="yg-btn-gray-2 yg-btn-update" id="filePicker1">上传文件</a>
        					<div id="loading1" class="loading"><img src="${BasePath}/images/loading.gif"></div>
							<div id="contract_attachment" class="inline-block">
                            <#if supplierContract??&&supplierContract.attachmentList??&&(supplierContract.attachmentList?size>0)>
				              <#list supplierContract.attachmentList as item >
				                <#if item['attachmentType']=='1'>
									<div class="attachment_item">
				                    <input name="contract_attachment" value="1;${item['attachmentName']!''};${item['attachmentRealName']!''}" type="hidden">
				                    <span class="supplier-query-cont Blue ml5">${item['attachmentName']!''}</span><a href="javascript:void(0);"  class="link-del ml10 Blue">删除</a>
				                	</div>
									</#if>
				              </#list>
				          	</#if>
							</div>
                        </div>
                    </div>
					<div class="supplier-query-wrap clearfix">	
						<div class="supplier-query-left">
                            <label for="" class="supplier-query-title">销售发票开具方：</label> 
                        	<#if supplier??><#if supplier.isInvoice == 0><span>优购</span></#if></#if>
                            <#if supplier??><#if supplier.isInvoice == 1><span>商家</span></#if></#if>
						</div>
						<div class="supplier-query-right">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>结算方式：</label>
                            <select class="supplier-query-select" style="width:142px"  name="clearingForm" id="clearingForm">
						        <option value="0" <#if supplierContract??&&supplierContract.clearingForm='0'>selected="selected"</#if>>请选择结算方式</option>
						        <option value="1" <#if supplierContract??&&supplierContract.clearingForm='1'>selected="selected"</#if>>底价结算</option>
						        <option value="2" <#if supplierContract??&&supplierContract.clearingForm='2'>selected="selected"</#if>>扣点结算</option>
						        <option value="3" <#if supplierContract??&&supplierContract.clearingForm='3'>selected="selected"</#if>>配折结算</option>
						        <option value="4" <#if supplierContract??&&supplierContract.clearingForm='4'>selected="selected"</#if>>促销结算</option>
						    </select>
                        </div>
                        
                        
                    </div>
                    <div class="supplier-query-wrap clearfix">
                       <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">使用优购WMS：</label>
                            <#if supplier??><#if supplier.isUseYougouWms == 0><span>否</span></#if></#if>
                            <#if supplier??><#if supplier.isUseYougouWms == 1><span>是</span></#if></#if>
                        </div> 
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>使用ERP系统对接：</label>
                            <input type="radio" class="supplier-query-radio" name="isUseERP" value="1" <#if supplierContract?? && supplierContract.isUseERP??><#if supplierContract.isUseERP == '1'>checked</#if></#if>/><label for="y_1" class="supplier-query-title w25">是</label>
                			<input type="radio" class="supplier-query-radio ml50" name="isUseERP" value="0" <#if supplierContract?? && supplierContract.isUseERP?? && supplierContract.isUseERP == '0'>checked</#if>/><label for="n_1">否</label>
                        </div> 
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                          <#--  <label for="" class="supplier-query-title">优惠券分摊比例：</label>
							<#if supplier?? && supplier.couponsAllocationProportion??><span>${supplier.couponsAllocationProportion}%</span></#if> -->
							 <label for="" class="supplier-query-title">卡券最高分摊：</label>
							 <#if supplierExpand?? && supplierExpand.maxCouponAmount??><span>${supplierExpand.maxCouponAmount!0}元</span></#if>
                     
                        </div>
						<div class="supplier-query-right">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>是否需续签：</label>
                            <input type="radio" class="supplier-query-radio" name="isNeedRenew"  value="1" <#if supplierContract??&&supplierContract.isNeedRenew??><#if supplierContract.isNeedRenew == '1'>checked</#if></#if>/><label for="y_1" class="supplier-query-title w25">是</label>
                			<input type="radio" class="supplier-query-radio ml50" name="isNeedRenew"  value="0" <#if supplierContract??&&supplierContract.isNeedRenew?? && supplierContract.isNeedRenew == '0'>checked</#if>/><label for="n_1">否</label>
                        </div> 
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>需缴纳保证金：</label>
							<#if supplierContract?? && supplierContract.depositStatus?? && supplierContract.depositStatus != '' &&(supplierContract.depositStatus != '1') >
							<input type="radio" class="supplier-query-radio" disabled  name="isNeedDeposit" value="1" <#if supplierContract?? && supplierContract.isNeedDeposit??><#if supplierContract.isNeedDeposit == '1'>checked</#if></#if>/><label for="y_1" class="supplier-query-title w25">是</label>
                			<input type="radio" class="supplier-query-radio ml50"  disabled name="isNeedDeposit" value="0" <#if supplierContract?? && supplierContract.isNeedDeposit?? && supplierContract.isNeedDeposit == '0'>checked</#if>/><label for="n_1">否</label>
							<#else>			
                             <input type="radio" class="supplier-query-radio"  name="isNeedDeposit" value="1" <#if supplierContract?? && supplierContract.isNeedDeposit??><#if supplierContract.isNeedDeposit == '1'>checked</#if></#if>/><label for="y_1" class="supplier-query-title w25">是</label>
                			<input type="radio" class="supplier-query-radio ml50" name="isNeedDeposit" value="0" <#if supplierContract?? && supplierContract.isNeedDeposit?? && supplierContract.isNeedDeposit == '0'>checked</#if>/><label for="n_1">否</label>
							</#if>			
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>需缴纳平台使用费：</label>
                            <#if supplierContract?? && supplierContract.useFeeStatus?? && supplierContract.useFeeStatus != '' &&(supplierContract.useFeeStatus != '1')> 
							<input type="radio" class="supplier-query-radio" disabled  name="isNeedUseFee" value="1" <#if supplierContract?? && supplierContract.isNeedUseFee??><#if supplierContract.isNeedUseFee == '1'>checked</#if></#if>/><label for="y_1" class="supplier-query-title w25">是</label>
                			<input type="radio" class="supplier-query-radio ml50" disabled  name="isNeedUseFee" value="0" <#if supplierContract?? && supplierContract.isNeedUseFee?? && supplierContract.isNeedUseFee == '0'>checked</#if>/><label for="n_1">否</label>
                        	<#else>	
							<input type="radio" class="supplier-query-radio"  name="isNeedUseFee" value="1" <#if supplierContract?? && supplierContract.isNeedUseFee??><#if supplierContract.isNeedUseFee == '1'>checked</#if></#if>/><label for="y_1" class="supplier-query-title w25">是</label>
                			<input type="radio" class="supplier-query-radio ml50"  name="isNeedUseFee" value="0" <#if supplierContract?? && supplierContract.isNeedUseFee?? && supplierContract.isNeedUseFee == '0'>checked</#if>/><label for="n_1">否</label>
                        	</#if>		
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix" id="feeAll">
                        <div class="supplier-query-left" >
							<div id="feeDeposit">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>保证金金额：</label>
                            <input type="text" class="supplier-query-text w136" maxlength="10"  id="deposit" <#if supplierContract?? && supplierContract.depositStatus?? && supplierContract.depositStatus!= '' &&(supplierContract.depositStatus != '1')>readonly="readonly"</#if>  name="deposit"  value="<#if supplierContract?? && supplierContract.deposit??>${(supplierContract.deposit)?string("#.##")}</#if>"/> 元
                        	</div>
							</div>
                        <div class="supplier-query-right" >
                            <div id="feeUse">
							<label for="" class="supplier-query-title"><span class="Red">*</span>平台使用费金额：</label>
                            <input type="text" class="supplier-query-text w136" maxlength="10" <#if supplierContract?? && supplierContract.useFeeStatus?? && supplierContract.useFeeStatus!= '' &&(supplierContract.useFeeStatus != '1')>readonly="readonly"</#if> id="useFee"  name="useFee"  value="<#if supplierContract?? && supplierContract.useFee??>${(supplierContract.useFee)?string("#.##")}</#if>"/> 元
                        	</div>
						</div>
                    </div>  
                    <div class="supplier-query-wrap clearfix">
                        
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title">申报人：</label>
                            <input class="supplier-query-text w136" type="text" maxlength="30" name="declarant" id="declarant" value="<#if supplierContract??>${supplierContract.declarant!''}</#if>">
						</div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-main">
                            <label for="" class="supplier-query-title">备注：</label>
                            <input type="text" class="supplier-query-text w600" name="remark" id="remark" maxlength="200"  value="<#if supplierContract?? && supplierContract.remark??>${supplierContract.remark}</#if>" />
                        </div>
                    </div>    
                </div>
                <#import "add_trademark.ftl" as page>
                <@page.trademark/>
                <div class="btn-box">
                    <a href="javascript:" onClick="add_supplierContract(this,'false');" class="yg-btn-gray">保存</a>
                    <#if (!supplierContract??) || (supplierContract.status != '8')>
                    <a  href="javascript:" onClick="add_supplierContract(this,'true');" class="yg-btn-blue"/>保存并提交审核</a>
					</#if>
                </div>
            </div>
            </form>
        </div>
    </div>
</body>
</html>
<script type="text/javascript">
var basePath = '${BasePath}';
$(document).ready(function(){
	if($("[name=isNeedUseFee]:checked").val()=='0'&& $("[name=isNeedDeposit]:checked").val()=='0'){
		$('#useFee').val(0);
		$('#deposit').val(0);
		$('#feeAll').hide();
  	}else
  	if($("[name=isNeedDeposit]:checked").val()=='0'){
		$('#deposit').val(0);
		$('#feeDeposit').hide();
  	}else
	if($("[name=isNeedUseFee]:checked").val()=='0'){
		$('#useFee').val(0);
		$('#feeAll').hide();
	}
  $("input[name=isNeedDeposit]").live("click",function(){
  		if($("[name=isNeedDeposit]:checked").val()=='0'){
  			$('#deposit').val(0);
  			$('#feeDeposit').hide();
  			if($("[name=isNeedUseFee]:checked").val()=='0'){
  				$('#useFee').val(0);
  				$('#feeAll').hide();
  			}
  		}else{
  			$('#feeAll').show();
  			$('#deposit').val('');
			$('#feeDeposit').show(); 
  		}
  });
  $("input[name=isNeedUseFee]").live("click",function(){
  		if($("[name=isNeedUseFee]:checked").val()=='0'){
  			$('#useFee').val(0);
			$('#feeUse').hide();
			if($("[name=isNeedDeposit]:checked").val()=='0'){
				$('#deposit').val(0);
  				$('#feeAll').hide();
			}
  		}else{
  			$('#feeAll').show();
  			$('#useFee').val('');
			$('#feeUse').show();
  		}
  });
});

// 永久勾选的设置
function setEndForever(){
  if(  $('#foreverFor').attr("checked")=='checked' ){
	  $('#failure').val('2099-12-31');
  }else{
	  $('#failure').val('');
  }
}

function changeWms(value) {
	if("1"==value){
		$("input:radio[name=isUseYougouWms][value='1']").attr('checked','true');
	}else{
		$("input:radio[name=isUseYougouWms][value='0']").attr('checked','true');
	}
	if("0"==value){
	    $("#setOfBooksCode option[value='ZT20140903837400']").attr("selected", true); 
	}else{
		$("#setOfBooksCode option[value='']").attr("selected", true); 
	}
}

var maxFileSize = '${maxFileSize}';

var uploader1 = WebUploader.create({
    // 选完文件后，是否自动上传。
    auto: true,
    // swf文件路径
    swf: '${BasePath}/webuploader/Uploader.swf',
    // 文件接收服务端。
    server: "${BasePath}/yitiansystem/merchants/businessorder/attachmentUpload.sc",
    // 选择文件的按钮。可选。
    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
    pick: {
      id:'#filePicker1',
      multiple:false
    },
    duplicate:1,   //不去重
    compress:false,  //压缩
    fileSingleSizeLimit:100*1024*1024
});
uploader1.on( 'fileQueued', function( file ) {
   	var name = file.name;
	var type = name.substring(name.lastIndexOf(".")+1);
	if(type && type!=''){
		type = type.trim();
		type = type.toLowerCase();
	}	
	if(type!='doc'&& type!='xls'&& type!='docx'&& type!='xlsx'&& type!='pdf'&& type!='txt'
		&& type!='jpg'&& type!='bmp'&& type!='png'&& type!='jpeg' && type!='rar'){
	   uploader1.removeFile(file);
	   ygdg.dialog.alert("附件格式只能是 doc、xls、docx、xlsx 、pdf、txt、jpg、bmp、png、jpeg、rar");
	   return;
	}
	if(file.size > maxFileSize){
		uploader1.removeFile(file);
		ygdg.dialog.alert("文件最大不能超过"+(maxFileSize/1024/1024)+"M.");
		return;
	}
	
   $("#filePicker1").hide();
   $("#loading1").show();
});

uploader1.on( 'uploadSuccess', function( file,response) {
	$("#filePicker1").show();
    $("#loading1").hide();
	if(response.resultCode=="200"){
		response.type="1";
		new attachmentItem(response).appendTo("#contract_attachment");
	}else{
    	ygdg.dialog.alert(response.msg);
	}
});


function initContractData(supplierId){
	
	$.ajax({
      async : true,
      cache : false,
      type : 'GET',
      dataType : "json",
      url : "${BasePath}/yitiansystem/merchants/businessorder/getLatestContractId.sc?supplierId="+supplierId,
      success : function(data) {
         var contractId =data.contractId;
         document.location.href = "${BasePath}/yitiansystem/merchants/businessorder/to_add_contract_merchant.sc?id="+contractId+"&supplierId="+supplierId;
      }
    });
}

//选择供应商
function tosupper(){
  openwindow('${BasePath}/yitiansystem/merchants/businessorder/to_suppliersp4Contract.sc?supplierType=1',800,500,'选择商家');
}

//添加合同信息
function add_supplierContract(curObj,isSubmit){
  if( isSubmit && isSubmit=='true' ){// 保存并提交审核
  	if( !validateForm() ){
  	 return;
  	 }
  }else{// 保存
  	if( !validateFormSimple() ){
  	 return;
  	 }
  }
  curObj = $(curObj); 
  curObj.css('cursor', 'no-drop');// 鼠标变为禁止点击
  if($(curObj).data('isFirst')){// 根据开关 确定 是否return
	  return;
  }
  $(curObj).data('isFirst',true);// 开关  
  $.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data:$("#queryForm").serialize(),
		url : "${BasePath}/yitiansystem/merchants/businessorder/saveContractMerchant.sc?isSubmit="+isSubmit,
		success : function(data) {
			curObj.css('cursor', 'default');// 鼠标恢复
			if(data.resultCode=="200"){
				ygdg.dialog.alert("保存成功");
				document.location.href="${BasePath}/yitiansystem/merchants/businessorder/to_supplierContractList.sc?listKind=MANAGE_CONTRACT";
			}else{
				$(curObj).data('isFirst',false);// 开关 不让return
				ygdg.dialog.alert(data.msg);
			}
		},error:function(e){
				curObj.css('cursor', 'default');
		     	$(curObj).data('isFirst',false);// 开关 不让return
		     	ygdg.dialog.alert('保存合同提交失败.');
			}
   });
}

//保存 的校验
function validateFormSimple(){
 	//供应商名称
  	var supplier = $("#supplier").val();
  	 //结算方式
    var clearingForm = $("#clearingForm").val();
 
	if(supplier=="" ){
	    ygdg.dialog.alert("供应商名称不能为空!");
	    return false;
	}
	if(clearingForm == 0){
	  	ygdg.dialog.alert("请选择结算方式!");
	    return false;
	}
	checkBrand();
	 return true;
}

// 保存并提交审核的校验
function validateForm(){
	//有效日期开始日期
  var effectiveDate= $("#effective").val();
  //有效日期结束日期
  var failureDate = $("#failure").val();
  //结算方式
  var clearingForm = $("#clearingForm").val();
  //供应商名称
  var supplier = $("#supplier").val();
  //合同ID
  var id=$("#contractId").val();
  //供应商id
  var supplierSpId=$("#supplierSpId").val();
  //申报人
  var declarant = $.trim($("#declarant").val());
  var re = /[^\u4e00-\u9fa5]+$/; 
 
  if(supplier=="" ){
    ygdg.dialog.alert("供应商名称不能为空!");
    return false;
  }
  if(effectiveDate==""){
    ygdg.dialog.alert("合同开始日期不能为空!");
    return false;
  }
  if(failureDate==""){
    ygdg.dialog.alert("合同结束日期不能为空!");
    return false;
  }
  if(comparisonDate(effectiveDate,failureDate)){
  	 ygdg.dialog.alert("合同结束日期不能小于合同开始日期！");
     return false;
  }
  if(clearingForm == 0){
  	ygdg.dialog.alert("请选择结算方式!");
    return false;
  }
  if(!$("[name=isUseERP]:checked").val()){
  	 ygdg.dialog.alert("请选择是否使用ERP系统对接!");
     return false;
  }
  if(!$("[name=isNeedRenew]:checked").val()){
  	 ygdg.dialog.alert("请选择是否需续签!");
     return false;
  }
  if(!$("[name=isNeedDeposit]:checked").val()){
  	 ygdg.dialog.alert("请选择是否需缴纳保证金!");
     return false;
  }
  if(!$("[name=isNeedUseFee]:checked").val()){
  	 ygdg.dialog.alert("请选择是否需缴纳平台使用费!");
     return false;
  }

  if($("[name=isNeedDeposit]:checked").val()=='1'){
  var deposit = $('#deposit').val();
  if (!/^([1-9]\d*|[1-9]\d*\.\d*|0\.\d*[1-9]\d*)$/.test(deposit)) {
	  ygdg.dialog.alert("保证金金额只能为大于0的数值！");
      return false;
  }
  if (deposit>9999999.99) {
	  ygdg.dialog.alert("保证金金额过大，请重新输入！");
      return false;
  }
  }
  if($("[name=isNeedUseFee]:checked").val()=='1'){
  var useFee = $('#useFee').val();
  if (!/^([1-9]\d*|[1-9]\d*\.\d*|0\.\d*[1-9]\d*)$/.test(useFee)) {
	  ygdg.dialog.alert("平台使用费金额只能为大于0的数值！");
      return false;
  }
  if (useFee>9999999.99) {
	  ygdg.dialog.alert("平台使用费金额过大，请重新输入！");
      return false;
  }
  }
  
  if(declarant!=""&&re.test(declarant)){
   	ygdg.dialog.alert("申报人只能填写中文！");
     return false;
  } 
  
   //校验合同附件
    var contractAttachment = $("input[name='contract_attachment']");   
    var result = '';        
	for(var i=0; i<contractAttachment.length ;i++){
		var attachArray = contractAttachment[i].value.split(';');
		result += attachArray[0];
	}
	if(result.indexOf('1') < 0){
		ygdg.dialog.alert("请上传合同电子版附件！");
		return false;
	}else if(result.indexOf('3') < 0){
		ygdg.dialog.alert("请上传授权书附件！");
 		return false;
	}else if(result.indexOf('4') < 0){
		ygdg.dialog.alert("请上传商标注册证附件！");
 		return false;
	}
	var checkResult = checkBrand();
	if(checkResult[0] != ''){
		ygdg.dialog.alert(checkResult[0]);
 		return false;
	}
	if(checkResult[1] != ''){
		ygdg.dialog.alert(checkResult[1]);
 		return false;
	}
  	return true;
}

function clearAttachment(target){      
  	$(target).parent().remove();
}

function comparisonDate(a, b) {
    var arr = a.split("-");
    var starttime = new Date(arr[0], arr[1], arr[2]);
    var starttimes = starttime.getTime();

    var arrs = b.split("-");
    var lktime = new Date(arrs[0], arrs[1], arrs[2]);
    var lktimes = lktime.getTime();

    if (starttimes > lktimes) {
       
        return true;
    }
    else
        return false;

}
</script>
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>