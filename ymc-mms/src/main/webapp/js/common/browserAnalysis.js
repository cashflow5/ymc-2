 $(function(){
    
	 var Sys = {};

    var ua = navigator.userAgent.toLowerCase();

    if (window.ActiveXObject)

        Sys.ie = ua.match(/msie ([\d.]+)/)[1]

    else if (document.getBoxObjectFor)

        Sys.firefox = ua.match(/firefox\/([\d.]+)/)[1]

    else if (window.MessageEvent && !document.getBoxObjectFor)

        Sys.chrome = ua.match(/chrome\/([\d.]+)/)[1]

    else if (window.opera)

        Sys.opera = ua.match(/opera.([\d.]+)/)[1]

    else if (window.openDatabase)

        Sys.safari = ua.match(/version\/([\d.]+)/)[1];
    
    // 检验浏览器类型，提示用户
    if( !(Sys.firefox || Sys.chrome) ){
    	forbiddenFunc();
    }
        
     /*   
        var type = getOs();
    	if( type!="Safari" && type!="Firefox" ){
    		forbiddenFunc();
    	}*/
});
 
 function getOs()  
 {  
     var OsObject = "";  
    if(navigator.userAgent.indexOf("MSIE")>0) {  
         return "MSIE";  
    }  
    if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){  
         return "Firefox";  
    }  
    if(isSafari=navigator.userAgent.indexOf("Safari")>0) {  
         return "Safari";  
    }   
    if(isCamino=navigator.userAgent.indexOf("Camino")>0){  
         return "Camino";  
    }  
    if(isMozilla=navigator.userAgent.indexOf("Gecko/")>0){  
         return "Gecko";  
    }  
     
 } 