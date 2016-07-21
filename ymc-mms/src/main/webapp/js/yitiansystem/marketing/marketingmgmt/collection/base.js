
//公共方法
Array.prototype.del=function(n) {  //n表示第几项，从0开始算起。
//prototype为对象原型，注意这里为对象增加自定义方法的方法。
  if(n<0)  //如果n<0，则不进行任何操作。
    return this;
  else
    return this.slice(0,n).concat(this.slice(n+1,this.length));
    /**//*
       concat方法：返回一个新数组，这个新数组是由两个或更多数组组合而成的。
       　　　　　　这里就是返回this.slice(0,n)/this.slice(n+1,this.length)
      　　　　　　组成的新数组，这中间，刚好少了第n项。
       slice方法： 返回一个数组的一段，两个参数，分别指定开始和结束的位置。
    */
};
Array.prototype.indexOf = function(val){
	for(var i = 0 ; i < this.length ;i++){
		if(this[i] == val){
			return i;
		}
	}
	return -1;
};

Array.prototype.each = function(fn){
	for(var i = 0 ; i < this.length ;i++){
		fn(this[i],i);
	}
};




/***************************Ajax 公共调用*****************************************/
//[公共方法] 通过三级分类获取商品数据 return:三级分类下的商品集合
function _public_loadLevelCommodityList(structName,pageNo){
	var commodityData = null;
	$.ajax( {
		type : "POST",
		url : BASE_PATH+"/yitiansystem/marketing/marketingmgmt/marketingMgmt/loadLevelCommodityList.sc?pageNo="+pageNo+"&structName="+structName,
		async : false,
		dataType : "json",
		success : function(data) {
			if(data[0]==null){
				alert("未查询到相关记录!");
			}
			commodityData = new Commodity(data[0],data[1],data[2],data[3]);
		}
	});
	return commodityData;
}