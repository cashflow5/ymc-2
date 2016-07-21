<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>数据智慧_实时数据</title>
	<link type="text/css" rel="stylesheet" href="${BasePath}/manage/report/css/ygui.css?${style_v}" />
	<link type="text/css" rel="stylesheet" href="${BasePath}/manage/report/css/detail.css?${style_v}" />
	<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/css/mold.css?${style_v}"/>

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
                        <li >
                            <a href="${BasePath }/report/gotoReportManagementSurvey.sc">
                                    首页
                                </a>
                        </li>
                        <li class="select">
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
                <p class="font-icon mt17 tright">欢迎您，<#if merchantUsers??>${merchantUsers.supplier!''}</#if>：<#if merchantUsers??>${merchantUsers.login_name!''}</#if> <a class="ml15" href="${BasePath}/merchants/login/to_Back.sc" title="登出"><i class="iconfont">&#xe60c;</i></a></p>
            </div>
        </div>
    </div>
    <!--公共头部 end-->
    <!--header created time: 2015/04/21 by guoran end-->
    <div class="yg-body">
    	<div class="yg-box">
            <div class="box-title">
                <h1>实时指标</h1> 
                <small>更新时间：${realTimeStatisticsVo.currentTime!''}</small>
                <!-- <a href="javascript:;" class="fr mr10">实时概况 <span class="icon iconfont f12">&#xe610;</span></a> -->
                <!-- start 下拉框 -->
                <span class="detail-select fr">
                	<form id="queryForm" name="queryForm" method="post" action="${BasePath!''}/report/reportRealTimeStatistics.sc">
                	<label class="detail_item_label"><span class="detail_item_star"></span>&nbsp;品牌： </label>
					
					<select id="brandNo" name="brandNo" data-ui-type="dropdown">
						<option value="">请选择</option>
						<#if lstBrand??>
							<#list lstBrand as item>
								<option value="${(item.brandNo)!""}"  brandId="${(item.id)!''}">${(item.brandName)!""}</option>
							</#list>
						</#if>
						<#--<#if realTimeStatisticsVo?? && item?? > <#if realTimeStatisticsVo.brandNo == item.brandNo> selected=selected </#if> </#if> -->
					</select>
					<label class="detail_item_label"><span class="detail_item_star"></span>&nbsp;分类： </label>
                    <select name="rootCattegory" id="category1" data-ui-type="dropdown" linkage="true">
                        <option value="" selected="selected">一级分类</option>
                        <#--
					    <#if lstCat??>
						    <#list lstCat as item>
						    	<option <#if realTimeStatisticsVo.rootCattegory??&&item.structName??&&realTimeStatisticsVo.rootCattegory==item.structName >selected</#if> value="${(item.structName)!""}">${(item.catName)!""}</option>
						    </#list>
					    </#if>
					    -->
                    </select>
                    <select name="secondCategory" id="category2" data-ui-type="dropdown" linkage="true">
                        <option value="" selected="selected">二级分类</option>
                    </select>
                    <select name="threeCategory" id="category3" data-ui-type="dropdown" linkage="true">
                        <option value="" selected="selected">三级分类</option>
                    </select>
                    <a class="btn-default mr7 no-btn" onclick="queryRealTimeInfo();">查询</a>
                    </form>
                </span>
                <!-- end 下拉框 -->
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
        	<div class="today-record">
	        	<div class="today-number">
	        		<h1 class="record-title">支付订单数</h1>
	        		<div class="record-content record-number"></div>
	        		<#--
	        		<div class="record-scale">
	        			<div class="record-news fl">新用户<#if realTimeStatisticsVo??>${realTimeStatisticsVo.newCustomOrder!''}</#if>(<span class="percent"><#if realTimeStatisticsVo??>${realTimeStatisticsVo.newCustomOrderPercent!''}</#if>%</span>)</div>
	        			<span class="record-old">老用户<#if realTimeStatisticsVo??>${realTimeStatisticsVo.newCustomOrder!''}</#if>(<#if realTimeStatisticsVo??>${realTimeStatisticsVo.newCustomOrderPercent!''}</#if>%)</span>
	        		</div>
	        		-->
	        		
	        		<div class="center pt20 pb20">
                        <span class="Gray">今日:<a class="Black" title="<#if realTimeStatisticsVo??>${realTimeStatisticsVo.payedOrder!''}</#if>"><#if realTimeStatisticsVo??>${realTimeStatisticsVo.payedOrder!''}</#if></a></span>
                        <span class="pl10 Gray">预计今日:<a class="Black" title="<#if realTimeStatisticsVo??>${realTimeStatisticsVo.expectPayedOrder!''}</#if>"><#if realTimeStatisticsVo??>${realTimeStatisticsVo.expectPayedOrder!''}</#if></a></span>
                        <span class="pl10 Gray">比昨日此时：
                        	<a class="<#if realTimeStatisticsVo.compareYesterdayOrder?? && (realTimeStatisticsVo.compareYesterdayOrder < 0 )> Red <#else> Black </#if>" title="<#if realTimeStatisticsVo??>${realTimeStatisticsVo.compareYesterdayOrder!''}</#if>%">
                        		<#if realTimeStatisticsVo??>${realTimeStatisticsVo.compareYesterdayOrder!''}</#if>%
                        	</a>
                        </span>
                        <span class="pl10 Gray">昨日全天:<a class="Black" title="<#if realTimeStatisticsVo??>${realTimeStatisticsVo.yesterdayOrder!''}</#if>"><#if realTimeStatisticsVo??>${realTimeStatisticsVo.yesterdayOrder!''}</#if></a></span>
                        <span class="pl10 Gray">预计比昨天:
                        	<a class="<#if realTimeStatisticsVo.expectCompareYesterdayOrder?? && (realTimeStatisticsVo.expectCompareYesterdayOrder < 0 )> Red <#else> Black </#if>" title="<#if realTimeStatisticsVo??>${realTimeStatisticsVo.expectCompareYesterdayOrder!''}</#if>%">
                        		<#if realTimeStatisticsVo??>${realTimeStatisticsVo.expectCompareYesterdayOrder!''}</#if>%
                        	</a>
                        </span>
                    </div>
                    
	        		<#--<ul class="record-list">
	        			<li class="record-list-li w14 tleft">今日:<span class="Black"><#if realTimeStatisticsVo??>${realTimeStatisticsVo.payedOrder!''}</#if></span></li>
	        			<li class="record-list-li tleft">预计今日:<span class="Black"><#if realTimeStatisticsVo??>${realTimeStatisticsVo.expectPayedOrder!''}</#if></span></li>
	        			<li class="record-list-li w24 center">比昨日此时：
	        				<span class="<#if realTimeStatisticsVo.compareYesterdayOrder?? && (realTimeStatisticsVo.compareYesterdayOrder < 0 )> Red <#else> Black </#if>"><#if realTimeStatisticsVo??>${realTimeStatisticsVo.compareYesterdayOrder!''}</#if></span>
	        			</li>
	        			<li class="record-list-li center">昨日全天:<span class="Black"><#if realTimeStatisticsVo??>${realTimeStatisticsVo.yesterdayOrder!''}</#if></span></li>
	        			<li class="record-list-li w24 tright">预计比昨天:<span class="<#if realTimeStatisticsVo.expectCompareYesterdayOrder?? && (realTimeStatisticsVo.expectCompareYesterdayOrder < 0 )> Red <#else> Black </#if>"><#if realTimeStatisticsVo??>${realTimeStatisticsVo.expectCompareYesterdayOrder!''}</#if></span></li>
	        		</ul> -->
	        	</div>
	        	<div class="today-number">
	        		<h1 class="record-title">支付金额（元）</h1>
	        		<div class="record-content record-amount"></div>
	        		<#--
	        		<div class="record-scale">
	        			<div class="record-news fl">新用户<#if realTimeStatisticsVo??>${realTimeStatisticsVo.newCustomAmount!''}</#if>(<span class="percent"><#if realTimeStatisticsVo??>${realTimeStatisticsVo.newCustomAmountPercent!''}</#if>%</span>)</div>
	        			<span class="record-old">老用户<#if realTimeStatisticsVo??>${realTimeStatisticsVo.newCustomAmount!''}</#if>(<#if realTimeStatisticsVo??>${realTimeStatisticsVo.oldCustomAmountPercent!''}</#if>)%</span>
	        		</div>
	        		-->
	        		
	        		<div class="center pt20 pb20">
                        <span class="Gray">今日:<a class="Black" title="<#if realTimeStatisticsVo??>${realTimeStatisticsVo.payedAmount!''}</#if>"><#if realTimeStatisticsVo??>${realTimeStatisticsVo.payedAmount!''}</#if></a></span>
                        <span class="pl10 Gray">预计今日:<a class="Black" title="<#if realTimeStatisticsVo??>${realTimeStatisticsVo.expectPayedAmount!''}</#if>"><#if realTimeStatisticsVo??>${realTimeStatisticsVo.expectPayedAmount!''}</#if></a></span>
                        <span class="pl10 Gray">比昨日此时：
                        	<a class="<#if realTimeStatisticsVo.compareYesterdayAmount?? && (realTimeStatisticsVo.compareYesterdayAmount < 0 )> Red <#else> Black </#if>" title="<#if realTimeStatisticsVo??>${realTimeStatisticsVo.compareYesterdayAmount!''}</#if>%">
                        	<#if realTimeStatisticsVo??>${realTimeStatisticsVo.compareYesterdayAmount!''}</#if>%
                        	</a>
                        </span>
                        <span class="pl10 Gray">昨日全天:<a class="Black" title="<#if realTimeStatisticsVo??>${realTimeStatisticsVo.yesterdayAmount!''}</#if>"><#if realTimeStatisticsVo??>${realTimeStatisticsVo.yesterdayAmount!''}</#if></a></span>
                        <span class="pl10 Gray">预计比昨天:
                        	<a class="<#if realTimeStatisticsVo.expectCompareYesterdayAmount?? && (realTimeStatisticsVo.expectCompareYesterdayAmount < 0 )> Red <#else> Black </#if>" title="<#if realTimeStatisticsVo??>${realTimeStatisticsVo.expectCompareYesterdayAmount!''}</#if>%">
                        	<#if realTimeStatisticsVo??>${realTimeStatisticsVo.expectCompareYesterdayAmount!''}</#if>%
                        	</a>
                        </span>
                    </div>
	        		<#--<ul class="record-list">
	        			<li class="record-list-li w14 tleft">今日:<span class="Black"><#if realTimeStatisticsVo??>${realTimeStatisticsVo.payedAmount!''}</#if></span></li>
	        			<li class="record-list-li tleft">预计今日:<span class="Black"><#if realTimeStatisticsVo??>${realTimeStatisticsVo.expectPayedAmount!''}</#if></span></li>
	        			<li class="record-list-li w24 center">比昨日此时：<span class="<#if realTimeStatisticsVo.compareYesterdayAmount?? && (realTimeStatisticsVo.compareYesterdayAmount < 0 )> Red <#else> Black </#if>"><#if realTimeStatisticsVo??>${realTimeStatisticsVo.compareYesterdayAmount!''}</#if></span></li>
	        			<li class="record-list-li center">昨日全天:<span class="Black"><#if realTimeStatisticsVo??>${realTimeStatisticsVo.yesterdayAmount!''}</#if></span></li>
	        			<li class="record-list-li w24 tright">预计比昨天:<span class="<#if realTimeStatisticsVo.expectCompareYesterdayAmount?? && (realTimeStatisticsVo.expectCompareYesterdayAmount < 0 )> Red <#else> Black </#if>"><#if realTimeStatisticsVo??>${realTimeStatisticsVo.expectCompareYesterdayAmount!''}</#if></span></li>
	        		</ul>-->
	        	</div>
        	</div>
        	 <div class="today-record">
	        	<div class="today-number">
	        		<h1 class="record-title">订单均价（元）</h1>
	        		<div class="record-content average-order"></div>
	        		
	        		<div class="center pt20 pb20">
                        <span class="Gray">今日:<a class="Black" title="<#if realTimeStatisticsVo??>${realTimeStatisticsVo.payedAveragePrice!''}</#if>"><#if realTimeStatisticsVo??>${realTimeStatisticsVo.payedAveragePrice!''}</#if></a></span>
                        <span class="pl10 Gray">预计今日:<a class="Black" title="<#if realTimeStatisticsVo??>${realTimeStatisticsVo.expectPayedAveragePrice!''}</#if>"><#if realTimeStatisticsVo??>${realTimeStatisticsVo.expectPayedAveragePrice!''}</#if></a></span>
                        <span class="pl10 Gray">比昨日此时：
                        	<a class="<#if realTimeStatisticsVo.expectCompareYesterdayOrder?? && (realTimeStatisticsVo.expectCompareYesterdayOrder < 0 )> Red <#else> Black </#if>" title="<#if realTimeStatisticsVo??>${realTimeStatisticsVo.compareYesterdayAveragePrice!''}</#if>%">
                        		<#if realTimeStatisticsVo??>${realTimeStatisticsVo.compareYesterdayAveragePrice!''}</#if>%
                        	</a>
                        </span>
                        <span class="pl10 Gray">昨日全天:<a class="Black" title="<#if realTimeStatisticsVo??>${realTimeStatisticsVo.yesterdayAveragePrice!''}</#if>"><#if realTimeStatisticsVo??>${realTimeStatisticsVo.yesterdayAveragePrice!''}</#if></a></span>
                        <span class="pl10 Gray">预计比昨天:
                        	<a class="<#if realTimeStatisticsVo.expectCompareYesterdayAveragePrice?? && (realTimeStatisticsVo.expectCompareYesterdayAveragePrice < 0 )> Red <#else> Black </#if>" title="<#if realTimeStatisticsVo??>${realTimeStatisticsVo.expectCompareYesterdayAveragePrice!''}</#if>%">
                        		<#if realTimeStatisticsVo??>${realTimeStatisticsVo.expectCompareYesterdayAveragePrice!''}</#if>%
                        	</a>
                        </span>
                    </div>
                    <#--
	        		<ul class="record-list">
	        			<li class="record-list-li w14 tleft">今日:<span class="Black"><#if realTimeStatisticsVo??>${realTimeStatisticsVo.payedAveragePrice!''}</#if></span></li>
	        			<li class="record-list-li tleft">预计今日:<span class="Black"><#if realTimeStatisticsVo??>${realTimeStatisticsVo.expectPayedAveragePrice!''}</#if></span></li>
	        			<li class="record-list-li w24 center">比昨日此时：<span class="<#if realTimeStatisticsVo.expectCompareYesterdayOrder?? && (realTimeStatisticsVo.expectCompareYesterdayOrder < 0 )> Red <#else> Black </#if>"><#if realTimeStatisticsVo??>${realTimeStatisticsVo.compareYesterdayAveragePrice!''}</#if></span></li>
	        			<li class="record-list-li center">昨日全天:<span class="Black"><#if realTimeStatisticsVo??>${realTimeStatisticsVo.yesterdayAveragePrice!''}</#if></span></li>
	        			<li class="record-list-li w24 tright">预计比昨天:<span class="<#if realTimeStatisticsVo.expectCompareYesterdayAveragePrice?? && (realTimeStatisticsVo.expectCompareYesterdayAveragePrice < 0 )> Red <#else> Black </#if>"><#if realTimeStatisticsVo??>${realTimeStatisticsVo.expectCompareYesterdayAveragePrice!''}</#if></span></li>
	        		</ul> -->
	        	</div>
	        	<div class="today-number" id="today-number">
				</div>
        	</div>
        </div>

    </div>
    <!-- 底部 start-->
    <div class="yg-footer"></div>
    
    <!-- 底部 end-->
</body>

	<script type="text/javascript" src="${BasePath}/manage/report/hcharts/js/jquery-1.8.2.min.js"></script>	
    <!-- 滚动条插件 -->
    <script type="text/javascript" src="${BasePath}/manage/report/hcharts/js/nicescroll.js"></script>
		
    <script type="text/javascript" src="${BasePath}/manage/report/hcharts/js/highcharts.js"></script>
    <script type="text/javascript" src="${BasePath}/manage/report/hcharts/js/grid-yg.js"></script>
    <script type="text/javascript" src="${BasePath}/manage/report/hcharts/js/yg.common.js"></script>
    <script type="text/javascript" src="${BasePath}/manage/report/hcharts/js/yg.detail.js"></script>
    <script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=blue"></script>
    <script type="text/javascript" src="${BasePath}/yougou/js/ygui.js"></script>

<script>

var goodsAdd = {};
var basePath = "${BasePath}";

var curPage = 1;
function loadData(pageNo){
	curPage = pageNo;
	var data = {};
	data.page  = pageNo;
	// alert($("#queryForm").serialize());
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "html",
		data:data,
		url : "${BasePath}/report/loadCommoditySaleRankPage.sc?"+$("#queryForm").serialize(),
		success : function(data) {
			$("#today-number").html(data);
			YGUI.collect();
		}
	});
}
    
    $(function() {
   
        //今日实收件数数据
    	var categories1 = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23'],
        series1 = [
            {
                name: '今天',
                visible: true,//默认显示曲线
                data:  ${realTimeStatisticsVo.todayHourlyOrder}
            },
            {
                name: '昨天',
               // color:'#f8b491',
                visible: true,//默认不显示曲线
                data: ${realTimeStatisticsVo.yesterdayHourlyOrder}
            },
            {
                name: '上周同天',
              //  color:'#a691f8',
                visible: true,//默认不显示曲线
                data: ${realTimeStatisticsVo.lastWeekSameDayHourlyOrder}
            }
        ],

        //今日实收金额数据
        categories2= ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23'],
        series2 = [
            {
                name: '今天',
                visible:true,//默认不显示曲线
                data: ${realTimeStatisticsVo.todayHourlyAmount}
            },
            {
                name: '昨天',
               // color:'#f8b491',
                visible: true,//默认不显示曲线
                data: ${realTimeStatisticsVo.yesterdayHourlyAmount}
            },
            {
                name: '上周同天',
               // color:'#a691f8',
                visible: true,//默认不显示曲线
                data: ${realTimeStatisticsVo.lastWeekSameDayHourlyAmount}
            }
        ],

        //订单均价数据
        categories3=['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23'],
        series3 = [
            {
                name: '今天',
                visible: true,//默认显示曲线
                data: ${realTimeStatisticsVo.todayHourlyAveragePrice}
            },
            {
                name: '昨天',
               // color:'#f8b491',
                visible: true,//默认不显示曲线
                data: ${realTimeStatisticsVo.yesterdayHourlyAveragePrice}
            },
            {
                name: '上周同天',
               // color:'#a691f8',
                visible: true,//默认不显示曲线
                data: ${realTimeStatisticsVo.lastWeekSameDayHourlyAveragePrice}
            }
        ];

		

       
    G.Detail.bindChart('.record-number',categories1, '笔', series1);//今日实收件数曲线图
    G.Detail.bindChart('.record-amount',categories2, '元', series2);//今日实收金额曲线图
    G.Detail.bindChart('.average-order',categories3, '元', series3);//订单均价数据曲线图
    
    var categories;
    //品牌下拉框onchange事件
	$("#brandNo").change(function() {
		var brandName = $(this).find("option:selected").text();
		var brandId = $.trim($(this).find("option:selected").attr("brandId"));
	
		// 初始化分类信息（新）
		categories = [];
		initCatForBrand(brandId, 'category1', categories);

	});

	
	$("#category1").change(function() {
		reinitializeOption($(this).find(':selected').attr('id'), 2, '#category2', categories);
	});
	
	$("#category2").change(function() {
		reinitializeOption($(this).find(':selected').attr('id'), 3, '#category3', categories);
	});
	
	// 选中品牌下拉项
	var brandNo = "${(realTimeStatisticsVo.brandNo)!''}";	
	$("#brandNo").children().each(function(index,item){
		if($(this).val() == brandNo){
			$(this).attr("selected","selected");
			var index = $(this).index();
			// 模拟单击事件，达到页面选择效果，同时触发品牌change事件，初始化一级分类
			$('.yg-dropdown-list li',$('#brandNo').parents('.yg-dropdown')).get(index).click();
			return ;
		}
	});
	
	// 选中一级分类
	var rootCattegory = "${(realTimeStatisticsVo.rootCattegory)!''}";
	$("#category1").children().each(function(index,item){
		if($(this).val() == rootCattegory){
			$(this).attr("selected","selected");
			var index = $(this).index();
			// 模拟单击事件，达到页面选择效果，同时触发change事件，初始化二级分类
			$('.yg-dropdown-list li',$('#category1').parents('.yg-dropdown')).get(index).click();
			return ;
		}
	});	
	
	// 选中二级分类
	var secondCategory = "${(realTimeStatisticsVo.secondCategory)!''}";
	$("#category2").children().each(function(index,item){
		if($(this).val() == secondCategory){
			$(this).attr("selected","selected");
			var index = $(this).index();
			// 模拟单击事件，达到页面选择效果，同时触发change事件，初始化三级分类
			$('.yg-dropdown-list li',$('#category2').parents('.yg-dropdown')).get(index).click();
			return ;
		}
	});	
	
	// 选中二级分类
	var threeCategory = "${(realTimeStatisticsVo.threeCategory)!''}";
	$("#category3").children().each(function(index,item){
		if($(this).val() == threeCategory){
			$(this).attr("selected","selected");
			var index = $(this).index();
			// 模拟单击事件，达到页面选择效果
			$('.yg-dropdown-list li',$('#category3').parents('.yg-dropdown')).get(index).click();
			return ;
		}
	});	
	
	loadData(1);
	
});


function initCatForBrand(brandId, selId, categories) {
	if (brandId == null || brandId == '' || brandId.length == 0) {
		$("#" + selId).get(0).options.length = 1;
		$("#" + selId).data('ui').refresh();
		return false;
	}
	
	$.ajax( {
		type : "POST",
		url : basePath + "/commodity/queryBrandCat.sc",
		async : false,
		data : {
			"brandId" : brandId
		},
		dataType : "json",
		success : function(data) {
			if (data == null) {
				$("#" + selId).get(0).options.length = 1;
				return;
			}
			
			for ( var i = 0; i < data.length; i++) {
				categories[categories.length] = { label: data[i].catName, 
						//value: data[i].structName + ";" + data[i].id + ";" + data[i].catNo + ";" + data[i].catName, 
						value:data[i].structName,
						level: data[i].catLeave, 
						self: data[i].structName, 
						owner: data[i].parentId };
			}

			reinitializeOption('0', 1, '#' + selId, categories);
		}
	});
}

function reinitializeOption(id, level, selector, categories) {
	var optionText = $(categories).filter(function(){
		return (this.level == level) && (this.owner == id); 
	}).map(function(){
		return '<option id="' + this.self + '" value="' + this.value + '">' + this.label + '</option>';
	}).get().join('');

    $(selector).get(0).options.length = 1;
	$(selector).append(optionText);
	$(selector).data('ui').refresh();
}	

function queryRealTimeInfo(){
	var cat1 = $("#category1").val();
	var cat2 = $("#category2").val();
	var cat3 = $("#category3").val();
	
	/*if(cat1 == "" && cat2 == "" && cat3==""){
		ygdg.dialog.alert("请选择分类！");
		return false;
	}*/
	
	$("#queryForm").submit();
}
</script>



</html>