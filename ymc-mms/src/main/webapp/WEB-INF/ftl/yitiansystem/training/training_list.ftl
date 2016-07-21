<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/css/university.css" />
<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.8.2.min.js"></script> 

<title>优购商城--商家后台</title>
<!-- 日期 -->
<script src="${BasePath}/js/common/form/datepicker/WdatePicker.js" type="text/javascript"></script>
</head>

<body>
<div class="container">
	<!--工具栏start--> 
	<div class="toolbar">
		<div class="t-content">
			<div class="btn" onclick="addCourse();">
				<span class="btn_l"></span>
	        	<b class="ico_btn add"></b>
	        	<span class="btn_txt">添加课程</span>
	        	<span class="btn_r"></span>
        	</div> 
		</div>
	</div>
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li  class="curr">
				  <span><a href="#" class="btn-onselc">课程列表</a></span>
				</li>
			</ul>
		</div>
 <div class="modify"> 
     <form action="${BasePath}/yitiansystem/merchants/training/to_training.sc" name="queryForm" id="queryForm" method="post">
     			
     			 <div class="wms-top">
                        <p>
                            <label class="first">标题：</label>
                            <input type="text" name="title" id="title" value="<#if merchantTraining??&&merchantTraining.title??>${merchantTraining.title!""}</#if>"/>
                            <label>状态：</label>
                             <select name="isPublish" id="isPublish">
		                        <option value="">请选择状态</option>
		                        <option value="1" <#if merchantTraining??&&merchantTraining.isPublish??&&merchantTraining.isPublish==1>selected</#if>>已发布</option>
		                        <option value="0" <#if merchantTraining??&&merchantTraining.isPublish??&&merchantTraining.isPublish==0>selected</#if>>未发布</option>
		                     </select>
                            <label>创建时间：</label>
                            <input type="text" name="createTimeStart" id="createTimeStart" value="<#if merchantTraining??&&merchantTraining.createTimeStart??>${merchantTraining.createTimeStart!""}</#if>"/>
                            &nbsp;至&nbsp;<input type="text" name="createTimeEnd" id="createTimeEnd" value="<#if merchantTraining??&&merchantTraining.createTimeEnd??>${merchantTraining.createTimeEnd!""}</#if>"/>
                        </p>
                        <p style="margin-top:5px;">
                            <label class="first">分类：</label>
                            <select name="catName">
                                <option value="">--请选择--</option>
                                <option value="1" <#if merchantTraining??&&merchantTraining.catName??&&merchantTraining.catName=='1'>selected</#if>>新手报到</option>
                                <option value="2" <#if merchantTraining??&&merchantTraining.catName??&&merchantTraining.catName=='2'>selected</#if>>商品管理</option>
                            	<option value="3" <#if merchantTraining??&&merchantTraining.catName??&&merchantTraining.catName=='3'>selected</#if>>店铺管理</option>
                            	<option value="4" <#if merchantTraining??&&merchantTraining.catName??&&merchantTraining.catName=='4'>selected</#if>>促销引流</option>
                            	<option value="5" <#if merchantTraining??&&merchantTraining.catName??&&merchantTraining.catName=='5'>selected</#if>>客户服务</option>
                            	<option value="6" <#if merchantTraining??&&merchantTraining.catName??&&merchantTraining.catName=='6'>selected</#if>>规则解读</option>
                            </select>
                            <label>文件类型：</label>
                            <select name="fileType" id="fileType">
                                <option value="">--请选择--</option>
                                <option value="1" <#if merchantTraining??&&merchantTraining.fileType??&&merchantTraining.fileType==1>selected</#if>>视频</option>
                                <option value="0" <#if merchantTraining??&&merchantTraining.fileType??&&merchantTraining.fileType==0>selected</#if>>PPT</option>
                            </select>
                            <label for="searchBtn">&nbsp;</label>
                            <input type="button" value="搜 索" id="searchBtn"   class="yt-seach-btn">
                        </p>
                    </div>
     			
             	</form>
             	
             	  <div id="content_list">
                    <table cellpadding="0" cellspacing="0" class="list_table" width="100%">
                        <thead>
                            <tr>
                                <th width="400">标题</th>
                                <th>分类</th>
                                <th>文件类型</th>
                                <th>状态</th>
                                <th>课程浏览</th>
                                <th>更新时间</th>
                                <th>最后操作人</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
                    
                       <#if pageFinder??&&pageFinder.data??&&pageFinder.data?size!=0>
                    	<#list pageFinder.data as item >
	                        <tr class="odd">
		                        <td class="ft-cl-r">${item['title']!""}</td>
		                        <td class="ft-cl-r">
		                        	<#if item.catName??&&item.catName=='1'>新手报到
		                        	<#elseif item.catName??&&item.catName=='2'>商品管理
		                        	<#elseif item.catName??&&item.catName=='3'>商家管理 
		                        	<#elseif item.catName??&&item.catName=='4'>促销引流 
		                        	<#elseif item.catName??&&item.catName=='5'>客户服务 
		                        	<#else>规则解读
		                        	</#if>
		                        </td>
		                        <td class="ft-cl-r"><#if item['fileType']??&&item['fileType']==1>视频
			                       <#else>PPT</#if></td>
			                    <td class="ft-cl-r"><#if item['isPublish']??&&item['isPublish']==1>已发布
			                       <#else>未发布</#if></td>
			                    <td class="ft-cl-r">${item['totalClick']!""}</td>
		                        <td class="ft-cl-r"><#if item.updateTime??>${item['updateTime']}</#if></td>
		                        <td class="ft-cl-r">${item['operator']}</td>
		                        <td>
		                            <#if item.isPublish??&&item.isPublish==1>
		                            	<a href="#" onclick="queryDetailById('${item['id']!''}')">查看</a>
		                            	<a href="#" onclick="unPublish('${item['id']!''}')">取消发布</a>
		                            	 <#if item.onTop??&&item.onTop==1>
		                            	<a href="#" onclick="unPush('${item['id']!''}')">取消置顶</a>
		                            	<#else>
		                            	<a href="#" onclick="push('${item['id']!''}')">置顶</a>
		                            	</#if>
		                            	<a href="#" onclick="queryHistoryById('${item['id']!''}')">日志</a>
		                            <#else>
			                       		<a href="#" onclick="toUpdateCourse('${item['id']!''}')">编辑</a>
		                            	<a href="#" onclick="publish('${item['id']!''}')">立即发布</a>
		                            	<a href="#" onclick="deleteById('${item['id']!''}')">删除</a>
		                            	<a href="#" onclick="queryHistoryById('${item['id']!''}')">日志</a>
		                           	</#if>
		                        </td>
	                        </tr>
                        </#list>
                        	<#else>
                        	<tr>
                        	<td colSpan="10">
                        	抱歉，没有您要找的数据 
	                        </td>
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

<script type="text/javascript">

$(function() {
        $('#createTimeStart').calendar({
            format: 'yyyy-MM-dd'
        });
        $('#createTimeEnd').calendar({
            format: 'yyyy-MM-dd'
        });
       
        $("#searchBtn").click(function() {
            queryTrainings();
        });
    });
//跳转到日志记录页面
function queryHistoryById(id){
  openwindow("${BasePath}/yitiansystem/merchants/training/training_log.sc?trainingId=" + id, 900, 700, "查看日志");
  // location.href="${BasePath}/yitiansystem/merchants/training/training_log.sc?trainingId="+id;
}

//跳转到查看课程信息页面
function queryDetailById(id){
     location.href="${BasePath}/yitiansystem/merchants/training/training_detail.sc?id="+id;
}

//跳转到添修改课程信息页面
function toUpdateCourse(id){
     location.href="${BasePath}/yitiansystem/merchants/training/to_add_training.sc?id="+id;
}

//跳转到添修改课程信息页面
function addCourse(){
     location.href="${BasePath}/yitiansystem/merchants/training/to_add_training.sc";
}

//删除课程信息
function deleteById(id){
 if(id!=""){
   if(confirm("是否真的删除!")){
	$.ajax({ 
		type: "post", 
		url: "${BasePath}/yitiansystem/merchants/training/del_training.sc?id=" + id, 
		success: function(response){
			if(typeof(response)!='undefined' && 'true'==response){
			   ygdg.dialog.alert("删除成功!");
			   document.location.href="${BasePath}/yitiansystem/merchants/training/to_training.sc";
			}else{
			   ygdg.dialog.alert("删除失败!");
			   document.location.href="${BasePath}/yitiansystem/merchants/training/to_training.sc";
			}
		} 
	});
   }
 }
}

function publish(id){
 $.ajax({
 	 type : 'POST',
	 dataType : "json",
 	 url:"${BasePath}/yitiansystem/merchants/training/publish_training.sc?id="+ id+"&flag=1", 
 	 success:function(response){
 	 	    var message = "";
 	 	    if(response.resultCode=="200"){
 	 		   		message = "发布成功!";
			}else{
 	 		   		message = response.msg;
			}
			ygdg.dialog.alert(message);
			document.location.href="${BasePath}/yitiansystem/merchants/training/to_training.sc";
 	 }	
 });
}

function push(id){
$.ajax({
 	type:"post",
 	 url:"${BasePath}/yitiansystem/merchants/training/push_training.sc?id=" + id+"&flag=1", 
 	 success:function(response){
 	 	    var message = "";
 	 	    if(typeof(response)!='undefined' && response.indexOf('true')>-1){
 	 		   		message = "置顶成功!";
			}else{
 	 		   		message = "置顶失败!";
			}
			ygdg.dialog.alert(message);
			document.location.href="${BasePath}/yitiansystem/merchants/training/to_training.sc";
 	 }	
 });
}

function unPublish(id){
 $.ajax({
 	 type : 'POST',
	 dataType : "json",
 	 url:"${BasePath}/yitiansystem/merchants/training/publish_training.sc?id="+ id+"&flag=0", 
 	 success:function(response){
 	 	if(response.resultCode=="200"){
			   ygdg.dialog.alert("取消发布成功!");
			   document.location.href="${BasePath}/yitiansystem/merchants/training/to_training.sc";
			}else{
			   ygdg.dialog.alert("取消发布失败!");
			   document.location.href="${BasePath}/yitiansystem/merchants/training/to_training.sc";
			}
 	 }	
 });
}

function unPush(id){
$.ajax({
 	type:"post",
 	 url:"${BasePath}/yitiansystem/merchants/training/push_training.sc?id=" + id+"&flag=0", 
 	 success:function(response){
 	 	if(typeof(response)!='undefined' && 'true'==response){
			   ygdg.dialog.alert("取消置顶成功!");
			   document.location.href="${BasePath}/yitiansystem/merchants/training/to_training.sc";
			}else{
			   ygdg.dialog.alert("取消置顶失败!");
			   document.location.href="${BasePath}/yitiansystem/merchants/training/to_training.sc";
			}
 	 }	
 });
}

function queryTrainings(){
   document.queryForm.method="post";
   document.queryForm.submit();
}


</script>

<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
<div style="display: none; position: fixed; left: 0px; top: 0px; width: 100%; height: 100%; cursor: move; opacity: 0; background-color: rgb(255, 255, 255); background-position: initial initial; background-repeat: initial initial;"></div>
</body>
</html>