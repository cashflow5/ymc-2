<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>数据智慧_首页概览</title>
    <!-- 日期插件样式 -->
    <link type="text/css" rel="stylesheet" href="${BasePath}/manage/report/css/WdatePicker.css" />
    <!-- 自定义，单页样式和公共样式 -->
    <link type="text/css" rel="stylesheet" href="${BasePath}/manage/report/css/ygui.css?${style_v}" />
    <link type="text/css" rel="stylesheet" href="${BasePath}/manage/report/css/index.css?${style_v}" />
    <link type="text/css" rel="stylesheet" href="${BasePath}/yougou/css/manage/report/report.css?${style_v}" />
</head>

<body>
    <!--header created time: 2015/04/21 by guoran start-->
    <!--公共头部 start-->
    <div class="yg-header">
        <div class="yg-header-content">
            <!-- 头部Logo & banner start-->
            <a href="javascript:;" class="yg-logo"></a>
            <!-- 头部Logo & banner end-->
            <!-- 头部导行 start-->
            <div class="yg-menu-box">
                <div class="yg-menu">
                    <ul class="navList">
                    <li class="select">
                            <a href="${BasePath }/report/gotoReportManagementSurvey.sc">
                                    首页
                                </a>
                        </li>
                        <li>
                            <a href="${BasePath }/report/reportRealTimeStatistics.sc">
                                    实时概况
                                </a>
                        </li>
                        <li >
                            <a href="${BasePath }/report/commodityAnalysisList.sc">
                                经营分析
                                </a>
                        </li>
                        <li>
                            <a href="${BasePath }/favoriteClassifyController/queryFavoriteClassifyInfo.sc">
                                    收藏夹
                                </a>
                        </li>
                    </ul>
                </div>
            </div>
            <!-- 头部导行 end-->
            <div class="yg-login-info">
                <p class="font-icon mt17 tright">欢迎您，<#if merchantUsers??>
                <#if ((merchantUsers.supplier)?length <= 20)>
                ${(merchantUsers.supplier)!''}
                <#else>
                ${(merchantUsers.supplier)[0..19]}...
                </#if>
               	 ：
               	 <#if ((merchantUsers.login_name)?length <= 8)>
                ${(merchantUsers.login_name)!''}
                <#else>
                ${(merchantUsers.login_name)[0..7]}...
                </#if>
                </#if> 
                <a class="ml15" href="${BasePath}/merchants/login/to_Back.sc" title="登出"><i class="iconfont">&#xe60c;</i></a></p>
            </div>
        </div>
    </div>
    <!--公共头部 end-->
    <!--header created time: 2015/04/21 by guoran end-->
    <div class="yg-body">
        <div class="yg-box">
            <div class="box-title">
                <h1>实时指标</h1> <small>更新时间：${realTimeStatisticsVo.currentTime!''}</small>
                <a href="${BasePath }/report/reportRealTimeStatistics.sc" target="_blank" class="fr mr10">实时概况 &gt;</a>
            </div>
            <div class="realtime-indicators">
                <ul class="tags">
                     <li>
                        <div class="hd">
                            <h3>支付订单数<i class="iconfont" title="统计时间内，已支付的订单数量">&#xe608;</i></h3>
                            <h1><#if realTimeStatisticsVo??>${realTimeStatisticsVo.payedOrder!''}</#if></h1>
                            <p>预计今天：<#if realTimeStatisticsVo??>${realTimeStatisticsVo.expectPayedOrder!''}</#if></p>
                        </div>
                        <div class="bd">
                            <span class="Gray">比昨日此时：</span>
                            <#if realTimeStatisticsVo??> 
                            	<#if realTimeStatisticsVo.compareYesterdayOrder?? && (realTimeStatisticsVo.compareYesterdayOrder < 0 )>
                            		<span class="Red">
                            		${realTimeStatisticsVo.compareYesterdayOrder!''}%
                            		</span>
                            	<#else>
                            		${realTimeStatisticsVo.compareYesterdayOrder!''}%	
                            	</#if>
                            	
                             </#if>
                            <span class="Gray ml5">昨日全天：</span><#if realTimeStatisticsVo??>${realTimeStatisticsVo.yesterdayOrder!''}</#if>
                            <span class="Gray ml5">预计比昨日：</span>
                            <#if realTimeStatisticsVo??>                  	
                            	<#if realTimeStatisticsVo.expectCompareYesterdayOrder?? && (realTimeStatisticsVo.expectCompareYesterdayOrder < 0 )>
                            		<span class="Red">
                            		${realTimeStatisticsVo.expectCompareYesterdayOrder!''}%
                            		</span>
                            	<#else>
                            		${realTimeStatisticsVo.expectCompareYesterdayOrder!''}%	
                            	</#if>
                            </#if>
                        </div>
                    </li>
                    <li>
                        <div class="hd blue2">
                            <h3>支付金额（元）<i class="iconfont" title="统计时间内，已支付的订单金额">&#xe608;</i></h3>
                            <h1><#if realTimeStatisticsVo??>${realTimeStatisticsVo.payedAmount!''}</#if></h1>
                            <p>预计今天：<#if realTimeStatisticsVo??>${realTimeStatisticsVo.expectPayedAmount!''}</#if></p>
                        </div>
                        <div class="bd">
                            <span class="Gray">比昨日此时：</span>
                            <#if realTimeStatisticsVo??>                            
                            	<#if realTimeStatisticsVo.compareYesterdayAmount?? && (realTimeStatisticsVo.compareYesterdayAmount < 0 )>
                            		<span class="Red">
                            		${realTimeStatisticsVo.compareYesterdayAmount!''}%
                            		</span>
                            	<#else>
                            		${realTimeStatisticsVo.compareYesterdayAmount!''}%	
                            	</#if>
                            </#if>
                            
                            <span class="Gray ml5">昨日全天：</span><#if realTimeStatisticsVo??>${realTimeStatisticsVo.yesterdayAmount!''}</#if>
                            <span class="Gray ml5">预计比昨天：</span>
                            <#if realTimeStatisticsVo??>
                            	<#if realTimeStatisticsVo.expectCompareYesterdayAmount?? && (realTimeStatisticsVo.expectCompareYesterdayAmount < 0 )>
                            		<span class="Red">
                            		${realTimeStatisticsVo.expectCompareYesterdayAmount!''}%
                            		</span>
                            	<#else>
                            		${realTimeStatisticsVo.expectCompareYesterdayAmount!''}%	
                            	</#if>
                            </#if>
                        </div>
                    </li>
                    <li class="last">
                        <div class="hd blue3">
                            <h3>订单均价（元）<i class="iconfont" title="统计时间内，支付金额/支付订单数">&#xe608;</i></h3>
                            <h1><#if realTimeStatisticsVo??>${realTimeStatisticsVo.payedAveragePrice!''}</#if></h1>
                            <p>预计今天：<#if realTimeStatisticsVo??>${realTimeStatisticsVo.expectPayedAveragePrice!''}</#if></p>
                        </div>
                        <div class="bd">
                            <span class="Gray">比昨日此时：</span>
                            <#if realTimeStatisticsVo??>
                            	<#if realTimeStatisticsVo.compareYesterdayAveragePrice?? && (realTimeStatisticsVo.compareYesterdayAveragePrice < 0 )>
                            		<span class="Red">
                            		${realTimeStatisticsVo.compareYesterdayAveragePrice!''}%
                            		</span>
                            	<#else>
                            		${realTimeStatisticsVo.compareYesterdayAveragePrice!''}%	
                            	</#if>
                            </#if>
                            <span class="Gray ml5">昨日全天：</span><#if realTimeStatisticsVo??>${realTimeStatisticsVo.yesterdayAveragePrice!''}</#if>
                            <span class="Gray ml5">预计比昨天：</span>
                            <#if realTimeStatisticsVo??>
                            	<#if realTimeStatisticsVo.expectCompareYesterdayAveragePrice?? && (realTimeStatisticsVo.expectCompareYesterdayAveragePrice < 0 )>
                            		<span class="Red">
                            		${realTimeStatisticsVo.expectCompareYesterdayAveragePrice!''}%
                            		</span>
                            	<#else>
                            		${realTimeStatisticsVo.expectCompareYesterdayAveragePrice!''}%	
                            	</#if>
                            </#if>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
        <div class="yg-box">
            <div class="box-title">
                <h1>经营概况</h1>
                <div class="box-filter">
                    <span class="box-fileter-item box-filter-buttons">
                    	<#-- <a href="javascript:;" id="yesterdayTotalIndexBtn" class="btn-default active" onclick="test();">昨天</a> -->
					    <a href="javascript:;" id="sevenDayTotalIndexBtn"  class="btn-default active" onclick="loadSevenDayTotalIndexData();">最近7天</a>
					    <a href="javascript:;" id="thityDayTotalIndexBtn" class="btn-default" onclick="loadThityDayTotalIndexData();">最近30天</a> 
    				</span>
                    <span class="box-fileter-item ml15 mr25">
                    	自定义时间范围 <input type="text" autocomplete="off" id="starttime-1" value="${startDate!''}"  class="daterange starttime">
                    	<input type="text" autocomplete="off" id="endtime-1" value="${endDate!''}" class="daterange ml10 endtime">
                    </span>
                    <#--
                    <span class="box-fileter-item box-filter-buttons">
                		<a href="javascript:;" class="btn-default" onclick = "loadTotalIndexData();">查询</a> 
                	</span>
                	-->
                	<span class="box-fileter-item relative">                   
                        <a class="ml25 btn-quota first" href="javascript:void(0);">指标 <span class="iconfont Gray"><i>&#xe60d;</i></span></a>                                           
                        <div class="quota-box hide">
                            <h1 class="quota-title">选择指标<span class="normal">(请选择7-15项)</span></h1>
                            <div class="quota-content">
                                <ul class="quota-list quota-general">
                                	<form id="indexForm">
            						<#if surveyIndexList??>
            							<#assign defaultSize = 0 />
								    	<#list surveyIndexList  as item >
								    		<#if item?? && item.is_default == "1">
								    			<#assign defaultSize = defaultSize + 1 />
								    			<li><input type="checkbox" autocomplete="off" class="quota-general" label="${item.label !''}" checked = "checked" name="manageServeyIndex" id="g-${item.en_name !''}" value="${item.en_name !''}"/><label for="g-${item.en_name !''}" >${item.label !''}</label></li>
								    		<#else>
								    			<li><input type="checkbox" autocomplete="off" class="quota-general" label="${item.label !''}" name="manageServeyIndex" id="g-${item.en_name !''}" value="${item.en_name !''}"/><label for="g-${item.en_name !''}" >${item.label !''}</label></li>
								    		</#if>
								    	</#list>
								    </#if>	
								    <input type="hidden" id="isNew" name="isNew" value="${isNew !''}">
								    </form>
                                </ul>
                            </div>
                        
                            <div class="quota-btn-box">
                                <span class="Gray checked-item">已选择<span id="checkedNum">${defaultSize !0}</span>项</span>
                                <a href="javascript:void(0);" class="btn-default active" onclick="changeShowIndex(this);">确定</a>
                                <a href="javascript:void(0);" class="btn-default ml5 quota-cancel">取消</a>
                            </div>
                        </div>
                    </span>
                </div>
            </div>
            <div class="box-content nav-default">
                <ul class="navs" id="indexBox">          
					<#-- 
                    <li class="active" identify="visitCount">
                        <div data-href="#content1" class="navitem" data-toggle="navitem" onclick="loadEveryDayOrHourData('visitCount');">
                            <p class="nav-title">访次（VV）<i class="iconfont" title="从访客来到您商品最终关闭了该单品页面，计为1次访问">&#xe608;</i></p>
                            <p class="nav-info" id="visitCount">0</p>
                        </div>
                    </li>
                    <li identify="pageView">
                        <div data-href="#content2" class="navitem" data-toggle="navitem" onclick="loadEveryDayOrHourData('pageView');">
                            <p class="nav-title">浏览量（PV）<i class="iconfont" title="统计时间段内，访问该商家商品的浏览次数，用以衡量网站用户访问的网页数量">&#xe608;</i></p>
                            <p class="nav-info" id="pageView">0</p>
                        </div>
                    </li>
                    <li identify="payedOrderNum"> 
                        <div data-href="#content3" class="navitem"  data-toggle="navitem" onclick="loadEveryDayOrHourData('payedOrderNum');">
                            <p class="nav-title">支付订单数<i class="iconfont" title="统计时间内，已支付的订单数量">&#xe608;</i></p>
                            <p class="nav-info" id="payedOrderNum">0</p>
                        </div>
                    </li>
                    <li identify="payedOrderAmt">
                        <div data-href="#content4" class="navitem"  data-toggle="navitem" onclick="loadEveryDayOrHourData('payedOrderAmt');">
                            <p class="nav-title">支付金额（元）<i class="iconfont" title="统计时间内，已支付的商品金额">&#xe608;</i></p>
                            <p class="nav-info" id="payedOrderAmt">0.00</p>
                        </div>
                    </li>
                    <li identify="payedOrderAvgAmt">
                        <div data-href="#content5" class="navitem"  data-toggle="navitem" onclick="loadEveryDayOrHourData('payedOrderAvgAmt');">
                            <p class="nav-title">订单均价（元）<i class="iconfont" title="支付金额/支付订单数">&#xe608;</i></p>
                            <p class="nav-info" id="payedOrderAvgAmt">0.00</p>
                        </div>
                    </li>
                    <li identify="changePercent">
                        <div data-href="#content6" class="navitem" data-toggle="navitem" onclick="loadEveryDayOrHourData('changePercent');">
                            <p class="nav-title">转化率 <i class="iconfont" title="收订订单数/商家所有商品的访次数量">&#xe608;</i></p>
                            <p class="nav-info Red" id="changePercent">0.00%</p>
                        </div>
                    </li>
                    <li  identify="deliveryPercent">
                        <div data-href="#content7" class="navitem" data-toggle="navitem" onclick="loadEveryDayOrHourData('deliveryPercent');">
                            <p class="nav-title">发货率 <i class="iconfont" title="已发货订单件数/已支付订单件数">&#xe608;</i></p>
                            <p class="nav-info" id="deliveryPercent">0.00%</p>
                        </div>
                    </li>
                    <li  identify="newSaleCommodityNum">
                        <div data-href="#content7" class="navitem" data-toggle="navitem" onclick="loadEveryDayOrHourData('newSaleCommodityNum');">
                            <p class="nav-title">新上架商品数 <i class="iconfont" title="统计时间内，第一次上架的商品数量">&#xe608;</i></p>
                            <p class="nav-info" id="newSaleCommodityNum">0.00</p>
                        </div>
                    </li>
                     <li identify="payedCommodityNum">
                        <div data-href="#content7" class="navitem" data-toggle="navitem" onclick="loadEveryDayOrHourData('payedCommodityNum');">
                            <p class="nav-title">支付件数 <i class="iconfont" title="统计时间内，已支付商品数量">&#xe608;</i></p>
                            <p class="nav-info" id="payedCommodityNum">0.00</p>
                        </div>
                    </li>
                    <li class="last" identify="avgCommdotiyAmt">
                        <div data-href="#content7" class="navitem" data-toggle="navitem" onclick="loadEveryDayOrHourData('avgCommdotiyAmt');">
                            <p class="nav-title">商品均价（元） <i class="iconfont" title="已支付商品金额/已支付商品件数">&#xe608;</i></p>
                            <p class="nav-info" id="avgCommdotiyAmt">0.00</p>
                        </div>
                    </li>
                    -->
                    <#if surveyIndexList??>
				    	<#list surveyIndexList  as item >
				    		<#if item?? && item.is_default == "1">
				    			<li class="active" identify="${item.en_name !''}">
			                        <div data-href="#content1" class="navitem" data-toggle="navitem" onclick="loadEveryDayOrHourData('${item.en_name  !''}');">
			                            <p class="nav-title">${item.label  !''}<i class="iconfont" title="${item.descText  !''}">&#xe608;</i></p>
			                            <p class="nav-info" id="${item.en_name  !''}">0</p>
			                        </div>
			                    </li>
				    		</#if>
				    	</#list>
				    </#if>	
                </ul>
                <div class="nav-content">
                    <div class="nav-content-item active" id="content1">
                        <div class="filter-box">
                            <div class="filter-select">
                                <select class="fl" name="firstIndexName" autocomplete="off" id="firstIndexName" data-ui-type="dropdown">
                                	<#--
                                    <option value="visitCount" >访次</option>
                                    <option value="pageView">浏览量</option>
                                    <option value="payedOrderNum">支付订单数</option>
                                    <option value="payedOrderAmt">支付金额</option>
                                    <option value="payedOrderAvgAmt">订单均价</option>
                                    <option value="changePercent">转化率</option>
                                    <option value="deliveryPercent">发货率</option>
                                    <option value="newSaleCommodityNum">新上架商品数</option>
                                    <option value="payedCommodityNum">支付件数</option>
                                    <option value="avgCommdotiyAmt">商品均价</option>
                                    -->
                                    <#if surveyIndexList??>
								    	<#list surveyIndexList  as item >
								    		<#if item?? && item.is_default == "1">								    			
												 <option value="${item.en_name  !''}">${item.label  !''}</option>								  
								    		</#if>
								    	</#list>
								    </#if>	
                                </select>
                                <span class="fl">对比</span>
                                <select class="fl" name="secondIndexName" autocomplete="off" id="secondIndexName" data-ui-type="dropdown" >
                                    <option value="" >请选择</option> 
                                    <#--
                                    <option value="visitCount">访次</option>
                                    <option value="pageView">浏览量</option>
                                    <option value="payedOrderNum">支付订单数</option>
                                    <option value="payedOrderAmt">支付金额</option>
                                    <option value="payedOrderAvgAmt">订单均价</option>
                                    <option value="changePercent">转化率</option>
                                    <option value="deliveryPercent">发货率</option>
                                    <option value="newSaleCommodityNum">新上架商品数</option>
                                    <option value="payedCommodityNum">支付件数</option>
                                    <option value="avgCommdotiyAmt">商品均价</option>
                                      -->
                                     <#if surveyIndexList??>
								    	<#list surveyIndexList  as item >
								    		<#if item?? && item.is_default == "1">								    			
												 <option value="${item.en_name  !''}">${item.label  !''}</option>								  
								    		</#if>
								    	</#list>
								    </#if>	  
                                </select>
                            </div>
                        </div>
                        <div id="visitorsNumber1" class="item-container">
                        </div>
                    </div>
                    <#--
                    <div class="nav-content-item" id="content2">
                        <div class="filter-box">
                        </div>
                        <div id="visitorsNumber2" class="item-container">
                        </div>
                    </div>
                    <div class="nav-content-item" id="content3">
                        <div class="filter-box">
                        </div>
                        <div id="visitorsNumber3" class="item-container">
                        </div>
                    </div>
                    <div class="nav-content-item" id="content4">
                        <div class="filter-box">
                        </div>
                        <div id="visitorsNumber4" class="item-container">
                        </div>
                    </div>
                    <div class="nav-content-item" id="content5">
                        <div class="filter-box">
                        </div>
                        <div id="visitorsNumber5" class="item-container">
                        </div>
                    </div>
                    <div class="nav-content-item" id="content6">
                        <div class="filter-box">
                        </div>
                        <div id="visitorsNumber6" class="item-container">
                        </div>
                    </div>
                    <div class="nav-content-item" id="content7">
                        <div class="filter-box">
                        </div>
                        <div id="visitorsNumber7" class="item-container">
                        </div>
                    </div>
                    -->
                </div>
            </div>
        </div>
        <div class="yg-box">
             <form class="commodityStyle">
             <div class="box-title">
                <h1>商品分析</h1>
                <div class="box-filter">
                    <span class="box-fileter-item box-filter-buttons commoditySpan"><a href="javascript:setDate(1);" class="btn-default active">昨天</a>
					<a href="javascript:setDate(2);" class="btn-default">最近7天</a>
				    <a href="javascript:setDate(3);" class="btn-default">最近30天</a> 
					</span>
                    <span class="box-fileter-item ml15">自定义时间范围 <input type="text" id="starttime-2" name="queryStartDate" value="${(Parameters['queryStartDate'])!'' }" class="daterange starttime">
                    <input type="text" id="endtime-2" name="queryEndDate" value="${(Parameters['queryEndDate'])!'' }" class="daterange ml10 endtime"></span>
                    <span class="box-fileter-item relative">
                        <a class="ml25 btn-quota first" href="javascript:getIndex($('#dimensions').val());">指标 <span class="iconfont Gray"><i>&#xe60d;</i></span></a>
                        <a class="ml25 mr25" href="javascript:exportExcel();" data-toggle="tooltip" data-placement="top" id="exportData" title="根据时间跨度导出">
                        <span class="iconfont Gray"><i>&#xe60a;</i></span> 导出<span class="hide" id="progressBar" ><img src='${BasePath}/yougou/images/loading.gif'/><span class="iconfont Gray">0</span>%</span></a>
                        <div class="quota-box hide" id="analysisQuota">
                        </div>
                    </span>
                </div>
            </div>
            <div class="box-search">
                <#--<div class="yg-senior fr">高级</div>-->
                <!-- start search -->
                <div class="yg-search fr">
                    <i class="yg-search-ico"></i>
                    <input type="text" name="keywords" autocomplete="off" class="yg-search-input" value="请输入商品名称或商品编码" />
                </div>
                <!-- end search -->
                <!-- start 下拉框 -->
                <select class="fr" name="dimensions" autocomplete="off" id="dimensions" data-ui-type="dropdown">
                    <option value="1">按商品维度</option>
                    <option value="2">按分类维度</option>
                </select>
                <!-- end 下拉框 -->
            </div>
            <input type="hidden" name="dateCondition" id="dateCondition" value="1"/>
            <input type="hidden" name="sortDirection" id="sortDirection" value="0"/>
            <input type="hidden" name="sortBy" id="sortBy" value=""/> 
            </form>
            <!-- start 查询条件 -->
            <div class="yg-query">
                <ul class="query-list">
                    <li class="query-item">
                        <label class='title'>分类名称:</label>
                        <select name="goodsd" id="q-1" data-ui-type="dropdown">
                            <option value="1">全部</option>
                        </select>
                        <select name="goodsd" id="q-2" data-ui-type="dropdown">
                            <option value="1">全部</option>
                            <option value="2">按分类维度</option>
                            <option value="3">选项3</option>
                            <option value="4">选项4</option>
                            <option value="5">选项5</option>
                        </select>
                        <select name="goodsd" id="q-3" data-ui-type="dropdown">
                            <option value="1">全部</option>
                            <option value="2">按分类维度</option>
                            <option value="3">选项3</option>
                            <option value="4">选项4</option>
                            <option value="5">选项5</option>
                        </select>
                    </li>
                    <li class="query-item">
                        <label class='title'>年份:</label>
                        <select name="year" id="q-4" data-ui-type="dropdown">
                            <option value="1">全部</option>
                            <option value="2">按分类维度</option>
                            <option value="3">选项3</option>
                            <option value="4">选项4</option>
                            <option value="5">选项5</option>
                        </select>
                    </li>
                    <li class="query-item">
                        <label class='title'>季节:</label>
                        <select name="year" id="q-4" data-ui-type="dropdown">
                            <option value="1">全部</option>
                            <option value="2">按分类维度</option>
                            <option value="3">选项3</option>
                            <option value="4">选项4</option>
                            <option value="5">选项5</option>
                        </select>
                    </li>
                    <li class="query-item">
                        <label class='title'>商品状态:</label>
                        <select name="year" id="q-4" data-ui-type="dropdown">
                            <option value="1">全部</option>
                            <option value="2">按分类维度</option>
                            <option value="3">选项3</option>
                            <option value="4">选项4</option>
                            <option value="5">选项5</option>
                        </select>
                    </li>
                    <li class="query-item">
                        <label class='title'>支付数量:</label>
                        <input type="text" class="number">
                        <span>至</span>
                        <input type="text" class="number">
                    </li>
                    <li class="query-item">
                        <label class='title'>库存数量:</label>
                        <input type="text" class="number">
                        <span>至</span>
                        <input type="text" class="number">
                    </li>
                    <li class="query-item">
                        <label class='title'>上架天数:</label>
                        <input type="text" class="number">
                        <span>至</span>
                        <input type="text" class="number">
                    </li>
                    <li class="query-item">
                        <label class='title'>优购价:</label>
                        <input type="text" class="number">
                        <span>至</span>
                        <input type="text" class="number">
                    </li>
                    <li class="query-item">
                        <label class='title'>是否收藏:</label>
                        <select name="year" id="q-4" data-ui-type="dropdown">
                            <option value="1">全部</option>
                            <option value="2">按分类维度</option>
                            <option value="3">选项3</option>
                            <option value="4">选项4</option>
                            <option value="5">选项5</option>
                        </select>
                    </li>
                    <li class="query-item pd9">
                        <a class="btn-default active yes-btn">确定</a>
                        <a class="btn-default no-btn">取消</a>
                    </li>
                </ul> 
           </div> 
           <!-- end 查询条件 -->

	        <!-- 商品分析、品类分析表格开始 -->
	        <div id="prodAnalyseTable"> </div>         
        </div>
          <div style="margin-bottom: 90px"></div>
    </div>
    <!-- 底部 start-->
    <div class="yg-footer"></div>
    <!-- 底部 end-->   
    <script type="text/javascript" src="${BasePath}/manage/report/hcharts/js/jquery-1.8.2.min.js"></script>	       
	<!-- 日期插件 -->
    <script type="text/javascript" src="${BasePath}/manage/report/hcharts/js/WdatePicker.js"></script>	
    <!-- 滚动条插件 -->
    <script type="text/javascript" src="${BasePath}/manage/report/hcharts/js/nicescroll.js"></script>		
    <script type="text/javascript" src="${BasePath}/manage/report/hcharts/js/highcharts.js"></script>
    <script type="text/javascript" src="${BasePath}/manage/report/hcharts/js/grid-yg.js"></script> 
    <script type="text/javascript" src="${BasePath}/manage/report/hcharts/js/yg.common.js"></script>
    <script type="text/javascript" src="${BasePath}/manage/report/hcharts/js/yg.index.js"></script>
    <script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=blue"></script>
    <script type="text/javascript" src="${BasePath}/yougou/js/ygui.js"></script>
    <script type="text/javascript" src="${BasePath}/yougou/js/tools/bootstrap-tooltip.js"></script>
    <script>  
    var basePath = "${BasePath}";
    var exportResult = "${exportResult!''}";
    var isExportData = "${isExportData!''}";
    var progress = "${progress!0}";
    var setId = "";
    $(function() {
    	$("#dateCondition").val(1);
    	$("#dimensions").val(1);
    	$('#prodAnalyseTable').delegate('span.yg-sort','click', function(){
        	$("#sortBy").val($(this).find("input[data-ui-type='sort']").val());
            if($(this).children("span.icon").hasClass("checked")){
            	$("#sortDirection").val(1);
            }else{
            	$("#sortDirection").val(0);
            }
            searchForm();
        });
        
        //汇总指标第一个指标改变加载双Y轴数据，或者单个指标数据
        $('#firstIndexName').on('change', function() {
        	$("#indexBox ul").removeClass("active");
        	//对比的第二个指标未选，只加载第一个指标
	        if($(this).val()!='' && $('#secondIndexName').val()==''){
	       		 loadEveryDayOrHourData($(this).val());
        	}else if($(this).val()!='' && $('#secondIndexName').val()!=''){
        		 loadEveryDayOrHourData();
        	}
        });
        //汇总指标第二个指标改变加载双Y轴数据
        $('#secondIndexName').on('change', function() {
	        if($(this).val()!=''){
	       		 loadEveryDayOrHourData();
        	}
        });
	    // 日期函数调用
        G.Index.dataTime('.starttime');
        G.Index.dataTime('#endtime-1','',{
        	onpicked:function(){
        		if($('#starttime-1').val()!=''){
        			$("#sevenDayTotalIndexBtn").removeClass("active");
        			$("#thityDayTotalIndexBtn").removeClass("active");
        			loadTotalIndexData();
        			$(this).blur();
        		}
        	}	
        });
        G.Index.dataTime('#endtime-2','',{
        	onpicked:function(){
        		if($('#starttime-2').val()!=''){
        			$(".commoditySpan a").removeClass("active");
            		$("#dateCondition").val("1");
	        		searchForm();
	        		$(this).blur();
            	}
            }
        });
	    //点击高级收缩功能
	    G.Index.senior();
	    //点击搜索框按钮
	    G.Index.search();
	    $('.nav-default').NavPageScroll(function(e, id) {
	        if (!e.data('access')) {
	           // G.Index.bindChart(id, '访客数(UV)', categories, '人', series);
	        }
	    });
	    //指标click函数执行
    	G.Index.quotaClick();
	     //加载汇总指标
	    loadTotalIndexData();
	});
	var curPage = 1;
	var pageSize = 10;
	// 加载分页列表
	function loadData(pageNo,pageSize,loadUrl){
		$('.loadImg').remove();
		$("#prodAnalyseTable").empty().after('<div class="loading_img loadImg"><img src="${BasePath}/manage/report/images/loading-spinner-blue.gif"/></div>');
		$.ajax({
			cache : false,
			type : 'POST',
			dataType : "html",
			data:$("form.commodityStyle").serialize()+"&page="+pageNo+"&pageSize="+pageSize,
			url : "${BasePath}"+loadUrl,  
			success : function(data) {
				$('.loadImg').remove();
				$("#prodAnalyseTable").html(data);
				$('[data-ui-type=sort]','#prodAnalyseTable').Sort();
				YGUI.collect();
				if($("#sortIndex").val()!=''){
					$("#sortBy").val($("#sortIndex").val());
				}
				if($("#sortBy").val()==''){
					$("#sortBy").val($(".yg-table").find("input[name='displayCol']:eq(0)").val());
				}
				var sortBy = $("#sortBy").val();
				if("1"==$("#sortDirection").val()){
					$("input[name='displayCol'][value='"+sortBy+"']").removeAttr("Nenabled")
						.parents('.yg-sort').children(".icon").removeClass("disabled").addClass("checked");
				}else{
					$("input[name='displayCol'][value='"+sortBy+"']").removeAttr("Nenabled")
					.parents('.yg-sort').children(".icon").removeClass("disabled").removeClass("checked");
				}
			}
		});
	}
	
	//选择指标后重新动态加载，指标页签 以及对比指标的下拉选项
	function changeShowIndex($this){		
		var len = $("[name='manageServeyIndex']:checked").length;
		if(len < 7){
			ygdg.dialog.alert("请选择7-15项指标！");
			return false;
		}		
		
		//清空指标页签，对比下拉指标选项
		$("#indexBox").html("");
		$("#firstIndexName").html("");
		$("#secondIndexName").html('<option value="" >请选择</option>');
		var title = "";
		var indexHtml = "";
		var count = 0;
		var option = "";
		$("[name='manageServeyIndex']:checked").each(function(index,obj){
			// 拼装指标页签html 代码，并动态加载和渲染到页面
			 var chName = getIndexChName($(obj).val());
			 title = chName.desc;	
			 if(count ==0 ){
				 indexHtml ='<li class="active" identify="'+$(obj).val()+'">';
			 }else if( (count + 1)==len ){
			 	 indexHtml ='<li class="last" identify="'+$(obj).val()+'">';
			 }else{
			 	 indexHtml ='<li identify="'+$(obj).val()+'">';
			 }
		  	 indexHtml += '<div data-href="#content1" class="navitem" data-toggle="navitem" data-access="true" onclick="loadEveryDayOrHourData(\''+$(obj).val()+'\');">';
             indexHtml += '<p class="nav-title">'+$(obj).attr("label")+'<i class="iconfont" title="'+title+'">&#xe608;</i></p>';
             indexHtml += '<p class="nav-info" id="'+$(obj).val()+'">0</p>';
             indexHtml += '</div>';
             indexHtml += '</li>';
		    $("#indexBox").append(indexHtml);
		    count++;
		    
		    // 拼装对比指标下拉选择option
		    option += '<option value="'+$(obj).val()+'">'+$(obj).attr("label")+'</option>';
		    
		});
		//渲染到页面
		loadIndexHtml();
		$("#firstIndexName").append(option).data('ui').refresh();
		$("#secondIndexName").append(option).data('ui').refresh();
		
		 $($this).parent().parent().addClass('hide');
		//加载指标数据
		loadTotalIndexData();
	}
	
	//重新渲染指标页签
	function loadIndexHtml(){
		$('.nav-default').NavPageScroll(function(e, id) {
	        if (!e.data('access')) {
	           // G.Index.bindChart(id, '访客数(UV)', categories, '人', series);
	        }
	    });
    }
     function  saveFavoriteInfo(ficationClassifyId,mmodityCodeId){
    
	   $.ajax({
	  			cache : false,
	  			type : 'POST',
	  			dataType : "json",
	  			data:"favoriteIds="+ficationClassifyId+"&commodity_id="+mmodityCodeId,
	  			url :basePath+"/favoriteClassifyController/saveFavoriteCommodity.sc",  
	  			success : function(data) {
	  			}
	  		});
	}
    
    </script>
    <script type="text/javascript" src="${BasePath}/yougou/js/manage/report/report.js?${style_v}"></script>
</body>

</html>
