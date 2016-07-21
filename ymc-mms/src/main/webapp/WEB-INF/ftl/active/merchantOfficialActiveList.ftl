<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
   
    
     <link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
    <link rel="stylesheet" type="text/css" href="${BasePath}/css/yitiansystem/merchants/merchants.css" />
    
    <!-- 排序样式 -->
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/active/css/sortfilter.css"/>
    <!-- 小图标库的样式 -->
	<link rel="stylesheet" type="text/css" href="${BasePath}/css/yitiansystem/merchants/icon.css" />
	<link rel="stylesheet" type="text/css" href="${BasePath}/css/yitiansystem/merchants/iconfont.css" />
	
	<script type="text/javascript" src="${BasePath}/js/jquery-1.8.3.min.js"></script>
	<script type="text/ecmascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/artDialog/artDialog.js"></script>
	<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/js.js"></script>
	<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/order.js"></script>
	<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/supplier-contracts.js"></script>
	<!-- 日期 -->
	<script src="${BasePath}/js/common/form/datepicker/WdatePicker.js" type="text/javascript"></script>
    <title>优购商城--商家后台</title>
</head>

<body>
    <div class="container">
        <div class="toolbar">
            <div class="t-content"></div>
        </div>
        <!--工具栏start-->
        <div class="list_content">
            <div class="top clearfix">
                <ul class="tab">
                    <li class="curr">
                        <span>官方活动</span>
                    </li>
                </ul>
            </div>
            <div class="modify">
                <input type="hidden" id="pageSize" value="20">
                 <form action="${BasePath}/active/web/merchantOfficialActiveController/qeuryMerchantOfficialActive.sc" id="queryVoform" name="queryVoform" method="post">
                   <ul class="searchs w-auto clearfix">
                        <li>
                            <label for="">活动名称：</label>
                            <input type="text"  value="${(params.activeName)!''}" name="activeName"  id="activeName" class="w120" />
                        </li>
                         <li>
                            <label for="">活动类型：</label>
                            <select class="w120" name="activeType" id="activeType">
                            <option value="">请选择</option>
                               <#list activeType?keys as key>
										    <option value="${key}" <#if params.activeType??&&params.activeType == key>selected</#if>>${activeType[key]}</option>
								</#list>
                            </select>
                        </li>
                        <li>
                            <label for="">活动状态：</label>
                          
                            <select class="w120"   name="status">
                                    	<option value="">请选择</option>
                                        <option value="5" <#if params.status??&&(params.status?number == 5)>selected</#if>>已结束</option>
                                        <option value="6" <#if params.status??&&(params.status?number == 6)>selected</#if>>未开始</option>
                                        <option value="7" <#if params.status??&&(params.status?number == 7)>selected</#if>>活动中</option>
                                        <option value="10" <#if params.status??&&(params.status?number == 10)>selected</#if>>报名中</option>
                                        <option value="11" <#if params.status??&&(params.status?number == 11)>selected</#if>>审核中</option>
                                        
                            </select>
                        </li>
                        <li>
                            <label for="">报名时间：</label>
                            <input type="text"   value="${(params.createTimeStart_1)!''}"  name="createTimeStart_1"  name="calendar" class="calendar timeStart w120" id="createTimeStart_1" /> 至 <input type="text" value="${(params.createTimeEnd_1)!''}"  name="createTimeEnd_1"   name="calendar" class="calendar timeEnd" id="createTimeEnd_1" />
                        </li>
                        <li>
                            <label for="">审核时间：</label>
                            <input type="text" name="createTimeStart_2"  value="${(params.createTimeStart_2)!''}"  class="calendar timeStart w120" id="createTimeStart_2" /> 至 <input type="text" name="createTimeEnd_2" class="calendar timeEnd"    value="${(params.createTimeEnd_2)!''}"  id="createTimeEnd_2" />
                        </li>
                        <li>
                            <label for="">活动时间：</label>
                            <input type="text" name="createTimeStart_3" class="calendar timeStart w120"    value="${(params.createTimeStart_3)!''}" id="createTimeStart_3" /> 至 <input type="text" name="createTimeEnd_3" class="calendar timeEnd"   value="${(params.createTimeEnd_3)!''}" id="createTimeEnd_3" />
                        </li>
                        <li>
                           
                              <button class="ml10">搜 索</button>
                        </li>
                    </ul>
                </form>
                <div id="content_list">
                    <table cellpadding="0" cellspacing="0" class="list_table" width="100%">
                        <thead>
                            <tr>
                                <th>活动名称</th>
                                <th>活动类型</th>
                                <th>报名时间</th>
                                <th>审核时间</th>
                                <th>活动时间</th>
                                <th>报名数量</th>
                                <th>活动状态</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
                       <#if pageFinder?? && (pageFinder.data)?? > 
        					<#list pageFinder.data as item >
	                            <tr class="odd even">
	                                <td>${item.activeName!"—"}</td>
	                                <td>
	                                
	                                <#list activeType?keys as key> 
										    <#if key?number == item.activeType>	   
												${activeType[key]}				
											</#if>
										</#list>
										
										
								</td>
	                                <td>
	                                     <p>开始  ${item.signUpStartTime?string("yyyy-MM-dd HH:mm:ss ")}</p>
	                                    <p>结束   ${item.signUpEndTime?string("yyyy-MM-dd HH:mm:ss ")}</p>
	                                </td>
	                                <td>
	                                    <p>开始  ${item.merchantAuditStartTime?string("yyyy-MM-dd HH:mm:ss ")}</p>
	                                    <p>结束   ${item.merchantAuditEndTime?string("yyyy-MM-dd HH:mm:ss ")}</p>
	                                    
	                                </td>
	                                <td>
	                                    <p>开始  ${item.startTime?string("yyyy-MM-dd HH:mm:ss")}</p>
	                                    <p>结束   ${item.endTime?string("yyyy-MM-dd HH:mm:ss ")}</p>
	                                    
	                                </td>
	                                <td>${item.merchantCount}</td>
	                                <td>
		                                
										<#if item.activeState?number == 5>已结束</#if>
										<#if item.activeState?number == 6>未开始</#if>
										<#if item.activeState?number == 7>活动中</#if>
										<#if item.activeState?number == 10>报名中</#if>
										<#if item.activeState?number == 11>审核中</#if>
										
										
									</td>
	                                <td>
	                                    <a href="javascript:void(0);" onclick="View('${(item.id!'')}')">查看活动</a>
	                                    <#if (item.merchantCount?number>= 1)>
	                                    <a href="${BasePath}/active/web/merchantOfficialActiveController/queryMerchant.sc?activeId=${item.id}">查看商家</a>
	                                   </#if>
	                                   
	                                    
	                                    
	                                </td>
	                            </tr>
                             </#list>
	                   	 <#else>
								<tr>
									<td colspan="12" class="td-no">没有相关数据！</td>
								</tr>
						</#if>
                        </tbody>
                    </table>
                    <div class="bottom clearfix">
							<#if pageFinder??>
								<#import "../../../common.ftl" as page>
								<@page.queryForm formId="queryVoform"/>
							</#if>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">

    //活动规则
    function View(id){
        dialog_obj=$.dialog.open('${BasePath}/active/web/merchantOfficialActiveController/queryActiveRule.sc?activeId='+id,{
            title:"查看活动",
            max:false,
            min:false,
            width: '1130px',
            height: '870px',
            lock:true,
            cancel:function(){

            }
        });
    }

    //关闭窗口
    function dialog_close(){
        dialog_obj.close();
    }
	function mysubmit(){
		$("#queryVoform").submit();
	}
	
    </script>
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>  
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator-4.1.1.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidatorRegex.js"></script>
    
    
    
    
</body>

</html>
