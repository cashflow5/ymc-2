//下拉框
(function($) {

    function YGUI(type) {
        this.version = '1.0';
        this.name = "YGUI";
        this.type = type;
    }

    window.YGUI = YGUI;

    var dropdown = function(target) {
        this.target = target;
        this.data = [];
        YGUI.call(this, 'dropdown');
    };

    //刷新数据
    dropdown.prototype.refreshData = function() {
        with(this) {
            data = [];
            for (var i = 0; i < target.length; i++) {
                data.push({
                    text: target[i].text,
                    value: target[i].value
                });
            };
        }
    }

    dropdown.prototype.init = function() {
        var that = this;
        with(that) {
            refreshData();
            render();
            triggerEvent();
        }
    };

    dropdown.prototype.render = function() {
        var ddbox = $('<span class="yg-dropdown"></span>'),
            list = [],
            $target = $(this.target),
            tclass = $target.attr('class');
        ddbox.addClass(tclass);
        $target.removeAttr('class');
        $.each(this.data, function(i, item) {
            list.push('<li data-value="{0}">{1}</li>'.format(item.value, item.text));
        });
        ddbox.append('<div class="yg-dropdown-s"></div><div class="yg-dropdown-title"><input type="text" class="yg-dropdown-val" readonly="readonly"/><i class="iconfont Gray yg-dropdown-ico">&#xe60d;</i></div><ul class="yg-dropdown-list hide">' + list.join('') + '</ul>')
        $target.wrap(ddbox);
    };

    dropdown.prototype.refresh = function() {
        var list = [],
            $target = $(this.target),
            dropbox = $target.parents('.yg-dropdown'),
            ddlist = $('.yg-dropdown-list', dropbox);
        this.refreshData();
        $.each(this.data, function(i, item) {
            list.push('<li data-value="{0}">{1}</li>'.format(item.value, item.text));
        });
        ddlist.html(list.join(''));
        this.listitemEvent(ddlist, dropbox);
    };

    dropdown.prototype.listitemEvent = function(ul, dropbox) {
        var $target = $(this.target),
            li = $('li', ul),
            input_val = $('.yg-dropdown-val', dropbox),
            intext = li.eq(0).text();
        li.hover(function() {
            $(this).addClass('hover');
        }, function() {
            $(this).removeClass('hover');
        }).click(function(event) {
            var $this = $(this);
            ul.addClass('hide');
            if (input_val.data('value') != $this.data('value')) {
                input_val.val($this.text()).data('value', $this.data('value'));
                $('option', $target).eq($this.index()).prop('selected', true).click();
                $target.change();
            }
        });
        if (input_val.data('value') != li.data('value') || input_val.val() != intext) {
            $target.change();
        }
        input_val.val(intext ? intext : '').data('value', li.data('value'));
    }

    dropdown.prototype.triggerEvent = function() {
        var $target = $(this.target),
            dropbox = $target.parents('.yg-dropdown'),
            ul = $('.yg-dropdown-list', dropbox),
            yg_dropdown_title = $('.yg-dropdown-title', dropbox),
            display = ul.css("display");
        yg_dropdown_title.click(function(event) {
            ul.toggleClass('hide');
        });
        this.listitemEvent(ul, dropbox);
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

    $.fn.dropdown = function(args) {
        return $(this).each(function(i, item) {
            var dd = new dropdown(item);
            dd.init();
            $(item).data('ui', dd);
        });
    }

    //排序样式设置
    var SORTOPT = {
            desc: true,
            asc: false
        },
        sortfilter = function(target) {
            this.target = target;
            this.data = SORTOPT.asc;
            YGUI.call(this, 'sort');
        };
    sortfilter.prototype.init = function() {
        var that = this;
        with(that) {
            render();
            triggerEvent();
        }
    };
    sortfilter.prototype.render = function() {
        var $target = $(this.target),
            tclass = $target.attr('class'),
            sortbox = $('<span class="yg-sort"><span class="yg-sort-s"></span><span class="icon iconfont"><i class="sort-up">&#xe603;</i><i class="sort-down Blue">&#xe604;</i></span>&nbsp;</span>');
        sortbox.addClass(tclass);
        $target.wrap(sortbox);
    };
    sortfilter.prototype.triggerEvent = function() {
        var $target = $(this.target),
            sortbox = $target.parents('.yg-sort'),
            sort_icon = $('.icon', sortbox);
        sortbox.click(function() {
            sort_icon.toggleClass('checked');
        })
    };
    $.fn.Sort = function(args) {
        return $(this).each(function(i, item) {
            var sf = new sortfilter(item);
            sf.init();
            $(item).data('ui', sf);
        });
    }

    //开关状态枚举
    var TOGGLEEM = {
            on: true,
            off: false
        },
        /**
         * 开关按钮
         * @param  {Object} target 目标源
         */
        togglebutton = function(target) {
            this.target = target;
            this.data = TOGGLEEM.off;
            YGUI.call(this, 'togglebutton');
        };
    togglebutton.prototype.init = function() {
        with(this) {
            render();
            triggerEvent();
            if ($(target).prop('checked')) {
                changeStage(true);
            }
        }
    };
    togglebutton.prototype.render = function() {
        var $target = $(this.target),
            tclass = $target.attr('class'),
            togglebox = $('<span class="yg-toggle"><span class="yg-toggle-s"></span><div class="toggle-states"><span class="toggle-text">未启用</span></div></span>');
        togglebox.addClass(tclass);
        $target.wrap(togglebox);
    };
    togglebutton.prototype.triggerEvent = function() {
        var $target = $(this.target),
            that = this,
            togglebox = $target.parents('.yg-toggle');
        togglebox.click(function() {
            that.changeStage();
        });
    };
    /**
     * 更改状态
     * @param  {Boolean} isInit 是否为初始化
     */
    togglebutton.prototype.changeStage = function(isInit) {
        var $target = $(this.target),
            togglebox = $target.parents('.yg-toggle'),
            togglestate = $('.toggle-states', togglebox),
            toggletext = $('.toggle-text', togglestate);
        if (!isInit) {
            $target.prop('checked', !$target.prop('checked'));
        }
        if ($target.prop('checked')) {
            togglestate.addClass('on');
            toggletext.text('已启用');
        } else {
        	toggletext.text('未启用');
        	togglestate.removeClass('on');
        }
        // console.log($target.prop('checked'))
        if(!isInit){
        	$target.change();
        }
    }
    $.fn.ToggleButton = function(args) {
        return $(this).each(function(i, item) {
            var sf = new togglebutton(item);
            sf.init();
            $(item).data('ui', sf);
        });
    }

    $(function() {
        $('[data-ui-type=dropdown]').dropdown().size();
        $('[data-ui-type=sort]').Sort();
        $('[data-ui-type=toggle]').ToggleButton();
        $(window).on('mousedown.bby.dropdown', function(e) {
            var t = $(e.target);
            if (!t.hasClass('.yg-dropdown')) {
                if (!t.parents('.yg-dropdown')[0]) {
                    $('.yg-dropdown-list').addClass('hide');
                }
            }
        });
    });

})(jQuery);
