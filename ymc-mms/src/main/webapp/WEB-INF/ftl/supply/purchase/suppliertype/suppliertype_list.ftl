<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content=" , ,优购网,B网络营销系统,商品管理" />
<meta name="Description" content=" , ,B网络营销系统-商品管理" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<title>B网络营销系统-采购管理-优购网</title>
<script type="text/javascript">	
	//提交按钮所在的表单
	function postForm(formId, url){
		$("#"+formId).attr("action",url);
		//添加hidden到form		
	  	var param = $('#param').val();	  	
	  	if("0" == param && ""== param){
	  		alert("条件为空,不能搜索!");
	  		return;
	  	}
		$("#"+formId).submit();
	}
	
	function deleteSupplierType(id){	
		if(window.confirm('确认删除？')==false){
			return;
		}		
	    var value=id;	     	
       	$.ajax({
           type: "POST",
           url: "deleteSupplierType.sc",
           data: {"id":value},           
           success: function(data){           
              if(data=="success"){
 		 		alert("删除成功!"); 	
 		 		window.location.reload();	 		
 		 	  }              
           }
         });          
      } 

//添加供应商类型
function addType() {
	openwindow('${BasePath}/supply/manage/suppliertype/toAdd.sc',600,300,'添加供应商类型');
}

function editSupplierType(id) {
	openwindow('${BasePath}/supply/manage/suppliertype/u_editTypeUI.sc?id='+id,600,300,'修改供应商类型');
}

</script>
</head>
<body>


<div class="container">
	<!--工具栏start-->
	<div class="toolbar">
		<div class="t-content">
			<div class="btn" onclick="addType();">
				<span class="btn_l" ></span>
				<b class="ico_btn add"></b>
				<span class="btn_txt">添加</span>
				<span class="btn_r"></span>
			</div>
		</div>
	</div>
	<div class="list_content">
		<!--当前位置start-->
		<div class="top clearfix">
			<ul class="tab">
				<li class="curr"><span>供应商类型列表</span></li>
			</ul>
		</div>
		<!--当前位置end-->
		<form action ="querySuppliertype.sc" id="supplierListForm" name="supplierListForm" method="post">
		<table class="list_table" cellspacing="0" cellpadding="0" border="0">
					<thead> 
	                    <tr >	  													
						<th>供应商类型值</th>									
						<th>操作</th>
					  </tr>	            
	                </thead>
	                <tbody>
	                <#if pageFinder?? && pageFinder.data??>
						  <#list pageFinder.data as supplier>
							  <tr >																				
								<td>${supplier.typeValue?default("")}</td>																						
								<td >										
								<a href="javascript:editSupplierType('${supplier.id}');">修改</a>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;									
								<a href="javascript:deleteSupplierType('${supplier.id}');">删除</a>
								</td>
							  </tr>
						  </#list>	
		 			 </#if>
	                </tbody>
				</table>
				</form>
			<!--分页start-->
		<div class="bottom clearfix">
				<#if pageFinder ??>
					<#import "../../../common.ftl" as page>
					<@page.queryForm formId="supplierListForm" />
				</#if>
		</div>
		<!--分页end--> 
	</div>
</div>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
</body>
</html>

