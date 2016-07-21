<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-报表-售后总览</title>
<script type="text/javascript" src="${BasePath}/highcharts/js/highcharts.js"></script>
<script type="text/javascript" src="${BasePath}/highcharts/js/modules/exporting.js"></script>
</head>

<body>
	
	
	<div class="main_container">
		
		<div class="normal_box">
		<p class="title site">当前位置：商家中心 &gt; 报表 &gt; 售后总览</p>
		<div class="tab_panel">
			<ul class="tab">
				<li class="curr">
					<span>售后总览</span>
				</li>
			</ul>
			<div class="tab_content">
				<form id="queryForm" name="queryForm" method="post" action="${BasePath}/bi/queryAftersaleStatistics.sc">
					<div class="search_box">
						<p>
							<span>
								<label style="width: 150px; text-align: left; margin-left: 10px;">请选择查看的报表类型：</label>
								<select name="analyzePatttern" id="analyzePatttern">
									<#list AnalyzePattterns as item>
										<option value="${item}" weight="${item.weight}" <#if (afterSaleStatisticsVo.analyzePatttern)?? && afterSaleStatisticsVo.analyzePatttern == item>selected="selected"</#if>>${item.description}</option>
									</#list>
								</select>
								<input type="hidden" name="thisYear" id="thisYear" />
							</span>
							<span>
								&nbsp;<input type="text" id="start" name="startText" class="inputtxt" readonly="readonly" style="width: 100px;" />
								<input type="hidden" name="start" />
							</span>
							<span style="display: none;">
								&nbsp;至&nbsp;<input type="text" id="end" name="endText" class="inputtxt" readonly="readonly" style="width: 100px;" />
								<input type="hidden" name="end" />
							</span>
							<span>
								<label style="width: auto;">
									<a class="button" id="viewer"><span>查看</span></a>
								</label>
								<label id="quickview">快速查看：</label>
								<label id="prev" style="width: auto; color: #0000ff; cursor: pointer; margin: 0 5px 0 5px;" onclick="javascript:$('#viewer').addClass('prev').click();"></label>
								<label id="next" style="width: auto; color: #0000ff; cursor: pointer; margin: 0 5px 0 5px;" onclick="javascript:$('#viewer').addClass('next').click();"></label>
								<label id="quickviewMsg" style="color: red; width: 160px; text-align: left; margin-left: 10px;"></label>
							</span>
						</p>
						<br />
						<p>
							<span>
								<b style="width: 150px; text-align: left; margin-left: 10px; font-size: 14px;">售后概况</b>&nbsp;&nbsp;&nbsp;&nbsp;<a class="startTextShadow"></a>&nbsp;数据
							</span>
						</p>
						<hr style="border: 1px #ccc dashed; "/>
						<p>
							<span>
								<label style="width: 200px; line-height: 50px;">退货数量：${afterSaleStatisticsVo.quitNums}</label>
							</span>
							<span>
								<label style="width: 200px; line-height: 50px;">退货金额：${afterSaleStatisticsVo.quitAmount}</label>
							</span>
							<span>
								<label style="width: 200px; line-height: 50px;">换货数量：${afterSaleStatisticsVo.tradeNums}</label>
							</span>
							<span>
								<label style="width: 200px; line-height: 50px;">换货金额：${afterSaleStatisticsVo.tradeAmount}</label>
							</span>
						</p>
						<br />
						<p>
							<span style="float: left; display: inline;">
								<b style="width: 150px; text-align: left; margin-left: 10px; font-size: 14px;">售后数据走势</b>&nbsp;&nbsp;&nbsp;&nbsp;<a class="startTextShadow"></a>&nbsp;数据
							</span>
							<span style="float: right; display: inline;">
								<!-- 
								<a href="#"><img src="${BasePath}/yougou/images/bi_print.jpg" alt="打印" /></a>
								 -->
							</span>
						</p>
						<hr style="border: 1px #ccc dashed; "/>
						<p>
							<span>
								<label style="width: 150px; text-align: left; margin-left: 10px; line-height: 50px;">请选择查看的走势图类型：</label>
								<#list Tendencys as item>
									<label for="${item}" style="width: 120px; text-align: left; line-height: 50px;">
										<input type="checkbox" name="operatingDataTrends" id="${item}" index="${item_index}" value="${item}" class="checkbox" />
										${item.description}
									</label>
								</#list>
							</span>
						</p>
						<p>
							<div id="container" style="min-width: 100%; height: 400px; margin: 0 auto; float: left;"></div>
						</p>
						<p>
							<span style="float: left; display: inline;">
								<b style="width: 150px; text-align: left; margin-left: 10px; font-size: 14px;">售后明细</b>&nbsp;&nbsp;&nbsp;&nbsp;<a class="startTextShadow"></a>&nbsp;数据
							</span>
							<span style="float: right; display: inline;">
								<a href="javascript:;" onclick="javascript:_as.print();return false;"><img src="${BasePath}/yougou/images/bi_print.jpg" alt="打印" /></a>
							</span>
						</p>
						<hr style="border: 1px #ccc dashed; "/>
						<br />
						<p>
							<a name="details"></a>
							<span>
								<label style="text-align: left; margin-left: 10px;">处理状态：</label>
								<select name="saleStatus" id="saleStatus">
									<option value="">所有状态</option>
									<#list SaleStatuses as item>
									<option value="${item}" <#if afterSaleStatisticsVo.saleStatus?? && afterSaleStatisticsVo.saleStatus == item>selected="selected"</#if>>${item.description}</option>
									</#list>
								</select>
							</span>
							<span>
								<label style="text-align: left; margin-left: 10px;">售后类型：</label>
								<select name="saleType" id="saleType">
									<option value="">所有类型</option>
									<#list SaleTypes as item>
									<option value="${item}" <#if afterSaleStatisticsVo.saleType?? && afterSaleStatisticsVo.saleType == item>selected="selected"</#if>>${item.description}</option>
									</#list>
								</select>
							</span>
						</p>
						<div>
							<!--列表start-->
							<table class="list_table">
								<thead>
									<tr>
										<th width="8%">订单号</th>
										<th width="8%">货号</th>
										<th width="8%">商品名称</th>
										<th width="8%">退回仓库</th>
										<th width="8%">下单时间</th>
										<th width="8%">订单金额</th>
										<th width="8%">申请单号</th>
										<th width="8%">售后申请时间</th>
										<th width="6%">处理状态</th>
										<th width="6%">售后类型</th>
										<th width="8%">退换货原因</th>
										<th width="8%">退回快递公司</th>
										<th width="8%">退回快递单号</th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
							<!--列表end-->
							<!--分页start-->
							<div class="page_box">
								<#if pageFinder ??>
									<#import "/manage/widget/common.ftl" as page>
									<@page.queryForm formId="queryForm"/>
								</#if>
							</div>
							<!--分页end-->
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	
	
</div>
	
</body>
<script type="text/javascript">
/***************
 * 智能日历脚本  *
 ***************/
function IntelligenceCalendar() {
	this.now = new Date();
	this.selectorDefinitions = { yearWeeks: AftersaleStatistics.prototype.BY_WEEK, yearMonths: AftersaleStatistics.prototype.BY_MONTH };
	this.prepareControls();
}
// 准备日历控件
IntelligenceCalendar.prototype.prepareControls = function() {
	var selectors = '';
	for (var key in this.selectorDefinitions) {
		selectors += '<div class="selectors ' + this.selectorDefinitions[key] + '" style="display: none; padding: 5px; position: absolute; background-color: #ffffb1; border: 1px #ffa500 solid; width: 160px; word-wrap: break-word; word-break: break-all;">';
		selectors += '	<div>';
		selectors += '		<div style="float: left; display: inline; width: 33%; text-align: center;"><a href="#" onclick="javascript:_ic.initialize(Number($(this).parent().next().text()) - 1);return false;">&lt;&lt;</a></div>';
		selectors += '		<div id="year" style="float: left; display: inline; width: 33%; text-align: center;"></div>';
		selectors += '		<div style="float: left; display: inline; width: 33%; text-align: center;"><a href="#" onclick="javascript:_ic.initialize(Number($(this).parent().prev().text()) + 1);return false;">&gt;&gt;</a></div>';
		selectors += '	</div>';
		selectors += '	<div>';
		selectors += '		<div style="width: 100%;">';
		selectors += '			<select id="' + key + '" style="width: 120px; margin: 5px 16px 0 16px;" onchange="javascript:_ic.onSelected(&quot;.' + this.selectorDefinitions[key] + '&quot;);">';
		selectors += '			</select>';
		selectors += '		</div>';
		selectors += '	</div>';
		selectors += '</div>';
	}
	$(document.body).append(selectors);
}
// 日期格式化函数
IntelligenceCalendar.prototype.getFormattedDate = function(date) {
	date = (date || this.now)
	return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
}
// 左日期时间格式化函数
IntelligenceCalendar.prototype.getLFormattedDateTime = function(date) {
	return (typeof(date) === 'string' ? date : this.getFormattedDate(date)) + ' 00:00:00';
}
// 右日期时间格式化函数
IntelligenceCalendar.prototype.getRFormattedDateTime = function(date) {
	return (typeof(date) === 'string' ? date : this.getFormattedDate(date)) + ' 23:59:59';
}
// 按年份加载智能日期
IntelligenceCalendar.prototype.initialize = function(thisYear) {
	if (thisYear && thisYear > this.now.getFullYear()) {
		return false;
	}
	$.ajax({
		type: 'post',
		url: '${BasePath}/bi/serializeIntelligenceCalendar.sc',
		dataType: 'json',
		data: 'year=' + (thisYear || this.now.getFullYear()) + '&rand=' + Math.random(),
		success: function(data, textStatus) {
			$('#thisYear').val(data.year);
			var text = $('#start').val();
			for (key in data) {
				var comp = $('.selectors').find('#' + key);
				var val = data[key];
				if (typeof(val) === 'number') {
					comp.text(val);
				} else if (jQuery.isArray(val)) {
					var opts = '', symbol = undefined;
					$.each(val, function(i) {
						opts += '<option start="' + this.start + '" end="' + this.end + '" checked="' + this.checked + '" value="' + this.text + '"' + (text == this.text ? (symbol = ' selected="selected" ') : '') +' title="' + (this.start.substring(0, 10) + ' 至 ' + this.end.substring(0, 10)) + '">' + this.text + '</option>';
					});
					comp.html(opts).find('option[checked="true"]:not([selected="selected"])').attr('selected', !symbol);
					comp.reJqSelect();
				}
			}
			$('#analyzePatttern').blur();
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			$('.selectors').find('#year').html('ERROR');
		}
	});
}
// 选择事件(弹出菜单按周按月)
IntelligenceCalendar.prototype.onSelected = function(selector) {
	var selectedValue = $(selector).css({ display: 'none' }).find('.x-combox-text').text().trim();
	var selectedItem = $('select').find('option[value="'+ selectedValue + '"]');
	$('#start').val(selectedValue);
	$('input[name="start"]').val(selectedItem.attr('start'));
	$('input[name="end"]').val(selectedItem.attr('end'));
}
// 快速查看导航消息
IntelligenceCalendar.prototype.throwMessage = function() {
	$('#quickviewMsg').text('呜呜,当前年份没有' + $('#' + $('#viewer').attr('class').split(/\s+/g).join(',#')).text() + '啦!');
	$('#viewer').removeClass('prev next');
	window.setTimeout('$("#quickviewMsg").text("")', 2000);
}

/***************
 * 售后明细脚本  *
 ***************/
function AftersaleStatistics() {

}
// 分析查询模式
AftersaleStatistics.prototype.BY_DAY = 'BY_DAY';
AftersaleStatistics.prototype.BY_WEEK = 'BY_WEEK';
AftersaleStatistics.prototype.BY_MONTH = 'BY_MONTH';
AftersaleStatistics.prototype.BY_USER_DEFINED = 'BY_USER_DEFINED';
// 加载售后明细
AftersaleStatistics.prototype.initialize = function(toPage) {
	$.ajax({
		type: 'post',
		url: '${BasePath}/bi/queryAftersaleStatisticsDetail.sc',
		dataType: 'html',
		data: $('#queryForm').serialize() + (toPage ? '&page=' + toPage : '') + '&rand=' + Math.random(),
		beforeSend: function(XMLHttpRequest) {
			$('.list_table > tbody').html('<tr><td colspan="14"><font color="#006600">数据载入中...</font></td></tr>');
		},
		success: function(data, textStatus) {
			$('.list_table').parent().html(data);
		},
		complete: function(XMLHttpRequest, textStatus) {
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			$('.list_table > tbody').html('<tr><td colspan="14"><font color="#ff0000">数据载入异常：ERROR</font></td></tr>');
		}
	});
}
// 打印
AftersaleStatistics.prototype.print = function() {
	window.open('${BasePath}/bi/printAftersaleStatisticsDetail.sc?' + $('#queryForm').serialize());
}
// 查询
AftersaleStatistics.prototype.query = function() {
	var selectedValue = $("#analyzePatttern").val();
	// 校验参数
	if (selectedValue == '') {
		alert('请选择查看的报表类型!');
		return false;
	}
	if ($('#start').val() == '') {
		alert('请按报表类型输入信息!');
		return false;
	}
	// 计算偏移量
	var offset = $(this).hasClass('prev') ? -1 : $(this).hasClass('next') ? 1 : 0;
	// (上/下/前/后)事件日期处理
	if (offset != 0) {
		if (selectedValue == AftersaleStatistics.prototype.BY_DAY) {
			var sections = $('#start').val().split('-');
			var dateHandle =  new Date();
			dateHandle.setFullYear(sections[0], sections[1] - 1, Number(sections[2]) + offset);
			$('#start').val(_ic.getFormattedDate(dateHandle));
		} else if (selectedValue == AftersaleStatistics.prototype.BY_WEEK || selectedValue == AftersaleStatistics.prototype.BY_MONTH) {
			var selectedItem = $('.' + selectedValue).find('option[value="' + $('#start').val() + '"]');
			var selectedIndex = selectedItem[0].index + offset;
			var selector = selectedItem.parent().get(0);
			var expectItem = (selectedIndex >= 0 && selectedIndex < selector.options.length ? selector.options[selectedIndex] : null);
			if (!expectItem) {
				_ic.throwMessage();
				return false;
			} else {
				selectedItem = $(expectItem);
				$('#start').val(selectedItem.val());
				$('input[name="start"]').val(selectedItem.attr('start'));
				$('input[name="end"]').val(selectedItem.attr('end'));
			}
		}
	}
	// 默认事件日期格式处理
	if (selectedValue == AftersaleStatistics.prototype.BY_DAY) {
		$('input[name="start"]').val(_ic.getLFormattedDateTime($('#start').val()));
		$('input[name="end"]').val(_ic.getRFormattedDateTime($('#start').val()));
	} else if (selectedValue == AftersaleStatistics.prototype.BY_USER_DEFINED) {
		if ($('#start').val() != '') {
			$('input[name="start"]').val(_ic.getLFormattedDateTime($('#start').val()));
		}
		if ($('#end').val() != '') {
			$('input[name="end"]').val(_ic.getRFormattedDateTime($('#end').val()));
		}
	}
	// 提交查询表单
	$('#queryForm').submit();
}
// 选择类型事件
AftersaleStatistics.prototype.change = function() {
	// 解除事件清空值
	$('#start,#end').unbind('click').val('');
	$('input[name="start"]').val('');
	$('input[name="end"]').val('');
	// 获取条件选择
	var isUserDefined = $(this).val() == 'BY_USER_DEFINED';
	var selectedText = $(this).find('option:selected').text();
	// 隐藏弹出层
	$('.selectors').css({ display: 'none' });
	// 处理快速导航项文本
	if (!isUserDefined) {
		$('#prev').html('上一' + selectedText.substring(1));
		$('#next').html('下一' + selectedText.substring(1));
	}
	// 显示隐藏
	$('#end').parent().css('display', (isUserDefined ? '' : 'none'));
	$('#quickview,#prev,#next').css('display', (!isUserDefined ? '' : 'none'));
	// 触发失去焦点事件
	$(this).blur();
}
// 失去焦点事件
AftersaleStatistics.prototype.blur = function() {
	var selectedValue = $(this).val();
	// 类型变更后重新注册相应事件并做相应业务预处理
	if (selectedValue == AftersaleStatistics.prototype.BY_DAY) {
		$("#start").calendar({maxDate:'#end'});
		// 如未控件未设置信息,设置为默认信息(本天)
		if ($.trim($("#start").val()) == '') {
			$("#start").val(_ic.getFormattedDate());
			$('input[name="start"]').val(_ic.getLFormattedDateTime());
			$('input[name="end"]').val(_ic.getRFormattedDateTime());
		}
	} else if (selectedValue == AftersaleStatistics.prototype.BY_WEEK || selectedValue == AftersaleStatistics.prototype.BY_MONTH) {
		$("#start").bind('click', function(){
			var aOffset = $(this).offset();
			var aHeight = $(this).outerHeight();
			$('.' + selectedValue).css({
				left : aOffset.left + 'px',
				top: (aOffset.top + aHeight + 2) + 'px',
				display: 'block'
			});
		});
		// 如未控件未设置信息,设置为默认信息(本周/本月/首周/首月/尾周/尾月)
		var selectedItem = $('.' + selectedValue).find('option:selected').eq(0);
		$("#start").val(selectedItem.text());
		$('input[name="start"]').val(selectedItem.attr('start'));
		$('input[name="end"]').val(selectedItem.attr('end'));
	} else if (selectedValue == AftersaleStatistics.prototype.BY_USER_DEFINED) {
		$("#start").calendar({maxDate:'#end'});
		$("#end").calendar({minDate:'#start'});
	}
}

// 初始化对象
var _ic = new IntelligenceCalendar();
var _as = new AftersaleStatistics();

//分页提交表单拦截事件
function onQueryPage(toPage) {
	_as.initialize(toPage);
}

/*****************
 * 文档初始化脚本  *
 *****************/
$(document).ready(function(){

	$('#viewer').bind('click', _as.query);
	$('#saleStatus,#saleType').bind('change', _as.query);
	var isWeek = $('#analyzePatttern').bind('blur', _as.blur).bind('change', _as.change).change().val() == AftersaleStatistics.prototype.BY_WEEK;
	
	// 默认初始化逻辑
	$('input[name="start"]').val('<#if afterSaleStatisticsVo.start??>${afterSaleStatisticsVo.start?datetime}</#if>');
	$('input[name="end"]').val('<#if afterSaleStatisticsVo.end??>${afterSaleStatisticsVo.end?datetime}</#if>');
	$('#start').val('${afterSaleStatisticsVo.startText?if_exists}');
	$('#end').val('${afterSaleStatisticsVo.endText?if_exists}');
	$('#thisYear').val('${afterSaleStatisticsVo.thisYear?if_exists}');
	$('.startTextShadow').html(isWeek ? ($('input[name="start"]').val().split(/\s+/g)[0] + '&nbsp至&nbsp;' + $('input[name="end"]').val().split(/\s+/g)[0]) : ($('#start').val() + ($.trim($('#end').val()) != '' ? '&nbsp至&nbsp;' : '') + $('#end').val()));
	
	// 加载当前年份信息
	_ic.initialize($('#thisYear').val());
	// 加载售后明细
	_as.initialize();
	
	/************************************************************/
	/** Highchart **/
	/************************************************************/
	
	var chartSeriesData = {
		RETURNS_NUM: [<#list afterSaleStatisticsVo.quitNumList as item>${item}<#if item_has_next>,</#if></#list>],
		RETURNS_AMOUNT: [<#list afterSaleStatisticsVo.quitAmountList as item>${item}<#if item_has_next>,</#if></#list>],
		EXCHANGE_NUM: [<#list afterSaleStatisticsVo.tradeNumList as item>${item?string('0.##')}<#if item_has_next>,</#if></#list>],
		EXCHANGE_AMOUNT: [<#list afterSaleStatisticsVo.tradeAmountList as item>${item}<#if item_has_next>,</#if></#list>]
	};
	
	var chartSeries = [];
	<#list Tendencys as item>
	chartSeries[chartSeries.length] = { name: '${item.description}', type: 'line', yAxis: ${item.yAxis}, data: chartSeriesData['${item}'], identifier: '${item}', marker: { radius: 4, lineWidth: 0 } };
	</#list>
	
    // 实例化图表控件
    window.chart = new Highcharts.Chart({
    	//控件
        chart: {
        	renderTo: 'container',
        	zoomType: 'xy',
        	marginTop: 25,
        	marginBottom: 50
        },
      	//颜色
        colors: [
        	'#4572A7',
        	'#ff0000',
        	'#89A54E',
        	'#AA4643'
        ],
      	//标题
        title: {
        	text: null
        },
      	//提示
        tooltip: {
            shared: true,
            crosshairs: true
        },
        //图例选项
        legend: {
        	x: 0,
        	y: 0,
            floating: true,
            borderWidth: 0
        },
        //版权
        credits: {
        	enabled: false
        },
        //常量
        lang: {
        	printButtonTitle: '打印图表',
        	exportButtonTitle: '导出图片',
        	downloadJPEG: '另存为JPEG图片',
        	downloadPDF: '另存为PDF文档',
        	downloadPNG: '另存为PNG图片',
        	downloadSVG: '另存为SVG矢量图'
        },
        //按扭
        navigation: {
        	buttonOptions: {
        		y: 0,
        		align: 'right',
        		verticalAlign: 'top'
        	}
        },
        //绘图选项
        plotOptions: {
        	series: {
        		events: {
        			legendItemClick: function(event) {
        				var checkbox = $('#' + this.options.identifier);
        				var isChecked = !checkbox.is(':checked');
        				checkbox.attr('checked', isChecked);
        				this[isChecked ? 'show' : 'hide']();
        				return false;
        			}
        		}
        	}
        },
        //数据
        series: chartSeries,
        //X轴
        xAxis: {
            gridLineWidth: 1,
            labels: {
                align: 'center',
                formatter: function() {
                	if (this.value) {
	                	i = this.value.lastIndexOf('-') + 1;
	                    return this.value.substring(i, i + 2);
                	}
                }
            },
            categories: [<#list afterSaleStatisticsVo.labelList as item>'${item}'<#if item_has_next>,</#if></#list>]
        },
        //Y轴
        yAxis: [{ // left y axis
            title: {
                text: '数量',
                align: 'high'
            },
            labels: {
                align: 'right',
                x: 0,
                y: 5,
                formatter: function() {
                    return this.value;
                }
            }
        }, { // right y axis
            title: {
                text: '金额',
                rotation: 270,
                align: 'high',
                style: {
                	color: '#AA4643'
                }
            },
            labels: {
                align: 'left',
                x: 0,
                y: 5,
                formatter: function() {
                    return '￥' + this.value;
                },
                style: {
                	color: '#AA4643'
                }
            },
            opposite: true
        }]
    });
    
	// 注册图表互动事件
	$('input[name="operatingDataTrends"]').click(function(){
		chart.series[this.getAttribute('index')][this.checked ? 'show' : 'hide']();
	});
	// 选中第1、第2图表项
    $('#RETURNS_NUM,#EXCHANGE_NUM').attr('checked', true);
    // 隐藏第3、第4图表项
    chart.series[1].hide();
    chart.series[3].hide();
    // 删除图表控件前后空行
    $(chart.renderTo).siblings('p').filter(function(){ return $(this).text().trim() == '' }).remove();
	
});
</script>
</html>
