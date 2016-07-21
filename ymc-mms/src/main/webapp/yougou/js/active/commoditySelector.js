if(typeof BasePath == "undefined") {
	BasePath = "/bms";
}
var commoditySelector={
	open:function(options,callback){
		var defaults = {
        	title:'商品选择器',
        	width:1120,
        	height:692,
        	closabled:true,
        	url:BasePath+'/yitiansystem/chain/active/commodity/queryCommodityList.sc'
	    };
		var opts = $.extend(defaults, options);
		// 弹出内容框
    	openwindow(opts.url,opts.width,opts.height,opts.title,function(){
	    	// 获取内容体
	    	var document = this.iframe.contentWindow.document;
	    	// 通过约定格式获取选定数据
	    	var str="";
			var tb=$(document.getElementById("tbody"));
			str='[';
			var checkObj=tb.find("tr input:checked");
			checkObj.each(function(index, element) {
			   str+='{';
			   var vals=$(this).val().split(';');
			   var val="";
			   for(var i=0;i<vals.length;i++)
			   {
				   val='"id":"'+vals[0]+'","commodityName":"'+vals[1]+'","picSmall":"'+vals[2]+'","no":"'+vals[3]
				   +'","supplierCode":"'+vals[4]+'","styleNo":"'+vals[5]+'",'
				   +'"catName":"'+vals[6]+'","publicPrice":"'+vals[7]+'","salePrice":"'+vals[8]+'","specName":"'+vals[9]+'",'
				   +'"commodityStatus":"'+vals[10]+'","inventoryNumber":"'+vals[11]+'","brandName":"'+vals[12]+'","costPrice":"'+vals[13]+'"';
			   }
			   str+=val+'}';
			   if(index!=checkObj.length-1)
			   	{
					str+=',';
				}
	        });
			str+=']';

	    	// 调用回调函数
	    	callback(eval(str));
	    	
	    	// 关闭弹出框
	    	if(opts.closabled){
	    		this.close();
	    	}
	    	
			return false;
	    });
	}
};