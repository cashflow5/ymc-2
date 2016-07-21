<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link TYPE="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" TYPE="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" TYPE="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>

<script TYPE="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/jquery-1.3.2.min.js"></script>
<script TYPE="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
<script TYPE="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/js.js"></script>
<script TYPE="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/order.js"></script>
<title>优购商城--商家后台</title>
<!-- 日期 -->
<script src="${BasePath}/js/common/form/datepicker/WdatePicker.js" TYPE="text/javascript"></script>
</head>
<script TYPE="text/javascript">
</script>
<body>
<DIV class="container">
	<!--工具栏START--> 
	<DIV class="toolbar">
		<DIV class="t-content">
			<DIV class="btn" onclick="addNotice();">
				<span class="btn_l"></span>
	        	<b class="ico_btn add"></b>
	        	<span class="btn_txt">添加公告</span>
	        	<span class="btn_r"></span>
        	</DIV> 
		</DIV>
	</DIV>
	<DIV class="list_content">
		<DIV class="top clearfix">
			<ul class="tab">
				<li  class="curr">
				  <span><a href="#" class="btn-onselc">公告管理</a></span>
				</li>
			</ul>
		</DIV>
 <DIV class="modify"> 
     <form ACTION="${BasePath}/yitiansystem/notice/notice_list.sc" NAME="queryForm" id="queryForm" method="post"> 
  			  	<div class="add_detail_box">
                      <span>
                  	  <label>标题:</label>
                      <input type="text" name="title" id="title" value="<#if vo??&&vo.title??>${vo.title!''}</#if>"/>&nbsp;
                      </span>
                      <span>
                      <label>公告类型：</label>
                      <select name="noticeType" id="noticeType">
	                    <option value="">请选择公告类型</option>
	                   	<option <#if vo.noticeType??&&vo.noticeType=='1'>selected</#if> value="1">新功能</option>
	                   	<option <#if vo.noticeType??&&vo.noticeType=='0'>selected</#if> value="0">重要通知</option>
                   	  </select>
                   	  </span>
                   	  <span>
                      <label>创建人：</label>
                      <input type="text" name="author" id="author" style="width:80px;" value="<#if vo??&&vo.author??>${vo.author!''}</#if>"/>&nbsp;
                      </span>
                      <span>
                      <label>创建时间：</label>
                      <input type="text" name="createTimeStart" id="createTimeStart" value="<#if vo??&&vo.createTimeStart??>${vo.createTimeStart!''}</#if>"/>&nbsp;至
                      <input type="text" name="createTimeEnd" id="createTimeEnd" value="<#if vo??&&vo.createTimeEnd??>${vo.createTimeEnd!''}</#if>"/>&nbsp;
                      </span>
                      <span>
                      <input type="button" value="搜索" onclick="querySupplierContactsp();" class="yt-seach-btn" /></span>
              	</div>
              	</form>
                <TABLE cellpadding="0" cellspacing="0" class="list_table" style="text-align:center;">
                		<thead>
                        <tr>
	                        <th style="text-align:center;">标题</th>
	                        <th style="text-align:center;">创建时间</th>
	                        <th style="text-align:center;">合作模式</th>
	                        <th style="text-align:center;">创建人</th>
	                        <th style="text-align:center;">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#if pageFinder??&&pageFinder.data?? >
	                    	<#list pageFinder.data as item >
		                        <tr>
			                        <td style="<#if item.isRed=='1'>color: #FF6600;</#if>text-align:left;">【<#if item.noticeType=="1" >新功能<#else>重要通知</#if>】${item['title']!""}</td>
			                        <td>${item['createTime']?datetime}</td>
			                        <td><#if item.merchantType=="0" >不入优购仓库，商家发货<#elseif item.merchantType=="1">入优购仓库，优购发货<#elseif item.merchantType=="2">不入优购仓库，优购发货<#else>未知</#if></td>
			                        <td>${item['author']!""}</td>
			                        <td>
			                        	<a href="#" onclick="editNotice('${item['id']!''}');">修改</a>
			                        	<a href="#" onclick="delNotice('${item['id']!''}');">删除</a>
			                        	<#if item.isTop=="1" >
			                        		<a href="#" onclick="setTop('${item['id']!''}','0','取消置顶');">取消置顶</a>
										<#else>
											<a href="#" onclick="setTop('${item['id']!''}','1','置顶');">&nbsp;&nbsp;置顶&nbsp;&nbsp;</a>
										</#if>
			                        </td>
		                        </tr>
	                        </#list>
                        <#else>
                        	<tr>
	                        	<td colSpan="10">抱歉，没有您要找的数据 </td>
	                        </tr>
                        </#if>
                      </tbody>
                </TABLE>
              </DIV>
              <DIV class="bottom clearfix">
			  	<#if pageFinder ??><#import "../../../common.ftl" as page>
			  		<@page.queryForm formId="queryForm"/></#if>
			  </DIV>
              <DIV class="blank20"></DIV>
          </DIV>
</DIV>
</body>
</html>
<script TYPE="text/javascript">
$(function(){
	$('#createTimeStart').calendar({maxDate:'#createTimeEnd',format:'yyyy-MM-dd HH:mm:ss'});
	$('#createTimeEnd').calendar({minDate:'#createTimeStart',format:'yyyy-MM-dd HH:mm:ss'});
});
//根据条件查询
function querySupplierContactsp(){
   document.queryForm.method="post";
   document.queryForm.submit();
}

//修改
function editNotice(id){
	openwindow("${BasePath}/yitiansystem/notice/edit_notice.sc?id="+id,750,700,"修改公告");
}
//添加公告
function addNotice(){
  openwindow("${BasePath}/yitiansystem/notice/new_notice.sc",750,700,"添加公告");
}
//删除公告
function delNotice(id){
  if(confirm("确实要删除吗?")){
  	$.ajax({
		        type: "POST",
		        url:'${BasePath}/yitiansystem/notice/del_notice.sc',
		        data:"id="+id,
		        dataType:"json",
		        success: function(msg) {
		            if(msg.success=='1'){
			            setTimeout(function() {
			            	//刷新页面
			            	window.location.reload();
			            }, 500);
		            }else{
		              alert("删除失败");
		            }
		        }
	   		});
  }
}
//置顶设置
function setTop(id,istop,mes){
    if(confirm("确实要"+mes+"吗?")){
  	$.ajax({
		        type: "POST",
		        url:'${BasePath}/yitiansystem/notice/set_top.sc',
		        data:"id="+id+"&is_top="+istop,
		        dataType:"json",
		        success: function(msg) {
		            if(msg.success=='1'){
			            setTimeout(function() {
			            	//刷新页面
			            	window.location.reload();
			            }, 500);
		            }else{
		              alert("操作失败");
		            }
		        }
	   		});
  }
}
function myreload(){
	window.location.reload();
}
</script>
<script TYPE="text/javascript" src="${BasePath}/yougou/js/jquery-1.4.2.min.js"></script> 
<script TYPE="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script TYPE="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script TYPE="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script TYPE="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script TYPE="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>