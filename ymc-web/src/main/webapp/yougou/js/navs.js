/*简单Nav切换效果*/
;
(function($) {
    $.fn.NavBarBasic = function(callback) {
        var $this = $(this);
        $this.each(function() {
            var $that = $(this);
            $('.navbar-title a', $that).click(function(e) {
                e.preventDefault();
                var _a = $(this);
                _a.addClass('active').siblings().removeClass('active');
                $(_a.attr('href')).addClass('active').siblings().removeClass('active');
                if (callback) {
                    if (typeof callback == 'function') {
                        callback(_a);
                    } else {
                        console.error('亲，你可以不传，但如果你传的话，则必须是个方法呀！');
                    }
                }

            });
        });
        return $this;
    };

    function navItemEvent(navitem, callback) {
        navitem.on('click', '[data-toggle=navitem]', function(e) {
            e.preventDefault();
            var _a = $(this),
                _li = _a.parents('li');
            _li.addClass('active').siblings().removeClass('active');
            $(_a.data('href')).addClass('active').siblings().removeClass('active');

            if (callback) {
                if (typeof callback == 'function') {
                    callback(_a, _a.data('href'));
                    _a.data('access', 'true');
                } else {
                    console.error('亲，你可以不传，但如果你传的话，则必须是个方法呀！');
                }
            }
        });
    }

    /*默认Nav页切换效果*/
    $.fn.NavPage = function(callback) {
        navItemEvent($(this));
        return $(this);
    };

    /**
     * Nav页切换效果 带滚动
     * @callback {function} 插件调用后的回调方法
     */
    $.fn.NavPageScroll = function(callback) {
        var $this = $(this),
            navs = $('.navs', $this),
            length = $('.navs li', $this).length,
            width = $('.navs li:first').width()+10;
	        if(length <=4){
	        	navs.width('450px');
	        }else{
	        	navs.width(width * length);
	        }
            

        /**     
         * 滚动nav
         * @obj {Object} 点击的按钮
         * @type {int} 1为向右， 0为向左
         * @speed {int} 每次滚动的数值
         * @difference 差异调整
         */
        function scrollNavs(obj, type, speed, difference) {
            ul = obj.parents('.navs'),
                ml = parseInt(ul.css('marginLeft')),
                mlcomput = (type ? ml - speed : ml + speed),
                nav = ul.parent(),
                navWidth = nav.width(),
                ulWidth = ul.width(); //obj.siblings('li').length * speed,
            range = ulWidth - navWidth - difference;
            // console.log('ul:' + ulWidth + ' nav:' + navWidth + ' range' + range);
            if (type) {
                //mlcomput = mlcomput < -range ? -range : mlcomput;
                if (mlcomput < -range) {
                    mlcomput = -range;
                    $('.nav-click-right', $this).hide().siblings('.nav-click-left').show();
                }
            } else {
                //mlcomput = mlcomput > 0 ? 0 : mlcomput;
                if (mlcomput > 0) {
                    mlcomput = 0;
                    $('.nav-click-left', $this).hide().siblings('.nav-click-right').show();
                }
            }
            mlpx = mlcomput + 'px';
            ul.animate({
                marginLeft: mlpx
            });
        }

        $('<span class="nav-click-left icon iconfont" title="前一项">&#xe60e;</span><span class="nav-click-right icon iconfont" title="后一项">&#xe610;</span>').appendTo(navs);

        $('.nav-click-right', $this).on('click', function() {
            scrollNavs($(this), 1, 190, 2);
        });

        $('.nav-click-left', $this).on('click', function() {
            scrollNavs($(this), 0, 190, 2);
        });
        navItemEvent($this, callback);
        return $(this);
    };

})(jQuery);
$(function(){
    $('.nav-default').NavPageScroll(function(e, id) {
        if (!e.data('access')) {
            G.Index.bindChart(id, '访客数(UV)', categories, '人', series);
        }
    });
    //add lijunfang 20150924
    $('.upload_list').on('mouseover','.upload_list li',function(){
        $(this).find('.del-btn').removeClass('hide');
    });
    $('.upload_list').on('mouseout','.upload_list li',function(){
        $(this).find('.del-btn').addClass('hide');
    });

    $('.upload_list').on('click', '.del-btn', function(event) {
        $(this).parent().remove();
    });
})