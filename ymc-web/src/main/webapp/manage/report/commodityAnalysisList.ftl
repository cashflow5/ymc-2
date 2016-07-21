<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>数据智慧_经营分析</title>
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
                  <h1 class="sidebar-title"><i class="iconfont">&#xe604;</i>经营分析</h1>
                  <ul class="sub-menu">
                      <li class="selected" id="commodityAnalysisMenu"><a href="javascript:void(0)" onclick="changeDimensions('1');">商品分析</a></li>
                      <li id="catagoryAnalysisMenu"><a href="javascript:void(0)" onclick="changeDimensions('2');" >品类分析</a></li>
                  </ul>
                </div>
            </div>
            <div class="yg-main">
            	<form class="commodityStyle">
                <div class="box-title pl12">
                    <span>经营分析> 商品分析</span>
                    <div class="box-filter">
	                    <span class="box-fileter-item box-filter-buttons commoditySpan"><a href="javascript:setDate(1);" class="btn-default active">昨天</a>
						<a href="javascript:setDate(2);" class="btn-default">最近7天</a>
					    <a href="javascript:setDate(3);" class="btn-default">最近30天</a>
						</span>
	                    <span class="box-fileter-item ml15">自定义时间范围 
	                    <input type="text" autocomplete="off" id="starttime-2" name="queryStartDate" value="${(Parameters['queryStartDate'])!'' }" class="daterange starttime">
	                    <input type="text" autocomplete="off" id="endtime-2" name="queryEndDate" value="${(Parameters['queryEndDate'])!'' }" class="daterange ml10 endtime"></span>
	                    <span class="box-fileter-item relative">
	                        <a class="ml25 btn-quota first" href="javascript:getIndex($('#dimensions').val());">指标 <span class="iconfont Gray"><i>&#xe60d;</i></span></a>
	                        <a class="ml25 mr25" href="javascript:exportExcel();" data-toggle="tooltip" data-placement="top" id="exportData" title="根据时间跨度导出">
	                        <span class="iconfont Gray"><i>&#xe60a;</i></span> 导出<span class="hide" id="progressBar" ><img src='${BasePath}/yougou/images/loading.gif'/><span class="iconfont Gray">0</span>%</span></a>
	                        <div class="quota-box hide" id="analysisQuota">
	                        </div>
	                    </span>
	                </div>
                </div>
                <div class="box-search zIndex10">
                    <!-- start search -->
	                <div class="yg-search fr">
	                    <i class="yg-search-ico"></i>
	                    <input type="text" autocomplete="off" name="keywords" autocomplete="off" class="yg-search-input" value="请输入商品名称或商品编码" />
	                </div>
	                <!-- end search -->	                               
	       
                    <span class="detail-select fr" style="margin-top:10px;">
	                	<label class="detail_item_label"><span class="detail_item_star"></span>&nbsp;品牌： </label>						
						<select id="brandNo" name="brandNo" autocomplete="off" data-ui-type="dropdown">
							<option value="">请选择</option>
							<#if lstBrand??>
								<#list lstBrand as item>
									<option value="${(item.brandNo)!""}"  brandId="${(item.id)!''}">${(item.brandName)!""}</option>
								</#list>
							</#if>
							<#--<#if realTimeStatisticsVo?? && item?? > <#if realTimeStatisticsVo.brandNo == item.brandNo> selected=selected </#if> </#if> -->
						</select>
						<label class="detail_item_label"><span class="detail_item_star"></span>&nbsp;分类： </label>
	                    <select autocomplete="off" name="rootCategory" id="category1" data-ui-type="dropdown" linkage="true">
	                        <option value="" selected="selected">一级分类</option>	                    
	                    </select>
	                    <select autocomplete="off" name="secondCategory" id="category2" data-ui-type="dropdown" linkage="true">
	                        <option value="" selected="selected">二级分类</option>
	                    </select>
	                    <select autocomplete="off" name="threeCategory" id="category3" data-ui-type="dropdown" linkage="true">
	                        <option value="" selected="selected">三级分类</option>
	                    </select>
	                    <a class="btn-default mr7 no-btn" href="javascript:void(0)" onclick="searchForm();">查询</a>	                
	                </span>
                </div>
                <input autocomplete="off" type="hidden" name="dateCondition" id="dateCondition" value="1"/>
	            <input autocomplete="off" type="hidden" name="sortDirection" id="sortDirection" value="0"/>
	            <input autocomplete="off" type="hidden" name="sortBy" id="sortBy" value=""/> 
	            <input autocomplete="off" type="hidden" name="dimensions" id="dimensions" value="${dimensions!'1'}">
	            </form>
                <!-- 表格开始 -->
                <!-- 商品分析、品类分析表格开始 -->
	       		<div id="prodAnalyseTable"> </div>     
              
            </div>
        </div>
    </div>
    <!-- 底部 start-->
    <div class="yg-footer"></div>
    
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
    	if($("#dimensions").val()==""){
    		$("#dimensions").val(1);
    	}
    	if($("#dimensions").val()=="1"){
    		if(!$("#commodityAnalysisMenu").hasClass("selected")){
				$("#commodityAnalysisMenu").addClass("selected");
			}
			$("#catagoryAnalysisMenu").removeClass("selected");
    	}else {
    		$("#commodityAnalysisMenu").removeClass("selected");
			if(!$("#catagoryAnalysisMenu").hasClass("selected")){
				$("#catagoryAnalysisMenu").addClass("selected");
			}
    	}
    	$('#prodAnalyseTable').delegate('span.yg-sort','click', function(){
        	$("#sortBy").val($(this).find("input[data-ui-type='sort']").val());
            if($(this).children("span.icon").hasClass("checked")){
            	$("#sortDirection").val(1);
            }else{
            	$("#sortDirection").val(0);
            }
            searchForm();
        });
        
	    // 日期函数调用
        G.Index.dataTime('.starttime');
        G.Index.dataTime('#endtime-2','',{
        	onpicked:function(){
        		if($('#starttime-2').val()!=''){
        			$(".commoditySpan a").removeClass("active");
            		$("#dateCondition").val("1");
	        		//searchForm();
	        		$(this).blur();
            	}
            	
            }
        });
	
	    //点击搜索框按钮
	    G.Index.search();

	    //指标click函数执行
    	G.Index.quotaClick();
	
	});
	
	var curPage = 1;
	var pageSize = 10;
	// 加载分页列表
	function loadData(pageNo,pageSize,loadUrl){
		//alert(loadUrl);
		$('.loadImg').remove();
		$("#prodAnalyseTable").empty().after('<div class="loading_img loadImg"><img src="${BasePath}/manage/report/images/loading-spinner-blue.gif"/></div>');
		$.ajax({
			cache : false,
			type : 'POST',
			dataType : "html",
			data:$("form.commodityStyle").serialize()+"&page="+pageNo+"&pageSize="+pageSize+"&class="+"w960",
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
	
	//点击左侧商品分析，品类分析菜单加载对应列表数据
	function changeDimensions(demension){
		$("#dimensions").val(demension);
		//品类分析
		if(demension == "2"){
			$("#commodityAnalysisMenu").removeClass("selected");
			if(!$("#catagoryAnalysisMenu").hasClass("selected")){
				$("#catagoryAnalysisMenu").addClass("selected");
			}
		//商品分析
		}else{
			if(!$("#commodityAnalysisMenu").hasClass("selected")){
				$("#commodityAnalysisMenu").addClass("selected");
			}
			$("#catagoryAnalysisMenu").removeClass("selected");
		}
		//setDate(1);
		loadTable(demension);
	}
	</script>
</body>
    <script type="text/javascript" src="${BasePath}/yougou/js/manage/report/report.js?${style_v}"></script>
    <script type="text/javascript" src="${BasePath}/yougou/js/manage/report/category-common.js?${style_v}"></script>
</html>
