/**
 * 涉及到多栏目的工具类
 * 2011.06.22 修改 过滤重复商品选择
 */
//==========================================================================
var CHOOSE_columnNames = new Array();

//通过栏目索引获取栏目名称
function getColumnNameByIndex(index){
	for(var i = 0 ; i < CHOOSE_columnNames.length ; i++){
		if(index == CHOOSE_columnNames[i].index){
			return CHOOSE_columnNames[i].columnName;
		}
	}
	return null;
}

function getIndexByColumnIndex(index){
	for(var i = 0 ; i < CHOOSE_columnNames.length ; i++){
		if(index == CHOOSE_columnNames[i].index){
			return i;
		}
	}
	return -1;
}
//==========================================================================

//栏目下所有商品的存放集合,集合值为商品id
var MoreColumn_Commodity_data = new Array();

/**
 * 描述：存放已在栏目下的商品
 * cid ：存入商品id
 */
function pushColumnCommodity(cid){
	var result = isExist(cid);
	if(!result){
		MoreColumn_Commodity_data.push(cid);
	}else{
		//alert("已存在");
	}
	return result;
}
/**
 * 描述：检验是否存在cid于集合
 * @param cid 存入商品id
 * @return true:存在 false:不存在
 */
function isExist(cid){
	if(cid == undefined || cid == null){
		return true;
	}
	return (MoreColumn_Commodity_data.indexOf(cid) == -1)?false:true;
}

/**
 * 描述：删除栏目下的商品校验数据
 * @param cid:存入商品id
 * @return
 */
function delColumnCommodity(cid){
	
	if(MoreColumn_Commodity_data.length<=0)
		return;
	
	MoreColumn_Commodity_data = MoreColumn_Commodity_data.del(MoreColumn_Commodity_data.indexOf(cid));
	
}

function removeColumnCommodity(o){
	var trNode = o.parentNode.parentNode;
	var cid = trNode.firstChild.childNodes[1].value;
	//alert(cid);
	if(cid==undefined||cid==null) return;
	delColumnCommodity(cid);
}



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
