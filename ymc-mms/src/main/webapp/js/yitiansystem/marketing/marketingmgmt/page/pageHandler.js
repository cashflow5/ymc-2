function ActPage(data,dataContainerID,pageContainerID){
	this.dataContainerID = dataContainerID;
	this.pageContainerID = pageContainerID;
	//数据集合
	this.data       = data;
	//数据总记录数
	this.dataCount  = data.length;
	//单页记录数
	this.pageSize   = 20;
	//计算页数
	if(this.dataCount==0)
		this.pageCount = 0;
	else
		this.pageCount  = (this.dataCount<=this.pageSize)?1:(this.dataCount+this.pageSize-1)/this.pageSize;
	//当前页索引
	this.pageIndex = 0;
}

//dataConainerID:放入数据的位置ID
function go(obj,pageIndex){
	if(pageIndex<=0){
		pageIndex = 1;
	}else if(pageIndex>=obj.pageSize){
		pageIndex = obj.pageSize;
	}
	obj.pageIndex = pageIndex;
	//改变数据区域与分页区域
	obj._change();
	return [obj.data.splice((pageIndex-1)*obj.pageSize,pageIndex*obj.pageSize),obj.pageIndex];
};

ActPage.prototype._change =function(){
	$("#commoditys_container01").html("");
	//组合数据
	this.buildDataHTML();
	
	var html = '<a href="ap.go('+(this.pageIndex-1)+')">上一页</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="ap.go('+(this.pageIndex+1)+')">下一页</a>，当前第'+this.pageIndex+'/'+this.pageCount+'页，每页'+this.pageSize+'条记录';
	$("#page_").innerHTML = html;
};

ActPage.prototype.buildDataHTML = function(){
	var html2 = '<a href="ap.go('+(this.pageIndex-1)+')">上一页</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="ap.go('+(this.pageIndex+1)+')">下一页</a>，当前第'+this.pageIndex+'/'+this.pageCount+'页，每页'+this.pageSize+'条记录';
	var html='<tr class="search_commodity01"><td colspan="5" id="'+this.pageContainerID+'">'+html2+'</td></tr>';
	
	for(var i = 0 ; i <this.data.length ;i++){
		commodity_id             = this.data[i].id;
		commodity_no             = this.data[i].no;
		commodity_commodityName  = this.data[i].commodityName;
		commodity_publicPrice    = this.data[i].publicPrice;
		
		tmpCommodity = new BaseCommodity(commodity_id,commodity_no,commodity_commodityName,commodity_publicPrice);
			
		pageCommodityDataArray[i] = {"index":i,"commodity":tmpCommodity,"fn":hg};
		
		html+='<tr class="search_commodity01"><td align="center"><input type="checkbox" name="chk_sure_hg" value="'+i+'" id="chk_sure_hg'+i+'"></td><td  align="center">'+commodity_no+'</td><td  align="center">'+commodity_commodityName+'</td><td align="center">'+commodity_publicPrice+'</td><td align="center">10000</td></tr>';
        //html += '<tr onclick="_public_choose_invoke_callBack_commodity('+i+')"><td>'+commodity.data[i].no+'</td><td>'+commodity.data[i].commodityName+'</td><td>￥'+commodity.data[i].publicPrice+'</td></tr>';
	}
	//alert(1);
	var ap1 = new ActPage(commodity.data,"commoditys_container01","page_1");
	html+='<tr class="search_commodity01"><td colspan="5" id="page_">.....</td></tr>';
	
	
	$("#"+this.dataContainerID).append(html);
};