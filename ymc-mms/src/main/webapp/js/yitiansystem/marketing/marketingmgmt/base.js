

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


//分页查询出的Commodity数据集合
var pageCommodityDataArray = new Array();

//所有品牌Brand数据集合[A]
var brandCommodityDataArray = new Array();
//存放页面已经存在的Brand数据集合[A]
var extisCommodityDataArray = new Array();

//促销放置的单品商品信息
var Commodity_SingleDataArray = null;

//促销放置的多品促销商品信息
var Commodity_MultiDataArray = null;

//当前栏目的累计数
var item_index = 1;
//当前选择某栏目的添加商品index
var item_add_index = -1;

//当前多级优惠的级别(注意：当选择普通优惠时当前值为0)
var prefereLevel = 0;

$(document).ready(function(){

	//初始化日期
	//$("#datepicker_start").dateDisplay('${BasePath}');
	//$("#datepicker_end").dateDisplay('${BasePath}');
	
	//注册关闭div事件
	$(".close").click(function(){
		var current=$(".close").index($(this));
		$(".flick").eq(current).css('display','none');
	})
	
	
	$('#rootCattegory01').change( function() {
		//选中项的value值
			var selValue = $(this).children('option:selected').val();
			var value = selValue.split(";");
			// 选中项的text值
			var selText = $(this).children('option:selected').text();
			if (selValue != "0") {
				loadLevel(value[0], "secondCategory01");
			} else {
				$("#secondCategory01").empty();// 清空下来框
				$("#secondCategory01").append("<option value='0'>二级分类</option>");
			}
			$("#threeCategory01").empty();// 清空下来框
			$("#threeCategory01").append("<option value='0'>三级分类</option>");
		})
	$('#secondCategory01').change( function() {
		//选中项的value值
			var selValue = $(this).children('option:selected').val();
			var value = selValue.split(";");
			// 选中项的text值
			var selText = $(this).children('option:selected').text();
			if (selValue != "0") {
				loadLevel(value[0], "threeCategory01");
			} else {
				$("#threeCategory01").empty();// 清空下来框
				$("#threeCategory01").append("<option value='0'>三级分类</option>");
			}
		})
		$("#threeCategory01").change(function(){
			pageCommodityDataArray = new Array();
			
			var selValue = $(this).children('option:selected').val();
			var structName = selValue.split(";")[0];
			
			//加载三级分类的商品
			var commodity = _public_loadLevelCommodityList(structName,1);
			if(commodity.data != null && commodity.data.length >0){
				var hg = new HG_commodity();
				var html = '<table class="addtab tcenter" width="100%" cellpadding="1" cellspacing="1">'+
			               '<tr><th width="15%">编码</th><th width="45%">商品名称</th><th width="15%">市场价格</th></tr>';
				  var tmpCommodity = null;
			     var commodity_id,commodity_no,commodity_commodityName,commodity_publicPrice;
				for(var i = 0 ; i <commodity.data.length ;i++){
					commodity_id             = commodity.data[0].id;
					commodity_no             = commodity.data[i].no;
					commodity_commodityName  = commodity.data[i].commodityName;
					commodity_publicPrice    = commodity.data[i].salePrice;
					
					tmpCommodity = new BaseCommodity(commodity_id,commodity_no,commodity_commodityName,commodity_publicPrice);
						
					pageCommodityDataArray[i] = {"index":i,"commodity":tmpCommodity,"fn":hg};
	                html += '<tr onclick="_public_choose_invoke_callBack_commodity('+i+')"><td>'+commodity.data[i].no+'</td><td>'+commodity.data[i].commodityName+'</td><td>￥'+commodity.data[i].publicPrice+'</td></tr>';
				}
				$("#commoditys_container").html(html+'</table>');
			}
		});
	
	//-----------------------------------------------------------------第二次三级分类
	$('#rootCattegory02').change( function() {
	//选中项的value值
		var selValue = $(this).children('option:selected').val();
		var value = selValue.split(";");
		// 选中项的text值
		var selText = $(this).children('option:selected').text();
		if (selValue != "0") {
			loadLevel(value[0], "secondCategory02");
		} else {
			$("#secondCategory02").empty();// 清空下来框
			$("#secondCategory02").append("<option value='0'>二级分类</option>");
		}
		$("#threeCategory02").empty();// 清空下来框
		$("#threeCategory02").append("<option value='0'>三级分类</option>");
	})
	$('#secondCategory02').change( function() {
	//选中项的value值
		var selValue = $(this).children('option:selected').val();
		var value = selValue.split(";");
		// 选中项的text值
		var selText = $(this).children('option:selected').text();
		if (selValue != "0") {
			loadLevel(value[0], "threeCategory02");
		} else {
			$("#threeCategory02").empty();// 清空下来框
			$("#threeCategory02").append("<option value='0'>三级分类</option>");
		}
	})
	
	
	//-----------[单品促销-分类选择]
	$('#rootCattegory03').change( function() {
		//选中项的value值
			var selValue = $(this).children('option:selected').val();
			var value = selValue.split(";");
			// 选中项的text值
			var selText = $(this).children('option:selected').text();
			if (selValue != "0") {
				loadLevel(value[0], "secondCategory03");
			} else {
				$("#secondCategory03").empty();// 清空下来框
				$("#secondCategory03").append("<option value='0'>二级分类</option>");
			}
			$("#threeCategory03").empty();// 清空下来框
			$("#threeCategory03").append("<option value='0'>三级分类</option>");
		})
	$('#secondCategory03').change( function() {
		//选中项的value值
			var selValue = $(this).children('option:selected').val();
			var value = selValue.split(";");
			// 选中项的text值
			var selText = $(this).children('option:selected').text();
			if (selValue != "0") {
				loadLevel(value[0], "threeCategory03");
			} else {
				$("#threeCategory03").empty();// 清空下来框
				$("#threeCategory03").append("<option value='0'>三级分类</option>");
			}
		})
		$("#threeCategory03").change(function(){
			Commodity_SingleDataArray = new Array();
			
			var selValue = $(this).children('option:selected').val();
			var structName = selValue.split(";")[0];
			
			//加载三级分类的商品
			var commodity = _public_loadLevelCommodityList(structName,1);
			if(commodity.data != null && commodity.data.length >0){
				var html = '<table class="addtab tcenter" width="100%" cellpadding="1" cellspacing="1">'+
			               '<tr><th width="15%">编码</th><th width="45%">商品名称</th><th width="15%">市场价格</th></tr>';
			     pageCommodityDataArray = new Array();
			     var commodity_id,commodity_no,commodity_commodityName,commodity_publicPrice;
				for(var i = 0 ; i <commodity.data.length ;i++){
					commodity_id             = commodity.data[0].id;
					commodity_no             = commodity.data[i].no;
					commodity_commodityName  = commodity.data[i].commodityName;
					commodity_publicPrice    = commodity.data[i].publicPrice;
					
					var tmpCommodity = new BaseCommodity(commodity_id,commodity_no,commodity_commodityName,commodity_publicPrice);
					Commodity_SingleDataArray.push(tmpCommodity);
								
	                html += '<tr onclick="_public_choose_invoke_callBack_commodity_Method('+i+',\'single\')"><td>'+commodity.data[i].no+'</td><td>'+commodity.data[i].commodityName+'</td><td>￥'+commodity.data[i].publicPrice+'</td></tr>';
				}
				$("#commoditys_container02").html(html+'</table>');
			}
			
		});
	//-----------[多品促销-分类选择]
	$('#rootCattegory04').change( function() {
		//选中项的value值
			var selValue = $(this).children('option:selected').val();
			var value = selValue.split(";");
			// 选中项的text值
			var selText = $(this).children('option:selected').text();
			if (selValue != "0") {
				loadLevel(value[0], "secondCategory04");
			} else {
				$("#secondCategory04").empty();// 清空下来框
				$("#secondCategory04").append("<option value='0'>二级分类</option>");
			}
			$("#threeCategory04").empty();// 清空下来框
			$("#threeCategory04").append("<option value='0'>三级分类</option>");
		})
	$('#secondCategory04').change( function() {
		//选中项的value值
			var selValue = $(this).children('option:selected').val();
			var value = selValue.split(";");
			// 选中项的text值
			var selText = $(this).children('option:selected').text();
			if (selValue != "0") {
				loadLevel(value[0], "threeCategory04");
			} else {
				$("#threeCategory04").empty();// 清空下来框
				$("#threeCategory04").append("<option value='0'>三级分类</option>");
			}
		})
		$("#threeCategory04").change(function(){
			Commodity_SingleDataArray = new Array();
			
			var selValue = $(this).children('option:selected').val();
			var structName = selValue.split(";")[0];
			
			//加载三级分类的商品
			var commodity = _public_loadLevelCommodityList(structName,1);
			if(commodity.data != null && commodity.data.length >0){
				var html = '<table class="addtab tcenter" width="100%" cellpadding="1" cellspacing="1">'+
			               '<tr><th width="15%">编码</th><th width="45%">商品名称</th><th width="15%">市场价格</th></tr>';
				Commodity_MultiDataArray = new Array();
			     var commodity_id,commodity_no,commodity_commodityName,commodity_publicPrice;
				for(var i = 0 ; i <commodity.data.length ;i++){
					commodity_id             = commodity.data[0].id;
					commodity_no             = commodity.data[i].no;
					commodity_commodityName  = commodity.data[i].commodityName;
					commodity_publicPrice    = commodity.data[i].publicPrice;
					
					var tmpCommodity = new BaseCommodity(commodity_id,commodity_no,commodity_commodityName,commodity_publicPrice);
					Commodity_MultiDataArray.push(tmpCommodity);
								
	                html += '<tr onclick="_public_choose_invoke_callBack_commodity_Method('+i+',\'multi\')"><td>'+commodity.data[i].no+'</td><td>'+commodity.data[i].commodityName+'</td><td>￥'+commodity.data[i].publicPrice+'</td></tr>';
				}
				$("#commoditys_container03").html(html+'</table>');
			}
			
		});
		
});


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
