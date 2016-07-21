G.Detail = {
    bindChart: function(dom,categories, valueSuffix, series) {
        $(dom).highcharts({
            chart:{
                type:'line',
                height:350
            },
           /* plotOptions: {
                series: {
                   pointPadding:0,//数据点之间的距离值
                   groupPadding: 0,//分组之间的距离值
                   borderWidth: 0, 
                   shadow: false,
                   pointWidth:5 //柱子之间的距离值
                }
            },*/
            title: {
                text: null,
                x: -20 //center
            },
            xAxis: {
                categories: categories,
                labels: {
                    style: {
                        fontSize:'10px'
                    }
                }
            },
            yAxis: {
                title: {
                    text: null
                },
                //tickPositions:[0,2,4,6, 8, 10],//设置y轴的刻度
                //tickPixelInterval:100,//控制y轴的刻度间隔值
                // tickInterval: 2,//设置刻度隔几个值显示
                max:null,//设置y轴最大值 ,自动计算
                plotLines: [{
                    value: 0,
                    width:1,
                    color: '#808080'
                }],
                min: 0,
                allowDecimals: false
            },
            tooltip: {
                valueSuffix: valueSuffix,
                shared: true,
                formatter: function () {
                    var s = '<b>' + this.x +':00'+ '</b>';
                    $.each(this.points, function () {
                        s += '<br/>' + this.series.name + ': ' +
                            this.y + valueSuffix;
                    });
                    return s;
                }
            },
            legend: {
                layout: 'horizontal',
                align: 'center',
                verticalAlign: 'bottom',//今天、昨天、上周同天标题的垂直位置
                borderWidth: 0
            },
            series: series
        });
    },
    bindChartArea:function (dom,categories, valueSuffix, series) {
        $(dom).highcharts({
            chart:{
                type:'area',
                height:50//设置图标的高度
                //width:107
            },
            title: {
                text: null,
                x: -20 //center
            },
            xAxis: {
                labels:{
                     enabled: false
                 },
                categories: categories,
                  gridLineColor:"#fff"
            },
            yAxis: {
                labels:{
                     enabled: false
                 },
                title: {
                    text: null
                },
                gridLineColor:"#fff",//网格的颜色
                min: 0
            },
            // tooltip: {
            //     valueSuffix: valueSuffix
            // },
            plotOptions: {
            area: {
                pointStart: 0,
                marker: {
                    enabled: false,
                    symbol: 'circle',
                    radius: 2,
                    states: {
                        hover: {
                            enabled: true
                        }
                    }
                }
            }
        },
            legend: {
                enabled:false//去掉底下线条的提示
            },
            series: series
        });
    },
    // 进度条的宽度设置
    progressWidth:function(){
        var text,
            percent=$('.percent');    
        $.each(percent,function(index){
            text=percent.eq(index).html();
            percent.eq(index).parent().width(text);
        })  
    }
}


