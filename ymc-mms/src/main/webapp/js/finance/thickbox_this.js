/*
 * Thickbox 2.0 - One Box To Rule Them All.
 * By Cody Lindley (http://www.codylindley.com)
 * Copyright (c) 2006 cody lindley
 * Licensed under the MIT License:
 *   http://www.opensource.org/licenses/mit-license.php
 * Thickbox is built on top of the very light weight jQuery library.
 */

//on page load call TB_init
$(document).ready(TB_init_this);

function showThickBox_this(t,url,g){
	window.top.tb_show_this(t,url,g);
	return true;
}

//add thickbox to href elements that have a class of .thickbox
 
function TB_init_this(){
	$("a.thickbox").click(function(){
	var t = this.title || this.name || null;
	var g = this.rel || false;
	
	tb_show_this(t,this.href,g);
	
	//修改后
	//window.top.tb_show(t,this.href,g);
	
	this.blur();
	return false;
	});
}

function tb_show_this(caption, url, imageGroup) {//function called when the user clicks on a thickbox link
	reinitifH();
	try {
		if (document.getElementById("TB_HideSelect") == null) {
		$("body").append("<iframe id='TB_HideSelect'></iframe><div id='TB_overlay'></div><div id='TB_window'></div>");
	     	$("#TB_overlay").click(TB_remove_this);   //点击遮盖层时 取消遮盖
		}
		
		if(caption==null){caption=""};
		
		$(window).scroll(TB_position_this);
 		
		TB_overlaySize_this();
		$("body").append("<div id='TB_load'><img src='./images/loadingAnimation.gif' /></div>");
		TB_load_position_this();
		
		var urlString = /\.jpg|\.jpeg|\.png|\.gif|\.html|\.htm|\.php|\.cfm|\.asp|\.aspx|\.jsp|\.jst|\.rb|\.txt|\.bmp/g;
		var urlType = url.toLowerCase().match(urlString);
		
		if(urlType == '.jpg' || urlType == '.jpeg' || urlType == '.png' || urlType == '.gif' || urlType == '.bmp'){//code to show images
				
			TB_PrevCaption = "";
			TB_PrevURL = "";
			TB_PrevHTML = "";
			TB_NextCaption = "";
			TB_NextURL = "";
			TB_NextHTML = "";
			TB_imageCount = "";
			TB_FoundURL = false;
			if(imageGroup){
				TB_TempArray = $("a[@rel="+imageGroup+"]").get();
				for (TB_Counter = 0; ((TB_Counter < TB_TempArray.length) && (TB_NextHTML == "")); TB_Counter++) {
					var urlTypeTemp = TB_TempArray[TB_Counter].href.toLowerCase().match(urlString);
						if (!(TB_TempArray[TB_Counter].href == url)) {						
							if (TB_FoundURL) {
								TB_NextCaption = TB_TempArray[TB_Counter].title;
								TB_NextURL = TB_TempArray[TB_Counter].href;
								TB_NextHTML = "<span id='TB_next'>&nbsp;&nbsp;<a href='#'>Next &gt;</a></span>";
							} else {
								TB_PrevCaption = TB_TempArray[TB_Counter].title;
								TB_PrevURL = TB_TempArray[TB_Counter].href;
								TB_PrevHTML = "<span id='TB_prev'>&nbsp;&nbsp;<a href='#'>&lt; Prev</a></span>";
							}
						} else {
							TB_FoundURL = true;
							TB_imageCount = "Image " + (TB_Counter + 1) +" of "+ (TB_TempArray.length);											
						}
				}
			}

			//imgPreloader = new Image();
			imgPreloader.onload = function(){
			
				imgPreloader.onload = null;
					
				// Resizing large images - orginal by Christian Montoya edited by me.
				var pagesize = TB_getPageSize_this();
				var x = pagesize[0] - 150;
				var y = pagesize[1] - 150;
				var imageWidth = imgPreloader.width;
				var imageHeight = imgPreloader.height;
				if (imageWidth > x) {
					imageHeight = imageHeight * (x / imageWidth); 
					imageWidth = x; 
					if (imageHeight > y) { 
						imageWidth = imageWidth * (y / imageHeight); 
						imageHeight = y; 
					}
				} else if (imageHeight > y) { 
					imageWidth = imageWidth * (y / imageHeight); 
					imageHeight = y; 
					if (imageWidth > x) { 
						imageHeight = imageHeight * (x / imageWidth); 
						imageWidth = x;
					}
				}
				// End Resizing
				
				TB_WIDTH = imageWidth + 30;
				TB_HEIGHT = imageHeight + 60;
				//$("#TB_window").append("<a href='' id='TB_ImageOff' title='Close'><img id='TB_Image' src='"+url+"' width='"+imageWidth+"' height='"+imageHeight+"' alt='"+caption+"'/></a>" + "<div id='TB_caption'>"+caption+"<div id='TB_secondLine'>" + TB_imageCount + TB_PrevHTML + TB_NextHTML + "</div></div><div id='TB_closeWindow'><a href='#' id='TB_closeWindowButton' title='Close or Escape Button'>close</a></div>"); 		
				$("#TB_window").append("<a href='' id='TB_ImageOff' title='Close'><img id='TB_Image' src='"+url+"' width='"+imageWidth+"' height='"+imageHeight+"' alt='"+caption+"'/></a>" + "<div id='TB_caption'>"+caption+"<div id='TB_secondLine'>" + TB_imageCount + TB_PrevHTML + TB_NextHTML + "</div></div>"); 		
				
				$("#TB_closeWindowButton").click(TB_remove_this);
				
				if (!(TB_PrevHTML == "")) {
					function goPrev(){
						if($(document).unclick(goPrev)){$(document).unclick(goPrev)};
						$("#TB_window").remove();
						$("body").append("<div id='TB_window'></div>");
					//	$(document).unkeyup();
						tb_show_this(TB_PrevCaption, TB_PrevURL, imageGroup);
						return false;	
					}
				
					$("#TB_prev").click(goPrev);
					
					$(document).keyup( function(e){ var key = e.keyCode; if(key == 37){goPrev()} });
				}
				
				
				
				if (!(TB_NextHTML == "")) {		
					function goNext(){
						$("#TB_window").remove();
						$("body").append("<div id='TB_window'></div>");
					//	$(document).unkeyup();
						tb_show(TB_NextCaption, TB_NextURL, imageGroup);				
						return false;	
					}
					
					$("#TB_next").click(goNext);
				
					$(document).keyup( function(e){ var key = e.keyCode; if(key == 39){goNext()} });
				}
				
				TB_position_this();
				$("#TB_load").remove();
				$("#TB_ImageOff").click(TB_remove_this);
				$("#TB_window").css({display:"block"}); //for safari using css instead of show
			};
	  
			imgPreloader.src = url;
		}
		
		if(urlType=='.ftl'||urlType=='.htm'||urlType=='.html'||urlType=='.php'||urlType=='.asp'||urlType=='.aspx'||urlType=='.jsp'||urlType=='.jst'||urlType=='.rb'||urlType=='.txt'||urlType=='.cfm' || (url.indexOf('TB_inline') != -1) || (url.indexOf('TB_iframe') != -1) ){//code to show html pages
			
			var queryString = url.replace(/^[^\?]+\??/,'');
			var params = TB_parseQuery_this( queryString );
			/**TB_HEIGHT_SELF = 450;
			if(params['height']==undefined) {
				if($.browser.msie){
					TB_HEIGHT = document.compatMode == "CSS1Compat"? document.documentElement.clientHeight - 130 : document.body.clientHeight - 130;
					TB_WIDTH = document.compatMode == "CSS1Compat"? document.documentElement.clientWidth - 50 : document.body.clientWidth - 130;
				}else{
					TB_HEIGHT = self.innerHeight - 130;
					TB_WIDTH = self.innerWidth - 50;
				} 
				TB_HEIGHT = Math.min(TB_HEIGHT_SELF, TB_HEIGHT);
			} else {
				if($.browser.msie){
					TB_WIDTH = document.compatMode == "CSS1Compat"? document.documentElement.clientWidth - 50 : document.body.clientWidth - 130;
				}else{
					TB_WIDTH = self.innerWidth - 50;
				} 
				TB_HEIGHT = (params['height']*1) + 40;
			}**/
			//TB_HEIGHT = document.body.clientHeight - 130;
			//TB_WIDTH = document.body.clientWidth - 50;
			
			//TB_HEIGHT = window.screen.availHeight;
			//TB_WIDTH = window.screen.availWidth;
			
			if($.browser.msie){
				TB_HEIGHT = document.compatMode == "CSS1Compat"? document.documentElement.clientHeight - 130 : document.body.clientHeight - 130;
				TB_WIDTH = document.compatMode == "CSS1Compat"? document.documentElement.clientWidth - 50 : document.body.clientWidth - 130;
			}else{
				TB_HEIGHT = self.innerHeight - 130;
				TB_WIDTH = self.innerWidth - 50;
			} 
			//alert(TB_WIDTH+','+TB_HEIGHT);
			if(params['height']!=undefined){
				TB_HEIGHT = (params['height']*1) + 40;
			}
			if(params['width']!=undefined){
				TB_WIDTH = (params['width']*1) + 40;
			}
			
			ajaxContentW = TB_WIDTH - 30;
			ajaxContentH = TB_HEIGHT - 45;
			

			if(url.indexOf('TB_iframe') != -1){				
					urlNoQuery = url.substr(0,TB_strpos_this(url, "?"));			
					$("#TB_window").append("<div id='TB_title'><div id='TB_ajaxWindowTitle'>"+caption+"</div><div id='TB_closeAjaxWindow'><a href='#' id='TB_closeWindowButton'>关 闭</a></div></div><iframe name='TB_iframeContent' src='"+url+"' id='TB_iframeContent' style='width:"+(ajaxContentW + 30)+"px;height:"+ajaxContentH+"px;'></iframe>");
//					$("#TB_window").append("<div id='TB_title'><div id='TB_ajaxWindowTitle'>"+caption+"</div></div><iframe src='"+urlNoQuery+"' id='TB_iframeContent' style='width:"+(ajaxContentW + 30)+"px;height:"+(ajaxContentH + 18)+"px;'></iframe>");
					
				}else{
					$("#TB_window").append("<div id='TB_title'><div id='TB_ajaxWindowTitle'>"+caption+"</div><div id='TB_closeAjaxWindow'><a href='#' id='TB_closeWindowButton'>close</a></div></div><div id='TB_ajaxContent' style='width:"+ajaxContentW+"px;height:"+ajaxContentH+"px;'></div>");
			}
					
			$("#TB_closeWindowButton").click(TB_remove_this);
			
				if(url.indexOf('TB_inline') != -1){	
					$("#TB_ajaxContent").html($('#' + params['inlineId']).html());
					TB_position_this();
					$("#TB_load").remove();
					$("#TB_window").css({display:"block"}); 
				}else if(url.indexOf('TB_iframe') != -1){
					TB_position_this();
					$("#TB_load").remove();
					$("#TB_window").css({display:"block"}); 
				}else{
					$("#TB_ajaxContent").load(url, function(){
						TB_position_this();
						$("#TB_load").remove();
						$("#TB_window").css({display:"block"}); 
					});
				}
			
		}
		TB_position_this();
		$(window).resize();
		
	} catch(e) {
		alert( e );
	}
}

//helper functions below

function TB_remove_this() {
	$("#TB_window").fadeOut("fast",function(){$('#TB_window,#TB_overlay,#TB_HideSelect').remove();});
	$("#TB_load").remove();
//	$(document).unkeyup();
	return false;
}

function TB_position_this() {
	var pagesize = TB_getPageSize_this();	
	var arrayPageScroll = TB_getPageScrollTop_this();
	/**var TB_HEIGHT_CUSTOMER = TB_HEIGHT;
	
	if($.browser.msie){
		TB_HEIGHT_CUSTOMER = document.compatMode == "CSS1Compat"? document.documentElement.clientHeight - 130 : document.body.clientHeight - 130;
	}else{
		TB_HEIGHT_CUSTOMER = self.innerHeight - 130;
	} 
	
	$("#TB_window").css({width:TB_WIDTH+"px",height:TB_HEIGHT+"px",left: ((pagesize[0] - TB_WIDTH)/2)+"px", top: (arrayPageScroll[1] + ((pagesize[1]-TB_HEIGHT_CUSTOMER)/2))+"px" });
	**/	
	$("#TB_window").css({width:TB_WIDTH+"px",height:TB_HEIGHT+"px",left: ((pagesize[0] - TB_WIDTH)/2)+"px", top: (arrayPageScroll[1] + ((pagesize[1]-TB_HEIGHT)/2))+"px" });
	TB_overlaySize_this();
}

function TB_overlaySize_this(){
	if (window.innerHeight && window.scrollMaxY) {	
		yScroll = window.innerHeight + window.scrollMaxY;
	} else if (document.body.scrollHeight > document.body.offsetHeight){ // all but Explorer Mac
		yScroll = document.body.scrollHeight;
	} else { // Explorer Mac...would also work in Explorer 6 Strict, Mozilla and Safari
		yScroll = document.body.offsetHeight;
  	}
	$("#TB_overlay").css("height",yScroll +"px");
}

function TB_load_position_this() {
	var pagesize = TB_getPageSize_this();
	var arrayPageScroll = TB_getPageScrollTop_this();

	$("#TB_load")
	.css({left: ((pagesize[0] - 100)/2)+"px", top: (arrayPageScroll[1] + ((pagesize[1]-100)/2))+"px" })
	.css({display:"block"});
}

function TB_parseQuery_this ( query ) {
   var Params = new Object ();
   if ( ! query ) return Params; // return empty object
   var Pairs = query.split(/[;&]/);
   for ( var i = 0; i < Pairs.length; i++ ) {
      var KeyVal = Pairs[i].split('=');
      if ( ! KeyVal || KeyVal.length != 2 ) continue;
      var key = unescape( KeyVal[0] );
      var val = unescape( KeyVal[1] );
      val = val.replace(/\+/g, ' ');
      Params[key] = val;
   }
   return Params;
}

function TB_getPageScrollTop_this(){
	var yScrolltop;
	if (self.pageYOffset) {
		yScrolltop = self.pageYOffset;
	} else if (document.documentElement && document.documentElement.scrollTop){	 // Explorer 6 Strict
		yScrolltop = document.documentElement.scrollTop;
	} else if (document.body) {// all other Explorers
		yScrolltop = document.body.scrollTop;
	}
	arrayPageScroll = new Array('',yScrolltop) 
	return arrayPageScroll;
}

function TB_getPageSize_this(){
	var de = document.documentElement;
	var w = window.clientWidth || self.clientWidth || (de&&de.clientWidth) || document.body.clientWidth;
	var h = window.clientWidth || self.clientWidth || (de&&de.clientHeight) || document.body.clientHeight;
	
	arrayPageSize = new Array(w,h);
	return arrayPageSize;
}

function TB_strpos_this(str, ch) {
for (var i = 0; i < str.length; i++)
if (str.substring(i, i+1) == ch) return i;
return -1;
}

