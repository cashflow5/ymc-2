/*时间倒计时*/
(function($){
	$.fn.countDown=function(options){
		var settings = { 
			type:1
		}; 
		settings = $.extend(settings, options || {});
		var _type=settings.type;
		return this.each(function(){
			var _this=$(this);
			var _leftSec=_this.attr('endtime');;
			var _t=null;
			var d,h,m,s,ms;
			function countdown(){
				if(_leftSec==-1){
					_this.html('');
					return;
				}
				if(_leftSec==0){
					_this.html('<font color="red">已到期</font>');
					return;
				}
				_leftSec-=100;
				d=Math.floor(_leftSec/(1000*60*60*24));
				h=Math.floor((_leftSec/(1000*60*60))%24);
				m=Math.floor((_leftSec/(1000*60))%60);
				s=Math.floor((_leftSec/1000)%60);
				if(m<10){
					m="0"+m;
				}
				if(s<10){
					s="0"+s;
				}
				switch (_type){
					case 0:
					_this.html('<em>'+h+'</em>小时<em>'+m+'</em>分钟<em>'+s+'</em>秒');
					if(_leftSec<=0) {
						clearTimeout(_t);
						location.reload();
					}
					break;
					case 1:
					_this.html('<font color="red">'+d+'</font>天 <font color="red">'+h+'</font>小时 <font color="red">'+m+'</font>分钟 <font color="red">'+s+'</font>秒');
					if(d<=0){
						_this.html('<font color="red">'+h+'</font>小时 <font color="red">'+m+'</font>分钟 <font color="red">'+s+'</font>秒');
					}
				}
				if(_leftSec>0){
					_t=setTimeout(countdown,100);	
				}
			}
			countdown();
		});
	}
})(jQuery);