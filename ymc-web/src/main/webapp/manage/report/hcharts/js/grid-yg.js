/**
 * Grid theme for Highcharts JS
 * @author guoran 20150424
 */

Highcharts.theme = {
	colors: ['#91d5f8', '#bbbbbb', '#faeba3', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4'],
    chart: {
        type: 'line',
        backgroundColor: {
            linearGradient: {
                x1: 0,
                y1: 0,
                x2: 1,
                y2: 1
            },
            stops: [
                [0, 'rgb(255, 255, 255)'],
                [1, 'rgb(255, 255, 255)']
            ]
        },
        borderWidth: 0
    },
    xAxis: {
        gridLineWidth: 1,
        gridLineColor: '#f0f0f0',
        min: 0,
        minorTickInterval: '',
        lineColor: '#d2e7fc',
        lineWidth: 2,
        tickWidth: 0,
        tickColor: '#f0f0f0',
        // tickmarkPlacement: 'on' /*between*/,
        tickInterval: 1
    },
    yAxis: {
        min: 0,
        gridLineColor: '#f0f0f0',
        lineColor: '#f0f0f0',
        lineWidth: 1
    },
    credits: {
        enabled: false,
        text: "yougou.com",
        href: "http://www.yougou.com"
    }
};

// Apply the theme
var highchartsOptions = Highcharts.setOptions(Highcharts.theme);
