G.Single = {
    bindChart: function(dom,categories, valueSuffix, series) {
        $(dom).highcharts({
            chart:{
                type:'line',
                height:350
            },
            plotOptions: {
                series: {
                   pointPadding:0,//数据点之间的距离值
                   groupPadding: 0,//分组之间的距离值
                   borderWidth: 0, 
                   shadow: false,
                   pointWidth:5 //柱子之间的距离值
                }
            },
            title: {
                text: null,
                x: -20 //center
            },
            xAxis: {
                categories: categories
            },
            yAxis: {
                title: {
                    text: null
                },
                //tickPositions:[0,2,4,6, 8, 10],//设置y轴的刻度
                //tickPixelInterval:100,//控制y轴的刻度间隔值
                 tickInterval: 2,//设置刻度隔几个值显示
                max:10,//设置y轴最大值
                plotLines: [{
                    value: 0,
                    width:1,
                    color: '#808080'
                }],
                min: 0
            },
            tooltip: {
                valueSuffix: valueSuffix
            },
            legend: {
                layout: 'horizontal',
                align: 'right',
                verticalAlign: 'top',//今天、昨天、上周同天标题的垂直位置
                borderWidth: 0
            },
            series: series
        });
    },
    dataTime:function(id,id_val,opt){
        $(id).focus(function(event) {
            var $this=$(this),
                id_str=$this.attr("id"),
                arr=id_str.split('-');
            if($this.hasClass('starttime')){
                WdatePicker($.extend({
                    onpicked:function(){//设置联动开始时间和结束时间联动
                        $dp.$('endtime-'+arr[1]).focus();
                    },
                    //maxDate:'#F{$dp.$D(\'endtime-'+arr[1]+'\')}'//设置开始的最大值不能超过结束时间选择的值
                    maxDate:'#F{$dp.$D(\'endtime-'+arr[1]+'\')||\'%y-%M-{%d-1}\'}',
                	minDate:'#F{$dp.$D(\'endtime-'+arr[1]+'\',{M:-6})&&\'2015-08-13\'}'
                },opt));
            }else{
                WdatePicker($.extend({
                	maxDate:'#F{\'%y-%M-{%d-1}\'||$dp.$D(\'starttime-'+arr[1]+'\',{M:6})}',
                    minDate:'#F{$dp.$D(\'starttime-'+arr[1]+'\')}'
                },opt));               
            }
        });
    },
    bindChartByDate:function(id,yAxis,series){  	
    	$(id).highcharts({
    		title: {
                text: null,
                x: -20 //center
            },
            xAxis: {
                type: 'datetime',
                min:null,
                labels: {    
                   formatter: function () {  
                      return Highcharts.dateFormat('%m/%d', this.value);  
                  }  
                },
                tickInterval: null
            },
            yAxis: yAxis,
            tooltip: {  
               xDateFormat: '%Y-%m-%d',		
               shared: true
             },
             credits:{
             	enabled:false,
             	href:'http://www.yougou.com',
             	text:'www.yougou.com'
             },
             exporting:{
             	enabled:false
             },
             legend: {
                 layout: 'horizontal',
                 align: 'right',
                 verticalAlign: 'top',
                 borderWidth: 0
             },
            series:series
      
        });
    	
    }
};

$(function() {

        //今日实收件数数据
   /* var categories1 = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17','18','19','20','21'],
        series1 = [
            {
                name: '今天',
                visible: true,//默认显示曲线
                data: [2, 0, 0, 0, 1, 7, 1, 0, 6, 1, 4, 6, 6, 0, 0, 0, 0,0,7,8,5,0]
            }
        ];
    G.Single.bindChart('.record-number',categories1, '件', series1);//今日实收件数曲线图
*/
    // 日期函数调用
    G.Single.dataTime('.starttime');
    G.Single.dataTime('#endtime-3','',{
    	onpicked:function(){
    		if($('#starttime-3').val()!=''){   			
    			//加载指标数据
    			loadSingleCommodityEveryDayIndex();
    			$(this).blur();
    			
    		}
    	}	
    });
});
