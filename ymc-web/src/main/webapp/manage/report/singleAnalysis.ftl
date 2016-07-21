<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>数据智慧_单品分析</title>
      <!-- 日期插件样式 -->
    <link type="text/css" rel="stylesheet" href="${BasePath}/manage/report/css/WdatePicker.css" />
    <!-- 自定义，单页样式和公共样式 -->
    <link type="text/css" rel="stylesheet" href="${BasePath}/manage/report/css/ygui.css?${style_v}" />
    <link type="text/css" rel="stylesheet" href="${BasePath}/manage/report/css/singleAnalysis.css?${style_v}" />
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
                        <li>
                            <a href="${BasePath }/report/reportRealTimeStatistics.sc">
                                    实时概况
                                </a>
                        </li>
                        <li class="select">
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
        <div class="yg-box bg-gray">
            <div class="yg-sidebar">
                <div class="yg-sidebar-menu">
                  <h1 class="sidebar-title"><i class="iconfont">&#xe604;</i>经营与分析</h1>
                  <ul class="sub-menu">
                      <li class="selected"><a href="${BasePath}/report/commodityAnalysisList.sc?dimensions=1">商品分析</a></li>
                      <li><a href="${BasePath}/report/commodityAnalysisList.sc?dimensions=2">品类分析</a></li>
                  </ul>
                </div>
            </div>
            <div class="yg-main">
                <div class="box-title pl12">
                    <span>经营分析> 商品分析> 单品分析</span>
                </div>
                <div class="goods-wrap border-top">
                    <div class="goods-box fl">
                        <img class="goods-img" src="${(singleAnalysisVo.picUrl)!'' }" alt=""/>
                    </div>
                    <div class="goods-box fl">
                        <h1 class="goods-tile">${(singleAnalysisVo.commodityName)!'' }</h1>
                        <ul class="goods-message fl mt15">
                            <li>
                                <span class="message-title Gray">商品编码：</span>
                                <input type="hidden" id="commodityNo" value="${(singleAnalysisVo.commodityNo)!'' }"/>
                                <span>${(singleAnalysisVo.commodityNo)!'' }</span>
                            </li>
                            <li>
                                <span class="message-title Gray">款色编码：</span>
                                <span>${(singleAnalysisVo.commodityStyleNo)!'' }</span>
                            </li>
                            <li>
                                <span class="message-title Gray">市 场 价 ：</span>
                                <span>${(singleAnalysisVo.publicPrice)!'' }</span>
                            </li>
                            <li>
                                <span class="message-title Gray">优 购 价：</span>
                                <span class="goods-price Red">￥${(singleAnalysisVo.yougouPrice)!'' }</span>（${(singleAnalysisVo.discount)!'' }折）
                            </li>
                            <li>
                                <span class="message-title Gray">商品评分：</span>
                                <span>${(singleAnalysisVo.commodityScore)!'' }分(${(singleAnalysisVo.commodityTimes)!'' }个人点评)</span>
                            </li>
                            <li>
                                <span class="message-title Gray">收藏人数：</span>
                                <span>${(singleAnalysisVo.favoriteCount)!'0' }</span>
                            </li> 
                        </ul>
                        <div class="operate fr mt15">
                            <div class="collect collect-two">
                               <#if (singleAnalysisVo.classifyCount)?? && (singleAnalysisVo.classifyCount) &gt; 0>
		                    		<span class="star"><i class="iconfont Gray Blue">&#xe605;</i></span>
		                    	<#else>	
		                    		<span class="star"><i class="iconfont Gray">&#xe605;</i></span>
		                    	</#if>		                        
		                        <span class="collect-btn collect-click first">收藏至<i class="up iconfont Gray">&#xe610;</i></span>
                                <input id="ommodityCode_favorite" type="hidden" value="${(singleAnalysisVo.commodityNo)!'' }"/>
                                <div class="collect-list-wrap">
                                    <ul class="collect-list">
                                    </ul>
                                </div>
                            </div>
                        </div>
                         <ul class="message-list mt20 fl">
                            <li>
                                <span class="message-item Gray">单品浏览量:</span>
                                <span>${(singleAnalysisVo.pageView)!'' }</span>
                            </li>
                            <li>
                                <span class="message-item Gray">单品访次:</span>
                                <span>${(singleAnalysisVo.visitCount)!'' }</span>
                                
                            </li>
                            <li>
                                <span class="message-item Gray">单品转化率：</span>
                                <span>${(singleAnalysisVo.changePercent)!'0' }%</span>
                            </li>
                            <li>
                                <span class="message-item Gray">支付件数：</span>
                                <span>${(singleAnalysisVo.commodityNum)!'' }</span>
                            </li>
                            <li>
                                <span class="message-item Gray">支付金额：</span>
                                <span>${(singleAnalysisVo.prodTotalAmt)!'' }元</span>
                            </li>
                            <li>
                                <span class="message-item Gray">发货均价：</span>
                                <span>${(singleAnalysisVo.avgSendAmt)!'' }元</span>
                            </li>
                            <li>
                                <span class="message-item Gray">上架时间：</span>
                                <span>${(singleAnalysisVo.commodityFirstSaleDate)!'' }</span>
                            </li>
                            <li>
                                <span class="message-item Gray">可售库存：</span>
                                <span>${(singleAnalysisVo.commodityTotalAvailable)!'' }件</span>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="goods-wrap">
                    <div class="goods-box fl">
                        <select name="firstIndexName" id="firstIndexName" onchange="loadSingleCommodityEveryDayIndex();" class="fl" data-ui-type="dropdown" autocomplete="off">
                        	 <#if indexList?? && indexList?size &gt; 0>
                            	<#list indexList as indexInfo>
                            		<option value="${(indexInfo.en_name)!'' }" label="${(indexInfo.label)!''}" valueSuffix="${(indexInfo.valueSuffix)!''}" <#if indexInfo_index == 0 > selected="selected" </#if> >${(indexInfo.label)!'' }</option>
                            	</#list>
                             </#if>
                        		
                        </select>
                        <span class="fl mt3">对比</span>
                        <select name="secondIndexName" id="secondIndexName" onchange="loadSingleCommodityEveryDayIndex();" class="fl" data-ui-type="dropdown" autocomplete="off">
                             <#if indexList?? && indexList?size &gt; 0>
                            	<#list indexList as indexInfo>
                            		<option value="${(indexInfo.en_name)!'' }" label="${(indexInfo.label)!''}" valueSuffix="${(indexInfo.valueSuffix)!''}" <#if indexInfo_index == 1 > selected="selected" </#if> >${(indexInfo.label)!'' }</option>
                            	</#list>
                             </#if>
                        </select>
                    </div>
                    <div class="goods-box fl">
                        <span class="box-fileter-item fr">
                            自定义时间范围 <input type="text" id="starttime-3"  autocomplete="off" value="${startDate!''}" class="daterange starttime">
                            <input type="text" id="endtime-3"  autocomplete="off" value="${endDate!''}" class="daterange ml10 endtime">
                        </span>
                    </div>
                </div>
                <div class="goods-wrap">
                    <div class="record-number mr10" id="singeCommodityIndex">
                    </div>
                </div>
                <div class="goods-wrap">
                    <div class="goods-box fl">
                        <h1 class="message-table-title mb13">商品事件 <span class="Gray"></span></h1>
                        <div class="scroll-y">
                        <table class="message-table">
                             <thead>
                                <tr>
                                    <th width="65">开始时间</th>
                                    <th>事件</th>
                                    <th width="65">结束时间</th>
                                </tr>
                            </thead>
                            <#if loginfoList?? && loginfoList?size &gt; 0>
                            	<#list loginfoList as loginfo>
                            		<tr>
		                                <td>${(loginfo.startDate)!'--'}</td>
		                                <td><span class="nowrap-text" title="${(loginfo.loginfo)!'--'}">${(loginfo.loginfo)!'--'}</span></td>
		                                <td>${(loginfo.endDate)!'--'}</td>
		                            </tr>
		                           
                            	</#list>
                            <#else>
                            	<tr>
                                	<td colspan="3" align="center">暂无数据！</td>
                          	    </tr>	
                            </#if>
                            
                         
                          
                        </table>
                        </div>
                    </div>

                     <div class="goods-box fl">
                        <h1 class="message-table-title mb13">可售库存 <span class="Gray"></span></h1>
                        <table class="message-table w488 mt2">
                            <tr>
                                <th>尺码</th>
                                <th>可售库存</th>
                                <th>最终销售商品件数</th>
                            </tr>
                             <#if sizeList?? && sizeList?size &gt; 0>
                            	<#list sizeList as sizeInfo>
                            		<tr>
		                                <td>${(sizeInfo.sizeName)!'--'}</td>
		                                <td>${(sizeInfo.totalStorageNum)!'--'}</td>
		                                <td>${(sizeInfo.finalSaleNum)!'--'}</td>
		                            </tr>
                            	</#list>
                            <#else>
                            	<tr>
                                	<td colspan="3" align="center">数据正在同步中，建议10:10 以后查看！</td>
                          	    </tr>	
                            </#if>
                         
                        </table>
                    </div>
                </div>
            </div>
        </div>
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
    <script type="text/javascript" src="${BasePath}/manage/report/hcharts/js/yg.single-sanalysis.js"></script>
    <script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=blue"></script>
    
    <script type="text/javascript">
    	var basePath = "${BasePath}";
    	
    	$(function(){
    		loadSingleCommodityEveryDayIndex();
    	});
    	
    	function loadSingleCommodityEveryDayIndex(){
    		$(".loading_img[name='surveyIndexLoading']").remove();
			$("#singeCommodityIndex").empty().before('<div class="loading_img" name="singeCommodityIndex"><img id="" src='+basePath+'"/manage/report/images/loading-spinner-blue.gif"/></div>');
    		var requestdata = {};
			var firstIndexName = $("#firstIndexName").val();
			var secondIndexName = $("#secondIndexName").val();
			requestdata.firstIndexName = firstIndexName;
			requestdata.secondIndexName = secondIndexName;
			// 默认查最近7天数据
			var startDate = $("#starttime-3").val();
			var endDate = $("#endtime-3").val();
			requestdata.startDate = startDate;
			requestdata.endDate = endDate;
			requestdata.commodityNo = $("#commodityNo").val();
			$.ajax({
				async : true, 
				cache : false,
				type : 'POST',
				dataType : "json",
				data:requestdata,
				url : basePath+"/report/loadSingleCommodityEveryDayIndex.sc",  
				success : function(data) {	
					//debugger;		
					//定义Y轴数组，数据填充数组
					var yAxis = [];
					var series = [];
					//定义首个指标Y轴参数，并赋予默认值
					var firstyAxit = {
						labels:{
							style:{color:"#808080"}
						},
						min: 0,
						title:{
							text:$("#firstIndexName option:selected").attr("label"),
							style:{
								color:"#808080"
							}
						},
						gridLineColor: '#f0f0f0'
					};
					//定义首个指标数据展示对象，并赋予默认值
					var firstSerie = {
						name:$("#firstIndexName option:selected").attr("label"),
						yAxis:0,
						tooltip:{
							valueSuffix:$("#firstIndexName option:selected").attr("valueSuffix")
						},
						data:data.firstIndex	
					};
	
					//将组装好的数据存入数组
					yAxis.push(firstyAxit);
					series.push(firstSerie);				
					
					//同上，定义第二个指标相关对象，并赋予默认值
					var secondAxit;
					var secondSerie;

					secondAxit = {
						labels:{
							style:{color:"#808080"}
						},
						title:{
							text:$("#secondIndexName option:selected").attr("label"),
							style:{
								color: "#808080"
							}
						},
		                opposite: true,
		                min: 0,
		                gridLineColor: '#f0f0f0'
					};
					
					secondSerie = {
						name:$("#secondIndexName option:selected").attr("label"),
						yAxis:1,
						tooltip:{
							valueSuffix:$("#secondIndexName option:selected").attr("valueSuffix"),
						},
						data:data.secondIndex	
					};
						
					//将组装好的数据存入数组
					yAxis.push(secondAxit);
					series.push(secondSerie);	

					$(".loading_img[name='singeCommodityIndex']").remove();
					G.Single.bindChartByDate("#singeCommodityIndex",yAxis,series);
					
				},
				error:function(e){
					ygdg.dialog.alert("加载指标折线图数据异常！");
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
	
</body>

</html>
