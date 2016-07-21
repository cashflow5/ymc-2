package com.yougou.kaidian.taobao.model;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2014-8-20 上午10:15:39
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public class TaobaoItemExtend extends TaobaoItem {
	/**
	 * 
	 */
	private static final long serialVersionUID = 10086L;
	private String extendId;
	/**
	 * 商品数字id
	 */
	private Long numIid;

	/**
	 * 商家编码
	 */
	private String merchantCode;

	/**
	 * 商品编码
	 */
	private String yougouCommodityNo;
	/**
	 * 优购价
	 */
	private Double yougouPrice;
	/**
	 * 优购商家款色编码
	 */
	private String yougouSupplierCode;
	/**
	 * 颜色
	 */
	private String yougouSpecName;

	/**
	 * 商品是否已导入优购 TATUS_1("-1", "删除"), STATUS1("1", "已导入优购"),
	 * STATUS0("2","未导入优购");
	 */
	private String isImportYougou;

	/**
	 * 商品导入优购时间
	 */
	private String importYougouTime;

	/**
	 * 
	 * 商品描述
	 */
	private String yougouDescription;

	/**
	 * 校验状态
	 */
	private String checkStatus;

	private String operater;

	private String operated;

	private String taobaoCatFullName;
	
	private String defaultPic;

	/**
	 * add by lsm 
	 */
	private String taobaoCid;
	
	public String getTaobaoCid() {
		return taobaoCid;
	}

	public void setTaobaoCid(String taobaoCid) {
		this.taobaoCid = taobaoCid;
	}

	public String getExtendId() {
		return extendId;
	}

	public void setExtendId(String extendId) {
		this.extendId = extendId;
	}

	public Long getNumIid() {
		return numIid;
	}

	public void setNumIid(Long numIid) {
		this.numIid = numIid;
	}

	public String getYougouCommodityNo() {
		return yougouCommodityNo;
	}

	public void setYougouCommodityNo(String yougouCommodityNo) {
		this.yougouCommodityNo = yougouCommodityNo;
	}

	public Double getYougouPrice() {
		return yougouPrice;
	}

	public void setYougouPrice(Double yougouPrice) {
		this.yougouPrice = yougouPrice;
	}

	public String getYougouSupplierCode() {
		return yougouSupplierCode;
	}

	public void setYougouSupplierCode(String yougouSupplierCode) {
		this.yougouSupplierCode = yougouSupplierCode;
	}

	public String getYougouSpecName() {
		return yougouSpecName;
	}

	public void setYougouSpecName(String yougouSpecName) {
		this.yougouSpecName = yougouSpecName;
	}

	public String getIsImportYougou() {
		return isImportYougou;
	}

	public void setIsImportYougou(String isImportYougou) {
		this.isImportYougou = isImportYougou;
	}

	public String getImportYougouTime() {
		return importYougouTime;
	}

	public void setImportYougouTime(String importYougouTime) {
		this.importYougouTime = importYougouTime;
	}

	public String getYougouDescription() {
		return yougouDescription;
	}

	public void setYougouDescription(String yougouDescription) {
		this.yougouDescription = yougouDescription;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getOperater() {
		return operater;
	}

	public void setOperater(String operater) {
		this.operater = operater;
	}

	public String getOperated() {
		return operated;
	}

	public void setOperated(String operated) {
		this.operated = operated;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getTaobaoCatFullName() {
		return taobaoCatFullName;
	}

	public void setTaobaoCatFullName(String taobaoCatFullName) {
		this.taobaoCatFullName = taobaoCatFullName;
	}

	public String getDefaultPic() {
		return defaultPic;
	}

	public void setDefaultPic(String defaultPic) {
		this.defaultPic = defaultPic;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
