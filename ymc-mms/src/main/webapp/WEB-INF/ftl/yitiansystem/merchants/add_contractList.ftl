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
  <script type="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
  <script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/js.js"></script>
  <script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/order.js"></script>

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
                        <span><a href="#" class="btn-onselc"><#if supplierContract??>修改普通供应商合同<#else>添加普通供应商合同</#if></a></span>
                    </li>
                </ul>
                <div style="padding-left:576px;padding-top:5px;">注意：文件上传格式必须为doc、xls、docx、xlsx、pdf、txt、jpg、bmp、png、jpeg ,或者打包rar格式上传。</div>
            </div>
            <div class="modify">
            	<form  name="queryForm" id="queryForm" method="post">
                <div class="form-box">
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>商家名称：</label>
                            <#if supplierContract??&&supplierContract.id??>
							<span>${(supplierContract.supplier)!'' }</span>
                            <input class="supplier-query-text" type="hidden" name="supplier" id="supplier" value="${(supplierContract.supplier)!'' }" > 
							<#else>
                            <input class="supplier-query-text" readonly="readonly" style="width:250px" type="text" name="supplier" id="supplier" value="${(supplierContract.supplier)!'' }" onchange="changeBindStatus();">
							<a class="yg-btn-gray-2" href="javascript:tosupper();">选择</a>
							</#if>
                        	<input type="hidden" name="bindStatus" value="${(supplierContract.bindStatus)!'0' }" id="bindStatus"/>
							<input type="hidden" name="id" value="<#if supplierContract??>${supplierContract.id!''}</#if>">
          					<input type="hidden" name="supplierId" id="supplierSpId" value="<#if supplierContract??>${supplierContract.supplierId!''}</#if>">                        
          				</div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title">合同编号：</label>
                            <span><#if supplierContract??>${(supplierContract.contractNo)!'' }<#else>${contractNo}</#if></span>
                        	<input type="hidden" name="contractNo" id="contractNo" value="<#if supplierContract??>${(supplierContract.contractNo)!'' }<#else>${contractNo}</#if>">
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>结算方式：</label>
                            <select class="supplier-query-select" name="clearingForm" id="clearingForm">
						        <option value="0" <#if supplierContract??&&supplierContract.clearingForm='0'>selected="selected"</#if>>请选择结算方式</option>
						        <option value="1" <#if supplierContract??&&supplierContract.clearingForm='1'>selected="selected"</#if>>底价结算</option>
						        <option value="2" <#if supplierContract??&&supplierContract.clearingForm='2'>selected="selected"</#if>>扣点结算</option>
						        <option value="3" <#if supplierContract??&&supplierContract.clearingForm='3'>selected="selected"</#if>>配折结算</option>
						        <option value="4" <#if supplierContract??&&supplierContract.clearingForm='4'>selected="selected"</#if>>促销结算</option>
						    </select>
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
                            <label for="" class="supplier-query-title">申报人：</label>
                            <input class="supplier-query-text" type="text" name="declarant" maxlength="30" id="declarant" value="<#if supplierContract??>${supplierContract.declarant!''}</#if>">
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
                </div>
                <h1 class="supplier-title">授权资质</h1>
                <div class="form-box">
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>授权书：</label>
                            <a class="yg-btn-gray-2 yg-btn-update" id="filePicker3">上传文件</a>
        					<div id="loading3" class="loading"><img src="${BasePath}/images/loading.gif"></div>
							<div id="authority_attachment" class="inline-block">
                            <#if supplierContract??&&supplierContract.attachmentList??&&(supplierContract.attachmentList?size>0)>
					              <#list supplierContract.attachmentList as item >
					                <#if item['attachmentType']=='3'>
					                  <div class="attachment_item">
					                    <input name="contract_attachment" value="3;${item['attachmentName']!''};${item['attachmentRealName']!''}" type="hidden">
					                    <span class="supplier-query-cont Blue ml5">${item['attachmentName']!''}</span><a href="javascript:void(0)" class="link-del ml10 Blue">删除</a>
					                  </div>
					                </#if>
					              </#list>
					          </#if>
							</div>
                        </div>
                        <div class="supplier-query-right">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>商标注册证：</label>
                            <a class="yg-btn-gray-2 yg-btn-update" id="filePicker4">上传文件</a>
        					<div id="loading4" class="loading"><img src="${BasePath}/images/loading.gif"></div>
							<div id="trademark_attachment" class="inline-block">
                            <#if supplierContract??&&supplierContract.attachmentList??&&(supplierContract.attachmentList?size>0)>
				              <#list supplierContract.attachmentList as item >
				                <#if item['attachmentType']=='4'>
				                  <div class="attachment_item">
				                    <input name="contract_attachment" value="4;${item['attachmentName']!''};${item['attachmentRealName']!''}" type="hidden">
				                    <span class="supplier-query-cont Blue ml5" title="${item['attachmentName']!''}">${item['attachmentName']!''}</span><a href="javascript:void(0)" class="link-del ml10 Blue">删除</a>
				                  </div>
				                </#if>
				              </#list>
				            </#if>
						</div>
                        </div>
                    </div>
                    <div class="supplier-query-wrap clearfix">
                        <div class="supplier-query-left">
                            <label for="" class="supplier-query-title"><span class="Red">*</span>商标授权：</label>
                            <a class="yg-btn-gray-2" onclick="addSubTable()">添加</a>
                        </div>
                        <div class="supplier-query-right"></div>
                    </div>
                </div>
                <div id="content_list">
                    <table class="list_table" cellpadding="0" cellspacing="0" width="100%">
                        <thead>
                            <tr>
                                <th><span class="Red">*</span>商标</th>
                                <th>商标专利授权人</th>
                                <th>类别</th>
                                <th>注册商标号</th>
                                <th>注册开始日期</th>
                                <th>注册截止日期</th>
                                <th>授权级别</th>
                                <th>被授权人</th>
                                <th>授权期起</th>
                                <th>授权期止</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody id="sub-tbody">
        				</tbody>                        
                    </table>                    
                </div>

                <div class="btn-box">
                    <a href="javascript:add_supplierContract(this);" class="yg-btn-blue">保存</a>
                </div>
            </div>
            </form>
        </div>
    </div>
</body>
</html>
<script type="text/javascript">
var maxFileSize = '${maxFileSize}';

// 永久勾选的设置
function setEndForever(){
  if($('#foreverFor').attr("checked")=='checked'){
	  $('#failure').val('2099-12-31');
  }else{
	  $('#failure').val('');
  }
}

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
		&& type!='jpg'&& type!='bmp'&& type!='png'&& type!='jpeg'&& type!='rar'){
	   uploader1.removeFile(file);
	   ygdg.dialog.alert("附件格式只能是 doc、xls、docx、xlsx 、pdf、txt、jpg、bmp、png、jpeg、rar.");
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

var uploader3 = WebUploader.create({
    // 选完文件后，是否自动上传。
    auto: true,
    // swf文件路径
    swf: '${BasePath}/webuploader/Uploader.swf',
    // 文件接收服务端。
    server: "${BasePath}/yitiansystem/merchants/businessorder/attachmentUpload.sc",
    // 选择文件的按钮。可选。
    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
    pick: {
      id:'#filePicker3',
      multiple:false
    },
    duplicate:1,   //不去重
    compress:false,  //压缩
    fileSingleSizeLimit:100*1024*1024
});
uploader3.on( 'fileQueued', function( file ) {
	var name = file.name;
	var type = name.substring(name.lastIndexOf(".")+1);
	if(type && type!=''){
		type = type.trim();
		type = type.toLowerCase();
	}	
	if(type!='doc'&& type!='xls'&& type!='docx'&& type!='xlsx'&& type!='pdf'&& type!='txt'
	   && type!='jpg'&& type!='bmp'&& type!='png'&& type!='jpeg'&& type!='rar'){
	   uploader3.removeFile(file);
	   ygdg.dialog.alert("附件格式只能是 doc、xls、docx、xlsx 、pdf、txt、jpg、bmp、png、jpeg、rar.");
	   return;
	}
	if(file.size > maxFileSize){
		uploader1.removeFile(file);
		ygdg.dialog.alert("文件最大不能超过"+(maxFileSize/1024/1024)+"M.");
		return;
	}
   $("#filePicker3").hide();
   $("#loading3").show();
});

uploader3.on( 'uploadSuccess', function( file,response) {
	$("#filePicker3").show();
    $("#loading3").hide();
	if(response.resultCode=="200"){
		response.type="3";
		new attachmentItem(response).appendTo("#authority_attachment");
	}else{
    	ygdg.dialog.alert(response.msg);
	}
});


var uploader4 = WebUploader.create({
    // 选完文件后，是否自动上传。
    auto: true,
    // swf文件路径
    swf: '${BasePath}/webuploader/Uploader.swf',
    // 文件接收服务端。
    server: "${BasePath}/yitiansystem/merchants/businessorder/attachmentUpload.sc",
    // 选择文件的按钮。可选。
    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
    pick: {
      id:'#filePicker4',
      multiple:false
    },
    duplicate:1,   //不去重
    compress:false,  //压缩
    fileSingleSizeLimit:100*1024*1024
});
uploader4.on( 'fileQueued', function( file ) {
	var name = file.name;
	var type = name.substring(name.lastIndexOf(".")+1);
	if(type && type!=''){
		type = type.trim();
		type = type.toLowerCase();
	}	
	if(type!='doc'&& type!='xls'&& type!='docx'&& type!='xlsx'&& type!='pdf'&& type!='txt'
		&& type!='jpg'&& type!='bmp'&& type!='png'&& type!='jpeg'&& type!='rar'){
	   uploader4.removeFile(file);
	   ygdg.dialog.alert("附件格式只能是 doc、xls、docx、xlsx 、pdf、txt、jpg、bmp、png、jpeg、rar.");
	   return;
	}
	if(file.size > maxFileSize){
		uploader1.removeFile(file);
		ygdg.dialog.alert("文件最大不能超过"+(maxFileSize/1024/1024)+"M.");
		return;
	}
   $("#filePicker4").hide();
   $("#loading4").show();
});

uploader4.on( 'uploadSuccess', function( file,response) {
	$("#filePicker4").show();
    $("#loading4").hide();
	if(response.resultCode=="200"){
		response.type="4";
		new attachmentItem(response).appendTo("#trademark_attachment");
	}else{
    	ygdg.dialog.alert(response.msg);
	}
});
//type 附件类型 1：合同附件类型 2：资质附件类型 3：授权书附件类型 4:商标注册证附件类型
function attachmentItem(data){
	var type = data.type,fileName = data.fileName,realName = data.realName;
	var item = $("<div class='attachment_item'><input name='contract_attachment' type='hidden' value='"+type+";"+fileName+";"+realName+"'><span class='supplier-query-cont Blue ml5' title='"+fileName+"'>"+fileName+"</span><a href='javascript:void(0);'  class='link-del ml10 Blue'>删除</a></div>");
	return  item;
}

function initContractData(supplierId){
	
	$.ajax({
      async : true,
      cache : false,
      type : 'GET',
      dataType : "json",
      url : "${BasePath}/yitiansystem/merchants/businessorder/getLatestContractId.sc?supplierId="+supplierId,
      success : function(data) {
         var contractId =data.contractId;
         if(contractId){
         	document.location.href = "${BasePath}/yitiansystem/merchants/businessorder/to_add_contract.sc?id="+contractId;
         }
      }
    });
}

function addSubTable(){
	var index = $("#sub-tbody").find("tr.add-row").length+1;
	$(Subtable(index)).appendTo("#sub-tbody");
	$('#reg_start_'+index).calendar({format:'yyyy-MM-dd'});
	$('#reg_end_'+index).calendar({format:'yyyy-MM-dd'});
	for(var i=1;i<7;i++){
		$('#authoriz_startdate_'+i+'_'+index).calendar({format:'yyyy-MM-dd'});
		$('#authoriz_enddate_'+i+'_'+index).calendar({format:'yyyy-MM-dd'});
	}
}

function Subtable(index){
	var numberArray = ["二","三","四","五","六"];
	var tablestr = '';
       tablestr = tablestr+'<tr class="add-row">'+
          '<td rowspan="6" class="ft-cl-r">'+
            '<input class="table-text" type="text" name="trademark" maxlength="100" id="trademark_'+index+'"></td>'+
          '<td rowspan="6" class="ft-cl-r">'+
            '<input class="table-text" type="text" name="authorizer" maxlength="50" id="authorizer_'+index+'"></td>'+
          '<td rowspan="6" class="ft-cl-r">'+
            '<input class="table-text" type="text" name="type" maxlength="10" id="type_'+index+'"></td>'+
          '<td rowspan="6" class="ft-cl-r">'+
            '<input class="table-text" type="text" name="registeredTrademark" maxlength="100" id="registeredTrademark_'+index+'"></td>'+
          '<td rowspan="6" class="ft-cl-r">'+
            '<input class="table-text" type="text" readonly="readonly" name="registeredStartDate" id="reg_start_'+index+'"></td>'+
          '<td rowspan="6" class="ft-cl-r">'+
            '<input class="table-text" type="text" readonly="readonly" name="registeredEndDate" id="reg_end_'+index+'"></td>'+
          '<td>一级授权</td>'+
          '<td>'+
           '<input class="table-text" type="text" name="beAuthorizer" maxlength="50" id="beAuthorizer_1_'+index+'"></td>'+
          '<td>'+
             '<input class="table-text" type="text" readonly="readonly" name="authorizStartdate" readonly="readonly" id="authoriz_startdate_1_'+index+'"></td>'+
          '<td>'+
            '<input class="table-text" type="text" readonly="readonly" name="authorizEnddate" readonly="readonly" id="authoriz_enddate_1_'+index+'"></td>'+
          '<td rowspan="6" class="ft-cl-r"><a href="javascript:void(0)" class="del-tr">删除</a></td>'+
          '</tr>';
          for(var i=0;i<5;i++){
          	tablestr = tablestr +'<tr>'+
	         '<td>'+numberArray[i]+'级授权</td>'+
	          '<td>'+
	            '<input class="table-text" type="text" name="beAuthorizer" id="beAuthorizer_'+(i+2)+'_'+index+'"></td>'+
	          '<td>'+
	            '<input class="table-text" type="text" readonly="readonly" name="authorizStartdate" readonly="readonly" id="authoriz_startdate_'+(i+2)+'_'+index+'"></td>'+
	          '<td>'+
	            '<input class="table-text" type="text" readonly="readonly" name="authorizEnddate" readonly="readonly" id="authoriz_enddate_'+(i+2)+'_'+index+'"></td>'+
	        '</tr>';
          }
    return tablestr;
}

//选择供应商
function tosupper(){
  openwindow('${BasePath}/yitiansystem/merchants/businessorder/to_suppliersp4Contract.sc?supplierType=2',800,500,'选择商家');
}

//添加合同信息
function add_supplierContract(curObj){
  curObj = $(curObj);  
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
  if(clearingForm == 0){
  	ygdg.dialog.alert("请选择结算方式!");
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
  	 ygdg.dialog.alert("合同结束日期不能小于合同开始日期");
     return false;
  }
  
  if(declarant!=""&&re.test(declarant)){
   	ygdg.dialog.alert("申报人只能填写中文");
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
   
  
  //校验商标
  var trademarks = $("input[name='trademark']");
  var msg = "";
  for(var i=0,_len=trademarks.length;i<_len;i++){
	 if($.trim(trademarks.eq(i).val())==""){
	     msg = msg+"第"+(i+1)+"行商标不能为空<br>";
	 }
		
	 var flag = false;
	 // 校验授权
	 for(var row=1;row<7;row++){
	 	 var beAuthorizer = $.trim($('#beAuthorizer_'+row+'_'+(i+1)).val());
	 	 var authoriz_startdate = $.trim($('#authoriz_startdate_'+row+'_'+(i+1)).val());
	 	 var theAuthorizEnddate = $.trim($('#authoriz_enddate_'+row+'_'+(i+1)).val());
		 if(beAuthorizer!='' && authoriz_startdate!='' && theAuthorizEnddate!=''){
			  flag = true;
		 }
	 }
	 if(!flag){
		msg = msg+"第"+(i+1)+"行商标请输入至少一个授权级别的被授权人和授权起止时间<br>";
	 }

  }
  var trs = $("#sub-tbody").find("tr");
  for(var i=0,_len=trs.length;i<_len;i++){
  	var tr = trs.eq(i);
  	var authorizStartdate = $.trim(tr.find("input[name='authorizStartdate']").val());
  	var authorizEnddate = $.trim(tr.find("input[name='authorizEnddate']").val());
  	if(authorizStartdate!=""&&authorizEnddate!=""&&comparisonDate(authorizStartdate,authorizEnddate)){
  		 msg = msg+"第"+(i+1)+"行授权结束日期必须大于授权开始日期<br>";
  	}
  }
  if(msg!=""){
  	 ygdg.dialog.alert(msg);
  	 return;
  }
  curObj.attr("disabled",true);
  $.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data:$("#queryForm").serialize(),
		url : "${BasePath}/yitiansystem/merchants/businessorder/saveContract.sc",
		success : function(data) {
			curObj.attr("disabled",false);
			if(data.resultCode=="200"){
				ygdg.dialog.alert("保存成功");
				document.location.href="${BasePath}/yitiansystem/merchants/businessorder/to_supplierContractList.sc?listKind=MANAGE_CONTRACT";
			}else{
				ygdg.dialog.alert(data.msg);
			}
		}
   });
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
<#if supplierContract??>
var id = "${supplierContract.id!''}";
<#else>
var id = "";
</#if>
$(function(){
  $(".link-del").live("click",function(){
  	$(this).parent().remove();
  });
  
  $(".del-tr").live("click",function(){
  	var index = $("#sub-tbody").find("tr.add-row").length;
	if(index<=1){
		 ygdg.dialog.alert("至少需要保留一行商标授权!");
		 return;
	}  
  	 var tr = $(this).parent().parent();
  	 //删除相邻元素
  	 tr.next().remove();
  	 tr.next().remove();
  	 tr.next().remove();
  	 tr.next().remove();
  	 tr.next().remove();
  	 //最后删除自己
  	 tr.remove();
  });
  if(id==""){
    addSubTable(); 
  }else{
    $.ajax({
      async : true,
      cache : false,
      type : 'GET',
      dataType : "json",
      url : "${BasePath}/yitiansystem/merchants/businessorder/getContractDetailAjax.sc?id="+id,
      success : function(data) {
         var trademarkList =data.trademarkList;
         for(var i=0,_len=trademarkList.length;i<_len;i++){
            addSubTable(); 
            var trademark = trademarkList[i];
            $("#trademark_"+(i+1)).val(trademark.trademark);
            $("#authorizer_"+(i+1)).val(trademark.authorizer);
            $("#type_"+(i+1)).val(trademark.type);
            $("#registeredTrademark_"+(i+1)).val(trademark.registeredTrademark);
            $("#reg_start_"+(i+1)).val(trademark.registeredStartDate);
            $("#reg_end_"+(i+1)).val(trademark.registeredEndDate);
            for(var j=0,_sublen=trademark.trademarkSubList.length;j<_sublen;j++){
                var trademarkSub = trademark.trademarkSubList[j];
                $("#beAuthorizer_"+(trademarkSub.level)+"_"+(i+1)).val(trademarkSub.beAuthorizer);
                $("#authoriz_startdate_"+(trademarkSub.level)+"_"+(i+1)).val(trademarkSub.authorizStartdate);
                $("#authoriz_enddate_"+(trademarkSub.level)+"_"+(i+1)).val(trademarkSub.authorizEnddate);
            }
         }
      }
    });
  }
  $('#effective').calendar({format:'yyyy-MM-dd'});
  $('#failure').calendar({format:'yyyy-MM-dd'});
});
</script>
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>