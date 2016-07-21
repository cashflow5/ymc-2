(function($) {
	$.fn.pager = function(options) {
		var opts = $.extend({}, $.fn.pager.defaults, options);
		return this.each(function() {
			// empty out the destination element and then render out the pager with the supplied options
			// specify correct cursor activity
			$(this).empty().append(renderpager(parseInt(options.pagenumber), parseInt(options.pagecount), options.buttonClickCallback));
		});
	};

	$.fn.pager.defaults = {pagenumber: 1,pagecount: 1};

	// render and return the pager with the supplied options
	function renderpager(pagenumber, pagecount, buttonClickCallback) {
		//构建分页标签
		var $pager = $('<ul class="paginator"></ul>');

		//添加首页、上一页按钮
		$pager.append(renderButton('首页', pagenumber, pagecount, buttonClickCallback));
		$pager.append(renderButton('上一页', pagenumber, pagecount, buttonClickCallback));

		// 控制10个可见按钮
		var startPoint = 1;
		var endPoint = 9;
		var thpoint="<li class='thpoint'>...</li>";
		if (pagenumber > 4) {
			startPoint = pagenumber - 4;
			endPoint = pagenumber + 4;
		}
		if (endPoint > pagecount) {
			startPoint = pagecount - 8;
			endPoint = pagecount;
			thpoint = "";
		}
		if (startPoint < 1) {
			startPoint = 1;
		}

		// 循环渲染页码按钮
		for (var page = startPoint; page <= endPoint; page++){
			var currentButton = $('<li><a>' + (page) + '</a></li>');
			page == pagenumber ? currentButton.children().addClass('current') : currentButton.children().click(function() {
				buttonClickCallback(this.firstChild.data);
			});
			currentButton.appendTo($pager);
		}

		//添加末页、下一页按钮
		$pager.append(thpoint).append(renderButton('下一页', pagenumber, pagecount, buttonClickCallback));
		$pager.append(renderButton('末页', pagenumber, pagecount, buttonClickCallback));

		return $pager;
	}

	function renderButton(buttonLabel, pagenumber, pagecount, buttonClickCallback) {
		var $Button = $('<li><a>' + buttonLabel + '</a></li>');
		var destPage = 1;
		// work out destination page for required button type
		switch (buttonLabel) {
			case "首页":
				destPage = 1;
				break;
			case "上一页":
				destPage = pagenumber - 1;
				break;
			case "下一页":
				destPage = pagenumber + 1;
			break;
			case "末页":
				destPage = pagecount;
			break;
		}
		// disable and 'grey' out buttons if not needed.
		if (buttonLabel == "首页" || buttonLabel == "上一页") {
			pagenumber <= 1 ? $Button.children().addClass('nolink') : $Button.click(function() { buttonClickCallback(destPage); });
		}
		else {
			pagenumber >= pagecount ? $Button.children().addClass('nolink') : $Button.click(function() { buttonClickCallback(destPage); });
		}
		return $Button;
	}
 })(jQuery);