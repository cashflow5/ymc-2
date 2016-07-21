<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>优购-商家中心-售后确认收货列表</title>
    <link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/base.css" />
    <link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css" />
</head>

<body>
    <script type="text/javascript" src="${BasePath}/yougou/js/bootstrap.js"></script>

        <div id="newmain" class="main_bd fr">
            <div class="main_container">
                <div class="normal_box">
                    <p class="title site">当前位置：商家中心 &gt; 售后 &gt; 售后单查询</p>
                    <div class="tab_panel  relative">
                        <div class="tab_content">
                            <!-- 单据打印导航tab模版引入 -->
                           
                            <!--搜索start-->
                            <div class="search_box">
                                <form method="post" id="queryForm" action="backOrderList.sc">
                                    <p>
                                        <span>
                                            <label>退回单号：</label>
                                            <input type="text" class="inputtxt" name="backCode" value="${(backOrderVo.backCode)!''}" />
                                        </span>
                                        <span>
                                            <label>订单号：</label>
                                            <input type="text" class="inputtxt" name="orderSubNo" value="${(params.orderSubNo)!''}" />
                                        </span>
                                        <span>
                                            <label>收货状态：</label>
                                            <select name="receiveStatus">
                                            	<option value="" <#if (backOrderVo.receiveStatus)?? >  <#else> selected="selected" </#if> >请选择 </option>
                                                <option value="0" <#if (backOrderVo.receiveStatus)?? &&(backOrderVo.receiveStatus==0)  > selected="selected" </#if> >待确认收货</option>
                                                <option value="1" <#if (backOrderVo.receiveStatus)?? &&(backOrderVo.receiveStatus==1)  > selected="selected" </#if> >已确认收货</option>
                                                <option value="2" <#if (backOrderVo.receiveStatus)?? &&(backOrderVo.receiveStatus==2)  > selected="selected" </#if> >部分确认收货</option>
                                            </select>
                                        </span>
                                        <span>
                                            <label>退货时间：</label>
                                            <input type="text" name="startDate" id="startDate" value="${(params.startDate)!''}" class="inputtxt" style="width:80px;" /> 至
                                        <input type="text" name="endDate" id="endDate" value="${(params.endDate)!''}" class="inputtxt" style="width:80px;" />
                                        </span>
                                        <span>
                                            <a class="button" id="searchBtn"><span>搜索</span></a>
                                        </span>
                                    </p>
                                </form>
                            </div>
                            <!--列表start-->
                            <table class="list_table">
                                <thead>
                                    <tr>
                                        <th>退回单号</th>
                                        <th>计划退回总数</th>
                                        <th>已退回总数</th>
                                        <th>收货状态</th>
                                        <th>退货人</th>
                                        <th>退货时间</th>
                                        <th>操作</th>
                                    </tr>
                                </thead>
                                <tbody>
                                	<#if (pageFinder.data)?? && (pageFinder.data)?size &gt; 0 >
                                		<#list pageFinder.data as item>
                                			<tr>
		                                        <td>${(item.backCode)!''}</td>
		                                        <td>${(item.planBackTotalCount)!'0'}</td>
		                                        <td>${(item.alreadyBackTotalCount)!'0'}</td>
		                                        <td>
		                                        	<#if (item.receiveStatus)?? && (item.receiveStatus == 0)>
		                                        		待确认收货
		                                        	<#elseif (item.receiveStatus)?? && (item.receiveStatus == 1)>
		                                        		已确认收货
		                                        	<#elseif (item.receiveStatus)?? && (item.receiveStatus == 2)>
		                                        		部分确认收货	
		                                        	<#else>
		                                        		待确认收货
		                                        	</#if>
		                                        </td>
		                                        <td>${(item.backOperator)!'--'}</td>
		                                        <td>${(item.backTime)!'--'}</td>
		                                        <td>
		                                            <#if (item.receiveStatus)?? && (item.receiveStatus == 0 || item.receiveStatus == 2)>
		                                        		<a target="_blank" href="${BasePath}/backOrder/backOrderDetailList.sc?handle=receive&mainId=${(item.id)!''}">处理</a>
		                                        	<#elseif (item.receiveStatus)?? && (item.receiveStatus == 1)>
		                                        		<a target="_blank" href="${BasePath}/backOrder/backOrderDetailList.sc?handle=query&mainId=${(item.id)!''}">查看</a>		                      
		                                        	</#if>
		                                        </td>
		                                    </tr>
                                		</#list>
                                	<#else>
                                		 <tr>
	                                        <td colspan = "7">暂无数据！</td>
	                                    </tr>	
                                	</#if>
                                	
                                   
                                    
                                </tbody>
                            </table>
                            <!--列表end--> 
                            <#--
                            <div class="page_box">
                                <div class="page">
                                    <span style="float:left;padding-right:15px;line-height:23px;">总记录行数：<font color ="red">3438</font>
                                    </span>
                                        <a class="prevdis" href="javascript:;">&nbsp;&nbsp;上一页</a>
                                                <a href="javascript:queryPage(1)" class="curr" >1</a>
                                                <a href="javascript:queryPage(2)" >2</a>
                                        <a href="javascript:queryPage(2)" class="next"  title="下一页"  >下一页</a>
                                    <input id="query.page" name="query.page" value="1"  onkeyup="value=value.replace(/[^\d]/g,'')"  class="inputtxt" type="text" value="1" title="输入页码后点击转到" style="float:left;height:20px;width:40px;text-align:center;" />
                                    <input class="btn_normal pagego_btn" type="button" name="" value="转到" onclick="queryPage(0);">
                                </div>
                                
                            </div>
                             --#>
                            <!--分页start-->
							<#if (pageFinder.data)??>
								<div class="page_box">
									<div class="dobox">
									</div>
										<#import "/manage/widget/common.ftl" as page>
										<@page.queryForm formId="queryForm"/>
								</div>
							</#if>
							<!--分页end-->

                        </div>
                    </div>
                </div>
            </div>
        </div>

</body>
<script type="text/javascript">
	//初始化时间控件
	$("#startDate").calendar({maxDate:'#endDate',diffDate:180});
	$("#endDate").calendar({minDate:'#startDate',diffDate:180});
	
	//搜索
	$("#searchBtn").click(function(){
		$("#queryForm").submit();
		
	});
</script>		
		
</html>
