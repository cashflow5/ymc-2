package com.yougou.kaidian.user.model.pojo;


/**
 * 商家发货地址设置
 * @author wang.m
 * @DATE 2012-04-28
 *
 */
public class MerchantconsignmentAdress {

	private String id;
	private String supplyId;//商家id
	private String consignmentName;//发货人姓名
	private String phone;//发货人手机
	private String tell;//发货人电话
	private String postCode;//发货人邮编
	private String hometown;//发货人地区
	private String adress;//发货人地址
	private String creater;//创建人
	private String createTime;//创建时间
	private String remark;//备注
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSupplyId() {
		return supplyId;
	}

	public void setSupplyId(String supplyId) {
		this.supplyId = supplyId;
	}

	public String getConsignmentName() {
		return consignmentName;
	}

	public void setConsignmentName(String consignmentName) {
		this.consignmentName = consignmentName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTell() {
		return tell;
	}

	public void setTell(String tell) {
		this.tell = tell;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public MerchantconsignmentAdress(String id, String supplyId,
			String consignmentName, String phone, String tell, String postCode,
			String hometown, String adress, String creater, String createTime,
			String remark) {
		super();
		this.id = id;
		this.supplyId = supplyId;
		this.consignmentName = consignmentName;
		this.phone = phone;
		this.tell = tell;
		this.postCode = postCode;
		this.hometown = hometown;
		this.adress = adress;
		this.creater = creater;
		this.createTime = createTime;
		this.remark = remark;
	}

	public MerchantconsignmentAdress() {
		super();
	}
	
}
