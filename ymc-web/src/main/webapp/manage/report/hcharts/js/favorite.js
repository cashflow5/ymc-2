(function($) {
//收藏效果设置
YGUI.collect=function (){
    var attr, length, display,
        operate = $('.operate'),
        collect = $('.collect'),
        ul = $('.collect-list'),
        li = $('.collect-item'),
        collect_btn = $('.collect-btn'),
        collect_click = $('.collect-click'),
        checkbox = li.find('input[type="checkbox"]'),
        data = [{
            'name': '需做促销商品',
            'value':'bbbbbbbbbbbbbbbb'
        }, {
            'name': '需做流商品',
            'value':'bbbbbbbbbbbbbbbb'
        }, {
            'name': '转化率较低商品',
            'value':'bbbbbbbbbbbbbbbb'
        }, {
            'name': '库存较大商品',
            'value':'bbbbbbbbbbbbbbbb'
        }, {
            'name': '重观察商品',
            'value':'bbbbbbbbbbbbbbbb'
        }, {
            'name': '爆款商品',
            'value':'bbbbbbbbbbbbbbbb'
        }, {
            'name': '需补货商品',
            'value':'bbbbbbbbbbbbbbbb'
        }, {
            'name': '13周专题商品',
            'value':'bbbbbbbbbbbbbbbb'
        }, {
            'name': '13周首页商品',
            'value':'bbbbbbbbbbbbbbbb'
        }];
    ul.on('mouseover mouseout', '.collect-item', function(event) { //设置下拉框的hover事件，添加背景高亮显示
        if (event.type == "mouseover") {
            $(this).addClass('hover').siblings().removeClass('hover');
        } else {
            $(this).removeClass('hover');
        }
    });
    collect.click(function(event) {
        var arr = [],collect_list,
            collect_index = collect.index(this);
        $(data).each(function(index, el) {
            var name = this.name,
                li_new = '<li class="collect-item"><label for="d-' + collect_index + '-' + index + '"><input type="checkbox"class="checbox" name="destination" id="d-' + collect_index + '-' + index + '" value="'+this.value+'">' + this.name + '</label></li>';
            arr.push(li_new);
        });
        display = $(this).find('.collect-list').css('display');
        collect_list = $(this).find('.collect-list');
        if ($(this).find('.collect-click').hasClass('first')) { //判断是否是第一次点击
            collect_list.html(arr.join(""));
            $(this).find('.collect-click').find('i.up').html('&#xe60d;');
            collect_list.show();
        } else {
            if (display == "none") {
                collect_list.show();
                $(this).find('.collect-click').find('i.up').html('&#xe60d;');
            } else {
                collect_list.hide();
                $(this).find('.collect-click').find('i.up').html('&#xe610;');
            }
        }
        $(this).find('.collect-click').removeClass('first');
    });
    collect.hover(function() {
        $(this).parent().addClass('hover'); //添加class hover解决ie7下index的问题
    }, function() {
        $(this).find('.collect-list').hide();
        $(this).find('i.up').html('&#xe610;');
        $(this).parent().removeClass('hover');
    });
    ul.on("click", 'input[type="checkbox"]', function() { //给input添加click事件
        attr = $(this).prop('checked'),
            length = $(this).parent().parent().parent().find('input[type="checkbox"]:checked').length;
        if (length > 0) {
            $(this).parent().parent().parent().parent().siblings('.star').find('i').addClass('Blue');
        } else {
            $(this).parent().parent().parent().parent().siblings('.star').find('i').removeClass('Blue');
        }
        if (attr) {
            $(this).parent().parent().addClass('checked').siblings().removeClass('checked');
        } else {
            $(this).parent().parent().removeClass('checked');
        }
    });
    
    // 滚动条的样式插件
    ul.niceScroll({
        cursorcolor: "#5893ed",
        cursoropacitymax: 1,
        touchbehavior: false,
        cursorwidth: "5px",
        cursorborder: "0",
        cursorborderradius: "5px"
    });
}
    YGUI.collect();
})(jQuery);
