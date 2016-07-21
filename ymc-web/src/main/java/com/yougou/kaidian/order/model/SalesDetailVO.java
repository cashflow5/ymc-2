package com.yougou.kaidian.order.model;
/**
 * @directions:销售明细 vo
 * @author： daixiaowei
 * @create： 2012-3-15 下午01:49:24
 * @history：
 * @version:
 */
public class SalesDetailVO {
	
	/**
	 * 子订单编号
	 */
	private String orderSubId;
	/**
	 * 发货时间
	 */
	private String shipTime;
	/**
	 * 下单时间
	 */
	private String createTime;
	/**
	 * 子订单号
	 */
	private String orderSubNo;
	/**
	 * 	商家款色编码
	 */
	private String supplierCode;
	/**
	 * 商家货品条码 等价于 商家货品编码
	 */
	private String thirdPartyCode;
	/**
	 * 优购货品条码 
	 */
	private String insideCode;
	/**
	 * 商品名称
	 */
	private String commodityName;
	/**
	 * 商品规格
	 */
	private String commoditySpecificationStr;
	/**
	 * 发货数量
	 */
	private Integer outGoods;
	/**
	 * 售后退货数量
	 */
	private Integer rejectionGoods;
	/**
	 * 拒收退货数量
	 */
	private Integer returnGoods;
	
	
	
	/**
	 * 货品编码
	 * @return
	 */
	private String prodNo;
	/**
	 * 发货时间 开始
	 * @return
	 */
	private String shipTimeStart;
	/**
	 * 发货时间 结束
	 * @return
	 */
	private String shipTimeEnd;
	/**
	 * 下单时间 开始
	 * @return
	 */
	private String createTimeStart;
	/**
	 * 下单时间 结束
	 * @return
	 */
	private String createTimeEnd;
	
	
	public String getShipTimeStart() {
		return shipTimeStart;
	}
	public void setShipTimeStart(String shipTimeStart) {
		this.shipTimeStart = shipTimeStart;
	}
	public String getShipTimeEnd() {
		return shipTimeEnd;
	}
	public void setShipTimeEnd(String shipTimeEnd) {
		this.shipTimeEnd = shipTimeEnd;
	}
	public String getCreateTimeStart() {
		return createTimeStart;
	}
	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}
	public String getCreateTimeEnd() {
		return createTimeEnd;
	}
	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}
	public String getProdNo() {
		return prodNo;
	}
	public void setProdNo(String prodNo) {
		this.prodNo = prodNo;
	}
	public String getOrderSubId() {
		return orderSubId;
	}
	public void setOrderSubId(String orderSubId) {
		this.orderSubId = orderSubId;
	}
	public String getShipTime() {
		return shipTime;
	}
	public void setShipTime(String shipTime) {
		this.shipTime = shipTime;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getOrderSubNo() {
		return orderSubNo;
	}
	public void setOrderSubNo(String orderSubNo) {
		this.orderSubNo = orderSubNo;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getThirdPartyCode() {
		return thirdPartyCode;
	}
	public void setThirdPartyCode(String thirdPartyCode) {
		this.thirdPartyCode = thirdPartyCode;
	}
	public String getInsideCode() {
		return insideCode;
	}
	public void setInsideCode(String insideCode) {
		this.insideCode = insideCode;
	}
	public String getCommodityName() {
		return commodityName;
	}
	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}
	public String getCommoditySpecificationStr() {
		return commoditySpecificationStr;
	}
	public void setCommoditySpecificationStr(String commoditySpecificationStr) {
		this.commoditySpecificationStr = commoditySpecificationStr;
	}
	public Integer getOutGoods() {
		return outGoods;
	}
	public void setOutGoods(Integer outGoods) {
		this.outGoods = outGoods;
	}
	public Integer getRejectionGoods() {
		return rejectionGoods;
	}
	public void setRejectionGoods(Integer rejectionGoods) {
		this.rejectionGoods = rejectionGoods;
	}
	public Integer getReturnGoods() {
		return returnGoods;
	}
	public void setReturnGoods(Integer returnGoods) {
		this.returnGoods = returnGoods;
	}
	public SalesDetailVO(String orderSubId, String shipTime,
			String createTime, String orderSubNo, String supplierCode,
			String thirdPartyCode, String insideCode, String commodityName,
			String commoditySpecificationStr, Integer outGoods,
			Integer rejectionGoods, Integer returnGoods) {
		super();
		this.orderSubId = orderSubId;
		this.shipTime = shipTime;
		this.createTime = createTime;
		this.orderSubNo = orderSubNo;
		this.supplierCode = supplierCode;
		this.thirdPartyCode = thirdPartyCode;
		this.insideCode = insideCode;
		this.commodityName = commodityName;
		this.commoditySpecificationStr = commoditySpecificationStr;
		this.outGoods = outGoods;
		this.rejectionGoods = rejectionGoods;
		this.returnGoods = returnGoods;
	}
	public SalesDetailVO() {
		super();
	}
	
}
