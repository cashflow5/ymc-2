<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优购商家中心-报表-商品销售明细</title>
<script type="text/javascript" src="${BasePath}/highcharts/js/highcharts.js"></script>
<script type="text/javascript" src="${BasePath}/highcharts/js/modules/exporting.js"></script>
<style type="text/css">
.chart-legend {
	width: 280px;
	display: block;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
	-o-text-overflow: ellipsis;
}
</style>
</head>

<body>
	
	
	<div class="main_container">
		
		<div class="normal_box">
		<p class="title site">当前位置：商家中心 &gt; 报表 &gt; 商品销售明细</p>
		<div class="tab_panel">
			<ul class="tab">
				<li class="curr">
					<span>商品销售明细</span>
				</li>
			</ul>
			<div class="tab_content">
				<div class="search_box">
					<form id="queryForm" name="queryForm" method="post" action="${BasePath}/bi/queryAllCommoditySalesDetails.sc">
						<p>
							<span>
								<label style="width: 150px; text-align: left; margin-left: 10px;">请选择查看的报表类型：</label>
								<select name="analyzePatttern" id="analyzePatttern">
									<#list AnalyzePattterns as item>
										<option value="${item}" weight="${item.weight}" <#if commoditySaleDetailsVo.analyzePatttern == item>selected="selected"</#if>>${item.description}</option>
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
								<b style="width: 150px; text-align: left; margin-left: 10px; font-size: 14px;">商品概况</b>&nbsp;&nbsp;&nbsp;&nbsp;<a class="startTextShadow"></a>&nbsp;数据
							</span>
						</p>
						<hr style="border: 1px #ccc dashed; "/>
						<p>
							<span>
								<label style="width: 200px; line-height: 50px;">下单量<img src="${BasePath}/yougou/images/bi_help.jpg" title="统计期内客户提交的总订单量（先款订单付款后列入统计，先货订单提交后列入统计）" />：${commoditySaleDetailsVo.orderNums}</label>
							</span>
							<span>
								<label style="width: 200px; line-height: 50px;">下单商品件数<img src="${BasePath}/yougou/images/bi_help.jpg" title="统计期内提交的订单包含的总商品件数（先款订单付款后列入统计，先货订单提交后列入统计）" />：${commoditySaleDetailsVo.orderCommodityNums}</label>
							</span>
							<span>
								<label style="width: 200px; line-height: 50px;">下单金额<img src="${BasePath}/yougou/images/bi_help.jpg" title="统计期内提交的订单金额之和（先款订单付款后列入统计，先货订单提交后列入统计）" />：${commoditySaleDetailsVo.orderPayTotalPrices?string('0.##')}</label>
							</span>
							<span>
								<label style="width: 200px; line-height: 50px;">成交金额<img src="${BasePath}/yougou/images/bi_help.jpg" title="统计期内在线支付已支付的金额" />：${commoditySaleDetailsVo.orderTradedAmount?string('0.##')}</label>
							</span>
						</p>
						<br />
						<p>
							<span style="float: left; display: inline;">
								<b style="width: 150px; text-align: left; margin-left: 10px; font-size: 14px;">商品销售排行TOP15</b>&nbsp;&nbsp;&nbsp;&nbsp;<a class="startTextShadow"></a>&nbsp;数据
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
										<input type="radio" name="operatingDataTrends" id="${item}" index="${item_index}" value="${item}" />
										${item.description}
									</label>
								</#list>
							</span>
						</p>
						<p>
							<#if commoditySaleDetailsVo.top15?? && commoditySaleDetailsVo.top15?size != 0>
							<div id="container" style="min-width: 100%; height: 400px; margin: 0 auto; float: left;"></div>
							<#else>
							<div style="margin: 0 auto; text-align: center; color: red;">暂无数据</div>
							</#if>
						</p>
						<p>
							<span style="float: left; display: inline;">
								<b style="width: 150px; text-align: left; margin-left: 10px; font-size: 14px;">销售明细</b>&nbsp;&nbsp;&nbsp;&nbsp;<a class="startTextShadow"></a>&nbsp;数据
							</span>
							<span style="float: right; display: inline;">
								<a href="javascript:;" onclick="javascript:_csd.print();return false;"><img src="${BasePath}/yougou/images/bi_print.jpg" alt="打印" /></a>
							</span>
						</p>
						<hr style="border: 1px #ccc dashed; "/>
					</form>
					
					<a href="#printableArea"></a>
					<div id="printableArea" class="tab_panel">
						<!--选项卡-->
						<ul id="salesQueryNavigationBar" class="tab">
							<#list CommoditySalesQueryStates as item>
							<li state="${item}" onclick="javascript:if(!$(this).hasClass('curr')){$(this).toggleClass('curr').siblings('li').toggleClass('curr');$('#filter').click();}" ${item.selected?string('class="curr"', '')}><span>${item.description}</span></li>
							</#list>
						</ul>
						<!--选项卡-->
						<div class="tab_content">
							<div class="search_box">
								<p>
									<span>
										<label>商品名称：</label>
										<input type="text" name="commodityName" id="commodityName" class="inputtxt" style="width:100px;" />
									</span>
									<span>
										<label>货号：</label>
										<input type="text" name="thirdPartyCode" id="thirdPartyCode" class="inputtxt" style="width:100px;" />
									</span>
									<a class="button" id="filter" href="javascript:;" onclick="javascript:_csd.initialize.call(this);"><span>查询</span></a>
								</p>
								<div id="salesQueryDatas">
									<!--列表start-->
									<table class="list_table" style="margin-top: 5px;">
										<thead>
											<tr>
												<th width="6%">序号</th>
												<th width="8%">商品图片</th>
												<th width="24%">商品名称</th>
												<th width="12%">货号</th>
												<th width="10%">最近上架时间</th>
												<th width="10%">下单商品件数</th>
												<th width="10%">下单数量</th>
												<th width="10%">下单金额</th>
												<th width="10%">销售均价<img src="${BasePath}/yougou/images/bi_help.jpg" title="统计期内（先款订单付款后列入统计，先货订单提交后列入统计），某商品的平均销售单价。销售均价=下单金额/下单商品件数"/></th>
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
						</div>
					</div>
				</div>
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
	this.selectorDefinitions = { yearWeeks: CommoditySalesDetails.prototype.BY_WEEK, yearMonths: CommoditySalesDetails.prototype.BY_MONTH };
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
 * 销售明细脚本  *
 ***************/
function CommoditySalesDetails() {

}
// 分析查询模式
CommoditySalesDetails.prototype.BY_DAY = 'BY_DAY';
CommoditySalesDetails.prototype.BY_WEEK = 'BY_WEEK';
CommoditySalesDetails.prototype.BY_MONTH = 'BY_MONTH';
CommoditySalesDetails.prototype.BY_USER_DEFINED = 'BY_USER_DEFINED';
// 加载销售明细
CommoditySalesDetails.prototype.initialize = function(toPage) {
	var salesQueryState = $('#salesQueryNavigationBar').find('.curr');
	$.ajax({
		type: 'post',
		url: '${BasePath}/bi/queryCommoditySalesDetails.sc',
		dataType: 'html',
		data: $('#queryForm').serialize() + '&commodityName=' + $('#commodityName').val() + '&thirdPartyCode=' + $('#thirdPartyCode').val() + '&salesQueryState=' + salesQueryState.attr('state') + (toPage ? '&page=' + toPage : '') +  '&rand=' + Math.random(),
		beforeSend: function(XMLHttpRequest) {
			$('#salesQueryDatas').find('tbody').html('<tr><td colspan="10"><font color="#006600">数据载入中...</font></td></tr>');
		},
		success: function(data, textStatus) {
			$('#salesQueryDatas').html(data).attr('data', this.data);
		},
		complete: function(XMLHttpRequest, textStatus) {
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			$('#salesQueryDatas').find('tbody').html('<tr><td colspan="10"><font color="#ff0000">数据载入异常：ERROR</font></td></tr>');
		}
	});
}
// 打印
CommoditySalesDetails.prototype.print = function() {
	if ($('#salesQueryDatas').attr('data')) {
		window.open('${BasePath}/bi/printCommoditySalesDetails.sc?' + $('#salesQueryDatas').attr('data'));
	}
}
// 查询
CommoditySalesDetails.prototype.query = function() {
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
		if (selectedValue == CommoditySalesDetails.prototype.BY_DAY) {
			var sections = $('#start').val().split('-');
			var dateHandle =  new Date();
			dateHandle.setFullYear(sections[0], sections[1] - 1, Number(sections[2]) + offset);
			$('#start').val(_ic.getFormattedDate(dateHandle));
		} else if (selectedValue == CommoditySalesDetails.prototype.BY_WEEK || selectedValue == CommoditySalesDetails.prototype.BY_MONTH) {
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
	if (selectedValue == CommoditySalesDetails.prototype.BY_DAY) {
		$('input[name="start"]').val(_ic.getLFormattedDateTime($('#start').val()));
		$('input[name="end"]').val(_ic.getRFormattedDateTime($('#start').val()));
	} else if (selectedValue == CommoditySalesDetails.prototype.BY_USER_DEFINED) {
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
CommoditySalesDetails.prototype.change = function() {
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
CommoditySalesDetails.prototype.blur = function() {
	var selectedValue = $(this).val();
	// 类型变更后重新注册相应事件并做相应业务预处理
	if (selectedValue == CommoditySalesDetails.prototype.BY_DAY) {
		$("#start").calendar({maxDate:'#end'});
		// 如未控件未设置信息,设置为默认信息(本天)
		if ($.trim($("#start").val()) == '') {
			$("#start").val(_ic.getFormattedDate());
			$('input[name="start"]').val(_ic.getLFormattedDateTime());
			$('input[name="end"]').val(_ic.getRFormattedDateTime());
		}
	} else if (selectedValue == CommoditySalesDetails.prototype.BY_WEEK || selectedValue == CommoditySalesDetails.prototype.BY_MONTH) {
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
	} else if (selectedValue == CommoditySalesDetails.prototype.BY_USER_DEFINED) {
		$("#start").calendar({maxDate:'#end'});
		$("#end").calendar({minDate:'#start'});
	}
}

//初始化对象
var _ic = new IntelligenceCalendar();
var _csd = new CommoditySalesDetails();

//分页提交表单拦截事件
function onQueryPage(toPage) {
	_csd.initialize(toPage);
}

$(document).ready(function(){
	
	$('#viewer').bind('click', _csd.query);
	var isWeek = $('#analyzePatttern').bind('blur', _csd.blur).bind('change', _csd.change).change().val() == CommoditySalesDetails.prototype.BY_WEEK;
	
	// 默认初始化逻辑
	$('input[name="start"]').val('<#if commoditySaleDetailsVo.start??>${commoditySaleDetailsVo.start?datetime}</#if>');
	$('input[name="end"]').val('<#if commoditySaleDetailsVo.end??>${commoditySaleDetailsVo.end?datetime}</#if>');
	$('#start').val('${commoditySaleDetailsVo.startText?if_exists}');
	$('#end').val('${commoditySaleDetailsVo.endText?if_exists}');
	$('#thisYear').val('${commoditySaleDetailsVo.thisYear?if_exists}');
	$('.startTextShadow').html(isWeek ? ($('input[name="start"]').val().split(/\s+/g)[0] + '&nbsp至&nbsp;' + $('input[name="end"]').val().split(/\s+/g)[0]) : ($('#start').val() + ($.trim($('#end').val()) != '' ? '&nbsp至&nbsp;' : '') + $('#end').val()));
	
	// 加载当前年份信息
	_ic.initialize($('#thisYear').val());
	// 加载销售明细
	_csd.initialize();
	
	/************************************************************/
	/** Highchart **/
	/************************************************************/
	
	//数据集(下单数量/下单商品件数/下单金额/成交金额)
	var ORDER_NUMS = 0, ORDER_COMMODITY_NUMS = 1, ORDER_PAY_TOTAL_PRICES = 2, ORDER_TRADED_AMOUNTS = 3;
	var chartSerieses = [[], [], [], []];
	//颜色集
	var chartSeriesColors = ['#6A5ACD','#87CEEB','#191970','#A0522D','#FFA500','#2E8B57','#C71585','#FA8072','#808000','#4169E1','#BC8F8F','#FF0000','#800080','#778899','#191970'];
	<#if commoditySaleDetailsVo.top15?? && commoditySaleDetailsVo.top15?size != 0>
	<#list commoditySaleDetailsVo.top15 as item>
	chartSerieses[ORDER_NUMS][${item_index}] = { name: '${item.commodityName?html}', yAxis: 0, type: 'column', color: chartSeriesColors[${item_index}], data: [${item.orderNums?string('0')}], marker: { radius: 4, lineWidth: 0 }, url: '${item.commodityUrl!''}' };
	chartSerieses[ORDER_COMMODITY_NUMS][${item_index}] = jQuery.extend(true, {}, chartSerieses[ORDER_NUMS][${item_index}], { data: [${item.orderCommodityNums?string('0')}] });
	chartSerieses[ORDER_PAY_TOTAL_PRICES][${item_index}] = jQuery.extend(true, {}, chartSerieses[ORDER_NUMS][${item_index}], { data: [${item.orderPayTotalPrices?string('0.##')}] });
	chartSerieses[ORDER_TRADED_AMOUNTS][${item_index}] = jQuery.extend(true, {}, chartSerieses[ORDER_NUMS][${item_index}], { data: [${item.orderTradedAmounts?string('0.##')}] });
	</#list>
	</#if>
	
	// 创建图表控件
	function createChart(chartSeries) {
	    // 实例化图表控件
	    window.chart = new Highcharts.Chart({
	    	//控件
	        chart: {
	        	renderTo: 'container',
	        	zoomType: 'xy',
	        	marginTop: 25,
	        	marginLeft: 400,
	        	marginBottom: 50
	        },
	      	//标题
	        title: {
	        	text: null
	        },
	      	//提示
	        tooltip: {
	            useHTML: true,
	            positioner: function() {
	            	return { x: 420, y: 35 };
	            },
	            headerFormat: '',
	            pointFormat: '<font color="{series.color}">{series.name}: </font><b>{point.y}</b><br/>',
	            footerFormat: ''
	        },
	        //图例选项
	        legend: {
	        	x: 0,
	        	y: 0,
	        	layout: 'vertical',
	        	align: 'left',
	        	verticalAlign: 'middle',
	            floating: true,
	            borderWidth: 1,
	            width: 320,
	            useHTML: true,
	            labelFormatter: function() {
	            	return '<span class="chart-legend" title="' + this.name + '" index="' + this.index + '" onclick="javascript:window.open(&quot;' + this.options.url + '&quot;)">' + this.name + '</span>';
	            }
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
	                	return null;
	                }
	            },
	            categories: []
	        },
	        //Y轴
	        yAxis: [{ // left y axis
	            title: {
	                text: function(){
	                	return ($('input[name="operatingDataTrends"]:checked').parent().text().trim().match(/金额/ig) || '数量') 
	                }(),
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
	        }]
	    });
	}
	
	// 注册图表互动事件
	$('input[name="operatingDataTrends"]').click(function(){
		var chartSeries = chartSerieses[this.getAttribute('index')];
		if (chartSeries.length > 0) {
			// 创建图表
			createChart(chartSeries);
		    // 删除图表控件前后空行
		    $(window.chart.renderTo).siblings('p').filter(function(){ return $(this).text().trim() == '' }).remove();
		}
	}).eq(ORDER_PAY_TOTAL_PRICES).attr('checked', true).click();
});
</script>
</html>
