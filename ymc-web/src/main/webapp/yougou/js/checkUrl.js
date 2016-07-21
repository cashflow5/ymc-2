

	var validatedURI = {
		www: "www",
		mall: "mall",
		outlets: "outlets",
		flashbuy: "flashbuy",
		style:"style"
	};
	
	var checkYGUrl = function(url){
		if(url=='#'||url==''||url == undefined){
			return true;
		}
		var flag = false;
		if(url){
			
			if(url.indexOf("#")==0){
				 return true;
			}
			if(url.indexOf(".yougou.com") == -1 && url.indexOf(".ygimg.cn") == -1){
				return flag;
				//flag = false;
			}else if(url.indexOf(".ygimg.cn") >=0){
				return true;
			}
			else{
				url = url.substring(0,url.indexOf(".yougou.com")).replace('http://','').replace('https://','');
				url = url.replace('HTTP://','').replace('HTTPS://','');
				if(validatedURI[url]){
					flag = true;
				} 
			} 
		 }
		return  flag;
	};
	
	var numberInput = function(str){
		   
			 var  patrn=/^\d*$/;     
			 if   (patrn.test(str)){   
			          return true;  
			 }else{   
			          return false; 
			 }   
	};
	var deleteTraceCode = function(url) {
		if(url){
			var refIndex = url.indexOf("#ref=");
			if(refIndex != -1){
				url = url.substring(0, refIndex);
			}
			
			var poIndex = url.indexOf("&po=");
			if(poIndex != -1){
				url = url.substring(0, poIndex);
			}
		}
		return url;
	};

