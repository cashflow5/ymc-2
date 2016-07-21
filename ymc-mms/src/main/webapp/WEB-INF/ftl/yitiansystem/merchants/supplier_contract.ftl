<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/css/yitiansystem/merchants/merchants.css"/>

<script type="text/javascript" src="${BasePath}/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/js.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/order.js"></script>
<!-- 排序样式 -->
<link rel="stylesheet" type="text/css" href="${BasePath}/css/yitiansystem/merchants/sortfilter.css" />
<!-- 小图标库的样式 -->
<link rel="stylesheet" type="text/css" href="${BasePath}/css/yitiansystem/merchants/icon.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/css/yitiansystem/merchants/iconfont.css" />
<title>优购商城--商家后台</title>
<!-- 日期 -->
<script src="${BasePath}/js/common/form/datepicker/WdatePicker.js" type="text/javascript"></script>
</head>
<style>
.list_table th{text-align:center;}

.icon-arrow-down{
     background-image: none;
     background-color: #ddd;  
 }
 
 .icon-arrow-up{
     background-image: none;
     background-color: #fff; 
 }
</style>

<body>
<div class="container">
	<!--工具栏start--> 
	<#if listKind?? &&listKind == 'MANAGE_CONTRACT'>
	<div class="toolbar">
		<div class="t-content">			
        	<div class="btn" onclick="addSupplierContactMerchant();">
                <span class="btn_l"></span>
                <b class="ico_btn add"></b>
                <span class="btn_txt">添加招商供应商合同</span>
                <span class="btn_r"></span>
            </div>
            <#-- 
            <div class="btn" onclick="addSupplierContact();">
                <span class="btn_l"></span>
                <b class="ico_btn add"></b>
                <span class="btn_txt">添加普通供应商合同</span>
                <span class="btn_r"></span>
            </div>
            -->
        	<div class="btn" onclick="doExport();">
				<span class="btn_l"></span>
	        	<b class="ico_btn add"></b>
	        	<span class="btn_txt">导出合同</span>
	        	<span class="btn_r"></span>
        	</div> 
		</div>
	</div>
	</#if>
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li  class="curr">
				  <span><a href="#" class="btn-onselc">合同列表</a></span>
				</li>
			</ul>
		</div>
 <div class="modify"> 
 <input type="hidden" id="pageSize" value="20">
     <form name="queryForm" id="queryForm" action="${BasePath}/yitiansystem/merchants/businessorder/getContractList.sc" method="post">
          <ul class="searchs w-auto clearfix">
            <li>
                <label for="">商家名称：</label>
                <input class="w120" type="text" name="supplierName" value="">
            </li>
            <li>
                <label for="">商家编号：</label>
                <input class="w120" type="text" name="supplierCode"  value="">
            </li>
            <li>
                <label for="">创建时间：</label>
                <input id="createTimeStart" name="createTimeStart" class="calendar w120 timeStart" type="text">
					- <input id="createTimeEnd" name="createTimeEnd" class="calendar timeEnd" type="text">
            </li>
            <li>
                <label for="">合同编号：</label>
                <input class="w120" type="text" name="contractNo" value="">
            </li>
            <li>
                <label for="">合同状态：</label>
                <select class="w120" name="status" value="">
                    <option value="">请选择</option>
                 	<option value="1">新建</option>
                 	<option value="2">待审核</option>
                 	<option value="3">业务审核通过</option>
                 	<option value="4">业务审核不通过</option>
                 	<option value="5">财务审核通过</option>
                 	<option value="6">财务审核不通过</option>
                 	<option value="7">生效</option>
                 	<option value="8">已过期</option>
                </select>
            </li>
            <li>
                <label for="">申报人：</label>
                <input class="w120" type="text" name="declarant" value="">
            </li>
            
            <li>
                <label for="">合同开始日期：</label>
                <input name="calendar" class="calendar timeStart w120" name="effectiveDateStart" id="effectiveDateStart" type="text">
                - <input name="calendar" class="calendar timeEnd" name="effectiveDateEnd" id="effectiveDateEnd" type="text">
            </li>
            <li>
                <label for="">是否上传附件：</label>
                <select class="w120" name="haveUploadAttachment">
                    <option value="">全部</option>
                 	<option value="Y">是</option>
                 	<option value="N">否</option>
                </select>
            </li>
            <#--
            <li>
                <label for="">商家类型：</label>
                <select class="w120" name="supplierType"  value="">
                    <option value="">请选择</option>
                 	<option value="招商供应商">招商供应商</option>
                 	<option value="普通供应商">普通供应商</option>
                </select>
            </li>
            -->
            <li>
                <label for="">商标：</label>
                <input class="w120" type="text" name="trademark"  value="">
            </li>
            <li>
                <label for="">合同截止日期：</label>
                <input name="calendar" class="calendar timeStart w120" name="failureDateStart" id="failureDateStart" type="text">
                - <input name="calendar" class="calendar timeEnd" name="failureDateEnd" id="failureDateEnd" type="text">
            </li>
            
            <li>
                <label for="">绑定状态：</label>
                <select class="w120" name="bindStatus">
                    <option value="">请选择</option>
                 	<option value="1">已绑定</option>
                 	<option value="0">未绑定</option>
                </select>
            </li>
            <li>
                <label for="">合同类型：</label>
                <select class="w120" name="isNewContract">
                    <option value="">请选择</option>
                 	<option value="1">新合同</option>
                 	<option value="0">续签合同</option>
                </select>
            </li>
            <li>
                <label for=""></label>
                <input type="button" value="搜 索" id="searchBtn" class="yt-seach-btn" />
            </li>
        </ul>
      	<input type="hidden" value="" id="orderBy" name="orderBy"/>
        <input type="hidden" value="" id="sequence"  name="sequence"/>
      	</form>
       <div id="content_list">
			
	   </div>
      </div>
  </div>
</div>
</body>
</html>
<script type="text/javascript">
function searchSort(name,sequence){
$("#orderBy").val(name);
$("#sequence").val(sequence);
loadData(1);
}

//跳转到添加普通供应商合同页面
function addSupplierContact(){
	document.location.href="${BasePath}/yitiansystem/merchants/businessorder/to_add_contract.sc";
}

//跳转到添加招商供应商合同页面
function addSupplierContactMerchant(){
	document.location.href="${BasePath}/yitiansystem/merchants/businessorder/to_add_contract_merchant.sc";
}

$(function(){
  $('#effectiveDateStart').calendar({format:'yyyy-MM-dd'});
  $('#effectiveDateEnd').calendar({format:'yyyy-MM-dd'});
  $('#failureDateStart').calendar({format:'yyyy-MM-dd'});
  $('#failureDateEnd').calendar({format:'yyyy-MM-dd'});
  $('#createTimeStart').calendar({format:'yyyy-MM-dd'});
  $('#createTimeEnd').calendar({format:'yyyy-MM-dd'});
  loadData(1);
  $("#searchBtn").click(function(){
  	 loadData(1);
  });
});
var doQueryUrl ="${BasePath}/yitiansystem/merchants/businessorder/getContractList.sc";
function loadData(page,pageSize){
	if(page==null){
		page=1;
	}
	if(typeof(pageSize)=='undefined'||pageSize==''||pageSize==null){
		pageSize = $("#pageSize").val();
	}else{
		$("#pageSize").val(pageSize);
	}
		$("#content_list").html('<table cellpadding="0" cellspacing="0" class="list_table">'+
    		'<thead>'+
            '<tr>'+
            '<th>创建时间</th>'+
            '<th>商家名称</th>'+
            '<th>商标</th>'+
            '<th>商标授权剩余天数</th>'+
            '<th>商家编号</th>'+
            '<th>合同编号</th>'+
            '<th>申报人</th>'+
            '<th>合同开始日期</th>'+
            '<th>合同截止日期</th>'+
            '<th>合同到期剩余天数</th>'+
            '<th>货品负责人</th>'+
            '<th>最近更新时间</th>'+
            '<th>附件是否上传</th>'+
            '<th>绑定状态</th>'+
            '<th>查看</th>'+
            '</tr>'+
            '</thead>'+
            '<tbody>'+
           		'<tr><td colspan="13" style="text-align:center">正在加载.....</td></tr>'+
         	'</tbody>'+
    '</table>');
    
    var searchParams = $("#queryForm").serialize();
    searchParams = decodeURIComponent(searchParams);
    searchParams = encodeURI(encodeURI(searchParams));
    var url = doQueryUrl+"?"+searchParams;
    <#if listKind??>
		url+="&listKind=${listKind}";
	</#if>
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "html",
		data:{page:page,pageSize:pageSize},
		url :url,
		success : function(data) {
			$("#content_list").html(data);
			$('[data-ui-type=sort]',"#content_list").Sort();
		}
	});
}

var doExportUrl="${BasePath}/yitiansystem/merchants/businessorder/exportContractList.sc";

function doExport() {
	if (!confirm("确定导出！")) {
		return;
	}
	var queryForm = document.getElementById("queryForm");
	queryForm.action = doExportUrl;
	queryForm.submit();
	queryForm.action = doQueryUrl;
}

</script>

<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>