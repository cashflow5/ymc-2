var ymc_common = {};
ymc_common.loading = function(type,msg){
	if(msg==null||msg==""){
		msg = "请稍后,正在加载......";
	}
	var  body_width =  document.body.clientWidth;
	var  body_height= $(window).height();
	//展示loading
	if(type=="show"){
		var myload = $("<div id='myload' style='border:2px solid #95B8E7;display:inline-block;padding:10px 8px;position:fixed;z-index:999999999;top:0px;left:0px;background:#ffffff;font-size:12px'>"+
				"<div style='float:left;'></div>"+
				"<div style='float:left;display:inline-block;margin-top:2px;margin-left:5px;'>"+msg+"</div>"+
		 "</div>").appendTo($("body"));
		 var myloadwidth = myload.width();
		 var myloadheight = myload.height();
		 myload.css({"left":(body_width-myloadwidth)/2,"top":(body_height-myloadheight)/2});
		 $("<div id='remote_load' style='position:fixed;width:100%;height:"+body_height+"px;z-index:99999999;top:0px;left:0px;background-color: #ccc;opacity: 0.3;filter: alpha(opacity = 30);'></div>").appendTo($("body"));
	}else{
		$("#myload").remove();
		$("#remote_load").remove();
	}
};

ymc_common.loadingInWindow = function(type,msg,id){
	if(msg==null||msg==""){
		msg = "请稍后,正在登录中......";
	}
	var  body_width = $("#"+id).width();
	var  body_height= $("#"+id).height();
	//展示loading
	if(type=="show"){
		var body_top = $("#"+id).offset().top;
		var body_left = $("#"+id).offset().left;
		var myload = $("<div id='myload' style='border:2px solid #95B8E7;display:inline-block;padding:10px 8px;position:fixed;z-index:999999999;top:"+body_top+"px;left:"+body_left+"px;background:#ffffff;font-size:12px'>"+
				"<div style='float:left;'></div>"+
				"<div style='float:left;display:inline-block;margin-top:2px;margin-left:5px;'>"+msg+"</div>"+
		 "</div>").appendTo($("body"));
		 var myloadwidth = myload.width();
		 var myloadheight = myload.height();
		 myload.css({"left":body_left+(body_width-myloadwidth)/2,"top":body_top+(body_height-myloadheight)/2});
		 $("<div id='remote_load' style='position:fixed;width:"+body_width+"px;height:"+body_height+"px;z-index:99999999;top:"+body_top+"px;left:"+body_left+"px;background-color: #ccc;opacity: 0.3;filter: alpha(opacity = 30);'></div>").appendTo($("body"));
	}else{
		$("#myload").remove();
		$("#remote_load").remove();
	}
};

//格式化时间
Date.prototype.format = function(format) {
	var o = {
		"M+": this.getMonth() + 1,
		//month
		"d+": this.getDate(),
		//day
		"h+": this.getHours(),
		//hour
		"m+": this.getMinutes(),
		//minute
		"s+": this.getSeconds(),
		//second
		"q+": Math.floor((this.getMonth() + 3) / 3),
		//quarter
		"S": this.getMilliseconds() //millisecond
	};
	if (/(y+)/.test(format)) format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for (var k in o) if (new RegExp("(" + k + ")").test(format)) format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
	return format;
};