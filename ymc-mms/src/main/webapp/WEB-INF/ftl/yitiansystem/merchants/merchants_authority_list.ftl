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
			<div class="btn" onclick="addMerchantsAuthority();">
				<span class="btn_l"></span>
	        	<b class="ico_btn add"></b>
	        	<span class="btn_txt">添加用户资源列表</span>
	        	<span class="btn_r"></span>
        	</div> 
        	<div class="btn" onclick="toUpdateHistoryById();">
				<span class="btn_l"></span>
	        	<b class="ico_btn add"></b>
	        	<span class="btn_txt">查看日志</span>
	        	<span class="btn_r"></span>
        	</div>
		</div>
	</div>
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li  class="curr">
				  <span><a href="#" class="btn-onselc">资源列表</a></span>
				</li>
			</ul>
		</div>
 <div class="modify"> 
     <form action="${BasePath}/yitiansystem/merchants/businessorder/to_merchants_authority.sc" name="queryForm" id="queryForm" method="post"> 
  			  	<div class="wms-top">
                     <label>资源名称：</label>
                     <input type="text" name="authrityName" id="authrityName" value="<#if merchantsAuthority??&&merchantsAuthority.authrityName??>${merchantsAuthority.authrityName!''}</#if>"/>&nbsp;&nbsp;&nbsp;
                    <input type="button" value="搜索" onclick="queryMerchantsAuthority();" class="yt-seach-btn" />&nbsp;&nbsp;&nbsp;
              	</div>
              	</form>
                <table cellpadding="0" cellspacing="0" class="list_table">
                		<thead>
                        <tr>
                        <th>资源名称</th>
                        <th>资源路径</th>
                        <th>所属模块</th>
                        <th>排序号</th>
                        <th>创建时间</th>
                        <th>备注</th>
                        <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                       <#if pageFinder??&&pageFinder.data??>
                    	<#list pageFinder.data as item >
	                        <tr>
		                        <td>${item['authrity_name']!""}</td>
		                        <td>${item['authrity_url']!""}</td>
		                        <td><#if item['id']!="0">${item['parent_name']!""}</#if></td>
		                        <td>${item['sort_no']!""}</td>
		                        <td>${item['create_time']!""}</td>
		                        <td>${item['remark']!""}</td>
		                        <td><#if item['id']!="0">
		                        <a href="#" onclick="deleteAuthority('${item['id']}');">删除</a>
		                        <a href="#" onclick="updateAuthority('${item['id']}');">修改</a></#if></td>
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
			  	<#if pageFinder ??><#import "../../common.ftl" as page>
			  		<@page.queryForm formId="queryForm"/></#if>
			  </div>
              <div class="blank20"></div>
          </div>
</div>
</body>
</html>
<script type="text/javascript">
//跳转到日志界面
function toUpdateHistoryById(){
   openwindow("${BasePath}/yitiansystem/merchants/businessorder/viewAuthorityOperationLog.sc", 900, 700, "查看日志");
}
//删除商家角色
function deleteAuthority(id){
  if(id!=""){
      if(confirm("是否真的删除!")){
		$.ajax({ 
			type: "post", 
			url: "${BasePath}/yitiansystem/merchants/businessorder/delete_authority.sc?id=" + id, 
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
function queryMerchantsAuthority(){
  document.queryForm.submit();
}
//添加资源列表
function addMerchantsAuthority(){
   openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_add_Merchants_authority.sc",600,400,"添加资源信息");
}

//修改资源列表
function updateAuthority(id){
   openwindow("${BasePath}/yitiansystem/merchants/businessorder/to_update_merchants_authority.sc?id="+id,600,400,"修改资源信息");
}
</script>
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
