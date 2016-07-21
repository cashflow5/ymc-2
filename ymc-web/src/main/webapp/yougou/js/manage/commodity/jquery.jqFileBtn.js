
/*
 * 上传按钮插件
 */
(function($) {
	var isDebug = false;
	/**
	 * 记录日志
	 * @param {Object} msg
	 */
	function log(msg) {
		if(isDebug) console.log(msg);
	}
	
	/**
	 * 默认参数
	 */
	var defaults = {
		width: "85",  //按钮宽度，单位px
		text: "上传文件"	//按钮文字
	};
	
	/**常量类*/
	var CONST = {};
	/**绑定到data域上名字*/
	CONST.DATA_OBJ_NAME = "jqFileBtn";
	
	/**
	 * 上传按钮插件
	 * @param {Object} options 选项
	 */
	$.fn.jqFileBtn = function(options) {
		//覆盖默认参数
		options = $.extend(true, {}, defaults, options);
		this.each(function(i, _item) {
			//当前操作的jquery dom对象
			options.thisObj = $(this);
			var item = $(_item);
			var obj = new JqFileBtn(options);
			item.data(CONST.DATA_OBJ_NAME, obj);
			obj.init();
		});
	};
	
	/**
	 * 上传按钮类
	 * @param {Object} options 选项
	 */
	function JqFileBtn(options) {
		var self = this;
		//当前操作的jquery dom 对象
		var thisObj = options.thisObj;
		
		var btn = {};
		/**初始化相关*/
		btn.init = {};
		/**
		 * 按钮初始化
		 */
		btn.init.initBtn = function() {
			var prefix = "btn.init.initBtn-> ";
			//上传框宽度
			var fileWidth = thisObj.width();
			log(prefix + "fileWidth: " + fileWidth);
			var wrapperId = "jqFileBtn_" + parseInt(Math.random() * 100000000);
			var wrapper = 
					'<div id="' + wrapperId + '" style="position: relative; overflow: hidden; width: ' + options.width + 'px; margin: 0 auto;">' +
					'<a class="button" style="margin-left:2px; cursor: pointer;"><span>' + options.text + '</span></a>' +
					'</div>';
			thisObj.parent().append(wrapper);
			thisObj.css({
	                "position": "absolute",
	                "cursor": "pointer",
	                "opacity": "0.0",
					"right": (0) + 'px',
					"top": '-5px',
					"font-size": "30px",
					"z-index": 10
	            })
				.appendTo("#" + wrapperId);
		};
		
		//----公开的属性
		//选项
		self.args = options;
		
		//----公开的方法
		self.init = btn.init.initBtn;
	}
	
})(jQuery);
