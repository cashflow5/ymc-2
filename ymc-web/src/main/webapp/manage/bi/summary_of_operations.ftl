<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-报表-经营概况</title>
<script type="text/javascript" src="${BasePath}/highcharts/js/highcharts.js"></script>
<script type="text/javascript" src="${BasePath}/highcharts/js/modules/exporting.js"></script>
</head>

<body>
	
	
	<div class="main_container">
		
		<div class="normal_box">
		<p class="title site">当前位置：商家中心 &gt; 报表 &gt; 经营概况</p>
		<div class="tab_panel">
			<ul class="tab">
				<li class="curr">
					<span>经营概况</span>
				</li>
			</ul>
			<div class="tab_content">
				<form id="queryForm" name="queryForm" method="post"  action="${BasePath}/bi/querySummaryOfOperations.sc">
					<div class="search_box">
						<p>
							<span>
								<label style="width: 150px; text-align: left; margin-left: 10px;">请选择查看的报表类型：</label>
								<select name="analyzePatttern" id="analyzePatttern">
									<#list AnalyzePattterns as item>
										<option value="${item}" weight="${item.weight}" <#if summaryOfOperationsVo.analyzePatttern == item>selected="selected"</#if>>${item.description}</option>
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
								<b style="width: 150px; text-align: left; margin-left: 10px; font-size: 14px;">经营概况</b>&nbsp;&nbsp;&nbsp;&nbsp;<a class="startTextShadow"></a>&nbsp;数据
							</span>
						</p>
						<hr style="border: 1px #ccc dashed; "/>
						<p>
							<span>
								<label style="width: 200px; line-height: 50px;">下单量<img src="${BasePath}/yougou/images/bi_help.jpg" title="统计期内客户提交的总订单量（先款订单付款后列入统计，先货订单提交后列入统计）" />：${summaryOfOperationsVo.orderNums}</label>
							</span>
							<span>
								<label style="width: 200px; line-height: 50px;">下单商品件数<img src="${BasePath}/yougou/images/bi_help.jpg" title="统计期内提交的订单包含的总商品件数（先款订单付款后列入统计，先货订单提交后列入统计）" />：${summaryOfOperationsVo.orderCommodityNums}</label>
							</span>
							<span>
								<label style="width: 200px; line-height: 50px;">下单金额<img src="${BasePath}/yougou/images/bi_help.jpg" title="统计期内提交的订单金额之和（先款订单付款后列入统计，先货订单提交后列入统计）" />：${summaryOfOperationsVo.orderPayTotalPrices?string('0.##')}</label>
							</span>
							<span>
								<label style="width: 200px; line-height: 50px;">下单客户数<img src="${BasePath}/yougou/images/bi_help.jpg" title="统计期内提交订单的客户数（先款订单付款后列入统计，先货订单提交后列入统计）" />：${summaryOfOperationsVo.orderCustomers}</label>
							</span>
							<br />
							<span>
								<label style="width: 200px; line-height: 50px;">客单价<img src="${BasePath}/yougou/images/bi_help.jpg" title="统计期内单个客户的平均下单金额。客单价=下单金额/下单客户数" />：${summaryOfOperationsVo.avgOrderCustomerPrice?string('0.##')}</label>
							</span>
							<span>
								<label style="width: 200px; line-height: 50px;">上架商品数量<img src="${BasePath}/yougou/images/bi_help.jpg" title="统计期截止时间点之间的上架的商品数量。（可按天、按周、按月统计）" />：${summaryOfOperationsVo.sellCommodityNums}</label>
							</span>
							<#if summaryOfOperationsVo.analyzePatttern == 'BY_DAY'>
							<span>
								<label style="width: 200px; line-height: 50px;">总上架商品数量<img src="${BasePath}/yougou/images/bi_help.jpg" title="统计期截止时间点的上架的总商品数量。（只能按天统计）" />：${summaryOfOperationsVo.totalCommodityNums}</label>
							</span>
							</#if>
						</p>
						<br />
						<p>
							<span style="float: left; display: inline;">
								<b style="width: 150px; text-align: left; margin-left: 10px; font-size: 14px;">经营数据走势</b>&nbsp;&nbsp;&nbsp;&nbsp;<a class="startTextShadow"></a>&nbsp;数据
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
								<b style="width: 150px; text-align: left; margin-left: 10px; font-size: 14px;">经营明细</b>&nbsp;&nbsp;&nbsp;&nbsp;<a class="startTextShadow"></a>&nbsp;数据
							</span>
							<span style="float: right; display: inline;">
								<a href="javascript:;" onclick="javascript:_sop.print();return false;"><img src="${BasePath}/yougou/images/bi_print.jpg" alt="打印" /></a>
							</span>
						</p>
						<hr style="border: 1px #ccc dashed; "/>
						<!--列表start-->
						<table class="list_table">
							<thead>
								<tr>
									<th width="20%">时间段</th>
									<th width="20%">下单量</th>
									<th width="20%">下单客户数</th>
									<th width="20%">下单商品数</th>
									<th width="20%">下单金额</th>
								</tr>
							</thead>
							<tbody>
								<#if summaryOfOperationsVo.summaryOfOperationsVoDetails?size != 0>
									<#list summaryOfOperationsVo.summaryOfOperationsVoDetails as detail>
										<tr>
											<td series="CATEGORIES">${detail.timeRange}</td>
											<td series="PURCHASE_QUANTITY">${detail.orderNums}</td>
											<td series="PURCHASE_CUSTOMER_NUM">${detail.orderCustomers}</td>
											<td series="PURCHASE_COMMODITY_NUM">${detail.orderCommodityNums}</td>
											<td series="PURCHASE_AMOUNT">${detail.orderPayTotalPrices?string('0.##')}</td>
										</tr>
									</#list>
									<tr>
										<td><b>总和</b></td>
										<td>${summaryOfOperationsVo.orderNums}</td>
										<td>${summaryOfOperationsVo.orderCustomers}</td>
										<td>${summaryOfOperationsVo.orderCommodityNums}</td>
										<td>${summaryOfOperationsVo.orderPayTotalPrices?string('0.##')}</td>
									</tr>
									<tr>
										<td><b>平均值</b></td>
										<td>${summaryOfOperationsVo.avgOrderNums}</td>
										<td>${summaryOfOperationsVo.avgOrderCustomers}</td>
										<td>${summaryOfOperationsVo.avgOrderCommodityNums}</td>
										<td>${summaryOfOperationsVo.avgOrderPayTotalPrices?string('0.##')}</td>
									</tr>
								<#else>
									<tr>
										<td colspan="5">暂无数据</td>
									</tr>
								</#if>
							</tbody>
						</table>
						<!--列表end-->
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
	this.selectorDefinitions = { yearWeeks: SummaryOfOperations.prototype.BY_WEEK, yearMonths: SummaryOfOperations.prototype.BY_MONTH };
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
function SummaryOfOperations() {

}
// 分析查询模式
SummaryOfOperations.prototype.BY_DAY = 'BY_DAY';
SummaryOfOperations.prototype.BY_WEEK = 'BY_WEEK';
SummaryOfOperations.prototype.BY_MONTH = 'BY_MONTH';
SummaryOfOperations.prototype.BY_USER_DEFINED = 'BY_USER_DEFINED';
// 打印
SummaryOfOperations.prototype.print = function() {
	window.open('${BasePath}/bi/printSummaryOfOperations.sc?' + $('#queryForm').serialize());
}
// 查询
SummaryOfOperations.prototype.query = function() {
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
		if (selectedValue == SummaryOfOperations.prototype.BY_DAY) {
			var sections = $('#start').val().split('-');
			var dateHandle =  new Date();
			dateHandle.setFullYear(sections[0], sections[1] - 1, Number(sections[2]) + offset);
			$('#start').val(_ic.getFormattedDate(dateHandle));
		} else if (selectedValue == SummaryOfOperations.prototype.BY_WEEK || selectedValue == SummaryOfOperations.prototype.BY_MONTH) {
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
	if (selectedValue == SummaryOfOperations.prototype.BY_DAY) {
		$('input[name="start"]').val(_ic.getLFormattedDateTime($('#start').val()));
		$('input[name="end"]').val(_ic.getRFormattedDateTime($('#start').val()));
	} else if (selectedValue == SummaryOfOperations.prototype.BY_USER_DEFINED) {
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
SummaryOfOperations.prototype.change = function() {
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
SummaryOfOperations.prototype.blur = function() {
	var selectedValue = $(this).val();
	// 类型变更后重新注册相应事件并做相应业务预处理
	if (selectedValue == SummaryOfOperations.prototype.BY_DAY) {
		$("#start").calendar({maxDate:'#end'});
		// 如未控件未设置信息,设置为默认信息(本天)
		if ($.trim($("#start").val()) == '') {
			$("#start").val(_ic.getFormattedDate());
			$('input[name="start"]').val(_ic.getLFormattedDateTime());
			$('input[name="end"]').val(_ic.getRFormattedDateTime());
		}
	} else if (selectedValue == SummaryOfOperations.prototype.BY_WEEK || selectedValue == SummaryOfOperations.prototype.BY_MONTH) {
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
	} else if (selectedValue == SummaryOfOperations.prototype.BY_USER_DEFINED) {
		$("#start").calendar({maxDate:'#end'});
		$("#end").calendar({minDate:'#start'});
	}
}

// 初始化对象
var _ic = new IntelligenceCalendar();
var _sop = new SummaryOfOperations();

$(document).ready(function(){
	
	$('#viewer').bind('click', _sop.query);
	var isWeek = $('#analyzePatttern').bind('blur', _sop.blur).bind('change', _sop.change).change().val() == SummaryOfOperations.prototype.BY_WEEK;
	
	// 默认初始化逻辑
	$('input[name="start"]').val('<#if summaryOfOperationsVo.start??>${summaryOfOperationsVo.start?datetime}</#if>');
	$('input[name="end"]').val('<#if summaryOfOperationsVo.end??>${summaryOfOperationsVo.end?datetime}</#if>');
	$('#start').val('${summaryOfOperationsVo.startText?if_exists}');
	$('#end').val('${summaryOfOperationsVo.endText?if_exists}');
	$('#thisYear').val('${summaryOfOperationsVo.thisYear?if_exists}');
	$('.startTextShadow').html(isWeek ? ($('input[name="start"]').val().split(/\s+/g)[0] + '&nbsp至&nbsp;' + $('input[name="end"]').val().split(/\s+/g)[0]) : ($('#start').val() + ($.trim($('#end').val()) != '' ? '&nbsp至&nbsp;' : '') + $('#end').val()));
	
	// 加载当前年份信息
	_ic.initialize($('#thisYear').val());
	
	/************************************************************/
	/** Highchart **/
	/************************************************************/
	var chartSeries = [];
	<#list Tendencys as item>
	chartSeries[chartSeries.length] = { name: '${item.description}', type: 'line', yAxis: ${item.yAxis}, data: $('td[series="${item}"]').map(function(){ return Number($(this).text()) }).get(), identifier: '${item}', marker: { radius: 4, lineWidth: 0 } };
	</#list>
	
    // 实例化图表控件
    window.chart = new Highcharts.Chart({
    	//控件
        chart: {
        	renderTo: 'container',
        	zoomType: 'x',
        	marginTop: 25,
        	marginBottom: 50
        },
        //颜色
        colors: [
        	'#AA4643',
        	'#4572A7',
        	'#89A54E',
        	'#80699B'
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
            categories: $('td[series="CATEGORIES"]').map(function(){ return $(this).text() }).get()
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
    $('#PURCHASE_AMOUNT,#PURCHASE_QUANTITY').attr('checked', true);
    // 隐藏第3、第4图表项
    chart.series[2].hide();
    chart.series[3].hide();
    // 删除图表控件前后空行
    $(chart.renderTo).siblings('p').filter(function(){ return $(this).text().trim() == '' }).remove();
});
</script>
</html>
