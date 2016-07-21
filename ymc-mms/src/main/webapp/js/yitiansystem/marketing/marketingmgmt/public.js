

//显示某个栏目下的商品搜索Div
function showColumnItem(columnIndex){
	$("#column_item"+columnIndex).show();
	$("#column_item"+columnIndex+" .product_search_box").show();
}

function closeColumnItem(columnIndex){
	//初始化栏目中所有东西
	$("#column_item"+columnIndex).hide();
}








//[公共方法] 选择商品调用回调方法
function _public_choose_invoke_callBack_commodity(i){
	//处理回调
	var commodity = pageCommodityDataArray[i].commodity;
	var fn = pageCommodityDataArray[i].fn;
	//开始回调相应处理函数
	fn.callable_chooseCommodity(commodity);
	//关闭层
	$("#catb2c_dialog").css('display','none');
}

//[公共方法] 选择
function _public_choose_invoke_callBack_brand(i){
	brandCommodityDataArray[i].fn.callable_chooseBrand(brandCommodityDataArray[i].id,brandCommodityDataArray[i].brandName);
}

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

//[公共方法] 促销选择商品insert到表格中，可以单品或者多品促销使用
function _public_choose_invoke_callBack_commodity_Method(index,type){
	var contrainerID = "#commodity_"+type+"_container";
	var html = '';
	var commodity = null;
	if(type == "single"){
		commodity = Commodity_SingleDataArray[index];
		//alert(commodity.id+"---");
		html += '<tr><td class="tleft" width="40%">'+commodity.no+'</td><td class="tleft" width="50%">'+commodity.commodityName+'</td><td width="10%"><a href="#">删除</a></td></tr>';
		$(contrainerID).append(html);
	}else if(type == "multi"){
		//alert(item_add_index);
		//alert(110);
		commodity = Commodity_MultiDataArray[index];
		
		html += '<tr><td class="tleft" width="40%">'+commodity.no+'</td><td class="tleft" width="50%">'+commodity.commodityName+'</td><td width="10%"><a href="#">删除</a></td></tr>';
		//alert(contrainerID+item_add_index);
		$(contrainerID+item_add_index).append(html);
	}
	$("#commodity_"+type+"_dialog").css('display','none');
}


//[公共方法] 促销多品选择商品插入表格中
function _public_column_choose_invoke_callBack_commodity_Method(columnIndex,index){
//CHOOSE_column
	
}


//[扩展方法] 扩展Array原生方法del =>eg : arr = arr.del(0); =>参数n:下标
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

//[扩展方法] 扩展Array原生方法del =>eg : arr = arr.indexOf("a"); =>参数val:数组内的值
Array.prototype.indexOf = function(val){
	for(var i = 0 ; i < this.length ;i++){
		if(this[i] == val){
			return i;
		}
	}
	return -1;
};


//[扩展方法] 扩展Array原生方法del =>eg : arr.each(function(val,index){ alert(val+'-'+index);  }); =>参数fn:回调函数
Array.prototype.each = function(fn){
	for(var i = 0 ; i < this.length ;i++){
		fn(this[i],i);
	}
};


//------------------------------------【UI效果】

//显示多级优惠条件、内容
function showMoreBank(){
	$("#more_bank_inputs1").show();
	$("#more_bank_inputs2").show();
}

//隐藏多级优惠条件、内容
function hideMoreBank(){
	$("#more_bank_inputs1").hide();
	$("#more_bank_inputs2").hide();
}

function deletecolumn(o,i){
	CHOOSE_columnNames = CHOOSE_columnNames.del(getIndexByColumnIndex(i));
	var column_id="column_list_table_"+i;
	var x=document.getElementById(column_id).getElementsByTagName("tr").length-1;
	
	//过滤重复商品
	removeColumnCommodity(o);
	
	o.parentNode.parentNode.parentNode.removeChild(o.parentNode.parentNode);
	reinitifH();
	if(x==1){$("#"+column_id).parent().hide();reinitifH();}
}
function deletecolumn2(o,v1,v2){
	o.parentNode.parentNode.parentNode.removeChild(o.parentNode.parentNode);
	reinitifH();
	if(v1&&v2&&v1!=null&&v2!=null){
		orderAmtSpace = orderAmtSpace.del(orderAmtSpace.indexOf(v1+"_"+v2));
		$("#form"+v1+"_"+v2).remove();
	}
}

function ShowConfirm(message){
	art.dialog.confirm(message,function(topWin){
		alert('注销操作');
		this.close();
	},
	function(){
		this.close();
	});
}