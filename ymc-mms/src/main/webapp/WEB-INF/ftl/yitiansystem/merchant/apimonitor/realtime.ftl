<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#setting number_format="#">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>招商--商家后台-实时监控-</title>
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>
<script type="text/javascript" src="${BasePath}/js/jquery-1.8.3.min.js"></script>
<script src="${BasePath}/js/highcharts/highcharts.js"></script>
<script src="${BasePath}/js/highcharts/exporting.js"></script>
<script type="text/javascript" src="${BasePath}/js/yitiansystem/ordermgmt/js/js.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/AutoComplete.js"></script>
<script type="text/javascript">
Date.prototype.Format = function(fmt)   
{ //author: meizz   
  var o = {   
    "M+" : this.getMonth()+1,                 //月份   
    "d+" : this.getDate(),                    //日   
    "h+" : this.getHours(),                   //小时   
    "m+" : this.getMinutes(),                 //分   
    "s+" : this.getSeconds(),                 //秒   
    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
    "S"  : this.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
}
Highcharts.theme = {
	colors: ["#55BF3B", "#DF5353", "#DDDF0D", "#ffffff", "#aaeeee", "#ff0066", "#eeaaee",
		"#55BF3B", "#DF5353", "#7798BF", "#aaeeee"],
	chart: {
		backgroundColor: {
			linearGradient: [0, 0, 250, 500],
			stops: [
				[0, 'rgb(48, 96, 48)'],[1, 'rgb(0, 0, 0)']]
		},
		borderColor: '#000000',
		borderWidth: 2,
		className: 'dark-container',
		plotBackgroundColor: 'rgba(255, 255, 255, .1)',
		plotBorderColor: '#CCCCCC',
		plotBorderWidth: 1
	},
	title: {
		style: {color: '#ffffff'}
	},
	subtitle: {
		style: {color: '#ffffff'}
	},
	xAxis: {
		gridLineColor: '#333333',
		gridLineWidth: 1,
		labels: {
			style: {color: '#A0A0A0'}
		},
		lineColor: '#A0A0A0',
		tickColor: '#A0A0A0',
		title: {
			style: {
				color: '#CCCCCC',
				fontWeight: 'bold',
				fontSize: '12px',
				fontFamily: 'Trebuchet MS, Verdana, sans-serif'
			}
		}
	},
	yAxis: {
		gridLineColor: '#333333',
		labels: {
			style: {color: '#A0A0A0'}
		},
		lineColor: '#A0A0A0',
		minorTickInterval: null,
		tickColor: '#A0A0A0',
		tickWidth: 1,
		title: {
			style: {
				color: '#CCC',
				fontWeight: 'bold',
				fontSize: '12px',
				fontFamily: 'Trebuchet MS, Verdana, sans-serif'
			}
		}
	},
	tooltip: {
		backgroundColor: 'rgba(0, 0, 0, 0.75)',
		style: {color: '#F0F0F0'}
	},
	toolbar: {itemStyle: {color: 'silver'}},
	plotOptions: {
		line: {dataLabels: {color: '#CCC'},marker: {lineColor: '#333'}},
		spline: {marker: {lineColor: '#333'}},
		scatter: {marker: {lineColor: '#333'}},
		candlestick: {lineColor: 'white'}
	},
	legend: {
		itemStyle: {color: '#A0A0A0'},
		itemHoverStyle: {color: '#FFF'},
		itemHiddenStyle: {color: '#444'}
	},
	credits: {
		style: {color: '#666'}
	},
	labels: {
		style: {color: '#CCC'}
	},
	navigation: {
		buttonOptions: {
			symbolStroke: '#DDDDDD',
			hoverSymbolStroke: '#FFFFFF',
			theme: {
				fill: {
					linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
					stops: [[0.4, '#606060'],[0.6, '#333333']]
				},stroke: '#000000'
			}
		}
	},

	// scroll charts
	rangeSelector: {
		buttonTheme: {
			fill: {
				linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
				stops: [
					[0.4, '#888'],
					[0.6, '#555']
				]
			},
			stroke: '#000000',
			style: {
				color: '#CCC',
				fontWeight: 'bold'
			},
			states: {
				hover: {
					fill: {
						linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
						stops: [
							[0.4, '#BBB'],
							[0.6, '#888']
						]
					},
					stroke: '#000000',
					style: {
						color: 'white'
					}
				},
				select: {
					fill: {
						linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
						stops: [
							[0.1, '#000'],
							[0.3, '#333']
						]
					},
					stroke: '#000000',
					style: {color: 'yellow'}
				}
			}
		},
		inputStyle: {backgroundColor: '#333',color: 'silver'},
		labelStyle: {color: 'silver'}
	},
	navigator: {
		handles: {
			backgroundColor: '#666',
			borderColor: '#AAA'
		},
		outlineColor: '#CCC',
		maskFill: 'rgba(16, 16, 16, 0.5)',
		series: {
			color: '#7798BF',
			lineColor: '#A6C7ED'
		}
	},
	scrollbar: {
		barBackgroundColor: {
				linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
				stops: [
					[0.4, '#888'],
					[0.6, '#555']
				]
			},
		barBorderColor: '#CCC',
		buttonArrowColor: '#CCC',
		buttonBackgroundColor: {
				linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
				stops: [
					[0.4, '#888'],
					[0.6, '#555']
				]
			},
		buttonBorderColor: '#CCC',
		rifleColor: '#FFF',
		trackBackgroundColor: {
			linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
			stops: [
				[0, '#000'],
				[1, '#333']
			]
		},
		trackBorderColor: '#666'
	},
	legendBackgroundColor: 'rgba(0, 0, 0, 0.5)',
	legendBackgroundColorSolid: 'rgb(35, 35, 70)',
	dataLabelsColor: '#444',
	textColor: '#C0C0C0',
	maskColor: 'rgba(255,255,255,0.3)'
};

        Highcharts.setOptions(Highcharts.theme);
	    var max = 30;
	    var period=2000;
	    var callCount=${callCount?default(0)};
        var failCount=${failCount?default(0)};
        var index=0;
        var flag=true;
        var time = (new Date()).getTime();
		$(function () {
		    $(document).ready(function() {
		        Highcharts.setOptions({
		            global: {
		                useUTC: false
		            }
		        });
		    
		        var chart;
		        $('#container').highcharts({
		            chart: {
		                type: 'spline',
		                animation: Highcharts.svg, // don't animate in old IE
		                events: {
		                    load: function() {
		    
                                var series = this.series;
                                var loadData = function() {
                                	    var x = (new Date()).getTime(); // current time
                                	    if(flag){
                                	        flag=false;
                                	    	$.ajax({
									            type: "GET",
									            url: "${BasePath}/apiJob/apiAllCount.sc",
									            async:false,
									            contentType: "application/json; charset=utf-8",
									            dataType: "json",
									            success: function (data) {
									                var isShift = index >= max;
		                                	     	index++;
		                                	     	//console.log(index+'---------'+isShift);
		                                	     	series[0].addPoint([x,data.callCount-callCount], true, isShift,false);
		                                	     	series[1].addPoint([x,data.failCount-failCount], true, isShift,false);
		                                	     	series[2].addPoint([x,parseFloat((data.exTime/(1000*data.callCount)).toFixed(4))], true, isShift,false);
		                                	     	callCount=data.callCount;
		                                	     	failCount=data.failCount;
									            },
									            complete: function () {
									                flag=true;
									            }
									        });
                                	    }

                                };
								loadData();
								setInterval(loadData, period);
		                    }
		                }
		            },
		            lang : { //这个是修改是上面 打印 下载的提升为汉字，如果在highcharts.js里面修改 简直累死
						exportButtonTitle : '导出',
						printButtonTitle : '打印',
						downloadJPEG : "下载JPEG 图片",
						downloadPDF : "下载PDF文档",
						downloadPNG : "下载PNG 图片",
						downloadSVG : "下载SVG 矢量图",
						printChart : "导出图表"
			        },
        			credits : {//右下角的文本
						enabled : false
					},
		            title: {
		                text: '访问量实时监控'
		            },
		            xAxis: {
		                type: 'datetime',
		                tickPixelInterval: 150
		            },
                    yAxis: [
                            {
                                title: {
                                    text: '请求频率(次/'+period/1000+'秒)',
                                    style: {
                                        color: '#55BF3B'
                                    }
                                }
                            },
                            {
                                title: {
                                    text: '平均响应时间',
                                    style: {
                                        color: '#DDDF0D'
                                    }
                                },opposite:true
                            }
                    ],
                    plotOptions: {
                        spline: {
                            marker:{
                                enabled: false,
                                states: {
                                    hover: {
                                        enabled: true,
                                        symbol: 'circle',
                                        radius: 5,
                                        lineWidth: 1
                                    }
                                }
                            }
                        }
                    },
		            tooltip: {
		                xDateFormat: '%Y-%m-%d',
						shared: true
		            },
                    series: [
                            {
                                 name: '总请求频率',
                                 data: (function() {
					                    var data = [],i;
					    
					                    for (i = -max+1; i <= 0; i++) {
					                        data.push({
					                            x: time + i * period,
					                            y: null
					                        });
					                    }
					                    return data;
					                })(),
					                tooltip: {
                						valueSuffix: ' 次'
            						}
                             },
                             {
                                 name: '错误请求频率',
                                 data: (function() {
					                    var data = [],i;
					    
					                    for (i = -max+1; i <= 0; i++) {
					                        data.push({
					                            x: time + i * period,
					                            y: null
					                        });
					                    }
					                    return data;
					                })(),
					                tooltip: {
                						valueSuffix: ' 次'
            						}
                             },
                             {
                                 name: '平均响应时间',
                                 yAxis:1,
                                 data: (function() {
					                    var data = [],i;
					    
					                    for (i = -max+1; i <= 0; i++) {
					                        data.push({
					                            x: time + i * period,
					                            y: null
					                        });
					                    }
					                    return data;
					                })(),
					                tooltip: {
                						valueSuffix: ' 秒'
            						}
                             }
                         ]
		        });
		    });
		    
		});
</script>
</head>
<script type="text/javascript">
</script>
<body>
<div class="container">
	<div class="list_content">
        <div class="modify" id="container" style="min-width: 310px; height: 350px; margin: 0 auto"></div>
        <div style="height:800px;">
			<div class="top clearfix">
				<ul class="tab">
				      　<li id="head1" class="curr"><span><a href="javascript:setTab(1,2)">API实时查询</a></span></li>
	  　                                        <li id="head2"><span><a href="javascript:setTab(2,2)">AppKey实时查询</a></span></li>
				</ul>
			</div>
	 		<div class="modify" id="page1">
	 		    <div class="wms-top">
			        <span>
				  		<label>接口：</label>
				  		<input onkeyup="AutoSuggest(queryApiID(this),this,event);" style="width: 400px" autocomplete="off" id="queryinput_api"><input type="button" value="搜索" onclick="queryApiCount();" class="yt-seach-btn" />&nbsp;&nbsp;&nbsp;<label>搜索时间：</label><span id="queryTime1">-</span>
				  	</span>
			  	</div>
			  	<div id="queryinput_api_div" mytype="sq" style="position: absolute; display: none; border: 1px solid #817F82; background-color: #FFFFFF;"></div>
			  	<table cellpadding="0" cellspacing="0" class="list_table2">
					<thead>
						<tr>
						    <th width="50px;"><strong>序号</strong></th>
	                        <th width="400px;"><strong>AppKey持有者</strong></th>
	                        <th><strong>本日累积流量(次)</strong></th>
	                        <th><strong>错误流量(次)</strong></th>
	                        <th><strong>当前频率</strong></th>
	                        <th><strong>IP连接数</strong></th>
	                    </tr>
					</thead>
					<tbody id="apiBody">
			        </tbody>     
	   			</table>
			</div>
			<div class="modify" id="page2" style="display:none">
				<div class="wms-top">
			        <span>
				  		<label>Appkey持有者：</label>
				  		<input mytype="sq" onkeyup="AutoSuggest(queryAppKey(this),this,event);" style="width: 400px" autocomplete="off" id="queryinput_appkey"><input type="button" value="搜索" onclick="queryAppkeyCount();" class="yt-seach-btn" />&nbsp;&nbsp;&nbsp;<label>搜索时间：</label><span id="queryTime2">-</span>
				  		<div id="queryinput_appkey_div" style="position: absolute; paddint-top:1px;display: none; border: 1px solid #817F82; background-color: #FFFFFF;"></div>
				  	</span>
			  	</div>
			  	<table cellpadding="0" cellspacing="0" class="list_table2">
					<thead>
						<tr>
						    <th width="50px;"><strong>序号</strong></th>
	                        <th width="400px;"><strong>接口</strong></th>
	                        <th><strong>本日累积流量(次)</strong></th>
	                        <th><strong>错误流量(次)</strong></th>
	                        <th><strong>当前频率</strong></th>
	                    </tr>
					</thead>
					<tbody id="appkeyBody">
			        </tbody>     
	   			</table>
			</div>
		</div>
        <div class="blank20"></div>
     </div>
</div>
</body>
</html>
<script type="text/javascript">
//切换tab页
function setTab(currentPageIndex,pageCounts){
     $("#page2").removeAttr("style");
     for(var i=1;i<=pageCounts;i++){
	　　　　var page=document.getElementById('page'+i);
	　　　　if(i==currentPageIndex){
	        $("#head"+i).addClass("curr");
	　　　　　　page.style.visibility = "visible";
	　　　　　　page.style.position="static";
	　　　　}else{
	        $("#head"+i).removeClass("curr");
	　　　　　　page.style.visibility="hidden";
	　　　　　　page.style.position="absolute";
	　　　　}
     }
}
function queryApiCount() {  
    $("#queryTime1").html((new Date()).Format("yyyy-MM-dd hh:mm:ss"));
    var key="";
    if(!(typeof($("#queryinput_api").attr("mykey")) == "undefined")){
       key=$("#queryinput_api").attr("mykey");
    }else{
       key=$("#queryinput_api").val();
    }
	$.getJSON(
	    "${BasePath}/apiJob/apiCount.sc",{"apiId":key},
	    function(data) {
	    	var sumCallCount=0;
	    	var sumFailCount=0;
	    	var tt = "";
	        $.each(data, function(index, item) {
	            tt += "<tr>" + "<td>" + (index+1) +"</td>" + "<td>" + item.appkeyUser +"</td>" + "<td>" + item.callCount +"</td>" + "<td>" + item.failCount +"</td>" + "<td>" + "-" +"</td>" + "<td>" + "-" +"</td>" + "</tr>";
	            sumCallCount+=item.callCount;
	            sumFailCount+=item.failCount;
	        });
	        tt += "<tr>" + "<td  colspan=2><strong>合计</strong></td>" + "<td>" + sumCallCount +"</td>" + "<td>" + sumFailCount +"</td>" + "<td>" + "-" +"</td>" + "<td>" + "-" +"</td>" + "</tr>";
	        $("#apiBody").html(tt);
	});
}
function queryAppkeyCount() {
    $("#queryTime2").html((new Date()).Format("yyyy-MM-dd hh:mm:ss"));
    var key="";
    if(!(typeof($("#queryinput_appkey").attr("mykey")) == "undefined")){
       key=$("#queryinput_appkey").attr("mykey");
    }else{
       key=$("#queryinput_appkey").val();
    }
	$.getJSON(
	    "${BasePath}/apiJob/appkeyCount.sc",{"appkey":key},
	    function(data) {
	        var sumCallCount=0;
	    	var sumFailCount=0;
	    	var tt = "";
	        $.each(data, function(index, item) {
	            tt += "<tr>" + "<td>" + (index+1) +"</td>" + "<td>" + item.apiName +"</td>" + "<td>" + item.callCount +"</td>" + "<td>" + item.failCount +"</td>" + "<td>" + "-" +"</td>"  + "</tr>";
	            sumCallCount+=item.callCount;
	            sumFailCount+=item.failCount;
	        });
	        tt += "<tr>" + "<td  colspan=2><strong>合计</strong></td>" + "<td>" + sumCallCount +"</td>" + "<td>" + sumFailCount +"</td>" + "<td>" + "-" +"</td>" + "</tr>";
	        $("#appkeyBody").html(tt);
	});
}
function queryApiID(input) {
    var backdata="";
    $.ajax({
        type: "POST",
        url: "${BasePath}/api/monitor/manage/queryApi.sc",
        data: {"apiText":$(input).val()},
        async:false,
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType: "json",
        success: function (data) {
			backdata=data;
        }
    });
	return backdata;
}
function queryAppKey(input) {
    var backdata="";
    $.ajax({
        type: "POST",
        url: "${BasePath}/api/monitor/manage/queryAppKey.sc",
        data: {"appKeyText":$(input).val()},
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        async:false,
        dataType: "json",
        success: function (data) {
			backdata=data;
        }
    });
	return backdata;
}
</script>
