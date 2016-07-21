package com.belle.other.model.vo;


/**
 * 
 * 类描述：调拨明细
 * 
 * @author xiongjingang
 * @date 2011-5-3 下午01:59:02
 * @email xjg-280@163.com
 */
public class AllocationDetailWmsVo {

	private String commodityCode;
	private String goodsName;
	private String specification;
	private String units;
	private Integer allocationQuantity;

	public AllocationDetailWmsVo() {
		super();
	}

	public AllocationDetailWmsVo( String commodityCode, String goodsName, String specification, String units,
			Integer allocationQuantity) {
		super();
		this.commodityCode = commodityCode;
		this.goodsName = goodsName;
		this.specification = specification;
		this.units = units;
		this.allocationQuantity = allocationQuantity;
	}

	public String getCommodityCode() {
		return commodityCode;
	}

	public void setCommodityCode(String commodityCode) {
		this.commodityCode = commodityCode;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public Integer getAllocationQuantity() {
		return allocationQuantity;
	}

	public void setAllocationQuantity(Integer allocationQuantity) {
		this.allocationQuantity = allocationQuantity;
	}

}
