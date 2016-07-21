/**
 * 商品基本对象
 * @param id 商品id
 * @param no 商品编码
 * @param commodityName 商品名称
 * @param publicPrice 商品市场价
 * @return
 */
function BaseCommodity(id,no,commodityName,commodity_publicPrice,commodity_Price2,commodity_catName){
	this.id = id;
	this.no = no;
	this.commodityName = commodityName;
	this.commodity_publicPrice = commodity_publicPrice;
	this.commodity_Price2 = commodity_Price2;//市场价
	this.commodity_catName = commodity_catName;
}


/**
 * 商品分页JS对象
 * @param data 条件查询后的商品集合
 * @param pageNo 当前页No
 * @param pageCount 总页数
 * @param pageSize 每页总记录数
 * @return
 */
function Commodity(data,pageNo,pageCount,pageSize){
	this.data      = data;
	this.pageNo    = pageNo;
	this.pageCount = pageCount;
	this.pageSize  = pageSize;
}



