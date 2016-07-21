<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#setting number_format="#">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${BasePath}/css/ordermgmt/css/css.css" />
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-base.css"/>
<link rel="stylesheet" type="text/css" href="${BasePath}/yougou/styles/sys-global.css"/>

<script type="text/javascript" src="${BasePath}/yougou/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/common.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/validator/formValidator.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/calendar/lhgcalendar.min.js"></script> 
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js?s=chrome"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/ygdialog/lhgdialog.min.js"></script>
<script type="text/javascript" src="${BasePath}/js/highcharts/highcharts.js"></script>
<script type="text/javascript" src="${BasePath}/js/highcharts/exporting.js"></script>
<script type="text/javascript" src="${BasePath}/yougou/js/AutoComplete.js"></script>
<!-- 日期 -->
<script src="${BasePath}/js/common/form/datepicker/WdatePicker.js" type="text/javascript"></script>
<title>招商--商家后台--appKey明细报表</title>
</head>
<body>
<div class="container">
	<!--工具栏start-->
	<div class="toolbar">
		<div class="t-content"> <!--操作按钮start--> 
			<div class="btn" onclick="to_export_api_report();">
				<span class="btn_l"></span>
	        	<b class="ico_btn add"></b>
	        	<span class="btn_txt">导出数据</span>
	        	<span class="btn_r"></span>
        	</div> 
		</div>
	</div>
	<div class="list_content">
		<div class="top clearfix">
			<ul class="tab">
				<li <#if flag??&&flag==1>class="curr"</#if>>
				  <span><a href="#" onclick="onclick_tab(1);" class="btn-onselc">构成分析</a></span>
				</li>
				<li <#if flag??&&flag==2>class="curr"</#if>>
				  <span><a href="#" onclick="onclick_tab(2);" class="btn-onselc">趋势分析</a></span>
				</li>
			</ul>
		</div>
		
		<div class="modify">
		<form action="${BasePath}/api/report/to_analyze_detail_appkey_report.sc" name="queryForm" id="queryForm" method="post"> 
  			  		<input type="hidden" id="appKeyHolder" name="appKeyHolder" value="${appKeyHolder}" />
					<input type="hidden" id="appKey" name="appKey" value="${appKey}" />
					<input type="hidden" id="startTime" name="startTime" value="${startTime}" />
					<input type="hidden" id="endTime" name="endTime" value="${endTime}" />
					<input type="hidden" id="flag" name="flag" value="${flag?default(1)}" />
					<input type="hidden" id="svg" name="svg" value=""/>
				<div class="wms-top">
  			  		<label><b>AppKey持有者：</b></label>${appKeyHolder}
  			  		<label><b>查询日期：</b></label>${startTime}&nbsp;至&nbsp;${endTime}
           			<br />
           			<span>
           				<label><b>API接口：</b></label>
			  		<input onkeyup="AutoSuggest(queryApiID(this),this,event);" style="width: 200px" autocomplete="off" id="queryinput_api"></span>
			  		<div id="queryinput_api_div" mytype="sq" style="position: absolute; display: none; border: 1px solid #817F82; background-color: #FFFFFF;"></div>
			  	</span>
                    &nbsp;&nbsp;&nbsp;<input type="submit" value="搜索" class="yt-seach-btn" onclick="queryall();"/>
				</div>
       
		</form>
		
		<#if flag??&&flag==1>
		<div>
			<table cellpadding="0" cellspacing="0" class="list_table2">
				<thead>
					<tr>
                        <th style="width:180px;font-weight:bold;">API接口</th>
                        <th style="font-weight:bold;">总次数</th>
                        <th style="font-weight:bold;">成功次数</th>
                        <th style="font-weight:bold;">失败次数</th>
                        <th style="font-weight:bold;">成功率</th>
                        <th style="font-weight:bold;">总次数排名</th>
                        <th style="font-weight:bold;">平均频率（次/时）</th>
                        <th style="font-weight:bold;">最高频率（次/时）</th>
                        <th style="font-weight:bold;">最高频率排名</th>
                        <th style="font-weight:bold;">平均接口执行时间(ms)</th>
                    </tr>
				</thead>
				<tbody>
				<#if list??&& list?size != 0>
					<#list list as item>
					<tr>
						<td>${item.apiName!'-'}</td>
		                <td id="callCount_${flag}_${item_index}">${item.callCount!'0'}</td>
		                <td id="sucessCallCount_${flag}_${item_index}">${item.sucessCallCount!'0'}</td>
						<td id="failCallCount_${flag}_${item_index}">${item.failCallCount!'0'}</td>
						<td>
							${item.successRate?string("0.##")}%
						</td>
						<td>${item.rankingCall!0}</td>
						<td id="avgFrequency_${flag}_${item_index}">${item.avgFrequency!'0'}</td>
						<td id="maxFrequency_${flag}_${item_index}">${item.maxFrequency!'0'}</td>
						<td>${item.rankingFrequency!0}</td>
						<td id="avgExTime_${flag}_${item_index}">${item.avgExTime!'0'}</td>
	                </tr>
                    </#list>
                    <#--合计-->
                    <tr>
                    	<td style="font-weight:bold;">合计</td>
                        <td id="callCount_total_${flag}" style="font-weight:bold;"></td>
                        <td id="sucessCallCount_total_${flag}" style="font-weight:bold;"></td>
                       	<td id="failCallCount_total_${flag}" style="font-weight:bold;"></td>
                        <td style="font-weight:bold;"></td>
                        <td style="font-weight:bold;">-</td>
                        <td id="avgFrequency_total_${flag}" style="font-weight:bold;"></td>
                        <td id="maxFrequency_total_${flag}" style="font-weight:bold;"></td>
                        <td style="font-weight:bold;">-</td>
                        <td id="avgExTime_total_${flag}" style="font-weight:bold;">-</td>
                    </tr>
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
		<#elseif flag??&&flag==2>
			<!-- 图表 -->
			<div>
				<p>
					<div id="container" style="min-width: 100%; height: 250px; margin: 0 auto; float: left;"></div>
				</p>
			</div>
			<div>
				<table cellpadding="0" cellspacing="0" class="list_table2">
				<thead>
					<tr>
                        <th style="width:180px;font-weight:bold;">时间段</th>
                        <th style="font-weight:bold;">总次数</th>
                        <th style="font-weight:bold;">成功次数</th>
                        <th style="font-weight:bold;">失败次数</th>
                        <th style="font-weight:bold;">成功率</th>
                        <th style="font-weight:bold;">平均频率（次/时）</th>
                        <th style="font-weight:bold;">最高频率（次/时）</th>
                        <th style="font-weight:bold;">平均接口执行时间(ms)</th>
                    </tr>
				</thead>
				<tbody>
				<#if list??&& list?size != 0>
					<#list list as item>
					<tr>
						<td>
							${item.timeQuantum!'-'}
						</td>
		                <td id="callCount_${flag}_${item_index}">${item.callCount!'0'}</td>
		                <td id="sucessCallCount_${flag}_${item_index}">${item.sucessCallCount!'0'}</td>
						<td id="failCallCount_${flag}_${item_index}">${item.failCallCount!'0'}</td>
						<td>
							${item.successRate?string("0.##")}%
						</td>
						<td id="avgFrequency_${flag}_${item_index}">${item.avgFrequency!'0'}</td>
						<td id="maxFrequency_${flag}_${item_index}">${item.maxFrequency!'0'}</td>
						<td id="avgExTime_${flag}_${item_index}">${item.avgExTime!'-'}</td>
	                </tr>
                    </#list>
                    <#--合计-->
                    <tr>
                    	<td style="font-weight:bold;">合计</td>
                        <td id="callCount_total_${flag}" style="font-weight:bold;"></td>
                        <td id="sucessCallCount_total_${flag}" style="font-weight:bold;"></td>
                       	<td id="failCallCount_total_${flag}" style="font-weight:bold;"></td>
                        <td style="font-weight:bold;">-</td>
                        <td id="avgFrequency_total_${flag}" style="font-weight:bold;"></td>
                        <td id="maxFrequency_total_${flag}" style="font-weight:bold;"></td>
                        <td id="avgExTime_total_${flag}" style="font-weight:bold;"></td>
                    </tr>
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
		</#if>
		<div>
		<div class="bottom clearfix">
			<#if pageFinder ??><#import "../../../common.ftl" as page>
			  		<@page.queryForm formId="queryForm"/></#if>
		</div>
        <div class="blank20"></div>
       	</div>
</div>
</body>
</html>
<script type="text/javascript">
function to_export_api_report() {
	var chart = $('#container').highcharts();
	var svg = chart.getSVG();
	if (svg != 'undefined' && svg.length > 100) {
		$('#svg').val(svg);
	}
	var url = "${BasePath}/api/report/to_analyze_detail_appkey_report.sc";
	$('#queryForm').attr('action', url + "?isExport=" + '1');
	$('#queryForm').submit();
}

//根据tab来设置action
function queryall(){
	var key="";
    if(!(typeof($("#queryinput_api").attr("mykey")) == "undefined")){
       key=$("#queryinput_api").attr("mykey");
    }else{
       key=$("#queryinput_api").val();
    }
	var url = "${BasePath}/api/report/to_analyze_detail_appkey_report.sc";
	$('#queryForm').attr('action', url + "?apiId=" + key);
	$('#queryForm').submit();
}

//切换tab页
function onclick_tab(flag) {
	var url = "${BasePath}/api/report/to_analyze_detail_appkey_report.sc";
	$('#flag').val(flag);
	$('#queryForm').attr('action', url);
	$('#queryForm').submit();
}

$(function($) {
  var flag = $('#flag').val();
  if (flag == 1) {
  	<#if list??&& list?size != 0>
  		//总计
  		$('#callCount_total_' + flag).html(sum('callCount', ${list?size}, flag));
  		$('#sucessCallCount_total_' + flag).html(sum('sucessCallCount', ${list?size}, flag));
  		$('#failCallCount_total_' + flag).html(sum('failCallCount', ${list?size}, flag));
  		
  		$('#avgFrequency_total_' + flag).html(avg('avgFrequency', ${list?size}, flag));
  		$('#maxFrequency_total_' + flag).html(max('maxFrequency', ${list?size}, flag));
  	</#if>	
  } else if (flag == 2) {
  	<#if list??&& list?size != 0>
  		//总计
  		$('#callCount_total_' + flag).html(sum('callCount', ${list?size}, flag));
  		$('#sucessCallCount_total_' + flag).html(sum('sucessCallCount', ${list?size}, flag));
  		$('#failCallCount_total_' + flag).html(sum('failCallCount', ${list?size}, flag));
  		
  		$('#avgFrequency_total_' + flag).html(avg('avgFrequency', ${list?size}, flag));
  		$('#maxFrequency_total_' + flag).html(max('maxFrequency', ${list?size}, flag));
  		$('#avgExTime_total_' + flag).html(avg('avgExTime', ${list?size}, flag));
  		
  		//调用次数
  		var call_counts = [<#list list as item>${item.callCount}<#if item_has_next>,</#if></#list>];
  		//成功率
  		var success_rates = [<#list list as item>${item.successRate?string("0.##")}<#if item_has_next>,</#if></#list>];
		var _categories = [<#list list as item>'${item.timeQuantum!'-'}'<#if item_has_next>,</#if></#list>];
		//记录数超过10时将分类倾斜45度
		var length = ${list?size};
		var _rotation = 0;
		if (length >= 10) _rotation = -45;
		
		$('#container').highcharts({
			chart: {
	            zoomType: 'xy'
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
			exporting : {// 是否允许导出 就是右上角的按钮显示不显示
				enabled : true
			},
			title : {
				text : null
			},
	        xAxis: {
	        	labels: {
	        		rotation: _rotation,
	        		formatter: function() {
	        			if (this.value.length < 12) 
							return this.value;
						else if (this.value.length == 19) 
							return this.value.substring(11, 16);
					}
	        	},
	        	categories: _categories
	        },
	        yAxis: [{  // Primary yAxis
            	labels: {
	                format: '{value}次',
	                style: {
	                    color: '#89A54E'
	                }
	            },
	            title: {
	                text: '总调用次数',
	                style: {
	                    color: '#89A54E'
	                }
	            },
	            min:0
	        },{   // Secondary yAxis
	            title: {
	                text: '成功率',
	                style: {
	                    color: '#4572A7'
	                }
	            },
	            labels: {
	                format: '{value} %',
	                style: {
	                    color: '#4572A7'
	                }
	            },
	            min:0,
	            opposite: true
	        }],
	        legend: {
	            layout: 'horizontal',//水平线
	            align: 'center',
	            verticalAlign: 'bottom',//底部
	            borderWidth: 1
	        },
	        tooltip : { //这个是鼠标放在上面提升的文字
				shared: true
			},
	        series: [{ 
						name : '总调用次数',
						color: '#89A54E',
						type: 'spline',
						data : call_counts,
						tooltip: {
                			valueSuffix: ' 次'
            			}
					}, {
						name : '成功率',
						color: '#4572A7',
						type: 'spline',
						data : success_rates,
						yAxis : 1,
						tooltip: {
                			valueSuffix: ' %'
            			}
					}]
	    });
  	</#if>
  }
});

//求总数
function sum(id, index, flag) {
	var count = 0;
	for(var i = 0; i < index; i++) {
		var obj = $('#' + id + '_' + flag + '_' + i).html();
		//alert(parseInt(obj.replace(/,/g, '')));
		count += parseInt(obj.replace(/,/g, ''));
	}
	
	return count;
}

//求平均值
function avg(id, index, flag) {
	var count = 0;
	for(var i = 0; i < index; i++) {
		var obj = $('#' + id + '_' + flag + '_' + i).html();
		count += parseInt(obj.replace(/,/g, ''));
	}
	
	return parseInt(count/index);
}

//求最大值
function max(id, index, flag) {
	var max = 0;
	for(var i = 0; i < index; i++) {
		var obj = $('#' + id + '_' + flag + '_' + i).html();
		if (parseInt(obj.replace(/,/g, '')) > max) {
			max = parseInt(obj.replace(/,/g, ''));
		}
	}
	
	return max;
}

function queryApiID(input) {
    var backdata="";
    $.ajax({
        type: "GET",
        url: "${BasePath}/api/monitor/manage/queryApi.sc?apiText="+$(input).val(),
        async:false,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
			backdata=data;
        }
    });
	return backdata;
}
</script>