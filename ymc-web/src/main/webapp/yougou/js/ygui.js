//jqselect start********
try { document.execCommand('BackgroundImageCache', false, true); } catch (e) { }; 
(function($) {
    $.fn.jqSelect = function(o) {
		//if($(this).parent().parent().attr('class')=='fl') return;
		if($(this).attr('class')=="combox") return;
        o = $.extend({
            arrowUrl: "images/select_bg.png", //选择框右侧向下的箭头图片
            arrowWidth: 18, //上述图片的宽
            arrowHeight: 22, //上述图片的高
            borderColor: "#b5b8c8", //选择框的边框颜色
            dropDownListHeight: 200, //下拉列表的高度，超过此高度将出现垂直滚动条
            hoverColor: "#cbdaf0", //下拉列表每项在鼠标经过时的背景色
            currItemAddStyle: true
        }, o || {});
		var combox=$("<div  />");
        var ul, lis, currItem = $("<div  />"),
        arrowHeight = parseInt(o.arrowHeight), arrowWidth = parseInt(o.arrowWidth), borderColor = o.borderColor,
        select = $(this).attr('class','combox').hide(),
        options = $("option", select),
        optionsSize = options.size(),
        dropDownListHeight = optionsSize * arrowHeight > parseInt(o.dropDownListHeight) ? parseInt(o.dropDownListHeight) : optionsSize * arrowHeight ,

        getListItem = function(option) {
            var currText = option.text(), currClass = $.trim(option.attr("class")), currStyle = $.trim(option.attr("style"));
            return $("<div   />").text(currText).addClass(currClass).attr("style", currStyle);
        },
        getSelectedItem = function() {
            return $("option:selected", select);

        },
        getListItems = function() {
            var $ul = $("<ul class='ylDropDownUl' tabindex='0' />");
            options.each(function() {
                var li = $("<li />"), option = $(this);
                if (option.attr("title")) {
                	li.attr("title",option.attr("title"));
                }
                li.append(getListItem(option));
                $ul.append(li);
            });
            ul = $ul;
            lis = $("li", ul);
            return $ul;
        },
         setCurrItem = function() {
             currItem.empty();
             if (o.currItemAddStyle) {
                 currItem.append(getListItem(getSelectedItem()));
             }
             else {
                 currItem.text(getSelectedItem().text());
             }
         },
        createElem = function() {
            select.wrap("<div  />");
			select.parent().attr('style',select.attr('style'));
            select.after(getListItems());
			select.after(combox);
		   $(combox).append(currItem);
            setCurrItem();
        },
        setElem = function() {
			
			currItem.addClass('x-combox-text').height(20);
			combox.addClass('x-combox').height(arrowHeight);
			ul.addClass('x-combox-ul').height(dropDownListHeight)
           
            select.parent().css({ "display": "inline","text-align":"left","float":"left","margin-right":"2px" }).height(25);
            var width = ul.width() + 22;
            ul.hide();
            if ($.browser.msie && ($.browser.version == 7 || $.browser.version == 6)) {//hack ie7,ie6
                select.parent().css({ "display": "inline", "zoom": "1", "vertical-align": "middle" })
            };
			var w;
			w=select.width();
			if(w==0) 
			{
				w=$('.x-combox-text').width()+14
			}
            currItem.width(w-14);
			combox.width(w+10);
			select.parent().width(w+10);
            // ul.width(w + 8);
			ul.css({"minWidth":(w + 8)+"px"});	

        },
        setScrollTop = function(index) {
            var visibleNum = parseInt(dropDownListHeight / arrowHeight);
            ul.scrollTop((index - visibleNum + 1) * arrowHeight);

        },
        showUl = function() {
            $(".ylDropDownUl").hide();
            var index = options.index(getSelectedItem());
            lis.eq(index).css({"background-color": "#cbdaf0",'border':'1px dotted #8eabe4'});
            ul.show();
            //ul.focus();
            setScrollTop(index);
        },
        hideUl = function() {
            ul.hide();
        },
        toggleUl = function() {
            if (ul.css("display") == "none") {
                showUl();
            } else {
                hideUl();
            }
        },
        setScroll = function(step) {
            var index = options.index(getSelectedItem());
            index = index + step;
            if (index == -1)
                index = optionsSize - 1;
            currIndex = index % optionsSize;
            lis.eq(currIndex).mouseover();

            options.eq(currIndex).attr("selected", true);

            setScrollTop(currIndex + 1);
            setCurrItem();
        },
        bindEvents = function() {
            $("html").click(function() { hideUl(); });
            combox.click(function(event) {
                event.stopPropagation();
                toggleUl();

            }).keydown(function(event) {
                switch (event.keyCode) {
                    case 40: // up
                        event.preventDefault();
                        showUl();
                        break;

                }

            });
            ul.click(function(event) { event.stopPropagation(); })
              .keydown(function(event) {
                  switch (event.keyCode) {
                      case 38: // up
                          event.preventDefault();
                          setScroll(-1);
                          break;
                      case 40: // down
                          event.preventDefault();
                          setScroll(1);
                          break;
                      case 9: // tab
                      case 13: // return
                      case 27: //escape
                          hideUl();
                          break;
                  }
              });
            lis.click(function(event) {
                event.stopPropagation();
                var index = lis.index($(this));
                options.eq(index).attr("selected", true);
                setCurrItem();
                hideUl();
                select.change();

            }).mouseover(function() {
                lis.css({"background-color": "transparent","border":"1px solid #fff"});
                $(this).css({"background-color": "#cbdaf0",'border':'1px dotted #8eabe4'});
			})
        };

        createElem();
        setElem();
        bindEvents();
        return $(this);
    };
})(jQuery);


(function($) {
     $.fn.reJqSelect = function(options){
		_select=$(this);
		var  opt = $("option", _select);
		var len=$(opt).length;
		var ul=$(this).nextAll("ul.ylDropDownUl");
		var dropHeight=22;
		var setScrollTop = function(index) {
            var visibleNum = parseInt(dropHeight / 22);
            ul.scrollTop((index - visibleNum + 1) * 22);
        };
		 ul.empty();
		 for(i=0;i<len;i++)
		 {
			ul.append('<li '+($(opt[i]).attr("title") ? 'title="' + $(opt[i]).attr("title") + '"' : '') +'" style="background-color: transparent; border: 1px solid rgb(255, 255, 255);"><div>'+$(opt[i]).text()+'</div></li>');
		 }
		 var lis=$("li",ul);
		 dropHeight=opt.size()*22;
		 if(dropHeight>200) dropHeight=200;
		 ul.height(dropHeight);
		 lis.mouseover(function(){
			ul.find("li").css({"background-color": "transparent","border":"1px solid #fff"});
            $(this).css({"background-color": "#cbdaf0",'border':'1px dotted #8eabe4'});
		 });
		 ul.prev().children().children().text(_select.find("option:selected").text());
		 lis.click(function(event){
				event.stopPropagation();
                var index = lis.index($(this));
				var sel=$(this).parent().parent().find('select').show().css({'position':'absolute','left':'-9000px'});//ie6 隐藏bug
				try{
				sel.get(0).options[index].selected = true;
				}catch(e){}
			    ul.prev().children().children().text($(this).text());
                ul.hide();
                sel.change();
 
		 })
		 
		 $(".x-combox").click(function(){
			 var index=_select.get(0).selectedIndex;
			 setScrollTop(index);
			 
		 })
		 
	 };
})(jQuery);


//jqselect end********

//jqradio ********
(function($) {
     $.fn.jqRadio = function(options){
        var self = this;
        return $(':radio+label',this).each(function(){
            $(this).addClass('hRadio');
            if($(this).prev().is(":checked"))
			{
                $(this).addClass('hRadio-checked');
			}
        }).click(function(event){
            $(this).siblings().removeClass("hRadio-checked");
            if(!$(this).prev().is(':checked')){
				$(this).addClass("hRadio-checked");
                $(this).prev()[0].checked = true;
            }
            event.stopPropagation();
        }).prev().hide();
        
    };
})(jQuery);

//jqcheckbox ********
(function($) {
		$.fn.jqCheckbox=function(options){
		$(':checkbox',this).each(function(){
			$(this).parent().addClass('checkbox');
            if($(this).is(':disabled')==false){
                if($(this).is(':checked'))
				    $(this).parent().addClass("checked");
            }else{
                $(this).parent().addClass('disabled');
            }
		}).hide();
	};
})(jQuery);

//jqfile ********
(function ($) {
    $.fn.jqfile = function (options) {
        if (options) {
            $.extend(options);
        };
        return this.each(function () {
            var self = this;
			if($(self).hasClass('jqfile')) return;
			w=$(this).width();
            var wrapper = $("<div>").addClass('file_button')
			
            var filename = $('<input type="text" readonly="readonly" class="ginput">').addClass($(self).attr("class")).css({
                "display": "inline",
                "text-align":"left",
				"float":"left",
               	 width: w + "px"
            });
            $(self).before(filename);
			//$(wrapper).append('<a class=button style="margin-left:2px;"><span>浏览</span></a>');
            $(self).wrap(wrapper);
			
            $(self).css({
				"float":"left",
                "position": "relative",
                "cursor": "pointer",
                "opacity": "0.0"
            }).addClass('jqfile');
                    $(self).css({"margin-left":-w+50+'px'});
            $(self).bind("change", function () {
                filename.val($(self).val());
            });
           
        });
    };
})(jQuery);