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
<!-- 日期 -->
<script src="${BasePath}/js/common/form/datepicker/WdatePicker.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
</script>
<body>
<div class="container">
	<!--工具栏start--> 
	<div class="toolbar">
		<div class="t-content">
			<div class="btn" onclick="gotolink('${BasePath}/merchant/api/template/api_template_config_add.sc');">
				<span class="btn_l"></span>
	        	<b class="ico_btn add"></b>
	        	<span class="btn_txt">添加模板</span>
	        	<span class="btn_r"></span>
        	</div> 
		</div>
	</div>
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li  class="curr">
				  <span><a href="#" class="btn-onselc">API权限模板设置</a></span>
				</li>
			</ul>
		</div>
 <div class="modify"> 
     <form action="${BasePath}/merchant/api/template/api_template_config.sc" name="queryForm" id="queryForm" method="post"> 
  			  	<div class="wms-top">
                     <label>模板编号/名称：</label>
                     <input type="text" name="templateName" id="templateName" value="<#if template??&&template.templateName??>${template.templateName!''}</#if>"/>&nbsp;&nbsp;&nbsp;
                    <input type="button" value="搜索" onclick="queryTemplate();" class="yt-seach-btn" />&nbsp;&nbsp;&nbsp;
              	</div>
              	</form>
                <table cellpadding="0" cellspacing="0" class="list_table">
                		<thead>
                        <tr>
                        <th>序号</th>
                        <th>模板编号</th>
                        <th>模板名称</th>
                        <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                       <#if pageFinder??&&pageFinder.data??>
                    	<#list pageFinder.data as item >
	                        <tr>
		                        <td>${item_index + 1}</td>
		                         <td><a href="${BasePath}/merchant/api/template/api_template_config_view.sc?templateNo=${item.templateNo!''}">${item.templateNo!""}</a></td>
		                         <td>${item.templateName!""}</td>
		                        <td>
		                        <a href="#" onclick="deleteTemplate('${item.templateNo!''}');">删除</a>
		                        <a href="${BasePath}/merchant/api/template/api_template_config_modify.sc?templateNo=${item.templateNo!''}">修改</a></td>
	                        </tr>
                        </#list>
                        	<#else>
                        	<tr>
                        	<td colSpan="8">抱歉，没有您要找的数据  </td>
	                        </tr>
                        </#if>
                      </tbody>
                </table>
              </div>
               <div class="bottom clearfix">
			  	<#if pageFinder ??><#import "../../../common.ftl" as page>
			  		<@page.queryForm formId="queryForm"/></#if>
			  </div>
              <div class="blank20"></div>
          </div>
</div>
</body>
</html>
<script type="text/javascript">
//删除模板
function deleteTemplate(templateNo){
  if(templateNo!=""){
      if(confirm("确实要删除选中的模板吗!")){
		$.ajax({ 
			type: "post", 
			url: "${BasePath}/merchant/api/template/api_template_config_delete.sc?templateNo=" + templateNo, 
			success: function(dt){
				if("success"==dt){
				   alert("删除成功!");
				   refreshpage();
				}else{
				   alert("删除失败!");
				   refreshpage();
				}
			} 
		});
   }
  }
}

//根据条件搜索
function queryTemplate(){
  document.queryForm.submit();
}

</script>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
