/* 
    jquery.suggest.js
    ~~~~~~~~~~~~~~~~~

    This plugin allows to provide a google like suggest functionality
    from static data for input fields.

    Example
    -------

    $('input').suggest(['foo', 'bar', 'some', 'words', 'or phrases']);

    :copyright: (C) 2009 Florian Boesch <pyalot@gmail.com>
    :license: GNU AGPL3, see LICENSE for more details.
*/

(function($){
    var arrow_up = 38;
    var arrow_down = 40;
    var enter = 13;
    var tab = 9;
    var esc = 27;
    $.fn.suggest = function(params){
        var input = $(this).attr('autocomplete', 'off');
        var selectId=params.selectId || "";
        var optmap=getallopts(selectId);
        var words=optmap.keys();
        var minComplete = params.minComplete || 1;
		//搜索框默认显示初始值
		if(!$("#"+selectId).attr("multiple"))
		{
			var selectopt=$("#"+selectId+" option[selected]");
			input.val(selectopt.eq(0).text().trim());
		}
		
        $(document).click(function(){
            box.hide();
        });
        var selection = null;
        var length = 0;
        var active = false;
        var show = function(){
            var offset = input.offset();
            box
                .css({
                    top: offset.top + input.height() + 7,
                    left: offset.left
                })
                .empty()
                .show();
            selection = null;
            active = true;
        };
        var initShow=function(){
        	var offset = input.offset();
            box
                .css({
                    top: offset.top + input.height() + 7,
                    left: offset.left
                })
                .show();
                box.find("li").remove();
	            $.each(words,function(i,word){
		            $('<li>' + word + '</li>')
		                .css({
		                    paddingleft: 0,
		                    paddingRight: 0,
		                    margin: 0,
		                    paddingLeft: '5px',
		                    paddingRight: '5px',
		                    cursor: 'pointer',
		                })
		                .data('text', word)
		                .hover(
		                    function(){
		                        $(this).css('background-color', 'LightBlue');
		                    },
		                    function(){
		                        $(this).css('background-color', 'transparent');
		                    }
		                )
		                .click(function(){
		               		hide();
		               		input.val(word);
		                    addtoselect(word,optmap,selectId);
		                    return false;
		                })
		                .appendTo(box);    
	            });
        };
        var hide = function(){
            box.hide();
            active = false;
        };
        var box = $('<ul></ul>')
            .css({
                position: 'absolute',
                backgroundColor: 'white',
                border: '1px solid #999',
                paddingLeft: '0px',
                paddingRight: '0px',
                margin: 0,
                paddingTop: '3px',
                paddingBottom: '3px',
                listStyleType: 'none',
                minWidth: input.width(),
                color: '#555',
                textAlign: 'left',
                zIndex:1000,
                height:'200px',
                overflow:'auto'
            })
            .hide()
            .appendTo('body');

        input
            .keyup(function(event){
                if(
                    event.keyCode != arrow_down &&
                    event.keyCode != arrow_up &&
                    event.keyCode != enter &&
                    event.keyCode != tab &&
                    event.keyCode != esc
                ){
                    var value = input.val();
                    var to_complete = value.split(' ').pop().toLowerCase();
                    if(to_complete.length >= minComplete){
                        var suggestions = $.grep(words, function(word, i){
                            return word.toLowerCase().indexOf(to_complete.toLowerCase()) >= 0;
                        });
                        if(suggestions.length){
                            selection = null;
                            length = suggestions.length;
                            show();
                            $.each(suggestions, function(i, text){
                                $('<li>' + text + '</li>')
                                    .css({
                                        paddingleft: 0,
                                        paddingRight: 0,
                                        margin: 0,
                                        paddingLeft: '5px',
                                        paddingRight: '5px',
                                        cursor: 'pointer'
                                    })
                                    .data('text', text)
                                    .hover(
                                        function(){
                                            $(this).css('background-color', 'LightBlue');
                                        },
                                        function(){
                                            $(this).css('background-color', 'transparent');
                                        }
                                    )
                                    .click(function(){
                                        hide();
                                        input.val(text).focus();
                                        addtoselect(text,optmap,selectId);
                                        return false;
                                    })
                                    .appendTo(box);
                            });
                        }
                        else{
                            hide();
                        }
                    }
                    else{
                        hide();
                    }
                }
            }).click(function(){
            	initShow();
            	return false;
            });
        var keyhandler = function(event){
            if(active){
                if(event.keyCode == arrow_down || event.keyCode == arrow_up){
                    if(selection == null){
                        selection = event.keyCode == arrow_down ? 0 : length-1;
                    }
                    else{
                        selection += event.keyCode == arrow_down ? 1 : -1;
                        if(selection < 0){
                            selection = length - 1;
                        }
                        else if(selection >= length){
                            selection = 0;
                        }
                    }
                    var text = box.find('li')
                        .css('background-color', 'transparent')
                        .eq(selection)
                            .css('background-color', 'LightBlue')
                            .data('text');
                    input.val(text);
                    return false;
                }
                else if(event.keyCode == enter){
                    input.val(box.hide().find('li').eq(selection).data('text'));
                   	addtoselect(box.hide().find('li').eq(selection).data('text'),optmap,selectId);
                   	return false;
                }
                else if(event.keyCode == tab || event.keyCode == esc){
                    hide();
                }
                else if(event.keyCode == 8)
                {
                	if(!$("#"+selectId).attr("multiple"))
					{
						removeallopts(selectId);
					}
                }
            }
        };
        if($.browser.safari){
            input.keydown(keyhandler);
        }
        else{
            input.keypress(keyhandler);
        }
    };
})(jQuery);
//获取select所有options
function getallopts(selectId)
{
	var selectopts=$("#"+selectId+" option[selected]");
	var opts=$("#"+selectId+" option");
	removeallopts(selectId);
	var map=new Map();
	$.each(opts,function(){
		map.put($(this).text().trim(),$(this).attr("selected","selected"));
	});
	var text="";
	$.each(selectopts,function(i,opt){
		text+=$(opt).text().trim()+",";
	});
	addtoselect(text.substring(0,text.length-1),map,selectId);
	return map;
}
//移除所有options
function removeallopts(selectId)
{
	$("#"+selectId+" option").remove();
}
//将文本框中搜索到的添加到select中
function addtoselect(text,optmap,selectId)
{
	if($("#"+selectId).attr("multiple"))
	{
		var optvals=text.split(",");
		$(optvals).each(function(i,val){
			var option=$(optmap.get(val));
			option.bind("dblclick",function(){
				$(this).remove();
			});
			$("#"+selectId).append(option);
		});
	}
	else
	{
		removeallopts(selectId);
		var option=$(optmap.get(text.trim()));
		$("#"+selectId).append(option);
	}
}


//map实现
//construction
function Map() {
      this.obj = new Object();
};
//add a key-value
Map.prototype.put = function(key, value) {
      this.obj[key] = value;
};
//get a value by a key,if don't exist,return undefined
Map.prototype.get = function(key) {
      return this.obj[key];
};
//remove a value by a key
Map.prototype.remove = function(key) {
      if(this.get(key)==undefined) {
              return;
      }
      delete this.obj[key];
};
//clear the map
Map.prototype.clear = function() {
      this.obj = new Object();
};
//get the size
Map.prototype.size = function() {
      var ary = this.keys();
      return ary.length;
};
//get all keys
Map.prototype.keys = function() {
      var ary = new Array();
      for(var temp in this.obj) {
              ary.push(temp);
      }
      return ary;
};
//get all values
Map.prototype.values = function() {
      var ary = new Array();
      for(var temp in this.obj) {
              ary.push(this.obj[temp]);
      }
      return ary;
};