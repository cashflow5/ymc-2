<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
 <link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
  <link rel="stylesheet" type="text/css" href="${BasePath}/css/yitiansystem/merchants/merchants.css" />
<!-- 排序样式 -->
<link rel="stylesheet" type="text/css" href="${BasePath}/css/yitiansystem/merchants/sortfilter.css" />
<!-- 小图标库的样式 -->
<link rel="stylesheet" type="text/css" href="${BasePath}/css/yitiansystem/merchants/icon.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/css/yitiansystem/merchants/iconfont.css" />

<script type="text/javascript" src="${BasePath}/js/jquery-1.8.3.min.js"></script>
<script type="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/js.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/order.js"></script>
<!-- 日期 -->
<script src="${BasePath}/js/common/form/datepicker/WdatePicker.js" type="text/javascript"></script>
</head>

<body>
    <div class="container">
        <div class="toolbar">
            <div class="t-content">
                <div class="btn" onclick="addMerchants();">
					<span class="btn_l"></span>
		        	<b class="ico_btn add"></b>
		        	<span class="btn_txt">添加商家</span>
		        	<span class="btn_r"></span>
	        	</div> 
            </div>
        </div>
        <!--工具栏start-->
       <div class="list_content">
			<div class="top clearfix">
				<ul class="tab">
					<li >
					  <span><a href="${BasePath}/yitiansystem/merchants/manage/to_supplier_List.sc"  class="btn-onselc">商家列表</a></span>
					</li>
					<li  class="curr">
					  <span><a href="${BasePath}/yitiansystem/merchants/manage/draft_supplier_List.sc"  class="btn-onselc">草稿列表</a></span>
					</li>
				</ul>
			</div>
            <div class="modify">
                <input type="hidden" id="pageSize" value="20">
                <form action='${BasePath}/yitiansystem/merchants/manage/draft_supplier_List.sc' name="queryForm" id="queryForm" method="post">
                    <div class="wms-top">
                        <p>
                            <label class="first">商家名称：</label>
                            <input  type="text" name="supplier" id="supplier" value="<#if merchantsVo??&&merchantsVo.supplier??>${merchantsVo.supplier!""}</#if>"/>
                            <label>最后更新时间：</label>
                            <input type="text" name="effectiveDate" class="calendar" id="effectiveDate" value="<#if merchantsVo??&&merchantsVo.effectiveDate??>${(merchantsVo.effectiveDate)!""}</#if>"
                            > - <input type="text" name="failureDate" class="calendar" id="failureDate" value="<#if merchantsVo??&&merchantsVo.failureDate??>${(merchantsVo.failureDate)!""}</#if>">
                            <label>更新人：</label>
                            <input type="text" name="updateUser" id="updateUser" value="<#if merchantsVo??&&merchantsVo.updateUser??>${(merchantsVo.updateUser)!""}</#if>"/>
                            <input type="button" value="搜 索" id="searchBtn" class="yt-seach-btn">
                        </p>
                    </div>
                </form>
                <div id="content_list">
                    <table cellpadding="0" cellspacing="0" class="list_table" width="100%">
                        <thead>
                            <tr>
                                <th>商家名称</th>
                                <th>最后更新时间</th>
                                <th>更新人</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
                        <#if pageFinder??&&pageFinder.data??&&pageFinder.data?size!=0>
                    	<#list pageFinder.data as item >
	                        <tr>
		                        <td>${item['supplier']!""}</td>
                             	<td><#if item.updateDate??>${item['updateDate']?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
		                        <td>${item.updateUser?default("")}</td>
		                        <td><a href="#" onclick="to_add_supplier('${item['id']!''}')">编辑</a>
		                        <a href="#" onclick="del_draft('${item['id']!''}')">删除</a></td>
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
        $("#effectiveDate").calendar({ format:'yyyy-MM-dd HH:mm:ss'});
		$("#failureDate").calendar({ format:'yyyy-MM-dd HH:mm:ss'});

        $("#searchBtn").click(function() {
            queryMerchants();
        });
    });
 //根据条件查询招商信息
function queryMerchants(){
   document.queryForm.method="post";
   document.queryForm.submit();
}   
//跳转到添加商家信息页面
function addMerchants(){
    location.href="${BasePath}/yitiansystem/merchants/manage/to_add_supplier.sc";
}
function to_add_supplier(redisKey){
	if(redisKey!=""){
		location.href="${BasePath}/yitiansystem/merchants/manage/to_add_supplier.sc?redisKey="+redisKey;
	}else{
		ygdg.dialog.alert("请重新选择一条有效的草稿进行编辑!",function(){
			location.href="${BasePath}/yitiansystem/merchants/manage/draft_supplier_List.sc";
		});
	}
}
function del_draft(redisKey){
	if(redisKey!=""){
		$.ajax({ 
			type: "post", 
			url: "${BasePath}/yitiansystem/merchants/manage/del_supplier_draft.sc?redisKey=" + redisKey, 
			success: function(result){
				if( "success"==result ){
					ygdg.dialog.alert("删除成功！",function(){
						refreshpage();
					});
				}else{
					ygdg.dialog.alert(result);
				}
			} 
		});  
	}else{
		ygdg.dialog.alert("请重新选择一条有效的草稿进行删除!");
	}
}
</script>
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
</body>

</html>
